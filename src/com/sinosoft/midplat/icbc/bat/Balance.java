package com.sinosoft.midplat.icbc.bat;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import javax.crypto.Cipher;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.*;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.icbc.net.IcbcKeyCache;
import com.sinosoft.midplat.service.Service;

public abstract class Balance extends TimerTask implements XmlTag {
	protected final Logger cLogger = Logger.getLogger(getClass());
	
	private final XmlConf cThisConf;
	private final int cFuncFlag;	//���״���
	
	/**
	 * �ṩһ��ȫ�ַ��ʵ㣬ֻ��ÿ�ζ��˿�ʼʱ��ʼ����
	 * ȷ���ڸôζ��˴������������������һ���ԣ�
	 * ���ܿ���(ǰ��Ĵ�����0��ǰ���������0���)��Ӱ�졣
	 */
	protected Date cTranDate;
	
	protected String cResultMsg;
	
	/**
	 * �ṩһ��ȫ�ַ��ʵ㣬ֻ��ÿ�ζ��˿�ʼʱ���³�ʼ����
	 * ȷ���ڸôζ��˴������������������һ���ԣ�
	 * ���������ļ��Զ����ص�Ӱ�졣Ҳ����˵�����ζ�ʱ����һ��������
	 * ��������ļ����޸Ľ�������һ������ʱ��Ч����Ӱ�챾�Ρ�
	 */
	protected Element cMidplatRoot = null;
	protected Element cThisConfRoot = null;
	protected Element cThisBusiConf = null;
	protected String tZoneNo = null;
	protected String tBrNo = null;
	
	public Balance(XmlConf pThisConf, String pFuncFlag) {
		this(pThisConf, Integer.parseInt(pFuncFlag));
	}
	
	public Balance(XmlConf pThisConf, int pFuncFlag) {
		cThisConf = pThisConf;
		cFuncFlag = pFuncFlag;
	}
	
	/**
	 * Ϊ��֤����Timer������Ϊĳ���һ���쳣����ֹ��������벶��run()�е������쳣��
	 */
	
