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
		<msg>交易代码不能为空</msg>
	</xsl:if>	
</xsl:template>
  
<xsl:template match="TranData/Body"> 

<xsl:if test="BankCode!=10">

<xsl:variable name="sContPrtNo" select="java:com.sinosoft.midplat.common.YBTDataVerification.PrtNoVerification(ContPrtNo)" />
	<xsl:if test="string-length(ContPrtNo) = 0 ">
		<msg>单证号不能为空</msg>
	</xsl:if>
		<xsl:if test="$sContPrtNo = 1 and (string-length(ContPrtNo) != 0)"> 
		<msg>单证号录入错误，银保通单证号为8位纯数字，不含字母</msg>
	</xsl:if>    
</xsl:if>
	<xsl:if test="string-length(AccNo)=0">
		<msg>缴费账户不能为空</msg>
	</xsl:if> 
	<xsl:if test="HealthNotice = 'Y'">
		<msg>有健康告知则自核不通过</msg>
	</xsl:if>
	<xsl:if test="JobNotice = 'Y'"> 
		<msg>有职业告知则自核不通过</msg>
	</xsl:if>
	<xsl:if test="string-length(PolApplyDate)=0">
		<msg>投保日期不能为空</msg> 
	</xsl:if>  
	<xsl:if test="PolApplyDate != (java:com.sinosoft.midplat.common.DateUtil.getCur8Date())">
		<msg>投保日期必须为当天</msg> 
	</xsl:if> 
	<xsl:if test="AccName !=(Appnt/Name)">
		<msg>帐户姓名必须与投保人姓名一致</msg> 
	</xsl:if> 
	<xsl:apply-templates select="Appnt"/> 
	<xsl:apply-templates select="Insured"/>
	<xsl:apply-templates select="Bnf"/>
	<xsl:apply-templates select="Risk"/>
</xsl:template>

