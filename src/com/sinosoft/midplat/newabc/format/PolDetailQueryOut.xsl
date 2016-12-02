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
			<PolicyStatus>00</PolicyStatus>
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
			<PolicyValue><xsl:value-of select="ContValue"/></PolicyValue>
	        <!-- ��ǰ�˻���ֵ --> 
	        <AccountValue><xsl:value-of select="AccountValue"/></AccountValue>
			<!--�����ڼ�	--> 
			<InsuDueDate><xsl:value-of select="(Risk[RiskCode=MainRiskCode])/InsuYear"/>��</InsuDueDate>
			<!--�ɷ�ʡ�д���	-->
			<PayProv><xsl:value-of select="PayProv"/></PayProv>
			<!--�ɷ������	-->
			<PayBranch><xsl:value-of select="PayNodeNo"/></PayBranch>
			
			<!--�ɷѽ��-->	
			<Prem><xsl:value-of select="PayPrem"/></Prem>
			<!--�ɷ��˻�	-->
			<PayAccount><xsl:value-of select="AccNo"/></PayAccount>
			<!-- �ɷѷ�ʽ -->
			<PayType>
			<xsl:choose>
	        <xsl:when test="PayType =0">1</xsl:when>  
		    <xsl:when test="PayType =12">0</xsl:when>
		    <xsl:otherwise>--</xsl:otherwise> 
		   </xsl:choose>
			</PayType>
			<PayDue><xsl:value-of select="PayendYear"/>��</PayDue>
			
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
			    <Country>
		<xsl:choose>
		<xsl:when test="Appnt/Nationality = 'CHN'">156</xsl:when> <!--�й�                      -->
		<xsl:when test="Appnt/Nationality = 'HK'">344</xsl:when> <!--�й����                  -->
		<xsl:when test="Appnt/Nationality ='TW'">158</xsl:when> <!--�й�̨��                  -->
		<xsl:when test="Appnt/Nationality ='MO'">446</xsl:when> <!--�й�����                  -->
		<xsl:when test="Appnt/Nationality ='JAN'">392</xsl:when> <!--�ձ�                      -->
		<xsl:when test="Appnt/Nationality ='USA'">840</xsl:when> <!--����                      -->
		<xsl:when test="Appnt/Nationality ='RUS'">643</xsl:when> <!--����˹                    -->
		<xsl:when test="Appnt/Nationality ='ENG'">826</xsl:when> <!--Ӣ��                      -->
		<xsl:when test="Appnt/Nationality ='FRA'">250</xsl:when> <!--����                      -->
		<xsl:when test="Appnt/Nationality ='DEU'">276</xsl:when> <!--�¹�                      -->
		<xsl:when test="Appnt/Nationality ='KOR'">410</xsl:when> <!--����                      -->
		<xsl:when test="Appnt/Nationality ='SG'">702</xsl:when> <!--�¼���                    -->
		<xsl:when test="Appnt/Nationality ='INA'">360</xsl:when> <!--ӡ��������                -->
		<xsl:when test="Appnt/Nationality ='IND'">356</xsl:when> <!--ӡ��                      -->
		<xsl:when test="Appnt/Nationality ='ITA'">380</xsl:when> <!--�����                    -->
		<xsl:when test="Appnt/Nationality ='MY'">458</xsl:when> <!--��������                  -->
		<xsl:when test="Appnt/Nationality ='THA'">764</xsl:when> <!--̩��                      -->
		<xsl:when test="Appnt/Nationality ='OTH'">999</xsl:when> <!--�������Һ͵���            -->
	</xsl:choose>
				</Country>            
				<!--Ͷ����������-->  
				<AnnualIncome></AnnualIncome>       
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
				<Name3><xsl:value-of select="Objt/Name3"/></Name3><!--����Ŀ������3-->
				<Amnt3><xsl:value-of select="Objt/Amnt3"/></Amnt3><!--����Ŀ����3-->
				<Name4><xsl:value-of select="Objt/Name4"/></Name4><!--����Ŀ������4-->
				<Amnt4><xsl:value-of select="Objt/Amnt4"/></Amnt4><!--����Ŀ����4-->
				<Name5><xsl:value-of select="Objt/Name5"/></Name5><!--����Ŀ������5-->
				<Amnt5><xsl:value-of select="Objt/Amnt5"/></Amnt5><!--����Ŀ����5-->
				<Name6><xsl:value-of select="Objt/Name6"/></Name6><!--����Ŀ������6-->
				<Amnt6><xsl:value-of select="Objt/Amnt6"/></Amnt6><!--����Ŀ����6-->
				<Name7><xsl:value-of select="Objt/Name7"/></Name7><!--����Ŀ������7-->
				<Amnt7><xsl:value-of select="Objt/Amnt7"/></Amnt7><!--����Ŀ����7-->
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
		<xsl:when test=".=1">110023</xsl:when>  <!-- �л����񹲺͹����� -->
		<xsl:when test=".=2">110027</xsl:when>  <!-- ����֤ -->
		<xsl:when test=".=4">110005</xsl:when>  <!-- ���ڲ� -->		
		<xsl:when test=".=5">110017</xsl:when>  <!-- ����ԺУѧԱ֤ -->
		<xsl:when test=".=8">119999</xsl:when>  <!-- ��������֤��ʶ���ʶ -->			
		<xsl:when test=".=A">110035</xsl:when>  <!-- ����ʿ��֤ -->
		<xsl:when test=".=C">110003</xsl:when>	<!-- ��ʱ�������֤ -->
		<xsl:when test=".=D">110031</xsl:when>  <!-- ����֤  -->
		<xsl:when test=".=E">110021</xsl:when>  <!-- ̨�����������½ͨ��֤ -->		
		<xsl:when test=".=F">110019</xsl:when>  <!-- �۰ľ��������ڵ�ͨ��֤ -->
		<xsl:otherwise>119999</xsl:otherwise>  
	</xsl:choose>
</xsl:template>
<!-- �ɷѷ�ʽ -->
<xsl:template name="payMode" match="PayType">
	<xsl:choose>
		<xsl:when test=".=0">1</xsl:when>	<!-- ���� -->
		<xsl:when test=".=1">0</xsl:when>	<!-- �ڽ� -->
		<xsl:otherwise></xsl:otherwise>  
	</xsl:choose>
</xsl:template>
<!-- ����״̬ -->
<xsl:template name="polStatus" match="PolicyStatus">
	<xsl:choose>
		<xsl:when test=".='0'">00</xsl:when>	<!-- ���ġ���Ч/���С���Ч -->
		<xsl:when test=".='15'">00</xsl:when>	<!-- ���ġ��ѽɷ�δǩ��/���С���Ч -->
		<xsl:when test=".='02'">01</xsl:when>	<!-- ���ġ��˱���ֹ/���С��˱� -->
		<xsl:when test=".='06'">03</xsl:when>	<!-- ���ġ�������ֹ/���С��̳� -->
		<xsl:when test=".='04'">07</xsl:when>	<!-- ���ġ�������ֹ/���С�������ֹ -->
		<xsl:when test=".='01'">04</xsl:when>	<!-- ���ġ�������ֹ/���С����ڸ��� -->
		<xsl:when test=".='14'">02</xsl:when>	<!-- ���ġ���ʵʱ������ֹ/���С����ճ��� -->		
		<xsl:when test=".='16'">08</xsl:when>   <!-- ���ġ�����/���С������� 20160301 Add -->
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