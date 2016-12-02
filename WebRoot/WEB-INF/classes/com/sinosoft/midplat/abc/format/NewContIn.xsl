<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">

 
<xsl:template match="Req">
	  <TranData>
	     <Head>
	        <!-- 银行交易流水号 -->
			<TranNo><xsl:value-of select="TransrNo"/></TranNo>
			<!-- 地区代码 -->
			<ZoneNo><xsl:value-of select="ZoneNo"/></ZoneNo>
			<!-- 网点代码 -->
			<NodeNo>
			<xsl:value-of select="ZoneNo"/>
			<xsl:value-of select="BrNo"/>
			</NodeNo>
	  		<!-- 银行交易日期 -->
	  		<TranDate><xsl:value-of select="BankDate"/></TranDate>
	  		<!-- 交易时间 农行不传交易时间 取系统当前时间 -->
			<TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur6Time()"/></TranTime>
			<!-- 银行交易时间 -->
			<!-- <TranTime><xsl:value-of select="BankTime"/></TranTime> -->
			<TranCom><xsl:value-of select="BankCode"></xsl:value-of></TranCom>
			<!-- 柜员代码 -->
			<TellerNo><xsl:value-of select="TellerNo"/></TellerNo>
			<!-- 银行代码 -->
			<BankCode>0102</BankCode>
			
			<!-- YBT组织的节点信息 -->
			 <xsl:copy-of select="Head/*"/> <!-- -->
	  	</Head>
	  	
		<!--投保信息-->
		<Body>
			<xsl:variable name ="sAccNo" select ="Base/AccNo"/>
			<!-- 投保单号 -->
			<ProposalPrtNo><xsl:value-of select="Base/ProposalContNo"/></ProposalPrtNo>
			<!-- 保单印刷号 -->
			<ContPrtNo><xsl:value-of select="Base/PrtNo"/></ContPrtNo>
			<!-- 投保日期 -->
			<PolApplyDate><xsl:value-of select="Base/PolApplyDate"/></PolApplyDate>
			<!-- 账户姓名 -->
			<!--
			<AccName><xsl:value-of select="Appl/Name"/></AccName>
			-->
			<!-- 交费帐号姓名 续期缴费账号姓名ReAccName，首期缴费账号姓名AccName 和农行的联调人员沟通过 -->
			<AccName><xsl:value-of select="Base/AccName"/></AccName>
			<!-- 账户银行代码 -->
			<AccBankCode/>
			<!-- 交费帐号 续期缴费账号 BankAccNo，首期缴费账号AccNo 和农行的联调人员沟通过20130402 -->
			<AccNo><xsl:value-of select="Base/AccNo"/></AccNo>
			<!--
			<xsl:if test="$sAccNo = ''">
			<AccNo>0000</AccNo>
			</xsl:if>
			<xsl:if test="$sAccNo != ''">
			<AccNo><xsl:value-of select="Base/AccNo"/></AccNo>
			</xsl:if>
			-->
			<!--缴费形式 和朱诚沟通，从银保通出单的中韩这边缴费形式都置为B -工行对应处的说明-->
			<PayMode>B</PayMode>
			<!-- 保单传送方式 -->
			<GetPolMode>1</GetPolMode><!-- 默认为1，所以写死成1 -->
			<!-- 职业告知 -->
			<JobNotice/>
			<!-- 健康告知 -->
			<HealthNotice><xsl:apply-templates select="Risks/Risk[Code=MainRiskCode]/HealthFlag"/></HealthNotice>
			<!-- 投保人 -->
			<xsl:apply-templates select="Appl"/>
			<!-- 被保人 -->
			<xsl:apply-templates select="Insu"/>
			<!-- 受益人 -->
			<xsl:apply-templates select="Bnfs"/>
			<!-- 险种信息 -->
			<xsl:apply-templates select="Risks"/>
			<!-- 贷款信息 -->
			<xsl:apply-templates select="Loan"/>
		</Body>
	</TranData>
</xsl:template>	
	
