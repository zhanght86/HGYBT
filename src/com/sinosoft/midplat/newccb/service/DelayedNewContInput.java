package com.sinosoft.midplat.newccb.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;

public class DelayedNewContInput extends ServiceImpl{
	public DelayedNewContInput(Element pThisBusiConf)
	{
		super(pThisBusiConf);
	}

	@Override
	public Document service(Document pInXmlDoc) {
		cLogger.info("Into DelayedNewContInput.service()!");
		cLogger.info("转换的标准报文:"+JdomUtil.toStringFmt(pInXmlDoc));
		//投保单号
		String mProposalContNo = pInXmlDoc.getRootElement().getChild("LCCont").getChildText("ProposalContNo");
		long mStartMillis = System.currentTimeMillis();
		try {
			cTranLogDB = insertTranLog(pInXmlDoc);
			// 校验系统中是否有相同保单正在处理，尚未返回
			// 默认超时设置为5分钟(300s)；如果未配置锁定时间，则使用该值。
			int tLockTime = 300; 
			try
			{
				tLockTime = Integer.parseInt(cThisBusiConf.getChildText(locktime));
			}
			catch (Exception ex)
			{ // 使用默认值
				cLogger.debug("未配置锁定时间，或配置有误，使用默认值(s)：" + tLockTime, ex);
			}
			Calendar tCurCalendar = Calendar.getInstance();
			tCurCalendar.add(Calendar.SECOND, -tLockTime);
			String tSqlStr = new StringBuilder("select count(1) from TranLog where RCode=")
			                 .append(CodeDef.RCode_NULL).append(" and ProposalPrtNo='")
			                 .append(mProposalContNo).append('\'').append(" and MakeDate>=")
			                 .append(DateUtil.get8Date(tCurCalendar)).append(" and MakeTime>=")
			                 .append(DateUtil.get6Time(tCurCalendar)).toString();
			cLogger.info(tSqlStr);
			if (!"1".equals(new ExeSQL().getOneValue(tSqlStr)))
			{
				throw new MidplatException("此保单数据正在处理中，请稍候！");
			}
			tSqlStr="select 1 from tranlog where ProposalPrtNo='"+mProposalContNo+"' and funcflag='1060' and rcode='0'";
			if("1".equals(new ExeSQL().getOneValue(tSqlStr))){
				throw new MidplatException("此保单已经投保!");
			}
			//证件类型校验
			if("99".equals(pInXmlDoc.getRootElement().getChild("LCCont").getChild("LCAppnt").getChildText("AppntIDType"))){
				throw new MidplatException("被保人证件类型不符合保险公司规定");
			}
			//请求核心
			cOutXmlDoc=CallWebsvcAtomSvc(pInXmlDoc);
		} catch (Exception e) {
			cLogger.info(cThisBusiConf.getChildText("name")+"交易失败！"+e);
			cOutXmlDoc=getSimpleErrorXml("0",e.getMessage());
		}
		if (null != cTranLogDB)
		{ // 插入日志失败时cTranLogDB=null
			Element RetDataEle = cOutXmlDoc.getRootElement().getChild("RetData");
			cTranLogDB.setRCode(RetDataEle.getChildText("Flag").equals("1")?"0":"1");
			cTranLogDB.setRText(RetDataEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update())
			{
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}
		cLogger.info("核心返回报文:"+JdomUtil.toStringFmt(cOutXmlDoc));
		cLogger.info("Out DelayedNewContInput.service()!");
		return cOutXmlDoc;
	}
	public Document getSimpleErrorXml(String mFlag,String mDesc){
		Element TranDataEle=new Element("TranData");
		Element RetDataEle=new Element("RetData");
		Element FlagEle=new Element("Flag");
		FlagEle.setText(mFlag);
		Element DescEle=new Element("Desc");
		DescEle.setText(mDesc);
		RetDataEle.addContent(FlagEle);
		RetDataEle.addContent(DescEle);
		TranDataEle.addContent(RetDataEle);
		return new Document(TranDataEle);
	}
    public Document CallWebsvcAtomSvc(Document inSvcDocXml) throws Exception{
    	cLogger.info("Into CallWebsvcAtomSvc.service()...");
        String cServiceId=inSvcDocXml.getRootElement().getChild("Head").getChildText("ServiceId");
    	String mTranCom=inSvcDocXml.getRootElement().getChild("Head").getChildText("TranCom");
    	//投保单号
		String mProposalContNo = inSvcDocXml.getRootElement().getChild("LCCont").getChildText("ProposalContNo");
		//获取请求WebService的路径及方法名称
    	XPath mXPath = XPath.newInstance("/midplat/atomservices/service[@id='"+cServiceId+"']");
		Element serviceEle = (Element) mXPath.selectSingleNode(MidplatConf.newInstance().getConf());
		String mServAddress = serviceEle.getAttributeValue(address);
		String mServMethod = serviceEle.getAttributeValue(method);
		//标准报文落地
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName()).append('_').append(NoFactory.nextAppNo()).append('_').append(cServiceId).append("_inSvc.xml");
		SaveMessage.save(inSvcDocXml, mTranCom, mSaveName.toString());
		cLogger.info("保存核心报文完毕！" + mSaveName);
		System.out.println("start call " + serviceEle.getAttributeValue(name) + "(" + mServAddress + "." + mServMethod + ")...");
		cLogger.info("start call " + serviceEle.getAttributeValue(name) + "(" + mServAddress + "." + mServMethod + ")...");
		byte[] mInXmlByte = JdomUtil.toBytes(inSvcDocXml,"GBK");
		long mStartMillis = System.currentTimeMillis();
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
		cLogger.info("投保单号" + mProposalContNo + serviceEle.getAttributeValue(name) + "(" + mServMethod + ")耗时：" + (System.currentTimeMillis() - mStartMillis) / 1000.0 + "s");
		Document mOutXmlDoc = JdomUtil.build(mOutStr);
		if (null == mOutXmlDoc)
		{
			throw new MidplatException(serviceEle.getAttributeValue(name) + "服务返回结果异常！");
		}

		mSaveName = new StringBuffer(Thread.currentThread().getName()).append('_').append(NoFactory.nextAppNo()).append('_').append(cServiceId).append("_outSvc.xml");
		SaveMessage.save(mOutXmlDoc, mTranCom, mSaveName.toString());
		cLogger.info("保存核心返回报文完毕！" + mSaveName);

		cLogger.info("Out CallWebsvcAtomSvc.service()!");
		return mOutXmlDoc;
    }
	@Override
	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException
	{
		cLogger.debug("Into DelayedNewContInput.insertTranLog()...");

		Element mTranDataEle = pXmlDoc.getRootElement();
		Element mBaseInfoEle = mTranDataEle.getChild("BaseInfo");
		Element mLCContEle = mTranDataEle.getChild("LCCont");
		Element mHeadEle = mTranDataEle.getChild("Head");

		TranLogDB mTranLogDB = new TranLogDB();
		mTranLogDB.setLogNo(Thread.currentThread().getName());
		System.out.println("进程名：" + Thread.currentThread().getName());
		mTranLogDB.setTranCom(mHeadEle.getChildText("TranCom"));
		mTranLogDB.setZoneNo(mBaseInfoEle.getChildText("ZoneNo"));
		mTranLogDB.setNodeNo(mBaseInfoEle.getChildText("BankCode"));
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
		mTranLogDB.setRCode(CodeDef.RCode_NULL);
		mTranLogDB.setUsedTime(-1);
		mTranLogDB.setRText("处理中");
		//保险年期类别代码|保险期限|保险周期代码|保费缴费方式代码|保费缴费期数|保费缴费周期代码 
		mTranLogDB.setBak1(mBaseInfoEle.getChildText("RiskMessage").replaceAll(" ",""));
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

		cLogger.debug("Out DelayedNewContInput.insertTranLog()!");
		return mTranLogDB;
	}
}
