/*
 * ������ԥ���˱�����
 */
package com.sinosoft.midplat.boc.bat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.jdom.Element;

import com.sinosoft.midplat.bat.Balance;
import com.sinosoft.midplat.boc.BocConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.boc.bat.BocTBBusiBlc;

public class BocTBBusiBlc extends Balance {
	public BocTBBusiBlc() {
		super(BocConf.newInstance(), "801");
	}
	
	/**
	 * ��ȡ�����ϴ�����ԥ���˱������ļ���
	 */
	protected String getFileName() {
		Element mBankEle = cThisConfRoot.getChild("bank");
		//��ȡ�ļ�
		System.out.println("��ȡ�����ϴ�����ԥ���˱������ļ�");
		return "YYQTB"+mBankEle.getAttributeValue("insu")+"BOC"+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+".txt";
	}
	
	/**
	 * 
	 */
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into BocTBBusiBlc.parse()...");
		
		//��ȡ��ǰ���������ļ����ڵ���charset�ӽڵ��ı�
		String mCharset = cThisBusiConf.getChildText(charset);
		//charset�ӽڵ��ı�Ϊ�ա����ַ���
		if (null==mCharset || "".equals(mCharset)) {
			//charset�ӽڵ��ı���ΪGBK
			mCharset = "GBK";
		}
		
		//�½�ʹ��GBK�ַ������ֽ���ͨ���ַ���������
		BufferedReader mBufReader = new BufferedReader(
				new InputStreamReader(pBatIs, mCharset));
		
		//�½�Body�ڵ�
		Element mBodyEle = new Element(Body);
		
		//����FTPIP
		String sFtpIP = cThisBusiConf.getChildText("FtpIP");
		//����FTP�˿�
		String sFtpPort = cThisBusiConf.getChildText("FtpPort");
		//����FTP�û���
		String sFtpUser = cThisBusiConf.getChildText("FtpUser");
		//����FTP����
		String sFtpPass = cThisBusiConf.getChildText("FtpPass");
		//����FTP·��
		String sFtpFilePath = cThisBusiConf.getChildText("FtpFilePath");
		//�����������ļ���ַ
		String sFilePath = cThisBusiConf.getChildText("FilePath");
		//�����ϴ��ļ���ַ[�ļ���]
		String sFileName = getFileName();
		
		//�����������ļ���ַ�ڵ�
		Element tFilePath = new Element("FilePath");
		//�����ϴ��ļ���ַ[�ļ���]�ڵ�
		Element tFileName = new Element("FileName");
		//����FTPIP�ڵ�
		Element tFtpIP = new Element("FtpIP");
		//����FTP�˿ڽڵ�
		Element tFtpPort = new Element("FtpPort");
		//����FTP�û����ڵ�
		Element tFtpUser = new Element("FtpUser");
		//����FTP����ڵ�
		Element tFtpPass = new Element("FtpPass");
		//����FTP·���ڵ�
		Element tFtpFilePath = new Element("FtpFilePath");
		
		//�����ı�����Ϊ����FTPIP
		tFtpIP.setText(sFtpIP);
		//�����ı�����Ϊ����FTP�˿�
		tFtpPort.setText(sFtpPort);
		//�����ı�����Ϊ����FTP�û���
		tFtpUser.setText(sFtpUser);
		//�����ı�����Ϊ����FTP����
		tFtpPass.setText(sFtpPass);
		//�����ı�����Ϊ����FTP·��
		tFtpFilePath.setText(sFtpFilePath);
		//�����ı�����Ϊ�����ϴ��ļ���ַ[�ļ���]
		tFileName.setText(sFileName);
		//�����ı�����Ϊ�����������ļ���ַ
		tFilePath.setText(sFilePath);
		
		/**
		 * Body�ڵ��������FTPIP�ڵ㡢����FTP�˿ڽڵ㡢����FTP�û����ڵ㡢����FTP����ڵ㡢
		 * ����FTP·���ڵ㡢�����ϴ��ļ���ַ[�ļ���]�ڵ㡢�����������ļ���ַ�ڵ�
		 **/
		mBodyEle.addContent(tFtpIP);
		mBodyEle.addContent(tFtpPort);
		mBodyEle.addContent(tFtpUser);
		mBodyEle.addContent(tFtpPass);
		mBodyEle.addContent(tFtpFilePath);
		mBodyEle.addContent(tFileName);
		mBodyEle.addContent(tFilePath);
		mBufReader.close();	//�ر��� 
		
		cLogger.info("Out BocTBBusiBlc.parse()!");
		//����Body�ڵ�
		return mBodyEle;
	}
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.boc.bat.BocTBBusiBlc.main");
		mLogger.info("����ʼ...");
		
		BocTBBusiBlc mBatch = new BocTBBusiBlc();
		
		//���ڲ����ˣ����ò���������
		if (0 != args.length) {
			mLogger.info("args[0] = " + args[0]);
			
			/**
			 * �ϸ�����У���������ʽ��\\d{4}-((0\\d)|(1[012]))-(([012]\\d)|(3[01]))��
			 * 4λ��-2λ��-2λ�ա�
			 * 4λ�꣺4λ[0-9]�����֡�
			 * 1��2λ�£�������Ϊ0��[0-9]�����֣�˫���±�����1��ͷ��β��Ϊ0��1��2������֮һ��
			 * 1��2λ�գ���0��1��2��ͷ��[0-9]�����֣�������3��ͷ��0��1��
			 * 
			 * ������У���������ʽ��\\d{4}-\\d{2}-\\d{2}��
			 */
			if (args[0].matches("\\d{4}-((0\\d)|(1[012]))-(([012]\\d)|(3[01]))")) {
				mBatch.setDate(args[0]);
			} else {
				throw new MidplatException("���ڸ�ʽ����ӦΪyyyy-MM-dd��" + args[0]);
			}
		}
		
		mBatch.run();
		
		mLogger.info("�ɹ�������");
	}
}
