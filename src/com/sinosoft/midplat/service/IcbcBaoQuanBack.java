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
			
			//处理前置机传过来的报错信息(扫描超时等)
			String tErrorStr = cInXmlDoc.getRootElement().getChildText(Error);
			if (null != tErrorStr) {
				throw new MidplatException(tErrorStr);
			}
			
			cOutXmlDoc = new CallWebsvcAtomSvc("26").call(cInXmlDoc);
			
			//测试模拟使用
//			String mInFilePathName = "D:/YBT_SAVE_XML/ZHH/保全数据回传核心返回模拟报文.xml";
//			InputStream mIs = new FileInputStream(mInFilePathName);
//			byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
//			Document XmlDoc = JdomUtil.build(mInClearBodyBytes, "GBK");
//			cOutXmlDoc = XmlDoc;
						
					
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {	//交易失败
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
			List<Element> mResultDetailEles = cOutXmlDoc.getRootElement().getChild(Body).getChildren("Detail");
			if(0 == mResultDetailEles.size() ){
				cOutXmlDoc = MidplatUtil.getSimpOutXml(2, "当日没有保全数据,无需回传发送！");
			}
		} 
		catch (MidplatException ex) {
			cLogger.info(cThisBusiConf.getChildText(name)+"交易失败！", ex);			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		} 
		catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"交易失败！", ex);
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
	
		cLogger.info("Out IcbcBaoQuanBack.service()!");
		return cOutXmlDoc;
	}

}

