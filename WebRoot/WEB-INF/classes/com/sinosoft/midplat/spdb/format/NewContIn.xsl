<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"  exclude-result-prefixes="java">
 	
<xsl:template match="REQUEST">
	  <TranData><!-- 核心录单自核请求报文 -->
	     <Head>
	  		<!-- 交易日期 -->
	  		<TranDate><xsl:value-of select="BUSI/TRSDATE"/></TranDate>
	  		<!-- 交易时间-->
			<TranTime><xsl:value-of select="string(java:com.sinosoft.midplat.common.DateUtil.getCur6Time())"/></TranTime>
			<!-- 银行代码 -->
			<BankCode><xsl:value-of select="Head/BankCode"/></BankCode>
			<!-- 地区代码 -->
			<ZoneNo><xsl:value-of select="DIST/ZONE"/></ZoneNo>
			<!-- 银行网点 -->
			<NodeNo><xsl:value-of select="DIST/DEPT"/></NodeNo>
			<!-- 柜员代码 -->
			<TellerNo><xsl:value-of select="DIST/TELLER"/></TellerNo>
			<!-- 交易流水号 -->
			<TranNo><xsl:value-of select="BUSI/TRANS"/></TranNo>
			<!-- YBT组织的节点信息 -->
			<xsl:copy-of select="Head/*"/>
	  	</Head>
		<!--投保信息-->
		<Body>
			<!-- 销售渠道-->
			<SaleChannel>0</SaleChannel>
			<!-- 申请顺序号- 暂时不用 -->
			<ApplyNo />
			<xsl:variable name="sAccNo" select="BUSI/CONTENT" />
		  	<!-- 投保单(印刷)号 -->
			<ProposalPrtNo>
				<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(BUSI/CONTENT/MAIN/APPNO)"/>
			</ProposalPrtNo>  
		   	<!-- 保单合同印刷号 (单证)  -->
			<ContPrtNo>
				<xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(BUSI/CONTENT/BILL/BILL_USED)"/>
			</ContPrtNo>
		   <!-- 投保日期 -->
			<PolApplyDate><xsl:value-of select="BUSI/CONTENT/MAIN/APPDATE"/></PolApplyDate>
		   <!-- 保单递送方式 默认全为1-->
			<GetPolMode>1</GetPolMode><!-- 默认为1，所以写死成1 -->
		   <!-- 健康告知(N/Y) -->
			<HealthNotice><xsl:value-of select="BUSI/CONTENT/HEALTH/NOTICE"/></HealthNotice>
		   <!-- 交费账户姓名 -->
		   <AccName><xsl:value-of select="BUSI/CONTENT/TBR/NAME" /></AccName>
		   <!-- 交费银行账户 -->
		   <AccNo><xsl:value-of select="BUSI/CONTENT/MAIN/PAYACC"/></AccNo>
		    <!-- 销售人员姓名  -->
			<SaleName/>
		   	<!-- 销售人员工号 -->
			<SaleStaff><xsl:value-of select="//DIST/TELLER"/></SaleStaff>
            <!-- 销售人员资格证号  -->
            <SaleCertNo/>
			<!-- 投保人 -->
			<xsl:apply-templates select="BUSI/CONTENT/TBR"/>
			<!-- 被保人 -->
			<xsl:apply-templates select="BUSI/CONTENT/BBR"/>
			<!-- 受益人 -->
			<xsl:apply-templates select="BUSI/CONTENT/SYR"/>
			<!-- 险种信息 -->
			<xsl:for-each select="BUSI/CONTENT/PTS/PT">
			    <Risk>
			    	<!-- 险种代码 -->
					<RiskCode><xsl:value-of select="ID"/></RiskCode>
					<!-- 主险险种代码 待确定 -->
					<MainRiskCode><xsl:value-of select="ID"/></MainRiskCode>
					<!-- 保额(分)  -->
					<Amnt><xsl:value-of select="AMNT"/></Amnt>
					<!-- 保险费(分) -->
					<Prem><xsl:value-of select="PREMIUM"/></Prem>
					<!-- 投保份数 -->
					<Mult>
						<xsl:value-of select="UNIT"/>
					</Mult>
					<!-- 缴费形式 -->
					<PayMode>B</PayMode>
					<!-- 缴费频次 -->
					<PayIntv>
						<xsl:call-template name="tran_payintv">
							<xsl:with-param name="payintv">
								<xsl:value-of select="CRG_T"/>
							</xsl:with-param>
						</xsl:call-template>
					</PayIntv>
					<!-- 保险年期年龄标志 -->
					<InsuYearFlag><xsl:apply-templates select="COVER_T"/></InsuYearFlag>
					<!-- 保险年期年龄 -->
					<InsuYear><xsl:value-of select="COVER_Y"/></InsuYear>
					<xsl:choose>
						<xsl:when test="CRG_T = '1'"><!-- 趸交传1000Y -->
							<!-- 缴费年期年龄标志 -->
							<PayEndYearFlag>Y</PayEndYearFlag>			
							<!-- 缴费年期年龄 -->
							<PayEndYear>1000</PayEndYear>
						</xsl:when>
						<xsl:when test="CRG_T = '2' or CRG_T='7'"><!-- 年交传2Y -->
							<!-- 缴费年期年龄标志 -->
							<PayEndYearFlag><xsl:apply-templates select="CRG_T"/></PayEndYearFlag>			
							<!-- 缴费年期年龄 -->
							<PayEndYear><xsl:value-of select="CRG_Y"/></PayEndYear>
						</xsl:when>
					</xsl:choose>
					<!-- 红利领取方式 -->
					<BonusGetMode><xsl:apply-templates select="HLLQ_T"/></BonusGetMode>
					<!-- 满期领取金领取方式 -->
					<FullBonusGetMode><xsl:apply-templates select="DRAW_T" /></FullBonusGetMode>
					<!-- 领取年龄年期标志 -->
					<GetYearFlag><xsl:apply-templates select="DRAW_T" /></GetYearFlag>
					<!-- 领取年龄 -->
					<xsl:variable name="Start" select="DRAW_FST" />
					<xsl:variable name="End" select="DRAW_LST" />
					<xsl:if test="string-length($Start) != 0 and string-length($End) !=0">
						<GetYear><xsl:value-of select="$Start - $End" /></GetYear>
					</xsl:if>
					<xsl:if test="string-length($Start) = 0 or string-length($End) =0">
						<GetYear>0</GetYear>
					</xsl:if>
					<!-- 领取年期-->
					<GetTerms/>
					<!-- 领取方式 传空 -->
					<GetIntv/>
					<!-- 领取银行编码 传空 -->
					<GetBankCode/>
					<!-- 领取银行账户 传空 -->
					<GetBankAccNo/>
					<!-- 领取银行户名  传空 -->
					<GetAccName/>
					<!-- 自动垫交标志 传空 -->
			    	<AutoPayFlag/>
			    </Risk>
			</xsl:for-each>
						
			<!--缴费形式 和朱诚沟通，从银保通出单的中韩这边缴费形式都置为B -工行对应处的说明-->
			<PayMode>B</PayMode>
			<!-- 职业告知 -->
			<JobNotice/>
		</Body>
	</TranData>
