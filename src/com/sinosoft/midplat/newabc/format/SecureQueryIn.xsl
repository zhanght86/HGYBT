<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">

<xsl:template match="ABCB2I">
<TranData><!-- ����ũ�б�ȫ��ѯ������ -->
	<!--������Ϣ-->
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
				<!-- ��Ա���� -->
			<xsl:choose>
			<xsl:when test="Header/EntrustWay ='11'">
			<TellerNo><xsl:value-of select="Header/Tlid"/></TellerNo>
			</xsl:when>
			<xsl:otherwise>
			<TellerNo>0005</TellerNo>
			</xsl:otherwise>
			</xsl:choose>
			<!-- ���д��� -->
			<BankCode>0102</BankCode>
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
			<ContPrtNo><xsl:value-of select="App/Req/PrintCode"/></ContPrtNo>
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
			<!--��������� -->
			<DrwName><xsl:value-of select="App/Req/PayeetName"/></DrwName>
			<!--�����֤������ -->
			<DrwIDType><xsl:apply-templates select="App/Req/PayeeIdKind"/></DrwIDType>		
			<!--�����֤������ -->
			<DrwIDNo><xsl:value-of select="App/Req/PayeeIdCode"/></DrwIDNo>
			<!--������ˣ������� -->
			<PayAccNo><xsl:value-of select="App/Req/PayAcc"/></PayAccNo>
			<!--���� -->
			<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(App/Req/Amt)"/></Prem>
			<!--ҵ������  -->
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
		<xsl:if test="$BusiType = 02">09</xsl:if><!-- ���� -->
		<xsl:if test="$BusiType = 03">10</xsl:if><!-- �˱� -->
	</xsl:template>

<!-- ֤������-->
<xsl:template name="tran_idtype" match="IdKind">
	<xsl:choose>
		<xsl:when test=".=110001">0</xsl:when>
		<xsl:when test=".=110002">0</xsl:when>	
		<xsl:when test=".=110003">C</xsl:when>
		<xsl:when test=".=110004">C</xsl:when>
		<xsl:when test=".=110005">4</xsl:when> 
		<xsl:when test=".=110006">4</xsl:when>  
		<xsl:when test=".=110007">2</xsl:when>  
		<xsl:when test=".=110008">2</xsl:when> 
		<xsl:when test=".=110009">D</xsl:when>  
		<xsl:when test=".=110010">D</xsl:when>  
		<xsl:when test=".=110011">8</xsl:when>
		<xsl:when test=".=110012">8</xsl:when>
		<xsl:when test=".=110013">8</xsl:when> 
		<xsl:when test=".=110014">8</xsl:when>
		<xsl:when test=".=110015">8</xsl:when>  
		<xsl:when test=".=110016">8</xsl:when> 
		<xsl:when test=".=110017">5</xsl:when> 
		<xsl:when test=".=110018">5</xsl:when>  
		<xsl:when test=".=110019">8</xsl:when>
		<xsl:when test=".=110020">8</xsl:when>  
		<xsl:when test=".=110021">E</xsl:when>
		<xsl:when test=".=110022">E</xsl:when>  
		<xsl:when test=".=110023">1</xsl:when>
		<xsl:when test=".=110024">1</xsl:when>  
		<xsl:when test=".=110025">1</xsl:when>
		<xsl:when test=".=110026">1</xsl:when>  
		<xsl:when test=".=110027">2</xsl:when> 
		<xsl:when test=".=110028">2</xsl:when>
		<xsl:when test=".=110029">8</xsl:when> 
		<xsl:when test=".=110030">8</xsl:when> 
		<xsl:when test=".=110031">D</xsl:when>  
		<xsl:when test=".=110032">D</xsl:when>  
		<xsl:when test=".=110033">A</xsl:when>  
		<xsl:when test=".=110034">A</xsl:when>  
		<xsl:when test=".=110035">A</xsl:when>  
		<xsl:when test=".=110036">A</xsl:when>  
		<xsl:when test=".=119998">8</xsl:when>  
		<xsl:when test=".=119999">8</xsl:when> 
	</xsl:choose>
</xsl:template>


<xsl:template name="tran_idtype2" match="PayeeIdKind">
	<xsl:choose>
		<xsl:when test=".=110001">0</xsl:when>
		<xsl:when test=".=110002">0</xsl:when>
		<xsl:when test=".=110003">C</xsl:when>	
		<xsl:when test=".=110004">C</xsl:when>	
		<xsl:when test=".=110005">4</xsl:when>  
		<xsl:when test=".=110006">4</xsl:when>  
		<xsl:when test=".=110007">2</xsl:when> 
		<xsl:when test=".=110008">2</xsl:when> 
		<xsl:when test=".=110009">D</xsl:when>
		<xsl:when test=".=110010">D</xsl:when>
		<xsl:when test=".=110011">8</xsl:when> 
		<xsl:when test=".=110012">8</xsl:when>  
		<xsl:when test=".=110013">8</xsl:when>  
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
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>