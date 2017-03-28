package com.sinosoft.midplat.newabc.bat;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.midplat.MidplatConf;
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
import com.sinosoft.midplat.newabc.NewAbcConf;
import com.sinosoft.midplat.newabc.format.NonRealTimeContRstDetailOutXsl;
import com.sinosoft.midplat.service.Service;
import com.sinosoft.utility.ElementLis;

/**
 * @ClassName: NonRealTimeContRstDetail
 * @Description: 非实时出单结果明细文件
 * @author sinosoft
 * @date 2017-3-27 下午2:38:04
 */
public class NonRealTimeContRstDetail extends TimerTask implements XmlTag {

	protected final static Logger cLogger=Logger.getLogger(NonRealTimeContRstDetail.class);
	private final XmlConf cThisConf;
	private final int cFuncFlag;
	protected Date cTranDate;
	protected String cResultMsg;
	protected Element cMidplatRoot=null;
	protected Element cThisConfRoot=null;
	protected Element cThisBusiConf=null;
	private String sTranNo=null;
	@SuppressWarnings("unused")
	private String curDate=null;
	private String fileDate=null;
	
	public NonRealTimeContRstDetail() {
		this(NewAbcConf.newInstance(),2005);
	}
	
	public NonRealTimeContRstDetail(XmlConf pThisConf, int pFuncFlag) {
		this.cThisConf=pThisConf;
		this.cFuncFlag=pFuncFlag;
	}

	@SuppressWarnings({ "unused", "static-access" })
	private Document sendRequest() {
		cLogger.info("Into NonRealTimeContRstDetail.sendRequest()...");
		ElementLis tTranData=new ElementLis(TranData);
		ElementLis tHead=new ElementLis(Head,tTranData);
		int date=DateUtil.get8Date(System.currentTimeMillis());
		int time=DateUtil.get6Time(System.currentTimeMillis());
		String sTranTime=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		ElementLis tTranDate=new ElementLis(TranDate,sTranTime.substring(0,8),tHead);
		ElementLis tTranTime=new ElementLis(TranTime,sTranTime.substring(8,14),tHead);
		ElementLis tTranCom=new ElementLis(TranCom,"05",tHead);
		ElementLis tBankCode=new ElementLis("BankCode","0102",tHead);
		ElementLis tZoneNo=new ElementLis(ZoneNo,"11",tHead);
		ElementLis tNodeNo=new ElementLis(NodeNo,cThisBusiConf.getChildText(NodeNo),tHead);
		ElementLis tTellerNo=new ElementLis(TellerNo,"11010102110",tHead);
		PubFun1 p=new PubFun1();
		sTranNo=p.CreateMaxNo("TransNo", 16);
		ElementLis tTranNo=new ElementLis(TranNo,sTranNo,tHead);
		ElementLis tFuncFlag=new ElementLis(FuncFlag,cThisBusiConf.getChildText(funcFlag),tHead);
		ElementLis tBody=new ElementLis(Body,tTranData);
		ElementLis ttTranDate=new ElementLis(TranDate,sTranTime.substring(0,8),tBody);
		Document pXmlDoc=new Document(tTranData);
		cLogger.info("Out NonRealTimeContRstDetail.sendRequest()!");
		return pXmlDoc;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		Thread.currentThread().setName(String.valueOf(NoFactory.nextTranLogNo()));
		cLogger.info("Into NonRealTimeContRstDetail.run()...");
		cResultMsg=null;
		try {
			cMidplatRoot=MidplatConf.newInstance().getConf().getRootElement();
			System.out.println(cThisConf.getConf().getRootElement().getChildText(TranCom));
			cThisConfRoot=cThisConf.getConf().getRootElement();
			System.out.println(cThisConfRoot);
			System.out.println(cFuncFlag);
			cThisBusiConf=(Element) XPath.selectSingleNode(cThisConfRoot, "business[funcFlag='"+cFuncFlag+"']");
			JdomUtil.print(cThisBusiConf);
			System.out.println(cThisBusiConf.getContent());
			String nextDate=cThisBusiConf.getChildText("nextDate");
			if(null==cTranDate)
				if((null!=nextDate)&&("Y".equals(nextDate))){
					cTranDate=new Date();
					cTranDate=new Date(cTranDate.getTime()-1000*3600*24);
				}else{
					cTranDate=new Date();
				}
			Element tTranData=new Element(TranData);
			Document tInStdXml=new Document(tTranData);
			System.out.println("====此处打印:发给核心的报文====");
			JdomUtil.print(tInStdXml);
			Element tHeadEle=getHead();
			tTranData.addContent(tHeadEle);
			try {
				String ttLocalDir=cThisBusiConf.getChildTextTrim(localDir);
				deal(ttLocalDir);
			} catch (Exception e) {
				cLogger.error("生成标准对账报文出错!",e);
				Element tError=new Element(Error);
				String tErrorStr=e.getMessage();
				if("".equals(tErrorStr))
					tErrorStr=e.toString();
				tError.setText(tErrorStr);
				tTranData.addContent(tError);
			}
			String tServiceClassName="com.sinosoft.midplat.service.ServiceImpl";
			String tServiceValue=cMidplatRoot.getChildText(service);
			if(null!=tServiceValue&&!"".equals(tServiceValue)){
				tServiceClassName=tServiceValue;
			}
			tServiceValue=cThisConfRoot.getChildText(service);
			if(null!=tServiceValue&&!"".equals(tServiceValue)){
				tServiceClassName=tServiceValue;
			}
			tServiceValue=cThisBusiConf.getChildText(service);
			if(null!=tServiceValue&&!"".equals(tServiceValue)){
				tServiceClassName=tServiceValue;
			}
			cLogger.info("业务处理模块:"+tServiceClassName);
			Constructor<Service> tServiceConstructor=(Constructor<Service>) Class.forName(tServiceClassName).getConstructor(new Class[]{Element.class});
			Service tService=(Service)tServiceConstructor.newInstance(new Object[]{cThisBusiConf});
			System.out.println("看看是啥:");
			JdomUtil.print(tInStdXml);
			Document tOutStdXml=tService.service(tInStdXml);
			cResultMsg=tOutStdXml.getRootElement().getChild(Head).getChildText(Desc);
			if("01".equals(DateUtil.getDateStr(cTranDate, "dd"))){
				bakFiles(cThisBusiConf.getChildTextTrim(localDir));
			}
		} catch (Throwable e) {
			cLogger.error("交易出错!",e);
			cResultMsg=e.toString();
		}
		cTranDate=null;
		cLogger.info("Out NonRealTimeContRstDetail.run()!");
	}

