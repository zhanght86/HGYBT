<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">

 
<xsl:template match="ABCB2I">
	  <TranData>
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
			<TellerNo><xsl:value-of select="Header/Tlid"/></TellerNo>
			<!-- 银行代码 -->
			<BankCode>0102</BankCode>
			
			<!-- YBT组织的节点信息 -->
			 <xsl:copy-of select="Head/*"/> <!-- -->
	  	</Head>
	  	
		<!--投保信息-->
		<Body>
			<!-- 投保单号 -->
			<ProposalPrtNo><xsl:value-of select="App/Req/PolicyApplyNo"/></ProposalPrtNo>

			<!-- 交费帐号 续期缴费账号 BankAccNo，首期缴费账号AccNo 和农行的联调人员沟通过20130402 -->
			<AccNo><xsl:value-of select="App/Req/AccNo"/></AccNo>
			
			<!-- 投保人 -->
			<xsl:apply-templates select="App/Req/Appl"/>

			<!-- 主险种信息 -->
		    <Risk>
		    
			    <!-- 险种代码 -->
				<RiskCode><xsl:apply-templates select="App/Req/RiskCode"/></RiskCode>
				<!-- 保费 -->
				<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(App/Req/Prem)"/></Prem>
			
			</Risk>
			<!-- 贷款信息 -->
			<xsl:apply-templates select="App/Req/Loan"/>
		</Body>
	</TranData>
</xsl:template>	
<xsl:template match="App/Req/Appl">
		<Appnt>
			<!-- 姓名 -->
			<Name><xsl:value-of select="Name"/></Name>
			<!-- 证件类型 -->
			<IDType><xsl:apply-templates select="IDKind"/></IDType>
			<!-- 证件号码 -->
			<IDNo><xsl:value-of select="IDCode"/></IDNo>
			<!-- 地址 -->
			<Address><xsl:value-of select="Address"/></Address>
			<!-- 邮编 -->
			<ZipCode><xsl:value-of select="ZipCode"/></ZipCode>
			<!-- 家庭电话 -->
			<Phone><xsl:value-of select="Phone"/></Phone>
			<!-- 手机号码 -->
			<Mobile><xsl:value-of select="CellPhone"/></Mobile>
			<!-- 投保人居民类型 -->
			 <DenType><xsl:apply-templates select="CustSource" /></DenType>
		</Appnt>
</xsl:template>
		

<!--贷款信息-->
<xsl:template match="App/Req">
		<Loan>
			<!-- 贷款合同号 -->
			<LoanNo><xsl:value-of select="LoanContact"/></LoanNo>
		</Loan>
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
	<xsl:when test=".=231201">231201</xsl:when>	<!-- 中韩智赢财富两全保险（分红型）A款 -->
	<xsl:when test=".=231202">231202</xsl:when>	<!-- 中韩智赢财富两全保险（分红型）B款 -->
	<xsl:when test=".=231203">231203</xsl:when> 	<!-- 中韩卓越财富两全保险（分红型） -->
	<xsl:when test=".=211901">211901</xsl:when>  	<!-- 中韩安赢借款人意外伤害保险 -->
	<xsl:when test=".=221201">221201</xsl:when>  	<!-- 中韩保驾护航两全保险A款 -->
	<xsl:when test=".=231204">231204</xsl:when>	<!-- 中韩智赢财富两全保险（分红型）C款 -->
	<xsl:when test=".=211902">211902</xsl:when>  	<!-- 中韩安赢借款人意外伤害保险 A款-->
	<xsl:otherwise>--</xsl:otherwise>
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


</xsl:stylesheet>