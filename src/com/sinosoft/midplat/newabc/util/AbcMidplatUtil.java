package com.sinosoft.midplat.newabc.util;

import java.io.ByteArrayInputStream;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.exception.MidplatException;

/**
 * @ClassName: AbcMidplatUtil
 * @Description: 农行中间平台处理类
 * @author sinosoft
 * @date 2017-3-2 下午2:29:07
 */
public class AbcMidplatUtil implements AbcXmlTag {

	//生成一个本类的日志对象
	private final static Logger cLogger = Logger.getLogger(AbcMidplatUtil.class);
	
	//将字符串按照指定的分隔字符进行拆分,返回从指定序号的分隔符到前一个分隔符之间的字符串
	public static String decodeStr(String strMain, String strDelimiters,
			int intSerialNo) {
		int intIndex = 0; /*分隔符出现在主字符串中的起始位置*/
		int intCount = 0; /*在扫描主字符串的过程中,第几次遇到分隔符字符串*/
		String strReturn = ""; /*作为返回值的字符串*/

		if (strMain.length() < strDelimiters.length())
			return ""; /*若主字符串比分隔符串还要短的话,则返回空字符串*/

		intIndex = strMain.indexOf(strDelimiters);
		if (intIndex == -1)
			return ""; /*若主字符串中不存在分隔符,则返回空字符串*/

		while (intIndex != -1) /*未找到分隔符时退出循环,并返回空字符串*/
		{
			strReturn = strMain.substring(0, intIndex);
			intCount++;
			if (intCount == intSerialNo) {
				if (intIndex == 0)
					return "";
				else
					return strReturn.trim().replaceAll("/", "");
			}
			strMain = strMain.substring(intIndex + 1);
			intIndex = strMain.indexOf(strDelimiters);
		}
		return "";
	}

	public static String rpad(String strValue, int intLength, char tmp) {

		int strLen = strValue.getBytes().length;
		String strReturn = "";
		if (strLen > intLength)
			strReturn = strValue.substring(0, intLength);
		else {
			strReturn = strValue;
			for (int i = strLen; i < intLength; i++)
				strReturn = strReturn + tmp;
		}
		return new String(strReturn);
	}

	/**
	 * 字符串8位长度
	 * @param strValue 字符串值
	 * @param intLength  长度
	 * @param tmp  临时
	 * @return
	 */
	public static String lpad(String strValue, int intLength, char tmp) {
		//int strLen = strValue.length();  
		//字符串值长度
		int strLen = strValue.getBytes().length;
		//字符串返回值
		String strReturn = "";
		//字符串值长度大于指定长度
		if (strLen > intLength)
			strReturn = strValue.substring(0, intLength);//截取前指定长度个字符
		else {
			strReturn = strValue;//保存原字符串值
			//遍历
			for (int i = strLen; i < intLength; i++)
				strReturn = tmp + strReturn;
		}
		return new String(strReturn);
	}

	//递归获取竖线分割符中的字段
	public static String getValue(String str, int num, int num1, int num2) //num1和num2开始位置为1
	{

		  //System.out.println("str" + str);
		  int RealNum=0;
		  int temp=0 ;
		  int i=0;
		  int j=0;
		  String tmpStr1="";
		  if(num >=45 )   //受益人
		  {
		    j=10;        //一个受益人位数
		    RealNum=45;
		    tmpStr1 =decodeStr(str,"|", 45);
		    
		    //System.out.println("受益人个数:["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	    
		    i = 45;
		  }
		  if(num >=75)    //缴费账户信息
		  {
		    j=3;
		    RealNum = RealNum +30;
		    tmpStr1 =decodeStr(str,"|", RealNum);
		    //System.out.println("缴费账户信息["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	  
		     i = 75;
		  }
		  if(num >=82)   //现金价值表
		  {
		    j=1;
		    RealNum = RealNum + 7;
		    tmpStr1 =decodeStr(str,"|", RealNum);
		    //System.out.println("现金价值表["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	
		    i = 82;
		  }	  
		  if(num >=83)    //附加险  num1表示第几个附加险层面，num2表示改层面的值（除了现金价值表）,num3表示该层面现金价值项
		  {
		    RealNum = RealNum + 1;
		    tmpStr1 =decodeStr(str,"|", RealNum);
//		    System.out.println("附加险个数["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    if(num != 83){
		    	for(int k = 1;k <= temp;k ++){
		    		String tmpYears = decodeStr(str,"|", RealNum + 9); //附加险现金价值年度数
		    		RealNum = RealNum + 9 + Integer.parseInt(tmpYears);
		    	}
		    }
		    else{
		    	 for(int m = 1;m <= num1; m++){ // 第几层
		 	    	if(m != num1){
		 	    		String tmpYears = decodeStr(str,"|", RealNum + 9); //附加险现金价值年度数
		 	    		RealNum = RealNum + 9 + Integer.parseInt(tmpYears);
		 	    	}
		 	    	else{
		 	    		RealNum = RealNum + num2;
		 	    	}
		 	    }
		    }
		    i = 83;
		  }	    
		  if(num >=85)    //客户号
		  {
		    j=2;
		    RealNum = RealNum + 2;
		    tmpStr1 =decodeStr(str,"|", RealNum);
		    //System.out.println("客户号["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	
		    i = 85;
		  }
		  ////System.out.println("i= "+ i + " j= "+j+" num="+ num+ " ");
		  if(num==45 || num==75 || num==82 || num==85)
		  {
		    //System.out.println("i= "+ i + " j= "+j+" num1="+num1+" ");
		    if(num1 == 0){
		    	return tmpStr1;
		    }else{
		    	RealNum =  RealNum - temp*j  +  (num1-1)*j+num2;
		    }
		  }
		  else if(num == 83){ //为附加险单独出一个流程
			  if(num1 == 0){
				  return tmpStr1; 
			  }
		  }else{
		    //System.out.println("i= "+ i + " j= "+j+" RealNum="+RealNum+" ");
		    RealNum +=num-i;
		  }
		  //System.out.println("RealNum"+ "]["+ RealNum +"]");
		  tmpStr1 = decodeStr(str,"|",RealNum).trim();
		  
