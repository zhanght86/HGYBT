<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:template match="TranData">
	<RETURN>
		<ACKSTS>0</ACKSTS>
		<STSDESC>����</STSDESC>
		<BUSI>
			<SUBJECT>1</SUBJECT>
			<TRANS><xsl:value-of select ="Head/TranNo"/></TRANS>
			<!-- ���д�ӡ -->
			<xsl:variable name ="print">
			&lt;![CDATA[
			��
			��
			��
			��
			��
			��
			��
			��
			��
			��
			<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
			<xsl:variable name="MainRisk" select="Body/Risk[RiskCode=MainRiskCode]" />
			<!-- ���յ����ݿ�ʼ -->
			
			��<xsl:text>��������</xsl:text>���պ�ͬ�ţ�<xsl:value-of select="Body/ContNo"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 48)"/>���ҵ�λ�������/Ԫ 
			��<xsl:text>��������</xsl:text>------------------------------------------------------------------------------------------------
			��
			��<xsl:text>��������</xsl:text>���պ�ͬ�����գ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 41)"/>���պ�ͬ��Ч���ڣ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(Body/Risk/CValiDate)" /> 
			��<xsl:text>��������</xsl:text>------------------------------------------------------------------------------------------------      
			��<xsl:text>��������</xsl:text><xsl:text>Ͷ����������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Body/Appnt/Name, 12)"/>
																							<xsl:text>�Ա�</xsl:text><xsl:apply-templates select="Body/Appnt/Sex"/><xsl:text>   </xsl:text>
																							<xsl:text>    ���䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(string(java:com.sinosoft.midplat.common.DateUtil.getAge(Body/Appnt/Birthday)),2)"/><xsl:text>            </xsl:text>  
																							<xsl:text>֤�����룺</xsl:text><xsl:value-of select="Body/Appnt/IDNo"/>
			
			��<xsl:text>��������</xsl:text><xsl:text>������������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Body/Insured/Name, 12)"/>
																							<xsl:text>�Ա�</xsl:text><xsl:apply-templates select="Body/Insured/Sex"/><xsl:text>   </xsl:text>
																							<xsl:text>    ���䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(string(java:com.sinosoft.midplat.common.DateUtil.getAge(Body/Insured/Birthday)),2)"/><xsl:text>            </xsl:text>
																							<xsl:text>֤�����룺</xsl:text><xsl:value-of select="Body/Insured/IDNo"/>
			
			<xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" />  
			<xsl:variable name="sflag" select="java:java.lang.Boolean.parseBoolean('true')" />    
			<xsl:variable name="num" select="count(Body/Bnf) " />
			<xsl:for-each select="Body/Bnf">
			��<xsl:text>��������</xsl:text><xsl:text></xsl:text>����������: <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 12)"/>																
			<xsl:text>�Ա�:</xsl:text><xsl:text> </xsl:text><xsl:apply-templates select="Sex"/><xsl:text>       </xsl:text>
			 <xsl:text>����˳��: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 10)"/>	
			<xsl:text>�������:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text>%</xsl:text>
                 	
			</xsl:for-each>
			<xsl:choose>
			<xsl:when test="$num = 0">��<xsl:text>��������</xsl:text><xsl:text></xsl:text>����������:<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ����', 13)"/>																
			<xsl:text>�Ա�:</xsl:text><xsl:text> </xsl:text>--<xsl:text>       </xsl:text>
			 <xsl:text>����˳��: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 10)"/>	
			<xsl:text>�������:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 3, $Falseflag)"/>
                 	</xsl:when>
			</xsl:choose>		
			��
			��
			��
			��
			��
			��
			��<xsl:text>��������</xsl:text>------------------------------------------------------------------------------------------------      
			��<xsl:text>��������</xsl:text>��������                          �����ڼ�    ��������    ���ѷ�ʽ  ������������/����   ���շ�
			<xsl:for-each select="Body/Risk">
			<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
			<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
			<xsl:variable name="Mult" select="Mult"/>
			<!-- �������� -->
			��<xsl:text>��������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 36)"/>
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
																	<xsl:apply-templates select="PayIntv"/>
																						<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Mult,11,$Falseflag)"/><xsl:text>��</xsl:text>
																	 <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,13,$Falseflag)"/>Ԫ
			</xsl:for-each>
			��
			��
			��
			��
			��
			��
			��<xsl:text>��������</xsl:text>���շѺϼƣ�<xsl:value-of select="Body/PremText"/>��RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Prem)"/>Ԫ��
			��<xsl:text>��������</xsl:text>------------------------------------------------------------------------------------------------
			<xsl:choose><!-- ���ݻ�����Ʒ��221201�� �˴���ӡ���ǣ������հף������Ǻ�����ȡ��ʽ -->
			<xsl:when test="Risk[RiskCode=MainRiskCode]/RiskCode != '221201'">
			��<xsl:text>��������</xsl:text>������ȡ��ʽ��<xsl:apply-templates select="Body/Risk[RiskCode=MainRiskCode]/BonusGetMode" />
			</xsl:when>
			<xsl:otherwise>
			��<xsl:text>��������</xsl:text>�������հף�
			��
			</xsl:otherwise>
			</xsl:choose>
			<xsl:choose>
				<xsl:when test="$num=1 or $num=0">��������</xsl:when><!-- �����˸���Ϊ0���߸���Ϊ1�������һ���ģ������˵���Ϣ��ռһ�� -->
				<xsl:when test="$num=2">������</xsl:when>
				<xsl:when test="$num=3">����</xsl:when>
				<xsl:when test="$num=4">��</xsl:when>
				<xsl:otherwise></xsl:otherwise>
			</xsl:choose>
			��<xsl:text>��������</xsl:text>------------------------------------------------------------------------------------------------
			��<xsl:text>��������</xsl:text><xsl:text>�ر�Լ����</xsl:text>
																			<xsl:choose>
																					<xsl:when test="$MainRisk/SpecContent = ''">
																						<xsl:text>���ޣ�</xsl:text>
																					</xsl:when>
																					<xsl:otherwise> 
																						<xsl:value-of select="$MainRisk/SpecContent"/>
																					</xsl:otherwise>
																			</xsl:choose>
			
			��
			��
			��
			��
			��
			��
			��
			��
			��
			��
			��
			��<xsl:text>��������</xsl:text>------------------------------------------------------------------------------------------------
			��<xsl:text>��������</xsl:text>�����������ƣ�<xsl:value-of select="Body/AgentComName"/>
			��<xsl:text>��������</xsl:text>����������Ա����/���룺<xsl:value-of select="Body/SaleName"/>/<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Body/SaleStaff,44)"/>��ӡʱ�䣺<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:text> </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/>
			��<xsl:text>��������</xsl:text>��������������<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Body/AgentName, 54)"/>��������绰��<xsl:value-of select="Body/AgentPhone"/>
			��<xsl:text>��������</xsl:text>ǩ��������<xsl:value-of select="Body/ComName"/>
			��<xsl:text>��������</xsl:text>������ַ��<xsl:value-of select="Body/ComLocation"/>
			��
			��
			��
			��
			��
			��
			��
			��
			��
			��
			��
			��<xsl:text>��������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 76,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11($MainRisk/SignDate)"/>
			�� 
			
		<xsl:if test="Body/Risk[RiskCode=MainRiskCode]/RiskCode != '011A0100'">
			<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Body/Amnt)"/>
			<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('false')" />
			<xsl:variable name="MainRisk" select="Body/Risk[RiskCode=MainRiskCode]"/>
			��
			��
			��
			��
			��
			��<xsl:text>��������</xsl:text>                                     �ֽ��ֵ��                     
			��
			��<xsl:text>��������</xsl:text>���պ�ͬ�ţ�<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Body/ContNo, 50)"/><xsl:text></xsl:text>�������ս� <xsl:value-of select="$Amnt"/>
			��<xsl:text>��������</xsl:text>�������ƣ�<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($MainRisk/RiskName, 52)"/>���ҵ�λ�������/Ԫ
			��<xsl:text>��������</xsl:text>------------------------------------------------------------------------------------------------
			��<xsl:text>��������������</xsl:text>  �������ĩ  �ֽ��ֵ  | �������ĩ  �ֽ��ֵ  | �������ĩ  �ֽ��ֵ
		  ��<xsl:text>��������</xsl:text>------------------------------------------------------------------------------------------------
			<xsl:text></xsl:text>           <xsl:apply-templates select="Body/Risk/CashValues"/>
			��<xsl:text>��������</xsl:text>------------------------------------------------------------------------------------------------
			��<xsl:text>��������</xsl:text>*�ֽ��ֵ���и������ֽ��ֵΪ�ͻ��������ɱ�����������б��շѵ�����£����������ĩ����Ӧ����
		  ��<xsl:text>��������</xsl:text>���ֵ�Ͷ���������ĸ���������ʹ���������á�
		  ��<xsl:text>��������</xsl:text>*���ڱ��ֽ��ֵ����δ�г��ı������ĩ�ֽ��ֵ��������������м�����һ��ı���ͬ���ֽ��ֵ������
		  ��<xsl:text>��������</xsl:text>��˾���紹ѯ��				    
			��
			��
			��
			��
			��
			��
			��
			��
			��
			��
			��
			��
			��
			��
			��
			��
				 
		</xsl:if>
		
		    <!-- <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" /> -->
			��
		  ��
		  ��
		  ��
		  ��
		  ��<xsl:text>��������</xsl:text>                                        ���պ�ͬ�ʹ��ִ
		  ��
		  ��
		  ��<xsl:text>��������</xsl:text><xsl:text>���պ�ͬ��: </xsl:text><xsl:value-of select="Body/ContNo"/>
		  ��<xsl:text>��������</xsl:text><xsl:text>Ͷ����: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Body/Appnt/Name, 19)"/><xsl:text>��������������      </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Body/AgentName, 18)"/><xsl:text>����������룺</xsl:text><xsl:value-of select="Body/AgentCode"/>
				   <xsl:choose><!-- ���ݻ�����Ʒ��221201�� û�в�Ʒ˵��˵���飬��ӮC�� -->
					<xsl:when test="Body/Risk[RiskCode=MainRiskCode]/RiskCode != '221201' and Body/Risk[RiskCode=MainRiskCode]/RiskCode!='221301'">
			��<xsl:text>��������</xsl:text><xsl:text>    ��Ͷ�������յ���˾�ı��պ�ͬ�����պ�ͬ��: </xsl:text><xsl:value-of select="Body/ContNo"/><xsl:text>���������պ�ͬ�������յ���</xsl:text>
			��<xsl:text>��������</xsl:text><xsl:text>���������������ϣ������ȷ�ϱ��պ�ͬ������ȷ���󡣱������Ķ�����Ʒ���Ͷ����ʾ</xsl:text>
			��<xsl:text>��������</xsl:text><xsl:text>��Ͳ�Ʒ˵���飬ȷ�����˽Ⲣ�Ͽɱ��պ�ͬ��ȫ�����ݣ�֪�����˵�Ȩ��������</xsl:text>
					</xsl:when>
					<xsl:otherwise>
			��<xsl:text>��������</xsl:text><xsl:text>    ��Ͷ�������յ���˾�ı��պ�ͬ�����պ�ͬ��: </xsl:text><xsl:value-of select="Body/ContNo"/><xsl:text>���������պ�ͬ�������յ�����</xsl:text>
			��<xsl:text>��������</xsl:text><xsl:text>���ֵ�����������������ϣ������ȷ�ϱ��պ�ͬ������ȷ���󡣱������Ķ�����Ʒ���Ͷ����ʾ</xsl:text>
			��<xsl:text>��������</xsl:text><xsl:text>�飬ȷ�����˽Ⲣ�Ͽɱ��պ�ͬ��ȫ�����ݣ�֪�����˵�Ȩ��������</xsl:text>
					</xsl:otherwise>
					</xsl:choose>
			��<xsl:text>��������</xsl:text><xsl:text></xsl:text>
			��<xsl:text>��������</xsl:text><xsl:text>    Ͷ����ǩ����                                    ǩ�����ڣ�         ��     ��     ��</xsl:text>
			��<xsl:text>��������</xsl:text><xsl:text>                                      �������ɹ�˾��Ա��д</xsl:text>
			��<xsl:text>��������</xsl:text><xsl:text>------------------------------------------------------------------------------------------------</xsl:text>
			��<xsl:text>��������</xsl:text><xsl:text>    ���պ�ͬ��Ϊ </xsl:text><xsl:value-of select="Body/ContNo"/><xsl:text>�ı��պ�ͬ���ʹ�ͻ����ɿͻ��ױ�ǩ��ȷ�ϣ��ֽ����պ�ͬ�ʹ�</xsl:text>
			��<xsl:text>��������</xsl:text><xsl:text>��ִ���ء�</xsl:text>
			��<xsl:text>��������</xsl:text><xsl:text>    ��������ǩ����	          ������ǩ�֣�           ���ڣ�      ��     ��     ��</xsl:text>
		   	
			<text>��]]&gt;</text>
			</xsl:variable>
			<!-- ���д�ӡ���� -->
			<PRINT>
				<xsl:value-of disable-output-escaping="yes" select="string($print)" />
			</PRINT>
		</BUSI>
	</RETURN>
