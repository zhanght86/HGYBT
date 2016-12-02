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
<xsl:variable name="sTranCom" select="../Head/TranCom" />
<xsl:variable name="sSaleChannel" select="../Body/SaleChannel" />
<xsl:variable name="sRiskCode" select="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode" />
	<xsl:if test="string-length(ContPrtNo) = 0 and $sSaleChannel ='0'  and $sTranCom='01'">
		<msg>单证号不能为空</msg>
	</xsl:if>
	<!-- 借贷险工行不传缴费账户，此处校验放开 -->
	<xsl:if test="string-length(AccNo)=0">
		<xsl:choose>
			<xsl:when test="$sTranCom='03'">
			</xsl:when>
			<xsl:when test="$sTranCom='01' and ($sRiskCode = '211901' or $sRiskCode = '211902')">
			</xsl:when>
			<xsl:when test="$sTranCom='05' and (Risk/PayIntv = 0 )"><!-- 农行趸交的时候为空，期缴的时候必填-->
			</xsl:when>
			<xsl:when test="$sTranCom='05' and (string-length(Risk/AccName) = 0 )"><!-- 农行自助终端的时候缴费账户可以为空-->
			</xsl:when>
			<xsl:otherwise>
				<msg>缴费账户不能为空</msg>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:if>
<!-- 	<xsl:if test="$sTranCom='01' and $sRiskCode != '211901' and $sRiskCode != '211902' and string-length(AccNo)=0"> -->
		
<!-- 	</xsl:if>  -->
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
	
	<xsl:if test="$sTranCom='05' and string-length(SaleName) = 0">
		<msg>银行销售人员姓名不能为空</msg> 
	</xsl:if> 
	<xsl:if test="$sTranCom='05' and string-length(SaleStaff) = 0">
		<msg>银行销售人员代码不能为空</msg> 
	</xsl:if> 
	<!--  
	<xsl:if test="AccName !=(Appnt/Name)">
		<msg>帐户姓名必须与投保人姓名一致</msg> 
	</xsl:if> 
	-->
  
	<xsl:apply-templates select="Appnt"/> 
	<xsl:apply-templates select="Insured"/>
	<xsl:apply-templates select="Bnf"/>
	<xsl:apply-templates select="Risk"/>
	<xsl:apply-templates select="Loan"/>
</xsl:template>

