<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
	<TranData>
	<Head>
		<!-- YBT组织的节点信息 -->
		 <xsl:copy-of select="Head/*"/>
	</Head>
	<Body>
		<xsl:for-each select="Body/Detail">
		 	<!--循环节点 -->
			<Detail>
				<!--交易日期  -->
				<TranDate><xsl:value-of select="TranDate"/></TranDate > 
				<!--试算申请序号-->
				<ApplyNo><xsl:value-of select="ApplyNo"/></ApplyNo >
				<!--投保人信息-->
				<Appnt> 
					<!--投保人姓名-->
					<AppntName><xsl:value-of select="Appnt/AppntName"/></AppntName >			
					<!--投保人证件类型 -->
					<IDType><xsl:apply-templates select="Appnt/IDType"/></IDType > 
					<!--证件号码-->
					<IDNo><xsl:value-of select="Appnt/IDNo"/></IDNo >
				</Appnt>
				<!--险种代码-->
				<RiskCode><xsl:value-of select="RiskCode"/></RiskCode>
				 <!--产品代码 -->
				<ProdCode><xsl:value-of select="ProdCode"/></ProdCode>
				<!--保单号-->
				<ContNo><xsl:value-of select="ContNo"/></ContNo>
				<!--受理日期(承保日期)-->
				<ApplyDate><xsl:value-of select="SignDate"/></ApplyDate>
				<!--投保人与被保人关系  非实时不走银保通，关系不能一对多转换，可以放空-->
				<RelationToInusre>
					<xsl:call-template name="tran_relationtoinusre">
						<xsl:with-param name="tRelationToInusre" select="RelationToInusre"/>
						<xsl:with-param name="tSex" select="Appnt/AppntSex"/>
					</xsl:call-template>
				</RelationToInusre>
				<!--被保人信息-->
				<Insured> 
					<!--被保人姓名-->
					<Name><xsl:value-of select="Insured/Name"/></Name >			
					<!--被保人证件类型 -->
					<IDType><xsl:apply-templates select="Insured/IDType"/></IDType> 
					<!--证件号码-->
					<IDNo><xsl:value-of select="Insured/IDNo"/></IDNo>
				</Insured>
				<!--保费-->
				<Prem><xsl:value-of select="Prem"/></Prem>
				<!--保额-->
				<Amnt><xsl:value-of select="Amnt"/></Amnt>
				<!--缴费账号-->
				<AccNo><xsl:value-of select="AccNo"/></AccNo>
				<!--缴费方式-->
				<PayIntv><xsl:apply-templates select="PayIntv"/></PayIntv >
				<!--缴费期限-->
				<PayEndDate><xsl:value-of select="PayEndDate"/></PayEndDate >
				<!--保单到期日-->
				<ContEndDate><xsl:value-of select="ContEndDate"/></ContEndDate >
				<!--投保份数-->
				<Mult><xsl:value-of select="Mult"/></Mult >
				<!--保单印刷号-->
				<ContPrtNo><xsl:value-of select="ContPrtNo"/></ContPrtNo >
				<!--个性化费率-->
				<SpecialRate><xsl:value-of select="SpecialRate"/></SpecialRate>
				<!--附加险个数-->
				<EtraRiskCnt><xsl:value-of select="EtraRiskCnt"/></EtraRiskCnt >
				<!-- 状态 0:承保1:拒保 -->
				<State><xsl:value-of select="State"/></State>
				<!-- 拒保原因 -->
				<Rtext><xsl:value-of select="Rtext"/></Rtext>
			</Detail>
		</xsl:for-each>
		</Body>
	</TranData>
	</xsl:template>
	
	<!-- 证件类型-->
	<xsl:template name="tran_idtype" match="IDType">
		<xsl:choose>
			<xsl:when test=".=0">110001</xsl:when>	<!-- 身份证 -->
			<xsl:when test=".=1">110023</xsl:when>	<!-- 护照 -->
			<xsl:when test=".=2">110027</xsl:when>	<!-- (军人证)军官证 -->
			<xsl:when test=".=4">110005</xsl:when>	<!-- 户口本 -->
			<xsl:when test=".=7"></xsl:when>	<!-- 出生证 -->
			<xsl:when test=".=F">110019</xsl:when>	<!-- 港、澳、台通行证 -->
			<xsl:otherwise>119999</xsl:otherwise>   <!-- 其他个人证件识别标识 -->
		</xsl:choose>
	</xsl:template>	
	
	<!-- 投保人与被保人关系 -->
	<xsl:template name="tran_relationtoinusre" match="RelationToInusre">
		<xsl:param name="tRelationToInusre"/>
		<xsl:param name="tSex"/>
		<xsl:choose>
			<xsl:when test="$tRelationToInusre=00">01</xsl:when><!-- 本人 -->
			<xsl:when test="$tRelationToInusre=01 and $tSex=0">04</xsl:when><!-- 父亲 -->
			<xsl:when test="$tRelationToInusre=01 and $tSex=1">05</xsl:when><!-- 母亲 -->
			<xsl:when test="$tRelationToInusre=02 and $tSex=0">02</xsl:when><!-- 丈夫 -->
			<xsl:when test="$tRelationToInusre=02 and $tSex=1">03</xsl:when><!-- 妻子 -->
			<xsl:when test="$tRelationToInusre=03 and $tSex=0">06</xsl:when><!-- 儿子 -->
			<xsl:when test="$tRelationToInusre=03 and $tSex=1">07</xsl:when><!-- 女儿 -->
			<xsl:when test="$tRelationToInusre=04"></xsl:when><!-- 祖孙 -->
			<xsl:when test="$tRelationToInusre=05"></xsl:when><!-- 法定监护人 -->
			<xsl:when test="$tRelationToInusre=06">30</xsl:when><!-- 其他 -->
			<xsl:when test="$tRelationToInusre=09">29</xsl:when><!-- 雇佣关系 -->
		</xsl:choose>
	</xsl:template>

	<!-- 缴费方式 -->
	<xsl:template name="tran_payintv" match="PayIntv">
		<xsl:choose>
			<xsl:when test=".=0">1</xsl:when>	<!-- 趸交 -->
			<xsl:when test=".=1">2</xsl:when>	<!-- 月交 -->
			<xsl:when test=".=3">3</xsl:when>	<!-- 季交 -->
			<xsl:when test=".=6">4</xsl:when>	<!-- 半年交 -->
			<xsl:when test=".=12">5</xsl:when>	<!-- 年交 -->
			<xsl:when test=".=-1">0</xsl:when>	<!-- 不定期 -->
		</xsl:choose>
	</xsl:template>
	
</xsl:stylesheet>
