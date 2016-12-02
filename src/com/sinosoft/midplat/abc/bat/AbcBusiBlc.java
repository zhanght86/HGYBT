/**
 * ũ������Լ����
 */

package com.sinosoft.midplat.abc.bat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;
import org.jdom.Element;

import com.sinosoft.midplat.bat.Balance;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.abc.AbcConf;
import com.sinosoft.utility.ExeSQL;

public class AbcBusiBlc extends Balance {
	public AbcBusiBlc() {
		super(AbcConf.newInstance(), "2005");
	}
	
	protected String getFileName() {
		Element mBankEle = cThisConfRoot.getChild("bank");
		return "B"+mBankEle.getAttributeValue("insu")+mBankEle.getAttributeValue(id)+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+".TXT";
	}
	
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into AbcBusiBlc.parse()...");
		
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		
		BufferedReader mBufReader = new BufferedReader(
				new InputStreamReader(pBatIs, mCharset));
		
		String[] mSubMsgs = mBufReader.readLine().split("\\|", -1);
		Element mCountEle = new Element(Count);
		mCountEle.setText(mSubMsgs[4].trim());
		Element mSumPremEle = new Element(Prem);
		mSumPremEle.setText(String.valueOf(NumberUtil.yuanToFen(mSubMsgs[5].trim())));
		
		Element mBodyEle = new Element(Body);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mSumPremEle);
		
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			
			//���У�ֱ������
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("���У�ֱ��������������һ����");
				continue;
			}
			
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			
			if (!"01".equals(tSubMsgs[10])) {
				cLogger.warn("�ǳб�������ֱ��������������һ����");
				continue;
			}
			if (!("01".equals(tSubMsgs[11])&&("0000".equals(tSubMsgs[12])))) {
				cLogger.warn("�б������������򳷵��ĵ��ӣ���ֱ��������������һ����");
				continue;
			}
			
			/*
			 * ũ�е�ʵʱ�ĵ�������4λ�ģ�����ʡ�д���+2λ�����룩�����˵ĵ�������2λ�ģ����Զ��˵ĵ����뻹Ҫƴ��ʡ�����д��벿��
			 * ������ʱ���ũ�е���Աȷ�ϵģ�20130403
			 */
			String nodeNo=null;
			if(tSubMsgs[3]!=null&&tSubMsgs[4]!=null&&tSubMsgs[5]!=null){
				nodeNo=tSubMsgs[3].trim()+tSubMsgs[4].trim()+tSubMsgs[5].trim();
			}
			
			Element tTranDateEle = new Element(TranDate);
			tTranDateEle.setText(tSubMsgs[0]);
			
			Element tTranNoEle = new Element(TranNo);
			tTranNoEle.setText(tSubMsgs[2]);
			
			Element tProposalPrtNoEle = new Element(ProposalPrtNo);
			tProposalPrtNoEle.setText(tSubMsgs[7]);
			
			Element tContNoEle = new Element(ContNo);
			tContNoEle.setText(tSubMsgs[8]);
			
			Element tAgentCom=new Element(AgentCom);
			tAgentCom.setText(nodeNo);
			
			Element tPremEle = new Element(Prem);
			long tPremFen = NumberUtil.yuanToFen(tSubMsgs[9]);
			tPremEle.setText(String.valueOf(tPremFen));
			
			/*Element tContTypeEle = new Element("ContType");
			if (!(tSubMsgs[8].trim()).endsWith("88")) {
				tContTypeEle.setText(String.valueOf(HxlifeCodeDef.ContType_Group));
			} else {
				tContTypeEle.setText(String.valueOf(HxlifeCodeDef.ContType_Bank));
			}*/
			
			Element tDetailEle = new Element(Detail);
//			tDetailEle.addContent(tTranDateEle);
			tDetailEle.addContent(tAgentCom);
//			tDetailEle.addContent(tTranNoEle);
			tDetailEle.addContent(tProposalPrtNoEle);
			tDetailEle.addContent(tContNoEle);
			tDetailEle.addContent(tPremEle);
			//tDetailEle.addContent(tContTypeEle);
			
			mBodyEle.addContent(tDetailEle);
		}
		mBufReader.close();	//�ر���
		
		cLogger.info("Out AbcBusiBlc.parse()!");
		return mBodyEle;
	}
	
	protected Element getHead() {
		cLogger.info("Into Balance.getHead()...");
		String tBalanceFlag = "0";
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		String mCurrDate = DateUtil.getCurDate("yyyyMMdd");
		cLogger.info(" ��������Ϊ..."+DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		cLogger.info(" ��ǰ����Ϊ..."+mCurrDate);
		
		// ���ֹ����ˣ���tBalanceFlag��־��Ϊ1 �����ն�����Ϊ0 modify by liuq 2010-11-11
		if(!DateUtil.getDateStr(cTranDate, "yyyyMMdd").equals(mCurrDate)){
			tBalanceFlag = "1";
		}
		
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));
		
		Element mTranCom = new Element(TranCom);
		mTranCom.setText(cThisConfRoot.getChildText("TranCom"));
		String tTempStr = cThisConfRoot.getChild("TranCom").getAttributeValue(outcode);
		if (null!=tTempStr && !"".equals(tTempStr)) {
			mTranCom.setAttribute(outcode, tTempStr);
		}
		
		Element mZoneNo = new Element("ZoneNo");
		mZoneNo.setText(cThisBusiConf.getChildText("zone"));
		
		Element mNodeNo = new Element(NodeNo);
		mNodeNo.setText(cThisBusiConf.getChildText("node"));
		
		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText("sys");
		
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(Thread.currentThread().getName());
		
		Element mFuncFlag = new Element(FuncFlag);

		tTempStr = cThisBusiConf.getChild(funcFlag).getAttributeValue(outcode);
		mFuncFlag.setText(tTempStr);
		
		Element mBalanceFlag = new Element("BalanceFlag");
		mBalanceFlag.setText(tBalanceFlag);
		
		//����ͷ������Ӻ��ĵ����б���
		Element mBankCode = new Element("BankCode");
		mBankCode.setText("0102");
		
		Element mHead = new Element(Head);
		mHead.addContent(mTranDate);
		mHead.addContent(mTranTime);
		
		//����ͷ������Ӻ��ĵ����б���
		mHead.addContent(mBankCode);
		
		mHead.addContent(mTranCom);
		mHead.addContent(mZoneNo);
		mHead.addContent(mNodeNo);
		mHead.addContent(mTellerNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);
		mHead.addContent(mBalanceFlag);

		cLogger.info("Out Balance.getHead()!");
		return mHead;
	}
	
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.Abc.bat.AbcBusiBlc.main");
		mLogger.info("����ʼ...");
		
		AbcBusiBlc mBatch = new AbcBusiBlc();
		
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
