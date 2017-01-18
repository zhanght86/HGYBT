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
					<!-- �������� -->
			         <!-- <FuncFlag><xsl:value-of select="TX_HEADER/SYS_TX_CODE" /></FuncFlag>  -->	
					<!-- ����id 
					<ServiceId>1</ServiceId> -->
					
					<!-- �����������淽ʽ������Ϊ�˲������Լ��������ֶ�-->
					
					<!-- �������� ������������-->
					<!-- ����id ������������-->
					<!-- ���ж�ip[�Ǳ���] ������������-->
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
			<FileName><xsl:value-of select="//AgIns_BtchBag_Nm" /><xsl:text>_RESULT.xml</xsl:text></FileName>
			<BatFlag><xsl:value-of select="//AgInsBtchBagPcsg_StCd" /></BatFlag>
			<Num><xsl:value-of select="//Cur_Btch_Dtl_TDnum" /></Num><!--��ǰ����ϸ�ܱ���-->
			<SumAmt><xsl:value-of select="//Cur_Btch_Dtl_TAmt" /></SumAmt><!--��ǰ����ϸ�ܽ��-->
		</Body>
	</xsl:template>
	

</xsl:stylesheet>
