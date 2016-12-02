<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
	
	<TranData>
	<Head>
		<!-- YBT组织的节点信息 -->
		 <xsl:copy-of select="Head/*"/> <!-- -->
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
				<!--受理日期-->
				<ApplyDate><xsl:value-of select="ApplyDate"/></ApplyDate>
				<!--投保人与被保人关系  非实时不走银保通，关系不能一对多转换，放空了，无奈-->
				<RelationToInusre></RelationToInusre>
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
			</Detail>
		</xsl:for-each>
		</Body>
	</TranData>
		
	</xsl:template>
	
<!-- 证件类型-->
<xsl:template name="tran_idtype" match="IDType">
	<xsl:choose>
		<xsl:when test=".=0">110001</xsl:when>	<!-- 身份证 -->
																		<!-- 重号居民身份证-->
		<xsl:when test=".=C">110003</xsl:when>	<!-- 临时居民身份证 -->
																		<!-- 重号临时居民身份证 -->
		<xsl:when test=".=4">110005</xsl:when>   <!-- 户口簿 -->
																		<!-- 重号户口簿  -->
		<xsl:when test=".=2">110007</xsl:when>   <!-- 中国人民解放军军人身份证  -->
																	    <!-- 重号中国人民解放军军人身份证  -->
		<xsl:when test=".=D">110009</xsl:when>  <!-- 中国人民武装警察身份证件  -->
																		<!-- 重号中国人民武装警察身份证件  -->
																		<!-- 离休干部荣誉证 -->
																		<!-- 重号离休干部荣誉证 -->
		<xsl:when test=".=8">110013</xsl:when>   <!-- 军官退休证 -->
																	   <!-- 重号军官退休证 -->
																		<!-- 文职干部退休证 -->
																		<!-- 重号文职干部退休证 -->
		<xsl:when test=".=5">110017</xsl:when>   <!-- 军事院校学员证 -->
																	    <!-- 重号军事院校学员证 -->
																	    <!-- 港澳居民往来内地通行证 -->
																	    <!-- 重号港澳居民往来内地通行证 -->
		<xsl:when test=".=E">110021</xsl:when>   <!-- 台湾居民往来大陆通行证 -->
																	   <!-- 重号台湾居民往来大陆通行证 -->
		<xsl:when test=".=1">110023</xsl:when>   <!-- 中华人民共和国护照 -->
																	   <!-- 重号中华人民共和国护照 -->
																	   <!-- 外国护照 -->
																	   <!-- 重号外国护照 -->
		<xsl:when test=".=2">110027</xsl:when>   <!-- 军官证 -->
																	   <!-- 重号军官证 -->
																	   <!-- 文职干部证 -->
																	   <!-- 重号文职干部证 -->
																	   <!-- 警官证 -->
																		<!-- 重号警官证 -->
																	   <!-- 军人士兵证 -->
																		<!-- 重号军人士兵证 -->
		<xsl:when test=".=A">110035</xsl:when>  <!-- 武警士兵证 -->
																	   <!-- 重号武警士兵证 -->
																	  <!-- 系统使用的个人证件识别标识 -->
																	  <!-- 其他个人证件识别标识 -->
		<xsl:otherwise>119999</xsl:otherwise>   <!-- 其他个人证件识别标识 -->
	</xsl:choose>
</xsl:template>	

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
