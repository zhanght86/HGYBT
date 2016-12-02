<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/"> 
<rules>
	<xsl:apply-templates select="TranData/Head"/>
	<xsl:apply-templates select="TranData/Body"/> 
</rules>
</xsl:template>

<xsl:template match="TranData/Head">
	<xsl:if test="string-length(FuncFlag)=0">
		<msg>���״��벻��Ϊ��</msg>
	</xsl:if>	
</xsl:template>
  
<xsl:template match="TranData/Body"> 

<xsl:if test="BankCode!=10">

<xsl:variable name="sContPrtNo" select="java:com.sinosoft.midplat.common.YBTDataVerification.PrtNoVerification(ContPrtNo)" />
	<xsl:if test="string-length(ContPrtNo) = 0 ">
		<msg>��֤�Ų���Ϊ��</msg>
	</xsl:if>
		<xsl:if test="$sContPrtNo = 1 and (string-length(ContPrtNo) != 0)"> 
		<msg>��֤��¼���������ͨ��֤��Ϊ8λ�����֣�������ĸ</msg>
	</xsl:if>    
</xsl:if>
	<xsl:if test="string-length(AccNo)=0">
		<msg>�ɷ��˻�����Ϊ��</msg>
	</xsl:if> 
	<xsl:if test="HealthNotice = 'Y'">
		<msg>�н�����֪���Ժ˲�ͨ��</msg>
	</xsl:if>
	<xsl:if test="JobNotice = 'Y'"> 
		<msg>��ְҵ��֪���Ժ˲�ͨ��</msg>
	</xsl:if>
	<xsl:if test="string-length(PolApplyDate)=0">
		<msg>Ͷ�����ڲ���Ϊ��</msg> 
	</xsl:if>  
	<xsl:if test="PolApplyDate != (java:com.sinosoft.midplat.common.DateUtil.getCur8Date())">
		<msg>Ͷ�����ڱ���Ϊ����</msg> 
	</xsl:if> 
	<xsl:if test="AccName !=(Appnt/Name)">
		<msg>�ʻ�����������Ͷ��������һ��</msg> 
	</xsl:if> 
	<xsl:apply-templates select="Appnt"/> 
	<xsl:apply-templates select="Insured"/>
	<xsl:apply-templates select="Bnf"/>
	<xsl:apply-templates select="Risk"/>
</xsl:template>

