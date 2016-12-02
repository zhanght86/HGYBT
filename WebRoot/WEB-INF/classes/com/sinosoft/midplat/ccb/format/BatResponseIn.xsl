<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="/">
		<!--送盘-->
		<TranData>
				<xsl:for-each select="/Transaction/Transaction_Header">
			<Head>
					<TranDate>
						<xsl:value-of select="BkPlatDate" />
					</TranDate>
					<!--银行交易日期-->
				　　				<TranTime>
					<xsl:value-of select="BkPlatTime" />
				    </TranTime>
				    <!-- 银行交易时间 -->
				　　<BankCode>0104</BankCode> <!--银行分行代码 -->
					<!--银行代码(cd05)-->
					<ZoneNo>
						<xsl:value-of
							select="substring(BkBrchNo, 1, 3)" />
					</ZoneNo>
					<!--地区代码 -->
					<NodeNo>
						<xsl:value-of
							select="BkBrchNo" />
					</NodeNo>
					<!--网点代码-->
					<TellerNo><!--建行银行柜员代码为12位，我方数据库(LKTRANSSTATUS)中对应字段(BANKOPERATOR)为10位，自动截取后10位。-->
						<xsl:value-of select="substring(BkTellerNo, 3)" />
					</TellerNo>
					<!--柜员代码-->
					<TranNo>
						<xsl:value-of select="BkPlatSeqNo" />
					</TranNo>
					<!--交易流水号-->
					<SaleChannel>
						<xsl:value-of select="BkChnlNo" />
					</SaleChannel>
					<!--交易渠道代号-->
					<InsuID>
						<xsl:value-of select="PbInsuId" />
					</InsuID>
					<xsl:copy-of select="../Head/*"/>
					<!--保险公司代码(cd03) -->
					</Head>
				</xsl:for-each>
			
			<Body>
				<xsl:variable name="FileName"
				select="/Transaction/Transaction_Body/BkFileName" />
				<FileName>
					<xsl:value-of select="$FileName" />
				</FileName>
				<!-- 批量文件名 -->
				<DealType>
					<xsl:call-template name="tran_type">
						<xsl:with-param name="type">
							<xsl:value-of
								select="substring($FileName, 3, 1)" />
						</xsl:with-param>
					</xsl:call-template>
				</DealType><!-- 业务类型 -->
			
				<TotalNum>
					<xsl:value-of
						select="/Transaction/Transaction_Body/PbOperSuccNum" />
				</TotalNum><!--当前批明细总笔数-->
				<TotalMoney>
					<xsl:value-of
						select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(/Transaction/Transaction_Body/PbOperSuccSum)" />
				</TotalMoney><!--当前批明细总金额-->
				<xsl:for-each
					select="/Transaction/Transaction_Body/Detail_List/Detail">
					<Detail>
						<AccName>
							<xsl:value-of select="BkCustName" />
						</AccName><!--客户姓名-->
						<AccNo>
							<xsl:value-of select="BkAcctNo" />
						</AccNo><!--帐号-->
						<IDType></IDType>
						<IDNo></IDNo>
						<PayCode>
							<xsl:value-of select="BkOthRetSeq" />
						</PayCode><!--保险公司方明细序号-->
						<NoType>
							<xsl:apply-templates select="LiOperType" />
						</NoType><!--业务类型-->
						<PolNo>
							<xsl:value-of select="PbRemark1" />
						</PolNo><!--保单号-->
						<PayMoney>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(BkAmt1)" />
						</PayMoney><!--金额-->
						<SerialNo>
							<xsl:value-of select="PbInsuSlipNo" />
						</SerialNo><!--批次号-->
						<AgentCode></AgentCode>
						<BankSuccFlag>
							<xsl:apply-templates select="BkRetCode" />
						</BankSuccFlag><!--处理响应码-->
						<Reason><xsl:value-of select="BkRetMsg" /></Reason>
						<!-- 错误原因 -->
					</Detail>
				</xsl:for-each>
			</Body>
		</TranData>
	</xsl:template>

	<xsl:template match="LiOperType">
		<xsl:choose>
			<xsl:when test=".='01'">02</xsl:when>
			<xsl:when test=".='02'">01</xsl:when>
			<xsl:when test=".='11'">06</xsl:when>
			<xsl:when test=".='12'">07</xsl:when>
			<xsl:when test=".='13'">08</xsl:when>
			<xsl:when test=".='14'">04</xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template match="BkRetCode">
		<xsl:choose>
			<xsl:when test=".=00000">0000</xsl:when>
			<xsl:when test=".=E8208">3017</xsl:when>
			<xsl:when test=".=E8201">3018</xsl:when>
			<xsl:when test=".=E3150">3009</xsl:when>
			<xsl:when test=".=E5502">3999</xsl:when>
			<xsl:when test=".=E4501">3057</xsl:when>
			<xsl:when test=".=E3001">3036</xsl:when>
			<xsl:when test=".=E4500">3057</xsl:when>
			<xsl:when test=".=E5000">3017</xsl:when>
			<xsl:when test=".=E3551">3017</xsl:when>
			<xsl:when test=".=E4500">3017</xsl:when>
			<xsl:when test=".=E5002">3006</xsl:when>
			<xsl:when test=".=E7102">3008</xsl:when>
			<xsl:when test=".=E3266">3031</xsl:when>
			<xsl:when test=".=E1408">3999</xsl:when>
			<xsl:when test=".=E8301">3999</xsl:when>
			<xsl:when test=".=E1085">3057</xsl:when>
			<xsl:when test=".=E3540">3057</xsl:when>
			<xsl:when test=".=E3556">3999</xsl:when>
			<xsl:when test=".=SHK02">3999</xsl:when>
			<xsl:when test=".=SDB01">3999</xsl:when>
			<xsl:otherwise>3999</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="tran_type">
		<xsl:param name="type">0</xsl:param>
		<xsl:choose>
			<xsl:when test="$type = 0">S</xsl:when>
			<xsl:when test="$type = 1">F</xsl:when>
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
