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
				<SYS_SND_SEC_ID>510096</SYS_SND_SEC_ID>
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
				            <FILE_NUM></FILE_NUM>
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
			        		<!-- #代理保险批量包名称 -->
			        		<AgIns_BtchBag_Nm></AgIns_BtchBag_Nm>
			        		<!-- #当前批明细总比数 -->
			        		<Cur_Btch_Dtl_TDnum><xsl:value-of select="count(/TranData/Body/Detail)" /></Cur_Btch_Dtl_TDnum>
			        		<Insu_List>
			        			<xsl:for-each select="/TranData/Body/Detail">
			        			<Insu_Detail>
					        		<!-- 保险公司编号  -->
									<Ins_Co_ID>010079</Ins_Co_ID>
									<!-- 保险公司名称  -->
									<Ins_Co_Nm>华贵人寿保险有限公司</Ins_Co_Nm>
								    <!-- 建行代理标志  -->
								    <CCB_Agnc_Ind>1</CCB_Agnc_Ind>
								    <!-- 代理保险套餐编号 -->
						    		<AgIns_Pkg_ID></AgIns_Pkg_ID>
								    <!-- 套餐名称  -->
								    <Pkg_Nm></Pkg_Nm>
								    <!-- 界面模板类型代码 -->
								    <Intfc_Tpl_TpCd></Intfc_Tpl_TpCd>
									<!-- 投保人姓名 -->
								    <Plchd_Nm><xsl:value-of select="Appnt/Name"/></Plchd_Nm>
									<!-- 投保人性别代码 -->
									<Plchd_Gnd_Cd>
											<xsl:call-template name="tran_sex">
												<xsl:with-param name="Sex">
													<xsl:value-of select="Appnt/Sex" />
												</xsl:with-param>
											</xsl:call-template>
									</Plchd_Gnd_Cd>
									<!-- 投保人出生日期 -->
									<Plchd_Brth_Dt><xsl:value-of select="Appnt/Birthday"/></Plchd_Brth_Dt>
									<!-- 投保人证件类型代码 -->
									<Plchd_Crdt_TpCd>
									    	<xsl:call-template name="tran_idtype">
												<xsl:with-param name="idtype">
													<xsl:value-of select="Appnt/IDType" />
												</xsl:with-param>
											</xsl:call-template>
									</Plchd_Crdt_TpCd>
									<!-- 投保人证件号 -->
									<Plchd_Crdt_No><xsl:value-of select="Appnt/IDNo"/></Plchd_Crdt_No>
									<!-- 投保人证件生效日期 -->
									<Plchd_Crdt_EfDt></Plchd_Crdt_EfDt>
									<!-- 投保人证件失效日期 -->
									<Plchd_Crdt_ExpDt></Plchd_Crdt_ExpDt>
									<!--投保人联系地址国家地区代码  -->
									<PlchdCtcAdrCtyRgon_Cd></PlchdCtcAdrCtyRgon_Cd>
									<!-- 投保人省代码 -->
									<Plchd_Prov_Cd></Plchd_Prov_Cd>
									<!-- 投保人市代码 -->
									<Plchd_City_Cd></Plchd_City_Cd>
									<!-- 投保人区县代码 -->
									<Plchd_CntyAndDstc_Cd></Plchd_CntyAndDstc_Cd>
									<!-- 投保人详细地址内容 -->
									<Plchd_Dtl_Adr_Cntnt><xsl:value-of select="Appnt/AddressContent"/></Plchd_Dtl_Adr_Cntnt>
									<!-- 投保人固定电话国际区号 -->
									<PlchdFixTelItnlDstcNo></PlchdFixTelItnlDstcNo>
									<!-- 投保人固定电话国内区号 -->
									<PlchdFixTelDmstDstcNo><xsl:value-of select="Appnt/FixTelDmstDstcNo"/></PlchdFixTelDmstDstcNo>
									<!--  投保人移动电话国际区号-->
									<PlchdMoveTelItlDstcNo><xsl:value-of select="Appnt/MobileItlDstcNo"/></PlchdMoveTelItlDstcNo>
									<!-- 投保人国籍代码 -->
									<Plchd_Nat_Cd>
											<xsl:call-template name="tran_Nationality">
												<xsl:with-param name="Nationality">
													<xsl:value-of select="Appnt/Nationality"/>
												</xsl:with-param>
											</xsl:call-template>
									</Plchd_Nat_Cd>
									<!-- 投保人通信地址 -->
									<Plchd_Comm_Adr><xsl:value-of select="Appnt/Address"/></Plchd_Comm_Adr>
									<!-- 投保人邮政编码 -->
									<Plchd_ZipECD><xsl:value-of select="Appnt/ZipCode"/></Plchd_ZipECD>
									<!-- 投保人固定电话 -->
									<Plchd_Fix_TelNo><xsl:value-of select="Appnt/Phone"/></Plchd_Fix_TelNo>
									<!-- 投保人移动电话 -->
									<Plchd_Move_TelNo><xsl:value-of select="Appnt/Mobile"/></Plchd_Move_TelNo>
									<!-- 投保人电子邮件地址 -->
									<Plchd_Email_Adr><xsl:value-of select="Appnt/Email"/></Plchd_Email_Adr>
									<!-- 投保人职业代码 -->
									<Plchd_Ocp_Cd></Plchd_Ocp_Cd>
									<!-- 投保人年收入 -->
									<Plchd_Yr_IncmAm><xsl:value-of select="Appnt/YearSalary" /></Plchd_Yr_IncmAm>
									<!-- 投保人家庭年收入 -->
									<Fam_Yr_IncmAm><xsl:value-of select="Appnt/FamilyYearSalary" /></Fam_Yr_IncmAm>
									<!-- 投保人居民类型代码 -->
									<Rsdnt_TpCd><xsl:value-of select="Appnt/DenType" /></Rsdnt_TpCd>
							    	<!-- 投保人与被保人关系代码 -->
									<Plchd_And_Rcgn_ReTpCd>
										<xsl:if test = "Appnt/RelaToInsured = '00'">0133043</xsl:if>
										<xsl:if test = "Appnt/RelaToInsured = '02'">0133010</xsl:if>
										<xsl:if test = "(Appnt/RelaToInsured = '01') and (Insured/Sex = '0')">0133011</xsl:if>
										<xsl:if test = "(Appnt/RelaToInsured = '01') and (Insured/Sex = '1')">0133012</xsl:if>
										<xsl:if test = "(Appnt/RelaToInsured = '03') and (Insured/Sex = '0')">0133015</xsl:if>
										<xsl:if test = "(Appnt/RelaToInsured = '03') and (Insured/Sex = '1')">0133016</xsl:if>
										<xsl:if test = "Appnt/RelaToInsured = '06'">0133010</xsl:if>
									</Plchd_And_Rcgn_ReTpCd>
						    		<!-- 被保人姓名 -->
								    <Rcgn_Nm><xsl:value-of select="Insured/Name"/></Rcgn_Nm>
						    		<!-- 被保人拼音全称 -->
								    <Rcgn_CPA_FullNm></Rcgn_CPA_FullNm>
						    		<!-- 被保人性别 -->
								    <Rcgn_Gnd_Cd>
										<xsl:call-template name="tran_sex">
											<xsl:with-param name="Sex">
												<xsl:value-of select="Insured/Sex" />
											</xsl:with-param>
										</xsl:call-template>
									</Rcgn_Gnd_Cd>
						    		<!-- 被保人出生日期 -->
								    <Rcgn_Brth_Dt><xsl:value-of select="Insured/Birthday"/></Rcgn_Brth_Dt>
						    		<!-- 被保人证件类型代码 -->
								    <Rcgn_Crdt_TpCd>
								    	<xsl:call-template name="tran_idtype">
											<xsl:with-param name="idtype">
												<xsl:value-of select="Insured/IDType" />
											</xsl:with-param>
										</xsl:call-template>
									</Rcgn_Crdt_TpCd>
						    		<!-- 被保人证件号 -->
								    <Rcgn_Crdt_No><xsl:value-of select="Insured/IDNo"/></Rcgn_Crdt_No>
						    		<!-- 被保人证件生效日期 -->
								    <Rcgn_Crdt_EfDt></Rcgn_Crdt_EfDt>
						    		<!-- 被保人证件失效日期 -->
								    <Rcgn_Crdt_ExpDt></Rcgn_Crdt_ExpDt>
								    <!--被保人联系地址国家地区代码  -->
									<RcgnCtcAdr_CtyRgon_Cd></RcgnCtcAdr_CtyRgon_Cd>
									<!-- 被保人省代码 -->
									<Rcgn_Prov_Cd></Rcgn_Prov_Cd>
									<!-- 被保人市代码 -->
									<Rcgn_City_Cd></Rcgn_City_Cd>
									<!-- 被保人区县代码 -->
									<Rcgn_CntyAndDstc_Cd></Rcgn_CntyAndDstc_Cd>
									<!-- 被保人详细地址内容 -->
									<Rcgn_Dtl_Adr_Cntnt><xsl:value-of select="Insured/AddressContent"/></Rcgn_Dtl_Adr_Cntnt>
									<!-- 被保人固定电话国际区号 -->
									<RcgnFixTelItnl_DstcNo></RcgnFixTelItnl_DstcNo>
									<!-- 被保人固定电话国内区号 -->
									<RcgnFixTelDmst_DstcNo><xsl:value-of select="Insured/FixTelDmstDstcNo"/></RcgnFixTelDmst_DstcNo>
									<!--  被保人移动电话国际区号-->
									<RcgnMoveTelItnlDstcNo><xsl:value-of select="Insured/MobileItlDstcNo"/></RcgnMoveTelItnlDstcNo>
						    		<!-- 被保人国籍代码 -->
								    <Rcgn_Nat_Cd>
										<xsl:call-template name="tran_Nationality">
											<xsl:with-param name="Nationality">
												<xsl:value-of select="Insured/Nationality"/>
											</xsl:with-param>
										</xsl:call-template>						    
								    </Rcgn_Nat_Cd>
						    		<!-- 被保人通讯地址 -->
								    <Rcgn_Comm_Adr><xsl:value-of select="Insured/Address"/></Rcgn_Comm_Adr>
						    		<!-- 被保人邮政编码 -->
								    <Rcgn_ZipECD><xsl:value-of select="Insured/ZipCode"/></Rcgn_ZipECD>
						    		<!-- 被保人固定电话 -->
								    <Rcgn_Fix_TelNo><xsl:value-of select="Insured/Phone"/></Rcgn_Fix_TelNo>
						    		<!-- 被保人移动电话 -->
								    <Rcgn_Move_TelNo><xsl:value-of select="Insured/Mobile"/></Rcgn_Move_TelNo>
						    		<!-- 被保人电子邮件地址 -->
								    <Rcgn_Email_Adr><xsl:value-of select="Insured/Email"/></Rcgn_Email_Adr>
						    		<!-- 被保人职业代码 -->
								    <Rcgn_Ocp_Cd></Rcgn_Ocp_Cd>
						    		<!-- 被保人年收入 -->
								    <Rcgn_Yr_IncmAm></Rcgn_Yr_IncmAm>
						    		<!-- 被保人前往目的地个数 -->
								    <Rcgn_LvFr_Pps_Lnd_Num>0</Rcgn_LvFr_Pps_Lnd_Num>
								    <!-- 被保人目的地个数循环 -->
						    		<Pps_List>
						    			<Pps_Detail>
							          		<Rcgn_LvFr_Pps_Lnd />
							        	</Pps_Detail>
						    		</Pps_List>
						    		
						    		<!-- 受益人个数 -->
								    <Benf_Num><xsl:value-of select="count(Bnf)"/></Benf_Num>
								    <Benf_List>
								    	<!-- 受益人信息循环 -->
								    	<xsl:for-each select="Bnf">
									    	<Benf_Detail>
									    		<!-- 代理保险受益人类型代码 -->
											    <AgIns_Benf_TpCd><xsl:value-of select="Type"/></AgIns_Benf_TpCd>
									    		<!-- #序号编号 -->
											    <SN_ID></SN_ID>
									    		<!-- 代理保险受益次序值 -->
											    <AgIns_Bnft_Ord_Val><xsl:value-of select="Grade"/></AgIns_Bnft_Ord_Val>
									    		<!-- 受益人姓名 -->
											    <Benf_Nm><xsl:value-of select="Name"/></Benf_Nm>
									    		<!-- 受益人性别代码 -->
											    <Benf_Gnd_Cd>
													<xsl:call-template name="tran_sex">
														<xsl:with-param name="Sex">
															<xsl:value-of select="Sex" />
														</xsl:with-param>
													</xsl:call-template>
												</Benf_Gnd_Cd>
									    		<!-- 受益人出生日期 -->
											    <Benf_Brth_Dt><xsl:value-of select="Birthday"/></Benf_Brth_Dt>
									    		<!-- 受益人证件类型代码 -->
											    <Benf_Crdt_TpCd>						    	
											    	<xsl:call-template name="tran_idtype">
														<xsl:with-param name="idtype">
															<xsl:value-of select="IDType" />
														</xsl:with-param>
													</xsl:call-template>
												</Benf_Crdt_TpCd>
									    		<!-- 受益人证件号 -->
											    <Benf_Crdt_No><xsl:value-of select="IDNo"/></Benf_Crdt_No>
									    		<!-- 受益人证件生效日期 -->
											    <Benf_Crdt_EfDt></Benf_Crdt_EfDt>
									    		<!-- 受益人证件失效日期 -->
											    <Benf_Crdt_ExpDt></Benf_Crdt_ExpDt>
											    <!--受益人联系地址国家地区代码  -->
												<BenfCtcAdr_CtyRgon_Cd></BenfCtcAdr_CtyRgon_Cd>
												<!-- 受益人省代码 -->
												<Benf_Prov_Cd></Benf_Prov_Cd>
												<!-- 受益人市代码 -->
												<Benf_City_Cd></Benf_City_Cd>
												<!-- 受益人区县代码 -->
												<Benf_CntyAndDstc_Cd></Benf_CntyAndDstc_Cd>
												<!-- 受益人详细地址内容 -->
												<Benf_Dtl_Adr_Cntnt><xsl:value-of select="AddressContent"/></Benf_Dtl_Adr_Cntnt>
									    		<!-- 受益人国籍代码 -->
											    <Benf_Nat_Cd>
													<xsl:call-template name="tran_Nationality">
													<xsl:with-param name="Nationality">
															<xsl:value-of select="Nationality"/>
													</xsl:with-param>
													</xsl:call-template>								    
											    </Benf_Nat_Cd>
									    		<!-- 受益人与被保人关系代码 -->
											    <Benf_And_Rcgn_ReTpCd>
											    	<xsl:if test = "RelaToInsured = '00'">0133043</xsl:if>
													<xsl:if test = "RelaToInsured= '02'">0133010</xsl:if>
													<xsl:if test = "(RelaToInsured = '01') and (Sex = '0')">0133015</xsl:if>
													<xsl:if test = "(RelaToInsured = '01') and (Sex = '1')">0133016</xsl:if>
													<xsl:if test = "(RelaToInsured = '03') and (Sex = '0')">0133011</xsl:if>
													<xsl:if test = "(RelaToInsured = '03') and (Sex = '1')">0133012</xsl:if>
													<xsl:if test = "RelaToInsured = '06'">0133010</xsl:if>
												</Benf_And_Rcgn_ReTpCd>
									    		<!-- 受益比例 -->
											   <!--  <Bnft_Pct><xsl:value-of select="Lot"/></Bnft_Pct> -->
											    <Bnft_Pct><xsl:value-of select="format-number(Lot div 100.00 , '#0.0000' )" /></Bnft_Pct>
									    		<!-- 受益人通讯地址 -->
											    <Benf_Comm_Adr><xsl:value-of select="Address"/></Benf_Comm_Adr>
									    	</Benf_Detail>
								    	</xsl:for-each>
								    </Benf_List>
						    		
						    		<!-- #循环记录条数 -->
									<Rvl_Rcrd_Num><xsl:value-of select="count(Risk)"/></Rvl_Rcrd_Num>				    		
						    		<Busi_List>
						    			<xsl:for-each select="Risk">
						    				<Busi_Detail>
									    		<!-- 险种编号 -->
											    <Cvr_ID><xsl:value-of select="RiskCode"/></Cvr_ID>
									    		<!-- 险种名称 -->
											    <Cvr_Nm><xsl:value-of select="RiskName"/></Cvr_Nm>
									    		<!-- 主附险标识 -->
									    		<xsl:if test="RiskCode = MainRiskCode">
									    			<MainAndAdlIns_Ind>1</MainAndAdlIns_Ind>
									    		</xsl:if>
											    <xsl:if test="RiskCode != MainRiskCode">
											    	<MainAndAdlIns_Ind>0</MainAndAdlIns_Ind>
											    </xsl:if>
									    		<!-- 投保份数 -->
											    <Ins_Cps><xsl:value-of select="Mult"/></Ins_Cps>
									    		<!-- 保费金额 -->
											    <InsPrem_Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/></InsPrem_Amt>
									    		<!-- 投保保额 -->
											    <Ins_Cvr><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/></Ins_Cvr>
									    		<!-- 投保方案信息 -->
											    <Ins_Scm_Inf/>
									    		<!-- 可选部分身故保险金额 -->
											    <Opt_Part_DieIns_Amt>0.00</Opt_Part_DieIns_Amt>
									    		<!-- 首次额外追加保费 -->
											    <FTm_Extr_Adl_InsPrem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/></FTm_Extr_Adl_InsPrem>
									    		<!-- 投资方式代码 -->
											    <Ivs_MtdCd></Ivs_MtdCd>
									    		<!-- 建行帐号 -->
											    <CCB_AccNo></CCB_AccNo>
											    
									    		<!-- 保险年期类别代码 -->
											    <Ins_Yr_Prd_CgyCd>
													<xsl:call-template name="tran_PbIYF">
														<xsl:with-param name="PbInsuYearFlag">
															<xsl:value-of select="InsuYearFlag"/>
														</xsl:with-param>
													</xsl:call-template>	
											    </Ins_Yr_Prd_CgyCd>
									    		<!-- 保险期限 -->
											    <Ins_Ddln><xsl:value-of select="InsuYear"/></Ins_Ddln>
									    		<!-- 保险周期代码 -->
											    <Ins_Cyc_Cd></Ins_Cyc_Cd>
									    		<!-- 保费缴纳方式代码 -->
											    <InsPrem_PyF_MtdCd>
											    	<xsl:call-template name="tran_Contpayintv">
														<xsl:with-param name="payintv">
															<xsl:value-of select="PayIntv" />
														</xsl:with-param>
													</xsl:call-template>
											    </InsPrem_PyF_MtdCd>
									    		<!-- 保费缴费期数 -->
											    <InsPrem_PyF_Prd_Num><xsl:value-of select="PayEndYear"/></InsPrem_PyF_Prd_Num>
									    		<!-- 保费缴纳周期代码 -->
											    <InsPrem_PyF_Cyc_Cd></InsPrem_PyF_Cyc_Cd>
									    		<!-- 特别约定信息 -->
											    <Spcl_Apnt_Inf><xsl:value-of select="SpecContent"/></Spcl_Apnt_Inf>
									    		<!-- 年金领取类别代码 -->
											    <Anuty_Drw_CgyCd>
											    	<xsl:call-template name="tran_Contpayintv">
														<xsl:with-param name="payintv">
															<xsl:value-of select="PayIntv" />
														</xsl:with-param>
													</xsl:call-template>
											    </Anuty_Drw_CgyCd>
									    		<!-- 年金领取期数 -->
											    <Anuty_Drw_Prd_Num><xsl:value-of select="GetYear"/></Anuty_Drw_Prd_Num>
									    		<!-- 年金领取周期代码 -->
											    <Anuty_Drw_Cyc_Cd>
											       <xsl:call-template name="tran_Contpayintv">
														<xsl:with-param name="payintv">
															<xsl:value-of select="PayIntv" />
														</xsl:with-param>
													</xsl:call-template>
											    </Anuty_Drw_Cyc_Cd>
									    		<!-- 年金处理方式代码 -->
											    <Anuty_Pcsg_MtdCd>2</Anuty_Pcsg_MtdCd>
									    		<!-- 满期保险金领取方式类别代码 -->
											    <ExpPrmmRcvModCgyCd><xsl:value-of select="GetIntv"/></ExpPrmmRcvModCgyCd>
									    		<!-- 生存金领取周期代码 -->
											    <SvBnf_Drw_Cyc_Cd/>
									    		<!-- 红利领取方式 -->
											    <XtraDvdn_Pcsg_MtdCd><xsl:value-of select="BonusGetMode"/></XtraDvdn_Pcsg_MtdCd>
									    		<!-- 约定保费垫交标志 -->
											    <ApntInsPremPyAdvnInd><xsl:value-of select="AutoPayFlag"/></ApntInsPremPyAdvnInd>
									    		<!-- 自动续保标志 -->
											    <Auto_RnwCv_Ind></Auto_RnwCv_Ind>
									    		<!-- 红利分配标志 -->
											    <XtraDvdn_Alct_Ind></XtraDvdn_Alct_Ind>
									    		<!-- 减额交清标志 -->
											    <RdAmtPyCls_Ind></RdAmtPyCls_Ind>
									    		<!-- 保险金额递减方式代码 -->
											    <Ins_Amt_Dgrs_MtdCd></Ins_Amt_Dgrs_MtdCd>
									    		<!-- 保单生效日期 -->
											    <InsPolcy_EfDt><xsl:value-of select="CValiDate"/></InsPolcy_EfDt>
									    		<!-- 保单拟生效日期 -->
											    <InsPolcy_Intnd_EfDt></InsPolcy_Intnd_EfDt>
									    		<!-- 保单失效日期 -->
											    <InsPolcy_ExpDt><xsl:value-of select="InsuEndDate"/></InsPolcy_ExpDt>
									    		<!-- 紧急联系人 -->
											    <Emgr_CtcPsn></Emgr_CtcPsn>
									    		<!-- 紧急联系人与被保人关系类型代码 -->
											    <EmgrCtcPsnAndRcReTpCd></EmgrCtcPsnAndRcReTpCd>
									    		<!-- 紧急联系电话 -->
											    <Emgr_Ctc_Tel></Emgr_Ctc_Tel>
									    		<!-- 银行贷款合同编号 -->
											    <Bnk_Loan_Ctr_ID></Bnk_Loan_Ctr_ID>
									    		<!-- 贷款合同编号失效日期 -->
											    <Ln_Ctr_ExpDt></Ln_Ctr_ExpDt>
									    		<!-- 未还贷款金额 -->
											    <Upd_Loan_Amt>0.00</Upd_Loan_Amt>
									    		<!-- 主单保单凭证号码 -->
											    <PrimBlInsPolcyVchr_No></PrimBlInsPolcyVchr_No>
									    		<!-- 投连险账户个数 -->
											    <IvLkIns_Acc_Num>0</IvLkIns_Acc_Num>
												<!-- 投连险账户循环 -->
												<PayAcctCode_List>
													<PayAcctCode_Detail>
														<ILIVA_ID />
														<ILIVA_Nm />
														<Ivs_Tp_Alct_Pctg />
														<Adl_Ins_Fee_Alct_Pctg />
													</PayAcctCode_Detail>
												</PayAcctCode_List>

												<!-- 保险费逾期未付方式代码 -->
												<InsFeeOdue_NtPa_MtdCd></InsFeeOdue_NtPa_MtdCd>
											    <!-- #保留字段一 -->
												<Rsrv_Fld_1></Rsrv_Fld_1>
												<!-- #保留字段二 -->
												<Rsrv_Fld_2></Rsrv_Fld_2>
												<!-- #保留字段三 -->
												<Rsrv_Fld_3></Rsrv_Fld_3>
												<!-- #保留字段四 -->
												<Rsrv_Fld_4></Rsrv_Fld_4>
												<!-- #保留字段五 -->
												<Rsrv_Fld_5></Rsrv_Fld_5>
												<!-- #保留字段六 -->
												<Rsrv_Fld_6></Rsrv_Fld_6>
												<!-- #保留字段七 -->
												<Rsrv_Fld_7></Rsrv_Fld_7>
												<!-- #保留字段八 -->
												<Rsrv_Fld_8></Rsrv_Fld_8>
												<!-- #保留字段九 -->
												<Rsrv_Fld_9></Rsrv_Fld_9>
												<!-- #保留字段十 -->
												<Rsrv_Fld_10></Rsrv_Fld_10>
						    				</Busi_Detail>
						    			</xsl:for-each>
						    		</Busi_List>
						    		
									<!-- 投保单号 -->
									<Ins_BillNo><xsl:value-of select="substring(ProposalPrtNo,1,13)"/></Ins_BillNo>
									<!-- 保单号码 -->
									<InsPolcy_No><xsl:value-of select="ContNo"/></InsPolcy_No>
									<!-- 投保人缴费账号 -->
									<Plchd_PyF_AccNo></Plchd_PyF_AccNo>
									<!-- 投保人领取账号 -->
									<Plchd_Drw_AccNo></Plchd_Drw_AccNo>
									<!-- 被保人帐号 -->
									<Rcgn_AccNo></Rcgn_AccNo>
									<!-- 受益人帐号 -->
									<Benf_AccNo></Benf_AccNo>
									<!-- 续期缴费支付方式代码 -->
									<Rnew_PyF_PyMd_Cd></Rnew_PyF_PyMd_Cd>
									<!-- 代理保险合约状态代码 -->
									<AcIsAR_StCd>
										<xsl:call-template name="tran_Status">
											<xsl:with-param name="Status">
												<xsl:value-of select="PolicyStatus" />
											</xsl:with-param>
										</xsl:call-template>
									</AcIsAR_StCd>
									<!-- 代理保险客户条线类型代码 -->
									<AgIns_Cst_Line_TpCd></AgIns_Cst_Line_TpCd>
									<!-- 保单介质类型代码 -->
									<InsPolcy_Medm_TpCd></InsPolcy_Medm_TpCd>
									<!-- 建行机构编码 -->
									<CCBIns_ID></CCBIns_ID>
									<!-- 一级分行号 -->
									<Lv1_Br_No></Lv1_Br_No>
									<!-- 网点保险兼职代理业务许可证编码 -->
									<BOInsPrAgnBsnLcns_ECD></BOInsPrAgnBsnLcns_ECD>
									<!-- 网点分管代理保险业务负责人编号 -->
									<BOIChOfAgInsBsnPnp_ID></BOIChOfAgInsBsnPnp_ID>
									<!-- 网点分管代理保险业务负责人姓名 -->
									<BOIChOfAgInsBsnPnp_Nm></BOIChOfAgInsBsnPnp_Nm>
									<!-- 投保人操作编码 -->
									<Ins_Mnplt_Psn_ID></Ins_Mnplt_Psn_ID>
									<!-- 网点销售人员编码 -->
									<BO_Sale_Stff_ID><xsl:value-of select="SaleStaff"/></BO_Sale_Stff_ID>
									<!-- 网点销售人员姓名" -->
									<BO_Sale_Stff_Nm><xsl:value-of select="SaleName"/></BO_Sale_Stff_Nm>
									<!-- 销售人员代理保险从业人员资格证书编号-->
									<Sale_Stff_AICSQCtf_ID><xsl:value-of select="SaleCertNo"/></Sale_Stff_AICSQCtf_ID>
									<!-- 推荐客户经理编号 -->
									<RcmCst_Mgr_ID></RcmCst_Mgr_ID>
									<!-- 推荐客户经理姓名 -->
									<RcmCst_Mgr_Nm></RcmCst_Mgr_Nm>
									<!-- 保险专案类别代码 -->
									<Ins_Prj_CgyCd></Ins_Prj_CgyCd>
									<!-- 见费出单类型代码 -->
									<PydFeeOutBill_CgyCd></PydFeeOutBill_CgyCd>
									<!-- 保单实际销售地区编码 -->
									<InsPolcyActSaleRgonID></InsPolcyActSaleRgonID>
									<!-- 保险客户名单提供地区编码 -->
									<Ins_CsLs_Prvd_Rgon_ID></Ins_CsLs_Prvd_Rgon_ID>
									<!-- 保单领取方式代码 -->
									<InsPolcy_Rcv_MtdCd></InsPolcy_Rcv_MtdCd>
									<!-- 争议处理方式代码 -->
									<Dspt_Pcsg_MtdCd></Dspt_Pcsg_MtdCd>
									<!-- 争议仲裁机构名称 -->
									<Dspt_Arbtr_Inst_Nm></Dspt_Arbtr_Inst_Nm>
									<!-- 告知事项标志 -->
									<Ntf_Itm_Ind></Ntf_Itm_Ind>
									<!-- #保留字段十一 -->
									<Rsrv_Fld_11></Rsrv_Fld_11>
									<!-- #保留字段十二 -->
									<Rsrv_Fld_12></Rsrv_Fld_12>
									<!-- #保留字段十三 -->
									<Rsrv_Fld_13></Rsrv_Fld_13>
									<!-- #保留字段十四 -->
									<Rsrv_Fld_14></Rsrv_Fld_14>
									<!-- #保留字段十五 -->
									<Rsrv_Fld_15></Rsrv_Fld_15>
									<!-- #保留字段十六 -->
									<Rsrv_Fld_16></Rsrv_Fld_16>
									<!-- #保留字段十七 -->
									<Rsrv_Fld_17></Rsrv_Fld_17>
									<!-- #保留字段十八 -->
									<Rsrv_Fld_18></Rsrv_Fld_18>
									<!-- #保留字段十九 -->
									<Rsrv_Fld_19></Rsrv_Fld_19>
									<!-- #保留字段二十 -->
									<Rsrv_Fld_20></Rsrv_Fld_20>
									<!-- 附言备录 -->
									<Pstcrpt_Rmrk></Pstcrpt_Rmrk>
			        			
			        			</Insu_Detail>
			        			</xsl:for-each>
			        		</Insu_List>
			        	</APP_ENTITY>
			        </ENTITY>
	      	</TX_BODY>
	      	
		</TX>
	</xsl:template>


	<!-- 性别 -->
	<xsl:template name="tran_sex" >
		<xsl:param name="Sex"></xsl:param>
		<xsl:if test="$Sex = 0">01</xsl:if><!-- 男 -->
		<xsl:if test="$Sex = 1">02</xsl:if><!-- 女 -->
	</xsl:template>

	<xsl:template name="tran_LiRenteDrawMode">
		<xsl:param name="GETLiRenteDrawMode"></xsl:param>
		<xsl:choose>
			<xsl:when test="$GETLiRenteDrawMode = 'Y'">00</xsl:when>          <!--  0-一次给付  -->
			<xsl:when test="$GETLiRenteDrawMode = 'Y'">01</xsl:when>          <!--  1-月给付  -->
			<xsl:when test="$GETLiRenteDrawMode = 'Y'">03</xsl:when>          <!--  3-季给付  -->
			<xsl:when test="$GETLiRenteDrawMode = 'Y'">06</xsl:when>          <!--  6-半年给付  -->
			<xsl:when test="$GETLiRenteDrawMode = 'Y'">12</xsl:when>         <!--  12-年给付  -->
		</xsl:choose>
		
	</xsl:template>

	<!-- 国籍代码 -->
	<xsl:template name="tran_Nationality">
		<xsl:param name="Nationality"></xsl:param>
		<xsl:if test="$Nationality = 'CHN'">156</xsl:if>
		<xsl:if test="$Nationality = 'JAN'">392</xsl:if><!--日本 -->
		<xsl:if test="$Nationality = 'USA'">840</xsl:if><!--美国-->
		<xsl:if test="$Nationality = 'ENG'">826</xsl:if><!--英国 -->
		<xsl:if test="$Nationality = 'FRA'">250</xsl:if><!--法国-->
		<xsl:if test="$Nationality = 'DEU'">276</xsl:if><!--德国 -->
		<xsl:if test="$Nationality = 'KOR'">410</xsl:if><!--韩国 -->
		<xsl:if test="$Nationality = 'SG'">702</xsl:if><!--新加坡 -->
		<xsl:if test="$Nationality = 'INA'">360</xsl:if><!--印度尼西亚-->
		<xsl:if test="$Nationality = 'IND'">356</xsl:if><!--印度-->
		<xsl:if test="$Nationality = 'ITA'">380</xsl:if><!--意大利 -->
		<xsl:if test="$Nationality = 'MY'">458</xsl:if><!--马来西亚 -->
		<xsl:if test="$Nationality = 'THA'">764</xsl:if><!--泰国-->
		<xsl:if test="$Nationality = ''">999</xsl:if><!--其他-->
	</xsl:template>

	<!-- 证件类型 -->
	<xsl:template name="tran_idtype">
		<xsl:param name="idtype"></xsl:param>
		<xsl:choose>
			<xsl:when test="$idtype = '0'">1010</xsl:when><!-- 公民身份证号码 -->
			<xsl:when test="$idtype = '2'">1022</xsl:when><!-- 军官证 -->
			<xsl:when test="$idtype = 'D'">1032</xsl:when><!-- 警官证 -->
			<xsl:when test="$idtype = 'A'">1021</xsl:when><!-- 解放军士兵证 -->
			<xsl:when test="$idtype = '4'">1040</xsl:when><!-- 户口簿 -->
			<xsl:when test="$idtype = 'B'">1080</xsl:when><!-- (港澳)回乡证及通行证 -->
			<xsl:when test="$idtype = '1'">1050</xsl:when><!-- 护照-->
			<xsl:when test="$idtype = '5'">1060</xsl:when><!-- 学生证-->
			<xsl:when test="$idtype = '6'">2999</xsl:when><!-- 对公其他证件-->
			<xsl:when test="$idtype = '3'">1100</xsl:when><!-- 驾照 -->
			<xsl:when test="$idtype = 'C'">1011</xsl:when><!-- 临时居民身份证 -->
			<xsl:when test="$idtype = 'E'">1160</xsl:when><!-- 台湾居民身份证 台胞证 -->
			<xsl:otherwise>
					<xsl:value-of select="1999" /><!-- 其他 -->
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- 关系 -->
	<xsl:template name="tran_relation">
		<xsl:param name="RelaToInsured"></xsl:param>
		<xsl:if test="$RelaToInsured = 00">0133043</xsl:if><!--本人 -->
		<xsl:if test="$RelaToInsured = 02">0133010</xsl:if><!--配偶 -->
		<xsl:if test="$RelaToInsured = 01">0133015</xsl:if><!--父亲 -->
		<xsl:if test="$RelaToInsured = 01">0133016</xsl:if><!--母亲 -->
		<xsl:if test="$RelaToInsured = 03">0133011</xsl:if><!--儿子 -->
		<xsl:if test="$RelaToInsured = 03">0133012</xsl:if><!--女儿-->
		<xsl:if test="$RelaToInsured = 06">0133021</xsl:if><!--其它亲属 -->
	</xsl:template>

	
	<!-- 保障年期/年龄标志 -->
	<xsl:template name="tran_PbIYF">
		<xsl:param name="PbInsuYearFlag">02</xsl:param>
		<xsl:choose>
			<xsl:when test="$PbInsuYearFlag = 'Y'">03</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 'M'">03</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 'D'">03</xsl:when>
			<xsl:when test="$PbInsuYearFlag = 'A'">04</xsl:when>
			<xsl:otherwise>03</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<!-- 保单的缴费方式 -->
	<xsl:template name="tran_Contpayintv">
		<xsl:param name="payintv">0</xsl:param>
		<xsl:choose>
			<xsl:when test="$payintv = -1">01</xsl:when><!-- 不定期交 -->
			<xsl:when test="$payintv = 0">02</xsl:when><!-- 趸交 -->
		<!--	<xsl:when test="$payintv = 1">1</xsl:when> 月交 -->
		<!--	<xsl:when test="$payintv = 3">3</xsl:when> 季交 -->
		<!--	<xsl:when test="$payintv = 6">6</xsl:when> 半年交 -->
			<xsl:when test="$payintv = 12">03</xsl:when><!-- 年交 -->
			<xsl:when test="$payintv = 98">04</xsl:when><!-- 交至某确定年 -->
			<xsl:when test="$payintv = 99">05</xsl:when><!-- 终身交费 -->
			<xsl:otherwise>
				<xsl:value-of select="-1" />
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	
	<!-- 代理保险合约状态 -->
	<xsl:template name="tran_Status">
		<xsl:param name="Status"></xsl:param>
		<!-- 有效 -->
		<xsl:if test="$Status = ''">076012</xsl:if>
		<!-- 有效 -->
		<xsl:if test="$Status = '1'">076012</xsl:if>
		<!-- 犹退 -->
		<xsl:if test="$Status = '2'">076024</xsl:if>
		<!-- 退保 -->
		<xsl:if test="$Status = '3'">076025</xsl:if>
		<!-- 失效 -->
		<xsl:if test="$Status = '4'">076034</xsl:if>
	</xsl:template>
	
</xsl:stylesheet>	