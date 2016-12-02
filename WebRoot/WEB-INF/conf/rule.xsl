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
<xsl:variable name="sTranCom" select="../Head/TranCom" />
<xsl:variable name="sSaleChannel" select="../Body/SaleChannel" />
<xsl:variable name="sRiskCode" select="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode" />
	<xsl:if test="string-length(ContPrtNo) = 0 and $sSaleChannel ='0'  and $sTranCom='01'">
		<msg>��֤�Ų���Ϊ��</msg>
	</xsl:if>
	<!-- ����չ��в����ɷ��˻����˴�У��ſ� -->
	<xsl:if test="string-length(AccNo)=0">
		<xsl:choose>
			<xsl:when test="$sTranCom='03'">
			</xsl:when>
			<xsl:when test="$sTranCom='01' and ($sRiskCode = '211901' or $sRiskCode = '211902')">
			</xsl:when>
			<xsl:when test="$sTranCom='05' and (Risk/PayIntv = 0 )"><!-- ũ��������ʱ��Ϊ�գ��ڽɵ�ʱ�����-->
			</xsl:when>
			<xsl:when test="$sTranCom='05' and (string-length(Risk/AccName) = 0 )"><!-- ũ�������ն˵�ʱ��ɷ��˻�����Ϊ��-->
			</xsl:when>
			<xsl:otherwise>
				<msg>�ɷ��˻�����Ϊ��</msg>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:if>
<!-- 	<xsl:if test="$sTranCom='01' and $sRiskCode != '211901' and $sRiskCode != '211902' and string-length(AccNo)=0"> -->
		
<!-- 	</xsl:if>  -->
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
	
	<xsl:if test="$sTranCom='05' and string-length(SaleName) = 0">
		<msg>����������Ա��������Ϊ��</msg> 
	</xsl:if> 
	<xsl:if test="$sTranCom='05' and string-length(SaleStaff) = 0">
		<msg>����������Ա���벻��Ϊ��</msg> 
	</xsl:if> 
	<!--  
	<xsl:if test="AccName !=(Appnt/Name)">
		<msg>�ʻ�����������Ͷ��������һ��</msg> 
	</xsl:if> 
	-->
  
	<xsl:apply-templates select="Appnt"/> 
	<xsl:apply-templates select="Insured"/>
	<xsl:apply-templates select="Bnf"/>
	<xsl:apply-templates select="Risk"/>
	<xsl:apply-templates select="Loan"/>
</xsl:template>

