package com.sinosoft.midplat.common;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;



import com.sinosoft.midplat.exception.MidplatException;
public class YBTDataVerification {
	 
	public YBTDataVerification(){}
	
	/**
	 * 对邮政编码进行有效性校验
	 * @param ZipCode
	 * @return String flag 
	 */  
	public static String ZipCodeVerification(String ZipCode) {
		System.out.println("对邮编进行校验");
		 String flag = "0";
		if (ZipCode.matches("\\d{6}")) {
			flag = "1"; 
			return flag;  
		} 
		System.out.println("返回的结果是:" + flag);
		return flag;
	}
	
	/**
	 * 对单证号进行8位数字校验
	 * @param ZipCode
	 * @return String flag 
	 */  
	public static String PrtNoVerification(String PrtNo) {
		System.out.println("单证号进行校验");
		
		 String flag = "0";
		 if(PrtNo == null || "".equals(PrtNo)){
				return  "1";
		}
		 
		 if(PrtNo.length() != 8){
			 return  "1";
		 }
		 Pattern pattern = Pattern.compile("[0-9]*");
         Matcher isNum = pattern.matcher(PrtNo);
         if( !isNum.matches() )
         {
        	 flag = "1";
         }

		System.out.println("返回的结果是:" + flag);
		return flag;
	}
	     
	
	/**
	 * 同一受益顺序受益人验证
	 * @param InXmlDoc XML文档
	 * @return
	 */
	public  boolean  SameGradeBnfVerification(Document InXmlDoc) {	
		// 受益人列表   
		Element mRootEle = InXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild("Body");
		
		String tBnfGrade = "";
		List bnfs = new ArrayList(); //用来装受益人
		List bnfLots = new ArrayList(); //用来装受益人
		bnfs = mBodyEle.getChildren("Bnf");
		for(int i = 0; i<bnfs.size(); i++){
			Element bnf = (Element) bnfs.get(i);
			String beneficType = bnf.getChildTextTrim("BeneficType");			
			if(beneficType.equals("N")){
				String[] tBnfLot1 = new String[2];
				tBnfLot1[0] = bnf.getChildText("Grade");
				tBnfLot1[1] =  bnf.getChildText("Lot");
				bnfLots.add(tBnfLot1);
			}
		} 
		
	// 判断同一顺序的受益人份额是否为100% 
	for (int i = 0; i < bnfLots.size(); i++)
	{
		String[] tArrBnfLoti = (String[]) bnfLots.get(i);
		String tBnfGradei = tArrBnfLoti[0];
		double tBnfLoti = Double.parseDouble(tArrBnfLoti[1]);
		
		// 如果仅有一个受益人，则直接进行判断 
		if (bnfLots.size() == 1)
		{
			if (tBnfLoti < 100 || tBnfLoti>100)
			{	   
				return false;
			}
		}
		
		if ("".equals(tBnfGrade) || tBnfGrade.indexOf(tBnfGradei) < 0)
		{
			for (int j = i + 1; j < bnfLots.size(); j++)
			{
				String[] tArrBnfLotj = (String[]) bnfLots.get(j);
				String tBnfGradej = tArrBnfLotj[0];
				double tBnfLotj = Double.parseDouble(tArrBnfLotj[1]);
				if (tBnfGradej.equals(tBnfGradei))
				{
					// 设置已经判断过的受益人顺序
					tBnfGrade += tBnfGradej + ",";
					// 设置同一受益顺序的受益人总份额
				tBnfLoti += tBnfLotj;
				}
				if (j == bnfLots.size() - 1)
				{
				if (tBnfLoti > 100)
					{
					return false;
					}
					if (tBnfLoti < 100)
					{
						return false;
					}
				} 
			}
			if (i == bnfLots.size() - 1)
			{
				if (tBnfLoti < 100)
				{
					return false;
				}
		} 
		}
	}
	return true;
	} 
	

