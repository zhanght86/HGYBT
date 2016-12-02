<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:template match="TranData">
	<RETURN>
	<ACKSTS>0</ACKSTS>
	<STSDESC>����</STSDESC>
		<BUSI>
		<SUBJECT>2</SUBJECT>  
		<TRANS><xsl:value-of select="Head/TranNo" /></TRANS>
		<xsl:if test="Head/Flag='0'">
			<xsl:apply-templates select="Body" />
		</xsl:if>
		<xsl:if test="Head/Flag!='0'">
			<CONTENT>   
				<REJECT_CODE>0001</REJECT_CODE>   
				<REJECT_DESC><xsl:value-of select="Head/Desc" /></REJECT_DESC>  
			</CONTENT>
		</xsl:if>
		</BUSI>	       
	</RETURN >
</xsl:template>
	    
	
	


<xsl:template name="RETURN" match="TranData/Body">
<xsl:variable name="MainRisk" select="Risk[RiskCode=MainRiskCode]" />
<xsl:variable name="RiderRisk" select="Risk[RiskCode!=MainRiskCode]" />
<xsl:variable name="flag" select="java:java.lang.Boolean.parseBoolean('false')" />  
<xsl:variable name="sflag" select="java:java.lang.Boolean.parseBoolean('true')" />    
<xsl:variable name="num" select="count(/TranData/Body/Bnf) " />
<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
<xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('false')" />
	
	    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')"/>
	    <xsl:variable name ="print">
	    <xsl:text>&lt;![CDATA[����������������������</xsl:text>
		<xsl:text>  </xsl:text>  ���պ�ͬ��:<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(ContNo, 56)"/><xsl:text>���ҵ�λ�������/Ԫ</xsl:text>
		<xsl:text>��  </xsl:text><xsl:text>  ------------------------------------------------------------------------------------------------</xsl:text> 
		<xsl:text>����  </xsl:text>  ���պ�ͬ�����գ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 37)"/>���պ�ͬ��Ч���ڣ�<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.date8to10(/TranData/Body/Risk/CValiDate)" />
	    <xsl:text>��  </xsl:text><xsl:text>  ------------------------------------------------------------------------------------------------</xsl:text>
		<xsl:text>��  </xsl:text>  Ͷ����������<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Appnt/Name, 18)"/>
																							<xsl:text>  �Ա�</xsl:text><xsl:apply-templates select="Appnt/Sex"/><xsl:text>   </xsl:text>
																							<xsl:text>  ���䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(Appnt/Birthday), 2)"/><xsl:text>����       </xsl:text>  
																							<xsl:text>  ֤�����룺</xsl:text><xsl:value-of select="Appnt/IDNo"/>
		<xsl:text>��  </xsl:text><xsl:text>  ������������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Insured/Name, 18)"/>
																							<xsl:text>  �Ա�</xsl:text><xsl:apply-templates select="Insured/Sex"/><xsl:text>   </xsl:text>
																							<xsl:text>  ���䣺</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.DateUtil.getAge(Insured/Birthday), 2)"/><xsl:text>����       </xsl:text>
																							<xsl:text>  ֤�����룺</xsl:text><xsl:value-of select="Insured/IDNo"/>
		
		<xsl:for-each select="Bnf">
		<xsl:text>��  </xsl:text><xsl:text>  ����������:</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Name, 19)"/><xsl:text>�Ա�:</xsl:text><xsl:text> </xsl:text><xsl:apply-templates select="Sex"/><xsl:text>   </xsl:text><xsl:text>����˳��: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Grade, 14)"/><xsl:text>�������:</xsl:text><xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(Lot, 3, $Falseflag)"/><xsl:text>%</xsl:text></xsl:for-each>
      <xsl:choose><xsl:when test="$num = 0">
		<xsl:text>��  </xsl:text>  ����������:<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ����', 18)"/>																
																							<xsl:text>  �Ա�:</xsl:text><xsl:text> </xsl:text>--<xsl:text></xsl:text>
																							<xsl:text>  ����˳��: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 10)"/>	
																							<xsl:text>  �������:</xsl:text><xsl:text>   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('--', 3, $Falseflag)"/>
		</xsl:when></xsl:choose>
		<xsl:text>��  </xsl:text><xsl:text>  ------------------------------------------------------------------------------------------------</xsl:text>
		<xsl:text>���� </xsl:text><xsl:text>  ��������               �����ڼ�    �����ڼ�   ���ѷ�ʽ        ������������/����     ���շ�</xsl:text>
	 	<xsl:for-each select="Risk">
		<xsl:variable name="Amnt" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/>
		<xsl:variable name="Prem" select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/>
		<xsl:text>��   </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(RiskName, 22)"/>

		<xsl:choose>
				<xsl:when test="/TranData/Body/Risk/RiskCode = '221301'">
					<xsl:choose>
						<xsl:when test="InsuYearFlag = 'A'">
							<xsl:text>  ��</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 4,$Falseflag)"/><xsl:text>����</xsl:text>
						</xsl:when>
						<xsl:when test="InsuYearFlag = 'Y'">
							<xsl:text>  ��</xsl:text><xsl:value-of select=" java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 4,$Falseflag)"/><xsl:text>��  </xsl:text>
						</xsl:when>  
						<xsl:when test="InsuYearFlag = 'M'">
							<xsl:text>  ��</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 4,$Falseflag)"/><xsl:text>��  </xsl:text>
						</xsl:when>
						<xsl:otherwise> 
							<xsl:text>  ��</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 4,$Falseflag)"/><xsl:text>��</xsl:text>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:when>
				<xsl:otherwise> 
					<xsl:choose>
						<xsl:when test="InsuYearFlag = 'A'">
							<xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 4,$Falseflag)"/><xsl:text>��</xsl:text>
						</xsl:when>
						<xsl:when test="InsuYearFlag = 'Y'">
							<xsl:text></xsl:text><xsl:value-of select=" java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 4,$Falseflag)"/><xsl:text>��  </xsl:text>
						</xsl:when>  
						<xsl:when test="InsuYearFlag = 'M'">
							<xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 4,$Falseflag)"/><xsl:text>��  </xsl:text>
						</xsl:when>
						<xsl:otherwise> 
							<xsl:text></xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(InsuYear, 4,$Falseflag)"/><xsl:text>��</xsl:text>
						</xsl:otherwise>
					</xsl:choose>
				</xsl:otherwise>
		</xsl:choose>
		
		<xsl:text></xsl:text>
		<xsl:choose>
			<xsl:when test="PayIntv = 0">
				<xsl:text>      ����        </xsl:text>
			</xsl:when>
			<xsl:when test="PayEndYearFlag = 'Y'">
				<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 6,$Falseflag)"/><xsl:text>��        </xsl:text>
			</xsl:when>
			<xsl:when test="PayEndYearFlag = 'M'">
				<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 6,$Falseflag)"/><xsl:text>��        </xsl:text>
			</xsl:when>
			<xsl:when test="PayEndYearFlag = 'D'">
				<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 6,$Falseflag)"/><xsl:text>��        </xsl:text>
			</xsl:when>  
			<xsl:otherwise> 
				<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(PayEndYear, 6,$Falseflag)"/><xsl:text>����    </xsl:text>
			</xsl:otherwise>
		</xsl:choose>
		<xsl:apply-templates select="PayIntv"/>
		<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Amnt,20,$Falseflag)"/><xsl:text>Ԫ</xsl:text>
 		<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($Prem,17,$Falseflag)"/>Ԫ</xsl:for-each>
		<xsl:text>��������  </xsl:text>  ���շѺϼƣ�<xsl:value-of select="PremText"/>��RMB <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/><xsl:text>Ԫ��</xsl:text>
		<xsl:text>��  </xsl:text><xsl:text>  ------------------------------------------------------------------------------------------------</xsl:text>
		<xsl:choose>
				<xsl:when test="/TranData/Body/Risk/RiskCode = '221301'">
					<xsl:text>��</xsl:text>    �����ȡ��ʼ���䣺<xsl:value-of select="/TranData/Body/Risk/GetYear"/>����            ��ȡ���ޣ�<xsl:value-of select="/TranData/Body/Risk/GetTerms" />��
					<xsl:text>����  </xsl:text>
							<xsl:text>��  </xsl:text><xsl:text>  ------------------------------------------------------------------------------------------------</xsl:text>
							<xsl:text>��  </xsl:text><xsl:text>  �ر�Լ����</xsl:text>
							<xsl:choose>
									<xsl:when test="$MainRisk/SpecContent = ''">
										<xsl:text>���ޣ�</xsl:text>
									</xsl:when>
									<xsl:otherwise> 
										<xsl:value-of select="$MainRisk/SpecContent"/>
									</xsl:otherwise>
							</xsl:choose>
				</xsl:when>
				<xsl:otherwise> 
							<xsl:text>��  </xsl:text><xsl:text>  �ر�Լ����</xsl:text>
							<xsl:choose>
									<xsl:when test="$MainRisk/SpecContent = ''">
										<xsl:text>���ޣ�</xsl:text>
									</xsl:when>
									<xsl:otherwise> 
										<xsl:value-of select="$MainRisk/SpecContent"/>
									</xsl:otherwise>
							</xsl:choose>
							<xsl:text>����������  </xsl:text>
				</xsl:otherwise>
		</xsl:choose>
		

		<xsl:text>����������������������  </xsl:text><xsl:text>  ------------------------------------------------------------------------------------------------</xsl:text>
		<xsl:text>��   </xsl:text>  �����������ƣ�<xsl:value-of select="/TranData/Body/AgentComName"/>
		<xsl:text>��   </xsl:text>  ����������Ա����/���룺<xsl:value-of select="/TranData/Body/SaleName"/>/<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/SaleStaff,34)"/>��ӡʱ�䣺<xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur10Date()"/><xsl:text> </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtil.getCur8Time()"/>
		<xsl:text>��   </xsl:text>  ��������������<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/AgentName, 50)"/>��������绰��<xsl:value-of select="/TranData/Body/AgentPhone"/>
		<xsl:text>����������������������</xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(' ', 76,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.DateUtilZR.date8to11($MainRisk/SignDate)"/>
		<xsl:text>������������������   </xsl:text><xsl:text>                                          �ֽ��ֵ��</xsl:text>
		<xsl:text>�� ��</xsl:text>   ���պ�ͬ�ţ�<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/ContNo, 50)"/><xsl:text></xsl:text>�������ս� <xsl:value-of select="$Amnt"/>
		<xsl:text>�� </xsl:text>  �������ƣ�<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_($MainRisk/RiskName, 52)"/><xsl:text>���ҵ�λ�������/Ԫ</xsl:text>
		<xsl:text>��  </xsl:text><xsl:text>  ------------------------------------------------------------------------------------------------</xsl:text>
		<xsl:text>��         </xsl:text><xsl:text>  �������ĩ �ֽ��ֵ  | �������ĩ �ֽ��ֵ  | �������ĩ  �ֽ��ֵ</xsl:text>
		<xsl:text>��  </xsl:text><xsl:text>  ------------------------------------------------------------------------------------------------</xsl:text>
		<xsl:text> </xsl:text><xsl:apply-templates select="/TranData/Body/Risk/CashValues"/>
	    <xsl:text>��  </xsl:text><xsl:text>  ------------------------------------------------------------------------------------------------</xsl:text>
		<xsl:text>��</xsl:text><xsl:text>    *�ֽ��ֵ���и������ֽ��ֵΪ�ͻ��������ɱ�����������б��շѵ�����£����������ĩ</xsl:text>
		<xsl:text>�� </xsl:text><xsl:text>    ����Ӧ���ֽ��ֵ�Ͷ���������ĸ���������ʹ���������á�</xsl:text>
		<xsl:text>��</xsl:text><xsl:text>    *���ڱ��ֽ��ֵ����δ�г��ı������ĩ�ֽ��ֵ��������������м�����һ��ı���ͬ���ֽ�</xsl:text>
		<xsl:text>��</xsl:text><xsl:text>    ��ֵ������˾���紹ѯ��</xsl:text>
		<xsl:text>����������������������������������</xsl:text><xsl:text>                                           ���պ�ͬ�ʹ��ִ</xsl:text>
		<xsl:text>������    </xsl:text><xsl:text>  ���պ�ͬ��: </xsl:text><xsl:value-of select="/TranData/Body/ContNo"/>
       	<xsl:text>��    </xsl:text><xsl:text>  Ͷ����: </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/Appnt/Name, 19)"/><xsl:text>��������������      </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(/TranData/Body/AgentName, 18)"/><xsl:text>����������룺</xsl:text><xsl:value-of select="/TranData/Body/AgentCode"/>
	   	<xsl:text>��    </xsl:text><xsl:text>      ��Ͷ�������յ���˾�ı��պ�ͬ�����պ�ͬ��: </xsl:text><xsl:value-of select="/TranData/Body/ContNo"/><xsl:text>���������պ�ͬ�������յ���</xsl:text>
	   	<xsl:text>��    </xsl:text><xsl:text>  �ֽ��ֵ�����������������ϣ������ȷ�ϱ��պ�ͬ������ȷ���󡣱������Ķ�����Ʒ���</xsl:text>
	   	<xsl:text>��    </xsl:text><xsl:text>  Ͷ����ʾ�飬ȷ�����˽Ⲣ�Ͽɱ��պ�ͬ��ȫ�����ݣ�֪�����˵�Ȩ��������</xsl:text>
	   	<xsl:text>��  </xsl:text><xsl:text></xsl:text>
		<xsl:text>����</xsl:text><xsl:text>      Ͷ����ǩ����                                    ǩ�����ڣ�         ��     ��     ��</xsl:text>
		<xsl:text>��  </xsl:text><xsl:text>                                      �������ɹ�˾��Ա��д</xsl:text>
		<xsl:text>��  </xsl:text><xsl:text>  ------------------------------------------------------------------------------------------------</xsl:text>
		<xsl:text>��</xsl:text><xsl:text>      ���պ�ͬ��Ϊ </xsl:text><xsl:value-of select="/TranData/Body/ContNo"/><xsl:text>�ı��պ�ͬ���ʹ�ͻ����ɿͻ��ױ�ǩ��ȷ�ϣ��ֽ����պ�ͬ�ʹ�</xsl:text>
		<xsl:text>��</xsl:text><xsl:text>      ��ִ���ء�</xsl:text>
		<xsl:text>����</xsl:text><xsl:text>      ��������ǩ����	          ������ǩ�֣�           ���ڣ�      ��     ��     ��</xsl:text>
		<xsl:text>��]]&gt;</xsl:text>
		</xsl:variable>
	<PRINT><xsl:value-of disable-output-escaping="yes" select="$print"/></PRINT>
