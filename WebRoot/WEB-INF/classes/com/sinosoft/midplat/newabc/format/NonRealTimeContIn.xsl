<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
	exclude-result-prefixes="java">
	<xsl:template match="ABCB2I">
		<TranData>
			<Head>
				<ServiceId>67</ServiceId>
				<TranCom><xsl:value-of select="Head/TranCom" /></TranCom>
			</Head>
			<BaseInfo>
				<!--���б�� -->
				<!-- <BankCode>0102</BankCode> -->
				<BankCode>
					<xsl:value-of select="Header/ProvCode"/>
					<xsl:value-of select="Header/BranchNo"/>
				</BankCode>
				<!--�������� -->
				<BankDate>
					<xsl:value-of select="Header/TransDate" />
				</BankDate>
				<!--����ʱ�� -->
				<BankTime>
					<xsl:value-of select="Header/TransTime" />
				</BankTime>
				<!-- һ�����к�(�������) -->
				<ZoneNo>
					<xsl:value-of select="Header/ProvCode"/>
				</ZoneNo>
				<!-- ���տͻ������ṩ�������(��֧���) -->
				<BrNo>
					<xsl:value-of select="Header/ProvCode"/>
					<xsl:value-of select="Header/BranchNo"/>
				</BrNo>
				<!--��Ա��� -->
				<TellerNo>
					<xsl:value-of select="Header/Tlid" />
				</TellerNo>
				<!--#������ˮ�� -->
				<TransrNo>
					<xsl:value-of select="Header/SerialNo" />
				</TransrNo>
				<!--�������� -->
				<FunctionFlag>
					<xsl:value-of select="Head/FuncFlag" />
				</FunctionFlag>
				<!--���չ�˾��� -->
				<InsuID>
					<xsl:value-of select="Header/CorpNo" />
				</InsuID>
				<!--һ�����к�-->
				<BankBrNo_Lv1 />
				<InNoDoc>
					<xsl:value-of select="Head/InNoDoc" />
				</InNoDoc>
			</BaseInfo>
			<LCCont>
				<Risks>
					<!-- ���ָ��� -->
					<RiskCount>1</RiskCount>
						<Risk>
							<!-- ���ֱ�� -->
							<RiskCode>
								<xsl:value-of select="App/Req/RiskCode" />
							</RiskCode>
						</Risk>
				</Risks>
				<!--Ͷ����ӡˢ�� -->
				<ProposalContNo>
					<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/PolicyApplyNo)" />
				</ProposalContNo>
				<!-- Ԥ���� -->
				<xsl:variable name="BudGet" select="App/Req/Prem"/>
				<BudGet>
					<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen($BudGet)" />
				</BudGet>
				<LCAppnt>
					<!-- Ͷ�������� -->
					<AppntName>
						<xsl:value-of select="App/Req/Appl/Name" />
					</AppntName>
					<!-- Ͷ����֤�����ʹ��� -->
					<AppntIDType>
						<xsl:apply-templates select="App/Req/Appl/IDKind"/>
					</AppntIDType>
					<!-- Ͷ����֤������ -->
					<AppntIDNo>
						<xsl:value-of select="App/Req/Appl/IDCode" />
					</AppntIDNo>
				</LCAppnt>
			</LCCont>
		</TranData>
	</xsl:template>
	<!-- ֤������-->
	<xsl:template name="tran_AppntIDType" match="App/Req/Appl/IDKind">
		<xsl:choose>
			<xsl:when test=".=110001">0</xsl:when>	<!-- �������֤ -->
			<xsl:when test=".=110002">0</xsl:when>	<!-- �غž������֤-->
			<xsl:when test=".=110003">0</xsl:when>	<!-- ��ʱ�������֤ -->
			<xsl:when test=".=110004">0</xsl:when>	<!-- �غ���ʱ�������֤ -->
			<xsl:when test=".=110005">4</xsl:when>  <!-- ���ڲ� -->
			<xsl:when test=".=110006">4</xsl:when>  <!-- �غŻ��ڲ�  -->
			<xsl:when test=".=110007">2</xsl:when>  <!-- �й������ž��������֤  -->
			<xsl:when test=".=110008">2</xsl:when>  <!-- �غ��й������ž��������֤  -->
			<xsl:when test=".=110009">D</xsl:when>  <!-- �й�������װ�������֤��  -->
			<xsl:when test=".=110010">D</xsl:when>  <!-- �غ��й�������װ�������֤��  -->
			<xsl:when test=".=110011">99</xsl:when>  <!-- ���ݸɲ�����֤ -->
			<xsl:when test=".=110012">99</xsl:when>  <!-- �غ����ݸɲ�����֤ -->
			<xsl:when test=".=110013">99</xsl:when>  <!-- ��������֤ -->
			<xsl:when test=".=110014">99</xsl:when>  <!-- �غž�������֤ -->
			<xsl:when test=".=110015">99</xsl:when>  <!-- ��ְ�ɲ�����֤ -->
			<xsl:when test=".=110016">99</xsl:when>  <!-- �غ���ְ�ɲ�����֤ -->
			<xsl:when test=".=110017">99</xsl:when>  <!-- ����ԺУѧԱ֤ -->
			<xsl:when test=".=110018">99</xsl:when>  <!-- �غž���ԺУѧԱ֤ -->
			<xsl:when test=".=110019">F</xsl:when>  <!-- �۰ľ��������ڵ�ͨ��֤ -->
			<xsl:when test=".=110020">F</xsl:when>  <!-- �غŸ۰ľ��������ڵ�ͨ��֤ -->
			<xsl:when test=".=110021">F</xsl:when>  <!-- ̨�����������½ͨ��֤ -->
			<xsl:when test=".=110022">F</xsl:when>  <!-- �غ�̨�����������½ͨ��֤ -->
			<xsl:when test=".=110023">1</xsl:when>  <!-- �л����񹲺͹����� -->
			<xsl:when test=".=110024">1</xsl:when>  <!-- �غ��л����񹲺͹����� -->
			<xsl:when test=".=110025">1</xsl:when>  <!-- ������� -->
			<xsl:when test=".=110026">1</xsl:when>  <!-- �غ�������� -->
			<xsl:when test=".=110027">2</xsl:when>  <!-- ����֤ -->
			<xsl:when test=".=110028">2</xsl:when>  <!-- �غž���֤ -->
			<xsl:when test=".=110029">99</xsl:when>  <!-- ��ְ�ɲ�֤ -->
			<xsl:when test=".=110030">99</xsl:when>  <!-- �غ���ְ�ɲ�֤ -->
			<xsl:when test=".=110031">D</xsl:when>  <!-- ����֤ -->
			<xsl:when test=".=110032">D</xsl:when>  <!-- �غž���֤ -->
			<xsl:when test=".=110033">2</xsl:when>  <!-- ����ʿ��֤ -->
			<xsl:when test=".=110034">2</xsl:when>  <!-- �غž���ʿ��֤ -->
			<xsl:when test=".=110035">D</xsl:when>  <!-- �侯ʿ��֤ -->
			<xsl:when test=".=110036">D</xsl:when>  <!-- �غ��侯ʿ��֤ -->
			<xsl:when test=".=119998">99</xsl:when>  <!-- ϵͳʹ�õĸ���֤��ʶ���ʶ -->
			<xsl:when test=".=119999">99</xsl:when>  <!-- ��������֤��ʶ���ʶ -->
			<xsl:otherwise>--</xsl:otherwise>  
		</xsl:choose>
	</xsl:template>
		
</xsl:stylesheet>
