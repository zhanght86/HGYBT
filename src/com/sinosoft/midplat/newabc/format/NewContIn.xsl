<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">

 
<xsl:template match="ABCB2I">
	  <TranData><!-- 核心录单自核请求报文 -->
	     <Head>
	        <!-- 银行交易流水号 -->
			<TranNo><xsl:value-of select="Header/SerialNo"/></TranNo>
			<!-- 地区代码 -->
			<ZoneNo><xsl:value-of select="Header/ProvCode"/></ZoneNo>
			<!-- 网点代码 -->
			<NodeNo>
			<xsl:value-of select="Header/ProvCode"/>
			<xsl:value-of select="Header/BranchNo"/>
			</NodeNo>
	  		<!-- 银行交易日期 -->
	  		<TranDate><xsl:value-of select="Header/TransDate"/></TranDate>
	  		<!-- 交易时间-->
			<TranTime><xsl:value-of select="Header/TransTime"/></TranTime>
			
			<!-- 柜员代码 -->
			<xsl:choose>
			<xsl:when test="Header/EntrustWay ='11'">
			<TellerNo><xsl:value-of select="Header/Tlid"/></TellerNo>
			</xsl:when>
			<xsl:otherwise>
			<TellerNo>0005</TellerNo>
			</xsl:otherwise>
			</xsl:choose>
			
			
			
			<!-- 银行代码 -->
			<BankCode>0102</BankCode>
			<!-- YBT组织的节点信息 -->
			 <xsl:copy-of select="Head/*"/> <!-- -->
	  	</Head>
	  	
		<!--投保信息-->
		<Body>
			<!-- 农行自助终端渠道 0柜面 8自助终端 -->
			<xsl:choose>
			<xsl:when test="Header/EntrustWay ='11'">
			<SaleChannel>0</SaleChannel>
			</xsl:when>
			<xsl:when test="Header/EntrustWay ='04'">
			<SaleChannel>8</SaleChannel>
			</xsl:when>
			
			</xsl:choose>
		    <!-- 销售人员工号   与农行约定备用字段2为销售人员工号-->
		   
		    <xsl:choose>
			<xsl:when test="Header/EntrustWay ='11'">
			 <SaleStaff><xsl:value-of select="App/Req/Base/SalerCertNo" /></SaleStaff>
			</xsl:when>
			<xsl:when test="Header/EntrustWay ='04'">
			  <SaleStaff>0007 </SaleStaff>
			</xsl:when>
			</xsl:choose>
		    <!-- 销售人员姓名  -->
            <xsl:choose>
			<xsl:when test="Header/EntrustWay ='11'">
			 <SaleName>
            <xsl:value-of select="App/Req/Base/Saler" />
            </SaleName>
			</xsl:when>
			<xsl:when test="Header/EntrustWay ='04'">
			 <SaleName>0007</SaleName>
			</xsl:when>
			</xsl:choose>
			
            
            <!-- 销售人员资格证号  -->
            <SaleCertNo><xsl:value-of select="App/Req/Base/SalerCertNo" /></SaleCertNo>
            <!-- 试算申请顺序号 -->
			<ApplyNo><xsl:value-of  select ="App/Req/AppNo"/></ApplyNo >	
			<!-- 投保单号 -->
			<ProposalPrtNo>
			<xsl:if test="Header/EntrustWay = '11'"><xsl:value-of select="App/Req/Base/PolicyApplySerial"/></xsl:if>
			<xsl:if test="Header/EntrustWay = '04'"><xsl:value-of select="java:com.sinosoft.midplat.newabc.format.NewCont.trannoStringBuffer(Header/TransDate,Header/SerialNo)"/></xsl:if>
			</ProposalPrtNo>  
			<!-- 保单印刷号 -->
			<ContPrtNo>
			<xsl:if test="Header/EntrustWay = '11'"><xsl:value-of select="App/Req/Base/VchNo"/></xsl:if>
			<xsl:if test="Header/EntrustWay = '04'"></xsl:if>
			</ContPrtNo>
			<!-- 投保日期 -->
			<PolApplyDate><xsl:value-of select="App/Req/Base/ApplyDate"/></PolApplyDate>
			<!-- 账户姓名 -->
			<!--
			<AccName><xsl:value-of select="Appl/Name"/></AccName>
			-->
			<!-- 交费帐号姓名 续期缴费账号姓名ConAccName，农行没传首期账户姓名  -->
			<AccName>
			<xsl:if test="Header/EntrustWay = '11'"><xsl:value-of select="App/Req/Base/ConAccName"/></xsl:if>
			<xsl:if test="Header/EntrustWay = '04'"></xsl:if>
			</AccName>
			<!-- 账户银行代码 -->
			<AccBankCode/>
			<!-- 交费帐号 续期缴费账号 ConAccNo，首期缴费账号在新单确认交易中会传过来的， 和农行的联调人员沟通过20141203 -->
			<AccNo>
			<xsl:if test="Header/EntrustWay = '11'"><xsl:value-of select="App/Req/Base/ConAccNo"/></xsl:if>
			<xsl:if test="Header/EntrustWay = '04'"></xsl:if>
			</AccNo>
			
			<!--缴费形式 和朱诚沟通，从银保通出单的中韩这边缴费形式都置为B -工行对应处的说明-->
			<PayMode>B</PayMode>
			<!-- 保单传送方式 -->
			<GetPolMode>1</GetPolMode><!-- 默认为1，所以写死成1 -->
			<!-- 职业告知 -->
			<JobNotice/>
			<!-- 健康告知 ,取被保人健康告知  0否  1是-->
			<HealthNotice><xsl:apply-templates select="App/Req/Insu/HealthNotice"/></HealthNotice>
			<!-- 投保人 -->
			<xsl:apply-templates select="App/Req/Appl"/>
			<!-- 被保人 -->
			<xsl:apply-templates select="App/Req/Insu"/>
			
			<!-- 主险种信息 -->
		    <Risk>
		    <!-- 险种代码 -->
			<RiskCode><xsl:apply-templates select="App/Req/Risks/RiskCode"/></RiskCode>
			<!-- 主险险种代码 -->
			<MainRiskCode><xsl:apply-templates select="App/Req/Risks/RiskCode"/></MainRiskCode>
			<!-- 保额 -->
			<Amnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(App/Req/Risks/Amnt)"/></Amnt>
			<!-- 保费 -->
			<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(App/Req/Risks/Prem)"/></Prem>
			<!-- 投保份数 -->
			<Mult>
			<xsl:if test="Header/EntrustWay = '11'"><xsl:value-of select="App/Req/Risks/Share"/></xsl:if>
			<xsl:if test="Header/EntrustWay = '04'">1</xsl:if>
			</Mult>
			<!-- 缴费形式 -->
			<PayMode>B</PayMode>
			<!-- 缴费频次 -->
			<!--<xsl:choose> 农行借贷险的缴费频次为0-趸交-放开在rule.xsl做校验
			<xsl:when test="$MainProductCode=211901"><PayIntv>0</PayIntv></xsl:when>
			<xsl:otherwise>
			<PayIntv><xsl:apply-templates select="PayIntv"/></PayIntv>
			</xsl:otherwise>
			</xsl:choose>
			-->
			<xsl:choose>
			<xsl:when test="App/Req/Risks/RiskCode='145201'">
			<PayIntv>0</PayIntv>
			</xsl:when>
			<xsl:otherwise>
			<PayIntv><xsl:apply-templates select="App/Req/Risks/PayType"/></PayIntv>
			</xsl:otherwise>
			</xsl:choose>
			<xsl:choose>
				<xsl:when test="App/Req/Risks/PayType=1"><!-- 趸交传1000Y -->
				<!-- 缴费年期年龄标志 -->
				<PayEndYearFlag>Y</PayEndYearFlag>			
				<!-- 缴费年期年龄 -->
				<PayEndYear>1000</PayEndYear>
				</xsl:when>
				<xsl:when test="App/Req/Risks/RiskCode=211901"><!-- 趸交传1000Y -->
				<!-- 缴费年期年龄标志 -->
				<PayEndYearFlag>Y</PayEndYearFlag>			
				<!-- 缴费年期年龄 -->
				<PayEndYear>1000</PayEndYear>
				</xsl:when>
				<xsl:otherwise>
				<!-- 缴费年期年龄标志 -->
				<PayEndYearFlag><xsl:apply-templates select="App/Req/Risks/PayDueType"/></PayEndYearFlag>			
				<!-- 缴费年期年龄 -->
				<PayEndYear><xsl:value-of select="App/Req/Risks/PayDueDate"/></PayEndYear>
				</xsl:otherwise>
			</xsl:choose>
			
			<!-- 保险年期年龄标志 -->
			<InsuYearFlag><xsl:apply-templates select="App/Req/Risks/InsuDueType"/></InsuYearFlag>
			<!-- 保险年期年龄 -->
			<InsuYear><xsl:value-of select="App/Req/Risks/InsuDueDate"/></InsuYear>
			<!-- 红利领取方式 -->
			<BonusGetMode><xsl:apply-templates select="App/Req/Risks/BonusGetMode"/></BonusGetMode>
			<!-- 待定 --> <!-- 满期领取金领取方式 -->
			<FullBonusGetMode><xsl:apply-templates select="App/Req/Risks/FullBonusGetMode"/></FullBonusGetMode>
			<!-- 待定 --> <!-- 领取年龄年期标志 -->
			<GetYearFlag><xsl:apply-templates select="App/Req/Risks/GetYearFlag"/></GetYearFlag>
			<!-- 待定 --> <!-- 领取年龄 -->
			<GetYear><xsl:value-of select="App/Req/Risks/GetYear"/></GetYear>
			<!-- 农行不传 -->
			<GetIntv/>
			<GetBankCode/>
			<GetBankAccNo/>
			<GetAccName/>
		</Risk>
			<!-- 附加险信息 -->
			<xsl:apply-templates select="App/Req/Addt"/>
			<!-- 贷款信息 -->
			<xsl:apply-templates select="App/Req/Loan"/>
		</Body>
	</TranData>
