package com.sinosoft.midplat.ccb.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.ccb.CcbConf;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ExeSQL;

public class ContBatQuery implements XmlTag{
	/**
	 * mod by zhoupan 20120726
	 * 用来生成批量查询的标准输出报文 
	 */
	static String sdate=DateUtil.date10to8(DateUtil.getCur10Date());
	private final static Logger cLogger = Logger.getLogger(CancelBlcStdxml.class);
	public static Document call(Document pInXmlDoc) throws Exception {
		
		//代收数据个数
		int count3 =0;
		//代付数据个数
		int count4 =0;
		
		cLogger.info("Into CancelBlcStdxml.call()...");
		Element tOutHeadEle=pInXmlDoc.getRootElement().getChild(Head);
		Element Flag=new Element("Flag");
		Element Desc=new Element("Desc");
		Element BatIncome = new Element("BatIncome");//代收文件个数
		Element BatPay = new Element("BatPay");//代付
		try {
			Element tRootEle=pInXmlDoc.getRootElement();
			sdate = tRootEle.getChild(Head).getChildText(TranDate);
			tRootEle.getChild(Body).addContent(BatIncome);
			tRootEle.getChild(Body).addContent(BatPay);
			tOutHeadEle.addContent(Flag);
			tOutHeadEle.addContent(Desc);
			BatIncome.setText("-1");
			BatPay.setText("-1");
			String[] mSub =getFile(sdate).split("\\|", -1);
			int count1 = 0;
			int count2 = 0;
			for (int i = 0; i < mSub.length; i++) {
				System.out.println("mSub[i]:"+mSub[i]);
				if(mSub[i].equals("")){
					System.out.println("代收，代扣文件为空");
				}else{
					if(mSub[i].substring(5, 6).equals("F")){
						count1++;
					}else if(mSub[i].substring(5, 6).equals("S")){
						count2++;
					}
				}
			}
			if(count1 == 0){
//				String sql1="select count(*) from lis.lybanklog a where a.bankcode = '0301'" +
//						" and a.logtype='F' and a.makedate =( Select to_date('"+sdate+"','YYYYMMDD') from dual)";
//				int count3 = Integer.parseInt(new ExeSQL().getOneValue(sql1));
				
				/*
				 * 和核心进行数据交互的”交易化“处理，
				 */
				
				Document requestDoc  = new Document(pInXmlDoc.getRootElement().getChild(Head));
				Document responseDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContBatQuery).call(requestDoc);//批量查询的WebSephere接口的ID
				Element OutRootEle = responseDoc.getRootElement();
				Element OutHeadEle = OutRootEle.getChild(Head);
				Element tOutBodyEle = OutRootEle.getChild(Body);
				if (CodeDef.RCode_ERROR == Integer.parseInt(OutHeadEle.getChildText("Flag"))) {
					throw new MidplatException(OutHeadEle.getChildText("Desc"));
				}
				
				//代收个数
				String sFcount = tOutBodyEle.getChildText("Fcount");
				count3 = Integer.parseInt(sFcount);
				//代付个数
				String sScount = tOutBodyEle.getChildText("Scount");
				count4 = Integer.parseInt(sScount);
				
				
				if(count3 > 0){
					BatIncome.setText("-1");
					System.out.println("暂时未生成代收数据，请等等！");
				}else{
					BatIncome.setText("0");
				}
			}else if(count1 > 0){
				BatIncome.setText("1");
			}else {
				BatIncome.setText("0");
			}
			if(count2 == 0){
//				String sql2="select count(*) from lis.lybanklog a where a.bankcode = '0301' " +
//						"and a.logtype='S' and a.makedate =( Select to_date('"+sdate+"','YYYYMMDD') from dual)";
//				int count4= Integer.parseInt(new ExeSQL().getOneValue(sql2));
								
				if(count4 > 0){
					BatPay.setText("-1");
					System.out.println("暂时未生成代扣数据，请等等！");
				}else{
				BatPay.setText("0");
				}
			}else if(count2 > 0){
				BatPay.setText("1");
			}else {
				BatPay.setText("0");
			}
			Flag.setText("0");
			Desc.setText("批量查询交易成功！");
			
		} catch (Exception e) {
			e.printStackTrace();
			Flag.setText("1");
			Desc.setText("批量查询交易失败！");
			throw new MidplatException(tOutHeadEle.getChildText("Desc"));
		} 
		JdomUtil.print(pInXmlDoc);
		return pInXmlDoc;
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
//			String []filelist=file.list();
//			File mfile = new File(filelist[1]);
//			if(mfile.exists()){
//				String pathName=file.getAbsolutePath();
//				for (int i = 0; i < filelist.length; i++) {
//					spath = displayFile(pathName+File.separator+filelist[i]);
//				}
//			}
//			else{
//				spath="";
//			}
			File[] list=file.listFiles();   
			for (int i = 0; i <list.length; i++) {
				String pathName=file.getAbsolutePath();
				System.out.println(list[i].getName());
				if(i>0){
//					spath+="|"+pathName+File.separator+list[i].getName();
					spath+= "|"+list[i].getName();
				}else{
//					spath+=pathName+File.separator+list[i].getName();
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
		//取得文件中的内容。
		String file=getFile(sdate);
		System.out.println("::::"+file);
		InputStream mIns = null;
		String[] mSub =getFile(sdate).split("\\|", -1);
		for (int i = 0; i < mSub.length; i++) {
			mIns = new FileInputStream(mSub[i]);
			BufferedReader mBufReader = new BufferedReader(
					new InputStreamReader(mIns, "GBK"));
			String br = "";
			while((br=mBufReader.readLine())!=null){
			String[] mSubMsgs = br.split("\\|", -1);
			for (int j = 0; j < mSubMsgs.length; j++) {
				System.out.println("mSubMsgs:"+mSubMsgs[j]);
			}
			}
		}		
	}
}
