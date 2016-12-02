package com.sinosoft.lis.InterFace;

import java.io.File;

import jxl.CellType;

import org.apache.log4j.Logger;
import org.jdom.Element;


import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.db.IFLogDB;
import com.sinosoft.lis.db.IFdetailDB;
import com.sinosoft.lis.excel.InterFaceCreatExcel;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.*;
import com.sinosoft.midplat.exception.MidplatException;

/**
 * <p>
 * ClassName: LO_PrintBL
 * </p>
 * <p>
 * Description: 银保通每日实时数据导出表
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @Database: AXA_DB
 * @CreateDate：2011-07-26
 * @ReWriteDate:
 */

public class IF_NewCont extends InterFaceCreatExcel {
	
	protected final Logger cLogger = Logger.getLogger(getClass());

	public CErrors mErrors = new CErrors();// 错误处理类，每个需要错误处理的类中都放置该类

	private VData mInputData = new VData(); // 往后面传输数据的容器

	private VData mResult = new VData(); // 往界面传输数据的容器

	private GlobalInput mGlobalInput = new GlobalInput(); // 全局数据

	private TransferData mTransferData = new TransferData();

	private String sDay = "";

	private String sArea = "";

//	private String filePath = "";
	
	private String Path="";
	
	private String fileName="";
	
	private String cArea="";
	
	private int logNo = 0;
	
	private int fileNo=1;
	
	private MMap cSubmitMMap = new MMap();

	SSRS tSSRS = new SSRS(); // 公共，所有查询都可使用

	SSRS tSSRS1 = new SSRS();

	public IF_NewCont() {

	}

	/**
	 * 返回错误
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * 传输数据的公共方法
	 * 
	 * @param: cInputData 输入的数据 cOperate 数据操作
	 * @return:
	 * @throws MidplatException 
	 */
	public boolean submitData(VData cInputData) throws MidplatException {
		try {
			String errAllMessage = "";
			String[] mArea = {"SH","GZ","BJ"};
			for (int i =0;i< mArea.length ; i++){
				String errMessage = "";
					 this.cArea = mArea[i];
				// 得到外部传入的数据,将数据备份到本类中
				if (!getInputData(cInputData)) {
					return false;
				}
				// 打印
				if (!getPrintData(i)) {
					return false;
				}
				
				if(this.mErrors.getErrorCount()==0){
					errMessage=cArea+"接口文件生成成功；<br />";
				}else{
					errMessage=this.mErrors.getLastError().toString();
				}
				errMessage=errMessage.replace("SH", "东区");
				errMessage=errMessage.replace("GZ", "南区");
				errMessage=errMessage.replace("BJ", "北区");
				
				errAllMessage+=errMessage;
				this.mErrors.clearErrors();
			}
			
			this.mErrors.clearErrors();
			CError tError = new CError();
			tError.moduleName = "PrintRateBL";
			tError.functionName = "getPrintBankData";
			tError.errorMessage = errAllMessage;
			this.mErrors.addOneError(tError);
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "PrintRateBL";
			tError.functionName = "getPrintBankData";
			tError.errorMessage = cArea+e.getMessage()+"<br />";
			this.mErrors.addOneError(tError); 
		}
		
		return true;
	}

	/**
	 * 取传入参数信息
	 * 
	 * @param cInputData
	 *            VData
	 * @return boolean
	 */
	private boolean getInputData(VData cInputData) throws MidplatException{
		try {
			this.mInputData = cInputData;
			mGlobalInput = (GlobalInput) mInputData.getObjectByObjectName(
					"GlobalInput", 0);
			mTransferData = (TransferData) mInputData.getObjectByObjectName(
					"TransferData", 0);

			this.sDay = (String) mTransferData.getValueByName("Day");
			
			Element eLimitNum = 
				 MidplatConf.newInstance().getConf().getRootElement().getChild("NewContIF");
			String sLimitNum = eLimitNum.getAttributeValue("path");
			this.Path=sLimitNum+DateUtil.getCurDate("yyyy/yyyyMM/");
//			System.out.println("啦啦啦啦啦啦"+Path);
		    
			
			this.fileName="Y"+cArea+DateUtil.getCurDate("yyMMdd");
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "PrintRateBL";
			tError.functionName = "getPrintBankData";
			tError.errorMessage = cArea+e.getMessage()+"<br />";
			this.mErrors.addOneError(tError); 
		}

		return true;
	}

