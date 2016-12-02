package com.sinosoft.midplat.abc.bat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.GregorianCalendar;

import org.apache.log4j.Logger;
import org.jdom.Element;

import com.sinosoft.midplat.bat.Balance;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.abc.AbcConf;

public class AbcProcedureFee extends Balance {
	public AbcProcedureFee() {
		super(AbcConf.newInstance(), "9");
	}

	protected String getFileName() {
		Element mBankEle = cThisConfRoot.getChild("bank");
		GregorianCalendar mInsuEndCalendar = new GregorianCalendar();
		mInsuEndCalendar.setTime(cTranDate);
		mInsuEndCalendar.add(GregorianCalendar.DAY_OF_MONTH, 0);
		return "YBTCX" + mBankEle.getAttributeValue("insu")
				//+ DateUtil.getDateStr(mInsuEndCalendar, "yyyyMMdd") + "_RTN";
		+ DateUtil.getDateStr(mInsuEndCalendar, "yyyyMMdd");
	}

	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into AbcProcedureFee.parse()...");
		
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null == mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(
				pBatIs, mCharset));
		
		Element mBodyEle = new Element(Body);
		Element mCountEle = new Element(Count);
		Element mPremEle = new Element(Prem);
		mPremEle.setText("0");
		Element mFileName = new Element("FileName");
		mFileName.setText(getFileName());
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mPremEle);
		mBodyEle.addContent(mFileName);

		int mCount = 0;
		for (String tLineMsg; null != (tLineMsg = mBufReader.readLine());) {
			cLogger.info(tLineMsg);

			// ���У�ֱ������
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("���У�ֱ��������������һ����");
				continue;
			}

			mCount++;
		}
		mCountEle.setText(String.valueOf(mCount));

		mBufReader.close(); // �ر���

		cLogger.info("Out AbcProcedureFee.parse()!");
		return mBodyEle;
	}

	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger
				.getLogger("com.sinosoft.midplat.abc.bat.AbcProcedureFee.main");
		mLogger.info("����ʼ...");

		AbcProcedureFee mBatch = new AbcProcedureFee();

		// ���ڲ����ˣ����ò���������
		if (0 != args.length) {
			mLogger.info("args[0] = " + args[0]);

			/**
			 * �ϸ�����У���������ʽ��\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))��
			 * 4λ��-2λ��-2λ�ա� 4λ�꣺4λ[0-9]�����֡�
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
