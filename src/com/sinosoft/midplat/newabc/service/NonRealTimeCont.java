//农行非实时出单
package com.sinosoft.midplat.newabc.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.jsp.tagext.TryCatchFinally;
import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.lis.db.ContDB;
import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.RuleParser;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.common.YBTDataVerification;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;

public class NonRealTimeCont extends ServiceImpl {
	public NonRealTimeCont(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	@SuppressWarnings("unchecked")
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into NonRealTimeCont.service()...");
		cInXmlDoc = pInXmlDoc;
		
		Element mRootEle = cInXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild("LCCont");
		String mProposalPrtNo = mBodyEle.getChildText("ProposalContNo");
		
		try {
//			System.out.println("--------------------------------------------------------------------------------------------------------");
//			JdomUtil.print(cInXmlDoc);
			cTranLogDB = insertTranLog(cInXmlDoc);
			
//			cLogger.info("Into NewContInput.service()...-->authority(cInXmlDoc)网点与权限 添加代理");	
			//add by zhj 网点与权限 添加代理
//			cInXmlDoc = authority(cInXmlDoc); 	
			//add by zhj 网点与权限 添加代理end 
			
			//校验系统中是否有相同保单正在处理，尚未返回
			int tLockTime = 300;	//默认超时设置为5分钟(300s)；如果未配置锁定时间，则使用该值。
			try {
				tLockTime = Integer.parseInt(cThisBusiConf.getChildText(locktime));
			} catch (Exception ex) {	//使用默认值
				cLogger.debug("未配置锁定时间，或配置有误，使用默认值(s)："+tLockTime, ex);
			}
			
			JdomUtil.print(cInXmlDoc);
			
//			new RuleParser().check(cInXmlDoc);
			
			cOutXmlDoc = call(cInXmlDoc);

//			假交易，返回成功
			/*Element tTranData=new Element(TranData);
			
			Element tHead=new Element(Head);
			Element tFlag=new Element(Flag);
			
			Element tDesc=new Element(Desc);
			Element tBody=new Element(Body);
			
			tTranData.addContent(tHead);
			tTranData.addContent(tBody);
			
			tHead.addContent(tFlag);
			tHead.addContent(tDesc);*/

			
			/*//判断此投保单号试算时核心是否返回 需非实时出单
			String tRcodeSql = new StringBuilder("select count(1) from TranLog where funcflag='").append("1012'")
			.append(" and ProposalPrtNo='").append(mProposalPrtNo).append('\'')
			.append(" and rcode='").append("2'")
			.toString();

			//
			if(("1").equals(new ExeSQL().getOneValue(tRcodeSql))){
				tFlag.setText("0");
				tDesc.setText("交易成功");
			}else{
				tFlag.setText("1");
				tDesc.setText("请先做新单试算！");
			}*/
			/*cOutXmlDoc=new Document(tTranData);*/
			
			System.out.println("-----------------------------------------------");
			cLogger.info("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
			JdomUtil.print(cOutXmlDoc);
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild("RetData");
			Element tOutBodyEle = tOutRootEle.getChild("LCCont");
			if (CodeDef.RCode_ERROR  != Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
			//超时自动删除数据
			long tUseTime = System.currentTimeMillis() - mStartMillis;
			int tTimeOut = 60;	//默认超时设置为1分钟；如果未配置超时时间，则使用该值。
			try {
				tTimeOut = Integer.parseInt(cThisBusiConf.getChildText(timeout));
			} catch (Exception ex) {	//使用默认值
				cLogger.debug("未配置超时，或配置有误，使用默认值(s)："+tTimeOut, ex);
			}
			if (tUseTime > tTimeOut*1000) {
				cLogger.error("处理超时！UseTime=" + tUseTime/1000.0 + "s；TimeOut=" + tTimeOut + "s；投保书：" + mProposalPrtNo);
				rollback();	//回滚系统数据
				throw new MidplatException("系统繁忙，请稍后再试！");
			}
			
			//保存保单信息
//			ContDB tContDB = getContDB();
//			Date tCurDate = new Date();
//			tContDB.setMakeDate(DateUtil.get8Date(tCurDate));
//			tContDB.setMakeTime(DateUtil.get6Time(tCurDate));
//			tContDB.setModifyDate(tContDB.getMakeDate());
//			tContDB.setModifyTime(tContDB.getMakeTime());
			
			
//			if (!tContDB.insert()) {
//				cLogger.error("保单信息(Cont)入库失败！" + tContDB.mErrors.getFirstError());
//			}
//			cTranLogDB.setContNo(tContDB.getContNo());
//			cTranLogDB.setManageCom(tContDB.getManageCom());
//			cTranLogDB.setAgentCom(tContDB.getAgentCom());
//			cTranLogDB.setAgentCode(tContDB.getAgentCode());
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"交易失败！", ex);
			
			cOutXmlDoc = getSimpOutXml(CodeDef.RCode_OK, ex.getMessage());
		}
		
		if (null != cTranLogDB) {	//插入日志失败时cTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild("RetData");
			String flag=tHeadEle.getChildText(Flag).equals("1")?"0":"1";
			cTranLogDB.setRCode(flag);
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}
		
		cLogger.info("Out NonRealTimeCont.service()!");
		return cOutXmlDoc;
	}
	
	private ContDB getContDB() {
		cLogger.debug("Into NonRealTimeCont.getContDB()...");
		
		Element mInBodyEle = cInXmlDoc.getRootElement().getChild(Body);
//		Element mInRiskEle = mInBodyEle.getChild(Risk);
		
		Element mOutBodyEle = cOutXmlDoc.getRootElement().getChild(Body);
		Element mInAppntEle = mInBodyEle.getChild(Appnt);
//		Element mOutInsuredEle = mOutBodyEle.getChild(Insured);
//		Element mOutMainRiskEle = mOutBodyEle.getChild(Risk);	//前面已经做排序了，第一个节点就是主险信息
		
		ContDB mContDB = new ContDB();
		mContDB.setRecordNo(NoFactory.nextContRecordNo());
		mContDB.setType(AblifeCodeDef.ContType_Bank);
		mContDB.setContNo(mOutBodyEle.getChildText(ContNo));
//System.out.println("mOutBodyEle.getChildText(ProposalPrtNo):"+mOutBodyEle.getChildText(ContNo));
//JdomUtil.print(cOutXmlDoc);
		mContDB.setProposalPrtNo(mOutBodyEle.getChildText(ProposalPrtNo));
		mContDB.setProductId(mInBodyEle.getChildText(ProductId));
		mContDB.setTranCom(cTranLogDB.getTranCom());
		mContDB.setNodeNo(cTranLogDB.getNodeNo());
		mContDB.setAgentCom(mOutBodyEle.getChildText(AgentCom));
		mContDB.setAgentComName(mOutBodyEle.getChildText(AgentComName));
		mContDB.setAgentCode(mOutBodyEle.getChildText(AgentCode));
		mContDB.setAgentName(mOutBodyEle.getChildText(AgentName));
		mContDB.setManageCom(mOutBodyEle.getChildText(ComCode));
//		mContDB.setAppntNo(mOutAppntEle.getChildText(CustomerNo));
		mContDB.setAppntName(mInAppntEle.getChildText(Name));
		mContDB.setAppntSex(mInAppntEle.getChildText(Sex));
		mContDB.setAppntBirthday(mInAppntEle.getChildText(Birthday));
		mContDB.setAppntIDType(mInAppntEle.getChildText(IDType));
		mContDB.setAppntIDNo(mInAppntEle.getChildText(IDNo));
//		mContDB.setInsuredNo(mOutInsuredEle.getChildText(CustomerNo));
//		mContDB.setInsuredName(mOutInsuredEle.getChildText(Name));
//		mContDB.setInsuredSex(mOutInsuredEle.getChildText(Sex));
//		mContDB.setInsuredBirthday(mOutInsuredEle.getChildText(Birthday));
//		mContDB.setInsuredIDType(mOutInsuredEle.getChildText(IDType));
//		mContDB.setInsuredIDNo(mOutInsuredEle.getChildText(IDNo));
		mContDB.setTranDate(cTranLogDB.getTranDate());
//		mContDB.setPolApplyDate(mOutMainRiskEle.getChildText(PolApplyDate));
		mContDB.setPrem(mOutBodyEle.getChildText(Prem));
		mContDB.setAmnt(mOutBodyEle.getChildText(Amnt));
		mContDB.setState(AblifeCodeDef.ContState_Input);
//		mContDB.setBak1(mInRiskEle.getChildText(MainRiskCode));
		mContDB.setBak8(mInBodyEle.getChildText("ApplyNo"));
		mContDB.setOperator(CodeDef.SYS);
		
		cLogger.debug("Out NonRealTimeCont.getContDB()!");
		return mContDB;
	}
	
	private void rollback() {
		cLogger.debug("Into NonRealTimeCont.rollback()...");
		
		Element mInRootEle = cInXmlDoc.getRootElement();
		Element mInBodyEle = mInRootEle.getChild(Body);
		Element mHeadEle = (Element) mInRootEle.getChild(Head).clone();
		mHeadEle.getChild(ServiceId).setText(AblifeCodeDef.SID_Bank_ContRollback);
		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(
				(Element) mInBodyEle.getChild(ProposalPrtNo).clone());
		mBodyEle.addContent(
				(Element) mInBodyEle.getChild(ContPrtNo).clone());
		mBodyEle.addContent(
				(Element) cOutXmlDoc.getRootElement().getChild(Body).getChild(ContNo).clone());
		Element mTranDataEle = new Element(TranData);
		mTranDataEle.addContent(mHeadEle);
		mTranDataEle.addContent(mBodyEle);
		Document mInXmlDoc = new Document(mTranDataEle);
		
		try {
			new CallWebsvcAtomSvc(mHeadEle.getChildText(ServiceId)).call(mInXmlDoc);
		} catch (Exception ex) {
			cLogger.error("回滚数据失败！", ex);
		}
		
		cLogger.debug("Out NonRealTimeCont.rollback()!");
	}
	/**
	 * @param mInXmlDoc
	 * @return cInXmlDoc
	 * @throws MidplatException
	 * create by zhj 2010 11 05
	 * 网点 权限 添加校验方法
	 */
	private Document authority(Document mInXmlDoc) throws MidplatException{
		
  
		Element mRootEle = mInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head);
		Element mAgentCom = mHeadEle.getChild("AgentCom");
		Element mAgentCode = mHeadEle.getChild("AgentCode");
		String sNodeNo = (String)mHeadEle.getChildTextTrim("NodeNo");
		String sTranCom =  (String)mHeadEle.getChildTextTrim("TranCom");
		 
		cLogger.info("通过银行,地区,网点号查询代理机构号,并添加！");
		String tSqlStr2 = new StringBuilder("select AgentCom from NodeMap where TranCom='"+sTranCom).append('\'')
			.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCom = new ExeSQL().getOneValue(tSqlStr2);
		String tSqlStr3 = new StringBuilder("select AgentCode from NodeMap where TranCom='"+sTranCom).append('\'')
		.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCode = new ExeSQL().getOneValue(tSqlStr3); 
		cLogger.info("authority-->"+sAgentCom);
		cLogger.info("authority-->"+sAgentCode);   
		if (((""==sAgentCom)||(sAgentCom==null)) && ((""==sAgentCode)||(sAgentCode==null))){ 
			throw new MidplatException("此网点不存在，请确认！");
		}
		mAgentCom.setText(sAgentCom);
		mAgentCode.setText(sAgentCode); 
		return mInXmlDoc;
	}
	
	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException{
		cLogger.debug("Into NonRealTimeCont.insertTranLog()...");//15:43:10,347 DEBUG service.ServiceImpl(54) - Into ServiceImpl.insertTranLog()...
		Element mTranDataEle = pXmlDoc.getRootElement();
	    Element mBaseInfoEle = mTranDataEle.getChild("BaseInfo");
	    Element mLCContEle = mTranDataEle.getChild("LCCont");
	    Element mHeadEle = mTranDataEle.getChild("Head");

	    TranLogDB mTranLogDB = new TranLogDB();
	    mTranLogDB.setLogNo(Thread.currentThread().getName());
	    System.out.println("进程名：" + Thread.currentThread().getName());
	    mTranLogDB.setTranCom(mHeadEle.getChildText("TranCom"));
	    mTranLogDB.setZoneNo(mBaseInfoEle.getChildText("ZoneNo"));
	    mTranLogDB.setNodeNo(mBaseInfoEle.getChildText("BrNo"));
	    mTranLogDB.setTranNo(mBaseInfoEle.getChildText("TransrNo"));
	    mTranLogDB.setOperator(mBaseInfoEle.getChildText("TellerNo"));
	    mTranLogDB.setFuncFlag(mBaseInfoEle.getChildText("FunctionFlag"));
	    mTranLogDB.setTranDate(mBaseInfoEle.getChildText("BankDate"));
	    mTranLogDB.setTranTime(mBaseInfoEle.getChildText("BankTime"));
	    mTranLogDB.setInNoDoc(mBaseInfoEle.getChildText("InNoDoc"));
	    System.out.println("trancom:" + mTranLogDB.getTranCom());
	    System.out.println("FuncFlag:" + mTranLogDB.getFuncFlag());
	    System.out.println("mHeadEle.getChildText" + mBaseInfoEle.getChildText("InNoDoc"));
	    mTranLogDB.setProposalPrtNo(mLCContEle.getChildText("ProposalContNo"));
	    mTranLogDB.setContNo("");
	    mTranLogDB.setOtherNo("");
	    mTranLogDB.setBak2("");
	    mTranLogDB.setAppntName(mLCContEle.getChild("LCAppnt").getChildText("AppntName"));
	    mTranLogDB.setAppntIDNo(mLCContEle.getChild("LCAppnt").getChildText("AppntIDNo"));
	    mTranLogDB.setRCode(-1);
	    mTranLogDB.setUsedTime(-1);
	    mTranLogDB.setRText("处理中");
	    mTranLogDB.setBak1("");
	    Date mCurDate = new Date();
	    mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
	    mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
	    mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
	    mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
	    if (!(mTranLogDB.insert()))
	    {
	      this.cLogger.error(mTranLogDB.mErrors.getFirstError());
	      throw new MidplatException("插入日志失败！");
	    }
		cLogger.debug("Out NonRealTimeCont.insertTranLog()!");//15:43:10,488 DEBUG service.ServiceImpl(118) - Out ServiceImpl.insertTranLog()!
		return mTranLogDB;
	}
	
