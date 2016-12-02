//���г�������
package com.sinosoft.midplat.boc.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.utility.ExeSQL;

public class WriteOff extends XmlSimpFormat {
	String InsuId;//���չ�˾����
	String ZoneNo;//��������
	String BrNo;//�������
	String TellerNo;//��Ա����
	String TransNo;//������ˮ��
	String TranCode;//������
	public WriteOff(Element pThisConf) {
		super(pThisConf);
	}
	
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into WriteOff.noStd2Std()...");
		cLogger.info("������������:"+JdomUtil.toStringFmt(pNoStdXml));
		Element mainEle=pNoStdXml.getRootElement().getChild("Main");
		InsuId=mainEle.getChildText("InsuId");
		ZoneNo=mainEle.getChildText("ZoneNo");
		BrNo=mainEle.getChildText("BrNo");
		TellerNo=mainEle.getChildText("TellerNo");
		TransNo=mainEle.getChildText("TransNo");
		TranCode=mainEle.getChildText("TranCode");
		Document mStdXml =WriteOffInXsl.newInstance().getCache().transform(pNoStdXml);
		cLogger.info("������ı���:"+JdomUtil.toStringFmt(mStdXml));
		cLogger.info("Out WriteOff.noStd2Std()!");
		return mStdXml;
	}
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into WriteOff.std2NoStd()...");
		cLogger.info("���ķ��ر���:"+JdomUtil.toStringFmt(pStdXml));
		Document mNoStdXml = WriteOffOutXsl.newInstance().getCache().transform(pStdXml);
		Element mainEle=mNoStdXml.getRootElement().getChild("Main");
		mainEle.getChild("InsuId").setText(InsuId);
		mainEle.getChild("ZoneNo").setText(ZoneNo);
		mainEle.getChild("BrNo").setText(BrNo);
		mainEle.getChild("TellerNo").setText(TellerNo);
		mainEle.getChild("TransNo").setText(TransNo);
		mainEle.getChild("TranCode").setText(TranCode);
		cLogger.info("���ظ�����������:"+JdomUtil.toStringFmt(mNoStdXml));
		cLogger.info("Out WriteOff.std2NoStd()!");
		return mNoStdXml;
	}
}