<!--投保人信息-->
<xsl:template match="Appl">
		<Appnt>
			<!-- 姓名 -->
			<Name><xsl:value-of select="Name"/></Name>
			<!-- 性别 -->
			<Sex><xsl:apply-templates select="Sex"/></Sex>
			<!-- 生日 -->
			<Birthday><xsl:value-of select="Birthday"/></Birthday>
			<!-- 证件类型 -->
			<IDType><xsl:apply-templates select="IDType"/></IDType>
			<!-- 证件号码 -->
			<IDNo><xsl:value-of select="IDNo"/></IDNo>
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
			<YearSalary/>
			<DenType><xsl:value-of select="PbDenType" /></DenType>
			<!-- 证件有效期 --><!-- 8位日期，长期有效为20991231 -->
			<xsl:choose>
				<xsl:when test = "ValidYear=20991231"><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="ValidYear"/></IdExpDate></xsl:otherwise>
			</xsl:choose>
			<!-- 婚否 -->
			<MaritalStatus/>
				<!-- 投保人与背保人关系 -->
		<RelaToInsured><xsl:apply-templates select="RelaToInsured"/></RelaToInsured>
		</Appnt>
</xsl:template>
		
<!--被保人信息-->
<xsl:template match="Insu">
		<Insured>
			<!-- 姓名 -->
			<Name><xsl:value-of select="Name"/></Name>
			<!-- 性别 -->
			<Sex><xsl:apply-templates select="Sex"/></Sex>
			<!-- 生日 -->
			<Birthday><xsl:value-of select="Birthday"/></Birthday>
			<!-- 证件类型 -->
			<IDType><xsl:apply-templates select="IDType"/></IDType>
			<!-- 证件号码 -->
			<IDNo><xsl:value-of select="IDNo"/></IDNo>
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
			<YearSalary/>
			<!-- 证件有效期 --><!-- 8位日期，长期有效为20991231 -->
			<xsl:choose>
				<xsl:when test = "ValidYear=20991231"><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="ValidYear"/></IdExpDate></xsl:otherwise>
			</xsl:choose>
			<!-- 婚否 -->
			<MaritalStatus/>
		</Insured>
</xsl:template>

<!--受益人-->
<xsl:template match="Bnfs">
	<xsl:variable name ="count" select ="Count"/>
	<xsl:choose>
	<xsl:when test="$count!=0">
		<xsl:for-each select="Bnf">
			<Bnf>
			<BeneficType>N</BeneficType>
				<!-- 受益人类型 农行和核心的一样   0-生存受益人，1-身故受益人-->
				<Type><xsl:value-of select="Type"/></Type>
				<!-- 收益顺序 -->
				<Grade><xsl:value-of select="BnfGrade"/></Grade>
				<!-- 姓名 -->
				<Name><xsl:value-of select="Name"/></Name>
				<!-- 性别 -->
				<Sex><xsl:apply-templates select="Sex"/></Sex>
				<!-- 生日 -->
				<Birthday><xsl:value-of select="Birthday"/></Birthday>
				<!-- 证件类型 -->
				<IDType><xsl:apply-templates select="IDType"/></IDType>
				<!-- 证件号码 -->
				<IDNo><xsl:value-of select="IDNo"/></IDNo>
				<!-- 证件有效期 --><!-- 8位日期，长期有效为20991231 -->
				<xsl:choose>
					<xsl:when test = "ValidYear=20991231"><IdExpDate>99990101</IdExpDate></xsl:when>
					<xsl:otherwise><IdExpDate><xsl:value-of select="ValidYear"/></IdExpDate></xsl:otherwise>
				</xsl:choose>
				<!--投保人与背保人关系   -->
				<RelaToInsured><xsl:apply-templates select="RelationToInsured"/></RelaToInsured>
				<!-- 收益比例 -->
				<Lot><xsl:value-of select="BnfLot"/></Lot>
			</Bnf>
		</xsl:for-each>
	</xsl:when>
	<xsl:otherwise>
	</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!--险种信息-->
