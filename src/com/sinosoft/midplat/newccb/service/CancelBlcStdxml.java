package com.sinosoft.midplat.newccb.service;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class CancelBlcStdxml implements XmlTag{

	private final static Logger cLogger = Logger.getLogger(CancelBlcStdxml.class);
	public static Document call(Document pInXmlDoc) throws Exception {
		cLogger.info("Into CancelBlcStdxml.call()...");
		Element tOutHeadEle=new Element(Head);
		Element Flag=new Element("Flag");
		Element Desc=new Element("Desc");
		try {
		Element tRootEle=pInXmlDoc.getRootElement();
		tRootEle.removeChild(Head);
		tRootEle.addContent(tOutHeadEle);
		tOutHeadEle.addContent(Flag);
		tOutHeadEle.addContent(Desc);
		String sTranDate=pInXmlDoc.getRootElement().getChild(Body).getChildText("EdorCTDate");//获取 退保信息数据日期
		if(sTranDate.equals("")){
			Flag.setText("1");
			Desc.setText("退保日期不能为空！");
			throw new MidplatException(tOutHeadEle.getChildText("Desc"));
		}
		
		/**
		 * 调用核心的---- 银保退保信息传递 ----服务
		 */
		pInXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_Bank_EdorCTInfo).call(pInXmlDoc);
		
		} catch (Exception e) {
			Flag.setText("1");
			if(tOutHeadEle.getChildText("Desc").equals("")){
				Desc.setText("(退保交易传递)交易失败！");
			}else{
				Desc.setText(tOutHeadEle.getChildText("Desc"));
			}
			throw new MidplatException(tOutHeadEle.getChildText("Desc"));
		}
		
		return pInXmlDoc;
		}
}
