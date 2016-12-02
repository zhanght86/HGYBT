/**
 * ����״̬������������ļ�������
 */

package com.sinosoft.midplat.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.FTPDealBL;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.ExeSQL;

public class ToIcbcStateChangeBlc extends ServiceImpl {
	public ToIcbcStateChangeBlc(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	private ByteArrayInputStream enStream;
	
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into ToIcbcStateChangeBlc.service()...");
		cInXmlDoc = pInXmlDoc; 
		JdomUtil.print(cInXmlDoc);
		cInXmlDoc.getRootElement().getChild("Head").getChild("TranNo").setText("202"+DateUtil.getCur8Date()+DateUtil.getCur6Time());
		
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
				throw new MidplatException("�ѳɹ���������״̬����ش����ף������ظ�������");
			} else if (tExeSQL.mErrors.needDealError()) {
				throw new MidplatException("��ѯ��ʷ������Ϣ�쳣��");
			} 
			
			//����ǰ�û��������ı�����Ϣ(ɨ�賬ʱ��)
			String tErrorStr = cInXmlDoc.getRootElement().getChildText(Error);
			if (null != tErrorStr) {
				throw new MidplatException(tErrorStr);
			}
			
			Element mRootEle = cInXmlDoc.getRootElement();
			Element mBodyEle = mRootEle.getChild(Body);   

			String sFileName = mBodyEle.getChildText("FileName");
			String sFilePath = mBodyEle.getChildText("FilePath");
			
