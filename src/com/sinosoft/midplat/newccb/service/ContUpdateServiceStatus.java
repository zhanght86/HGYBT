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

import com.sinosoft.lis.db.ContDB;
import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.lis.schema.ContSchema;
import com.sinosoft.lis.vschema.ContSet;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;

/**
 * @ClassName: ContUpdateServiceStatus
 * @Description: 
 * @author yuantongxin
 * @date 2017-1-4 上午10:56:43
 */
public class ContUpdateServiceStatus extends ServiceImpl {

	public ContUpdateServiceStatus(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into ContUpdateServiceStatus.service()...");
		cInXmlDoc = pInXmlDoc;
		
		//Java文档对象模型工具将输入标准报文打印到控制台[GBK编码，缩进3空格]
		//打印标准输入报文
//		JdomUtil.print(cInXmlDoc);//[Element:<TranData/>]
		cLogger.info(JdomUtil.toStringFmt(cInXmlDoc));
		Element mRootEle = cInXmlDoc.getRootElement();
		//标准输入报文Body报文体节点
		Element mLCConts = mRootEle.getChild("LCConts");
		Element mLCCont = mLCConts.getChild("LCCont");
//		String mProposalContNo = mLCCont.getChildText("ProposalContNo");
		
		try {
			cTranLogDB = insertTranLog(pInXmlDoc);

			// 校验系统中是否有相同保单正在处理，尚未返回
			int tLockTime = 300; // 默认超时设置为5分钟(300s)；如果未配置锁定时间，则使用该值。
			try {
				tLockTime = Integer.parseInt(cThisBusiConf
						.getChildText(locktime));
			} catch (Exception ex) { // 使用默认值
				cLogger.debug("未配置锁定时间，或配置有误，使用默认值(s)：" + tLockTime, ex);
			}
			Calendar tCurCalendar = Calendar.getInstance();
			tCurCalendar.add(Calendar.SECOND, -tLockTime);

			cOutXmlDoc=call(pInXmlDoc);
			// 和核心联调的时候放开 end

			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild("RetData");
			Element tOutBodyEle = tOutRootEle.getChild("LCConts");
//			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle
			if (CodeDef.RCode_ERROR  != Integer.parseInt(tOutHeadEle
					.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			// modified by chengqi 20121129
			String mContNo = tOutBodyEle.getChildText(ContNo);
			cTranLogDB.setContNo(mContNo);

			// 超时自动删除数据
			long tUseTime = System.currentTimeMillis() - mStartMillis;
			int tTimeOut = 60; // 默认超时设置为1分钟；如果未配置超时时间，则使用该值。
			try {
				tTimeOut = Integer
						.parseInt(cThisBusiConf.getChildText(timeout));
			} catch (Exception ex) { // 使用默认值
				cLogger.debug("未配置超时，或配置有误，使用默认值(s)：" + tTimeOut, ex);
			}
			if (tUseTime > tTimeOut * 1000) {
				cLogger.error("处理超时！UseTime=" + tUseTime / 1000.0
						+ "s；TimeOut=" + tTimeOut);
				throw new MidplatException("系统繁忙，请稍后再试！");
			}

		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name) + "交易失败！", ex);

			cOutXmlDoc = getSimpOutXml(CodeDef.RCode_OK,
					ex.getMessage());
		}

		if (null != cTranLogDB) { // 插入日志失败时cTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild("RetData");
			String flag=tHeadEle.getChildText(Flag).equals("1")?"0":"1";
			cTranLogDB.setRCode(flag);
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}

		cLogger.info("Out ContUpdateServiceStatus.service()!");
		return cOutXmlDoc;
	}
	