</xsl:template>	

<xsl:template match="BUSI/CONTENT/TBR">
		<Appnt>
			<!-- 姓名 -->
			<Name><xsl:value-of select="NAME"/></Name>
			<!-- 性别 -->
			<Sex><xsl:apply-templates select="SEX"/></Sex>
			<!-- 出生日期 -->
			<Birthday><xsl:value-of select="BIRTH"/></Birthday>
			<!-- 证件类型 -->
			<IDType><xsl:apply-templates select="IDTYPE"/></IDType>
			<!-- 证件号码 -->
			<IDNo><xsl:value-of select="IDNO"/></IDNo>
			<!-- 职业代码 -->
			<JobCode><xsl:apply-templates select="Occupation"/></JobCode>
			<!-- 国籍 -->
			<Nationality><xsl:apply-templates select="COUNTRY_CODE"/></Nationality>
			<!-- 身高(cm)  空值 -->
			<Stature/>
			<!-- 体重(kg)  -->
			<Weight/>
			<!-- 婚否(N/Y) 空值 -->
			<MaritalStatus><xsl:apply-templates select="marriage"/></MaritalStatus>
			<!-- 年收入(万元) -->
			<YearSalary><xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.fenToWYuan(INCOME)"/></YearSalary>
			<!-- 投保人家庭年收入 -->
			<FamilyYearSalary/>
			<!-- 身份证证件有效期格式:yyyyMMdd --><!-- 8位日期，长期有效为20991231 -->
			<xsl:choose>
				<xsl:when test = "IDVALIDATE=20991231"><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="IDVALIDATE"/></IdExpDate></xsl:otherwise>
			</xsl:choose>
			<!-- 投保人详细地址内容 -->
			<AddressContent/>
			<!-- 投保人固定电话国内区号 -->
			<FixTelDmstDstcNo/>
			<!-- 投保人移动电话国际区号 -->
			<MobileItlDstcNo/>
			<!-- 投保人国家地区代码 -->
			<NationalityCode/>
			<!-- 投保人地址 -->
			<Address><xsl:value-of select="ADDR"/></Address>
			<!-- 投保人邮政编码 -->
			<ZipCode><xsl:value-of select="ZIP"/></ZipCode>
			<!-- 移动电话 -->
			<Mobile><xsl:value-of select="MP"/></Mobile>
			<!-- 家庭电话 -->
			<Phone><xsl:value-of select="TEL"/></Phone>
			<!-- 电子邮件 -->
			<Email><xsl:value-of select="EMAIL"/></Email>
			<!-- 投保人与被保人关系 -->
			<RelaToInsured><xsl:apply-templates select="BBR_RELA"/></RelaToInsured>
			<!-- 投保人居民类型 -->
		 	<DenType></DenType>
			
		</Appnt>