</xsl:template>	
<xsl:template match="App/Req/Appl">
		<Appnt>
			<!-- 姓名 -->
			<Name><xsl:value-of select="Name"/></Name>
			<!-- 性别 -->
			<Sex><xsl:apply-templates select="Sex"/></Sex>
			<!-- 生日 -->
			<Birthday><xsl:value-of select="Birthday"/></Birthday>
			<!-- 证件类型 -->
			<IDType><xsl:apply-templates select="IDKind"/></IDType>
			<!-- 证件号码 -->
			<IDNo><xsl:value-of select="IDCode"/></IDNo>
			<!-- 地址 -->
			<Prov><xsl:value-of select="Prov"/></Prov>
			<City><xsl:value-of select="City"/></City>
			<Zone><xsl:value-of select="Zone"/></Zone>
			<Address><xsl:value-of select="Address"/></Address>
			<!-- 邮编 -->
			<ZipCode><xsl:value-of select="ZipCode"/></ZipCode>
			<!-- 家庭电话 -->
			<Phone><xsl:value-of select="Phone"/></Phone>
			<!-- 手机号码 -->
			<Mobile><xsl:value-of select="Mobile"/></Mobile>
			<!-- 电子邮件 -->
			<Email><xsl:value-of select="Email"/></Email>
			<!-- 职业代码 --><!-- 和核心以及客户确认过，农行不传，传默认值3010101（一般内勤），有确认邮件 -->
			 <JobCode>3010101</JobCode>
			<!-- 国籍 -->
			<Nationality>CHN</Nationality>
			<!-- 体重 -->
			<Stature/>
			<!-- 身高 -->
			<Weight/>
			<!-- 收入 -->
			<YearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(AnnualIncome)"/></YearSalary>
			
			<!-- 证件有效期 --><!-- 8位日期，长期有效为20991231 -->
			<xsl:choose>
				<xsl:when test = "InvalidDate=20991231"><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="InvalidDate"/></IdExpDate></xsl:otherwise>
			</xsl:choose>
			<!-- 婚否 -->
			<MaritalStatus/>
				<!-- 投保人与背保人关系 -->
		<RelaToInsured><xsl:apply-templates select="RelaToInsured"/></RelaToInsured>
		<!-- 投保人居民类型 -->
		 <DenType><xsl:apply-templates select="CustSource" /></DenType>
		</Appnt>
