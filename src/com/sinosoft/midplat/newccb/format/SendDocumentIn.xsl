<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output method="xml" indent="yes"/>
		<xsl:template match="TX">
			<TranData>
				<Head>
				     <!--�������� -->
					<TranDate><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,0,8)" /></TranDate>
					<!--����ʱ�� -->
					<TranTime><xsl:value-of select="java:com.sinosoft.midplat.newccb.util.NewCcbFormatUtil.getTimeAndDate(//TX/TX_HEADER/SYS_REQ_TIME,8,14)" /></TranTime>
					<!-- �������� -->
					<NodeNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCBIns_ID" /></NodeNo>
					<!-- ���б��� -->
					<BankCode>0104</BankCode>
					<!--��Ա�� -->
					<TellerNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/CCB_EmpID" /></TellerNo>
					<!-- ������ˮ�� -->
					<TranNo><xsl:value-of select="TX_BODY/ENTITY/COM_ENTITY/SvPt_Jrnl_No" /></TranNo>
				    <xsl:copy-of select="Head/*"/>
			  </Head>
			  <!-- ������ -->
			  <xsl:apply-templates select="TX_BODY/ENTITY/APP_ENTITY" />
		 </TranData>	
	</xsl:template>
			
		<xsl:template match="TX_BODY/ENTITY/APP_ENTITY">	
			<Body>
			<!-- �ܱ��� -->
				<Count><xsl:value-of select="Cur_Dtl_Num" /></Count>
				<xsl:for-each select="Detail_List/Detail">
				      <Detail>
						 <!-- ��֤���� -->
						 <CardType></CardType> 
						 <!-- ��֤�� -->
					     <CardNo><xsl:value-of select="Ins_IBVoch_ID" /></CardNo> 
					     <!-- ��֤״̬ -->
					     <CardState>
						     <xsl:call-template name="tran_CardState">
								<xsl:with-param name="CardState">
									<xsl:value-of select="IpOpR_Crcl_StCd" />
								</xsl:with-param>
							</xsl:call-template>
					     </CardState> 
					     <!-- ��֤������(�����š���ȫ�ŵ�) -->
					     <OtherNo></OtherNo> 
					     <!-- ����[�Ǳ��룬��Щ���д�] -->
					     <AgentCom><xsl:value-of select="CCBIns_ID" /></AgentCom> 
					     <!-- ��Ա����[�Ǳ��룬��Щ���д�] -->
					     <TellerNo></TellerNo> 
					     <!-- ������ˮ��[�Ǳ��룬��Щ���д�] -->
					     <TranNo><xsl:value-of select="RqPtTcNum" /></TranNo> 
				      </Detail>
			      </xsl:for-each>
  			</Body>
		</xsl:template>

	<!-- ��֤����״̬-->
	<xsl:template name="tran_CardState">
		<xsl:param name="CardState">4</xsl:param>
		<xsl:if test="$CardState = '03'">4</xsl:if>
		<xsl:if test="$CardState = '04'">6</xsl:if>
		<!-- ����״̬
			1�������    ���ڴ������У���Ҫ�������ȷ�ϡ�
			2������⣨��棩    ���ȷ�Ϻ�Ĵ��������(���)��
			3���ѷ���δ����    ������B(����)��D(������)�µ�δ������֤��
			4���Զ�����    ����ʹ�õĵ�֤����ϵͳ�ڽ���ʱ�Զ����֡�
			5���ֹ�����    ����ʹ�õĵ�֤���˹�������
			6��ʹ������
			7��ͣ������
			8������    ����ʹ�ý�ֹ���ڵĵ�֤����ϵͳ�Զ���Ϊ��״̬��
			9����ʧ
			10����ʧ
			11������
		 -->
	</xsl:template>

</xsl:stylesheet>