</xsl:template>


<!-- ѭ��ȡ�ֽ��ֵ��Ϣ -->
<xsl:template name="Cashs" match="CashValues">
        <xsl:variable name="LeiShu" select="33"></xsl:variable>	
	    <xsl:for-each select="CashValue[EndYear &lt; ($LeiShu+1)]">
	    <xsl:variable name="EndYear" select="EndYear"></xsl:variable>	
	    <xsl:variable name="Falseflag" select="java:java.lang.Boolean.parseBoolean('true')" />
		<xsl:text>��        </xsl:text><xsl:choose><xsl:when test="EndYear!='' and Cash!='-'"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(EndYear,6,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Cash),15,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:text>    </xsl:text><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',15,$Falseflag)"/></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+$LeiShu)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+$LeiShu)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+$LeiShu)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose> |   <xsl:choose><xsl:when test="../CashValue[EndYear=($EndYear+$LeiShu+$LeiShu)]/EndYear!=''"><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(../CashValue[EndYear=($EndYear+$LeiShu+$LeiShu)]/EndYear,2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_(java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(../CashValue[EndYear=($EndYear+$LeiShu+$LeiShu)]/Cash),17,$Falseflag)"/></xsl:when><xsl:otherwise><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',2,$Falseflag)"/><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fillStrWith_('-',14,$Falseflag)"/><xsl:text>   </xsl:text></xsl:otherwise></xsl:choose>
	    </xsl:for-each>
