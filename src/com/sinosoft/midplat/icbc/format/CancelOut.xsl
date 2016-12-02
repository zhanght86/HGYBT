<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
<TXLife>
	
	<xsl:copy-of select="TranData/Head" />
	<TXLifeResponse>
	<TransRefGUID><xsl:value-of select="TranData/Body/TranNo"/></TransRefGUID>
		<!-- ������ˮ�� -->
		<TransType><xsl:value-of select="TranData/Body/FuncFlag"/></TransType>
		<!-- ������/�����־ -->
 	 <TransExeDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></TransExeDate>
 	 <TransExeTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></TransExeTime>
	<TransMode>8</TransMode>
		<!-- ����ģʽ -->
		<TransResult>
		<ResultCode><xsl:value-of select ="Head/Flag"/></ResultCode>
			<ResultInfo>
				<!-- ������Ϣ�������ʾ -->
				<ResultInfoDesc><xsl:value-of select ="Head/Desc"/></ResultInfoDesc>
			</ResultInfo>
		</TransResult>
	  	<OLifE>
			<FormInstance id="Form_1">
				<!-- ��֤���� -->
				<FormName>3</FormName>
				<!-- ������ -->
				<DocumentControlNumber>123456123</DocumentControlNumber>
				<!-- ���ģ����ظ��� -->
				<Attachment id="Attachment_1">
					<!-- ��������1����һ�У� -->
					<AttachmentData>content</AttachmentData>
				</Attachment>
			</FormInstance>
		</OLifE>
		<OLifEExtension VendorCode="1">
			<TransNo></TransNo>
			<!-- ���״��� -->
			<RcptId></RcptId>
			<!-- ƽ��ǰ����ˮ�� -->
		</OLifEExtension>
	 </TXLifeResponse>
</TXLife>
</xsl:template>

</xsl:stylesheet>