<!-- 投保人 -->
<xsl:template match="Appnt">
<xsl:variable name="ZipCode" select="java:com.sinosoft.midplat.common.YBTDataVerification.ZipCodeVerification(ZipCode)" />
	
	<!-- 投保人姓名非空校验 -->
	<xsl:if test="Name= ''">   
		<msg>投保人姓名不能为空</msg>  
	</xsl:if>	
	
	<!-- 投保人姓名长度校验 -->
	<xsl:if test="string-length(Name) > 20"> 
		<msg>投保人姓名长度不能超过20位</msg>
	</xsl:if> 
	 
	<!--投保人姓别非空校验-->
	<xsl:if test="Sex = '--' or Sex = ''"> 
		<msg>投保人性别不能为空</msg>
	</xsl:if> 
	
	<!-- 投保人出生日期非空校验 -->
	<xsl:if test="string-length(Birthday)= 0">
		<msg>投保人出生日期不能为空</msg>
	</xsl:if> 
	  
	<!-- 投保人证件类型非空校验 -->
	<xsl:if test="IDType = '--' or IDType=''">   
		<msg>投保人证件类型不能为空</msg>
	</xsl:if> 
	  
	<!-- 投保人证件号码非空校验 --> 
	<xsl:if test="string-length(IDNo)= 0">   
		<msg>投保人证件号码不能为空</msg>
	</xsl:if>   
	
		<!-- 投保人有效期非空校验 -->
	<xsl:if test="IDExpDate= ''">   
		<msg>投保人证件有效日期不能为空</msg>  
	</xsl:if>
	
	<!-- 期交时，投保人年收入非空校验 -->      
	<xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/PayIntv!='0'">
	<xsl:if test="Salary = ''"> 
		<msg>投保人年收入不能为空</msg>
	</xsl:if>  
	 </xsl:if>
	<!-- 投保人国籍非空校验 -->
	<xsl:if test="Nationality= ''">   
		<msg>投保人国籍不能为空</msg>  
	</xsl:if>
	  <xsl:if test="Nationality!='CHN'">
		<msg>投保人国籍必须是中国</msg> 
	</xsl:if> 
	<!-- 投保人身份证号只能为15位或者18位校验 -->
	<xsl:if test="IDType = '0' and not((string-length(IDNo)= 15) or (string-length(IDNo) = 18)) and (string-length(IDNo)!= 0)">
		<msg>投保人身份证号只能为15位或者18位</msg>
	</xsl:if>    
	 
	<!-- 投保人15位身份证号与投保人性别不匹配 -->
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 15)) and ((substring(IDNo,15,1) mod 2) = Sex)">
		<msg>投保人15位身份证号与投保人性别不匹配</msg>
	</xsl:if>  
	
	<!-- 投保人18位身份证号与投保人性别不匹配 -->
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 18)) and ((substring(IDNo,17,1) mod 2) = Sex)">
		<msg>投保人18位身份证号与投保人性别不匹配</msg>
	</xsl:if>     
	       
	<!-- 投保人15位身份证号与投保人出生日期不匹配 -->       
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 15)) and (substring(IDNo,7,6) != substring(Birthday,3,6)) and (string-length(Birthday)!= 0)">
		<msg>投保人15位身份证号与投保人出生日期不匹配</msg>
	</xsl:if>   
	  
	<!-- 投保人18位身份证号与投保人出生日期不匹配 -->
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 18)) and (substring(IDNo,7,8) != Birthday and (string-length(Birthday)!= 0))">
		<msg>投保人18位身份证号与投保人出生日期不匹配</msg>
	</xsl:if>   
	
	<!-- 投保人邮寄地址不能为空校验 -->
	<xsl:if test="string-length(Address) = 0 "> 
		<msg>投保人邮寄地址不能为空</msg>
	</xsl:if>        
	 
	 <!-- 投保人邮编不能为空校验 -->
	<xsl:if test="string-length(ZipCode) = 0 ">
		<msg>投保人邮编不能为空</msg>
	</xsl:if>
	 
	<!-- 投保人邮政编码格式校验 -->               
	<xsl:if test="$ZipCode = 0 and (string-length(ZipCode) != 0)"> 
		<msg>投保人邮编必须为6位数字</msg>
	</xsl:if>    
	  	<!-- 投保人电话非空校验 -->
	<xsl:if test="(Mobile= '')and (Phone= '')">   
		<msg>投保人手机号码和电话号码不能同时为空</msg>  
	</xsl:if>
	  
	 <!-- 投保人与被保人的关系校校验 -->
	<xsl:if test = "RelaToInsured = '--' or RelaToInsured=''"> 
		<msg>投保人与被保人的关系不能为空</msg>
	</xsl:if>    
	  
</xsl:template>              
     