</xsl:template>
<!-- �����ִ��� -->
<xsl:template name="idname" match="ID">
<xsl:choose>
	<xsl:when test=".=221201">�к����ݻ�����ȫ����A��</xsl:when>  	<!-- �к����ݻ�����ȫ����A�� -->
	<xsl:when test=".=211902">�к���Ӯ����������˺����� A��</xsl:when>  	<!-- �к���Ӯ����������˺����� A��-->
	<xsl:when test=".=221301">�к���δ�������</xsl:when>  	<!-- �к���δ�������-->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- �Ա� -->
<xsl:template name="tran_sex" match="Sex">
<xsl:choose>      
	<xsl:when test=".=0">��</xsl:when>	<!-- �� -->
	<xsl:when test=".=1">Ů</xsl:when>	<!-- Ů -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template> 

<!-- ֤�����ͣ�����-->
<xsl:template name="tran_GovtIDTC" match="IDType">
<xsl:choose>
	<xsl:when test=".=6">A</xsl:when>	<!--��ְ�ɲ�֤        -->
	<xsl:when test=".=A">B</xsl:when>	<!--ʿ��֤��          -->
	<xsl:when test=".=5">C</xsl:when> <!--����ԺУѧԱ֤    -->
	<xsl:when test=".=B">H</xsl:when> <!--����֤            -->
	<xsl:when test=".=C">0</xsl:when> <!--�����/��ʱ����֤ -->
	<xsl:when test=".=0">1</xsl:when> <!--�������֤        -->
	<xsl:when test=".=2">2</xsl:when> <!--����֤            -->
	<xsl:when test=".=4">3</xsl:when> <!--���ڲ�            -->
	<xsl:when test=".=1">4</xsl:when> <!--����              -->
	<xsl:when test=".=D">5</xsl:when> <!--����֤            -->
	<xsl:when test=".=8">8</xsl:when> <!--�������Թ�֤����  -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose>
