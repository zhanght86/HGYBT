
package com.sinosoft.midplat.boc.bat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.log4j.Logger;
import org.jdom.Element;
import com.sinosoft.midplat.boc.BocConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.bat.Balance;
/**
 *	��ȫ����
 * 	@author PengYF
 *
 */
public class SecuTradAppDoc extends Balance{
	public SecuTradAppDoc() {
		super(BocConf.newInstance(), "1106");
	}
	protected String getFileName(){
		Element mBankEle = cThisConfRoot.getChild("bank");
		return "SJTB"+mBankEle.getAttributeValue("insu")+"BOC"+DateUtil.getDateStr(cTranDate, "yyyyMMdd")+".txt";
	}
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into SecuTradAppDoc.parse()...");
		
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
		long prem=0;
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			//���У�ֱ������
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("���У�ֱ��������������һ����");
				continue;
			}
			tLineMsg = tLineMsg.trim();
			if(tLineMsg.length()==20){
				mCountEle.setText(Integer.parseInt(tLineMsg.substring(12,20))+"");
				cLogger.info("�ɹ�����:"+tLineMsg.substring(12,20).trim());
			}
			if(tLineMsg.length()>20){
				Element tZoneNo=new Element("ZoneNo");
				Element tNodeNo=new Element("NodeNo");
				tNodeNo.setText(tLineMsg.substring(16,21).trim());
				Element tBusiType = new Element("BusiType");
				if('Y'==tLineMsg.charAt(tLineMsg.length()-1)){//Y-��ԥ�����˱�
					tBusiType.setText("07");
				}
				if('X'==tLineMsg.charAt(tLineMsg.length()-1)){//X-��ԥ�����˱�
					tBusiType.setText("09");
				}
				if('R'==tLineMsg.charAt(tLineMsg.length()-1)){//R-���ڸ����ɹ�
					tBusiType.setText("11");
				}
				Element tTranDateEle = new Element(TranDate);
				tTranDateEle.setText(tLineMsg.substring(21,29).trim());
				Element tProposalPrtNoEle = new Element(ProposalPrtNo);
				Element tContNoEle = new Element(ContNo);
				tContNoEle.setText(tLineMsg.substring(29,59).trim());
				Element tAgentCom=new Element(AgentCom);
				tAgentCom.setText(tLineMsg.substring(16,21).trim());
				Element tPremEle = new Element(Prem);
				tPremEle.setText(NumberUtil.yuanToFen(tLineMsg.substring(tLineMsg.length()-17,tLineMsg.length()-4).trim())+"");
				prem+=NumberUtil.yuanToFen(tLineMsg.substring(tLineMsg.length()-17,tLineMsg.length()-4).trim());
				Element tDetailEle = new Element(Detail);
				cLogger.info("�������ڣ�"+ tLineMsg.substring(21,29).trim());
				cLogger.info("�����ţ�"+tLineMsg.substring(29,59).trim());
				cLogger.info("��"+NumberUtil.yuanToFen(tLineMsg.substring(tLineMsg.length()-17,tLineMsg.length()-4).trim()));
				cLogger.info("���㣺"+tLineMsg.substring(16,21).trim());
				tDetailEle.addContent(tTranDateEle);
				tDetailEle.addContent(tBusiType);
				tDetailEle.addContent(tAgentCom);
				tDetailEle.addContent(tProposalPrtNoEle);
				tDetailEle.addContent(tContNoEle);
				tDetailEle.addContent(tPremEle);
				tDetailEle.addContent(tNodeNo);
				tDetailEle.addContent(tZoneNo);
				
				mBodyEle.addContent(tDetailEle);
			}
		}
		cLogger.info("�ܽ��:"+prem);
		mBodyEle.getChild("Prem").setText(String.valueOf(prem));
		mBufReader.close();	//�ر���
		cLogger.info("Out SecuTradAppDoc.parse()!");
		return mBodyEle;
	}
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.boc.bat.SecuTradAppDoc.main");
		mLogger.info("�й����б�ȫ���˳���ʼ...");
		
		SecuTradAppDoc mBatch = new SecuTradAppDoc();
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
		mLogger.info("�й����б�ȫ���˳ɹ�������");
	}
}