<!-- 被保人 -->  
<xsl:template match="Insured"> 
	<xsl:variable name="ZipCode" select="java:com.sinosoft.midplat.common.YBTDataVerification.ZipCodeVerification(ZipCode)" />
	
	<!-- 被保人姓名非空校验 -->
	<xsl:if test="Name= ''">
		<msg>被保人姓名不能为空</msg>  
	</xsl:if>	
	
	<!-- 被保人姓名长度校验 -->
	<xsl:if test="string-length(Name) > 20"> 
		<msg>被保人姓名长度不能超过20位</msg>
	</xsl:if> 
	 
	<!--被保人姓别非空校验-->
	<xsl:if test="Sex = '--' or Sex = '' "> 
		<msg>被保人性别不能为空</msg>
	</xsl:if> 
	
	<!-- 被保人出生日期非空校验 -->
	<xsl:if test="string-length(Birthday)= 0">
		<msg>被保人出生日期不能为空</msg>
	</xsl:if> 
	  
	<!-- 被保人证件类型非空校验 -->
	<xsl:if test="IDType = '--' or IDType =''">   
		<msg>被保人证件类型不能为空</msg>
	</xsl:if> 
	  
	<!-- 被保人证件号码非空校验 --> 
	<xsl:if test="string-length(IDNo)= 0">   
		<msg>被保人证件号码不能为空</msg>
	</xsl:if>   
	
	<!-- 被保人身份证号只能为15位或者18位校验 -->
	<xsl:if test="IDType = '0' and not((string-length(IDNo)= 15) or (string-length(IDNo) = 18)) and (string-length(IDNo)!= 0)">
		<msg>被保人身份证号只能为15位或者18位</msg>
	</xsl:if>    
	 
	<!-- 被保人15位身份证号与被保人性别不匹配 -->
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 15)) and ((substring(IDNo,15,1) mod 2) = Sex)">
		<msg>被保人15位身份证号与被保人性别不匹配</msg>
	</xsl:if>  
	
	<!-- 被保人18位身份证号与被保人性别不匹配 -->
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 18)) and ((substring(IDNo,17,1) mod 2) = Sex)">
		<msg>被保人18位身份证号与被保人性别不匹配</msg>
	</xsl:if>     
	       
	<!-- 被保人15位身份证号与被保人出生日期不匹配 -->       
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 15)) and (substring(IDNo,7,6) != substring(Birthday,3,6)) and (string-length(Birthday)!= 0)">
		<msg>被保人15位身份证号与被保人出生日期不匹配</msg>
	</xsl:if>   
	  
	<!-- 被保人18位身份证号与被保人出生日期不匹配 -->
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 18)) and (substring(IDNo,7,8) != Birthday and (string-length(Birthday)!= 0))">
		<msg>被保人18位身份证号与被保人出生日期不匹配</msg>
	</xsl:if>   
	
	<!-- 被保人邮寄地址不能为空校验 -->
	<xsl:if test="string-length(Address) = 0 "> 
		<msg>被保人邮寄地址不能为空</msg>
	</xsl:if>        

	<!--被保人有效期非空校验 -->
	<xsl:if test="IDExpDate= ''">   
		<msg>被保人证件有效日期不能为空</msg>  
	</xsl:if>

	<!-- 被保人国籍非空校验 -->
	<xsl:if test="Nationality= ''">   
		<msg>被保人国籍不能为空</msg>  
	</xsl:if>
	  <xsl:if test="Nationality!='CHN'">
	<msg>被保人国籍必须是中国</msg> 
	</xsl:if> 
	 <!-- 被保人邮编不能为空校验 -->
	<xsl:if test="string-length(ZipCode) = 0 ">
		<msg>被保人邮编不能为空</msg>
	</xsl:if>
	 
	<!-- 被保人邮政编码格式校验,引用$ZipCode = 0 时调用校验规则 -->               
	<xsl:if test="$ZipCode = 0 and (string-length(ZipCode) != 0)"> 
		<msg>被保人邮政编码格式不正确</msg>
	</xsl:if>  
</xsl:template>

<!-- 受益人 -->
<xsl:template match="Bnf">  
	<xsl:if test="BeneficType = 'N'"> 
	
	<!-- 受益人姓名非空校验 -->
	<xsl:if test="Name= ''">
		<msg>受益人姓名不能为空</msg>  
	</xsl:if>	
	
	<!-- 受益人姓名长度校验 -->
	<xsl:if test="string-length(Name) > 20"> 
		<msg>受益人姓名长度不能超过20位</msg>
	</xsl:if> 
	 
	<!--受益人姓别非空校验-->
	<xsl:if test="Sex = '--' or Sex=''"> 
		<msg>受益人姓别不能为空</msg>
	</xsl:if> 
	 
	<!-- 受益人出生日期非空校验 -->
	<xsl:if test="string-length(Birthday) = 0">
		<msg>受益人出生日期不能为空</msg>
	</xsl:if>   
	  
	<!-- 受益人证件类型非空校验 -->
	<xsl:if test="IDType = '--' or IDType = ''">    
		<msg>受益人证件类型不能为空</msg>
	</xsl:if>  
	  
	<!-- 受益人证件号码非空校验 --> 
	<xsl:if test="string-length(IDNo)= 0">   
		<msg>受益人证件号码不能为空</msg> 
	</xsl:if>    
	
	
	<!-- 受益人身份证号只能为15位或者18位校验 -->
	<xsl:if test="IDType = '0' and not((string-length(IDNo)= 15) or (string-length(IDNo) = 18)) and (string-length(IDNo)!= 0)">
		<msg>受益人身份证号只能为15位或者18位</msg>
	</xsl:if>    
	 
	<!-- 受益人15位身份证号与受益人性别不匹配 -->
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 15)) and ((substring(IDNo,15,1) mod 2) = Sex)">
		<msg>受益人15位身份证号与受益人性别不匹配</msg>
	</xsl:if>  
	
	<!-- 受益人18位身份证号与受益人性别不匹配 -->
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 18)) and ((substring(IDNo,17,1) mod 2) = Sex)">
		<msg>受益人18位身份证号与受益人性别不匹配</msg>
	</xsl:if>     
	       
	<!-- 受益人15位身份证号与受益人出生日期不匹配 -->       
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 15)) and (substring(IDNo,7,6) != substring(Birthday,3,6)) and (string-length(Birthday)!= 0)">
		<msg>受益人15位身份证号与受益人出生日期不匹配</msg>
	</xsl:if>   
	  
	<!-- 受益人18位身份证号与受益人出生日期不匹配 -->
	<xsl:if test="((IDType = '0') and (string-length(IDNo)= 18)) and (substring(IDNo,7,8) != Birthday and (string-length(Birthday)!= 0))">
		<msg>受益人18位身份证号与受益人出生日期不匹配</msg>
	</xsl:if>   
		
	 <!-- 收益人与被保人的关系校校验 -->
	<xsl:if test = "RelaToInsured = '--' or RelaToInsured = ''"> 
		<msg>受益人与被保人的关系不能为空</msg>
	</xsl:if>  
		 
	<xsl:if test="Grade = ''">
		<msg>受益人受益顺序不能为空</msg>  
	</xsl:if>
		 
	<xsl:if test="Lot = ''">
		<msg>受益人受益份额不能为空</msg>  
	</xsl:if>
		
	</xsl:if>  