	public Document call(Document pInXmlDoc) throws Exception{
		cLogger.info("Into ContUpdateServiceStatus.call()...");
		String cServiceId=AblifeCodeDef.SID_NonRealTimeApplication;
		XPath mXPath = XPath.newInstance("/midplat/atomservices/service[@id='" + cServiceId + "']");
		Element cConfEle = (Element) mXPath.selectSingleNode(MidplatConf.newInstance().getConf());
		String mServAddress = cConfEle.getAttributeValue(address);
		String mServMethod = cConfEle.getAttributeValue(method);
		
		Element mHeadEle = pInXmlDoc.getRootElement().getChild("Head");
		Element mBodyEle = pInXmlDoc.getRootElement().getChild("LCCont");
		String mPrtNo = mBodyEle.getChildText("ProposalContNo");
		
		String mTranCom = mHeadEle.getChildText(TranCom);
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName()).append('_').append(NoFactory.nextAppNo()).append('_').append(cServiceId).append("_inSvc.xml");
		SaveMessage.save(pInXmlDoc, mTranCom, mSaveName.toString());
		//保存报文完毕！1550_6_0_inSvc.xml
		//保存[核心]报文完毕！1561_12_30_inSvc.xml
		cLogger.info("保存报文完毕！" + mSaveName);
		
