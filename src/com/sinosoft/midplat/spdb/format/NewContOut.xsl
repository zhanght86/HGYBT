<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:template match="TranData">
<!--�������� ������ʽ�� -->
<RETURN>
	<!-- Ӧ��״̬ -->
	<ACKSTS>0</ACKSTS>
	<!-- ״̬���� -->
	<STSDESC>����</STSDESC>
	<!-- ����ɹ�����������ڵ� -->
	<xsl:if test="Head/Flag='0'"> 
		<!-- ����Ӧ���Ŀ -->
		<BUSI>
			<!-- ���׿�Ŀ -->
			<SUBJECT>1</SUBJECT>
			<!-- ������ˮ�� -->
			<TRANS></TRANS>
			<!-- ����������Ϣ�� -->
			<CONTENT>
				<!-- Ͷ����������Ϣ -->
				<MAIN>
					<!-- ���ڱ��ѣ���д�� -->
					<PREMC><xsl:value-of select="substring(Body/PremText,4)" /></PREMC>
					<!-- ���ڱ��� -->
					<PREM><xsl:value-of select="Body/Prem" /></PREM>
				</MAIN>
				<!-- Ͷ������ -->
				<APPNO><xsl:value-of select="substring(Body/ProposalPrtNo,1,13)" /></APPNO>
				<!-- ������ -->
				<POL><xsl:value-of select="Body/ContNo" /></POL>
				<!-- �ܱ�ԭ����� -->
				<REJECT_CODE>0000</REJECT_CODE>
				<!-- �ܱ�ԭ��˵�� -->
				<REJECT_DESC>���׳ɹ�</REJECT_DESC>
				<!-- �����б� -->
				<xsl:apply-templates select="Body" />
				<!-- �ֽ��ֵ -->
				<xsl:choose>
					<xsl:when test="boolean(//CashValues)">
						<xsl:apply-templates select="Body/Risk[RiskCode = MainRiskCode]/CashValues" />
					</xsl:when>
					<xsl:otherwise>
						<VT>
							<VTI></VTI>
						</VT>
					</xsl:otherwise>
				</xsl:choose>
			</CONTENT>
		</BUSI>
	</xsl:if>
	<!-- ���ʧ�ܣ���������ڵ� -->
	<xsl:if test="Head/Flag!='0'">
		<!-- ����Ӧ���Ŀ -->
		<BUSI>
			<!-- ����Ӧ���Ŀ -->
			<SUBJECT>1</SUBJECT>
			<!-- ������ˮ�� -->
			<TRANS></TRANS>
			<!-- ����������Ϣ�� -->
			<CONTENT>
				<!-- �ܱ�ԭ����� -->
				<REJECT_CODE>1234</REJECT_CODE>
				<!-- �ܱ�ԭ��˵�� -->
				<REJECT_DESC><xsl:value-of select="Head/Desc" /></REJECT_DESC>
			</CONTENT>
		</BUSI>
	</xsl:if>
</RETURN>
</xsl:template>

<!-- ����ģ�� -->
<xsl:template name="Risks" match="Body">
	<!-- �����б� -->
	<PTS>
		<xsl:for-each select="Risk">
			<xsl:if test="RiskCode = MainRiskCode">
				<!-- ���� -->
				<PT>
					<!-- ���ִ��� -->
					<ID><xsl:value-of select="RiskCode"/></ID>
					<!-- Ͷ������ -->
					<UNIT><xsl:value-of select="Mult"/></UNIT>
					<!--  �ɷ���������-->
					<CRG>
						<xsl:choose>
							<xsl:when test="PayIntv = '0'">1</xsl:when>
							<xsl:when test="PayEndYearFlag = 'A'">6</xsl:when><!-- ����ĳȷ������  -->
							<xsl:when test="PayEndYearFlag = 'M'">5</xsl:when><!-- �½� -->
							<xsl:when test="PayEndYearFlag = 'Y'">2</xsl:when><!-- ��� -->
						</xsl:choose>			
					</CRG>	
					<!-- �������� -->
					<NAME><xsl:value-of select="RiskName" /></NAME>
					<!-- �������� -->
					<PERIOD><xsl:value-of select="InsuYear" /></PERIOD>
					<!-- ����������� -->
					<DRAW_T><xsl:value-of select="GetYear" /></DRAW_T>
					<!-- ���ս�� -->
					<AMT><xsl:value-of select="Amnt" /></AMT>
					<!-- Ͷ����� -->
					<PREM><xsl:value-of select="Prem" /></PREM>
				</PT>
			</xsl:if>
		</xsl:for-each>
	</PTS>
</xsl:template>

<!-- �ּ�ģ�� -->
<xsl:template name="CashValues" match="Body/Risk[RiskCode = MainRiskCode]/CashValues">
	<!-- �ֽ��ֵ�� -->
	<VT>
		<!-- ѭ���ּ� -->
		<xsl:for-each select="CashValue">
			<!-- �ֽ��ֵ���� -->
			<VTI>
				<!-- ������� -->
				<LIVE/>
				<!-- ������ʱ��ս� -->
				<ILL/>
				<!-- ��� -->
				<YEAR><xsl:value-of select="EndYear" /></YEAR>
				<!-- ��ĩ -->
				<END/>
				<!-- ��ĩ�ֽ��ֵ -->
				<CASH><xsl:value-of select="Cash" /></CASH>
				<!-- ������ʱ��ս� -->
				<ACI/>
			</VTI>
		</xsl:for-each>
	</VT>
</xsl:template>
</xsl:stylesheet>