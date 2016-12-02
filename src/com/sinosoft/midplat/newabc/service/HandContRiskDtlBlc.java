///�ֹ������������ϸ�ļ�-���д���������
package com.sinosoft.midplat.newabc.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newabc.format.HandContRiskDtlOutXsl;
import com.sinosoft.midplat.newabc.util.AbcFileTransUtil;
import com.sinosoft.midplat.service.ServiceImpl;

public class HandContRiskDtlBlc extends ServiceImpl {
	public HandContRiskDtlBlc(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into HandContBlc.service()...");
		cInXmlDoc = pInXmlDoc;
		JdomUtil.print(cInXmlDoc);
		try {
			cTranLogDB = insertTranLog(pInXmlDoc);
			String cTrandate = cInXmlDoc.getRootElement().getChild(Body).getChildText(TranDate);
			
			cLogger.info("start call lis service()...");
		    cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_NonRealTimeContRiskDtl).call(cInXmlDoc);
		    cLogger.info("end call lis service()...");
		    //������ı��ģ��������ֹ��������ϸ�ļ��ϴ�������
		    dealFile(cOutXmlDoc,cTrandate);
		    
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
	} 
		catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		
		if (null != cTranLogDB) {	//������־ʧ��ʱcTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}
		cLogger.info("Out NewContQuery.service()!");
		JdomUtil.print(cOutXmlDoc);
		return cOutXmlDoc;
	}

	private void dealFile(Document cOutXmlDoc, String cTrandate) throws Exception {
		cLogger.info("Into HandContRiskDtl.receive()..."+cTrandate);
		JdomUtil.print(cOutXmlDoc);
		JdomUtil.print(cOutXmlDoc);
		cOutXmlDoc= HandContRiskDtlOutXsl.newInstance().getCache().transform(cOutXmlDoc);
		String outHead = null;//ͷ��¼
		String serialNo="";//���
		String fileName = "";//�ļ�����
		String out ="";
		String outBody="";
	    int  maxNo = 100;//һ��txt������������ϸ��¼��  ���ԵĻ���ֻ��Ҫ�����ֵ��������
		//ͷ��¼����
		Element tRoot = cOutXmlDoc.getRootElement();
		Element tHead = tRoot.getChild("Head");
		System.out.println(tRoot+"           ===========      "+tHead);
		String tFlag = tHead.getChildText("Flag");//���׳ɹ���־
		String tComCode = "3103";//���չ�˾����
		String tFuncFlag = cThisBusiConf.getChildText("funcFlag");//���״���
		String tCount = null;//��ϸ��¼�ܱ���
		String tSumMoney = null;//�ܽ��
	    Element tBody = tRoot.getChild("Body");
	    List<Element> list  = tBody.getChildren("Detail");
    	tCount = Integer.toString(list.size());
    	tSumMoney="0000";
    	long sum=0;
    	String ttLocalDir = cThisBusiConf.getChildText("localDir");  	
	    cLogger.info("���ɷ����ļ�ͷ��¼"+outHead);
	    cLogger.info("���ɷ����ļ��ܼ�¼"+list.size());
	    if(list.size()!=0){
	    /*
		 * ͨ�������������ȡ�����������ļ�������
		 */
	    for (int i = 0; i <=list.size()/maxNo; i++) {
			if(i<10){
				serialNo ="0" + Integer.toString(i);
				fileName = "SRESULTKZ"+tComCode+"."+cTrandate;
			}else{
				serialNo = Integer.toString(i);
				fileName = "SRESULTKZ"+tComCode+"."+cTrandate;
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
		  	fileName = "SRESULTKZ"+tComCode+"."+cTrandate;
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
			cLogger.info("ֻ����ͷ�ļ�������δ�����˱�ֵ"+out);
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
        System.out.println("�ļ�����"+ttLocalDir+"/"+fileName);
		AbcFileTransUtil util = new AbcFileTransUtil(ttLocalDir,ttLocalDir);
		util.upLoadFile(fileName,"02");
		cLogger.info("Out HandContRiskDtl.receive()...");
	}
}
