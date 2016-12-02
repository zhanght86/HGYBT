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
							<!-- 主险险种编号 -->
					        <MainIns_Cvr_ID><xsl:value-of select="/TranData/Body/Risk/MainRiskCode" /></MainIns_Cvr_ID>
			        		<!-- 代理保险套餐编号 -->
			        		<AgIns_Pkg_ID></AgIns_Pkg_ID>
					        <!-- 保单失效日期-->
					        <InsPolcy_ExpDt><xsl:value-of select="/TranData/Body/Risk/InsuEndDate" /></InsPolcy_ExpDt>
			        		<!-- 保单领取日期 -->
			        		<InsPolcy_Rcv_Dt><xsl:value-of select="/TranData/Body/Risk/GetStartDate"/></InsPolcy_Rcv_Dt>
							<!-- 保单号 -->
							<InsPolcy_No><xsl:value-of select="/TranData/Body/ContNo"/></InsPolcy_No>
							<!-- 保险缴纳金额 -->
							<Ins_PyF_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Risk/Prem)"/></Ins_PyF_Amt>
							<!-- 投保人电子邮件地址-->
							<Plchd_Email_Adr><xsl:value-of select="/TranData/Body/Appnt/Email"/></Plchd_Email_Adr>
							<!-- 保单投保日期-->
							<InsPolcy_Ins_Dt><xsl:value-of select="/TranData/Body/Risk/PolApplyDate"/></InsPolcy_Ins_Dt>
			        		<!-- 保单生效日期 -->
			        		<InsPolcy_EfDt><xsl:value-of select="/TranData/Body/Risk/CValiDate"/></InsPolcy_EfDt>
			        		<!-- 代理保险期缴代扣账号 -->
			        		<AgInsRgAutoDdcn_AccNo></AgInsRgAutoDdcn_AccNo>
			        		<!-- 每期缴费金额信息 -->
			        		<EcIst_PyF_Amt_Inf><xsl:value-of select="/TranData/Body/Risk/Prem"/></EcIst_PyF_Amt_Inf>
			        		<!-- 保费缴费方式代码 -->
			        		<InsPrem_PyF_MtdCd>
						        <xsl:call-template name="tran_Contpayintv">
									<xsl:with-param name="payintv">
										<xsl:value-of select="/TranData/Body/Risk/PayIntv"/>
									</xsl:with-param>
								</xsl:call-template>
			        		</InsPrem_PyF_MtdCd>
			        		<!-- 保费缴费期数 -->
			        		<InsPrem_PyF_Prd_Num><xsl:value-of select="/TranData/Body/Risk/PayEndYear"/></InsPrem_PyF_Prd_Num>
			        		<!-- 保费缴费周期代码 -->
			        		<InsPrem_PyF_Cyc_Cd>
						        <xsl:call-template name="tran_PbPayPerCode">
									<xsl:with-param name="PbPayPerCode">
										<xsl:value-of select="/TranData/Body/Risk/PayIntv"/>
									</xsl:with-param>
								</xsl:call-template>
			        		</InsPrem_PyF_Cyc_Cd>			        		
			        		<!-- 保单犹豫期 -->
			        		<InsPolcy_HsitPrd><xsl:value-of select="/TranData/Body/HesitatePeriod"/></InsPolcy_HsitPrd>
			        		<!-- 返回文件数量 -->
			       <!-- 	<Ret_File_Num><xsl:value-of select="/TranData/Body/Ret_File_Num"/></Ret_File_Num> --> 	
			        		<Ret_File_Num>3</Ret_File_Num>
					<!-- 	<xsl:for-each select="/TranData/Body/Detail_List">
								<Detail_List>
									<Prmpt_Inf_Dsc><xsl:value-of select="Prmpt_Inf_Dsc"/></Prmpt_Inf_Dsc>
									<AgIns_Vchr_TpCd><xsl:value-of select="AgIns_Vchr_TpCd"/></AgIns_Vchr_TpCd>
									<Rvl_Rcrd_Num><xsl:value-of select="Rvl_Rcrd_Num"/></Rvl_Rcrd_Num>
									<xsl:for-each>
										<Detail>
											<Ret_Inf><xsl:value-of select="Detail/Ret_Inf"/></Ret_Inf>
										</Detail>
									</xsl:for-each>
								</Detail_List>
							</xsl:for-each>   -->	

							<Detail_List>
								<!-- 提示信息描述 -->
								<Prmpt_Inf_Dsc>保单第一页</Prmpt_Inf_Dsc>
								<!-- 代理保险凭证代码 -->
								<!-- 	1	保单
										2	批单
										3	现金价值单
										4	发票
										5	代理凭证
										6	投保单
										7	客户权益保障确认书 -->
								<AgIns_Vchr_TpCd>1</AgIns_Vchr_TpCd>
								<!-- #循环记录条数 -->
								<Rvl_Rcrd_Num>52</Rvl_Rcrd_Num>
								<Detail>
								<!--一行打印的内容--> 
								<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
								<xsl:variable name="MainRisk" select="/TranData/Body/Risk[RiskCode=MainRiskCode]" />
								<Ret_Inf>　</Ret_Inf> 
								<Ret_Inf>　</Ret_Inf> 
								<Ret_Inf>　</Ret_Inf> 
								<Ret_Inf>　</Ret_Inf> 
								<Ret_Inf>　</Ret_Inf> 
								<Ret_Inf>　</Ret_Inf> 
								<Ret_Inf>　</Ret_Inf> 
								<Ret_Inf>　</Ret_Inf> 
								<Ret_Inf>　</Ret_Inf> 
								<Ret_Inf>　</Ret_Inf> 
								<Ret_Inf>　</Ret_Inf> 
								<Ret_Inf><xsl:text>         </xsl:text>保险合同号：<xsl:value-of select="/TranData/Body/ContNo"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 48)"/>货币单位：人民币/元 </Ret_Inf>
								<Ret_Inf><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Ret_Inf>
								<Ret_Inf/>
								<Ret_Inf><xsl:text>         </xsl:text>保险合同成立日：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 41)"/>保险合同生效日期：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(/TranData/Body/Risk/CValiDate)" /> </Ret_Inf>
								<Ret_Inf><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Ret_Inf>      
								<Ret_Inf><xsl:text>         </xsl:text><xsl:text>投保人姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Appnt/Name, 12)"/>
																												<xsl:text>性别：</xsl:text><xsl:apply-templates select="/TranData/Body/Appnt/Sex"/><xsl:text>   </xsl:text>
																												<xsl:text>    年龄：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(/TranData/Body/Appnt/Birthday),2)"/><xsl:text>            </xsl:text>  
																												<xsl:text>证件号码：</xsl:text><xsl:value-of select="/TranData/Body/Appnt/IDNo"/>
								</Ret_Inf>
								<Ret_Inf><xsl:text>         </xsl:text><xsl:text>被保人姓名：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Insured/Name, 12)"/>
																												<xsl:text>性别：</xsl:text><xsl:apply-templates select="/TranData/Body/Insured/Sex"/><xsl:text>   </xsl:text>
																												<xsl:text>    年龄：</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(/TranData/Body/Insured/Birthday),2)"/><xsl:text>            </xsl:text>
																												<xsl:text>证件号码：</xsl:text><xsl:value-of select="/TranData/Body/Insured/IDNo"/>
								</Ret_Inf>
								<xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" />  
								<xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" /> 
								<xsl:variable name="sflag" select="java:java.lang.Boolean.parseBoolean('true')" />    
								<xsl:variable name="num" select="count(/TranData/Body/Bnf) " />
								<xsl:for-each select="/TranData/Body/Bnf">
								<Ret_Inf><xsl:text>         </xsl:text><xsl:text></xsl:text>受益人姓名: <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 12)"/>																
								<xsl:text>性别:</xsl:text><xsl:text> </xsl:text><xsl:apply-templates select="Sex"/><xsl:text>       </xsl:text>
								 <xsl:text>受益顺序: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 10)"/>	
								<xsl:text>受益比例:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text>%</xsl:text>
			                   	</Ret_Inf>
								</xsl:for-each>
								<xsl:choose>
								<xsl:when test="$num = 0"><Ret_Inf><xsl:text>         </xsl:text><xsl:text></xsl:text>受益人姓名:<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' 法定', 13)"/>																
								<xsl:text>性别:</xsl:text><xsl:text> </xsl:text>--<xsl:text>       </xsl:text>
								 <xsl:text>受益顺序: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 10)"/>	
								<xsl:text>受益比例:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 3, $Falseflag)"/>
			                   	</Ret_Inf></xsl:when>
								</xsl:choose>		
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Ret_Inf>      
								<Ret_Inf><xsl:text>         </xsl:text>险种名称                          保险期间    交费年期    交费方式  （基本）保额/份数   保险费</Ret_Inf>
								<xsl:for-each select="/TranData/Body/Risk">
								<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
								<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
								<Ret_Inf>
								<!-- 险种名称 -->
								<xsl:text>         </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 34)"/>
								                                                     <xsl:choose>
																										<xsl:when test="InsuYearFlag = 'A'"><xsl:text>至</xsl:text>
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>周岁</xsl:text>
																										</xsl:when>
																										<xsl:when test="InsuYearFlag = 'Y'">
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>年  </xsl:text>
																										</xsl:when>  
																										<xsl:when test="InsuYearFlag = 'M'">
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>月  </xsl:text>
																										</xsl:when>
																										<xsl:otherwise> 
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>日</xsl:text>
																										</xsl:otherwise>
																								</xsl:choose>
																						<xsl:text>     </xsl:text>
																						<xsl:choose>
																										<xsl:when test="PayIntv = 0">
																											<xsl:text>趸交        </xsl:text>
																										</xsl:when>
																										<xsl:when test="PayEndYearFlag = 'Y'">
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>年        </xsl:text>
																										</xsl:when>
																										<xsl:when test="PayEndYearFlag = 'M'">
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>月        </xsl:text>
																										</xsl:when>
																										<xsl:when test="PayEndYearFlag = 'D'">
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>日        </xsl:text>
																										</xsl:when>  
																										<xsl:otherwise> 
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>周岁    </xsl:text>
																										</xsl:otherwise>
																								</xsl:choose>
																						<xsl:apply-templates select="PayIntv"/>
																											<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Amnt,11,$Falseflag)"/><xsl:text>元</xsl:text>
																						 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,13,$Falseflag)"/>元</Ret_Inf>
								</xsl:for-each>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								 <Ret_Inf/>
								<Ret_Inf><xsl:text>         </xsl:text>保险费合计：<xsl:value-of select="TranData/Body/PremText"/>（RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(TranData/Body/Prem)"/>元）</Ret_Inf>
								<Ret_Inf><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Ret_Inf>
								<Ret_Inf><xsl:text>         </xsl:text>红利领取方式：<xsl:apply-templates select="TranData/Body/Risk[RiskCode=MainRiskCode]/BonusGetMode" /></Ret_Inf>
								<xsl:choose>
								       <xsl:when test="TranData/Body/Risk[RiskCode=MainRiskCode]/GetYearFlag='E'">
								           <Ret_Inf><xsl:text>         </xsl:text>年金领取起始年龄：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('交费期满',52)"/>年金领取期限：<xsl:value-of select="TranData/Body/Risk[RiskCode=MainRiskCode]/GetTerms"/>年</Ret_Inf>
								      </xsl:when>
								       <xsl:when test="TranData/Body/Risk[RiskCode=MainRiskCode]/GetYearFlag!='E'">
								           <Ret_Inf><xsl:text>         </xsl:text>年金领取起始年龄：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('交费期满',52)"/>年金领取期限：<xsl:value-of select="TranData/Body/Risk[RiskCode=MainRiskCode]/GetTerms"/>年</Ret_Inf>
								      </xsl:when>
								      <xsl:otherwise>
								            <Ret_Inf><xsl:text>        </xsl:text>年金领取起始年龄：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/Risk[RiskCode=MainRiskCode]/GetYear,52)"/>年金领取期限：<xsl:value-of select="TranData/Body/Risk[RiskCode=MainRiskCode]/GetTerms"/>年</Ret_Inf>
								      </xsl:otherwise>
								</xsl:choose>
								<Ret_Inf><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Ret_Inf>
								<Ret_Inf><xsl:text>         </xsl:text><xsl:text>特别约定：</xsl:text>
																								<xsl:choose>
																										<xsl:when test="$MainRisk/SpecContent = ''">
																											<xsl:text>（无）</xsl:text>
																										</xsl:when>
																										<xsl:otherwise> 
																											<xsl:value-of select="$MainRisk/SpecContent"/>
																										</xsl:otherwise>
																								</xsl:choose>
								</Ret_Inf>
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Ret_Inf>
								<Ret_Inf><xsl:text>         </xsl:text>银行网点名称：<xsl:value-of select="TranData/Body/AgentComName"/></Ret_Inf>
								<Ret_Inf><xsl:text>         </xsl:text>银行销售人员姓名/代码：<xsl:value-of select="TranData/Body/SaleName"/>/<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/SaleStaff,40)"/>打印时间：<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:text> </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></Ret_Inf>
								<Ret_Inf><xsl:text>         </xsl:text>银保经理姓名：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/AgentName, 55)"/>银保经理电话：<xsl:value-of select="TranData/Body/AgentPhone"/></Ret_Inf>
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf />
								<Ret_Inf><xsl:text>         </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 76,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11($MainRisk/SignDate)"/></Ret_Inf>
								<Ret_Inf /> 
							    </Detail>
								 </Detail_List>
								 <Detail_List>
								  <xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(TranData/Body/Amnt)"/>
								  <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('false')" />
								  <xsl:variable name="MainRisk" select="TranData/Body/Risk[RiskCode=MainRiskCode]"/>
								<!-- 提示信息描述 -->
								<Prmpt_Inf_Dsc>保单第二页</Prmpt_Inf_Dsc>
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
								 <Ret_Inf><xsl:text></xsl:text>                                                 现金价值及减额交清表                                               </Ret_Inf>
								 <Ret_Inf/>
								 <Ret_Inf><xsl:text>     </xsl:text>保险合同号：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/ContNo, 50)"/><xsl:text></xsl:text>基本保险金额： <xsl:value-of select="$Amnt"/></Ret_Inf>
								 <Ret_Inf><xsl:text>     </xsl:text>险种名称：<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($MainRisk/RiskName, 52)"/>货币单位：人民币/元</Ret_Inf>
								 <Ret_Inf><xsl:text>     </xsl:text>------------------------------------------------------------------------------------------------</Ret_Inf>
								 <Ret_Inf><xsl:text>     </xsl:text>保单年 <xsl:text>         </xsl:text>减额交清后|保单年<xsl:text>          </xsl:text>减额交清后|保单年<xsl:text>         </xsl:text>减额交清后</Ret_Inf>
								 <Ret_Inf><xsl:text>     </xsl:text>度末 <xsl:text> </xsl:text>现金价值<xsl:text>  </xsl:text>的基本保额|度末 <xsl:text> </xsl:text>现金价值<xsl:text>  </xsl:text>的基本保额|度末 <xsl:text> </xsl:text>现金价值<xsl:text>  </xsl:text>的基本保额</Ret_Inf>
								 <Ret_Inf><xsl:text>     </xsl:text>------------------------------------------------------------------------------------------------</Ret_Inf>
								 <xsl:apply-templates select="/TranData/Body/Risk/CashValues"/>
								 <Ret_Inf><xsl:text>     </xsl:text>------------------------------------------------------------------------------------------------</Ret_Inf>
								 <Ret_Inf><xsl:text>     </xsl:text>*现金价值表中给出的现金价值为客户已足额缴纳保单年度内所有保险费的情况下，各保单年度末所对应的</Ret_Inf>
								 <Ret_Inf><xsl:text>     </xsl:text>现金价值额。投保后所做的各项变更可能使本表不再适用。</Ret_Inf>
								 <Ret_Inf><xsl:text>     </xsl:text>*对于本现金价值表中未列出的保单年度末现金价值及两个保单年度中间任意一天的本合同的现金价值，可向公</Ret_Inf>
							     <Ret_Inf><xsl:text>     </xsl:text>司来电垂询。</Ret_Inf>
							     <Ret_Inf><xsl:text>     </xsl:text>*变更为减额交清保险后，本合同将不再参加以后各年度的红利分配。</Ret_Inf>			    
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
							<Detail_List>
							    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
								<!-- 提示信息描述 -->
								<Prmpt_Inf_Dsc>保单第三页</Prmpt_Inf_Dsc>
								<!-- 代理保险凭证代码 ?-->
								<AgIns_Vchr_TpCd>9</AgIns_Vchr_TpCd>
								<!-- #循环记录条数 -->
								<Rvl_Rcrd_Num>52</Rvl_Rcrd_Num>
								<Detail>
								       <Ret_Inf/>
								       <Ret_Inf/>
								       <Ret_Inf/>
								       <Ret_Inf/>
								       <Ret_Inf/>
								       <Ret_Inf><xsl:text>     </xsl:text>                                     保险合同送达回执</Ret_Inf>
								       <Ret_Inf/>
								       <Ret_Inf/>
								       <Ret_Inf><xsl:text>     </xsl:text><xsl:text>保险合同号: </xsl:text><xsl:value-of select="TranData/Body/ContNo"/></Ret_Inf>
								       <Ret_Inf><xsl:text>     </xsl:text><xsl:text>投保人: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/Appnt/Name, 19)"/><xsl:text>银保经理姓名：      </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/AgentName, 18)"/><xsl:text>银保经理代码：</xsl:text><xsl:value-of select="TranData/Body/AgentCode"/></Ret_Inf>
									   <Ret_Inf><xsl:text>     </xsl:text><xsl:text>    本投保人已收到贵公司的保险合同（保险合同号: </xsl:text><xsl:value-of select="TranData/Body/ContNo"/><xsl:text>），本保险合同包括保险单、现</xsl:text></Ret_Inf>
									   <Ret_Inf><xsl:text>     </xsl:text><xsl:text>金价值表、保险条款等相关资料，经审核确认保险合同内容正确无误。本人已阅读过产品条款、投保提示</xsl:text></Ret_Inf>
									   <Ret_Inf><xsl:text>     </xsl:text><xsl:text>书和产品说明书，确认已了解并认可保险合同的全部内容，知晓本人的权利和义务。</xsl:text></Ret_Inf>
									   <Ret_Inf><xsl:text>     </xsl:text><xsl:text></xsl:text></Ret_Inf>
									   <Ret_Inf/>
									   <Ret_Inf><xsl:text>     </xsl:text><xsl:text>    投保人签名：                                    签收日期：         年     月     日</xsl:text></Ret_Inf>
									   <Ret_Inf><xsl:text>     </xsl:text><xsl:text>                                      以下栏由公司人员填写</xsl:text></Ret_Inf>
									   <Ret_Inf><xsl:text>     </xsl:text><xsl:text>------------------------------------------------------------------------------------------------</xsl:text></Ret_Inf>
									   <Ret_Inf><xsl:text>     </xsl:text><xsl:text>    保险合同号为 </xsl:text><xsl:value-of select="TranData/Body/ContNo"/><xsl:text>的保险合同已送达客户，由客户亲笔签字确认，现将保险合同送达</xsl:text></Ret_Inf>
									   <Ret_Inf><xsl:text>     </xsl:text><xsl:text>回执交回。</xsl:text></Ret_Inf>
									   <Ret_Inf/>
									   <Ret_Inf><xsl:text>     </xsl:text><xsl:text>    银保经理签名：	          经办人签字：           日期：      年     月     日</xsl:text></Ret_Inf>
							  </Detail>	 
							</Detail_List>	

			        	</APP_ENTITY>
			        </ENTITY>
	      	</TX_BODY>
	      	
		</TX>
	</xsl:template>
	
