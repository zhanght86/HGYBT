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
			        	
							<!-- 续期缴费笔数  -->
							<Rnew_PyF_Dnum><xsl:value-of select="count(/TranData/Body/Detail1)"/></Rnew_PyF_Dnum>
							<!-- 缴费信息循环 -->
							<InsPyF_List>
								<xsl:for-each select="/TranData/Body/Detail1">
									<InsPyF_Detail>
										<!-- 保险缴费日期 -->
							    		<Ins_PyF_Dt><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date10to8(PayDate)"/></Ins_PyF_Dt>
										<!-- 保险缴费金额 -->
							    		<Ins_PyF_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/></Ins_PyF_Amt>
									</InsPyF_Detail>
								</xsl:for-each>
							</InsPyF_List>
							
						    <!-- 分红次数 -->
				    		<Dvdn_Cnt><xsl:value-of select="count(/TranData/Body/Detail2)"/></Dvdn_Cnt>
				    		<!-- 分红信息循环 -->
				    		<XtraDvdn_List>
				    			<xsl:for-each select="/TranData/Body/Detail2">
				    				<!-- 红利实际发放日期 -->
						    		<XtraDvdn_Act_Dstr_Dt><xsl:value-of select="BonusGetDate"/></XtraDvdn_Act_Dstr_Dt>
				    				<!-- 红利处理方式代码 -->
						    		<XtraDvdn_Pcsg_MtdCd><xsl:value-of select="BonusGetMode"/>
						    			<xsl:call-template name="tran_BonusGetMode">
											<xsl:with-param name="tBonusGetMode">
												<xsl:value-of select="BonusGetMode" />
											</xsl:with-param>
										</xsl:call-template>
						    		</XtraDvdn_Pcsg_MtdCd>
				    				<!-- 本期红利金额 -->
						    		<CrnPrd_XtDvdAmt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(CurrentAmt)"/></CrnPrd_XtDvdAmt>
				    				<!-- 终了红利金额 -->
						    		<ATEndOBns_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(EndAmt)"/></ATEndOBns_Amt>
				    				<!-- 累计红利金额 -->
						    		<Acm_XtDvdAmt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(BonusAmt)"/></Acm_XtDvdAmt>
				    			</xsl:for-each>
				    		</XtraDvdn_List>
				    		
				    		<!-- 保全业务笔数 -->
						    <PsvBsn_Dnum><xsl:value-of select="count(/TranData/Body/Detail3)"/></PsvBsn_Dnum>
							<!-- 保全信息循环 -->			        	
			        		<Prsrvt_List>
			        			<xsl:for-each select="/TranData/Body/Detail3">
			        				<Prsrvt_Detail>
							    		<!-- 保全日期 -->
									    <Prsrvt_Dt><xsl:value-of select="EdorDate"/></Prsrvt_Dt>
							    		<!-- 保全变动事项描述 -->
									    <Prsrvt_Chg_Itm_Dsc><xsl:value-of select="Description"/></Prsrvt_Chg_Itm_Dsc>
			        				</Prsrvt_Detail>
			        			</xsl:for-each>
			        		</Prsrvt_List>
			        		
				    		<!-- 保单生效复效次数 -->
						    <InsPolcyEff_Rinst_Cnt><xsl:value-of select="count(/TranData/Body/Detail4)"/></InsPolcyEff_Rinst_Cnt>
							<!-- 失效复效信息循环 -->			        	
			        		<InsPolcyDt_List>
			        			<xsl:for-each select="/TranData/Body/Detail4">
			        				<InsPolcyDt_Detail>
							    		<!-- 保单失效日期 -->
									    <InsPolcy_ExpDt><xsl:value-of select="InsuEndDate"/></InsPolcy_ExpDt>
							    		<!-- 保单复效日期 -->
									    <InsPolcy_Rinst_Dt><xsl:value-of select="InsuRinstDate"/></InsPolcy_Rinst_Dt>
							    		<!-- 代理保险合约状态代码 -->
							    		<!-- 核心只有下列保单状态 
									    		Available: 0-有效 1-失效 （险种状态）
												Terminate: 0-未终止 1-终止 （险种状态）
												Lost: 0-未挂失 1-挂失 （保单状态）
												PayPrem: 0-未自垫 1-保费自动垫交 （险种状态）
												Loan: 0-未贷款 1-贷款 （保单状态）
												BankLoan: 0-未质押银行贷款 1-质押银行贷款 （保单状态）
												RPU：0-未减额缴清 1-减额缴清 （险种状态）
												暂时与核心约定 状态代码
																		0-有效
																		1-失效
																		2-复效
							    		 -->
									    <AcIsAR_StCd>
										    <xsl:call-template name="tran_Status">
												<xsl:with-param name="Status">
													<xsl:value-of select="PolicyStatus" />
												</xsl:with-param>
											</xsl:call-template>
									    </AcIsAR_StCd>
			        				</InsPolcyDt_Detail>
			        			</xsl:for-each>
			        		</InsPolcyDt_List>
			        	
				    		<!-- 保单保额变动次数 -->
						    <InsPolcy_Cvr_Chg_Cnt><xsl:value-of select="count(/TranData/Body/Detail5)"/></InsPolcy_Cvr_Chg_Cnt>
							<!-- 增保/减保/退保信息循环 -->			        	
			        		<InsPolcyAply_List>
			        			<xsl:for-each select="/TranData/Body/Detail5">
			        				<InsPolcyAply_Detail>
							    		<!-- 保单申请业务类别代码 -->
									    <InsPolcyAplyBsnCgyCd>
									    	<xsl:call-template name="tran_Type">
												<xsl:with-param name="Type">
													<xsl:value-of select="EdorType" />
												</xsl:with-param>
											</xsl:call-template>
									    </InsPolcyAplyBsnCgyCd>
							    		<!-- 业务申请日期 -->
									    <Bapl_Dt><xsl:value-of select="ApplyDate"/></Bapl_Dt>
			        				</InsPolcyAply_Detail>
			        			</xsl:for-each>
			        		</InsPolcyAply_List>
			        	
				    		<!-- 理赔记录数 -->
						    <SetlOfClms_Rcrd_Num><xsl:value-of select="count(/TranData/Body/Detail6)"/></SetlOfClms_Rcrd_Num>
							<!-- 理赔信息循环 -->			        	
			        		<SetlOfClms_List>
			        			<xsl:for-each select="/TranData/Body/Detail6">
			        				<SetlOfClms_Detail>
							    		<!-- 理赔记录日期 -->
									    <SetlOfClms_Rcrd_Dt><xsl:value-of select="RcrdDate"/></SetlOfClms_Rcrd_Dt>
							    		<!-- 理赔金额 -->
									    <SetlOfClms_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/></SetlOfClms_Amt>
			        				</SetlOfClms_Detail>
			        			</xsl:for-each>
			        		</SetlOfClms_List>
			        	
			    		<!-- 净值日期 -->
					    <NetVal_Dt><xsl:value-of select="/TranData/Body/NetValDate"/></NetVal_Dt>
						<!-- 代理保险现金净值 -->
					    <AgIns_Cash_NetVal><xsl:value-of select="/TranData/Body/AgInsCashNetVal"/></AgIns_Cash_NetVal>
			        	
			        	</APP_ENTITY>
			        </ENTITY>
	      	</TX_BODY>
		</TX>
	</xsl:template>

	<!-- 红利领取方式 -->
	<xsl:template name="tran_BonusGetMode">
		<xsl:param name="tBonusGetMode"></xsl:param>
		<xsl:if test="$tBonusGetMode = 2">0</xsl:if><!-- 直接给付 -->
		<xsl:if test="$tBonusGetMode = 3">1</xsl:if><!-- 抵交保费 -->
		<xsl:if test="$tBonusGetMode = 1">2</xsl:if><!-- 累积生息 -->
		<xsl:if test="$tBonusGetMode = 5">3</xsl:if><!-- 增额交清 -->
	</xsl:template>
	
	<!-- 代理保险合约状态 -->
	<xsl:template name="tran_Status">
		<xsl:param name="Status"></xsl:param>
		<xsl:if test="$Status = 0">076012</xsl:if><!-- 有效 -->
		<xsl:if test="$Status = 1">076034</xsl:if><!-- 失效 -->
		<xsl:if test="$Status = 2">076035</xsl:if><!-- 复效 -->
	</xsl:template>
	
	<!-- 保单申请业务类别代码 -->
	<xsl:template name="tran_Type">
		<xsl:param name="Type"></xsl:param>
		<xsl:if test="$Type = 0">003</xsl:if><!-- 增保 -->
		<xsl:if test="$Type = PT">002</xsl:if><!-- 减保 -->
		<xsl:if test="$Type = CT ">004</xsl:if><!-- 退保 -->
	</xsl:template>

</xsl:stylesheet>
