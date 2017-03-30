package com.sinosoft.midplat.newccb.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.ContDB;
//import com.sinosoft.lis.db.CoreDataDB;
import com.sinosoft.lis.schema.ContSchema;
import com.sinosoft.lis.vschema.ContSet;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.newccb.format.PolicyCountQuery;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
//import com.sinosoft.midplat.common.JSLifeCodeDef;
import com.sinosoft.midplat.common.JdomUtil;
//import com.sinosoft.midplat.common.MidplatCheck;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.service.ServiceImpl;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;


public class ContPolicyCountQuery extends ServiceImpl {
	public ContPolicyCountQuery(Element pThisBusiConf) {
		super(pThisBusiConf);
	}
	
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into ContPolicyCountQuery.service()...");
		cInXmlDoc = pInXmlDoc;
		Element mDatabaseEle = MidplatConf.newInstance().getConf().getRootElement().getChild("coretable");
	   	String pos_info = mDatabaseEle.getAttributeValue("pos_info");
//		try {
			//cTranLogDB = insertTranLog(pInXmlDoc);
			
			cLogger.info("Into ContPolicyCountQuery.service()...-->authorityCheck.submitData(mHeadEle)交易权限");	
			//add by zhj 网点与权限 添加代理   
			//cInXmlDoc = authority(cInXmlDoc);

			int  maxNo = 10;//一个file中允许最多的CONTNO  测试的话，只需要把这个值换掉即可
//			String tQueryDate = cInXmlDoc.getRootElement().getChild("Body").getChildText("QueryDate");
//			String tSqlContNo = "select t.policy_no from Toi.pos_info t where t.pos_no in " +
//			"(select p.pos_no form POSA.pos_info " +
//			"where p.reg_type='CCB' and p.status='35' and p.DBCHK_Time='"+tQueryDate+"') " +
//			"and t.policy_no in (select p.policy_no form POSA.pos_info " +
//			"where p.reg_type='CCB' and p.status='35' and p.DBCHK_Time='"+tQueryDate+"') " +
//			"and t.status='35'";
//			SSRS ssrs = new ExeSQL().execSQL(tSqlContNo);
//			int size = ssrs.MaxRow;
			List<String> list = new ArrayList<String>();
			list.add("001");
			list.add("002");
			list.add("003");
			list.add("004");
			list.add("005");
			list.add("006");
			list.add("007");
			list.add("008");
			list.add("009");
			list.add("0010");
			list.add("0011");
			list.add("0012");
			list.add("0013");
			list.add("0014");
			list.add("0015");
			list.add("0016");
			list.add("0017");
			list.add("0018");
			int size = list.size();
			//Element mPackNum = cInXmlDoc.getRootElement().getChild("Body").getChild("PackNum");
			//Element pThisConfRoot = CcbGetFile.newInstance().getConf().getRootElement();
			Element tFiles = new Element("Files");
			if(tFiles!=null){
				tFiles.detach();
			}
			for (int i = 0; i <= size/maxNo; i++) {
				Element tFile = new Element("File");
				Element tPackNum = new Element("PackNum");
				Element tDate = new Element("Date");
			//	tDate.setText(tQueryDate);
				Element tContNo_List = new Element("ContNo_List");
				for (int j = i*maxNo; j <(i+1)*maxNo; j++) {
					if(j==size){
						break;
					}
					//String contNo = ssrs.GetText(1, j);
					String contNo = list.get(j);
					System.out.println("contNo===="+contNo);
					Element tContNo = new Element("ContNo");
					tContNo.setText(contNo);
					tContNo_List.addContent(tContNo);
				}
				tFile.addContent(tPackNum);
				tFile.addContent(tDate);
				tFile.addContent(tContNo_List);
				tFiles.addContent(tFile);
				if(i*maxNo == size){
					tFile.detach();
				}
			}
		    Document document  = new Document(tFiles); 
			byte[] mBodyBytes = JdomUtil.toBytes(document);
			String filePath=SysInfo.cBasePath+"com/sinosoft/midplat/ccbNew/format/xslTemplate/getfile.xml";
			File file = new File(filePath);
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(mBodyBytes);
				fos.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    cOutXmlDoc = document;
//		}
//		catch (MidplatException ex) {
//			cLogger.info(cThisBusiConf.getChildText(name)+"交易失败！", ex);			
//			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
//		} 
//		catch (Exception ex) {
//			cLogger.error(cThisBusiConf.getChildText(name)+"交易失败！", ex);
//			cOutXmlDoc = MidplatUtil.getSimpOutXml(CodeDef.RCode_ERROR, ex.getMessage());
//		} 
		
//		if (null != cTranLogDB) {	//插入日志失败时cTranLogDB=null
//			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
//			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
//			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
//			long tCurrMillis = System.currentTimeMillis();
//			cTranLogDB.setUsedTime((int)((tCurrMillis-mStartMillis)/1000));
//			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurrMillis));
//			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurrMillis));
//			if (!cTranLogDB.update()) {
//				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
//			}
//		}
		
