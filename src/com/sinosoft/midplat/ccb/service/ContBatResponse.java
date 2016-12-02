package com.sinosoft.midplat.ccb.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.midplat.ccb.CcbConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.DateUtilZR;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.ExeSQL;

public class ContBatResponse implements XmlTag{
	/**
	 * mod by zhoupan 20120726
	 * �������ɵı�׼������� 
	 */
	static String sdate=DateUtil.date10to8(DateUtil.getCur10Date());
	private final static Logger cLogger = Logger.getLogger(ContBatRequest.class);
	private static String mFlag="";
	private static String mSavePath="";
	public static Document call(Document pInXmlDoc) throws Exception {
		cLogger.info("Into ContBatResponse.call()...");
		Element tOutHeadEle=pInXmlDoc.getRootElement().getChild(Head);
		Element sBody = pInXmlDoc.getRootElement().getChild(Body);
		Element Flag=new Element("Flag");
		Element Desc=new Element("Desc");
		String TotalNum=sBody.getChildText("TotalNum");
		String TotalMoney=sBody.getChildText("TotalMoney");
		try {
//			Element tRootEle=pInXmlDoc.getRootElement();
			String DealType = sBody.getChildText("DealType");
			tOutHeadEle.addContent(Flag);
			tOutHeadEle.addContent(Desc);
			StringBuffer sbstr=new StringBuffer();
//			String DealType=sBody.getChildText(DealType);
			sbstr.append(DealType).append(",")
			.append(000000000000351).append(",")
			.append(before8Day(DateUtil.getCur10Date())).append(",")
			.append(TotalNum).append(",")
			.append(TotalMoney).append(",");
			if(DealType.equals("S")){
				sbstr.append("98");
			}else if(DealType.equals("F")){
				sbstr.append("99");
			}else{
				sbstr.append("00");
				throw new MidplatException("�����ļ�������");
			}
			List<Element> DetailList=sBody.getChildren("Detail");
			for (int i = 0; i < DetailList.size(); i++) {
				Element Detail=DetailList.get(i);
				String sPayCode=Detail.getChildText("PayCode");//���չ�˾����ϸ���
				String sAccName=Detail.getChildText(AccName);//�˺���
				String sAccNo=Detail.getChildText(AccNo);//�˺�
				String sPayMoney=Detail.getChildText("PayMoney");//���
				String sBankSuccFlag=Detail.getChildText("BankSuccFlag");//�ɹ���־
				String sReason=Detail.getChildText("Reason");//�ɹ�����
				sbstr.append("\r\n").append(sPayCode).append(",")
				.append(",").append(CcbConf.newInstance().getConf().getRootElement().getChildText("BankNo")).append(",").append("00").append(",")
				.append(sAccNo).append(",").append(sAccName).append(",").append("")
				.append(",").append("").append(",").append("").append(",")
				.append("0").append(",").append(sPayMoney).append(",")
				.append("CNY").append(",").append(" ").append(",").append("").append(",")
				.append(",").append("").append(",").append("").append(",").append("")
				.append(",").append("").append(",")
				.append(sBankSuccFlag).append(",").append(sReason);
			}
			String tdate=beforeDay(DateUtil.getCur10Date());
			String tpath=getFolderName(tdate);
			if(CreateFileFolder(tpath))
				{
				try {
					if(DealType.equals("F")){
						insertFile(getFileName(), sbstr);
					}else if(DealType.equals("S")){
						insertFile(getFileName2(), sbstr);
					}else {
						throw new MidplatException("�����ļ�������");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				}
			cLogger.info(sbstr.toString());
			Flag.setText("0");
			Desc.setText("�������̽��׳ɹ���");
			
		} catch (Exception e) {
			e.printStackTrace();
			Flag.setText("1");
			if(tOutHeadEle.getChildText("Desc").equals("")){
				Desc.setText("�������̽���ʧ�ܣ�");
			}else{
				Desc.setText(tOutHeadEle.getChildText("Desc"));
			}
			throw new MidplatException(tOutHeadEle.getChildText("Desc"));
		} 
		JdomUtil.print(pInXmlDoc);
		cLogger.info("out ContBatResponse.call()...");
		return pInXmlDoc;
	}
	

	/**
	 * 1����ǰһ������� --10λ  mod by zhoupan 20120728
	 * @param date
	 * @return
	 */
	public static String beforeDay(String date){
		 if(date.length()==8){
			 date=DateUtil.date8to10(date);
		 }
		 Date   sDate   =   DateUtil.parseDate(date, "yyyy-MM-dd");
		 Calendar cal = Calendar.getInstance();    
	     cal.setTime(sDate);
	     cal.add(Calendar.DATE, -1); 
	     return DateUtil.get10Date(cal.getTime());
	 }
	/**
	 * 1����ǰһ������� --8λ  mod by zhoupan 20120728
	 * @param date
	 * @return
	 */
	public static String before8Day(String date){
		 if(date.length()==10){
			 date=DateUtil.date10to8(date);
		 }
		 Date   sDate   =   DateUtil.parseDate(date, "yyyyMMdd");
		 Calendar cal = Calendar.getInstance();    
	     cal.setTime(sDate);
	     cal.add(Calendar.DATE, -1); 
	     return get8Date(cal.getTime());
	 }
	public static String get8Date(Date pDate) {
		return new SimpleDateFormat("yyyyMMdd").format(pDate);
	}
	/**
	 * 2��ȡ�ļ�·��mod by zhoupan 20120728
	 * @return
	 */
	public static String getFolderName(String sdate){
		String spath =  CcbConf.newInstance().getConf().getRootElement().getChildText("BatlocalDir");
		spath+="ReturnFromBankFile"+File.separator+sdate.substring(0, 4)+File.separator+sdate.substring(5, 7)+File.separator+sdate.substring(8, 10); 
		return spath;
		
	}
	/**
	 * 3�����ļ���
	 * @param path
	 * @return
	 */
	public static boolean CreateFileFolder(String path){
		 File tFile = new File(path);
		 boolean bl = tFile.exists();
		 System.out.println(bl);
		 if(!bl){
			 return tFile.mkdirs(); 
		 }else {
			 return bl;
		 }
	}
	/**
	 * 4.1��ȡ�����ļ���·�� mod by zhoupan 20120728
	 * @return
	 */
	public static String getFileName(){
		String mDate = DateUtil.getCur10Date();
		mDate = beforeDay(mDate);
		String path=getFolderName(mDate)+File.separator+"B0301F10000000000000000000("+mDate+").z";
		cLogger.info(mDate);
		return path;
	}
	/**
	 * 4.2��ȡ�����ļ���·�� mod by zhoupan 20120728
	 * @return
	 */
	public static String getFileName2(){
		String mDate = DateUtil.getCur10Date();
		mDate = beforeDay(mDate);
		String path=getFolderName(mDate)+File.separator+"B0301S10000000000000000000("+mDate+").z";
		cLogger.info(mDate);
		return path;
	}
	/**
	 * 5�����ļ�
	 * @param path
	 * @param ttFileName
	 * @throws Exception
	 */
	public static void insertFile(String path, StringBuffer ttFileName)
	throws Exception {
		BufferedWriter bw = null;
		try {
			checkFile(path);
			File cFile = new File(path);
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(cFile,true)));		
			System.out.println(ttFileName.toString());
			bw.write(ttFileName.toString());      //Ϊ������Ϣ��ֵ				
			bw.newLine();
			bw.flush();
			bw.close();
			cLogger.info("�ļ���д��ϣ��ѱ��浽����:"+path);	
		}
		catch (Exception e) {
	deleteFile(path);
	cLogger.fatal("�ļ���дʧ��:", e);
	e.printStackTrace();			
}
cLogger.info("�����ļ�������");
}
	