</xsl:template>
		
<!--被保人信息-->
<xsl:template match="BUSI/CONTENT/BBR">
		<Insured>
			<!-- 姓名 -->
			<Name><xsl:value-of select="NAME"/></Name>
			<!-- 性别 -->
			<Sex><xsl:apply-templates select="SEX"/></Sex>
			<!-- 出生日期 -->
			<Birthday><xsl:value-of select="BIRTH"/></Birthday>
			<!-- 证件类型 -->
			<IDType><xsl:apply-templates select="IDTYPE"/></IDType>
			<!-- 证件号码 -->
			<IDNo><xsl:value-of select="IDNO"/></IDNo>
			<!-- 职业代码 -->
			<JobCode><xsl:apply-templates select="Occupation"/></JobCode>
			<!-- 国籍 -->
			<Nationality><xsl:apply-templates select="COUNTRY_CODE"/></Nationality>
			<!-- 身高(cm)  空值 -->
			<Stature/>
			<!-- 体重(g)  空值 -->
			<Weight/>
			<!-- 婚否(N/Y) 空值 -->
			<MaritalStatus><xsl:apply-templates select="marriage"/></MaritalStatus>
			<!-- 年收入(万元) -->
			<!-- 根据交易渠道的不同金额的显示方式不同 -->
			<YearSalary>
				<xsl:value-of select="java:com.sinosoft.midplat.common.CalculateUtil.fenToWYuan(INCOME)"/>
			</YearSalary>
			<!-- 身份证证件有效期 --><!-- 8位日期，长期有效为20991231 -->
			<xsl:choose>
				<xsl:when test = "IDVALIDATE=20991231"><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="IDVALIDATE"/></IdExpDate></xsl:otherwise>
			</xsl:choose>
			<!-- 被保人地址 -->
			<Address><xsl:value-of select="ADDR"/></Address>
			<!-- 邮编 -->
			<ZipCode><xsl:value-of select="ZIP"/></ZipCode>
			<!-- 移动电话 -->
			<Mobile><xsl:value-of select="MP"/></Mobile>
			<!-- 固定电话 -->
			<Phone><xsl:value-of select="TEL"/></Phone>
			<!-- 电子邮件 -->
			<Email><xsl:value-of select="EMAIL"/></Email>
			
		</Insured>
</xsl:template>

<xsl:template match="BUSI/CONTENT/SYR">
		<Bnf>
			<!-- 受益人类别 (0生存，1身故，2红利) -->
			<Type>0</Type>
			<!-- 受益顺序 -->
			<Grade><xsl:value-of select="ORDER"/></Grade>
			<!-- 姓名 -->
			<Name><xsl:value-of select="NAME"/></Name>
			<!-- 性别 -->
			<Sex><xsl:apply-templates select="SEX"/></Sex>
			<!-- 出生日期 -->
			<Birthday><xsl:value-of select="BIRTH"/></Birthday>
			<!-- 证件类型 -->
			<IDType><xsl:apply-templates select="IDTYPE"/></IDType>
			<!-- 证件号码 -->
			<IDNo><xsl:value-of select="IDNO"/></IDNo>
			 <!-- 受益人与被保人关系 -->
			<RelaToInsured><xsl:apply-templates select="BBR_RELA"/></RelaToInsured>
			<!-- 受益比例 -->
			<Lot><xsl:value-of select="RATIO"/></Lot>
			<!-- 身份证证件有效期 -->
			<xsl:choose>
				<xsl:when test = "IDVALIDATE=20991231"><IdExpDate>99990101</IdExpDate></xsl:when>
				<xsl:otherwise><IdExpDate><xsl:value-of select="IDVALIDATE"/></IdExpDate></xsl:otherwise>
			</xsl:choose>
			<BeneficType>N</BeneficType>
		</Bnf>