	public  boolean digitBnfVerification(Document InXmlDoc) {
		boolean digitFlag = true;
//		 受益人列表
		Element mRootEle = InXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild("Body");
		List bnfs = new ArrayList(); //用来装受益人
		List<Integer> bnfGrades = new ArrayList<Integer>(); //用来装受益人 
		bnfs = mBodyEle.getChildren("Bnf"); 
		for(int i = 0; i<bnfs.size(); i++){
			Element bnf = (Element) bnfs.get(i);
			String beneficType = bnf.getChildTextTrim("BeneficType");	
			String Grade = bnf.getChildTextTrim("Grade"); 
			int iGrade = Integer.valueOf(Grade);
			if(beneficType.equals("N")){
				bnfGrades.add(iGrade);
			}
		}
System.out.println(bnfGrades);
		//对数组排序
		Collections.sort(bnfGrades);
System.out.println(bnfGrades); 
        //检查跳号
		digitFlag = check(bnfGrades);
		      
		return digitFlag;			
	}
	   
	public  boolean check(List<Integer> bnfGrades){
		for(int i = 0; i < bnfGrades.size()-1 ; i ++){
			if((bnfGrades.get(i+1)-bnfGrades.get(i))>1){
				return false;  
				}
		}
		return true;  
	}
	
	
	/**
	 * 对同一顺序、同一类型收益份额之和是否为1的校验
	 * @param InXmlDoc
	 * @return String flag
	 * @throws MidplatException 
	 */	
	public  boolean  SameGradeTypeBnfVerification(Document InXmlDoc) {	
		// 受益人列表   
		Element mRootEle = InXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild("Body");
		
		String tBnfGrade = "";
		List bnfs = new ArrayList(); //用来装受益人
		List bnfLots = new ArrayList(); //用来装受益人
		bnfs = mBodyEle.getChildren("Bnf");
		for(int i = 0; i<bnfs.size(); i++){
			Element bnf = (Element) bnfs.get(i);
			String beneficType = bnf.getChildTextTrim("BeneficType");			
			if(beneficType.equals("N")){
				String[] tBnfLot1 = new String[3];
				tBnfLot1[0] = bnf.getChildText("Grade");
				tBnfLot1[1] =  bnf.getChildText("Lot");
				
				tBnfLot1[2] =  bnf.getChildText("Type");
				bnfLots.add(tBnfLot1);
			}
		} 
		
	// 判断同一顺序的受益人份额是否为100% 
	for (int i = 0; i < bnfLots.size(); i++)
	{
		String[] tArrBnfLoti = (String[]) bnfLots.get(i);
		String tBnfGradei = tArrBnfLoti[0];
		double tBnfLoti = Double.parseDouble(tArrBnfLoti[1]);
		String tBnfTypei = tArrBnfLoti[2];
		
		// 如果仅有一个受益人，则直接进行判断 
		if (bnfLots.size() == 1)
		{
			if (tBnfLoti < 100 || tBnfLoti>100)
			{	   
				return false;
			}
		}
		
		if ("".equals(tBnfGrade) || tBnfGrade.indexOf(tBnfGradei) < 0)
		{
			for (int j = i + 1; j < bnfLots.size(); j++)
			{
				String[] tArrBnfLotj = (String[]) bnfLots.get(j);
				String tBnfGradej = tArrBnfLotj[0];
				double tBnfLotj = Double.parseDouble(tArrBnfLotj[1]);
				
				String tBnfTypej = tArrBnfLotj[2];
				//同一顺序，同一类型
				if (tBnfGradej.equals(tBnfGradei)&& tBnfTypej.equals(tBnfTypei))
				{
					// 设置已经判断过的受益人顺序
					tBnfGrade += tBnfGradej + ",";
					// 设置同一受益顺序,同一类型的受益人总份额
				tBnfLoti += tBnfLotj;
				}
				if (j == bnfLots.size() - 1)
				{
				if (tBnfLoti > 100)
					{
					return false;
					}
					if (tBnfLoti < 100)
					{
						return false;
					}
				} 
			}
			if (i == bnfLots.size() - 1)
			{
				if (tBnfLoti < 100)
				{
					return false;
				}
		} 
		}
	}
	return true;
	} 
	
	
	public static void main(String [] args){
		System.out.println(PrtNoVerification("11112222"));
	}
}


 