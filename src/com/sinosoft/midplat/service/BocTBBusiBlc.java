package com.sinosoft.midplat.service;

import java.io.File;
import org.jdom.Document;
import org.jdom.Element;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.FTPDealBL;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.ExeSQL;

public class BocTBBusiBlc extends ServiceImpl {
	public BocTBBusiBlc(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into BocTBBusiBlc.service()...");
		cInXmlDoc = pInXmlDoc; 
		JdomUtil.print(cInXmlDoc);
		
		
		try {
			cTranLogDB = insertTranLog(cInXmlDoc);
			
			String tSqlStr = new StringBuilder("select 1 from TranLog where RCode=").append(CodeDef.RCode_OK)
				.append(" and TranDate=").append(cTranLogDB.getTranDate())
				.append(" and FuncFlag=").append(cTranLogDB.getFuncFlag())
				.append(" and TranCom=").append(cTranLogDB.getTranCom())
				.append(" and NodeNo='").append(cTranLogDB.getNodeNo()).append('\'')
				.toString();
			ExeSQL tExeSQL = new ExeSQL();
			if ("1".equals(tExeSQL.getOneValue(tSqlStr))) {
				throw new MidplatException("�ѳɹ�����������ԥ���˱����˽��ף������ظ�������");
			} else if (tExeSQL.mErrors.needDealError()) {
				throw new MidplatException("��ѯ��ʷ������Ϣ�쳣��");
			} 
			
			//����ǰ�û��������ı�����Ϣ(ɨ�賬ʱ��)
			String tErrorStr = cInXmlDoc.getRootElement().getChildText(Error);
			if (null != tErrorStr) {
				throw new MidplatException(tErrorStr);
			}
			
			Element mRootEle = cInXmlDoc.getRootElement();
//			Element mHeadEle = (Element) mRootEle.getChild(Head).clone();
			Element mBodyEle = mRootEle.getChild(Body);   

			String sFileName = mBodyEle.getChildText("FileName");
			String sFtpIP = mBodyEle.getChildText("FtpIP");
			String sFtpPort = mBodyEle.getChildText("FtpPort");
			String sFtpUser = mBodyEle.getChildText("FtpUser");
			String sFtpPass = mBodyEle.getChildText("FtpPass");
			String sFtpFilePath = mBodyEle.getChildText("FtpFilePath");
			String sFilePath = mBodyEle.getChildText("FilePath");
			
			FTPDealBL tFTPDealBL = new FTPDealBL(sFtpIP, sFtpUser, sFtpPass, Integer.valueOf(sFtpPort));
			File file = new File(sFilePath+sFileName);

			if (!tFTPDealBL.ApacheFTPUploadFile(file, sFtpFilePath)) {

				throw new MidplatException("FTP�ϴ����� FileName = " + sFileName);
			}
			cOutXmlDoc = getSimpOutXml("1", "���׳ɹ�");

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
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));	//-1-δ���أ�0-���׳ɹ������أ�1-����ʧ�ܣ�����
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}
		
		cLogger.info("Out BocTBBusiBlc.service()!");
		return cOutXmlDoc;
	}
	
	public static Document getSimpOutXml(String pFlag, String pMessage)
	{
		Element mFlag = new Element("Flag");
		mFlag.addContent(pFlag);

		Element mDesc = new Element("Desc");
		mDesc.addContent(pMessage);

		Element mHeadEle = new Element("Head");
		mHeadEle.addContent(mFlag);
		mHeadEle.addContent(mDesc);

		Element mTranData = new Element("TranData");
		mTranData.addContent(mHeadEle);

		return new Document(mTranData);
	}
}
