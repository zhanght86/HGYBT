package com.sinosoft.midplat.boc.format;


import java.io.FileInputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.transform.XSLTransformException;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
/**
 * �ɷѳ���ת��
 * @author anico
 *
 */
public class PolicyCancelTrial extends XmlSimpFormat {
	String InsuId;//���չ�˾����
	String ZoneNo;//��������
	String BrNo;//�������
	String TellerNo;//��Ա����
	String TransNo;//������ˮ��
	String TranCode;//������
	public PolicyCancelTrial(Element pThisConf) {
		super(pThisConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into ContConfirm.noStd2Std()...");
		cLogger.info("������������:"+JdomUtil.toStringFmt(pNoStdXml));
		Element mainEle=pNoStdXml.getRootElement().getChild("Main");
		InsuId=mainEle.getChildText("InsuId");
		ZoneNo=mainEle.getChildText("ZoneNo");
		BrNo=mainEle.getChildText("BrNo");
		TellerNo=mainEle.getChildText("TellerNo");
		TransNo=mainEle.getChildText("TransNo");
		TranCode=mainEle.getChildText("TranCode");
		Document mStdXml = PolicyCancelTrialInXsl.newInstance().getCache().transform(pNoStdXml);
		cLogger.info("������ı���:"+JdomUtil.toStringFmt(mStdXml));
		cLogger.info("Out ContConfirm.noStd2Std()!");
		return mStdXml;
	}
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into ContConfirm.std2NoStd()...");
		cLogger.info("���ķ��ر���:"+JdomUtil.toStringFmt(pStdXml));
		Document mNoStdXml = PolicyCancelTrialOutXsl.newInstance().getCache().transform(pStdXml);
		Element mainEle=mNoStdXml.getRootElement().getChild("Main");
		mainEle.getChild("InsuId").setText(InsuId);
		mainEle.getChild("ZoneNo").setText(ZoneNo);
		mainEle.getChild("BrNo").setText(BrNo);
		mainEle.getChild("TellerNo").setText(TellerNo);
		mainEle.getChild("TransNo").setText(TransNo);
		mainEle.getChild("TranCode").setText(TranCode);
		cLogger.info("���ظ�����������:"+JdomUtil.toStringFmt(mNoStdXml));
		cLogger.info("Out ContConfirm.std2NoStd()!");
		return mNoStdXml;
	}
	public static void main(String[] args) throws Exception {
		String pathXml="E:\\middleware\\user_projects\\domains\\base_domain\\autodeploy\\HGLIFE\\WEB-INF\\msg\\11\\OutStd\\2016\\201611\\20161108\\2016101410002904_1014_113327.xml";
		Document xmlDoc=JdomUtil.build(new FileInputStream(pathXml));
		Document mNoStdXml = ContConfirmOutXsl.newInstance().getCache().transform(xmlDoc);
		System.out.println(JdomUtil.toStringFmt(mNoStdXml));
	}
}
