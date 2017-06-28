<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
	<xsl:output indent="yes"/>
<xsl:template match="TranData">
<InsuReq>
  <Main>
    <TranDate><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Date()"/></TranDate>
    <TranTime><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur6Time()"/></TranTime>
    <InsuId></InsuId>
    <ZoneNo></ZoneNo>
    <BrNo></BrNo>
    <TellerNo></TellerNo>
    <TransNo></TransNo>
    <TranCode></TranCode>
    <xsl:if test="Head/Flag='0'">
    <ResultCode>0000</ResultCode>
    </xsl:if>
    <xsl:if test="Head/Flag='1'">
    <ResultCode>0001</ResultCode>
    </xsl:if>
    <ResultInfo><xsl:value-of select="Head/Desc"/></ResultInfo>
  </Main>
  <xsl:if test="Head/Flag='0'">
  <Policy>
    <ApplyNo>
      <xsl:value-of select="substring(Body/ProposalPrtNo,1,13)"/>
    </ApplyNo>
    <PolicyNo><xsl:value-of select="Body/ContNo"/></PolicyNo>
    <PrintNo>
      <xsl:value-of select="substring(Body/ContPrtNo,1,13)"/>
    </PrintNo>
    <ApplyDate><xsl:value-of select="Body/Risk/PolApplyDate"/></ApplyDate>
    <TotalPrem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Prem)"/></TotalPrem>
    <InsuAmount><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Amnt)"/></InsuAmount>
    <xsl:if test="Body/Risk/InsuYearFlag='1000Y'">
      <PayEndDate>99999999</PayEndDate>
    </xsl:if>
    <xsl:if test="Body/Risk/InsuYearFlag!='1000Y'">
      <PayEndDate><xsl:value-of select="Body/Risk/InsuEndDate"/></PayEndDate>
    </xsl:if>
    <PolEffDate><xsl:value-of select="Body/Risk/CValiDate"/></PolEffDate>
    <PolEndDate><xsl:value-of select="Body/Risk/InsuEndDate"/></PolEndDate>
  </Policy>  
  <Print>
    <PaperTypeCount>1</PaperTypeCount>
    <Paper>
      <PaperType>1</PaperType>
      <PaperTitle></PaperTitle>
      <PageCount>3</PageCount>
      <PageContent>
        <RowCount></RowCount>
        <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
        <xsl:variable name="MainRisk" select="/TranData/Body/Risk[RiskCode=MainRiskCode]" />
        <Details>
          <Row><xsl:text>��</xsl:text></Row>
          <Row><xsl:text>��</xsl:text></Row>
          <Row><xsl:text>��</xsl:text></Row>
          <Row><xsl:text>��</xsl:text></Row>
          <Row><xsl:text>��</xsl:text></Row>
          <Row><xsl:text>��</xsl:text></Row>
          <Row><xsl:text>��</xsl:text>���պ�ͬ�ţ�<xsl:value-of select="/TranData/Body/ContNo"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 48)"/>���ҵ�λ�������/Ԫ </Row>
          <Row><xsl:text>��</xsl:text>------------------------------------------------------------------------------------------------</Row>
          <Row></Row>
          <Row><xsl:text>��</xsl:text>���պ�ͬ�����գ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 41)"/>���պ�ͬ��Ч���ڣ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(/TranData/Body/Risk/CValiDate)" /> </Row>
          <Row><xsl:text>��</xsl:text>------------------------------------------------------------------------------------------------</Row>
          <Row><xsl:text>��</xsl:text><xsl:text>Ͷ����������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Appnt/Name, 18)"/>
												<xsl:text>�Ա�</xsl:text>
												<xsl:call-template name="tran_Sex">
			                                        <xsl:with-param name="Sex">
				                                          <xsl:value-of select="/TranData/Body/Appnt/Sex"/>
			                                        </xsl:with-param>
		                                        </xsl:call-template>
												<xsl:text>   </xsl:text>
												<xsl:text>    ���䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(string(java:com.sinosoft.midplat.common.DateUtil.getAge(/TranData/Body/Appnt/Birthday)),2)"/><xsl:text>            </xsl:text>  
												<xsl:text>֤�����룺</xsl:text><xsl:value-of select="/TranData/Body/Appnt/IDNo"/>
          </Row>
          <Row><xsl:text>��</xsl:text><xsl:text>������������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Insured/Name, 18)"/>
												<xsl:text>�Ա�</xsl:text>
												<xsl:call-template name="tran_Sex">
			                                        <xsl:with-param name="Sex">
				                                          <xsl:value-of select="/TranData/Body/Appnt/Sex"/>
			                                        </xsl:with-param>
		                                        </xsl:call-template>
												<xsl:text>   </xsl:text>
												<xsl:text>    ���䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(string(java:com.sinosoft.midplat.common.DateUtil.getAge(/TranData/Body/Insured/Birthday)),2)"/><xsl:text>            </xsl:text>
												<xsl:text>֤�����룺</xsl:text><xsl:value-of select="/TranData/Body/Insured/IDNo"/>
		  </Row>
		  <xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" />  
		  <xsl:variable name="sflag" select="java:java.lang.Boolean.parseBoolean('true')" />    
		  <xsl:variable name="num" select="count(/TranData/Body/Bnf) " />
		  <xsl:for-each select="/TranData/Body/Bnf">
		  	<xsl:if test="Type='0'"><!-- ���������� -->
			  	<Row><xsl:text>��</xsl:text><xsl:text></xsl:text>��������������: <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 14)"/>
			  	<xsl:text>�Ա�:</xsl:text><xsl:text> </xsl:text>
			  	<xsl:call-template name="tran_Sex">
				  	<xsl:with-param name="Sex">
						<xsl:value-of select="/TranData/Body/Appnt/Sex"/>
				  	</xsl:with-param>
			  	</xsl:call-template><xsl:text>       </xsl:text>
			  	<xsl:text>����˳��: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 10)"/>
			  	<xsl:text>�������:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text>%</xsl:text>
			  	</Row>
			  </xsl:if>
			  <xsl:if test="Type='1'"><!-- ��������� -->
		  		  <Row><xsl:text>��</xsl:text><xsl:text></xsl:text>�������������: <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 14)"/>
			  	  <xsl:text>�Ա�:</xsl:text><xsl:text> </xsl:text>
			  	  <xsl:call-template name="tran_Sex">
				  	<xsl:with-param name="Sex">
						<xsl:value-of select="/TranData/Body/Appnt/Sex"/>
				  	</xsl:with-param>
			  	  </xsl:call-template><xsl:text>       </xsl:text>
			  	  <xsl:text>����˳��: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 10)"/>
			  	  <xsl:text>�������:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text>%</xsl:text>
			  	</Row>
			  </xsl:if>
		  </xsl:for-each>
          <xsl:choose>
			<xsl:when test="$num = 0"><Row><xsl:text>��</xsl:text><xsl:text></xsl:text>����������:<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ����', 13)"/>																
			<xsl:text>�Ա�:</xsl:text><xsl:text> </xsl:text>--<xsl:text>       </xsl:text>
			<xsl:text>����˳��: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 10)"/>	
			<xsl:text>�������:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 3, $Falseflag)"/>
			</Row></xsl:when>
			</xsl:choose>	
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row><xsl:text>��</xsl:text>------------------------------------------------------------------------------------------------</Row>
          <xsl:variable name="MainRiskCode" select="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode" />
          <xsl:choose>
			<!-- ������ʢ����������� -->
			<xsl:when test="$MainRiskCode='012E0100'">
				<Prnt><xsl:text>��</xsl:text>��������                          �������ս��    ����    �����ڼ�  �����ڼ�   ÿ�ڱ��շ�</Prnt>
				<xsl:for-each select="/TranData/Body/Risk">
				<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
				<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
				<xsl:variable name="Mult" select="Mult"/>
				<Prnt>
				<!-- �������� -->
				<xsl:text>������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 35)"/>
				<!-- ���� -->
				<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Amnt,10,$Falseflag)"/><xsl:text></xsl:text>
				<xsl:choose>
					<xsl:when test="string-length($Mult)=2">
						<!-- ����-->
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Mult,8,$Falseflag)"/><xsl:text></xsl:text>
						<!-- �����ڼ� -->
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('����', 11,$Falseflag)"/>
					</xsl:when>
					<xsl:otherwise>
						<!-- ����-->
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Mult,9,$Falseflag)"/><xsl:text></xsl:text>
						<!-- �����ڼ� -->
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('����', 10,$Falseflag)"/>
					</xsl:otherwise>
				</xsl:choose>
				<xsl:text>     </xsl:text>
				<!--�����ڼ�  -->
				<xsl:choose>
					<xsl:when test="PayIntv = 0">
						<xsl:text> ����     </xsl:text>
					</xsl:when>
					<xsl:when test="PayEndYearFlag = 'Y'">
						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 3,$Falseflag)"/><xsl:text>��      </xsl:text>
					</xsl:when>
				</xsl:choose>
				<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,8,$Falseflag)"/></Prnt>
				</xsl:for-each>
			</xsl:when>
			<xsl:otherwise>
	          	<Row><xsl:text>��</xsl:text>��������                          �����ڼ�    ��������    ���ѷ�ʽ  ������������/����   ���շ�</Row>
		        <xsl:for-each select="/TranData/Body/Risk">
		        <xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
				<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
				<Row>
				<xsl:text>��</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 36)"/>
				<xsl:choose>
				 <xsl:when test="InsuYearFlag = 'A'"><xsl:text>��</xsl:text>
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
				 <xsl:call-template name="tran_PayIntv">
				    <xsl:with-param name="PayIntv">
						<xsl:value-of select="PayIntv"/>
					</xsl:with-param>
				 </xsl:call-template>
				 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Amnt,11,$Falseflag)"/><xsl:text>Ԫ</xsl:text>
				 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,13,$Falseflag)"/>Ԫ
				 </Row>
		         </xsl:for-each>
	         </xsl:otherwise>
          </xsl:choose>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row><xsl:text>��</xsl:text>���շѺϼƣ�<xsl:value-of select="/TranData/Body/PremText"/>��RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Prem)"/>Ԫ��</Row>
          <Row><xsl:text>��</xsl:text>------------------------------------------------------------------------------------------------</Row>
          <xsl:choose>
			<xsl:when test="Risk[RiskCode=MainRiskCode]/RiskCode != '221201'">
			<Row><xsl:text>��</xsl:text>������ȡ��ʽ��<xsl:apply-templates select="/TranData/Body/Risk[RiskCode=MainRiskCode]/BonusGetMode" /></Row>
			</xsl:when>
			<xsl:otherwise>
			<Row><xsl:text>��</xsl:text>�������հף�</Row>
			<Row/>
			</xsl:otherwise>
		  </xsl:choose>
		  <xsl:choose>
		   <xsl:when test="$num=1 or $num=0"><Row/><Row /><Row /><Row /></xsl:when><!-- �����˸���Ϊ0���߸���Ϊ1�������һ���ģ������˵���Ϣ��ռһ�� -->
		   <xsl:when test="$num=2"><Row /><Row /><Row /></xsl:when>
		   <xsl:when test="$num=3"><Row /><Row /></xsl:when>
		   <xsl:when test="$num=4"><Row /></xsl:when>
		   <xsl:otherwise></xsl:otherwise>
		   </xsl:choose>
		  <Row><xsl:text>��</xsl:text>------------------------------------------------------------------------------------------------</Row>
		  <Row>
		  <xsl:text>��</xsl:text><xsl:text>�ر�Լ����</xsl:text>
		    <xsl:choose>
		     <xsl:when test="$MainRisk/SpecContent = ''">
			  <xsl:text>���ޣ�</xsl:text>
		     </xsl:when>
		     <xsl:otherwise> 
		      <xsl:value-of select="$MainRisk/SpecContent"/>
		     </xsl:otherwise>
		    </xsl:choose>
		  </Row>
		  <Row></Row>
		  <Row></Row>
		  <Row></Row>
		  <Row></Row>
		  <Row></Row>
		  <Row></Row>
		  <Row></Row>
		  <Row><xsl:text>��</xsl:text>------------------------------------------------------------------------------------------------</Row>
          <Row><xsl:text>��</xsl:text>�����������ƣ�<xsl:value-of select="/TranData/Body/AgentComName"/></Row>
          <Row><xsl:text>��</xsl:text>����������Ա����/���룺<xsl:value-of select="/TranData/Body/SaleName"/>/<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/SaleStaff,44)"/>��ӡʱ�䣺<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:text> </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/></Row>
          <Row><xsl:text>��</xsl:text>��������������<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/AgentName, 54)"/>��������绰��<xsl:value-of select="/TranData/Body/AgentPhone"/></Row>
          <Row><xsl:text>��</xsl:text>ǩ��������<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/ComName, 54)"/></Row>
          <Row><xsl:text>��</xsl:text>������ַ��<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/ComLocation, 54)"/></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row><xsl:text>��</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 76,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11($MainRisk/SignDate)"/></Row>
          <Row></Row>
        </Details>
      </PageContent>
      <PageContent>
      <RowCount></RowCount>
      <xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(/TranData/Body/Amnt)" />
	  <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('false')" />
	  <xsl:variable name="MainRisk"  select="/TranData/Body/Risk[RiskCode=MainRiskCode]" />
        <Details>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row><xsl:text>������</xsl:text>                                     �ֽ��ֵ��                     </Row>
          <Row></Row>
          <Row><xsl:text>������</xsl:text>���պ�ͬ�ţ�<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/ContNo, 50)"/><xsl:text></xsl:text>�������ս� <xsl:value-of select="$Amnt"/></Row>
          <Row><xsl:text>������</xsl:text>�������ƣ�<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($MainRisk/RiskName, 52)"/>���ҵ�λ�������/Ԫ</Row>       
          <Row><xsl:text>������</xsl:text>------------------------------------------------------------------------------------------------</Row>
          <Row><xsl:text>������������</xsl:text>  �������ĩ  �ֽ��ֵ  | �������ĩ  �ֽ��ֵ  | �������ĩ  �ֽ��ֵ</Row>
          <Row><xsl:text>������</xsl:text>------------------------------------------------------------------------------------------------</Row>
          <xsl:text></xsl:text>           <xsl:apply-templates select="/TranData/Body/Risk/CashValues"/>
          <Row><xsl:text>������</xsl:text>------------------------------------------------------------------------------------------------</Row>
          <Row><xsl:text>������</xsl:text>*�ֽ��ֵ���и������ֽ��ֵΪ�ͻ��������ɱ�����������б��շѵ�����£����������ĩ����Ӧ����</Row>
          <Row><xsl:text>������</xsl:text>���ֵ�Ͷ���������ĸ���������ʹ���������á�</Row>
          <Row><xsl:text>������</xsl:text>*���ڱ��ֽ��ֵ����δ�г��ı������ĩ�ֽ��ֵ��������������м�����һ��ı���ͬ���ֽ��ֵ������</Row>
          <Row><xsl:text>������</xsl:text>��˾���紹ѯ��</Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>
          <Row></Row>       
        </Details>
      </PageContent>
      <PageContent>
		<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
		<RowCount></RowCount>
		<Details>
		  <Row/>
		  <Row/>
		  <Row/>
		  <Row/>
		  <Row/>
		  <Row><xsl:text>     </xsl:text>                                        ���պ�ͬ�ʹ��ִ</Row>
		  <Row/>
		  <Row/>
		  <Row><xsl:text>     </xsl:text><xsl:text>���պ�ͬ��: </xsl:text><xsl:value-of select="Body/ContNo"/></Row>
		  <Row><xsl:text>     </xsl:text><xsl:text>Ͷ����: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Body/Appnt/Name, 19)"/><xsl:text>��������������      </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(TranData/Body/AgentName, 18)"/><xsl:text>����������룺</xsl:text><xsl:value-of select="TranData/Body/AgentCode"/></Row>
		  <Row><xsl:text>     </xsl:text><xsl:text>    ��Ͷ�������յ���˾�ı��պ�ͬ�����պ�ͬ��: </xsl:text><xsl:value-of select="Body/ContNo"/><xsl:text>���������պ�ͬ�������յ�����</xsl:text></Row>
		  <Row><xsl:text>     </xsl:text><xsl:text>���ֵ�����������������ϣ������ȷ�ϱ��պ�ͬ������ȷ���󡣱������Ķ�����Ʒ���Ͷ����ʾ</xsl:text></Row>
		  <Row><xsl:text>     </xsl:text><xsl:text>�飬ȷ�����˽Ⲣ�Ͽɱ��պ�ͬ��ȫ�����ݣ�֪�����˵�Ȩ��������</xsl:text></Row>
		  <Row><xsl:text>     </xsl:text><xsl:text>    Ͷ����ǩ����                                    ǩ�����ڣ�         ��     ��     ��</xsl:text></Row>
		  <Row><xsl:text>     </xsl:text><xsl:text>                                      �������ɹ�˾��Ա��д</xsl:text></Row>
		  <Row><xsl:text>     </xsl:text><xsl:text>------------------------------------------------------------------------------------------------</xsl:text></Row>
		  <Row><xsl:text>     </xsl:text><xsl:text>    ���պ�ͬ��Ϊ </xsl:text><xsl:value-of select="Body/ContNo"/><xsl:text>�ı��պ�ͬ���ʹ�ͻ����ɿͻ��ױ�ǩ��ȷ�ϣ��ֽ����պ�ͬ�ʹ�</xsl:text></Row>
		  <Row><xsl:text>     </xsl:text><xsl:text>��ִ���ء�</xsl:text></Row>
		  <Row/>
		  <Row><xsl:text>     </xsl:text><xsl:text>    ��������ǩ����	          ������ǩ�֣�           ���ڣ�      ��     ��     ��</xsl:text></Row>
	    </Details>	 
	</PageContent>
    </Paper> 
  </Print>                      
  </xsl:if>
