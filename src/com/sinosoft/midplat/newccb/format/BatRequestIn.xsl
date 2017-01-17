<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

	<xsl:template match="/">
		<TranData> 
			<Head>
				    <!--�������� -->
					<TranDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" /></TranDate>
					<!--����ʱ�� -->
					<TranTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" /></TranTime>
					<!-- �������� -->
					<NodeNo><xsl:value-of select="//TX/TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" /></NodeNo>
					<!-- ���б��� -->
					<BankCode>0104</BankCode>
					<!--��Ա�� -->
					<TellerNo><xsl:value-of select="//TX/TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" /></TellerNo>
					<!-- ������ˮ�� -->
					<TranNo><xsl:value-of select="//TX/TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" /></TranNo>
					<!-- ���ж�ip[�Ǳ���] 
					<ClientIp>127.0.0.1</ClientIp> -->
					<!-- �������� -->
					<TranCom>03</TranCom> 
					<!-- ���ذ�ȫ�ڵ�� -->
					<LocalID><xsl:value-of select="//TX/TX_HEADER/LocalID" /></LocalID>
					<!-- ���а�ȫ�ڵ�� -->
					<RemoteID><xsl:value-of select="//TX/TX_HEADER/remoteID" /></RemoteID>
					<LocalID><xsl:value-of select="//TX/TX_HEADER/LocalID" /></LocalID>
                    <RemoteID><xsl:value-of select="//TX/TX_HEADER/remoteID" /></RemoteID>
				    <xsl:copy-of select="//TX/Head/*"/>
			  </Head>
			<!-- ������ -->
			<xsl:apply-templates select="//TX/TX_BODY/ENTITY/APP_ENTITY" />
		</TranData>
	</xsl:template>

	<!-- ������ -->
	<xsl:template match="APP_ENTITY">
		<Body>
			<xsl:variable name="FileName" select="//AgIns_BtchBag_Nm" />
				<FileName>
					<xsl:value-of select="$FileName" />
				</FileName>
				<!-- �����ļ��� -->
				<Type>
					<xsl:value-of select="substring($FileName, 3, 1)" />
				</Type>
				<OrderNo>
					<xsl:value-of select="substring($FileName, 18, 2)" />
				</OrderNo>
		</Body>
	</xsl:template>
	

</xsl:stylesheet>

