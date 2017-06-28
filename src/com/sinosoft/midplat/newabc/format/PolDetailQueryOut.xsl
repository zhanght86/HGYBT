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
			<!-- ������ -->
			<PolicyNo><xsl:value-of select="ContNo"/></PolicyNo>
			<!--���չ�˾���ִ���  -->
			<RiskCode><xsl:value-of select="Risk/RiskCode"/></RiskCode>
			<!--��������  -->
			<RiskName><xsl:value-of select="Risk/RiskName"/></RiskName>
			<!--  ����״̬-->
			<!-- <PolicyStatus><xsl:apply-templates select="PolicyStatus"/></PolicyStatus> -->
			<PolicyStatus>
				<xsl:call-template name="policyStatus">
						<xsl:with-param name="PolicyStatus">
							<xsl:value-of select="PolicyStatus"/>
						</xsl:with-param>
					</xsl:call-template>
			</PolicyStatus>
			<!-- ������Ѻ״̬ -->
			<PolicyPledge><xsl:value-of select="PolicyPledge"/></PolicyPledge>
			<!--  ��������-->
			<AcceptDate><xsl:value-of select="(Risk[RiskCode=MainRiskCode])/SignDate"/></AcceptDate>
			<!--  ������Ч��-->
			<PolicyBgnDate><xsl:value-of select="(Risk[RiskCode=MainRiskCode])/CValiDate"/></PolicyBgnDate>
			<!--  ����������-->
			<PolicyEndDate><xsl:value-of select="(Risk[RiskCode=MainRiskCode])/InsuEndDate"/></PolicyEndDate>
			<!-- Ͷ������ -->
			<PolicyAmmount><xsl:value-of select="(Risk[RiskCode=MainRiskCode])/Mult"/></PolicyAmmount>
			<!--  ����-->
			<Amt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Prem)"/></Amt>
			<!--  ����-->
			<Beamt><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(Amnt)"/></Beamt>
			<!-- ������ֵ -->
			<PolicyValue><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(ContValue)"/></PolicyValue>
	        <!-- ��ǰ�˻���ֵ --> 
	        <AccountValue><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(AccountValue)"/></AccountValue>
			<!--�����ڼ�	--> 
			<InsuDueDate>
				<xsl:choose>
					<xsl:when test="(Risk[RiskCode=MainRiskCode])/InsuYearFlag = 'A' and (Risk[RiskCode=MainRiskCode])/InsuYear=105"  >����</xsl:when>
					<xsl:when test="(Risk[RiskCode=MainRiskCode])/InsuYearFlag = 'A'"><xsl:value-of select="(Risk[RiskCode=MainRiskCode])/InsuYear"/>����</xsl:when>
					<xsl:when test="(Risk[RiskCode=MainRiskCode])/InsuYearFlag = 'Y'"><xsl:value-of select="(Risk[RiskCode=MainRiskCode])/InsuYear"/>��</xsl:when>
					<xsl:when test="(Risk[RiskCode=MainRiskCode])/InsuYearFlag = 'M'"><xsl:value-of select="(Risk[RiskCode=MainRiskCode])/InsuYear"/>��</xsl:when>
					<xsl:otherwise><xsl:value-of select="(Risk[RiskCode=MainRiskCode])/InsuYear"/>��</xsl:otherwise>
			    </xsl:choose>
			</InsuDueDate>
			<!--�ɷ�ʡ�д���	-->
			<PayProv><xsl:value-of select="PayProv"/></PayProv>
			<!--�ɷ������	-->
			<PayBranch><xsl:value-of select="PayNodeNo"/></PayBranch>
			<!--�ɷѽ��-->	
			<Prem><xsl:value-of select="java:com.sinosoft.midplat.common.NumberUtil.fenToYuan(PayPrem)"/></Prem>
			<!--�ɷ��˻�	-->
			<PayAccount><xsl:value-of select="AccNo"/></PayAccount>
			<!-- �ɷѷ�ʽ -->
			<PayType><xsl:apply-templates select="PayIntv"/></PayType>
			<PayDue>
				<xsl:choose>
					<xsl:when test="PayIntv = 0">����</xsl:when>
					<xsl:when test="PayendYearFlag = 'Y'"><xsl:value-of select="PayendYear"/>��</xsl:when>
					<xsl:when test="PayendYearFlag = 'M'"><xsl:value-of select="PayendYear"/>��</xsl:when>
					<xsl:when test="PayendYearFlag = 'D'"><xsl:value-of select="PayendYear"/>��</xsl:when>  
					<xsl:otherwise><xsl:value-of select="PayendYear"/>����</xsl:otherwise>
				</xsl:choose>
			</PayDue>
		    <!-- Ͷ���˽ڵ� -->	
		    <Appl>
	            <!-- Ͷ����֤������ -->
		        <IDKind><xsl:apply-templates select="Appnt/IDType"/></IDKind>
				<!--Ͷ����֤������-->	
				<IDCode><xsl:value-of select="Appnt/IDNo"/></IDCode>
				<!--Ͷ��������	-->
				<Name><xsl:value-of select="Appnt/Name"/></Name>
				<!--Ͷ�����Ա�	-->
				<Sex><xsl:value-of select="Appnt/Sex"/></Sex>
				<!--Ͷ���˳�������	-->
				<Birthday><xsl:value-of select="Appnt/Birthday"/></Birthday>
				<!--Ͷ����ͨѶ��ַ-->	
				<Address><xsl:value-of select="Appnt/Address"/></Address>            
				<!--Ͷ������������-->	 
				<ZipCode><xsl:value-of select="Appnt/ZipCode"/></ZipCode>           
				<!--Ͷ���˵�������-->	
			    <Email><xsl:value-of select="Appnt/Email"/></Email>             
				<!--Ͷ���˹̶��绰-->	 
				<Phone><xsl:value-of select="Appnt/Phone"/></Phone>              
				<!--Ͷ�����ƶ��绰:-->	 
				<Mobile><xsl:value-of select="Appnt/Mobile"/></Mobile>
				<!-- ���� -->
			    <Country><xsl:apply-templates select="Appnt/Nationality"/></Country>            
				<!--Ͷ������������ķ��ص�λ����Ԫ--> 
				<xsl:variable name="mYearSalary" select="substring-before(Appnt/YearSalary,'.')"></xsl:variable> 
				<xsl:choose>
					<!-- ����������ն����� -->
					<xsl:when test="SaleChannel='8' or SaleChannel='0'">
						<AnnualIncome><xsl:value-of select="$mYearSalary"/>0000.00</AnnualIncome>    
					</xsl:when>
					<!-- �������ֻ��������� -->
					<xsl:when test="SaleChannel='1' or SaleChannel='2'">
						<xsl:choose>
							<xsl:when test="$mYearSalary='5'">
								<AnnualIncome>5������</AnnualIncome>
							</xsl:when>
							<xsl:when test="$mYearSalary='10'">
								<AnnualIncome>5-10��</AnnualIncome>
							</xsl:when>
							<xsl:when test="$mYearSalary='30'">
								<AnnualIncome>10-30��</AnnualIncome>
							</xsl:when>
							<xsl:when test="$mYearSalary='50'">
								<AnnualIncome>30-50��</AnnualIncome>
							</xsl:when>
							<xsl:otherwise>
								<AnnualIncome>50������</AnnualIncome>
							</xsl:otherwise>
						</xsl:choose>
					</xsl:when>
				</xsl:choose>
				<!--Ͷ�����뱻�����˹�ϵ-->	
				<RelaToInsured>
					<xsl:call-template name="tran_relaApp">
				    	<xsl:with-param name="rela">
					    	<xsl:value-of select="Appnt/RelaToInsured"/>
					    </xsl:with-param>
					</xsl:call-template>						
				</RelaToInsured>
			</Appl>
			<Insu>
			    <!--����������--> 	 
			    <Name><xsl:value-of select="Insured/Name"/></Name>        
				<!--�������Ա�:--> 	 
				<Sex><xsl:value-of select="Insured/Sex"/></Sex>         
				<!--������֤������--> 	 
				<IDKind><xsl:apply-templates select="Insured/IDType"/></IDKind>  
				<!--������֤������--> 	 
				<IDCode><xsl:value-of select="Insured/IDNo"/></IDCode>  
				<!--�����˳�������--> 	
				<Birthday><xsl:value-of select="Insured/Birthday"/></Birthday>
			</Insu>	
			<Objt>
			    <!--���շ��ݵ�ַ(ʡ/ֱϽ��)-->	  
			    <Prov><xsl:value-of select="Objt/Prov"/></Prov>
				<!--���շ��ݵ�ַ(��)-->	   
				<City><xsl:value-of select="Objt/City"/></City>
				<!--���շ��ݵ�ַ(��/��)-->	   
				<Zone><xsl:value-of select="Objt/Zone"/></Zone>
				<!--���շ��ݵ�ַ(�����ַ)-->	   
				<Address><xsl:value-of select="Objt/Address"/></Address>
				<!--���ݱ��ս��-->	  
				<Amnt><xsl:value-of select="Objt/Amnt"/></Amnt>
				<!--���շ����ʱ�	-->   
				<ZipCode><xsl:value-of select="Objt/ZipCode"/></ZipCode>
				<!--���շ������-->	   
				<Area><xsl:value-of select="Objt/Area"/></Area>
				<!--��������ʩ-->	   
				<PreFlag><xsl:value-of select="Objt/PreFlag"/></PreFlag>
				<!--������-->	  
				<NoPayPortion><xsl:value-of select="Objt/NoPayPortion"/></NoPayPortion>
				<!--����-->	  
				<NoPayPrem><xsl:value-of select="Objt/NoPayPrem"/></NoPayPrem>
				<!--���շ��ݽṹ-->	  
				<Struts><xsl:value-of select="Objt/Struts"/></Struts>
				<!--���շ�����;-->	  
				<Usage><xsl:value-of select="Objt/Usage"/></Usage>
				<!--�̶��ʲ�����ս��-->	   
				<FasAmnt><xsl:value-of select="Objt/FasAmnt"/></FasAmnt>
				<!--�����ʲ�����ս��-->	   
				<FloAmnt><xsl:value-of select="Objt/FloAmnt"/></FloAmnt>
				<!--����Ŀ�����-->	  
				<Count><xsl:value-of select="Objt/Count"/></Count>
				<!--����Ŀ������1-->	   
				<Name1><xsl:value-of select="Objt/Name1"/></Name1>
				<!--����Ŀ����1-->	  
				<Amnt1><xsl:value-of select="Objt/Amnt1"/></Amnt1>
				<!--����Ŀ������2-->	   
				<Name2><xsl:value-of select="Objt/Name2"/></Name2>
				<!--����Ŀ����2-->	  
				<Amnt2><xsl:value-of select="Objt/Amnt2"/></Amnt2>
				<!--����Ŀ������3-->
				<Name3><xsl:value-of select="Objt/Name3"/></Name3>
				<!--����Ŀ����3-->
				<Amnt3><xsl:value-of select="Objt/Amnt3"/></Amnt3>
				<!--����Ŀ������4-->
				<Name4><xsl:value-of select="Objt/Name4"/></Name4>
				<!--����Ŀ����4-->
				<Amnt4><xsl:value-of select="Objt/Amnt4"/></Amnt4>
				<!--����Ŀ������5-->
				<Name5><xsl:value-of select="Objt/Name5"/></Name5>
				<!--����Ŀ����5-->
				<Amnt5><xsl:value-of select="Objt/Amnt5"/></Amnt5>
				<!--����Ŀ������6-->
				<Name6><xsl:value-of select="Objt/Name6"/></Name6>
				<!--����Ŀ����6-->
				<Amnt6><xsl:value-of select="Objt/Amnt6"/></Amnt6>
				<!--����Ŀ������7-->
				<Name7><xsl:value-of select="Objt/Name7"/></Name7>
				<!--����Ŀ����7-->
				<Amnt7><xsl:value-of select="Objt/Amnt7"/></Amnt7>
			</Objt>	
			<xsl:for-each select="Bnfs/Bnf">
				<Bnfs>
			        <!--�����˸���-->	       
			        <Count><xsl:value-of select="/TranData/Body/Bnfs/Count"/></Count>    
					<!--����������:-->	      
					<Type><xsl:value-of select="Type"/></Type> 
					<!--������1����-->	      
					<Name><xsl:value-of select="Name"/></Name>    
					<!--�Ա�:-->	       
					<Sex><xsl:value-of select="Sex"/></Sex>           
					<!--��������:-->	       
					<Birthday><xsl:value-of select="Birthday"/></Birthday>  
					<!--֤������:-->	       
					<IDKind><xsl:value-of select="IDType"/></IDKind>    
					<!--֤������:-->	       
					<IDCode><xsl:value-of select="IDNo"/></IDCode>    
					<!--�뱻���˹�ϵ:-->	    
					<RelationToInsured>
						<xsl:call-template name="tran_relaApp">
					    	<xsl:with-param name="rela">
						    	<xsl:value-of select="RelaToInsured"/>
						    </xsl:with-param>
						</xsl:call-template>						
					</RelationToInsured>
					<!--������1����˳��-->	       
					<Sequence><xsl:value-of select="Grade"/></Sequence>
					<!--������1�������-->	       
					<Prop><xsl:value-of select="Lot"/></Prop>
			    </Bnfs>
		  	</xsl:for-each>
		</Ret>
	</App>