	/**
	 * 插入交易日志
	 * @param pXmlDoc XML文档[标准输入报文]
	 * @return 交易日志对象
	 * @throws MidplatException
	 */
	protected TranLogDB insertTranLog(Document pXmlDoc) throws MidplatException{
		//Into ServiceImpl.insertTranLog()...
		//Into ServiceImpl.insertTranLog()...
		//Into ServiceImpl.insertTranLog()...[进入业务处理实现类.插入交易日志...]
		cLogger.debug("Into ContUpdateServiceStatus.insertTranLog()...");//15:43:10,347 DEBUG service.ServiceImpl(54) - Into ServiceImpl.insertTranLog()...
		//[Element: <TranData/>]
		//[标准输入]报文TranData根节点
		Element mTranDataEle = pXmlDoc.getRootElement();
		//[Element: <Head/>]
		//[标准输入]Head报文头
		Element mHeadEle = mTranDataEle.getChild("BaseInfo");
		//[标准输入]Body报文体
		//[Element: <Body/>]
		Element mBodyEle = mTranDataEle.getChild("LCConts");
		//交易日志数据库操作类实例[不传入连接对象，交易日志生成数据库操作对象]
		TranLogDB mTranLogDB = new TranLogDB();//TranLog数据操作类实例
		//LogNo:2240
		//设置日志号为当前正在执行的线程对象的引用名称
		//设置日志号
		mTranLogDB.setLogNo(Thread.currentThread().getName());
		//进程名：2240
		//进程名：10091
		System.out.println("进程名：" + Thread.currentThread().getName());
		/*设置交易日志数据库操作类实例字段为核心标准输入报文头字段值*/
		/*设置TranLog表9个字段[标准输入报文头9个字段]：TranDate、TranTime、ZoneNo、NodeNo、TellerNo[Operator]、TranNo、TranCom、FuncFlag、InNoDoc*/
		//TranCom:9
		//设置交易单位为[标准输入]报文头交易机构代码子元素文本
		mTranLogDB.setTranCom(mHeadEle.getChildText(TranCom));
		//ZoneNo:01
		//设置地区代码为[标准输入]报文头省市代码子元素文本
		mTranLogDB.setZoneNo(mHeadEle.getChildText("ZoneNo"));
		//NodeNo:060150001222
		//设置交易网点为[标准输入]报文头网点代码子元素文本
		mTranLogDB.setNodeNo(mHeadEle.getChildText("BankCode"));
		//TranNo:2016120800010
		//设置交易流水号为[标准输入]报文头交易流水号子元素文本
		mTranLogDB.setTranNo(mHeadEle.getChildText("TransrNo"));
		//Operator:5201300002
		//设置操作员为[标准输入]报文头柜员代码子元素文本
		mTranLogDB.setOperator(mHeadEle.getChildText(TellerNo));
		//FuncFlag:1012
	   //设置交易类型为[标准输入]报文头交易类型子元素文本
		mTranLogDB.setFuncFlag(mHeadEle.getChildText(FuncFlag));
		//TranDate:20161108
		//设置交易日期为[标准输入]报文头交易日期子元素文本
		mTranLogDB.setTranDate(mHeadEle.getChildText("BankDate"));
		//TranTime:130101
		//设置交易时间为[标准输入]报文头交易时间子元素文本
		mTranLogDB.setTranTime(mHeadEle.getChildText("BankTime"));
		//InNoDoc:2240_3_1012_in.xml
		//设置进入报文为[标准输入]报文头进入报文子元素文本
		mTranLogDB.setInNoDoc(mHeadEle.getChildText("InNoDoc"));
		//trancom:9
		//trancom:13[输出交易单位]
		System.out.println("trancom:" + mTranLogDB.getTranCom());
		//FuncFlag:1012
		//FuncFlag:1012[输出交易类型]
		System.out.println("FuncFlag:" + mTranLogDB.getFuncFlag());
		//mHeadEle.getChildText10091_3_1012_in.xml
		//mHeadEle.getChildText2240_3_1012_in.xml[输出进入报文]
		System.out.println("mHeadEle.getChildText" + mHeadEle.getChildText("InNoDoc"));
		//<Body> != null
		//[标准输入]报文体非空
		//Body非空
		if (null != mBodyEle)
		{
			/*设置TranLog表4个字段:ProposalPrtNo、ContNo、ContPrtNo、OldLogNo[Bak2]*/
			//ProposalPrtNo:210414132201550
			//设置投保单(印刷)号为[标准输入]报文体投保单(印刷)号子元素文本
			mTranLogDB.setProposalPrtNo(mBodyEle.getChild("LCCont").getChildText("ProposalContNo"));
			//ContNo:null
			//设置保单号为[标准输入]报文体保险单号子元素文本
			mTranLogDB.setContNo(mBodyEle.getChildText(ContNo));
			//OtherNo:""
			//设置其他关联号为[标准输入]报文体保单合同印刷号 (单证) 子元素文本
			mTranLogDB.setOtherNo(mBodyEle.getChildText(ContPrtNo));
			//Bak2:null
			//设置备用2为[标准输入]报文体旧日志号子元素文本
			mTranLogDB.setBak2(mBodyEle.getChildText("OldLogNo"));
			//交易日志数据库操作类实例备用2为空、空字符
			if (null == mTranLogDB.getBak2() || "".equals(mTranLogDB.getBak2()))
			{
				//设置备用2为[标准输入]报文体旧交易流水号子元素文本
				mTranLogDB.setBak2(mBodyEle.getChildText("OldTranNo"));
			}
		}
		
		/*设置TranLog表7个字段:RCode、UsedTime、Bak1、MakeDate、MakeTime、ModifyDate、ModifyTime*/
		//设置交易结果为交易挂起，未返回 
		mTranLogDB.setRCode(CodeDef.RCode_NULL);
//		mTranLogDB.setRText(mHeadEle.getChild("RetData").getChildText("Desc"));
		//设置服务耗时为-1
		mTranLogDB.setUsedTime(-1);
		//设置备用1为银行端IP
		mTranLogDB.setBak1(mHeadEle.getChildText(ClientIp));
		//获取当前日期对象
		Date mCurDate = new Date();
		//设置入库日期[当前日期对象前八位
		mTranLogDB.setMakeDate(DateUtil.get8Date(mCurDate));
		//设置入库时间[当前日期对象后六位]
		mTranLogDB.setMakeTime(DateUtil.get6Time(mCurDate));
		//设置最后修改日期为入库日期
		mTranLogDB.setModifyDate(mTranLogDB.getMakeDate());
		//设置最后修改时间为入库时间
		mTranLogDB.setModifyTime(mTranLogDB.getMakeTime());
		//交易日志数据库操作插入失败
		//插入交易日志对象到交易日志表
		//!false=true插入失败
		if (!mTranLogDB.insert())
		{
			//ORA-00001: 违反唯一约束条件 (YBT.PK_TRANLOG) 
			cLogger.error(mTranLogDB.mErrors.getFirstError());
			/*com.sinosoft.midplat.exception.MidplatException: 插入日志失败！
				at com.sinosoft.midplat.service.ServiceImpl.insertTranLog(ServiceImpl.java:139)
				at com.sinosoft.midplat.newccb.service.NewContInput.service(NewContInput.java:57)
				at com.sinosoft.midplat.Ybt4Socket.run(Ybt4Socket.java:124)*/
			throw new MidplatException("插入日志失败！");
		}
		//Out ServiceImpl.insertTranLog()!
		//Out ServiceImpl.insertTranLog()!
		cLogger.debug("Out ContUpdateServiceStatus.insertTranLog()!");//15:43:10,488 DEBUG service.ServiceImpl(118) - Out ServiceImpl.insertTranLog()!
		return mTranLogDB;
	}

