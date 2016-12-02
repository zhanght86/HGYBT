<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
	
	<TranData>
	<Head>
		<!-- YBT��֯�Ľڵ���Ϣ -->
		 <xsl:copy-of select="Head/*"/> <!-- -->
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
				<!--��������-->
				<ApplyDate><xsl:value-of select="ApplyDate"/></ApplyDate>
				<!--Ͷ�����뱻���˹�ϵ  ��ʵʱ��������ͨ����ϵ����һ�Զ�ת�����ſ��ˣ�����-->
				<RelationToInusre></RelationToInusre>
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
			</Detail>
		</xsl:for-each>
		</Body>
	</TranData>
		
	</xsl:template>
	
<!-- ֤������-->
<xsl:template name="tran_idtype" match="IDType">
	<xsl:choose>
		<xsl:when test=".=0">110001</xsl:when>	<!-- ���֤ -->
																		<!-- �غž������֤-->
		<xsl:when test=".=C">110003</xsl:when>	<!-- ��ʱ�������֤ -->
																		<!-- �غ���ʱ�������֤ -->
		<xsl:when test=".=4">110005</xsl:when>   <!-- ���ڲ� -->
																		<!-- �غŻ��ڲ�  -->
		<xsl:when test=".=2">110007</xsl:when>   <!-- �й������ž��������֤  -->
																	    <!-- �غ��й������ž��������֤  -->
		<xsl:when test=".=D">110009</xsl:when>  <!-- �й�������װ�������֤��  -->
																		<!-- �غ��й�������װ�������֤��  -->
																		<!-- ���ݸɲ�����֤ -->
																		<!-- �غ����ݸɲ�����֤ -->
		<xsl:when test=".=8">110013</xsl:when>   <!-- ��������֤ -->
																	   <!-- �غž�������֤ -->
																		<!-- ��ְ�ɲ�����֤ -->
																		<!-- �غ���ְ�ɲ�����֤ -->
		<xsl:when test=".=5">110017</xsl:when>   <!-- ����ԺУѧԱ֤ -->
																	    <!-- �غž���ԺУѧԱ֤ -->
																	    <!-- �۰ľ��������ڵ�ͨ��֤ -->
																	    <!-- �غŸ۰ľ��������ڵ�ͨ��֤ -->
		<xsl:when test=".=E">110021</xsl:when>   <!-- ̨�����������½ͨ��֤ -->
																	   <!-- �غ�̨�����������½ͨ��֤ -->
		<xsl:when test=".=1">110023</xsl:when>   <!-- �л����񹲺͹����� -->
																	   <!-- �غ��л����񹲺͹����� -->
																	   <!-- ������� -->
																	   <!-- �غ�������� -->
		<xsl:when test=".=2">110027</xsl:when>   <!-- ����֤ -->
																	   <!-- �غž���֤ -->
																	   <!-- ��ְ�ɲ�֤ -->
																	   <!-- �غ���ְ�ɲ�֤ -->
																	   <!-- ����֤ -->
																		<!-- �غž���֤ -->
																	   <!-- ����ʿ��֤ -->
																		<!-- �غž���ʿ��֤ -->
		<xsl:when test=".=A">110035</xsl:when>  <!-- �侯ʿ��֤ -->
																	   <!-- �غ��侯ʿ��֤ -->
																	  <!-- ϵͳʹ�õĸ���֤��ʶ���ʶ -->
																	  <!-- ��������֤��ʶ���ʶ -->
		<xsl:otherwise>119999</xsl:otherwise>   <!-- ��������֤��ʶ���ʶ -->
	</xsl:choose>
</xsl:template>	

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
