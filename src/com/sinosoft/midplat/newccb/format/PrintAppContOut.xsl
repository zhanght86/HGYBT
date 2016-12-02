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
			        		<!-- 代理保险套餐编号 -->
			        		<AgIns_Pkg_ID></AgIns_Pkg_ID>
			        		<!-- 险种个数 -->
			        		<Cvr_Num><xsl:value-of select="count(/TranData/Body/Risk)"/></Cvr_Num>
			        		<BusiNo_List>
				        		<xsl:for-each select="/TranData/Body/Risk">
				        			<BusiNo_Detail>
					        			<!-- 险种编号 -->
					        			<Cvr_ID><xsl:value-of select="RiskCode" /></Cvr_ID>
				        			</BusiNo_Detail>
				        		</xsl:for-each>
				        	</BusiNo_List>
						    <!-- 投保单号 -->
						    <Ins_BillNo><xsl:value-of select="/TranData/Body/ProposalPrtNo"/></Ins_BillNo>
						    <!-- 保险公司派驻人员姓名 -->
						    <Ins_Co_Acrdt_Stff_Nm><xsl:value-of select="/TranData/Body/SaleName"/></Ins_Co_Acrdt_Stff_Nm>
						   	<!-- 保险公司派驻人员从业资格证书编号 -->
						    <InsCoAcrStCrQuaCtf_ID><xsl:value-of select="/TranData/Body/SaleCertNo"/></InsCoAcrStCrQuaCtf_ID>
						   	<!-- 返回文件的数量 -->
						    <Ret_File_Num>1</Ret_File_Num>
						    
								 <Detail_List>
								  <xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(TranData/Body/Amnt)"/>
								  <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('false')" />
								  <xsl:variable name="MainRisk" select="TranData/Body/Risk[RiskCode=MainRiskCode]"/>
								<!-- 提示信息描述 -->
								<Prmpt_Inf_Dsc>保单页</Prmpt_Inf_Dsc>
								<!-- 代理保险凭证代码 ?-->
								<AgIns_Vchr_TpCd>3</AgIns_Vchr_TpCd>
								<!-- #循环记录条数 -->
								<Rvl_Rcrd_Num>52</Rvl_Rcrd_Num>
								<Detail>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf><xsl:text>           </xsl:text>                                        现金价值表                     </Ret_Inf>
								 <Ret_Inf/>
								 <Ret_Inf><xsl:text>           </xsl:text>保险合同号：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/ContNo, 50)"/><xsl:text></xsl:text>基本保险金额： <xsl:value-of select="$Amnt"/></Ret_Inf>
								 <Ret_Inf><xsl:text>           </xsl:text>险种名称：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($MainRisk/RiskName, 52)"/>货币单位：人民币/元</Ret_Inf>
								 <Ret_Inf><xsl:text>           </xsl:text>------------------------------------------------------------------------------------------------</Ret_Inf>
								 <Ret_Inf><xsl:text>                    </xsl:text>  保单年度末  现金价值  | 保单年度末  现金价值  | 保单年度末  现金价值</Ret_Inf>
							     <Ret_Inf><xsl:text>           </xsl:text>------------------------------------------------------------------------------------------------</Ret_Inf>
								 <xsl:text></xsl:text>           <xsl:apply-templates select="/TranData/Body/Risk/CashValues"/>
								 <Ret_Inf><xsl:text>           </xsl:text>------------------------------------------------------------------------------------------------</Ret_Inf>
								 <Ret_Inf><xsl:text>           </xsl:text>*现金价值表中给出的现金价值为客户已足额缴纳保单年度内所有保险费的情况下，各保单年度末所对应的现</Ret_Inf>
							     <Ret_Inf><xsl:text>           </xsl:text>金价值额。投保后所做的各项变更可能使本表不再适用。</Ret_Inf>
							     <Ret_Inf><xsl:text>           </xsl:text>*对于本现金价值表中未列出的保单年度末现金价值及两个保单年度中间任意一天的本合同的现金价值，可向</Ret_Inf>
							     <Ret_Inf><xsl:text>           </xsl:text>公司来电垂询。</Ret_Inf>				    
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								</Detail>	 
							</Detail_List>

			        	</APP_ENTITY>
			        </ENTITY>
	      	</TX_BODY>
		</TX>
	</xsl:template>
	
<!-- 循环取现金价值信息 -->
<xsl:template name="Cashs" match="CashValues">
        <xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode != '221301'"> 
		<xsl:for-each select="CashValue[EndYear &lt; (26)]">
		<xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
				<Ret_Inf><xsl:text>                        </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+25)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+25)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+25)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+50)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+50)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+50)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></Ret_Inf>
		</xsl:for-each>
		</xsl:if>
		<xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode = '221301'"> 
		    <xsl:for-each select="CashValue[EndYear &lt; (34)]">
		    <xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
				<Ret_Inf><xsl:text>                        </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+33)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+33)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+33)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+66)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+66)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+66)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></Ret_Inf>
		</xsl:for-each>
		</xsl:if>
</xsl:template>	
</xsl:stylesheet>	