	public void run() {
		cLogger.info("Into Balance.run()...");
		
		//�����һ�ν����Ϣ
		cResultMsg = null;
		
		try {
			if (null == cTranDate) {
				cTranDate = new Date();
			}

			cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
			cThisConfRoot = cThisConf.getConf().getRootElement();
			cThisBusiConf = (Element) XPath.selectSingleNode(cThisConfRoot,
					"business[funcFlag='" + cFuncFlag + "']");

			Element tTranData = new Element(TranData);
			Document tInStdXml = new Document(tTranData);

			String ttFileName = getFileName();
			cLogger.info("FileName = " + ttFileName);
			String ttLocalDir = cThisBusiConf.getChildTextTrim("localDir");
			InputStream ttBatIns = null;
			if (null != ttLocalDir && !ttLocalDir.equals("")) { // ���localDir�����ã�����ȡ�����ļ�
				ttBatIns = getLocalFile(ttLocalDir, ttFileName);
			} else { // localDir��ftp��δ���ã�����
				throw new MidplatException("��ʵʱ������������");
			}
			//�����ļ�
			if(ttFileName.endsWith(".des"))
			{
				int x = 0;
				ByteArrayOutputStream ins = new ByteArrayOutputStream();
				while ((x = ttBatIns.read()) != -1) {
					ins.write(x);
				}
				byte[] bPack = ins.toByteArray();
				byte[] byte1 = decode(bPack);
			    cLogger.info("����ǰ�ֽ�������:"+bPack.length +" ���ܺ��ֽ�������: "+byte1.length);
			    if(byte1.length==0)
			    {
			    	throw new MidplatException("��������û�з�ʵʱ�ļ���ϸ��");
			    }
				ttBatIns = new ByteArrayInputStream(byte1, 0, byte1.length);
				//����һ����ʱ���ļ��������
				FileOutputStream output  = new FileOutputStream(ttLocalDir+ttFileName.substring(0, 22));
				byte[] b = new byte[5];
				int con = 0;
				while((con = ttBatIns.read(b))!=-1)
					{
					output.write(b, 0, con);
					}
				 ttBatIns.close();
				 output.close();
				 ttBatIns=getLocalFile(ttLocalDir, ttFileName.substring(0, 22));
				}
			
			Element tgetHead = getHead(ttBatIns);
			tTranData.addContent(tgetHead);
			if(ttFileName.endsWith(".des"))
			{
			ttBatIns=getLocalFile(ttLocalDir, ttFileName.substring(0, 22));
			}
			else{
				ttBatIns = getLocalFile(ttLocalDir, ttFileName);
			}
			// ��ȡ��׼���˱���
			Element ttBodyEle = (Element) parse(ttBatIns);
            tTranData.addContent((Element)ttBodyEle.clone());
//            cLogger.info(JdomUtil.toString(tInStdXml));
			// ����ҵ������ȡ��׼���ر���
			String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";
			// ��midplat.xml���зǿ�Ĭ�����ã����ø�����
			String tServiceValue = cMidplatRoot.getChildText("defaService");
			if (null != tServiceValue && !"".equals(tServiceValue)) {
				tServiceClassName = tServiceValue;
			}
			// ����ϵͳ�ĸ��Ի������ļ����зǿ�Ĭ�����ã����ø�����
			tServiceValue = cThisConfRoot.getChildText("service");
			if (null != tServiceValue && !"".equals(tServiceValue)) {
				tServiceClassName = tServiceValue;
			}
			tServiceValue = cThisBusiConf.getChildText("service");
			if (null != tServiceValue && !"".equals(tServiceValue)) {
				tServiceClassName = tServiceValue;
			}
			cLogger.info("ҵ����ģ�飺" + tServiceClassName);
			Constructor tServiceConstructor =  Class
					.forName(tServiceClassName).getConstructor(
							new Class[] { Element.class });
			Service tService = (Service)tServiceConstructor
					.newInstance(new Object[] { cThisBusiConf });
			Document tOutStdXml = tService.service(tInStdXml);
		}catch (MidplatException ex) {
				cLogger.info(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);			
		} catch (Exception ex) {
				cLogger.error(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);
		}
		
		cTranDate = null;	//ÿ�����꣬�������
		cLogger.info("Out Balance.run()!");
	}

