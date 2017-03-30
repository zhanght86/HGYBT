<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:template match="TranData">
	<ABCB2I><!-- ũҵ�����µ��ɷ�Ӧ���� -->
			<xsl:apply-templates select="Body"/>
	</ABCB2I>
	</xsl:template>
	<!-- ������׳ɹ����ŷ�������Ľ�� -->
	<xsl:template name="Base" match="Body">
	    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
		<xsl:variable name="MainRisk" select="Risk[RiskCode=MainRiskCode]"/>
		<xsl:variable name="SumPremYuan" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
		<xsl:if test="//Flag='0'">
			<!--������Ϣ-->
			<App>
			   <Ret>
						<!-- ���յ��� -->
						<PolicyNo>
							<xsl:value-of select="ContNo"/>
						</PolicyNo>
						<!-- ����ӡˢ�� -->
						<VchNo>
							<xsl:value-of select="substring(ContPrtNo,1,13)"/>
						</VchNo>
						<!-- ǩ������ -->
						<AcceptDate>
							<xsl:value-of select="Risk[RiskCode=MainRiskCode]/SignDate"/>
						</AcceptDate>
						<!-- ������Ч�� -->
						<ValidDate>
							<xsl:value-of select="Risk[RiskCode=MainRiskCode]/CValiDate"/>
						</ValidDate>
						<!-- ������ֹ�� -->
						<!-- ��ȷ�� -->
						<PolicyDuedate>
							<xsl:value-of select="Risk[RiskCode=MainRiskCode]/InsuEndDate"/>
						</PolicyDuedate>
						<!-- �ɷ����� -->
						<!-- ��ȷ�� -->
						<DueDate/>
						<!-- ҵ��Ա���� -->
						<UserId>
							<xsl:value-of select="AgentCode"/>
						</UserId>
						<!-- ���չ�˾�������� -->
						<ComPhone>
							<xsl:value-of select="ComPhone"/>
						</ComPhone>
						<!-- �������� -->
						<RiskName>
							<xsl:value-of select="Risk[RiskCode=MainRiskCode]/RiskName"/>
						</RiskName>
						<!-- �˻����� -->
						<!-- ��ȷ�� -->
						<PayAccount>
							<xsl:value-of select="AccName"/>
						</PayAccount>
						<!-- �ܱ��� -->
						<Prem>
							<xsl:value-of select="$SumPremYuan"/>
						</Prem>
						<!-- �������� -->
						<Risks>
							<xsl:for-each select="Risk[RiskCode=MainRiskCode]">
								<!-- ���� -->
									<Name>
										<xsl:value-of select="RiskName"/>
									</Name>
									<Share>
										<xsl:value-of select="Mult"/>
									</Share>
									<Prem>
										<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
									</Prem>
									<PayDueDate>
										<xsl:value-of select="PayEndYear"/>
									</PayDueDate>
									<PayType>
										<xsl:call-template name="TempPayIntv">
											<xsl:with-param name="PayIntv"> 
												<xsl:value-of select="PayIntv"/>
											</xsl:with-param>   
										</xsl:call-template>  
									</PayType>
							</xsl:for-each>
						</Risks>
						<!--  ������   ��һ�������մ���-->
						<Addt>
						    <Count><xsl:value-of select="count(Risk[RiskCode!=MainRiskCode])"/></Count>
						    <xsl:if test="count(Risk[RiskCode!=MainRiskCode])=1">
							     <xsl:for-each select="Risk[RiskCode!=MainRiskCode]">
							       <Name1><xsl:value-of select="RiskName"/></Name1>
				                   <Share1><xsl:value-of select="Mult"/></Share1>
				                   <Prem1><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/></Prem1>
				                   <PayDueDate1><xsl:value-of select="PayEndYear"/></PayDueDate1>
				                   <PayType1>
				                   <xsl:call-template name="TempPayIntv">
												<xsl:with-param name="PayIntv"> 
													<xsl:value-of select="PayIntv"/>
												</xsl:with-param>   
								    </xsl:call-template> 
				                   </PayType1>
				                   <RiskCode1><xsl:value-of select="RiskCode"/></RiskCode1>
							    </xsl:for-each>
						    </xsl:if>
						</Addt>
						<!-- ���ִ�ӡ�б� -->
						<Prnts>
								<!-- ���ִ�ӡ��Ϣ -->
								<Count>0</Count>
								<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')"/>
								<Prnt>��</Prnt>
								<Prnt>��</Prnt>
								<Prnt>��</Prnt>
								<Prnt>��</Prnt>
								<Prnt>��</Prnt>
								<Prnt>��</Prnt>
								<Prnt>��</Prnt>
								<Prnt>��</Prnt>
								<Prnt>��</Prnt> 
								<Prnt>��</Prnt> 
								<Prnt>��</Prnt> 
								<Prnt>��</Prnt> 
								<Prnt><xsl:text>         </xsl:text>���պ�ͬ��:<xsl:value-of select="ContNo"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 48)"/>���ҵ�λ�������/Ԫ</Prnt>
				 				<Prnt><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------ </Prnt>
				 				<Prnt /> 
				 				<Prnt><xsl:text>         </xsl:text>���պ�ͬ�����գ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 41)"/>���պ�ͬ��Ч���ڣ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Risk/CValiDate)" /> </Prnt>
			     				<Prnt><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Prnt>
								<Prnt><xsl:text>         </xsl:text><xsl:text>Ͷ����������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 18)"/>
																										   <xsl:text>�Ա�</xsl:text><xsl:apply-templates select="Appnt/Sex"/><xsl:text>   </xsl:text>
																										   <xsl:text>    ���䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(Appnt/Birthday), 2)"/><xsl:text>            </xsl:text>  
																										   <xsl:text>֤�����룺</xsl:text><xsl:value-of select="Appnt/IDNo"/>
								</Prnt>
								<Prnt><xsl:text>         </xsl:text><xsl:text>������������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Insured/Name, 18)"/>
																										   <xsl:text>�Ա�</xsl:text><xsl:apply-templates select="Insured/Sex"/><xsl:text>   </xsl:text>
																										   <xsl:text>    ���䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(Insured/Birthday), 2)"/><xsl:text>            </xsl:text>
																										   <xsl:text>֤�����룺</xsl:text><xsl:value-of select="Insured/IDNo"/>
								</Prnt>				
						    	<xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" />  
								<xsl:variable name="sflag" select="java:java.lang.Boolean.parseBoolean('true')" />    
								<xsl:variable name="num" select="count(Bnf) " />
								<xsl:for-each select="Bnf">
								<Prnt><xsl:text>         </xsl:text><xsl:text></xsl:text>������������<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 12)"/>																
								<xsl:text>�Ա�:</xsl:text><xsl:text> </xsl:text><xsl:apply-templates select="Sex"/><xsl:text>       </xsl:text>
								 <xsl:text>����˳��: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 10)"/>	
								<xsl:text>�������:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text>%</xsl:text>
			                   	</Prnt>
								</xsl:for-each>
								<xsl:choose>
									<xsl:when test="$num = 0"><Prnt><xsl:text>         </xsl:text><xsl:text></xsl:text>����������:<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ����', 13)"/>																
									<xsl:text>		   �Ա�:</xsl:text><xsl:text> </xsl:text>--<xsl:text>       </xsl:text>
									<xsl:text>	����˳��: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 10)"/>	
									<xsl:text>�������:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 3, $Falseflag)"/>
									</Prnt></xsl:when>
								</xsl:choose>
								<Prnt />
								<Prnt />
								<Prnt />
								<Prnt />
								<Prnt />
								<Prnt />
								<Prnt><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Prnt>
								<Prnt><xsl:text>         </xsl:text>��������                          �����ڼ�    �����ڼ�    ���ѷ�ʽ  ������������/����   ���շ�</Prnt>
								<xsl:for-each select="Risk">
							 	<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
							  	<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
							  	<xsl:variable name="Mult" select="Mult"/>
							  	<Prnt>
								<xsl:text>         </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 36)"/>
								                                                     <xsl:choose>
																							<xsl:when test="InsuYearFlag = 'A'">
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 0,$Falseflag)"/><xsl:text>����</xsl:text>
																							</xsl:when>
																							<xsl:when test="InsuYearFlag = 'Y'">
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>��  </xsl:text>
																							</xsl:when>  
																							<xsl:when test="InsuYearFlag = 'M'">
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>��  </xsl:text>
																							</xsl:when>
																							<xsl:otherwise> 
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>��</xsl:text>
																							</xsl:otherwise>
								                                                     </xsl:choose>
																			<xsl:text>     </xsl:text>
																			<xsl:choose>
																							<xsl:when test="PayIntv = 0">
																								<xsl:text>����        </xsl:text>
																							</xsl:when>
																							<xsl:when test="PayEndYearFlag = 'Y'">
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>��        </xsl:text>
																							</xsl:when>
																							<xsl:when test="PayEndYearFlag = 'M'">
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>��        </xsl:text>
																							</xsl:when>
																							<xsl:when test="PayEndYearFlag = 'D'">
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>��        </xsl:text>
																							</xsl:when>  
																							<xsl:otherwise> 
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>����    </xsl:text>
																							</xsl:otherwise>
																			</xsl:choose>
																			<xsl:apply-templates select="PayIntv"/>
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Mult,11,$Falseflag)"/><xsl:text>��</xsl:text>
																			 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,13,$Falseflag)"/>Ԫ</Prnt>
								</xsl:for-each>
								<Prnt></Prnt>
								<Prnt></Prnt>
								<Prnt></Prnt>
								<Prnt></Prnt>
								<Prnt></Prnt>
								<Prnt></Prnt>
				 				<Prnt><xsl:text>         </xsl:text>���շѺϼƣ�<xsl:value-of select="PremText"/>��RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>Ԫ��</Prnt>
				 				<Prnt><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Prnt>
				 				<xsl:choose>
					 				<xsl:when test = "RiskCode != '221201'">
								 	 <Prnt><xsl:text>         </xsl:text>������ȡ��ʽ��<xsl:apply-templates select="Risk[RiskCode=MainRiskCode]/BonusGetMode" /> </Prnt>
					 				</xsl:when>
								 	<xsl:otherwise>
								 	 <Prnt><xsl:text>         </xsl:text>�������հף�</Prnt>
						 	 		 <Prnt />
								 	</xsl:otherwise>
				 				</xsl:choose>
								<xsl:choose>
									<xsl:when test="$num=1 or $num=0"><Prnt/><Prnt /><Prnt /><Prnt /></xsl:when><!-- �����˸���Ϊ0���߸���Ϊ1�������һ���ģ������˵���Ϣ��ռһ�� -->
									<xsl:when test="$num=2"><Prnt /><Prnt /><Prnt /></xsl:when>
									<xsl:when test="$num=3"><Prnt /><Prnt /></xsl:when>
									<xsl:when test="$num=4"><Prnt /></xsl:when>
									<xsl:otherwise></xsl:otherwise>
								</xsl:choose>
								<Prnt><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Prnt>
								<Prnt><xsl:text>         </xsl:text><xsl:text>�ر�Լ����</xsl:text>
																						    <xsl:choose>
																									<xsl:when test="$MainRisk/SpecContent = ''">
																										<xsl:text>���ޣ�</xsl:text>
																									</xsl:when>
																									<xsl:otherwise> 
																										<xsl:value-of select="$MainRisk/SpecContent"/>
																									</xsl:otherwise>
																						    </xsl:choose>
								</Prnt>
								<Prnt/>
								<Prnt/>
								<Prnt/>
								<Prnt/>
								<Prnt/>
								<Prnt/>
								<Prnt/>
								<Prnt><xsl:text>         </xsl:text>------------------------------------------------------------------------------------------------</Prnt>
								<Prnt><xsl:text>         </xsl:text>�����������ƣ�<xsl:value-of select="/TranData/Body/AgentComName"/></Prnt>
								<Prnt><xsl:text>         </xsl:text>����������Ա����/���룺<xsl:value-of select="/TranData/Body/SaleName"/>/<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/SaleStaff,40)"/>��ӡʱ�䣺<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:text> </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></Prnt>
								<Prnt><xsl:text>         </xsl:text>��������������<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/AgentName, 54)"/>��������绰��<xsl:value-of select="/TranData/Body/AgentPhone"/></Prnt>
								<Prnt><xsl:text>         </xsl:text>ǩ��������<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/ComLocation, 54)"/></Prnt>
								<Prnt><xsl:text>         </xsl:text>������ַ��<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/ComName, 54)"/></Prnt>
								<Prnt></Prnt>
								<Prnt></Prnt>
								<Prnt></Prnt>
								<Prnt></Prnt>
								<Prnt></Prnt>
								<Prnt></Prnt>
								<Prnt></Prnt>
								<Prnt></Prnt>
								<Prnt></Prnt>
								<Prnt></Prnt>
								<Prnt><xsl:text>         </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 76,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11($MainRisk/SignDate)"/></Prnt>
								<Prnt/>
						</Prnts>
				
						<Messages>
								<Count>0</Count>
								 <xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
								 <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('false')" />
								 <Prnt></Prnt>
								 <Prnt></Prnt>
								 <Prnt></Prnt>
								 <Prnt></Prnt>
								 <Prnt></Prnt>
								 <Prnt><xsl:text>     </xsl:text>                                        �ֽ��ֵ��                     </Prnt>
								 <Prnt/>
								 <Prnt><xsl:text>     </xsl:text>���պ�ͬ�ţ�<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(ContNo, 50)"/><xsl:text></xsl:text>�������ս� <xsl:value-of select="$Amnt"/></Prnt>
								 <Prnt><xsl:text>     </xsl:text>�������ƣ�<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($MainRisk/RiskName, 52)"/>���ҵ�λ�������/Ԫ</Prnt>
								 <Prnt><xsl:text>     </xsl:text>------------------------------------------------------------------------------------------------</Prnt>
								 <Prnt><xsl:text>              </xsl:text>  �������ĩ  �ֽ��ֵ  | �������ĩ  �ֽ��ֵ  | �������ĩ  �ֽ��ֵ</Prnt>
								 <Prnt><xsl:text>     </xsl:text>------------------------------------------------------------------------------------------------</Prnt>
								 <xsl:text></xsl:text>     <xsl:apply-templates select="/TranData/Body/Risk/CashValues"/>
								 <Prnt><xsl:text>     </xsl:text>------------------------------------------------------------------------------------------------</Prnt>
								 <Prnt><xsl:text>     </xsl:text>*�ֽ��ֵ���и������ֽ��ֵΪ�ͻ��������ɱ�����������б��շѵ�����£����������ĩ����Ӧ����</Prnt>
								 <Prnt><xsl:text>     </xsl:text>���ֵ�Ͷ���������ĸ���������ʹ���������á�</Prnt>
								 <Prnt><xsl:text>     </xsl:text>*���ڱ��ֽ��ֵ����δ�г��ı������ĩ�ֽ��ֵ��������������м�����һ��ı���ͬ���ֽ��ֵ������</Prnt>
								 <Prnt><xsl:text>     </xsl:text>��˾���紹ѯ��</Prnt>
								 <Prnt></Prnt>
								 <Prnt></Prnt>
								 <Prnt></Prnt>
								 <Prnt></Prnt>
								 <Prnt></Prnt>
								 <Prnt></Prnt>
								 <Prnt></Prnt>
								 <Prnt></Prnt>
				 				 <Prnt></Prnt>
								 <Prnt></Prnt>
								 <Prnt></Prnt>
								 <Prnt></Prnt>
								 <Prnt></Prnt>
								 <Prnt></Prnt>
								 <Prnt></Prnt>
								 <Prnt></Prnt>
						</Messages>
						<TbdPrnts>
								 <Count></Count>
								 <Prnt/>
								 <Prnt/>
								 <Prnt/>
								 <Prnt/>
								 <Prnt/>
								 <Prnt><xsl:text>     </xsl:text>                                        ���պ�ͬ�ʹ��ִ</Prnt>
								 <Prnt/>
								 <Prnt/>
								 <Prnt><xsl:text>     </xsl:text><xsl:text>���պ�ͬ��: </xsl:text><xsl:value-of select="ContNo"/></Prnt>
								 <Prnt><xsl:text>     </xsl:text><xsl:text>Ͷ����: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 19)"/><xsl:text>��������������      </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentName, 18)"/><xsl:text>����������룺</xsl:text><xsl:value-of select="AgentCode"/></Prnt>
								 <Prnt><xsl:text>     </xsl:text><xsl:text>    ��Ͷ�������յ���˾�ı��պ�ͬ�����պ�ͬ��: </xsl:text><xsl:value-of select="ContNo"/><xsl:text>���������պ�ͬ�������յ���</xsl:text></Prnt>
								 <Prnt><xsl:text>     </xsl:text><xsl:text>�ֽ��ֵ�����������������ϣ������ȷ�ϱ��պ�ͬ������ȷ���󡣱������Ķ�����Ʒ���Ͷ��</xsl:text></Prnt>
								 <Prnt><xsl:text>     </xsl:text><xsl:text>��ʾ�飬ȷ�����˽Ⲣ�Ͽɱ��պ�ͬ��ȫ�����ݣ�֪�����˵�Ȩ��������</xsl:text></Prnt>
								 <Prnt><xsl:text>     </xsl:text><xsl:text>    Ͷ����ǩ����                                    ǩ�����ڣ�         ��     ��     ��</xsl:text></Prnt>
								 <Prnt><xsl:text>     </xsl:text><xsl:text>                                      �������ɹ�˾��Ա��д</xsl:text></Prnt>
								 <Prnt><xsl:text>     </xsl:text><xsl:text>------------------------------------------------------------------------------------------------</xsl:text></Prnt>
								 <Prnt><xsl:text>     </xsl:text><xsl:text>    ���պ�ͬ��Ϊ </xsl:text><xsl:value-of select="ContNo"/><xsl:text>�ı��պ�ͬ���ʹ�ͻ����ɿͻ��ױ�ǩ��ȷ�ϣ��ֽ����պ�ͬ�ʹ�</xsl:text></Prnt>
								 <Prnt><xsl:text>     </xsl:text><xsl:text>��ִ���ء�</xsl:text></Prnt>
								 <Prnt/>
								 <Prnt><xsl:text>     </xsl:text><xsl:text>    ��������ǩ����	          ������ǩ�֣�           ���ڣ�      ��     ��     ��</xsl:text></Prnt>
						</TbdPrnts>
				<!-- ������׳ɹ����ŷ�������Ľ�� -->
			   </Ret>
		</App>
		</xsl:if>
	</xsl:template>
	
	
	 <!-- ѭ��ȡ�ֽ��ֵ��Ϣ -->
