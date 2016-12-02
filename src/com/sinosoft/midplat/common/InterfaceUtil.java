package com.sinosoft.midplat.common;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;

public class InterfaceUtil {

	private final static Logger cLogger = Logger.getLogger(InterfaceUtil.class);
	private static CErrors cErrors = new CErrors();
//	public static String[] Err;
//	public static int Cout=0;

//	public static String[] getErr() {
//		return Err;
//	}
//
//
//	public static void setErr() {
////		String[] err;
//		Err = new String[2];
//		Cout = 0;
//	}


	public InterfaceUtil(){}
	
	
    public static CErrors getcErrors() {
		return cErrors;
	}


	public static void initcErrors() {
	       cErrors.clearErrors();
	}


	/**
     * ����������Ϊ������ţ�����ַ���
     * @param strMain String
     * @param strDelimiters String
     * ʧ�ܷ���NULL
     * @return String[]
     */
    public static String[] split(String strMain, String strDelimiters)
    {
        int i;
        int intIndex = 0; //��¼�ָ���λ�ã���ȡ���Ӵ�
        Vector vResult = new Vector(); //�洢�Ӵ�������
        String strSub = ""; //����Ӵ����м����

        strMain = strMain.trim();

        //�����ַ����ȷָ�������Ҫ�̵Ļ�,�򷵻ؿ��ַ���
        if (strMain.length() <= strDelimiters.length())
        {
            cLogger.info("�ָ��������ȴ��ڵ������ַ������ȣ����ܽ��в�֣�");
            return null;
        }

        //ȡ����һ���ָ����������е�λ��
        intIndex = strMain.indexOf(strDelimiters);

        //���������Ҳ����ָ���
        if (intIndex == -1)
        {
            String[] arrResult =
                    {
                    strMain};
            return arrResult;
        }

        //�ָ�������������
        while (intIndex != -1)
        {
            strSub = strMain.substring(0, intIndex);
            if (intIndex != 0)
            {
                vResult.add(strSub);
            }
            else
            {
                //break;
                vResult.add("");
            }

            strMain = strMain.substring(intIndex + strDelimiters.length()).trim();
            intIndex = strMain.indexOf(strDelimiters);
        }

        //�����ĩ���Ƿָ�����ȡ�����ַ���
//        if (!strMain.equals("") && strMain != null)
        if (!strMain.equals(""))
        {
            vResult.add(strMain);
        }

        String[] arrResult = new String[vResult.size()];
        for (i = 0; i < vResult.size(); i++)
        {
            arrResult[i] = (String) vResult.get(i);
        }

        return arrResult;
    }
       
      
    /**
     * ����ַÿ9���ֲ��Ϊһ��
     * @param Addr String
     * @return String[]
     */
    public static String[] seperateAddr(String Addr,String contNo){
    	   String[] Address=new String[3];
    	   if(Addr.length()<=9){
    		   Address[0]= Addr;
    		   return Address;
    	   }else if(Addr.length()<=18){
    		   Address[0]= Addr.substring(0, 9);
    		   Address[1]=Addr.substring(9, Addr.length());
    		   return Address;
    	   }else if(Addr.length()>27){
    		   Address[0]= Addr.substring(0, 9);
    		   Address[1]= Addr.substring(9, 18);
    		   Address[2]= Addr.substring(18, 27);
    		   CError sError = new CError();
				sError.moduleName = "InterfaceUtil";
				sError.functionName = "seperateAddr";
				sError.errorMessage = "����"+contNo+"��ַ��Ϣ������"+Addr;
				cErrors.addOneError(sError);  		   
    		   return Address;
    	   }else{
    		   Address[0]= Addr.substring(0, 9);
    		   Address[1]= Addr.substring(9, 18);
    		   Address[2]= Addr.substring(18, Addr.length());
    		   return Address;
    	   }
    	   
       }
      
    
    /**
     * ��������Ч������28���Ժ���ȫ����Ϊ28��
     * @param Date String
     * @return String
     */
       public static String EffectiveDate (String Date){
    	   String Day=Date.substring(Date.length()-2, Date.length());
    	   if(Integer.parseInt(Day)>28){
    		   StringBuffer sDate=new StringBuffer(Date);
    		   sDate.replace(Date.length()-2, Date.length(), "28");
    		   return sDate.toString();
    		   
    	   }
    	   return Date;
       }
       
       /**
        * ���ѷ�ʽӳ��
        * @param sMessage String
        * @return String
        */
       public static String getPayintv(String sMessage){
    	   String RiskCode=split(sMessage,";")[0];
    	   String PayIntv = split(sMessage,";")[1];
    	   
    	   if(RiskCode.equals("NHONP") || RiskCode.equals("GHONP") || RiskCode.equals("HONPG3")){
    		   return "M";
    	   }else if(RiskCode.equals("NBSP") || RiskCode.equals("SPPACA") || RiskCode.equals("SPPACB")){
    		   return "A";
    	   }else if(PayIntv.equals("1")){
    		   return "A";
    	   }else if(PayIntv.equals("2")){
    		   return "M";
    	   }else if(PayIntv.equals("3")){
    		   return "S";
    	   }else if(PayIntv.equals("4")){
    		   return "Q";
    	   }
    	   return "";
       }
       
