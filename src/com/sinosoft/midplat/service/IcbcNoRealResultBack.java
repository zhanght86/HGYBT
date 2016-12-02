package com.sinosoft.midplat.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.format.Format;
import com.sinosoft.midplat.icbc.bat.CreateTxtFile;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ExeSQL;


public class IcbcNoRealResultBack extends ServiceImpl {

	public IcbcNoRealResultBack(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into IcbcNoRealResultBack.service()...");
		cInXmlDoc = pInXmlDoc; 		
		
		try {
			//处理前置机传过来的报错信息(扫描超时等)
			String tErrorStr = cInXmlDoc.getRootElement().getChildText(Error);
			if (null != tErrorStr) {
				throw new MidplatException(tErrorStr);
			}
			
			
			
			cOutXmlDoc = new CallWebsvcAtomSvc("22").call(cInXmlDoc);
						
			
//			String mInFilePathName = "D:/YBT_SAVE_XML/ZHH/非实时/结果文件请求返回模拟报文.xml";
//			InputStream mIs = new FileInputStream(mInFilePathName);
//			byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
//			Document XmlDoc = JdomUtil.build(mInClearBodyBytes, "GBK");
//			cOutXmlDoc = XmlDoc;
			
					
			Element tOutHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {	//交易失败
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
			}
			
			List<Element> mResultDetailEles = cOutXmlDoc.getRootElement().getChild(Body).getChildren("ResultDetail");
			if(0 == mResultDetailEles.size() ){
				cOutXmlDoc = MidplatUtil.getSimpOutXml(2, "当日没有核保结论的投保单,无需发送！");
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

		cLogger.info("Out IcbcNoRealResultBack.service()!");
		return cOutXmlDoc;
	}
	
	
}
