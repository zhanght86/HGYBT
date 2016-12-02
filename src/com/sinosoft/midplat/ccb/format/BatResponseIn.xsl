<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="/">
		<!--����-->
		<TranData>
				<xsl:for-each select="/Transaction/Transaction_Header">
			<Head>
					<TranDate>
						<xsl:value-of select="BkPlatDate" />
					</TranDate>
					<!--���н�������-->
				����				<TranTime>
					<xsl:value-of select="BkPlatTime" />
				    </TranTime>
				    <!-- ���н���ʱ�� -->
				����<BankCode>0104</BankCode> <!--���з��д��� -->
					<!--���д���(cd05)-->
					<ZoneNo>
						<xsl:value-of
							select="substring(BkBrchNo, 1, 3)" />
					</ZoneNo>
					<!--�������� -->
					<NodeNo>
						<xsl:value-of
							select="BkBrchNo" />
					</NodeNo>
					<!--�������-->
					<TellerNo><!--�������й�Ա����Ϊ12λ���ҷ����ݿ�(LKTRANSSTATUS)�ж�Ӧ�ֶ�(BANKOPERATOR)Ϊ10λ���Զ���ȡ��10λ��-->
						<xsl:value-of select="substring(BkTellerNo, 3)" />
					</TellerNo>
					<!--��Ա����-->
					<TranNo>
						<xsl:value-of select="BkPlatSeqNo" />
					</TranNo>
					<!--������ˮ��-->
					<SaleChannel>
						<xsl:value-of select="BkChnlNo" />
					</SaleChannel>
					<!--������������-->
					<InsuID>
						<xsl:value-of select="PbInsuId" />
					</InsuID>
					<xsl:copy-of select="../Head/*"/>
					<!--���չ�˾����(cd03) -->
					</Head>
				</xsl:for-each>
			
			<Body>
				<xsl:variable name="FileName"
				select="/Transaction/Transaction_Body/BkFileName" />
				<FileName>
					<xsl:value-of select="$FileName" />
				</FileName>
				<!-- �����ļ��� -->
				<DealType>
					<xsl:call-template name="tran_type">
						<xsl:with-param name="type">
							<xsl:value-of
								select="substring($FileName, 3, 1)" />
						</xsl:with-param>
					</xsl:call-template>
				</DealType><!-- ҵ������ -->
			
				<TotalNum>
					<xsl:value-of
						select="/Transaction/Transaction_Body/PbOperSuccNum" />
				</TotalNum><!--��ǰ����ϸ�ܱ���-->
				<TotalMoney>
					<xsl:value-of
						select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(/Transaction/Transaction_Body/PbOperSuccSum)" />
				</TotalMoney><!--��ǰ����ϸ�ܽ��-->
				<xsl:for-each
					select="/Transaction/Transaction_Body/Detail_List/Detail">
					<Detail>
						<AccName>
							<xsl:value-of select="BkCustName" />
						</AccName><!--�ͻ�����-->
						<AccNo>
							<xsl:value-of select="BkAcctNo" />
						</AccNo><!--�ʺ�-->
						<IDType></IDType>
						<IDNo></IDNo>
						<PayCode>
							<xsl:value-of select="BkOthRetSeq" />
						</PayCode><!--���չ�˾����ϸ���-->
						<NoType>
							<xsl:apply-templates select="LiOperType" />
						</NoType><!--ҵ������-->
						<PolNo>
							<xsl:value-of select="PbRemark1" />
						</PolNo><!--������-->
						<PayMoney>
							<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(BkAmt1)" />
						</PayMoney><!--���-->
						<SerialNo>
							<xsl:value-of select="PbInsuSlipNo" />
						</SerialNo><!--���κ�-->
						<AgentCode></AgentCode>
						<BankSuccFlag>
							<xsl:apply-templates select="BkRetCode" />
						</BankSuccFlag><!--������Ӧ��-->
						<Reason><xsl:value-of select="BkRetMsg" /></Reason>
						<!-- ����ԭ�� -->
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
