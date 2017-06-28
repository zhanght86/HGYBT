<?xml version="1.0" encoding="GBK"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">

	<xsl:template match="/">
		<TX>
			<!-- 报文头 -->
			<TX_HEADER>
			     <!-- 报文头长度 -->
				<SYS_HDR_LEN></SYS_HDR_LEN>
			     <!-- 协议版本号 -->
				<SYS_PKG_VRSN>01</SYS_PKG_VRSN>
			     <!-- 报文总长度 -->
				<SYS_TTL_LEN></SYS_TTL_LEN>
			     <!-- 发送方安全节点编号 -->
				<SYS_SND_SEC_ID>510050</SYS_SND_SEC_ID>
			     <!-- 发起发安全节点编号  转换类中添加-->
				<SYS_REQ_SEC_ID></SYS_REQ_SEC_ID>
			     <!--服务种类-->
				<SYS_TX_TYPE>020000</SYS_TX_TYPE>
			     <!-- 全局事件跟踪号  转换类中添加-->
				<SYS_EVT_TRACE_ID></SYS_EVT_TRACE_ID>
			     <!-- 子交易序号  转换类中添加-->
				<SYS_SND_SERIAL_NO></SYS_SND_SERIAL_NO>
			     <!-- 应用报文格式类型 -->
				<SYS_PKG_TYPE>1</SYS_PKG_TYPE>    
			     <!-- 应用报文长度  转换类中添加-->
				<SYS_MSG_LEN></SYS_MSG_LEN>
			     <!-- 应用报文是否加密 -->
				<SYS_IS_ENCRYPTED>3</SYS_IS_ENCRYPTED>    
			     <!-- 应用报文加密方式 -->
				<SYS_ENCRYPT_TYPE>3</SYS_ENCRYPT_TYPE>
			     <!-- 应用报文压缩方式 -->
				<SYS_COMPRESS_TYPE>0</SYS_COMPRESS_TYPE>    
			     <!-- 捎带报文长度 -->
				<SYS_EMB_MSG_LEN>0</SYS_EMB_MSG_LEN>
			     <!-- 服务接受时间 转换类中添加-->
				<SYS_RECV_TIME></SYS_RECV_TIME>    
			     <!-- 服务响应时间  转换类中添加-->
				<SYS_RESP_TIME></SYS_RESP_TIME>
			     <!-- 报文状态类型  -->
				<SYS_PKG_STS_TYPE>01</SYS_PKG_STS_TYPE>    
				<xsl:if test = "/TranData/Head/Flag='0'">
			     <!-- 服务状态 -->
				<SYS_TX_STATUS>00</SYS_TX_STATUS>    
			     <!-- 服务响应码 -->
				<SYS_RESP_CODE>000000000000</SYS_RESP_CODE>    
				</xsl:if>
				<xsl:if test = "/TranData/Head/Flag !='0'">
			     <!-- 服务状态 -->
				<SYS_TX_STATUS>01</SYS_TX_STATUS>    
			     <!-- 服务响应码 -->
				<SYS_RESP_CODE></SYS_RESP_CODE>    
				</xsl:if>
			     <!-- 服务响应描述长度  转换类中添加-->
				<SYS_RESP_DESC_LEN></SYS_RESP_DESC_LEN>    
			     <!-- 服务响应描述 -->
				<SYS_RESP_DESC></SYS_RESP_DESC>    
				<xsl:copy-of select="Head/*"/>
			</TX_HEADER>
	
		<!-- 报文体 -->
			<TX_BODY>
	      			<COMMON>
	         			<FILE_LIST_PACK>
	         				<!-- 文件个数 -->
				            <FILE_NUM>0</FILE_NUM>
				            <!-- 文件处理方式 -->
				            <FILE_MODE>0</FILE_MODE>
				        	<!-- 文件节点 -->
				            <FILE_NODE></FILE_NODE>
				            <!-- 打包后后文件名 -->
				            <FILE_NAME_PACK></FILE_NAME_PACK>
				            <!-- 打包文件获取路径 -->
				            <FILE_PATH_PACK></FILE_PATH_PACK>
				            <!-- 文件信息 -->
				            <FILE_INFO>
				            <!-- 文件信息 -->
				               <FILE_NAME></FILE_NAME>
				            <!-- 文件路径 -->
				               <FILE_PATH></FILE_PATH>
				            </FILE_INFO>
	         			</FILE_LIST_PACK>
	      			</COMMON>
	      			<ENTITY>
			        	<APP_ENTITY>
			        		<!-- 代理保险套餐编号 -->
			        		<AgIns_Pkg_ID></AgIns_Pkg_ID>
			        		<!-- 险种个数 -->
			        		<Cvr_Num><xsl:value-of select="count(/TranData/Body/Risk/RiskCode)"/></Cvr_Num>
			        		<Bu_List>
				        		<xsl:for-each select="/TranData/Body/Risk">
				        			<Bu_Detail>
					        			<!-- 险种编号 -->
					        			<Cvr_ID><xsl:value-of select="RiskCode" /></Cvr_ID>
					        			<!-- 主附险标志 -->
					        			<xsl:if test="RiskCode != MainRiskCode">
					        				<MainAndAdlIns_Ind>0</MainAndAdlIns_Ind>
					        			</xsl:if>
					        			<xsl:if test="RiskCode!= MainRiskCode">
					        				<MainAndAdlIns_Ind>1</MainAndAdlIns_Ind>
					        			</xsl:if>
					        			<!-- 保费金额 -->
					        			<InsPrem_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/></InsPrem_Amt>
					        			<!-- 投保份数 -->
				        				<Ins_Cps><xsl:value-of select="Mult"/></Ins_Cps>
				        				<!-- 投保保额 -->
				        				<Ins_Cvr><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/></Ins_Cvr>
				        			</Bu_Detail>
				        		</xsl:for-each>
				        	</Bu_List>
			        		<!-- 投保单号 -->
			        		<Ins_BillNo><xsl:value-of select="substring(/TranData/Body/ProposalPrtNo,1,13)"/></Ins_BillNo>
			        		<!-- 总保费金额 -->
			        		<Tot_InsPrem_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/PayPrem)"/></Tot_InsPrem_Amt>
			        		<!-- 首期缴费金额 -->
			        		<Init_PyF_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Prem)"/></Init_PyF_Amt>
			        		<!-- 年化保费金额 -->
			        		<Anulz_InsPrem_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Risk[RiskCode=MainRiskCode]/Prem)"/></Anulz_InsPrem_Amt>
			        		<!-- 保险公司派驻人员姓名 -->
			        		<Ins_Co_Acrdt_Stff_Nm><xsl:value-of select="/TranData/Body/SaleName"/></Ins_Co_Acrdt_Stff_Nm>
			        		<!-- 保险公司派驻人员从业资格证书编号 -->
			        		<InsCoAcrStCrQuaCtf_ID><xsl:value-of select="/TranData/Body/SaleCertNo"/></InsCoAcrStCrQuaCtf_ID>
			        		<!-- 保费缴费方式代码 format类中添加-->
			        		<InsPrem_PyF_MtdCd></InsPrem_PyF_MtdCd>
			        		<!-- 代理保险期缴代扣账号 核心没有，建行非必填字段-->
			        		<AgInsRgAutoDdcn_AccNo></AgInsRgAutoDdcn_AccNo>
			        		<!-- 每期缴费金额信息 -->
			        		<EcIst_PyF_Amt_Inf><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Risk[RiskCode=MainRiskCode]/Prem)"/></EcIst_PyF_Amt_Inf>
			        		<!-- 保费缴费期数 -->
			        		<InsPrem_PyF_Prd_Num><xsl:value-of select="/TranData/Body/Risk/PayEndYear"/></InsPrem_PyF_Prd_Num>
			        		<!-- 保费缴费周期代码  format类中添加-->
			        		<InsPrem_PyF_Cyc_Cd></InsPrem_PyF_Cyc_Cd>
			        	</APP_ENTITY>
			        </ENTITY>
	      	</TX_BODY>
		</TX>
	</xsl:template>
	<!-- 保障年期/年龄标志 -->
	<xsl:template name="tran_PbIYF">
		<xsl:param name="PbInsuYearFlag">2</xsl:param>
		<xsl:choose>
			<xsl:when test="$PbInsuYearFlag = ''">0</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 'Y'">1</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 'Y'">2</xsl:when>
			<xsl:when test="$PbInsuYearFlag = ''">3</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 'M'">4</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 'D'">5</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 'A'">6</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="0" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>	

<!-- 主附险标志 no -->
<xsl:template name="tran_MainAndAdlIns_Ind">
	<xsl:param name="mainAndAdlIns_ind" />
	<xsl:choose>
    	<xsl:when test="$mainAndAdlIns_ind=1">1</xsl:when>	<!-- 主险  -->
		<xsl:when test="$mainAndAdlIns_ind=2">0</xsl:when>	<!-- 附加险 -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>
</xsl:stylesheet>	