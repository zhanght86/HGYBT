package com.sinosoft.midplat.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;

public class IcbcBaoQuanBack extends ServiceImpl {

	public IcbcBaoQuanBack(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc) {
		cLogger.info("Into IcbcBaoQuanBack.service()...");
		cInXmlDoc = pInXmlDoc; 		
		
		try {
			
			//����ǰ�û��������ı�����Ϣ(ɨ�賬ʱ��)
			String tErrorStr = cInXmlDoc.getRootElement().getChildText(Error);
			if (null != tErrorStr) {
				throw new MidplatException(tErrorStr);
			}
			
			cOutXmlDoc = new CallWebsvcAtomSvc("26").call(cInXmlDoc);
			
			//����ģ��ʹ��
//			String mInFilePathName = "D:/YBT_SAVE_XML/ZHH/��ȫ���ݻش����ķ���ģ�ⱨ��.xml";
//			InputStream mIs = new FileInputStream(mInFilePathName);
//			byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
//			Document XmlDoc = JdomUtil.build(mInClearBodyBytes, "GBK");
//			cOutXmlDoc = XmlDoc;
						
					
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {	//����ʧ��
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
			List<Element> mResultDetailEles = cOutXmlDoc.getRootElement().getChild(Body).getChildren("Detail");
			if(0 == mResultDetailEles.size() ){
				cOutXmlDoc = MidplatUtil.getSimpOutXml(2, "����û�б�ȫ����,����ش����ͣ�");
			}
		} 
		catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		} 
		catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
	
		cLogger.info("Out IcbcBaoQuanBack.service()!");
		return cOutXmlDoc;
	}

}