</xsl:template>
		
<!--被保人信息-->
<xsl:template match="App/Req/Insu">
		<Insured>
			<!-- 姓名 -->
			<Name><xsl:value-of select="Name"/></Name>
			<!-- 性别 -->
			<Sex><xsl:apply-templates select="Sex"/></Sex>
			<!-- 生日 -->
			<Birthday><xsl:value-of select="Birthday"/></Birthday>
			<!-- 证件类型 -->
			<IDType><xsl:apply-templates select="IDKind"/></IDType>
			<!-- 证件号码 -->
			<IDNo><xsl:value-of select="IDCode"/></IDNo>
			<!-- 地址 -->
			<Prov><xsl:value-of select="Prov"/></Prov>
			<City><xsl:value-of select="City"/></City>
			<Zone><xsl:value-of select="Zone"/></Zone>
			<Address><xsl:value-of select="Address"/></Address>
			<!-- 邮编 -->
			<ZipCode><xsl:value-of select="ZipCode"/></ZipCode>
			<!-- 家庭电话 -->
			<Phone><xsl:value-of select="Phone"/></Phone>
			<!-- 手机号码 -->
			<Mobile><xsl:value-of select="Mobile"/></Mobile>
			<!-- 电子邮件 -->
			<Email><xsl:value-of select="Email"/></Email>
			<!-- 职业代码 --><!-- 和核心以及客户确认过，农行不传，传默认值3010101（一般内勤），有确认邮件 -->
			<JobCode>3010101</JobCode>
			<!-- 国籍 -->
			<Nationality>CHN</Nationality>
			<!-- 体重 -->
			<Stature><xsl:value-of select="Tall"/></Stature>
			<!-- 身高 -->
			<Weight><xsl:value-of select="Weight"/></Weight>
			
			
			<!-- 年收入 -->
			<!-- 根据交易渠道的不同金额的显示方式不同 -->
			
			
			<YearSalary>
			<xsl:if  test="Header/EntrustWay = '04' ">
			<xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(AnnualIncome)"/>
			</xsl:if>
			
			<xsl:if  test="Header/EntrustWay = '11' ">
			<xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.yuanToWYuan(AnnualIncome)"/>
			</xsl:if>
			</YearSalary>
			
			
			
			
			
			
			<!-- 证件有效期 --><!-- 8位日期，长期有效为20991231 -->
			<xsl:choose>
				<xsl:when test = "InvalidDate=20991231"><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="InvalidDate"/></IdExpDate></xsl:otherwise>
			</xsl:choose>
			<!-- 婚否 -->
			<MaritalStatus/>
		</Insured>