	public static void checkFile(String path){
		 File tFile = new File(path);
		   if(tFile.exists()){ //�ж��ļ��Ƿ����
		    if(tFile.isFile()){            //�ж��Ƿ����ļ�
		    	System.out.println("�ļ����ڣ���Ҫɾ���Ѵ��ڵ��ļ�!");
		     if(!tFile.delete()){
		    	System.out.println(tFile.getPath()+"ɾ���ļ�ʧ��");
		     }                      //delete()���� ��Ӧ��֪�� ��ɾ������˼;
		    }
		   }else{
			   System.out.println("�ļ������ڣ����Դ�����"+'\n'); 
		   } 
	}
	public static void deleteFile(String path){
		   File tFile = new File(path);
		   if(tFile.exists()){ //�ж��ļ��Ƿ����
		    if(tFile.isFile()){            //�ж��Ƿ����ļ�
		    	System.out.println("ɾ���ļ�!");
		     if(!tFile.delete()){
		    	System.out.println(tFile.getPath()+"ɾ���ļ�ʧ��");
		     }                      //delete()���� ��Ӧ��֪�� ��ɾ������˼;
		    }
		   }else{
			   System.out.println("��ɾ�����ļ������ڣ�"+'\n'); 
		   } 
		}  
	/**
	 * �����ļ���
	 * @param sdate
	 * @return
	 */
	public static String getFile(String sdate){
		 String tSavePath = CcbConf.newInstance().getConf().getRootElement().getChildText("BatlocalDir");
		 System.out.println(tSavePath);
		 tSavePath+="SendToBankFile/"+sdate.substring(0, 4)+"/"+sdate.substring(4, 6)+"/"+sdate.substring(6, 8); 
		 mSavePath=tSavePath;
		 System.out.println(tSavePath);
		try {
			tSavePath = displayFile(tSavePath);
		} catch (MidplatException e) {
			e.printStackTrace();
		}
		System.out.println("�ļ�Ϊ"+tSavePath);
		 return tSavePath;
	}
	public static String displayFile(String path) throws MidplatException{
		File file=new File(path);
		String spath="";
		try {
		if(file.isFile()){
			System.out.println(file.getName());
			spath = file.getName();
		}else{
			System.out.println("<DIR>"+file.getName());
			File[] list=file.listFiles();   
			for (int i = 0; i <list.length; i++) {
				System.out.println(list[i].getName());
				if(i>0){
					spath+= "|"+list[i].getName();
				}else{
					spath+=list[i].getName();
				}
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
			throw new MidplatException("�ļ������ڻ���·��Ϊ�գ�");
		}
		System.out.println("�ļ�Ϊ::"+spath);
		return spath;
	}
	
	public static void main(String[] args) throws IOException {
//		String tdate=beforeDay(DateUtil.getCur10Date());
//		String tpath=getFolderName(tdate);
//		StringBuffer sbtr=new StringBuffer();
//		sbtr.append("123");
//		if(CreateFileFolder(tpath))
//			{
//			try {
//				insertFile(getFileName(), sbtr);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			}
		System.out.println(CcbConf.newInstance().getConf().getRootElement().getChildText("BankNo"));
		System.out.println(before8Day(DateUtil.getCur10Date()));
	}
}
