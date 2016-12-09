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
								        		
							<!-- 保单号 -->
							<InsPolcy_No><xsl:value-of select="/TranData/Body/ContNo"/></InsPolcy_No>
							<!-- 主险险种编号 -->
					        <MainIns_Cvr_ID><xsl:value-of select="/TranData/Body/MainRiskCode" /></MainIns_Cvr_ID>
			        		<!-- 代理保险套餐编号 -->
			        		<AgIns_Pkg_ID></AgIns_Pkg_ID>
			        		<!-- 保单犹豫期 -->
			        		<InsPolcy_HsitPrd><xsl:value-of select="/TranData/Body/HesitatePeriod"/></InsPolcy_HsitPrd>
			        		<!-- 保单生效日期 -->
			        		<InsPolcy_EfDt><xsl:value-of select="/TranData/Body/CValiDate"/></InsPolcy_EfDt>
					        <!-- 保单失效日期-->
					        <InsPolcy_ExpDt><xsl:value-of select="/TranData/Body/InsuEndDate" /></InsPolcy_ExpDt>
			        		<!-- 保单领取日期 -->
			        		<InsPolcy_Rcv_Dt><xsl:value-of select="/TranData/Body/GetStartDate"/></InsPolcy_Rcv_Dt>
							<!-- 保险缴纳金额 -->
							<Ins_PyF_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Prem)"/></Ins_PyF_Amt>
							<!-- 保费缴纳期数 总期数-->
							<InsPrem_PyF_Prd_Num><xsl:value-of select="/TranData/Body/RecvNum"/></InsPrem_PyF_Prd_Num>
							<!-- 剩余缴纳期数 剩余期数-->
							<Srpls_Pyf_Prd><xsl:value-of select="/TranData/Body/RestPeriods"/></Srpls_Pyf_Prd>
							<!-- 保费缴纳方式代码 -->
							<InsPrem_PyF_MtdCd>
								<xsl:call-template name="tran_Contpayintv">
									<xsl:with-param name="payintv">
										<xsl:value-of select="/TranData/Body/PayIntv"/>
									</xsl:with-param>
								</xsl:call-template>
							</InsPrem_PyF_MtdCd>
			        	</APP_ENTITY>
			        </ENTITY>
	      	</TX_BODY>
		</TX>
	</xsl:template>

	<!-- 保单的缴费方式 -->
	<xsl:template name="tran_Contpayintv">
		<xsl:param name="payintv"></xsl:param>
		<xsl:choose>
			<xsl:when test="$payintv = -1">01</xsl:when><!-- 不定期交 -->
			<xsl:when test="$payintv = 0">02</xsl:when><!-- 趸交 -->
		<!--	<xsl:when test="$payintv = 1">1</xsl:when> 月交 -->
		<!--	<xsl:when test="$payintv = 3">3</xsl:when> 季交 -->
		<!--	<xsl:when test="$payintv = 6">6</xsl:when> 半年交 -->
			<xsl:when test="$payintv = 12">03</xsl:when><!-- 年交 -->
			<xsl:when test="$payintv = 98">04</xsl:when><!-- 交至某确定年 -->
			<xsl:when test="$payintv = 99">05</xsl:when><!-- 终身交费 -->
		</xsl:choose>
	</xsl:template>

</xsl:stylesheet>