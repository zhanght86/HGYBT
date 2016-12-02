/**
 * 保单状态变更返回银行文件处理类
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
				throw new MidplatException("已成功做过保单状态变更回传交易，不能重复操作！");
			} else if (tExeSQL.mErrors.needDealError()) {
				throw new MidplatException("查询历史对账信息异常！");
			} 
			
			//处理前置机传过来的报错信息(扫描超时等)
			String tErrorStr = cInXmlDoc.getRootElement().getChildText(Error);
			if (null != tErrorStr) {
				throw new MidplatException(tErrorStr);
			}
			
			Element mRootEle = cInXmlDoc.getRootElement();
			Element mBodyEle = mRootEle.getChild(Body);   

			String sFileName = mBodyEle.getChildText("FileName");
			String sFilePath = mBodyEle.getChildText("FilePath");
			
			InputStream mIs = new FileInputStream(sFilePath+sFileName);	
			cLogger.info("文件所在路径及文件名："+sFilePath+sFileName);
			BufferedReader mBufReader = new BufferedReader(
					new InputStreamReader(mIs, "GBK"));
			int  n = sFilePath.lastIndexOf("/");
			String newFilePath = sFilePath.substring(0, n-3);//减去fss/ 然后保存在上一级
			FileOutputStream DXFiles_fos= new FileOutputStream(newFilePath+sFileName);
			for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
				cLogger.info(tLineMsg);
				//空行直接跳过
				tLineMsg = tLineMsg.trim();
				if ("".equals(tLineMsg)) {
					cLogger.warn("空行，直接跳过，继续下一条！");
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
			//加密文件
			cLogger.info("开始加密!!!");
			UpLoadEncryFile(newFilePath,sFileName);
			cOutXmlDoc = getSimpOutXml("1", "交易成功");

		} 
		catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"交易失败！", ex);			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		} 
		catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"交易失败！", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		
		if (null != cTranLogDB) {	//插入日志失败时cTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));	//-1-未返回；0-交易成功，返回；1-交易失败，返回
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
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
		 
		cLogger.info("通过银行,地区,网点号查询代理机构号,并添加！");
		String tSqlStr2 = new StringBuilder("select AgentCom from NodeMap where TranCom='"+sTranCom).append('\'')
			.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCom = new ExeSQL().getOneValue(tSqlStr2);

		cLogger.info("authority-->"+sAgentCom);

		if ((""==sAgentCom)||(sAgentCom==null)){ 
			throw new MidplatException("此网点不存在，请确认！");
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
		// 加密
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
				cLogger.error("密钥获取失败");
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
			this.cLogger.info("FTP上传:" + newName);
			cLogger.info("Out ToIcbcContState.JIAMI...");
			cLogger.info("In ToIcbcContState.SHANGCHUAN...");
			//通过调用配置文件icbc.xml中的ftp信息  上传给银行
			String sFtpIP = cThisBusiConf.getChildText("FtpIP");
			String sFtpPort = cThisBusiConf.getChildText("FtpPort");
			String sFtpUser = cThisBusiConf.getChildText("FtpUser");
			String sFtpPass = cThisBusiConf.getChildText("FtpPass");
			String sFtpFilePath = cThisBusiConf.getChildText("FtpFilePath");//上传路径
			cLogger.info("1111111...");
			FTPDealBL tFTPDealBL = new FTPDealBL(sFtpIP, sFtpUser,
					sFtpPass, Integer.valueOf(sFtpPort));
			cLogger.info("2222222...");
			File file = new File(HomeDir+newName);
			cLogger.info("3333333...");
			cLogger.info(HomeDir+newName);
			if (!tFTPDealBL.ApacheFTPUploadFile(file, sFtpFilePath)) {
				cLogger.info("4444444...");
				throw new MidplatException("FTP上传出错 FileName = " + newName);
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
			cLogger.info("密钥路径为：" + strPath);
			if (!strPath.endsWith("/") && !strPath.endsWith("\\")) {
				strPath += "/";
			}
			File fileEncryptKey = new File(strPath + "icbcKey.dat");
			
			FileInputStream fis = null;
			fis = new FileInputStream(fileEncryptKey);
			fis.read(bEncryptKey);
			fis.close();
			cLogger.info("密钥为：icbcKey.dat=" + new String(bEncryptKey));
			bEncryptKey = toByteArray(new String(bEncryptKey));
			cLogger.info("密钥为：" + bEncryptKey);
			cLogger.info("加密结束!!!");
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