<!-- Ͷ���� -->
<xsl:template match="Appnt">
<xsl:variable name="ZipCode" select="java:com.sinosoft.midplat.common.YBTDataVerification.ZipCodeVerification(ZipCode)" />
	
	<!-- Ͷ���������ǿ�У�� -->
	<xsl:if test="Name= ''">   
		<msg>Ͷ������������Ϊ��</msg>  
	</xsl:if>	
	
	<!-- Ͷ������������У�� -->
	<xsl:if test="string-length(Name) > 20"> 
		<msg>Ͷ�����������Ȳ��ܳ���20λ</msg>
	</xsl:if> 
	 
	<!--Ͷ�����ձ�ǿ�У��-->
	<xsl:if test="Sex = '--' or Sex = ''"> 
		<msg>Ͷ�����Ա���Ϊ��</msg>
	</xsl:if> 
	
	<!-- Ͷ���˳������ڷǿ�У�� -->
	<xsl:if test="string-length(Birthday)= 0">
		<msg>Ͷ���˳������ڲ���Ϊ��</msg>
	</xsl:if> 
	  
	<!-- Ͷ����֤�����ͷǿ�У�� -->
	<xsl:if test="IDType = '--' or IDType=''">   
		<msg>Ͷ����֤�����Ͳ���Ϊ��</msg>
	</xsl:if> 
	  
	<!-- Ͷ����֤������ǿ�У�� --> 
	<xsl:if test="string-length(IDNo)= 0">   
		<msg>Ͷ����֤�����벻��Ϊ��</msg>
	</xsl:if>   
	
		<!-- Ͷ������Ч�ڷǿ�У�� -->
	<xsl:if test="IDExpDate= ''">   
		<msg>Ͷ����֤����Ч���ڲ���Ϊ��</msg>  
	</xsl:if>
	
	<!-- �ڽ�ʱ��Ͷ����������ǿ�У�� -->      
	<xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/PayIntv!='0'">
	<xsl:if test="Salary = ''"> 
		<msg>Ͷ���������벻��Ϊ��</msg>
	</xsl:if>  
	 </xsl:if>
	<!-- Ͷ���˹����ǿ�У�� -->
	<xsl:if test="Nationality= ''">   
		<msg>Ͷ���˹�������Ϊ��</msg>  
	</xsl:if>
	  <xsl:if test="Nationality!='CHN'">
		<msg>Ͷ���˹����������й�</msg> 
	</xsl:if> 
	<!-- Ͷ�������֤��ֻ��Ϊ15λ����18λУ�� -->
	<xsl:if test="IDType = '0' and not((string-length(IDNo)= 15) or (string-length(IDNo) = 18)) and (string-length(IDNo)!= 0)">
		<msg>Ͷ�������֤��ֻ��Ϊ15λ����18λ</msg>
	</xsl:if>    
	 
	<!-- Ͷ����15λ���֤����Ͷ�����Ա�ƥ�� -->
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 15)) and ((substring(IDNo,15,1) mod 2) = Sex)">
		<msg>Ͷ����15λ���֤����Ͷ�����Ա�ƥ��</msg>
	</xsl:if>  
	
	<!-- Ͷ����18λ���֤����Ͷ�����Ա�ƥ�� -->
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 18)) and ((substring(IDNo,17,1) mod 2) = Sex)">
		<msg>Ͷ����18λ���֤����Ͷ�����Ա�ƥ��</msg>
	</xsl:if>     
	       
	<!-- Ͷ����15λ���֤����Ͷ���˳������ڲ�ƥ�� -->       
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 15)) and (substring(IDNo,7,6) != substring(Birthday,3,6)) and (string-length(Birthday)!= 0)">
		<msg>Ͷ����15λ���֤����Ͷ���˳������ڲ�ƥ��</msg>
	</xsl:if>   
	  
	<!-- Ͷ����18λ���֤����Ͷ���˳������ڲ�ƥ�� -->
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 18)) and (substring(IDNo,7,8) != Birthday and (string-length(Birthday)!= 0))">
		<msg>Ͷ����18λ���֤����Ͷ���˳������ڲ�ƥ��</msg>
	</xsl:if>   
	
	<!-- Ͷ�����ʼĵ�ַ����Ϊ��У�� -->
	<xsl:if test="string-length(Address) = 0 "> 
		<msg>Ͷ�����ʼĵ�ַ����Ϊ��</msg>
	</xsl:if>        
	 
	 <!-- Ͷ�����ʱ಻��Ϊ��У�� -->
	<xsl:if test="string-length(ZipCode) = 0 ">
		<msg>Ͷ�����ʱ಻��Ϊ��</msg>
	</xsl:if>
	 
	<!-- Ͷ�������������ʽУ�� -->               
	<xsl:if test="$ZipCode = 0 and (string-length(ZipCode) != 0)"> 
		<msg>Ͷ�����ʱ����Ϊ6λ����</msg>
	</xsl:if>    
	  	<!-- Ͷ���˵绰�ǿ�У�� -->
	<xsl:if test="(Mobile= '')and (Phone= '')">   
		<msg>Ͷ�����ֻ�����͵绰���벻��ͬʱΪ��</msg>  
	</xsl:if>
	  
	 <!-- Ͷ�����뱻���˵Ĺ�ϵУУ�� -->
	<xsl:if test = "RelaToInsured = '--' or RelaToInsured=''"> 
		<msg>Ͷ�����뱻���˵Ĺ�ϵ����Ϊ��</msg>
	</xsl:if>    
	  
</xsl:template>              
     
