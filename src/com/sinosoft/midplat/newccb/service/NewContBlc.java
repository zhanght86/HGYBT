package com.sinosoft.midplat.newccb.service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.ContBlcDtlSchema;
import com.sinosoft.lis.vschema.ContBlcDtlSet;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.newccb.format.PrintCont;
import com.sinosoft.midplat.newccb.util.FileUtil;
import com.sinosoft.midplat.service.Service;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ElementLis;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;

/**
 * 日终与保险公司对账 在该服务类中根据银行请求报文中的交易码来判断是保全对账还是账务对账 当为保全对账的时候调用保全对账服务类
 * 在该服务类中调用文件解密类，获取标准对账报文
 * 
 * @author
 * 
 */
public class NewContBlc extends ServiceImpl
{
	private Element cSubInXmlEle1 = new Element("Body");
	private Element cSubInXmlEle2 = new Element("Body");
	private Element cSubInXmlEle3 = new Element("Body");
	private Element cSubInXmlEle4 = new Element("Body");
	protected Document cSubInXmlDoc1 = null;
	protected Document cSubInXmlDoc2 = null;
	protected Document cSubInXmlDoc3 = null;
	protected Document cSubInXmlDoc4 = null;
	protected Document cSubOutXmlDoc1;
	protected Document cSubOutXmlDoc2;
	protected Document cSubOutXmlDoc3;
	protected Document cSubOutXmlDoc4;
	protected Document cMakeXmlDoc;
	private String typeCode = null;
	private String mTranCom = null;
	private Map<Integer, Element> rescueMap = new HashMap<Integer, Element>();
	private Map<Integer, String> contNoMap = new HashMap<Integer, String>();
	private Map<Integer, String> tranNoMap = new HashMap<Integer, String>();
	
	

