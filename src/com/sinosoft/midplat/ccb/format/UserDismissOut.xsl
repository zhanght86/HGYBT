<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
	<xsl:template match="/">
		<Transaction>
			<!--报文体-->
			<xsl:if test="TranData/Head/Flag=0">
			<Transaction_Body>
				<BkFileNum>1</BkFileNum>
				<!--打印文本数量-->
				<Detail_List>
				<BkFileDesc>客户解约</BkFileDesc>
				<BkType1></BkType1>
				<BkVchNo></BkVchNo>
				<BkRecNum>13</BkRecNum>
				<Detail>
				<BkDetail1>
				<xsl:text>　　　　</xsl:text>
						<xsl:text>保险公司名称：东吴人寿保险股份有限公司</xsl:text></BkDetail1>
				<BkDetail1>
				<xsl:text>　　　　</xsl:text>
						<xsl:text>业务种类：银行账号签约</xsl:text>
						</BkDetail1>
						<BkDetail1>
						<xsl:text>　　　　</xsl:text>
						<xsl:text>险种：</xsl:text><xsl:value-of select="TranData/Body/RiskName"/>
						</BkDetail1>
				<BkDetail1><xsl:text>　　　　</xsl:text><xsl:text>保单号：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/ContNo, 42)"/>
				<xsl:text>　　　　        </xsl:text><xsl:text> 流水号：</xsl:text><xsl:value-of select="TranData/Body/TranNo"/>
				</BkDetail1>
				<BkDetail1>
				<xsl:text>　　　　</xsl:text><xsl:text>投保人姓名:</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/AppntName, 40)"/>
				<xsl:text>　　　　        </xsl:text><xsl:text>开户行名称：中国建设银行</xsl:text>
				</BkDetail1>
				<BkDetail1>
				<xsl:text>　　　　</xsl:text>
				<xsl:text>生效日期：</xsl:text><xsl:value-of select="TranData/Body/CValidate"/>
				</BkDetail1>
							 <BkDetail1></BkDetail1>
								 <BkDetail1></BkDetail1>
								 								  <BkDetail1><xsl:text>　　　　        </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('', 20)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('申请人签章：', 40)"/><xsl:text></xsl:text><xsl:text>　　　　            	银行盖章：</xsl:text></BkDetail1>
								  <BkDetail1></BkDetail1>
									       <BkDetail1><xsl:text>　　　　</xsl:text>    如对您的变更结果有疑问或有其它保险业务需求，请联系您的寿险顾问/代理机构或拨打公司统一咨</BkDetail1>
					       <BkDetail1><xsl:text>　　　　</xsl:text>询热线垂询，也可以登陆公司网站，我们将竭诚为您提供更优质的服务。</BkDetail1>
					       <BkDetail1><xsl:text>　　　　</xsl:text>    公司全国统一咨询热线 4008256789;公司网址www.soochowlife.com;</BkDetail1>
				</Detail>
				</Detail_List>
			</Transaction_Body>
			</xsl:if>
		</Transaction>
	</xsl:template>
</xsl:stylesheet>