	/**
	 * 返回结果集
	 * 
	 * @return: VData 程序处理结果
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * 打印数据
	 * 
	 * @author JiaoYF
	 * @return boolean
	 * @throws MidplatException 
	 */
	private boolean getPrintData(int pArea) throws MidplatException {
		String areaNo = "";
		switch(pArea){
		case 0 :
			areaNo = "substr(d.comcode,3,2) in ('01','04')";
			break;
		case 1 :
			areaNo = "substr(d.comcode,3,2) = '03'";
			break;
		case 2 :
			areaNo = "substr(d.comcode,3,2) = '02'";
		
		}

		// 设置Excel的Sheet
		String[] Sheet = { "RFPNUPLWK" };
		setSheet(Sheet);
		String[] title = { "" };
		setTitle(title);
		ExeSQL exeSql = new ExeSQL();
		String sSql = "";

		String[] colSize = { "10", "10", "10", "10", "10", "10", "10", "10", "10", "10",
				 "10", "10", "10", "10", "10", "10", "10", "10", "10", "10",
				 "10", "10", "10", "10", "10", "10", "10", "10", "10", "10",
				 "10", "10", "10", "10", "10", "10", "10", "10", "10", "10",
				 "10", "10", "10", "10", "10", "10", "10", "10", "10", "10",
				 "10", "10", "10", "10", "10", "10", "10", "10", "10", "10",
				 "10", "10", "10", "10", "10", "10", "10", "10", "10", "10",
				 "10", "10", "10", "10", "10", "10", "10", "10", "10", "10",
				 "10", "10", "10", "10", "10", "10", "10", "10", "10", "10",
				 "10", "10", "10", "10", "10", "10", "10", "10", "10", "10",
				 "10", "10"
				 };

		

		sSql += "select 0,"
		    + "   d.RLSCode 分公司号,"
		    + "   c.contno 保单号,"
		    + "   CASE WHEN c.bankcode = '011' THEN 'DYR' WHEN c.bankcode = '012' THEN 'DGR' ELSE '--' END 银保通渠道区分代码,"
		    + "   to_number(to_char(c.signdate,'yyyymmdd')) 签单日期,"
		    + "   TO_NUMBER(REPLACE(c.axaagentcom,'-','')) 代理人编号,"
		    + "   0,"
		    + "   c.insuredname 被保人姓名,"
		    + "   to_number(to_char(c.insuredbirthday,'yyyymmdd')) 被保人生日,"
		    + "   'C'||c.insuredidno 被保人证件号,"
		    + "   (SELECT zipcode FROM LCAddress where customerno = a.appntno and addressno = a.addressno) 投保人邮编,"
		    + "   (SELECT postaladdress FROM LCAddress where customerno = a.appntno and addressno = a.addressno) 投保人地址,"
		    + "   '',"
		    + "   '',"
		    + "   (SELECT homephone FROM LCAddress where customerno = a.appntno and addressno = a.addressno)||';'||d.PHONE||';'||d.ZIPCODE 投保人电话,"
		    + "   (SELECT mobile FROM LCAddress where customerno = a.appntno and addressno = a.addressno)||';'||d.PHONE||';'||d.ZIPCODE 投保人手机,"
		    + "   c.insuredsex 被保人性别,"
		    + "   to_number(to_char(c.polapplydate,'yyyymmdd')) 投保单提交日期,"
		    + "   to_char(c.cvalidate,'yyyymmdd') 保单生效日期,"
		    + "   'N',"
		    + "   '',"
		    + "   'RMB',"
		    + "   p.riskcode||';'||c.payintv,"
		    + "   CASE WHEN p.riskcode in('NHONP','NBSP','GHONP','HONPG3','SPPACA','SPPACB') THEN '' when (select bc.bak1 from bankandinsumap bc where bc.trancom = c.bankcode   and bc.codetype='rlsCode' and bc.tran_code=c.managecom) is null then '' ELSE 'A' END 缴费形式,"
		    + "   'C',"
		    + "   p.riskcode||';'||c.prem||';'||p.riskalias 产品代码,"
		    + "   p.amnt 保额,"
		    + "   'S',"
		    + "   CASE WHEN p.riskcode = 'MCCIB' THEN 'MCCIR'||c.payendyear WHEN p.riskcode='TPD' THEN 'ADD' WHEN p.riskcode='LGCI' THEN 'LGCI5R' ELSE '' END 附加险1,"
		    + "   CASE WHEN p.riskcode in ('MCCIB','LGCI') THEN p.amnt  WHEN p.riskcode='TPD' THEN p.amnt*2 ELSE 0 END 附加险保额,"
		    + "   CASE WHEN p.riskcode IN ('MCCIB','TPD') THEN '1' ELSE '' END 附加险等级,"
		    + "   '',"
		    + "   0,"
		    + "   '',"
		    + "   '',"
		    + "   0,"
		    + "   '',"
		    + "   CASE WHEN a.pluralitytype = '8' THEN '' ELSE a.appntname END 投保人姓名,"
		    + "   CASE WHEN a.pluralitytype = '8' THEN '' ELSE a.appntname END 投保人中文姓名,"
		    + "   CASE WHEN a.pluralitytype ='1' THEN 'S' WHEN a.pluralitytype IN ('2','3') THEN 'P' WHEN a.pluralitytype IN ('4','28') THEN 'O' WHEN a.pluralitytype ='8' THEN 'I' ELSE '--' END 投被保人关系,"
		    + "   CASE WHEN a.pluralitytype = '8' THEN 0 ELSE to_number(TO_CHAR(a.appntbirthday,'yyyymmdd')) END 投保人生日,"
		    + "   CASE WHEN a.pluralitytype = '8' THEN 0 ELSE to_number(TO_CHAR(a.appntbirthday,'yyyymmdd')) END 投保人生日,"
		    + "   '',"
		    + "   CASE WHEN a.pluralitytype = '8' THEN '' ELSE 'C'||c.appntidno END 投保人证件号,"
		    + "   CASE WHEN a.pluralitytype = '8' THEN '' ELSE c.appntsex END 投保人性别,"
		    + "   CASE WHEN a.pluralitytype = '8' THEN '' ELSE c.appntsex END 投保人性别, "
		    + "   '',"
		    + "   1.6,"
		    + "   50,"
		    + "   'N',"
		    + "   'N',"
		    + "   'N',"
		    + "   'N',"
		    + "   'Y',"
		    + "   'N',"
		    + "   0,"
		    + "   nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(RTU)','HYG3rider(RTU)') THEN sp.prem ELSE 0 END 定期投资 FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear!=1),0) 定期投资,"
		    + "   (select nvl(p.firstaddprem,0)+nvl((SELECT CASE WHEN sp.riskcode in ('NHYrider(lpsm)','HYG3rider(lpsm)') THEN sp.prem ELSE 0 END 首期投资 FROM LCPOL sp WHERE (sp.polno=c.contno||'-1' or sp.polno=c.contno||'-2') AND sp.payendyear=1),0) from dual) 首期投资,"
		    + "   (SELECT ac.ACCCODE FROM LCInsureAcc ac WHERE ac.contno = c.Contno AND ac.insuaccno = '1') 投资账户1,"
		    + "   nvl((SELECT ac.accrate FROM LCInsureAcc ac WHERE ac.contno = c.Contno AND ac.insuaccno = '1'),0) 投资账户1比例,"
		    + "   (SELECT ac.ACCCODE FROM LCInsureAcc ac WHERE ac.contno = c.Contno AND ac.insuaccno = '2') 投资账户2,"
		    + "   nvl((SELECT ac.accrate FROM LCInsureAcc ac WHERE ac.contno = c.Contno AND ac.insuaccno = '2'),0) 投资账户2比例,"
		    + "   (SELECT ac.ACCCODE FROM LCInsureAcc ac WHERE ac.contno = c.Contno AND ac.insuaccno = '3') 投资账户3,"
		    + "   nvl((SELECT ac.accrate FROM LCInsureAcc ac WHERE ac.contno = c.Contno AND ac.insuaccno = '3'),0) 投资账户3比例,"
		    + "   (SELECT ac.ACCCODE FROM LCInsureAcc ac WHERE ac.contno = c.Contno AND ac.insuaccno = '4') 投资账户4,"
		    + "   nvl((SELECT ac.accrate FROM LCInsureAcc ac WHERE ac.contno = c.Contno AND ac.insuaccno = '4'),0) 投资账户4比例,"
		    + "   (select bc.bak1 from bankandinsumap bc where bc.trancom = c.bankcode   and bc.codetype='rlsCode' and bc.tran_code=c.managecom),"
		    + "   (select bc.bak2 from bankandinsumap bc where bc.trancom = c.bankcode   and bc.codetype='rlsCode' and bc.tran_code=c.managecom),"
		    + "   (select bc.bak3 from bankandinsumap bc where bc.trancom = c.bankcode   and bc.codetype='rlsCode' and bc.tran_code=c.managecom),"
		    + "    c.accname,"
		    + "    'A'||c.bankaccno,"
		    + "    c.insuredbirthday||';'||c.makedate,"
		    + "    0,"
		    + "    0,"
		    + "    c.insuredbirthday||';'||c.makedate,"
		    + "   (SELECT b.NAME FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 1) 受益人1,"
		    + "   (SELECT CASE WHEN b.relationtoinsured ='1' THEN 'SPOUSE' WHEN b.relationtoinsured = '2' THEN 'PARENT' WHEN b.relationtoinsured='3' THEN 'CHILD' WHEN b.relationtoinsured='4' THEN 'GRANDPARE' WHEN b.relationtoinsured = '5' THEN 'GRANDCHILD' ELSE 'OTHERS' END FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 1) 与被保人关系,"
		    + "   (SELECT b.bnflot*100 FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 1) 受益1比例,"
		    + "   nvl((SELECT to_number(to_char(b.birthday,'yyyymmdd')) FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 1),0) 受益人生日,"
		    + "   (SELECT b.sex FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 1) 受益人性别,"
		    + "   'R',"
		    + "   (SELECT b.NAME FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 2) 受益人2,"
		    + "   (SELECT CASE WHEN b.relationtoinsured ='1' THEN 'SPOUSE' WHEN b.relationtoinsured = '2' THEN 'PARENT' WHEN b.relationtoinsured='3' THEN 'CHILD' WHEN b.relationtoinsured='4' THEN 'GRANDPARE' WHEN b.relationtoinsured = '5' THEN 'GRANDCHILD' ELSE 'OTHERS' END FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 2) 与被保人关系,"
		    + "   nvl((SELECT b.bnflot*100 FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 2),0) 受益2比例,"
		    + "   nvl((SELECT to_number(to_char(b.birthday,'yyyymmdd')) FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 2),0) 受益人生日,"
		    + "   (SELECT b.sex FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 2) 受益人性别,"
		    + "   (SELECT 'R' FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 2),"
		    + "   (SELECT b.NAME FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 3) 受益人3,"
		    + "   (SELECT CASE WHEN b.relationtoinsured ='1' THEN 'SPOUSE' WHEN b.relationtoinsured = '2' THEN 'PARENT' WHEN b.relationtoinsured='3' THEN 'CHILD' WHEN b.relationtoinsured='4' THEN 'GRANDPARE' WHEN b.relationtoinsured = '5' THEN 'GRANDCHILD' ELSE 'OTHERS' END FROM LCBNF b WHERE b.contno = c.contno and b.bnfno =3) 与被保人关系,"
		    + "   nvl((SELECT b.bnflot*100 FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 3),0) 受益3比例,"
		    + "   nvl((SELECT to_number(to_char(b.birthday,'yyyymmdd')) FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 3),0) 受益人生日,"
		    + "   (SELECT b.sex FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 3) 受益人性别,"
		    + "   (SELECT 'R' FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 3),"
		    + "   (SELECT b.NAME FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 4) 受益人4,"
		    + "   (SELECT CASE WHEN b.relationtoinsured ='1' THEN 'SPOUSE' WHEN b.relationtoinsured = '2' THEN 'PARENT' WHEN b.relationtoinsured='3' THEN 'CHILD' WHEN b.relationtoinsured='4' THEN 'GRANDPARE' WHEN b.relationtoinsured = '5' THEN 'GRANDCHILD' ELSE 'OTHERS' END FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 4) 与被保人关系,"
		    + "   nvl((SELECT b.bnflot*100 FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 4),0) 受益4比例,"
		    + "   nvl((SELECT to_number(to_char(b.birthday,'yyyymmdd')) FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 4),0) 受益人生日,"
		    + "   (SELECT b.sex FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 4) 受益人性别,"
		    + "   (SELECT 'R' FROM LCBNF b WHERE b.contno = c.contno and b.bnfno = 4),"
		    + "   CASE WHEN p.riskcode in('NHONP','GHONP','HONPG3') THEN c.prem ELSE 0 END 首期追加投资,"
		    + "   CASE WHEN p.riskcode in('NBSP') THEN c.prem ELSE 0 END 基础保费,"
		    + "   'LR',"
		    + "   'N',"
		    + "   '',"
		    + "   '',"
		    + "   'N',"
		    + "   0,"
		    + "   '',"
		    + "   0,"
		    + "   '',"
		    + "   0,"
		    + "   '',"
		    + "   '',"
		    + "   '',"
		    + "   '',"
		    + "   '',"
		    + "   '',"
		    + "   '',"
		    + "   '',"
		    + "   '',"
		    + "   '',"
		    + "   '',"
		    + "   CASE WHEN p.riskcode in('TPD') THEN p.riskcode WHEN p.riskcode = 'MCCIB' THEN p.riskcode||c.payendyear ELSE '' END,"
		    + "   0,"
		    + "   '',"
		    + "   0,"
		    + "   (SELECT CASE WHEN ac.acctimeflag='2' THEN 'Y' ELSE '' END FROM LCInsureAcc ac WHERE ac.contno = c.contno and ac.insuaccno='1')"
		       
		    + "  from lccont c,"
		    + "   ldcom d,"
		    + "   LCInsured i,"
		    + "   LCAppnt a,"
		    + "   Lcpol p"

		    + "   where d.comcode = c.managecom and"
		    + "   i.contno = c.contno and"
		    + "   a.contno = c.contno and"
		    + "   p.polno = c.contno||'-0' and"
		    + "   c.makedate =  date'"+sDay+"'"
		    + "   and "+areaNo
		    + "   and (c.appflag='1' or c.appflag='B')"
		    + "   and c.uwflag='9' "
		    + "   and (c.norealflag != 'Y' or c.norealflag is null)"
		    + "   and c.downdate is null";
		          
		         


		try {
			tSSRS = exeSql.execSQL(sSql);
			
			String[][] print = tSSRS.getAllData();
			tSSRS = null;
			

			for (int i = 0; i < print.length; i++) {
				for (int j = 0; j < print[i].length; j++) {
					if (print[i][j] == null || "null".equals(print[i][j])) {
						print[i][j] = "";
					}
				}
			}
			

			
			
			//判断是否有保单数据		
			if(print.length>0){
				int slength=0;
				fileNo=1;
				String sql="select max(fileno) from iflog where area='"+cArea+"' and makedate= date'"+DateUtil.getCur10Date()+"' and iftype='newcont'";
				ExeSQL tExeSQL = new ExeSQL();
				String cFileNo=tExeSQL.getOneValue(sql);
				if(!cFileNo.equals("")){
					fileNo = Integer.parseInt(cFileNo)+1;
				}
				
				//每超过10000条生成一个新的批次
				while(print.length-slength>=10000){
					String[][] sprint = new String [10000][127];
					for(int k=slength;k<10000+slength;k++){
						for(int j=0;j<print[k].length;j++){						
							sprint[k][j]=print[k][j];
						}
					}
					
					 
					InterfaceUtil.initcErrors();
					for (int i =0;i<sprint.length; i++){
						 String[] Address= InterfaceUtil.seperateAddr(sprint[i][11],sprint[i][2]);
							sprint[i][11]= Address[0];
							sprint[i][12]= Address[1];
							sprint[i][13]= Address[2];
								
							sprint[i][14]=InterfaceUtil.getPhoneNo(sprint[i][14],sprint[i][2],"固话号码");
								
							sprint[i][15]=InterfaceUtil.getPhoneNo(sprint[i][15],sprint[i][2],"手机号码");
								
							sprint[i][18]=InterfaceUtil.EffectiveDate(sprint[i][18]);
								
							sprint[i][22]=InterfaceUtil.getPayintv(sprint[i][22]);
								
							sprint[i][25]=InterfaceUtil.getRiskCod(sprint[i][25]);
								
							sprint[i][71]=InterfaceUtil.getYINC(sprint[i][71]);
								
							sprint[i][74]=InterfaceUtil.getTUSOCL(sprint[i][74]);
							
						}
											
					
			    slength=slength+10000;	
			    
			    creatFile(sprint,colSize);
  }
				
				//不足10000条
				String[][] sprint=new String[print.length-slength][127];
				for(int i=slength;i<print.length;i++){
					for(int j=0;j<print[i].length;j++){
						sprint[i][j]=print[i][j];
					}				
				}
				
				InterfaceUtil.initcErrors();
				for (int i =0;i<sprint.length; i++){
					 String[] Address= InterfaceUtil.seperateAddr(sprint[i][11],sprint[i][2]);
						sprint[i][11]= Address[0];
						sprint[i][12]= Address[1];
						sprint[i][13]= Address[2];
							
						sprint[i][14]=InterfaceUtil.getPhoneNo(sprint[i][14],sprint[i][2],"固话号码");
							
						sprint[i][15]=InterfaceUtil.getPhoneNo(sprint[i][15],sprint[i][2],"手机号码");
							
						sprint[i][18]=InterfaceUtil.EffectiveDate(sprint[i][18]);
							
						sprint[i][22]=InterfaceUtil.getPayintv(sprint[i][22]);
							
						sprint[i][25]=InterfaceUtil.getRiskCod(sprint[i][25]);
							
						sprint[i][71]=InterfaceUtil.getYINC(sprint[i][71]);
							
						sprint[i][74]=InterfaceUtil.getTUSOCL(sprint[i][74]);
						
					}
				
				 creatFile(sprint,colSize);
			
			}else{
				//此区无保单数据
				IFLogDB mIFLogDB=new IFLogDB();
				mIFLogDB.setLogNo(NoFactory.nextIFLogNo());
				mIFLogDB.setIFtype("newcont");
				mIFLogDB.setRCode("N");
				mIFLogDB.setArea(cArea);
				mIFLogDB.setTranDate(sDay);
				mIFLogDB.setMakeDate(DateUtil.getCur10Date());
				mIFLogDB.setMakeTime(DateUtil.getCur8Time());
				if (!mIFLogDB.insert()) {
					cLogger.error(mIFLogDB.mErrors.getFirstError());
					throw new MidplatException("插入日志失败！");
				}
				this.mErrors.clearErrors();
				CError tError = new CError();
				tError.moduleName = "PrintRateBL";
				tError.functionName = "getPrintBankData";
				tError.errorMessage = cArea+"本次查询无保单，请查看是否已有生成文件或稍后在执行该操作！<br />";
				this.mErrors.addOneError(tError);
			}
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "PrintRateBL";
			tError.functionName = "getPrintBankData";
			tError.errorMessage = cArea+e.getMessage()+"<br />";
			this.mErrors.addOneError(tError); 
		}
		
		return true;
	}
	
	
	public void creatFile(String[][] sprint,String[] colSize)throws MidplatException{
		setData(0, sprint, 0, 0);
		setColSize(0, colSize, 0);
//		
		setDataColNumer(0,"defaut");
		setDataColNumer(1,"defaut");
		setDataColNumer(4,"defaut");		                                                                     
		setDataColNumer(5,"defaut");
		setDataColNumer(6,"defaut");
		setDataColNumer(8,"defaut");
		setDataColNumer(17,"defaut");
		setDataColNumer(18,"defaut");
		setDataColNumer(26,"defaut");
		setDataColNumer(29,"defaut");
		setDataColNumer(32,"defaut");
		setDataColNumer(35,"defaut");
		setDataColNumer(40,"defaut");
		setDataColNumer(41,"defaut");
		setDataColNumer(47,"defaut");
		setDataColNumer(48,"defaut");
		setDataColNumer(55,"defaut");
		setDataColNumer(56,"defaut");
		setDataColNumer(57,"defaut");
		setDataColNumer(59,"defaut");
		setDataColNumer(61,"defaut");
		setDataColNumer(63,"defaut");
		setDataColNumer(65,"defaut");
		setDataColNumer(71,"defaut");
		setDataColNumer(72,"defaut");
		setDataColNumer(73,"defaut");
		setDataColNumer(77,"defaut");
		setDataColNumer(78,"defaut");
		setDataColNumer(83,"defaut");
		setDataColNumer(84,"defaut");
		setDataColNumer(89,"defaut");
		setDataColNumer(90,"defaut");
		setDataColNumer(95,"defaut");
		setDataColNumer(96,"defaut");
		setDataColNumer(99,"defaut");
		setDataColNumer(100,"defaut");
		setDataColNumer(106,"defaut");
		setDataColNumer(108,"defaut");
		setDataColNumer(110,"defaut");
		setDataColNumer(123,"defaut");
		setDataColNumer(125,"defaut");
		
		
		
		
		
		if(fileNo<10){
			fileName = fileName+"0";
		}

		// 设置Excel文件输出的路径
		setFilePath(Path+fileName+fileNo+".xls");
		setFile(Path);
		
		try {
			if (!createExcel()) {
				// @@错误处理
				this.mErrors.clearErrors();
				CError tError = new CError();
				tError.moduleName = "PrintRateBL";
				tError.functionName = "getPrintBankData";
				tError.errorMessage = cArea+"有部分文件生成失败！<br />";
				this.mErrors.addOneError(tError);
				mResult.clear();
				//向IFLog表中插入一条失败信息，失败信息不含文件名和路径
				IFLogDB mIFLogDB=new IFLogDB();
				mIFLogDB.setLogNo(NoFactory.nextIFLogNo());
				mIFLogDB.setIFtype("newcont");
				mIFLogDB.setRCode("CE");//CE:生成文件出错
				mIFLogDB.setArea(cArea);
				mIFLogDB.setTranDate(sDay);
				mIFLogDB.setMakeDate(DateUtil.getCur10Date());
				mIFLogDB.setMakeTime(DateUtil.getCur8Time());
				if (!mIFLogDB.insert()) {
					cLogger.error(mIFLogDB.mErrors.getFirstError());
					throw new MidplatException("插入日志失败！");
				}
				
			} else { 
				//文件生成成功,update LCcont表中的downdate和downtime
		
				if(updateDownDate(sprint)){
				//若update成功向IFLog中插入一条成功信息		
				IFLogDB mIFLogDB=new IFLogDB();
				mIFLogDB.setLogNo(NoFactory.nextIFLogNo());
				mIFLogDB.setIFtype("newcont");
				mIFLogDB.setRCode("Y");
				mIFLogDB.setArea(this.cArea);
				mIFLogDB.setTranDate(sDay);
				mIFLogDB.setFilePath(Path);
				mIFLogDB.setFileName(fileName+fileNo+".xls");
				mIFLogDB.setFileNo(fileNo);
				mIFLogDB.setBak1(String.valueOf(sprint.length));
				mIFLogDB.setBak2(String.valueOf(InterfaceUtil.getcErrors().getErrorCount()));
				mIFLogDB.setMakeDate(DateUtil.getCur10Date());
				mIFLogDB.setMakeTime(DateUtil.getCur8Time());
				if (!mIFLogDB.insert()) {
					cLogger.error(mIFLogDB.mErrors.getFirstError());
					throw new MidplatException("插入接口日志失败！");
				}
				
				String err="";
				if(InterfaceUtil.getcErrors().getErrorCount()==0){
					 err = "此批次无处理失败的信息！";
					 IFdetailDB mIFdetailDB = new IFdetailDB();
					 mIFdetailDB.setLogNo(NoFactory.nextIFdetailNo());
					 mIFdetailDB.setIFtype("newcont");
					 mIFdetailDB.setIFLogNo(mIFLogDB.getLogNo());
					 mIFdetailDB.setFileName(mIFLogDB.getFileName());
					 mIFdetailDB.setRCode(err);
					 mIFdetailDB.setMakeDate(DateUtil.getCur10Date());
					 mIFdetailDB.setMakeTime(DateUtil.getCur8Time());
					     if (!mIFdetailDB.insert()) {
							cLogger.error(mIFdetailDB.mErrors.getFirstError());
							throw new MidplatException("插入接口明细失败！");
						}
				}else{
					for (int i=0;i<InterfaceUtil.getcErrors().getErrorCount();i++){
						err=InterfaceUtil.getcErrors().getError(i).errorMessage;
						IFdetailDB mIFdetailDB = new IFdetailDB();
						mIFdetailDB.setLogNo(NoFactory.nextIFdetailNo());
						mIFdetailDB.setIFtype("newcont");
						mIFdetailDB.setIFLogNo(mIFLogDB.getLogNo());
						mIFdetailDB.setFileName(mIFLogDB.getFileName());
						mIFdetailDB.setRCode(err);
						mIFdetailDB.setMakeDate(DateUtil.getCur10Date());
						mIFdetailDB.setMakeTime(DateUtil.getCur8Time());
						if (!mIFdetailDB.insert()) {
							cLogger.error(mIFdetailDB.mErrors.getFirstError());
							throw new MidplatException("插入接口明细失败！");
						}
					}
				}
				
				
				
				}
				fileNo++;
				
			}
		} catch (Exception ex) {
			CError tError = new CError();
			tError.moduleName = "PrintRateBL";
			tError.functionName = "getPrintBankData";
			tError.errorMessage = cArea+ex.getMessage()+"<br />";
			this.mErrors.addOneError(tError); 
		}
	}
	

       
	/** 
	 * 	update LCcont表中的downdate和downtime
	 * */
	public boolean updateDownDate(String[][] sprint) throws MidplatException{
		for(int i=0;i<sprint.length;i++){
			String contno=sprint[i][2];
			cSubmitMMap.put("update lccont set downdate=date'"+DateUtil.getCur10Date()+"',downtime='"+DateUtil.getCur8Time()+"' where contno = '" + contno + "'", "UPDATE");

		}
		VData mSubmitVData = new VData();
		mSubmitVData.add(cSubmitMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, ""))
		{
			this.mErrors.clearErrors();
			CError tError = new CError();
			tError.moduleName = "PrintRateBL";
			tError.functionName = "getPrintBankData";
			tError.errorMessage = cArea+"有部分文件生成失败！<br />";
			this.mErrors.addOneError(tError);
			IFLogDB mIFLogDB=new IFLogDB();
			mIFLogDB.setLogNo(NoFactory.nextIFLogNo());
			mIFLogDB.setIFtype("newcont");
			mIFLogDB.setRCode("UE");//update LCcont表失败
			mIFLogDB.setArea(cArea);
			mIFLogDB.setTranDate(sDay);
			mIFLogDB.setMakeDate(DateUtil.getCur10Date());
			mIFLogDB.setMakeTime(DateUtil.getCur8Time());
			if (!mIFLogDB.insert()) {
				cLogger.error(mIFLogDB.mErrors.getFirstError());
				throw new MidplatException("插入日志失败！");
			}
			return false;
		}	
		cSubmitMMap = new MMap();
		return true;
	}
	
	
	
	

	public static void main(String[] args) {
		
//		Thread.currentThread().setName(
//				String.valueOf(NoFactory.nextTranLogNo()));
		// 准备数据容器信息
		TransferData tTransferData = new TransferData();
//		String filePath = "C:/Users/asus/Desktop/aa.xls";

		
		String sDay = "2012-02-29";
	

		
//		System.out.println("LO:客户正在提取每日实时数据，文件路径为：" + filePath);

		
		tTransferData.setNameAndValue("Day", sDay);


		VData tVData = new VData();
		tVData.addElement(tTransferData);
		// tVData.addElement(tG);
		String Content = "";
		String FlagStr = "";
		IF_NewCont tLO_PrintBL = new IF_NewCont();
		try {
			if (!tLO_PrintBL.submitData(tVData)) {
				FlagStr = "Fail";
				Content = tLO_PrintBL.mErrors.getFirstError().toString();

			} else {
				FlagStr = "Succ";
				Content = (String) tLO_PrintBL.getResult().get(0);
				// Content.replaceAll("\\","/");
				System.out.println(tLO_PrintBL.mErrors.getError(0).toString());
			}
		} catch (MidplatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}