		cLogger.info("Out ContPolicyCountQuery.service()!");
		return cOutXmlDoc;
	}
	/**
	 * @param mInXmlDoc
	 * @return cInXmlDoc
	 * @throws MidplatException
	 * create by zhj 2010 11 05
	 * 网点 权限 添加校验方法
	 */
	private Document authority(Document mInXmlDoc) throws MidplatException{
		
  
		Element mRootEle = mInXmlDoc.getRootElement();
		Element mHeadEle = (Element) mRootEle.getChild(Head);
		Element mAgentCom = mHeadEle.getChild("AgentCom");
		Element mAgentCode = mHeadEle.getChild("AgentCode");
		String sNodeNo = (String)mHeadEle.getChildTextTrim("NodeNo");
		String sTranCom =  (String)mHeadEle.getChildTextTrim("TranCom");
		 
		cLogger.info("通过银行,地区,网点号查询代理机构号,并添加！");
		String tSqlStr2 = new StringBuilder("select AgentCom from NodeMap where TranCom='"+sTranCom).append('\'')
			.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCom = new ExeSQL().getOneValue(tSqlStr2);
		String tSqlStr3 = new StringBuilder("select AgentCode from NodeMap where TranCom='"+sTranCom).append('\'')
		.append(" and NodeNo='").append(sNodeNo).append('\'').toString();
		String sAgentCode = new ExeSQL().getOneValue(tSqlStr3); 
		cLogger.info("authority-->"+sAgentCom);
		cLogger.info("authority-->"+sAgentCode);   
		if (((""==sAgentCom)||(sAgentCom==null)) && ((""==sAgentCode)||(sAgentCode==null))){ 
			throw new MidplatException("此网点不存在，请确认！");
		}  
		mAgentCom.setText(sAgentCom);
		mAgentCode.setText(sAgentCode);
		return mInXmlDoc;
		
	}
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始…");
		
		String mInFilePath = "F:\\ccbNew\\绿灯测试请求报文.xml";
//		String mOutFilePath = "F:\\中融建行test\\ccb.xml";
		
		InputStream mIs = new FileInputStream(mInFilePath);
		Document mInXmlDoc = JdomUtil.build(mIs);
		mIs.close();
		
//		Document mOutXmlDoc = new PolicyCountQuery(null).std2NoStd(mInXmlDoc);
		Document mOutXmlDoc = new ContPolicyCountQuery(NewCcbConf.newInstance().getConf().getRootElement()).service(mInXmlDoc);

		JdomUtil.print(mOutXmlDoc);
		
//		OutputStream mOs = new FileOutputStream(mOutFilePath);
//		JdomUtil.output(mOutXmlDoc, mOs);
//		mOs.flush();
//		mOs.close();
		
		System.out.println("成功结束！");
	}
}