	private Element getHead() {
		cLogger.info("Into NonRealTimeContRstDetail.getHead()...");
		Element mTranDate=new Element(TranDate); 
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		Element mTranTime=new Element(TranTime); 
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));
		Element mTranCom=(Element)cThisConfRoot.getChild(TranCom).clone();
		Element mNodeNo=(Element)cThisBusiConf.getChild(NodeNo).clone();
		Element mTellerNo=new Element(TellerNo); 
		mTellerNo.setText(CodeDef.SYS);
		Element mTranNo=new Element(TranNo); 
		mTranNo.setText(getFileName());
		Element mFuncFlag=new Element(FuncFlag); 
		mFuncFlag.setText(cThisBusiConf.getChildText(funcFlag));
		Element mHead=new Element(Head); 
		mHead.addContent(mTranDate);
		mHead.addContent(mTranTime);
		mHead.addContent(mTranCom);
		mHead.addContent(mNodeNo);
		mHead.addContent(mTellerNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);
		cLogger.info("Out NonRealTimeContRstDetail.getHead()!");
		return mHead;
	}
	
	protected String getFileName(){
		String tTranNo=sendRequest().getRootElement().getChild(Head).getChildText(TranNo);
		return tTranNo;
	}

	protected void deal(String ttLocalDir) {
		cLogger.info("Into NonRealTimeContRstDetail.deal()...");
		Document cInXmlDoc=sendRequest();
		JdomUtil.print(cInXmlDoc);
		try {
			Document cOutXmlDoc=new CallWebsvcAtomSvc(AblifeCodeDef.SID_NonRealTimeContRstDtl).call(cInXmlDoc);
			cLogger.info("核心返回的非实时对账结果明细报文:");
			JdomUtil.print(cOutXmlDoc);
			cOutXmlDoc=NonRealTimeContRstDetailOutXsl.newInstance().getCache().transform(cOutXmlDoc);
			cLogger.info("转换后的对账结果明细报文:");
			JdomUtil.print(cOutXmlDoc);
			receive(cOutXmlDoc,ttLocalDir);
			getFileName2();
		}  catch (Exception e) {
			e.printStackTrace();
		}
		cLogger.info("Out NonRealTimeContRstDetail.deal()!");
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	private void receive(Document cOutXmlDoc, String ttLocalDir) throws Exception{
		cLogger.info("Into NonRealTimeContRstDetail.receive()..."+cTranDate);
		JdomUtil.print(cOutXmlDoc);
		fileDate=String.valueOf(DateUtil.get8Date(cTranDate))	;
		String sOutHead=null;
		String sSerialNo="";
		String sFileName="";
		String sOut="";
		String sOutBody="";
		int maxNo=100;
		Element tRoot=cOutXmlDoc.getRootElement();
		Element tHead=tRoot.getChild(Head);
		System.out.println(tRoot+"           ===========      "+tHead);
		String tFlag=tHead.getChildText(Flag);
		String tCorpNo=cThisConfRoot.getChild("bank").getAttributeValue("insu");
		String tFuncFlag=cThisBusiConf.getChildText(funcFlag);
		String tCount=null;
		String tSumMoney=null;
		Element tBody=tRoot.getChild(Body);
		List<Element> list=tBody.getChildren("Detail");
		tCount=Integer.toString(list.size());
		tSumMoney="0000";
		long sum=0;
		cLogger.info("生成返回文件头记录:"+sOutHead);
		cLogger.info("生成返回文件总记录:"+list.size());
		/*
		 *文件第一行：（汇总信息）格式：保险公司代码|银行代码|总记录数|总金额|
		 *文件其他内容：（明细记录）险种代码|保单号|缴费方式|缴费期间类型|缴费期间|保险期间类型|
		 *保险期间|保费|保额|
		 */
		if(list.size()!=0){
			for (int i = 0; i <= list.size()/maxNo; i++) {
				if(i<10){
					sSerialNo="0"+Integer.toString(i);
					sFileName="FRESULTKZ"+tCorpNo+"."+fileDate;
				}else{
					sSerialNo=Integer.toString(i);
					sFileName="FRESULTKZ"+tCorpNo+"."+fileDate;
				}
				File file=new File(ttLocalDir+"/"+sFileName);
				if(!file.exists()){
					try {
						file.createNewFile();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				for (int j = i*maxNo; j < (i+1)*maxNo; j++) {
					if(j==list.size()){
						break;
					}
					Element tDetail=list.get(j);
					String tRiskCode=tDetail.getChildText(RiskCode);
					String tContNo=tDetail.getChildText(ContNo);
					String tPayIntv=tDetail.getChildText(PayIntv);
					String tPayEndYearFlag=tDetail.getChildText(PayEndYearFlag);
					String tPayEndYear=tDetail.getChildText(PayEndYear);
					String tInsuYearFlag=tDetail.getChildText(InsuYearFlag);
					String tInsuYear=tDetail.getChildText(InsuYear);
					String tPrem=tDetail.getChildText(Prem);
					long prem=Long.parseLong(tPrem);
					sum+=prem;
					tPrem=NumberUtil.fenToYuan(tPrem);
					String tAmnt=tDetail.getChildText(Amnt);
					tAmnt=NumberUtil.fenToYuan(tAmnt);
					sOutBody+=tRiskCode+"|"+tContNo+"|"+tPayIntv+"|"+tPayEndYearFlag+"|"+tPayEndYear+"|"+tInsuYearFlag+"|"+tInsuYear+"|"+tPrem+"|"+tAmnt+"|"+"\n";
				}
				tSumMoney=String.valueOf(sum);
				tSumMoney=NumberUtil.fenToYuan(tSumMoney);
				System.out.println("总金额:"+tSumMoney);
				if("".equals(tSumMoney)||tSumMoney==null)
					tSumMoney="0.00";
				sOutHead=tCorpNo+"|"+"03"+"|"+tCount+"|"+tSumMoney+"|"+"\n";
				sOut=sOutHead+sOutBody;
				cLogger.info("第"+i+"个文件生成返回文件总记录:"+sOut);
				byte[] m=sOut.getBytes("GBK");
				try {
					FileOutputStream fos=new FileOutputStream(file);
					fos.write(m);
					fos.flush();
					fos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					cLogger.error("找不到文件!");
				}catch (IOException e) {
					e.printStackTrace();
					cLogger.error("IO异常!");
				}
				if(i*maxNo==list.size())
					file.delete();
				sOutBody="";
				sOut="";
			}
		}else{	
			sFileName="FRESULTKZ"+tCorpNo+"."+fileDate;
			File file=new File(ttLocalDir+"/"+sFileName);
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			tCount="0";
			tSumMoney="0.00";
			sOutHead=tCorpNo+"|"+"03"+"|"+tCount+"|"+tSumMoney+"|"+"\n";
			sOut=sOutHead;
			cLogger.info("只返回文件头，核心未返回保单险种信息!"+sOut);
			byte[] m=sOut.getBytes("GBK");
			try {
				FileOutputStream fos=new FileOutputStream(file);
				fos.write(m);
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				cLogger.error("找不到文件");
			}catch (IOException e) {
				e.printStackTrace();
				cLogger.error("IO异常");
			}
		}
		System.out.println("文件名:"+sFileName);
		cLogger.info("Out NonRealTimeContRstDetail.receive()!");
	}

	protected String getFileName2() throws Exception{
		Element mBankEle=cThisConfRoot.getChild("bank");
		File_download f=new File_download(cThisBusiConf,"FSSCDJGWJD",fileDate,mBankEle.getAttributeValue("insu"));
		String sFileName="FRESULTKZ"+mBankEle.getAttributeValue("insu")+"."+fileDate;
		try {
			f.bank_dz_file();
		} catch (Exception e) {
			e.printStackTrace();
			throw new MidplatException(e.getMessage());
		}
		return sFileName;
	}

	private void bakFiles(String pFileDir) {
		cLogger.info("Into NonRealTimeContRstDetail.bakFiles()...");
		if(null==pFileDir||"".equals(pFileDir)){
			cLogger.warn("本地文件目录为空，不进行备份操作!");
			return;
		}
		File mDirFile=new File(pFileDir);
		if(!mDirFile.exists()||!mDirFile.isDirectory()){
			cLogger.info("本地文件目录不存在，不进行备份操作!"+mDirFile);
			return;
		}
		File[] mOldFiles=mDirFile.listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pFile) {
				if(!pFile.isFile())
					return false;
				Calendar cCurCalendar=Calendar.getInstance();
				cCurCalendar.setTime(cTranDate);
				Calendar cFileCalendar=Calendar.getInstance();
				cFileCalendar.setTime(new Date(pFile.lastModified()));
				return cFileCalendar.before(cCurCalendar);
			}
		});
		Calendar mCalendar=Calendar.getInstance();
		mCalendar.add(Calendar.MONTH, -1);
		File mNewDir=new File(mDirFile,DateUtil.getDateStr(mCalendar, "yyyy/yyyyMM"));
		for (File tFile : mOldFiles) {
			try {
				String fileName_date=tFile.getName().substring(tFile.getName().length());
				Date date=new Date();
				if(!fileName_date.equals(String.valueOf(DateUtil.get8Date(date)))){
					cLogger.info(tFile.getAbsoluteFile()+" start move...");
					IOTrans.fileMove(tFile, mNewDir);
					cLogger.info(tFile.getAbsoluteFile()+" end move!");
				}
			} catch (IOException e) {
				cLogger.error(tFile.getAbsoluteFile()+"备份失败!",e);
			}
		}
		cLogger.info("Out NonRealTimeContRstDetail.bakFiles()!");
	}
	
	public static void main(String[] args) {
		cLogger.info("开始非实时出单结果明细传递...");	//没起作用，未打印
		
		NonRealTimeContRstDetail pt=new NonRealTimeContRstDetail();	//没起作用
		pt.run();	//没起作用
		
		cLogger.info("结束非实时出单结果明细传递!");	//没起作用，未打印
	}
	
}
