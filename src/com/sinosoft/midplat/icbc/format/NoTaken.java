package com.sinosoft.midplat.icbc.format;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class NoTaken extends XmlSimpFormat{
	public NoTaken(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into NoTaken.noStd2Std()...");
		
		Document mStdXml = 
			NoTakenInXsl.newInstance().getCache().transform(pNoStdXml);
		JdomUtil.print(mStdXml);
		cLogger.info("Out NoTaken.noStd2Std()!");
		
		return mStdXml;
	} 
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into NoTaken.std2NoStd()...");
		
		Element cRoot=pStdXml.getRootElement();
		Element cHead=cRoot.getChild("Head");
		String flag=cHead.getChildText("Flag");
		List<Element> list=null;
		Document mNoStdXml =   
			NoTakenOutXsl.newInstance().getCache().transform(pStdXml);
		if("0".equals(flag)){
		    list=pStdXml.getRootElement().getChild("Body").getChildren("Attachment");
		    Element mFormInstance=mNoStdXml.getRootElement().getChild("TXLifeResponse").getChild("OLifE").getChild("FormInstance");
			List<Element> noList=new ArrayList<Element>();
			int i=1;
			for(Element e:list){
				Element eDate=e.getChild("AttachmentData");
				String text=eDate.getText();
				System.out.println(text);
				if(i==1){
					eDate.setText("                                                "+text);
			    }
				else if(i==2){
					eDate.setText("                                                                 "+text);
			    }
				else if(i==14){
					eDate.setText("                                                            "+text);
			    }
				else if(i==16){
					eDate.setText("                                                            "+text);
			    }
				else{
					eDate.setText("  "+text);
				}
				
				Element eCopy=(Element)e.clone();
				noList.add(eCopy);
				i++;
			}
			
			mFormInstance.addContent(noList);
		}
		JdomUtil.print(mNoStdXml);
				cLogger.info("Out NoTaken.std2NoStd()!");
		return mNoStdXml;
	}
}
