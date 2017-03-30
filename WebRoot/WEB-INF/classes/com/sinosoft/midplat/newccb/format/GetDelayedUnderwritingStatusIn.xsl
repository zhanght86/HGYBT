<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
<xsl:template match="TX">
<TranData>
    <Head>
      <ServiceId>66</ServiceId>
      <TranCom><xsl:value-of select="Head/TranCom" /></TranCom>
      <LocalID><xsl:value-of select="TX_HEADER/LocalID" /></LocalID>
      <RemoteID><xsl:value-of select="TX_HEADER/remoteID" /></RemoteID>
    </Head>
	<BaseInfo>
		<!--���б�� -->
		<BankCode><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" /></BankCode>
		<!--�������� -->
		<BankDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(TX_HEADER/SYS_REQ_TIME,0,8)" /></BankDate>
		<!--����ʱ�� -->
		<BankTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(TX_HEADER/SYS_REQ_TIME,8,14)" /></BankTime>
		<!-- һ�����к�(�������) -->
		<ZoneNo>99999</ZoneNo>
		<!-- ���տͻ������ṩ�������(��֧���) -->
		<BrNo>99999</BrNo>
		<!--��Ա��� -->
		<TellerNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" /></TellerNo>
		<!--#������ˮ�� -->
		<TransrNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" /></TransrNo>
		<!--�������� -->
		<FunctionFlag><xsl:value-of select="Head/FuncFlag" /></FunctionFlag>
		<!--���չ�˾��� -->
		<InsuID><xsl:value-of select="Head/InsuID" /></InsuID>
		<!--һ�����к�-->
		<BankBrNo_Lv1><xsl:value-of select="TX_BODY/ENTITY/APP_ENTITY/Lv1_Br_No" /></BankBrNo_Lv1>
		<InNoDoc><xsl:value-of select="Head/InNoDoc" /></InNoDoc>
	</BaseInfo>
	<LCCont>
      <!--ѭ��������Ϣ-->
	  <ContList>
	    <!--�������� -->
		<ContNum><xsl:value-of select="TX_BODY/ENTITY/APP_ENTITY/NRlTmInsPlyDtlTot_Num" /></ContNum>
		<xsl:for-each select="TX_BODY/ENTITY/APP_ENTITY/Detail_List/Detail">
	    <ContInfo>
		  <!-- Ͷ�������� -->
		  <ProposalContNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(Ins_BillNo)" /></ProposalContNo>
	    </ContInfo>
	    </xsl:for-each>
	  </ContList>
	</LCCont>
</TranData>
</xsl:template>
</xsl:stylesheet>