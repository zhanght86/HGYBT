//������ԥ���˱�������
package com.sinosoft.midplat.ccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.format.NewCont;

public class NoTakenApply extends XmlSimpFormat{
	private Element cTransaction_Header = null;
	
	public NoTakenApply(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into NoTakenApply.noStd2Std()...");
		
		//�˴�����һ��������ͷ�����Ϣ����֯���ر���ʱ���õ�
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("Transaction_Header").clone();
		
		Document mStdXml = 
			NoTakenApplyInXsl.newInstance().getCache().transform(pNoStdXml);
		
		JdomUtil.print(mStdXml);
		cLogger.info("Out NoTakenApply.noStd2Std()!");
		
		return mStdXml;
	} 
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into NoTakenApply.std2NoStd()...");
        if (pStdXml.getRootElement().getChild("Head").getChildText(Flag).equals("0")) { // ���׳ɹ�
        	String prem=pStdXml.getRootElement().getChild("Body").getChildText("EdorCTPrem");
        	String prem2=null;
        	int prem3=Integer.parseInt(prem);
        	prem3=prem3/100;
        	prem2=String.valueOf(prem3);
        	pStdXml.getRootElement().getChild("Body").getChild("EdorCTPrem").setText(prem2);
        }
		Document mNoStdXml =   
			NoTakenApplyOutXsl.newInstance().getCache().transform(pStdXml);
		
        /* Start-��֯���ر���ͷ */
        Element mBkOthDate = new Element("BkOthDate");
        mBkOthDate.setText(DateUtil.getCurDate("yyyyMMdd"));

        Element mBkOthSeq = new Element("BkOthSeq");
        mBkOthSeq.setText(cTransaction_Header.getChildText("BkPlatSeqNo"));

        Element mBkOthRetCode = new Element("BkOthRetCode");
        Element mBkOthRetMsg = new Element("BkOthRetMsg");
        Element mRetData = pStdXml.getRootElement().getChild("Head");
        if (mRetData.getChildText(Flag).equals("0")) { // ���׳ɹ�
            mBkOthRetCode.setText("00000");
            mBkOthRetMsg.setText("���׳ɹ���");
        } else { // ����ʧ��
            mBkOthRetCode.setText("11111");
            mBkOthRetMsg.setText(mRetData.getChildText(Desc));
        }

        Element mTran_Response = new Element("Tran_Response");
        mTran_Response.addContent(mBkOthDate);
        mTran_Response.addContent(mBkOthSeq);
        mTran_Response.addContent(mBkOthRetCode);
        mTran_Response.addContent(mBkOthRetMsg);

        cTransaction_Header.addContent(mTran_Response);
        mNoStdXml.getRootElement().addContent(0, cTransaction_Header);
        /* End-��֯���ر���ͷ */
		
		JdomUtil.print(mNoStdXml);
				cLogger.info("Out NoTakenApply.std2NoStd()!");
		return mNoStdXml;
	}
	

}
