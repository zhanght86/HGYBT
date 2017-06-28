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
				<BankCode><xsl:value-of select="Head/BankCode"/></BankCode>
				<!-- �������� -->
				<ZoneNo><xsl:value-of select="Header/ProvCode"/></ZoneNo>
				<!-- �������� -->
				<xsl:choose>
					<xsl:when test="Header/EntrustWay='11'"><!-- ���� -->
						<NodeNo><xsl:value-of select="Header/ProvCode"/><xsl:value-of select="Header/BranchNo"/></NodeNo>
					</xsl:when>
					<xsl:otherwise><!-- ��������ʵ���������ȡʵ�����㣬������ȡ�������� -->
						<xsl:if test="Header/ProvCode != '' and Header/BranchNo != ''">
							<NodeNo><xsl:value-of select="Header/ProvCode"/><xsl:value-of select="Header/BranchNo"/></NodeNo>
						</xsl:if>
						<xsl:if test="Header/ProvCode = '' or Header/BranchNo = ''">
							<NodeNo>ABCWEB</NodeNo>
						</xsl:if>
					</xsl:otherwise>
				</xsl:choose>
				<!-- ��Ա���� -->
				<xsl:choose>
					<xsl:when test="Header/EntrustWay = '11'">
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
				<!-- �������� -->
				<SaleChannel><xsl:apply-templates select="Header/EntrustWay"/></SaleChannel>
				<!--������ -->
				<ContNo><xsl:value-of select="App/Req/PolicyNo" /></ContNo>
				<!-- ����ӡˢ�� -->
				<xsl:choose>
					<xsl:when test="Header/EntrustWay ='11'">
						<ContPrtNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/PrintCode)"/></ContPrtNo>
					</xsl:when>
					<xsl:otherwise>
						<ContPrtNo></ContPrtNo>
					</xsl:otherwise>
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

	<!-- ί�з�ʽ
	01-������������
	02-������������
	04-���������ն�����
	11-���й�̨����
	20-���չ�˾����
	 -->
	<xsl:template name="tran_salechannel" match="EntrustWay">
		<xsl:choose>
			<xsl:when test=".=01">1</xsl:when><!-- ������������ -->
			<xsl:when test=".=02">2</xsl:when><!-- ������������ -->
			<xsl:when test=".=04">8</xsl:when><!-- ���������ն����� -->
			<xsl:when test=".=11">0</xsl:when><!-- ���й�̨���� -->
			<xsl:when test=".=20"></xsl:when><!-- ���չ�˾���� -->
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</xsl:template>	

	<!--  ҵ������ -->
	<xsl:template name="busitype" >
		<xsl:param name="BusiType"></xsl:param>
		<xsl:if test="$BusiType = 01">07</xsl:if><!-- �̳�  -->
		<xsl:if test="$BusiType = 02">09</xsl:if><!-- ���ڸ��� -->
		<xsl:if test="$BusiType = 03">10</xsl:if><!-- �˱� -->
	</xsl:template>

	<!-- ֤������-->
	<xsl:template name="tran_idtype" match="IDKind">
		<xsl:choose>
			<xsl:when test=".=110001">0</xsl:when>	<!-- �������֤ -->
			<xsl:when test=".=110002">99</xsl:when>	<!-- �غž������֤-->
			<xsl:when test=".=110003">0</xsl:when>	<!-- ��ʱ�������֤ -->
			<xsl:when test=".=110004">99</xsl:when>	<!-- �غ���ʱ�������֤ -->
			<xsl:when test=".=110005">4</xsl:when>  <!-- ���ڲ� -->
			<xsl:when test=".=110006">99</xsl:when>  <!-- �غŻ��ڲ�  -->
			<xsl:when test=".=110007">99</xsl:when> <!-- �������֤ -->
			<xsl:when test=".=110008">99</xsl:when> <!-- �غ��й������ž��������֤�� -->
			<xsl:when test=".=110009">99</xsl:when> <!-- ��װ�������֤ -->
			<xsl:when test=".=110010">99</xsl:when> <!-- �غ��й�������װ�������֤�� -->
			<xsl:when test=".=110011">99</xsl:when>  <!-- ���ݸɲ�����֤ -->
			<xsl:when test=".=110012">99</xsl:when>  <!-- �غ����ݸɲ�����֤ -->
			<xsl:when test=".=110013">99</xsl:when>  <!-- ��������֤ -->
			<xsl:when test=".=110014">99</xsl:when>  <!-- �غž�������֤ -->
			<xsl:when test=".=110015">99</xsl:when>  <!-- ��ְ�ɲ�����֤ -->
			<xsl:when test=".=110016">99</xsl:when>  <!-- �غ���ְ�ɲ�����֤ -->
			<xsl:when test=".=110017">99</xsl:when>  <!-- ����ԺУѧԱ֤ -->
			<xsl:when test=".=110018">99</xsl:when>  <!-- �غž���ԺУѧԱ֤ -->
			<xsl:when test=".=110019">F</xsl:when>  <!-- �۰ľ��������ڵ�ͨ��֤ -->
			<xsl:when test=".=110020">99</xsl:when>  <!-- �غŸ۰ľ��������ڵ�ͨ��֤ -->
			<xsl:when test=".=110021">F</xsl:when>  <!-- ̨�����������½ͨ��֤ -->
			<xsl:when test=".=110022">99</xsl:when>  <!-- �غ�̨�����������½ͨ��֤ -->
			<xsl:when test=".=110023">99</xsl:when>  <!-- �л����񹲺͹����� -->
			<xsl:when test=".=110024">99</xsl:when>  <!-- �غ��л����񹲺͹����� -->
			<xsl:when test=".=110025">1</xsl:when>  <!-- ������� -->
			<xsl:when test=".=110026">99</xsl:when>  <!-- �غ�������� -->
			<xsl:when test=".=110027">2</xsl:when>  <!-- ����֤ -->
			<xsl:when test=".=110028">99</xsl:when>  <!-- �غž���֤ -->
			<xsl:when test=".=110029">2</xsl:when>  <!-- ��ְ�ɲ�֤ -->
			<xsl:when test=".=110030">99</xsl:when>  <!-- �غ���ְ�ɲ�֤ -->
			<xsl:when test=".=110031">D</xsl:when>  <!-- ����֤ -->
			<xsl:when test=".=110032">99</xsl:when>  <!-- �غž���֤ -->
			<xsl:when test=".=110033">2</xsl:when>  <!-- ����ʿ��֤ -->
			<xsl:when test=".=110034">99</xsl:when>  <!-- �غž���ʿ��֤ -->
			<xsl:when test=".=110035">2</xsl:when>  <!-- �侯ʿ��֤ -->
			<xsl:when test=".=110036">99</xsl:when>  <!-- �غ��侯ʿ��֤ -->
			<xsl:when test=".=110037">1</xsl:when>  <!-- ����˾���֤-->
			<xsl:when test=".=110043">99</xsl:when>  <!-- ����������֤ -->
			<xsl:when test=".=110045">99</xsl:when>  <!-- �⽻��֤ -->
			<xsl:when test=".=110047">99</xsl:when>  <!-- �л����񹲺͹�����֤ -->
			<xsl:when test=".=119998">99</xsl:when>  <!-- ϵͳʹ�õĸ���֤��ʶ���ʶ -->
			<xsl:when test=".=119999">99</xsl:when>  <!-- ��������֤��ʶ���ʶ -->
			<xsl:otherwise>--</xsl:otherwise>  
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>