package com.sinosoft.midplat.newccb.service;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
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

import cn.ccb.secapi.SecAPI;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;

public class GetDelayedUndStatusForService extends ServiceImpl{
	public GetDelayedUndStatusForService(Element pThisBusiConf)
	{
		super(pThisBusiConf);
	}

	@Override
	public Document service(Document pInXmlDoc) {
		cLogger.info("Into GetDelayedUndStatusForService.service()!");
		cLogger.info("转换的标准报文:"+JdomUtil.toStringFmt(pInXmlDoc));
		//交易标志
		String mFuncFlag=pInXmlDoc.getRootElement().getChild("BaseInfo").getChildText("FunctionFlag");
		String mTranCom=pInXmlDoc.getRootElement().getChild("Head").getChildText("TranCom");
		long mStartMillis = System.currentTimeMillis();
		try {
			//请求核心
			cOutXmlDoc=CallWebsvcAtomSvc(pInXmlDoc);
			//校验核心返回的保单明细总数和银行请求明细总数是否相等
			if(!pInXmlDoc.getRootElement().getChild("LCCont").getChild("ContList").getChildText("ContNum").equals(
			   cOutXmlDoc.getRootElement().getChild("LCCont").getChildText("ContNum"))){
				throw new MidplatException("核心返回的保单明细总数与银行请求明细总数不一致！");
			}
			cLogger.info("核心返回报文:"+JdomUtil.toStringFmt(cOutXmlDoc));
			//如果核心响应报文成功则生成响应文件:核心返回1：成功，0：失败
			if("1".equals(cOutXmlDoc.getRootElement().getChild("RetData").getChildText("Flag"))){
			     createEncryptFile(pInXmlDoc,cOutXmlDoc,this.cThisBusiConf);
			}
		} catch (Exception e) {
			e.printStackTrace();
			cLogger.info(cThisBusiConf.getChildText("name")+"交易失败！"+e.getMessage());
			cOutXmlDoc=getSimpleErrorXml("0",e.getMessage());
			cLogger.info(JdomUtil.toStringFmt(cOutXmlDoc));
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
		cLogger.info("Out GetDelayedUndStatusForService.service()!");
		return cOutXmlDoc;
	}
	/**
	 * 生成响应银行的加密文件
	 * @param pInXmlDoc  请求核心报文
	 * @param cOutXmlDoc 核心返回报文
	 * @param pThisBusiConf  获取非实时核保信息交易的配置节点
	 * @return 
	 */
	public void createEncryptFile(Document pInXmlDoc,Document cOutXmlDoc,Element pThisBusiConf) throws Exception{
		
		try {
			String localID=pInXmlDoc.getRootElement().getChild("Head").getChildText("LocalID");
			String remoteID=pInXmlDoc.getRootElement().getChild("Head").getChildText("RemoteID");
			cLogger.info("银行端安全节点号:"+remoteID);
			cLogger.info("保险公司端安全节点号:"+localID);
			String localDir=pThisBusiConf.getChildText("LocalDir");
			String ccbLocalDir=pThisBusiConf.getChildText("ccbLocalDir");
			cLogger.info("本地生成文件路径:"+localDir);
			cLogger.info("银行获取文件路径:"+ccbLocalDir);
			if(!localDir.endsWith("/")){
				localDir+="/";
			}
			if(!ccbLocalDir.endsWith("/")){
				ccbLocalDir+="/";
			}
			File localDirFile=new File(localDir);
		    if(!localDirFile.exists()){
		    	if(!localDirFile.mkdirs()){
		    		throw new MidplatException("本地生成文件路径创建失败！");
		    	}
		    }
			//组织响应报文格式
			Element LCContEle=cOutXmlDoc.getRootElement().getChild("LCCont");
			Element  RootEle=new Element("Root");
			Element  HeadEle=new Element("Head");
			Element Detail_ListEle=new Element("Detail_List");
			Element  NRlTmInsPlyDtlTot_NumEle=new Element("NRlTmInsPlyDtlTot_Num");
			NRlTmInsPlyDtlTot_NumEle.setText(LCContEle.getChildText("ContNum"));
			cLogger.info("核心响应非实时保单明细总数为："+LCContEle.getChildText("ContNum"));
			HeadEle.addContent(NRlTmInsPlyDtlTot_NumEle);
			RootEle.addContent(HeadEle);
			RootEle.addContent(Detail_ListEle);
			List<Element> LCContLisList=LCContEle.getChildren("LCContLis");
			if(!LCContEle.getChildText("ContNum").equals(String.valueOf(LCContLisList.size()))){
				cLogger.info("核心响应非实时保单明细总数与LCContLis节点数不符");
				throw new MidplatException("核心响应非实时保单明细总数与LCContLis节点数不符");
			}
			String sql=null;
			String BAK1=null;
			ExeSQL exeSql=new ExeSQL();
			for (Element LCContLisEle : LCContLisList) {
				Element DetailEle=new Element("Detail");
				
				Element Ins_BillNoEle=new Element("Ins_BillNo");//投保单号
				Ins_BillNoEle.setText(LCContLisEle.getChildText("ProposalContNo").substring(0,13));
				Element AcIsAR_StCdEle=new Element("AcIsAR_StCd");//代理保险合约状态代码
				AcIsAR_StCdEle.setText(LCContLisEle.getChildText("NrtStatus"));
				Element Uwrt_InfEle=new Element("Uwrt_Inf");//核保信息
				Element Tot_InsPrem_AmtEle=new Element("Tot_InsPrem_Amt");//总保费金额
				Tot_InsPrem_AmtEle.setText(NumberUtil.fenToYuan(LCContLisEle.getChildText("TotalMoney")));
				Element Ins_PyF_AmtEle=new Element("Ins_PyF_Amt");//保险缴费金额
				Ins_PyF_AmtEle.setText(NumberUtil.fenToYuan(LCContLisEle.getChildText("Money")));
				Element PyF_CODtEle=new Element("PyF_CODt");//缴费截止日期
				PyF_CODtEle.setText(LCContLisEle.getChildText("PayDndDate").replaceAll("-",""));
				Element Ins_Yr_Prd_CgyCdEle=new Element("Ins_Yr_Prd_CgyCd");//保险年期类别默认为03
				Ins_Yr_Prd_CgyCdEle.setText("03");
				Element Ins_DdlnEle=new Element("Ins_Ddln");
				Ins_DdlnEle.setText("10");//年金险保险周期数默认10年
				Element Ins_Cyc_CdEle=new Element("Ins_Cyc_Cd");
				Ins_Cyc_CdEle.setText("03");
			    sql="select BAK1 from TranLog where ProposalPrtNo='"+LCContLisEle.getChildText("ProposalContNo")+"' and funcflag='1060' and rcode='0'";
				BAK1=exeSql.getOneValue(sql);
				cLogger.info(BAK1);
			    String[] strs=BAK1.split("\\|");
				Element InsPrem_PyF_MtdCdEle=new Element("InsPrem_PyF_MtdCd");
				InsPrem_PyF_MtdCdEle.setText(strs[0]);
				Element InsPrem_PyF_Prd_NumEle=new Element("InsPrem_PyF_Prd_Num");//保费缴费期数默认为1
				InsPrem_PyF_Prd_NumEle.setText(strs[1]);
				Element InsPrem_PyF_Cyc_CdEle=new Element("InsPrem_PyF_Cyc_Cd");
				InsPrem_PyF_Cyc_CdEle.setText(strs[2]);
				Element PmNtc_BillNoEle=new Element("PmNtc_BillNo");
				
				DetailEle.addContent(Ins_BillNoEle);
				DetailEle.addContent(AcIsAR_StCdEle);
				DetailEle.addContent(Uwrt_InfEle);
				DetailEle.addContent(Tot_InsPrem_AmtEle);
				DetailEle.addContent(Ins_PyF_AmtEle);
				DetailEle.addContent(PyF_CODtEle);
				DetailEle.addContent(Ins_Yr_Prd_CgyCdEle);
				DetailEle.addContent(Ins_DdlnEle);
				DetailEle.addContent(Ins_Cyc_CdEle);
				DetailEle.addContent(InsPrem_PyF_MtdCdEle);
				DetailEle.addContent(InsPrem_PyF_Prd_NumEle);
				DetailEle.addContent(InsPrem_PyF_Cyc_CdEle);
				DetailEle.addContent(PmNtc_BillNoEle);
				
				Detail_ListEle.addContent(DetailEle);
			}
			Document outDoc=new Document(RootEle);
			cLogger.info("加密报文为:"+JdomUtil.toStringFmt(outDoc));
			//生成响应文件名
			File ccbLocalDirFile=new File(ccbLocalDir);
		    StringBuilder FileHeadStr=new StringBuilder(pThisBusiConf.getChild("funcFlag").getAttributeValue("outcode"))
			       .append("_").append(new SimpleDateFormat("yyyyMMdd").format(new Date()))
			       .append("_").append(pThisBusiConf.getParentElement().getChild("bank").getAttributeValue("insu"))
			       .append("_");
			final String fileName1=FileHeadStr.toString();
			//根据文件夹中以存在的文件生成下一个文件序号
			File[] files=ccbLocalDirFile.listFiles(new FileFilter() {
				@Override
				public boolean accept(File pathname) {
					if(pathname.isFile()&&pathname.getName().startsWith(fileName1)){
						return true;
					}
					return false;
				}
			});
			String fileName=FileHeadStr.toString();
			int fileSum=files.length+1;
			for (int i = 0; i <3-String.valueOf((fileSum)).length(); i++) {
				fileName+="0";
			}
			fileName=fileName+fileSum+".xml";
			cLogger.info("生成文件的文件名为:"+fileName);
			cLogger.info("本地文件生成路径:"+localDir+fileName);
			FileOutputStream output=new FileOutputStream(localDir+fileName);
			output.write(JdomUtil.toBytes(outDoc,"UTF-8"));
			output.flush();
			output.close();
			//加密文件
			cLogger.info("加密文件入参localID:"+localID+",remoteID:"+remoteID
					     +",localDir+fileName:"+localDir+fileName+",ccbLocalDir + fileName:"+ccbLocalDir + fileName);
			SecAPI.fileEnvelop(localID, remoteID,localDir+fileName, ccbLocalDir + fileName);
			cLogger.info("加密前文件路径:"+localDir+fileName);
			cLogger.info("加密后文件路径:"+ccbLocalDir + fileName);
			//在核心返回报文中RetData节点下添加FileName和FilePath节点
			Element FileNameEle=new Element("FileName");
			FileNameEle.setText(fileName);
			Element FilePathEle=new Element("FilePath");
			FilePathEle.setText(ccbLocalDir.substring(0,ccbLocalDir.length()-1));
			cOutXmlDoc.getRootElement().getChild("RetData").addContent(FileNameEle);
			cOutXmlDoc.getRootElement().getChild("RetData").addContent(FilePathEle);
		} catch (Exception e) {
			e.printStackTrace();
			cLogger.info("获取非实时核保状态交易生成响应文件失败！"+e.getMessage());
			throw new MidplatException("获取非实时核保状态交易生成响应文件失败！");
		}
		cLogger.info("生成加密响应文件成功！");
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
		mTranLogDB.setZoneNo("");
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
	    mTranLogDB.setProposalPrtNo("");
		mTranLogDB.setContNo("");
		mTranLogDB.setOtherNo("");
		mTranLogDB.setBak2("");
		mTranLogDB.setAppntName("");
		mTranLogDB.setAppntIDNo("");
		mTranLogDB.setRCode(CodeDef.RCode_NULL);
		mTranLogDB.setUsedTime(-1);
		mTranLogDB.setRText("处理中");
		mTranLogDB.setBak1("");
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
	public static void main(String[] args) {
//		File file=new File("C:/Users/anico/Desktop/测试文件夹/");
//		File[] fs =file.listFiles(new FileFilter() {
//			@Override
//			public boolean accept(File pathname) {
//				if(pathname.getName().startsWith("1")){
//				    return true;
//				}
//				return false;
//			}
//		});
//		System.out.println("++++++"+fs.length);
//		for (File file2 : fs) {
//			System.out.println("---"+file2.getName()+file2.isFile());
//		}
	}
}
