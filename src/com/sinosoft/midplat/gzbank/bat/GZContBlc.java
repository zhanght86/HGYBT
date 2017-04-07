package com.sinosoft.midplat.gzbank.bat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;
import org.jdom.Element;
import com.sinosoft.midplat.bat.Balance;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.gzbank.GZBankConf;


public class GZContBlc extends Balance {

	public GZContBlc() {
		super(GZBankConf.newInstance(), "9005");
	}

	@Override
	protected String getFileName() {
		Element mBankEle = cThisConfRoot.getChild("bank");
		return mBankEle.getAttributeValue("insu") + ""
				+ DateUtil.getDateStr(cTranDate, "yyyyMMdd") + ".txt";
	}
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into GZContBlc.parse()...");
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null == mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}

		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(
				pBatIs, mCharset));
		
		Element mBodyEle = new Element(Body);
		
		Element mCountEle = new Element(Count);
		Element mSumPremEle = new Element(Prem);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mSumPremEle);
		/**
		 * ����״̬(2λ)+��������(8λ)+ǩ�����д���(10λ)+����������(10)+������(7λ)
		 * +������ˮ��(30λ)+������(20λ)+���(12λ����С����)+ ����������2λ��Ϊ01
		 */
		int sumCount = 0;
		long sumPrem = 0;
		for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());) {
			// ���У�ֱ������
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("���У�ֱ��������������һ����");
				continue;
			}
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			if (!tSubMsgs[0].equals("01") || !tSubMsgs[4].equals("9000103")) {
				continue;
			}
			//��ϸ�ڵ�
			String nodeNo = tSubMsgs[3];
			Element tTranDateEle = new Element(TranDate);
			tTranDateEle.setText(tSubMsgs[1]);

			Element tTranNoEle = new Element(TranNo);
			tTranNoEle.setText(tSubMsgs[5]);

			Element tContNoEle = new Element(ContNo);
			tContNoEle.setText(tSubMsgs[6]);

			Element tAgentCom = new Element(AgentCom);
			tAgentCom.setText(nodeNo);

			Element tPremEle = new Element(Prem);
			long tPremFen = NumberUtil.yuanToFen(tSubMsgs[7]);
			tPremEle.setText(String.valueOf(tPremFen));

			sumPrem += tPremFen;
			sumCount ++;

			Element tDetailEle = new Element(Detail);
			tDetailEle.addContent(tTranDateEle);
			tDetailEle.addContent(tAgentCom);
			tDetailEle.addContent(tTranNoEle);
			tDetailEle.addContent(tContNoEle);
			tDetailEle.addContent(tPremEle);

			mBodyEle.addContent(tDetailEle);
		}
		mCountEle.setText(sumCount+"");
		mSumPremEle.setText(sumPrem+"");
		mBufReader.close(); 
		cLogger.info("Out GZContBlc.parse()!");
		return mBodyEle;
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.gzbank.bat.GZContBlc.main");
		mLogger.info("���������µ����˳���ʼ...");
		
		GZContBlc mBatch = new GZContBlc();
		
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
		
		mLogger.info("���������µ����˳ɹ�������");
	}
}
