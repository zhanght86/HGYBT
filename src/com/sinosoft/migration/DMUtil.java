package com.sinosoft.migration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;


public class DMUtil {
	
	public static String getSBSql(String sTable,ArrayList arrayList){
		StringBuffer sbSql = new StringBuffer("Insert into " + sTable + "( ");
		
		for(int i=0;i<arrayList.size();i++){
			String [] filenames = (String[]) arrayList.get(i);
			if(i==arrayList.size()-1){
				sbSql.append(filenames[0]).append(" ");
			}else{
				sbSql.append(filenames[0]).append(", ");
			}
		}
		
		sbSql.append(") values (" );
		 
		for(int i=0;i<arrayList.size();i++){
			String [] filenames = (String[]) arrayList.get(i);
			if(i==arrayList.size()-1){
				if(filenames[1].length()>=4 && filenames[1].startsWith("DATE")){
					sbSql.append(filenames[1]).append(" ");
				}else{
					sbSql.append("'").append(filenames[1]).append('\'').append(" ");
				}
			}else{
				if(filenames[1] != null && filenames[1].length()>=4 && filenames[1].startsWith("DATE")){
					sbSql.append(filenames[1]).append(", ");
				}else{
					sbSql.append("'").append(filenames[1]).append('\'').append(", ");
				}
				
			}
		}
		sbSql.append(")" );
		
		return sbSql.toString();
	}
	
	public static String[] get2String(String sColumn,String sValue){
		
		String [] ss = new String [2];
		ss[0] = sColumn;
		ss[1] = sValue;
		return ss;
		
	}
	
	public static String getPersonNo(String sName,String sSex,String sBirthDay,String sIDType,String sIDNo){
		
		String tsql = " select customerno from ldperson where 1=1 "
			+ " and name = '" + sName + "'"
			+ " and sex = '"
			+ sSex
			+ "'"
			+ " and IDType = '" + sIDType + "'"
			+ " and IDNO = '" + sIDNo + "'"
			+ " and Birthday = DATE'" + sBirthDay + "'";
		String PersonNo = new ExeSQL().getOneValue(tsql);
		
		return PersonNo;
		
	}
	
	public static String getAddressNo(String sCustomer,String kind,String contno){
		
		String tsql = " select ADDRESSNO from LCADDRESS where 1=1 "
			+ " and customerno = '" + sCustomer + "'"
			+ " and city = '" + contno + "'"
			+ " and province = '" + kind + "'";
		String AddressNo = new ExeSQL().getOneValue(tsql);
		
		return AddressNo;
		
	}
	
	public static String getAXAUWFlag(String sContNo){
		
		String tsql = " select YSTUA from YBTNOREALPOLICIES where 1=1 "
			+ " and YNO = '" + sContNo + "' ORDER BY YMDAT DESC";
		String AXAUWFlag = new ExeSQL().getOneValue(tsql);
		if(AXAUWFlag == null){
			AXAUWFlag = "";
		}
		return AXAUWFlag;	
	}
	

	
	public static String getMainRiskCode(String sRiskCode){
		
		String tsql = " select insu_code from BankAndInsuMap where 1=1 and TranCom='011'"
			+ " and Tran_Code = '" + sRiskCode + "'";
		String RiskCode = new ExeSQL().getOneValue(tsql);
		if(sRiskCode.equals("281")){
			RiskCode = "NHY";
		}
		return RiskCode;
	}
	