</xsl:template>



<!--附加险种信息-->
<xsl:template match="App/Req/Addt">
        <xsl:if test="Count!='0'">
	    <xsl:variable name="MainProductCode" select="../Risks/RiskCode"/>
		<Risk>
		    <!-- 险种代码 -->
			<RiskCode><xsl:apply-templates select="RiskCode1"/></RiskCode>
			<!-- 主险险种代码 -->
			<MainRiskCode><xsl:apply-templates select="$MainProductCode"/></MainRiskCode>
			<!-- 保额 -->
			<Amnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Amnt1)"/></Amnt>
			<!-- 保费 -->
			<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Prem1)"/></Prem>
			<!-- 投保份数 -->
			<Mult><xsl:value-of select="Share1"/></Mult>
			<!-- 缴费形式 --><!-- 农行无 -->
			<PayMode>B</PayMode>
			<!-- 缴费频次 -->
			<!--<xsl:choose> 农行借贷险的缴费频次为0-趸交-放开在rule.xsl做校验
			<xsl:when test="$MainProductCode=211901"><PayIntv>0</PayIntv></xsl:when>
			<xsl:otherwise>
			<PayIntv><xsl:apply-templates select="PayIntv"/></PayIntv>
			</xsl:otherwise>
			</xsl:choose>
			-->
			<xsl:choose>
			<xsl:when test="RiskCode1='145201'">
			<PayIntv>0</PayIntv>
			</xsl:when>
			<xsl:otherwise>
			<PayIntv><xsl:apply-templates select="PayType1"/></PayIntv>
			</xsl:otherwise>
			</xsl:choose>
			<xsl:choose>
				<xsl:when test="PayType1=1"><!-- 趸交传1000Y -->
				<!-- 缴费年期年龄标志 -->
				<PayEndYearFlag>Y</PayEndYearFlag>			
				<!-- 缴费年期年龄 -->
				<PayEndYear>1000</PayEndYear>
				</xsl:when>
				<xsl:when test="$MainProductCode=211901"><!-- 趸交传1000Y -->
				<!-- 缴费年期年龄标志 -->
				<PayEndYearFlag>Y</PayEndYearFlag>			
				<!-- 缴费年期年龄 -->
				<PayEndYear>1000</PayEndYear>
				</xsl:when>
				<xsl:otherwise>
				<!-- 缴费年期年龄标志 -->
				<PayEndYearFlag><xsl:apply-templates select="PayDueType1"/></PayEndYearFlag>			
				<!-- 缴费年期年龄 -->
				<PayEndYear><xsl:value-of select="PayDueDate1"/></PayEndYear>
				</xsl:otherwise>
			</xsl:choose>
			
			
			<!-- 保险年期年龄标志 -->
			<InsuYearFlag><xsl:apply-templates select="//App/Req/Risks/InsuDueType"/></InsuYearFlag>
			<!-- 保险年期年龄 -->
			<InsuYear><xsl:value-of select="//App/Req/Risks/InsuDueDate"/></InsuYear>
			<!-- 红利领取方式 -->
			<BonusGetMode></BonusGetMode>
			<!-- 待定 --> <!-- 满期领取金领取方式 -->
			<FullBonusGetMode></FullBonusGetMode>
			<!-- 待定 --> <!-- 领取年龄年期标志 -->
			<GetYearFlag></GetYearFlag>
			<!-- 待定 --> <!-- 领取年龄 -->
			<GetYear></GetYear>
			<!-- 农行不传 -->
			<GetIntv/>
			<GetBankCode/>
			<GetBankAccNo/>
			<GetAccName/>
		</Risk>
		</xsl:if>
