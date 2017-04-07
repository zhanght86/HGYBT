<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:java="http://xml.apache.org/xslt/java"
 	exclude-result-prefixes="java">
<xsl:template match="InsuReq">
<TranData>
   <Head>
      <TranDate><xsl:value-of select="Main/TranDate"/></TranDate>
      <TranTime><xsl:value-of select="Main/TranTime"/></TranTime>
      <TranCom><xsl:value-of select="Head/TranCom"/></TranCom>
      <NodeNo><xsl:value-of select="Main/BrNo"/></NodeNo>
      <BankCode><xsl:value-of select="Head/BankCode"/></BankCode>
      <TellerNo><xsl:value-of select="Main/TellerNo"/></TellerNo>
      <TranNo><xsl:value-of select="Main/TransNo"/></TranNo>
      <ZoneNo><xsl:value-of select="Main/ZoneNo"/></ZoneNo>
      <FuncFlag><xsl:value-of select="Head/FuncFlag"/></FuncFlag>
      <AgentCom />
      <AgentCode />
      <InNoDoc><xsl:value-of select="Head/InNoDoc"/></InNoDoc>
      <ClientIp><xsl:value-of select="Head/ClientIp"/></ClientIp>
   </Head>
   <Body>
   	  <!-- 销售渠道 -->
      <SaleChannel>
      <xsl:call-template name="tran_Channel">
        <xsl:with-param name="Channel">
		  <xsl:value-of select="Main/Channel"/>
	    </xsl:with-param>
	  </xsl:call-template>
      </SaleChannel>
      <!-- 投保单号 -->
      <ProposalPrtNo>
        <xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.no13To15(Main/ApplyNo)"/>
      </ProposalPrtNo>
      <!-- 保单号 -->
      <ContPrtNo />
      <!-- 投保日期 -->
      <PolApplyDate><xsl:value-of select="Main/ApplyDate"/></PolApplyDate>
      <!-- 保单递送方式 -->
      <GetPolMode>1</GetPolMode>
      <!-- 健康告知 -->
      <HealthNotice><xsl:value-of select="Risks/Appendix/HealthFlag"/></HealthNotice>
      <!-- 银行账户 -->
      <AccNo><xsl:value-of select="Risks/Appendix/PayAcc"/></AccNo>
      <!-- 账户姓名 -->
      <AccName><xsl:value-of select="Risks/Appendix/PayAccName"/></AccName>
      <!-- 投保人信息 -->
      <Appnt>
      	 <!-- 投保人姓名 -->
         <Name><xsl:value-of select="Appnt/Name"/></Name>
         <!-- 投保人性别 -->
         <Sex>
            <xsl:call-template name="tran_sex">
			  <xsl:with-param name="Sex">
				<xsl:value-of select="Appnt/Sex"/>
			  </xsl:with-param>
			</xsl:call-template>
         </Sex>
         <!-- 投保人出生日期-->
         <Birthday><xsl:value-of select="Appnt/Birthday"/></Birthday>
         <!-- 投保人证件类型 -->
         <IDType>
           <xsl:call-template name="tran_idtype">
			  <xsl:with-param name="IDType">
				<xsl:value-of select="Appnt/IDType"/>
			  </xsl:with-param>
			</xsl:call-template>
		 </IDType>
		 <!-- 投保人证件号码 -->
         <IDNo><xsl:value-of select="Appnt/IDNo"/></IDNo>
         <!-- 投保人职业代码(需转换)-->
         <JobCode>3010101</JobCode>
         <!-- 投保人国籍 -->
         <Nationality>
         	<xsl:call-template name="ApplCountry">
				<xsl:with-param name="Country">
					<xsl:value-of select="Appnt/Nationality"/>
				</xsl:with-param>
			</xsl:call-template>
         </Nationality>
         <!-- 投保人身高 -->
         <Stature></Stature>
         <!-- 投保人体重 -->
         <Weight></Weight>
         <!-- 婚否(N/Y) 空值 -->
         <MaritalStatus></MaritalStatus>
         <!-- 投保人年收入 (万元)-->
         <YearSalary><xsl:value-of select="Appnt/Income div 10000"/></YearSalary>
         <!-- 投保人证件有效期 -->
         <IdExpDate><xsl:value-of select="Appnt/IDEndDate"/></IdExpDate>
         <!-- 投保人地址 -->
         <Address><xsl:value-of select="Appnt/HomeAddr"/></Address>
         <!-- 投保人邮编 -->
         <ZipCode><xsl:value-of select="Appnt/HomeZipCode"/></ZipCode>
         <!-- 投保人固定电话 -->
         <Phone><xsl:value-of select="Appnt/HomePhone"/></Phone>
         <!-- 投保人移动电话 -->
         <Mobile><xsl:value-of select="Appnt/MobilePhone"/></Mobile>
         <!-- 投保人电子邮箱 -->
         <Email><xsl:value-of select="Appnt/Email"/></Email>
         <!-- 投保人与被保人关系 -->
         <RelaToInsured>
           <xsl:call-template name="tran_relatoinsured">
			 <xsl:with-param name="RelaToInsured">
			   <xsl:value-of select="Appnt/RelaToInsured"/>
			 </xsl:with-param>
		   </xsl:call-template>
         </RelaToInsured>
         <!-- 投保人单位地址 -->
         <WorkAddress></WorkAddress>
         <!-- 投保人单位邮编 -->
         <WorkZipCode></WorkZipCode>
      </Appnt>
      <!-- 被保人信息 -->
      <Insured>
      	 <!-- 被保人姓名 -->
         <Name><xsl:value-of select="Insured/Name"/></Name>
         <!-- 被保人性别 -->
         <Sex>
           <xsl:call-template name="tran_sex">
			 <xsl:with-param name="Sex">
			   <xsl:value-of select="Insured/Sex"/>
			 </xsl:with-param>
		   </xsl:call-template>
         </Sex>
         <!--被保人出生日期  -->
         <Birthday><xsl:value-of select="Insured/Birthday"/></Birthday>
         <!-- 被保人证件类型 -->
         <IDType>
           <xsl:call-template name="tran_idtype">
			 <xsl:with-param name="IDType">
			   <xsl:value-of select="Insured/IDType"/>
			 </xsl:with-param>
		   </xsl:call-template>
         </IDType>
         <!-- 被保人证件号码 -->
         <IDNo><xsl:value-of select="Insured/IDNo"/></IDNo>
         <!-- 被保人职业代码(需转换) -->
         <JobCode>3010101</JobCode>
         <!-- 被保人国籍(需转换) -->
         <Nationality>
         	<xsl:call-template name="ApplCountry">
				<xsl:with-param name="Country">
					<xsl:value-of select="Insured/Nationality"/>
				</xsl:with-param>
			</xsl:call-template>
		</Nationality>
         <!-- 被保人身高(cm) -->
         <Stature></Stature>
         <!--被保人体重(g) -->
         <Weight></Weight>
         <!-- 婚否(N/Y) 空值- -->
         <MaritalStatus></MaritalStatus>
         <!-- 被保人年收入(万元) -->
         <YearSalary></YearSalary>
         <!-- 被保人身份证证件有效期yyyyMMdd -->
         <IdExpDate><xsl:value-of select="Insured/IDEndDate"/></IdExpDate>
         <!-- 被保人地址 -->
         <Address><xsl:value-of select="Insured/HomeAddr"/></Address>
         <!-- 被保人邮编 -->
         <ZipCode><xsl:value-of select="Insured/HomeZipCode"/></ZipCode>
         <!-- 被保人固定电话 -->
         <Phone><xsl:value-of select="Insured/HomePhone"/></Phone>
         <!-- 被保人移动电话 -->
         <Mobile><xsl:value-of select="Insured/MobilePhone"/></Mobile>
         <!-- 被保人邮箱 -->
         <Email><xsl:value-of select="Insured/Email"/></Email>
      </Insured>
      <!-- 受益人信息 -->
      <xsl:for-each select="Bnfs/Bnf">
      <Bnf>
      	 <!-- 受益人类别(0生存，1身故，2红利) -->
         <Type>
           <xsl:call-template name="tran_Type">
			 <xsl:with-param name="Type">
			   <xsl:value-of select="Type"/>
			 </xsl:with-param>
		   </xsl:call-template>
         </Type>
         <!-- 受益顺序 -->
         <Grade><xsl:value-of select="Order"/></Grade>
         <!-- 受益人姓名 -->
         <Name><xsl:value-of select="Name"/></Name>
         <!-- 性别 -->
         <Sex>
           <xsl:call-template name="tran_sex">
			 <xsl:with-param name="Sex">
			   <xsl:value-of select="Sex"/>
			 </xsl:with-param>
		   </xsl:call-template>
         </Sex>
         <!-- 出生日期 -->
         <Birthday><xsl:value-of select="Birthday"/></Birthday>
         <!-- 证件类型 -->
         <IDType>
           <xsl:call-template name="tran_idtype">
			  <xsl:with-param name="IDType">
				<xsl:value-of select="IDType"/>
			  </xsl:with-param>
		   </xsl:call-template>
         </IDType>
         <!-- 证件号码 -->
         <IDNo><xsl:value-of select="IDNo"/></IDNo>
         <!-- 与被保人关系 -->
         <RelaToInsured>
           <xsl:call-template name="tran_relatoinsured">
			 <xsl:with-param name="RelaToInsured">
			   <xsl:value-of select="RelationToInsured"/>
			 </xsl:with-param>
		   </xsl:call-template>
         </RelaToInsured>
         <!-- 受益比例 -->
         <Lot><xsl:value-of select="Percent"/></Lot>
         <!--身份证证件有效期yyyyMMdd-  -->
         <IdExpDate><xsl:value-of select="IDEndDate"/></IdExpDate>
      </Bnf>
      </xsl:for-each>
      <!-- 险种信息 -->
      <xsl:variable name='MainCode'>
         <xsl:value-of select="Risks/Risk[MainSubFlag='1']/Code"/>
      </xsl:variable> 
      <xsl:for-each select="Risks/Risk">
      <Risk>
      	 <!-- 险种代码 -->
         <RiskCode><xsl:value-of select="Code"/></RiskCode>
         <!-- 主险险种代码 -->
         <MainRiskCode><xsl:value-of select="$MainCode"/></MainRiskCode>
         <!-- 保额（分） -->
         <Amnt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(InsuAmount)"/></Amnt>
         <!-- 保费（分） -->
         <Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.yuanToFen(Premium)"/></Prem>
         <!-- 投保份数 -->
         <Mult><xsl:value-of select="Unit"/></Mult>
         <!-- 缴费形式 -->
         <PayMode>B</PayMode>
         <!-- 缴费频次 -->
         <PayIntv>
	         <xsl:call-template name="tran_PayIntv">
			   <xsl:with-param name="PayIntv">
			     <xsl:value-of select="../Appendix/PayIntv"/>
			   </xsl:with-param>
			 </xsl:call-template>
         </PayIntv>
         <!-- 保险年期年龄标志 -->
         <InsuYearFlag>
	         <xsl:call-template name="tran_InsuYearFlag">
			   <xsl:with-param name="InsuYearFlag">
			     <xsl:value-of select="InsuYearFlag"/>
			   </xsl:with-param>
			 </xsl:call-template>
         </InsuYearFlag>
         <!-- 保险年期年龄 -->
         <InsuYear><xsl:value-of select="InsuYear"/></InsuYear>
         <xsl:if test="../Appendix/PayIntv='01'"><!-- 趸交1000Y给核心 -->				    
		   <PayEndYear>1000</PayEndYear><!-- 缴费年期年龄 -->
		   <PayEndYearFlag>Y</PayEndYearFlag><!-- 缴费年期年龄标志 -->
		 </xsl:if>
		 <xsl:if test="../Appendix/PayIntv!='01'">		    
		   <PayEndYear><xsl:value-of select="PayEndYear"/></PayEndYear>
           <PayEndYearFlag>
	           <xsl:call-template name="tran_PayEndYearFlag">
			     <xsl:with-param name="PayEndYearFlag">
			       <xsl:value-of select="PayEndYearFlag"/>
		   		 </xsl:with-param>
			   </xsl:call-template>
           </PayEndYearFlag>
		 </xsl:if>
		 <!-- 红利领取方式 -->
		 <BonusGetMode>
	         <xsl:call-template name="tran_BonusGetMode">
			   <xsl:with-param name="BonusGetMode">
			     <xsl:value-of select="../Appendix/BonusGetMode"/>
			   </xsl:with-param>
			 </xsl:call-template>
         </BonusGetMode>
		 <!-- 满期领取金领取方式 传空 -->
		 <FullBonusGetMode></FullBonusGetMode>
		 <!-- 领取年龄年期标志 -->
		 <GetYearFlag>
	         <xsl:call-template name="tran_GetYearFlag">
			   <xsl:with-param name="GetYearFlag">
			     <xsl:value-of select="../Appendix/GetYearFlag"/>
			   </xsl:with-param>
			 </xsl:call-template>
         </GetYearFlag>
		 <!-- 领取年龄 -->
		 <GetYear><xsl:value-of select="../Appendix/GetStartAge"/></GetYear>
		 <!-- 领取年期-->
		 <GetTerms><xsl:value-of select="../Appendix/GetYear"/></GetTerms>
		 <!--领取方式 传空  -->
		 <GetIntv />
		 <!-- 领取银行编码 传空 -->
		 <GetBankCode />
		 <!-- 领取银行账户 传空 -->
		 <GetBankAccNo />
		 <!-- 领取银行户名  传空-->
		 <GetAccName />
		 <!-- 自动垫交标志 传空 -->
         <AutoPayFlag />
      </Risk>
      </xsl:for-each>
   </Body>
