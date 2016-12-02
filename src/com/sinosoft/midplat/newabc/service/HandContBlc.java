///手工单出单结果文件-银行处理结果回盘
package com.sinosoft.midplat.newabc.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.AblifeCodeDef;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newabc.format.HandContOutXsl;
import com.sinosoft.midplat.newabc.util.AbcFileTransUtil;
import com.sinosoft.midplat.service.ServiceImpl;

public class HandContBlc extends ServiceImpl {
	public HandContBlc(Element pThisBusiConf) {
		super(pThisBusiConf);
	} 
	
	public Document service(Document pInXmlDoc) {
		long mStartMillis = System.currentTimeMillis();
		cLogger.info("Into HandContBlc.service()...");
		cInXmlDoc = pInXmlDoc;
		JdomUtil.print(cInXmlDoc);
		try {
			cTranLogDB = insertTranLog(pInXmlDoc);
			String cTrandate = cInXmlDoc.getRootElement().getChild(Body).getChildText(TranDate);
			
			cLogger.info("start call lis service()...");
		    cOutXmlDoc = new CallWebsvcAtomSvc(AblifeCodeDef.SID_HandContRst).call(cInXmlDoc);
		    cLogger.info("end call lis service()...");
		    //处理核心报文，并生成手工单结果文件上传至银行
		    dealFile(cOutXmlDoc,cTrandate);
		    
			Element tOutRootEle = cOutXmlDoc.getRootElement();
			Element tOutHeadEle = tOutRootEle.getChild(Head);
			if (CodeDef.RCode_ERROR == Integer.parseInt(tOutHeadEle.getChildText(Flag))) {
				throw new MidplatException(tOutHeadEle.getChildText(Desc));
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
		
		if (null != cTranLogDB) {	//插入日志失败时cTranLogDB=null
			Element tHeadEle = cOutXmlDoc.getRootElement().getChild(Head);
			cTranLogDB.setRCode(tHeadEle.getChildText(Flag));
			cTranLogDB.setRText(tHeadEle.getChildText(Desc));
			long tCurMillis = System.currentTimeMillis();
			cTranLogDB.setUsedTime((int)(tCurMillis-mStartMillis)/1000);
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			if (!cTranLogDB.update()) {
				cLogger.error("更新日志信息失败！" + cTranLogDB.mErrors.getFirstError());
			}
		}
		cLogger.info("Out NewContQuery.service()!");
		JdomUtil.print(cOutXmlDoc);
		return cOutXmlDoc;
	}

	private void dealFile(Document cOutXmlDoc, String cTrandate) throws Exception {
		cLogger.info("Into HandContBlc.dealFile()...");		
		JdomUtil.print(cOutXmlDoc);
		cOutXmlDoc=HandContOutXsl.newInstance().getCache().transform(cOutXmlDoc);
		String ttLocalDir = cThisBusiConf.getChildText("localDir");
		String outHead = null;//头记录
		String serialNo="";//序号
		String fileName = "";//文件名称
		String out ="";
		String outBody="";
	    int  maxNo = 100;//一个txt中允许最多的明细记录数  测试的话，只需要把这个值换掉即可
		//头记录处理
		Element tRoot = cOutXmlDoc.getRootElement();
		Element tHead = tRoot.getChild("Head");
		System.out.println(tRoot+"           ===========      "+tHead);
		String tFlag = tHead.getChildText("Flag");//交易成功标志
		String tComCode = "3103";//保险公司代码
		String tFuncFlag = cThisBusiConf.getChildText("funcFlag");//交易代码
		String tCount = null;//明细记录总笔数
		String tSumMoney = null;//总金额
	    Element tBody = tRoot.getChild("Body");
	    List<Element> list  = tBody.getChildren("Detail");
    	tCount = Integer.toString(list.size());
    	tSumMoney="0000";
    	long sum=0;
    	  	
	    cLogger.info("生成返回文件头记录"+outHead);
	    cLogger.info("生成返回文件总记录"+list.size());
	    
	    /*
	     * 文件第一行：（汇总信息）
			格式：保险公司代码|银行代码|总记录数|总金额|
			文件其他内容：（明细记录）
			保单号|银行省市代码|网点号|投保单号|投保人证件类型|投保人证件号码|投保人名称|投保人与被保人关系|被保人证件类型|
			被保人证件号码|被保人名称|投保份数|总保费|投保日期|保单到期日|承保日期|缴费账户|银行人员资格证编号|个性化费率|保单印刷号|附加险个数|
	     */
	    
	    if(list.size()!=0){
	    /*
		 * 通过总数和最大数取得所能生成文件的数量
		 */
	    for (int i = 0; i <=list.size()/maxNo; i++) {
			if(i<10){
				serialNo ="0" + Integer.toString(i);
				fileName = "SRESULT"+tComCode+"."+cTrandate;
			}else{
				serialNo = Integer.toString(i);
				fileName = "SRESULT"+tComCode+"."+cTrandate;
			}
			File file = new File(ttLocalDir+"/"+fileName);
			//如果文件不存在穿件文件
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			/*
			 * 此循环是向第i个文件中流出(i+1)*maxNo - i*maxNo个明细记录
			 * 第i个文件里取的明细记录是从第i*maxNo个至第(i+1)*maxNo个,
			 * 当取的值和总数相同时，则跳出此循环 
			 */
			for (int j = i*maxNo; j <(i+1)*maxNo; j++) {
				if(j==list.size()){
					break;
				}
				Element tDetail = list.get(j);

				String tZoneNo = tDetail.getChildText("ZoneNo");
				String tNodeNo = tDetail.getChildText("NodeNo");
				String tAppName = tDetail.getChild("Appnt").getChildText("AppntName");
				String tAppIDtype = tDetail.getChild("Appnt").getChildText("IDType");
				String tAppIDno = tDetail.getChild("Appnt").getChildText("IDNo");
				String tContNo = tDetail.getChildText("ContNo");
				String tApplyDate = tDetail.getChildText("ApplyDate");
				tApplyDate=DateUtil.date10to8(tApplyDate);
				String tSignDate = tDetail.getChildText("SignDate");
				tSignDate=DateUtil.date10to8(tSignDate);
				String tRelationToInusre = tDetail.getChildText("RelationToInusre");
				String tInsuName = tDetail.getChild("Insured").getChildText("Name");
				String tInsuDtype = tDetail.getChild("Insured").getChildText("IDType");
				String tInsuDno = tDetail.getChild("Insured").getChildText("IDNo");
				String tPrem = tDetail.getChildText("Prem");
				//处理金额
				long prem=Long.parseLong(tPrem);
				sum=sum+prem;
				tPrem = NumberUtil.fenToYuan(tPrem);
				String tAmnt = tDetail.getChildText("Amnt");
				tAmnt=NumberUtil.fenToYuan(tAmnt);
				String tAccNo = tDetail.getChildText("AccNo");
				String tContEndDate = tDetail.getChildText("ContEndDate");
				tContEndDate=DateUtil.date10to8(tContEndDate);
				String tMult = tDetail.getChildText("Mult");
				double dmult=Double.parseDouble(tMult);
				int imult=(int) dmult;
				tMult=String.valueOf(imult);
				String tProposalPrtNo = tDetail.getChildText("ProposalPrtNo");
				String tContPrtNo = tDetail.getChildText("ContPrtNo");
				String tSpecialRate = tDetail.getChildText("SpecialRate");
				String tSaleCertNo = tDetail.getChildText("SaleCertNo");
				String tEtraRiskCnt = tDetail.getChildText("EtraRiskCnt");

				outBody += tContNo+"|"+tZoneNo+"|"+tNodeNo+"|"+tProposalPrtNo+"|"+tAppIDtype+"|"+tAppIDno+"|"+tAppName+"|"+tRelationToInusre+"|"+
					tInsuDtype+"|"+tInsuDno+"|"+tInsuName+"|"+tMult+"|"+tPrem+"|"+tAmnt+"|"+tApplyDate+"|"+tContEndDate+"|"+tSignDate+"|"+
					tAccNo+"|"+tSaleCertNo+"|"+tSpecialRate+"|"+tContPrtNo+"|"+tEtraRiskCnt+"|"+"\n";
			}
			//输出记录至txt文件
			tSumMoney=String.valueOf(sum);
			tSumMoney=NumberUtil.fenToYuan(tSumMoney);
			System.out.println("总金额："+tSumMoney);
			if(tSumMoney.equals("")||tSumMoney==null){
				tSumMoney="0.00";
			}
			outHead = tComCode+"|"+"03"+"|"+tCount+"|"+tSumMoney+"|"+"\n";
			out = outHead + outBody;
			cLogger.info("第"+i+"个文件生成返回文件总记录"+out);
			byte[] m = out.getBytes("GBK");
			try {
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(m);
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				cLogger.error("找不到文件");
			} catch (IOException e) {
				e.printStackTrace();
				cLogger.error("I/O异常");
			}
			//删除文件，避免生成多余文件
			if(i*maxNo == list.size()){
				file.delete();
			}
			//清空明细记录和总记录
			outBody="";
			out="";
		}
	  }else{
		  	fileName = "SRESULT"+tComCode+"."+cTrandate;
	    	File file = new File(ttLocalDir+"/"+fileName);
			//如果文件不存在创建文件
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//输出记录至txt文件
			tCount="0";
			tSumMoney="0.00";
			outHead = tComCode+"|"+"03"+"|"+tCount+"|"+tSumMoney+"|"+"\n";
			out = outHead;
			cLogger.info("只返回头文件，核心未返回退保值"+out);
			byte[] m = out.getBytes("GBK");
			try {
				FileOutputStream fos = new FileOutputStream(file);
				fos.write(m);
				fos.flush();
				fos.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				cLogger.error("找不到文件");
			} catch (IOException e) {
				e.printStackTrace();
				cLogger.error("I/O异常");
			}
	  }
		System.out.println("文件名："+ttLocalDir+"/"+fileName);
		
		AbcFileTransUtil util = new AbcFileTransUtil(ttLocalDir,ttLocalDir);
		util.upLoadFile(fileName,"02");
		cLogger.info("Out HandContBlc.dealFile()...");
	}
}
