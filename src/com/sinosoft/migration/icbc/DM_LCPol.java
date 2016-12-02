package com.sinosoft.migration.icbc;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Element;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.DBConnFactory;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
//import com.sinosoft.midplat.icbc.format.NewContAll;
import com.sinosoft.migration.DMUtil;
import com.sinosoft.utility.ElementLis;

public class DM_LCPol extends TimerTask implements XmlTag {
	private static final Logger cLogger = Logger.getLogger(DM_LCPol.class);

	public void run() {
		long mStartMillis = System.currentTimeMillis();

		Connection mOutConn = null;
		Statement mOutStmt = null;
		ResultSet mOutResultSet = null;
		Connection mLocalConn = null;
		try {
			Element tMidplatRoot = MidplatConf.newInstance().getConf()
					.getRootElement();
			mOutConn = new DBConnFactory(tMidplatRoot.getChild("SqlServerDB"))
					.getConn();
			if (mOutConn.isClosed()) {
				System.out.println("关着的");
			}
			mOutStmt = mOutConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			mOutStmt.close();

			String sql = DMUtil
					.getSql4LocalFile("C:\\Users\\asus\\Documents\\SQL Server Management Studio\\Projects\\LCPol.sql");
			cLogger.info(sql);
			mOutStmt = mOutConn.createStatement();
			mOutResultSet = mOutStmt.executeQuery(sql);
		
			mLocalConn = new DBConnFactory(tMidplatRoot.getChild(database))
					.getConn();
			mLocalConn.setAutoCommit(false);
			String cDate = DateUtil.getCur10Date();
			String cTime = DateUtil.getCur8Time();
			try {
				String ttSqlStr = null;
				Statement ttLocalStmt = null;

				ttSqlStr = "delete lcpol ";
				cLogger.info(ttSqlStr);
				ttLocalStmt = mLocalConn.createStatement();
				ttLocalStmt.executeUpdate(ttSqlStr);
				ttLocalStmt.close();

				FileOutputStream out = null;
				FileOutputStream lccont = null;
				try {
					out = new FileOutputStream("C:\\Users\\asus\\Desktop\\lcpol_error"+".txt",false);
					lccont = new FileOutputStream("C:\\Users\\asus\\Desktop\\lcpol_utf8"+".sql",false);
				
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} 
				byte[] buff=new byte[]{};
				int tRecordNo = 0;
				while (mOutResultSet.next()) {
					tRecordNo++;
					
					
					cDate = (String) mOutResultSet.getString(60).subSequence(0, 10);
					
					String sContNo = mOutResultSet.getString(3);
					String sCityNo = mOutResultSet.getString(15);
					String sManageCom = DMUtil.getComCode(sCityNo);
					String sBankRiskCode = mOutResultSet.getString(11);
					String sMainRiskCode = DMUtil.getMainRiskCode(mOutResultSet.getString(11));

					String sInsuredNo = DMUtil.getPersonNo(mOutResultSet.getString(22),mOutResultSet.getString(23),mOutResultSet.getString(24).substring(0,10),mOutResultSet.getString(27),mOutResultSet.getString(28));;
					String sAppntNo = DMUtil.getAppntNo(sContNo);
					String sPayEndYearFlag = mOutResultSet.getString(35);
					String sPayEndYear = mOutResultSet.getString(36);
					String sInsuYearFlag = mOutResultSet.getString(37);
					String sInsuYear = mOutResultSet.getString(38);
					
					String sSumPrem = mOutResultSet.getString(61);
					if(sSumPrem == null || "".equals(sSumPrem)){
						sSumPrem = "0.00";
					}
					String sSumAmnt = mOutResultSet.getString(62);
					if(sSumAmnt == null || "".equals(sSumAmnt)){
						sSumAmnt = "0.00";
					}
					String sMainPrem = mOutResultSet.getString(63);
					if(sMainPrem == null || "".equals(sMainPrem)){
						sMainPrem = "0.00";
					}
					String sSubPrem = mOutResultSet.getString(64);
					if(sSubPrem == null || "".equals(sSubPrem)){
						sSubPrem = "0.00";
					}
					String sFirstAddPrem = mOutResultSet.getString(65);
					if(sFirstAddPrem == null || "".equals(sFirstAddPrem)){
						sFirstAddPrem = "0.00";
					}
					
					Element eRiskList = new Element("RiskList");
					if("002".equals(sBankRiskCode)){
						ElementLis Risk = new ElementLis("Risk");	 
							new ElementLis("KindCode",sMainRiskCode,Risk); 
							new ElementLis("RiskCode","NBSP",Risk);
							new ElementLis("RiskAlias",DMUtil.getAXARiskCode("NBSP",sPayEndYear,sPayEndYearFlag),Risk);
							new ElementLis("RiskVersion","002",Risk);
							
							new ElementLis("Prem",sMainPrem,Risk);
							new ElementLis("Amnt",sSumAmnt,Risk);
							new ElementLis("SumPrem",String.valueOf(Double.valueOf(sFirstAddPrem)+Double.valueOf(sMainPrem)),Risk);
							new ElementLis("FirstAddPrem",sFirstAddPrem,Risk);
							
							new ElementLis("PayEndYearFlag",sPayEndYearFlag,Risk);
							new ElementLis("PayEndYear",sPayEndYear,Risk);
							new ElementLis("InsuYearFlag",sInsuYearFlag,Risk);
							new ElementLis("InsuYear",sInsuYear,Risk);
							eRiskList.addContent(Risk);
					}else if("286".equals(sBankRiskCode)){
						ElementLis Risk = new ElementLis("Risk");	 
						new ElementLis("KindCode",sMainRiskCode,Risk); 
						new ElementLis("RiskCode","GHONP",Risk);
						new ElementLis("RiskAlias",DMUtil.getAXARiskCode("GHONP",sPayEndYear,sPayEndYearFlag),Risk);
						new ElementLis("RiskVersion","286",Risk);
						
						new ElementLis("Prem",sMainPrem,Risk);
						new ElementLis("Amnt",sSumAmnt,Risk);
						new ElementLis("SumPrem",String.valueOf(Double.valueOf(sFirstAddPrem)+Double.valueOf(sMainPrem)),Risk);
						new ElementLis("FirstAddPrem",sFirstAddPrem,Risk);
						
						new ElementLis("PayEndYearFlag",sPayEndYearFlag,Risk);
						new ElementLis("PayEndYear",sPayEndYear,Risk);
						new ElementLis("InsuYearFlag",sInsuYearFlag,Risk);
						new ElementLis("InsuYear",sInsuYear,Risk);
						eRiskList.addContent(Risk);
				}else if("004".equals(sBankRiskCode)){
					ElementLis Risk = new ElementLis("Risk");	 
					new ElementLis("KindCode",sMainRiskCode,Risk); 
					new ElementLis("RiskCode","HONPG3",Risk);
					new ElementLis("RiskAlias",DMUtil.getAXARiskCode("HONPG3",sPayEndYear,sPayEndYearFlag),Risk);
					new ElementLis("RiskVersion","004",Risk);
					sSumAmnt = "0.00";
					new ElementLis("Prem",sMainPrem,Risk);
					new ElementLis("Amnt",sSumAmnt,Risk);
					new ElementLis("SumPrem",String.valueOf(Double.valueOf(sFirstAddPrem)+Double.valueOf(sMainPrem)),Risk);
					new ElementLis("FirstAddPrem",sFirstAddPrem,Risk);
					
					new ElementLis("PayEndYearFlag",sPayEndYearFlag,Risk);
					new ElementLis("PayEndYear",sPayEndYear,Risk);
					new ElementLis("InsuYearFlag",sInsuYearFlag,Risk);
					new ElementLis("InsuYear",sInsuYear,Risk);
					eRiskList.addContent(Risk);
			}else if("005".equals(sBankRiskCode)){
				ElementLis Risk = new ElementLis("Risk");	 
				new ElementLis("KindCode",sMainRiskCode,Risk); 
				new ElementLis("RiskCode","MCCIB",Risk);
				new ElementLis("RiskAlias",DMUtil.getAXARiskCode("MCCIB",sPayEndYear,sPayEndYearFlag),Risk);
				new ElementLis("RiskVersion","005",Risk);
				//sSumAmnt = String.valueOf(Double.valueOf(sSumAmnt)/2);
				new ElementLis("Prem",sMainPrem,Risk);
				new ElementLis("Amnt",sSumAmnt,Risk);
				new ElementLis("SumPrem",String.valueOf(Double.valueOf(sFirstAddPrem)+Double.valueOf(sMainPrem)),Risk);
				new ElementLis("FirstAddPrem",sFirstAddPrem,Risk);
				
				new ElementLis("PayEndYearFlag",sPayEndYearFlag,Risk);
				new ElementLis("PayEndYear",sPayEndYear,Risk);
				new ElementLis("InsuYearFlag",sInsuYearFlag,Risk);
				new ElementLis("InsuYear",sInsuYear,Risk);
				
				ElementLis Risk2 = new ElementLis("Risk");	 
				new ElementLis("KindCode",sMainRiskCode,Risk2); 
				new ElementLis("RiskCode","MCCIR",Risk2);
				new ElementLis("RiskAlias",DMUtil.getAXARiskCode("MCCIR",sPayEndYear,sPayEndYearFlag),Risk2);
				new ElementLis("RiskVersion","103",Risk2);
	
				String sPrem = String.valueOf(Double.valueOf(sSumPrem)-Double.valueOf(sMainPrem));
				new ElementLis("Prem",sPrem,Risk2);
				new ElementLis("Amnt",sSumAmnt,Risk2);
				new ElementLis("SumPrem",String.valueOf(sPrem),Risk2);
				new ElementLis("FirstAddPrem",sFirstAddPrem,Risk2);
				
				new ElementLis("PayEndYearFlag",sPayEndYearFlag,Risk2);
				new ElementLis("PayEndYear",sPayEndYear,Risk2);
				new ElementLis("InsuYearFlag",sInsuYearFlag,Risk2);
				new ElementLis("InsuYear",sInsuYear,Risk2);
				eRiskList.addContent(Risk);
				eRiskList.addContent(Risk2);
		}else if("282".equals(sBankRiskCode)){
			ElementLis Risk = new ElementLis("Risk");	 
			new ElementLis("KindCode",sMainRiskCode,Risk); 
			new ElementLis("RiskCode","NHONP",Risk);
			new ElementLis("RiskAlias","NHONP",Risk);
			new ElementLis("RiskVersion","282",Risk);
			sSumAmnt = "0.00";
			new ElementLis("Prem",sMainPrem,Risk);
			new ElementLis("Amnt",sSumAmnt,Risk);
			new ElementLis("SumPrem",String.valueOf(Double.valueOf(sFirstAddPrem)+Double.valueOf(sMainPrem)),Risk);
			new ElementLis("FirstAddPrem",sFirstAddPrem,Risk);
			
			new ElementLis("PayEndYearFlag",sPayEndYearFlag,Risk);
			new ElementLis("PayEndYear",sPayEndYear,Risk);
			new ElementLis("InsuYearFlag",sInsuYearFlag,Risk);
			new ElementLis("InsuYear",sInsuYear,Risk);
			eRiskList.addContent(Risk);
	}else if("283".equals(sBankRiskCode)){
		ElementLis Risk = new ElementLis("Risk");	 
		new ElementLis("KindCode",sMainRiskCode,Risk); 
		new ElementLis("RiskCode","BSP",Risk);
		new ElementLis("RiskAlias","BSP",Risk);
		new ElementLis("RiskVersion","283",Risk);
		//sSumAmnt = "0.00";
		new ElementLis("Prem",sMainPrem,Risk);
		new ElementLis("Amnt",sSumAmnt,Risk);
		new ElementLis("SumPrem",String.valueOf(Double.valueOf(sFirstAddPrem)+Double.valueOf(sMainPrem)),Risk);
		new ElementLis("FirstAddPrem",sFirstAddPrem,Risk);
		
		new ElementLis("PayEndYearFlag",sPayEndYearFlag,Risk);
		new ElementLis("PayEndYear",sPayEndYear,Risk);
		new ElementLis("InsuYearFlag",sInsuYearFlag,Risk);
		new ElementLis("InsuYear",sInsuYear,Risk);
		eRiskList.addContent(Risk);
}else if("003".equals(sBankRiskCode)){
	ElementLis Risk = new ElementLis("Risk");	 
	new ElementLis("KindCode",sMainRiskCode,Risk); 
	new ElementLis("RiskCode","PAC",Risk);
	new ElementLis("RiskAlias",DMUtil.getAXARiskCode("PAC",sPayEndYear,sPayEndYearFlag),Risk);
	new ElementLis("RiskVersion","003",Risk);
	//sSumAmnt = "0.00";
	new ElementLis("Prem",sMainPrem,Risk);
	new ElementLis("Amnt",sSumAmnt,Risk);
	new ElementLis("SumPrem",String.valueOf(Double.valueOf(sFirstAddPrem)+Double.valueOf(sMainPrem)),Risk);
	new ElementLis("FirstAddPrem",sFirstAddPrem,Risk);
	
	new ElementLis("PayEndYearFlag",sPayEndYearFlag,Risk);
	new ElementLis("PayEndYear",sPayEndYear,Risk);
	new ElementLis("InsuYearFlag",sInsuYearFlag,Risk);
	new ElementLis("InsuYear",sInsuYear,Risk);
	eRiskList.addContent(Risk);
}else if("281".equals(sBankRiskCode)){
	ElementLis Risk = new ElementLis("Risk");	 
	new ElementLis("KindCode",sMainRiskCode,Risk); 
	new ElementLis("RiskCode","NHY",Risk);
	new ElementLis("RiskAlias",DMUtil.getAXARiskCode("NHY",sPayEndYear,sPayEndYearFlag),Risk);
	new ElementLis("RiskVersion","281",Risk);
	
	new ElementLis("Prem",sMainPrem,Risk);
	new ElementLis("Amnt",sSumAmnt,Risk);
	new ElementLis("SumPrem",String.valueOf(Double.valueOf(sFirstAddPrem)+Double.valueOf(sMainPrem)),Risk);
	new ElementLis("FirstAddPrem",sFirstAddPrem,Risk);
	
	new ElementLis("PayEndYearFlag",sPayEndYearFlag,Risk);
	new ElementLis("PayEndYear",sPayEndYear,Risk);
	new ElementLis("InsuYearFlag",sInsuYearFlag,Risk);
	new ElementLis("InsuYear",sInsuYear,Risk);
	eRiskList.addContent(Risk);
	
	String sPrem = sSubPrem;
	if(!"0.00".equals(sPrem)&&!"0.0000".equals(sPrem)){
		ElementLis Risk2 = new ElementLis("Risk");	 
		new ElementLis("KindCode",sMainRiskCode,Risk2); 
		new ElementLis("RiskCode","NHYrider(RTU)",Risk2);
		new ElementLis("RiskAlias",DMUtil.getAXARiskCode("NHYrider(RTU)",sPayEndYear,sPayEndYearFlag),Risk2);
		new ElementLis("RiskVersion","102",Risk2);
	
		sSumAmnt="0.00";
		new ElementLis("Prem",sPrem,Risk2);
		new ElementLis("Amnt",sSumAmnt,Risk2);
		new ElementLis("SumPrem",String.valueOf(sPrem),Risk2);
		new ElementLis("FirstAddPrem",sFirstAddPrem,Risk2);
		
		new ElementLis("PayEndYearFlag",sPayEndYearFlag,Risk2);
		new ElementLis("PayEndYear",sPayEndYear,Risk2);
		new ElementLis("InsuYearFlag",sInsuYearFlag,Risk2);
		new ElementLis("InsuYear",sInsuYear,Risk2);
		
		eRiskList.addContent(Risk2);
	}
}
else if("001".equals(sBankRiskCode)){
	ElementLis Risk = new ElementLis("Risk");	 
	new ElementLis("KindCode",sMainRiskCode,Risk); 
	new ElementLis("RiskCode","NHY",Risk);
	new ElementLis("RiskAlias",DMUtil.getAXARiskCode("NHY",sPayEndYear,sPayEndYearFlag),Risk);
	new ElementLis("RiskVersion","001",Risk);
	
	new ElementLis("Prem",sMainPrem,Risk);
	new ElementLis("Amnt",sSumAmnt,Risk);
	new ElementLis("SumPrem",String.valueOf(Double.valueOf(sMainPrem)),Risk);
	new ElementLis("FirstAddPrem","0.00",Risk);
	
	new ElementLis("PayEndYearFlag",sPayEndYearFlag,Risk);
	new ElementLis("PayEndYear",sPayEndYear,Risk);
	new ElementLis("InsuYearFlag",sInsuYearFlag,Risk);
	new ElementLis("InsuYear",sInsuYear,Risk);
	eRiskList.addContent(Risk);
	
	String sPrem = sSubPrem;
	if(!"0.00".equals(sPrem)&&!"0.0000".equals(sPrem)){
		ElementLis Risk2 = new ElementLis("Risk");	 
		new ElementLis("KindCode",sMainRiskCode,Risk2); 
		new ElementLis("RiskCode","NHYrider(RTU)",Risk2);
		new ElementLis("RiskAlias",DMUtil.getAXARiskCode("NHYrider(RTU)",sPayEndYear,sPayEndYearFlag),Risk2);
		new ElementLis("RiskVersion","102",Risk2);
	
		sSumAmnt="0.00";
		new ElementLis("Prem",sPrem,Risk2);
		new ElementLis("Amnt",sSumAmnt,Risk2);
		new ElementLis("SumPrem",String.valueOf(sPrem),Risk2);
		new ElementLis("FirstAddPrem","0.00",Risk2);
		
		new ElementLis("PayEndYearFlag",sPayEndYearFlag,Risk2);
		new ElementLis("PayEndYear",sPayEndYear,Risk2);
		new ElementLis("InsuYearFlag",sInsuYearFlag,Risk2);
		new ElementLis("InsuYear",sInsuYear,Risk2);
		
		eRiskList.addContent(Risk2);
	}
	
	String sPrem101 = sFirstAddPrem;
	if(!"0.00".equals(sPrem101)&&!"0.0000".equals(sPrem101)){
		ElementLis Risk2 = new ElementLis("Risk");	 
		new ElementLis("KindCode",sMainRiskCode,Risk2); 
		new ElementLis("RiskCode","NHYrider(lpsm)",Risk2);
		new ElementLis("RiskAlias",DMUtil.getAXARiskCode("NHYrider(lpsm)","105","5"),Risk2);
		new ElementLis("RiskVersion","101",Risk2);
	
		sSumAmnt="0.00";
		new ElementLis("Prem",sPrem101,Risk2);
		new ElementLis("Amnt",sSumAmnt,Risk2);
		new ElementLis("SumPrem",String.valueOf(sPrem101),Risk2);
		new ElementLis("FirstAddPrem","0.00",Risk2);
		
		new ElementLis("PayEndYearFlag","1",Risk2);
		new ElementLis("PayEndYear","1",Risk2);
		new ElementLis("InsuYearFlag",sInsuYearFlag,Risk2);
		new ElementLis("InsuYear",sInsuYear,Risk2);
		
		eRiskList.addContent(Risk2);
	}
}		
					
else if("009".equals(sBankRiskCode)){
	ElementLis Risk = new ElementLis("Risk");	 
	new ElementLis("KindCode",sMainRiskCode,Risk); 
	new ElementLis("RiskCode","HYG3",Risk);
	new ElementLis("RiskAlias",DMUtil.getAXARiskCode("HYG3",sPayEndYear,sPayEndYearFlag),Risk);
	new ElementLis("RiskVersion","009",Risk);
	
	new ElementLis("Prem",sMainPrem,Risk);
	new ElementLis("Amnt",sSumAmnt,Risk);
	new ElementLis("SumPrem",String.valueOf(Double.valueOf(sMainPrem)),Risk);
	new ElementLis("FirstAddPrem","0.00",Risk);
	
	new ElementLis("PayEndYearFlag",sPayEndYearFlag,Risk);
	new ElementLis("PayEndYear",sPayEndYear,Risk);
	new ElementLis("InsuYearFlag",sInsuYearFlag,Risk);
	new ElementLis("InsuYear",sInsuYear,Risk);
	eRiskList.addContent(Risk);
	
	String sPrem = sSubPrem;
	if(!"0.00".equals(sPrem)&&!"0.0000".equals(sPrem)){
		ElementLis Risk2 = new ElementLis("Risk");	 
		new ElementLis("KindCode",sMainRiskCode,Risk2); 
		new ElementLis("RiskCode","HYG3rider(RTU)",Risk2);
		new ElementLis("RiskAlias",DMUtil.getAXARiskCode("HYG3rider(RTU)",sPayEndYear,sPayEndYearFlag),Risk2);
		new ElementLis("RiskVersion","107",Risk2);
	
		sSumAmnt="0.00";
		new ElementLis("Prem",sPrem,Risk2);
		new ElementLis("Amnt",sSumAmnt,Risk2);
		new ElementLis("SumPrem",String.valueOf(sPrem),Risk2);
		new ElementLis("FirstAddPrem","0.00",Risk2);
		
		new ElementLis("PayEndYearFlag",sPayEndYearFlag,Risk2);
		new ElementLis("PayEndYear",sPayEndYear,Risk2);
		new ElementLis("InsuYearFlag",sInsuYearFlag,Risk2);
		new ElementLis("InsuYear",sInsuYear,Risk2);
		
		eRiskList.addContent(Risk2);
	}
	
	String sPrem106 = sFirstAddPrem;
	if(!"0.00".equals(sPrem106)&&!"0.0000".equals(sPrem106)){
		ElementLis Risk2 = new ElementLis("Risk");
		new ElementLis("KindCode",sMainRiskCode,Risk2); 
		new ElementLis("RiskCode","HYG3rider(lpsm)",Risk2);
		new ElementLis("RiskAlias",DMUtil.getAXARiskCode("HYG3rider(lpsm)","105","5"),Risk2);
		new ElementLis("RiskVersion","106",Risk2);
	
		sSumAmnt="0.00";
		new ElementLis("Prem",sPrem106,Risk2);
		new ElementLis("Amnt",sSumAmnt,Risk2);
		new ElementLis("SumPrem",String.valueOf(sPrem106),Risk2);
		new ElementLis("FirstAddPrem","0.00",Risk2);
		
		new ElementLis("PayEndYearFlag","1",Risk2);
		new ElementLis("PayEndYear","1",Risk2);
		new ElementLis("InsuYearFlag",sInsuYearFlag,Risk2);
		new ElementLis("InsuYear",sInsuYear,Risk2);
		
		eRiskList.addContent(Risk2);
	}
}							
					
					
					List <Element> risk = eRiskList.getChildren("Risk");
					for(int i = 0; i<risk.size(); i++){
						Element RiskEle = risk.get(i);
						
						ArrayList<String []> arrayList = new ArrayList<String[]>();
						arrayList.add(DMUtil.get2String("GrpContNo",mOutResultSet.getString(1)));
						arrayList.add(DMUtil.get2String("GrpPolNo",mOutResultSet.getString(2)));
						arrayList.add(DMUtil.get2String("ContNo",mOutResultSet.getString(3)));
						arrayList.add(DMUtil.get2String("PolNo",sContNo+"-"+String.valueOf(i)));
						arrayList.add(DMUtil.get2String("ProposalContNo",mOutResultSet.getString(5)));
						
						arrayList.add(DMUtil.get2String("ProposalNo",sContNo+"-"+String.valueOf(i)));
						arrayList.add(DMUtil.get2String("PrtNo",mOutResultSet.getString(7)));
						arrayList.add(DMUtil.get2String("ContType",mOutResultSet.getString(8)));
						arrayList.add(DMUtil.get2String("PolTypeFlag",mOutResultSet.getString(9)));
						arrayList.add(DMUtil.get2String("MainPolNo",mOutResultSet.getString(10)));
						
						arrayList.add(DMUtil.get2String("KindCode",RiskEle.getChildText("KindCode")));
						arrayList.add(DMUtil.get2String("RiskCode",RiskEle.getChildText("RiskCode")));
						arrayList.add(DMUtil.get2String("RiskAlias",RiskEle.getChildText("RiskAlias")));
						arrayList.add(DMUtil.get2String("RiskVersion",RiskEle.getChildText("RiskVersion")));
						arrayList.add(DMUtil.get2String("ManageCom",sManageCom));
									
						arrayList.add(DMUtil.get2String("AgentCom",mOutResultSet.getString(16)));
						arrayList.add(DMUtil.get2String("AgentCode",mOutResultSet.getString(17)));
						arrayList.add(DMUtil.get2String("AgentGroup",mOutResultSet.getString(18)));
						arrayList.add(DMUtil.get2String("SaleChnl",mOutResultSet.getString(19)));
						arrayList.add(DMUtil.get2String("Handler",mOutResultSet.getString(20)));
		
						arrayList.add(DMUtil.get2String("InsuredNo",sInsuredNo));
						arrayList.add(DMUtil.get2String("InsuredName",mOutResultSet.getString(22)));
						arrayList.add(DMUtil.get2String("InsuredSex",mOutResultSet.getString(23)));
						arrayList.add(DMUtil.get2String("InsuredBirthDay","DATE'"+mOutResultSet.getString(24).substring(0,10)+"'"));
						arrayList.add(DMUtil.get2String("InsuredAppAge",""));
						
						arrayList.add(DMUtil.get2String("InsuredPeoples",mOutResultSet.getString(26)));
						arrayList.add(DMUtil.get2String("InsuredIDType",mOutResultSet.getString(27)));
						arrayList.add(DMUtil.get2String("InsuredID",mOutResultSet.getString(28)));
						arrayList.add(DMUtil.get2String("OccupationType",mOutResultSet.getString(29)));
						arrayList.add(DMUtil.get2String("AppntNo",sAppntNo));
						
						arrayList.add(DMUtil.get2String("AppntName",mOutResultSet.getString(31)));
						arrayList.add(DMUtil.get2String("CValiDate","DATE'"+mOutResultSet.getString(32).substring(0,10)+"'"));
						arrayList.add(DMUtil.get2String("SignDate","DATE'"+mOutResultSet.getString(33).substring(0,10)+"'"));
						arrayList.add(DMUtil.get2String("GetYear",mOutResultSet.getString(34)));
						arrayList.add(DMUtil.get2String("PayEndYearFlag",RiskEle.getChildText("PayEndYearFlag")));
						
						arrayList.add(DMUtil.get2String("PayEndYear",RiskEle.getChildText("PayEndYear")));
						arrayList.add(DMUtil.get2String("InsuYearFlag",RiskEle.getChildText("InsuYearFlag")));
						arrayList.add(DMUtil.get2String("InsuYear",RiskEle.getChildText("InsuYear")));
						arrayList.add(DMUtil.get2String("SpecifyValiDate",mOutResultSet.getString(39)));
						arrayList.add(DMUtil.get2String("PayIntv",mOutResultSet.getString(40)));
						
						arrayList.add(DMUtil.get2String("Mult",mOutResultSet.getString(41)));
						
						arrayList.add(DMUtil.get2String("StandPrem",RiskEle.getChildText("Prem")));
						arrayList.add(DMUtil.get2String("FirstAddPrem",RiskEle.getChildText("FirstAddPrem")));
						arrayList.add(DMUtil.get2String("Prem",RiskEle.getChildText("Prem")));
						arrayList.add(DMUtil.get2String("SumPrem",RiskEle.getChildText("SumPrem")));
						arrayList.add(DMUtil.get2String("INPrem",RiskEle.getChildText("Prem")));
						arrayList.add(DMUtil.get2String("INAmnt",RiskEle.getChildText("Amnt")));
						arrayList.add(DMUtil.get2String("Amnt",RiskEle.getChildText("Amnt")));
						
						arrayList.add(DMUtil.get2String("AddPrem",("0.00")));
						arrayList.add(DMUtil.get2String("AddAmnt",("0.00")));
						arrayList.add(DMUtil.get2String("RISKAMNT","0.00"));
						arrayList.add(DMUtil.get2String("BNFFLAG","1"));
						arrayList.add(DMUtil.get2String("SMOKEFLAG","N"));
						arrayList.add(DMUtil.get2String("APPROVEFLAG","0"));
						arrayList.add(DMUtil.get2String("APPROVECODE","ybt"));
	
						
						arrayList.add(DMUtil.get2String("UWFlag",mOutResultSet.getString(56)));
						arrayList.add(DMUtil.get2String("PolApplyDate","DATE'"+mOutResultSet.getString(57).substring(0,10)+"'"));
						arrayList.add(DMUtil.get2String("APPFLAG",mOutResultSet.getString(58)));
						
						
						arrayList.add(DMUtil.get2String("OPERATOR","sys"));
						arrayList.add(DMUtil.get2String("MAKEDATE","DATE'"+cDate+"'"));
						arrayList.add(DMUtil.get2String("MAKETIME",cTime));
						arrayList.add(DMUtil.get2String("MODIFYDATE","DATE'"+cDate+"'"));
						arrayList.add(DMUtil.get2String("MODIFYTIME",cTime));
	
						ttSqlStr = DMUtil.getSBSql("LCPol", arrayList);
						cLogger.info(ttSqlStr);
						cLogger.info(tRecordNo);
						
						cLogger.info(ttSqlStr);
						cLogger.info(tRecordNo);
						//ChangeCharset changeCharset = new ChangeCharset();
						//String ss = new ChangeCharset().toUTF_8(ttSqlStr+";"+"\n");
							buff=(ttSqlStr+";"+"\n").getBytes("utf-8");
						    lccont.write(buff,0,buff.length);
						    if(tRecordNo%100 == 0){
								 //mLocalConn.commit();
								 buff=("commit"+";"+"\n").getBytes("utf-8");
								    lccont.write(buff,0,buff.length);
							 }
						
						    ttLocalStmt = mLocalConn.createStatement();
						 try{
							 if (1 != ttLocalStmt.executeUpdate(ttSqlStr)) {
								 throw new MidplatException("插入失败！");
						 }
						 
						 
						
						 ttLocalStmt.close();
						 }catch (Exception e) {
							 cLogger.info(ttSqlStr);
						System.out.println("有问题保单号:"+sContNo);
							buff=(sContNo+e.getMessage()).getBytes();    
						     out.write(buff,0,buff.length);
							e.printStackTrace();
						ttLocalStmt.close();
						}
					
					}
				}
				cLogger.info("本次同步记录数：" + tRecordNo);
				mLocalConn.commit();
			} catch (Exception ex) {
				mLocalConn.rollback();
				throw ex;
			}

		} catch (Throwable ex) {
			ex.printStackTrace();
		} finally {
			if (null != mOutResultSet) {
				try {
					mOutResultSet.close();
				} catch (SQLException ex) {
					cLogger.error("关闭OutResultSet异常！", ex);
				}
			}
			if (null != mOutStmt) {
				try {
					mOutStmt.close();
				} catch (SQLException ex) {
					cLogger.error("关闭OutStatement异常！", ex);
				}
			}
			if (null != mOutConn) {
				try {
					mOutConn.close();
				} catch (SQLException ex) {
					cLogger.error("关闭OutConnection异常！", ex);
				}
			}
			if (null != mLocalConn) {
				try {
					mLocalConn.close();
				} catch (SQLException ex) {
					cLogger.error("关闭LocalConnection异常！", ex);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger
				.getLogger("com.sinosoft.midplat.bat.NodeUpdate.main");
		mLogger.info("程序开始...");

		DM_LCPol mBatch = new DM_LCPol();

		mBatch.run();

		mLogger.info("成功结束！");
	}
}
