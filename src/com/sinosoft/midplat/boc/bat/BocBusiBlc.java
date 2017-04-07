/**
 * �������ն���
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
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;

public class BocBusiBlc extends Balance {
	public BocBusiBlc() {
		super(BocConf.newInstance(), "1105");
	}
	protected String getFileName() {
		Element mBankEle = cThisConfRoot.getChild("bank");
		return "RZDZ"+mBankEle.getAttributeValue("insu")+"BOC"+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+".txt";
	}
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into BocBusiBlc.parse()...");
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		BufferedReader mBufReader = new BufferedReader(new InputStreamReader(pBatIs, mCharset));
		Element mBodyEle = new Element(Body);
		Element mCountEle = new Element(Count);
		Element mPremEle = new Element(Prem);
		mBodyEle.addContent(mCountEle);
		mBodyEle.addContent(mPremEle);
        int count=0;
        long  prem=0;
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			//���У�ֱ������
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("���У�ֱ��������������һ����");
				continue;
			}
			tLineMsg = tLineMsg.trim();
			if(tLineMsg.length()<60)
			{
				cLogger.warn("�����У�ֱ��������������һ����");
				continue;
			}
			if(tLineMsg.length()>60){
				if("".equals(tLineMsg) || tLineMsg == null || tLineMsg.endsWith("W")){
					continue;
				}
				Element tTranNoEle = new Element(TranNo);
				tTranNoEle.setText(tLineMsg.substring(0, 16));
				Element tNodeNoEle = new Element("AgentCom");
				tNodeNoEle.setText(tLineMsg.substring(20, 25));
				Element tTranDateEle = new Element(TranDate);
				tTranDateEle.setText(tLineMsg.substring(25, 33));
				Element tContNoEle = new Element(ContNo);
				tContNoEle.setText(tLineMsg.substring(tLineMsg.length()-46, tLineMsg.length()-16).trim());
				Element tPremEle = new Element(Prem);
				tPremEle.setText(NumberUtil.yuanToFen(tLineMsg.substring(tLineMsg.length()-16, tLineMsg.length()-3).trim())+"");
				cLogger.info("������ˮ�ţ�"+tLineMsg.substring(0,16));
				cLogger.info("���㣺"+tLineMsg.substring(20,25));
				cLogger.info("�������ڣ�"+tLineMsg.substring(25,33));
				cLogger.info("�����ţ�"+tLineMsg.substring(tLineMsg.length()-46, tLineMsg.length()-16).trim());
				cLogger.info("���ѣ�"+tLineMsg.substring(tLineMsg.length()-16, tLineMsg.length()-3).trim());
				Element tDetailEle = new Element(Detail);
				tDetailEle.addContent(tTranDateEle);
				tDetailEle.addContent(tNodeNoEle);
				tDetailEle.addContent(tTranNoEle);
				tDetailEle.addContent(tContNoEle);
				tDetailEle.addContent(tPremEle);
				mBodyEle.addContent(tDetailEle);
				count++;
				prem+=NumberUtil.yuanToFen(tLineMsg.substring(tLineMsg.length()-15, tLineMsg.length()-3).trim());
			}
		}
		cLogger.info("�ܱ���:"+count);
		cLogger.info("�ܽ��:"+prem);
		mCountEle.setText(String.valueOf(count));
		mPremEle.setText(String.valueOf(prem));
		mBufReader.close();	//�ر��� 
		cLogger.info("Out BocBusiBlc.parse()!");
		return mBodyEle;
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.boc.bat.BocBusiBlc.main");
		mLogger.info("�й������µ����˳���ʼ...");
		
		BocBusiBlc mBatch = new BocBusiBlc();
		
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
		
		mLogger.info("�й������µ����˳ɹ�������");
	}
}
