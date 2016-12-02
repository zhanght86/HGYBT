/**
 * ��ũ�з�ʵʱ����������ϸ�ļ�
 */

package com.sinosoft.midplat.newabc.bat;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimerTask;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

import sun.security.util.BigInt;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.bat.Balance;
import com.sinosoft.midplat.bat.GetSFTPConnection;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.format.Format;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newabc.NewAbcConf;
import com.sinosoft.midplat.newabc.format.NonRealTimeContRiskDtlOutXsl;
import com.sinosoft.midplat.service.Service;
import com.sinosoft.utility.ElementLis;
import com.sinosoft.utility.ExeSQL;
import java.lang.reflect.Constructor;

public class NonRealTimeContRiskDtl extends TimerTask implements XmlTag{
	protected final Logger cLogger = Logger.getLogger(getClass());
	
	//��ϵͳ�����ļ����������:icbc.xml
	private final XmlConf cThisConf;
	private final int cFuncFlag;	//���״���
	/**
	 * �ṩһ��ȫ�ַ��ʵ㣬ֻ��ÿ�ζ��˿�ʼʱ��ʼ����
	 * ȷ���ڸôζ��˴������������������һ���ԣ�
	 * ���ܿ���(ǰ��Ĵ�����0��ǰ���������0���)��Ӱ�졣
	 */
	protected Date cTranDate;
	
	protected String cResultMsg;
	
	/**
	 * �ṩһ��ȫ�ַ��ʵ㣬ֻ��ÿ�ζ��˿�ʼʱ���³�ʼ����
	 * ȷ���ڸôζ��˴������������������һ���ԣ�
	 * ���������ļ��Զ����ص�Ӱ�졣Ҳ����˵�����ζ�ʱ����һ��������
	 * ��������ļ����޸Ľ�������һ������ʱ��Ч����Ӱ�챾�Ρ�
	 */
	protected Element cMidplatRoot = null;
	protected Element cThisConfRoot = null; 
	protected Element cThisBusiConf = null;
	
	private String stranNo = null;
	private String curDate = null;
	private String fileDate = null;
	
	public NonRealTimeContRiskDtl() {
		this(NewAbcConf.newInstance(),2009);
	}
	
	public NonRealTimeContRiskDtl(XmlConf pThisConf, int pFuncFlag) {
		cThisConf = pThisConf;
		cFuncFlag = pFuncFlag;
	}
	protected String getFileName() {
		String tTranNo = sendRequest().getRootElement().getChild("Head").getChildText("TranNo");
		return tTranNo;
	}
	protected String getCoreNum() {
		Element mBankEle = cThisConfRoot.getChild("bank");
		return mBankEle.getAttributeValue("insu");
	}
	
