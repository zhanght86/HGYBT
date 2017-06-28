<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
	<TranData>
	<Head>
		<!-- YBT��֯�Ľڵ���Ϣ -->
		 <xsl:copy-of select="Head/*"/>
	</Head>
	<Body>
		<xsl:for-each select="Body/Detail">
		 	<!--ѭ���ڵ� -->
			<Detail>
				<!--��������  -->
				<TranDate><xsl:value-of select="TranDate"/></TranDate > 
				<!--�����������-->
				<ApplyNo><xsl:value-of select="ApplyNo"/></ApplyNo >
				<!--Ͷ������Ϣ-->
				<Appnt> 
					<!--Ͷ��������-->
					<AppntName><xsl:value-of select="Appnt/AppntName"/></AppntName >			
					<!--Ͷ����֤������ -->
					<IDType><xsl:apply-templates select="Appnt/IDType"/></IDType > 
					<!--֤������-->
					<IDNo><xsl:value-of select="Appnt/IDNo"/></IDNo >
				</Appnt>
				<!--���ִ���-->
				<RiskCode><xsl:value-of select="RiskCode"/></RiskCode>
				 <!--��Ʒ���� -->
				<ProdCode><xsl:value-of select="ProdCode"/></ProdCode>
				<!--������-->
				<ContNo><xsl:value-of select="ContNo"/></ContNo>
				<!--��������(�б�����)-->
				<ApplyDate><xsl:value-of select="SignDate"/></ApplyDate>
				<!--Ͷ�����뱻���˹�ϵ  ��ʵʱ��������ͨ����ϵ����һ�Զ�ת�������Էſ�-->
				<RelationToInusre>
					<xsl:call-template name="tran_relationtoinusre">
						<xsl:with-param name="tRelationToInusre" select="RelationToInusre"/>
						<xsl:with-param name="tSex" select="Appnt/AppntSex"/>
					</xsl:call-template>
				</RelationToInusre>
				<!--��������Ϣ-->
				<Insured> 
					<!--����������-->
					<Name><xsl:value-of select="Insured/Name"/></Name >			
					<!--������֤������ -->
					<IDType><xsl:apply-templates select="Insured/IDType"/></IDType> 
					<!--֤������-->
					<IDNo><xsl:value-of select="Insured/IDNo"/></IDNo>
				</Insured>
				<!--����-->
				<Prem><xsl:value-of select="Prem"/></Prem>
				<!--����-->
				<Amnt><xsl:value-of select="Amnt"/></Amnt>
				<!--�ɷ��˺�-->
				<AccNo><xsl:value-of select="AccNo"/></AccNo>
				<!--�ɷѷ�ʽ-->
				<PayIntv><xsl:apply-templates select="PayIntv"/></PayIntv >
				<!--�ɷ�����-->
				<PayEndDate><xsl:value-of select="PayEndDate"/></PayEndDate >
				<!--����������-->
				<ContEndDate><xsl:value-of select="ContEndDate"/></ContEndDate >
				<!--Ͷ������-->
				<Mult><xsl:value-of select="Mult"/></Mult >
				<!--����ӡˢ��-->
				<ContPrtNo><xsl:value-of select="ContPrtNo"/></ContPrtNo >
				<!--���Ի�����-->
				<SpecialRate><xsl:value-of select="SpecialRate"/></SpecialRate>
				<!--�����ո���-->
				<EtraRiskCnt><xsl:value-of select="EtraRiskCnt"/></EtraRiskCnt >
				<!-- ״̬ 0:�б�1:�ܱ� -->
				<State><xsl:value-of select="State"/></State>
				<!-- �ܱ�ԭ�� -->
				<Rtext><xsl:value-of select="Rtext"/></Rtext>
			</Detail>
		</xsl:for-each>
		</Body>
	</TranData>
	</xsl:template>
	
	<!-- ֤������-->
	<xsl:template name="tran_idtype" match="IDType">
		<xsl:choose>
			<xsl:when test=".=0">110001</xsl:when>	<!-- ���֤ -->
			<xsl:when test=".=1">110023</xsl:when>	<!-- ���� -->
			<xsl:when test=".=2">110027</xsl:when>	<!-- (����֤)����֤ -->
			<xsl:when test=".=4">110005</xsl:when>	<!-- ���ڱ� -->
			<xsl:when test=".=7"></xsl:when>	<!-- ����֤ -->
			<xsl:when test=".=F">110019</xsl:when>	<!-- �ۡ��ġ�̨ͨ��֤ -->
			<xsl:otherwise>119999</xsl:otherwise>   <!-- ��������֤��ʶ���ʶ -->
		</xsl:choose>
	</xsl:template>	
	
	<!-- Ͷ�����뱻���˹�ϵ -->
	<xsl:template name="tran_relationtoinusre" match="RelationToInusre">
		<xsl:param name="tRelationToInusre"/>
		<xsl:param name="tSex"/>
		<xsl:choose>
			<xsl:when test="$tRelationToInusre=00">01</xsl:when><!-- ���� -->
			<xsl:when test="$tRelationToInusre=01 and $tSex=0">04</xsl:when><!-- ���� -->
			<xsl:when test="$tRelationToInusre=01 and $tSex=1">05</xsl:when><!-- ĸ�� -->
			<xsl:when test="$tRelationToInusre=02 and $tSex=0">02</xsl:when><!-- �ɷ� -->
			<xsl:when test="$tRelationToInusre=02 and $tSex=1">03</xsl:when><!-- ���� -->
			<xsl:when test="$tRelationToInusre=03 and $tSex=0">06</xsl:when><!-- ���� -->
			<xsl:when test="$tRelationToInusre=03 and $tSex=1">07</xsl:when><!-- Ů�� -->
			<xsl:when test="$tRelationToInusre=04"></xsl:when><!-- ���� -->
			<xsl:when test="$tRelationToInusre=05"></xsl:when><!-- �����໤�� -->
			<xsl:when test="$tRelationToInusre=06">30</xsl:when><!-- ���� -->
			<xsl:when test="$tRelationToInusre=09">29</xsl:when><!-- ��Ӷ��ϵ -->
		</xsl:choose>
	</xsl:template>

	<!-- �ɷѷ�ʽ -->
	<xsl:template name="tran_payintv" match="PayIntv">
		<xsl:choose>
			<xsl:when test=".=0">1</xsl:when>	<!-- ���� -->
			<xsl:when test=".=1">2</xsl:when>	<!-- �½� -->
			<xsl:when test=".=3">3</xsl:when>	<!-- ���� -->
			<xsl:when test=".=6">4</xsl:when>	<!-- ���꽻 -->
			<xsl:when test=".=12">5</xsl:when>	<!-- �꽻 -->
			<xsl:when test=".=-1">0</xsl:when>	<!-- ������ -->
		</xsl:choose>
	</xsl:template>
	
</xsl:stylesheet>