</xsl:template>


<!--贷款信息-->
<xsl:template match="App/Req/Loan">
		<Loan>
			<!-- 贷款合同号 -->
			<LoanNo><xsl:value-of select="ContNo"/></LoanNo>
			<!-- 贷款机构 -->
			<LoanBank><xsl:value-of select="LoanBank"/></LoanBank>
			<!-- 贷款日期 -->
			<LoanDate><xsl:value-of select="BegDate"/></LoanDate>
			<!-- 贷款到期日 -->
			<LoanEndDate><xsl:value-of select="EndDate"/></LoanEndDate>
			<!-- 贷款种类 -->
			<LoanType><xsl:apply-templates select="LoanType"/></LoanType>
			<!-- 贷款账号 -->
			<AccNo><xsl:value-of select="AccNo"/></AccNo>
			<!-- 贷款金额 -->
			<xsl:choose><!-- 这里判断choose下，不然空的话在yuanToFen的时候变成0了，数据不原汁原味了 -->
			<xsl:when test = "Prem=''"><LoanPrem/></xsl:when>
			<xsl:otherwise>
			<LoanPrem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Prem)"/></LoanPrem>
			</xsl:otherwise>
			</xsl:choose>
			<!-- 保险起始日 -->
			<InsuDate><xsl:value-of select="/ABCB2I/App/Req/Risks/RiskBeginDate"/></InsuDate>
			<!-- 保险期满日 -->
			<InsuEndDate><xsl:value-of select="/ABCB2I/App/Req/Risks/RiskEndDate"/></InsuEndDate>
		</Loan>
</xsl:template>


<!--健康告知 -->
<xsl:template name="tran_health" match="HealthNotice">
	<xsl:choose>
		<xsl:when test=".=0">N</xsl:when>	<!-- 否 -->
		<xsl:when test=".=1">Y</xsl:when>	<!-- 是 -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- 关系代码 -->