</xsl:template>

<!-- 保单递送方式 -->
<xsl:template>
	<xsl:choose>
		<xsl:when test=".=1"></xsl:when><!-- 部门发送 -->
		<xsl:when test=".=2">1</xsl:when><!-- 邮寄 -->
		<xsl:when test=".=3"></xsl:when><!-- 上门递送 -->
		<xsl:when test=".=4">0</xsl:when><!-- 银行柜台 -->
		<xsl:otherwise></xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- 关系代码 -->
<xsl:template name="tran_relatoinsured" match="BBR_RELA">
	<xsl:choose>
		<xsl:when test=".=1">02</xsl:when><!-- 配偶 -->
		<xsl:when test=".=2">01</xsl:when><!-- 父母 -->
		<xsl:when test=".=3">03</xsl:when><!-- 子女 -->
		<xsl:when test=".=4"></xsl:when><!-- 亲属 -->
		<xsl:when test=".=5">00</xsl:when><!-- 本人 -->
		<xsl:when test=".=6">06</xsl:when><!-- 其它 -->
		<xsl:otherwise>--</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- 性别 -->
<xsl:template name="tran_sex" match="SEX">
	<xsl:choose>
		<xsl:when test=".=1">0</xsl:when>	<!-- 男 -->
		<xsl:when test=".=2">1</xsl:when>	<!-- 女 -->
		<xsl:when test=".=3"></xsl:when>	<!-- 不确定 -->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>


<!-- 证件类型-->
<xsl:template name="tran_idtype" match="IDTYPE">
	<xsl:choose>
		<xsl:when test=".=1">0</xsl:when>	<!-- 身份证 -->
		<xsl:when test=".=2">1</xsl:when>	<!-- 护照 -->
		<xsl:when test=".=3">2</xsl:when>	<!-- 军官证 -->
		<xsl:when test=".=4">D</xsl:when>	<!-- 武警证 -->
		<xsl:when test=".=5">F</xsl:when>	<!-- 港澳居民来往内地通行证 -->
		<xsl:when test=".=6">4</xsl:when>	<!-- 户口簿 -->
		<xsl:when test=".=7"></xsl:when>	<!-- 其他 -->
		<xsl:when test=".=8">D</xsl:when>	<!-- 警官证 -->
		<xsl:when test=".=9"></xsl:when>	<!-- 执行公务证 -->
		<xsl:when test=".=A">2</xsl:when>	<!-- 士兵证 -->
		<xsl:when test=".=B">F</xsl:when>	<!-- 台湾同胞来往内地通行证 -->
		<xsl:when test=".=C">0</xsl:when>	<!-- 临时身份证 -->
		<xsl:when test=".=D"></xsl:when>	<!-- 外国人居留证 -->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- 缴费方式（频次）   6	年龄   7	终身交费  核心没有   
0	不定期
1	趸交
2	月交
3	季交
4	半年交
5	年交
6	年龄
7	终身交费
-->
<xsl:template name="tran_payintv" >
	<xsl:param name="payintv"/>
	<xsl:choose>
		<xsl:when test="$payintv=1">0</xsl:when>	<!-- 趸交 -->
		<xsl:when test="$payintv=2">12</xsl:when>	<!-- 年交 -->
		<xsl:when test="$payintv=3">6</xsl:when>	<!-- 半年交 -->
		<xsl:when test="$payintv=4">3</xsl:when>	<!-- 季交 -->
		<xsl:when test="$payintv=5">1</xsl:when>	<!-- 月交 -->
		<xsl:when test="$payintv=6">12</xsl:when>	<!-- 年交 -->
		<xsl:when test="$payintv=8">-1</xsl:when>	<!-- 不定期交 -->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- 缴费年期年龄类型 -->
<xsl:template name="tran_payendyearflag" match="CRG_T">
	<xsl:choose>
		<xsl:when test=".='0'"></xsl:when>	<!-- 无关-->
		<xsl:when test=".='1'">A</xsl:when>	<!-- 趸缴 -->
		<xsl:when test=".='2'">Y</xsl:when>	<!-- 年缴 -->
		<xsl:when test=".='3'"></xsl:when>	<!-- 半年缴 -->
		<xsl:when test=".='4'"></xsl:when>	<!-- 季缴 -->
		<xsl:when test=".='5'">M</xsl:when><!-- 月缴 -->
		<xsl:when test=".='6'">A</xsl:when><!-- 缴至某确定年龄 -->
		<xsl:when test=".='7'"></xsl:when><!-- 终生缴费 -->
		<xsl:when test=".='8 '"></xsl:when><!-- 不定期缴 -->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>	

