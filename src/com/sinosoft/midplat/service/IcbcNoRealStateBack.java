package com.sinosoft.midplat.service;

import java.lang.reflect.Constructor;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.format.Format;
import com.sinosoft.midplat.icbc.bat.CreateTxtFile;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;

public class IcbcNoRealStateBack extends ServiceImpl {

	public IcbcNoRealStateBack(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into IcbcNoRealStateBack.service()...");
		cInXmlDoc = pInXmlDoc; 		
		
		try {
			
			//����ǰ�û��������ı�����Ϣ(ɨ�賬ʱ��)
			String tErrorStr = cInXmlDoc.getRootElement().getChildText(Error);
			if (null != tErrorStr) {
				throw new MidplatException(tErrorStr);
			}
			
			cOutXmlDoc = new CallWebsvcAtomSvc("23").call(cInXmlDoc);
						
//			String mInFilePathName = "D:/YBT_SAVE_XML/ZHH/��ʵʱ/״̬����ļ����󷵻�ģ�ⱨ��.xml";
//			InputStream mIs = new FileInputStream(mInFilePathName);
//			byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
//			Document XmlDoc = JdomUtil.build(mInClearBodyBytes, "GBK");
//			cOutXmlDoc = XmlDoc;
						
					
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {	//����ʧ��
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
			List<Element> mResultDetailEles = cOutXmlDoc.getRootElement().getChild(Body).getChildren("StateDetail");
			if(0 == mResultDetailEles.size() ){
				cOutXmlDoc = MidplatUtil.getSimpOutXml(2, "����û�к˱�״̬�����Ͷ����,���跢�ͣ�");
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
	
		cLogger.info("Out IcbcNoRealStateBack.service()!");
		return cOutXmlDoc;
	}

}