<xsl:template match="Risks">
	<xsl:variable name="MainProductCode" select="Risk[Code = MainRiskCode]/Code"/>
	<xsl:for-each select="Risk[Code = MainRiskCode]">
		<Risk>
		    <!-- 险种代码 -->
			<RiskCode><xsl:apply-templates select="Code"/></RiskCode>
			<!-- 主险险种代码 -->
			<MainRiskCode><xsl:apply-templates select="MainRiskCode"/></MainRiskCode>
			<!-- 保额 -->
			<Amnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Amnt)"/></Amnt>
			<!-- 保费 -->
			<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Prem)"/></Prem>
			<!-- 投保份数 -->
			<Mult><xsl:value-of select="Mult"/></Mult>
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
			
			<PayIntv><xsl:apply-templates select="PayIntv"/></PayIntv>
			
			<xsl:choose>
				<xsl:when test="PayIntv=1"><!-- 趸交传1000Y -->
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
				<PayEndYearFlag><xsl:apply-templates select="PayEndYearFlag"/></PayEndYearFlag>			
				<!-- 缴费年期年龄 -->
				<PayEndYear><xsl:value-of select="PayEndYear"/></PayEndYear>
				</xsl:otherwise>
			</xsl:choose>
			
			<!-- 保险年期年龄标志 -->
			<InsuYearFlag><xsl:apply-templates select="InsuYearFlag"/></InsuYearFlag>
			<!-- 保险年期年龄 -->
			<InsuYear><xsl:value-of select="InsuYear"/></InsuYear>
			<!-- 红利领取方式 -->
			<BonusGetMode><xsl:apply-templates select="BonusGetMode"/></BonusGetMode>
			<!-- 待定 --> <!-- 满期领取金领取方式 -->
			<FullBonusGetMode><xsl:apply-templates select="FullBonusGetMode"/></FullBonusGetMode>
			<!-- 待定 --> <!-- 领取年龄年期标志 -->
			<GetYearFlag><xsl:apply-templates select="GetYearFlag"/></GetYearFlag>
			<!-- 待定 --> <!-- 领取年龄 -->
			<GetYear><xsl:value-of select="GetYear"/></GetYear>
			<!-- 农行不传 -->
			<GetIntv/>
			<GetBankCode/>
			<GetBankAccNo/>
			<GetAccName/>
		</Risk>
	</xsl:for-each>
	<xsl:for-each select="Risk[Code != MainRiskCode]">
		<Risk>
		    <!-- 险种代码 -->
			<RiskCode><xsl:apply-templates select="Code"/></RiskCode>
			<!-- 主险险种代码 -->
			<MainRiskCode><xsl:apply-templates select="MainRiskCode"/></MainRiskCode>
			<!-- 保额 -->
			<Amnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Amnt)"/></Amnt>
			<!-- 保费 -->
			<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Prem)"/></Prem>
			<!-- 投保份数 -->
			<Mult><xsl:value-of select="Mult"/></Mult>
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
			
			<PayIntv><xsl:apply-templates select="PayIntv"/></PayIntv>
			<xsl:choose>
				<xsl:when test="PayIntv=1"><!-- 趸交传1000Y -->
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
				<PayEndYearFlag><xsl:apply-templates select="PayEndYearFlag"/></PayEndYearFlag>			
				<!-- 缴费年期年龄 -->
				<PayEndYear><xsl:value-of select="PayEndYear"/></PayEndYear>
				</xsl:otherwise>
			</xsl:choose>
			
			
			<!-- 保险年期年龄标志 -->
			<InsuYearFlag><xsl:apply-templates select="InsuYearFlag"/></InsuYearFlag>
			<!-- 保险年期年龄 -->
			<InsuYear><xsl:value-of select="InsuYear"/></InsuYear>
			<!-- 红利领取方式 -->
			<BonusGetMode><xsl:apply-templates select="BonusGetMode"/></BonusGetMode>
			<!-- 待定 --> <!-- 满期领取金领取方式 -->
			<FullBonusGetMode><xsl:apply-templates select="FullBonusGetMode"/></FullBonusGetMode>
			<!-- 待定 --> <!-- 领取年龄年期标志 -->
			<GetYearFlag><xsl:apply-templates select="GetYearFlag"/></GetYearFlag>
			<!-- 待定 --> <!-- 领取年龄 -->
			<GetYear><xsl:value-of select="GetYear"/></GetYear>
			<!-- 农行不传 -->
			<GetIntv/>
			<GetBankCode/>
			<GetBankAccNo/>
			<GetAccName/>
		</Risk>
	</xsl:for-each>
