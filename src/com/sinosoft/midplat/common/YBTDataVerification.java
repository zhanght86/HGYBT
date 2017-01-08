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

/**
 * @ClassName: YBTDataVerification
 * @Description: YBT���ݼ���
 * @author yuantongxin
 * @date 2017-1-6 ����10:16:25
 */
public class YBTDataVerification {
	 
	/**
	 * <p>Title: YBTDataVerification</p>
	 * <p>Description: YBT���ݼ��鹹����</p>
	 */
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
	 * @param InXmlDoc ��׼���뱨��
	 * @return 
	 */
	public  boolean  SameGradeBnfVerification(Document InXmlDoc) {	
		//��׼���뱨�ĸ��ڵ�
		Element mRootEle = InXmlDoc.getRootElement();
		//�������ӽڵ�
		Element mBodyEle = mRootEle.getChild("Body");
		//����˳��
		String tBnfGrade = "";
		// �������б�   
		List bnfs = new ArrayList(); //����װ������
		//��������б�
		List bnfLots = new ArrayList(); //����װ�������
		//��ȡ�����������������ӽڵ�
		bnfs = mBodyEle.getChildren("Bnf");
		//�����������б� 
		for(int i = 0; i<bnfs.size(); i++){
			//�������ӽڵ�
			Element bnf = (Element) bnfs.get(i);
			//�õ�����������Ԫ��ȥ���ո����ı�����[�̶�ΪN]
			String beneficType = bnf.getChildTextTrim("BeneficType");			
			//��������ΪN
			if(beneficType.equals("N")){
				//�����������[����˳��,�������]
				String[] tBnfLot1 = new String[2];
				//��ȡ����������˳���ӽڵ��ı�����
				tBnfLot1[0] = bnf.getChildText("Grade");
				//��ȡ��������������ӽڵ��ı�����
				tBnfLot1[1] =  bnf.getChildText("Lot");
				//��������б���뵱ǰ�����������
				bnfLots.add(tBnfLot1);
			}
		} 
		
	// �ж�ͬһ˳��������˷ݶ��Ƿ�Ϊ100% 
	//������������б�
	for (int i = 0; i < bnfLots.size(); i++)
	{
		//��ȡ�����������
		String[] tArrBnfLoti = (String[]) bnfLots.get(i);
		//��ȡ���������������˳��
		String tBnfGradei = tArrBnfLoti[0];
		//��ȡ������������������[˫����]
		double tBnfLoti = Double.parseDouble(tArrBnfLoti[1]);
		
		// �������һ�������ˣ���ֱ�ӽ����ж� 
		//�����������������
		if (bnfLots.size() == 1)
		{
			//�������������������100
			if (tBnfLoti < 100 || tBnfLoti>100)
			{	  
				//������������Ƿ�
				return false;
			}
		}
		
		//����˳��Ϊ�ա�����˳���ڵ�ǰ����˳����
		if ("".equals(tBnfGrade) || tBnfGrade.indexOf(tBnfGradei) < 0)
		{
			//������������б�[�ӵڶ�������������鿪ʼ]
			for (int j = i + 1; j < bnfLots.size(); j++)
			{
				//��ȡ�����������
				String[] tArrBnfLotj = (String[]) bnfLots.get(j);
				//��ȡ���������������˳��
				String tBnfGradej = tArrBnfLotj[0];
				//��ȡ������������������
				double tBnfLotj = Double.parseDouble(tArrBnfLotj[1]);
				//��ǰ����˳������׸�����˳��
				if (tBnfGradej.equals(tBnfGradei))
				{
					// �����Ѿ��жϹ���������˳��
					//����˳��ƴ���ϵ�ǰ����˳��[��,�ָ�]
					tBnfGrade += tBnfGradej + ",";
					// ����ͬһ����˳����������ܷݶ�
					//����ͬһ����˳���������֮��[�׸���������ۼӵ�ǰ�������]
					tBnfLoti += tBnfLotj;
				}
				//��ǰ�±�Ϊ��������б����һ��Ԫ���±�
				if (j == bnfLots.size() - 1)
				{
					//��������ܺ͹���
					if (tBnfLoti > 100)
					{
						//������������Ƿ�
						return false;
					}
					//��������ܺ͹�С
					if (tBnfLoti < 100)
					{
						//������������Ƿ�
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
	//������������Ϸ�
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


 