	/**
	 * 数据提交处理
	 * @return 
	 * @throws MidplatException
	 */
	public static void submit(MMap tMMap) throws MidplatException
	{
		VData mSubmitVData = new VData();
		mSubmitVData.add(tMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, ""))
		{
			throw new MidplatException(mPubSubmit.mErrors.getFirstError());
		}
	}
	
	public static String getAccName(String sAccCode){
		
		String tsql = " select codename from ldcode where 1=1 and codetype='account_code'"
			+ " and Code = '" + sAccCode + "'";
		String AccName = new ExeSQL().getOneValue(tsql);
		
		return AccName;
	}
	
	public static String getAppntNo(String contno){
		
		String tsql = " select appntno from lcappnt where 1=1 "
			+ " and contno = '" + contno + "'";
		String AppntNo = new ExeSQL().getOneValue(tsql);
		
		return AppntNo;
		
	}
	
	public static String getComCode(String tCityNo) throws MidplatException {
		boolean retFlag = true;

		String mSqlStr = new StringBuilder(
				"select comcode from LDCOM where comgrade='04' and 1=").append("1")
				.append(" and SUBSTR(ComCode,7,3)='").append(tCityNo)
				.append('\'').toString();
		String comcode = new ExeSQL().getOneValue(mSqlStr);
		if(comcode == null || "".equals(comcode)){
			throw new MidplatException(tCityNo+"查询机构为空");
		}
		return comcode;
	}
	
//	public static void main(String [] args){
//		
//		ArrayList arrayList = new ArrayList();
//		String [] filename1 = new String [2];
//		filename1[0] = "ContNo";
//		filename1[1] = "101-00009111";
//		String [] filename2 = new String [2];
//		filename2[0] = "CITYCODE";
//		filename2[1] = "101-00009111";
//		String [] filename3 = new String [2];
//		filename3[0] = "SYSFLAG";
//		filename3[1] = "101-00009111";
//		String [] filename4 = new String [2];
//		filename4[0] = "MANAGECOM";
//		filename4[1] = "";
//		String [] filename5 = new String [2];
//		filename5[0] = "MAKEDATE";
//		filename5[1] = "DATE('14-12-2011', 'dd-mm-yyyy')";
//		arrayList.add(filename1);
//		arrayList.add(filename2);
//		arrayList.add(filename3);
//		arrayList.add(filename4);
//		arrayList.add(filename5);
//		
//		System.out.println(DMUtil.getSBSql("LKContNo", arrayList));
//		
//	}
	
	public static String getSql4LocalFile(String path){
		StringBuffer sql = new StringBuffer("");
		 File file = new File(path);
	        BufferedReader reader = null;
	        try {
	            reader = new BufferedReader(new FileReader(file));
	            String tempString = null;
	            int line = 1;
	            // 一次读入一行，直到读入null为文件结束
	            while ((tempString = reader.readLine()) != null) {
	                // 显示行号
	               // System.out.println("line " + line + ": " + tempString);
	                line++;
	                sql.append(" ").append(tempString).append(" ");
	            }
	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (reader != null) {
	                try {
	                    reader.close();
	                } catch (IOException e1) {
	                }
	            }
	        }
		
		return sql.toString();
	}
	
	
	public static String getAXARiskCode(String RiskCode,String PayEndYear,String PayEndYearFlag){

		String sql = " select * from LmRiskParams where RiskCode = '" + RiskCode;
		sql += "' and PayEndYear = '" + PayEndYear + "' and PayEndYearFlag = '" + PayEndYearFlag+"'";
		String mAXARiskCode = new ExeSQL().getOneValue(sql);
		if(mAXARiskCode == null || "".equals(mAXARiskCode)){
			mAXARiskCode = RiskCode;
		}
	    return mAXARiskCode;
	}
	
	public static SSRS getAXAAgentCode(String sNodeNo){
		
		String sql = " select n.unitcode||'-'||'03'||'-'||n.agentcode,(select t.agentname from axaagent t where t.agentcode=n.agentcode) from nodemap n where TranCOm = 11 and zoneno =  '" + sNodeNo.substring(4, 9)+"' "
		+ "and nodeno =  '" + sNodeNo.substring(10, 15)+"'";
		SSRS SS = new ExeSQL().execSQL(sql);

	    return SS;
	}

	public static void main(String args[]){
		String SS = "000000-04-111111";
		//System.out.print(SS.substring(4, 9));
		System.out.print(SS.substring(10, 16));
	}
}
