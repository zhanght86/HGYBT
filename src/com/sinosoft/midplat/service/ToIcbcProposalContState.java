/**
 * tou����״̬������������ļ�������
 */

package com.sinosoft.midplat.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.FTPDealBL;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.ExeSQL;

public class ToIcbcProposalContState extends ServiceImpl {
	public ToIcbcProposalContState(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into ToIcbcProposalContState.service()...");
		cInXmlDoc = pInXmlDoc; 
		JdomUtil.print(cInXmlDoc);
		cInXmlDoc.getRootElement().getChild("Head").getChild("TranNo").setText("203"+DateUtil.getCur8Date()+DateUtil.getCur6Time());		
		
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
				throw new MidplatException("�ѳɹ�����Ͷ����״̬����ش����ף������ظ�������");
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
			String sFtpIP = mBodyEle.getChildText("FtpIP");
			String sFtpPort = mBodyEle.getChildText("FtpPort");
			String sFtpUser = mBodyEle.getChildText("FtpUser");
			String sFtpPass = mBodyEle.getChildText("FtpPass");
			String sFtpFilePath = mBodyEle.getChildText("FtpFilePath");
			String sFilePath = mBodyEle.getChildText("FilePath");
			File tfile = new File(sFilePath+sFileName);
			tfile.renameTo(new File(sFilePath+sFileName+".bak"));
			InputStream mIs = new FileInputStream(sFilePath+sFileName+".bak");	
			cLogger.info("neirong1"+sFilePath+sFileName);
			BufferedReader mBufReader = new BufferedReader(
					new InputStreamReader(mIs, "GBK"));
			FileOutputStream DXFiles_fos= new FileOutputStream(sFilePath+sFileName);
			for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
				cLogger.info(tLineMsg);
				//����ֱ������
				tLineMsg = tLineMsg.trim();
				if ("".equals(tLineMsg)) {
					cLogger.warn("���У�ֱ��������������һ����");
					continue;
				}
				String[] tSubMsgs = tLineMsg.split("\\|", -1);
				String msgprtno = tSubMsgs[3];
				String sql1 = "select nodeno from tranlog where proposalprtno = '"+msgprtno+"' and trancom = '1'";
				ExeSQL texesql = new ExeSQL();
				if(!"".equals(texesql.getOneValue(sql1))){
					String tnodeno = texesql.getOneValue(sql1).substring(0, 5);
					tSubMsgs[0]=tnodeno;
				}				
				String t = tSubMsgs[0]+"|";
				for(int i =1;i<tSubMsgs.length-1;i++){
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
			
			FTPDealBL tFTPDealBL = new FTPDealBL(sFtpIP, sFtpUser,
					sFtpPass, Integer.valueOf(sFtpPort));
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
		
		cLogger.info("Out ToIcbcProposalContState.service()!");
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
}
