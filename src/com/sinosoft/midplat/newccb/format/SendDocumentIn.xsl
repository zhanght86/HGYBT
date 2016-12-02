<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
		<xsl:template match="TX">
			<TranData>
				<Head>
				     <!--交易日期 -->
					<TranDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" /></TranDate>
					<!--交易时间 -->
					<TranTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" /></TranTime>
					<!-- 银行网点 -->
					<NodeNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" /></NodeNo>
					<!-- 银行编码 -->
					<BankCode>0104</BankCode>
					<!--柜员号 -->
					<TellerNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" /></TellerNo>
					<!-- 交易流水号 -->
					<TranNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" /></TranNo>
				    <xsl:copy-of select="Head/*"/>
			  </Head>
			  <!-- 报文体 -->
			  <xsl:apply-templates select="TX_BODY/ENTITY/APP_ENTITY" />
		 </TranData>	
	</xsl:template>
			
		<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">	
			<Body>
			<!-- 总比数 -->
				<Count><xsl:value-of select="Cur_Dtl_Num" /></Count>
				<xsl:for-each select="Detail_List/Detail">
				      <Detail>
						 <!-- 单证类型 -->
						 <CardType></CardType> 
						 <!-- 单证号 -->
					     <CardNo><xsl:value-of select="Ins_IBVoch_ID" /></CardNo> 
					     <!-- 单证状态 -->
					     <CardState>
						     <xsl:call-template name="tran_CardState">
								<xsl:with-param name="CardState">
									<xsl:value-of select="IpOpR_Crcl_StCd" />
								</xsl:with-param>
							</xsl:call-template>
					     </CardState> 
					     <!-- 单证关联号(保单号、保全号等) -->
					     <OtherNo></OtherNo> 
					     <!-- 机构[非必须，有些银行传] -->
					     <AgentCom><xsl:value-of select="CCBIns_ID" /></AgentCom> 
					     <!-- 柜员代码[非必须，有些银行传] -->
					     <TellerNo></TellerNo> 
					     <!-- 交易流水号[非必须，有些银行传] -->
					     <TranNo><xsl:value-of select="RqPtTcNum" /></TranNo> 
				      </Detail>
			      </xsl:for-each>
  			</Body>
		</xsl:template>

	<!-- 单证类型状态-->
	<xsl:template name="tran_CardState">
		<xsl:param name="CardState">4</xsl:param>
		<xsl:if test="$CardState = '03'">4</xsl:if>
		<xsl:if test="$CardState = '04'">6</xsl:if>
		<!-- 核心状态
			1、待入库    处于待入库池中，需要进行入库确认。
			2、已入库（库存）    入库确认后的处于已入库(库存)。
			3、已发放未核销    发放至B(部门)与D(代理人)下的未核销单证。
			4、自动缴销    交费使用的单证，由系统在交费时自动呈现。
			5、手工缴销    交费使用的单证，人工触发。
			6、使用作废
			7、停用作废
			8、逾期    超过使用截止日期的单证，由系统自动置为此状态。
			9、挂失
			10、遗失
			11、销毁
		 -->
	</xsl:template>

</xsl:stylesheet>
