/**
 * �ս�����ļ���ʽ����ʵʱ�˱���
 */

package com.sinosoft.midplat.icbc.bat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jdom.Element;

import com.sinosoft.midplat.bat.Balance;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.KeyUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.icbc.IcbcConf;
import com.sinosoft.midplat.icbc.net.IcbcKeyCache;

public class IcbcNoRealBlc extends Balance {
	public IcbcNoRealBlc() {
		super(IcbcConf.newInstance(), "1201");
	}
	
	protected String getFileName() {
		Element mBankEle = cThisConfRoot.getChild("bank");
//		return "ENY"+mBankEle.getAttributeValue("insu")+mBankEle.getAttributeValue(id)+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+"03.txt";
		
		//�ܶ��ܣ�ENY(3λ)+���չ�˾����(3λ)+���д��루2λ��+���ڣ�8λ��+03.txt ���ܵĶ����ļ����ļ���
		return "ENY"+mBankEle.getAttributeValue("insu")+mBankEle.getAttributeValue(id)+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+"03.txt.des";
	}
	
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into IcbcNoRealBlc.parse()...");
		
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		
		/**
		 * �����ļ����ܣ�������Ӧ�������ܶ����ļ�����
		 */
		pBatIs = KeyUtil.decode(pBatIs, IcbcKeyCache.newInstance().getKey());
		
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));
				
		Element mBodyEle = new Element(Body);
		Element mCountEle = new Element(Count);
		mBodyEle.addContent(mCountEle);

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
			
			Element tTranDateEle = new Element(TranDate);
			tTranDateEle.setText(tSubMsgs[1]);
			
			Element tZoneNoEle = new Element("ZoneNo");
			tZoneNoEle.setText(tSubMsgs[2]);
			
			//�к�����ֻʹ��NodeNo��NodeNo��ֵΪ������+�������ֵ��
			String nodeNo = tSubMsgs[2].trim()+tSubMsgs[3].trim();
			Element tNodeNoEle = new Element(NodeNo);
			tNodeNoEle.setText(nodeNo);
			cLogger.info("NodeNo:"+nodeNo);
			
			Element tTellerNoEle = new Element(TellerNo);
			tTellerNoEle.setText(tSubMsgs[4]);
			
			Element tTranNoEle = new Element(TranNo);
			tTranNoEle.setText(tSubMsgs[5]);
			
			Element tProposalPrtNoEle = new Element(ProposalPrtNo);
			tProposalPrtNoEle.setText(tSubMsgs[6]);
			cLogger.info("ProposalPrtNo:"+tSubMsgs[6]);
			
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
	protected Element getHead() {
		cLogger.info("Into Balance.getHead()...");
		
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));
		
		Element mTranCom = (Element) cThisConfRoot.getChild(TranCom).clone();
		
		Element mZoneNo = (Element) cThisBusiConf.getChild(ZoneNo).clone();
		
		Element mNodeNo = (Element) cThisBusiConf.getChild(NodeNo).clone();
		
		//����ͷ������Ӻ��ĵ����б���
		Element mBankCode = new Element("BankCode");
		mBankCode.setText("0101");
		
		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText(CodeDef.SYS);
		
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(getFileName());
		
		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cThisBusiConf.getChildText(funcFlag));
		
		Element mTranLogNo = new Element("TranLogNo");
		mTranLogNo.setText(Thread.currentThread().getName());
		
		
		Element mHead = new Element(Head);
		mHead.addContent(mTranDate);
		mHead.addContent(mTranTime);
		mHead.addContent(mTranCom);
		mHead.addContent(mZoneNo);
		mHead.addContent(mNodeNo);
		//����ͷ������Ӻ��ĵ����б���
		mHead.addContent(mBankCode);
		mHead.addContent(mTellerNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);
		mHead.addContent(mTranLogNo);
		
		cLogger.info("Out Balance.getHead()!");
		return mHead;
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.icbc.bat.IcbcNoRealBlc.main");
		mLogger.info("����ʼ...");
		
		IcbcNoRealBlc mBatch = new IcbcNoRealBlc();
		mBatch.setDate(new Date());
		//���ڲ����ˣ����ò���������
		if (0 != args.length) {
			mLogger.info("args[0] = " + args[0]);
			
			/**
			 * �ϸ�����У���������ʽ��\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))��
			 * 4λ��-2λ��-2λ�ա�
			 * 4λ�꣺4λ[0-9]�����֡�
			 * 1��2λ�£�������Ϊ0��[0-9]�����֣�˫���±�����1��ͷ��β��Ϊ0��1��2������֮һ��
			 * 1��2λ�գ���0��1��2��ͷ��[0-9]�����֣�������3��ͷ��0��1��
			 * 
			 * ������У���������ʽ��\\d{4}\\d{2}\\d{2}��
			 */ 
			if (args[0].matches("\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))")) {
				mBatch.setDate(args[0]);
			} else {
				throw new MidplatException("���ڸ�ʽ����ӦΪyyyyMMdd��" + args[0]);
			}
		}  
		
		mBatch.run();
		
		mLogger.info("�ɹ�������");
	}
}