	public NewContBlc(Element pThisBusiConf)
	{
		super(pThisBusiConf);
	}

	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) throws Exception
	{
		long mStartMillis = System.currentTimeMillis();
		Element TX_HEADER = pInXmlDoc.getRootElement().getChild("TX_HEADER");
		typeCode = TX_HEADER.getChildTextTrim("SYS_TX_CODE");

		cLogger.info("Into NewContBlc.service()...");

		try
		{
			int fileNum = Integer.valueOf(pInXmlDoc.getRootElement().getChild("TX_BODY").getChild("COMMON").getChild("FILE_LIST_PACK").getChildTextTrim("FILE_NUM"));
			if (fileNum < 1)
			{
				throw new MidplatException("没有对账文件！");
			}
			try
			{
				// 解密报文，并且获得标准报文
				FileUtil fu = new FileUtil(pInXmlDoc);
				cInXmlDoc = fu.fileSecurity();
				cMakeXmlDoc = cInXmlDoc;
				cLogger.info("成功做完标准对账报文.................");
			}
			catch (Exception ex)
			{
				cLogger.error(ex.getMessage());
				ex.printStackTrace();
				throw new MidplatException("获取银行账务标准对账报文出错！");
			}
			Element mRootEle = cInXmlDoc.getRootElement();
			Element mHeadEle = mRootEle.getChild("Head");
			String FuncFlag = mHeadEle.getChildText("FuncFlag");
			mTranCom = mHeadEle.getChildText("TranCom");

			JdomUtil.print(cInXmlDoc);

			cTranLogDB = insertTranLog(cInXmlDoc);
			

			// 处理前置机传过来的报错信息(扫描超时等)
			String tErrorStr = cInXmlDoc.getRootElement().getChildText(Error);
			if (null != tErrorStr)
			{
				throw new MidplatException(tErrorStr);
			}

			// 账务对账
			if (pInXmlDoc.getRootElement().getChild("TX_HEADER").getChildTextTrim("SYS_TX_CODE").equals("P53817103"))
			{

				List<Element> tDetail = XPath.selectNodes(cInXmlDoc.getRootElement(), "//TranData/Body/Detail");
				//续期
				int cnt3 = 0;
				int prem3 = 0;
				//新单
				int cnt4 = 0;
				int prem4 = 0;
				// 剔除对账内容，再按类型组装报文
				for (int i = 0; i < tDetail.size(); i++)
				{
					cLogger.info("交易类型：" + tDetail.get(i).getChildText("BusiType"));
					if (tDetail.get(i).getChildTextTrim("BusiType").equals("11"))
					{// 续期
						cSubInXmlEle3.addContent((Element) tDetail.get(i).clone());
						cnt3++;
						prem3 = prem3 + Integer.parseInt(String.valueOf(tDetail.get(i).getChildText("Prem")));
					}
					else if (tDetail.get(i).getChildTextTrim("BusiType").equals("01"))
					{// 新单
						cSubInXmlEle4.addContent((Element) tDetail.get(i).clone());
						cnt4++;
						prem4 = prem4 + Integer.parseInt(String.valueOf(tDetail.get(i).getChildText("Prem")));
					}
				}

				try
				{
					// 重新按类型将Detail填充到子报文中去
					System.out.println("=============重新组装报文=============");
					cInXmlDoc.getRootElement().removeChild("Body");
					cInXmlDoc.getRootElement().addContent(cSubInXmlEle3);
					cSubInXmlDoc3 = (Document) cInXmlDoc.clone();
					cInXmlDoc.getRootElement().removeChild("Body");
					cInXmlDoc.getRootElement().addContent(cSubInXmlEle4);
					cSubInXmlDoc4 = (Document) cInXmlDoc.clone();
					Element tbody3 = cSubInXmlDoc3.getRootElement().getChild("Body");
					ElementLis tCnt3 = new ElementLis("Count", String.valueOf(cnt3), tbody3);
					ElementLis tPrem3 = new ElementLis("Prem", String.valueOf(prem3), tbody3);
					Element tbody4 = cSubInXmlDoc4.getRootElement().getChild("Body");
					ElementLis tCnt4 = new ElementLis("Count", String.valueOf(cnt4), tbody4);
					ElementLis tPrem4 = new ElementLis("Prem", String.valueOf(prem4), tbody4);

				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				JdomUtil.print(cSubInXmlDoc3);
				JdomUtil.print(cSubInXmlDoc4);

				String desc = "";

				if (cSubInXmlDoc3.getRootElement().getChild("Body").getChildren("Detail") != null)
				{
					// 由于建行是分对总形式，所以先把各分行对账文件内容保存在contblcdtl表中，等待批量程序取数据发起对账
					// 此处只保存对账明细，不发起对账请求
					Element tTranData = new Element(TranData);
					Element tHead = new Element(Head);
					Element tFlag = new Element(Flag);
					tFlag.setText("0");
					Element tDesc = new Element(Desc);
					tDesc.setText("续期对账文件接收成功，待对账！");
					Element tBody = new Element(Body);

					tTranData.addContent(tHead);
					tTranData.addContent(tBody);

					tHead.addContent(tFlag);
					tHead.addContent(tDesc);

					cSubOutXmlDoc3 = new Document(tTranData);
					// cSubOutXmlDoc3 = new
					// CallWebsvcAtomSvc("18").call(cSubInXmlDoc3);
				}
				if (cSubInXmlDoc4.getRootElement().getChild("Body").getChildren("Detail") != null)
				{
					// 由于建行是分对总形式，所以先把各分行对账文件内容保存在contblcdtl表中，等待批量程序取数据发起对账
					// 此处只保存对账明细，不发起对账请求
					Element tTranData = new Element(TranData);
					Element tHead = new Element(Head);
					Element tFlag = new Element(Flag);
					tFlag.setText("0");
					Element tDesc = new Element(Desc);
					tDesc.setText("新单对账文件接收成功，待对账！");
					Element tBody = new Element(Body);

					tTranData.addContent(tHead);
					tTranData.addContent(tBody);

					tHead.addContent(tFlag);
					tHead.addContent(tDesc);

					cSubOutXmlDoc4 = new Document(tTranData);
					// cSubOutXmlDoc4 = new
					// CallWebsvcAtomSvc("6").call(cSubInXmlDoc4);
				}

				Element tOutHeadEle3 = cSubOutXmlDoc3.getRootElement().getChild(Head);
				Element tOutHeadEle4 = cSubOutXmlDoc4.getRootElement().getChild(Head);

				if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle3.getChildText(Flag)))
				{ // 交易失败
					cOutXmlDoc = cSubOutXmlDoc3;
					desc = desc + "续期对账：" + cSubInXmlDoc3.getRootElement().getChild("Head").getChildText("Desc") + ";";
				}
				if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle4.getChildText(Flag)))
				{ // 交易失败
					cOutXmlDoc = cSubOutXmlDoc4;
					desc = desc + "新单对账：" + cSubInXmlDoc4.getRootElement().getChild("Head").getChildText("Desc") + ";";
				}

				if (CodeDef.RCode_OK == Integer.parseInt(tOutHeadEle3.getChildText(Flag)) && CodeDef.RCode_OK == Integer.parseInt(tOutHeadEle4.getChildText(Flag)))
				{
					cOutXmlDoc = cSubOutXmlDoc3;
					// 保存账务续期对账明细
					cLogger.info("此处开始打印保存到续期对账明细的报文：");
					JdomUtil.print(cSubInXmlDoc3);
					saveDetails(cSubInXmlDoc3);
					// 保存账务新单对账明细
					cLogger.info("此处开始打印保存到新单对账明细的报文：");
					JdomUtil.print(cSubInXmlDoc4);
					saveDetails(cSubInXmlDoc4);
				}

				Element tOutRootEle = cOutXmlDoc.getRootElement();
				Element tOutHeadEle = tOutRootEle.getChild("Head");
				cOutXmlDoc.getRootElement().getChild("Head").getChild("Desc").setText(desc);
				if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag)))
				{
					throw new MidplatException(tOutHeadEle.getChildText(Desc));
				}
				else
				{
					cOutXmlDoc.getRootElement().getChild("Head").getChild("Desc").setText("新单、续期对账文件接收成功，待对账！");
				}

			}

			// 保全对账
			if (pInXmlDoc.getRootElement().getChild("TX_HEADER").getChildTextTrim("SYS_TX_CODE").equals("P53817104"))
			{

				List<Element> tDetail = XPath.selectNodes(cInXmlDoc.getRootElement(), "//TranData/Body/Detail");
				int cnt = 0;
				int prem = 0;
				// 剔除对账内容，再按类型组装报文
				for (int i = 0; i < tDetail.size(); i++)
				{
					cLogger.info("交易类型：" + tDetail.get(i).getChildText("BusiType"));
					if (tDetail.get(i).getChildTextTrim("BusiType").equals("09"))
					{// 满期
						cSubInXmlEle1.addContent((Element) tDetail.get(i).clone());
					}
					else if (tDetail.get(i).getChildTextTrim("BusiType").equals("10"))
					{// 退保
						cSubInXmlEle2.addContent((Element) tDetail.get(i).clone());
						cnt++;
					}
				}

				try
				{
					// 重新按类型将Detail填充到子报文中去
					System.out.println("=============重新组装报文=============");
					cInXmlDoc.getRootElement().removeChild("Body");
					cInXmlDoc.getRootElement().addContent(cSubInXmlEle1);
					cSubInXmlDoc1 = (Document) cInXmlDoc.clone();
					cInXmlDoc.getRootElement().removeChild("Body");
					cInXmlDoc.getRootElement().addContent(cSubInXmlEle2);
					cSubInXmlDoc2 = (Document) cInXmlDoc.clone();
					Element tbody = cSubInXmlDoc2.getRootElement().getChild("Body");
					ElementLis tCnt = new ElementLis("Count", String.valueOf(cnt), tbody);
					ElementLis tPrem = new ElementLis("Prem", "0", tbody);
					cMakeXmlDoc = cSubInXmlDoc2;
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				JdomUtil.print(cSubInXmlDoc1);
				JdomUtil.print(cSubInXmlDoc2);

				String desc = "";

				if (cSubInXmlDoc2.getRootElement().getChild("Body").getChildren("Detail") != null)
				{
					// 由于建行是分对总形式，所以先把各分行对账文件内容保存在contblcdtl表中，等待批量程序取数据发起对账
					// 此处只保存对账明细，不发起对账请求
					Element tTranData = new Element(TranData);

					Element tHead = new Element(Head);
					Element tFlag = new Element(Flag);
					tFlag.setText("0");
					Element tDesc = new Element(Desc);
					tDesc.setText("退保对账文件接收成功，待对账！");
					Element tBody = new Element(Body);

					tTranData.addContent(tHead);
					tTranData.addContent(tBody);

					tHead.addContent(tFlag);
					tHead.addContent(tDesc);

					cSubOutXmlDoc2 = new Document(tTranData);
					// cSubOutXmlDoc2 = new
					// CallWebsvcAtomSvc("16").call(cSubInXmlDoc2);
				}

				// 满期对账核心没有，假交易，返回成功
				Element tTranData = new Element(TranData);

				Element tHead = new Element(Head);
				Element tFlag = new Element(Flag);
				tFlag.setText("0");
				Element tDesc = new Element(Desc);
				tDesc.setText("满期对账文件接收成功，待对账！");
				Element tBody = new Element(Body);

				tTranData.addContent(tHead);
				tTranData.addContent(tBody);

				tHead.addContent(tFlag);
				tHead.addContent(tDesc);

				cSubOutXmlDoc1 = new Document(tTranData);

				Element tOutHeadEle = cSubOutXmlDoc1.getRootElement().getChild(Head);
				Element tOutHeadEle2 = cSubOutXmlDoc2.getRootElement().getChild(Head);

				if (cSubInXmlDoc1.getRootElement().getChild("Body").getContentSize() != 0)
				{
					if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag)))
					{ // 交易失败
						cOutXmlDoc = cSubOutXmlDoc1;
						desc = desc + cSubInXmlDoc1.getRootElement().getChild("Head").getChildText("Desc") + ";";
					}
				}
				if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle2.getChildText(Flag)))
				{ // 交易失败
					cOutXmlDoc = cSubOutXmlDoc2;
					desc = desc + cSubInXmlDoc1.getRootElement().getChild("Head").getChildText("Desc") + ";";
				}

				if (CodeDef.RCode_OK == Integer.parseInt(tOutHeadEle.getChildText(Flag)) && CodeDef.RCode_OK == Integer.parseInt(tOutHeadEle2.getChildText(Flag)))
				{
					cOutXmlDoc = cSubOutXmlDoc2;
				}

				cOutXmlDoc.getRootElement().getChild("Head").getChild("Desc").setText(desc);
				if (CodeDef.RCode_ERROR == Integer.parseInt(cOutXmlDoc.getRootElement().getChild("Head").getChildText(Flag)))
				{
					throw new MidplatException(cOutXmlDoc.getRootElement().getChild("Head").getChildText(Desc));
				}
				else
				{
					cOutXmlDoc.getRootElement().getChild("Head").getChild("Desc").setText("满期、退保对账文件接收成功，待对账！");
				}

				// 保存保全对账明细
				cLogger.info("此处开始打印保存到保全对账明细的报文：");
				JdomUtil.print(cMakeXmlDoc);
				saveDetails(cMakeXmlDoc);
			}

			// 单证对账
			if (pInXmlDoc.getRootElement().getChild("TX_HEADER").getChildTextTrim("SYS_TX_CODE").equals("P53817105"))
			{

				cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContCardBlc).call(cInXmlDoc);
			}

			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild("Head");

			cLogger.info("对账核心返回的报文：");
			JdomUtil.print(cOutXmlDoc);

			if (null != cTranLogDB)
			{ // 插入日志失败时cTranLogDB=null

				Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
				cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
				cTranLogDB.setRText(tHeadEle.getChildText(Desc));
				cTranLogDB.setBak1("1");// 账务
				long tCurMillis = System.currentTimeMillis();
				cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
				cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
				cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
				if (!cTranLogDB.update())
				{
					cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
				}
			}
		}
		catch (MidplatException ex)
		{
			cLogger.info(cThisBusiConf.getChildText(name) + "交易失败！", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
			// 插入日志失败时cTranLogDB=null
			cTranLogDB.setRCode("1");
			// cTranLogDB.setBak1("1");//账务
			cTranLogDB.setRText(ex.getMessage());
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update())
			{
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}

		}

		cLogger.info("Out NewContBlc.service()!");
		return cOutXmlDoc;
	}

	/**
	 * 保存对账明细，返回保存的明细数据(ContBlcDtlSet)
	 */
	@SuppressWarnings("unchecked")
	protected ContBlcDtlSet saveDetails(Document pXmlDoc) throws Exception
	{
		cLogger.debug("Into NewContBlc.saveDetails()...");

		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mBodyEle = mTranDataEle.getChild(Body);
		Element mHeadEle = mTranDataEle.getChild(Head);
		String sfuncflag = mHeadEle.getChildText("FuncFlag");
		int mCount = Integer.parseInt(mBodyEle.getChildText(Count));
		// long mSumPrem = Long.parseLong(mBodyEle.getChildText(Prem));
		double mSumPrem = Double.parseDouble(mBodyEle.getChildText(Prem));
		List<Element> mDetailList = mBodyEle.getChildren(Detail);
		ContBlcDtlSet mContBlcDtlSet = new ContBlcDtlSet();
		if (mDetailList.size() != mCount)
		{
			throw new MidplatException("汇总笔数与明细笔数不符！" + mCount + "!=" + mDetailList.size());
		}
		double mSumDtlPrem = 0;
		for (Element tDetailEle : mDetailList)
		{
			mSumDtlPrem += Double.parseDouble(tDetailEle.getChildText(Prem));

			ContBlcDtlSchema tContBlcDtlSchema = new ContBlcDtlSchema();
			tContBlcDtlSchema.setBlcTranNo(cTranLogDB.getLogNo());
			tContBlcDtlSchema.setContNo(tDetailEle.getChildText(ContNo));
			tContBlcDtlSchema.setProposalPrtNo(tDetailEle.getChildText(ProposalPrtNo)); // 有些银行传
			tContBlcDtlSchema.setTranDate(cTranLogDB.getTranDate());
			
			//从detaile取交易日期,如果日期不为空,则设置为交易日期
			String tTranDate = tDetailEle.getChildText(TranDate);
			if(StringUtils.isNotEmpty(tTranDate))
			{
				tContBlcDtlSchema.setTranDate(tTranDate);
			}
			tContBlcDtlSchema.setTranCom(cTranLogDB.getTranCom());
			// tContBlcDtlSchema.setNodeNo(tDetailEle.getChildText(NodeNo));
			tContBlcDtlSchema.setMakeDate(cTranLogDB.getMakeDate());
			tContBlcDtlSchema.setMakeTime(cTranLogDB.getMakeTime());
			tContBlcDtlSchema.setModifyDate(tContBlcDtlSchema.getMakeDate());
			tContBlcDtlSchema.setModifyTime(tContBlcDtlSchema.getMakeTime());
			tContBlcDtlSchema.setOperator(CodeDef.SYS);
			tContBlcDtlSchema.setType(tDetailEle.getChildText("BusiType"));// 在对账明细表存对账类型01新单，11续期，09满期，10退保
			//如果代理机构节点不为空，则存储代理机构编码
			if(tDetailEle.getChild("AgentCom") != null)
			{
				tContBlcDtlSchema.setAgentCom(tDetailEle.getChild("AgentCom").getTextTrim());
			}
			if ("1048".equals(sfuncflag))
			{// 保全存一个网点号
				tContBlcDtlSchema.setBak1(tDetailEle.getChildText("NodeNo"));// 保存网点号
				tContBlcDtlSchema.setBak2(tDetailEle.getChildText("TranNo"));// 保存交易流水号
				tContBlcDtlSchema.setBak3(tDetailEle.getChildText("EdorNo"));// 保存保全申请号
				tContBlcDtlSchema.setPrem(0);
			}
			else
			{
				tContBlcDtlSchema.setPrem(tDetailEle.getChildText(Prem));
			}
			tContBlcDtlSchema.setBak4(tDetailEle.getChildText("TranDate"));// 保存此保单交易日期
			mContBlcDtlSet.add(tContBlcDtlSchema);
		}
		if (mSumPrem != mSumDtlPrem)
		{
			throw new MidplatException("汇总金额与明细总金额不符！" + mSumPrem + "!=" + mSumDtlPrem);
		}

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

		cLogger.debug("Out NewContBlc.saveDetails()!");
		return mContBlcDtlSet;
	}

	/**
	 * @param mInXmlDoc
	 * @return cInXmlDoc
	 * @throws MidplatException
	 *             create by zhj 2010 11 05 网点 权限 添加校验方法
	 */
	private Document authority(Document mInXmlDoc) throws MidplatException
	{

		Element mRootEle = mInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head);
		Element mAgentCom = mHeadEle.getChild("AgentCom");
		Element mAgentCode = mHeadEle.getChild("AgentCode");
		String sNodeNo = (String) mHeadEle.getChildTextTrim("NodeNo");
		String sTranCom = (String) mHeadEle.getChildTextTrim("TranCom");

		cLogger.info("通过银行,地区,网点号查询代理机构号,并添加！");
		String tSqlStr2 = new StringBuilder("select AgentCom from NodeMap where TranCom='" + sTranCom).append('\'').append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCom = new ExeSQL().getOneValue(tSqlStr2);
		String tSqlStr3 = new StringBuilder("select AgentCode from NodeMap where TranCom='" + sTranCom).append('\'').append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCode = new ExeSQL().getOneValue(tSqlStr3);
		cLogger.info("authority-->" + sAgentCom);
		cLogger.info("authority-->" + sAgentCode);
		if ((("" == sAgentCom) || (sAgentCom == null)) && (("" == sAgentCode) || (sAgentCode == null)))
		{
			throw new MidplatException("此网点不存在，请确认！");
		}
		mAgentCom.setText(sAgentCom);
		mAgentCode.setText(sAgentCode);
		return mInXmlDoc;

	}

	public static void main(String[] args) throws Exception {
		String mInFilePath="D:/task/20170117/newccb/core_test/32592_6_6_inSvc.xml";
		InputStream mIs = new FileInputStream(mInFilePath);
		Document document = JdomUtil.build(mIs,"UTF-8");
		Document cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_NewContBlc).call(document);
		JdomUtil.print(cOutXmlDoc);
	}
	
}
