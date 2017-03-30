package com.sinosoft.midplat.newccb.bat;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

import cn.ccb.secapi.SecAPI;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ContBlcDtlSchema;
import com.sinosoft.lis.vschema.ContBlcDtlSet;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.bat.BatConf;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.newccb.util.FileUtil;
import com.sinosoft.utility.ElementLis;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;

public class NewCcbBuBalance extends TimerTask implements XmlTag
{
	protected final Logger cLogger = Logger.getLogger(getClass());

	protected Date cTranDate;
	private String typeCode = null;
	private String secNodeId = "510050";
	private String rmtSecNodeId = "105005";
	private String fileName = null;
	private String filePath = null;
	protected String cResultMsg;
	private Element cBusiConfRoot = null;
	// protected Element cThisConfRoot = null;
	protected Element cThisBusiConf = null;
	private Document returnNoStd = null;// ���ܺ�ķǱ�׼����
	private Document cInXmlDoc = null;// ת����ı�׼����
	private java.util.List<Element> Detail = new ArrayList<Element>();
	protected Element cMidplatRoot = null;
	protected Element cBatchConfRoot = null;
	protected Element cNewCcbConfRoot = null;
	private String fileDate = null;

	public void run()
	{
		try
		{
			Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
			Document cOutXmlDoc = null;
			cLogger.info("Into NewCcbBuBalance.run()!");
			cResultMsg = null;
			cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
			cBatchConfRoot = BatConf.newInstance().getConf().getRootElement();
			cNewCcbConfRoot = NewCcbConf.newInstance().getConf().getRootElement();
			cThisBusiConf = ((Element) XPath.selectSingleNode(cNewCcbConfRoot, "business[funcFlag='" + "3009" + "']"));

			// String ccbDirFile="D:/YBT_SAVE_XML/ZHH/newccb/localrcv/";
			System.out.println(cThisBusiConf.getChildText("name"));
			//���б�������λ��
			//String ccbDirFile = cThisBusiConf.getChildText("ccbLocal");
			String ccbDirFile = cThisBusiConf.getChildText("LocalDir");
			File oldFile = new File(ccbDirFile);
			File[] file = oldFile.listFiles();

			for (File tFile : file)
			{
				String pLocalDir = tFile.getParent();
				if ((pLocalDir != null) && (!"".equals(pLocalDir)))
				{
					pLocalDir = pLocalDir.replace('\\', '/');
					if (!pLocalDir.endsWith("/"))
					{
						pLocalDir = pLocalDir + '/';
					}
					System.out.println(pLocalDir + tFile.getName());
					String type = tFile.getName().substring(0, 3);
					//������������,��ȡ�ļ�����
					if ("Rcn".equals(type))
					{
						fileDate = tFile.getName().substring(10, 18);
					}
					fileSecurity(type, pLocalDir, tFile.getName());
					if(type.equals("Bnk")){
						cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContCardBlc).call(cInXmlDoc);
						if(cOutXmlDoc != null && "0".equals(cOutXmlDoc.getRootElement().getChild(Head).getChildText("Flag"))){
							cResultMsg = cOutXmlDoc.getRootElement().getChild(Head).getChildText("Desc");
							cLogger.info("�½��е�֤�����˳ɹ�");
						}else{
							cResultMsg = "�½��е�֤������ʧ��";
							cLogger.info("�½��е�֤������ʧ��");
						}
					}
				}
			}
		}
		catch (MidplatException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		cLogger.info("Out NewCcbBuBalance.run()!");
	}