<xsl:template name="tran_relatoinsured" match="RelaToInsured">
	<xsl:choose>
		<xsl:when test=".=00">00</xsl:when> <!-- 本人              -->
		<xsl:when test=".=01">00</xsl:when> <!-- 本人              -->
		<xsl:when test=".=02">02</xsl:when> <!-- 丈夫              -->
		<xsl:when test=".=03">02</xsl:when> <!-- 妻子              -->
		<xsl:when test=".=04">01</xsl:when> <!-- 父亲              -->
		<xsl:when test=".=05">01</xsl:when> <!-- 母亲              -->
		<xsl:when test=".=06">03</xsl:when> <!-- 儿子              -->
		<xsl:when test=".=07">03</xsl:when> <!-- 女儿              -->
		<xsl:when test=".=08">04</xsl:when> <!-- 祖父              -->
		<xsl:when test=".=09">04</xsl:when> <!-- 祖母              -->
		<xsl:when test=".=10">04</xsl:when> <!--孙子               -->
		<xsl:when test=".=11">04</xsl:when> <!--孙女               -->
		<xsl:when test=".=12">04</xsl:when> <!--外祖父             -->
		<xsl:when test=".=13">04</xsl:when> <!--外祖母             -->
		<xsl:when test=".=14">04</xsl:when> <!--外孙               -->
		<xsl:when test=".=15">04</xsl:when> <!--外孙女             -->
		<xsl:when test=".=16">06</xsl:when> <!--哥哥               -->
		<xsl:when test=".=17">06</xsl:when> <!--姐姐               -->
		<xsl:when test=".=18">06</xsl:when> <!--弟弟               -->
		<xsl:when test=".=19">06</xsl:when> <!--妹妹               -->
		<xsl:when test=".=20">06</xsl:when> <!--公公               -->
		<xsl:when test=".=21">06</xsl:when> <!--婆婆               -->
		<xsl:when test=".=22">06</xsl:when> <!--岳父               -->
		<xsl:when test=".=23">06</xsl:when> <!--岳母               -->
		<xsl:when test=".=24">06</xsl:when> <!--儿媳               -->
		<xsl:when test=".=25">06</xsl:when> <!--女婿               -->
		<xsl:when test=".=26">06</xsl:when> <!--其他亲属        -->
		<xsl:when test=".=27">06</xsl:when> <!--同事     -->
		<xsl:when test=".=28">06</xsl:when> <!--朋友     -->
		<xsl:when test=".=29">06</xsl:when> <!--雇主     -->
		<xsl:when test=".=30">06</xsl:when> <!--其他     -->
		<xsl:otherwise>--</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- 性别 -->
<xsl:template name="tran_sex" match="Sex">
	<xsl:choose>
		<xsl:when test=".=0">0</xsl:when>	<!-- 男 -->
		<xsl:when test=".=1">1</xsl:when>	<!-- 女 -->
		<xsl:when test=".=2">2</xsl:when>	<!-- 默认 -->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>


<!-- 证件类型-->
<xsl:template name="tran_idtype" match="IDKind">
	<xsl:choose>
		<xsl:when test=".=110001">0</xsl:when>	<!-- 身份证 -->
		<xsl:when test=".=110002">0</xsl:when>	<!-- 重号居民身份证-->
		<xsl:when test=".=110003">C</xsl:when>	<!-- 临时居民身份证 -->
		<xsl:when test=".=110004">C</xsl:when>	<!-- 重号临时居民身份证 -->
		<xsl:when test=".=110005">4</xsl:when>  <!-- 户口簿 -->
		<xsl:when test=".=110006">4</xsl:when>  <!-- 重号户口簿  -->
		<xsl:when test=".=110007">2</xsl:when>  <!-- 中国人民解放军军人身份证  -->
		<xsl:when test=".=110008">2</xsl:when>  <!-- 重号中国人民解放军军人身份证  -->
		<xsl:when test=".=110009">D</xsl:when>  <!-- 中国人民武装警察身份证件  -->
		<xsl:when test=".=110010">D</xsl:when>  <!-- 重号中国人民武装警察身份证件  -->
		<xsl:when test=".=110011">8</xsl:when>  <!-- 离休干部荣誉证 -->
		<xsl:when test=".=110012">8</xsl:when>  <!-- 重号离休干部荣誉证 -->
		<xsl:when test=".=110013">8</xsl:when>  <!-- 军官退休证 -->
		<xsl:when test=".=110014">8</xsl:when>  <!-- 重号军官退休证 -->
		<xsl:when test=".=110015">8</xsl:when>  <!-- 文职干部退休证 -->
		<xsl:when test=".=110016">8</xsl:when>  <!-- 重号文职干部退休证 -->
		<xsl:when test=".=110017">5</xsl:when>  <!-- 军事院校学员证 -->
		<xsl:when test=".=110018">5</xsl:when>  <!-- 重号军事院校学员证 -->
		<xsl:when test=".=110019">8</xsl:when>  <!-- 港澳居民往来内地通行证 -->
		<xsl:when test=".=110020">8</xsl:when>  <!-- 重号港澳居民往来内地通行证 -->
		<xsl:when test=".=110021">E</xsl:when>  <!-- 台湾居民往来大陆通行证 -->
		<xsl:when test=".=110022">E</xsl:when>  <!-- 重号台湾居民往来大陆通行证 -->
		<xsl:when test=".=110023">1</xsl:when>  <!-- 中华人民共和国护照 -->
		<xsl:when test=".=110024">1</xsl:when>  <!-- 重号中华人民共和国护照 -->
		<xsl:when test=".=110025">1</xsl:when>  <!-- 外国护照 -->
		<xsl:when test=".=110026">1</xsl:when>  <!-- 重号外国护照 -->
		<xsl:when test=".=110027">2</xsl:when>  <!-- 军官证 -->
		<xsl:when test=".=110028">2</xsl:when>  <!-- 重号军官证 -->
		<xsl:when test=".=110029">8</xsl:when>  <!-- 文职干部证 -->
		<xsl:when test=".=110030">8</xsl:when>  <!-- 重号文职干部证 -->
		<xsl:when test=".=110031">D</xsl:when>  <!-- 警官证 -->
		<xsl:when test=".=110032">D</xsl:when>  <!-- 重号警官证 -->
		<xsl:when test=".=110033">A</xsl:when>  <!-- 军人士兵证 -->
		<xsl:when test=".=110034">A</xsl:when>  <!-- 重号军人士兵证 -->
		<xsl:when test=".=110035">A</xsl:when>  <!-- 武警士兵证 -->
		<xsl:when test=".=110036">A</xsl:when>  <!-- 重号武警士兵证 -->
		<xsl:when test=".=119998">8</xsl:when>  <!-- 系统使用的个人证件识别标识 -->
		<xsl:when test=".=119999">8</xsl:when>  <!-- 其他个人证件识别标识 -->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- 险种代码 -->