		  return tmpStr1.replaceAll("/", "");	  
		
	}
	
	
	
	
	public static void main(String args[]){
//		String a = "RQ00T0023|01ABC|04|1101|11010102|6755|99000420000010126560|2011/10/18|131420|150|1014|00000|千里马两全保险(分红型)C款||31101400000355|131014000293|||02|08||过时间|1|1984/01/01|0|370681198401013227|发达方法发达放大放大|111111|11111111|||001|01|过时间|1|1984/01/01|0|370681198401013227|发达方法发达放大放大|111111|11111111|||001|1|1|1|法定|||||30|1.0000||2011/10/19|2011/10/18|2|12000.00|12.00|0|||8||1|8|1|35||04|null|43242343141|n|2||2011/10/19|2011/10/19|10021407|冯福平|100214|0.00|0.00|0.000|0|13524.00|N|12|||以1000元年交保险费为单位【注】|8|940.00|964.00|990.00|1016.00|1043.00|1070.00|1098.00|0.00|2|201|附加世纪泰康防癌个人疾病保险|20.00|20000.00|258.00|17.1|24.7|以1000元保险费为单位【注】|8|10.69|9.71|8.60|7.33|5.86|4.16|2.21|0.00|202|泰康附加特定定期意外伤害保险|20.00|20000.00|80.00|0.0|0.0||0|12338.00|2|11|0003146077|21|0003146077|";
//	    String b = "RQ00T0105|01   |04|1101|11010102|6755|99000420000041123507|2011/09/22|093009|150|1014|00000|千里马两全保险(分红型)C款|211014010064|              |            |||02|08||过时间|1|1984/01/01|0|370681198401013227|发达方法发达放大放大|111111|11111111|||001|01|过时间|1|1984/01/01|0|370681198401013227|发达方法发达放大放大|111111|11111111|||001|1|1|1|法定|||||30|1.0000||2011/09/23|2011/09/22|2|12000.00|12.00|0||2011/09/22|8||1|8|1|35||04|null|43242343141|n|2||2019/09/23|2011/09/23|10021407|冯福平|100214|0.00|0.00|0.000|1|150101|12000.00|1.0000|13524.00||0|8.3|6.0|以1000元年交保险费为单位【注】|8|940.00|964.00|990.00|1016.00|1043.00|1070.00|1098.00|0.00|2|201|附加世纪泰康防癌个人疾病保险|20.00|20000.00|258.00|17.1|24.7|以1000元年交保险费为单位【注】|8|10.69|9.71|8.60|7.33|5.86|4.16|2.21|0.00|202|泰康附加特定定期意外伤害保险|20.00|20000.00|80.00|0.0|0.0||0|12338.00|0|2|11|0003146077|21|0003146077|泰康人寿山东分公司|济南市经十路38号舜山园A座第一大道20层|泰康|250001|95522|2019/09/23|27|";
//		System.out.println(AbcMidplatUtil.getValue4Prmcalck(a, 83, 1, 5));
//		System.out.println(AbcMidplatUtil.getValue(b, 85, 0, 0));
//		String c = "凤飞飞";
//		System.out.println("c.length=" + c.getBytes().length);
		Document abxml= getSimpOutXml(9999,"shibai");
		JdomUtil.print(abxml);
	}
	
	//试算交易递归获取竖线分割符中的字段
	public static String getValue4Prmcalck(String str, int num, int num1, int num2){ //num1和num2开始位置为1
		  //System.out.println("str" + str);
		  int RealNum=0;
		  int temp=0 ;
		  int i=0;
		  int j=0;
		  String tmpStr1="";
		  if(num >=45 )   //受益人
		  {
		    j=10;        //一个受益人位数
		    RealNum=45;
		    tmpStr1 =decodeStr(str,"|", 45);
		    
		    //System.out.println("受益人个数:["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	    
		    i = 45;
		  }
		  if(num >=75)    //缴费账户信息
		  {
		    j=3;
		    RealNum = RealNum +30;
		    tmpStr1 =decodeStr(str,"|", RealNum);
		    //System.out.println("缴费账户信息["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	  
		     i = 75;
		  }
		  if(num >=82)   //现金价值表
		  {
		    j=1;
		    RealNum = RealNum + 7;
		    tmpStr1 =decodeStr(str,"|", RealNum);
		    //System.out.println("现金价值表["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	
		    i = 82;
		  }	  
		  if(num >=83)    //附加险  num1表示第几个附加险层面，num2表示改层面的值（除了现金价值表）,num3表示该层面现金价值项
		  {
		    RealNum = RealNum + 1;
		    tmpStr1 =decodeStr(str,"|", RealNum);
	//		    System.out.println("附加险个数["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    if(num != 83){
		    	for(int k = 1;k <= temp;k ++){
		    		String tmpYears = decodeStr(str,"|", RealNum + 9); //附加险现金价值年度数
		    		RealNum = RealNum + 9 + Integer.parseInt(tmpYears);
		    	}
		    }
		    else{
		    	 for(int m = 1;m <= num1; m++){ // 第几层
		 	    	if(m != num1){
		 	    		String tmpYears = decodeStr(str,"|", RealNum + 9); //附加险现金价值年度数
		 	    		RealNum = RealNum + 9 + Integer.parseInt(tmpYears);
		 	    	}
		 	    	else{
		 	    		RealNum = RealNum + num2;
		 	    	}
		 	    }
		    }
		    i = 83;
		  }	    
		  if(num >=85)    //客户号
		  {
		    j=2;
		    RealNum = RealNum + 2;
		    tmpStr1 =decodeStr(str,"|", RealNum);
		    //System.out.println("客户号["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	
		    i = 85;
		  }
		  ////System.out.println("i= "+ i + " j= "+j+" num="+ num+ " ");
		  if(num==45 || num==75 || num==82 || num==85)
		  {
		    //System.out.println("i= "+ i + " j= "+j+" num1="+num1+" ");
		    if(num1 == 0){
		    	return tmpStr1;
		    }else{
		    	RealNum =  RealNum - temp*j  +  (num1-1)*j+num2;
		    }
		  }
		  else if(num == 83){ //为附加险单独出一个流程
			  if(num1 == 0){
				  return tmpStr1; 
			  }
		  }else{
		    //System.out.println("i= "+ i + " j= "+j+" RealNum="+RealNum+" ");
		    RealNum +=num-i;
		  }
		  //System.out.println("RealNum"+ "]["+ RealNum +"]");
		  tmpStr1 = decodeStr(str,"|",RealNum).trim();
		  
		  return tmpStr1.replaceAll("/", "");	  
	}
	
	//获取通过保单号cxbd服务返回的农行竖线分割字段信息
	public static String getValue4CxbdContNo(String str, int num, int num1, int num2) //num1和num2开始位置为1
	{

		  //System.out.println("str" + str);
		  int RealNum=0;
		  int temp=0 ;
		  int i=0;
		  int j=0;
		  String tmpStr1="";
		  if(num >=43 )   //受益人
		  {
		    j=10;        //一个受益人位数
		    RealNum=43;
		    tmpStr1 =decodeStr(str,"|", 43);
		    
//		    System.out.println("受益人个数:["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	    
		    i = 43;
		  }
		  if(num >=66 )   //附加险
		  {
		    j=5;        //一个附加险位数
		    RealNum=RealNum + 23;
		    tmpStr1 =decodeStr(str,"|", RealNum);
		    
//		    System.out.println("受益人个数:["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	    
		    i = 66;
		  }
		  if(num >=76)    //缴费账户信息
		  {
		    j=6;
		    RealNum = RealNum +10;
		    tmpStr1 =decodeStr(str,"|", RealNum);
//		    System.out.println("缴费账户信息["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	  
		     i = 76;
		  }
		  if(num==43 || num==66 || num==76)
		  {
		    //System.out.println("i= "+ i + " j= "+j+" num1="+num1+" ");
		    if(num1 == 0){
		    	return tmpStr1;
		    }else{
		    	RealNum =  RealNum - temp*j  +  (num1-1)*j+num2;
		    }
		  }
		  else{
		    //System.out.println("i= "+ i + " j= "+j+" RealNum="+RealNum+" ");
		    RealNum +=num-i;
		  }
		  //System.out.println("RealNum"+ "]["+ RealNum +"]");
		  tmpStr1 = decodeStr(str,"|",RealNum).trim();
		  
		  return tmpStr1.replaceAll("/", "");	  
		
	}
	

	//ABC--TK
	public static String tran_IDTypeIn(String pIDTypeAbc) {
		//所有重号的都给干掉，转为其他

		String mIDTypeTK = "";
		if ("110001".equals(pIDTypeAbc)) { //居民身份证
			mIDTypeTK = "0"; //身份证 
		} else if ("110003".equals(pIDTypeAbc)) { //临时身份证
			mIDTypeTK = "c"; //临时身份证
		} else if ("110005".equals(pIDTypeAbc)) { //户口簿
			mIDTypeTK = "4"; //户口本
		} else if ("110019".equals(pIDTypeAbc)) { //港澳居民往来内地通行证 
			mIDTypeTK = "e"; //港澳通行证
		} else if ("110021".equals(pIDTypeAbc)) { //台湾来大陆通行证 new
			mIDTypeTK = "f"; //台胞证
		} else if ("110023".equals(pIDTypeAbc)) { //中国护照
			mIDTypeTK = "1"; //护照
		} else if ("110025".equals(pIDTypeAbc)) { //外国护照
			mIDTypeTK = "1";
		} else if ("110027".equals(pIDTypeAbc)) { //军官证
			mIDTypeTK = "2"; //军官证
		} else if ("110031".equals(pIDTypeAbc)) { //警官证
			mIDTypeTK = "d"; //警官证
		}else if ("110033".equals(pIDTypeAbc)) { //军人士兵证 new
			mIDTypeTK = "a"; //士兵证
		} else if ("110035".equals(pIDTypeAbc)) { //武警证
			mIDTypeTK = "g"; //武警证
		} else {
			mIDTypeTK = "7"; //其他
		}
		return mIDTypeTK;
	}
	/**
	 * tk --> bank ABC
	 * @param pIDTypeTK
	 * @return
	 */
	public static String tran_IDTypeOut(String pIDTypeTK) {
		String mIDTypeAbc = "";

		//0 1 2 4 b c d g
		if ("0".equals(pIDTypeTK)) { //身份证
			mIDTypeAbc = "110001";
		} else if ("1".equals(pIDTypeTK)) { //护照
			mIDTypeAbc = "110023";
		} else if ("2".equals(pIDTypeTK)) { //军官证
			mIDTypeAbc = "110027";
		} else if ("4".equals(pIDTypeTK)) { //户口本
			mIDTypeAbc = "110005";
		} else if ("b".equals(pIDTypeTK)) { //回乡证
			mIDTypeAbc = "110019";
		} else if ("c".equals(pIDTypeTK)) { //临时身份证
			mIDTypeAbc = "110003";
		} else if ("d".equals(pIDTypeTK)) { //警官证
			mIDTypeAbc = "110035";
		} else if ("g".equals(pIDTypeTK)) { //武警证
			mIDTypeAbc = "110035";
		} else {
			mIDTypeAbc = "119999";
		}

		return mIDTypeAbc;
	}

	public static String tran_JobCodeIn(String pJobCodeAbc) {
		String mJobCodeTK = "";

		if ("01".equals(pJobCodeAbc)) { //国家机关、党群组织、企业、事业单位人员
			mJobCodeTK = "001";
		} else if ("02".equals(pJobCodeAbc)) {//卫生专业技术人员
			mJobCodeTK = "001";
		} else if ("03".equals(pJobCodeAbc)) {//金融业务人员
			mJobCodeTK = "002";
		} else if ("04".equals(pJobCodeAbc)) {//法律专业人员
			mJobCodeTK = "002";
		} else if ("05".equals(pJobCodeAbc)) {//教学人员
			mJobCodeTK = "002";
		} else if ("06".equals(pJobCodeAbc)) {//新闻出版及文学艺术工作人员
			mJobCodeTK = "002";
		} else if ("07".equals(pJobCodeAbc)) {//宗教职业者
			mJobCodeTK = "002";
		} else if ("08".equals(pJobCodeAbc)) {//邮政和电信业务人员
			mJobCodeTK = "003";
		} else if ("09".equals(pJobCodeAbc)) {//商业、服务业人员
			mJobCodeTK = "003";
		} else if ("10".equals(pJobCodeAbc)) {//农、林、牧、渔、水利业生产人员
			mJobCodeTK = "004";
		} else if ("11".equals(pJobCodeAbc)) {//运输人员
			mJobCodeTK = "004";
		} else if ("12".equals(pJobCodeAbc)) {//地质勘测人员
			mJobCodeTK = "005";
		} else if ("13".equals(pJobCodeAbc)) {//工程施工人员
			mJobCodeTK = "005";
		} else if ("14".equals(pJobCodeAbc)) {//加工制造、检验及计量人员
			mJobCodeTK = "003";
		} else if ("15".equals(pJobCodeAbc)) {//军人
			mJobCodeTK = "003";
		} else if ("16".equals(pJobCodeAbc)) {//无业
			mJobCodeTK = "001";
		} else {
			mJobCodeTK = "008";
		}

		return mJobCodeTK;
	}

	public static String tran_JobCodeOut(String pJobCodeTK) {
		String mJobCodeAbc = "";

		if ("001".equals(pJobCodeTK)) {
			mJobCodeAbc = "01";
		} else if ("002".equals(pJobCodeTK)) {
			mJobCodeAbc = "03";
		} else if ("003".equals(pJobCodeTK)) {
			mJobCodeAbc = "08";
		} else if ("004".equals(pJobCodeTK)) {
			mJobCodeAbc = "10";
		} else if ("005".equals(pJobCodeTK)) {
			mJobCodeAbc = "12";
		} else {
			mJobCodeAbc = "17";
		}

		return mJobCodeAbc;
	}

	//	 public static String tran_YearAgeFlagIn(String pFlagAbc){
	//		 String mFlagTK = "";
	//		 
	//		 //1年龄；2月；3日；4年
	//		 if ("1".equals(pFlagAbc)){ //年龄
	//			 mFlagTK = "";
	//		 }
	//		 
	////		 return mFlagTK;
	//	 }
	//	 
	//	 public static String tran_YearAgeFlagOut(String pFlagTK){
	//		 String mFlagAbc = "";
	//		 
	//		 if (){
	//			 
	//		 }
	//	 }

	//缴费方式 abc-->tk
	public static String tran_JfWayIn(String pJfWayAbc) {
		String mJfWayTK = "";

		if ("1".equals(pJfWayAbc)) {
			mJfWayTK = "0"; //趸交
		} else if ("2".equals(pJfWayAbc)) {
			mJfWayTK = "1"; //月缴
		} else if ("3".equals(pJfWayAbc)) {
			mJfWayTK = "3"; //季缴
		} else if ("4".equals(pJfWayAbc)) {
			mJfWayTK = "6"; //半年交
		} else if ("5".equals(pJfWayAbc)) {
			mJfWayTK = "12"; //年缴
		} else if ("6".equals(pJfWayAbc)) { //abc 年龄
			mJfWayTK = "12"; //年缴
		} else if ("0".equals(pJfWayAbc)) {
			mJfWayTK = "-1"; //不定期缴
		} else {
			mJfWayTK = "9"; //其他
		}

		return mJfWayTK;
	}

	//缴费方式 tk-->abc
	public static String tran_JfWayOut(String pJfWayTK) {
		String mJfWayAbc = "";

		if ("-1".equals(pJfWayTK)) {
			mJfWayAbc = "6"; //不定期缴
		} else if ("0".equals(pJfWayTK)) {
			mJfWayAbc = "1"; //趸交
		} else if ("1".equals(pJfWayTK)) {
			mJfWayAbc = "2"; //月交
		} else if ("3".equals(pJfWayTK)) {
			mJfWayAbc = "3"; //季缴
		} else if ("6".equals(pJfWayTK)) {
			mJfWayAbc = "4"; //半年交
		} else if ("9".equals(pJfWayTK)) {
			mJfWayAbc = "6";
		} else if ("12".equals(pJfWayTK)) {
			mJfWayAbc = "5"; //年缴
		}

		return mJfWayAbc;
	}

	//红利领取方式 abc-->TK
	public static String tran_BonusPayIn(String pBonusPayAbc) {
		String mBonusPayTK = "";

		/*
		 * 0                       无
		1                   	累积生息
		2                   	领取现金
		3                   	抵交保费
		4                   	其他
		5                   	增额交清
		 */
		if ("2".equals(pBonusPayAbc)) {
			mBonusPayTK = "1"; //领取现金
		} else {
			mBonusPayTK = "2"; //累计生息
		}
		return mBonusPayTK;
	}

	//红利领取方式 tk-->abc
	public static String tran_BonusPayOut(String pBonusPayTK) {
		String mBonusPayAbc = "";

		if ("1".equals(pBonusPayTK)) {
			mBonusPayAbc = "2";
		} else {
			mBonusPayAbc = "1";
		}

		return mBonusPayAbc;
	}

	//满期金领取方式 abc-->tk
	public static String tran_FullBonusGetModeIn(String pFullBonusGetModeAbc) {
		String mFullBonusGetModeTK = "";
		/*
		 * 1                   	一次统一给付
		2                   	按年金方式领
		 */
		if ("1".equals(pFullBonusGetModeAbc)) {
			mFullBonusGetModeTK = "0";
		} else {
			mFullBonusGetModeTK = "12";
		}

		return mFullBonusGetModeTK;
	}

	//满期金领取方式 tk-->abc
	public static String tran_FullBonusGetModeOut(String pFullBonusGetModeTK) {
		String mFullBonusGetModeAbc = "";

		if ("0".equals(pFullBonusGetModeTK)) {
			mFullBonusGetModeAbc = "1";
		} else {
			mFullBonusGetModeAbc = "2";
		}

		return mFullBonusGetModeAbc;
	}

	//返回码转换
	public static String tran_RetFlagOut(String pRetFlagTK) {
		String mRetFlagAbc = "";

		//处理结果（返回码）	0：失败；1：成功；
		if ("00000".equals(pRetFlagTK)) {
			mRetFlagAbc = "1";
		} else {
			mRetFlagAbc = "0";
		}

		return mRetFlagAbc;
	}

	/**
	 * 根据pFlag和pMessage，生成简单的标准返回报文。
	 */
	public static Document getSimpOutXml(int pFlag, String pMessage) {
		Element mABCB2I = new Element(ABCB2I);
		Element mHeader = new Element(Header);
		mABCB2I.addContent(mHeader);
		Element mRetCode  = new Element("RetCode");
		Element mRetMsg = new Element ("RetMsg");
		String returncode= AbcMidplatUtil.lpad(String.valueOf(pFlag), 6, '0');
		mRetCode.setText(String.valueOf(returncode));
		mRetMsg.setText(pMessage);
		mHeader.addContent(mRetCode);
		mHeader.addContent(mRetMsg);
		return new Document(mABCB2I);
	}
	
	/**
	 * 根据pFlag和pMesg，生成简单的标准返回报文。
	 */
	public static Document getSimpOutXml1(int pFlag, String pMessage) {
		Element mFlag = new Element(Flag);
		mFlag.addContent(String.valueOf(pFlag));

		Element mMesg = new Element(Mesg);
		mMesg.addContent(pMessage);

		Element mRetData = new Element(RetData);
		mRetData.addContent(mFlag);
		mRetData.addContent(mMesg);
		
		Element mBase = new Element(Base);
		Element mProposalContNo = new Element(ProposalContNo);
		Element mReqsrNo = new Element(ReqsrNo);
		Element mPrem = new Element(Prem);
		mBase.addContent(mProposalContNo);
		mBase.addContent(mReqsrNo);
		mBase.addContent(mPrem);
		
		Element mRisks = new Element("Risks");
		Element mRisk = new Element("Risk");
		Element mPrem1 = new Element("Prem1");
		Element mPrem2 = new Element("Prem2");
		Element mPrem3 = new Element("Prem3");
		mRisk.addContent(mPrem1);
		mRisk.addContent(mPrem2);
		mRisk.addContent(mPrem3);
		mRisks.addContent(mRisk);
		
		Element mRet = new Element(Ret);
		mRet.addContent(mRetData);
		mRet.addContent(mBase);
		mRet.addContent(mRisks);

		return new Document(mRet);
	}

	//将核心返回的00000转换成农行的 1：成功 0：失败	
	public static String tran_ErrCodeOut(String pErrCodeMsgTK) {
		String mErrCodeAbc = "0";

		//处理结果（返回码）	0：失败；1：成功；
		//if ("00000".equals(pErrCodeMsgTK)||) {
		if ("00000".equals(pErrCodeMsgTK)||"0".equals(pErrCodeMsgTK)) { //xiaowq 20130325 农行康悦人生538当日撤单成功返回的是0，而不是00000
			mErrCodeAbc = "1";
		} else {
			mErrCodeAbc = "0";
		}

		return mErrCodeAbc;
	}
	
	//将核心返回的00001信息错误 转换成农行要求的ErrMsg
	public static String tran_ErrMsgOut(String pErrCodeMsgTK) {
		String mErrMsgAbc = "";

		//if ("00000".equals(pErrCodeMsgTK)) {
		if ("00000".equals(pErrCodeMsgTK)||"0".equals(pErrCodeMsgTK)) { //xiaowq 20130325 农行康悦人生538当日撤单成功返回的是0，而不是00000
			mErrMsgAbc = "成功";
		} else {
			if (pErrCodeMsgTK.length() <= 5){
				mErrMsgAbc = "";
			} else{
				mErrMsgAbc = pErrCodeMsgTK.substring(5);
			} 
		}

		return mErrMsgAbc;
	}
	
	//将交易确认核心返回的00001信息错误 转换成农行要求的ErrMsg
	public static String tran_JyqrErrMsgOut(String pErrCodeMsgTK) {
		String mErrMsgAbc = "";

		if ("0".equals(pErrCodeMsgTK)) {	//交易确认成功为0
			mErrMsgAbc = "成功";
		} else {
			if (pErrCodeMsgTK.length() <= 5){
				mErrMsgAbc = "";
			} else{
				mErrMsgAbc = pErrCodeMsgTK.substring(5);
			} 
		}

		return mErrMsgAbc;
	}
	//获取RetData 1:Mesg
	public static Element getRetData1(String pOutStd) {
		Element mRetData = new Element(RetData);
		Element mFlag = new Element(Flag);
		Element mMesg = new Element(Mesg);

		String mErrCodeMsgTK = decodeStr(pOutStd, PACKAGESPLITER, 12);
		mFlag.setText(tran_ErrCodeOut(mErrCodeMsgTK));
		mMesg.setText(tran_ErrMsgOut(mErrCodeMsgTK));

		mRetData.addContent(mFlag);
		mRetData.addContent(mMesg);

		return mRetData;
	}
	
	//获取RetData 2:Mesg
	public static Element getRetData2(String pOutStd) {
		Element mRetData = new Element(RetData);
		Element mFlag = new Element(Flag);
		Element mMesg = new Element(Mesg);

		String mErrCodeMsgTK = decodeStr(pOutStd, PACKAGESPLITER, 12);
		mFlag.setText(tran_ErrCodeOut(mErrCodeMsgTK));
		mMesg.setText(tran_ErrMsgOut(mErrCodeMsgTK));

		mRetData.addContent(mFlag);
		mRetData.addContent(mMesg);

		return mRetData;
	}
	
	//获取SucFlag
	public static boolean getSucFlag(String pOutStd){
		String flag = decodeStr(pOutStd, PACKAGESPLITER, 12);
		if ("00000".equals(flag) || "0".equals(flag)){
			return true;
		} else {
			return false;
		}
	}
	
	public static String getYesterday(String nowDate) {    
	    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");   
	     try {   
	        Date date = sdf.parse(nowDate);   
	        if(date == null) {   
	            return null;   
	        }   
	        Calendar cal = Calendar.getInstance();   
	        cal.setTime(date);   
	        cal.add(Calendar.DAY_OF_MONTH, -1);   
	        return sdf.format(cal.getTime());   
	    } catch (ParseException e) {   
	        return null;   
	    }   
	}
	
	//给传入的字符串添加tuxedo头信息
	public static String FormatStr(StringBuffer pStrBuf, String pTuxSrv) {
		String len = String.valueOf(pStrBuf.toString().length());
		String tempStr = AbcMidplatUtil.rpad(len, mesHeadLen, ' ');
		tempStr += "0       000004    04    01    AA";
		tempStr +=  AbcMidplatUtil.rpad(pTuxSrv, 18, ' '); 
		tempStr += "0 ";
		pStrBuf.insert(0, tempStr);
//		cLogger.debug("tempStr:["+tempStr+"]");
		
		return pStrBuf.toString();
	}
	
	
	//取ftp文件
	public static InputStream getFtpFile(Element pFtpEle, String pFileName, String pLocalDir) throws Exception {
		System.out.println("Into getFtpFile()...");
		
		String mFtpIp = pFtpEle.getAttributeValue(ip);
		System.out.println("ftp.ip = " + mFtpIp);
		if (null==mFtpIp || mFtpIp.equals("")) {
			throw new MidplatException("未配置上传ftp的ip！");
		}
		
		String mFtpPort = pFtpEle.getAttributeValue("port");
		if (null==mFtpPort || mFtpPort.equals("")) {
			mFtpPort = "21";
		}
		System.out.println("ftp.port = " + mFtpPort);
		
		String mFtpUser = pFtpEle.getAttributeValue("user");
		System.out.println("ftp.user = " + mFtpUser);
		
		String mFtpPassword = pFtpEle.getAttributeValue("password");
		System.out.println("ftp.password = " + mFtpPassword);
		
		//重复连接次数
		int mReConn = 5;
		String mAttrValue = pFtpEle.getAttributeValue("reconn");
		if (null!=mAttrValue && !"".equals(mAttrValue)) {
			try {
				mReConn = Integer.parseInt(mAttrValue);
			} catch (Exception ex) {
				System.out.println("未正确配置ftp最大重复连接次数，将采用系统默认值！");
			}
		}
		System.out.println("ftp.reconn = " + mReConn);
		
		//连接超时，默认为5分钟
		int mTimeout = 5 * 60;
		mAttrValue = pFtpEle.getAttributeValue("timeout");
		if (null!=mAttrValue && !"".equals(mAttrValue)) {
			try {
				mTimeout = Integer.parseInt(mAttrValue);
			} catch (Exception ex) {
				System.out.println("未正确配置ftp超时，采用系统默认值！");
			}
		}
		System.out.println("ftp.timeout = " + mTimeout + "s");
		
		String mLocalPath = null;
		if (null!=pLocalDir && !"".equals(pLocalDir)) {
			pLocalDir = pLocalDir.replace('\\', '/');
			if (!pLocalDir.endsWith("/")) {
				pLocalDir += '/';
			}
			mLocalPath = pLocalDir + pFileName;
		}
		System.out.println("LocalPath = " + mLocalPath);
		
		//ftp到银行
		FTPClient mFTPClient = new FTPClient();
		mFTPClient.setDefaultPort(Integer.parseInt(mFtpPort));
		mFTPClient.setDefaultTimeout(mTimeout * 1000);	//设置超时
		InputStream mBatIs = null;
		for (int i = 1; (i<=mReConn) && (null==mBatIs); i++) {
			System.out.println("------" + i + "------------");
			try {
				//建立ftp连接
				mFTPClient.connect(mFtpIp);
				int tReplyCode = mFTPClient.getReplyCode();
				if (!FTPReply.isPositiveCompletion(tReplyCode)) {
					System.out.println("ftp连接失败！" + mFTPClient.getReplyString());
					throw new MidplatException("ftp连接失败！" + mFtpIp + ": " + tReplyCode);
				}
				System.out.println("ftp连接成功！" + mFtpIp);
				
				//登录
				if (!mFTPClient.login(mFtpUser, mFtpPassword)) {
					System.out.println("ftp登录失败！" + mFTPClient.getReplyString());
					throw new MidplatException("ftp登录失败！" + mFtpUser + ":" + mFtpPassword);
				}
				System.out.println("ftp登录成功！");
				
				//二进制传输
				if (mFTPClient.setFileType(FTP.BINARY_FILE_TYPE)) {
					System.out.println("采用二进制传输！");
				} else {
					System.out.println("设置传输模式为二进制失败！" + mFTPClient.getReplyString());
				}
				
				String tFtpPath = pFtpEle.getAttributeValue("path");
				if (null!=tFtpPath && !"".equals(tFtpPath)) {
					if (!mFTPClient.changeWorkingDirectory(tFtpPath)) {
						System.out.println("切换ftp工作目录失败！" + tFtpPath + "; " + mFTPClient.getReplyString());
					}
				}
				System.out.println("CurWorkingDir = " + mFTPClient.printWorkingDirectory());
				
				//下载数据
				FileOutputStream tLocalFos = null;
				try {
					if (!mLocalPath.endsWith(".out")) { //不再本地备份.out文件
						tLocalFos = new FileOutputStream(mLocalPath);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (null == tLocalFos) {	//未正确设置备份目录，直接使用ftp流进行对账
					ByteArrayOutputStream ttBaos = new ByteArrayOutputStream();
					if (mFTPClient.retrieveFile(pFileName, ttBaos)) {
						System.out.println("ftp下载数据成功！");
						mBatIs = new ByteArrayInputStream(ttBaos.toByteArray());
					} else {
						System.out.println("ftp下载数据失败！" + mFTPClient.getReplyString());
					}
				} else {
					if (mFTPClient.retrieveFile(pFileName, tLocalFos)) {
						System.out.println("ftp下载数据成功！" + mLocalPath);
						mBatIs = new FileInputStream(mLocalPath);
					} else {
						System.out.println("ftp下载数据失败！" + mFTPClient.getReplyString());
					}
					tLocalFos.close();
				}
				
				//退出登陆
				mFTPClient.logout();
				System.out.println("ftp退出成功！");
			} catch (SocketTimeoutException ex) {	//超时，重新连接
				System.out.println("ftp服务器响应超时，尝试重新连接！");
			} finally {
				if (mFTPClient.isConnected()) {
					try {
						mFTPClient.disconnect();
						System.out.println("ftp连接断开！");
					} catch(IOException ex) {
						System.out.println("服务端连接已断开！");
						ex.printStackTrace();
					}
				}
			}
		}
		
		if (null == mBatIs) {
			throw new MidplatException("未找到文件！" + pFileName);
		}
		
		System.out.println("Out getFtpFile()!");
		return mBatIs;
	}
	
	public static void save(byte[] pBytes, String pFilePath, String pName) {

		File mFileDir = new File(pFilePath);
		mFileDir.mkdirs();
		if (!mFileDir.exists()) {
			cLogger.error("目录不存在，且尝试创建失败！" + mFileDir.getPath());
			return;
		}
		
		try {
			FileOutputStream tFos = new FileOutputStream(pFilePath + pName);
			tFos.write(pBytes);
			tFos.close();
		} catch (IOException ex) {
			cLogger.error("保存文件失败！", ex);
		}
	}
	
	//临时写的，比较简陋，如果需要更多配置，以后需要补充
	public static boolean Upload(Element pFtpEle, String pLocalDir, String pFileName) {
		String tIp = pFtpEle.getAttributeValue(ip);
		String tUser = pFtpEle.getAttributeValue("user");
		String tPassword =  pFtpEle.getAttributeValue("password");
		String tRemotepath = pFtpEle.getAttributeValue("path");

		System.out.println("Upload FTP File Start");
		FTPClient tFTPClient = new FTPClient(); 

		try {
			/**测试环境***/
			tFTPClient.connect(tIp);		
			boolean login = tFTPClient.login(tUser, tPassword);
			
			if (!login) {
				cLogger.debug("FTP连接失败"); 
				tFTPClient.logout();
				tFTPClient.disconnect(); 
				tFTPClient = null; 
				return false; 
			}

			cLogger.info("上传ftp文件名：" + pFileName);
			
			if (!pLocalDir.endsWith("/")) {
				pLocalDir += "/";
			}
			InputStream mInputStream = new FileInputStream(pLocalDir + pFileName);
			
			String rmtPathName = "";
			if (null==tRemotepath || "".equals(tRemotepath)) {
				rmtPathName = pFileName;
			} else {
				rmtPathName = tRemotepath + pFileName;
			}
			tFTPClient.storeFile(rmtPathName, mInputStream); 
			tFTPClient.logout();
			tFTPClient.disconnect(); 
			tFTPClient = null;
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		cLogger.info("Upload FTP File End");
		return true;
		
	
	}
	
	public static InputStream getLocalFile(String pDir, String pName) throws MidplatException {
		cLogger.debug("Into getLocalFile()...");
		
		pDir = pDir.replace('\\', '/');
		if (!pDir.endsWith("/")) {
			pDir += '/';
		}
		String mPathName = pDir + pName;
		cLogger.info("LocalPath = " + mPathName);
		
		InputStream mIns = null;
		try {
			mIns = new FileInputStream(mPathName);
		} catch (IOException ex) {
			//未找到对账文件
			throw new MidplatException("未找到文件！" + mPathName);
		}
		
		cLogger.debug("Out getLocalFile()!");
		return mIns;
	}
	
	public static String FormatStrHZ(String pHZMsg, String pTuxSrv){

		String len = String.valueOf(pHZMsg.length());
		String tempStr = AbcMidplatUtil.rpad(len, 8, ' ');
		tempStr += "0       000004    04    01    AA";
		tempStr +=  AbcMidplatUtil.rpad(pTuxSrv, 18, ' '); 
		tempStr += "0 ";
//		cLogger.debug("tempStr:["+tempStr+"]");
		tempStr = tempStr + pHZMsg;
		return tempStr;
	}
	//通过投保份数计算保费（农行不传保费），仅适用于非保额算保费类的产品。不适用于保额算保费类的产品(如康悦人生538)
	public static String MultNotoPrem(String MultNo, String MainRiskCodeNo ){
		
       String tempStr = "";
       String itempStr = null;
       if (MainRiskCodeNo.equals("510") || MainRiskCodeNo.equals("530")){
    	   itempStr = multiplication(MultNo, "500");
       }else{
    	   itempStr = multiplication(MultNo, "1000");
       }
       tempStr = String.valueOf(itempStr);
		return tempStr;
	}
	
	/**
	 * 乘法运算
	 * @param num1
	 * @param num2
	 * @return 任何错误都返回0
	 */
	public static String multiplication(String num1, String num2){
		String multi = "0";
		try{
			BigDecimal mul1 = new BigDecimal(num1);
			BigDecimal mul2 = new BigDecimal(num2);
			multi = mul1.multiply(mul2).toString();
		}catch(Exception e){
			cLogger.error("乘法运算错误");
			multi = "0";
		}
		return multi;
	}
	
	//处理收益份额中的整数转为百分制
	public static String BnfLot(String BnfLot ){
		
       String tempStr = "";
       float itempStr = 0;
       float iMultNo = 0;
       if (!BnfLot.equals("")&&BnfLot.length()!=0){
    	  iMultNo = Integer.valueOf(BnfLot);
    	  itempStr = iMultNo/100;
    	  tempStr = String.valueOf(itempStr);
       }  	       	       
		return tempStr;
	}
	
	/**
	 * 银行代码转换方法，供AccBankNAME节点用
	 * 将农行传入的银行编码转换成泰康定义的银行编码
	 * @param bank4ABC   农行传入银行代码
	 * @return bank4TK   泰康定义银行代码
	 */
	public static String BankChange(String bank4ABC){
		String bank4TK = "";
		if(bank4ABC != null && !bank4ABC.equals("")){
			if(bank4ABC.equals("01")){ 			//ABC中国人民银行
				bank4TK = "26";        				//TK其它金融机构
			}else if(bank4ABC.equals("02")){    //ABC中国工商银行
				bank4TK = "01";						//TK
			}else if(bank4ABC.equals("03")){	//ABC中国农业银行
				bank4TK = "04";						//TK
			}else if(bank4ABC.equals("04")){	//ABC中国银行
				bank4TK = "02";						//TK
			}else if(bank4ABC.equals("05")){	//ABC中国建设银行
				bank4TK = "03";						//TK
			}else if(bank4ABC.equals("06")){	//ABC中国交通银行
				bank4TK = "10";						//TK
			}else if(bank4ABC.equals("07")){	//ABC中国邮政储汇局
				bank4TK = "16";						//TK
			}else if(bank4ABC.equals("08")){	//ABC浦东发展银行
				bank4TK = "09";						//TK
			}else if(bank4ABC.equals("09")){	//ABC中国招商银行
				bank4TK = "06";						//TK
			}else if(bank4ABC.equals("10")){	//ABC深圳发展银行
				bank4TK = "12";						//TK
			}else if(bank4ABC.equals("11")){	//ABC广东发展银行
				bank4TK = "14";						//TK
			}else if(bank4ABC.equals("12")){	//ABC合作银行
				bank4TK = "26";						//TK其它金融机构
			}else if(bank4ABC.equals("13")){	//ABC光大银行
				bank4TK = "11";						//TK
			}else if(bank4ABC.equals("14")){	//ABC福建兴业银行
				bank4TK = "13";						//TK
			}else if(bank4ABC.equals("15")){	//ABC中信实业银行
				bank4TK = "15";						//TK
			}else if(bank4ABC.equals("16")){	//ABC华夏银行
				bank4TK = "08";						//TK
			}else if(bank4ABC.equals("17")){	//ABC国家开发银行
				bank4TK = "26";						//TK其它金融机构
			}else if(bank4ABC.equals("18")){	//ABC中国进出口银行
				bank4TK = "26";						//TK其它金融机构
			}else if(bank4ABC.equals("19")){	//ABC中国农业发展银行
				bank4TK = "04";						//TK
			}else if(bank4ABC.equals("20")){	//ABC中国民生银行
				bank4TK = "07";						//TK
			}else if(bank4ABC.equals("21")){	//ABC城市商业银行
				bank4TK = "05";						//TK
			}else if(bank4ABC.equals("22")){	//ABC中国信托投资公司
				bank4TK = "26";						//TK其它金融机构
			}else if(bank4ABC.equals("23")){	//ABC城市信用社
				bank4TK = "26";						//TK其它金融机构
			}else if(bank4ABC.equals("24")){	//ABC农村信用社
				bank4TK = "26";						//TK其它金融机构
			}else if(bank4ABC.equals("25")){	//ABC财务公司
				bank4TK = "26";						//TK其它金融机构
			}else if(bank4ABC.equals("26")){	//ABC租赁公司 
				bank4TK = "26";						//TK其它金融机构
			}else if(bank4ABC.equals("27")){	//ABC厦门国际银行
				bank4TK = "26";						//TK其它金融机构
			}else if(bank4ABC.equals("28")){	//ABC上海银行 
				bank4TK = "26";						//TK其它金融机构
			}else if(bank4ABC.equals("29")){	//ABC汇丰银行
				bank4TK = "26";						//TK其它金融机构
			}else if(bank4ABC.equals("30")){	//ABC花旗银行
				bank4TK = "26";						//TK其它金融机构
			}else if(bank4ABC.equals("31")){	//ABC渣打银行
				bank4TK = "26";						//TK其它金融机构
			}else if(bank4ABC.equals("32")){	//ABC摩根大通银行
				bank4TK = "26";						//TK其它金融机构
			}else if(bank4ABC.equals("33")){	//ABC大华银行
				bank4TK = "26";						//TK其它金融机构
			}else if(bank4ABC.equals("34")){	//ABC东亚银行
				bank4TK = "26";						//TK其它金融机构
			}else if(bank4ABC.equals("35")){	//ABC恒丰银行
				bank4TK = "26";						//TK其它金融机构
			}else if(bank4ABC.equals("36")){	//ABC北京银行
				bank4TK = "35";						//TK
			}else{
				bank4TK = null;
			}
			return bank4TK;
		}else{
			return "04";
		}
	}
	
	/**
	 * 犹豫期退保取险种名称
	 * @param str
	 * @param num
	 * @return
	 */
	public static String getValue4Pgwt(String str, int num) 
	{
		  int RealNum=0;
		  int temp=0 ;
		  int j=0;
		  String tmpStr1="";
		  if(num >= 19 )   //单证
		  {
		    j=2;        //一个单证位数
		    RealNum=19;
		    tmpStr1 =decodeStr(str,"|", 19);
		    
		    temp = Integer.parseInt(tmpStr1);
		    if(temp == 0){
		    	RealNum = num;
		    }else{
		    	RealNum += temp*j;	    
		    }
		  }
		  tmpStr1 = decodeStr(str,"|",RealNum).trim();
		  
		  return tmpStr1;	  
	}
	
	/**
	 * 犹豫期后退单险种代码对应险种名称
	 * 暂时在前置机中加，农行2期上线后在B_pgzt.4gl中加
	 * @param kinds
	 * @return
	 */
	public static String getKindsName(String kinds){
		String name = "";
		if(kinds != null && !kinds.equals("")){
			try{
				int code = Integer.valueOf(kinds);
				switch(code){
				  case 118:
					  name = "放心理财投资连结保险";
					  break;
				  case 126:
					  name = "泰康千里马两全保险(分红型)";
					  break;
				  case 149:
					  name = "泰康千里马两全保险(分红型)B款";
					  break;
				  case 150:
					  name = "泰康千里马两全保险(分红型)C款";
					  break;
				  case 175:
					  name = "泰康康寿保综合意外伤害保险";
					  break;
				  case 180:
					  name = "泰康放心理财终身寿险（万能型）";
					  break;
				  case 185:
					  name = "泰康放心理财终身寿险（万能型）C款";
					  break;
				  case 187:
					  name = "泰康希望之星两全保险(分红型)";
					  break;
				  case 505:
					  name = "泰康金利两全保险（分红型）";
					  break;
				  case 506:
					  name = "泰康安心理财投资连结保险";
					  break;
				  case 507:
					  name = "泰康赢家理财投资连结保险";
					  break;
				  case 508:
					  name = "泰康放心理财经典版终身寿险（万能型）";
					  break;
				  case 509:
					  name = "泰康放心理财财富版终身寿险（万能型）";
					  break;
				  case 510:
					  name = "泰康金满仓两全保险（分红型）";
					  break;
//				  case 511:
//					  name = "泰康致富理财终身寿险（万能型）";
//					  break;
//				  case 512:
//					  name = "泰康E理财投资连结保险";
//					  break;
//				  case 515:
//					  name = "泰康和家理财终身寿险(万能型)";
//					  break;
//				  case 516:
//					  name = "泰康附加和家理财投资连结保险";
//					  break;
				  case 518:
					  name = "泰康赢家理财投资连结保险";
					  break;
				  case 519:
					  name = "泰康金满仓B款年金保险（分红型）";
					  break;
//				  case 522:
//					  name = "泰康e理财B款投资连结保险";
//					  break;
				  case 523:
					  name = "泰康幸福人生A款终身年金保险（分红型）";
					  break;
				  case 530:
					  name = "泰康金满仓D款两全保险（分红型）";
					  break;
				}
			}catch(NumberFormatException ne){
				cLogger.error("险种代码转Int错误");
				return "";
			}
		}
		return name;
	}
	
	/**
	 * 账户代码转换方法
	 * @param bm_class
	 * @return
	 */
	public static String getBmClassName(String bm_class){
    	String returnStr = "";
    	if("507001".equals(bm_class)){
    		returnStr = "稳健收益型投资帐户";
    	}
    	else if("507002".equals(bm_class)){
    		returnStr = "平衡配置型投资帐户";
    	}
    	else if("507003".equals(bm_class)){
    		returnStr = "积极成长型投资帐户";
    	}
    	else if("507004".equals(bm_class)){
    		returnStr = "基金精选投资帐户";
    	}
    	else if("518001".equals(bm_class)){
    		returnStr = "稳健收益型投资帐户";
    	}
    	else if("518002".equals(bm_class)){
    		returnStr = "平衡配置型投资帐户";
    	}
    	else if("518003".equals(bm_class)){
    		returnStr = "积极成长型投资帐户";
    	}
    	else if("518004".equals(bm_class)){
    		returnStr = "基金精选投资帐户";
    	}
    	else{
    		returnStr = bm_class;
    	}
    	return returnStr; 
	}
	
	//xiaowq 20130321 add 农行康瑞人生(538)开发添加
	public static String getErrInfo(String errCode){
		String info = "";
		if (errCode.equals("10888")) {
			info = "投保人豁免保额超过100万，不能银保通出单";
		} else if(errCode.equals("10501")) {
			info = "被保人为未成年人累计身故保额不能超过10万元";
		} else if(errCode.equals("10511")) {
			info = "55周岁以上，不能银保通出单";
		} else if(errCode.equals("10502")) {
			//info = "18至45周岁，累计风险保额不能超过40万元";
			info = "被保人年龄18-45岁之间,累计风险保额超过40万元需体检";
		} else if(errCode.equals("10503")) {
			//info = "46至50周岁，累计风险保额不能超过30万元";
			info = "被保人年龄46-50岁之间,累计风险保额超过30万元需体检";
		} else if(errCode.equals("10504")) {
			//info = "51至55周岁，累计风险保额不能超过10万元";
			info = "被保人年龄51-55岁之间,累计风险保额超过10万元需体检";
		} else if(errCode.equals("10505")) {
			//info = "18至45周岁，累计风险保额不能超过30万元";
			info = "被保人年龄18-45岁之间,累计风险保额超过30万元需体检";
		} else if(errCode.equals("10506")) {
			//info = "46至50周岁，累计风险保额不能超过20万元";
			info = "被保人年龄46-50岁之间,累计风险保额超过20万元需体检";
		} else if(errCode.equals("10507")) {
			//info = "51至55周岁，累计风险保额不能超过5万元";
			info = "被保人年龄51-55岁之间,累计风险保额超过5万元需体检";
		} else if(errCode.equals("10508")) {
			//info = "18至45周岁，累计风险保额不能超过20万元";
			info = "被保人年龄18-45岁之间,累计风险保额超过20万元需体检";
		} else if(errCode.equals("10509")) {
			//info = "46至50周岁，累计风险保额不能超过10万元";
			info = "被保人年龄46-50岁之间,累计风险保额超过10万元需体检";
		} else if(errCode.equals("10510")) {
			info = "51至55周岁，不能银保通出单";
		} else if(errCode.equals("10151")) {
			info = "累计保额不得大于30万";
		} else if(errCode.equals("10152")) {
			info = "累计保额不得大于20万";
		}
		return info;
	}
	
	//获取RetData 1:Mesg
	public static Element getRetDataFail538(String pOutStd) {
		Element mRetData = new Element(RetData);
		Element mFlag = new Element(Flag);
		Element mMesg = new Element(Mesg);

		String mErrCodeMsgTK = decodeStr(pOutStd, PACKAGESPLITER, 12);
		mFlag.setText(tran_ErrCodeOut(mErrCodeMsgTK));
		/*cLogger.info("pOutStd === "+pOutStd);
		cLogger.info("===80==="+AbcMidplatUtil.getValue(pOutStd, 80, 0, 0));
		cLogger.info("===81==="+AbcMidplatUtil.getValue(pOutStd, 81, 0, 0));
		cLogger.info("===82==="+AbcMidplatUtil.getValue(pOutStd, 82, 0, 0));
		cLogger.info("===83==="+AbcMidplatUtil.getValue(pOutStd, 83, 0, 0));
		cLogger.info("===84==="+AbcMidplatUtil.getValue(pOutStd, 84, 0, 0));
		cLogger.info("===85==="+AbcMidplatUtil.getValue(pOutStd, 85, 0, 0));
		cLogger.info("===86==="+AbcMidplatUtil.getValue(pOutStd, 86, 0, 0));
		cLogger.info("===87==="+AbcMidplatUtil.getValue(pOutStd, 87, 0, 0));*/
		String errMsg = AbcMidplatUtil.getValue(pOutStd, 87, 0, 0);
		errMsg = errMsg.replaceAll(" ", "");
		cLogger.info("农行康悦人生(538)错误信息:"+errMsg);
		mMesg.setText(errMsg);

		mRetData.addContent(mFlag);
		mRetData.addContent(mMesg);

		return mRetData;
	}
	/**
	 * 
	 * @param pOutStd 试算返回的标准报文
	 * @param jyCode 交易代码
	 * @return 报文的头信息
	 */
	public static Element getHeader(String pOutStd,String jyCode){
		Element head = new Element(Header);
//		<Header>
//	        <RetCode>000000</RetCode>
//	        <RetMsg>交易成功</RetMsg>
//	        <SerialNo>12345678</SerialNo>
//	        <InsuSerial>20130701000000000002</InsuSerial>
//	        <TransDate>20130703</TransDate>
//	        <BankCode>03</BankCode>
//	        <CorpNo>3002</CorpNo>
//	        <TransCode>1002</TransCode>
//		</Header>
		String returncode=decodeStr(pOutStd, PACKAGESPLITER, 12).trim();//错误码
		String errorinfo = ""; //错误信息描述
		if (!returncode.equals("00000") &&(jyCode.equals("1012")||jyCode.equals("1014"))){//保全查询 错误信息放在13位
			errorinfo = decodeStr(pOutStd, PACKAGESPLITER, 13).trim();
		}else{
			if(!returncode.equals("0000")&&!returncode.equals("0") &&!returncode.equals("00000")&& returncode.length()>=5){
			
				errorinfo = returncode.substring(5,returncode.length());
				returncode = returncode.substring(0,5);
				cLogger.info("xxxxxxssss:"+returncode);
			}
		}
		cLogger.info("xxxxxx:"+returncode);
		Element RetCode = new Element("RetCode"); //交易返回码
		//应银行要求 如果成功返回000000 如果失败返回009999 如果是累计保额超限009990
		if (returncode.equals("0000") || returncode.equals("0") || returncode.equals("00000")){
			RetCode.setText("000000");
		}else{
			RetCode.setText("009999");
		}
		
		head.addContent(RetCode);
		Element RetMsg = new Element("RetMsg"); //返回信息
		if(returncode.equals("0") || returncode.equals("00000")){
			RetMsg.setText("交易成功");
			
		}else{
			if(errorinfo.equals("") || errorinfo==null ){
				if(pOutStd.indexOf("RQ00T0001")!= -1){
					String error =AbcMidplatUtil.getValue(pOutStd, 87, 0, 0);
					System.out.println("Error:"+error);
					RetMsg.setText(error);
				}else{
					RetMsg.setText("交易失败");
				}
			}else{
				RetMsg.setText(errorinfo);
			}
			
		}
		head.addContent(RetMsg);
		Element SerialNo = new Element("SerialNo"); //交易流水号码
		SerialNo.setText("");
		head.addContent(SerialNo);
		Element InsuSerial = new Element("InsuSerial"); //保险交易流水号码
		InsuSerial.setText(decodeStr(pOutStd, PACKAGESPLITER, 7).trim());
		head.addContent(InsuSerial);
		
		Element  TransTime = new Element("TransTime"); //交易时间
		TransTime.setText(decodeStr(pOutStd, PACKAGESPLITER, 9).trim());
		head.addContent(TransTime);
		
		Element TransDate = new Element("TransDate");//交易日期
		TransDate.setText(decodeStr(pOutStd, PACKAGESPLITER, 8).trim());
		head.addContent(TransDate);
		
		Element BankCode = new Element("BankCode");//银行代码
		BankCode.setText("04");
		head.addContent(BankCode);
		
		Element CorpNo = new Element("CorpNo");//保险公司代码
		CorpNo.setText("1118"); //ABC zhong tk de daima 
		head.addContent(CorpNo);
		Element TransCode = new Element("TransCode"); //交易编码
		TransCode.setText(jyCode);
		head.addContent(TransCode);
		return head;
	}
	public static String getnowdate(){
		SimpleDateFormat formate = new SimpleDateFormat("yyyyMMdd");
		String nowdate = formate.format(new Date());
		return nowdate;
	}
	public static String getPgcx_Values(String str, int num, int num1, int num2) //num1和num2开始位置为1
	{
		  int RealNum=0;
		  int temp=0 ;
		  int i=0;
		  int j=0;
		  String tmpStr1="";
		  if(num >= 43 )   //受益人
		  {
		    j=10;        //一个受益人位数
		    RealNum=43;
		    tmpStr1 =decodeStr(str,"|", 43);
		    
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	    
		    i = 43;
		  }
		  if(num >= 68)    //附加险个数
		  {
		    j=5;
		    RealNum = RealNum +25;
		    tmpStr1 =decodeStr(str,"|", RealNum);
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	  
		    i = 68;
		  }
		  if (num >= 88) {
			j = 6;
			RealNum = RealNum + 20;
			tmpStr1 = decodeStr(str, "|", RealNum);
			temp = Integer.parseInt(tmpStr1);
			RealNum += temp * j;
			i = 88;
		  }
		  if(num==43 || num==68 || num==88)
		  {
		    if(num1 == 0){
		    	return tmpStr1;
		    }else{
		    	RealNum =  RealNum - temp*j  +  (num1-1)*j+num2;
		    }
		  }else{
			  RealNum +=num-i;
		  }
		  tmpStr1 = decodeStr(str,"|",RealNum).trim();
		  
		  return tmpStr1;	  
	}
}