			InputStream mIs = new FileInputStream(sFilePath+sFileName);	
			cLogger.info("�ļ�����·�����ļ�����"+sFilePath+sFileName);
			BufferedReader mBufReader = new BufferedReader(
					new InputStreamReader(mIs, "GBK"));
			int  n = sFilePath.lastIndexOf("/");
			String newFilePath = sFilePath.substring(0, n-3);//��ȥfss/ Ȼ�󱣴�����һ��
			FileOutputStream DXFiles_fos= new FileOutputStream(newFilePath+sFileName);
			for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
				cLogger.info(tLineMsg);
				//����ֱ������
				tLineMsg = tLineMsg.trim();
				if ("".equals(tLineMsg)) {
					cLogger.warn("���У�ֱ��������������һ����");
					continue;
				}
				String[] tSubMsgs = tLineMsg.split("\\|", -1);
				String msgprtno = tSubMsgs[5];
				String sql1 = "select nodeno from tranlog where proposalprtno = '"+msgprtno+"' and trancom = '1'";
				ExeSQL texesql = new ExeSQL();
				if(!"".equals(texesql.getOneValue(sql1))){
					String tnodeno = texesql.getOneValue(sql1).substring(0, 5);
					tSubMsgs[4]=tnodeno;
				}				
				String t = "";
				for(int i =0;i<tSubMsgs.length-1;i++){
					t+=tSubMsgs[i]+"|";
				}
				t+="\r\n";
				cLogger.info("neirong"+t);
				DXFiles_fos.write(t.getBytes());
			}
			mBufReader.close();
			DXFiles_fos.flush();
			DXFiles_fos.close();	
			cInXmlDoc = authority(cInXmlDoc);
			//�����ļ�
			cLogger.info("��ʼ����!!!");
			UpLoadEncryFile(newFilePath,sFileName);
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
		
		cLogger.info("Out ToIcbcStateChangeBlc.service()!");
		return cOutXmlDoc;
	}

	private Document authority(Document mInXmlDoc) throws MidplatException{
		
  
		Element mRootEle = mInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head);
		
		Element mAgentCom = new Element("AgentCom");
		mHeadEle.addContent(mAgentCom);
		String sNodeNo = (String)mHeadEle.getChildTextTrim("NodeNo");
		String sTranCom =  (String)mHeadEle.getChildTextTrim("TranCom");
		 
		cLogger.info("ͨ������,����,����Ų�ѯ���������,����ӣ�");
		String tSqlStr2 = new StringBuilder("select AgentCom from NodeMap where TranCom='"+sTranCom).append('\'')
			.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCom = new ExeSQL().getOneValue(tSqlStr2);

		cLogger.info("authority-->"+sAgentCom);

		if ((""==sAgentCom)||(sAgentCom==null)){ 
			throw new MidplatException("�����㲻���ڣ���ȷ�ϣ�");
		}   
		mAgentCom.setText(sAgentCom);

		return mInXmlDoc;
		
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
	
	private void UpLoadEncryFile(String HomeDir,String mFileName) {
		cLogger.info("In ToIcbcContState.JIAMI...");
		// ����
		try {
			int x = 0;
			FileInputStream in;
			in = new FileInputStream(HomeDir+"/"+mFileName);

			ByteArrayOutputStream ins = new ByteArrayOutputStream();
			while ((x = in.read()) != -1) {
				ins.write(x);
			}
			byte[] bPack = ins.toByteArray();
			byte[] bt1 = new byte[1];
			byte[] mEncryptKey = getEncrtptKry();
			if (mEncryptKey == null) {
				cLogger.error("��Կ��ȡʧ��");
			}
			bPack = encode(bPack, mEncryptKey);
			enStream = new ByteArrayInputStream(bPack, 0, bPack.length);
			String newFileName = mFileName + ".des";
			FileOutputStream FHFiles_fos= new FileOutputStream(HomeDir+newFileName);  
			while (enStream.read(bt1) != -1) {
				FHFiles_fos.write(bt1);
			}
			ins.close();
			in.close();
			FHFiles_fos.close();
			String newName = newFileName;  
			this.cLogger.info("FTP�ϴ�:" + newName);
			cLogger.info("Out ToIcbcContState.JIAMI...");
			cLogger.info("In ToIcbcContState.SHANGCHUAN...");
			//ͨ�����������ļ�icbc.xml�е�ftp��Ϣ  �ϴ�������
			String sFtpIP = cThisBusiConf.getChildText("FtpIP");
			String sFtpPort = cThisBusiConf.getChildText("FtpPort");
			String sFtpUser = cThisBusiConf.getChildText("FtpUser");
			String sFtpPass = cThisBusiConf.getChildText("FtpPass");
			String sFtpFilePath = cThisBusiConf.getChildText("FtpFilePath");//�ϴ�·��
			cLogger.info("1111111...");
			FTPDealBL tFTPDealBL = new FTPDealBL(sFtpIP, sFtpUser,
					sFtpPass, Integer.valueOf(sFtpPort));
			cLogger.info("2222222...");
			File file = new File(HomeDir+newName);
			cLogger.info("3333333...");
			cLogger.info(HomeDir+newName);
			if (!tFTPDealBL.ApacheFTPUploadFile(file, sFtpFilePath)) {
				cLogger.info("4444444...");
				throw new MidplatException("FTP�ϴ����� FileName = " + newName);
			} 
			cLogger.info("Out ToIcbcContState.SHANGCHUAN...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static byte[] encode(byte[] input2, byte[] key2) throws Exception {
		SecretKey deskey2 = new javax.crypto.spec.SecretKeySpec(key2, "DES");
		Cipher c2 = Cipher.getInstance("DES");
		c2.init(Cipher.ENCRYPT_MODE, deskey2);
		byte[] clearByte2 = c2.doFinal(input2);
		return clearByte2;
	}

	private byte[] getEncrtptKry() {
    	try {
    		byte[] bEncryptKey = new byte[16];
			String strPath = SysInfo.cHome+"key/";
			cLogger.info("��Կ·��Ϊ��" + strPath);
			if (!strPath.endsWith("/") && !strPath.endsWith("\\")) {
				strPath += "/";
			}
			File fileEncryptKey = new File(strPath + "icbcKey.dat");
			
			FileInputStream fis = null;
			fis = new FileInputStream(fileEncryptKey);
			fis.read(bEncryptKey);
			fis.close();
			cLogger.info("��ԿΪ��icbcKey.dat=" + new String(bEncryptKey));
			bEncryptKey = toByteArray(new String(bEncryptKey));
			cLogger.info("��ԿΪ��" + bEncryptKey);
			cLogger.info("���ܽ���!!!");
			return bEncryptKey;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	private byte[] toByteArray(String strHex) {
		byte[] bHex = new byte[8];

		if (strHex == null || strHex.length() != 16) {
			return null;
		}
		for (int nIndex = 0; nIndex < 8; nIndex++) {
			int n = Integer.parseInt(strHex.substring(nIndex * 2,
					nIndex * 2 + 2), 16);
			bHex[nIndex] = (byte) n;
		}
		return bHex;
	}
}