</xsl:template>

<!-- ��ϵ -->
<xsl:template name="tran_RelationRoleCode" match="RelaToInsured">
<xsl:choose>
	<xsl:when test=".=00">5</xsl:when> <!-- ����    -->
	<xsl:when test=".=02">1</xsl:when> <!-- ��ż    -->
	<xsl:when test=".=01">2</xsl:when> <!-- ��ĸ    -->
	<xsl:when test=".=03">3</xsl:when> <!-- ��Ů    -->
	<xsl:when test=".=04">4</xsl:when> <!-- ����    -->
	<xsl:when test=".=06">6</xsl:when> <!-- ����     -->
	<xsl:otherwise>--</xsl:otherwise>
</xsl:choose> 
</xsl:template>

<!-- �ɷѷ�ʽ -->
<xsl:template name="tran_payintv" match="PayIntv">
	<xsl:choose>
		<xsl:when test=".=0">����</xsl:when>	<!-- ���� -->
		<xsl:when test=".=12">�꽻</xsl:when>	<!-- �꽻 -->
		<xsl:when test=".=6">���꽻 </xsl:when>	<!-- ���꽻 -->
		<xsl:when test=".=3">����</xsl:when>	<!-- ���� -->
		<xsl:when test=".=1">�½�</xsl:when>	<!-- �½� -->
		<xsl:when test=".=-1">������</xsl:when>	<!-- ������ -->
		<xsl:otherwise>--</xsl:otherwise> 
	</xsl:choose> 
