<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output indent="yes"/>
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
				<xsl:if test = "/TranData/RetData/Flag='1'">
			     <!-- 服务状态 -->
				<SYS_TX_STATUS>00</SYS_TX_STATUS>    
			     <!-- 服务响应码 -->
				<SYS_RESP_CODE>000000000000</SYS_RESP_CODE>    
				</xsl:if>
				<xsl:if test = "/TranData/RetData/Flag ='0'">
			     <!-- 服务状态 -->
				<SYS_TX_STATUS>01</SYS_TX_STATUS>    
			     <!-- 服务响应码 -->
				<SYS_RESP_CODE></SYS_RESP_CODE>    
				</xsl:if>    
			     <!-- 服务响应描述长度  转换类中添加-->
				<SYS_RESP_DESC_LEN></SYS_RESP_DESC_LEN>    
			     <!-- 服务响应描述 -->
				<SYS_RESP_DESC></SYS_RESP_DESC>    
			</TX_HEADER>
	
		<!-- 报文体 -->
			<TX_BODY>
	      			<COMMON>
	         			<FILE_LIST_PACK>
	         				<!-- 文件个数 -->
				            <FILE_NUM>0</FILE_NUM>
				            <!-- 文件处理方式 -->
				            <FILE_MODE></FILE_MODE>
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
							<!-- 投保单号 -->
							<Ins_BillNo><xsl:value-of select="/TranData/LCCont/PrtNo"/></Ins_BillNo>
							<!-- 险种编号 -->
					        <Cvr_ID><xsl:value-of select="/TranData/LCCont/RiskCode" /></Cvr_ID>
							<!-- 险种名称 -->
							<Cvr_Nm><xsl:value-of select="/TranData/LCCont/RiskName"/></Cvr_Nm>
			        		<!-- 代理保险套餐编号 -->
			        		<AgIns_Pkg_ID></AgIns_Pkg_ID>
							<!-- 套餐名称 -->
							<Pkg_Nm></Pkg_Nm>
							<!-- 投保人名称 -->
							<Plchd_Nm><xsl:value-of select="/TranData/LCCont/AppntName"/></Plchd_Nm>
							<!-- 保险缴费金额  -->
							<Ins_PyF_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/LCCont/Prem)"/></Ins_PyF_Amt>
							<!-- 保险年期类别代码 -->
							<Ins_Yr_Prd_CgyCd>03</Ins_Yr_Prd_CgyCd>
							<!-- 保险期限 -->
							<Ins_Ddln><xsl:value-of select="/TranData/LCCont/InsuYear"/></Ins_Ddln>
							<!-- 保险周期代码 -->
							<Ins_Cyc_Cd>
								<xsl:call-template name="tran_insCycCd">
									<xsl:with-param name="insCycCd">
										<xsl:value-of select="TranData/LCCont/InsuYearFlag"/>
									</xsl:with-param>
								</xsl:call-template>
							</Ins_Cyc_Cd>
							<!-- 保费缴费方式代码 -->
							<InsPrem_PyF_MtdCd>
								<xsl:call-template name="tran_insPremPyFMtdCd">
									<xsl:with-param name="insPremPyFMtdCd">
										<!-- <xsl:value-of select="PayEndYearFlag"/> -->
										<xsl:value-of select="/TranData/LCCont/PayIntv"/>
									</xsl:with-param>
								</xsl:call-template>
							</InsPrem_PyF_MtdCd>
							<!-- 保费缴费期数 -->
							<!-- <InsPrem_PyF_Prd_Num><xsl:value-of select="/TranData/LCCont/PayEndYear"/></InsPrem_PyF_Prd_Num> -->
							<InsPrem_PyF_Prd_Num>1</InsPrem_PyF_Prd_Num>
							<!-- 保费缴费周期代码 -->
							<InsPrem_PyF_Cyc_Cd>
								<xsl:call-template name="tran_insPremPyFCycCd">
									<xsl:with-param name="insPremPyFCycCd">
										<!-- <xsl:value-of select="/TranData/LCCont/PayEndYearFlag"/> -->
										<xsl:value-of select="/TranData/LCCont/PayIntv"/>
									</xsl:with-param>
								</xsl:call-template>
							</InsPrem_PyF_Cyc_Cd>
							<!-- 代理保险缴费业务细分代码 -->
							<AgInsPyFBsnSbdvsn_Cd></AgInsPyFBsnSbdvsn_Cd>
			        	</APP_ENTITY>
			        </ENTITY>
	      	</TX_BODY>
		</TX>
	</xsl:template>
	
	<xsl:template name="tran_insCycCd">
		<xsl:param name="insCycCd"></xsl:param>
		<xsl:choose>
			<!-- <xsl:when test="$insCycCd ='A'">0100</xsl:when> -->
			<xsl:when test="$insCycCd ='Y'">03</xsl:when><!--按年  -->
			<xsl:when test="$insCycCd ='M'">04</xsl:when><!-- 按月 -->
			<xsl:when test="$insCycCd ='D'">05</xsl:when><!--按天-->
			<xsl:otherwise>99</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_insPremPyFMtdCd">
		<xsl:param name="insPremPyFMtdCd">02</xsl:param>
		<xsl:choose>
			<xsl:when test="$insPremPyFMtdCd='-1'">01</xsl:when><!--不定期缴-->
			<xsl:when test="$insPremPyFMtdCd='0'">02</xsl:when><!--趸交-->
			<xsl:when test="$insPremPyFMtdCd='12'">03</xsl:when><!--年缴-->
			<xsl:when test="$insPremPyFMtdCd='98'">04</xsl:when><!--交至某确定年-->
			<xsl:when test="$insPremPyFMtdCd='99'">05</xsl:when><!--终身缴费-->
			<xsl:otherwise>01</xsl:otherwise><!--不定期缴-->
		</xsl:choose>
	</xsl:template>
	
	<xsl:template name="tran_insPremPyFCycCd">
		<xsl:param name="insPremPyFCycCd"></xsl:param>
		<xsl:choose>
			<xsl:when test="$insPremPyFCycCd='0'">0100</xsl:when><!--趸交-->
			<xsl:when test="$insPremPyFCycCd='3'">0201</xsl:when><!--季缴-->
			<xsl:when test="$insPremPyFCycCd='6'">0202</xsl:when><!--半年缴-->
			<xsl:when test="$insPremPyFCycCd='12'">0203</xsl:when><!--年缴-->
			<xsl:when test="$insPremPyFCycCd='1'">0204</xsl:when><!--月缴-->
			<xsl:otherwise>9999</xsl:otherwise><!--其他-->
		</xsl:choose>
	</xsl:template>
	
</xsl:stylesheet>