<!-- 循环取现金价值信息 -->
<xsl:template name="Cashs" match="CashValues">
       <!--  <xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode = '221301'"> 
		<xsl:for-each select="CashValue[EndYear &lt; (26)]">
		<xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
					<Ret_Inf><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,12,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/><xsl:choose><xsl:when test="EndYearAmnt!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(EndYearAmnt),12,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-  ',18,$Falseflag)"/></xsl:otherwise></xsl:choose></xsl:when><xsl:otherwise>								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose>  | <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+35)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+35)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+35)]/Cash),13,$Falseflag)"/><xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+35)]/EndYearAmnt!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+35)]/EndYearAmnt),11,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-  ',11,$Falseflag)"/></xsl:otherwise></xsl:choose></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',11,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose>|<xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+70)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+70)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+70)]/Cash),11,$Falseflag)"/><xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+70)]/EndYearAmnt!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+70)]/EndYearAmnt),11,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',9,$Falseflag)"/></xsl:otherwise></xsl:choose> </xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',9,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',11,$Falseflag)"/></xsl:otherwise></xsl:choose></Ret_Inf>
		</xsl:for-each>
		</xsl:if> -->
        <xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode = '231302'"> 
		<xsl:for-each select="CashValue[EndYear &lt; (36)]">
		<xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
					<Ret_Inf><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,12,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/><xsl:choose><xsl:when test="EndYearAmnt!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(EndYearAmnt),10,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-  ',10,$Falseflag)"/></xsl:otherwise></xsl:choose></xsl:when><xsl:otherwise>								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose>  | <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+35)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+35)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+35)]/Cash),13,$Falseflag)"/><xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+35)]/EndYearAmnt!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+35)]/EndYearAmnt),11,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-  ',11,$Falseflag)"/></xsl:otherwise></xsl:choose></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',11,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose>|<xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+70)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+70)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+70)]/Cash),11,$Falseflag)"/><xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+70)]/EndYearAmnt!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+70)]/EndYearAmnt),11,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',9,$Falseflag)"/></xsl:otherwise></xsl:choose> </xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',9,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',11,$Falseflag)"/></xsl:otherwise></xsl:choose></Ret_Inf>
		</xsl:for-each>
		</xsl:if>

