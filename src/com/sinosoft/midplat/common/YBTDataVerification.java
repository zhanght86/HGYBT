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
	 * ���������������Ч��У��
	 * @param ZipCode
	 * @return String flag 
	 */  
	public static String ZipCodeVerification(String ZipCode) {
		System.out.println("���ʱ����У��");
		 String flag = "0";
		if (ZipCode.matches("\\d{6}")) {
			flag = "1"; 
			return flag;  
		} 
		System.out.println("���صĽ����:" + flag);
		return flag;
	}
	
	/**
	 * �Ե�֤�Ž���8λ����У��
	 * @param ZipCode
	 * @return String flag 
	 */  
	public static String PrtNoVerification(String PrtNo) {
		System.out.println("��֤�Ž���У��");
		
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

		System.out.println("���صĽ����:" + flag);
		return flag;
	}
	     
	
	/**
	 * ͬһ����˳����������֤
	 * @param InXmlDoc XML�ĵ�
	 * @return
	 */
	public  boolean  SameGradeBnfVerification(Document InXmlDoc) {	
		// �������б�   
		Element mRootEle = InXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild("Body");
		
		String tBnfGrade = "";
		List bnfs = new ArrayList(); //����װ������
		List bnfLots = new ArrayList(); //����װ������
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
		
	// �ж�ͬһ˳��������˷ݶ��Ƿ�Ϊ100% 
	for (int i = 0; i < bnfLots.size(); i++)
	{
		String[] tArrBnfLoti = (String[]) bnfLots.get(i);
		String tBnfGradei = tArrBnfLoti[0];
		double tBnfLoti = Double.parseDouble(tArrBnfLoti[1]);
		
		// �������һ�������ˣ���ֱ�ӽ����ж� 
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
					// �����Ѿ��жϹ���������˳��
					tBnfGrade += tBnfGradej + ",";
					// ����ͬһ����˳����������ܷݶ�
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
//		 �������б�
		Element mRootEle = InXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild("Body");
		List bnfs = new ArrayList(); //����װ������
		List<Integer> bnfGrades = new ArrayList<Integer>(); //����װ������ 
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
		//����������
		Collections.sort(bnfGrades);
System.out.println(bnfGrades); 
        //�������
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
	 * ��ͬһ˳��ͬһ��������ݶ�֮���Ƿ�Ϊ1��У��
	 * @param InXmlDoc
	 * @return String flag
	 * @throws MidplatException 
	 */	
	public  boolean  SameGradeTypeBnfVerification(Document InXmlDoc) {	
		// �������б�   
		Element mRootEle = InXmlDoc.getRootElement();
		Element mBodyEle = mRootEle.getChild("Body");
		
		String tBnfGrade = "";
		List bnfs = new ArrayList(); //����װ������
		List bnfLots = new ArrayList(); //����װ������
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
		
	// �ж�ͬһ˳��������˷ݶ��Ƿ�Ϊ100% 
	for (int i = 0; i < bnfLots.size(); i++)
	{
		String[] tArrBnfLoti = (String[]) bnfLots.get(i);
		String tBnfGradei = tArrBnfLoti[0];
		double tBnfLoti = Double.parseDouble(tArrBnfLoti[1]);
		String tBnfTypei = tArrBnfLoti[2];
		
		// �������һ�������ˣ���ֱ�ӽ����ж� 
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
				//ͬһ˳��ͬһ����
				if (tBnfGradej.equals(tBnfGradei)&& tBnfTypej.equals(tBnfTypei))
				{
					// �����Ѿ��жϹ���������˳��
					tBnfGrade += tBnfGradej + ",";
					// ����ͬһ����˳��,ͬһ���͵��������ܷݶ�
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


 