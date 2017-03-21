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
				<!--银行编号 -->
				<!-- <BankCode>0102</BankCode> -->
				<BankCode>
					<xsl:value-of select="Header/ProvCode"/>
					<xsl:value-of select="Header/BranchNo"/>
				</BankCode>
				<!--交易日期 -->
				<BankDate>
					<xsl:value-of select="Header/TransDate" />
				</BankDate>
				<!--交易时间 -->
				<BankTime>
					<xsl:value-of select="Header/TransTime" />
				</BankTime>
				<!-- 一级分行号(地区编号) -->
				<ZoneNo>
					<xsl:value-of select="Header/ProvCode"/>
				</ZoneNo>
				<!-- 保险客户名单提供地区编号(分支编号) -->
				<BrNo>
					<xsl:value-of select="Header/ProvCode"/>
					<xsl:value-of select="Header/BranchNo"/>
				</BrNo>
				<!--柜员编号 -->
				<TellerNo>
					<xsl:value-of select="Header/Tlid" />
				</TellerNo>
				<!--#服务方流水号 -->
				<TransrNo>
					<xsl:value-of select="Header/SerialNo" />
				</TransrNo>
				<!--交易类型 -->
				<FunctionFlag>
					<xsl:value-of select="Head/FuncFlag" />
				</FunctionFlag>
				<!--保险公司编号 -->
				<InsuID>
					<xsl:value-of select="Header/CorpNo" />
				</InsuID>
				<!--一级分行号-->
				<BankBrNo_Lv1 />
				<InNoDoc>
					<xsl:value-of select="Head/InNoDoc" />
				</InNoDoc>
			</BaseInfo>
			<LCCont>
				<Risks>
					<!-- 险种个数 -->
					<RiskCount>1</RiskCount>
						<Risk>
							<!-- 险种编号 -->
							<RiskCode>
								<xsl:value-of select="App/Req/RiskCode" />
							</RiskCode>
						</Risk>
				</Risks>
				<!--投保单印刷号 -->
				<ProposalContNo>
					<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(App/Req/PolicyApplyNo)" />
				</ProposalContNo>
				<!-- 预算金额 -->
				<xsl:variable name="BudGet" select="App/Req/Prem"/>
				<BudGet>
					<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen($BudGet)" />
				</BudGet>
				<LCAppnt>
					<!-- 投保人名称 -->
					<AppntName>
						<xsl:value-of select="App/Req/Appl/Name" />
					</AppntName>
					<!-- 投保人证件类型代码 -->
					<AppntIDType>
						<xsl:apply-templates select="App/Req/Appl/IDKind"/>
					</AppntIDType>
					<!-- 投保人证件号码 -->
					<AppntIDNo>
						<xsl:value-of select="App/Req/Appl/IDCode" />
					</AppntIDNo>
				</LCAppnt>
			</LCCont>
		</TranData>
	</xsl:template>
	<!-- 证件类型-->
	<xsl:template name="tran_AppntIDType" match="App/Req/Appl/IDKind">
		<xsl:choose>
			<xsl:when test=".=110001">0</xsl:when>	<!-- 居民身份证 -->
			<xsl:when test=".=110002">0</xsl:when>	<!-- 重号居民身份证-->
			<xsl:when test=".=110003">0</xsl:when>	<!-- 临时居民身份证 -->
			<xsl:when test=".=110004">0</xsl:when>	<!-- 重号临时居民身份证 -->
			<xsl:when test=".=110005">4</xsl:when>  <!-- 户口簿 -->
			<xsl:when test=".=110006">4</xsl:when>  <!-- 重号户口簿  -->
			<xsl:when test=".=110007">2</xsl:when>  <!-- 中国人民解放军军人身份证  -->
			<xsl:when test=".=110008">2</xsl:when>  <!-- 重号中国人民解放军军人身份证  -->
			<xsl:when test=".=110009">D</xsl:when>  <!-- 中国人民武装警察身份证件  -->
			<xsl:when test=".=110010">D</xsl:when>  <!-- 重号中国人民武装警察身份证件  -->
			<xsl:when test=".=110011">99</xsl:when>  <!-- 离休干部荣誉证 -->
			<xsl:when test=".=110012">99</xsl:when>  <!-- 重号离休干部荣誉证 -->
			<xsl:when test=".=110013">99</xsl:when>  <!-- 军官退休证 -->
			<xsl:when test=".=110014">99</xsl:when>  <!-- 重号军官退休证 -->
			<xsl:when test=".=110015">99</xsl:when>  <!-- 文职干部退休证 -->
			<xsl:when test=".=110016">99</xsl:when>  <!-- 重号文职干部退休证 -->
			<xsl:when test=".=110017">99</xsl:when>  <!-- 军事院校学员证 -->
			<xsl:when test=".=110018">99</xsl:when>  <!-- 重号军事院校学员证 -->
			<xsl:when test=".=110019">F</xsl:when>  <!-- 港澳居民往来内地通行证 -->
			<xsl:when test=".=110020">F</xsl:when>  <!-- 重号港澳居民往来内地通行证 -->
			<xsl:when test=".=110021">F</xsl:when>  <!-- 台湾居民往来大陆通行证 -->
			<xsl:when test=".=110022">F</xsl:when>  <!-- 重号台湾居民往来大陆通行证 -->
			<xsl:when test=".=110023">1</xsl:when>  <!-- 中华人民共和国护照 -->
			<xsl:when test=".=110024">1</xsl:when>  <!-- 重号中华人民共和国护照 -->
			<xsl:when test=".=110025">1</xsl:when>  <!-- 外国护照 -->
			<xsl:when test=".=110026">1</xsl:when>  <!-- 重号外国护照 -->
			<xsl:when test=".=110027">2</xsl:when>  <!-- 军官证 -->
			<xsl:when test=".=110028">2</xsl:when>  <!-- 重号军官证 -->
			<xsl:when test=".=110029">99</xsl:when>  <!-- 文职干部证 -->
			<xsl:when test=".=110030">99</xsl:when>  <!-- 重号文职干部证 -->
			<xsl:when test=".=110031">D</xsl:when>  <!-- 警官证 -->
			<xsl:when test=".=110032">D</xsl:when>  <!-- 重号警官证 -->
			<xsl:when test=".=110033">2</xsl:when>  <!-- 军人士兵证 -->
			<xsl:when test=".=110034">2</xsl:when>  <!-- 重号军人士兵证 -->
			<xsl:when test=".=110035">D</xsl:when>  <!-- 武警士兵证 -->
			<xsl:when test=".=110036">D</xsl:when>  <!-- 重号武警士兵证 -->
			<xsl:when test=".=119998">99</xsl:when>  <!-- 系统使用的个人证件识别标识 -->
			<xsl:when test=".=119999">99</xsl:when>  <!-- 其他个人证件识别标识 -->
			<xsl:otherwise>--</xsl:otherwise>  
		</xsl:choose>
	</xsl:template>
		
</xsl:stylesheet>