<!-- ������ -->  
<xsl:template match="Insured"> 
	<xsl:variable name="ZipCode" select="java:com.sinosoft.midplat.common.YBTDataVerification.ZipCodeVerification(ZipCode)" />
	
	<!-- �����������ǿ�У�� -->
	<xsl:if test="Name= ''">
		<msg>��������������Ϊ��</msg>  
	</xsl:if>	
	
	<!-- ��������������У�� -->
	<xsl:if test="string-length(Name) > 20"> 
		<msg>�������������Ȳ��ܳ���20λ</msg>
	</xsl:if> 
	 
	<!--�������ձ�ǿ�У��-->
	<xsl:if test="Sex = '--' or Sex = '' "> 
		<msg>�������Ա���Ϊ��</msg>
	</xsl:if> 
	
	<!-- �����˳������ڷǿ�У�� -->
	<xsl:if test="string-length(Birthday)= 0">
		<msg>�����˳������ڲ���Ϊ��</msg>
	</xsl:if> 
	  
	<!-- ������֤�����ͷǿ�У�� -->
	<xsl:if test="IDType = '--' or IDType =''">   
		<msg>������֤�����Ͳ���Ϊ��</msg>
	</xsl:if> 
	  
	<!-- ������֤������ǿ�У�� --> 
	<xsl:if test="string-length(IDNo)= 0">   
		<msg>������֤�����벻��Ϊ��</msg>
	</xsl:if>   
	
	<!-- ���������֤��ֻ��Ϊ15λ����18λУ�� -->
	<xsl:if test="IDType = '0' and not((string-length(IDNo)= 15) or (string-length(IDNo) = 18)) and (string-length(IDNo)!= 0)">
		<msg>���������֤��ֻ��Ϊ15λ����18λ</msg>
	</xsl:if>    
	 
	<!-- ������15λ���֤���뱻�����Ա�ƥ�� -->
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 15)) and ((substring(IDNo,15,1) mod 2) = Sex)">
		<msg>������15λ���֤���뱻�����Ա�ƥ��</msg>
	</xsl:if>  
	
	<!-- ������18λ���֤���뱻�����Ա�ƥ�� -->
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 18)) and ((substring(IDNo,17,1) mod 2) = Sex)">
		<msg>������18λ���֤���뱻�����Ա�ƥ��</msg>
	</xsl:if>     
	       
	<!-- ������15λ���֤���뱻���˳������ڲ�ƥ�� -->       
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 15)) and (substring(IDNo,7,6) != substring(Birthday,3,6)) and (string-length(Birthday)!= 0)">
		<msg>������15λ���֤���뱻���˳������ڲ�ƥ��</msg>
	</xsl:if>   
	  
	<!-- ������18λ���֤���뱻���˳������ڲ�ƥ�� -->
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 18)) and (substring(IDNo,7,8) != Birthday and (string-length(Birthday)!= 0))">
		<msg>������18λ���֤���뱻���˳������ڲ�ƥ��</msg>
	</xsl:if>   
	
	<!-- �������ʼĵ�ַ����Ϊ��У�� -->
	<xsl:if test="string-length(Address) = 0 "> 
		<msg>�������ʼĵ�ַ����Ϊ��</msg>
	</xsl:if>        

	<!--��������Ч�ڷǿ�У�� -->
	<xsl:if test="IDExpDate= ''">   
		<msg>������֤����Ч���ڲ���Ϊ��</msg>  
	</xsl:if>

	<!-- �����˹����ǿ�У�� -->
	<xsl:if test="Nationality= ''">   
		<msg>�����˹�������Ϊ��</msg>  
	</xsl:if>
	  <xsl:if test="Nationality!='CHN'">
	<msg>�����˹����������й�</msg> 
	</xsl:if> 
	 <!-- �������ʱ಻��Ϊ��У�� -->
	<xsl:if test="string-length(ZipCode) = 0 ">
		<msg>�������ʱ಻��Ϊ��</msg>
	</xsl:if>
	 
	<!-- ���������������ʽУ��,����$ZipCode = 0 ʱ����У����� -->               
	<xsl:if test="$ZipCode = 0 and (string-length(ZipCode) != 0)"> 
		<msg>���������������ʽ����ȷ</msg>
	</xsl:if>  
</xsl:template>

<!-- ������ -->
<xsl:template match="Bnf">  
	<xsl:if test="BeneficType = 'N'"> 
	
	<!-- �����������ǿ�У�� -->
	<xsl:if test="Name= ''">
		<msg>��������������Ϊ��</msg>  
	</xsl:if>	
	
	<!-- ��������������У�� -->
	<xsl:if test="string-length(Name) > 20"> 
		<msg>�������������Ȳ��ܳ���20λ</msg>
	</xsl:if> 
	 
	<!--�������ձ�ǿ�У��-->
	<xsl:if test="Sex = '--' or Sex=''"> 
		<msg>�������ձ���Ϊ��</msg>
	</xsl:if> 
	 
	<!-- �����˳������ڷǿ�У�� -->
	<xsl:if test="string-length(Birthday) = 0">
		<msg>�����˳������ڲ���Ϊ��</msg>
	</xsl:if>   
	  
	<!-- ������֤�����ͷǿ�У�� -->
	<xsl:if test="IDType = '--' or IDType = ''">    
		<msg>������֤�����Ͳ���Ϊ��</msg>
	</xsl:if>  
	  
	<!-- ������֤������ǿ�У�� --> 
	<xsl:if test="string-length(IDNo)= 0">   
		<msg>������֤�����벻��Ϊ��</msg> 
	</xsl:if>    
	
	
	<!-- ���������֤��ֻ��Ϊ15λ����18λУ�� -->
	<xsl:if test="IDType = '0' and not((string-length(IDNo)= 15) or (string-length(IDNo) = 18)) and (string-length(IDNo)!= 0)">
		<msg>���������֤��ֻ��Ϊ15λ����18λ</msg>
	</xsl:if>    
	 
	<!-- ������15λ���֤�����������Ա�ƥ�� -->
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 15)) and ((substring(IDNo,15,1) mod 2) = Sex)">
		<msg>������15λ���֤�����������Ա�ƥ��</msg>
	</xsl:if>  
	
	<!-- ������18λ���֤�����������Ա�ƥ�� -->
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 18)) and ((substring(IDNo,17,1) mod 2) = Sex)">
		<msg>������18λ���֤�����������Ա�ƥ��</msg>
	</xsl:if>     
	       
	<!-- ������15λ���֤���������˳������ڲ�ƥ�� -->       
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 15)) and (substring(IDNo,7,6) != substring(Birthday,3,6)) and (string-length(Birthday)!= 0)">
		<msg>������15λ���֤���������˳������ڲ�ƥ��</msg>
	</xsl:if>   
	  
	<!-- ������18λ���֤���������˳������ڲ�ƥ�� -->
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 18)) and (substring(IDNo,7,8) != Birthday and (string-length(Birthday)!= 0))">
		<msg>������18λ���֤���������˳������ڲ�ƥ��</msg>
	</xsl:if>   
		
	 <!-- �������뱻���˵Ĺ�ϵУУ�� -->
	<xsl:if test = "RelaToInsured = '--' or RelaToInsured = ''"> 
		<msg>�������뱻���˵Ĺ�ϵ����Ϊ��</msg>
	</xsl:if>  
		 
	<xsl:if test="Grade = ''">
		<msg>����������˳����Ϊ��</msg>  
	</xsl:if>
		 
	<xsl:if test="Lot = ''">
		<msg>����������ݶ��Ϊ��</msg>  
	</xsl:if>
		
	</xsl:if>  