</xsl:template>

<!-- ֤������-->
<xsl:template name="tran_idtype" match="IDType">
	<xsl:choose>
		<xsl:when test=".=0">110001</xsl:when>	<!-- ���֤ -->
		<xsl:when test=".=1">110025</xsl:when>  <!-- ������� -->
		<xsl:when test=".=2">110027</xsl:when>  <!-- ����֤ -->
		<xsl:when test=".=4">110005</xsl:when>  <!-- ���ڲ� -->		
		<xsl:when test=".=D">110031</xsl:when>  <!-- ����֤  -->
		<xsl:when test=".=F">110019</xsl:when>  <!-- �۰ľ��������ڵ�ͨ��֤ -->
		<xsl:otherwise>119999</xsl:otherwise>  
	</xsl:choose>
</xsl:template>

<!--  -->
<xsl:template name="tran_nationality" match="Nationality">
	<xsl:choose>
		<xsl:when test=".= 'CHN'">156</xsl:when> <!--�й�-->
		<xsl:when test=".= 'HK'">344</xsl:when> <!--�й����-->
		<xsl:when test=".='TW'">158</xsl:when> <!--�й�̨��-->
		<xsl:when test=".='MO'">446</xsl:when> <!--�й�����-->
		<xsl:when test=".='JAN'">392</xsl:when> <!--�ձ�-->
		<xsl:when test=".='USA'">840</xsl:when> <!--����-->
		<xsl:when test=".='RUS'">643</xsl:when> <!--����˹-->
		<xsl:when test=".='ENG'">826</xsl:when> <!--Ӣ��-->
		<xsl:when test=".='FRA'">250</xsl:when> <!--����-->
		<xsl:when test=".='DEU'">276</xsl:when> <!--�¹�-->
		<xsl:when test=".='KOR'">410</xsl:when> <!--���� -->
		<xsl:when test=".='SG'">702</xsl:when> <!--�¼���-->
		<xsl:when test=".='INA'">360</xsl:when> <!--ӡ��������-->
		<xsl:when test=".='IND'">356</xsl:when> <!--ӡ��-->
		<xsl:when test=".='ITA'">380</xsl:when> <!--�����-->
		<xsl:when test=".='MY'">458</xsl:when> <!--��������-->
		<xsl:when test=".='THA'">764</xsl:when> <!--̩��-->
		<xsl:when test=".='OTH'">999</xsl:when> <!--�������Һ͵���-->
	</xsl:choose>