</xsl:template>


<!--贷款信息-->
<xsl:template match="Loan">
		<Loan>
			<!-- 贷款合同号 -->
			<LoanNo><xsl:value-of select="LoanNo"/></LoanNo>
			<!-- 贷款机构 -->
			<LoanBank><xsl:value-of select="LoanBank"/></LoanBank>
			<!-- 贷款日期 -->
			<LoanDate><xsl:value-of select="LoanDate"/></LoanDate>
			<!-- 贷款到期日 -->
			<LoanEndDate><xsl:value-of select="LoanEndDate"/></LoanEndDate>
			<!-- 贷款种类 -->
			<LoanType><xsl:apply-templates select="LoanType"/></LoanType>
			<!-- 贷款账号 -->
			<AccNo><xsl:value-of select="AccNo"/></AccNo>
			<!-- 贷款金额 -->
			<xsl:choose><!-- 这里判断choose下，不然空的话在yuanToFen的时候变成0了，数据不原汁原味了 -->
			<xsl:when test = "LoanPrem=''"><LoanPrem/></xsl:when>
			<xsl:otherwise>
			<LoanPrem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(LoanPrem)"/></LoanPrem>
			</xsl:otherwise>
			</xsl:choose>
			<!-- 保险起始日 -->
			<InsuDate><xsl:value-of select="InsuDate"/></InsuDate>
			<!-- 保险期满日 -->
			<InsuEndDate><xsl:value-of select="InsuEndDate"/></InsuEndDate>
		</Loan>
</xsl:template>


<!--健康情况 -->
<xsl:template name="tran_health" match="HealthFlag">
	<xsl:choose>
		<xsl:when test=".=0">N</xsl:when>	<!-- 否 -->
		<xsl:when test=".=1">Y</xsl:when>	<!-- 是 -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<xsl:template name="RelationToInsured" match="RelationToInsured"> 
 <xsl:choose>
	<xsl:when test=".=1">00</xsl:when> <!-- 本人    -->
		<xsl:when test=".=2">02</xsl:when> <!-- 丈夫    -->
		<xsl:when test=".=3">02</xsl:when> <!-- 妻子    -->
		<xsl:when test=".=4">01</xsl:when> <!-- 父亲    -->
		<xsl:when test=".=5">01</xsl:when> <!-- 母亲    -->
		<xsl:when test=".=6">03</xsl:when> <!-- 儿子    -->
		<xsl:when test=".=7">03</xsl:when> <!-- 女儿    -->
		<xsl:when test=".=8">04</xsl:when> <!-- 祖父    -->
		<xsl:when test=".=9">04</xsl:when> <!-- 祖母    -->
		<xsl:when test=".=10">04</xsl:when> <!--孙子     -->
		<xsl:when test=".=11">04</xsl:when> <!--孙女     -->
		<xsl:when test=".=12">04</xsl:when> <!--外祖父   -->
		<xsl:when test=".=13">04</xsl:when> <!--外祖母   -->
		<xsl:when test=".=14">04</xsl:when> <!--外孙     -->
		<xsl:when test=".=15">04</xsl:when> <!--外孙女   -->
		<xsl:when test=".=16">06</xsl:when> <!--哥哥     -->
		<xsl:when test=".=17">06</xsl:when> <!--姐姐     -->
		<xsl:when test=".=18">06</xsl:when> <!--弟弟     -->
		<xsl:when test=".=19">06</xsl:when> <!--妹妹     -->
		<xsl:when test=".=20">06</xsl:when> <!--公公     -->
		<xsl:when test=".=21">06</xsl:when> <!--婆婆     -->
		<xsl:when test=".=22">06</xsl:when> <!--岳父     -->
		<xsl:when test=".=23">06</xsl:when> <!--岳母     -->
		<xsl:when test=".=24">06</xsl:when> <!--儿媳     -->
		<xsl:when test=".=25">06</xsl:when> <!--女婿     -->
		<xsl:when test=".=26">06</xsl:when> <!--其他亲属 -->
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
<xsl:template name="tran_idtype" match="IDType">
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
<xsl:template name="Code" match="Code">
<xsl:choose>
	<xsl:when test=".=231201">231201</xsl:when>		<!-- 中韩智赢财富两全保险（分红型）A款 -->
	<xsl:when test=".=231202">231202</xsl:when>		<!-- 中韩智赢财富两全保险（分红型）B款 -->
	<xsl:when test=".=231203">231203</xsl:when> 	<!-- 中韩卓越财富两全保险（分红型） -->
	<xsl:when test=".=211901">211901</xsl:when>  	<!-- 中韩安赢借款人意外伤害保险 -->
	<xsl:when test=".=221201">221201</xsl:when>  	<!-- 中韩保驾护航两全保险A款 -->
	<xsl:when test=".=231204">231204</xsl:when>		<!-- 中韩智赢财富两全保险（分红型）C款 -->
	<xsl:when test=".=211902">211902</xsl:when>  	<!-- 中韩安赢借款人意外伤害保险 A款-->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- 主险种代码 -->
