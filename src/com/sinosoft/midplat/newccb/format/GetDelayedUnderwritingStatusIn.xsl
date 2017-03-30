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
		<!--银行编号 -->
		<BankCode><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" /></BankCode>
		<!--交易日期 -->
		<BankDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(TX_HEADER/SYS_REQ_TIME,0,8)" /></BankDate>
		<!--交易时间 -->
		<BankTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(TX_HEADER/SYS_REQ_TIME,8,14)" /></BankTime>
		<!-- 一级分行号(地区编号) -->
		<ZoneNo>99999</ZoneNo>
		<!-- 保险客户名单提供地区编号(分支编号) -->
		<BrNo>99999</BrNo>
		<!--柜员编号 -->
		<TellerNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" /></TellerNo>
		<!--#服务方流水号 -->
		<TransrNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" /></TransrNo>
		<!--交易类型 -->
		<FunctionFlag><xsl:value-of select="Head/FuncFlag" /></FunctionFlag>
		<!--保险公司编号 -->
		<InsuID><xsl:value-of select="Head/InsuID" /></InsuID>
		<!--一级分行号-->
		<BankBrNo_Lv1><xsl:value-of select="TX_BODY/ENTITY/APP_ENTITY/Lv1_Br_No" /></BankBrNo_Lv1>
		<InNoDoc><xsl:value-of select="Head/InNoDoc" /></InNoDoc>
	</BaseInfo>
	<LCCont>
      <!--循环保单信息-->
	  <ContList>
	    <!--保单个数 -->
		<ContNum><xsl:value-of select="TX_BODY/ENTITY/APP_ENTITY/NRlTmInsPlyDtlTot_Num" /></ContNum>
		<xsl:for-each select="TX_BODY/ENTITY/APP_ENTITY/Detail_List/Detail">
	    <ContInfo>
		  <!-- 投保单号码 -->
		  <ProposalContNo><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(Ins_BillNo)" /></ProposalContNo>
	    </ContInfo>
	    </xsl:for-each>
	  </ContList>
	</LCCont>
</TranData>
</xsl:template>
</xsl:stylesheet>