	// �������з��͹����ļ����ļ������Ҷ�ȡ�ļ������ݲ�ͬ����ת���ɲ�ͬ��׼���ġ�
	public Document fileSecurity(String type, String filePath, String fileName) throws MidplatException
	{
		cLogger.info("Into NewCcbBuBalance.fileSecurity()...");

		typeCode = type;
		try
		{

			if (typeCode.equals("Bnk"))
			{// �������ж˵�֤��Ϣ
				try
				{
					cThisBusiConf = (Element) XPath.selectSingleNode(cNewCcbConfRoot, "business[funcFlag='3006']");
				}
				catch (JDOMException e)
				{
					e.printStackTrace();
				}
				try
				{
					cLogger.info("FileName = " + fileName);
					cLogger.info("FilePath==" + filePath);
					// ���ܺ�ı���·��
					System.out.println(cThisBusiConf.getChildText("name"));
					// String mFilePath = new StringBuilder(SysInfo.cHome)
					// ����
//					cLogger.info("����ǰ�Ķ����ļ����·����" + filePath + fileName);
//					cLogger.info("���ܺ�Ķ����ļ����·����" + mFilePath + fileName);
//					SecAPI.nodeInit(secNodeId);
//					secNodeId = "510050";
//					rmtSecNodeId = "105005";
//					cLogger.info("secNodeId:" + secNodeId + " rmtSecNodeId:" + rmtSecNodeId);
//					SecAPI.fileUnEnvelop(secNodeId, rmtSecNodeId, filePath + fileName, mFilePath + fileName);
					// ���ݽ��ܺ���·�������ƻ�ȡ������
					InputStream ttBatIns = getLocalFile(filePath, fileName);
					// ��ȡ�Ǳ�׼����
					returnNoStd = JdomUtil.build(ttBatIns, "UTF-8");
					// JdomUtil.print(returnNoStd);
				}
				catch (Exception ex)
				{
					cLogger.error("��ñ�׼���˱��ĳ���", ex);
					throw new MidplatException("��ȡ���ܱ��ĳ���");
				}
				try
				{
					// ��װ���ı�׼����
					cInXmlDoc = docuInfoTo(returnNoStd);
				}
				catch (Exception ex)
				{
					throw new MidplatException("��ȡ��׼���ĳ���");
				}
			}
		}
		catch (Exception ex)
		{
			throw new MidplatException("�ļ����ܹ��̱���");
		}

		cLogger.info("Out NewCcbBuBalance.fileSecurity()...");
		return cInXmlDoc;
	}

	private InputStream getLocalFile(String pDir, String pName) throws MidplatException
	{
		cLogger.info("Into NewCcbBuBalance.getLocalFile()...");

		pDir = pDir.replace('\\', '/');
		if (!pDir.endsWith("/"))
		{
			pDir += '/';
		}
		String mPathName = pDir + pName;
		cLogger.info("LocalPath = " + mPathName);

		InputStream mIns = null;
		try
		{
			mIns = new FileInputStream(mPathName);
		}
		catch (IOException ex)
		{
			// δ�ҵ������ļ�
			throw new MidplatException("δ�ҵ������ļ���" + mPathName);
		}

		cLogger.info("Out NewCcbBuBalance.getLocalFile()!");
		return mIns;
	}

	// ��֤����
	private Document docuInfoTo(Document returnNoStd)
	{
		Document tInXmlDoc = null;

		Element Detail_List = returnNoStd.getRootElement().getChild("Detail_List");
		Element mHead = returnNoStd.getRootElement().getChild("Head");
		ElementLis TranData = new ElementLis("TranData");
		ElementLis Body = new ElementLis("Body", TranData);
		Element Head = new Element("Head");
		Head = getHead();
		// ���ɱ�׼����ͷ
		Date tCurDate = new Date();
		Element mTranDate = new ElementLis("TranDate", fileDate, Head);
		Element mTranTime = new ElementLis("TranTime", String.valueOf(DateUtil.get6Time(tCurDate)), Head);
		Element mNodeNo = new Element("NodeNo");
		mNodeNo.setText(cThisBusiConf.getChildText("NodeNo"));// newccb.xml����
		Element mTellerNo = new Element("TellerNo");
		mTellerNo.setText(CodeDef.SYS);
		Head.addContent(mNodeNo);
		Head.addContent(mTellerNo);
		TranData.addContent(Head);

		if (Detail_List != null)
		{// �������ļ�Ϊ�յ�ʱ����û��Detail_List�ڵ��

			try
			{
				Detail = Detail_List.getChildren("Detail");
				int size = Detail.size();

				ElementLis Count = new ElementLis("Count", Body);
				Count.setText(String.valueOf(size));

				for (int i = 0; i < size; i++)
				{
					Element tempEle = Detail.get(i);
					ElementLis listDetail = new ElementLis("Detail", Body);
					// �ؿ�����
					ElementLis CardType = new ElementLis("CardType", tempEle.getChildTextTrim("Ins_IBVoch_ID").substring(0, 7), listDetail);
					// �ؿ�ӡˢ��
					ElementLis CardNo = new ElementLis("CardNo", tempEle.getChildTextTrim("Ins_IBVoch_ID"), listDetail);
					// �ؿ�״̬
					ElementLis CardState = new ElementLis("CardState", tempEle.getChildTextTrim("IpOpR_Crcl_StCd"), listDetail);
					if (CardState.getText().equals("01"))
					{// δʹ��
						CardState.setText("9");
					}
					if (CardState.getText().equals("03"))
					{// ��ʹ��--���N
						CardState.setText("4");
					}
					if (CardState.getText().equals("04"))
					{// ������--���U
						CardState.setText("6");
					}
					// ��֤������
					ElementLis OtherNo = new ElementLis("OtherNo", listDetail);
					// ���ʻ�����
					ElementLis AgentCom = new ElementLis("AgentCom", listDetail);

					ElementLis TellerNo = new ElementLis("TellerNo", listDetail);
					// ���з�������ˮ��
					ElementLis TranNo = new ElementLis("TranNo", tempEle.getChildTextTrim("RqPtTcNum"), listDetail);
				}
				tInXmlDoc = new Document(TranData);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}

		}
		else
		{// �����ļ�����Ϊ��
			tInXmlDoc = new Document(TranData);
		}

		return tInXmlDoc;
	}

