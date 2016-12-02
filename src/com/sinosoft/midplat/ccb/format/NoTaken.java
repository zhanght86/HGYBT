//建行犹豫期退保交易
package com.sinosoft.midplat.ccb.format;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.format.XmlSimpFormat;
import com.sinosoft.midplat.newccb.format.NewCont;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;

public class NoTaken extends XmlSimpFormat{
	private Element cTransaction_Header = null;
	
	public NoTaken(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	public Document noStd2Std(Document pNoStdXml) throws Exception {
		cLogger.info("Into NoTaken.noStd2Std()...");
		
		//此处备份一下请求报文头相关信息，组织返回报文时会用到
		cTransaction_Header =
			(Element) pNoStdXml.getRootElement().getChild("Transaction_Header").clone();
		
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
		JdomUtil.print(pStdXml);
		if("0".equals(flag)){
		    list=pStdXml.getRootElement().getChild("Body").getChildren("Attachment");
		    JdomUtil.print(mNoStdXml);
		    Element mDetail=mNoStdXml.getRootElement().getChild("Transaction_Body").getChild("Detail_List");
			List<Element> noList=new ArrayList<Element>();
			int i=1;
			for(Element e:list){
				Element eDate= new Element("BkDetail1");
//				String text=eDate.getText();
				String text=e.getChild("AttachmentData").getText();
				System.out.println("====="+text);
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
				
//				Element eCopy=(Element)e.clone();
//				noList.add(eCopy);
				noList.add(eDate);
				i++;
			}
			
			mDetail.addContent(noList);
		}
		
        /* Start-组织返回报文头 */
        Element mBkOthDate = new Element("BkOthDate");
        mBkOthDate.setText(DateUtil.getCurDate("yyyyMMdd"));

        Element mBkOthSeq = new Element("BkOthSeq");
        mBkOthSeq.setText(cTransaction_Header.getChildText("BkPlatSeqNo"));

        Element mBkOthRetCode = new Element("BkOthRetCode");
        Element mBkOthRetMsg = new Element("BkOthRetMsg");
        Element mRetData = pStdXml.getRootElement().getChild("Head");
        if (mRetData.getChildText(Flag).equals("0")) { // 交易成功
            mBkOthRetCode.setText("00000");
            mBkOthRetMsg.setText("交易成功！");
        } else { // 交易失败
            mBkOthRetCode.setText("11111");
            mBkOthRetMsg.setText(mRetData.getChildText(Desc));
        }

        Element mTran_Response = new Element("Tran_Response");
        mTran_Response.addContent(mBkOthDate);
        mTran_Response.addContent(mBkOthSeq);
        mTran_Response.addContent(mBkOthRetCode);
        mTran_Response.addContent(mBkOthRetMsg);

        cTransaction_Header.addContent(mTran_Response);
        
        mNoStdXml.getRootElement().addContent(0, cTransaction_Header);
        /* End-组织返回报文头 */
		
		JdomUtil.print(mNoStdXml);
				cLogger.info("Out NoTaken.std2NoStd()!");
		return mNoStdXml;
	}
	
}
