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

public class ContBatRequest implements XmlTag{
	/**
	 * mod by zhoupan 20120726
	 * 用来生成的标准输出报文 
	 */
	static String sdate=DateUtil.date10to8(DateUtil.getCur10Date());
	private final static Logger cLogger = Logger.getLogger(ContBatRequest.class);
	private static String mFlag="";
	private static String mSavePath="";
	public static Document call(Document pInXmlDoc) throws Exception {
		cLogger.info("Into CancelBlcStdxml.call()...");
		Element tOutHeadEle=pInXmlDoc.getRootElement().getChild(Head);
		Element sBody = pInXmlDoc.getRootElement().getChild(Body);
		String sFlag = sBody.getChildText("DealType");
		Element Flag=new Element("Flag");
		Element Desc=new Element("Desc");
		Element TotalNum=new Element("TotalNum");
		Element TotalSum=new Element("TotalSum");
		sBody.addContent(TotalNum);
		sBody.addContent(TotalSum);
		try {
			Element tRootEle=pInXmlDoc.getRootElement();
			sdate = tRootEle.getChild(Head).getChildText(TranDate);
			tOutHeadEle.addContent(Flag);
			tOutHeadEle.addContent(Desc);
			String[] mSub =getFile(sdate).split("\\|", -1);
			int CTotalNum = 0;
			double CTotalSum = 0;
			for (int i = 0; i < mSub.length; i++) {
				System.out.println("mSub[i]:"+mSub[i]);
				if(mSub[i].equals("")){
					System.out.println("代收，代扣文件为空");
				}else{
					if(mSub[i].substring(5, 6).equals(sFlag)){
						InputStream mIns = null;
						mIns = new FileInputStream(mSavePath+File.separator+mSub[i]);
						BufferedReader mBufReader = new BufferedReader(
								new InputStreamReader(mIns, "GBK"));
						String br="";
						int CountLine = 0;
						while((br=mBufReader.readLine())!=null){
						String[] mSubMsgs = br.split("\\|", -1);
						for (int j = 0; j < mSubMsgs.length; j++) {
							if(mSubMsgs[j].equals("")||(mSubMsgs[j]==null)){
								continue;
							}else{
								CountLine++;
								if(CountLine==1){//第一行为汇总信息
									cLogger.info("汇总信息："+mSubMsgs[j]);
									String[] mMsgs = mSubMsgs[j].split(",", -1);
									mFlag = mMsgs[0];
									CTotalNum+=Integer.parseInt(mMsgs[3]);
									CTotalSum+=Double.parseDouble(NumberUtil.fenToYuan(mMsgs[4]));
								}else{//以下为正文。
									String[] mMsgs = mSubMsgs[j].split(",", -1);
									Element Detail=new Element("Detail");
									sBody.addContent(Detail);
									Element AccName=new Element("AccName");
									Element AccNo=new Element("AccNo");
									Element PayCode=new Element("PayCode");
									Element OperType=new Element("OperType");
									Element SerialNo=new Element("SerialNo");
									Element PayMoney=new Element("PayMoney");
									Element PolNo=new Element("PolNo");
									Detail.addContent(AccName);
									Detail.addContent(AccNo);
									Detail.addContent(PayCode);
									Detail.addContent(OperType);
									Detail.addContent(SerialNo);
									Detail.addContent(PayMoney);
									Detail.addContent(PolNo);
									AccName.setText(mMsgs[5]);
									AccNo.setText(mMsgs[4]);
									PayCode.setText(mMsgs[0]);
									if(mFlag.equals("S")){
										OperType.setText("98");
									}else if(mFlag.equals("F")){
										OperType.setText("99");
									}else {
										OperType.setText("00");
									}
									SerialNo.setText("");
									PayMoney.setText(NumberUtil.fenToYuan(mMsgs[10]));
									PolNo.setText("");
								}
							}
							System.out.println("mSubMsgs:"+mSubMsgs[j]);
						}
					}
				}
			}
			}
			TotalNum.setText(String.valueOf(CTotalNum));
			TotalSum.setText(String.valueOf(CTotalSum));
			Flag.setText("0");
			Desc.setText("批量取盘交易成功！");
			
		} catch (Exception e) {
			e.printStackTrace();
			Flag.setText("1");
			if(tOutHeadEle.getChildText("Desc").equals("")){
				Desc.setText("批量取盘交易失败！");
			}else{
				Desc.setText(tOutHeadEle.getChildText("Desc"));
			}
			throw new MidplatException(tOutHeadEle.getChildText("Desc"));
		} 
		JdomUtil.print(pInXmlDoc);
		cLogger.info("out CancelBlcStdxml.call()...");
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
		//取得文件中的内容。
		String file=getFile(sdate);
		System.out.println("::::"+file);
		InputStream mIns = null;
		String[] mSub =getFile(sdate).split("\\|", -1);
		for (int i = 0; i < mSub.length; i++) {
			mIns = new FileInputStream(mSub[i]);
			BufferedReader mBufReader = new BufferedReader(
					new InputStreamReader(mIns, "GBK"));
			String br="";
			while((br=mBufReader.readLine())!=null){
			String[] mSubMsgs = br.split("\\|", -1);
			for (int j = 0; j < mSubMsgs.length; j++) {
				System.out.println("mSubMsgs:"+mSubMsgs[j]);
			}
			}
		}
		
	}
}
