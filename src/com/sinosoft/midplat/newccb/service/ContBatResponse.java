package com.sinosoft.midplat.newccb.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.service.ServiceImpl;

public class ContBatResponse extends ServiceImpl {

	public ContBatResponse(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document service(Document pInXmlDoc) throws Exception {

		cLogger.info("Into ContBatRequResult()...");
		long mStartMillis = System.currentTimeMillis();
		JdomUtil.print(pInXmlDoc);
		String tBatchFTPPaht4LIS = NewCcbConf.newInstance().getConf().getRootElement().getChildText("BatchFTPPaht4LIS");
		String tLocalFilePathRcv = NewCcbConf.newInstance().getConf().getRootElement().getChildText("LocalFilePathRcv");
		
		Element tBodyEle = pInXmlDoc.getRootElement().getChild(Body);
		String tFileName = tBodyEle.getChildText("FileName");
		String tBatFlag = tBodyEle.getChildText("BatFlag");
		String tNum = tBodyEle.getChildText("Num");
		String tSumAmt = tBodyEle.getChildText("SumAmt");
		tBodyEle.getChild("FileName").setText(tFileName+"_RESULT.xml");
		try { 
			// 1:��¼��־
			cTranLogDB = insertTranLog(pInXmlDoc);
			cTranLogDB.setBak1(tFileName);
			cTranLogDB.setBak2(tBatFlag);
			cTranLogDB.setBak3(tNum);
			cTranLogDB.setBak4(tSumAmt);
			
			Element mFlagEle = new Element(Flag);
			Element mDescEle = new Element(Desc);
			if(!"00".equals(tBatFlag)){
				mFlagEle.setText("1");
				if("01".equals(tBatFlag)){
					mDescEle.setText("δ�յ��˰�");
				}else if("02".equals(tBatFlag)){
					mDescEle.setText("������ϸ�ܱ�����ʵ����ϸ�������ܲ���");
				}else if("03".equals(tBatFlag)){
					mDescEle.setText("������ϸ�ܽ����ʵ����ϸ�����ܲ���");
				}else if("04".equals(tBatFlag)){
					mDescEle.setText("����������������");
				}else if("05".equals(tBatFlag)){
					mDescEle.setText("����ϸ���ڸ����");
				}else if("06".equals(tBatFlag)){
					mDescEle.setText("���չ�˾�������������Ʋ������򷵻�������");
				}else if("07".equals(tBatFlag)){
					mDescEle.setText("����ϸ��Ϊ0");
				}else if("08".equals(tBatFlag)){
					mDescEle.setText("������У����ȷ");
				}else if("09".equals(tBatFlag)){
					mDescEle.setText("������δ����");
				}else if("10".equals(tBatFlag)){
					mDescEle.setText("������������");
				}else if("11".equals(tBatFlag)){
					mDescEle.setText("�����������ظ��ύ,�ظ�������Ϊx");
				}else if("12".equals(tBatFlag)){
					mDescEle.setText("������������Ӧ�ļ�ʧ�� ");
				}else if("13".equals(tBatFlag)){
					mDescEle.setText("���չ�˾�˻����㣬����ʧ��");
				}else if("14".equals(tBatFlag)){
					mDescEle.setText("������ϸ����ظ�");
				}else if("15".equals(tBatFlag)){
					mDescEle.setText("�������ļ���ʽ����");
				}/*else if("16".equals(tBatFlag)){
					mDescEle.setText("����ʧ��");
				}*/else if("99".equals(tBatFlag)){
					mDescEle.setText("�����ڲ����������Ҫ�˹���ʵ���");
				}
				tBodyEle.getChild("FileName").setText(tFileName+"_SOURCE.xml");
			}else{
				mFlagEle.setText("0");
				mDescEle.setText("�ѵ����к��ļ���");
			}
			tBodyEle.addContent(mFlagEle);
			tBodyEle.addContent(mDescEle);
			
			cLogger.info("�ļ�·��:"+tLocalFilePathRcv+"/"+tFileName+"_RESULT.XML");
			File tFile = new File(tLocalFilePathRcv+"/"+tFileName+"_RESULT.XML");
			File tMoveFile = new File(tBatchFTPPaht4LIS);
			if(!tMoveFile.exists()){
				tMoveFile.mkdirs();
			}
			if(!tFile.renameTo(new File(tBatchFTPPaht4LIS+tFileName+"_RESULT.xml"))){
				throw new MidplatException("�ƶ��ļ�ʧ��"+tFileName+"_RESULT.xml");
			}
			//���ڻ��̵Ľ���ļ��洢��snd�ļ����У����Խ��еڶ���ת��
			String sndLocal = NewCcbConf.newInstance().getConf().getRootElement().getChildText("LocalFilePathSnd");
			cLogger.info("�ڶ���ת���ļ�·��:"+sndLocal+tFileName+"_RESULT.XML");
		    tFile = new File(tLocalFilePathRcv+tFileName+"_RESULT.XML");
		    cLogger.info("start rename......");
			if(tFile.isFile()){
				tFile.renameTo(new File(tBatchFTPPaht4LIS+tFileName+"_RESULT.xml"));
			}
			cLogger.info("end rename......");
			
			cOutXmlDoc = new CallWebsvcAtomSvc("109").call(pInXmlDoc);
			
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {	//����ʧ��
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "���׳ɹ�");
			cTranLogDB.setRCode(0);
			cTranLogDB.setRText("�ѵ����к��ļ���");
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name) + "����ʧ�ܣ�", ex);

			if (null != cTranLogDB) { // ������־ʧ��ʱcTranLogDB=null
				cTranLogDB.setRCode(1); // -1-δ���أ�0-���׳ɹ������أ�1-����ʧ�ܣ�����
				cTranLogDB.setRText(NumberUtil.cutStrByByte(ex.getMessage(),
						150, MidplatConf.newInstance().getDBCharset()));
			}
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "���׳ɹ�");
		}
		if (null != cTranLogDB) { // ������־ʧ��ʱcTranLogDB=null
			long tCurMillis = System.currentTimeMillis();
			
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�"
						+ cTranLogDB.mErrors.getFirstError());
			}
		}
		JdomUtil.print(cOutXmlDoc);
		return cOutXmlDoc;
	}

	public static void main(String[] args) throws Exception {

//		Element cThisBusiConf = (Element)XPath.selectSingleNode(CcbConf.newInstance().getConf().getRootElement(), (new StringBuilder("business[funcFlag='")).append("803").append("']").toString());
//		ContBatResponse batch = new ContBatResponse(cThisBusiConf);
//		String mFilePath = "D:\\MyEclipseWorkSpace\\LIAN\\src\\test\\doc\\ccb\\803_inSvc.xml";
//		InputStream mIs = new FileInputStream(mFilePath);
//		Document pInXmlDoc = JdomUtil.build(mIs);
//		JdomUtil.print(batch.service(pInXmlDoc));
		
	
	}
}