</xsl:template>

<!-- �ɷѷ�ʽ -->
<xsl:template name="tran_payintv" match="PayIntv">
	<xsl:choose>
		<xsl:when test=".=0">1</xsl:when>	<!-- ���� -->
		<xsl:when test=".=1">2</xsl:when>	<!-- �½� -->
		<xsl:when test=".=3">3</xsl:when>	<!-- ���� -->
		<xsl:when test=".=6">4</xsl:when>	<!-- ���꽻 -->
		<xsl:when test=".=12">5</xsl:when>	<!-- �꽻 -->
		<xsl:when test=".=-1">0</xsl:when>	<!-- ������ -->
	</xsl:choose>
</xsl:template>

<!--  ����״̬ -->
<xsl:template name="policyStatus" >
	<xsl:param name="PolicyStatus"></xsl:param>
		<xsl:choose>
			<xsl:when test="$PolicyStatus = '1'">00</xsl:when><!-- 	��Ч -->
			<xsl:when test="$PolicyStatus = '3'">01</xsl:when><!-- 	�˱���ֹ -->
			<!--  <xsl:when test="$PolicyStatus = ''">02</xsl:when>--><!--   ���ճ��� -->
			<xsl:when test="$PolicyStatus = '2'">03</xsl:when><!-- 	������ֹ -->
			<xsl:when test="$PolicyStatus = '4'">04</xsl:when><!-- 	������ֹ -->
			<xsl:when test="$PolicyStatus = ''">05</xsl:when><!-- 	״̬����ȷ -->	
			<xsl:when test="$PolicyStatus = '5'">07</xsl:when><!-- 	������ֹ -->
			<xsl:when test="$PolicyStatus = 'B'">00</xsl:when><!-- 	��Ч -->
			<xsl:otherwise>06</xsl:otherwise><!-- ������� -->
		</xsl:choose>
