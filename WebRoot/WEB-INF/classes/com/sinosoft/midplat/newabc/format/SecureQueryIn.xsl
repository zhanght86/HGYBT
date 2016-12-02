<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">

<xsl:template match="ABCB2I">
<TranData><!-- 核心农行保全查询请求报文 -->
	<!--基本信息-->
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
				<!-- 柜员代码 -->
			<xsl:choose>
			<xsl:when test="Header/EntrustWay ='11'">
			<TellerNo><xsl:value-of select="Header/Tlid"/></TellerNo>
			</xsl:when>
			<xsl:otherwise>
			<TellerNo>0005</TellerNo>
			</xsl:otherwise>
			</xsl:choose>
			<!-- 银行代码 -->
			<BankCode>0102</BankCode>
			<!-- YBT组织的节点信息 -->
			 <xsl:copy-of select="Head/*"/>
	</Head>
	<Body>
	         <!-- 农行自助终端渠道 0柜面 8自助终端 -->
	        <xsl:choose>
			<xsl:when test="Header/EntrustWay ='11'">
			<SaleChannel>0</SaleChannel>
			</xsl:when>
			<xsl:when test="Header/EntrustWay ='04'">
			<SaleChannel>8</SaleChannel>
			</xsl:when>
			</xsl:choose>
			<!--保单号 -->
			<ContNo>
			<xsl:if test="Header/EntrustWay = '11'"><xsl:value-of select="App/Req/PolicyNo" /></xsl:if>
			<xsl:if test="Header/EntrustWay = '04'"><xsl:value-of select="java:com.sinosoft.midplat.newabc.format.NewCont.trannoStringBuffer(Header/TransDate,Header/SerialNo)"/></xsl:if>
			</ContNo>
			
			<!-- 保单印刷号 -->
			<xsl:choose>
			<xsl:when test="Header/EntrustWay ='11'">
			<ContPrtNo><xsl:value-of select="App/Req/PrintCode"/></ContPrtNo>
			</xsl:when>
			<xsl:when test="Header/EntrustWay ='04'">
			<ContPrtNo></ContPrtNo>
			</xsl:when>
			</xsl:choose>
			<!-- 保单密码  -->
			<Password><xsl:value-of select="App/Req/PolicyPwd"/></Password>
			<!--申请人姓名  -->
			<AccName><xsl:value-of select="App/Req/ClientName"/></AccName>
			<!--证件类型 -->
			<IDType><xsl:apply-templates select="App/Req/IdKind"/></IDType>
			<!-- 证件号码 -->
			<IDNo><xsl:value-of select="App/Req/IdCode"/></IDNo>
			<!--领款人姓名 -->
			<DrwName><xsl:value-of select="App/Req/PayeetName"/></DrwName>
			<!--领款人证件类型 -->
			<DrwIDType><xsl:apply-templates select="App/Req/PayeeIdKind"/></DrwIDType>		
			<!--领款人证件号码 -->
			<DrwIDNo><xsl:value-of select="App/Req/PayeeIdCode"/></DrwIDNo>
			<!--领款人账（卡）号 -->
			<PayAccNo><xsl:value-of select="App/Req/PayAcc"/></PayAccNo>
			<!--保费 -->
			<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(App/Req/Amt)"/></Prem>
			<!--业务类型  -->
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

	<!--  业务类型 -->
	<xsl:template name="busitype" >
		<xsl:param name="BusiType"></xsl:param>
		<xsl:if test="$BusiType = 01">07</xsl:if><!-- 犹撤  -->
		<xsl:if test="$BusiType = 02">09</xsl:if><!-- 满期 -->
		<xsl:if test="$BusiType = 03">10</xsl:if><!-- 退保 -->
	</xsl:template>

<!-- 证件类型-->
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
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>