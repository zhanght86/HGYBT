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
	 * 用来生成的标准输出报文 
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
				throw new MidplatException("送盘文件名错误！");
			}
			List<Element> DetailList=sBody.getChildren("Detail");
			for (int i = 0; i < DetailList.size(); i++) {
				Element Detail=DetailList.get(i);
				String sPayCode=Detail.getChildText("PayCode");//保险公司方明细序号
				String sAccName=Detail.getChildText(AccName);//账号名
				String sAccNo=Detail.getChildText(AccNo);//账号
				String sPayMoney=Detail.getChildText("PayMoney");//金额
				String sBankSuccFlag=Detail.getChildText("BankSuccFlag");//成功标志
				String sReason=Detail.getChildText("Reason");//成功描述
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
						throw new MidplatException("送盘文件名错误！");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				}
			cLogger.info(sbstr.toString());
			Flag.setText("0");
			Desc.setText("批量置盘交易成功！");
			
		} catch (Exception e) {
			e.printStackTrace();
			Flag.setText("1");
			if(tOutHeadEle.getChildText("Desc").equals("")){
				Desc.setText("批量置盘交易失败！");
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
	 * 1计算前一天的日期 --10位  mod by zhoupan 20120728
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
	 * 1计算前一天的日期 --8位  mod by zhoupan 20120728
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
	 * 2获取文件路径mod by zhoupan 20120728
	 * @return
	 */
	public static String getFolderName(String sdate){
		String spath =  CcbConf.newInstance().getConf().getRootElement().getChildText("BatlocalDir");
		spath+="ReturnFromBankFile"+File.separator+sdate.substring(0, 4)+File.separator+sdate.substring(5, 7)+File.separator+sdate.substring(8, 10); 
		return spath;
		
	}
	/**
	 * 3创建文件夹
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
	 * 4.1获取代付文件名路径 mod by zhoupan 20120728
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
	 * 4.2获取代收文件名路径 mod by zhoupan 20120728
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
	 * 5创建文件
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
			bw.write(ttFileName.toString());      //为保单信息赋值				
			bw.newLine();
			bw.flush();
			bw.close();
			cLogger.info("文件书写完毕，已保存到本地:"+path);	
		}
		catch (Exception e) {
	deleteFile(path);
	cLogger.fatal("文件书写失败:", e);
	e.printStackTrace();			
}
cLogger.info("保存文件结束！");
}
	
	public static void checkFile(String path){
		 File tFile = new File(path);
		   if(tFile.exists()){ //判断文件是否存在
		    if(tFile.isFile()){            //判断是否是文件
		    	System.out.println("文件存在，需要删除已存在的文件!");
		     if(!tFile.delete()){
		    	System.out.println(tFile.getPath()+"删除文件失败");
		     }                      //delete()方法 你应该知道 是删除的意思;
		    }
		   }else{
			   System.out.println("文件不存在，可以创建！"+'\n'); 
		   } 
	}
	public static void deleteFile(String path){
		   File tFile = new File(path);
		   if(tFile.exists()){ //判断文件是否存在
		    if(tFile.isFile()){            //判断是否是文件
		    	System.out.println("删除文件!");
		     if(!tFile.delete()){
		    	System.out.println(tFile.getPath()+"删除文件失败");
		     }                      //delete()方法 你应该知道 是删除的意思;
		    }
		   }else{
			   System.out.println("所删除的文件不存在！"+'\n'); 
		   } 
		}  
	/**
	 * 代收文件名
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
		System.out.println("文件为"+tSavePath);
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
			throw new MidplatException("文件不存在或者路径为空！");
		}
		System.out.println("文件为::"+spath);
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
