package com.sinosoft.midplat.icbc.format;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;

public class Taken extends XmlSimpFormat{

	public Taken(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into Taken.noStd2Std()...");
		
		Document mStdXml = 
			TakenInXsl.newInstance().getCache().transform(pNoStdXml);
		JdomUtil.print(mStdXml);
		cLogger.info("Out Taken.noStd2Std()!");
		
		return mStdXml;
	} 
	
	public Document std2NoStd(Document pStdXml) throws Exception {
		cLogger.info("Into Taken.std2NoStd()...");
		
		Element cRoot=pStdXml.getRootElement();
		Element cHead=cRoot.getChild("Head");
		String flag=cHead.getChildText("Flag");
		List<Element> list=null;
		Document mNoStdXml =   
			TakenOutXsl.newInstance().getCache().transform(pStdXml);
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
				else if(i==15){
					eDate.setText("                                                            "+text);
			    }
				else if(i==17){
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
				cLogger.info("Out Taken.std2NoStd()!");
		return mNoStdXml;
	}
//	public static void main(String[] args)throws Exception{
//		String mInFilePath = "E:\\t.xml";
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document doc=JdomUtil.build(mIs);
//		JdomUtil.print(new Taken().std2NoStd(doc));
//	}
}
