<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:template match="/">
<TXLife>
<xsl:copy-of select="TranData/Head" />
	<TXLifeResponse>
		<!-- ���׳ɹ��ŷ������½ڵ� -->
		<OLifE>
			<Holding>
				<Policy>
					<PolNumber><xsl:value-of select="/TranData/Body/PolNumber"/></PolNumber>
				</Policy>
			</Holding>
			<FormInstance id="Form_1">
				<!-- ��֤���� -->
				<FormName>3</FormName>
				<!-- ������ -->
				<DocumentControlNumber><xsl:value-of select="/TranData/Body/DocumentControlNumber"/></DocumentControlNumber>
				<!-- ������Ч���� -->
				<FormInstanceStatusDate><xsl:value-of select="/TranData/Body/TranDate"/></FormInstanceStatusDate>
				<!-- ���ģ����ظ��� -->
				<Attachment id="Attachment_Form_0">
					<AttachmentData></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_10">
					<AttachmentData>                                         ��ȫ����</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_2">
					<AttachmentData>�𾴵�<xsl:value-of select="/TranData/Body/CustomerName"/><xsl:value-of select="/TranData/Body/CustomerSex"/>:</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_3">
					<AttachmentData>    �ȸ����������룬�ֶ�<xsl:value-of select="/TranData/Body/PolNumber"/>�ű��յ���������ע��</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_4">
					<AttachmentData></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_5">
					<AttachmentData>    �ͻ����ϱ��</AttachmentData>
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
					<AttachmentData>    ������ע��Чʱ�䣺<xsl:value-of select="substring(/TranData/Body/TranDate,0,5)" />��<xsl:value-of select="substring(/TranData/Body/TranDate,6,2)" />��<xsl:value-of select="substring(/TranData/Body/TranDate,9,2)" />��</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_10">
					<AttachmentData></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_11">
					<AttachmentData>    �ش���ע��</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_12">
					<AttachmentData></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_13">
					<AttachmentData>    ��ȫ������Ϣ��</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_14">
					<AttachmentData>    �������룺<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/DocumentControlNumber,30)"/>Ͷ����������<xsl:value-of select="/TranData/Body/CustomerName"/></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_15">
					<AttachmentData>    �������ƣ�<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/ManageName,30)"/>���������Ա������<xsl:value-of select="/TranData/Body/JOperator"/></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_16">
					<AttachmentData>    ��ӡ���ڣ�<xsl:value-of select="substring(/TranData/Body/TranDate,0,5)" />��<xsl:value-of select="substring(/TranData/Body/TranDate,6,2)" />��<xsl:value-of select="substring(/TranData/Body/TranDate,9,2)" />��</AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_17">
					<AttachmentData></AttachmentData>
				</Attachment>
				<Attachment id="Attachment_Form_18">
					<AttachmentData>                                            (��ҳ���¿հ�)</AttachmentData>
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