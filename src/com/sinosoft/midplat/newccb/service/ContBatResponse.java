package com.sinosoft.midplat.newccb.service;

import org.jdom.Document;
import org.jdom.Element;

import cn.ccb.secapi.SecAPI;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.service.ServiceImpl;

public class ContBatResponse extends ServiceImpl {
    private String tTranCom=null;
	public ContBatResponse(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document service(Document pInXmlDoc) throws Exception {
		cLogger.info("Into ContBatRequResult()...");
		Element tHeadEle = pInXmlDoc.getRootElement().getChild(Head);
		String tranNo=tHeadEle.getChildText(TranNo);
		tTranCom=tHeadEle.getChildText("TranCom");
		long mStartMillis = System.currentTimeMillis();
		cLogger.info(JdomUtil.toStringFmt(pInXmlDoc));
		//���л����ļ������ļ�·��
		String localDir=this.cThisBusiConf.getChildText("LocalDir");
		String localID=pInXmlDoc.getRootElement().getChild("Head").getChildText("LocalID");
		String remoteID=pInXmlDoc.getRootElement().getChild("Head").getChildText("RemoteID");
		cLogger.info("���ж˰�ȫ�ڵ��:"+remoteID);
		cLogger.info("���չ�˾�˰�ȫ�ڵ��:"+localID);
		String filePath=this.cThisBusiConf.getChildText("ccbLocalDir");;//���������ļ�·��
		Element tBodyEle = pInXmlDoc.getRootElement().getChild(Body);
		String tFileName = tBodyEle.getChildText("FileName");
	    cLogger.info("���л����ļ�·��Ϊ:"+filePath+tFileName);
		int tBatFlag = Integer.parseInt(tBodyEle.getChildText("BatFlag"));
		String tNum = tBodyEle.getChildText("Num");
		String tSumAmt = tBodyEle.getChildText("SumAmt");
		try { 
			// 1:��¼��־
			cTranLogDB = insertTranLog(pInXmlDoc);
			cTranLogDB.setBak1(tFileName);
			cTranLogDB.setBak2(tBodyEle.getChildText("BatFlag"));
			cTranLogDB.setBak3(tNum);
			cTranLogDB.setBak4(tSumAmt);
			
			String descStr=null;
			if(!(tBatFlag==0)&&!(tBatFlag==16)){
				//00:��������ɹ���16����������ʧ��
				switch (tBatFlag) {
				  case 1:descStr="δ�յ��˰�";break;
				  case 2:descStr="������ϸ�ܱ�����ʵ����ϸ�������ܲ���";break;
				  case 3:descStr="������ϸ�ܽ����ʵ����ϸ�����ܲ���";break;
				  case 4:descStr="����������������";break;
				  case 5:descStr="����ϸ���ڸ����";break;
				  case 6:descStr="���չ�˾�������������Ʋ������򷵻�������";break;
				  case 7:descStr="����ϸ��Ϊ0";break;
				  case 8:descStr="������У����ȷ";break;
				  case 9:descStr="������δ����";break;
				  case 10:descStr="������������";break;
				  case 11:descStr="�����������ظ��ύ,�ظ�������Ϊx";break;
				  case 12:descStr="������������Ӧ�ļ�ʧ��";break;
				  case 13:descStr="���չ�˾�˻����㣬����ʧ��";break;
				  case 14:descStr="������ϸ����ظ�";break;
				  case 15:descStr="�������ļ���ʽ����";break;
				  case 99:descStr="�����ڲ����������Ҫ�˹���ʵ���";break;
			    }
				throw new MidplatException(descStr);
			}
			cLogger.info("�����ļ����localID:"+localID+",remoteID:"+remoteID +",����ǰ�ļ�:"+filePath+tFileName
				         +",���ܺ��ļ�:"+localDir+tFileName);
		    //�����������̽����ļ�
			SecAPI.fileUnEnvelop(localID, remoteID,filePath+tFileName,localDir+tFileName);
			//�����߳� �������������ļ����ɺ���zip�ļ��ϴ�FTP������
			BackFileThread backFileThread=new BackFileThread(localDir+tFileName,tTranCom,tranNo);
			backFileThread.start();
			
//			BackFileThread backFileThread=new BackFileThread("C:\\Users\\anico\\Desktop\\unencrypt\\AL01100792017022701_RESULT.XML",tTranCom,tranNo);
//			backFileThread.start();
		//	String fileName=generateCoreZipFile(localDir+tFileName);
        // 	String fileName=generateCoreZipFile("C:\\Users\\anico\\Desktop\\unencrypt\\AL01100792017022701_RESULT.XML");
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "���׳ɹ�");
			cTranLogDB.setRCode(0);
			cTranLogDB.setRText("�����ļ����ճɹ�:"+filePath+tFileName);
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name) + "����ʧ�ܣ�", ex);
			if (null != cTranLogDB) { 
				cTranLogDB.setRCode(1); 
				cTranLogDB.setRText(ex.getMessage());
			}
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "���׳ɹ�");
		}
		if (null != cTranLogDB) { // ������־ʧ��ʱcTranLogDB=null
			long tCurMillis = System.currentTimeMillis();
			
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�"+ cTranLogDB.mErrors.getFirstError());
			}
		}
		cLogger.info(JdomUtil.toStringFmt(cOutXmlDoc));
		return cOutXmlDoc;
	}
	public static void main(String[] args) throws Exception {

//     FileInputStream in=new FileInputStream(new File("C:\\Users\\anico\\Desktop\\���в��Ա���\\CCB���ո��ļ�20170111\\CCB���ո��ļ�20170111\\AL03100192017011101_RESULT.XML"));
//     Element doc=JdomUtil.build(in,"UTF-8").getRootElement();
//     //System.out.println(JdomUtil.toStringFmt(doc));
//     XPath tXPath = XPath.newInstance("Detail_List/Detail[Cst_AccNo='1216129980110291838']");
//     Element e=(Element) tXPath.selectSingleNode(doc);
//	 System.out.println(JdomUtil.toStringFmt(e));
//		String str="232493u,";
//		str=str.substring(0,str.lastIndexOf(","));
//		System.out.println(str);
	}
}