</xsl:template>  


<!-- 险种 --> 
<xsl:template match="Risk">
<xsl:if test="RiskCode=MainRiskCode">
	<xsl:if test="RiskCode='--' or RiskCode = ''" >
		<msg>主险种代码不能为空</msg>
	</xsl:if>
	<xsl:if test="Prem=''">
		<msg>主险保费不能为空</msg>
	</xsl:if>
	<xsl:if test="InsuYear =''">
		<msg>主险保险期间不能为空</msg>
	</xsl:if>  
 
	<xsl:if test="InsuYearFlag ='--' or InsuYearFlag =''">
		<msg>主险保险期间标志不能为空</msg>
	</xsl:if>
		<xsl:if test="PayEndYearFlag =99">
		<msg>主险缴费期间类型不能为‘终身缴费’</msg>
	</xsl:if>	
	
	<xsl:if test="PayEndYearFlag =100">
		<msg>主险缴费期间类型不能为‘不定期缴’</msg>
	</xsl:if>
	<xsl:if test="PayEndYear =''">
		<msg>主险缴费期间不能为空</msg>
	</xsl:if>	
	
	<xsl:if test="PayEndYearFlag ='--' or PayEndYearFlag =''">
		<msg>主险缴费期间类型不能为空</msg>
	</xsl:if>	
	<xsl:if test="PayEndYearFlag =99">
		<msg>主险缴费期间类型不能为‘其他’</msg>
	</xsl:if>	
	
		<xsl:if test="PayEndYearFlag ='98'">
		<msg>主险缴费期间类型不能为‘无关’</msg>
	</xsl:if>	
	<xsl:if test="PayIntv = '--' or PayIntv = ''">  
		<msg>缴费方式不能为空</msg> 
	</xsl:if>
	 
	<xsl:if test="PayIntv='9'">  
		<msg>缴费方式不能为其他或者无关</msg> 
	</xsl:if>
	
	<xsl:if test="InsuYearFlag='9'">
		<msg>主险保险年期标志不能为其他</msg>
	</xsl:if> 
	<xsl:if test="Mult=-1">
	<msg>主险份数不正确（保单份数不为整数）</msg>
	</xsl:if>
	
	<xsl:if test="Amnt ='' and  Mult = '' ">
		<msg>主险份数与基本保额不得同时为空</msg>
	</xsl:if> 	 
	
	<xsl:if test="(MainRiskCode='113020' or MainRiskCode='113030') and BonusGetMode!='1'">
	<msg>红利领取方式必须是累计生息</msg> 
	</xsl:if>
	
	<xsl:if test="Prem =''">
		<msg>主险保险费不得为空</msg>
	</xsl:if> 	
</xsl:if>


</xsl:template>
</xsl:stylesheet>