<xsl:template name="MainRiskCode" match="MainRiskCode">
<xsl:choose>
	<xsl:when test=".=231201">231201</xsl:when>		<!-- 中韩智赢财富两全保险（分红型）A款 -->
	<xsl:when test=".=231202">231202</xsl:when>		<!-- 中韩智赢财富两全保险（分红型）B款 -->
	<xsl:when test=".=231203">231203</xsl:when> 	<!-- 中韩卓越财富两全保险（分红型） -->
	<xsl:when test=".=211901">211901</xsl:when> 	<!-- 中韩安赢借款人意外伤害保险 -->
	<xsl:when test=".=221201">221201</xsl:when>  	<!-- 中韩保驾护航两全保险A款 -->
	<xsl:when test=".=231204">231204</xsl:when>		<!-- 中韩智赢财富两全保险（分红型）C款 -->
	<xsl:when test=".=211902">211902</xsl:when>  	<!-- 中韩安赢借款人意外伤害保险 A款-->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>


<!-- 缴费方式（频次） -->
<xsl:template name="tran_payintv" match="PayIntv">
	<xsl:choose>
		<xsl:when test=".=1">0</xsl:when>	<!-- 趸交 -->
		<xsl:when test=".=2">1</xsl:when>	<!-- 月交 -->
		<xsl:when test=".=3">3</xsl:when>	<!-- 季交 -->
		<xsl:when test=".=4">6</xsl:when>	<!-- 半年交 -->
		<xsl:when test=".=5">12</xsl:when>	<!-- 年交 -->
		<xsl:when test=".=6">-1</xsl:when>	<!-- 不定期 -->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- 缴费年期年龄类型 -->
<xsl:template name="tran_payendyearflag" match="PayEndYearFlag">
	<xsl:choose>
		<xsl:when test=".='1'">A</xsl:when>	<!-- 缴至某确定年龄 -->
		<xsl:when test=".='0'">A</xsl:when>	<!-- 趸缴-->
		<xsl:when test=".='2'">M</xsl:when>	<!-- 月 -->
		<xsl:when test=".='3'">D</xsl:when>	<!-- 日 -->
		<xsl:when test=".='4'">Y</xsl:when>	<!-- 年 -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>	

