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
		</xsl:call-template><xsl:text>,</xsl:text><!-- 代收标志 -->
		<xsl:value-of select="/Root/Head/Cur_Btch_Dtl_TDnum" /><xsl:text>,</xsl:text><!-- 商户ID -->
		<xsl:value-of select="substring(/Root/Head/AgIns_BtchBag_Nm,10,8)" /><xsl:text>,</xsl:text><!-- 提交日期 -->
		<xsl:value-of select="/Root/Head/Cur_Btch_Dtl_TDnum" /><xsl:text>,</xsl:text><!-- 总记录数 -->
		<xsl:value-of select="/Root/Head/Cur_Btch_Dtl_TAmt" /><xsl:text>,</xsl:text><!-- 总金额 -->
		<xsl:value-of select="/Root/Head/AgIns_BtchBag_TpCd"/><xsl:text>,</xsl:text><!-- 业务类型 -->
<xsl:text>
</xsl:text>
		<xsl:for-each select="/Root/Detail_List/Detail">
			<xsl:value-of select="Btch_Dtl_SN"/><xsl:text>,</xsl:text><!-- 记录序号 Y -->
			<xsl:text>,</xsl:text><!--通联支付用户编号  -->
			<xsl:text>105,</xsl:text><!-- 银行代码 -->
			<xsl:text>00,</xsl:text><!-- 帐号类型 -->
			<xsl:value-of select="Cst_AccNo"/><xsl:text>,</xsl:text><!-- 账号 Y -->
			<xsl:value-of select="Cst_Nm"/><xsl:text>,</xsl:text><!--账户名 Y -->
			<xsl:text>,</xsl:text><!-- 开户行所在省 -->
			<xsl:text>,</xsl:text><!-- 开户行所在市 -->
			<xsl:text>中国建设银行,</xsl:text><!--开户行名称  -->
			<xsl:text>0,</xsl:text><!-- 账户类型 -->
			<xsl:value-of select="Amt"/><xsl:text>,</xsl:text><!-- 金额 Y -->
			<xsl:text>CNY,</xsl:text><!-- 货币类型 -->
			<xsl:text>,</xsl:text><!-- 协议号 -->
			<xsl:text>,</xsl:text><!-- 协议用户编号 -->
			<xsl:text>0,</xsl:text><!-- 开户证件类型 -->
			<xsl:text>,</xsl:text><!-- 证件号 -->
			<xsl:text>,</xsl:text><!-- 手机号/小灵通 -->
			<xsl:text>,</xsl:text><!-- 自定义用户号 -->
			<xsl:text>,</xsl:text><!-- 备注 -->
			<xsl:call-template name="tran_feedbackcode">
				<xsl:with-param name="feedbackcode">
					<xsl:value-of select="Hst_Rsp_Cd"/>
				</xsl:with-param>
			</xsl:call-template><xsl:text>,</xsl:text><!-- 反馈码 -->
			<xsl:value-of select="Hst_Rsp_Inf"/><xsl:text>,</xsl:text><!-- 原因 -->
<xsl:text>
</xsl:text>
		</xsl:for-each>
	</xsl:template>
	<xsl:template name="tran_servicetype">
		<xsl:param name="agInsBtchBagNm" ></xsl:param>
		<xsl:variable name="subStr"  select="substring($agInsBtchBagNm,3,1)"></xsl:variable>
		<xsl:choose>
			<xsl:when test="$subStr='0'">S</xsl:when><!-- 批扣 -->
			<xsl:when test="$subStr='1'">F</xsl:when><!-- 批付 -->
			<xsl:otherwise></xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="tran_feedbackcode">
		<xsl:param name="feedbackcode"></xsl:param>
		<xsl:choose>
			<xsl:when test="$feedbackcode='00000'">0000</xsl:when><!-- 处理成功 -->
			<xsl:when test="$feedbackcode='E8208'"></xsl:when><!-- 一本通中该币别/钞汇已结清 -->
			<xsl:when test="$feedbackcode='E8201'"></xsl:when><!-- 帐户不正常 -->
			<xsl:when test="$feedbackcode='E3150'"></xsl:when><!-- 帐户不正常 -->
			<xsl:when test="$feedbackcode='E5502'"></xsl:when><!-- 密码已挂失 -->
			<xsl:when test="$feedbackcode='E4501'"></xsl:when><!-- 帐户不正常 -->
			<xsl:when test="$feedbackcode='E3001'"></xsl:when><!-- 币别或钞汇鉴别错误 -->
			<xsl:when test="$feedbackcode='E4500'"></xsl:when><!-- 帐户不正常 -->
			<xsl:when test="$feedbackcode='E5000'">3016</xsl:when><!-- 存折已挂失 -->
			<xsl:when test="$feedbackcode='E3551'">3017</xsl:when><!-- 帐户已冻结 -->
			<xsl:when test="$feedbackcode='E4500'">3017</xsl:when><!-- 帐户已冻结 -->
			<xsl:when test="$feedbackcode='E5002'">3006</xsl:when><!-- 卡已挂失 -->
			<xsl:when test="$feedbackcode='E7102'">3008</xsl:when><!-- 余额不足 -->
			<xsl:when test="$feedbackcode='E3266'">3031</xsl:when><!-- 客户名称与帐号不符 -->
			<xsl:when test="$feedbackcode='E1408'"></xsl:when><!-- 帐号类型不符 -->
			<xsl:when test="$feedbackcode='E8301'"></xsl:when><!-- 专项账户存入摘要代码错误 -->
			<xsl:when test="$feedbackcode='E1085'"></xsl:when><!-- 帐户不正常 -->
			<xsl:when test="$feedbackcode='E3540'"></xsl:when><!-- 帐户不正常 -->
			<xsl:when test="$feedbackcode='E3556'"></xsl:when><!-- 卡状态不正常 -->
			<xsl:when test="$feedbackcode='SHK02'"></xsl:when><!-- 非法账号 -->
			<xsl:when test="$feedbackcode='SDB01'"></xsl:when><!-- 非法账号 -->
			<xsl:when test="$feedbackcode='E9999'"></xsl:when><!-- 整批失败：XXX -->
		</xsl:choose>
	</xsl:template>
</xsl:stylesheet>