</TranData>
</xsl:template>

<!--销售渠道-->
<xsl:template name="tran_Channel">
  <xsl:param name="Channel"></xsl:param>
  <xsl:choose>
  <xsl:when test="$Channel = '1'">0</xsl:when><!--柜面-->
  <xsl:when test="$Channel = '2'">07</xsl:when><!--网银 -->
   <xsl:when test="$Channel = '4'">06</xsl:when><!--自助终端 -->
  <xsl:otherwise></xsl:otherwise>  
  </xsl:choose>
</xsl:template>

<!-- 国籍 -->
<xsl:template name="ApplCountry">
	<xsl:param name="Country"></xsl:param>
	<xsl:choose>
		<xsl:when test="$Country='AU'">AUS</xsl:when>	<!-- 澳大利亚-->
		<xsl:when test="$Country='CN'">CHN</xsl:when>	<!-- 中国 -->
		<xsl:when test="$Country='US'">USA</xsl:when>	<!-- 美国 -->
		<xsl:when test="$Country='JP'">JAN</xsl:when>	<!-- 日本 -->
		<xsl:when test="$Country='GB'">ENG</xsl:when>	<!-- 英国 -->
		<xsl:when test="$Country='RU'">RUS</xsl:when>	<!-- 俄罗斯 -->
		<xsl:when test="$Country='TW'">TW</xsl:when>	<!-- 台湾 -->
		<xsl:when test="$Country='MO'">MO</xsl:when>	<!-- 澳门 -->
		<xsl:when test="$Country='HK'">HK</xsl:when>	<!-- 香港 -->
		<xsl:when test="$Country='PL'">PL</xsl:when>	<!-- 波兰 -->
		<xsl:when test="$Country='NL'">NL</xsl:when>	<!-- 荷兰 -->
		<xsl:when test="$Country='NO'">NO</xsl:when>	<!-- 挪威 -->
		<xsl:when test="$Country='SG'">SG</xsl:when>	<!-- 新加坡 -->
		<xsl:when test="$Country='GH'">DG</xsl:when>	<!-- 加纳 -->
		<xsl:when test="$Country='CA'">CAN</xsl:when>	<!-- 加拿大 -->
		<xsl:when test="$Country='DE'">DEU</xsl:when>	<!-- 德国 -->
		<xsl:when test="$Country='MY'">MY</xsl:when>	<!-- 马来西亚 -->
		<xsl:when test="$Country='IT'">ITA</xsl:when>	<!-- 意大利 -->
		<xsl:when test="$Country='02'">BOL</xsl:when>	<!--  玻利维亚-->
		<xsl:when test="$Country='NZ'">NZL</xsl:when>	<!-- 新西兰 -->
		<xsl:when test="$Country='PH'">PHL</xsl:when>	<!-- 菲律宾 -->
		<xsl:when test="$Country='TO'">TO</xsl:when>	<!-- 汤加 -->
		<xsl:when test="$Country='KR'">KOR</xsl:when>	<!-- 韩国 -->
		<xsl:when test="$Country='BR'">BAS</xsl:when>	<!-- 巴西 -->
		<xsl:when test="$Country='LK'">SLK</xsl:when>	<!-- 斯里兰卡 -->
		<xsl:when test="$Country='AF'">AFG</xsl:when>	<!--阿富汗  -->
		<xsl:when test="$Country='AL'">ALB</xsl:when>	<!-- 阿尔巴尼亚 -->
		<xsl:when test="$Country='DZ'">ALG</xsl:when>	<!-- 阿尔及利亚 -->
		<xsl:when test="$Country='AD'">AND</xsl:when>	<!-- 安道尔 -->
		<xsl:when test="$Country='AO'">ANG</xsl:when>	<!--  安哥拉-->
		<xsl:when test="$Country='AM'">ARM</xsl:when>	<!-- 亚美尼亚 -->
		<xsl:when test="$Country='AR'">ARG</xsl:when>	<!-- 阿根廷 -->
		<xsl:when test="$Country='AW'">ARU</xsl:when>	<!-- 阿鲁巴岛 -->
		<xsl:when test="$Country='AT'">AUT</xsl:when>	<!-- 奥地利 -->
		<xsl:when test="$Country='AZ'">AZE</xsl:when>	<!-- 阿塞拜疆 -->
		<xsl:when test="$Country='BD'">BAN</xsl:when>	<!-- 孟加拉国 -->
		<xsl:when test="$Country='BB'">BAR</xsl:when>	<!--巴巴多斯  -->
		<xsl:when test="$Country='BH'">BRN</xsl:when>	<!-- 巴林 -->
		<xsl:when test="$Country='BY'">BLR</xsl:when>	<!-- 白俄罗斯 -->
		<xsl:when test="$Country='BE'">BEL</xsl:when>	<!--  比利时-->
		<xsl:when test="$Country='BM'">BER</xsl:when>	<!-- 百慕大 -->
		<xsl:when test="$Country='BW'">BOT</xsl:when>	<!-- 博茨瓦纳 -->
		<xsl:when test="$Country='BN'">BRU</xsl:when>	<!-- 文莱 -->
		<xsl:when test="$Country='VG'">IVB</xsl:when>	<!-- 英属维尔京群岛 -->
		<xsl:when test="$Country='01'">BUL</xsl:when>	<!-- 保加利亚 -->
		<xsl:when test="$Country='05'">CHI</xsl:when>	<!-- 智利 -->
		<xsl:when test="$Country='CO'">COL</xsl:when>	<!-- 哥伦比亚 -->
		<xsl:when test="$Country='CR'">CRC</xsl:when>	<!-- 哥斯达黎加 -->
		<xsl:when test="$Country='HR'">CRO</xsl:when>	<!-- 克罗地亚 -->
		<xsl:when test="$Country='CU'">CUB</xsl:when>	<!-- 古巴 -->
		<xsl:when test="$Country='CY'">CYP</xsl:when>	<!-- 塞浦路斯 -->
		<xsl:when test="$Country='CZ'">CZE</xsl:when>	<!-- 捷克共和国 -->
		<xsl:when test="$Country='DK'">DEN</xsl:when>	<!-- 丹麦 -->
		<xsl:when test="$Country='DO'">DOM</xsl:when>	<!--多米尼加  -->
		<xsl:when test="$Country='EG'">EGY</xsl:when>	<!-- 埃及 -->
		<xsl:when test="$Country='EC'">ECU</xsl:when>	<!-- 厄瓜多尔 -->
		<xsl:when test="$Country='EE'">EST</xsl:when>	<!-- 爱沙尼亚 -->
		<xsl:when test="$Country='ET'">ETH</xsl:when>	<!-- 埃塞俄比亚 -->
		<xsl:when test="$Country='FO'">FAI</xsl:when>	<!-- 法罗群岛 -->
		<xsl:when test="$Country='FI'">FIN</xsl:when>	<!-- 芬兰 -->
		<xsl:when test="$Country='FR'">FRA</xsl:when>	<!--法国  -->
		<xsl:when test="$Country='MK'">MKD</xsl:when>	<!-- 马其顿 -->
		<xsl:when test="$Country='GE'">GEO</xsl:when>	<!-- 格鲁吉亚 -->
		<xsl:when test="$Country='GR'">GRE</xsl:when>	<!-- 希腊 -->
		<xsl:when test="$Country='HN'">HON</xsl:when>	<!-- 洪都拉斯 -->
		<xsl:when test="$Country='HU'">HUN</xsl:when>	<!-- 匈牙利 -->
		<xsl:when test="$Country='IN'">IND</xsl:when>	<!-- 印度 -->
		<xsl:when test="$Country='IE'">IRL</xsl:when>	<!--爱尔兰 -->
		<xsl:when test="$Country='IQ'">IRQ</xsl:when>	<!-- 伊拉克 -->
		<xsl:when test="$Country='IR'">IRI</xsl:when>	<!-- 伊朗 -->
		<xsl:when test="$Country='IS'">ISL</xsl:when>	<!--冰岛  -->
		<xsl:when test="$Country='ID'">INA</xsl:when>	<!-- 印度尼西亚 -->
		<xsl:when test="$Country='IL'">ISR</xsl:when>	<!-- 以色列 -->
		<xsl:when test="$Country='JM'">JAM</xsl:when>	<!-- 牙买加 -->
		<xsl:when test="$Country='JE'">JCI</xsl:when>	<!-- 泽西岛 -->
		<xsl:when test="$Country='KZ'">KAZ</xsl:when>	<!-- 哈萨克斯坦 -->
		<xsl:when test="$Country='KE'">KEN</xsl:when>	<!-- 肯尼亚 -->
		<xsl:when test="$Country='TJ'">KGZ</xsl:when>	<!--塔吉克斯坦  -->
		<xsl:when test="$Country='LV'">LAT</xsl:when>	<!--拉脱维亚  -->
		<xsl:when test="$Country='LB'">LIB</xsl:when>	<!-- 黎巴嫩 -->
		<xsl:when test="$Country='LY'">LBA</xsl:when>	<!-- 利比亚 -->
		<xsl:when test="$Country='LI'">LIE</xsl:when>	<!--列支敦士登  -->
		<xsl:when test="$Country='LT'">LTU</xsl:when>	<!-- 立陶宛 -->
		<xsl:when test="$Country='LU'">LUX</xsl:when>	<!-- 卢森堡 -->
		<xsl:when test="$Country='MW'">MAW</xsl:when>	<!-- 马拉维 -->
		<xsl:when test="$Country='MT'">MLT</xsl:when>	<!-- 马尔他 -->
		<xsl:when test="$Country='ZZ'">OTH</xsl:when>	<!-- 其他 -->
		<xsl:when test="$Country='MU'">MRI</xsl:when>	<!-- 毛利求斯 -->
		<xsl:when test="$Country='MX'">MEX</xsl:when>	<!-- 墨西哥 -->
		<xsl:when test="$Country='MC'">MNC</xsl:when>	<!-- 摩纳哥 -->
		<xsl:when test="$Country='MN'">MGL</xsl:when>	<!-- 蒙古 -->
		<xsl:when test="$Country='MA'">MAR</xsl:when>	<!-- 摩洛哥 -->
		<xsl:when test="$Country='NA'">NAM</xsl:when>	<!--纳米比亚  -->
		<xsl:when test="$Country='AN'">AHO</xsl:when>	<!-- 荷属安的列斯 -->
		<xsl:when test="$Country='NI'">NCA</xsl:when>	<!--尼加拉瓜 -->
		<xsl:when test="$Country='NG'">NGR</xsl:when>	<!--尼日利亚  -->
		<xsl:when test="$Country='PS'">PLE</xsl:when>	<!--巴勒斯坦  -->
		<xsl:when test="$Country='PA'">PAN</xsl:when>	<!-- 巴拿马 -->
		<xsl:when test="$Country='PG'">PNG</xsl:when>	<!-- 巴布亚新几内亚 -->
		<xsl:when test="$Country='PY'">PAR</xsl:when>	<!-- 巴拉圭 -->
		<xsl:when test="$Country='PE'">PER</xsl:when>	<!-- 秘鲁 -->
		<xsl:when test="$Country='PT'">POR</xsl:when>	<!-- 葡萄牙 -->
		<xsl:when test="$Country='QA'">QAT</xsl:when>	<!--卡塔尔  -->
		<xsl:when test="$Country='RO'">ROM</xsl:when>	<!-- 罗马尼亚 -->
		<xsl:when test="$Country='RW'">RWA</xsl:when>	<!-- 卢旺达 -->
		<xsl:when test="$Country='SM'">SMR</xsl:when>	<!-- 圣马力诺 -->
		<xsl:when test="$Country='SC'">SEY</xsl:when>	<!-- 塞舌尔 -->
		<xsl:when test="$Country='SK'">SVK</xsl:when>	<!-- 斯洛伐克 -->
		<xsl:when test="$Country='SI'">SLO</xsl:when>	<!-- 斯洛文尼亚 -->
		<xsl:when test="$Country='SO'">SOM</xsl:when>	<!-- 索马里 -->
		<xsl:when test="$Country='ES'">ESP</xsl:when>	<!-- 西班牙 -->
		<xsl:when test="$Country='SD'">SUD</xsl:when>	<!-- 苏丹 -->
		<xsl:when test="$Country='SR'">SUR</xsl:when>	<!-- 苏里南 -->
		<xsl:when test="$Country='SE'">SWE</xsl:when>	<!-- 瑞典 -->
		<xsl:when test="$Country='CH'">SUI</xsl:when>	<!-- 瑞士 -->
		<xsl:when test="$Country='SY'">SYR</xsl:when>	<!-- 叙利亚 -->
		<xsl:when test="$Country='TJ'">TJK</xsl:when>	<!-- 塔吉克斯坦 -->
		<xsl:when test="$Country='TH'">THA</xsl:when>	<!-- 泰国 -->
		<xsl:when test="$Country='TT'">TRI</xsl:when>	<!-- 特立尼达和多巴哥 -->
		<xsl:when test="$Country='TN'">TUN</xsl:when>	<!-- 突尼斯 -->
		<xsl:when test="$Country='TR'">TUR</xsl:when>	<!-- 土尔其 -->
		<xsl:when test="$Country='TM'">TKM</xsl:when>	<!-- 土库曼斯坦 -->
		<xsl:when test="$Country='UG'">UGA</xsl:when>	<!-- 乌干达 -->
		<xsl:when test="$Country='ZA'">SFA</xsl:when>	<!--南非  -->
		<xsl:when test="$Country='UA'">UKR</xsl:when>	<!--乌克兰  -->
		<xsl:when test="$Country='UY'">URU</xsl:when>	<!-- 乌拉圭 -->
		<xsl:when test="$Country='VI'">ISV</xsl:when>	<!-- 美属维京群岛 -->
		<xsl:when test="$Country='UZ'">UZB</xsl:when>	<!-- 乌兹别克斯坦 -->
		<xsl:when test="$Country='VE'">VEN</xsl:when>	<!--委内瑞拉  -->
		<xsl:when test="$Country='VN'">VIE</xsl:when>	<!-- 越南 -->
		<xsl:when test="$Country='YE'">YEM</xsl:when>	<!-- 也门 -->
		<xsl:when test="$Country='YU'">YUG</xsl:when>	<!--南斯拉夫  -->
		<xsl:when test="$Country='ZM'">ZAM</xsl:when>	<!-- 赞比亚 -->
		<xsl:when test="$Country='ZW'">ZIM</xsl:when>	<!-- 津巴布韦 -->
		<xsl:otherwise>OTH</xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!--性别转换-->