<!-- Ͷ���� -->
<xsl:template match="Appnt">
<xsl:variable name="ZipCode" select="java:com.sinosoft.midplat.common.YBTDataVerification.ZipCodeVerification(ZipCode)" />
<xsl:variable name="WorkZipCode" select="java:com.sinosoft.midplat.common.YBTDataVerification.ZipCodeVerification(WorkZipCode)" />
<xsl:variable name="sTranCom" select="/TranData/Head/TranCom" />	
	<!-- Ͷ���������ǿ�У�� -->
	<xsl:if test="Name= ''">   
		<msg>Ͷ������������Ϊ��</msg>  
	</xsl:if>	
	
	<!-- Ͷ������������У�� -->
	<xsl:if test="string-length(Name) > 20"> 
		<msg>Ͷ�����������Ȳ��ܳ���20λ</msg>
	</xsl:if> 
	 
	<!--Ͷ�����ձ�ǿ�У��-->
	<xsl:if test="Sex = '--'"> 
		<msg>Ͷ�����Ա���Ϊ��</msg>
	</xsl:if> 
	
	<!-- Ͷ���˳������ڷǿ�У�� -->
	<xsl:if test="string-length(Birthday)= 0">
		<msg>Ͷ���˳������ڲ���Ϊ��</msg>
	</xsl:if> 
	  
	<!-- Ͷ����֤�����ͷǿ�У�� -->
	<xsl:if test="IDType = '--'">   
		<msg>Ͷ����֤�����Ͳ���Ϊ��</msg>
	</xsl:if> 
	  
	<!-- Ͷ����֤������ǿ�У�� --> 
	<xsl:if test="string-length(IDNo)= 0">   
		<msg>Ͷ����֤�����벻��Ϊ��</msg>
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
	
	<!-- Ͷ����ͨѶ��ַ����Ϊ��У�� -���У�ũ�� -->
	<xsl:if test="$sTranCom !='03' and string-length(Address) = 0 "> 
		<msg>Ͷ����ͨѶ��ַ����Ϊ��</msg>
	</xsl:if>     
	<!-- Ͷ���˵�λ��ַ�ͼ�ͥ��ַ����ͬʱΪ��У�� -���� -->
	<xsl:if test="$sTranCom ='03' and string-length(Address) = 0 and string-length(WorkAddress) = 0"> 
		<msg>Ͷ���˵�λ��ַ�ͼ�ͥ��ַ����ͬʱΪ��</msg>
	</xsl:if>   
	<!-- Ͷ���˼�ͥ�绰�͵�λ�绰����ͬʱΪ��У�� -���� -->
	<xsl:if test="$sTranCom ='03' and string-length(Phone) = 0 and string-length(WorkPhone) = 0 and string-length(Mobile) = 0"> 
		<msg>Ͷ���˼�ͥ�绰����λ�绰���ֻ��Ų���ͬʱΪ��</msg>
	</xsl:if>  
	
	<!-- Ͷ���˵�λ�ʱ�ͼ�ͥ�ʱ಻��ͬʱΪ��У�� -���� -->
	<xsl:if test="$sTranCom ='03' and string-length(WorkZipCode) = 0 and string-length(ZipCode) = 0"> 
		<msg>Ͷ���˵�λ�ʱ�ͼ�ͥ�ʱ಻��ͬʱΪ��</msg>
	</xsl:if>
	
	<!-- Ͷ���˵绰���ֻ�����ͬʱΪ��У�� -ũ�� -->
	<xsl:if test="$sTranCom ='05' and string-length(Phone) = 0 and string-length(Mobile) = 0"> 
		<msg>Ͷ���˵绰���ֻ�����ͬʱΪ��</msg>
	</xsl:if>
	
	<xsl:if test="$sTranCom ='03'  and $ZipCode = 0 and (string-length(ZipCode) != 0)"> 
		<msg>Ͷ���˼�ͥ�ʱ����Ϊ6λ����</msg>
	</xsl:if>   
	
	<xsl:if test="$sTranCom ='03' and  $WorkZipCode = 0 and (string-length(WorkZipCode) != 0)"> 
		<msg>Ͷ���˵�λ�ʱ����Ϊ6λ����</msg>
	</xsl:if>   
	 
	 <!-- Ͷ�����ʱ಻��Ϊ��У�� -->
	<xsl:if test="$sTranCom !='03' and string-length(ZipCode) = 0 ">
		<msg>Ͷ�����ʱ಻��Ϊ��</msg>
	</xsl:if>
	 
	<!-- Ͷ�������������ʽУ�� -->               
	<xsl:if test="$sTranCom !='03' and $ZipCode = 0 and (string-length(ZipCode) != 0)"> 
		<msg>Ͷ�����ʱ����Ϊ6λ����</msg>
	</xsl:if>    
	  
	 <!-- Ͷ�����뱻���˵Ĺ�ϵУУ�� -->
	<xsl:if test = "RelaToInsured = '--'"> 
		<msg>Ͷ�����뱻���˵Ĺ�ϵ����Ϊ��</msg>
	</xsl:if>    
	
	<!-- Ͷ����֤������ǿ�У�� lilu20141212 �����м���У�飬��������Ҫ�ӵ�֪ͨ -->
	<xsl:if test="$sTranCom ='07' and string-length(DenType)= 0">   
		<msg>�������Ͳ���Ϊ��,¼������ѡ��:����ũ�塣</msg>
	</xsl:if> 
	
	<!-- Ͷ����֤������ǿ�У�� lilu20150118 �������м���У�飬��������Ҫ�ӵ�֪ͨ -->
	<xsl:if test="$sTranCom ='06' and string-length(DenType)= 0">   
		<msg>�������Ͳ���Ϊ��,¼������ѡ��:����ũ�塣</msg>
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
	<xsl:if test="Sex = '--'"> 
		<msg>�������Ա���Ϊ��</msg>
	</xsl:if> 
	
	<!-- �����˳������ڷǿ�У�� -->
	<xsl:if test="string-length(Birthday)= 0">
		<msg>�����˳������ڲ���Ϊ��</msg>
	</xsl:if> 
	  
	<!-- ������֤�����ͷǿ�У�� -->
	<xsl:if test="IDType = '--'">   
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
	 
	 <!-- �������ʱ಻��Ϊ��У�� -->
	<xsl:if test="string-length(ZipCode) = 0 ">
		<msg>�������ʱ಻��Ϊ��</msg>
	</xsl:if>
	 
	<!-- ���������������ʽУ�� -->               
	<xsl:if test="$ZipCode = 0 and (string-length(ZipCode) != 0)"> 
		<msg>���������������ʽ����ȷ</msg>
	</xsl:if>  
</xsl:template>

