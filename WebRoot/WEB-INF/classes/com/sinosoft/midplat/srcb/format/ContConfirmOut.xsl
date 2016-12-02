<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output indent="yes"/>
	<xsl:template match="/">
			<SRCB>
			<xsl:if test="/TranData/Head/Flag='0'">
			<Body>
				<!--# # # # ������Ϣ # # # # -->
				<!-- Ͷ������ -->
				<AppDate>
					<xsl:value-of select="/TranData/Body/Risk[RiskCode=MainRiskCode]/PolApplyDate"/>
				</AppDate>
				<!-- Ͷ�������� -->
				<AppName>
					<xsl:value-of select="/TranData/Body/Appnt/Name"/>
				</AppName>
				<!-- �ɷ��˻� ��format�������-->
				<AccNo></AccNo>
				<!-- �ɷ��˻����� ��format�������-->
				<AccName></AccName>
				<!-- ������ֹ���� -->
				<BeginDate>
					<xsl:value-of select="/TranData/Body/Risk[RiskCode=MainRiskCode]/CValiDate"/>
				</BeginDate>
				<!-- ������ֹ���� -->
				<EndDate>
					<xsl:value-of select="/TranData/Body/Risk[RiskCode=MainRiskCode]/InsuEndDate"/>
				</EndDate>

				<!--������-->
				<PolicyNo>
					<xsl:value-of select="/TranData/Body/ContNo"/>
				</PolicyNo>
				<!--Ͷ������-->
				<InsuranceSlipNo>
					<xsl:value-of select="/TranData/Body/ProposalPrtNo"/>
				</InsuranceSlipNo>
				<!--����ӡˢ��-->
				<InsurancePrintNo>
					<xsl:value-of select="/TranData/Body/ContPrtNo"/>
				</InsurancePrintNo>
				<!--���� (��λ����)-->
				<Prem>
					<xsl:value-of select="/TranData/Body/Prem"/>
				</Prem>
				<!--���Ѵ�д-->
				<PremText>
					<xsl:value-of select="/TranData/Body/PremText"/>
				</PremText>


					<!--һ�д�ӡ������  ��ӡ�������豣�չ�˾��������ȶ--> 
					<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
					<xsl:variable name="MainRisk" select="/TranData/Body/Risk[RiskCode=MainRiskCode]" />
					<Print1/> 
					<Print1/>
					<Print1/>
					<Print1/> 
					<Print1/>
					<Print1/>
					<Print1/>
					<Print1/>
					<Print1/>
					<Print1/>
					<Print1/>
					<Print1><xsl:text>           </xsl:text>���պ�ͬ�ţ�<xsl:value-of select="/TranData/Body/ContNo"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 48)"/>���ҵ�λ�������/Ԫ </Print1>
					<Print1><xsl:text>           </xsl:text>------------------------------------------------------------------------------------------------</Print1>
					<Print1/>
					<Print1><xsl:text>           </xsl:text>���պ�ͬ�����գ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 41)"/>���պ�ͬ��Ч���ڣ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(/TranData/Body/Risk/CValiDate)" /> </Print1>
					<Print1><xsl:text>           </xsl:text>------------------------------------------------------------------------------------------------</Print1>      
					<Print1><xsl:text>           </xsl:text><xsl:text>Ͷ����������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Appnt/Name, 12)"/>
																									<xsl:text>�Ա�</xsl:text><xsl:apply-templates select="/TranData/Body/Appnt/Sex"/><xsl:text>   </xsl:text>
																									<xsl:text>    ���䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(/TranData/Body/Appnt/Birthday),2)"/><xsl:text>���ꡡ����</xsl:text>  
																									<xsl:text>֤�����룺</xsl:text><xsl:value-of select="/TranData/Body/Appnt/IDNo"/>
					</Print1>
					<Print1><xsl:text>           </xsl:text><xsl:text>������������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Insured/Name, 12)"/>
																									<xsl:text>�Ա�</xsl:text><xsl:apply-templates select="/TranData/Body/Insured/Sex"/><xsl:text>   </xsl:text>
																									<xsl:text>    ���䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(/TranData/Body/Insured/Birthday),2)"/><xsl:text>���ꡡ����</xsl:text>
																									<xsl:text>֤�����룺</xsl:text><xsl:value-of select="/TranData/Body/Insured/IDNo"/>
					</Print1>
					<xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" />  
					<xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" /> 
					<xsl:variable name="sflag" select="java:java.lang.Boolean.parseBoolean('true')" />    
					<xsl:variable name="num" select="count(/TranData/Body/Bnf) " />
					<xsl:for-each select="/TranData/Body/Bnf">
					<Print1><xsl:text>           </xsl:text><xsl:text></xsl:text>����������: <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 12)"/>																
					<xsl:text>�Ա�:</xsl:text><xsl:text> </xsl:text><xsl:apply-templates select="Sex"/><xsl:text>       </xsl:text>
					 <xsl:text>����˳��: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 10)"/>	
					<xsl:text>�������:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text>%</xsl:text>
                   	</Print1>
					</xsl:for-each>
					<xsl:choose>
					<xsl:when test="$num = 0"><Print1><xsl:text>           </xsl:text><xsl:text></xsl:text>����������:<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ����', 13)"/>																
					<xsl:text>�Ա�:</xsl:text><xsl:text> </xsl:text>--<xsl:text>       </xsl:text>
					 <xsl:text>����˳��: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 10)"/>	
					<xsl:text>�������:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 3, $Falseflag)"/>
                   	</Print1></xsl:when>
					</xsl:choose>		
					<Print1 />
					<Print1 />
					<Print1 />
					<Print1><xsl:text>           </xsl:text>------------------------------------------------------------------------------------------------</Print1>      
					<Print1><xsl:text>           </xsl:text>��������                          �����ڼ�    ��������    ���ѷ�ʽ  ������������/����   ���շ�</Print1>
					<xsl:for-each select="/TranData/Body/Risk">
					<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
					<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
					<Print1>
					<!-- �������� -->
					<xsl:text>           </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 36)"/>
					                                                     <xsl:choose>
																							<xsl:when test="InsuYearFlag = 'A'">
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>����</xsl:text>
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
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Amnt,11,$Falseflag)"/><xsl:text>Ԫ</xsl:text>
																			 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,13,$Falseflag)"/>Ԫ</Print1>
					</xsl:for-each>
					 <Print1/>
					 <Print1/>
					 <Print1/>
					 <Print1/>
					<Print1><xsl:text>           </xsl:text>���շѺϼƣ�<xsl:value-of select="TranData/Body/PremText"/>��RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(TranData/Body/Risk[RiskCode=MainRiskCode]/Prem)"/>Ԫ��</Print1>
					<Print1><xsl:text>           </xsl:text>------------------------------------------------------------------------------------------------</Print1>
					<xsl:choose><!-- ���ݻ�����Ʒ��221201�� �˴���ӡ���ǣ������հף������Ǻ�����ȡ��ʽ -->
					<xsl:when test="TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode != '221201' and TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode!='221301'">
					<Print1><xsl:text>           </xsl:text>������ȡ��ʽ��<xsl:apply-templates select="TranData/Body/Risk[RiskCode=MainRiskCode]/BonusGetMode" /></Print1>
					</xsl:when>
					<xsl:otherwise>
					<Print1><xsl:text>           </xsl:text>�������հף�</Print1>
					<Print1/>
					</xsl:otherwise>
					</xsl:choose>
					<xsl:choose>
					  <xsl:when test="TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode = '221301'">
					       <xsl:choose>
					          <xsl:when test="TranData/Body/Risk[RiskCode=MainRiskCode]/GetYearFlag='E'">
					           <Print1><xsl:text>           </xsl:text>�����ȡ��ʼ���䣺<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('��������',52)"/>�����ȡ���ޣ�<xsl:value-of select="TranData/Body/Risk[RiskCode=MainRiskCode]/GetTerms"/>��</Print1>
					          </xsl:when>
					          <xsl:otherwise>
					            <Print1><xsl:text>           </xsl:text>�����ȡ��ʼ���䣺<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/Risk[RiskCode=MainRiskCode]/GetYear,52)"/>�����ȡ���ޣ�<xsl:value-of select="TranData/Body/Risk[RiskCode=MainRiskCode]/GetTerms"/>��</Print1>
					         </xsl:otherwise>
					       </xsl:choose>
					  </xsl:when>
					</xsl:choose>
					<xsl:choose>
						<xsl:when test="$num=1 or $num=0"><Print1/><Print1 /><Print1 /><Print1 /></xsl:when><!-- �����˸���Ϊ0���߸���Ϊ1�������һ���ģ������˵���Ϣ��ռһ�� -->
						<xsl:when test="$num=2"><Print1 /><Print1 /><Print1 /></xsl:when>
						<xsl:when test="$num=3"><Print1 /><Print1 /></xsl:when>
						<xsl:when test="$num=4"><Print1 /></xsl:when>
						<xsl:otherwise></xsl:otherwise>
					</xsl:choose>
					<Print1><xsl:text>           </xsl:text>------------------------------------------------------------------------------------------------</Print1>
					<Print1><xsl:text>           </xsl:text><xsl:text>�ر�Լ����</xsl:text>
																					<xsl:choose>
																							<xsl:when test="$MainRisk/SpecContent = ''">
																								<xsl:text>���ޣ�</xsl:text>
																							</xsl:when>
																							<xsl:otherwise> 
																								<xsl:value-of select="$MainRisk/SpecContent"/>
																							</xsl:otherwise>
																					</xsl:choose>
					</Print1>
					<Print1 />
					<Print1 />
					<Print1 />
					<Print1 />
					<Print1 />
					<Print1 />
					<Print1 />
					<Print1><xsl:text>           </xsl:text>------------------------------------------------------------------------------------------------</Print1>
					<Print1><xsl:text>           </xsl:text>�����������ƣ�<xsl:value-of select="TranData/Body/AgentComName"/></Print1>
					<Print1><xsl:text>           </xsl:text>����������Ա����/���룺<xsl:value-of select="TranData/Body/SaleName"/>/<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/SaleStaff,38)"/>��ӡʱ�䣺<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:text> </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></Print1>
					<Print1><xsl:text>           </xsl:text>��������������<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/AgentName, 54)"/>��������绰��<xsl:value-of select="TranData/Body/AgentPhone"/></Print1>
					<Print1 />
					<Print1 />
					<Print1 />
					<Print1 />
					<Print1 />
					<Print1 />
					<Print1 />
					<Print1 />
					<Print1 />
					<Print1 />
					<Print1 />
					<Print1 />
					<Print1 />
					<Print1 />
					<Print1 />
					<Print1 />
					<Print1><xsl:text>����������������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('��', 66,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11($MainRisk/SignDate)"/></Print1>
					<Print1 /> 
					
					<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
					<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
					 <Print1/>
					 <Print1/>
					 <Print1/>
					 <Print1/>
					 <Print1/>
					 <Print1><xsl:text>           </xsl:text>���������������������������������������������ֽ��ֵ��                     </Print1>
					 <Print1/>
					 <Print1><xsl:text>           </xsl:text>���պ�ͬ�ţ�<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/ContNo, 50)"/><xsl:text></xsl:text>�������ս� <xsl:value-of select="$Amnt"/></Print1>
					 <Print1><xsl:text>           </xsl:text>�������ƣ�<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($MainRisk/RiskName, 52)"/>���ҵ�λ�������/Ԫ</Print1>
					 <Print1><xsl:text>           </xsl:text>------------------------------------------------------------------------------------------------</Print1>
					 <Print1><xsl:text>                    </xsl:text>  �������ĩ  �ֽ��ֵ  | �������ĩ  �ֽ��ֵ  | �������ĩ  �ֽ��ֵ</Print1>
				     <Print1><xsl:text>           </xsl:text>------------------------------------------------------------------------------------------------</Print1>
					 <xsl:text></xsl:text>           <xsl:apply-templates select="/TranData/Body/Risk/CashValues"/>
					 <Print1><xsl:text>           </xsl:text>------------------------------------------------------------------------------------------------</Print1>
					 <Print1><xsl:text>           </xsl:text>*�ֽ��ֵ���и������ֽ��ֵΪ�ͻ��������ɱ�����������б��շѵ�����£����������ĩ����Ӧ����</Print1>
				     <Print1><xsl:text>           </xsl:text>���ֵ�Ͷ���������ĸ���������ʹ���������á�</Print1>
				     <Print1><xsl:text>           </xsl:text>*���ڱ��ֽ��ֵ����δ�г��ı������ĩ�ֽ��ֵ��������������м�����һ��ı���ͬ���ֽ��ֵ������</Print1>
				     <Print1><xsl:text>           </xsl:text>��˾���紹ѯ��</Print1>				    
					 <Print1/>
					 <Print1/>
					 <Print1/>
					 <Print1/>
					 <Print1/>
					 <Print1/>
					 <Print1/>
					 <Print1/>
					 <Print1/>
					 <Print1/>
					 <Print1/>
					 <Print1/>
					 <Print1/>
					 <Print1/>
					 <Print1/>
					 <Print1/>



					       <Print1/>
					       <Print1/>
					       <Print1/>
					       <Print1/>
					       <Print1/>
					       <Print1><xsl:text>           </xsl:text>���������������������������������������������պ�ͬ�ʹ��ִ</Print1>
					       <Print1/>
					       <Print1/>
					       <Print1><xsl:text>           </xsl:text><xsl:text>���պ�ͬ��: </xsl:text><xsl:value-of select="TranData/Body/ContNo"/></Print1>
					       <Print1><xsl:text>           </xsl:text><xsl:text>Ͷ����: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/Appnt/Name, 19)"/><xsl:text>��������������      </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/AgentName, 18)"/><xsl:text>����������룺</xsl:text><xsl:value-of select="TranData/Body/AgentCode"/></Print1>
							<xsl:choose><!-- ���ݻ�����Ʒ��221201�� û�в�Ʒ˵��˵���飬��ӮC�� -->
							<xsl:when test="TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode != '221201' and TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode!='221301'">
						   <Print1><xsl:text>           </xsl:text><xsl:text>������Ͷ�������յ���˾�ı��պ�ͬ�����պ�ͬ��: </xsl:text><xsl:value-of select="TranData/Body/ContNo"/><xsl:text>���������պ�ͬ�������յ�����</xsl:text></Print1>
						   <Print1><xsl:text>           </xsl:text><xsl:text>���ֵ�����������������ϣ������ȷ�ϱ��պ�ͬ������ȷ���󡣱������Ķ�����Ʒ���Ͷ����ʾ</xsl:text></Print1>
						   <Print1><xsl:text>           </xsl:text><xsl:text>�顢��Ʒ˵���飬ȷ�����˽Ⲣ�Ͽɱ��պ�ͬ��ȫ�����ݣ�֪�����˵�Ȩ��������</xsl:text></Print1>
							</xsl:when>
							<xsl:otherwise>
							<Print1><xsl:text>           </xsl:text><xsl:text>������Ͷ�������յ���˾�ı��պ�ͬ�����պ�ͬ��: </xsl:text><xsl:value-of select="TranData/Body/ContNo"/><xsl:text>���������պ�ͬ�������յ�����</xsl:text></Print1>
						   <Print1><xsl:text>           </xsl:text><xsl:text>���ֵ�����������������ϣ������ȷ�ϱ��պ�ͬ������ȷ���󡣱������Ķ�����Ʒ���Ͷ����ʾ</xsl:text></Print1>
						   <Print1><xsl:text>           </xsl:text><xsl:text>�飬ȷ�����˽Ⲣ�Ͽɱ��պ�ͬ��ȫ�����ݣ�֪�����˵�Ȩ��������</xsl:text></Print1>
							</xsl:otherwise>
							</xsl:choose>
						   <Print1><xsl:text>           </xsl:text><xsl:text></xsl:text></Print1>
						   <Print1/>
						   <Print1><xsl:text>           </xsl:text><xsl:text>    Ͷ����ǩ����                                    ǩ�����ڣ�         ��     ��     ��</xsl:text></Print1>
						   <Print1><xsl:text>           </xsl:text><xsl:text>                                      �������ɹ�˾��Ա��д</xsl:text></Print1>
						   <Print1><xsl:text>           </xsl:text><xsl:text>------------------------------------------------------------------------------------------------</xsl:text></Print1>
						   <Print1><xsl:text>           </xsl:text><xsl:text>    ���պ�ͬ��Ϊ </xsl:text><xsl:value-of select="TranData/Body/ContNo"/><xsl:text>�ı��պ�ͬ���ʹ�ͻ����ɿͻ��ױ�ǩ��ȷ�ϣ��ֽ����պ�ͬ�ʹ�</xsl:text></Print1>
						   <Print1><xsl:text>           </xsl:text><xsl:text>��ִ���ء�</xsl:text></Print1>
						   <Print1/>
						   <Print1><xsl:text>           </xsl:text><xsl:text>    ��������ǩ����	          ������ǩ�֣�           ���ڣ�      ��     ��     ��</xsl:text></Print1>

				
			</Body>
			</xsl:if>
		</SRCB>
	</xsl:template>
	
	
	
