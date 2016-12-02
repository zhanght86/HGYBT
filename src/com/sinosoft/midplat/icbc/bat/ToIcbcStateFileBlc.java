package com.sinosoft.midplat.icbc.bat;

import java.io.FileInputStream;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import javax.crypto.Cipher;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import com.sinosoft.midplat.bat.ToBankBalance;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.icbc.IcbcConf;
import com.sinosoft.midplat.icbc.net.IcbcKeyCache;
import com.sinosoft.utility.ExeSQL;

public class ToIcbcStateFileBlc extends ToBankBalance {
	public ToIcbcStateFileBlc() {
		super(IcbcConf.newInstance(), "1204");
	}

	protected String getFileName() {
		Element mBankEle = cThisConfRoot.getChild("bank");
		return "TOICBC" + mBankEle.getAttributeValue("insu")
				+ mBankEle.getAttributeValue(id)
				+ DateUtil.getDateStr(cTranDate, "yyyyMMdd") + "05.txt";
	}
		
	/**
	 * 生成投保单状态变更文件
	 * @param cOutXmlDoc
	 * @return
	 */
	public boolean createFile(Document cOutXmlDoc,int date){
		cLogger.info("Into IcbcNoRealStateBack.createStateFile()...");
		
		try {
		List<Element> mStateDetailEles = cOutXmlDoc.getRootElement().getChild(Body).getChildren("StateDetail");
		
		String[][] tStateDTLMSG = new String[mStateDetailEles.size()][6];
		String sql=null;
		for(int i = 0;i<mStateDetailEles.size();i++){	
			Element dtl = (Element) mStateDetailEles.get(i);
			
			String zoneNo = dtl.getChildText(NodeNo).substring(0,5);//核心的NodeNo是地区码（5位）+网点码（位）的，所以返回给银行地区码的话需截取前五位地区码即可
			String insuId  =dtl.getChildText("InsuId");
			String tranNo = null;
			String proposalPrtNo = dtl.getChildText(ProposalPrtNo);
			sql="select BankTranno from NoRealBlcDtl where proposalPrtNo='"+proposalPrtNo+"'";
			tranNo=new ExeSQL().getOneValue(sql);
			String appntName  =dtl.getChildText("AppntName");
			String state = dtl.getChildText(State);
			String reMark  = dtl.getChildText("ReMark");		
			cLogger.info("第"+(i+1)+"行数据:NodeNo:"+zoneNo+",InsuId:"+insuId+",TranNo:"+
					tranNo+",ProposalPrtNo:"+proposalPrtNo+",AppntName:"+appntName+",State:"+
					state+",ReMark:"+reMark);
			tStateDTLMSG[i]=new String[]{zoneNo+"|",insuId+"|",tranNo+"|",proposalPrtNo+"|",appntName+"|",state+"|",reMark+"|"};
		}
		
		
		String tranDate = String.valueOf(date);
		String fileName = "TOICBC050"+"01"+tranDate+"05.txt";
		
		CreateTxtFile createFile = new CreateTxtFile();
		
			createFile.writeFile(tStateDTLMSG,"1204",tranDate,fileName);
		} catch (Exception e) {
			cLogger.info("生产投保单状态变更文件失败！"+e.getMessage());
			e.printStackTrace();
			return false;
		}
		cLogger.info("Out IcbcNoRealStateBack.createStateFile()!");
		return true;
	}

	

	public static void main(String[] args) throws Exception {}
}