	public Document call(Document pInXmlDoc) throws Exception{
		cLogger.info("Into ContUpdateServiceStatus.call()...");
		String cServiceId=AblifeCodeDef.SID_UpdateServiceStatus;
		XPath mXPath = XPath.newInstance("/midplat/atomservices/service[@id='" + cServiceId + "']");
		Element cConfEle = (Element) mXPath.selectSingleNode(MidplatConf.newInstance().getConf());
		String mServAddress = cConfEle.getAttributeValue(address);
		String mServMethod = cConfEle.getAttributeValue(method);
		
		Element mHeadEle = pInXmlDoc.getRootElement().getChild("BaseInfo");
		Element mBodyEle = pInXmlDoc.getRootElement().getChild("LCConts");
		List<Element> mLCContList = mBodyEle.getChildren("LCCont");
		String mPrtNo = "";//mBodyEle.getChild("LCCont").getChildText("ProposalContNo");
		for (int i = 0; i < mLCContList.size(); i++) {
			mPrtNo+=mLCContList.get(i).getChildText("ProposalContNo");
			if(i!=mLCContList.size()-1) mPrtNo+=",";
		}
		Element mServiceIdEle = new Element(ServiceId);
		mServiceIdEle.setText(cServiceId);
		mHeadEle.addContent(mServiceIdEle);

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
		options.setTimeOutInMilliSeconds(800000);
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
