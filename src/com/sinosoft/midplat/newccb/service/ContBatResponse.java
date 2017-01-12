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
		//�����ļ�����·��
		String tBatchFTPPaht4LIS = NewCcbConf.newInstance().getConf().getRootElement().getChildText("BatchFTPPaht4LIS");
		//���ؽ����ļ�·��
		String tLocalFilePathRcv = NewCcbConf.newInstance().getConf().getRootElement().getChildText("LocalFilePathRcv");
		
		//��׼���뱨����
		Element tBodyEle = pInXmlDoc.getRootElement().getChild(Body);
		//����������
		String tFileName = tBodyEle.getChildText("FileName");
		//����״̬
		String tBatFlag = tBodyEle.getChildText("BatFlag");
		//��ϸ�ܱ���
		String tNum = tBodyEle.getChildText("Num");
		//��ϸ�ܽ��
		String tSumAmt = tBodyEle.getChildText("SumAmt");
		//���������ļ�����
		tBodyEle.getChild("FileName").setText(tFileName+"_RESULT.xml");
		try { 
			// 1:��¼��־
			//��׼���뱨�Ĳ��뽻����־
			cTranLogDB = insertTranLog(pInXmlDoc);
			//���ñ���1[����������]
			cTranLogDB.setBak1(tFileName);
			//���ñ���2[����״̬]
			cTranLogDB.setBak2(tBatFlag);
			//���ñ���3[��ϸ�ܱ���]
			cTranLogDB.setBak3(tNum);
			//���ñ���4[��ϸ�ܽ��]
			cTranLogDB.setBak4(tSumAmt);
			//���׽��
			Element mFlagEle = new Element(Flag);
			//���׽������
			Element mDescEle = new Element(Desc);
			//δ�����к��ļ���
			if(!"00".equals(tBatFlag)){
				//���׽��[ʧ��]
				mFlagEle.setText("1");
				//01:δ�յ��˰�
				if("01".equals(tBatFlag)){
					mDescEle.setText("δ�յ��˰�");
				//02:������ϸ�ܱ�����ʵ����ϸ�������ܲ���
				}else if("02".equals(tBatFlag)){
					mDescEle.setText("������ϸ�ܱ�����ʵ����ϸ�������ܲ���");
				//03:������ϸ�ܽ����ʵ����ϸ�����ܲ���
				}else if("03".equals(tBatFlag)){
					mDescEle.setText("������ϸ�ܽ����ʵ����ϸ�����ܲ���");
				//04:����������������
				}else if("04".equals(tBatFlag)){
					mDescEle.setText("����������������");
				//05:����ϸ���ڸ����
				}else if("05".equals(tBatFlag)){
					mDescEle.setText("����ϸ���ڸ����");
				//06:���չ�˾�������������Ʋ������򷵻�������
				}else if("06".equals(tBatFlag)){
					mDescEle.setText("���չ�˾�������������Ʋ������򷵻�������");
				//07:����ϸ��Ϊ0
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
				//99:�����ڲ����������Ҫ�˹���ʵ���
				}*/else if("99".equals(tBatFlag)){
					mDescEle.setText("�����ڲ����������Ҫ�˹���ʵ���");
				}
				//��׼���뱨������������������Ϊ���������ļ�����
				tBodyEle.getChild("FileName").setText(tFileName+"_SOURCE.xml");
			//00:�ѵ����к��ļ���
			}else{
				//���׽��[�ɹ�]
				mFlagEle.setText("0");
				//���ý��׽������[�ѵ����к��ļ���]
				mDescEle.setText("�ѵ����к��ļ���");
			}
			//��׼���뱨������뽻�׽��
			tBodyEle.addContent(mFlagEle);
			//��׼���뱨������뽻�׽������
			tBodyEle.addContent(mDescEle);
			
			//�ļ�·��:���ؽ����ļ�·��/���������ļ�����_RESULT.XML
			cLogger.info("�ļ�·��:"+tLocalFilePathRcv+"/"+tFileName+"_RESULT.XML");
			//���ؽ����ļ�·���ļ�����
			File tFile = new File(tLocalFilePathRcv+"/"+tFileName+"_RESULT.XML");
			//�����ļ�����·���ļ�����
			File tMoveFile = new File(tBatchFTPPaht4LIS);
			//�ƶ��ļ�������
			if(!tMoveFile.exists()){
				//�����ƶ��ļ�
				tMoveFile.mkdirs();
			}
			//���ؽ����ļ�������[�����ļ�����·�����������ļ�����]
			if(!tFile.renameTo(new File(tBatchFTPPaht4LIS+tFileName+"_RESULT.xml"))){
				//�ƶ��ļ�ʧ�ܣ��׳��쳣
				throw new MidplatException("�ƶ��ļ�ʧ��"+tFileName+"_RESULT.xml");
			}
			//���ڻ��̵Ľ���ļ��洢��snd�ļ����У����Խ��еڶ���ת��
			//���ط����ļ�·��
			String sndLocal = NewCcbConf.newInstance().getConf().getRootElement().getChildText("LocalFilePathSnd");
			//�ڶ���ת���ļ�·��:���ط����ļ�·������������_RESULT.XML
			cLogger.info("�ڶ���ת���ļ�·��:"+sndLocal+tFileName+"_RESULT.XML");
			//���ؽ����ļ�·������������_RESULT.XML
		    tFile = new File(tLocalFilePathRcv+tFileName+"_RESULT.XML");
		    //start rename......[��ʼ������]
		    cLogger.info("start rename......");
		    //���ؽ����ļ�·�����ļ�
			if(tFile.isFile()){
				//������[�����ļ�����·������������_RESULT.xml]
				tFile.renameTo(new File(tBatchFTPPaht4LIS+tFileName+"_RESULT.xml"));
			}
			//end rename......[����������]
			cLogger.info("end rename......");
			
			//��׼���뱨�ĵ���WebServiceԭ�ӷ��񷵻ر�׼�������
			cOutXmlDoc = new CallWebsvcAtomSvc("109").call(pInXmlDoc);
			
			//��׼�������ͷ
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			//���׽��[ʧ��]
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {	//����ʧ��
				//�׳��쳣����׼�������ͷ���׽������
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
			//���ݽ��׽���ͽ��׽�����������ɼ򵥵ı�׼�������
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "���׳ɹ�");
			//���ý�����־[���׽��:ʧ��]
			cTranLogDB.setRCode(0);
			//���ý�����־[�������]
			cTranLogDB.setRText("�ѵ����к��ļ���");
		} catch (Exception ex) {
			//�������մ������̽���ʧ�ܣ�
			cLogger.error(cThisBusiConf.getChildText(name) + "����ʧ�ܣ�", ex);
			//���⿪��ָ��
			if (null != cTranLogDB) { // ������־ʧ��ʱcTranLogDB=null
				//���ý��׽��[�ɹ�]
				cTranLogDB.setRCode(1); // -1-δ���أ�0-���׳ɹ������أ�1-����ʧ�ܣ�����
				//���ý������[��ȡǰ150λ]
				cTranLogDB.setRText(NumberUtil.cutStrByByte(ex.getMessage(),
						150, MidplatConf.newInstance().getDBCharset()));
			}
			//���ݽ��׽���ͽ��׽�����������ɼ򵥵ı�׼�������
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "���׳ɹ�");
		}
		//�����ָ��
		if (null != cTranLogDB) { // ������־ʧ��ʱcTranLogDB=null
			long tCurMillis = System.currentTimeMillis();
			//���÷����ʱ[(s)��ǰʱ�������-��ʼʱ�������]
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			//����޸�����[��ǰʱ�������8λ����]
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			//����޸�ʱ��[��ǰʱ�������6λʱ��]
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			//������־����ʧ��
			if (!cTranLogDB.update()) {
				//������־��Ϣʧ�ܣ�
				cLogger.error("������־��Ϣʧ�ܣ�"
						+ cTranLogDB.mErrors.getFirstError());
			}
		}
		JdomUtil.print(cOutXmlDoc);
		//���ر�׼�������
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
