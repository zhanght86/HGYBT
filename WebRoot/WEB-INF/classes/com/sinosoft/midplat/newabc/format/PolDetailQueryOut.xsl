<?xml version="1.0" encoding="GBK"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:java="http://xml.apache.org/xslt/java" exclude-result-prefixes="java">
<xsl:template match="TranData">
<ABCB2I>
	<xsl:apply-templates select="Body"/>
</ABCB2I> 
</xsl:template>
<xsl:template match="Body">
	<App> 
		<Ret>
			<!-- 保单号 -->
			<PolicyNo><xsl:value-of select="ContNo"/></PolicyNo>
			<!--保险公司险种代码  -->
			<RiskCode><xsl:value-of select="Risk/RiskCode"/></RiskCode>
			<!--险种名称  -->
			<RiskName><xsl:value-of select="Risk/RiskName"/></RiskName>
			<!--  保单状态-->
			<!-- <PolicyStatus><xsl:apply-templates select="PolicyStatus"/></PolicyStatus> -->
			<PolicyStatus>00</PolicyStatus>
			<!-- 保单质押状态 -->
			<PolicyPledge><xsl:value-of select="PolicyPledge"/></PolicyPledge>
			<!--  受理日期-->
			<AcceptDate><xsl:value-of select="(Risk[RiskCode=MainRiskCode])/SignDate"/></AcceptDate>
			<!--  保单生效日-->
		
		
			<PolicyBgnDate><xsl:value-of select="(Risk[RiskCode=MainRiskCode])/CValiDate"/></PolicyBgnDate>
			
			<!--  保单到期日-->
			<PolicyEndDate><xsl:value-of select="(Risk[RiskCode=MainRiskCode])/InsuEndDate"/></PolicyEndDate>
			<!-- 投保份数 -->
			<PolicyAmmount><xsl:value-of select="(Risk[RiskCode=MainRiskCode])/Mult"/></PolicyAmmount>
			<!--  保费-->
			<Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/></Amt>
			<!--  保额-->
			<Beamt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/></Beamt>
			<!-- 保单价值 -->
			<PolicyValue><xsl:value-of select="ContValue"/></PolicyValue>
	        <!-- 当前账户价值 --> 
	        <AccountValue><xsl:value-of select="AccountValue"/></AccountValue>
			<!--保险期间	--> 
			<InsuDueDate><xsl:value-of select="(Risk[RiskCode=MainRiskCode])/InsuYear"/>年</InsuDueDate>
			<!--缴费省市代码	-->
			<PayProv><xsl:value-of select="PayProv"/></PayProv>
			<!--缴费网点号	-->
			<PayBranch><xsl:value-of select="PayNodeNo"/></PayBranch>
			
			<!--缴费金额-->	
			<Prem><xsl:value-of select="PayPrem"/></Prem>
			<!--缴费账户	-->
			<PayAccount><xsl:value-of select="AccNo"/></PayAccount>
			<!-- 缴费方式 -->
			<PayType>
			<xsl:choose>
	        <xsl:when test="PayType =0">1</xsl:when>  
		    <xsl:when test="PayType =12">0</xsl:when>
		    <xsl:otherwise>--</xsl:otherwise> 
		   </xsl:choose>
			</PayType>
			<PayDue><xsl:value-of select="PayendYear"/>年</PayDue>
			
		    <!-- 投保人节点 -->	
		    <Appl>
	            <!-- 投保人证件类型 -->
		        <IDKind><xsl:apply-templates select="Appnt/IDType"/></IDKind>
				<!--投保人证件号码-->	
				<IDCode><xsl:value-of select="Appnt/IDNo"/></IDCode>
				<!--投保人名称	-->
				<Name><xsl:value-of select="Appnt/Name"/></Name>
				<!--投保人性别	-->
				<Sex><xsl:value-of select="Appnt/Sex"/></Sex>
				<!--投保人出生日期	-->
				<Birthday><xsl:value-of select="Appnt/Birthday"/></Birthday>
				<!--投保人通讯地址-->	
				<Address><xsl:value-of select="Appnt/Address"/></Address>            
				<!--投保人邮政编码-->	 
				<ZipCode><xsl:value-of select="Appnt/ZipCode"/></ZipCode>           
				<!--投保人电子邮箱-->	
			    <Email><xsl:value-of select="Appnt/Email"/></Email>             
				<!--投保人固定电话-->	 
				<Phone><xsl:value-of select="Appnt/Phone"/></Phone>              
				<!--投保人移动电话:-->	 
				<Mobile><xsl:value-of select="Appnt/Mobile"/></Mobile>
				<!-- 国籍 -->
			    <Country>
		<xsl:choose>
		<xsl:when test="Appnt/Nationality = 'CHN'">156</xsl:when> <!--中国                      -->
		<xsl:when test="Appnt/Nationality = 'HK'">344</xsl:when> <!--中国香港                  -->
		<xsl:when test="Appnt/Nationality ='TW'">158</xsl:when> <!--中国台湾                  -->
		<xsl:when test="Appnt/Nationality ='MO'">446</xsl:when> <!--中国澳门                  -->
		<xsl:when test="Appnt/Nationality ='JAN'">392</xsl:when> <!--日本                      -->
		<xsl:when test="Appnt/Nationality ='USA'">840</xsl:when> <!--美国                      -->
		<xsl:when test="Appnt/Nationality ='RUS'">643</xsl:when> <!--俄罗斯                    -->
		<xsl:when test="Appnt/Nationality ='ENG'">826</xsl:when> <!--英国                      -->
		<xsl:when test="Appnt/Nationality ='FRA'">250</xsl:when> <!--法国                      -->
		<xsl:when test="Appnt/Nationality ='DEU'">276</xsl:when> <!--德国                      -->
		<xsl:when test="Appnt/Nationality ='KOR'">410</xsl:when> <!--韩国                      -->
		<xsl:when test="Appnt/Nationality ='SG'">702</xsl:when> <!--新加坡                    -->
		<xsl:when test="Appnt/Nationality ='INA'">360</xsl:when> <!--印度尼西亚                -->
		<xsl:when test="Appnt/Nationality ='IND'">356</xsl:when> <!--印度                      -->
		<xsl:when test="Appnt/Nationality ='ITA'">380</xsl:when> <!--意大利                    -->
		<xsl:when test="Appnt/Nationality ='MY'">458</xsl:when> <!--马来西亚                  -->
		<xsl:when test="Appnt/Nationality ='THA'">764</xsl:when> <!--泰国                      -->
		<xsl:when test="Appnt/Nationality ='OTH'">999</xsl:when> <!--其他国家和地区            -->
	</xsl:choose>
				</Country>            
				<!--投保人年收入-->  
				<AnnualIncome></AnnualIncome>       
				<!--投保人与被保险人关系-->	
				
					<RelaToInsured>
						<xsl:call-template name="tran_relaApp">
					    	<xsl:with-param name="rela">
						    	<xsl:value-of select="Appnt/RelaToInsured"/>
						    </xsl:with-param>
						</xsl:call-template>						
					</RelaToInsured>
				
							
			</Appl>
			<Insu>
			    <!--被保人姓名--> 	 
			    <Name><xsl:value-of select="Insured/Name"/></Name>        
				<!--被保人性别:--> 	 
				<Sex><xsl:value-of select="Insured/Sex"/></Sex>         
				<!--被保人证件类型--> 	 
				<IDKind><xsl:apply-templates select="Insured/IDType"/></IDKind>  
				<!--被保人证件号码--> 	 
				<IDCode><xsl:value-of select="Insured/IDNo"/></IDCode>  
				<!--被保人出生日期--> 	
				<Birthday><xsl:value-of select="Insured/Birthday"/></Birthday>
			</Insu>	
			<Objt>
			    <!--保险房屋地址(省/直辖市)-->	  
			    <Prov><xsl:value-of select="Objt/Prov"/></Prov>
				<!--保险房屋地址(市)-->	   
				<City><xsl:value-of select="Objt/City"/></City>
				<!--保险房屋地址(区/县)-->	   
				<Zone><xsl:value-of select="Objt/Zone"/></Zone>
				<!--保险房屋地址(具体地址)-->	   
				<Address><xsl:value-of select="Objt/Address"/></Address>
				<!--房屋保险金额-->	  
				<Amnt><xsl:value-of select="Objt/Amnt"/></Amnt>
				<!--保险房屋邮编	-->   
				<ZipCode><xsl:value-of select="Objt/ZipCode"/></ZipCode>
				<!--保险房屋面积-->	   
				<Area><xsl:value-of select="Objt/Area"/></Area>
				<!--防盗抢设施-->	   
				<PreFlag><xsl:value-of select="Objt/PreFlag"/></PreFlag>
				<!--免赔率-->	  
				<NoPayPortion><xsl:value-of select="Objt/NoPayPortion"/></NoPayPortion>
				<!--免赔-->	  
				<NoPayPrem><xsl:value-of select="Objt/NoPayPrem"/></NoPayPrem>
				<!--保险房屋结构-->	  
				<Struts><xsl:value-of select="Objt/Struts"/></Struts>
				<!--保险房屋用途-->	  
				<Usage><xsl:value-of select="Objt/Usage"/></Usage>
				<!--固定资产分项保险金额-->	   
				<FasAmnt><xsl:value-of select="Objt/FasAmnt"/></FasAmnt>
				<!--流动资产分项保险金额-->	   
				<FloAmnt><xsl:value-of select="Objt/FloAmnt"/></FloAmnt>
				<!--保险目标个数-->	  
				<Count><xsl:value-of select="Objt/Count"/></Count>
				<!--保险目标名称1-->	   
				<Name1><xsl:value-of select="Objt/Name1"/></Name1>
				<!--保险目标金额1-->	  
				<Amnt1><xsl:value-of select="Objt/Amnt1"/></Amnt1>
				<!--保险目标名称2-->	   
				<Name2><xsl:value-of select="Objt/Name2"/></Name2>
				<!--保险目标金额2-->	  
				<Amnt2><xsl:value-of select="Objt/Amnt2"/></Amnt2>
				<Name3><xsl:value-of select="Objt/Name3"/></Name3><!--保险目标名称3-->
				<Amnt3><xsl:value-of select="Objt/Amnt3"/></Amnt3><!--保险目标金额3-->
				<Name4><xsl:value-of select="Objt/Name4"/></Name4><!--保险目标名称4-->
				<Amnt4><xsl:value-of select="Objt/Amnt4"/></Amnt4><!--保险目标金额4-->
				<Name5><xsl:value-of select="Objt/Name5"/></Name5><!--保险目标名称5-->
				<Amnt5><xsl:value-of select="Objt/Amnt5"/></Amnt5><!--保险目标金额5-->
				<Name6><xsl:value-of select="Objt/Name6"/></Name6><!--保险目标名称6-->
				<Amnt6><xsl:value-of select="Objt/Amnt6"/></Amnt6><!--保险目标金额6-->
				<Name7><xsl:value-of select="Objt/Name7"/></Name7><!--保险目标名称7-->
				<Amnt7><xsl:value-of select="Objt/Amnt7"/></Amnt7><!--保险目标金额7-->
			</Objt>	
			<xsl:for-each select="Bnfs/Bnf">
				<Bnfs>
			        <!--受益人个数-->	       
			        <Count><xsl:value-of select="/TranData/Body/Bnfs/Count"/></Count>    
					<!--受益人类型:-->	      
					<Type><xsl:value-of select="Type"/></Type> 
					<!--受益人1姓名-->	      
					<Name><xsl:value-of select="Name"/></Name>    
					<!--性别:-->	       
					<Sex><xsl:value-of select="Sex"/></Sex>           
					<!--出生日期:-->	       
					<Birthday><xsl:value-of select="Birthday"/></Birthday>  
					<!--证件类型:-->	       
					<IDKind><xsl:value-of select="IDType"/></IDKind>    
					<!--证件号码:-->	       
					<IDCode><xsl:value-of select="IDNo"/></IDCode>    
					<!--与被保人关系:-->	    
				
						<RelationToInsured>
							<xsl:call-template name="tran_relaApp">
						    	<xsl:with-param name="rela">
							    	<xsl:value-of select="RelaToInsured"/>
							    </xsl:with-param>
							</xsl:call-template>						
						</RelationToInsured>
				
									   
					<!--受益人1受益顺序-->	       
					<Sequence><xsl:value-of select="Grade"/></Sequence>
					<!--受益人1受益比例-->	       
					<Prop><xsl:value-of select="Lot"/></Prop>
			    </Bnfs>
		  	</xsl:for-each>
		</Ret>
	</App>