<!-- 保险年期年龄类型
0	无关
1	日
2	月
3	季
4	年
5	年龄
6	终身
 -->
<xsl:template name="tran_insuyearflag" match="COVER_T">
	<xsl:choose>
		<xsl:when test=".=0"></xsl:when><!-- 无关 -->
		<xsl:when test=".=1"></xsl:when>	<!-- 保终身 -->
		<xsl:when test=".=2">Y</xsl:when>	<!-- 按年限保 -->
		<xsl:when test=".=3">A</xsl:when><!-- 保至某确定年龄 -->
		<xsl:when test=".=4">M</xsl:when>	<!-- 按月保 -->
		<xsl:when test=".=5">D</xsl:when>	<!-- 按天保 -->
		<xsl:otherwise>--</xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- 领取年龄年期标志
   1 	按年龄
2	按月
3	按日
4	按年
 -->
<xsl:template name="tran_getYearFlag" match="GetYearFlag">
	<xsl:choose>
		<xsl:when test=".=0">Y</xsl:when>	<!-- 按年 -->
		<xsl:when test=".=1">A</xsl:when>	<!-- 按年龄 -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!-- 红利领取方式
 0	直接给付
1	抵交保费
2	累积生息
3	增额交清
 -->
<xsl:template name="tran_bonusgetmode" match="HLLQ_T">
		<xsl:choose>
		<xsl:when test=".=1">1</xsl:when>	<!-- 积累生息 -->
		<xsl:when test=".=2">2</xsl:when>	<!-- 现金领取 -->
		<xsl:when test=".=3">3</xsl:when>	<!-- 抵交保险费   -->
		<xsl:when test=".=4">5</xsl:when>	<!-- 缴清增额   -->
		<xsl:when test=".=''"></xsl:when><!-- 年金产品没有红利领取方式 -->
		<xsl:otherwise>4</xsl:otherwise><!-- 其他 -->
	</xsl:choose>
</xsl:template>

<!-- 满期领取金领取方式 -->
<xsl:template name="tran_FullBonusGetMode" match="DRAW_T">
	<xsl:choose>
		<xsl:when test=".=0"></xsl:when><!-- 无关 -->
		<xsl:when test=".=1">Y</xsl:when><!-- 年领 -->
		<xsl:when test=".=2"></xsl:when><!-- 半年领 -->
		<xsl:when test=".=3"></xsl:when><!-- 季领 -->
		<xsl:when test=".=4"></xsl:when><!-- 月领 -->
		<xsl:when test=".=5"></xsl:when><!-- 趸领 -->
		<xsl:otherwise></xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- 投保人居民类型 -->
<xsl:template name="cust_Source" match="CustSource">
	<xsl:choose>
		<xsl:when test=".=0">2</xsl:when>	<!-- 城镇 -->
		<xsl:when test=".=1">1</xsl:when>	<!-- 农村 -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<xsl:template name="tran_jobcode" match="Occupation">
	<xsl:choose>
		<xsl:when test=".=2113001">2099904</xsl:when><!-- 教练 -->
		<xsl:when test=".=9999999 "></xsl:when><!-- 9999999999 -->
		<xsl:when test=".=0000003">8000002</xsl:when><!-- 离退休人员 -->
		<xsl:when test=".=0000001">2099907</xsl:when><!-- 儿童、18岁前学生 -->
		<xsl:when test=".=0002001"></xsl:when><!-- 负责人（不亲自作业） -->
		<xsl:when test=".=0805001">2021106</xsl:when><!-- 工程师 -->
		<xsl:when test=".=0805002">6030516</xsl:when><!-- 技师 -->
		<xsl:when test=".=0601004">4040701</xsl:when><!-- 负责人 -->
		<xsl:when test=".=0703005"></xsl:when><!-- 一般内勤、经理 -->
		<xsl:when test=".=0810012">6050712</xsl:when><!-- 	领班、监工 -->
		<xsl:when test=".=0402001">6010316</xsl:when><!-- 经营者（不到现场者） -->
		<xsl:when test=".=0402002">6010317</xsl:when><!-- 经营者（现场监督者） -->
		<xsl:when test=".=0405001">6010608</xsl:when><!-- 行政人员 -->
		<xsl:when test=".=0602002"></xsl:when><!-- 一般内勤工作人员 -->
		<xsl:when test=".=0603001">4030503</xsl:when><!-- 经理人员 -->
		<xsl:when test=".=0705005">6230710</xsl:when><!-- 承包商、监工 -->
		<xsl:when test=".=0901001">2129901</xsl:when><!-- 内勤人员 -->
		<xsl:when test=".=0901002"></xsl:when><!-- 外勤人员 -->
		<xsl:when test=".=0902002"></xsl:when><!-- 业务员 -->
		<xsl:when test=".=1201001"></xsl:when><!-- 教师 -->
		<xsl:when test=".=1201002"></xsl:when><!-- 学生 -->
		<xsl:when test=".=1201004">2099902</xsl:when><!-- 军训教官、体育教师 -->
		<xsl:when test=".=1602001">2080301</xsl:when><!-- 律师 -->
		<xsl:when test=".=1602002">2060301</xsl:when><!-- 会计师 -->
		<xsl:when test=".=1701001">4071203</xsl:when><!-- 家庭主妇 -->
		<xsl:when test=".=1801001">3020101</xsl:when><!-- 警务行政及内勤人员 -->
		<xsl:otherwise></xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- 国籍  -->