		String mInXmlStr = JdomUtil.toString(pInXmlDoc);// 上websphere 的时候用这个及下行
		byte[] mInXmlByte = mInXmlStr.getBytes("GBK");// 上websphere的时候用这个

		System.out.println("start call " + cConfEle.getAttributeValue(name) + "(" + mServAddress + "." + mServMethod + ")...");//银行接口服务
		cLogger.info("start call " + cConfEle.getAttributeValue(name) + "(" + mServAddress + "." + mServMethod + ")...");
		long mStartMillis = System.currentTimeMillis();
//		JdomUtil.print(pInXmlDoc);
		cLogger.info(JdomUtil.toStringFmt(pInXmlDoc));
		// 使用RPC方式调用WebService
		RPCServiceClient serviceClient = new RPCServiceClient();

		Options options = serviceClient.getOptions();
		// 设置超时时间
		options.setTimeOutInMilliSeconds(60000);
		// 指定调用WebService的URL
		String servicePath = mServAddress + "?wsdl";
		EndpointReference targetEPR = new EndpointReference(servicePath);

		options.setTo(targetEPR);
		// 指定sayHelloToPerson方法的参数值
		Object[] opAddEntryArgs = new Object[] { mInXmlByte };
		// 指定sayHelloToPerson方法返回值的数据类型的Class对象
		Class[] classes = new Class[] { byte[].class };
		// Class[] classes = new Class[] { String.class };
		// 指定要调用的sayHelloToPerson方法及WSDL文件的命名空间
		QName opAddEntry = new QName("http://kernel.ablinkbank.sinosoft.com", mServMethod);
		// 调用sayHelloToPerson方法并输出该方法的返回值
		byte[] mOutStr = (byte[]) serviceClient.invokeBlocking(opAddEntry, opAddEntryArgs, classes)[0];
		cLogger.info("投保单号" + mPrtNo + cConfEle.getAttributeValue(name) + "(" + mServMethod + ")耗时：" + (System.currentTimeMillis() - mStartMillis) / 1000.0 + "s");
		// cLogger.debug(mOutStr);
		//核心请求报文:
		// System.out.println("核心返回的："+new String(mOutStr));
		Document mOutXmlDoc = JdomUtil.build(mOutStr);
		 JdomUtil.print(mOutXmlDoc);
		cLogger.info(JdomUtil.toStringFmt(mOutXmlDoc));
		if (null == mOutXmlDoc)
		{
			throw new MidplatException(cConfEle.getAttributeValue(name) + "服务返回结果异常！");
		}

		mSaveName = new StringBuffer(Thread.currentThread().getName()).append('_').append(NoFactory.nextAppNo()).append('_').append(cServiceId).append("_outSvc.xml");
		SaveMessage.save(mOutXmlDoc, mTranCom, mSaveName.toString());
		cLogger.info("保存报文完毕！" + mSaveName);

		cLogger.info("Out ContUpdateServiceStatus.service()!");
		return mOutXmlDoc;
	}
	
	/**
	 * 根据pFlag和pMessage，生成简单的标准返回报文。
	 */
	public static Document getSimpOutXml(int pFlag, String pMessage) {
		Element mFlag = new Element(Flag);
		mFlag.addContent(String.valueOf(pFlag));

		Element mDesc = new Element(Desc);
		mDesc.addContent(pMessage);

		Element mHead = new Element("RetData");
		mHead.addContent(mFlag);
		mHead.addContent(mDesc);

		Element mTranData = new Element(TranData);
		mTranData.addContent(mHead);

		return new Document(mTranData);
	}
	
}