	protected Element getHead() {
		cLogger.info("Into NonRealTimeContRiskDtl.getHead()...");
		
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));
		
		Element mTranCom = (Element) cThisConfRoot.getChild(TranCom).clone();
		
		Element mNodeNo = (Element) cThisBusiConf.getChild("NodeNo").clone();
		
		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText(CodeDef.SYS);
		
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(getFileName());
		
		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cThisBusiConf.getChildText(funcFlag));
		
		Element mHead = new Element(Head);
		mHead.addContent(mTranDate);
		mHead.addContent(mTranTime);
		mHead.addContent(mTranCom);
		mHead.addContent(mNodeNo);
		mHead.addContent(mTellerNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);

		cLogger.info("Out NonRealTimeContRiskDtl.getHead()!");
		return mHead;
	}
	protected void deal(String ttLocalDir) {
		cLogger.info("Into NonRealTimeContRiskDtl.deal()...");
		//����������������
		Document cInXmlDoc = sendRequest();
		try {
			//������ķ��ر���
			Document cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_NonRealTimeContRiskDtl).call(cInXmlDoc);
			
			//���ز�����
//			String mInFilePath = "F:/995499_2_49_outSvc.xml";
//			InputStream mIs = new FileInputStream(mInFilePath);
//			Document mInXmlDoc = JdomUtil.build(mIs);
//			
//			Document cOutXmlDoc = mInXmlDoc;
			
			//���Ա��ر���
//			InputStream is = new FileInputStream(new File("d://���˱�Ӧ���ĸ�ʽ.xml"));
//			Document cOutXmlDoc = JdomUtil.build(is);
			cLogger.info("���ķ��صķ�ʵʱ���˽��������ϸ���ģ�");
			JdomUtil.print(cOutXmlDoc);
			//ʹ����ʽ��Թ�ϵ���룬֤�����ͣ����ִ��룬�ɷѷ�ʽת����ũ�еģ����ı���ı�׼���Ľṹ
			cOutXmlDoc=NonRealTimeContRiskDtlOutXsl.newInstance().getCache().transform(cOutXmlDoc);
			cLogger.info("���ķ��صķ�ʵʱ���˽��������ϸ���ģ�");
			JdomUtil.print(cOutXmlDoc);
			receive(cOutXmlDoc,ttLocalDir);

			getFileName2();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		cLogger.info("Out NonRealTimeContRiskDtl.deal()...");
	}
	

	/**
	 *  ���ö����ļ���������ÿ�������һ�����ɵ��ļ���Ϊ�¸��µ�һ�� 
	 */	
	protected String dealFileName()throws Exception {

	    //���������ļ����ڣ�һ��ע��ÿ�������һ������ڣ���ũ�и��ܽ�Լ����
	    //�������ɵ��˱��̳������ļ��ͷ�ʵʱ��������ļ����ļ���Ϊ���գ�ũ�л��ڴ��մ���
		//curDate=��ǰ����
		//fileDate=�����ļ�����
		//20141217
		String gfileDate="";
		Calendar c = Calendar.getInstance();
	    curDate=String.valueOf(DateUtil.get8Date(c.getTime()));
	    System.out.println("�����ǣ�"+curDate);
	    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 

		Calendar   cDay1   =   Calendar.getInstance();   
        cDay1.setTime(new Date());   
        final   int   lastDay   =   cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);   
        Date   lastDate   =   cDay1.getTime();   
        lastDate.setDate(lastDay);  
		System.out.println("���һ�죺"+DateUtil.get8Date(lastDate));
		
		if(String.valueOf(DateUtil.get8Date(lastDate))==curDate||String.valueOf(DateUtil.get8Date(lastDate)).equals(curDate)){
			System.out.println("�����Ǳ������һ��");
		    c.set(Calendar.MONTH, c.get(Calendar.MONTH)+1);
		    c.set(Calendar.DAY_OF_MONTH, 1);
		    gfileDate=String.valueOf(DateUtil.get8Date(c.getTime()));
		    System.out.println("�¸��µĵ�һ��: " + gfileDate);
			System.out.println("������ļ����ڣ�"+gfileDate);
		}else{
			System.out.println("���첻�Ǳ������һ��");
			
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			Date date = calendar.getTime();
			gfileDate=String.valueOf(DateUtil.date10to8((sdf.format(date))));
			System.out.println("�������ɵ��ļ����ڣ�"+gfileDate);
		}
		return gfileDate;
	}
	
	
	/**
	 *  ��ȡ�����ļ��� 
	 */	
	protected String getFileName2()throws Exception {
		Element mBankEle = cThisConfRoot.getChild("bank");
		File_download f = new File_download(cThisBusiConf,"FSSCDXZMX",fileDate,mBankEle.getAttributeValue("insu"));
		String fileName="FRESULTKZ"+mBankEle.getAttributeValue("insu")+"."+fileDate;
		try {
			f.bank_dz_file();
		} catch (Exception e) {
			e.printStackTrace();
			throw new MidplatException(e.getMessage());
		}
		return fileName;
	}
	
	//����������  20141222 
	private Document sendRequest(){
		cLogger.info("Into NonRealTimeContRiskDtl.sendRequest()...");
		ElementLis TranData = new ElementLis("TranData");
		ElementLis Head = new ElementLis("Head",TranData);
		int date = DateUtil.get8Date(System.currentTimeMillis());
		int time = DateUtil.get6Time(System.currentTimeMillis());
		String trantime= new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		ElementLis TranDate = new ElementLis("TranDate",trantime.substring(0,8),Head);
		ElementLis TranTime = new ElementLis("TranTime",trantime.substring(8,14),Head);
		ElementLis TranCom = new ElementLis("TranCom","05",Head);
		ElementLis ZoneNo = new ElementLis("ZoneNo","11",Head);
		ElementLis NodeNo = new ElementLis("NodeNo",cThisBusiConf.getChildText("NodeNo"),Head);
		ElementLis BankCode = new ElementLis("BankCode","0102",Head);
		ElementLis TellerNo = new ElementLis("TellerNo","11010102110", Head);
		PubFun1 p = new PubFun1();
		stranNo = p.CreateMaxNo("TransNo",16);
		ElementLis TranNo = new ElementLis("TranNo",stranNo,Head);
		ElementLis FuncFlag = new ElementLis("FuncFlag",cThisBusiConf.getChildText("funcFlag"),Head);//���״���
		ElementLis Body = new ElementLis("Body",TranData);
		Document pXmlDoc = new Document(TranData);	
		cLogger.info("Out NonRealTimeContRiskDtl.sendRequest()..."+cTranDate);
		return pXmlDoc;
	}
	//������ķ��ر��Ĳ��洢�ڹ̶�·�� 20141222
	private void receive(Document cOutXmlDoc,String ttLocalDir) throws Exception{
		cLogger.info("Into NonRealTimeContRiskDtl.receive()..."+cTranDate);
		JdomUtil.print(cOutXmlDoc);
		
		fileDate=dealFileName();
		
		String outHead = null;//ͷ��¼
		String fileName = "";//�ļ�����
		String out ="";
		String outBody="";
	    int  maxNo = 100;//һ��txt������������ϸ��¼��  ���ԵĻ���ֻ��Ҫ�����ֵ��������
		//ͷ��¼����
		Element tRoot = cOutXmlDoc.getRootElement();
		Element tHead = tRoot.getChild("Head");
		System.out.println(tRoot+"           ===========      "+tHead);
		String tFlag = tHead.getChildText("Flag");//���׳ɹ���־
		String tComCode = cThisConfRoot.getChild("bank").getAttributeValue("insu");//���չ�˾����
		String tFuncFlag = cThisBusiConf.getChildText("funcFlag");//���״���
		String tCount = null;//��ϸ��¼�ܱ���
		String tSumMoney = null;//�ܽ��
	    Element tBody = tRoot.getChild("Body");
	    List<Element> list  = tBody.getChildren("Detail");
    	tCount = Integer.toString(list.size());
    	tSumMoney="0000";
    	long sum=0;
    	  	
	    cLogger.info("���ɷ����ļ�ͷ��¼"+outHead);
	    cLogger.info("���ɷ����ļ��ܼ�¼"+list.size());
	    
	    /*
	     *  �ļ���һ�У���������Ϣ��
			��ʽ�����չ�˾����|���д���|�ܼ�¼��|�ܽ��|
			�ļ��������ݣ�����ϸ��¼��
			���ִ���|������|�ɷѷ�ʽ|�ɷ��ڼ�����|�ɷ��ڼ�|�����ڼ�����|�����ڼ�|����|����|
	     */
	    if(list.size()!=0){
	    /*
		 * ͨ�������������ȡ�����������ļ�������
		 */
	    for (int i = 0; i <=list.size()/maxNo; i++) {
			if(i<10){
				fileName = "FRESULTKZ"+tComCode+"."+fileDate;
			}else{
				fileName = "FRESULTKZ"+tComCode+"."+fileDate;
			}
			File file = new File(ttLocalDir+"/"+fileName);
			//����ļ������ڴ����ļ�
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/*
			 * ��ѭ�������i���ļ�������(i+1)*maxNo - i*maxNo����ϸ��¼
			 * ��i���ļ���ȡ����ϸ��¼�Ǵӵ�i*maxNo������(i+1)*maxNo��,
			 * ��ȡ��ֵ��������ͬʱ����������ѭ�� 
			 */
			for (int j = i*maxNo; j <(i+1)*maxNo; j++) {
				if(j==list.size()){
					break;
				}
				Element tDetail = list.get(j);
				
				String tRiskCode = tDetail.getChildText("RiskCode");
				String tContNo = tDetail.getChildText("ContNo");
				String tPayIntv = tDetail.getChildText("PayIntv");
				String tPrem = tDetail.getChildText("Prem");
				//������
				long prem=Long.parseLong(tPrem);
				sum=sum+prem;
				tPrem = NumberUtil.fenToYuan(tPrem);
				String tAmnt = tDetail.getChildText("Amnt");
				tAmnt=NumberUtil.fenToYuan(tAmnt);
				
				String tPayEndYearFlag = tDetail.getChildText("PayEndYearFlag");
				String tPayEndYear = tDetail.getChildText("PayEndYear");
				String tInsuYearFlag = tDetail.getChildText("InsuYearFlag");
				String tInsuYear = tDetail.getChildText("InsuYear");

				outBody += tRiskCode+"|"+tContNo+"|"+tPayIntv+"|"+tPayEndYearFlag+"|"+tPayEndYear+"|"+tInsuYearFlag+"|"+tInsuYear+"|"+tPrem+"|"+tAmnt+"|"+"\n";
			}
			//�����¼��txt�ļ�
			tSumMoney=String.valueOf(sum);
			tSumMoney=NumberUtil.fenToYuan(tSumMoney);
			System.out.println("�ܽ�"+tSumMoney);
			if(tSumMoney.equals("")||tSumMoney==null){
				tSumMoney="0.00";
			}
			outHead = tComCode+"|"+"03"+"|"+tCount+"|"+tSumMoney+"|"+"\n";
			out = outHead + outBody;
			cLogger.info("��"+i+"���ļ����ɷ����ļ��ܼ�¼"+out);
			byte[] m = out.getBytes("GBK");
			try {
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(m);
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				cLogger.error("�Ҳ����ļ�");
			} catch (IOException e) {
				e.printStackTrace();
				cLogger.error("I/O�쳣");
			}
			//ɾ���ļ����������ɶ����ļ�
			if(i*maxNo == list.size()){
				file.delete();
			}
			//�����ϸ��¼���ܼ�¼
			outBody="";
			out="";
		}
	  }else{
		  	fileName = "FRESULTKZ"+tComCode+"."+fileDate;
	    	File file = new File(ttLocalDir+"/"+fileName);
			//����ļ������ڴ����ļ�
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//�����¼��txt�ļ�
			tCount="0";
			tSumMoney="0.00";
			outHead = tComCode+"|"+"03"+"|"+tCount+"|"+tSumMoney+"|"+"\n";
			out = outHead;
			cLogger.info("ֻ����ͷ�ļ�������δ����ֵ"+out);
			byte[] m = out.getBytes("GBK");
			try {
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(m);
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				cLogger.error("�Ҳ����ļ�");
			} catch (IOException e) {
				e.printStackTrace();
				cLogger.error("I/O�쳣");
			}
	  }
		System.out.println("�ļ�����"+fileName);
		cLogger.info("Out NonRealTimeContRiskDtl.receive()...");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Thread.currentThread().setName(
				String.valueOf(NoFactory.nextTranLogNo()));
		cLogger.info("Into NonRealTimeContRiskDtl.run()...");
		
		//�����һ�ν����Ϣ
		cResultMsg = null;
		
		try { 
			  
			cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
			System.out.println(cThisConf.getConf().getRootElement().getChildText("TranCom"));
			cThisConfRoot = cThisConf.getConf().getRootElement();
			System.out.println(cThisConfRoot);
			System.out.println(cFuncFlag);
			cThisBusiConf = (Element) XPath.selectSingleNode(
					cThisConfRoot, "business[funcFlag='"+cFuncFlag+"']");
			JdomUtil.print(cThisBusiConf);
			System.out.println(cThisBusiConf.getContent());
			String nextDate = cThisBusiConf.getChildText("nextDate");
				if (null == cTranDate) {
					if(null != nextDate && "Y".equals(nextDate)){
						cTranDate = new Date();
						cTranDate = new Date(cTranDate.getTime()-1000*3600*24);
					}else
						cTranDate = new Date();
				}
			
			Element tTranData = new Element(TranData);
			Document tInStdXml = new Document(tTranData);
			System.out.println("====�˴���ӡ���������ĵı���====");
			JdomUtil.print(tInStdXml);
			Element tHeadEle = getHead();
			tTranData.addContent(tHeadEle);
			
			try {
				String ttLocalDir = cThisBusiConf.getChildTextTrim(localDir);
				InputStream ttBatIns = null;

				deal(ttLocalDir);
				
			} catch (Exception ex) {
				cLogger.error("���ɱ�׼���˱��ĳ���", ex);

				//��ȡ��׼���˱���
				Element ttError = new Element(Error);
				String ttErrorStr = ex.getMessage();
				if ("".equals(ttErrorStr)) {
					ttErrorStr = ex.toString();
				}
				ttError.setText(ttErrorStr);
				tTranData.addContent(ttError);
			}
			
			//����ҵ������ȡ��׼���ر���
			String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";
			//��midplat.xml���зǿ�Ĭ�����ã����ø�����
			String tServiceValue = cMidplatRoot.getChildText(service);
			if (null!=tServiceValue && !"".equals(tServiceValue)) {
				tServiceClassName = tServiceValue;
			}
			//����ϵͳ�ĸ��Ի������ļ����зǿ�Ĭ�����ã����ø�����
			tServiceValue = cThisConfRoot.getChildText(service);
			if (null!=tServiceValue && !"".equals(tServiceValue)) {
				tServiceClassName = tServiceValue;
			}
			tServiceValue = cThisBusiConf.getChildText(service);
			if (null!=tServiceValue && !"".equals(tServiceValue)) {
				tServiceClassName = tServiceValue;
			}
			cLogger.info("ҵ����ģ�飺" + tServiceClassName);
			Constructor tServiceConstructor = (Constructor<Service>) Class.forName(
					tServiceClassName).getConstructor(new Class[]{Element.class});
			Service tService = (Service) tServiceConstructor.newInstance(new Object[]{cThisBusiConf});
			System.out.println("������ɶ��");
			JdomUtil.print(tInStdXml);
			Document tOutStdXml = tService.service(tInStdXml);
			
			cResultMsg = tOutStdXml.getRootElement().getChild( 
					Head).getChildText(Desc);
			
			//ÿ��1�ձ������µĶ����ļ�
			if ("01".equals(DateUtil.getDateStr(cTranDate, "dd"))) {
				bakFiles(cThisBusiConf.getChildTextTrim(localDir));
			}
		} catch (Throwable ex) {
			cLogger.error("���׳���", ex);
			cResultMsg = ex.toString();
		}
		
		cTranDate = null;	//ÿ�����꣬�������
		
		cLogger.info("Out NonRealTimeContRiskDtl.run()!");
	}
	
	//�������ļ�
	private void bakFiles(String pFileDir) {
		cLogger.info("Into Balance.bakFiles()...");
		
		if (null==pFileDir || "".equals(pFileDir)) {
			cLogger.warn("�����ļ�Ŀ¼Ϊ�գ������б��ݲ�����");
			return;
		}
		File mDirFile = new File(pFileDir);
		if (!mDirFile.exists() || !mDirFile.isDirectory()) {
			cLogger.warn("�����ļ�Ŀ¼�����ڣ������б��ݲ�����" + mDirFile);
			return;
		}
		
		File[] mOldFiles = mDirFile.listFiles(
				new FileFilter(){
					public boolean accept(File pFile) {
						if (!pFile.isFile()) {
							return false;
						}
						
						Calendar tCurCalendar = Calendar.getInstance();
						tCurCalendar.setTime(cTranDate);
						
						Calendar tFileCalendar = Calendar.getInstance();
						tFileCalendar.setTime(new Date(pFile.lastModified()));
						
						return tFileCalendar.before(tCurCalendar);
					}
				});
		
		Calendar mCalendar = Calendar.getInstance();
		mCalendar.add(Calendar.MONTH, -1);
		File mNewDir = 
			new File(mDirFile, DateUtil.getDateStr(mCalendar, "yyyy/yyyyMM"));
		for (File tFile : mOldFiles) {
			try {
				String fileName_date=tFile.getName().substring(tFile.getName().length()-8);
				Date date=new Date();
				if(!fileName_date.equals(String.valueOf(DateUtil.get8Date(date)))){
					cLogger.info(tFile.getAbsoluteFile() + " start move...");
					IOTrans.fileMove(tFile, mNewDir);
					cLogger.info(tFile.getAbsoluteFile() + " end move!");
				}
			} catch (IOException ex) {
				cLogger.error(tFile.getAbsoluteFile()+"����ʧ�ܣ�", ex);
			}
		}
		
		cLogger.info("Out Balance.bakFiles()!");
	}
}
