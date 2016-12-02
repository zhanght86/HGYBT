<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:template match="/">
<TXLife>
<xsl:copy-of select="TranData/Head" />
	<TXLifeResponse>
		<!-- 交易成功才返回以下节点 -->
		<OLifE>
			<Holding>
				<Policy>
					<PolNumber><xsl:value-of select="/TranData/Body/PolNumber"/></PolNumber>
				</Policy>
			</Holding>
			<FormInstance id="Form_1">
				<!-- 单证名称 -->
				<FormName>3</FormName>
				<!-- 批单号 -->
				<DocumentControlNumber><xsl:value-of select="/TranData/Body/DocumentControlNumber"/></DocumentControlNumber>
				<!-- 批改生效日期 -->
				<FormInstanceStatusDate><xsl:value-of select="/TranData/Body/TranDate"/></FormInstanceStatusDate>
				<!-- 批文（可重复） -->
				<Attachment id="Attachment_Form_0">
					<AttachmentData></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_10">
					<AttachmentData>                                         保全批单</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_2">
					<AttachmentData>尊敬的<xsl:value-of select="/TranData/Body/CustomerName"/><xsl:value-of select="/TranData/Body/CustomerSex"/>:</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_3">
					<AttachmentData>    兹根据您的申请，现对<xsl:value-of select="/TranData/Body/PolNumber"/>号保险单作如下批注：</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_4">
					<AttachmentData></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_5">
					<AttachmentData>    客户资料变更</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_6">
					<AttachmentData><xsl:text>    </xsl:text><xsl:value-of select="/TranData/Body/AttachmentTBR"/></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_7">
					<AttachmentData><xsl:text>    </xsl:text><xsl:value-of select="/TranData/Body/AttachmentBBR"/></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_8">
					<AttachmentData></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_9">
					<AttachmentData>    本次批注生效时间：<xsl:value-of select="substring(/TranData/Body/TranDate,0,5)" />年<xsl:value-of select="substring(/TranData/Body/TranDate,6,2)" />月<xsl:value-of select="substring(/TranData/Body/TranDate,9,2)" />日</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_10">
					<AttachmentData></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_11">
					<AttachmentData>    特此批注。</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_12">
					<AttachmentData></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_13">
					<AttachmentData>    保全服务信息：</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_14">
					<AttachmentData>    批单号码：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/DocumentControlNumber,30)"/>投保人姓名：<xsl:value-of select="/TranData/Body/CustomerName"/></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_15">
					<AttachmentData>    部门名称：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/ManageName,30)"/>银行网点柜员姓名：<xsl:value-of select="/TranData/Body/JOperator"/></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_16">
					<AttachmentData>    打印日期：<xsl:value-of select="substring(/TranData/Body/TranDate,0,5)" />年<xsl:value-of select="substring(/TranData/Body/TranDate,6,2)" />月<xsl:value-of select="substring(/TranData/Body/TranDate,9,2)" />日</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_17">
					<AttachmentData></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_18">
					<AttachmentData>                                            (此页以下空白)</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_19">
					<AttachmentData>-------------------------------------------------------------------------------------------------</AttachmentData>
				</Attachment>
			</FormInstance>
		</OLifE>
   </TXLifeResponse> 
</TXLife>
</xsl:template>

</xsl:stylesheet>