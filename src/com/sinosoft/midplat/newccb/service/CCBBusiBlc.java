package com.sinosoft.midplat.newccb.service;

/*
 * @author  Administrator
 * �ؿպ˶�
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
			// ���ܱ��ģ����һ�ñ�׼����
			FileUtil fu = new FileUtil(pInXmlDoc);
			cOutXmlDoc = fu.fileSecurity();
		}catch (Exception ex){
			cLogger.error(ex.getMessage());
			ex.printStackTrace();
		}
		cLogger.info("���ܽ�����ģ�"+JdomUtil.toStringFmt(cOutXmlDoc));
		return cOutXmlDoc;
	}
}