<xsl:template name="Cashs" match="CashValues">
		<xsl:for-each select="CashValue[EndYear &lt; (26)]">
		<xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
				<Prnt><xsl:text>                  </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+25)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+25)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+25)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+50)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+50)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+50)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></Prnt>
		</xsl:for-each>
</xsl:template>

	<!-- �Ա� -->
	<xsl:template name="tran_sex" match="Sex">
		<xsl:choose>
			<xsl:when test=".=0">��  </xsl:when>
			<!-- �� -->
			<xsl:when test=".=1">Ů  </xsl:when>
			<!-- Ů -->
			<xsl:when test=".=2">����</xsl:when>
			<!-- ���� -->
			<xsl:otherwise>--  </xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="tran_BonusGetMode" match="BonusGetMode">
		<xsl:choose>
			<xsl:when test=".=1">�ۻ���Ϣ  </xsl:when>
			<xsl:when test=".=2">��ȡ�ֽ�  </xsl:when>
			<xsl:when test=".=3">�ֽ�����  </xsl:when>
			<xsl:when test=".=4">���� </xsl:when>
			<xsl:when test=".=5">�����  </xsl:when>
			<xsl:otherwise>�� </xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template name="tran_PayIntv" match="PayIntv">
		<xsl:choose>
			<xsl:when test=".=12">�꽻     </xsl:when>	<!-- ��� -->
			<xsl:when test=".=1">�½�      </xsl:when>	<!-- �½� -->
			<xsl:when test=".=6">���꽻    </xsl:when>	<!-- ����� -->
			<xsl:when test=".=3">����      </xsl:when>	<!-- ���� -->
			<xsl:when test=".=0">����      </xsl:when>	<!-- ���� -->
			<xsl:when test=".=-1">�����ڽ�  </xsl:when>	<!-- ������ -->
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
	<xsl:template match="InsuYearFlag">
		<xsl:choose>
			<xsl:when test=".= 'Y'">��</xsl:when>
			<xsl:when test=".= 'A'">��</xsl:when>
			<xsl:otherwise>
				<xsl:value-of select="InsuYearFlag"/>
			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>
   <xsl:template name="TempPayIntv">
    <xsl:param name="PayIntv"></xsl:param> 
	<xsl:choose>
	    <xsl:when test="PayIntv =0">1</xsl:when>  
		<xsl:when test="PayIntv =1">2</xsl:when>
		<xsl:when test="PayIntv =3">3</xsl:when>
		<xsl:when test="PayIntv =6">4</xsl:when>
		<xsl:when test="PayIntv =12">5</xsl:when> 
		<xsl:when test="PayIntv =-1">6</xsl:when> 
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>    
	 
 </xsl:template>

</xsl:stylesheet>
