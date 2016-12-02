<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">

<xsl:template match="/">
<TXLife>	 
	<xsl:copy-of select="TranData/Head" />
	<TXLifeResponse> 
	<TransRefGUID></TransRefGUID> 
 	 <TransType>1013</TransType> 
 	 <TransExeDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/></TransExeDate> 
 	 <TransExeTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></TransExeTime> 
		<xsl:apply-templates select="TranData/Body" />
	</TXLifeResponse> 
</TXLife>
</xsl:template>  
 
 
<xsl:template name="OLifE" match="Body">
<xsl:variable name="MainRisk" select="Risk[RiskCode=MainRiskCode]" />
<OLifE>   
	<Holding id="cont">
		<CurrencyTypeCode>001</CurrencyTypeCode><!-- ? -->
		<!-- ������Ϣ --> 
		<Policy>    
			<!-- ������ContNo -->
			<PolNumber><xsl:value-of select="ContNo"/></PolNumber>
			<!--���ڱ���-->
			<PaymentAmt><xsl:value-of select="Prem"/></PaymentAmt> <!-- ���ڱ��շѺϼƣ�����PaymentAmtת��Ϊ���ֽ�� ��RMBPaymentAmtԪ�� -->
			<Life> 
				<CoverageCount><xsl:value-of select="count(Risk)"/></CoverageCount>
				<xsl:apply-templates select="Risk" />
			</Life>   
			<!--������Ϣ-->
			<ApplicationInfo>
				<!--Ͷ�����-->
				<HOAppFormNumber><xsl:value-of select="ProposalPrtNo"/></HOAppFormNumber>
				<SubmissionDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10($MainRisk/PolApplyDate)"/></SubmissionDate>
			</ApplicationInfo>
			<OLifEExtension>  
				<!-- �ر�Լ�� --> 
				<SpecialClause><xsl:value-of select="$MainRisk/SpecContent"/></SpecialClause>
				<!-- ���չ�˾����绰 -->
				<InsurerDialNumber><xsl:value-of select="ComPhone"/></InsurerDialNumber>		
				<!--  -->
				<WithdrawalDescription></WithdrawalDescription>	
			</OLifEExtension>
		</Policy>
	</Holding>
