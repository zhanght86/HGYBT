<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"  exclude-result-prefixes="java">
 	
	<xsl:template match="ABCB2I">
	<TranData><!-- 核心农行保全申请请求报文 -->
		<!--基本信息-->
		  	<Head>
		  		<!-- 交易日期 -->
		  		<TranDate><xsl:value-of select="Header/TransDate"/></TranDate>
		  		<!-- 交易时间-->
				<TranTime><xsl:value-of select="Header/TransTime"/></TranTime>
				<!-- 银行代码 -->
				<BankCode><xsl:value-of select="Head/BankCode"/></BankCode>
				<!-- 地区代码 -->
				<ZoneNo><xsl:value-of select="Header/ProvCode"/></ZoneNo>
				<!-- 银行网点 -->
				<xsl:choose>
					<xsl:when test="Header/EntrustWay='11'"><!-- 柜面 -->
						<NodeNo><xsl:value-of select="Header/ProvCode"/><xsl:value-of select="Header/BranchNo"/></NodeNo>
					</xsl:when>
					<xsl:otherwise><!-- 电子渠道实际网点存在取实际网点，不存在取虚拟网点 -->
						<xsl:if test="Header/ProvCode != '' and Header/BranchNo != ''">
							<NodeNo><xsl:value-of select="Header/ProvCode"/><xsl:value-of select="Header/BranchNo"/></NodeNo>
						</xsl:if>
						<xsl:if test="Header/ProvCode = '' or Header/BranchNo = ''">
							<NodeNo>ABCWEB</NodeNo>
						</xsl:if>
					</xsl:otherwise>
				</xsl:choose>
				<!-- 柜员代码 -->
				<xsl:choose>
					<xsl:when test="Header/EntrustWay = '11'">
						<TellerNo><xsl:value-of select="Header/Tlid"/></TellerNo>
					</xsl:when>
					<xsl:otherwise>
						<TellerNo>0005</TellerNo>
					</xsl:otherwise>
				</xsl:choose>
		        <!-- 交易流水号 -->
				<TranNo><xsl:value-of select="Header/SerialNo"/></TranNo>
				<!-- YBT组织的节点信息 -->
				 <xsl:copy-of select="Head/*"/> 
		  	</Head>
			<Body>
				<!-- 销售渠道 -->
				<SaleChannel><xsl:apply-templates select="Header/EntrustWay"/></SaleChannel>
				<!--保单号 -->
				<ContNo><xsl:value-of select="App/Req/PolicyNo" /></ContNo>
				<!-- 保单印刷号 -->
				<xsl:choose>
					<xsl:when test="Header/EntrustWay ='11'">
						<ContPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/PrintCode)"/></ContPrtNo>
					</xsl:when>
					<xsl:otherwise>
						<ContPrtNo></ContPrtNo>
					</xsl:otherwise>
				</xsl:choose>
				<!-- 保单密码  -->
				<Password><xsl:value-of select="App/Req/PolicyPwd"/></Password>
				<!--申请人姓名  -->
				<AccName><xsl:value-of select="App/Req/ClientName"/></AccName>
				<!--证件类型 -->
				<IDType><xsl:apply-templates select="App/Req/IdKind"/></IDType>
				<!-- 证件号码 -->
				<IDNo><xsl:value-of select="App/Req/IdCode"/></IDNo>
				<!--领款人账（卡）号 -->
				<PayAcc><xsl:value-of select="App/Req/PayAcc"/></PayAcc>
				<!-- 保险费(分) -->
				<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(App/Req/Amt)"/></Prem>
				<!--业务类型: 05投连险转换，06投连险部分领取，07犹豫期撤保，09 满期给付，10退保，11续期缴费 -->
				<BusiType>
						<xsl:call-template name="busitype">
							<xsl:with-param name="BusiType">
								<xsl:value-of select="App/Req/BusiType" />
							</xsl:with-param>
						</xsl:call-template>
				</BusiType>
		</Body>
	</TranData>
	</xsl:template>

	<!-- 委托方式
	01-银行网银渠道
	02-掌上银行渠道
	04-银行自助终端渠道
	11-银行柜台渠道
	20-保险公司渠道
	 -->
	<xsl:template name="tran_salechannel" match="EntrustWay">
		<xsl:choose>
			<xsl:when test=".=01">1</xsl:when><!-- 银行网银渠道 -->
			<xsl:when test=".=02">2</xsl:when><!-- 掌上银行渠道 -->
			<xsl:when test=".=04">8</xsl:when><!-- 银行自助终端渠道 -->
			<xsl:when test=".=11">0</xsl:when><!-- 银行柜台渠道 -->
			<xsl:when test=".=20"></xsl:when><!-- 保险公司渠道 -->
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</xsl:template>	

	<!--  业务类型 -->
	<xsl:template name="busitype" >
		<xsl:param name="BusiType"></xsl:param>
		<xsl:if test="$BusiType = 01">07</xsl:if><!-- 犹撤  -->
		<xsl:if test="$BusiType = 02">09</xsl:if><!-- 满期给付 -->
		<xsl:if test="$BusiType = 03">10</xsl:if><!-- 退保 -->
	</xsl:template>

	<!-- 证件类型-->
	<xsl:template name="tran_idtype" match="IDKind">
		<xsl:choose>
			<xsl:when test=".=110001">0</xsl:when>	<!-- 居民身份证 -->
			<xsl:when test=".=110002">99</xsl:when>	<!-- 重号居民身份证-->
			<xsl:when test=".=110003">0</xsl:when>	<!-- 临时居民身份证 -->
			<xsl:when test=".=110004">99</xsl:when>	<!-- 重号临时居民身份证 -->
			<xsl:when test=".=110005">4</xsl:when>  <!-- 户口簿 -->
			<xsl:when test=".=110006">99</xsl:when>  <!-- 重号户口簿  -->
			<xsl:when test=".=110007">99</xsl:when> <!-- 军人身份证 -->
			<xsl:when test=".=110008">99</xsl:when> <!-- 重号中国人民解放军军人身份证件 -->
			<xsl:when test=".=110009">99</xsl:when> <!-- 武装警察身份证 -->
			<xsl:when test=".=110010">99</xsl:when> <!-- 重号中国人民武装警察身份证件 -->
			<xsl:when test=".=110011">99</xsl:when>  <!-- 离休干部荣誉证 -->
			<xsl:when test=".=110012">99</xsl:when>  <!-- 重号离休干部荣誉证 -->
			<xsl:when test=".=110013">99</xsl:when>  <!-- 军官退休证 -->
			<xsl:when test=".=110014">99</xsl:when>  <!-- 重号军官退休证 -->
			<xsl:when test=".=110015">99</xsl:when>  <!-- 文职干部退休证 -->
			<xsl:when test=".=110016">99</xsl:when>  <!-- 重号文职干部退休证 -->
			<xsl:when test=".=110017">99</xsl:when>  <!-- 军事院校学员证 -->
			<xsl:when test=".=110018">99</xsl:when>  <!-- 重号军事院校学员证 -->
			<xsl:when test=".=110019">F</xsl:when>  <!-- 港澳居民往来内地通行证 -->
			<xsl:when test=".=110020">99</xsl:when>  <!-- 重号港澳居民往来内地通行证 -->
			<xsl:when test=".=110021">F</xsl:when>  <!-- 台湾居民往来大陆通行证 -->
			<xsl:when test=".=110022">99</xsl:when>  <!-- 重号台湾居民往来大陆通行证 -->
			<xsl:when test=".=110023">99</xsl:when>  <!-- 中华人民共和国护照 -->
			<xsl:when test=".=110024">99</xsl:when>  <!-- 重号中华人民共和国护照 -->
			<xsl:when test=".=110025">1</xsl:when>  <!-- 外国护照 -->
			<xsl:when test=".=110026">99</xsl:when>  <!-- 重号外国护照 -->
			<xsl:when test=".=110027">2</xsl:when>  <!-- 军官证 -->
			<xsl:when test=".=110028">99</xsl:when>  <!-- 重号军官证 -->
			<xsl:when test=".=110029">2</xsl:when>  <!-- 文职干部证 -->
			<xsl:when test=".=110030">99</xsl:when>  <!-- 重号文职干部证 -->
			<xsl:when test=".=110031">D</xsl:when>  <!-- 警官证 -->
			<xsl:when test=".=110032">99</xsl:when>  <!-- 重号警官证 -->
			<xsl:when test=".=110033">2</xsl:when>  <!-- 军人士兵证 -->
			<xsl:when test=".=110034">99</xsl:when>  <!-- 重号军人士兵证 -->
			<xsl:when test=".=110035">2</xsl:when>  <!-- 武警士兵证 -->
			<xsl:when test=".=110036">99</xsl:when>  <!-- 重号武警士兵证 -->
			<xsl:when test=".=110037">1</xsl:when>  <!-- 外国人居留证-->
			<xsl:when test=".=110043">99</xsl:when>  <!-- 外国居民身份证 -->
			<xsl:when test=".=110045">99</xsl:when>  <!-- 外交官证 -->
			<xsl:when test=".=110047">99</xsl:when>  <!-- 中华人民共和国旅行证 -->
			<xsl:when test=".=119998">99</xsl:when>  <!-- 系统使用的个人证件识别标识 -->
			<xsl:when test=".=119999">99</xsl:when>  <!-- 其他个人证件识别标识 -->
			<xsl:otherwise>--</xsl:otherwise>  
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>