<xsl:template name="Code" match="RiskCode">
<xsl:choose>
	<xsl:when test=".='011A0100'">011A0100</xsl:when>	<!--  -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- 主险种代码 -->
<xsl:template name="MainRiskCode" match="MainRiskCode">
<xsl:choose>
	<xsl:when test=".='011A0100'">011A0100</xsl:when>	<!--  -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- 缴费方式（频次）   6	年龄   7	终身交费  核心没有   
0	不定期
1	趸交
2	月交
3	季交
4	半年交
5	年交
6	年龄
7	终身交费
-->
<xsl:template name="tran_payintv" match="PayType">
	<xsl:choose>
		<xsl:when test=".=1">0</xsl:when>	<!-- 趸交 -->
		<xsl:when test=".=2">1</xsl:when>	<!-- 月交 -->
		<xsl:when test=".=3">3</xsl:when>	<!-- 季交 -->
		<xsl:when test=".=4">6</xsl:when>	<!-- 半年交 -->
		<xsl:when test=".=5">12</xsl:when>	<!-- 年交 -->
		<xsl:when test=".=0">-1</xsl:when>	<!-- 不定期 -->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<xsl:template name="tran_payendyearflag" match="PayDueType">
	<xsl:choose>
		<xsl:when test=".='1'">A</xsl:when>	<!-- 缴至某确定年龄 -->
		<xsl:when test=".='0'">A</xsl:when>	<!-- 趸缴-->
		<xsl:when test=".='2'">M</xsl:when>	<!-- 月 -->
		<xsl:when test=".='3'">D</xsl:when>	<!-- 日 -->
		<xsl:when test=".='4'">Y</xsl:when>	<!-- 年 -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>	

<xsl:template name="tran_payendyearflag1" match="PayDueType1">
	<xsl:choose>
		<xsl:when test=".='1'">A</xsl:when>	<!-- 缴至某确定年龄 -->
		<xsl:when test=".='0'">A</xsl:when>	<!-- 趸缴-->
		<xsl:when test=".='2'">M</xsl:when>	<!-- 月 -->
		<xsl:when test=".='3'">D</xsl:when>	<!-- 日 -->
		<xsl:when test=".='4'">Y</xsl:when>	<!-- 年 -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>	


<!-- 保险年期年龄类型
0	无关
1	日
2	月
3	季
4	年
5	年龄
6	终身
 -->
