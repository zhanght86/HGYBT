<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">

<Transaction>
	<xsl:if test="/TranData/Head/Flag='0'">
	<Transaction_Body>
	<!-- 交易流水号 -->
	<BkPlatSeqNo><xsl:value-of select="TranData/Body/TranNo"/></BkPlatSeqNo>
	<!-- 交易码/处理标志 -->
	<BkTxCode>OPR012</BkTxCode>
		
 	 <BkPlatDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></BkPlatDate> 
 	 <BkPlatTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></BkPlatTime> 
		<xsl:apply-templates select="TranData/Body"/>
	</Transaction_Body>    
	</xsl:if>
</Transaction> 
</xsl:template>   

<xsl:template match="Body">
			<!-- 返回文件的数量 --> 
			<BkFileNum>1</BkFileNum>
			<Detail_List>
					<!-- 打印提示信息 -->
					<BkFileDesc>犹豫期退保单据</BkFileDesc>				
					<!-- 重空类型 -->
					<BkType1>330072000001</BkType1>				
					<!-- 单证号/重空印刷号 -->
					<BkVchNo><xsl:value-of select="CardNo"/></BkVchNo>				
					<!-- 此文本打印行数 -->
					<BkRecNum>20</BkRecNum>				
			</Detail_List>
</xsl:template>
</xsl:stylesheet>