<xsl:template name="tran_sex">
  <xsl:param name="Sex"></xsl:param>
  <xsl:choose>
  <xsl:when test="$Sex = '1'">0</xsl:when><!-- 男-->
  <xsl:when test="$Sex = '0'">1</xsl:when><!-- 女 -->
  <xsl:otherwise></xsl:otherwise>  
  </xsl:choose>
</xsl:template>

<!-- 证件类型     -->
<xsl:template name="tran_idtype"><!--证件类型-->
  <xsl:param name="IDType"></xsl:param>
  <xsl:choose>
  <xsl:when test="$IDType = '01'">0</xsl:when><!-- 身份证 -->
  <xsl:when test="$IDType = '02'">C</xsl:when> <!-- 临时身份证 -->
  <xsl:when test="$IDType = '03'">1</xsl:when><!--护照 -->
  <xsl:when test="$IDType = '04'">4</xsl:when><!-- 户口本 -->
  <xsl:when test="$IDType = '05'">2</xsl:when><!--军官证 -->
  <xsl:when test="$IDType = '06'">D</xsl:when><!-- 警官证 -->
  <xsl:when test="$IDType = '08'">8</xsl:when><!--外交人员身份证-->
  <xsl:when test="$IDType = '09'">8</xsl:when><!-- 外国人居留许可证 -->
  <xsl:when test="$IDType = '10'">8</xsl:when><!-- 边民出入境通行证 -->
  <xsl:when test="$IDType = '11'">8</xsl:when><!-- 其他 -->
  <xsl:when test="$IDType = '47'">F</xsl:when><!-- 港澳居民来往内地通行证（香港） -->
  <xsl:when test="$IDType = '48'">F</xsl:when><!-- 港澳居民来往内地通行证（澳门） -->
  <xsl:when test="$IDType = '49'">F</xsl:when><!-- 台湾居民来往大陆通行证 -->
  <xsl:otherwise></xsl:otherwise> 
  </xsl:choose>
