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
	 * ��������������ѯ�ı�׼������� 
	 */
	static String sdate=DateUtil.date10to8(DateUtil.getCur10Date());
	private final static Logger cLogger = Logger.getLogger(CancelBlcStdxml.class);
	public static Document call(Document pInXmlDoc) throws Exception {
		
		//�������ݸ���
		int count3 =0;
		//�������ݸ���
		int count4 =0;
		
		cLogger.info("Into CancelBlcStdxml.call()...");
		Element tOutHeadEle=pInXmlDoc.getRootElement().getChild(Head);
		Element Flag=new Element("Flag");
		Element Desc=new Element("Desc");
		Element BatIncome = new Element("BatIncome");//�����ļ�����
		Element BatPay = new Element("BatPay");//����
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
					System.out.println("���գ������ļ�Ϊ��");
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
				 * �ͺ��Ľ������ݽ����ġ����׻�������
				 */
				
				Document requestDoc  = new Document(pInXmlDoc.getRootElement().getChild(Head));
				Document responseDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_ContBatQuery).call(requestDoc);//������ѯ��WebSephere�ӿڵ�ID
				Element OutRootEle = responseDoc.getRootElement();
				Element OutHeadEle = OutRootEle.getChild(Head);
				Element tOutBodyEle = OutRootEle.getChild(Body);
				if (CodeDef.RCode_ERROR == Integer.parseInt(OutHeadEle.getChildText("Flag"))) {
					throw new MidplatException(OutHeadEle.getChildText("Desc"));
				}
				
				//���ո���
				String sFcount = tOutBodyEle.getChildText("Fcount");
				count3 = Integer.parseInt(sFcount);
				//��������
				String sScount = tOutBodyEle.getChildText("Scount");
				count4 = Integer.parseInt(sScount);
				
				
				if(count3 > 0){
					BatIncome.setText("-1");
					System.out.println("��ʱδ���ɴ������ݣ���ȵȣ�");
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
					System.out.println("��ʱδ���ɴ������ݣ���ȵȣ�");
				}else{
				BatPay.setText("0");
				}
			}else if(count2 > 0){
				BatPay.setText("1");
			}else {
				BatPay.setText("0");
			}
			Flag.setText("0");
			Desc.setText("������ѯ���׳ɹ���");
			
		} catch (Exception e) {
			e.printStackTrace();
			Flag.setText("1");
			Desc.setText("������ѯ����ʧ�ܣ�");
			throw new MidplatException(tOutHeadEle.getChildText("Desc"));
		} 
		JdomUtil.print(pInXmlDoc);
		return pInXmlDoc;
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
			throw new MidplatException("�ļ������ڻ���·��Ϊ�գ�");
		}
		System.out.println("�ļ�Ϊ::"+spath);
		return spath;
	}
	public static void main(String[] args) throws IOException {
		//ȡ���ļ��е����ݡ�
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