</InsuReq>		
</xsl:template> 

<xsl:template name="Cashs" match="CashValues">
        <xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode != '221301'"> 
		<xsl:for-each select="CashValue[EndYear &lt; (26)]">
		<xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
				<Row><xsl:text>                </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+25)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+25)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+25)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+50)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+50)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+50)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></Row>
		</xsl:for-each>
		</xsl:if>
		<xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode = '221301'"> 
		    <xsl:for-each select="CashValue[EndYear &lt; (34)]">
		    <xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
		    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
				<Row><xsl:text>                        </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+33)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+33)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+33)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+66)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+66)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+66)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></Row>
		</xsl:for-each>
		</xsl:if>
</xsl:template>
<!--�Ա�ת��-->
<xsl:template name="tran_Sex">
  <xsl:param name="Sex"></xsl:param>
  <xsl:choose>
  <xsl:when test="$Sex = '0'">��</xsl:when><!-- ��-->
  <xsl:when test="$Sex = '1'">Ů</xsl:when><!-- Ů -->
  <xsl:otherwise>Ĭ��</xsl:otherwise>  
  </xsl:choose>
</xsl:template>
<!--�ɷѷ�ʽ-->
<xsl:template name="tran_PayIntv">
  <xsl:param name="PayIntv"></xsl:param>
  <xsl:choose>
  <xsl:when test="$PayIntv = '01'">����</xsl:when><!--���� -->
  <xsl:when test="$PayIntv = '02'">�꽻</xsl:when> <!--�꽻-->
  <xsl:when test="$PayIntv = '03'">���꽻</xsl:when><!--���꽻  -->
  <xsl:when test="$PayIntv = '04'">����</xsl:when><!--����  -->
  <xsl:when test="$PayIntv = '05'">�½�</xsl:when><!--�½�  -->
  <xsl:when test="$PayIntv = '06'">�����ڽ�</xsl:when><!--�����ڽ�  -->
  <xsl:otherwise></xsl:otherwise> 
  </xsl:choose>
</xsl:template>
</xsl:stylesheet>
