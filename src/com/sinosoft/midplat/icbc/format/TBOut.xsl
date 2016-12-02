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
					<AttachmentData>                                         ����ƾ֤</AttachmentData> 
				</Attachment>
				<Attachment id="Attachment_2">
					<AttachmentData>�𾴵�<xsl:value-of select="TranData/Body/PrintNode/CustomerName"/><xsl:if test = "$tCustomerSex = '��'">����</xsl:if><xsl:if test = "$tCustomerSex = 'Ů'">Ůʿ</xsl:if>:</AttachmentData> 
				</Attachment>
				<Attachment id="Attachment_3">
					<AttachmentData>    �ȸ����������룬��������˾���ͬ�⣬�ֶ�<xsl:value-of select="TranData/Body/PrintNode/PolNumber"/>�ű��յ�����������ע:</AttachmentData> 
				</Attachment>
				<Attachment id="Attachment_4">
					<AttachmentData />
				</Attachment>
				<Attachment id="Attachment_5">
					<AttachmentData>    �ֶԱ���������<xsl:value-of select="TranData/Body/PrintNode/EdorName"/>�������պ�ͬЧ����ֹ���������빲�˽�������<xsl:value-of select="TranData/Body/PrintNode/FinActivityGrossAmt"/>Ԫ��</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_6">
					<AttachmentData>    ��������</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_7">
					<AttachmentData><xsl:text>    </xsl:text><xsl:value-of select="TranData/Body/PrintNode/RiskName"/></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_8">
					<AttachmentData />
				</Attachment>
				<Attachment id="Attachment_9">
					<AttachmentData>    ������ʽ:����ת��</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_10">
					<AttachmentData>    ����:<xsl:value-of select="TranData/Body/PrintNode/BankName"/></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_11">
					<AttachmentData>    �˻���:<xsl:value-of select="TranData/Body/PrintNode/AccName"/>                 �����˺�:<xsl:value-of select="TranData/Body/PrintNode/BankAccNo"/></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_12">
					<AttachmentData>    �ش���ע��</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_13">
					<AttachmentData />
				</Attachment>
				<Attachment id="Attachment_14">
					<AttachmentData>    ��ӡ����:<xsl:value-of select="substring(TranData/Body/PrintNode/TranDate,0,5)" />��<xsl:value-of select="substring(TranData/Body/PrintNode/TranDate,6,2)" />��<xsl:value-of select="substring(TranData/Body/PrintNode/TranDate,9,2)" />��</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_15">
					<AttachmentData>                                           (��ҳ���¿հ�)</AttachmentData>
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