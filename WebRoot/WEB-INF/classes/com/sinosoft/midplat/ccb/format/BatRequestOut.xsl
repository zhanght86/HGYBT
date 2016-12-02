<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes" />
	<xsl:template match="/">
		<Transaction>
			<!--报文体-->
			<xsl:if test="/TranData/Head/Flag='0'">
			<Transaction_Body>
				<PbOperSuccNum>
					<xsl:value-of select="/TranData/Body/TotalNum" />
				</PbOperSuccNum>
				<PbOperSuccSum>
					<xsl:value-of select="/TranData/Body/TotalSum" />
				</PbOperSuccSum>
				<PbPackType>B</PbPackType>
				<Detail_List>
					<xsl:for-each select="/TranData/Body/Detail">
						<Detail>
							<BkCustName>
								<xsl:value-of select="AccName" />
							</BkCustName><!-- 客户姓名 -->
							<BkAcctNo>
								<xsl:value-of select="AccNo" />
							</BkAcctNo><!-- 帐号 -->
							<BkOthRetSeq>
								<xsl:value-of select="PayCode" />
							</BkOthRetSeq><!-- 保险公司方明细序号 -->
							<LiOperType>
								<xsl:value-of select="OperType" />
							</LiOperType><!-- 业务类型 -->
							<PbInsuSlipNo>
								<xsl:value-of select="SerialNo" />
							</PbInsuSlipNo><!-- 批次号(保单号) -->
							<BkAmt1>
								<xsl:value-of select="PayMoney" />
							</BkAmt1><!-- 金额 -->
							<PbRemark1>
								<xsl:value-of select="PolNo" />
							</PbRemark1><!-- 保单号 -->
							<PbRemark2></PbRemark2>
							<PbRemark3></PbRemark3>
						</Detail>
					</xsl:for-each>
				</Detail_List>
			</Transaction_Body>
			</xsl:if>
		</Transaction>
	</xsl:template>
</xsl:stylesheet>