<xsl:template name="tran_insuyearflag" match="InsuDueType">
	<xsl:choose>
		<xsl:when test=".=5">A</xsl:when>	<!-- 年龄 -->
		<xsl:when test=".=2">M</xsl:when>	<!-- 月 -->
		<xsl:when test=".=1">D</xsl:when>	<!-- 日 -->
		<xsl:when test=".=4">Y</xsl:when>	<!-- 年 -->
		<xsl:when test=".=6">A</xsl:when>	<!-- 终身 ?待确认，保险年期年龄类型核心保终身对应代码是多少-->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- 领取年龄年期标志
   1 	按年龄
2	按月
3	按日
4	按年
 -->
<xsl:template name="tran_getYearFlag" match="GetYearFlag">
	<xsl:choose>
		<xsl:when test=".=1">A</xsl:when>	<!-- 年龄 -->
		<xsl:when test=".=2">M</xsl:when>	<!-- 月 -->
		<xsl:when test=".=3">D</xsl:when>	<!-- 日 -->
		<xsl:when test=".=4">Y</xsl:when>	<!-- 年 -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- 红利领取方式
 0	直接给付
1	抵交保费
2	累积生息
3	增额交清
 -->
<xsl:template name="tran_bonusgetmode" match="BonusGetMode">
		<xsl:choose>
		<xsl:when test=".=2">1</xsl:when>	<!-- 积累生息 -->
		<xsl:when test=".=1">3</xsl:when>	<!-- 抵交保费 -->
		<xsl:when test=".=0">2</xsl:when>	<!-- 现金领取 -->
		<xsl:otherwise>1</xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- 借贷险贷款种类 -->
<xsl:template name="tran_LoanType" match="LoanType">
	<xsl:choose>
		<xsl:when test=".=00">01</xsl:when>	<!-- 一般商业贷款 -->
		<xsl:when test=".=01">02</xsl:when>	<!-- 组合商业贷款 -->
		<xsl:when test=".=10">03</xsl:when>	<!-- 公积金组合贷款 -->
		<xsl:when test=".=11">04</xsl:when>	<!-- 纯公积金贷款 -->
		<xsl:when test=".=20">05</xsl:when>	<!-- 贴息助学贷款 -->
		<xsl:otherwise>01</xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- 投保人居民类型 -->
<xsl:template name="cust_Source" match="CustSource">
	<xsl:choose>
		<xsl:when test=".=0">1</xsl:when>	<!-- 城镇 -->
		<xsl:when test=".=1">2</xsl:when>	<!-- 农村 -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- 国籍  -->
<xsl:template name="tran_nativeplace" match="nativeplace">
	<xsl:choose>
		<xsl:when test=".=156">CHN</xsl:when> <!--中国                      -->
		<xsl:when test=".=344">HK</xsl:when> <!--中国香港                  -->
		<xsl:when test=".=158">TW</xsl:when> <!--中国台湾                  -->
		<xsl:when test=".=446">MO</xsl:when> <!--中国澳门                  -->
		<xsl:when test=".=392">JAN</xsl:when> <!--日本                      -->
		<xsl:when test=".=840">USA</xsl:when> <!--美国                      -->
		<xsl:when test=".=643">RUS</xsl:when> <!--俄罗斯                    -->
		<xsl:when test=".=826">ENG</xsl:when> <!--英国                      -->
		<xsl:when test=".=250">FRA</xsl:when> <!--法国                      -->
		<xsl:when test=".=276">DEU</xsl:when> <!--德国                      -->
		<xsl:when test=".=410">KOR</xsl:when> <!--韩国                      -->
		<xsl:when test=".=702">SG</xsl:when> <!--新加坡                    -->
		<xsl:when test=".=360">INA</xsl:when> <!--印度尼西亚                -->
		<xsl:when test=".=356">IND</xsl:when> <!--印度                      -->
		<xsl:when test=".=380">ITA</xsl:when> <!--意大利                    -->
		<xsl:when test=".=458">MY</xsl:when> <!--马来西亚                  -->
		<xsl:when test=".=764">THA</xsl:when> <!--泰国                      -->
		<xsl:when test=".=999">OTH</xsl:when> <!--其他国家和地区            -->
		<xsl:otherwise>--</xsl:otherwise>
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>