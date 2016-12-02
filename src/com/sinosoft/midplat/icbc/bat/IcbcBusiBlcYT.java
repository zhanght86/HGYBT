/**
 * ����ԥ���˱�/�˱�ҵ�����
 */

package com.sinosoft.midplat.icbc.bat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.jdom.Element;

import com.sinosoft.midplat.bat.Balance;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.icbc.IcbcConf;

public class IcbcBusiBlcYT extends Balance {
	public IcbcBusiBlcYT() {
		super(IcbcConf.newInstance(), "45");
	}
	protected String getFileName() {
		Date now = new Date();
		Element mBankEle = cThisConfRoot.getChild("bank");
		return mBankEle.getAttributeValue("insu")+"01"+new SimpleDateFormat("yyyyMMdd").format(now)+"BAOQUAN.txt";
	}
	
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into IcbcBusiBlcYT.parse()...");
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		
		BufferedReader mBufReader = new BufferedReader(
				new InputStreamReader(pBatIs, mCharset));

		Element mBodyEle = new Element(Body);
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			
			//���У�ֱ������
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("���У�ֱ��������������һ����");
				continue;
			}
			
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			String type = tSubMsgs[0];
			if(type.equals("07"))//����
			{
				Element FuncFlagDetailEle = new Element("FuncFlagDetail");
				FuncFlagDetailEle.setText("404");
				
				Element tAgentComEle = new Element(AgentCom);
				tAgentComEle.setText(tSubMsgs[3]+tSubMsgs[4]);
				
				Element tDocumentControlNumberEle = new Element("DocumentControlNumber");
				tDocumentControlNumberEle.setText(tSubMsgs[8]);
				
				Element tPolNumber = new Element("PolNumber");
				tPolNumber.setText(tSubMsgs[7]);
				
				Element tDetailEle = new Element(Detail);
				tDetailEle.addContent(tAgentComEle);
				tDetailEle.addContent(tDocumentControlNumberEle);
				tDetailEle.addContent(tPolNumber);
				tDetailEle.addContent(FuncFlagDetailEle);
				
				mBodyEle.addContent(tDetailEle);
			}
			if(type.equals("10"))//�˱�
			{
				Element FuncFlagDetailEle = new Element("FuncFlagDetail");
				FuncFlagDetailEle.setText("414");

				Element tAgentComEle = new Element(AgentCom);
				tAgentComEle.setText(tSubMsgs[3]+tSubMsgs[4]);

				Element tDocumentControlNumberEle = new Element("DocumentControlNumber");
				tDocumentControlNumberEle.setText(tSubMsgs[8]);
				
				Element tPolNumber = new Element("PolNumber");
				tPolNumber.setText(tSubMsgs[7]);
				
				Element tDetailEle = new Element(Detail);
				tDetailEle.addContent(tAgentComEle);
				tDetailEle.addContent(tDocumentControlNumberEle);
				tDetailEle.addContent(tPolNumber);
				tDetailEle.addContent(FuncFlagDetailEle);
				
				mBodyEle.addContent(tDetailEle);
			}
			if(!type.equals("07")&&!type.equals("10"))
			{
				Element FuncFlagDetailEle = new Element("FuncFlagDetail");
				FuncFlagDetailEle.setText("424");

				Element tAgentComEle = new Element(AgentCom);
				tAgentComEle.setText(tSubMsgs[3]+tSubMsgs[4]);
				
				Element tDocumentControlNumberEle = new Element("DocumentControlNumber");
				tDocumentControlNumberEle.setText(tSubMsgs[8]);
				
				Element tPolNumber = new Element("PolNumber");
				tPolNumber.setText(tSubMsgs[7]);
				
				Element tDetailEle = new Element(Detail);
				tDetailEle.addContent(tAgentComEle);
				tDetailEle.addContent(tDocumentControlNumberEle);
				tDetailEle.addContent(tPolNumber);
				tDetailEle.addContent(FuncFlagDetailEle);
				
				mBodyEle.addContent(tDetailEle);
			} 
		} 

		mBufReader.close();	//�ر��� 
		
		cLogger.info("Out IcbcBusiBlcYT.parse()!");
		return mBodyEle;
	}
	
	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger.getLogger("com.sinosoft.midplat.icbc.bat.IcbcBusiBlcYT.main");
		mLogger.info("����ʼ...");
		
		IcbcBusiBlc mBatch = new IcbcBusiBlc();
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