</OLifE>

 <!--�������д�ӡ�ӿ�-->
	<Print>
		<SubVoucher>
			<!--ƾ֤����3����-->
			<VoucherType>3</VoucherType>
			<!--��ҳ��-->
			<PageTotal>1</PageTotal>
			<Text>
				<!--ҳ��-->
				<PageNum>1</PageNum>
					<!--һ�д�ӡ������--> 
					<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
					<TextRowContent/> 
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent/>
					<TextRowContent><xsl:text></xsl:text>                                           ������ҳ                                           </TextRowContent>
					<TextRowContent><xsl:text></xsl:text>���ѷ�ʽ��<xsl:apply-templates select="$MainRisk/PayIntv"/><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('���ҵ�λ�������',80,$Falseflag)"/></TextRowContent>
					<TextRowContent><xsl:text></xsl:text>----------------------------------------------------------------------------------------------</TextRowContent>
					<TextRowContent><xsl:text></xsl:text>������ͬ��ţ�<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(ContNo, 52)"/><xsl:text></xsl:text>���պ�ͬ��Ч���ڣ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10($MainRisk/CValiDate)" /><xsl:text></xsl:text></TextRowContent>
					<TextRowContent><xsl:text></xsl:text>----------------------------------------------------------------------------------------------</TextRowContent>      
					<TextRowContent><xsl:text></xsl:text>Ͷ������Ϣ                                                                                    </TextRowContent>
					<TextRowContent><xsl:text></xsl:text><xsl:text>������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 18)"/>
																									<xsl:text>�Ա�</xsl:text><xsl:apply-templates select="Appnt/Sex "/><xsl:text>      </xsl:text>
																									<xsl:text>�������ڣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Appnt/Birthday)"/><xsl:text>      </xsl:text>  
																									<xsl:text>֤�����룺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/IDNo , 20)"/>
					</TextRowContent>
					<TextRowContent><xsl:text></xsl:text>��������Ϣ                                                                                    </TextRowContent>
					<TextRowContent><xsl:text></xsl:text><xsl:text>������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Insured/Name, 18)"/>
																									<xsl:text>�Ա�</xsl:text><xsl:apply-templates select="Insured/Sex"/><xsl:text>      </xsl:text>
																									<xsl:text>�������ڣ�</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Insured/Birthday)"/><xsl:text>      </xsl:text>
																									<xsl:text>֤�����룺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Insured/IDNo , 20)"/>
					</TextRowContent>
					<TextRowContent><xsl:text></xsl:text>----------------------------------------------------------------------------------------------</TextRowContent> 
					<TextRowContent><xsl:text></xsl:text>��������Ϣ                                                                                    </TextRowContent>
					<xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" />  
					<xsl:if test="Bnf/Name = '����' and Bnf/Sex = ''">
						<xsl:for-each select="Bnf">
						       <TextRowContent><xsl:text></xsl:text><xsl:text>������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 94)"/>
                               </TextRowContent>
						</xsl:for-each>
						<TextRowContent/>
						<TextRowContent/>
					</xsl:if>                                                                         

					<xsl:if test="count(Bnf) = 1 and Bnf/Sex != '' ">
					    <xsl:for-each select="Bnf">
						       <TextRowContent><xsl:text></xsl:text><xsl:text>������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 18)"/>
                               <xsl:text>�Ա�</xsl:text><xsl:apply-templates select="Sex"/><xsl:text></xsl:text>
                               <xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('�������ڣ�', 16, $Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Birthday)"/><xsl:text></xsl:text>
                               <xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('���������', 14, $Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text> </xsl:text>
                               <xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('����˳��', 14, $Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 4)"/></TextRowContent>
						</xsl:for-each>
						<TextRowContent/>
						<TextRowContent/>
					</xsl:if>
					<xsl:if test="count(Bnf) = 2">
					    <xsl:for-each select="Bnf">
						       <TextRowContent><xsl:text></xsl:text><xsl:text>������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 18)"/>
                               <xsl:text>�Ա�</xsl:text><xsl:apply-templates select="Sex"/><xsl:text></xsl:text>
                               <xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('�������ڣ�', 16, $Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Birthday)"/><xsl:text></xsl:text>
                               <xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('���������', 14, $Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text> </xsl:text>
                               <xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('����˳��', 14, $Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 4)"/></TextRowContent>
						</xsl:for-each>
						<TextRowContent/>
					</xsl:if>
					<xsl:if test="count(Bnf) = 3">
					    <xsl:for-each select="Bnf">
						       <TextRowContent><xsl:text></xsl:text><xsl:text>������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 18)"/>
                               <xsl:text>�Ա�</xsl:text><xsl:apply-templates select="Sex"/><xsl:text></xsl:text>
                               <xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('�������ڣ�', 16, $Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Birthday)"/><xsl:text></xsl:text>
                               <xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('���������', 14, $Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text> </xsl:text>
                               <xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('����˳��', 14, $Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 4)"/></TextRowContent>
						</xsl:for-each>
					</xsl:if>
					
					<TextRowContent><xsl:text></xsl:text>----------------------------------------------------------------------------------------------</TextRowContent>
					<TextRowContent><xsl:text></xsl:text>��������                               �����ڼ�    ��������    ����/����    ���շ�/�������շ� </TextRowContent>
					<xsl:if test="count(Risk) = 2 ">
					<xsl:for-each select="Risk">
						<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
						<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
						<TextRowContent><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 38)"/>
																		<xsl:text> </xsl:text>
																					<xsl:choose>
																							<xsl:when test="InsuYearFlag = '2'">
																								<xsl:text>  </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>��  </xsl:text>
																							</xsl:when>  
																							<xsl:otherwise> 
																								<xsl:text>��</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>����</xsl:text>
																							</xsl:otherwise>
																					</xsl:choose>
																		<xsl:text>   </xsl:text>
																					<xsl:choose>
																							<xsl:when test="PayEndYearFlag = '2'">
																								<xsl:text>  </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>��  </xsl:text>
																							</xsl:when>  
																							<xsl:otherwise> 
																								<xsl:text>��</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>����</xsl:text>
																							</xsl:otherwise>
																					</xsl:choose>
																			<xsl:text></xsl:text>
																				<xsl:choose>
																							<xsl:otherwise>
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Amnt,12,$Falseflag)"/><xsl:text>Ԫ</xsl:text>
																							</xsl:otherwise>
																				 </xsl:choose>
																			 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,14,$Falseflag)"/>Ԫ<xsl:text>     </xsl:text></TextRowContent></xsl:for-each>				
					</xsl:if>
					<xsl:if test="count(Risk) = 1 ">
					<xsl:for-each select="Risk">
						<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
						<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
						<TextRowContent><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 38)"/>
																		<xsl:text> </xsl:text>
																					<xsl:choose>
																							<xsl:when test="InsuYearFlag = '2'">
																								<xsl:text>  </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>��  </xsl:text>
																							</xsl:when>  
																							<xsl:otherwise> 
																								<xsl:text>��</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>����</xsl:text>
																							</xsl:otherwise>
																					</xsl:choose>
																		<xsl:text>   </xsl:text>
																					<xsl:choose>
																							<xsl:when test="PayEndYearFlag = '2'">
																								<xsl:text>  </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>��  </xsl:text>
																							</xsl:when>  
																							<xsl:otherwise> 
																								<xsl:text>��</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>����</xsl:text>
																							</xsl:otherwise>
																					</xsl:choose>
																			<xsl:text></xsl:text>
																				<xsl:choose>
																							<xsl:otherwise>
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Amnt,12,$Falseflag)"/><xsl:text>Ԫ</xsl:text>
																							</xsl:otherwise>
																				 </xsl:choose>
																			 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,14,$Falseflag)"/>Ԫ<xsl:text>     </xsl:text></TextRowContent></xsl:for-each>				
					<TextRowContent />
					</xsl:if>
					<xsl:if test="FirstAddPrem != '' ">
						<TextRowContent>
							<xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.MidplatUtil.GetFirstAddMoney(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(FirstAddPrem)), 94 ,$Falseflag)"/>
						</TextRowContent>
					</xsl:if> 	
					<xsl:if test="FirstAddPrem = '' or FirstAddPrem = null ">
						<TextRowContent />
					</xsl:if>				                                                                  
					<TextRowContent><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.MidplatUtil.GetCountMoney(PremText , java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)), 94 ,$Falseflag)"/></TextRowContent>
					<TextRowContent><xsl:text></xsl:text>----------------------------------------------------------------------------------------------</TextRowContent>
					<xsl:choose>
						<xsl:when test="Risk/RiskCode = 'NHY'">
							<TextRowContent><xsl:text></xsl:text>Ͷ���˻�����                                                               �������<xsl:text>           </xsl:text></TextRowContent>
							<xsl:if test="count(Risk/AccountList/Account) = 0">
						    	<TextRowContent />
						    	<TextRowContent />
						    	<TextRowContent />
						    	<TextRowContent />
						    	<TextRowContent />
						    	<TextRowContent />
					        </xsl:if>
					        <xsl:if test="count(Risk/AccountList/Account) = 1">
						    	<xsl:for-each select="Risk/AccountList/Account">
					            	<TextRowContent><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AccName, 30)"/><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AccRate,50,$Falseflag)"/> %<xsl:text>            </xsl:text></TextRowContent>
								</xsl:for-each>
						    	<TextRowContent />
						    	<TextRowContent />
						    	<TextRowContent />
						    	<TextRowContent />
						    	<TextRowContent />
					        </xsl:if>
					        <xsl:if test="count(Risk/AccountList/Account) = 2">
						    	<xsl:for-each select="Risk/AccountList/Account">
					            	<TextRowContent><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AccName, 30)"/><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AccRate,50,$Falseflag)"/> %<xsl:text>            </xsl:text></TextRowContent>
								</xsl:for-each>
						    	<TextRowContent />
						    	<TextRowContent />
						    	<TextRowContent />
						    	<TextRowContent />
					        </xsl:if>
					        <xsl:if test="count(Risk/AccountList/Account) = 3">
						    	<xsl:for-each select="Risk/AccountList/Account">
					            	<TextRowContent><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AccName, 30)"/><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AccRate,50,$Falseflag)"/> %<xsl:text>            </xsl:text></TextRowContent>
								</xsl:for-each>
						    	<TextRowContent />
						    	<TextRowContent />
						    	<TextRowContent />
					        </xsl:if>
						</xsl:when>
						<xsl:otherwise>
							<TextRowContent />
							<TextRowContent />
							<TextRowContent />
							<TextRowContent />
							<TextRowContent />
							<TextRowContent />
							<TextRowContent />
						</xsl:otherwise>
					</xsl:choose>
					<TextRowContent><xsl:text></xsl:text>Ͷ��ʱ��<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('��ͬ��Ч������Ͷ��', 86 , $Falseflag)"/></TextRowContent>
					<TextRowContent><xsl:text></xsl:text>----------------------------------------------------------------------------------------------</TextRowContent>
					<TextRowContent><xsl:text></xsl:text><xsl:text>�ر�Լ����*</xsl:text>
																					<xsl:choose>
																							<xsl:when test="$MainRisk/SpecContent = ''">
																								<xsl:text>��                                                                                 </xsl:text>
																							</xsl:when>
																							<xsl:otherwise> 
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($MainRisk/SpecContent , 83)"/>
																							</xsl:otherwise>
																					</xsl:choose>
					</TextRowContent>
	        <TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent />
					<TextRowContent><xsl:text></xsl:text>----------------------------------------------------------------------------------------------</TextRowContent>
					<TextRowContent><xsl:text></xsl:text>��˾��ַ���Ϻ����ֶ�����½���컷·166��δ���ʲ�����19¥<xsl:text>   </xsl:text>ȫ����ѯ�绰��400-670-5566          </TextRowContent>
					<TextRowContent><xsl:text></xsl:text>Ӫҵ���㣺<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentCom,30)"/><xsl:text>                  </xsl:text>�����Ŷӣ�<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentComName , 26)"/><xsl:text></xsl:text></TextRowContent>
					<TextRowContent><xsl:text></xsl:text>������ӡʱ�䣺<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCurDateTime()" /><xsl:text>                         </xsl:text>ҵ��Ա��<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(AgentName , 25)"/><xsl:text></xsl:text></TextRowContent>	
			</Text>
		</SubVoucher>
		 <xsl:if test="$MainRisk/CashValues/CashValue != ''">
	<SubVoucher>
		<!--ƾ֤���� 4 �ּ۱�-->
		<VoucherType>4</VoucherType>
		<!--��ҳ��-->
		<PageTotal>1</PageTotal>
		<Text> 
					     <PageNum>1</PageNum>
					     <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
					     <TextRowContent/>
					     <TextRowContent/>
					     <TextRowContent/>
					     <TextRowContent/>
					     <TextRowContent/>
					     <TextRowContent/>
					     <TextRowContent/>
					     <TextRowContent/>
					     <TextRowContent><xsl:text></xsl:text>                                          �ֽ��ֵ��                                          </TextRowContent>
					     <TextRowContent/>
					     <TextRowContent><xsl:text></xsl:text>������ͬ���룺<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(ContNo, 26)"/><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.MidplatUtil.GetInsuredName(Insured/Name) , 54 ,$Falseflag)"/></TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>�������ƣ�<xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($MainRisk/RiskName , 84)"/></TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>----------------------------------------------------------------------------------------------</TextRowContent>
                           <TextRowContent>�����������  �ֽ��ֵ  �����������  �ֽ��ֵ  ���������   �ֽ��ֵ   �����������  �ֽ��ֵ</TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>----------------------------------------------------------------------------------------------</TextRowContent>				
						   <xsl:for-each select="Risk/CashValues/CashValue">						   
							   <xsl:if test="(EndYear) mod 4 = '1'">					   
				                   <TextRowContent>
				                   <xsl:variable name="EndYear1" select="java:com.sinosoft.midplat.common.MidplatUtil.GetNextOne(EndYear)"/>
								   <xsl:variable name="EndYear2" select="java:com.sinosoft.midplat.common.MidplatUtil.GetNextTwo(EndYear)"/>
								   <xsl:variable name="EndYear3" select="java:com.sinosoft.midplat.common.MidplatUtil.GetNextThree(EndYear)"/>												 			    									       
	                               		<xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,10)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash), 10)"/>                       		
	                               		<xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($EndYear1,10)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(//CashValue[$EndYear1=EndYear]/Cash),10)"/>
	                               		<xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($EndYear2,10)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(//CashValue[$EndYear2=EndYear]/Cash),10)"/>
	                               		<xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($EndYear3,9)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(//CashValue[$EndYear3=EndYear]/Cash),9)"/>
								   </TextRowContent>
							   </xsl:if>		
						   </xsl:for-each>	
					       <TextRowContent />
					       <TextRowContent />
					       <TextRowContent />
					       <TextRowContent />
					       <TextRowContent><xsl:text></xsl:text>---------------------------------------���������¿հף�---------------------------------------</TextRowContent>
					       <TextRowContent />
					       <TextRowContent><xsl:text></xsl:text>----------------------------------------------------------------------------------------------</TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>*���պ�ͬ���ʱ������˾��Ͷ�����˻�����ͬ���ֽ��ֵ���ֽ��ֵ�������ͬ������ձ���ͬ���ֽ� </TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>��ֵ����                                                                                      </TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>*���ڱ��ֽ��ֵ����δ�г��ı������ĩ�ֽ��ֵ��������������м�����һ��ı���ͬ���ֽ��ֵ���� </TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>��˾���紹ѯ                                                                                </TextRowContent>
					       <TextRowContent><xsl:text></xsl:text>*�ڸ��ݱ������й�����֧����ʹ�������ֽ���ֽ��ֵ������١�                                 </TextRowContent>
		 </Text>
	 </SubVoucher>
</xsl:if>
	</Print>
</xsl:template>



<!-- ������Ϣ -->
<xsl:template name="Coverage" match="Risk">
<xsl:variable name="MainRisk" select="Risk[RiskCode=MainRiskCode]" />
<Coverage>
<xsl:attribute name="id"><xsl:value-of select="concat('risk_', string(position()))" /></xsl:attribute>
	<!-- �������� -->
	<PlanName><xsl:value-of select="RiskName"/></PlanName>
	<!-- ���ִ��� -->
	<ProductCode><xsl:apply-templates select="RiskCode" /></ProductCode>

	<xsl:choose>
		<!-- �����ձ�־ -->
		<xsl:when test="RiskCode=MainRiskCode">
			<IndicatorCode tc="1">1</IndicatorCode>
		</xsl:when>
		<xsl:otherwise>
			<IndicatorCode tc="2">2</IndicatorCode>
		</xsl:otherwise>
	</xsl:choose> 
	  
	<!-- �ɷѷ�ʽ Ƶ�� -->  
	<PaymentMode>
		<xsl:value-of select="PayIntv" /> 
	</PaymentMode> 
     
	<!-- Ͷ����� -->   
	<InitCovAmt>   
		<xsl:choose>
			<xsl:when test="Amnt>=0">
				<xsl:value-of select="Amnt" />
			</xsl:when>
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</InitCovAmt>
	<!-- ? -->
	<FaceAmt>
		<xsl:choose>
			<xsl:when test="Amnt>=0"><xsl:value-of select="Amnt" /></xsl:when>
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose>
	</FaceAmt>
	<!-- Ͷ���ݶ� -->
	<IntialNumberOfUnits>    
		<xsl:choose>
			<xsl:when test="Mult>=0"><xsl:value-of select="Mult" /></xsl:when>
			<xsl:otherwise>--</xsl:otherwise>
		</xsl:choose> 
	</IntialNumberOfUnits>
	<!-- ���ֱ��� -->
	<ModalPremAmt><xsl:value-of select="Prem"/></ModalPremAmt>
	<!-- ������ -->
	<EffDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(CValiDate)" /></EffDate>
	<!-- ������ֹ���� -->
	<TermDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(InsuEndDate)" /></TermDate>
	<!-- ������ʼ���� -->  
	<FirstPaymentDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(SignDate)" /></FirstPaymentDate>
	<!-- �ɷ���ֹ���� -->
	<FinalPaymentDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(PayEndDate)" /></FinalPaymentDate>
	
	<OLifEExtension>
		<!-- �ɷ����ںͽɷ��������� --> 
			<PaymentDurationMode><xsl:value-of select="PayEndYearFlag" /></PaymentDurationMode>
			<PaymentDuration><xsl:value-of select="PayEndYear"/></PaymentDuration>		 		
		<!-- �������ںͱ����������� -->
			<DurationMode><xsl:value-of select="InsuYearFlag" /></DurationMode>
			<Duration><xsl:value-of select="InsuYear"/></Duration>
	</OLifEExtension>
</Coverage> 
</xsl:template> 
 




<!-- �Ա� -->
<xsl:template name="tran_sex" match="Sex">
<xsl:choose>      
	<xsl:when test=".='M'">��</xsl:when>	<!-- �� -->
	<xsl:when test=".='F'">Ů</xsl:when>	<!-- Ů -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template> 

<!-- ֤�����ͣ�����-->
<xsl:template name="tran_GovtIDTC" match="IDType">
<xsl:choose>
	<xsl:when test=".=0">0</xsl:when>	<!-- ���֤ -->
	<xsl:when test=".=1">1</xsl:when>	<!-- ���� -->
	<xsl:when test=".=2">2</xsl:when>	<!-- ����֤ -->
	<xsl:when test=".=3">3</xsl:when>	<!-- ʿ��֤  -->
	<xsl:when test=".=4">4</xsl:when>	<!-- ����֤  -->
	<xsl:when test=".=5">5</xsl:when>	<!-- ��ʱ���֤  -->
	<xsl:when test=".=6">6</xsl:when>	<!-- ���ڱ�  -->
	<xsl:when test=".=8">7</xsl:when>	<!-- ����  -->
	<xsl:when test=".=7">9</xsl:when>	<!-- ����֤  -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- ��ϵ -->
<xsl:template name="tran_RelationRoleCode" match="RelaToInsured">
<xsl:choose>
	<xsl:when test=".=01">1</xsl:when>	<!-- ��ż -->
	<xsl:when test=".=03">2</xsl:when>	<!-- ��ĸ -->
	<xsl:when test=".=04">3</xsl:when>	<!-- ��Ů -->
	<xsl:when test=".=21">4</xsl:when>	<!-- �游��ĸ -->
	<xsl:when test=".=31">5</xsl:when>	<!-- ����Ů -->
	<xsl:when test=".=12">6</xsl:when>	<!-- �ֵܽ��� -->
	<xsl:when test=".=25">7</xsl:when>	<!-- �������� -->
	<xsl:when test=".=00">8</xsl:when>	<!-- ���� -->
	<xsl:when test=".=27">9</xsl:when>	<!-- ���� -->
	<xsl:when test=".=25">99</xsl:when>	<!-- ���� -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose> 
</xsl:template>
  
<!-- ���ִ��� -->
<xsl:template name="tran_ProductCode" match="RiskCode">
<xsl:choose>
	<xsl:when test=".='PAC'">001</xsl:when>	<!-- ������ʢ�����������գ������ͣ�B�� -->
	<xsl:when test=".='NBHY'">002</xsl:when>	<!-- ������ʢ�����������գ������ͣ�A�� -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- �ɷ���ʽ
<xsl:template name="tran_PaymentMethod" match="PayMode">
<xsl:choose>
	<xsl:when test=".=4">1</xsl:when>	
	
	<xsl:when test=".=3">2</xsl:when>	 
	<xsl:when test=".=1">3</xsl:when>	
	<xsl:when test=".=1">4</xsl:when>	
	<xsl:when test=".=1">5</xsl:when>	
	<xsl:when test=".=4">6</xsl:when>	 
	<xsl:when test=".=8">7</xsl:when>	
	<xsl:when test=".=9">9</xsl:when>	
	
	<xsl:otherwise></xsl:otherwise>
</xsl:choose> 
</xsl:template>
 -->
 
       
   <!-- ���ؽɷѷ�ʽ -->
<xsl:template name="tran_PayIntv">
    <xsl:param name="PayIntv"></xsl:param> 
	<xsl:choose>
	    <xsl:when test="PayIntv =12">1</xsl:when>  
		<xsl:when test="PayIntv =1">2</xsl:when>
		<xsl:when test="PayIntv =6">3</xsl:when>
		<xsl:when test="PayIntv =3">4</xsl:when>
		<xsl:when test="PayIntv =0">5</xsl:when> 
		<xsl:when test="PayIntv =-1">6</xsl:when> 
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>    
	 
 </xsl:template> 
 
 
<!-- �ɷ�Ƶ��2 -->
<xsl:template  match="PayIntv">
<xsl:choose>
	<xsl:when test=".=1">���</xsl:when>	<!-- ��� -->
	<xsl:when test=".=2">�½�</xsl:when>	<!-- �½� -->
	<xsl:when test=".=3">�����</xsl:when>	<!-- ����� -->
	<xsl:when test=".=4">����</xsl:when>	<!-- ���� -->
	<xsl:when test=".=5">����</xsl:when>	<!-- ���� -->
	<xsl:when test=".=6">�����ڽ�</xsl:when>	<!-- ������ -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>  

    

<!-- �ս����ڱ�־��ת�� -->
<xsl:template name="tran_PaymentDurationMode" match="PayEndYearFlag">
<xsl:choose>
	<xsl:when test=".='A'">1</xsl:when>	<!-- ����ĳȷ������ -->
	<xsl:when test=".='Y'">2</xsl:when>	<!-- �� -->
	<xsl:when test=".='M'">3</xsl:when>	<!-- �� -->
	<xsl:when test=".='D'">4</xsl:when>	<!-- �� -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>
 
<!-- �����������ڱ�־ -->
<xsl:template name="tran_DurationMode" match="InsuYearFlag">
<xsl:choose>
	<xsl:when test=".='A'">1</xsl:when>	<!-- ����ĳȷ������ -->
	<xsl:when test=".='Y'">2</xsl:when>	<!-- �걣 -->
	<xsl:when test=".='M'">3</xsl:when>	<!-- �±� -->
	<xsl:when test=".='D'">4</xsl:when>	<!-- �ձ� -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template> 



<!-- ������ȡ��ʽ��ת�� -->	
<xsl:template match="Risk[RiskCode=MainRiskCode]/BonusGetMode">
<xsl:choose>
	<xsl:when test=".=1">�ۻ���Ϣ</xsl:when>
	<xsl:when test=".=2">��ȡ�ֽ�</xsl:when>
	<xsl:when test=".=3">�ֽ�����</xsl:when>
	<xsl:when test=".=5">�����</xsl:when>
	<xsl:when test=".=''">   --   </xsl:when> 
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>
</xsl:stylesheet>