</xsl:template>

<!-- 缴费频次/保费缴纳方式 -->
<xsl:template  match="PayIntv">
<xsl:choose>
	<xsl:when test=".=12">年交     </xsl:when>	<!-- 年缴 -->
	<xsl:when test=".=1">月交      </xsl:when>	<!-- 月缴 -->
	<xsl:when test=".=6">半年交    </xsl:when>	<!-- 半年缴 -->
	<xsl:when test=".=3">季交      </xsl:when>	<!-- 季缴 -->
	<xsl:when test=".=0">趸交      </xsl:when>	<!-- 趸缴 -->
	<xsl:when test=".=-1">不定期交  </xsl:when>	<!-- 不定期 -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
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

	<!-- 保费缴纳周期代码 -->
	<xsl:template name="tran_PbPayPerCode">
		<xsl:param name="PbPayPerCode"></xsl:param>
		<xsl:choose>
			<xsl:when test="$PbPayPerCode = 3">0201</xsl:when><!-- 季交 -->
			<xsl:when test="$PbPayPerCode = 0">0100</xsl:when><!-- 趸交 -->
			<xsl:when test="$PbPayPerCode = 12">0203</xsl:when><!-- 年交 -->
			<xsl:when test="$PbPayPerCode = 6">0202</xsl:when><!-- 半年交 -->
			<xsl:when test="$PbPayPerCode = 1">0204</xsl:when><!-- 月交 -->
			<xsl:otherwise>
				<xsl:value-of select="9999" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>	
<!-- 红利领取方式的转换 -->
<xsl:template match="Risk[RiskCode=MainRiskCode]/BonusGetMode">
<xsl:choose>
	<xsl:when test=".=1">累积生息</xsl:when>
	<xsl:when test=".=2">领取现金</xsl:when>
	<xsl:when test=".=3">抵交保费</xsl:when>
	<xsl:when test=".=5">增额交清</xsl:when>
	<xsl:when test=".=''">累积生息  </xsl:when> 
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>
<!-- 性别 -->
<xsl:template name="tran_sex" match="Sex">
<xsl:choose>      
	<xsl:when test=".=0">男</xsl:when>	<!-- 男 -->
	<xsl:when test=".=1">女</xsl:when>	<!-- 女 -->
	<xsl:when test=".=2">其他</xsl:when>	<!-- 其他 -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
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
</xsl:stylesheet>