</xsl:template>
<!-- 证件类型-->
<xsl:template name="tran_idtype" match="IDType">
	<xsl:choose>
		<xsl:when test=".=0">110001</xsl:when>	<!-- 身份证 -->
		<xsl:when test=".=1">110023</xsl:when>  <!-- 中华人民共和国护照 -->
		<xsl:when test=".=2">110027</xsl:when>  <!-- 军官证 -->
		<xsl:when test=".=4">110005</xsl:when>  <!-- 户口簿 -->		
		<xsl:when test=".=5">110017</xsl:when>  <!-- 军事院校学员证 -->
		<xsl:when test=".=8">119999</xsl:when>  <!-- 其他个人证件识别标识 -->			
		<xsl:when test=".=A">110035</xsl:when>  <!-- 军人士兵证 -->
		<xsl:when test=".=C">110003</xsl:when>	<!-- 临时居民身份证 -->
		<xsl:when test=".=D">110031</xsl:when>  <!-- 警官证  -->
		<xsl:when test=".=E">110021</xsl:when>  <!-- 台湾居民往来大陆通行证 -->		
		<xsl:when test=".=F">110019</xsl:when>  <!-- 港澳居民往来内地通行证 -->
		<xsl:otherwise>119999</xsl:otherwise>  
	</xsl:choose>