<!-- ������ -->
<xsl:template match="Bnf">  
<xsl:variable name="sTranCom" select="/TranData/Head/TranCom" />	

	<xsl:if test="BeneficType = 'N'"> 
	
	<!-- ����Ŀǰ���Բ�Ʒ������������ֻ֧�� 1-���������� -->
	<xsl:if test="$sTranCom ='03' and Type!='1'">
		<msg>����������ֻ��Ϊ����������</msg>  
	</xsl:if>
	
	<!-- �����������ǿ�У�� -->
	<xsl:if test="Name= ''">
		<msg>��������������Ϊ��</msg>  
	</xsl:if>	
	
	<!-- ��������������У�� -->
	<xsl:if test="string-length(Name) > 20"> 
		<msg>�������������Ȳ��ܳ���20λ</msg>
	</xsl:if> 
	 
	<!--�������ձ�ǿ�У��-->
	<xsl:if test="Sex = '--'"> 
		<msg>�������Ա���Ϊ��</msg>
	</xsl:if> 
	 
	<!-- �����˳������ڷǿ�У�� -->
	<xsl:if test="string-length(Birthday) = 0">
		<msg>�����˳������ڲ���Ϊ��</msg>
	</xsl:if>   
	  
	<!-- ������֤�����ͷǿ�У�� -->
	<xsl:if test="IDType = '--'">    
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
	<xsl:if test = "RelaToInsured = '--'"> 
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
<xsl:variable name="sTranCom" select="/TranData/Head/TranCom" />	
<xsl:variable name="sFuncFlag" select="/TranData/Head/FuncFlag" />

	<xsl:if test="RiskCode='--'">
		<msg>�����ִ��벻��Ϊ��</msg>
	</xsl:if>
	<!-- �����㱣�Ѳ�Ʒ ��Ҫ���㣬����ʱ��У�鱣���ֶ�  ���������ݻ����������ǣ���Ӯ������-->
	<xsl:if test="Prem=''  and RiskCode!='231302'  and RiskCode!='221203'  and RiskCode!='225501'  and RiskCode!='231301'  and RiskCode!='221201' and RiskCode!='211901' and RiskCode!='211902' and RiskCode!='421101'">
		<msg>���ձ��Ѳ���Ϊ��</msg>
	</xsl:if>
	<xsl:if test="RiskCode ='231301' and $sFuncFlag='1013'  and Prem=''">
		<msg>���ձ��Ѳ���Ϊ��</msg>
	</xsl:if>
	<!-- �����յı����ڼ䲻��ҪУ�� -->
	<xsl:if test="RiskCode !='211901' and RiskCode !='211902' and InsuYear =''">
		<msg>���ձ����ڼ䲻��Ϊ��</msg>
	</xsl:if>  
 	<!-- �����յı����ڼ����Ͳ���ҪУ�� -->
	<xsl:if test="RiskCode !='211901' and RiskCode !='211902' and InsuYearFlag ='--'">
		<msg>���ձ����ڼ��־����Ϊ��</msg>
	</xsl:if>
	<!-- ũ�����սɷѷ�ʽΪ������ʱ�򣬽ɷ��ڼ����ͺͽɷ�����ֵΪ�� -->
	<xsl:if test="$sTranCom ='05' and PayIntv !='0' and PayEndYear =''">
		<msg>���սɷ��ڼ䲻��Ϊ��</msg>
	</xsl:if>	
	<xsl:if test="$sTranCom ='05' and PayIntv !='0' and PayEndYearFlag =''">
		<msg>���սɷ��ڼ����Ͳ���Ϊ��</msg>
	</xsl:if>	
	
	<!-- ����յĽɷѷ�ʽֻ��Ϊ����У�� -->
	<xsl:if test="RiskCode ='211901' and PayIntv !='0' ">
		<msg>�����ֵĽɷѷ�ʽֻ��Ϊ����</msg>
	</xsl:if>
	
	<xsl:if test="RiskCode ='211902' and PayIntv !='0' ">
		<msg>�����ֵĽɷѷ�ʽֻ��Ϊ����</msg>
	</xsl:if>
	
	<xsl:if test="$sTranCom !='05' and PayEndYear =''">
		<msg>���սɷ��ڼ䲻��Ϊ��</msg>
	</xsl:if>	
	
	<xsl:if test="$sTranCom !='05' and PayEndYearFlag =''">
		<msg>���սɷ��ڼ����Ͳ���Ϊ��</msg>
	</xsl:if>	

	<xsl:if test="PayIntv = '--'">  
		<msg>�ɷ�Ƶ�β���Ϊ��</msg> 
	</xsl:if>
	 
	<xsl:if test="PayIntv='9'">  
		<msg>�ɷ�Ƶ�β���Ϊ����</msg> 
	</xsl:if>
	
	<xsl:if test="InsuYearFlag='9'">
		<msg>���ձ������ڱ�־����Ϊ����</msg>
	</xsl:if> 
</xsl:if>


</xsl:template>

<!-- ��������ֵĴ�����Ϣ���ǿ�У�� --> 
<xsl:template match="Loan">
<xsl:variable name="sRiskCode" select="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode" />
<xsl:if test="$sRiskCode = '211901' or $sRiskCode = '211902'">
	<xsl:if test="LoanNo=''">
		<msg>�����ͬ�Ų���Ϊ��</msg>
	</xsl:if>
	<xsl:if test="LoanBank=''">
		<msg>�����������Ϊ��</msg>
	</xsl:if>
	<xsl:if test="LoanType =''">
		<msg>�������಻��Ϊ��</msg>
	</xsl:if>  
	<xsl:if test="LoanPrem =''">
		<msg>�������Ϊ��</msg>
	</xsl:if>
	<xsl:if test="InsuDate =''">
		<msg>������ʼ�ղ���Ϊ��</msg>
	</xsl:if>	
	<xsl:if test="InsuEndDate =''">
		<msg>���������ղ���Ϊ��</msg>
	</xsl:if>
</xsl:if>
</xsl:template>
</xsl:stylesheet>