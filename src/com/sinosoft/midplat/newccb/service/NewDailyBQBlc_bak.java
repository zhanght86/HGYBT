//�������ն��˱�ȫ��
package com.sinosoft.midplat.newccb.service;

import java.util.List;

import javax.servlet.jsp.tagext.TryCatchFinally;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;

public class NewDailyBQBlc_bak extends ServiceImpl {
	private Element cSubInXmlEle1 = new Element("Body");
	private Element cSubInXmlEle2 = new Element("Body");
	private Element cSubInXmlEle3 = new Element("Body");
	protected Document cSubInXmlDoc1=null;
	protected Document cSubInXmlDoc2=null;
	protected Document cSubInXmlDoc3=null;
	protected Document cSubOutXmlDoc1;
	protected Document cSubOutXmlDoc2;
	protected Document cSubOutXmlDoc3;
	
	public NewDailyBQBlc_bak(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc) throws Exception {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into NewDailyBQBlc.service()...");
		cInXmlDoc = pInXmlDoc;
		//20140728 ���ı�ȫ���˷ֱ����������ӿ� ���ڸ������˱������ڽɷѣ������з������ı�ȫ���˱��İ������ֶ������ͣ���˱����ֳ����ֶ������͵ı��ķ����ε����ķ���
		//���ڸ��� cSubInXmlDoc1���˱� cSubInXmlDoc2������ cSubInXmlDoc3

		JdomUtil.print(cInXmlDoc);
		List<Element> tDetail = XPath.selectNodes(cInXmlDoc.getRootElement(), "//TranData/Body/Detail");
		
		//�޳��������ݣ��ٰ�������װ����
		for (int i=0;i<tDetail.size();i++) {
			cLogger.info("�������ͣ�"+tDetail.get(i).getChildText("BusiType"));
			if(tDetail.get(i).getChildTextTrim("BusiType").equals("09")){
				cSubInXmlEle1.addContent((Element)tDetail.get(i).clone());
			}else if(tDetail.get(i).getChildTextTrim("BusiType").equals("10")){
				cSubInXmlEle2.addContent((Element)tDetail.get(i).clone());
			}else if(tDetail.get(i).getChildTextTrim("BusiType").equals("11")){
				cSubInXmlEle3.addContent((Element)tDetail.get(i).clone());
			}
		}

		try {
			cTranLogDB = insertTranLog(cInXmlDoc);
			
//			String tSqlStr = new StringBuilder("select 1 from TranLog where RCode=").append(CodeDef.RCode_OK)
//				.append(" and TranDate=").append(cTranLogDB.getTranDate())
//				.append(" and FuncFlag=").append(cTranLogDB.getFuncFlag())
//				.append(" and TranCom=").append(cTranLogDB.getTranCom())
//				.append(" and NodeNo='").append(cTranLogDB.getNodeNo()).append('\'')
//				.toString();
//			ExeSQL tExeSQL = new ExeSQL();
//			if ("1".equals(tExeSQL.getOneValue(tSqlStr))) {
//				throw new MidplatException("�ѳɹ�������֤���ˣ������ظ�������");
//			} else if (tExeSQL.mErrors.needDealError()) {
//				throw new MidplatException("��ѯ��ʷ������Ϣ�쳣��");
//			}
//			
//			//����ǰ�û��������ı�����Ϣ(ɨ�賬ʱ��)
//			String tErrorStr = cInXmlDoc.getRootElement().getChildText(Error);
//			if (null != tErrorStr) {
//				throw new MidplatException(tErrorStr);
//			}
			try {
				//���°����ͽ�Detail��䵽�ӱ�����ȥ
				System.out.println("=============������װ����=============");
				cInXmlDoc.getRootElement().removeChild("Body");
				cInXmlDoc.getRootElement().addContent(cSubInXmlEle1);
				cSubInXmlDoc1 = (Document)cInXmlDoc.clone();
				cInXmlDoc.getRootElement().removeChild("Body");
				cInXmlDoc.getRootElement().addContent(cSubInXmlEle2);
				cSubInXmlDoc2 =  (Document)cInXmlDoc.clone();
				cInXmlDoc.getRootElement().removeChild("Body");
				cInXmlDoc.getRootElement().addContent(cSubInXmlEle3);
				cSubInXmlDoc3 =  (Document)cInXmlDoc.clone();
			} catch (Exception e) {
				e.printStackTrace();
			}
			JdomUtil.print(cSubInXmlDoc1);
			JdomUtil.print(cSubInXmlDoc2);
			JdomUtil.print(cSubInXmlDoc3);
			
//			cSubOutXmlDoc1 = new CallWebsvcAtomSvc("16").call(cSubInXmlDoc1);
			
			if(cSubInXmlDoc2.getRootElement().getChild("Body").getChildren("Detail")!=null){
				cSubOutXmlDoc2 = new CallWebsvcAtomSvc("16").call(cSubInXmlDoc2);
			}
			if(cSubInXmlDoc3.getRootElement().getChild("Body").getChildren("Detail")!=null){
				cSubOutXmlDoc3 = new CallWebsvcAtomSvc("18").call(cSubInXmlDoc3);
			}
			
//			���ڶ��˺���û�У��ٽ��ף����سɹ�
			Element tTranData=new Element(TranData);
			
			Element tHead=new Element(Head);
			Element tFlag=new Element(Flag);
			tFlag.setText("0");
			Element tDesc=new Element(Desc);
			tDesc.setText("���׳ɹ�");
			Element tBody=new Element(Body);
			
			tTranData.addContent(tHead);
			tTranData.addContent(tBody);
			
			tHead.addContent(tFlag);
			tHead.addContent(tDesc);
			
			cSubOutXmlDoc1=new Document(tTranData);
			
			Element tOutHeadEle = cSubOutXmlDoc1.getRootElement().getChild(Head);
			Element tOutHeadEle2 = cSubOutXmlDoc2.getRootElement().getChild(Head);
			Element tOutHeadEle3 = cSubOutXmlDoc3.getRootElement().getChild(Head);
			if(cSubInXmlDoc1.getRootElement().getChild("Body").getContentSize()!=0){
				if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {	//����ʧ��
					cOutXmlDoc=cSubOutXmlDoc1;
//					throw new MidplatException(tOutHeadEle.getChildText(Desc));
				}
			}
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle2.getChildText(Flag))) {	//����ʧ��
				cOutXmlDoc=cSubOutXmlDoc2;
//				throw new MidplatException(tOutHeadEle2.getChildText(Desc));
			}
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle3.getChildText(Flag))) {	//����ʧ��
				cOutXmlDoc=cSubOutXmlDoc3;
//				throw new MidplatException(tOutHeadEle3.getChildText(Desc));
			}
			
			if(CodeDef.RCode_OK == Integer.parseInt(tOutHeadEle.getChildText(Flag))&&CodeDef.RCode_OK == Integer.parseInt(tOutHeadEle2.getChildText(Flag))
					&&CodeDef.RCode_OK == Integer.parseInt(tOutHeadEle3.getChildText(Flag))){
				cOutXmlDoc=cSubOutXmlDoc1;
			}
		} catch (Exception ex) {
			cLogger.error(cThisBusiConf.getChildText(name)+"����ʧ�ܣ�", ex);
			
			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
		}
		
		if (null != cTranLogDB) {	//������־ʧ��ʱcTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));	//-1-δ���أ�0-���׳ɹ������أ�1-����ʧ�ܣ�����
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("������־��Ϣʧ�ܣ�" + cTranLogDB.mErrors.getFirstError());
			}
		}
		
		cLogger.info("Out NewDailyBQBlc.service()!");
		JdomUtil.print(cOutXmlDoc);
		return cOutXmlDoc;
	}
}
