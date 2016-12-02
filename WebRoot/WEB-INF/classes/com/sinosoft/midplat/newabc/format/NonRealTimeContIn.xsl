<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">

 
<xsl:template match="ABCB2I">
	  <TranData>
	     <Head>
	        <!-- ���н�����ˮ�� -->
			<TranNo><xsl:value-of select="Header/SerialNo"/></TranNo>
			<!-- �������� -->
			<ZoneNo><xsl:value-of select="Header/ProvCode"/></ZoneNo>
			<!-- ������� -->
			<NodeNo>
			<xsl:value-of select="Header/ProvCode"/>
			<xsl:value-of select="Header/BranchNo"/>
			</NodeNo>
	  		<!-- ���н������� -->
	  		<TranDate><xsl:value-of select="Header/TransDate"/></TranDate>
	  		<!-- ����ʱ��-->
			<TranTime><xsl:value-of select="Header/TransTime"/></TranTime>
			<!-- ��Ա���� -->
			<TellerNo><xsl:value-of select="Header/Tlid"/></TellerNo>
			<!-- ���д��� -->
			<BankCode>0102</BankCode>
			
			<!-- YBT��֯�Ľڵ���Ϣ -->
			 <xsl:copy-of select="Head/*"/> <!-- -->
	  	</Head>
	  	
		<!--Ͷ����Ϣ-->
		<Body>
			<!-- Ͷ������ -->
			<ProposalPrtNo><xsl:value-of select="App/Req/PolicyApplyNo"/></ProposalPrtNo>

			<!-- �����ʺ� ���ڽɷ��˺� BankAccNo�����ڽɷ��˺�AccNo ��ũ�е�������Ա��ͨ��20130402 -->
			<AccNo><xsl:value-of select="App/Req/AccNo"/></AccNo>
			
			<!-- Ͷ���� -->
			<xsl:apply-templates select="App/Req/Appl"/>

			<!-- ��������Ϣ -->
		    <Risk>
		    
			    <!-- ���ִ��� -->
				<RiskCode><xsl:apply-templates select="App/Req/RiskCode"/></RiskCode>
				<!-- ���� -->
				<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(App/Req/Prem)"/></Prem>
			
			</Risk>
			<!-- ������Ϣ -->
			<xsl:apply-templates select="App/Req/Loan"/>
		</Body>
	</TranData>
</xsl:template>	
<xsl:template match="App/Req/Appl">
		<Appnt>
			<!-- ���� -->
			<Name><xsl:value-of select="Name"/></Name>
			<!-- ֤������ -->
			<IDType><xsl:apply-templates select="IDKind"/></IDType>
			<!-- ֤������ -->
			<IDNo><xsl:value-of select="IDCode"/></IDNo>
			<!-- ��ַ -->
			<Address><xsl:value-of select="Address"/></Address>
			<!-- �ʱ� -->
			<ZipCode><xsl:value-of select="ZipCode"/></ZipCode>
			<!-- ��ͥ�绰 -->
			<Phone><xsl:value-of select="Phone"/></Phone>
			<!-- �ֻ����� -->
			<Mobile><xsl:value-of select="CellPhone"/></Mobile>
			<!-- Ͷ���˾������� -->
			 <DenType><xsl:apply-templates select="CustSource" /></DenType>
		</Appnt>
</xsl:template>
		

<!--������Ϣ-->
<xsl:template match="App/Req">
		<Loan>
			<!-- �����ͬ�� -->
			<LoanNo><xsl:value-of select="LoanContact"/></LoanNo>
		</Loan>
</xsl:template>