<!-- 投保人 -->
<xsl:template match="Appnt">
<xsl:variable name="ZipCode" select="java:com.sinosoft.midplat.common.YBTDataVerification.ZipCodeVerification(ZipCode)" />
<xsl:variable name="WorkZipCode" select="java:com.sinosoft.midplat.common.YBTDataVerification.ZipCodeVerification(WorkZipCode)" />
<xsl:variable name="sTranCom" select="/TranData/Head/TranCom" />	
	<!-- 投保人姓名非空校验 -->
	<xsl:if test="Name= ''">   
		<msg>投保人姓名不能为空</msg>  
	</xsl:if>	
	
	<!-- 投保人姓名长度校验 -->
	<xsl:if test="string-length(Name) > 20"> 
		<msg>投保人姓名长度不能超过20位</msg>
	</xsl:if> 
	 
	<!--投保人姓别非空校验-->
	<xsl:if test="Sex = '--'"> 
		<msg>投保人性别不能为空</msg>
	</xsl:if> 
	
	<!-- 投保人出生日期非空校验 -->
	<xsl:if test="string-length(Birthday)= 0">
		<msg>投保人出生日期不能为空</msg>
	</xsl:if> 
	  
	<!-- 投保人证件类型非空校验 -->
	<xsl:if test="IDType = '--'">   
		<msg>投保人证件类型不能为空</msg>
	</xsl:if> 
	  
	<!-- 投保人证件号码非空校验 --> 
	<xsl:if test="string-length(IDNo)= 0">   
		<msg>投保人证件号码不能为空</msg>
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
	
	<!-- 投保人通讯地址不能为空校验 -工行，农行 -->
	<xsl:if test="$sTranCom !='03' and string-length(Address) = 0 "> 
		<msg>投保人通讯地址不能为空</msg>
	</xsl:if>     
	<!-- 投保人单位地址和家庭地址不能同时为空校验 -建行 -->
	<xsl:if test="$sTranCom ='03' and string-length(Address) = 0 and string-length(WorkAddress) = 0"> 
		<msg>投保人单位地址和家庭地址不能同时为空</msg>
	</xsl:if>   
	<!-- 投保人家庭电话和单位电话不能同时为空校验 -建行 -->
	<xsl:if test="$sTranCom ='03' and string-length(Phone) = 0 and string-length(WorkPhone) = 0 and string-length(Mobile) = 0"> 
		<msg>投保人家庭电话、单位电话和手机号不能同时为空</msg>
	</xsl:if>  
	
	<!-- 投保人单位邮编和家庭邮编不能同时为空校验 -建行 -->
	<xsl:if test="$sTranCom ='03' and string-length(WorkZipCode) = 0 and string-length(ZipCode) = 0"> 
		<msg>投保人单位邮编和家庭邮编不能同时为空</msg>
	</xsl:if>
	
	<!-- 投保人电话和手机不能同时为空校验 -农行 -->
	<xsl:if test="$sTranCom ='05' and string-length(Phone) = 0 and string-length(Mobile) = 0"> 
		<msg>投保人电话和手机不能同时为空</msg>
	</xsl:if>
	
	<xsl:if test="$sTranCom ='03'  and $ZipCode = 0 and (string-length(ZipCode) != 0)"> 
		<msg>投保人家庭邮编必须为6位数字</msg>
	</xsl:if>   
	
	<xsl:if test="$sTranCom ='03' and  $WorkZipCode = 0 and (string-length(WorkZipCode) != 0)"> 
		<msg>投保人单位邮编必须为6位数字</msg>
	</xsl:if>   
	 
	 <!-- 投保人邮编不能为空校验 -->
	<xsl:if test="$sTranCom !='03' and string-length(ZipCode) = 0 ">
		<msg>投保人邮编不能为空</msg>
	</xsl:if>
	 
	<!-- 投保人邮政编码格式校验 -->               
	<xsl:if test="$sTranCom !='03' and $ZipCode = 0 and (string-length(ZipCode) != 0)"> 
		<msg>投保人邮编必须为6位数字</msg>
	</xsl:if>    
	  
	 <!-- 投保人与被保人的关系校校验 -->
	<xsl:if test = "RelaToInsured = '--'"> 
		<msg>投保人与被保人的关系不能为空</msg>
	</xsl:if>    
	
	<!-- 投保人证件号码非空校验 lilu20141212 金华银行加了校验，其他银行要加等通知 -->
	<xsl:if test="$sTranCom ='07' and string-length(DenType)= 0">   
		<msg>居民类型不能为空,录入内容选项:城镇、农村。</msg>
	</xsl:if> 
	
	<!-- 投保人证件号码非空校验 lilu20150118 宁波银行加了校验，其他银行要加等通知 -->
	<xsl:if test="$sTranCom ='06' and string-length(DenType)= 0">   
		<msg>居民类型不能为空,录入内容选项:城镇、农村。</msg>
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
	<xsl:if test="Sex = '--'"> 
		<msg>被保人性别不能为空</msg>
	</xsl:if> 
	
	<!-- 被保人出生日期非空校验 -->
	<xsl:if test="string-length(Birthday)= 0">
		<msg>被保人出生日期不能为空</msg>
	</xsl:if> 
	  
	<!-- 被保人证件类型非空校验 -->
	<xsl:if test="IDType = '--'">   
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
	 
	 <!-- 被保人邮编不能为空校验 -->
	<xsl:if test="string-length(ZipCode) = 0 ">
		<msg>被保人邮编不能为空</msg>
	</xsl:if>
	 
	<!-- 被保人邮政编码格式校验 -->               
	<xsl:if test="$ZipCode = 0 and (string-length(ZipCode) != 0)"> 
		<msg>被保人邮政编码格式不正确</msg>
	</xsl:if>  
</xsl:template>