	private byte[] decode(byte[] pBytes) throws Exception {
		Cipher mCipher = Cipher.getInstance("DES");
		mCipher.init(Cipher.DECRYPT_MODE, IcbcKeyCache.newInstance().getKey());
		
		return mCipher.doFinal(pBytes);
	}
	protected Element getHead(InputStream tIn) throws Exception {
		cLogger.info("Into Balance.getHead()...");
		
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));
		
		Element mTranCom = new Element(TranCom);
		mTranCom.setText("1");
		
		Element mNodeNo = (Element) cThisBusiConf.getChild(NodeNo).clone();
		
		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText(CodeDef.SYS);
		
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(getFileName());
		
		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cThisBusiConf.getChildText(funcFlag));
		
		Element mHead = new Element(Head);
		mHead.addContent(mTranDate);
		mHead.addContent(mTranTime);
		mHead.addContent(mTranCom);
		mHead.addContent(mNodeNo);
		mHead.addContent(mTellerNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);
		
		cLogger.info("Out Balance.getHead()!");
		return mHead;
	}
	
	
	private InputStream getLocalFile(String pDir, String pName) throws MidplatException {
		cLogger.info("Into Balance.getLocalFile()...");
		
		pDir = pDir.replace('\\', '/');
		if (!pDir.endsWith("/")) {
			pDir += '/';
		}
		String mPathName = pDir + pName;
		cLogger.info("LocalPath = " + mPathName);
		InputStream mIns = null;
		
		try {
			mIns = new FileInputStream(mPathName);
		} catch (IOException ex) {
			//δ�ҵ������ļ�
			throw new MidplatException("δ�ҵ������ļ���" + mPathName);
		}
		
		cLogger.info("Out Balance.getLocalFile()!");
		return mIns;
	}
	
	@SuppressWarnings("unchecked")
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into IcbcNoRealBlc.parse()...");
		
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		
		BufferedReader mBufReader = new BufferedReader(
				new InputStreamReader(pBatIs, mCharset));
		
		Element mBodyEle = new Element(Body);
		Element mCountEle = new Element(Count);
		mBodyEle.addContent(mCountEle);

		long mSumPrem = 0;
		int mCount = 0;
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			
			//���У�ֱ������
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("���У�ֱ��������������һ����");
				continue;
			}
			
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			
			Element tTranComEle = new Element(TranCom);
			tTranComEle.setText(tSubMsgs[0]);
			
			Element tTranDateEle = new Element(TranDate);
			tTranDateEle.setText(tSubMsgs[1]);
			
			Element tZoneNoEle = new Element("ZoneNo");
			tZoneNoEle.setText(tSubMsgs[2]);
			
			Element tNodeNoEle = new Element(NodeNo);
			tNodeNoEle.setText(tSubMsgs[3]);
			
			Element tTellerNoEle = new Element(TellerNo);
			tTellerNoEle.setText(tSubMsgs[4]);
			
			Element tTranNoEle = new Element(TranNo);
			tTranNoEle.setText(tSubMsgs[5]);
			
			Element tProposalPrtNoEle = new Element(ProposalPrtNo);
			tProposalPrtNoEle.setText(tSubMsgs[6]);
			
			Element tSaleChannelEle = new Element("SaleChannel");
			tSaleChannelEle.setText(tSubMsgs[7]);
			
			Element tAppFlagEle = new Element("AppFlag");
			tAppFlagEle.setText(tSubMsgs[8]);
			
			Element tAppntNameEle = new Element("AppntName");
			tAppntNameEle.setText(tSubMsgs[9]);
			
			Element tAppntIDTypeEle = new Element("AppntIDType");
			tAppntIDTypeEle.setText(tSubMsgs[10]);
			
			Element tAppntIDNoEle = new Element("AppntIDNo");
			tAppntIDNoEle.setText(tSubMsgs[11]);
			
			Element tAccNoEle = new Element("AccNo");
			tAccNoEle.setText(tSubMsgs[12]);
			
			
			
			Element tDetailEle = new Element(Detail);
			tDetailEle.addContent(tTranComEle);
			tDetailEle.addContent(tTranDateEle);
			tDetailEle.addContent(tZoneNoEle);
			tDetailEle.addContent(tNodeNoEle);
			tDetailEle.addContent(tTellerNoEle);
			
			tDetailEle.addContent(tTranNoEle);
			tDetailEle.addContent(tProposalPrtNoEle);
			tDetailEle.addContent(tSaleChannelEle);
			tDetailEle.addContent(tAppFlagEle);
			tDetailEle.addContent(tAppntNameEle);
			
			tDetailEle.addContent(tAppntIDTypeEle);
			tDetailEle.addContent(tAppntIDNoEle);
			tDetailEle.addContent(tAccNoEle);
			
			mBodyEle.addContent(tDetailEle);
			 
			mCount++; 
		} 
		mCountEle.setText(String.valueOf(mCount));

		mBufReader.close();	//�ر��� 
		
		cLogger.info("Out IcbcNoRealBlc.parse()!");
		return mBodyEle;
	}
	public final void setDate(Date pDate) {
		cTranDate = pDate;
	}
	
	public final void setDate(String p8DateStr) {
		cTranDate = DateUtil.parseDate(p8DateStr, "yyyyMMdd");
	}
	
	/**
	 * ���ݶ����ļ������������ɵ�������ļ���
	 */
	protected abstract String getFileName();
	
	/**
	 * �÷������ڵ������̨����֮�󱻵��á�
	 * Ĭ���ǿ�ʵ�֣���һЩ���⽻���п���������һЩ���Ի��Ķ����������
	 * ��������������з�ʵʱ�˱����ˣ��ڵ������̨�󣬽����ļ��ϴ������������ڴ˷�����ʵ�֡�
	 * @param Document pOutStdXml: ��̨���صı�׼���ġ�
	 */
	protected void ending(Document pOutStdXml) throws Exception {
		cLogger.info("Into Balance.ending()...");
		
		cLogger.info("do nothing, just out!");
		
		cLogger.info("Out Balance.ending()!");
	}
	
	public String getResultMsg() {
		return cResultMsg;
	}
}
