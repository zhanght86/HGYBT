package com.sinosoft.midplat.newccb.service;

/*
 * @author  Administrator
 * 重空核对
 * 
 */
import org.jdom.Document;
import org.jdom.Element;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.newccb.util.FileUtil;
import com.sinosoft.midplat.service.ServiceImpl;

public class CCBBusiBlc extends ServiceImpl {
	
	private Element mBusiConf = null;
	public CCBBusiBlc(Element pThisBusiConf) {
		super(pThisBusiConf);
		mBusiConf = pThisBusiConf;
	} 
	
	@Override
	public Document service(Document pInXmlDoc) {
		this.cLogger.info("Into CCBBusiBlc.service()...");
		try{
			// 解密报文，并且获得标准报文
			FileUtil fu = new FileUtil(pInXmlDoc);
			cOutXmlDoc = fu.fileSecurity();
		}catch (Exception ex){
			cLogger.error(ex.getMessage());
			ex.printStackTrace();
		}
		cLogger.info("解密结果报文："+JdomUtil.toStringFmt(cOutXmlDoc));
		return cOutXmlDoc;
	}
}