</xsl:template>

<!--投被保人关系 -->
<xsl:template name="tran_relatoinsured">
  <xsl:param name="RelaToInsured"></xsl:param>
  <xsl:choose>
  <xsl:when test="$RelaToInsured = '01'">00</xsl:when><!--本人 -->
  <xsl:when test="$RelaToInsured = '02'">02</xsl:when> <!--配偶-->
  <xsl:when test="$RelaToInsured = '03'">02</xsl:when><!--配偶 -->
  <xsl:when test="$RelaToInsured = '04'">01</xsl:when><!--父母 -->
  <xsl:when test="$RelaToInsured = '05'">01</xsl:when><!--父母 -->
  <xsl:when test="$RelaToInsured = '06'">03</xsl:when><!--子女 -->
  <xsl:when test="$RelaToInsured = '07'">03</xsl:when><!--子女-->
  <xsl:when test="$RelaToInsured = '08'">04</xsl:when><!-- 祖父祖母、孙子女 -->
  <xsl:when test="$RelaToInsured = '09'">04</xsl:when><!-- 祖父祖母、孙子女 -->
  <xsl:when test="$RelaToInsured = '10'">04</xsl:when><!-- 祖父祖母、孙子女 -->
  <xsl:when test="$RelaToInsured = '11'">04</xsl:when><!-- 祖父祖母、孙子女 -->
  <xsl:otherwise>06</xsl:otherwise> 
  </xsl:choose>
