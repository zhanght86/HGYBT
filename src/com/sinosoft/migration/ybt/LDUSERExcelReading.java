package com.sinosoft.migration.ybt;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.sinosoft.lis.encrypt.LisIDEA;
import com.sinosoft.midplat.common.MidplatUtil;

import jxl.Sheet;
import jxl.Workbook;
/**
 * 
 * @author zp-wmhx@163.com
 * @since 1.0
 */
public class LDUSERExcelReading {

	/**
	 * 
	 * @param args
	 */
	public static void main(String args[]) { 
		new LDUSERExcelReading();
	}


	public LDUSERExcelReading() {
		String sourcefile ="D:\\YBT_WORKSPACE\\金盛\\A-需求文档\\A-数据移植\\User.xls";
		String sheetName = "Sheet1";
		int sum=0;
		 FileOutputStream out = null;
		try {
			out = new FileOutputStream("D:\\YBT_WORKSPACE\\金盛\\A-需求文档\\A-数据移植\\DM文件\\User.sql",false);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		LisIDEA tIdea = new LisIDEA();
		try {
			
			 byte[] buff=new byte[]{};
			//getCell(i,j)  => 第j行的第i列 不要搞反了 

			Sheet sheet = Workbook.getWorkbook(new FileInputStream(sourcefile)).getSheet(0);
			for (int j = 1; j < sheet.getRows(); j++) {//行
				String UserCode = sheet.getCell(0,j).getContents();
				String UserName = sheet.getCell(1,j).getContents();
				
				if(UserName == null || "".equals(UserName)){
					UserName = UserCode;
				}
				
				String PassWord = tIdea.encryptString(UserCode);
				String ComCode = sheet.getCell(3,j).getContents();
				
				if("028".equals(ComCode)){
					ComCode = "86";
				}else if("001".equals(ComCode)||"004".equals(ComCode)){
					ComCode = "8601";
				}else if("002".equals(ComCode)){
					ComCode = "8602";
				}else if("003".equals(ComCode)){
					ComCode = "8603";
				}else
					ComCode = MidplatUtil.getManagecom4ac(ComCode);
				
				String MenuGrp = sheet.getCell(4,j).getContents();
				
				if("系统管理员".equals(MenuGrp)){
					MenuGrp = "001";
				}else if("Operation".equals(MenuGrp)){
					MenuGrp = "002";
				}else if("Finance".equals(MenuGrp)){
					MenuGrp = "004";
				}else if("Distr".equals(MenuGrp)){
					MenuGrp = "003";
				}else if("DistrSer".equals(MenuGrp)){
					MenuGrp = "005";
				}else if("UW".equals(MenuGrp)){
					MenuGrp = "007";
				}else if("CitiBD".equals(MenuGrp)){
					MenuGrp = "006";
				}
				
				if(UserName == null || "".equals(UserName)){
					UserName = UserCode;
				}
				
				String sql = "Insert into lduser" + "(UserCode, UserName, ComCode, PassWord, UserDescription, UserState, Operator,MakeDate,MakeTime) "
				+ "Values  " 
				+ "('" + UserCode + "', '" + UserName + "', '" + ComCode + "', '" + PassWord + "', '" + UserName + "', '" + "0" 
				+  "', '" + "admin" + "', date'" + "2011-12-13" + "', '" + "15:31:13" + "');"+'\n';
				
				
				sql = sql + "Insert into LDUSERTOMENUGRP" + "(UserCode, MenuGrpCode) "
				+ "Values  " 
				+ "('" + UserCode + "', '" + MenuGrp + "');"+'\n';
				
				 buff=sql.getBytes();  
		           
		        out.write(buff,0,buff.length); 
				System.out.println(sql);
				sum++;
			}
			System.out.println("commit;");
			System.out.println(sum);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