       /**
        * ���ִ���ӳ��
        * @param sMessage String
        * @return String
        */
       public static String getRiskCod(String sMessage){
    	   String riskcode = split(sMessage,";")[0];
    	   String prem = split(sMessage,";")[1];
    	   String riskalias=split(sMessage,";")[2];
    	   
    	   if(riskcode.equals("NHONP")){
    		   if(Double.parseDouble(prem)>=50000){
    			   return "BHONP";
    		   }else{
    			   return "NHONP";
    		   }
    	   }else if(riskcode.equals("HONPG3")){
    		   if(Double.parseDouble(prem)>=100000){
    			   return "HONPG3H";
    		   }else{
    			   return "HONPG3L";
    		   }
    	   }else {
    		   return riskalias;
    	   }
    	   
       }
       
       /**
        * �������봦������Ͷ�����й̻�λ����ȡ���Nλ�����̻�λ��û����lmbankmap���������ȡ��7λ
        * @param sMessage String
        * @return String
        */
       public static String getPhoneNo(String phoneNo,String contNo,String type){
    
    	   if (split(phoneNo,";")[0].length()>15){
    		   CError sError = new CError();
				sError.moduleName = "InterfaceUtil";
				sError.functionName = "seperateAddr";
				sError.errorMessage = "����"+contNo+""+type+"��Ϣ������"+split(phoneNo,";")[0];
				cErrors.addOneError(sError); 
    	   }
    	   int citybyte=0;
    	   String zipcode="";
    	   int zipcodebyte=0;
    	   Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$"); 
    	   
//    	   String s = "^(13[0-9]|15[0|3|6|7|8|9]|18[8|9])\\d{8}";
    	  if(split(phoneNo,";")[0]!=""){
    		  String phone = split(phoneNo,";")[0];
    	     if("86".equals(phone.substring(0,2))){
    		      phone=phone.substring(2, phone.length());
    	      }
    	     Matcher m = p.matcher(phone); 
   	         if(m.matches()){
	    	   return phone;
	         }
   	         if(phone.length()>=8){
   	        	if(split(phoneNo,";")[2]!=""){
     	    	   zipcode = split(phoneNo,";")[2];
     	    	   zipcodebyte = zipcode.length();
     	    	   if(phone.substring(0,zipcodebyte).equals(zipcode)){
     	    		   return phone.substring(zipcodebyte, phone.length());
     	    	   }else{
     	    		  if(phone.length()==15 && "028".equals(zipcode) && (phone.substring(0,3).equals("083")||phone.substring(0,3).equals("082")||phone.substring(0,3).equals("081")) ){
     	    			 return phone.substring(phone.length()-11, phone.length());  
     	    		  } 
     	    		   
     	    		  if(phone.length()>15){
     	    			 return phone.substring(phone.length()-15, phone.length()); 
     	   	         }
//     	    	       citybyte=Integer.parseInt(split(phoneNo,";")[1]);
//     	    	       return phone.substring(phone.length()-citybyte, phone.length());
     	    		  return phone;
     	       }    
     	    	   
     	            } 
   	         }
   	         
    	  }
    	   
    	   return split(phoneNo,";")[0];
       }
       
       public static String getMobile(String mobileNo){
    	   if(mobileNo.length()>11){
    		   mobileNo=mobileNo.substring(mobileNo.length()-11, mobileNo.length());
    	   }
    	   return mobileNo;
       }
       
       
       public static String getYINC(String sMessage){
    	   String birthday = split(sMessage,";")[0];
    	   String makeday = split(sMessage,";")[1];
    	   int appntAge=MidplatUtil.calInterval(birthday,
    			   makeday, "Y", "AXA");
    	   if(appntAge<18){
    		   return "0";
    	   }
    	   return "3000";
       }
       
       public static String getTUSOCL(String sMessage){
    	   String birthday = split(sMessage,";")[0];
    	   String makeday = split(sMessage,";")[1];
    	   int appntAge=MidplatUtil.calInterval(birthday,makeday, "Y", "AXA");
    	   if(appntAge<18){
    		   return "4";
    	   }
    	   return "1";
       }
       
   	public static void main(String args[]){
//   		String[] Address = seperateAddr("����ôڤ�����Ҽ����Ѳ������������������������̫̫̫̫̫������������������");
//   		System.out.println(Address[0]);
//   		System.out.println(Address[1]);
//   		System.out.println(Address[2]);
//   		
//   		System.out.println(getYINC("1988-03-10"));
   		
   		System.out.println(getPhoneNo("08281380013800;7;028","101-1234567","�ֻ�"));
   		
	
	}

	
}