</xsl:template>

<!--受益人类型 -->
<xsl:template name="tran_Type">
  <xsl:param name="Type"></xsl:param>
  <xsl:choose>
  <xsl:when test="$Type = '1'">1</xsl:when><!--身故 -->
  <xsl:when test="$Type = '2'">0</xsl:when> <!--生存-->
  <xsl:when test="$Type = '3'">2</xsl:when><!--红利  -->
  <xsl:otherwise></xsl:otherwise> 
  </xsl:choose>
</xsl:template>

<!--缴费方式-->
<xsl:template name="tran_PayIntv">
  <xsl:param name="PayIntv"></xsl:param>
  <xsl:choose>
  <xsl:when test="$PayIntv = '01'">0</xsl:when><!--趸交 -->
  <xsl:when test="$PayIntv = '02'">12</xsl:when> <!--年交-->
  <xsl:when test="$PayIntv = '03'">6</xsl:when><!--半年交  -->
  <xsl:when test="$PayIntv = '04'">3</xsl:when><!--季交  -->
  <xsl:when test="$PayIntv = '05'">1</xsl:when><!--月交  -->
  <xsl:when test="$PayIntv = '06'">-1</xsl:when><!--不定期交  -->
  <xsl:otherwise></xsl:otherwise> 
  </xsl:choose>
