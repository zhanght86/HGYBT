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
 * Description: ����ͨÿ��ʵʱ���ݵ�����
 * </p>
 * <p>
 * Copyright: Copyright (c) 2002
 * </p>
 * <p>
 * Company: sinosoft
 * </p>
 * 
 * @Database: AXA_DB
 * @CreateDate��2011-07-26
 * @ReWriteDate:
 */

public class IF_Finance extends InterFaceCreatExcel {
	
	protected final Logger cLogger = Logger.getLogger(getClass());

	public CErrors mErrors = new CErrors();// �������࣬ÿ����Ҫ����������ж����ø���

	private VData mInputData = new VData(); // �����洫�����ݵ�����

	private VData mResult = new VData(); // �����洫�����ݵ�����

	private GlobalInput mGlobalInput = new GlobalInput(); // ȫ������

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

	SSRS tSSRS = new SSRS(); // ���������в�ѯ����ʹ��

	SSRS tSSRS1 = new SSRS();

	public IF_Finance() {

	}

	/**
	 * ���ش���
	 */
	public CErrors getErrors() {
		return mErrors;
	}

	/**
	 * �������ݵĹ�������
	 * 
	 * @param: cInputData ��������� cOperate ���ݲ���
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
			// �õ��ⲿ���������,�����ݱ��ݵ�������
			if (!getInputData(cInputData)) {
				return false;
			}
			// ��ӡ
			if (!getPrintData(i)) {
				return false;
			}
			if(this.mErrors.getErrorCount()==0){
				errMessage=cArea+"�ӿ��ļ����ɳɹ���<br />";
			}else{
				errMessage=this.mErrors.getLastError().toString();
			}
			errMessage=errMessage.replace("SH", "����");
			errMessage=errMessage.replace("GZ", "����");
			errMessage=errMessage.replace("BJ", "����");
			
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
	 * ȡ���������Ϣ
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
	 * ���ؽ����
	 * 
	 * @return: VData ��������
	 */
	public VData getResult() {
		return mResult;
	}

	/**
	 * ��ӡ����
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

		// ����Excel��Sheet
		String[] Sheet = { "RFPNUPLWK" };
		setSheet(Sheet);
		String[] title = { "" };
		setTitle(title);
		ExeSQL exeSql = new ExeSQL();
		String sSql = "";

		String[] colSize = { "10", "10", "10", "10", "10", "10"};

		

		sSql += "select "
		    + "   c.contno ������,"
		    + "   replace(c.prem,'.00','') ����,"
		    + "   to_number(to_char(sysdate,'yyyymmdd')) ������������,"
		    + "   to_number(to_char(sysdate,'hh24mmss')) ��������ʱ��,"
		    + "   CASE WHEN c.bankcode = '011' THEN 'ICBC' WHEN c.bankcode = '012' THEN 'CGB' ELSE '--' END ���д���,"
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
			
			//�ж��Ƿ��б�������		
			if(print.length>0){
				int slength=0;
				fileNo=1;
				String sql="select max(fileno) from iflog where area='"+cArea+"' and makedate= date'"+DateUtil.getCur10Date()+"' and IFtype='fin'";
				ExeSQL tExeSQL = new ExeSQL();
				String cFileNo=tExeSQL.getOneValue(sql);
				if(!cFileNo.equals("")){
					fileNo = Integer.parseInt(cFileNo)+1;
				}
				
				//ÿ����10000������һ���µ�����
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
				
				//����10000��
				String[][] sprint=new String[print.length-slength][6];
				for(int i=slength;i<print.length;i++){
					for(int j=0;j<print[i].length;j++){
						sprint[i][j]=print[i][j];
					}				
				}
				
				 creatFile(sprint,colSize);
			
			}else{
				//�����ޱ�������
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
					throw new MidplatException("������־ʧ�ܣ�");
				}
				CError tError = new CError();
				tError.moduleName = "PrintRateBL";
				tError.functionName = "getPrintBankData";
				tError.errorMessage = cArea+"���β�ѯ�ޱ�������鿴�Ƿ����������ļ����Ժ���ִ�иò�����<br />";
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

		// ����Excel�ļ������·��
		setFilePath(Path+fileName+fileNo+".xls");
		setFile(Path);
		
		try {
			if (!createExcel()) {
				// @@������
				CError tError = new CError();
				tError.moduleName = "PrintRateBL";
				tError.functionName = "getPrintBankData";
				tError.errorMessage = cArea+"�в����ļ�����ʧ�ܣ�<br />";
				this.mErrors.addOneError(tError);
				mResult.clear();
				//��IFLog���в���һ��ʧ����Ϣ��ʧ����Ϣ�����ļ�����·��
				IFLogDB mIFLogDB=new IFLogDB();
				mIFLogDB.setLogNo(String.valueOf(NoFactory.nextIFLogNo()));
				mIFLogDB.setIFtype("fin");
				mIFLogDB.setRCode("CE");//CE:�����ļ�����
				mIFLogDB.setArea(cArea);
				mIFLogDB.setTranDate(sDay);
				mIFLogDB.setMakeDate(DateUtil.getCur10Date());
				mIFLogDB.setMakeTime(DateUtil.getCur8Time());
				if (!mIFLogDB.insert()) {
					cLogger.error(mIFLogDB.mErrors.getFirstError());
					throw new MidplatException("������־ʧ�ܣ�");
				}
				
			} else {
				//�ļ����ɳɹ�,update LCcont���е�findowndate��findowntime
				if(updateDownDate(sprint)){
				//��update�ɹ���IFLog�в���һ���ɹ���Ϣ	
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
					throw new MidplatException("������־ʧ�ܣ�");
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
	 * update LCcont���е�findowndate��findowntime
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
				tError.errorMessage = cArea+"�в����ļ�����ʧ�ܣ�<br />";
				this.mErrors.addOneError(tError);
				IFLogDB mIFLogDB=new IFLogDB();
				mIFLogDB.setLogNo(String.valueOf(NoFactory.nextIFLogNo()));
				mIFLogDB.setIFtype("fin");
				mIFLogDB.setRCode("UE");//update LCcont��ʧ��
				mIFLogDB.setArea(cArea);
				mIFLogDB.setTranDate(sDay);
				mIFLogDB.setMakeDate(DateUtil.getCur10Date());
				mIFLogDB.setMakeTime(DateUtil.getCur8Time());
				if (!mIFLogDB.insert()) {
					cLogger.error(mIFLogDB.mErrors.getFirstError());
					throw new MidplatException("������־ʧ�ܣ�");
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
		// ׼������������Ϣ
		TransferData tTransferData = new TransferData();
//		String filePath = "C:/Users/asus/Desktop/aa.xls";

		
		String sDay = "2012-02-29";
	

		
//		System.out.println("LO:�ͻ�������ȡÿ��ʵʱ���ݣ��ļ�·��Ϊ��" + filePath);

		
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