<!-- ֤������-->
<xsl:template name="tran_idtype" match="IDKind">
	<xsl:choose>
		<xsl:when test=".=110001">0</xsl:when>	<!-- ���֤ -->
		<xsl:when test=".=110002">0</xsl:when>	<!-- �غž������֤-->
		<xsl:when test=".=110003">C</xsl:when>	<!-- ��ʱ�������֤ -->
		<xsl:when test=".=110004">C</xsl:when>	<!-- �غ���ʱ�������֤ -->
		<xsl:when test=".=110005">4</xsl:when>  <!-- ���ڲ� -->
		<xsl:when test=".=110006">4</xsl:when>  <!-- �غŻ��ڲ�  -->
		<xsl:when test=".=110007">2</xsl:when>  <!-- �й������ž��������֤  -->
		<xsl:when test=".=110008">2</xsl:when>  <!-- �غ��й������ž��������֤  -->
		<xsl:when test=".=110009">D</xsl:when>  <!-- �й�������װ�������֤��  -->
		<xsl:when test=".=110010">D</xsl:when>  <!-- �غ��й�������װ�������֤��  -->
		<xsl:when test=".=110011">8</xsl:when>  <!-- ���ݸɲ�����֤ -->
		<xsl:when test=".=110012">8</xsl:when>  <!-- �غ����ݸɲ�����֤ -->
		<xsl:when test=".=110013">8</xsl:when>  <!-- ��������֤ -->
		<xsl:when test=".=110014">8</xsl:when>  <!-- �غž�������֤ -->
		<xsl:when test=".=110015">8</xsl:when>  <!-- ��ְ�ɲ�����֤ -->
		<xsl:when test=".=110016">8</xsl:when>  <!-- �غ���ְ�ɲ�����֤ -->
		<xsl:when test=".=110017">5</xsl:when>  <!-- ����ԺУѧԱ֤ -->
		<xsl:when test=".=110018">5</xsl:when>  <!-- �غž���ԺУѧԱ֤ -->
		<xsl:when test=".=110019">8</xsl:when>  <!-- �۰ľ��������ڵ�ͨ��֤ -->
		<xsl:when test=".=110020">8</xsl:when>  <!-- �غŸ۰ľ��������ڵ�ͨ��֤ -->
		<xsl:when test=".=110021">E</xsl:when>  <!-- ̨�����������½ͨ��֤ -->
		<xsl:when test=".=110022">E</xsl:when>  <!-- �غ�̨�����������½ͨ��֤ -->
		<xsl:when test=".=110023">1</xsl:when>  <!-- �л����񹲺͹����� -->
		<xsl:when test=".=110024">1</xsl:when>  <!-- �غ��л����񹲺͹����� -->
		<xsl:when test=".=110025">1</xsl:when>  <!-- ������� -->
		<xsl:when test=".=110026">1</xsl:when>  <!-- �غ�������� -->
		<xsl:when test=".=110027">2</xsl:when>  <!-- ����֤ -->
		<xsl:when test=".=110028">2</xsl:when>  <!-- �غž���֤ -->
		<xsl:when test=".=110029">8</xsl:when>  <!-- ��ְ�ɲ�֤ -->
		<xsl:when test=".=110030">8</xsl:when>  <!-- �غ���ְ�ɲ�֤ -->
		<xsl:when test=".=110031">D</xsl:when>  <!-- ����֤ -->
		<xsl:when test=".=110032">D</xsl:when>  <!-- �غž���֤ -->
		<xsl:when test=".=110033">A</xsl:when>  <!-- ����ʿ��֤ -->
		<xsl:when test=".=110034">A</xsl:when>  <!-- �غž���ʿ��֤ -->
		<xsl:when test=".=110035">A</xsl:when>  <!-- �侯ʿ��֤ -->
		<xsl:when test=".=110036">A</xsl:when>  <!-- �غ��侯ʿ��֤ -->
		<xsl:when test=".=119998">8</xsl:when>  <!-- ϵͳʹ�õĸ���֤��ʶ���ʶ -->
		<xsl:when test=".=119999">8</xsl:when>  <!-- ��������֤��ʶ���ʶ -->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- ���ִ��� -->
<xsl:template name="Code" match="RiskCode">
<xsl:choose>
	<xsl:when test=".=231201">231201</xsl:when>	<!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�A�� -->
	<xsl:when test=".=231202">231202</xsl:when>	<!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�B�� -->
	<xsl:when test=".=231203">231203</xsl:when> 	<!-- �к�׿Խ�Ƹ���ȫ���գ��ֺ��ͣ� -->
	<xsl:when test=".=211901">211901</xsl:when>  	<!-- �к���Ӯ����������˺����� -->
	<xsl:when test=".=221201">221201</xsl:when>  	<!-- �к����ݻ�����ȫ����A�� -->
	<xsl:when test=".=231204">231204</xsl:when>	<!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C�� -->
	<xsl:when test=".=211902">211902</xsl:when>  	<!-- �к���Ӯ����������˺����� A��-->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>


<!-- Ͷ���˾������� -->
<xsl:template name="cust_Source" match="CustSource">
	<xsl:choose>
		<xsl:when test=".=0">1</xsl:when>	<!-- ���� -->
		<xsl:when test=".=1">2</xsl:when>	<!-- ũ�� -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>


</xsl:stylesheet>