</xsl:template>

<!--缴费年期年龄标志-->
<xsl:template name="tran_PayEndYearFlag">
  <xsl:param name="PayEndYearFlag"></xsl:param>
  <xsl:choose>
  <xsl:when test="$PayEndYearFlag = '00'"></xsl:when><!--无关 -->
  <xsl:when test="$PayEndYearFlag = '01'">A</xsl:when> <!--趸交-->
  <xsl:when test="$PayEndYearFlag = '02'">Y</xsl:when><!--年交  -->
  <xsl:when test="$PayEndYearFlag = '05'">M</xsl:when><!--月交  -->
  <xsl:when test="$PayEndYearFlag = '06'">D</xsl:when><!--日缴  -->
  <xsl:when test="$PayEndYearFlag = '07'">A</xsl:when><!--缴至某确定年龄  -->
  <xsl:otherwise></xsl:otherwise> 
  </xsl:choose>
</xsl:template>

<!--领取年期年龄标志-->
<xsl:template name="tran_GetYearFlag">
  <xsl:param name="GetYearFlag"></xsl:param>
  <xsl:choose>
  <xsl:when test="$GetYearFlag = '00'"></xsl:when><!--无关 -->
  <xsl:when test="$GetYearFlag = '01'"></xsl:when> <!--趸领-->
  <xsl:when test="$GetYearFlag = '02'">Y</xsl:when><!--年领  -->
  <xsl:when test="$GetYearFlag = '03'"></xsl:when><!--半年领  -->
  <xsl:when test="$GetYearFlag = '04'"></xsl:when><!--季领  -->
  <xsl:when test="$GetYearFlag = '05'">M</xsl:when><!--不定期交  -->
  <xsl:otherwise></xsl:otherwise> 
  </xsl:choose>
