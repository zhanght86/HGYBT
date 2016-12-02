<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:template match="/">
<TXLife>
<xsl:copy-of select="TranData/Head" />
	<TXLifeResponse>
		<OLifE>
		<xsl:variable name="tCustomerSex" select="TranData/Body/PrintNode/CustomerSex" />
 			<FormInstance id="Form_1">
 				<FormName>3</FormName> 
 				<DocumentControlNumber><xsl:value-of select="TranData/Body/DocumentControlNumber"/></DocumentControlNumber>
 				<Attachment id="Attachment_0">
					<AttachmentData /> 
				</Attachment>
 				<Attachment id="Attachment_1">
					<AttachmentData>                                         受理凭证</AttachmentData> 
				</Attachment>
				<Attachment id="Attachment_2">
					<AttachmentData>尊敬的<xsl:value-of select="TranData/Body/PrintNode/CustomerName"/><xsl:if test = "$tCustomerSex = '男'">先生</xsl:if><xsl:if test = "$tCustomerSex = '女'">女士</xsl:if>:</AttachmentData> 
				</Attachment>
				<Attachment id="Attachment_3">
					<AttachmentData>    兹根据您的申请，并经本公司审核同意，现对<xsl:value-of select="TranData/Body/PrintNode/PolNumber"/>号保险单作出如下批注:</AttachmentData> 
				</Attachment>
				<Attachment id="Attachment_4">
					<AttachmentData />
				</Attachment>
				<Attachment id="Attachment_5">
					<AttachmentData>    现对本保单进行<xsl:value-of select="TranData/Body/PrintNode/EdorName"/>处理，保险合同效力中止，本次申请共退金额人民币<xsl:value-of select="TranData/Body/PrintNode/FinActivityGrossAmt"/>元。</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_6">
					<AttachmentData>    险种名称</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_7">
					<AttachmentData><xsl:text>    </xsl:text><xsl:value-of select="TranData/Body/PrintNode/RiskName"/></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_8">
					<AttachmentData />
				</Attachment>
				<Attachment id="Attachment_9">
					<AttachmentData>    本次领款方式:银行转账</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_10">
					<AttachmentData>    银行:<xsl:value-of select="TranData/Body/PrintNode/BankName"/></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_11">
					<AttachmentData>    账户名:<xsl:value-of select="TranData/Body/PrintNode/AccName"/>                 银行账号:<xsl:value-of select="TranData/Body/PrintNode/BankAccNo"/></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_12">
					<AttachmentData>    特此批注。</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_13">
					<AttachmentData />
				</Attachment>
				<Attachment id="Attachment_14">
					<AttachmentData>    打印日期:<xsl:value-of select="substring(TranData/Body/PrintNode/TranDate,0,5)" />年<xsl:value-of select="substring(TranData/Body/PrintNode/TranDate,6,2)" />月<xsl:value-of select="substring(TranData/Body/PrintNode/TranDate,9,2)" />日</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_15">
					<AttachmentData>                                           (此页以下空白)</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_16">
					<AttachmentData>------------------------------------------------------------------------------------------------</AttachmentData>
				</Attachment>
 			</FormInstance>
		</OLifE>
   </TXLifeResponse> 
</TXLife>


</xsl:template>

</xsl:stylesheet>