</xsl:template>
<!-- 缴费方式 -->
<xsl:template name="payMode" match="PayType">
	<xsl:choose>
		<xsl:when test=".=0">1</xsl:when>	<!-- 趸交 -->
		<xsl:when test=".=1">0</xsl:when>	<!-- 期缴 -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>
<!-- 保单状态 -->
<xsl:template name="polStatus" match="PolicyStatus">
	<xsl:choose>
		<xsl:when test=".='0'">00</xsl:when>	<!-- 核心—有效/银行—有效 -->
		<xsl:when test=".='15'">00</xsl:when>	<!-- 核心—已缴费未签单/银行—有效 -->
		<xsl:when test=".='02'">01</xsl:when>	<!-- 核心—退保终止/银行—退保 -->
		<xsl:when test=".='06'">03</xsl:when>	<!-- 核心—犹退终止/银行—犹撤 -->
		<xsl:when test=".='04'">07</xsl:when>	<!-- 核心—理赔终止/银行—理赔终止 -->
		<xsl:when test=".='01'">04</xsl:when>	<!-- 核心—满期终止/银行—满期给付 -->
		<xsl:when test=".='14'">02</xsl:when>	<!-- 核心—非实时撤单终止/银行—当日撤单 -->		
		<xsl:when test=".='16'">08</xsl:when>   <!-- 核心—挂起/银行—处理中 20160301 Add -->
	</xsl:choose>