<!-- ת�����ִ��� -->
	<xsl:template name="tran_RiskCode">
		<xsl:param name="RiskCode">0</xsl:param>
		<xsl:if test="$RiskCode = 231204">0001</xsl:if><!-- �к���Ӯ�Ƹ���ȫ���գ��ֺ��ͣ�C�� -->
		<xsl:if test="$RiskCode = 211902">0002</xsl:if><!-- �к���Ӯ����������˺�����A�� -->
		<xsl:if test="$RiskCode = 221201">0003</xsl:if><!-- �к����ݻ�����ȫ����A�� -->
		<xsl:if test="$RiskCode = 231302">0004</xsl:if><!-- �к�������������գ��ֺ��ͣ� -->
		<xsl:if test="$RiskCode = 221203">0005</xsl:if><!-- �к���������ȫ���� -->
		<xsl:if test="$RiskCode = 225501">0006</xsl:if><!-- �к������������ش󼲲����� -->
	</xsl:template>
	
<!-- ѭ��ȡ�ֽ��ֵ��Ϣ -->
<xsl:template name="Cashs" match="CashValues">
        <xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode != '221301'"> 
		<xsl:for-each select="CashValue[EndYear &lt; (26)]">
		<xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
				<Print1><xsl:text>                        </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+25)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+25)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+25)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+50)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+50)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+50)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></Print1>
		</xsl:for-each>
		</xsl:if>
		<xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode = '221301'"> 
		    <xsl:for-each select="CashValue[EndYear &lt; (34)]">
		    <xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
				<Print1><xsl:text>                        </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+33)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+33)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+33)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+66)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+66)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+66)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></Print1>
		</xsl:for-each>
		</xsl:if>
</xsl:template>

<!-- ������ȡ��ʽ��ת�� -->
<xsl:template match="Risk[RiskCode=MainRiskCode]/BonusGetMode">
<xsl:choose>
	<xsl:when test=".=1">�ۻ���Ϣ</xsl:when>
	<xsl:when test=".=2">��ȡ�ֽ�</xsl:when>
	<xsl:when test=".=3">�ֽ�����</xsl:when>
	<xsl:when test=".=5">�����</xsl:when>
	<xsl:when test=".=''">�ۻ���Ϣ  </xsl:when> 
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- �ɷ�Ƶ��2 -->
<xsl:template  match="PayIntv">
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
<!-- �Ա� -->
<xsl:template name="tran_sex" match="Sex">
<xsl:choose>      
	<xsl:when test=".=0">��</xsl:when>	<!-- �� -->
	<xsl:when test=".=1">Ů</xsl:when>	<!-- Ů -->
	<xsl:when test=".=2">����</xsl:when>	<!-- ���� -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template> 
</xsl:stylesheet>
