package com.sinosoft.lis.pubfun;
/**
 *  �����࣬��ӭ��Ҹ���
 *  author:������
 *  mailto:liujc@sinosoft.com.cn
 */
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.sinosoft.lis.db.*;
import com.sinosoft.lis.pubfun.ExportExcel;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.Schema;
import com.sinosoft.utility.SchemaSet;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YayaGMFun {
	public static  boolean promissoryFlag=false;//�Ƿ���Լ���ı��calbl������
	
	public static boolean IsHaveBQ(String grpcontno)
	{
		String sql="select count(*) from Lpgrpedoritem where grpcontno='"+grpcontno+"' ";
		String rel=(new ExeSQL()).getOneValue(sql);
		if(rel!=null && rel.equals("0"))
		{
			return false;
		}
		else if(rel!=null && !rel.equals("0"))
		{
			return true;
		}
		
		return false;
	}
	

    

	
	public static String getCvalidateDate(String contno)
	{
		LCContDB tLCContDB = new LCContDB();
		tLCContDB.setContNo(contno);
		if(tLCContDB.query().size()<=0)
		{
			return null;
		}
		else
		{
			return tLCContDB.query().get(1).getCValiDate();
		}
	}
	
	public static String getJoinCompanyDate(String contno)
	{
		LCInsuredDB tLCInsuredDB = new LCInsuredDB();
		tLCInsuredDB.setContNo(contno);
		if(tLCInsuredDB.query().size()<=0)
		{
			return null;
		}
		else
		{
			return tLCInsuredDB.query().get(1).getJoinCompanyDate();
		}
	}
	
	/**
	 * �Ƿ�Ϊ���������������
	 * @param riskcode
	 * @return
	 */
	public static boolean IsWNJRiskForRiskCode(String riskcode)
	{
		try
		{
			if(riskcode==null || riskcode.equals(""))
			{
				return false;
			}
			String sql="select count(*) from lmriskapp where riskcode='"+riskcode+"' and Risktype3='4' and Risktype4='4' ";
			String rel=(new ExeSQL()).getOneValue(sql);
			if(rel!=null && !rel.equals("") && !rel.equals("0"))
			{
				return true;
			}
			else
			{
				return false;
			}
		}catch(Exception ex)
		{
			return false;
		}
	}
	
	public static String GetAccTypeByInsurAccNo(String Insuraccno)
	{
		if(Insuraccno==null || Insuraccno.equals(""))
		{
			return null;
		}
		
		String sql="select acctype from Lmriskinsuacc where insuaccno='"+Insuraccno+"' ";
		String rel=(new ExeSQL()).getOneValue(sql);
		if(rel==null || rel.equals(""))
		{
			return null;
		}
		else
		{
			return rel;
		}
	}
	
	public static double getInterestrate_GrpSpecialmedicalRisk(String Grpcontno,String Insuaccno)
	{
		try
		{
			if(Grpcontno==null || Grpcontno.equals(""))
			{
				return -1;
			}
			
			if(Insuaccno==null || Insuaccno.equals(""))
			{
				return -1;
			}
			
			String sql="select Interestrate from LCGrpAccType where  grpcontno='"+Grpcontno+"' and  Insuaccno='"+Insuaccno+"' ";
			String rel=(new ExeSQL()).getOneValue(sql);
			if(rel==null || rel.equals(""))
			{
				return -1;
			}

			return Double.parseDouble(rel);
		}catch(Exception ex)
		{
			return -1;
		}
	}
	
	/**
	 * 
	 * @param Grpcontno
	 * @param Insuaccno
	 * @return ���践�ؽ�Լ
	 */
	public static double getChargerate_GrpSpecialmedicalRisk(String Grpcontno,String Insuaccno)
	{
		try
		{
			if(Grpcontno==null || Grpcontno.equals(""))
			{
				return -1;
			}
			
			if(Insuaccno==null || Insuaccno.equals(""))
			{
				return -1;
			}
			
			String sql="select chargerate from lcgrpcharge a where a.grpcontno='"+Grpcontno+"' and Insuaccno='"+Insuaccno+"' ";
			String rel=(new ExeSQL()).getOneValue(sql);
			if(rel==null || rel.equals(""))
			{
				return -1;
			}
			
			return Double.parseDouble(rel);
		}catch(Exception ex)
		{
			return -1;
		}
	}
	
	/**
	 * 
	 * @param Grpcontno
	 * @param Insuaccno
	 * @return ���� GL �����ͱ�����Ҫ��Ϣ����BZ �����ͱ���
	 */
	public static String WhAttribute_GrpSpecialmedicalRisk(String Grpcontno,String Insuaccno)
	{
		if(Grpcontno==null || Grpcontno.equals(""))
		{
			return null;
		}
		
		if(Insuaccno==null || Insuaccno.equals(""))
		{
			return null;
		}
		
		String sql="select count(*) from LCGrpAccType a where a.grpcontno='"+Grpcontno+"' and Insuaccno='"+Insuaccno+"' and Insuacctype='0' ";
		String rel=(new ExeSQL()).getOneValue(sql);
		if(rel==null || rel.equals(""))
		{
			return null;
		}
		else
		{
			if(!rel.equals("0"))
			{
				return "GL";
			}
			else
			{
				return "BZ";
			}
		}
	}

	/**
	 * 
	 * @param edorno
	 * @return
	 */
	public static String WhBQMoneySource_GrpSpecialmedicalRisk(String edorno)
	{
		if(edorno==null || edorno.equals(""))
		{
			return null;
		}
		
		String sql="select lpgrpedoritem.Edorreasonno from lpgrpedoritem where edorno='"+edorno+"' ";
		String rel=(new ExeSQL()).getOneValue(sql);
		if(rel!=null && !rel.equals(""))
		{
			if(rel.equals("1"))
			{
				return "Person";
			}
			else if(rel.equals("0"))
			{
				return "GrpAcc";
			}
		}
		
		return null;
	}
	/**
	 * �Ƿ�Ϊ��������ҽ������
	 * @param grppolno,����Ϊ��
	 * @param grpcontno
	 * @return  "Y"
	 */
	public static String IsSpecialmedicalRiskForGrpNumber(String grppolno,String grpcontno)
	{
		if((grppolno==null || grppolno.equals("")) && (grpcontno==null || grpcontno.equals("")))
		{
			return null;
		}
		
		if((grppolno==null || grppolno.equals("")) &&   (grpcontno!=null && !grpcontno.equals("")))
		{
			String sql="select count(*) from lcgrppol where grpcontno='"+grpcontno+"' and riskcode in (select riskcode from Lmriskapp where risktype3='5') ";
			String rel=(new ExeSQL()).getOneValue(sql);
			if(rel!=null)
			{
				if (rel!="0")
				{
					return "Y";
				}
				else
				{
					return "N";
				}
			}
			else
			{
				return null;
			}
		}
		
		String sql="select count(*) from lcgrppol where grppolno='"+grppolno+"' and riskcode in (select riskcode from Lmriskapp where risktype3='5') ";
		String rel=(new ExeSQL()).getOneValue(sql);
		if(rel!=null)
		{
			if (rel!="0")
			{
				return "Y";
			}
			else
			{
				return "N";
			}
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * �Ƿ�Ϊ��������ҽ������
	 * @param riskcode
	 * @return
	 */
	public static boolean IsSpecialmedicalRiskForRiskCode(String riskcode)
	{
		try
		{
			if(riskcode==null || riskcode.equals(""))
			{
				return false;
			}
			String sql="select count(*) from lmriskapp where riskcode='"+riskcode+"' and risktype3='5' ";
			String rel=(new ExeSQL()).getOneValue(sql);
			if(rel!=null && !rel.equals("") && !rel.equals("0"))
			{
				return true;
			}
			else
			{
				return false;
			}
		}catch(Exception ex)
		{
			return false;
		}
	}
	
	public static  String IsSameYearAndMonth(String date1,String date2)
	{
		if(date1==null || date1.equals("") || date2==null || date2.equals(""))
		{
			return null;
		}
	    if(!YayaGMFun.checkDate(date1, "yyyy-M-d", false) && !YayaGMFun.checkDate(date1, "yyyy-MM-dd", false))
	    {
	    	  return null;
	    }
	    if(!YayaGMFun.checkDate(date2, "yyyy-M-d", false) && !YayaGMFun.checkDate(date2, "yyyy-MM-dd", false))
	    {
	    	  return null;
	    }
	    
	    String DateCompareSql1="select year(to_date('"+date1+"','yyyy-mm-dd')) || month(to_date('"+date1+"','yyyy-mm-dd')) from dual ";
	    String DateCompareSql2="select year(to_date('"+date2+"','yyyy-mm-dd')) || month(to_date('"+date2+"','yyyy-mm-dd')) from dual ";
	    
	    String DateCompareRel1=(new ExeSQL()).getOneValue(DateCompareSql1);
	    String DateCompareRel2=(new ExeSQL()).getOneValue(DateCompareSql2);
	    
	    if(DateCompareRel1==null || DateCompareRel1.equals("") || DateCompareRel2==null || DateCompareRel2.equals(""))
	    {
	    	return null;
	    }
	    
	    if(DateCompareRel1.equals(DateCompareRel2))
	    {
	    	return "Y";
	    }
	    else
	    {
	    	return "N";
	    }
	}

	
	public static String getEdorCodeByEdoracceptNo(String EdoracceptNo)
	{
		if(EdoracceptNo==null || EdoracceptNo.equals(""))
		{
			return null;
		}
		String sql="(select Edortype from Lpedoritem where edoracceptno='"+EdoracceptNo+"') union (select Edortype from Lpgrpedoritem where edoracceptno='"+EdoracceptNo+"') ";
		String rel=(new ExeSQL()).getOneValue(sql);
		if(rel==null || rel.equals(""))
		{
			return null;
		}
		return rel;
	}
	/**
	 *
	 * @param contno
	 * @return  ��ѯ��������
	 */
	public static String getChannel(String contno)
	{
		if(contno==null || contno.equals(""))
		{
			return null;
		}
		String sql="select a.Salechnl from lccont a where a.contno='"+contno+"' ";
		ExeSQL tExeSQL=new ExeSQL();
		String Salechnl=tExeSQL.getOneValue(sql);
		if(Salechnl==null || Salechnl.equals(""))
		{
			return null;
		}
		return Salechnl;
	}
	/**
	 * 
	 * @param RiskCode
	 * @return  �������Y��ʾ���ֱ仯��Ӱ�챣�ѣ��������N��ʾ���ֱ仯��Ӱ�챣��������������ֵ��ʾ������δ֪��״̬���ߴ���
	 */
	public static String IsAffectPrem(String RiskCode)
	{
		ExeSQL tExeSQL=new ExeSQL();
		String sql="select dutycode from Lmriskduty where riskcode='"+RiskCode+"' and rownum<2";
		String rel=tExeSQL.getOneValue(sql);
		if(rel!=null)
		{
             sql="select calmode from lmduty where dutycode='"+rel+"'  and rownum<2";
             rel=tExeSQL.getOneValue(sql);
		}
		else
		{
			return null;
		}
		
		if(rel!=null)
		{
			if(rel.equals("G"))
			{
				return "Y";
			}
			else if(rel.equals("O"))
			{
				return "N";
			}
			else
			{
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	/**
	 * 
	 * @param str
	 * @param Delimiters
	 * @param inStr
	 * @return  �����ַ��Ƿ����ַ��������У���InStr("112;123;212",null,"212")����true
	 */
	public static boolean IsInStr(String str,String Delimiters,String inStr)
	{
	   if(Delimiters==null)
	   {
		   Delimiters=";";
	   }
	   if(str==null)
	   {
		   return false;
	   }
	   if(inStr==null)
	   {
		   return false;
	   }
	   String[] values=null;
	   values=str.split(";");
	   for(int i=0;i<values.length;i++)
	   {
		   String value=values[i];
		   if(value.equals(inStr))
		   {
			   return true;
		   }
	   }
	   return false;
	}
	
	public static  String SwitchtoPercent(String str)
	{
		try
		{
			if(str==null)
				return null;
			
			if(str.indexOf("%")!=-1)
			{
				return str;
			}
			return YayaGMFun.RRetainTwoDecimal(String.valueOf(Double.parseDouble(str)*100))+"%";
		}catch(Exception ex)
		{
			return str;
		}

	}
	
	public static LinkedHashMap conversionRankMap(LinkedHashMap  tLinkedHashMap,String rankStr)
	{
		LinkedHashMap tRankLinkedHashMap=new LinkedHashMap();
		int sort=1;
		
		//����
        System.out.println(tLinkedHashMap);
		
        //����
        
        //���ȣ�����һ��������ȥ
        double[] dbl=new double[tLinkedHashMap.size()];
        int i=0;
        Iterator iterator = tLinkedHashMap.keySet().iterator(); 
		while (iterator.hasNext()) { 
	       Object value = tLinkedHashMap.get(iterator.next()); 
	       dbl[i]=PubFun.setPrecision(Double.parseDouble(value.toString()), rankStr);
	       i++;
	        }
	   //�õ��ظ������ʶ����������
	   List noDupList=DealDuplicateArray(dbl,rankStr);

	   
	   //�����µ�˳��������
	   for(int j=noDupList.size()-1;j>=0;j--)//�Ӵ�С
	   {
		   System.out.println("noDupList:"+noDupList.get(j));
		   List tList=(List)getKey(String.valueOf(noDupList.get(j)),tLinkedHashMap);
		   for(int k=0;k<tList.size();k++)
		   {
			   String key=(String)tList.get(k);
			   tRankLinkedHashMap.put(key, String.valueOf(sort));
		   }
		   sort++;
	   }
	   
	   System.out.println(tRankLinkedHashMap);
	   
	   return tRankLinkedHashMap;
	}
	
	private static  Object getKey(Object value,Map map)
	{
		Object o=null;
		ArrayList all=new ArrayList();
		Set set=map	.entrySet();
		Iterator it=set.iterator();
		while(it.hasNext())
		{
			Map.Entry entry=(Map.Entry)it.next();
			if(entry.getValue().equals(value))
			{
				o=entry.getKey();
				all.add(o);
			}
		}
		return all;
	}
	
	private static List DealDuplicateArray(double[] db,String rankStr)
	{
		List noDuplicateList=new ArrayList();
		Set duplicateSet=new HashSet();
	    for(int i=0;i<db.length;i++)
	    {
	    	boolean duplicateFlag=false;
	    	for(int j=i+1;j<db.length;j++)
	    	{
	           if(db[i]==db[j])
	           {
	        	   duplicateFlag=true;
	        	   break;
	           }
	    	}
	    	
	         DecimalFormat r=new DecimalFormat();  
	         r.applyPattern("#"+rankStr);//����С��λ��������Ჹ�� 
	    	if(!duplicateFlag)
	    	{
	    		noDuplicateList.add(r.format(db[i]));
	    	}
	    	else
	    	{
	    		duplicateSet.add(r.format(db[i]));
	    	}
     	   
	    }
	    
       java.util.Collections.sort(noDuplicateList); //��list���п�������

	    return noDuplicateList;
	}
	
	public static String setPrecision(double value, String precision)
	{
		String str2 = new DecimalFormat(precision).format(Double.valueOf(String.valueOf(value)));
		return str2;
	}	

	/**
	 * 
	 * @param str
	 * @return  ������������2λС������ַ���������ĻᲹ�㱣֤���ص���"��λ"С��
	 */
	public static String RRetainTwoDecimal(String str)
	{
		try
		{
			return setPrecision(Arith.round(Double.parseDouble(str), 2),"0.00");
		}catch(Exception ex)
		{
			return str;
		}
	}
	
	public static String swichNull(String str)
	{
		if(str==null)
		{
			return "";
		}
		else
		{
			return str;
		}
	}
    /**
     * 
     * @param dateStr
     * @param Delimiters
     * @return  ��20090909ת��Ϊ2009-09-09,��200909ת��Ϊ2009-09
     */
    public static String TranseFullDate(String dateStr,String Delimiters)
    {
    	if(Delimiters==null)
    	{
    		Delimiters="-";
    	}
    	
    	if(dateStr.indexOf("-")!=-1)
    	{
    		return dateStr;
    	}
    	
    	if(dateStr.indexOf(Delimiters)!=-1)
    	{
    		return dateStr;
    	}
    	
    	if(dateStr.length()==8)
    	{
    		return dateStr.substring(0,4)+Delimiters+dateStr.substring(4,6)+Delimiters+dateStr.substring(6,8);
    	}
    	
    	if(dateStr.length()==6)
    	{
    		return dateStr.substring(0,4)+Delimiters+dateStr.substring(4,6);
    	}
    	
    	return dateStr;
    }
	
    /**
     * 
     * @param str
     * @return  У���ַ����Ƿ����������
     */
    public static boolean isNumeric(String str){ 
        Pattern pattern = Pattern.compile("[0-9]*"); 
        return pattern.matcher(str).matches();    
     }
    
	/**
	 * 
	 * @param Edoracceptno
	 * @return  ���ݱ�ȫ������ж����ŵ����Ǹ���
	 */
	public static String IsGrpOrSinglePolicy(String Edoracceptno)
	{
		if(Edoracceptno==null || Edoracceptno.equals(""))
		{
			return null;
		}
		ExeSQL tExeSQL = new ExeSQL();
		String sql="select count(*) from Lpgrpedoritem where edoracceptno='"+Edoracceptno+"'";
		String rel=tExeSQL.getOneValue(sql);
		if(rel==null || rel.equals(""))
		{
			return null;
		}
		else if(rel.equals("0"))
		{
		    sql="select count(*) from Lpedoritem where edoracceptno='"+Edoracceptno+"' and grpcontno='00000000000000000000' ";
		    rel=tExeSQL.getOneValue(sql);
		    if(rel==null || rel.equals(""))
		    {
		    	return null;
		    }
		    else if(rel.equals("0"))
		    {
		    	return null;
		    }
		    else if(!rel.equals("0"))
		    {
		    	return "I";
		    }
		}
		else if(!rel.equals("0"))
		{
			return "G";
		}
	   
		return null;
	}
	/**
	 * 
	 * @param Edoracceptno
	 * @return ������ʾ������漰������������Ա����Ϊ������Ա�������漰������������Ա ����Ϊ������Ա
	 */
	public static String GetRealOperator(String Edoracceptno)
	{
		if(Edoracceptno==null || Edoracceptno.equals(""))
		{
			return null;
		}
		
		ExeSQL tExeSQL = new ExeSQL();
		
		String flag=IsGrpOrSinglePolicy(Edoracceptno);
		if(flag==null)
		{
			return null;
		}
		
		String sql="";
		if(flag.equals("G"))
		{
//			//������ʾ������Ա���˱���ʾ�˱���Ա
//			sql=" (select a.Uwoperator from LPGrpEdorMain a,LPEdorApp b where a.Edoracceptno=b.Edoracceptno and b.Approveoperator is null and a.edoracceptno='"+Edoracceptno+"' and (length(a.Uwgrade)>2 or (a.Uwoperator <> a.Operator and a.Approveoperator is null))) "+
//			        " union" +
//			        " (select a.Approveoperator from LPEdorApp a where edoracceptno='"+Edoracceptno+"'  and a.Approveoperator is not null) "+
//			        " union" +
//			        " (select a.Operator from LPGrpEdorMain a,LPEdorApp b where a.Edoracceptno=b.Edoracceptno and b.Approveoperator is null  and a.edoracceptno='"+Edoracceptno+"'  and  a.Approveoperator is null and length(a.Uwgrade)<2 and a.Uwoperator = a.Operator ) "
//                    ;
			sql=" (select a.Approveoperator from LPEdorApp a where edoracceptno='"+Edoracceptno+"'  and a.Approveoperator is not null) "+
	        		" union " +
	        		" (select a.Operator from LPGrpEdorMain a,LPEdorApp b where a.Edoracceptno=b.Edoracceptno and b.Approveoperator is null  and a.edoracceptno='"+Edoracceptno+"'  and  a.Approveoperator is null) "
	        		;
		}
		else if(flag.equals("I"))
		{
//			//������ʾ������Ա���˱���ʾ�˱���Ա
//		    sql="(select a.Uwoperator from LPEdorApp a where edoracceptno='"+Edoracceptno+"' and (length(a.Uwgrade)>2 or (a.Uwoperator <> a.Operator and a.Approveoperator is null))) " +
//            " union "+
//            " (select a.Approveoperator from LPEdorApp a where edoracceptno='"+Edoracceptno+"'  and a.Approveoperator is not null) " +
//            " union "+
//            " (select a.Operator from LPEdorApp a where edoracceptno='"+Edoracceptno+"'  and  a.Approveoperator is null and length(a.Uwgrade)<2 and a.Uwoperator = a.Operator ) "
//            ;
		    sql=" (select a.Approveoperator from LPEdorApp a where edoracceptno='"+Edoracceptno+"'  and a.Approveoperator is not null) " +
	    			" union "+
	    			" (select a.Operator from LPEdorApp a where edoracceptno='"+Edoracceptno+"'  and  a.Approveoperator is null ) "
	    			;	
			
		}
		else
		{
			return null;
		}

        String rel=tExeSQL.getOneValue(sql);
        if(rel==null || rel.equals(""))
        {
        	return null;
        }
        else
        {
        	return rel;
        }
	}
	

	
	public static String Trimstr(String str)
    {
    	if(str==null) return null;
    	if(str.equals("")) return "";
    	return str.trim();
    }
	/**
	 * 
	 * @param tSchemaSet
	 * @param queryCode
	 * @param compareCode
	 * @return  ����set�м�¼��û��queryCode�ֶζ�Ӧ��ֵcompareCode
	 */
	public static boolean IsInSet(SchemaSet tSchemaSet,String queryCode,String compareCode)
	{
		for(int i=1;i<=tSchemaSet.size();i++)
		{
			String qCode=((Schema)tSchemaSet.getObj(i)).getV(queryCode);
			if(qCode!=null && !qCode.equals(""))
			{
				if(qCode.equals(compareCode))
				{
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 
	 * @param date1   yyyy-mm-dd ��ʽ
	 * @param date2   yyyy-mm-dd ��ʽ
	 * @return ʵ�ʹ�����
	 */
	public static int realWorkDay(String date1,String date2)
	{
		  DateFormat   df=new   SimpleDateFormat("yyyy-MM-dd");   
		  Date   d1=null,d2=null;   
		  try   {   
		  d1   =   df.parse(date1);   
		  d2=df.parse(date2);   
		  }   catch   (ParseException   e)   {   
		  //   TODO   Auto-generated   catch   block   
		  e.printStackTrace();   
		  }   
		  return workDays(d1,d2);
	}
	  
	  private   static   int   workDays(Date   d1,Date   d2)   
	  {   
	  Date   d=d1.before(d2)?d1:d2;   
	  Calendar   cal=Calendar.getInstance();   
	  cal.setTime(d);   
	  int   count=0;   
	  while(!d.after(d2))   
	  {   
	  int   day=cal.get(Calendar.DAY_OF_WEEK);   
	  // System.out.println(d.toString()+"   "+day);   
	  if(day<7&&day>1)count++;//1��ʾ���գ�7��ʾ���� 
	  cal.add(Calendar.DATE,1);   
	  d=cal.getTime();   
	  }   
	  return   count;   
	  }   
	
    /**
     * 
     * ��ʽ������
     * ��������
     *     	System.out.println(formatDate(new Date(),"yyyy �� MM �� dd ��"));
     *	    System.out.println(formatTime(new Date(),"yyyy��MM��dd�� HHʱmm��ss��"));
     */
        public static String formatDate(Date date,String formatString){
            SimpleDateFormat sdf = new SimpleDateFormat(formatString);
            return sdf.format(date);
        }
        
        public static String formatTime(Date date,String formatString){
            SimpleDateFormat sdf = new SimpleDateFormat(formatString);
            return sdf.format(date);
        }
	
	public static String getAreaType(String Managercom)
	{
		if(Managercom==null || Managercom.equals(""))
		{
			return null;
		}
		ExeSQL xExeSQL=new ExeSQL();
		//��ȡ��������
		String sql = " Select trim(code2) from  LDCodeRela Where RelaType ='comtoareatypebank' and trim(code1)='"+Managercom+"' and othersign='3' ";
		String tAreaType=xExeSQL.getOneValue(sql);
		if(tAreaType==null || tAreaType.equals(""))
		{
			return null;
		}
	
		return tAreaType;
	}
	
	/**
	 * 
	 * @param content
	 * @return  У���Ƿ�Ϊ��
	 */
	public static boolean verifyNull(String content)
   {
	   if(content==null||"".equals(content.trim()))
	   {
		   return false;
	   }
	   return true;
   }
	
	   /**
     * ���������Ƿ�Ϊ��ȷ�����ڸ�ʽ(��������κ����),�ϸ�Ҫ��������ȷ��,��ʽ:yyyy-MM-dd HH:mm,��ȻҲ�����ñ�ģ����磺yyyyMM
     * @param sourceDate
     * @return
     */
    public static boolean checkDate(String sourceDate,String format,boolean IsStrict){
    	System.out.println("sourceDate:"+sourceDate);
        if(sourceDate==null){
            return false;
        }
        try {
               SimpleDateFormat dateFormat = new SimpleDateFormat(format);
               dateFormat.setLenient(false);
               Date   dd   =   dateFormat.parse(sourceDate);
               String   ddp   =   dateFormat.format(dd);
               if(ddp.equals(sourceDate))
               {
            	   System.out.println("equals:"+ddp+" : "+sourceDate);
                   if(IsStrict)//������ر��ϸ��У�飬��ô20099Ҳ������yyyyMM�Ĺ���
                   {
                	   if(sourceDate.length()!=format.length())
                	   {
                		   System.out.println("equals but Strict worng:"+sourceDate.length()+" : "+format.length());
                		   return false;
                	   }
                   }
            	   return true;   
               }
               else
               {
            	   System.out.println("Not equals:"+ddp+" : "+sourceDate);
            	   return false;
               }
        } catch (Exception e) {
        	System.out.println("Exception sourceDate:"+sourceDate+" : "+e.getMessage());
            return false;
        }
    }
//    public static boolean checkDate(String sourceDate,String format,boolean IsStrict){
//        if(sourceDate==null){
//            return false;
//        }
//        try {
//               SimpleDateFormat dateFormat = new SimpleDateFormat(format);
//               dateFormat.setLenient(false);//���2006-02-31������û����һ��   ��Ҳ����Ϊʱ���ʽ����
//               dateFormat.parse(sourceDate);
//               
//               if(IsStrict)//������ر��ϸ��У�飬��ô20099Ҳ������yyyyMM�Ĺ���
//               {
//            	   if(sourceDate.length()!=format.length())
//            	   {
//            		   return false;
//            	   }
//               }
//               return true;
//        } catch (Exception e) {
//        	//System.out.print(e);
//        }
//         return false;
//    }
	
	/**
	 * 
	 * @param s
	 * @return �Ƿ��пո�
	 */
	public static boolean HasSpace(String s){
    	int i = s.indexOf(" ");
    	if(i!=-1)return true;
    	return false;
    	}
	
	/**
	 * 
	 * @param s
	 * @param Character
	 * @return  �Ƿ���ĳ�������ַ�
	 */
	public static boolean HasSomeCharacter(String s,String Character){
    	int i = s.indexOf(Character);
    	if(i!=-1)return true;
    	return false;
    	}
	
	/**
	 * 
	 * @param s
	 * @return /�������ķ���true,���򷵻�false
	 */
	public static boolean isChinese(String s)   
	{   
//	    String pattern="[u4e00-u9fa5]+";   
//	    Pattern p=Pattern.compile(pattern);   
//	    Matcher result=p.matcher(s);                   
//	    return result.find();
        String anotherString = null;
        try {
        anotherString = new String(s.getBytes("GBK"), "ISO8859_1");
        }
        catch (java.io.UnsupportedEncodingException ex) {
        }
        //System.out.println(aString.length() + "," + anotherString.length());
        return s.length()!=anotherString.length(); //��������ֵ�������˵���ǽ��������ַ�
	} 
	/**
	 * 
	 * @param number
	 * @return  ȥ������λǰ����� ���磺 000000000000000011.500000000 ��ת��Ϊ11.500000000
	 */
	public static String FormatNumber(String number)
	{
		String pre="";
		String hrx="";
	    int pos=number.indexOf(".");
	    int numberPos=0;
	    for(int i=0;i<pos;i++)
	    {
	    	if(number.charAt(i)!='0')
	    	{
	    		pre+=number.charAt(i);
	    		numberPos=i;
	    		break;
	    	}
	    }
	    
	    if(pre.equals(""))
	    {
	    	pre="0";
	    }
	    
	    if(pre.equals("0"))
	    {
	    	return pre+number.substring(pos,number.length());
	    }
	    
	    return pre+number.substring(numberPos+1,number.length());
	}
	
	public static String getComName(String comCode,int type)
	{
		if(comCode==null || comCode.equals(""))
		{
			return null;
		}
		
		LDComDB tLDComDB=new LDComDB();
		tLDComDB.setComCode(comCode);
		if(type==1)
		{
			return tLDComDB.query().get(1).getShortName();
		}
		else
		{
			return tLDComDB.query().get(1).getName();
		}
	}
	/**
	 * ������������֮����������,��������String����ʽΪ��yyyy-MM-dd�������������������������������������ͬһ���£��򷵻�1
	 * ע�⣺�����봦���쳣
	 */ 
	public static int getDiffer(String begin, String end) throws Exception
	{

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = df.parse(begin);
		Date endDate = df.parse(end);
	
		int beginYear = beginDate.getYear();
		int beginMonth = beginDate.getMonth();
	
		int endYear = endDate.getYear();
		int endMonth = endDate.getMonth();
	
		int difMonth = (endYear-beginYear)*12+(endMonth-beginMonth)+1;
	
		return difMonth; 

	}
	
	
	/**
	 *  ��yyyy-mm-ddת��Ϊyyyy��mm��dd��
	 */
	public static String SwitchTwinsDate(String date)
	{
		if(date==null || date.equals(""))
		{
			return null;
		}
		
		String Year=null;
		String Month=null;
		String Day=null;
		try
		{
			String dates[]=date.split("-");
			if(dates.length<2)
			{
				return null;
			}
			
			 Year=dates[0];
			 Month=dates[1];
			 Day=dates[2];
		}catch(Exception ex)
		{
			return null;
		}

		return Year+"��"+Month+"��"+Day+"��";
		
	}
	
	//����Ϊ����д�ķ��������Բ���̫��
	public static String getCHNNum(String value)
	{
		if(value.length()==1)
		{
			return getNum(value,false);
		}
		
		if(value.length()==2)
		{
			return getNum(value.substring(0,1),true)+"ʮ"+getNum(value.substring(1,2),true);
		}
		
		if(value.length()==3)
		{
			return getNum(value.substring(0,1),true)+"��"+getNum(value.substring(1,2),true)+"ʮ"+getNum(value.substring(2,3),true);
		}
		return value;
	}
	
    /**
     * ��ȡ���������ֺ��������ֵĶ�Ӧ��ϵ
     * @param value String
     * @return String
     */
    public static String getNum(String value,boolean flag)
    {
        String sNum = "";
        Integer I = new Integer(value);
        int iValue = I.intValue();
        switch (iValue)
        {
            case 0:
            	if(flag)
            	{
            		sNum = "";
            	}
            	else
            	{
                    sNum = "��";
            	}
                break;
            case 1:
                sNum = "һ";
                break;
            case 2:
                sNum = "��";
                break;
            case 3:
                sNum = "��";
                break;
            case 4:
                sNum = "��";
                break;
            case 5:
                sNum = "��";
                break;
            case 6:
                sNum = "��";
                break;
            case 7:
                sNum = "��";
                break;
            case 8:
                sNum = "��";
                break;
            case 9:
                sNum = "��";
                break;
        }
        return sNum;
    }

	  
	/**
	 * 
	 * @param str
	 * @return   �жϴ����ַ����Ƿ�Ϊ����
	 */
	public static boolean HasLM(String str)
	{
	    for (int i = 0; i < str.length(); i++)
	    {
	      char c = str.charAt(i);
	      if (c>=190 && c<=210)
	      {
	    	  return true;
	      }
	      if ((int)c == 0xfffd)
	      {
	        return true;
	      }
	    }
	    
	    return false;
		}
	/**
	 * 
	 * @param str
	 * @return  ���Ϊ�գ����� "---"
	 */
	public static String ChangeNull(String str)
	{
		try
		{
		    if(str==null || str.equals(""))
		    {
		    	return "---";
		    }
		}catch(Exception ex)
		{
			return "---";
		}
	    return str;
	}

	/**
	 * 
	 * @param date1  �Ƚ�����
	 * @param date2  ���Ƚ�����
	 * @return ���� 1 С�� -1 ���� 0
	 */
	public static int comparedate(String date1,String date2)
	{
		  if(date1==null || date1.equals(""))
		  {
			  return -99;
		  }
		  
		  if(date2==null || date2.equals(""))
		  {
			  return -99;
		  }
		  
		  DateFormat   df   =   new   SimpleDateFormat("yyyy-MM-dd"); 
		  try {
             //���� -1 С�� 1 ���� 0
			  return (df.parse(date1)).compareTo(df.parse(date2));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -99;
		}
	}
	
	public static String getUserName(String usercode)
	{
		if(usercode==null || usercode.equals(""))
		{
			return "--";
		}
		LDUserDB tLDUserDB = new LDUserDB();
		tLDUserDB.setUserCode(usercode);
		LDUserSchema tLDUserSchema=tLDUserDB.query().get(1);
		if(tLDUserSchema.getUserName()==null || tLDUserSchema.getUserName().equals(""))
		{
			return usercode;
		}
		return tLDUserSchema.getUserName();
	}
	
	public static String getOneMainpolno(String contno)
	{
		if(contno==null || contno.equals(""))
		{
			return null;
		}
		try
		{
			LCPolDB tLCPolDB = new LCPolDB();
			tLCPolDB.setContNo(contno);
			LCPolSchema tLCPolSchema = tLCPolDB.query().get(1);
			return tLCPolSchema.getMainPolNo();
		}catch(Exception ex)
		{
			return null;
		}
	}
	
    public static String getCodeName(String strCode,String strCodeType) {
        LDCodeDB tLDCodeDB = new LDCodeDB();
        tLDCodeDB.setCode(strCode);
        tLDCodeDB.setCodeType(strCodeType);
        LDCodeSchema tLDCodeSchema=new LDCodeSchema();
        tLDCodeDB.getInfo();
        tLDCodeSchema.setSchema(tLDCodeDB.getSchema());
        if(tLDCodeSchema==null)
        {
        	return null;
        }
        return tLDCodeSchema.getCodeName();
    }
    

    

    
    public static String getRiskCodeByPolno(String Polno)
    {
    	String sql="select distinct riskcode from lcpol where polno='"+Polno+"'";
    	ExeSQL tExeSQL = new ExeSQL();
    	String riskCode = tExeSQL.getOneValue(sql);

    	return riskCode;
    }
    
    /**
     *  general:�жϵ�lcpol��renewflagΪ-2��ʱ���Ǳ�ȫ���»�����Լ���£�����ȫ��Լ����-2ʱ���ж��Ǳ�ȫ����
     *  param:LCPolSchema
     *  return: "BQ",��ȫ����
     *          "QY",��Լ����
     *          "Renewflag-1",ԭ��״̬Ϊ"-1"(����������)
     *          "null",��������
     *  author:������
     *  date:081129
     */
    public static String IfQyOrBqRenewflag(LCPolSchema sLCPolSchema)
    {
    	String contno=sLCPolSchema.getContNo();
    	String polno=sLCPolSchema.getPolNo();
    	if(contno==null || contno.equals(""))
    	{
    		return "null";
    	}
    	if(polno==null || polno.equals(""))
    	{
    		return "null";
    	}
    	
    	//�Ƿ�ԭ����Ϊ"-1"
		ExeSQL tExeSQL=new ExeSQL();
		String sql="select RnewFlag from lcpol where contno='"+contno+"' and polno='"+polno+"'";
		String rel=tExeSQL.getOneValue(sql);
		if(rel==null || rel.equals(""))
		{
			return "null";
		}
		if(rel.equals("-1"))
		{
			return "Renewflag-1";
		}
		if(!rel.equals("-2"))//���������"-1"Ҳ����"-2"��ô�����ڸ÷����жϵķ�Χ��ֱ�ӷ���"null"
		{
			return "null";
		}
		
		//�Ƿ�������ȫ���Լ�������ȫ�Ĵ���
		sql="select count(*) from Lpedoritem where edortype='RC' and edorstate='0' and contno='"+contno+"' and polno='"+polno+"'";
		rel=tExeSQL.getOneValue(sql);
		if(rel!=null && Integer.parseInt(rel)>0)
		{
			int relInt=Integer.parseInt(rel);
			if(relInt%2!=0)
			{
				return "BQ";
			}
			else if(relInt%2==0)
			{
				return "QY";
			}
		}
		else if(rel!=null && Integer.parseInt(rel)==0)
		{
			return "QY";
		}
		
    	return "null";
    }
    public static String getCurrentDate3() {
        String pattern = "yyMMdd";
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date today = new Date();
        String tString = df.format(today);
        return tString;
    }

    
    public static ExportExcel.Format CreateExcelForOneRow(String[] columns)
	   {
		 ExportExcel.Format format = new ExportExcel.Format();
		 ArrayList listCell = new ArrayList();
		 ArrayList listLB = new ArrayList();
		 ArrayList listColWidth = new ArrayList();
		 format.mListCell=listCell;
		 format.mListBL=listLB;
		 format.mListColWidth=listColWidth;
		 ExportExcel.Cell tCell=null;
		 ExportExcel.ListBlock tLB=null;
		 listColWidth.add(new String[]{"0","5000"});  
		 tLB = new ExportExcel.ListBlock("001");
		 tLB.colName = new String[columns.length];
		 for(int i=0;i<columns.length;i++){
		 	tLB.colName[i]=columns[i];
		 	System.out.println("colName="+tLB.colName[i]);
		 }
		 tLB.row1 = 0;
		 tLB.col1 = 0;
		 tLB.InitData();
		 listLB.add(tLB);
		 return format;
	   }
	

   private static boolean VerifyIsNull(String str)
   {
	   if(str==null || str.equals(""))
	   {
		   return false;
	   }
	   return true;
   }
		
   /**
    * path:��׺����zip
    * file:Ҫѹ�����ļ�����
    * \(^o^)/~
    */
   public static boolean zip(String[] file,String path)
   {
     try{
     	  FileOutputStream f=new FileOutputStream(path);
     	  ZipOutputStream out=null;
		  CheckedOutputStream ch=new CheckedOutputStream(f,new CRC32());
		  out=new ZipOutputStream(new BufferedOutputStream(ch));
     	  for(int i=0;i<file.length;i++)
     	  {
	           BufferedReader in=new BufferedReader(
	           new InputStreamReader(new FileInputStream(file[i]),"ISO8859_1"));
			      int c;
			      
			      File tfile=new File(file[i]);//��������������·��Ҳѹ����ȥ��
			      String filename=tfile.getName();
			      
			      
			      out.putNextEntry(new ZipEntry(filename));
			      while((c=in.read())!=-1)
			         out.write(c);
			         out.closeEntry();
			         in.close();
			         
     	  }
     	  out.close();
        }
       catch(Exception e){
           e.printStackTrace();
           return false;
       }
       return true;
   }
   

   /** 
    *  ����һ���ַ�����ĳһ�ַ����ֵĴ���
    *  str Ҫ���ҵ��ַ�
    */
   public static int count(String s,String str) 
   { 
	   int count=0; 
	   int m=s.indexOf(str); 
	
	   while(m!=-1){ 
	   m=s.indexOf(str,m+1); 
	   count++; 
	   } 
	
	   return count; 
   } 
  
   /**
    * 
    * @param riskcode
    * @return  true  ����
    *                   false �ǳ���
    */
   public static boolean IsLongrisk(String riskcode)
   {
	  if(riskcode==null || riskcode.equals(""))
	  {
		  return false;
	  }
	  ExeSQL tExeSQL = new ExeSQL();
	  String sql="select count(*) from Lmriskapp where riskcode='"+riskcode+"' and Riskperiod='L'";
	  String rel=tExeSQL.getOneValue(sql);
	  if(rel!=null)
	  {
		  if(!rel.equals("0"))
		  {
			  return true;
		  }
	  }
	  return false;
   }
   
   /**
    * 
    * @param riskcode
    * @return true          �������������ӱ�����
    */
   public static boolean IsAscriptRisk(String riskcode)
   {
	  if(riskcode==null || riskcode.equals(""))
	  {
		  return false;
	  }
	  ExeSQL tExeSQL = new ExeSQL();
	  String sql="select count(*) from lmfactorymode where riskcode ='"+riskcode+"'  and Factorytype='000006'";
	  String rel=tExeSQL.getOneValue(sql);
	  if(rel!=null)
	  {
		  if(!rel.equals("0"))
		  {
			  return true;
		  }
	  }
	  return false;
   }
   
   public static boolean IsBounusRisk(String riskcode)
   {
	  if(riskcode==null || riskcode.equals(""))
	  {
		  return false;
	  }
	  ExeSQL tExeSQL = new ExeSQL();
	  String sql="select count(*) from Lmriskapp where riskcode='"+riskcode+"' and risktype3='2'";
	  String rel=tExeSQL.getOneValue(sql);
	  if(rel!=null)
	  {
		  if(!rel.equals("0"))
		  {
			  return true;
		  }
	  }
	  return false;
   }
   
   /**
    * 
    * @param type
    * @param detailtype
    * @return  ��ñ�ȫ�����˹��˱���������죬��������˱�֪ͨ��Ļظ��������ڡ�
    */
   public static String getKXdays(String type,String detailtype)
   {
	   if(type==null || type.equals("") || detailtype==null || detailtype.equals(""))
	   {
		   return null;
	   }
	   ExeSQL tExeSQL = new ExeSQL();
	   String sql="select codename from ldcode where codetype='"+type+"' and code='"+detailtype+"'";
	   String KXdays=tExeSQL.getOneValue(sql);
	   if(KXdays==null || KXdays.equals(""))
	   {
		   return null;
	   }
	   return KXdays;
   }
   
   /**
    * 
    * @param riskcode
    * @return true �������幫���˻����е�λ���˻�
    */
   public static boolean IsHavePulicAccAndCropAcc(String riskcode)
   {
	   ExeSQL tExeSQL = new ExeSQL();
	   //�����˻�
	   String sql="select count(*) from Lmrisktoacc a ,Lmriskinsuacc b where riskcode='"+riskcode+"' and a.Insuaccno=b.Insuaccno and b.Acctype='001' and owner='0'";
	   String PulicAccCount=tExeSQL.getOneValue(sql);
	   sql="select count(*) from Lmrisktoacc a ,Lmriskinsuacc b where riskcode='"+riskcode+"' and a.Insuaccno=b.Insuaccno and b.Acctype='003' and owner='0'";
	   String CropAccCount=tExeSQL.getOneValue(sql);
	   if(PulicAccCount!=null && CropAccCount!=null)
	   {
		   if(!PulicAccCount.equals("0") && !CropAccCount.equals("0"))
		   {
			   return true;
		   }
	   }
	   return false;
   }
   
   /**
    * 
    * @param riskcode
    * @return true �и��˺����˻�����λ���ѣ�
    */
   public static boolean IsHaveHLAcc(String riskcode)
   {
	   ExeSQL tExeSQL = new ExeSQL();
	   //�����˻�
	   String sql="select * from Lmrisktoacc a ,Lmriskinsuacc b where riskcode='"+riskcode+"' and a.Insuaccno=b.Insuaccno and b.Acctype='004' and owner='0'";
	   String HLAccCount=tExeSQL.getOneValue(sql);
	   if(HLAccCount!=null)
	   {
		   if(!HLAccCount.equals("0"))
		   {
			   return true;
		   }
	   }
	return false;
   }
   

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        //System.out.println(getCodeName("0","idtype"));
		LCPolSchema tLCPolSchema=new LCPolSchema();
		tLCPolSchema.setContNo("1001012008110602");
		tLCPolSchema.setPolNo("110210000013476");
		System.out.println(IfQyOrBqRenewflag(tLCPolSchema));
	}

}