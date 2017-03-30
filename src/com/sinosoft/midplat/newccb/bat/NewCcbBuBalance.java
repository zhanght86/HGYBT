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
	private Document returnNoStd = null;// 解密后的非标准报文
	private Document cInXmlDoc = null;// 转换后的标准报文
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
			//银行报文所在位置
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
					//如果是账务对账,获取文件日期
					if ("Rcn".equals(type))
					{
						fileDate = tFile.getName().substring(10, 18);
					}
					fileSecurity(type, pLocalDir, tFile.getName());
					if(type.equals("Bnk")){
						cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContCardBlc).call(cInXmlDoc);
						if(cOutXmlDoc != null && "0".equals(cOutXmlDoc.getRootElement().getChild(Head).getChildText("Flag"))){
							cResultMsg = cOutXmlDoc.getRootElement().getChild(Head).getChildText("Desc");
							cLogger.info("新建行单证补对账成功");
						}else{
							cResultMsg = "新建行单证补对账失败";
							cLogger.info("新建行单证补对账失败");
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

	// 解密银行发送过来的加密文件，并且读取文件，根据不同交易转换成不同标准报文。
	public Document fileSecurity(String type, String filePath, String fileName) throws MidplatException
	{
		cLogger.info("Into NewCcbBuBalance.fileSecurity()...");

		typeCode = type;
		try
		{

			if (typeCode.equals("Bnk"))
			{// 发送银行端单证信息
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
					// 解密后的报文路径
					System.out.println(cThisBusiConf.getChildText("name"));
					// String mFilePath = new StringBuilder(SysInfo.cHome)
					// 解密
//					cLogger.info("解密前的对账文件存放路径：" + filePath + fileName);
//					cLogger.info("解密后的对账文件存放路径：" + mFilePath + fileName);
//					SecAPI.nodeInit(secNodeId);
//					secNodeId = "510050";
//					rmtSecNodeId = "105005";
//					cLogger.info("secNodeId:" + secNodeId + " rmtSecNodeId:" + rmtSecNodeId);
//					SecAPI.fileUnEnvelop(secNodeId, rmtSecNodeId, filePath + fileName, mFilePath + fileName);
					// 根据解密后报文路径和名称获取数据流
					InputStream ttBatIns = getLocalFile(filePath, fileName);
					// 获取非标准报文
					returnNoStd = JdomUtil.build(ttBatIns, "UTF-8");
					// JdomUtil.print(returnNoStd);
				}
				catch (Exception ex)
				{
					cLogger.error("获得标准对账报文出错！", ex);
					throw new MidplatException("获取解密报文出错");
				}
				try
				{
					// 组装核心标准报文
					cInXmlDoc = docuInfoTo(returnNoStd);
				}
				catch (Exception ex)
				{
					throw new MidplatException("获取标准报文出错");
				}
			}
		}
		catch (Exception ex)
		{
			throw new MidplatException("文件解密过程报错！");
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
			// 未找到对账文件
			throw new MidplatException("未找到对账文件！" + mPathName);
		}

		cLogger.info("Out NewCcbBuBalance.getLocalFile()!");
		return mIns;
	}

	// 单证对账
	private Document docuInfoTo(Document returnNoStd)
	{
		Document tInXmlDoc = null;

		Element Detail_List = returnNoStd.getRootElement().getChild("Detail_List");
		Element mHead = returnNoStd.getRootElement().getChild("Head");
		ElementLis TranData = new ElementLis("TranData");
		ElementLis Body = new ElementLis("Body", TranData);
		Element Head = new Element("Head");
		Head = getHead();
		// 生成标准报文头
		Date tCurDate = new Date();
		Element mTranDate = new ElementLis("TranDate", fileDate, Head);
		Element mTranTime = new ElementLis("TranTime", String.valueOf(DateUtil.get6Time(tCurDate)), Head);
		Element mNodeNo = new Element("NodeNo");
		mNodeNo.setText(cThisBusiConf.getChildText("NodeNo"));// newccb.xml配置
		Element mTellerNo = new Element("TellerNo");
		mTellerNo.setText(CodeDef.SYS);
		Head.addContent(mNodeNo);
		Head.addContent(mTellerNo);
		TranData.addContent(Head);

		if (Detail_List != null)
		{// 当对账文件为空的时候是没有Detail_List节点的

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
					// 重空类型
					ElementLis CardType = new ElementLis("CardType", tempEle.getChildTextTrim("Ins_IBVoch_ID").substring(0, 7), listDetail);
					// 重空印刷号
					ElementLis CardNo = new ElementLis("CardNo", tempEle.getChildTextTrim("Ins_IBVoch_ID"), listDetail);
					// 重空状态
					ElementLis CardState = new ElementLis("CardState", tempEle.getChildTextTrim("IpOpR_Crcl_StCd"), listDetail);
					if (CardState.getText().equals("01"))
					{// 未使用
						CardState.setText("9");
					}
					if (CardState.getText().equals("03"))
					{// 已使用--回銷
						CardState.setText("4");
					}
					if (CardState.getText().equals("04"))
					{// 已作废--作廢
						CardState.setText("6");
					}
					// 单证关联号
					ElementLis OtherNo = new ElementLis("OtherNo", listDetail);
					// 记帐机构号
					ElementLis AgentCom = new ElementLis("AgentCom", listDetail);

					ElementLis TellerNo = new ElementLis("TellerNo", listDetail);
					// 建行方记帐流水号
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
		{// 对账文件内容为空
			tInXmlDoc = new Document(TranData);
		}

		return tInXmlDoc;
	}

	// 日终对账和保全对账
	private Document balanceTo(Document returnNoStd) throws Exception
	{
		// JdomUtil.print(returnNoStd);
		Document tInXmlDoc = null;

		Element Detail_List = returnNoStd.getRootElement().getChild("Detail_List");

		// 封装标准报文

		ElementLis TranData = new ElementLis("TranData");
		ElementLis Body = new ElementLis("Body", TranData);
		Element Head = getHead();
		Date tCurDate = new Date();
		Element mTranDate = new ElementLis("TranDate", fileDate, Head);
		Element mTranTime = new ElementLis("TranTime", String.valueOf(DateUtil.get6Time(tCurDate)), Head);
		Element mNodeNo = new Element("NodeNo");
		mNodeNo.setText(cThisBusiConf.getChildText("NodeNo"));// newccb.xml配置
		Element mTellerNo = new Element("TellerNo");
		mTellerNo.setText(CodeDef.SYS);
		Head.addContent(mNodeNo);
		Head.addContent(mTellerNo);
		TranData.addContent(Head);

		if (Detail_List != null)
		{// 当对账文件为空的时候是没有Detail_List节点的

			try
			{
				Detail = Detail_List.getChildren("Detail");
				Element mHead = returnNoStd.getRootElement().getChild("Head");
				int size = Detail.size();
				int cnt = 0;
				cLogger.info("共有" + size + "笔对账！");

				if (size > 0)
				{
					for (int i = 0; i < size; i++)
					{
						ElementLis listDetail = new ElementLis("Detail", Body);
						Element tempEle = Detail.get(i);
						// ElementLis check_trans = new
						// ElementLis("check-trans",check_trans_list);
						// 日终对账
						if (typeCode.equals("Rcn"))
						{
							// ElementLis Count = new ElementLis("Count",Body);
							// ElementLis sumPrem = new ElementLis("Prem",Body);
							cLogger.info("========进入账务对账报文拼装=========");
							ElementLis BusiType = new ElementLis("BusiType", listDetail);
							if (tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819152"))
							{// 新单缴费
								BusiType.setText("01");
							}
							if (tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819156"))
							{// 续期缴费
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
						// 保全对账
						if (typeCode.equals("Pre"))
						{// 建行把修改保单基本信息P53819161也放到保全里面来了，但是核心并不对账此类型
						// mClientIpEle.setText("3006");
							cLogger.info("========进入保全对账报文拼装=========");
							ElementLis BusiType = new ElementLis("BusiType", listDetail);
							if (tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819192"))
							{// 满期给付
								BusiType.setText("09");
								cnt++;
							}
							if (tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819144"))
							{// 退保
								BusiType.setText("10");
								cnt++;
							}
							if (tempEle.getChildTextTrim("ORG_TX_ID").equals("P53819161"))
							{// 修改保单基本信息
								BusiType.setText("07");
								cnt++;
							}
							ElementLis TranNo = new ElementLis("TranNo", tempEle.getChildTextTrim("Ins_Co_Jrnl_No"), listDetail);
							ElementLis NodeNo = new ElementLis("NodeNo", tempEle.getChildTextTrim("CCBIns_ID"), listDetail);
							ElementLis ContNo = new ElementLis("ContNo", tempEle.getChildTextTrim("InsPolcy_No"), listDetail);
							ElementLis EdorNo = new ElementLis("EdorNo", tempEle.getChildTextTrim("InsPolcy_Vchr_No"), listDetail);
							ElementLis AccNo = new ElementLis("AccNo", listDetail);
							ElementLis AccName = new ElementLis("AccName", "", listDetail);
							ElementLis Prem = new ElementLis("Prem", "0", listDetail);// 其实保全不需要这个，只是在saveDetail的时候取值，所以给个节点，不然空指针异常
							ElementLis ProposalPrtNo = new ElementLis("ProposalPrtNo", "", listDetail);// 其实保全不需要这个，只是在saveDetail的时候取值，所以给个节点，不然空指针异常
							ElementLis TranDate = new ElementLis("TranDate", tempEle.getChildTextTrim("Txn_Dt"), listDetail);

						}

					}
				}
				tInXmlDoc = new Document(TranData);
				if (typeCode.equals("Pre"))
				{
					cLogger.info("========进入账务对账Count和Prem报文拼装=========");
					JdomUtil.print(tInXmlDoc);
					Element tBody = tInXmlDoc.getRootElement().getChild("Body");
					java.util.List<Element> tDetail = tBody.getChildren("Detail");
					long mSumPrem = 0;
					for (int i = 0; i < tDetail.size(); i++)
					{
						long tPremFen = NumberUtil.yuanToFen(((Element) tDetail.get(i)).getChildText("Prem"));
						System.out.println("对账保费：" + tPremFen);
						tDetail.get(i).getChild("Prem").setText(String.valueOf(tPremFen));
						mSumPrem += tPremFen;
					}
					cLogger.info("====最终总保费===" + mSumPrem);
					try
					{
						ElementLis sumCount = new ElementLis("Count", Body);// 总比数
						ElementLis sumPrem = new ElementLis("Prem", Body);// 总保费
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
					cLogger.info("========进入保全对账Count和Prem报文拼装=========");
					JdomUtil.print(tInXmlDoc);
					Element tBody = tInXmlDoc.getRootElement().getChild("Body");
					java.util.List<Element> tDetail = tBody.getChildren("Detail");
					try
					{
						ElementLis sumCount = new ElementLis("Count", Body);// 总比数
						ElementLis sumPrem = new ElementLis("Prem", Body);// 总保费
																			// 插入contblcdtl是后取prem，所以不能没有
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
		{// 对账文件内容为空
			tInXmlDoc = new Document(TranData);
			ElementLis sumCount = new ElementLis("Count", Body);// 总比数
			ElementLis sumPrem = new ElementLis("Prem", Body);// 总保费
			tInXmlDoc.getRootElement().getChild("Body").getChild("Prem").setText("0");
			tInXmlDoc.getRootElement().getChild("Body").getChild("Count").setText("0");
		}

		cLogger.info("大家看看,标准报文咋样：");
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

		// 报文头结点增加核心的银行编码
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
		// 报文头结点增加核心的银行编码
		mHead.addContent(mBankCode);

		cLogger.info("Out NewCcbBuBalance.getHead()!");
		return mHead;
	}

	/**
	 * 保存对账明细，返回保存的明细数据(ContBlcDtlSet)
	 */
	@SuppressWarnings("unchecked")
	protected ContBlcDtlSet saveDetails(Document pXmlDoc) throws Exception
	{
		cLogger.debug("Into NewCcbBuBalance.saveDetails()...");

		TranLogDB cTranLogDB = new TranLogDB();
		cTranLogDB.setLogNo(Thread.currentThread().getName());
		System.out.println("进程名：" + Thread.currentThread().getName());

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
		// throw new MidplatException("汇总笔数与明细笔数不符！"+ mCount + "!=" +
		// mDetailList.size());
		// }
		double mSumDtlPrem = 0;
		for (Element tDetailEle : mDetailList)
		{
			mSumDtlPrem += Double.parseDouble(tDetailEle.getChildText(Prem));

			ContBlcDtlSchema tContBlcDtlSchema = new ContBlcDtlSchema();
			tContBlcDtlSchema.setBlcTranNo(cTranLogDB.getLogNo());
			tContBlcDtlSchema.setContNo(tDetailEle.getChildText(ContNo));
			tContBlcDtlSchema.setProposalPrtNo(tDetailEle.getChildText(ProposalPrtNo)); // 有些银行传
			// tContBlcDtlSchema.setTranDate(cTranLogDB.getTranDate());
			tContBlcDtlSchema.setTranDate(fileDate);
			tContBlcDtlSchema.setTranCom(cTranLogDB.getTranCom());
			// tContBlcDtlSchema.setNodeNo(tDetailEle.getChildText(NodeNo));
			tContBlcDtlSchema.setMakeDate(cTranLogDB.getMakeDate());
			tContBlcDtlSchema.setMakeTime(cTranLogDB.getMakeTime());
			tContBlcDtlSchema.setModifyDate(tContBlcDtlSchema.getMakeDate());
			tContBlcDtlSchema.setModifyTime(tContBlcDtlSchema.getMakeTime());
			tContBlcDtlSchema.setOperator(CodeDef.SYS);
			tContBlcDtlSchema.setType(tDetailEle.getChildText("BusiType"));// 在对账明细表存对账类型01新单，11续期，09满期，10退保
			if ("1048".equals(sfuncflag))
			{// 保全存一个网点号
				tContBlcDtlSchema.setBak1(tDetailEle.getChildText("NodeNo"));// 保存网点号
				tContBlcDtlSchema.setBak2(tDetailEle.getChildText("TranNo"));// 保存交易流水号
				tContBlcDtlSchema.setBak3(tDetailEle.getChildText("EdorNo"));// 保存保全申请号
				tContBlcDtlSchema.setPrem(0);
			}
			else
			{
				tContBlcDtlSchema.setPrem((int) NumberUtil.yuanToFen(tDetailEle.getChildText(Prem)));
			}
			tContBlcDtlSchema.setBak4(tDetailEle.getChildText("TranDate"));// 保存此保单交易日期
			mContBlcDtlSet.add(tContBlcDtlSchema);
		}
		// if (mSumPrem != mSumDtlPrem) {
		// throw new MidplatException("汇总金额与明细总金额不符！"+ mSumPrem + "!=" +
		// mSumDtlPrem);
		// }

		/**
		 * 将银行发过来的对账明细存储到对账明细表(ContBlcDtl)中
		 */
		cLogger.info("对账明细总数(DtlSet)为：" + mContBlcDtlSet.size());
		MMap mSubmitMMap = new MMap();
		mSubmitMMap.put(mContBlcDtlSet, "INSERT");
		VData mSubmitVData = new VData();
		mSubmitVData.add(mSubmitMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, ""))
		{
			cLogger.error("保存对账明细失败！" + mPubSubmit.mErrors.getFirstError());
			throw new MidplatException("保存对账明细失败！");
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
		{// 此处是因为只有一个进程，tranlog的主键是logno
			mTranLogDB.setLogNo(String.valueOf(Integer.parseInt(Thread.currentThread().getName()) + 1));
			System.out.println("进程名：" + String.valueOf(Integer.parseInt(Thread.currentThread().getName()) + 1));
			mTranLogDB.setBak1("11");
		}
		else
		{
			mTranLogDB.setLogNo(Thread.currentThread().getName());
			System.out.println("进程名：" + Thread.currentThread().getName());
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
			throw new MidplatException("插入日志失败！");
		}

		cLogger.debug("Out NewCcbBuBalance.insertTranLog()!");
		return mTranLogDB;
	}
	public String getResultMsg()
	{
		return this.cResultMsg;
	}
	/*
	 * 设置对账日期
	 */
	public final void setDate(String p8DateStr)
	{
		this.cTranDate = DateUtil.parseDate(p8DateStr, "yyyyMMdd");
	}
}