</xsl:template>
<!-- 关系代码转换-->
<xsl:template name="tran_relaApp">
	<xsl:param name="rela"/>
	<xsl:variable name="aSex" select="Appnt/Sex"></xsl:variable>
	<xsl:choose>
		<xsl:when test="$rela ='00'">01</xsl:when><!-- 银行-本人/核心-本人 -->
		<xsl:when test="$rela ='01' and $aSex='0'">04</xsl:when><!-- 银行-父亲/核心-本人 -->
		<xsl:when test="$rela ='01' and $aSex='1'">05</xsl:when><!-- 银行-母亲/核心-本人 -->
		<xsl:when test="$rela ='02' and $aSex='0'">02</xsl:when><!-- 银行-丈夫/核心-本人 -->
		<xsl:when test="$rela ='02' and $aSex='1'">03</xsl:when><!-- 银行-妻子/核心-本人 -->
		<xsl:when test="$rela ='03' and $aSex='0'">06</xsl:when><!-- 银行-儿子/核心-本人 -->		
		<xsl:when test="$rela ='03' and $aSex='1'">07</xsl:when><!-- 银行-女儿/核心-本人 -->
		<xsl:otherwise></xsl:otherwise>
	</xsl:choose>
</xsl:template>
<!-- 关系代码转换-->
<xsl:template name="tran_relaBnf">
	<xsl:param name="rela"/>
	<xsl:variable name="bSex" select="Sex"></xsl:variable>
	<xsl:choose>
		<xsl:when test="$rela ='00'">01</xsl:when><!-- 银行-本人/核心-本人 -->
		<xsl:when test="$rela ='01' and $bSex='0'">04</xsl:when><!-- 银行-父亲/核心-本人 -->
		<xsl:when test="$rela ='01' and $bSex='1'">05</xsl:when><!-- 银行-母亲/核心-本人 -->
		<xsl:when test="$rela ='02' and $bSex='0'">02</xsl:when><!-- 银行-丈夫/核心-本人 -->
		<xsl:when test="$rela ='02' and $bSex='1'">03</xsl:when><!-- 银行-妻子/核心-本人 -->
		<xsl:when test="$rela ='03' and $bSex='0'">06</xsl:when><!-- 银行-儿子/核心-本人 -->		
		<xsl:when test="$rela ='03' and $bSex='1'">07</xsl:when><!-- 银行-女儿/核心-本人 -->
		<xsl:otherwise></xsl:otherwise>
	</xsl:choose>
</xsl:template>
</xsl:stylesheet>