</xsl:template>

<!--红利领取方式-->
<xsl:template name="tran_BonusGetMode">
  <xsl:param name="BonusGetMode"></xsl:param>
  <xsl:choose>
  <xsl:when test="$BonusGetMode = '0'">4</xsl:when><!--无关 -->
  <xsl:when test="$BonusGetMode = '1'">1</xsl:when> <!--趸领-->
  <xsl:when test="$BonusGetMode = '2'">2</xsl:when><!--年领  -->
  <xsl:when test="$BonusGetMode = '3'">3</xsl:when><!--半年领  -->
  <xsl:when test="$BonusGetMode = '4'">5</xsl:when><!--季领  -->
  <xsl:otherwise></xsl:otherwise> 
  </xsl:choose>
</xsl:template>

<!--保险年期年龄标志-->
<xsl:template name="tran_InsuYearFlag">
  <xsl:param name="InsuYearFlag"></xsl:param>
  <xsl:choose>
  <xsl:when test="$InsuYearFlag = '01'">Y</xsl:when><!--年保 -->
  <xsl:when test="$InsuYearFlag = '03'">M</xsl:when> <!--月保-->
  <xsl:when test="$InsuYearFlag = '04'">D</xsl:when><!--日保 -->
  <xsl:when test="$InsuYearFlag = '05'">A</xsl:when><!--按年龄保  -->
  <xsl:when test="$InsuYearFlag = '06'">1000Y</xsl:when><!--保终身 -->
  <xsl:otherwise></xsl:otherwise> 
  </xsl:choose>
</xsl:template>
</xsl:stylesheet>
