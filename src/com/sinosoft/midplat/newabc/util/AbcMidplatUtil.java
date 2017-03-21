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
 * @Description: ũ���м�ƽ̨������
 * @author sinosoft
 * @date 2017-3-2 ����2:29:07
 */
public class AbcMidplatUtil implements AbcXmlTag {

	//����һ���������־����
	private final static Logger cLogger = Logger.getLogger(AbcMidplatUtil.class);
	
	//���ַ�������ָ���ķָ��ַ����в��,���ش�ָ����ŵķָ�����ǰһ���ָ���֮����ַ���
	public static String decodeStr(String strMain, String strDelimiters,
			int intSerialNo) {
		int intIndex = 0; /*�ָ������������ַ����е���ʼλ��*/
		int intCount = 0; /*��ɨ�����ַ����Ĺ�����,�ڼ��������ָ����ַ���*/
		String strReturn = ""; /*��Ϊ����ֵ���ַ���*/

		if (strMain.length() < strDelimiters.length())
			return ""; /*�����ַ����ȷָ�������Ҫ�̵Ļ�,�򷵻ؿ��ַ���*/

		intIndex = strMain.indexOf(strDelimiters);
		if (intIndex == -1)
			return ""; /*�����ַ����в����ڷָ���,�򷵻ؿ��ַ���*/

		while (intIndex != -1) /*δ�ҵ��ָ���ʱ�˳�ѭ��,�����ؿ��ַ���*/
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
	 * �ַ���8λ����
	 * @param strValue �ַ���ֵ
	 * @param intLength  ����
	 * @param tmp  ��ʱ
	 * @return
	 */
	public static String lpad(String strValue, int intLength, char tmp) {
		//int strLen = strValue.length();  
		//�ַ���ֵ����
		int strLen = strValue.getBytes().length;
		//�ַ�������ֵ
		String strReturn = "";
		//�ַ���ֵ���ȴ���ָ������
		if (strLen > intLength)
			strReturn = strValue.substring(0, intLength);//��ȡǰָ�����ȸ��ַ�
		else {
			strReturn = strValue;//����ԭ�ַ���ֵ
			//����
			for (int i = strLen; i < intLength; i++)
				strReturn = tmp + strReturn;
		}
		return new String(strReturn);
	}

	//�ݹ��ȡ���߷ָ���е��ֶ�
	public static String getValue(String str, int num, int num1, int num2) //num1��num2��ʼλ��Ϊ1
	{

		  //System.out.println("str" + str);
		  int RealNum=0;
		  int temp=0 ;
		  int i=0;
		  int j=0;
		  String tmpStr1="";
		  if(num >=45 )   //������
		  {
		    j=10;        //һ��������λ��
		    RealNum=45;
		    tmpStr1 =decodeStr(str,"|", 45);
		    
		    //System.out.println("�����˸���:["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	    
		    i = 45;
		  }
		  if(num >=75)    //�ɷ��˻���Ϣ
		  {
		    j=3;
		    RealNum = RealNum +30;
		    tmpStr1 =decodeStr(str,"|", RealNum);
		    //System.out.println("�ɷ��˻���Ϣ["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	  
		     i = 75;
		  }
		  if(num >=82)   //�ֽ��ֵ��
		  {
		    j=1;
		    RealNum = RealNum + 7;
		    tmpStr1 =decodeStr(str,"|", RealNum);
		    //System.out.println("�ֽ��ֵ��["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	
		    i = 82;
		  }	  
		  if(num >=83)    //������  num1��ʾ�ڼ��������ղ��棬num2��ʾ�Ĳ����ֵ�������ֽ��ֵ��,num3��ʾ�ò����ֽ��ֵ��
		  {
		    RealNum = RealNum + 1;
		    tmpStr1 =decodeStr(str,"|", RealNum);
//		    System.out.println("�����ո���["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    if(num != 83){
		    	for(int k = 1;k <= temp;k ++){
		    		String tmpYears = decodeStr(str,"|", RealNum + 9); //�������ֽ��ֵ�����
		    		RealNum = RealNum + 9 + Integer.parseInt(tmpYears);
		    	}
		    }
		    else{
		    	 for(int m = 1;m <= num1; m++){ // �ڼ���
		 	    	if(m != num1){
		 	    		String tmpYears = decodeStr(str,"|", RealNum + 9); //�������ֽ��ֵ�����
		 	    		RealNum = RealNum + 9 + Integer.parseInt(tmpYears);
		 	    	}
		 	    	else{
		 	    		RealNum = RealNum + num2;
		 	    	}
		 	    }
		    }
		    i = 83;
		  }	    
		  if(num >=85)    //�ͻ���
		  {
		    j=2;
		    RealNum = RealNum + 2;
		    tmpStr1 =decodeStr(str,"|", RealNum);
		    //System.out.println("�ͻ���["+tmpStr1+"]["+ RealNum +"]");
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
		  else if(num == 83){ //Ϊ�����յ�����һ������
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
//		String a = "RQ00T0023|01ABC|04|1101|11010102|6755|99000420000010126560|2011/10/18|131420|150|1014|00000|ǧ������ȫ����(�ֺ���)C��||31101400000355|131014000293|||02|08||��ʱ��|1|1984/01/01|0|370681198401013227|���﷽������Ŵ�Ŵ�|111111|11111111|||001|01|��ʱ��|1|1984/01/01|0|370681198401013227|���﷽������Ŵ�Ŵ�|111111|11111111|||001|1|1|1|����|||||30|1.0000||2011/10/19|2011/10/18|2|12000.00|12.00|0|||8||1|8|1|35||04|null|43242343141|n|2||2011/10/19|2011/10/19|10021407|�븣ƽ|100214|0.00|0.00|0.000|0|13524.00|N|12|||��1000Ԫ�꽻���շ�Ϊ��λ��ע��|8|940.00|964.00|990.00|1016.00|1043.00|1070.00|1098.00|0.00|2|201|��������̩���������˼�������|20.00|20000.00|258.00|17.1|24.7|��1000Ԫ���շ�Ϊ��λ��ע��|8|10.69|9.71|8.60|7.33|5.86|4.16|2.21|0.00|202|̩�������ض����������˺�����|20.00|20000.00|80.00|0.0|0.0||0|12338.00|2|11|0003146077|21|0003146077|";
//	    String b = "RQ00T0105|01   |04|1101|11010102|6755|99000420000041123507|2011/09/22|093009|150|1014|00000|ǧ������ȫ����(�ֺ���)C��|211014010064|              |            |||02|08||��ʱ��|1|1984/01/01|0|370681198401013227|���﷽������Ŵ�Ŵ�|111111|11111111|||001|01|��ʱ��|1|1984/01/01|0|370681198401013227|���﷽������Ŵ�Ŵ�|111111|11111111|||001|1|1|1|����|||||30|1.0000||2011/09/23|2011/09/22|2|12000.00|12.00|0||2011/09/22|8||1|8|1|35||04|null|43242343141|n|2||2019/09/23|2011/09/23|10021407|�븣ƽ|100214|0.00|0.00|0.000|1|150101|12000.00|1.0000|13524.00||0|8.3|6.0|��1000Ԫ�꽻���շ�Ϊ��λ��ע��|8|940.00|964.00|990.00|1016.00|1043.00|1070.00|1098.00|0.00|2|201|��������̩���������˼�������|20.00|20000.00|258.00|17.1|24.7|��1000Ԫ�꽻���շ�Ϊ��λ��ע��|8|10.69|9.71|8.60|7.33|5.86|4.16|2.21|0.00|202|̩�������ض����������˺�����|20.00|20000.00|80.00|0.0|0.0||0|12338.00|0|2|11|0003146077|21|0003146077|̩������ɽ���ֹ�˾|�����о�ʮ·38��˴ɽ԰A����һ���20��|̩��|250001|95522|2019/09/23|27|";
//		System.out.println(AbcMidplatUtil.getValue4Prmcalck(a, 83, 1, 5));
//		System.out.println(AbcMidplatUtil.getValue(b, 85, 0, 0));
//		String c = "��ɷ�";
//		System.out.println("c.length=" + c.getBytes().length);
		Document abxml= getSimpOutXml(9999,"shibai");
		JdomUtil.print(abxml);
	}
	
	//���㽻�׵ݹ��ȡ���߷ָ���е��ֶ�
	public static String getValue4Prmcalck(String str, int num, int num1, int num2){ //num1��num2��ʼλ��Ϊ1
		  //System.out.println("str" + str);
		  int RealNum=0;
		  int temp=0 ;
		  int i=0;
		  int j=0;
		  String tmpStr1="";
		  if(num >=45 )   //������
		  {
		    j=10;        //һ��������λ��
		    RealNum=45;
		    tmpStr1 =decodeStr(str,"|", 45);
		    
		    //System.out.println("�����˸���:["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	    
		    i = 45;
		  }
		  if(num >=75)    //�ɷ��˻���Ϣ
		  {
		    j=3;
		    RealNum = RealNum +30;
		    tmpStr1 =decodeStr(str,"|", RealNum);
		    //System.out.println("�ɷ��˻���Ϣ["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	  
		     i = 75;
		  }
		  if(num >=82)   //�ֽ��ֵ��
		  {
		    j=1;
		    RealNum = RealNum + 7;
		    tmpStr1 =decodeStr(str,"|", RealNum);
		    //System.out.println("�ֽ��ֵ��["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	
		    i = 82;
		  }	  
		  if(num >=83)    //������  num1��ʾ�ڼ��������ղ��棬num2��ʾ�Ĳ����ֵ�������ֽ��ֵ��,num3��ʾ�ò����ֽ��ֵ��
		  {
		    RealNum = RealNum + 1;
		    tmpStr1 =decodeStr(str,"|", RealNum);
	//		    System.out.println("�����ո���["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    if(num != 83){
		    	for(int k = 1;k <= temp;k ++){
		    		String tmpYears = decodeStr(str,"|", RealNum + 9); //�������ֽ��ֵ�����
		    		RealNum = RealNum + 9 + Integer.parseInt(tmpYears);
		    	}
		    }
		    else{
		    	 for(int m = 1;m <= num1; m++){ // �ڼ���
		 	    	if(m != num1){
		 	    		String tmpYears = decodeStr(str,"|", RealNum + 9); //�������ֽ��ֵ�����
		 	    		RealNum = RealNum + 9 + Integer.parseInt(tmpYears);
		 	    	}
		 	    	else{
		 	    		RealNum = RealNum + num2;
		 	    	}
		 	    }
		    }
		    i = 83;
		  }	    
		  if(num >=85)    //�ͻ���
		  {
		    j=2;
		    RealNum = RealNum + 2;
		    tmpStr1 =decodeStr(str,"|", RealNum);
		    //System.out.println("�ͻ���["+tmpStr1+"]["+ RealNum +"]");
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
		  else if(num == 83){ //Ϊ�����յ�����һ������
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
	
	//��ȡͨ��������cxbd���񷵻ص�ũ�����߷ָ��ֶ���Ϣ
	public static String getValue4CxbdContNo(String str, int num, int num1, int num2) //num1��num2��ʼλ��Ϊ1
	{

		  //System.out.println("str" + str);
		  int RealNum=0;
		  int temp=0 ;
		  int i=0;
		  int j=0;
		  String tmpStr1="";
		  if(num >=43 )   //������
		  {
		    j=10;        //һ��������λ��
		    RealNum=43;
		    tmpStr1 =decodeStr(str,"|", 43);
		    
//		    System.out.println("�����˸���:["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	    
		    i = 43;
		  }
		  if(num >=66 )   //������
		  {
		    j=5;        //һ��������λ��
		    RealNum=RealNum + 23;
		    tmpStr1 =decodeStr(str,"|", RealNum);
		    
//		    System.out.println("�����˸���:["+tmpStr1+"]["+ RealNum +"]");
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	    
		    i = 66;
		  }
		  if(num >=76)    //�ɷ��˻���Ϣ
		  {
		    j=6;
		    RealNum = RealNum +10;
		    tmpStr1 =decodeStr(str,"|", RealNum);
//		    System.out.println("�ɷ��˻���Ϣ["+tmpStr1+"]["+ RealNum +"]");
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
		//�����غŵĶ����ɵ���תΪ����

		String mIDTypeTK = "";
		if ("110001".equals(pIDTypeAbc)) { //�������֤
			mIDTypeTK = "0"; //���֤ 
		} else if ("110003".equals(pIDTypeAbc)) { //��ʱ���֤
			mIDTypeTK = "c"; //��ʱ���֤
		} else if ("110005".equals(pIDTypeAbc)) { //���ڲ�
			mIDTypeTK = "4"; //���ڱ�
		} else if ("110019".equals(pIDTypeAbc)) { //�۰ľ��������ڵ�ͨ��֤ 
			mIDTypeTK = "e"; //�۰�ͨ��֤
		} else if ("110021".equals(pIDTypeAbc)) { //̨������½ͨ��֤ new
			mIDTypeTK = "f"; //̨��֤
		} else if ("110023".equals(pIDTypeAbc)) { //�й�����
			mIDTypeTK = "1"; //����
		} else if ("110025".equals(pIDTypeAbc)) { //�������
			mIDTypeTK = "1";
		} else if ("110027".equals(pIDTypeAbc)) { //����֤
			mIDTypeTK = "2"; //����֤
		} else if ("110031".equals(pIDTypeAbc)) { //����֤
			mIDTypeTK = "d"; //����֤
		}else if ("110033".equals(pIDTypeAbc)) { //����ʿ��֤ new
			mIDTypeTK = "a"; //ʿ��֤
		} else if ("110035".equals(pIDTypeAbc)) { //�侯֤
			mIDTypeTK = "g"; //�侯֤
		} else {
			mIDTypeTK = "7"; //����
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
		if ("0".equals(pIDTypeTK)) { //���֤
			mIDTypeAbc = "110001";
		} else if ("1".equals(pIDTypeTK)) { //����
			mIDTypeAbc = "110023";
		} else if ("2".equals(pIDTypeTK)) { //����֤
			mIDTypeAbc = "110027";
		} else if ("4".equals(pIDTypeTK)) { //���ڱ�
			mIDTypeAbc = "110005";
		} else if ("b".equals(pIDTypeTK)) { //����֤
			mIDTypeAbc = "110019";
		} else if ("c".equals(pIDTypeTK)) { //��ʱ���֤
			mIDTypeAbc = "110003";
		} else if ("d".equals(pIDTypeTK)) { //����֤
			mIDTypeAbc = "110035";
		} else if ("g".equals(pIDTypeTK)) { //�侯֤
			mIDTypeAbc = "110035";
		} else {
			mIDTypeAbc = "119999";
		}

		return mIDTypeAbc;
	}

	public static String tran_JobCodeIn(String pJobCodeAbc) {
		String mJobCodeTK = "";

		if ("01".equals(pJobCodeAbc)) { //���һ��ء���Ⱥ��֯����ҵ����ҵ��λ��Ա
			mJobCodeTK = "001";
		} else if ("02".equals(pJobCodeAbc)) {//����רҵ������Ա
			mJobCodeTK = "001";
		} else if ("03".equals(pJobCodeAbc)) {//����ҵ����Ա
			mJobCodeTK = "002";
		} else if ("04".equals(pJobCodeAbc)) {//����רҵ��Ա
			mJobCodeTK = "002";
		} else if ("05".equals(pJobCodeAbc)) {//��ѧ��Ա
			mJobCodeTK = "002";
		} else if ("06".equals(pJobCodeAbc)) {//���ų��漰��ѧ����������Ա
			mJobCodeTK = "002";
		} else if ("07".equals(pJobCodeAbc)) {//�ڽ�ְҵ��
			mJobCodeTK = "002";
		} else if ("08".equals(pJobCodeAbc)) {//�����͵���ҵ����Ա
			mJobCodeTK = "003";
		} else if ("09".equals(pJobCodeAbc)) {//��ҵ������ҵ��Ա
			mJobCodeTK = "003";
		} else if ("10".equals(pJobCodeAbc)) {//ũ���֡������桢ˮ��ҵ������Ա
			mJobCodeTK = "004";
		} else if ("11".equals(pJobCodeAbc)) {//������Ա
			mJobCodeTK = "004";
		} else if ("12".equals(pJobCodeAbc)) {//���ʿ�����Ա
			mJobCodeTK = "005";
		} else if ("13".equals(pJobCodeAbc)) {//����ʩ����Ա
			mJobCodeTK = "005";
		} else if ("14".equals(pJobCodeAbc)) {//�ӹ����졢���鼰������Ա
			mJobCodeTK = "003";
		} else if ("15".equals(pJobCodeAbc)) {//����
			mJobCodeTK = "003";
		} else if ("16".equals(pJobCodeAbc)) {//��ҵ
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
	//		 //1���䣻2�£�3�գ�4��
	//		 if ("1".equals(pFlagAbc)){ //����
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

	//�ɷѷ�ʽ abc-->tk
	public static String tran_JfWayIn(String pJfWayAbc) {
		String mJfWayTK = "";

		if ("1".equals(pJfWayAbc)) {
			mJfWayTK = "0"; //����
		} else if ("2".equals(pJfWayAbc)) {
			mJfWayTK = "1"; //�½�
		} else if ("3".equals(pJfWayAbc)) {
			mJfWayTK = "3"; //����
		} else if ("4".equals(pJfWayAbc)) {
			mJfWayTK = "6"; //���꽻
		} else if ("5".equals(pJfWayAbc)) {
			mJfWayTK = "12"; //���
		} else if ("6".equals(pJfWayAbc)) { //abc ����
			mJfWayTK = "12"; //���
		} else if ("0".equals(pJfWayAbc)) {
			mJfWayTK = "-1"; //�����ڽ�
		} else {
			mJfWayTK = "9"; //����
		}

		return mJfWayTK;
	}

	//�ɷѷ�ʽ tk-->abc
	public static String tran_JfWayOut(String pJfWayTK) {
		String mJfWayAbc = "";

		if ("-1".equals(pJfWayTK)) {
			mJfWayAbc = "6"; //�����ڽ�
		} else if ("0".equals(pJfWayTK)) {
			mJfWayAbc = "1"; //����
		} else if ("1".equals(pJfWayTK)) {
			mJfWayAbc = "2"; //�½�
		} else if ("3".equals(pJfWayTK)) {
			mJfWayAbc = "3"; //����
		} else if ("6".equals(pJfWayTK)) {
			mJfWayAbc = "4"; //���꽻
		} else if ("9".equals(pJfWayTK)) {
			mJfWayAbc = "6";
		} else if ("12".equals(pJfWayTK)) {
			mJfWayAbc = "5"; //���
		}

		return mJfWayAbc;
	}

	//������ȡ��ʽ abc-->TK
	public static String tran_BonusPayIn(String pBonusPayAbc) {
		String mBonusPayTK = "";

		/*
		 * 0                       ��
		1                   	�ۻ���Ϣ
		2                   	��ȡ�ֽ�
		3                   	�ֽ�����
		4                   	����
		5                   	�����
		 */
		if ("2".equals(pBonusPayAbc)) {
			mBonusPayTK = "1"; //��ȡ�ֽ�
		} else {
			mBonusPayTK = "2"; //�ۼ���Ϣ
		}
		return mBonusPayTK;
	}

	//������ȡ��ʽ tk-->abc
	public static String tran_BonusPayOut(String pBonusPayTK) {
		String mBonusPayAbc = "";

		if ("1".equals(pBonusPayTK)) {
			mBonusPayAbc = "2";
		} else {
			mBonusPayAbc = "1";
		}

		return mBonusPayAbc;
	}

	//���ڽ���ȡ��ʽ abc-->tk
	public static String tran_FullBonusGetModeIn(String pFullBonusGetModeAbc) {
		String mFullBonusGetModeTK = "";
		/*
		 * 1                   	һ��ͳһ����
		2                   	�����ʽ��
		 */
		if ("1".equals(pFullBonusGetModeAbc)) {
			mFullBonusGetModeTK = "0";
		} else {
			mFullBonusGetModeTK = "12";
		}

		return mFullBonusGetModeTK;
	}

	//���ڽ���ȡ��ʽ tk-->abc
	public static String tran_FullBonusGetModeOut(String pFullBonusGetModeTK) {
		String mFullBonusGetModeAbc = "";

		if ("0".equals(pFullBonusGetModeTK)) {
			mFullBonusGetModeAbc = "1";
		} else {
			mFullBonusGetModeAbc = "2";
		}

		return mFullBonusGetModeAbc;
	}

	//������ת��
	public static String tran_RetFlagOut(String pRetFlagTK) {
		String mRetFlagAbc = "";

		//�������������룩	0��ʧ�ܣ�1���ɹ���
		if ("00000".equals(pRetFlagTK)) {
			mRetFlagAbc = "1";
		} else {
			mRetFlagAbc = "0";
		}

		return mRetFlagAbc;
	}

	/**
	 * ����pFlag��pMessage�����ɼ򵥵ı�׼���ر��ġ�
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
	 * ����pFlag��pMesg�����ɼ򵥵ı�׼���ر��ġ�
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

	//�����ķ��ص�00000ת����ũ�е� 1���ɹ� 0��ʧ��	
	public static String tran_ErrCodeOut(String pErrCodeMsgTK) {
		String mErrCodeAbc = "0";

		//�������������룩	0��ʧ�ܣ�1���ɹ���
		//if ("00000".equals(pErrCodeMsgTK)||) {
		if ("00000".equals(pErrCodeMsgTK)||"0".equals(pErrCodeMsgTK)) { //xiaowq 20130325 ũ�п�������538���ճ����ɹ����ص���0��������00000
			mErrCodeAbc = "1";
		} else {
			mErrCodeAbc = "0";
		}

		return mErrCodeAbc;
	}
	
	//�����ķ��ص�00001��Ϣ���� ת����ũ��Ҫ���ErrMsg
	public static String tran_ErrMsgOut(String pErrCodeMsgTK) {
		String mErrMsgAbc = "";

		//if ("00000".equals(pErrCodeMsgTK)) {
		if ("00000".equals(pErrCodeMsgTK)||"0".equals(pErrCodeMsgTK)) { //xiaowq 20130325 ũ�п�������538���ճ����ɹ����ص���0��������00000
			mErrMsgAbc = "�ɹ�";
		} else {
			if (pErrCodeMsgTK.length() <= 5){
				mErrMsgAbc = "";
			} else{
				mErrMsgAbc = pErrCodeMsgTK.substring(5);
			} 
		}

		return mErrMsgAbc;
	}
	
	//������ȷ�Ϻ��ķ��ص�00001��Ϣ���� ת����ũ��Ҫ���ErrMsg
	public static String tran_JyqrErrMsgOut(String pErrCodeMsgTK) {
		String mErrMsgAbc = "";

		if ("0".equals(pErrCodeMsgTK)) {	//����ȷ�ϳɹ�Ϊ0
			mErrMsgAbc = "�ɹ�";
		} else {
			if (pErrCodeMsgTK.length() <= 5){
				mErrMsgAbc = "";
			} else{
				mErrMsgAbc = pErrCodeMsgTK.substring(5);
			} 
		}

		return mErrMsgAbc;
	}
	//��ȡRetData 1:Mesg
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
	
	//��ȡRetData 2:Mesg
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
	
	//��ȡSucFlag
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
	
	//��������ַ������tuxedoͷ��Ϣ
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
	
	
	//ȡftp�ļ�
	public static InputStream getFtpFile(Element pFtpEle, String pFileName, String pLocalDir) throws Exception {
		System.out.println("Into getFtpFile()...");
		
		String mFtpIp = pFtpEle.getAttributeValue(ip);
		System.out.println("ftp.ip = " + mFtpIp);
		if (null==mFtpIp || mFtpIp.equals("")) {
			throw new MidplatException("δ�����ϴ�ftp��ip��");
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
		
		//�ظ����Ӵ���
		int mReConn = 5;
		String mAttrValue = pFtpEle.getAttributeValue("reconn");
		if (null!=mAttrValue && !"".equals(mAttrValue)) {
			try {
				mReConn = Integer.parseInt(mAttrValue);
			} catch (Exception ex) {
				System.out.println("δ��ȷ����ftp����ظ����Ӵ�����������ϵͳĬ��ֵ��");
			}
		}
		System.out.println("ftp.reconn = " + mReConn);
		
		//���ӳ�ʱ��Ĭ��Ϊ5����
		int mTimeout = 5 * 60;
		mAttrValue = pFtpEle.getAttributeValue("timeout");
		if (null!=mAttrValue && !"".equals(mAttrValue)) {
			try {
				mTimeout = Integer.parseInt(mAttrValue);
			} catch (Exception ex) {
				System.out.println("δ��ȷ����ftp��ʱ������ϵͳĬ��ֵ��");
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
		
		//ftp������
		FTPClient mFTPClient = new FTPClient();
		mFTPClient.setDefaultPort(Integer.parseInt(mFtpPort));
		mFTPClient.setDefaultTimeout(mTimeout * 1000);	//���ó�ʱ
		InputStream mBatIs = null;
		for (int i = 1; (i<=mReConn) && (null==mBatIs); i++) {
			System.out.println("------" + i + "------------");
			try {
				//����ftp����
				mFTPClient.connect(mFtpIp);
				int tReplyCode = mFTPClient.getReplyCode();
				if (!FTPReply.isPositiveCompletion(tReplyCode)) {
					System.out.println("ftp����ʧ�ܣ�" + mFTPClient.getReplyString());
					throw new MidplatException("ftp����ʧ�ܣ�" + mFtpIp + ": " + tReplyCode);
				}
				System.out.println("ftp���ӳɹ���" + mFtpIp);
				
				//��¼
				if (!mFTPClient.login(mFtpUser, mFtpPassword)) {
					System.out.println("ftp��¼ʧ�ܣ�" + mFTPClient.getReplyString());
					throw new MidplatException("ftp��¼ʧ�ܣ�" + mFtpUser + ":" + mFtpPassword);
				}
				System.out.println("ftp��¼�ɹ���");
				
				//�����ƴ���
				if (mFTPClient.setFileType(FTP.BINARY_FILE_TYPE)) {
					System.out.println("���ö����ƴ��䣡");
				} else {
					System.out.println("���ô���ģʽΪ������ʧ�ܣ�" + mFTPClient.getReplyString());
				}
				
				String tFtpPath = pFtpEle.getAttributeValue("path");
				if (null!=tFtpPath && !"".equals(tFtpPath)) {
					if (!mFTPClient.changeWorkingDirectory(tFtpPath)) {
						System.out.println("�л�ftp����Ŀ¼ʧ�ܣ�" + tFtpPath + "; " + mFTPClient.getReplyString());
					}
				}
				System.out.println("CurWorkingDir = " + mFTPClient.printWorkingDirectory());
				
				//��������
				FileOutputStream tLocalFos = null;
				try {
					if (!mLocalPath.endsWith(".out")) { //���ٱ��ر���.out�ļ�
						tLocalFos = new FileOutputStream(mLocalPath);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				if (null == tLocalFos) {	//δ��ȷ���ñ���Ŀ¼��ֱ��ʹ��ftp�����ж���
					ByteArrayOutputStream ttBaos = new ByteArrayOutputStream();
					if (mFTPClient.retrieveFile(pFileName, ttBaos)) {
						System.out.println("ftp�������ݳɹ���");
						mBatIs = new ByteArrayInputStream(ttBaos.toByteArray());
					} else {
						System.out.println("ftp��������ʧ�ܣ�" + mFTPClient.getReplyString());
					}
				} else {
					if (mFTPClient.retrieveFile(pFileName, tLocalFos)) {
						System.out.println("ftp�������ݳɹ���" + mLocalPath);
						mBatIs = new FileInputStream(mLocalPath);
					} else {
						System.out.println("ftp��������ʧ�ܣ�" + mFTPClient.getReplyString());
					}
					tLocalFos.close();
				}
				
				//�˳���½
				mFTPClient.logout();
				System.out.println("ftp�˳��ɹ���");
			} catch (SocketTimeoutException ex) {	//��ʱ����������
				System.out.println("ftp��������Ӧ��ʱ�������������ӣ�");
			} finally {
				if (mFTPClient.isConnected()) {
					try {
						mFTPClient.disconnect();
						System.out.println("ftp���ӶϿ���");
					} catch(IOException ex) {
						System.out.println("����������ѶϿ���");
						ex.printStackTrace();
					}
				}
			}
		}
		
		if (null == mBatIs) {
			throw new MidplatException("δ�ҵ��ļ���" + pFileName);
		}
		
		System.out.println("Out getFtpFile()!");
		return mBatIs;
	}
	
	public static void save(byte[] pBytes, String pFilePath, String pName) {

		File mFileDir = new File(pFilePath);
		mFileDir.mkdirs();
		if (!mFileDir.exists()) {
			cLogger.error("Ŀ¼�����ڣ��ҳ��Դ���ʧ�ܣ�" + mFileDir.getPath());
			return;
		}
		
		try {
			FileOutputStream tFos = new FileOutputStream(pFilePath + pName);
			tFos.write(pBytes);
			tFos.close();
		} catch (IOException ex) {
			cLogger.error("�����ļ�ʧ�ܣ�", ex);
		}
	}
	
	//��ʱд�ģ��Ƚϼ�ª�������Ҫ�������ã��Ժ���Ҫ����
	public static boolean Upload(Element pFtpEle, String pLocalDir, String pFileName) {
		String tIp = pFtpEle.getAttributeValue(ip);
		String tUser = pFtpEle.getAttributeValue("user");
		String tPassword =  pFtpEle.getAttributeValue("password");
		String tRemotepath = pFtpEle.getAttributeValue("path");

		System.out.println("Upload FTP File Start");
		FTPClient tFTPClient = new FTPClient(); 

		try {
			/**���Ի���***/
			tFTPClient.connect(tIp);		
			boolean login = tFTPClient.login(tUser, tPassword);
			
			if (!login) {
				cLogger.debug("FTP����ʧ��"); 
				tFTPClient.logout();
				tFTPClient.disconnect(); 
				tFTPClient = null; 
				return false; 
			}

			cLogger.info("�ϴ�ftp�ļ�����" + pFileName);
			
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
			//δ�ҵ������ļ�
			throw new MidplatException("δ�ҵ��ļ���" + mPathName);
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
	//ͨ��Ͷ���������㱣�ѣ�ũ�в������ѣ����������ڷǱ����㱣����Ĳ�Ʒ���������ڱ����㱣����Ĳ�Ʒ(�翵������538)
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
	 * �˷�����
	 * @param num1
	 * @param num2
	 * @return �κδ��󶼷���0
	 */
	public static String multiplication(String num1, String num2){
		String multi = "0";
		try{
			BigDecimal mul1 = new BigDecimal(num1);
			BigDecimal mul2 = new BigDecimal(num2);
			multi = mul1.multiply(mul2).toString();
		}catch(Exception e){
			cLogger.error("�˷��������");
			multi = "0";
		}
		return multi;
	}
	
	//��������ݶ��е�����תΪ�ٷ���
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
	 * ���д���ת����������AccBankNAME�ڵ���
	 * ��ũ�д�������б���ת����̩����������б���
	 * @param bank4ABC   ũ�д������д���
	 * @return bank4TK   ̩���������д���
	 */
	public static String BankChange(String bank4ABC){
		String bank4TK = "";
		if(bank4ABC != null && !bank4ABC.equals("")){
			if(bank4ABC.equals("01")){ 			//ABC�й���������
				bank4TK = "26";        				//TK�������ڻ���
			}else if(bank4ABC.equals("02")){    //ABC�й���������
				bank4TK = "01";						//TK
			}else if(bank4ABC.equals("03")){	//ABC�й�ũҵ����
				bank4TK = "04";						//TK
			}else if(bank4ABC.equals("04")){	//ABC�й�����
				bank4TK = "02";						//TK
			}else if(bank4ABC.equals("05")){	//ABC�й���������
				bank4TK = "03";						//TK
			}else if(bank4ABC.equals("06")){	//ABC�й���ͨ����
				bank4TK = "10";						//TK
			}else if(bank4ABC.equals("07")){	//ABC�й����������
				bank4TK = "16";						//TK
			}else if(bank4ABC.equals("08")){	//ABC�ֶ���չ����
				bank4TK = "09";						//TK
			}else if(bank4ABC.equals("09")){	//ABC�й���������
				bank4TK = "06";						//TK
			}else if(bank4ABC.equals("10")){	//ABC���ڷ�չ����
				bank4TK = "12";						//TK
			}else if(bank4ABC.equals("11")){	//ABC�㶫��չ����
				bank4TK = "14";						//TK
			}else if(bank4ABC.equals("12")){	//ABC��������
				bank4TK = "26";						//TK�������ڻ���
			}else if(bank4ABC.equals("13")){	//ABC�������
				bank4TK = "11";						//TK
			}else if(bank4ABC.equals("14")){	//ABC������ҵ����
				bank4TK = "13";						//TK
			}else if(bank4ABC.equals("15")){	//ABC����ʵҵ����
				bank4TK = "15";						//TK
			}else if(bank4ABC.equals("16")){	//ABC��������
				bank4TK = "08";						//TK
			}else if(bank4ABC.equals("17")){	//ABC���ҿ�������
				bank4TK = "26";						//TK�������ڻ���
			}else if(bank4ABC.equals("18")){	//ABC�й�����������
				bank4TK = "26";						//TK�������ڻ���
			}else if(bank4ABC.equals("19")){	//ABC�й�ũҵ��չ����
				bank4TK = "04";						//TK
			}else if(bank4ABC.equals("20")){	//ABC�й���������
				bank4TK = "07";						//TK
			}else if(bank4ABC.equals("21")){	//ABC������ҵ����
				bank4TK = "05";						//TK
			}else if(bank4ABC.equals("22")){	//ABC�й�����Ͷ�ʹ�˾
				bank4TK = "26";						//TK�������ڻ���
			}else if(bank4ABC.equals("23")){	//ABC����������
				bank4TK = "26";						//TK�������ڻ���
			}else if(bank4ABC.equals("24")){	//ABCũ��������
				bank4TK = "26";						//TK�������ڻ���
			}else if(bank4ABC.equals("25")){	//ABC����˾
				bank4TK = "26";						//TK�������ڻ���
			}else if(bank4ABC.equals("26")){	//ABC���޹�˾ 
				bank4TK = "26";						//TK�������ڻ���
			}else if(bank4ABC.equals("27")){	//ABC���Ź�������
				bank4TK = "26";						//TK�������ڻ���
			}else if(bank4ABC.equals("28")){	//ABC�Ϻ����� 
				bank4TK = "26";						//TK�������ڻ���
			}else if(bank4ABC.equals("29")){	//ABC�������
				bank4TK = "26";						//TK�������ڻ���
			}else if(bank4ABC.equals("30")){	//ABC��������
				bank4TK = "26";						//TK�������ڻ���
			}else if(bank4ABC.equals("31")){	//ABC��������
				bank4TK = "26";						//TK�������ڻ���
			}else if(bank4ABC.equals("32")){	//ABCĦ����ͨ����
				bank4TK = "26";						//TK�������ڻ���
			}else if(bank4ABC.equals("33")){	//ABC������
				bank4TK = "26";						//TK�������ڻ���
			}else if(bank4ABC.equals("34")){	//ABC��������
				bank4TK = "26";						//TK�������ڻ���
			}else if(bank4ABC.equals("35")){	//ABC�������
				bank4TK = "26";						//TK�������ڻ���
			}else if(bank4ABC.equals("36")){	//ABC��������
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
	 * ��ԥ���˱�ȡ��������
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
		  if(num >= 19 )   //��֤
		  {
		    j=2;        //һ����֤λ��
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
	 * ��ԥ�ں��˵����ִ����Ӧ��������
	 * ��ʱ��ǰ�û��мӣ�ũ��2�����ߺ���B_pgzt.4gl�м�
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
					  name = "�������Ͷ�����ᱣ��";
					  break;
				  case 126:
					  name = "̩��ǧ������ȫ����(�ֺ���)";
					  break;
				  case 149:
					  name = "̩��ǧ������ȫ����(�ֺ���)B��";
					  break;
				  case 150:
					  name = "̩��ǧ������ȫ����(�ֺ���)C��";
					  break;
				  case 175:
					  name = "̩�����ٱ��ۺ������˺�����";
					  break;
				  case 180:
					  name = "̩����������������գ������ͣ�";
					  break;
				  case 185:
					  name = "̩����������������գ������ͣ�C��";
					  break;
				  case 187:
					  name = "̩��ϣ��֮����ȫ����(�ֺ���)";
					  break;
				  case 505:
					  name = "̩��������ȫ���գ��ֺ��ͣ�";
					  break;
				  case 506:
					  name = "̩���������Ͷ�����ᱣ��";
					  break;
				  case 507:
					  name = "̩��Ӯ�����Ͷ�����ᱣ��";
					  break;
				  case 508:
					  name = "̩��������ƾ�����������գ������ͣ�";
					  break;
				  case 509:
					  name = "̩��������ƲƸ����������գ������ͣ�";
					  break;
				  case 510:
					  name = "̩����������ȫ���գ��ֺ��ͣ�";
					  break;
//				  case 511:
//					  name = "̩���¸�����������գ������ͣ�";
//					  break;
//				  case 512:
//					  name = "̩��E���Ͷ�����ᱣ��";
//					  break;
//				  case 515:
//					  name = "̩���ͼ������������(������)";
//					  break;
//				  case 516:
//					  name = "̩�����Ӻͼ����Ͷ�����ᱣ��";
//					  break;
				  case 518:
					  name = "̩��Ӯ�����Ͷ�����ᱣ��";
					  break;
				  case 519:
					  name = "̩��������B������գ��ֺ��ͣ�";
					  break;
//				  case 522:
//					  name = "̩��e���B��Ͷ�����ᱣ��";
//					  break;
				  case 523:
					  name = "̩���Ҹ�����A����������գ��ֺ��ͣ�";
					  break;
				  case 530:
					  name = "̩��������D����ȫ���գ��ֺ��ͣ�";
					  break;
				}
			}catch(NumberFormatException ne){
				cLogger.error("���ִ���תInt����");
				return "";
			}
		}
		return name;
	}
	
	/**
	 * �˻�����ת������
	 * @param bm_class
	 * @return
	 */
	public static String getBmClassName(String bm_class){
    	String returnStr = "";
    	if("507001".equals(bm_class)){
    		returnStr = "�Ƚ�������Ͷ���ʻ�";
    	}
    	else if("507002".equals(bm_class)){
    		returnStr = "ƽ��������Ͷ���ʻ�";
    	}
    	else if("507003".equals(bm_class)){
    		returnStr = "�����ɳ���Ͷ���ʻ�";
    	}
    	else if("507004".equals(bm_class)){
    		returnStr = "����ѡͶ���ʻ�";
    	}
    	else if("518001".equals(bm_class)){
    		returnStr = "�Ƚ�������Ͷ���ʻ�";
    	}
    	else if("518002".equals(bm_class)){
    		returnStr = "ƽ��������Ͷ���ʻ�";
    	}
    	else if("518003".equals(bm_class)){
    		returnStr = "�����ɳ���Ͷ���ʻ�";
    	}
    	else if("518004".equals(bm_class)){
    		returnStr = "����ѡͶ���ʻ�";
    	}
    	else{
    		returnStr = bm_class;
    	}
    	return returnStr; 
	}
	
	//xiaowq 20130321 add ũ�п�������(538)�������
	public static String getErrInfo(String errCode){
		String info = "";
		if (errCode.equals("10888")) {
			info = "Ͷ���˻��Ᵽ���100�򣬲�������ͨ����";
		} else if(errCode.equals("10501")) {
			info = "������Ϊδ�������ۼ���ʱ���ܳ���10��Ԫ";
		} else if(errCode.equals("10511")) {
			info = "55�������ϣ���������ͨ����";
		} else if(errCode.equals("10502")) {
			//info = "18��45���꣬�ۼƷ��ձ���ܳ���40��Ԫ";
			info = "����������18-45��֮��,�ۼƷ��ձ����40��Ԫ�����";
		} else if(errCode.equals("10503")) {
			//info = "46��50���꣬�ۼƷ��ձ���ܳ���30��Ԫ";
			info = "����������46-50��֮��,�ۼƷ��ձ����30��Ԫ�����";
		} else if(errCode.equals("10504")) {
			//info = "51��55���꣬�ۼƷ��ձ���ܳ���10��Ԫ";
			info = "����������51-55��֮��,�ۼƷ��ձ����10��Ԫ�����";
		} else if(errCode.equals("10505")) {
			//info = "18��45���꣬�ۼƷ��ձ���ܳ���30��Ԫ";
			info = "����������18-45��֮��,�ۼƷ��ձ����30��Ԫ�����";
		} else if(errCode.equals("10506")) {
			//info = "46��50���꣬�ۼƷ��ձ���ܳ���20��Ԫ";
			info = "����������46-50��֮��,�ۼƷ��ձ����20��Ԫ�����";
		} else if(errCode.equals("10507")) {
			//info = "51��55���꣬�ۼƷ��ձ���ܳ���5��Ԫ";
			info = "����������51-55��֮��,�ۼƷ��ձ����5��Ԫ�����";
		} else if(errCode.equals("10508")) {
			//info = "18��45���꣬�ۼƷ��ձ���ܳ���20��Ԫ";
			info = "����������18-45��֮��,�ۼƷ��ձ����20��Ԫ�����";
		} else if(errCode.equals("10509")) {
			//info = "46��50���꣬�ۼƷ��ձ���ܳ���10��Ԫ";
			info = "����������46-50��֮��,�ۼƷ��ձ����10��Ԫ�����";
		} else if(errCode.equals("10510")) {
			info = "51��55���꣬��������ͨ����";
		} else if(errCode.equals("10151")) {
			info = "�ۼƱ���ô���30��";
		} else if(errCode.equals("10152")) {
			info = "�ۼƱ���ô���20��";
		}
		return info;
	}
	
	//��ȡRetData 1:Mesg
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
		cLogger.info("ũ�п�������(538)������Ϣ:"+errMsg);
		mMesg.setText(errMsg);

		mRetData.addContent(mFlag);
		mRetData.addContent(mMesg);

		return mRetData;
	}
	/**
	 * 
	 * @param pOutStd ���㷵�صı�׼����
	 * @param jyCode ���״���
	 * @return ���ĵ�ͷ��Ϣ
	 */
	public static Element getHeader(String pOutStd,String jyCode){
		Element head = new Element(Header);
//		<Header>
//	        <RetCode>000000</RetCode>
//	        <RetMsg>���׳ɹ�</RetMsg>
//	        <SerialNo>12345678</SerialNo>
//	        <InsuSerial>20130701000000000002</InsuSerial>
//	        <TransDate>20130703</TransDate>
//	        <BankCode>03</BankCode>
//	        <CorpNo>3002</CorpNo>
//	        <TransCode>1002</TransCode>
//		</Header>
		String returncode=decodeStr(pOutStd, PACKAGESPLITER, 12).trim();//������
		String errorinfo = ""; //������Ϣ����
		if (!returncode.equals("00000") &&(jyCode.equals("1012")||jyCode.equals("1014"))){//��ȫ��ѯ ������Ϣ����13λ
			errorinfo = decodeStr(pOutStd, PACKAGESPLITER, 13).trim();
		}else{
			if(!returncode.equals("0000")&&!returncode.equals("0") &&!returncode.equals("00000")&& returncode.length()>=5){
			
				errorinfo = returncode.substring(5,returncode.length());
				returncode = returncode.substring(0,5);
				cLogger.info("xxxxxxssss:"+returncode);
			}
		}
		cLogger.info("xxxxxx:"+returncode);
		Element RetCode = new Element("RetCode"); //���׷�����
		//Ӧ����Ҫ�� ����ɹ�����000000 ���ʧ�ܷ���009999 ������ۼƱ����009990
		if (returncode.equals("0000") || returncode.equals("0") || returncode.equals("00000")){
			RetCode.setText("000000");
		}else{
			RetCode.setText("009999");
		}
		
		head.addContent(RetCode);
		Element RetMsg = new Element("RetMsg"); //������Ϣ
		if(returncode.equals("0") || returncode.equals("00000")){
			RetMsg.setText("���׳ɹ�");
			
		}else{
			if(errorinfo.equals("") || errorinfo==null ){
				if(pOutStd.indexOf("RQ00T0001")!= -1){
					String error =AbcMidplatUtil.getValue(pOutStd, 87, 0, 0);
					System.out.println("Error:"+error);
					RetMsg.setText(error);
				}else{
					RetMsg.setText("����ʧ��");
				}
			}else{
				RetMsg.setText(errorinfo);
			}
			
		}
		head.addContent(RetMsg);
		Element SerialNo = new Element("SerialNo"); //������ˮ����
		SerialNo.setText("");
		head.addContent(SerialNo);
		Element InsuSerial = new Element("InsuSerial"); //���ս�����ˮ����
		InsuSerial.setText(decodeStr(pOutStd, PACKAGESPLITER, 7).trim());
		head.addContent(InsuSerial);
		
		Element  TransTime = new Element("TransTime"); //����ʱ��
		TransTime.setText(decodeStr(pOutStd, PACKAGESPLITER, 9).trim());
		head.addContent(TransTime);
		
		Element TransDate = new Element("TransDate");//��������
		TransDate.setText(decodeStr(pOutStd, PACKAGESPLITER, 8).trim());
		head.addContent(TransDate);
		
		Element BankCode = new Element("BankCode");//���д���
		BankCode.setText("04");
		head.addContent(BankCode);
		
		Element CorpNo = new Element("CorpNo");//���չ�˾����
		CorpNo.setText("1118"); //ABC zhong tk de daima 
		head.addContent(CorpNo);
		Element TransCode = new Element("TransCode"); //���ױ���
		TransCode.setText(jyCode);
		head.addContent(TransCode);
		return head;
	}
	public static String getnowdate(){
		SimpleDateFormat formate = new SimpleDateFormat("yyyyMMdd");
		String nowdate = formate.format(new Date());
		return nowdate;
	}
	public static String getPgcx_Values(String str, int num, int num1, int num2) //num1��num2��ʼλ��Ϊ1
	{
		  int RealNum=0;
		  int temp=0 ;
		  int i=0;
		  int j=0;
		  String tmpStr1="";
		  if(num >= 43 )   //������
		  {
		    j=10;        //һ��������λ��
		    RealNum=43;
		    tmpStr1 =decodeStr(str,"|", 43);
		    
		    temp = Integer.parseInt(tmpStr1);
		    RealNum += temp*j;	    
		    i = 43;
		  }
		  if(num >= 68)    //�����ո���
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