	// ���ն��˺ͱ�ȫ����
	private Document balanceTo(Document returnNoStd) throws Exception
	{
		// JdomUtil.print(returnNoStd);
		Document tInXmlDoc = null;

		Element Detail_List = returnNoStd.getRootElement().getChild("Detail_List");

		// ��װ��׼����

		ElementLis TranData = new ElementLis("TranData");
		ElementLis Body = new ElementLis("Body", TranData);
		Element Head = getHead();
		Date tCurDate = new Date();
		Element mTranDate = new ElementLis("TranDate", fileDate, Head);
		Element mTranTime = new ElementLis("TranTime", String.valueOf(DateUtil.get6Time(tCurDate)), Head);
		Element mNodeNo = new Element("NodeNo");
		mNodeNo.setText(cThisBusiConf.getChildText("NodeNo"));// newccb.xml����
		Element mTellerNo = new Element("TellerNo");
		mTellerNo.setText(CodeDef.SYS);
		Head.addContent(mNodeNo);
		Head.addContent(mTellerNo);
		TranData.addContent(Head);

		if (Detail_List != null)
		{// �������ļ�Ϊ�յ�ʱ����û��Detail_List�ڵ��

			try
			{
				Detail = Detail_List.getChildren("Detail");
				Element mHead = returnNoStd.getRootElement().getChild("Head");
				int size = Detail.size();
				int cnt = 0;
				cLogger.info("����" + size + "�ʶ��ˣ�");

				if (size > 0)
				{
					for (int i = 0; i < size; i++)
					{
						ElementLis listDetail = new ElementLis("Detail", Body);
						Element tempEle = Detail.get(i);
						// ElementLis check_trans = new
						// ElementLis("check-trans",check_trans_list);
						// ���ն���
						if (typeCode.equals("Rcn"))
						{
							// ElementLis Count = new ElementLis("Count",Body);
							// ElementLis sumPrem = new ElementLis("Prem",Body);
							cLogger.info("========����������˱���ƴװ=========");
							ElementLis BusiType = new ElementLis("BusiType", listDetail);
							if (tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819152"))
							{// �µ��ɷ�
								BusiType.setText("01");
							}
							if (tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819156"))
							{// ���ڽɷ�
								BusiType.setText("11");
							}
							cnt++;
							ElementLis ContNo = new ElementLis("ContNo", tempEle.getChildTextTrim("InsPolcy_No"), listDetail);
							ElementLis Prem = new ElementLis("Prem", tempEle.getChildTextTrim("TxnAmt"), listDetail);
							ElementLis AgentCom = new ElementLis("AgentCom", tempEle.getChildTextTrim("CCBIns_ID"), listDetail);
							ElementLis ProposalPrtNo = new ElementLis("ProposalPrtNo", "", listDetail);
							ElementLis AppntName = new ElementLis("AppntName", "", listDetail);
							ElementLis InsuredName = new ElementLis("InsuredName", "", listDetail);
							ElementLis TranDate = new ElementLis("TranDate", tempEle.getChildTextTrim("Txn_Dt"), listDetail);
							// mClientIpEle.setText("3005");
						}
						// ��ȫ����
						if (typeCode.equals("Pre"))
						{// ���а��޸ı���������ϢP53819161Ҳ�ŵ���ȫ�������ˣ����Ǻ��Ĳ������˴�����
						// mClientIpEle.setText("3006");
							cLogger.info("========���뱣ȫ���˱���ƴװ=========");
							ElementLis BusiType = new ElementLis("BusiType", listDetail);
							if (tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819192"))
							{// ���ڸ���
								BusiType.setText("09");
								cnt++;
							}
							if (tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819144"))
							{// �˱�
								BusiType.setText("10");
								cnt++;
							}
							if (tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819161"))
							{// �޸ı���������Ϣ
								BusiType.setText("07");
								cnt++;
							}
							ElementLis TranNo = new ElementLis("TranNo", tempEle.getChildTextTrim("Ins_Co_Jrnl_No"), listDetail);
							ElementLis NodeNo = new ElementLis("NodeNo", tempEle.getChildTextTrim("CCBIns_ID"), listDetail);
							ElementLis ContNo = new ElementLis("ContNo", tempEle.getChildTextTrim("InsPolcy_No"), listDetail);
							ElementLis EdorNo = new ElementLis("EdorNo", tempEle.getChildTextTrim("InsPolcy_Vchr_No"), listDetail);
							ElementLis AccNo = new ElementLis("AccNo", listDetail);
							ElementLis AccName = new ElementLis("AccName", "", listDetail);
							ElementLis Prem = new ElementLis("Prem", "0", listDetail);// ��ʵ��ȫ����Ҫ�����ֻ����saveDetail��ʱ��ȡֵ�����Ը����ڵ㣬��Ȼ��ָ���쳣
							ElementLis ProposalPrtNo = new ElementLis("ProposalPrtNo", "", listDetail);// ��ʵ��ȫ����Ҫ�����ֻ����saveDetail��ʱ��ȡֵ�����Ը����ڵ㣬��Ȼ��ָ���쳣
							ElementLis TranDate = new ElementLis("TranDate", tempEle.getChildTextTrim("Txn_Dt"), listDetail);

						}

					}
				}
				tInXmlDoc = new Document(TranData);
				if (typeCode.equals("Pre"))
				{
					cLogger.info("========�����������Count��Prem����ƴװ=========");
					JdomUtil.print(tInXmlDoc);
					Element tBody = tInXmlDoc.getRootElement().getChild("Body");
					java.util.List<Element> tDetail = tBody.getChildren("Detail");
					long mSumPrem = 0;
					for (int i = 0; i < tDetail.size(); i++)
					{
						long tPremFen = NumberUtil.yuanToFen(((Element) tDetail.get(i)).getChildText("Prem"));
						System.out.println("���˱��ѣ�" + tPremFen);
						tDetail.get(i).getChild("Prem").setText(String.valueOf(tPremFen));
						mSumPrem += tPremFen;
					}
					cLogger.info("====�����ܱ���===" + mSumPrem);
					try
					{
						ElementLis sumCount = new ElementLis("Count", Body);// �ܱ���
						ElementLis sumPrem = new ElementLis("Prem", Body);// �ܱ���
						tBody.getChild("Prem").setText(String.valueOf(mSumPrem));
						tBody.getChild("Count").setText(String.valueOf(cnt));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				if (typeCode.equals("Pre"))
				{
					cLogger.info("========���뱣ȫ����Count��Prem����ƴװ=========");
					JdomUtil.print(tInXmlDoc);
					Element tBody = tInXmlDoc.getRootElement().getChild("Body");
					java.util.List<Element> tDetail = tBody.getChildren("Detail");
					try
					{
						ElementLis sumCount = new ElementLis("Count", Body);// �ܱ���
						ElementLis sumPrem = new ElementLis("Prem", Body);// �ܱ���
																			// ����contblcdtl�Ǻ�ȡprem�����Բ���û��
						tBody.getChild("Prem").setText("0");
						tBody.getChild("Count").setText(String.valueOf(cnt));
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}

		}
		else
		{// �����ļ�����Ϊ��
			tInXmlDoc = new Document(TranData);
			ElementLis sumCount = new ElementLis("Count", Body);// �ܱ���
			ElementLis sumPrem = new ElementLis("Prem", Body);// �ܱ���
			tInXmlDoc.getRootElement().getChild("Body").getChild("Prem").setText("0");
			tInXmlDoc.getRootElement().getChild("Body").getChild("Count").setText("0");
		}

		cLogger.info("��ҿ���,��׼����զ����");
		JdomUtil.print(tInXmlDoc);

		try
		{

			if (!"0".equals(tInXmlDoc.getRootElement().getChild("Body").getChildText("Count")))
			{
				saveDetails(tInXmlDoc);
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return tInXmlDoc;
	}

	protected Element getHead()
	{
		cLogger.info("Into NewCcbBuBalance.getHead()...");

		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));

		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));

		Element mTranCom = (Element) cNewCcbConfRoot.getChild(TranCom).clone();

		Element mNodeNo = (Element) cThisBusiConf.getChild(NodeNo).clone();

		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText(CodeDef.SYS);

		Element mTranNo = new Element(TranNo);
		mTranNo.setText(Thread.currentThread().getName());

		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cThisBusiConf.getChildText(funcFlag));

		Element mTranLogNo = new Element("TranLogNo");
		mTranLogNo.setText(Thread.currentThread().getName());

		// ����ͷ������Ӻ��ĵ����б���
		Element mBankCode = new Element("BankCode");
		mBankCode.setText(cThisBusiConf.getChildText("BankCode"));

		Element mHead = new Element(Head);
		mHead.addContent(mTranDate);
		mHead.addContent(mTranTime);
		mHead.addContent(mTranCom);
		mHead.addContent(mNodeNo);
		mHead.addContent(mTellerNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);
		mHead.addContent(mTranLogNo);
		// ����ͷ������Ӻ��ĵ����б���
		mHead.addContent(mBankCode);

		cLogger.info("Out NewCcbBuBalance.getHead()!");
		return mHead;
	}

	/**
	 * ���������ϸ�����ر������ϸ����(ContBlcDtlSet)
	 */
	@SuppressWarnings("unchecked")
	protected ContBlcDtlSet saveDetails(Document pXmlDoc) throws Exception
	{
		cLogger.debug("Into NewCcbBuBalance.saveDetails()...");

		TranLogDB cTranLogDB = new TranLogDB();
		cTranLogDB.setLogNo(Thread.currentThread().getName());
		System.out.println("��������" + Thread.currentThread().getName());

		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mBodyEle = mTranDataEle.getChild(Body);
		Element mHeadEle = mTranDataEle.getChild(Head);
		String sfuncflag = mHeadEle.getChildText("FuncFlag");
		// int mCount = Integer.parseInt(mBodyEle.getChildText(Count));
		// long mSumPrem = Long.parseLong(mBodyEle.getChildText(Prem));
		// double mSumPrem = Double.parseDouble(mBodyEle.getChildText(Prem));
		List<Element> mDetailList = mBodyEle.getChildren("Detail");
		ContBlcDtlSet mContBlcDtlSet = new ContBlcDtlSet();
		// if (mDetailList.size() != mCount) {
		// throw new MidplatException("���ܱ�������ϸ����������"+ mCount + "!=" +
		// mDetailList.size());
		// }
		double mSumDtlPrem = 0;
		for (Element tDetailEle : mDetailList)
		{
			mSumDtlPrem += Double.parseDouble(tDetailEle.getChildText(Prem));

			ContBlcDtlSchema tContBlcDtlSchema = new ContBlcDtlSchema();
			tContBlcDtlSchema.setBlcTranNo(cTranLogDB.getLogNo());
			tContBlcDtlSchema.setContNo(tDetailEle.getChildText(ContNo));
			tContBlcDtlSchema.setProposalPrtNo(tDetailEle.getChildText(ProposalPrtNo)); // ��Щ���д�
			// tContBlcDtlSchema.setTranDate(cTranLogDB.getTranDate());
			tContBlcDtlSchema.setTranDate(fileDate);
			tContBlcDtlSchema.setTranCom(cTranLogDB.getTranCom());
			// tContBlcDtlSchema.setNodeNo(tDetailEle.getChildText(NodeNo));
			tContBlcDtlSchema.setMakeDate(cTranLogDB.getMakeDate());
			tContBlcDtlSchema.setMakeTime(cTranLogDB.getMakeTime());
			tContBlcDtlSchema.setModifyDate(tContBlcDtlSchema.getMakeDate());
			tContBlcDtlSchema.setModifyTime(tContBlcDtlSchema.getMakeTime());
			tContBlcDtlSchema.setOperator(CodeDef.SYS);
			tContBlcDtlSchema.setType(tDetailEle.getChildText("BusiType"));// �ڶ�����ϸ����������01�µ���11���ڣ�09���ڣ�10�˱�
			if ("1048".equals(sfuncflag))
			{// ��ȫ��һ�������
				tContBlcDtlSchema.setBak1(tDetailEle.getChildText("NodeNo"));// ���������
				tContBlcDtlSchema.setBak2(tDetailEle.getChildText("TranNo"));// ���潻����ˮ��
				tContBlcDtlSchema.setBak3(tDetailEle.getChildText("EdorNo"));// ���汣ȫ�����
				tContBlcDtlSchema.setPrem(0);
			}
			else
			{
				tContBlcDtlSchema.setPrem((int) NumberUtil.yuanToFen(tDetailEle.getChildText(Prem)));
			}
			tContBlcDtlSchema.setBak4(tDetailEle.getChildText("TranDate"));// ����˱�����������
			mContBlcDtlSet.add(tContBlcDtlSchema);
		}
		// if (mSumPrem != mSumDtlPrem) {
		// throw new MidplatException("���ܽ������ϸ�ܽ�����"+ mSumPrem + "!=" +
		// mSumDtlPrem);
		// }

		/**
		 * �����з������Ķ�����ϸ�洢��������ϸ��(ContBlcDtl)��
		 */
		cLogger.info("������ϸ����(DtlSet)Ϊ��" + mContBlcDtlSet.size());
		MMap mSubmitMMap = new MMap();
		mSubmitMMap.put(mContBlcDtlSet, "INSERT");
		VData mSubmitVData = new VData();
		mSubmitVData.add(mSubmitMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, ""))
		{
			cLogger.error("���������ϸʧ�ܣ�" + mPubSubmit.mErrors.getFirstError());
			throw new MidplatException("���������ϸʧ�ܣ�");
		}

		cLogger.debug("Out NewCcbBuBalance.saveDetails()!");
		return mContBlcDtlSet;
	}

	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException
	{
		cLogger.debug("Into NewCcbBuBalance.insertTranLog()...");

		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mHeadEle = mTranDataEle.getChild(Head);
		Element mBodyEle = mTranDataEle.getChild(Body);

		TranLogDB mTranLogDB = new TranLogDB();
		if ("11".equals(mBodyEle.getChildText("TranType")))
		{// �˴�����Ϊֻ��һ�����̣�tranlog��������logno
			mTranLogDB.setLogNo(String.valueOf(Integer.parseInt(Thread.currentThread().getName()) + 1));
			System.out.println("��������" + String.valueOf(Integer.parseInt(Thread.currentThread().getName()) + 1));
			mTranLogDB.setBak1("11");
		}
		else
		{
			mTranLogDB.setLogNo(Thread.currentThread().getName());
			System.out.println("��������" + Thread.currentThread().getName());
			mTranLogDB.setBak1("01");
		}
		mTranLogDB.setTranCom(mHeadEle.getChildText(TranCom));
		mTranLogDB.setZoneNo(mHeadEle.getChildText("ZoneNo"));
		mTranLogDB.setNodeNo(mHeadEle.getChildText(NodeNo));
		mTranLogDB.setTranNo(mHeadEle.getChildText(TranNo));
		mTranLogDB.setOperator(mHeadEle.getChildText(TellerNo));
		mTranLogDB.setFuncFlag(mHeadEle.getChildText(FuncFlag));
		mTranLogDB.setTranDate(mHeadEle.getChildText(TranDate));
		mTranLogDB.setTranTime(mHeadEle.getChildText(TranTime));

		mTranLogDB.setRCode(CodeDef.RCode_NULL);
		mTranLogDB.setUsedTime(-1);
		Date mCurDate = new Date();
		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
		mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
		mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
		if (!mTranLogDB.insert())
		{
			cLogger.error(mTranLogDB.mErrors.getFirstError());
			throw new MidplatException("������־ʧ�ܣ�");
		}

		cLogger.debug("Out NewCcbBuBalance.insertTranLog()!");
		return mTranLogDB;
	}
	public String getResultMsg()
	{
		return this.cResultMsg;
	}
	/*
	 * ���ö�������
	 */
	public final void setDate(String p8DateStr)
	{
		this.cTranDate = DateUtil.parseDate(p8DateStr, "yyyyMMdd");
	}
}
