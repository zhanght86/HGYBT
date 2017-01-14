<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:mxh="http://www.openuri.org/"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java" version="1.0">
	<xsl:output method="text"  omit-xml-declaration="yes"  indent="no" />

	<xsl:template match="/">
		<xsl:call-template name="tran_servicetype">
			<xsl:with-param name="agInsBtchBagNm">
				<xsl:value-of select="/Root/Head/AgIns_BtchBag_Nm" />
			</xsl:with-param>
		</xsl:call-template><xsl:text>,</xsl:text><!-- ���ձ�־ -->
		<xsl:value-of select="/Root/Head/Cur_Btch_Dtl_TDnum" /><xsl:text>,</xsl:text><!-- �̻�ID -->
		<xsl:value-of select="substring(/Root/Head/AgIns_BtchBag_Nm,10,8)" /><xsl:text>,</xsl:text><!-- �ύ���� -->
		<xsl:value-of select="/Root/Head/Cur_Btch_Dtl_TDnum" /><xsl:text>,</xsl:text><!-- �ܼ�¼�� -->
		<xsl:value-of select="/Root/Head/Cur_Btch_Dtl_TAmt" /><xsl:text>,</xsl:text><!-- �ܽ�� -->
		<xsl:value-of select="/Root/Head/AgIns_BtchBag_TpCd"/><xsl:text>,</xsl:text><!-- ҵ������ -->
<xsl:text>
</xsl:text>
		<xsl:for-each select="/Root/Detail_List/Detail">
			<xsl:value-of select="Btch_Dtl_SN"/><xsl:text>,</xsl:text><!-- ��¼��� Y -->
			<xsl:text>,</xsl:text><!--ͨ��֧���û����  -->
			<xsl:text>105,</xsl:text><!-- ���д��� -->
			<xsl:text>00,</xsl:text><!-- �ʺ����� -->
			<xsl:value-of select="Cst_AccNo"/><xsl:text>,</xsl:text><!-- �˺� Y -->
			<xsl:value-of select="Cst_Nm"/><xsl:text>,</xsl:text><!--�˻��� Y -->
			<xsl:text>,</xsl:text><!-- ����������ʡ -->
			<xsl:text>,</xsl:text><!-- ������������ -->
			<xsl:text>�й���������,</xsl:text><!--����������  -->
			<xsl:text>0,</xsl:text><!-- �˻����� -->
			<xsl:value-of select="Amt"/><xsl:text>,</xsl:text><!-- ��� Y -->
			<xsl:text>CNY,</xsl:text><!-- �������� -->
			<xsl:text>,</xsl:text><!-- Э��� -->
			<xsl:text>,</xsl:text><!-- Э���û���� -->
			<xsl:text>0,</xsl:text><!-- ����֤������ -->
			<xsl:text>,</xsl:text><!-- ֤���� -->
			<xsl:text>,</xsl:text><!-- �ֻ���/С��ͨ -->
			<xsl:text>,</xsl:text><!-- �Զ����û��� -->
			<xsl:text>,</xsl:text><!-- ��ע -->
			<xsl:call-template name="tran_feedbackcode">
				<xsl:with-param name="feedbackcode">
					<xsl:value-of select="Hst_Rsp_Cd"/>
				</xsl:with-param>
			</xsl:call-template><xsl:text>,</xsl:text><!-- ������ -->
			<xsl:value-of select="Hst_Rsp_Inf"/><xsl:text>,</xsl:text><!-- ԭ�� -->
<xsl:text>
</xsl:text>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="tran_servicetype">
		<xsl:param name="agInsBtchBagNm" ></xsl:param>
		<xsl:variable name="subStr"  select="substring($agInsBtchBagNm,3,1)"></xsl:variable>
		<xsl:choose>
			<xsl:when test="$subStr='0'">S</xsl:when><!-- ���� -->
			<xsl:when test="$subStr='1'">F</xsl:when><!-- ���� -->
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="tran_feedbackcode">
		<xsl:param name="feedbackcode"></xsl:param>
		<xsl:choose>
			<xsl:when test="$feedbackcode='00000'">0000</xsl:when><!-- ����ɹ� -->
			<xsl:when test="$feedbackcode='E8208'"></xsl:when><!-- һ��ͨ�иñұ�/�����ѽ��� -->
			<xsl:when test="$feedbackcode='E8201'"></xsl:when><!-- �ʻ������� -->
			<xsl:when test="$feedbackcode='E3150'"></xsl:when><!-- �ʻ������� -->
			<xsl:when test="$feedbackcode='E5502'"></xsl:when><!-- �����ѹ�ʧ -->
			<xsl:when test="$feedbackcode='E4501'"></xsl:when><!-- �ʻ������� -->
			<xsl:when test="$feedbackcode='E3001'"></xsl:when><!-- �ұ�򳮻������� -->
			<xsl:when test="$feedbackcode='E4500'"></xsl:when><!-- �ʻ������� -->
			<xsl:when test="$feedbackcode='E5000'">3016</xsl:when><!-- �����ѹ�ʧ -->
			<xsl:when test="$feedbackcode='E3551'">3017</xsl:when><!-- �ʻ��Ѷ��� -->
			<xsl:when test="$feedbackcode='E4500'">3017</xsl:when><!-- �ʻ��Ѷ��� -->
			<xsl:when test="$feedbackcode='E5002'">3006</xsl:when><!-- ���ѹ�ʧ -->
			<xsl:when test="$feedbackcode='E7102'">3008</xsl:when><!-- ���� -->
			<xsl:when test="$feedbackcode='E3266'">3031</xsl:when><!-- �ͻ��������ʺŲ��� -->
			<xsl:when test="$feedbackcode='E1408'"></xsl:when><!-- �ʺ����Ͳ��� -->
			<xsl:when test="$feedbackcode='E8301'"></xsl:when><!-- ר���˻�����ժҪ������� -->
			<xsl:when test="$feedbackcode='E1085'"></xsl:when><!-- �ʻ������� -->
			<xsl:when test="$feedbackcode='E3540'"></xsl:when><!-- �ʻ������� -->
			<xsl:when test="$feedbackcode='E3556'"></xsl:when><!-- ��״̬������ -->
			<xsl:when test="$feedbackcode='SHK02'"></xsl:when><!-- �Ƿ��˺� -->
			<xsl:when test="$feedbackcode='SDB01'"></xsl:when><!-- �Ƿ��˺� -->
			<xsl:when test="$feedbackcode='E9999'"></xsl:when><!-- ����ʧ�ܣ�XXX -->
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