</xsl:template>

<!-- �Ա� -->
<xsl:template name="tran_sex" match="Sex">
	<xsl:choose>
		<xsl:when test=".=0">��</xsl:when><!-- �� -->
		<xsl:when test=".=1">Ů</xsl:when><!-- Ů -->
		<xsl:when test=".=2">����</xsl:when><!-- ���� -->
		<xsl:otherwise>--</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- �ɷ�Ƶ��/���ѽ��ɷ�ʽ -->
<xsl:template  match="PayIntv">
<xsl:choose>
	<xsl:when test=".=12">�꽻     </xsl:when>	<!-- ��� -->
	<xsl:when test=".=1">�½�      </xsl:when>	<!-- �½� -->
	<xsl:when test=".=6">���꽻    </xsl:when>	<!-- ����� -->
	<xsl:when test=".=3">����      </xsl:when>	<!-- ���� -->
	<xsl:when test=".=0">����     </xsl:when>	<!-- ���� -->
	<xsl:when test=".=-1">�����ڽ�  </xsl:when>	<!-- ������ -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- ѭ��ȡ�ֽ��ֵ��Ϣ -->
<xsl:template name="Cashs" match="CashValues">
        <xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode != '221301'"> 
			<xsl:for-each select="CashValue[EndYear &lt; (26)]">
				<xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
				<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
				<text>��<xsl:text>                  </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+25)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+25)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+25)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+50)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+50)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+50)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></text>
			</xsl:for-each>
		</xsl:if>
		<xsl:if test="/TranData/Body/Risk[RiskCode=MainRiskCode]/RiskCode = '221301'"> 
		    <xsl:for-each select="CashValue[EndYear &lt; (34)]">
			    <xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
			    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />						
				<text>��<xsl:text>                          </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),13,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',13,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+33)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+33)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+33)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+66)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+66)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+66)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose></text>
			</xsl:for-each>
		</xsl:if>
</xsl:template>

</xsl:stylesheet>