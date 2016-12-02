package com.sinosoft.midplat.ccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.transform.XSLTransformer;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class DocumentBlc extends XmlSimpFormat {
	private Element cTransaction_Header = null;
	
	public DocumentBlc(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into DocumentBlc.noStd2Std()...");

		//�˴�����һ��������ͷ�����Ϣ����֯���ر���ʱ���õ�
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("Transaction_Header").clone();
		
		Document mStdXml = 
			DocumentBlcInXsl.newInstance().getCache().transform(pNoStdXml);
		
		mStdXml = dealCardType(mStdXml);
			
		cLogger.info("Out DocumentBlc.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into DocumentBlc.std2NoStd()...");

		/*Start-��֯���ر���ͷ*/
		Element Transaction = new Element("Transaction");
		Element mBkOthDate = new Element("BkOthDate");
		mBkOthDate.setText(
				String.valueOf(DateUtil.getCur8Date()));

		Element mBkOthSeq = new Element("BkOthSeq");
		mBkOthSeq.setText(cTransaction_Header.getChildText("BkPlatSeqNo"));

		Element mBkOthRetCode = new Element("BkOthRetCode");
		Element mBkOthRetMsg = new Element("BkOthRetMsg");
//		Element mRetData = pStdXml.getRootElement().getChild("RetData");
		Element mRetData = pStdXml.getRootElement().getChild("Head");
		if (mRetData.getChildText(Flag).equals("0")) {	//���׳ɹ�
			mBkOthRetCode.setText("00000");
			mBkOthRetMsg.setText("��֤���˽��׳ɹ���");
		} else {	//����ʧ��
			mBkOthRetCode.setText("11111");
			mBkOthRetMsg.setText(
					mRetData.getChildText(Desc));
		}

		Element mTran_Response = new Element("Tran_Response");
		mTran_Response.addContent(mBkOthDate);
		mTran_Response.addContent(mBkOthSeq);
		mTran_Response.addContent(mBkOthRetCode);
		mTran_Response.addContent(mBkOthRetMsg);

		cTransaction_Header.addContent(mTran_Response);

		Transaction.addContent(cTransaction_Header);
		/*End-��֯���ر���ͷ*/

		
		cLogger.info("Out DocumentBlc.std2NoStd()!");
		return new Document(Transaction);
	}
	
	private Document dealCardType(Document pStdXml){
		cLogger.info("Into DocumentBlc.dealCardType()...");
		List detailList = pStdXml.getRootElement().getChild(Body).getChildren(Detail);
		
		//��֤���˽�ȡ���źŵ�ǰ��λ��Ϊ��֤����
		for(int i=0;i<detailList.size();i++){
			Element detailEle = (Element) detailList.get(i);
			detailEle.getChild(CardType).setText(detailEle.getChildText(CardNo).substring(0,7));
		}
		
		cLogger.info("Into DocumentBlc.dealCardType()...");
		return pStdXml;
	}
	
	public static void main(String[] args) throws Exception {
}
}