<xsl:template name="tran_Country" match="COUNTRY_CODE">
	<xsl:choose>
		<xsl:when test=".=ALB">ALB</xsl:when><!-- 阿尔巴尼亚 -->
		<xsl:when test=".=DZA">ALG</xsl:when><!-- 阿尔及利亚 -->
		<xsl:when test=".=AFG">AFG</xsl:when><!-- 阿富汗 -->
		<xsl:when test=".=ARG">ARG</xsl:when><!-- 阿根廷 -->
		<xsl:when test=".=AZE">AZE</xsl:when><!-- 阿塞拜疆 -->
		<xsl:when test=".=EGY">EGY</xsl:when><!-- 埃及 -->
		<xsl:when test=".=ETH">ETH</xsl:when><!-- 埃塞俄比亚 -->
		<xsl:when test=".=IRL">IRL</xsl:when><!-- 爱尔兰 -->
		<xsl:when test=".=EST">EST</xsl:when><!-- 爱沙尼亚 -->
		<xsl:when test=".=AND">AND</xsl:when><!-- 安道尔 -->
		<xsl:when test=".=AUT">AUT</xsl:when><!-- 奥地利 -->
		<xsl:when test=".=AUS">AUS</xsl:when><!-- 澳大利亚 -->
		<xsl:when test=".=MAC">MO</xsl:when><!-- 澳门 -->
		<xsl:when test=".=BRB">BAR</xsl:when><!-- 巴巴多斯 -->
		<xsl:when test=".=PNG">PNG</xsl:when><!-- 巴布亚新几内亚 -->
		<xsl:when test=".=PRY">PAR</xsl:when><!-- 巴拉圭 -->
		<xsl:when test=".=PSE">PLE</xsl:when><!-- 巴勒斯坦 -->
		<xsl:when test=".=BHR">BRN</xsl:when><!-- 巴林 -->
		<xsl:when test=".=PAN">PAN</xsl:when><!-- 巴拿马 -->
		<xsl:when test=".=BRA">BAS</xsl:when><!-- 巴西 -->
		<xsl:when test=".=BLR">BLR</xsl:when><!-- 白俄罗斯 -->
		<xsl:when test=".=BMU">BER</xsl:when><!-- 百慕大 -->
		<xsl:when test=".=BGR">BUL</xsl:when><!-- 保加利亚 -->
		<xsl:when test=".=BEL">BEL</xsl:when><!-- 比利时 -->
		<xsl:when test=".=ISL">ISL</xsl:when><!-- 冰岛 -->
		<xsl:when test=".=POL">PL</xsl:when><!-- 波兰 -->
		<xsl:when test=".=BOL">BOL</xsl:when><!-- 玻利维亚 -->
		<xsl:when test=".=BIH">BIH</xsl:when><!-- 波斯尼亚和黑塞哥维那 -->
		<xsl:when test=".=BWA">BOT</xsl:when><!-- 博茨瓦纳 -->
		<xsl:when test=".=DNK">DEN</xsl:when><!-- 丹麦 -->
		<xsl:when test=".=DEU">DEU</xsl:when><!-- 德国 -->
		<xsl:when test=".=DOM">DOM</xsl:when><!-- 多米尼加共和国 -->
		<xsl:when test=".=RUS">RUS</xsl:when><!-- 俄罗斯 -->
		<xsl:when test=".=ECU">ECU</xsl:when><!-- 厄瓜多尔 -->
		<xsl:when test=".=FRA">FRA</xsl:when><!-- 法国 -->
		<xsl:when test=".=FRO">FAI</xsl:when><!-- 法罗群岛 -->
		<xsl:when test=".=PHL">PHL</xsl:when><!-- 菲律宾 -->
		<xsl:when test=".=FIN">FIN</xsl:when><!-- 芬兰 -->
		<xsl:when test=".=COL">COL</xsl:when><!-- 哥伦比亚 -->
		<xsl:when test=".=CRI">CRC</xsl:when><!-- 哥斯达黎加 -->
		<xsl:when test=".=GEO">GEO</xsl:when><!-- 格鲁吉亚 -->
		<xsl:when test=".=CUB">CUB</xsl:when><!-- 古巴 -->
		<xsl:when test=".=KAZ">KAZ</xsl:when><!-- 哈萨克斯坦 -->
		<xsl:when test=".=KOR">KOR</xsl:when><!-- 韩国 -->
		<xsl:when test=".=NLD">NL</xsl:when><!-- 荷兰 -->
		<xsl:when test=".=ANT">AHO</xsl:when><!-- 荷属安的列斯 -->
		<xsl:when test=".=HND">HON</xsl:when><!-- 洪都拉斯 -->
		<xsl:when test=".=CAN">CAN</xsl:when><!-- 加拿大 -->
		<xsl:when test=".=GHA">DG</xsl:when><!-- 加纳 -->
		<xsl:when test=".=CZE">CZE</xsl:when><!-- 捷克 -->
		<xsl:when test=".=ZWE">ZIM</xsl:when><!-- 津巴布韦 -->
		<xsl:when test=".=QAT">QAT</xsl:when><!-- 卡塔尔 -->
		<xsl:when test=".=HRV">CRO</xsl:when><!-- 克罗地亚 -->
		<xsl:when test=".=KEN">KEN</xsl:when><!-- 肯尼亚 -->
		<xsl:when test=".=LVA">LAT</xsl:when><!-- 拉脱维亚 -->
		<xsl:when test=".=LBN">LIB</xsl:when><!-- 黎巴嫩 -->
		<xsl:when test=".=LBY">LBA</xsl:when><!-- 利比亚 -->
		<xsl:when test=".=LTU">LTU</xsl:when><!-- 立陶宛 -->
		<xsl:when test=".=LIE">LIE</xsl:when><!-- 列支敦士登 -->
		<xsl:when test=".=LUX">LUX</xsl:when><!-- 卢森堡 -->
		<xsl:when test=".=RWA">RWA</xsl:when><!-- 卢旺达 -->
		<xsl:when test=".=ROM">ROM</xsl:when><!-- 罗马尼亚 -->
		<xsl:when test=".=MWI">MAW</xsl:when><!-- 马拉维 -->
		<xsl:when test=".=MYS">MY</xsl:when><!-- 马来西亚 -->
		<xsl:when test=".=MKD">MKD</xsl:when><!-- 马其顿 -->
		<xsl:when test=".=MUS">MRI</xsl:when><!-- 毛里求斯 -->
		<xsl:when test=".=USA">USA</xsl:when><!-- 美国 -->
		<xsl:when test=".=MNG">MGL</xsl:when><!-- 蒙古 -->
		<xsl:when test=".=BGD">BAN</xsl:when><!-- 孟加拉国 -->
		<xsl:when test=".=PER">PER</xsl:when><!-- 秘鲁 -->
		<xsl:when test=".=MAR">MAR</xsl:when><!-- 摩洛哥 -->
		<xsl:when test=".=MCO">MNC</xsl:when><!-- 摩纳哥 -->
		<xsl:when test=".=MEX">MEX</xsl:when><!-- 墨西哥 -->
		<xsl:when test=".=NAM">NAM</xsl:when><!-- 纳米比亚 -->
		<xsl:when test=".=ZAF">SFA</xsl:when><!-- 南非 -->
		<xsl:when test=".=NIC">NCA</xsl:when><!-- 尼加拉瓜 -->
		<xsl:when test=".=NGA">NGR</xsl:when><!-- 尼日利亚 -->
		<xsl:when test=".=NOR">NO</xsl:when><!-- 挪威 -->
		<xsl:when test=".=PRT">POR</xsl:when><!-- 葡萄牙 -->
		<xsl:when test=".=JPN">JAN</xsl:when><!-- 日本 -->
		<xsl:when test=".=SWE">SWE</xsl:when><!-- 瑞典 -->
		<xsl:when test=".=CHE">SUI</xsl:when><!-- 瑞士 -->
		<xsl:when test=".=CYP">CYP</xsl:when><!-- 塞浦路斯 -->
		<xsl:when test=".=SYC">SEY</xsl:when><!-- 塞舌尔 -->
		<xsl:when test=".=SAU">UAE</xsl:when><!-- 沙特阿拉伯 -->
		<xsl:when test=".=SMR">SMR</xsl:when><!-- 圣马力诺 -->
		<xsl:when test=".=LKA">SLK</xsl:when><!-- 斯里兰卡 -->
		<xsl:when test=".=SVK">SVK</xsl:when><!-- 斯洛伐克 -->
		<xsl:when test=".=SVN">SLO</xsl:when><!-- 斯洛文尼亚 -->
		<xsl:when test=".=SDN">SUD</xsl:when><!-- 苏丹 -->
		<xsl:when test=".=SUR">SUR</xsl:when><!-- 苏里南 -->
		<xsl:when test=".=SOM">SOM</xsl:when><!-- 索马里 -->
		<xsl:when test=".=TJK">KGZ</xsl:when><!-- 塔吉克斯坦 -->
		<xsl:when test=".=THA">THA</xsl:when><!-- 泰国 -->
		<xsl:when test=".=TON">TO</xsl:when><!-- 汤加 -->
		<xsl:when test=".=TTO">TRI</xsl:when><!-- 特立尼达和多巴哥 -->
		<xsl:when test=".=TUN">TUN</xsl:when><!-- 突尼斯 -->
		<xsl:when test=".=TUR">TUR</xsl:when><!-- 土耳其 -->
		<xsl:when test=".=TKM">TKM</xsl:when><!-- 土库曼斯坦 -->
		<xsl:when test=".=VEN">VEN</xsl:when><!-- 委内瑞拉 -->
		<xsl:when test=".=BRN">BRU</xsl:when><!-- 文莱 -->
		<xsl:when test=".=UGA">UGA</xsl:when><!-- 乌干达 -->
		<xsl:when test=".=UKR">UKR</xsl:when><!-- 乌克兰 -->
		<xsl:when test=".=URY">URU</xsl:when><!-- 乌拉圭 -->
		<xsl:when test=".=UZB">UZB</xsl:when><!-- 乌兹别克斯坦 -->
		<xsl:when test=".=ESP">ESP</xsl:when><!-- 西班牙 -->
		<xsl:when test=".=GRC">GRE</xsl:when><!-- 希腊 -->
		<xsl:when test=".=HKG">HK</xsl:when><!-- 香港 -->
		<xsl:when test=".=SGP">SG</xsl:when><!-- 新加坡 -->
		<xsl:when test=".=NZL">NZL</xsl:when><!-- 新西兰 -->
		<xsl:when test=".=HUN">HUN</xsl:when><!-- 匈牙利 -->
		<xsl:when test=".=SYR">SYR</xsl:when><!-- 叙利亚 -->
		<xsl:when test=".=JAM">JAM</xsl:when><!-- 牙买加 -->
		<xsl:when test=".=ARM">ARM</xsl:when><!-- 亚美尼亚 -->
		<xsl:when test=".=YEM">YEM</xsl:when><!-- 也门 -->
		<xsl:when test=".=IRQ">IRQ</xsl:when><!-- 伊拉克 -->
		<xsl:when test=".=IRN">IRI</xsl:when><!-- 伊朗 -->
		<xsl:when test=".=ISR">ISR</xsl:when><!-- 以色列 -->
		<xsl:when test=".=ITA">ITA</xsl:when><!-- 意大利 -->
		<xsl:when test=".=IND">IND</xsl:when><!-- 印度 -->
		<xsl:when test=".=IDN">INA</xsl:when><!-- 印度尼西亚 -->
		<xsl:when test=".=GBR">ENG</xsl:when><!-- 英国 -->
		<xsl:when test=".=VGB">IVB</xsl:when><!-- 英属维尔京群岛 -->
		<xsl:when test=".=VNM">VIE</xsl:when><!-- 越南 -->
		<xsl:when test=".=ZMB">ZAM</xsl:when><!-- 赞比亚 -->
		<xsl:when test=".=CHL">CHI</xsl:when><!-- 智利 -->
		<xsl:when test=".=CHN">CHN</xsl:when><!-- 中国 -->
		<xsl:when test=".=TWN">TW</xsl:when><!-- 中国台湾 -->
		<xsl:otherwise>OTH</xsl:otherwise><!-- 其他 -->
	</xsl:choose>
</xsl:template>

<!-- 婚否 -->
<xsl:template name="tran_maritalstatus" match="marriage">
	<xsl:choose>
		<xsl:when test=".=02 or 03 or 04">0</xsl:when><!-- 无配偶(未婚 、离异、丧偶) -->
		<xsl:when test=".=01 ">1</xsl:when><!-- 有配偶(已婚) -->
		<xsl:otherwise></xsl:otherwise>
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>