<!-- 受益人 -->
<xsl:template match="Bnf">  
<xsl:variable name="sTranCom" select="/TranData/Head/TranCom" />	

	<xsl:if test="BeneficType = 'N'"> 
	
	<!-- 建行目前所以产品的受益人类型只支持 1-死亡受益人 -->
	<xsl:if test="$sTranCom ='03' and Type!='1'">
		<msg>受益人类型只能为死亡受益人</msg>  
	</xsl:if>
	
	<!-- 受益人姓名非空校验 -->
	<xsl:if test="Name= ''">
		<msg>受益人姓名不能为空</msg>  
	</xsl:if>	
	
	<!-- 受益人姓名长度校验 -->
	<xsl:if test="string-length(Name) > 20"> 
		<msg>受益人姓名长度不能超过20位</msg>
	</xsl:if> 
	 
	<!--受益人姓别非空校验-->
	<xsl:if test="Sex = '--'"> 
		<msg>受益人性别不能为空</msg>
	</xsl:if> 
	 
	<!-- 受益人出生日期非空校验 -->
	<xsl:if test="string-length(Birthday) = 0">
		<msg>受益人出生日期不能为空</msg>
	</xsl:if>   
	  
	<!-- 受益人证件类型非空校验 -->
	<xsl:if test="IDType = '--'">    
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
	<xsl:if test = "RelaToInsured = '--'"> 
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
<xsl:variable name="sTranCom" select="/TranData/Head/TranCom" />	
<xsl:variable name="sFuncFlag" select="/TranData/Head/FuncFlag" />

	<xsl:if test="RiskCode='--'">
		<msg>主险种代码不能为空</msg>
	</xsl:if>
	<!-- 保额算保费产品 需要试算，试算时不校验保费字段  永利，保驾护航，悦无忧，安赢，永承-->
	<xsl:if test="Prem=''  and RiskCode!='231302'  and RiskCode!='221203'  and RiskCode!='225501'  and RiskCode!='231301'  and RiskCode!='221201' and RiskCode!='211901' and RiskCode!='211902' and RiskCode!='421101'">
		<msg>主险保费不能为空</msg>
	</xsl:if>
	<xsl:if test="RiskCode ='231301' and $sFuncFlag='1013'  and Prem=''">
		<msg>主险保费不能为空</msg>
	</xsl:if>
	<!-- 借意险的保险期间不需要校验 -->
	<xsl:if test="RiskCode !='211901' and RiskCode !='211902' and InsuYear =''">
		<msg>主险保险期间不能为空</msg>
	</xsl:if>  
 	<!-- 借意险的保险期间类型不需要校验 -->
	<xsl:if test="RiskCode !='211901' and RiskCode !='211902' and InsuYearFlag ='--'">
		<msg>主险保险期间标志不能为空</msg>
	</xsl:if>
	<!-- 农行主险缴费方式为趸交的时候，缴费期间类型和缴费年期值为空 -->
	<xsl:if test="$sTranCom ='05' and PayIntv !='0' and PayEndYear =''">
		<msg>主险缴费期间不能为空</msg>
	</xsl:if>	
	<xsl:if test="$sTranCom ='05' and PayIntv !='0' and PayEndYearFlag =''">
		<msg>主险缴费期间类型不能为空</msg>
	</xsl:if>	
	
	<!-- 借贷险的缴费方式只能为趸交校验 -->
	<xsl:if test="RiskCode ='211901' and PayIntv !='0' ">
		<msg>该险种的缴费方式只能为趸交</msg>
	</xsl:if>
	
	<xsl:if test="RiskCode ='211902' and PayIntv !='0' ">
		<msg>该险种的缴费方式只能为趸交</msg>
	</xsl:if>
	
	<xsl:if test="$sTranCom !='05' and PayEndYear =''">
		<msg>主险缴费期间不能为空</msg>
	</xsl:if>	
	
	<xsl:if test="$sTranCom !='05' and PayEndYearFlag =''">
		<msg>主险缴费期间类型不能为空</msg>
	</xsl:if>	

	<xsl:if test="PayIntv = '--'">  
		<msg>缴费频次不能为空</msg> 
	</xsl:if>
	 
	<xsl:if test="PayIntv='9'">  
		<msg>缴费频次不能为其他</msg> 
	</xsl:if>
	
	<xsl:if test="InsuYearFlag='9'">
		<msg>主险保险年期标志不能为其他</msg>
	</xsl:if> 
</xsl:if>


</xsl:template>

<!-- 借贷款险种的贷款信息做非空校验 --> 
<xsl:template match="Loan">
<xsl:variable name="sRiskCode" select="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode" />
<xsl:if test="$sRiskCode = '211901' or $sRiskCode = '211902'">
	<xsl:if test="LoanNo=''">
		<msg>贷款合同号不能为空</msg>
	</xsl:if>
	<xsl:if test="LoanBank=''">
		<msg>贷款机构不能为空</msg>
	</xsl:if>
	<xsl:if test="LoanType =''">
		<msg>贷款种类不能为空</msg>
	</xsl:if>  
	<xsl:if test="LoanPrem =''">
		<msg>贷款金额不能为空</msg>
	</xsl:if>
	<xsl:if test="InsuDate =''">
		<msg>保险起始日不能为空</msg>
	</xsl:if>	
	<xsl:if test="InsuEndDate =''">
		<msg>保险期满日不能为空</msg>
	</xsl:if>
</xsl:if>
</xsl:template>
</xsl:stylesheet>