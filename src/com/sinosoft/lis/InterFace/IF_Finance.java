package com.sinosoft.lis.InterFace;

import org.apache.log4j.Logger;
import org.jdom.Element;

import jxl.format.Colour;

import jxl.format.BorderLineStyle;

import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;
import com.sinosoft.lis.print.MatrixPrint;
import com.sinosoft.lis.db.IFLogDB;
import com.sinosoft.lis.excel.InterFaceCreatExcel;
import com.sinosoft.lis.excel.ExcelAlignment;
import com.sinosoft.lis.excel.ExcelBorder;
import com.sinosoft.lis.excel.ExcelBorderLineStyle;
import com.sinosoft.lis.excel.ExcelFont;
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

public class IF_Finance extends InterFaceCreatExcel {
	
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

	public IF_Finance() {

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
				 MidplatConf.newInstance().getConf().getRootElement().getChild("FinanceIF");
			String sLimitNum = eLimitNum.getAttributeValue("path");
			this.Path=sLimitNum+DateUtil.getCurDate("yyyy/yyyyMM/");
			
			this.fileName="F"+cArea+DateUtil.getCurDate("yyMMdd");
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

		String[] colSize = { "10", "10", "10", "10", "10", "10"};

		

		sSql += "select "
		    + "   c.contno 保单号,"
		    + "   replace(c.prem,'.00','') 保费,"
		    + "   to_number(to_char(sysdate,'yyyymmdd')) 数据下载日期,"
		    + "   to_number(to_char(sysdate,'hh24mmss')) 数据下载时间,"
		    + "   CASE WHEN c.bankcode = '011' THEN 'ICBC' WHEN c.bankcode = '012' THEN 'CGB' ELSE '--' END 银行代码,"
		    + "   'NBUL'"
		    
		    + "  from lccont c,ldcom d"
		  

		    + "   where d.comcode = c.managecom and"
		    + "   c.makedate =  date'"+sDay+"'"
		    + "   and "+areaNo
		    + "   and (c.appflag='1' or c.appflag='B')"
		    + "   and c.uwflag='9' "
		    + "   and (c.norealflag != 'Y' or c.norealflag is null)"
		    + "   and c.findowndate is null";
		          
		         


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
				String sql="select max(fileno) from iflog where area='"+cArea+"' and makedate= date'"+DateUtil.getCur10Date()+"' and IFtype='fin'";
				ExeSQL tExeSQL = new ExeSQL();
				String cFileNo=tExeSQL.getOneValue(sql);
				if(!cFileNo.equals("")){
					fileNo = Integer.parseInt(cFileNo)+1;
				}
				
				//每超过10000条生成一个新的批次
				while(print.length-slength>=10000){
					String[][] sprint = new String [10000][6];
					for(int i=slength;i<10000+slength;i++){
						for(int j=0;j<print[i].length;j++){						
							sprint[i][j]=print[i][j];
						}				
					}
			    slength=slength+10000;	
			    
			    creatFile(sprint,colSize);
  }
				
				//不足10000条
				String[][] sprint=new String[print.length-slength][6];
				for(int i=slength;i<print.length;i++){
					for(int j=0;j<print[i].length;j++){
						sprint[i][j]=print[i][j];
					}				
				}
				
				 creatFile(sprint,colSize);
			
			}else{
				//此区无保单数据
				IFLogDB mIFLogDB=new IFLogDB();
				mIFLogDB.setLogNo(String.valueOf(NoFactory.nextIFLogNo()));
				mIFLogDB.setIFtype("fin");
				mIFLogDB.setRCode("N");
				mIFLogDB.setArea(cArea);
				mIFLogDB.setTranDate(sDay);
				mIFLogDB.setMakeDate(DateUtil.getCur10Date());
				mIFLogDB.setMakeTime(DateUtil.getCur8Time());
				if (!mIFLogDB.insert()) {
					cLogger.error(mIFLogDB.mErrors.getFirstError());
					throw new MidplatException("插入日志失败！");
				}
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
		
		setDataColNumer(1,"defaut");
		setDataColNumer(2,"defaut");
		setDataColNumer(3,"defaut");
		
		if(fileNo<10){
			fileName = fileName+"0";
		}

		// 设置Excel文件输出的路径
		setFilePath(Path+fileName+fileNo+".xls");
		setFile(Path);
		
		try {
			if (!createExcel()) {
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "PrintRateBL";
				tError.functionName = "getPrintBankData";
				tError.errorMessage = cArea+"有部分文件生成失败！<br />";
				this.mErrors.addOneError(tError);
				mResult.clear();
				//向IFLog表中插入一条失败信息，失败信息不含文件名和路径
				IFLogDB mIFLogDB=new IFLogDB();
				mIFLogDB.setLogNo(String.valueOf(NoFactory.nextIFLogNo()));
				mIFLogDB.setIFtype("fin");
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
				//文件生成成功,update LCcont表中的findowndate和findowntime
				if(updateDownDate(sprint)){
				//若update成功向IFLog中插入一条成功信息	
				IFLogDB mIFLogDB=new IFLogDB();
				mIFLogDB.setLogNo(String.valueOf(NoFactory.nextIFLogNo()));
				mIFLogDB.setIFtype("fin");
				mIFLogDB.setRCode("Y");
				mIFLogDB.setArea(this.cArea);
				mIFLogDB.setTranDate(sDay);
				mIFLogDB.setFilePath(Path);
				mIFLogDB.setFileName(fileName+fileNo+".xls");
				mIFLogDB.setFileNo(fileNo);
				mIFLogDB.setMakeDate(DateUtil.getCur10Date());
				mIFLogDB.setMakeTime(DateUtil.getCur8Time());
				if (!mIFLogDB.insert()) {
					cLogger.error(mIFLogDB.mErrors.getFirstError());
					throw new MidplatException("插入日志失败！");
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
	 * update LCcont表中的findowndate和findowntime
	 *  */	
	public boolean updateDownDate(String[][] sprint) throws MidplatException{
		try {
			for(int i=0;i<sprint.length;i++){
				String contno=sprint[i][0];
				cSubmitMMap.put("update lccont set findowndate='"+DateUtil.getCur10Date()+"',findowntime='"+DateUtil.getCur8Time()+"' where contno = '" + contno + "'", "UPDATE");

			}
			VData mSubmitVData = new VData();
			mSubmitVData.add(cSubmitMMap);
			PubSubmit mPubSubmit = new PubSubmit();
			if (!mPubSubmit.submitData(mSubmitVData, ""))
			{
				
				CError tError = new CError();
				tError.moduleName = "PrintRateBL";
				tError.functionName = "getPrintBankData";
				tError.errorMessage = cArea+"有部分文件生成失败！<br />";
				this.mErrors.addOneError(tError);
				IFLogDB mIFLogDB=new IFLogDB();
				mIFLogDB.setLogNo(String.valueOf(NoFactory.nextIFLogNo()));
				mIFLogDB.setIFtype("fin");
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
			cSubmitMMap = new MMap();;
		} catch (Exception e) {
			CError tError = new CError();
			tError.moduleName = "PrintRateBL";
			tError.functionName = "getPrintBankData";
			tError.errorMessage = cArea+e.getMessage()+"<br />";
			this.mErrors.addOneError(tError); 
		}
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
		IF_Finance tLO_PrintBL = new IF_Finance();
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