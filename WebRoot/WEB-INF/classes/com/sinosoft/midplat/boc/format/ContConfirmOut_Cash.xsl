<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" 
	exclude-result-prefixes="java">
	<xsl:output indent="yes"/>
	<xsl:template match="/">
		<InsuRet>
			<Policy>
			<!-- Ͷ������ -->
				<ApplyNo>
				    <xsl:value-of select="TranData/Body/ProposalPrtNo"/>
				</ApplyNo>
				<!-- ������ -->
				<PolicyNo>
				    <xsl:value-of select="TranData/Body/ContNo"/>
				</PolicyNo>
				<!-- ����ӡˢ�� -->
				<PrintNo></PrintNo>
				<!-- Ͷ������ -->
				<ApplyDate>
				    <xsl:value-of select="TranData/Body/Risk/PolApplyDate"/>
				</ApplyDate>
				<!-- �����ܱ��� -->
				<TotalPrem>
				    <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(TranData/Body/Prem)"/>
				</TotalPrem>
				<!-- ���ձ��� -->
				<InsuAmount>
				     <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(TranData/Body/Amnt)"/>
				</InsuAmount>
				<!-- �ɷ���ֹ���� -->
				<PayEndDate>
				     <xsl:value-of select="TranData/Body/Risk/PayEndDate"/>
				</PayEndDate>
				<!-- ������Ч���� -->
				<PolEffDate>
				     <xsl:value-of select="TranData/Body/Risk/CValiDate"/>
				</PolEffDate>
				<!-- ������ֹ���� -->
				<PolEndDate>
				     <xsl:value-of select="TranData/Body/Risk/SignDate"/>
				</PolEndDate>
			</Policy>
			<Print>
				<PaperTypeCount>1</PaperTypeCount><!-- ƾ֤���͸��� -->
				<Paper>
					<PaperType>1</PaperType><!-- ƾ֤���� -->
					<PaperTitle>�������ٱ��յ�</PaperTitle><!-- ��ӡƾ֤˵�� -->
					<PageCount></PageCount><!-- ƾ֤ҳ�� -->
					<PageContent><!-- #####ÿҳƾ֤�Ĵ�ӡ��Ϣ###### -->
						<RowCount></RowCount><!-- ƾ֤ÿҳ���� -->
						<Details><!-- #####������ӡ������ѭ��##### -->
						<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
						<xsl:variable name="MainRisk" select="/TranData/Body/Risk[RiskCode=MainRiskCode]" />
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>                                                       �� �� ��</Row>
							<Row>             </Row>
							<Row>                                                                                           ��ֵ��λ�������Ԫ</Row>
							<Row>             -----------------------------------------------------------------------------------------------</Row>
							<Row>             ������ͬ���루�����ţ���<xsl:value-of select="TranData/Body/ContNo"/></Row>
							<Row>             ���պ�ͬ��Ч���ڣ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(/TranData/Body/Risk/CValiDate)" /><xsl:text>��ʱ                                   </xsl:text>���ѷ�ʽ��<xsl:apply-templates  select="$MainRisk/PayIntv" /></Row>
							<Row>             -----------------------------------------------------------------------------------------------</Row>
							<Row>             Ͷ���ˣ�<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Appnt/Name,22)"/>�Ա�<xsl:apply-templates select="TranData/Body/Appnt/Sex"/>    ���գ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(/TranData/Body/Appnt/Birthday)"/>       ֤�����룺<xsl:value-of select="TranData/Body/Appnt/IDNo"/></Row>
							<Row>             �������ˣ�<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/Insured/Name,20)"/>�Ա�<xsl:apply-templates select="TranData/Body/Insured/Sex"/>    ���գ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(/TranData/Body/Insured/Birthday)"/>       ֤�����룺<xsl:value-of select="TranData/Body/Insured/IDNo"/></Row>
							<Row>             -----------------------------------------------------------------------------------------------</Row>
							<Row>             </Row>
							<xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" />  
							<xsl:if test="count(/TranData/Body/Bnf) = 0">
							<Row>             ��������ˣ�δָ��</Row>
							</xsl:if>
							<xsl:choose>
								<xsl:when test="/TranData/Body/Bnf/Name = 'δָ��' or /TranData/Body/Bnf/Name = '����'">
									<Row>             ��������ˣ�δָ��</Row>
								</xsl:when>
								<xsl:when test="/TranData/Body/Bnf/Name != 'δָ��'">
									<xsl:for-each select="/TranData/Body/Bnf">
										<Row>             ��������ˣ�<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 20)"/>             ���������<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $flag)"/>%             ����˳��<xsl:value-of select="Grade"/></Row>
									</xsl:for-each>
								</xsl:when>   
							</xsl:choose>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             -----------------------------------------------------------------------------------------------</Row>
							<Row>             ��������                                ������ȡ��ʽ  �����ڼ�  ��������  ����/����    ���շ�</Row>
							<Row>             -----------------------------------------------------------------------------------------------</Row>
							<xsl:for-each select="/TranData/Body/Risk">
							<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
							<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
							<Row><xsl:text>             </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 42)"/>
															<xsl:apply-templates select="$MainRisk/BonusGetMode" />
																					<xsl:choose>
																							<xsl:when test="(InsuYear= 105) and (InsuYearFlag = 'A')">
																								<xsl:text>    ����</xsl:text>
																							</xsl:when>
																							<xsl:when test="InsuYearFlag = 'Y'">
																								<xsl:text>     </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>��  </xsl:text>
																							</xsl:when>  
																							<xsl:otherwise> 
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 2,$Falseflag)"/><xsl:text>����</xsl:text>
																							</xsl:otherwise>
																					</xsl:choose>
																			<xsl:text>      </xsl:text>
																				<xsl:choose>
																							<xsl:when test="PayIntv = 0">
																								<xsl:text>����</xsl:text>
																							</xsl:when>
																							<xsl:when test="PayEndYearFlag = 'Y'">
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>��  </xsl:text>
																							</xsl:when>  
																							<xsl:otherwise> 
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 2,$Falseflag)"/><xsl:text>����</xsl:text>
																							</xsl:otherwise>
																					</xsl:choose>
																				<xsl:choose>
																							<xsl:when test="RiskCode = 321010 or RiskCode = 321020 or RiskCode = '321170'">
																								<xsl:text>       --  </xsl:text>
																							</xsl:when>
																							<xsl:otherwise>
																								<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Amnt,11,$Falseflag)"/><xsl:text>Ԫ</xsl:text>
																							</xsl:otherwise>
																				 </xsl:choose>
																			 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,13,$Falseflag)"/>Ԫ</Row>
							</xsl:for-each>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             ���ڱ��շѺϼƣ�<xsl:value-of select="/TranData/Body/PremText"/>��RMB<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Prem)"/>Ԫ��</Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             ---------------------------------------����������Ϊ�հף�----------------------------------------</Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             -----------------------------------------------------------------------------------------------</Row>
							<Row>             �ر�Լ����</Row>
							<xsl:choose>
								<xsl:when test="$MainRisk/SpecContent = ''">
									<Row><xsl:text>             ��ԥ����ʾ�"�����յ����պ�ͬ��15����Ȼ������ȫ���˱����۳�������10Ԫ�Ĺ����ѣ���Ȩ����</xsl:text></Row>
								    <Row><xsl:text>             ����15����Ȼ���˱�����ʧ��" </xsl:text></Row>
								    </xsl:when> 
								<xsl:otherwise> 
									<Row><xsl:value-of select="$MainRisk/SpecContent"/></Row>
								</xsl:otherwise>
							</xsl:choose>
							<Row>             </Row>
							<Row>             -----------------------------------------------------------------------------------------------</Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             -----------------------------------------------------------------------------------------------</Row>
							<Row><xsl:text>             </xsl:text><xsl:value-of select="/TranData/Body/ComName"/></Row>
							<Row><xsl:text>             </xsl:text>���������ַ��<xsl:value-of select="/TranData/Body/ComLocation"/></Row>
							<Row><xsl:text>             </xsl:text>�������룺<xsl:value-of select="/TranData/Body/ComZipCode"/></Row>
							<Row><xsl:text>             </xsl:text>��˾��վ��www.zhongronglife.com</Row>
							<Row><xsl:text>             </xsl:text>�ͻ�����绰��4008186636</Row>
							<Row><xsl:text>             </xsl:text>Ϊȷ�����ı���Ȩ�棬�뼰ʱ���򱾹�˾����绰����½��վ�򵽹�̨</Row>
							<Row><xsl:text>             </xsl:text>���в�ѯ����ʵ������Ϣ��</Row>
							<Row></Row>
							<Row><xsl:text>             </xsl:text>�������ƣ�<xsl:value-of select="/TranData/Body/AgentComName"/></Row>
							<Row><xsl:text>             </xsl:text>������Ա���ƣ�<xsl:value-of select="/TranData/Body/AgentPersonName"/></Row>
						</Details>
					</PageContent>
					<xsl:if test="/TranData/Body/Risk/CashValues/CashValue != ''">
					<PageContent>
						<RowCount></RowCount>
						<Details>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>                                               �ֽ��ֵ��</Row>
							<Row><xsl:text>             </xsl:text>������ͬ���룺<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/ContNo, 16)"/><xsl:text>                                  </xsl:text>��ֵ��λ�������Ԫ </Row>
							<Row><xsl:text>             </xsl:text>�������ĩ<xsl:text>                                  </xsl:text><xsl:value-of select="/TranData/Body/Risk/RiskName"/></Row>
							<Row>             -----------------------------------------------------------------------------------------------</Row>
							<xsl:for-each select="/TranData/Body/Risk/CashValues/CashValue">  
					       	<Row><xsl:text>             </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,50)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash)"/>Ԫ</Row> 
					        </xsl:for-each>
							<Row><xsl:text>             </xsl:text>---------------------------------------����������Ϊ�հף�---------------------------------------</Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             -----------------------------------------------------------------------------------------------------</Row>
							<Row><xsl:text>             </xsl:text> �ֽ��ֵ���������б������ĩ�ֽ��ֵΪ���պ�ͬ��ÿһ����������һ����ֽ��ֵ���Ǳ���˾�����й����ռ�</Row>
							<Row><xsl:text>             </xsl:text> ������ίԱ����йع涨����ȷ���ģ�����ʱ����ֽ��ֵ���ڸ�ʱ�����ڱ�����ȶ�Ӧ�����ĩ�ֽ��ֵ����������</Row>
							<Row><xsl:text>             </xsl:text> ���㡣</Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
							<Row>             </Row>
						</Details>
					</PageContent>
					</xsl:if>									     
				</Paper>
			</Print>  	
		</InsuRet>
	</xsl:template>
	<xsl:template name="tran_RiskCode">
		<xsl:param name="RiskCode">0</xsl:param>
		<xsl:if test="$RiskCode = 321010">0002</xsl:if>
		<xsl:if test="$RiskCode = 313030">0001</xsl:if>
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
<!-- �ɷ�Ƶ��2 -->
<xsl:template  match="PayIntv">
<xsl:choose>
	<xsl:when test=".=12">���</xsl:when>	<!-- ��� -->
	<xsl:when test=".=1">�½�</xsl:when>	<!-- �½� -->
	<xsl:when test=".=6">�����</xsl:when>	<!-- ����� -->
	<xsl:when test=".=3">����</xsl:when>	<!-- ���� -->
	<xsl:when test=".=0">����</xsl:when>	<!-- ���� -->
	<xsl:when test=".=-1">�����ڽ�</xsl:when>	<!-- ������ -->
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