</xsl:template>  


<!-- ���� --> 
<xsl:template match="Risk">
<xsl:if test="RiskCode=MainRiskCode">
	<xsl:if test="RiskCode='--' or RiskCode = ''" >
		<msg>�����ִ��벻��Ϊ��</msg>
	</xsl:if>
	<xsl:if test="Prem=''">
		<msg>���ձ��Ѳ���Ϊ��</msg>
	</xsl:if>
	<xsl:if test="InsuYear =''">
		<msg>���ձ����ڼ䲻��Ϊ��</msg>
	</xsl:if>  
 
	<xsl:if test="InsuYearFlag ='--' or InsuYearFlag =''">
		<msg>���ձ����ڼ��־����Ϊ��</msg>
	</xsl:if>
		<xsl:if test="PayEndYearFlag =99">
		<msg>���սɷ��ڼ����Ͳ���Ϊ������ɷѡ�</msg>
	</xsl:if>	
	
	<xsl:if test="PayEndYearFlag =100">
		<msg>���սɷ��ڼ����Ͳ���Ϊ�������ڽɡ�</msg>
	</xsl:if>
	<xsl:if test="PayEndYear =''">
		<msg>���սɷ��ڼ䲻��Ϊ��</msg>
	</xsl:if>	
	
	<xsl:if test="PayEndYearFlag ='--' or PayEndYearFlag =''">
		<msg>���սɷ��ڼ����Ͳ���Ϊ��</msg>
	</xsl:if>	
	<xsl:if test="PayEndYearFlag =99">
		<msg>���սɷ��ڼ����Ͳ���Ϊ��������</msg>
	</xsl:if>	
	
		<xsl:if test="PayEndYearFlag ='98'">
		<msg>���սɷ��ڼ����Ͳ���Ϊ���޹ء�</msg>
	</xsl:if>	
	<xsl:if test="PayIntv = '--' or PayIntv = ''">  
		<msg>�ɷѷ�ʽ����Ϊ��</msg> 
	</xsl:if>
	 
	<xsl:if test="PayIntv='9'">  
		<msg>�ɷѷ�ʽ����Ϊ���������޹�</msg> 
	</xsl:if>
	
	<xsl:if test="InsuYearFlag='9'">
		<msg>���ձ������ڱ�־����Ϊ����</msg>
	</xsl:if> 
	<xsl:if test="Mult=-1">
	<msg>���շ�������ȷ������������Ϊ������</msg>
	</xsl:if>
	
	<xsl:if test="Amnt ='' and  Mult = '' ">
		<msg>���շ�������������ͬʱΪ��</msg>
	</xsl:if> 	 
	
	<xsl:if test="(MainRiskCode='113020' or MainRiskCode='113030') and BonusGetMode!='1'">
	<msg>������ȡ��ʽ�������ۼ���Ϣ</msg> 
	</xsl:if>
	
	<xsl:if test="Prem =''">
		<msg>���ձ��շѲ���Ϊ��</msg>
	</xsl:if> 	
</xsl:if>


</xsl:template>
</xsl:stylesheet>