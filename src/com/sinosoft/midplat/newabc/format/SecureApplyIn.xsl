<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"  exclude-result-prefixes="java">
 	
	<xsl:template match="ABCB2I">
	<TranData><!-- ����ũ�б�ȫ���������� -->
		<!--������Ϣ-->
		  	<Head>
		  		<!-- �������� -->
		  		<TranDate><xsl:value-of select="Header/TransDate"/></TranDate>
		  		<!-- ����ʱ��-->
				<TranTime><xsl:value-of select="Header/TransTime"/></TranTime>
				<!-- ���д��� -->
				<BankCode>0102</BankCode>
				<!-- �������� -->
				<ZoneNo><xsl:value-of select="Header/ProvCode"/></ZoneNo>
				<!-- �������� -->
				<NodeNo><xsl:value-of select="Header/ProvCode"/><xsl:value-of select="Header/BranchNo"/></NodeNo>
				<!-- ��Ա���� -->
				<xsl:choose>
					<xsl:when test="Header/EntrustWay ='11'">
						<TellerNo><xsl:value-of select="Header/Tlid"/></TellerNo>
					</xsl:when>
					<xsl:otherwise>
						<TellerNo>0005</TellerNo>
					</xsl:otherwise>
				</xsl:choose>
		        <!-- ������ˮ�� -->
				<TranNo><xsl:value-of select="Header/SerialNo"/></TranNo>
				<!-- YBT��֯�Ľڵ���Ϣ -->
				 <xsl:copy-of select="Head/*"/> 
		  	</Head>
		<Body>
			<!-- ũ�������ն����� 0���� 8�����ն� -->
				<xsl:choose>
					<xsl:when test="Header/EntrustWay ='11'">
						<SaleChannel>0</SaleChannel>
					</xsl:when>
					<xsl:when test="Header/EntrustWay ='04'">
						<SaleChannel>8</SaleChannel>
					</xsl:when>
				</xsl:choose>
				<!--������ -->
				<ContNo>
					<xsl:if test="Header/EntrustWay = '11'"><xsl:value-of select="App/Req/PolicyNo" /></xsl:if>
					<xsl:if test="Header/EntrustWay = '04'"><xsl:value-of select="java:com.sinosoft.midplat.newabc.format.NewCont.trannoStringBuffer(Header/TransDate,Header/SerialNo)"/></xsl:if>
				</ContNo>
				<!-- ����ӡˢ�� -->
				<xsl:choose>
					<xsl:when test="Header/EntrustWay ='11'">
						<ContPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/PrintCode)"/></ContPrtNo>
					</xsl:when>
					<xsl:when test="Header/EntrustWay ='04'">
						<ContPrtNo></ContPrtNo>
					</xsl:when>
				</xsl:choose>
				<!-- ��������  -->
				<Password><xsl:value-of select="App/Req/PolicyPwd"/></Password>
				<!--����������  -->
				<AccName><xsl:value-of select="App/Req/ClientName"/></AccName>
				<!--֤������ -->
				<IDType><xsl:apply-templates select="App/Req/IdKind"/></IDType>
				<!-- ֤������ -->
				<IDNo><xsl:value-of select="App/Req/IdCode"/></IDNo>
				<!--������ˣ������� -->
				<PayAcc><xsl:value-of select="App/Req/PayAcc"/></PayAcc>
				<!-- ���շ�(��) -->
				<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(App/Req/Amt)"/></Prem>
				<!--ҵ������: 05Ͷ����ת����06Ͷ���ղ�����ȡ��07��ԥ�ڳ�����09 ���ڸ�����10�˱���11���ڽɷ� -->
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

	<!--  ҵ������ -->
	<xsl:template name="busitype" >
		<xsl:param name="BusiType"></xsl:param>
		<xsl:if test="$BusiType = 01">07</xsl:if><!-- �̳�  -->
		<xsl:if test="$BusiType = 02">09</xsl:if><!-- ���ڸ��� -->
		<xsl:if test="$BusiType = 03">10</xsl:if><!-- �˱� -->
	</xsl:template>

	<!-- ֤������-->
	<xsl:template name="tran_idtype" match="IdKind">
		<xsl:choose>
			<xsl:when test=".=110001">0</xsl:when>	<!-- �������֤ -->
			<xsl:when test=".=110002">0</xsl:when>	<!-- �غž������֤-->
			<xsl:when test=".=110003">0</xsl:when>	<!-- ��ʱ�������֤ -->
			<xsl:when test=".=110004">0</xsl:when>	<!-- �غ���ʱ�������֤ -->
			<xsl:when test=".=110005">4</xsl:when>  <!-- ���ڲ� -->
			<xsl:when test=".=110006">4</xsl:when>  <!-- �غŻ��ڲ�  -->
			<xsl:when test=".=110011">99</xsl:when>  <!-- ���ݸɲ�����֤ -->
			<xsl:when test=".=110012">99</xsl:when>  <!-- �غ����ݸɲ�����֤ -->
			<xsl:when test=".=110013">99</xsl:when>  <!-- ��������֤ -->
			<xsl:when test=".=110014">99</xsl:when>  <!-- �غž�������֤ -->
			<xsl:when test=".=110015">99</xsl:when>  <!-- ��ְ�ɲ�����֤ -->
			<xsl:when test=".=110016">99</xsl:when>  <!-- �غ���ְ�ɲ�����֤ -->
			<xsl:when test=".=110017">99</xsl:when>  <!-- ����ԺУѧԱ֤ -->
			<xsl:when test=".=110018">99</xsl:when>  <!-- �غž���ԺУѧԱ֤ -->
			<xsl:when test=".=110019">F</xsl:when>  <!-- �۰ľ��������ڵ�ͨ��֤ -->
			<xsl:when test=".=110020">F</xsl:when>  <!-- �غŸ۰ľ��������ڵ�ͨ��֤ -->
			<xsl:when test=".=110021">F</xsl:when>  <!-- ̨�����������½ͨ��֤ -->
			<xsl:when test=".=110022">F</xsl:when>  <!-- �غ�̨�����������½ͨ��֤ -->
			<xsl:when test=".=110023">1</xsl:when>  <!-- �л����񹲺͹����� -->
			<xsl:when test=".=110024">1</xsl:when>  <!-- �غ��л����񹲺͹����� -->
			<xsl:when test=".=110025">1</xsl:when>  <!-- ������� -->
			<xsl:when test=".=110026">1</xsl:when>  <!-- �غ�������� -->
			<xsl:when test=".=110027">2</xsl:when>  <!-- ����֤ -->
			<xsl:when test=".=110028">2</xsl:when>  <!-- �غž���֤ -->
			<xsl:when test=".=110029">99</xsl:when>  <!-- ��ְ�ɲ�֤ -->
			<xsl:when test=".=110030">99</xsl:when>  <!-- �غ���ְ�ɲ�֤ -->
			<xsl:when test=".=110031">D</xsl:when>  <!-- ����֤ -->
			<xsl:when test=".=110032">D</xsl:when>  <!-- �غž���֤ -->
			<xsl:when test=".=110033">2</xsl:when>  <!-- ����ʿ��֤ -->
			<xsl:when test=".=110034">2</xsl:when>  <!-- �غž���ʿ��֤ -->
			<xsl:when test=".=110035">D</xsl:when>  <!-- �侯ʿ��֤ -->
			<xsl:when test=".=110036">D</xsl:when>  <!-- �غ��侯ʿ��֤ -->
			<xsl:when test=".=119998">99</xsl:when>  <!-- ϵͳʹ�õĸ���֤��ʶ���ʶ -->
			<xsl:when test=".=119999">99</xsl:when>  <!-- ��������֤��ʶ���ʶ -->
			<xsl:otherwise>--</xsl:otherwise>  
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>