<!-- 保险年期年龄类型 -->
<xsl:template name="tran_insuyearflag" match="InsuYearFlag">
	<xsl:choose>
		<xsl:when test=".=1">A</xsl:when>	<!-- 年龄 -->
		<xsl:when test=".=2">M</xsl:when>	<!-- 月 -->
		<xsl:when test=".=3">D</xsl:when>	<!-- 日 -->
		<xsl:when test=".=4">Y</xsl:when>	<!-- 年 -->
		<xsl:when test=".=5">A</xsl:when>	<!-- 终身 ?待确认，保险年期年龄类型核心保终身对应代码是多少-->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- 领取年龄年期标志 -->
<xsl:template name="tran_getYearFlag" match="GetYearFlag">
	<xsl:choose>
		<xsl:when test=".=1">A</xsl:when>	<!-- 年龄 -->
		<xsl:when test=".=2">M</xsl:when>	<!-- 月 -->
		<xsl:when test=".=3">D</xsl:when>	<!-- 日 -->
		<xsl:when test=".=4">Y</xsl:when>	<!-- 年 -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- 红利领取方式 -->
<xsl:template name="tran_bonusgetmode" match="BonusGetMode">
	<xsl:choose>
		<xsl:when test=".=0"></xsl:when>	<!-- 无红利 -->
		<xsl:when test=".=1">1</xsl:when>	<!-- 积累生息 -->
		<xsl:when test=".=2">2</xsl:when>	<!-- 领取现金 -->
		<xsl:when test=".=3">3</xsl:when>	<!-- 抵交保费 -->
		<xsl:when test=".=4">4</xsl:when>	<!-- 其他 -->
		<xsl:when test=".=5">5</xsl:when>	<!-- 增额交清 -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>


<!-- 关系代码 -->
<xsl:template name="tran_relatoinsured" match="RelaToInsured">
	<xsl:choose>
		<xsl:when test=".=1">00</xsl:when> <!-- 本人    -->
		<xsl:when test=".=2">02</xsl:when> <!-- 丈夫    -->
		<xsl:when test=".=3">02</xsl:when> <!-- 妻子    -->
		<xsl:when test=".=4">01</xsl:when> <!-- 父亲    -->
		<xsl:when test=".=5">01</xsl:when> <!-- 母亲    -->
		<xsl:when test=".=6">03</xsl:when> <!-- 儿子    -->
		<xsl:when test=".=7">03</xsl:when> <!-- 女儿    -->
		<xsl:when test=".=8">04</xsl:when> <!-- 祖父    -->
		<xsl:when test=".=9">04</xsl:when> <!-- 祖母    -->
		<xsl:when test=".=10">04</xsl:when> <!--孙子     -->
		<xsl:when test=".=11">04</xsl:when> <!--孙女     -->
		<xsl:when test=".=12">04</xsl:when> <!--外祖父   -->
		<xsl:when test=".=13">04</xsl:when> <!--外祖母   -->
		<xsl:when test=".=14">04</xsl:when> <!--外孙     -->
		<xsl:when test=".=15">04</xsl:when> <!--外孙女   -->
		<xsl:when test=".=16">06</xsl:when> <!--哥哥     -->
		<xsl:when test=".=17">06</xsl:when> <!--姐姐     -->
		<xsl:when test=".=18">06</xsl:when> <!--弟弟     -->
		<xsl:when test=".=19">06</xsl:when> <!--妹妹     -->
		<xsl:when test=".=20">06</xsl:when> <!--公公     -->
		<xsl:when test=".=21">06</xsl:when> <!--婆婆     -->
		<xsl:when test=".=22">06</xsl:when> <!--岳父     -->
		<xsl:when test=".=23">06</xsl:when> <!--岳母     -->
		<xsl:when test=".=24">06</xsl:when> <!--儿媳     -->
		<xsl:when test=".=25">06</xsl:when> <!--女婿     -->
		<xsl:when test=".=26">06</xsl:when> <!--其他亲属 -->
		<xsl:when test=".=27">06</xsl:when> <!--同事     -->
		<xsl:when test=".=28">06</xsl:when> <!--朋友     -->
		<xsl:when test=".=29">06</xsl:when> <!--雇主     -->
		<xsl:when test=".=30">06</xsl:when> <!--其他     -->
		<xsl:otherwise>--</xsl:otherwise>
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
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>