</xsl:template>

<!-- ��ϵ����ת��-->
<xsl:template name="tran_relaApp">
	<xsl:param name="rela"/>
	<xsl:variable name="aSex" select="Appnt/Sex"></xsl:variable>
	<xsl:choose>
		<xsl:when test="$rela ='00'">01</xsl:when><!-- ����-����/����-���� -->
		<xsl:when test="$rela ='01' and $aSex='0'">04</xsl:when><!-- ����-����/����-���� -->
		<xsl:when test="$rela ='01' and $aSex='1'">05</xsl:when><!-- ����-ĸ��/����-���� -->
		<xsl:when test="$rela ='02' and $aSex='0'">02</xsl:when><!-- ����-�ɷ�/����-���� -->
		<xsl:when test="$rela ='02' and $aSex='1'">03</xsl:when><!-- ����-����/����-���� -->
		<xsl:when test="$rela ='03' and $aSex='0'">06</xsl:when><!-- ����-����/����-���� -->		
		<xsl:when test="$rela ='03' and $aSex='1'">07</xsl:when><!-- ����-Ů��/����-���� -->
		<xsl:otherwise></xsl:otherwise>
	</xsl:choose>
</xsl:template>

<!-- ��ϵ����ת��-->
<xsl:template name="tran_relaBnf">
	<xsl:param name="rela"/>
	<xsl:variable name="bSex" select="Sex"></xsl:variable>
	<xsl:choose>
		<xsl:when test="$rela ='00'">01</xsl:when><!-- ����-����/����-���� -->
		<xsl:when test="$rela ='01' and $bSex='0'">04</xsl:when><!-- ����-����/����-���� -->
		<xsl:when test="$rela ='01' and $bSex='1'">05</xsl:when><!-- ����-ĸ��/����-���� -->
		<xsl:when test="$rela ='02' and $bSex='0'">02</xsl:when><!-- ����-�ɷ�/����-���� -->
		<xsl:when test="$rela ='02' and $bSex='1'">03</xsl:when><!-- ����-����/����-���� -->
		<xsl:when test="$rela ='03' and $bSex='0'">06</xsl:when><!-- ����-����/����-���� -->		
		<xsl:when test="$rela ='03' and $bSex='1'">07</xsl:when><!-- ����-Ů��/����-���� -->
		<xsl:otherwise></xsl:otherwise>
	</xsl:choose>
</xsl:template>

</xsl:stylesheet>