</xsl:template>
<!-- �ս����ڱ�־��ת�� -->
<xsl:template name="tran_PaymentDurationMode" match="PayEndYearFlag">
<xsl:choose>
	<xsl:when test=".='A'">1</xsl:when>	<!-- ����ĳȷ������ -->
	<xsl:when test=".='M'">2</xsl:when>	<!-- �� -->
	<xsl:when test=".='D'">3</xsl:when>	<!-- �� -->
	<xsl:when test=".='Y'">4</xsl:when>	<!-- �� -->
	<xsl:otherwise></xsl:otherwise>  
</xsl:choose>
</xsl:template>
<!-- �����������ڱ�־ -->
<xsl:template name="tran_DurationMode" match="InsuYearFlag">
<xsl:choose>
	<xsl:when test=".=Y">2</xsl:when>	<!-- �� -->
	<xsl:when test=".=A">3</xsl:when>	<!-- ���� -->
	<xsl:when test=".=M">4</xsl:when>	<!-- �� -->
	<xsl:when test=".=D">5</xsl:when>	<!-- �� -->
	<xsl:otherwise>--</xsl:otherwise>  
</xsl:choose>
</xsl:template> 


<!-- ������ȡ��ʽ��ת�� -->
<xsl:template match="BonusGetMode">
<xsl:choose>
	<xsl:when test=".=1">1</xsl:when>	<!-- ������Ϣ -->
	<xsl:when test=".=2">2</xsl:when>	<!-- ��ȡ�ֽ� -->
	<xsl:when test=".=3">3</xsl:when>	<!-- �ֽ����� -->
	<xsl:when test=".=4">5</xsl:when>	<!-- ����� -->
	<xsl:otherwise></xsl:otherwise>  
</xsl:choose>
</xsl:template>

</xsl:stylesheet>
