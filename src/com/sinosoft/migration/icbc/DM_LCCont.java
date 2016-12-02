package com.sinosoft.migration.icbc;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Element;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.ChangeCharset;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DBConnFactory;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.migration.DMUtil;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class DM_LCCont extends TimerTask implements XmlTag {
	private static final Logger cLogger = Logger.getLogger(DM_LCCont.class);

	public void run() {
		long mStartMillis = System.currentTimeMillis();

		Connection mOutConn = null;
		Statement mOutStmt = null;
		ResultSet mOutResultSet = null;
		Connection mLocalConn = null;
		try {
			Element tMidplatRoot = MidplatConf.newInstance().getConf()
					.getRootElement();
			mOutConn = new DBConnFactory(tMidplatRoot.getChild("SqlServerDB"))
					.getConn();
			if (mOutConn.isClosed()) {
				System.out.println("关着的");
			}
			mOutStmt = mOutConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			mOutStmt.close();

			String sql = DMUtil
					.getSql4LocalFile("C:\\Users\\asus\\Documents\\SQL Server Management Studio\\Projects\\LCCont.sql");
			cLogger.info(sql);
			mOutStmt = mOutConn.createStatement();
			mOutResultSet = mOutStmt.executeQuery(sql);
		
			mLocalConn = new DBConnFactory(tMidplatRoot.getChild(database))
					.getConn();
			mLocalConn.setAutoCommit(true);
			String cDate = DateUtil.getCur10Date();
			String cTime = DateUtil.getCur8Time();
			try {
				String ttSqlStr = null;
				Statement ttLocalStmt = null;
//
				ttSqlStr = "delete from LCCont ";
				//	 +" d where d.makedate!=date'2012-03-31'";
				cLogger.info(ttSqlStr);
				ttLocalStmt = mLocalConn.createStatement();
				ttLocalStmt.executeUpdate(ttSqlStr);
				ttLocalStmt.close();

				FileOutputStream out = null;
				FileOutputStream lccont = null;
				try {
					out = new FileOutputStream("C:\\Users\\asus\\Desktop\\error"+".txt",false);
					lccont = new FileOutputStream("C:\\Users\\asus\\Desktop\\lccont_utf8"+".sql",false);
				
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} 
				byte[] buff=new byte[]{};
				int tRecordNo = 0;
				while (mOutResultSet.next()) {
					tRecordNo++;
					
					
					cDate = (String) mOutResultSet.getString(84).subSequence(0, 10);
					
					String downDate = mOutResultSet.getString(66);
					String finDate = mOutResultSet.getString(68);
					
					String sContNo = mOutResultSet.getString(2).trim();
					String sCityNo = mOutResultSet.getString(11).trim();
					String sManageCom = DMUtil.getComCode(sCityNo);
					
					String sAppntNo = DMUtil.getPersonNo(mOutResultSet.getString(17),mOutResultSet.getString(18),mOutResultSet.getString(19).substring(0,10),mOutResultSet.getString(20),mOutResultSet.getString(21));
					String sInsuredNo = DMUtil.getPersonNo(mOutResultSet.getString(23),mOutResultSet.getString(24),mOutResultSet.getString(25).substring(0,10),mOutResultSet.getString(26),mOutResultSet.getString(27));
					
					String sAXAUWFlag = DMUtil.getAXAUWFlag(sContNo);
					String tsql = " select YSTUA,MAKEDATE from YBTNOREALPOLICIES where 1=1 "
						+ " and YNO = '" + sContNo + "' ORDER BY YMDAT DESC";
					SSRS S  = new ExeSQL().execSQL(tsql);
					String sAXAUWDate = "";
					if(S.MaxRow!=0){
						sAXAUWFlag = S.GetText(1, 1);
						sAXAUWDate = S.GetText(1, 2);
					}
					
					ArrayList<String []> arrayList = new ArrayList<String[]>();
					arrayList.add(DMUtil.get2String("GRPCONTNO",mOutResultSet.getString(1)));
					arrayList.add(DMUtil.get2String("CONTNO",sContNo));
					arrayList.add(DMUtil.get2String("ProposalContNo",mOutResultSet.getString(3).trim()));
					arrayList.add(DMUtil.get2String("PrtNo",mOutResultSet.getString(4).trim()));
					arrayList.add(DMUtil.get2String("ContType","1"));
					
					arrayList.add(DMUtil.get2String("PolType",mOutResultSet.getString(6)));
					arrayList.add(DMUtil.get2String("SaleChnl",mOutResultSet.getString(7)));
					
					String sAgentCode = mOutResultSet.getString(8);
					String tAgentCode = mOutResultSet.getString(8);
					String sAgentName = mOutResultSet.getString(14);
					if(sAgentCode == null || "".equals(sAgentCode) || sAgentCode.length()<10){
						SSRS ss = DMUtil.getAXAAgentCode(mOutResultSet.getString(10).trim());
						sAgentCode = ss.GetText(1, 1);
						sAgentName = ss.GetText(1, 2);
					}else if(tAgentCode.trim().length() ==15){
						sAgentCode = "0"+sAgentCode.trim();
					}
					 
					if("001".equals(tAgentCode)){
						SSRS ss = DMUtil.getAXAAgentCode(mOutResultSet.getString(10).trim());
						sAgentCode = ss.GetText(1, 1);
						sAgentName = ss.GetText(1, 2);
					}
					
					
					arrayList.add(DMUtil.get2String("AXAAgentCode",sAgentCode.trim()));
					arrayList.add(DMUtil.get2String("AXAAgentCom",mOutResultSet.getString(9).trim()));
					arrayList.add(DMUtil.get2String("AXANodeMap",mOutResultSet.getString(10).trim()));
					
					arrayList.add(DMUtil.get2String("ManageCom",sManageCom));
					arrayList.add(DMUtil.get2String("AgentCom",mOutResultSet.getString(12).trim()));
					arrayList.add(DMUtil.get2String("AgentComName",mOutResultSet.getString(13).trim()));
					arrayList.add(DMUtil.get2String("AgentName",sAgentName.trim()));
					arrayList.add(DMUtil.get2String("AgentCode",sAgentCode.trim().substring(10, 16)));
								
					arrayList.add(DMUtil.get2String("AppntNo",sAppntNo));
					arrayList.add(DMUtil.get2String("AppntName",mOutResultSet.getString(17).trim()));
					arrayList.add(DMUtil.get2String("AppntSex",mOutResultSet.getString(18).trim()));
					arrayList.add(DMUtil.get2String("AppntBirthDay","DATE'"+mOutResultSet.getString(19).substring(0,10).trim()+"'"));
					arrayList.add(DMUtil.get2String("AppntIDType",mOutResultSet.getString(20).trim()));

					arrayList.add(DMUtil.get2String("AppntIDNo",mOutResultSet.getString(21).trim()));
					arrayList.add(DMUtil.get2String("InsuredNo",sInsuredNo));
					arrayList.add(DMUtil.get2String("InsuredName",mOutResultSet.getString(23).trim()));
					arrayList.add(DMUtil.get2String("InsuredSex",mOutResultSet.getString(24).trim()));
					arrayList.add(DMUtil.get2String("InsuredBirthDay","DATE'"+mOutResultSet.getString(25).substring(0,10)+"'"));
				
					arrayList.add(DMUtil.get2String("InsuredIDType",mOutResultSet.getString(26).trim()));
					arrayList.add(DMUtil.get2String("InsuredIDNo",mOutResultSet.getString(27).trim()));
					arrayList.add(DMUtil.get2String("HealthNoticeFlag",mOutResultSet.getString(28).trim()));
					arrayList.add(DMUtil.get2String("JobNoticeFlag",mOutResultSet.getString(29)));
					arrayList.add(DMUtil.get2String("PayIntv",mOutResultSet.getString(30).trim()));
					
					arrayList.add(DMUtil.get2String("PayMode",mOutResultSet.getString(31).trim()));
					arrayList.add(DMUtil.get2String("PayEndYearFlag",mOutResultSet.getString(32).trim()));
					arrayList.add(DMUtil.get2String("PayEndYear",mOutResultSet.getString(33).trim()));
					arrayList.add(DMUtil.get2String("InsuYearFlag",mOutResultSet.getString(34).trim()));
					arrayList.add(DMUtil.get2String("InsuYear",mOutResultSet.getString(35).trim()));
					
					arrayList.add(DMUtil.get2String("GetPolMode",mOutResultSet.getString(36)));
					arrayList.add(DMUtil.get2String("BankCode",mOutResultSet.getString(37).trim()));
					arrayList.add(DMUtil.get2String("BankAccNo",mOutResultSet.getString(38).trim()));
					arrayList.add(DMUtil.get2String("AccName",mOutResultSet.getString(39)));
					arrayList.add(DMUtil.get2String("PrintCount",mOutResultSet.getString(40)));
					
					arrayList.add(DMUtil.get2String("Mult",mOutResultSet.getString(41)));
					
					if(mOutResultSet.getString(42) != null && !"".equals(mOutResultSet.getString(42))){
						arrayList.add(DMUtil.get2String("Prem",mOutResultSet.getString(42)));
					}
					if(mOutResultSet.getString(43) != null && !"".equals(mOutResultSet.getString(43))){
						arrayList.add(DMUtil.get2String("Amnt",mOutResultSet.getString(43)));
					}
					if(mOutResultSet.getString(44) != null && !"".equals(mOutResultSet.getString(44))){
						arrayList.add(DMUtil.get2String("MainPolPrem",mOutResultSet.getString(44)));
					}
					if(mOutResultSet.getString(45) != null && !"".equals(mOutResultSet.getString(45))){
						arrayList.add(DMUtil.get2String("FirstAddPrem",mOutResultSet.getString(45)));
					}
					
					
					arrayList.add(DMUtil.get2String("PolApplyDate","DATE'"+cDate+"'"));
					arrayList.add(DMUtil.get2String("CValiDate","DATE'"+cDate+"'"));
					arrayList.add(DMUtil.get2String("SignCom",sManageCom));
					arrayList.add(DMUtil.get2String("SignDate","DATE'"+cDate+"'"));
					arrayList.add(DMUtil.get2String("APPFLAG",mOutResultSet.getString(50)));
					
					arrayList.add(DMUtil.get2String("AgentBankCode",mOutResultSet.getString(51).trim()));
					arrayList.add(DMUtil.get2String("BankAgent",mOutResultSet.getString(52)));
					arrayList.add(DMUtil.get2String("ForceUWFlag",mOutResultSet.getString(53)));
					arrayList.add(DMUtil.get2String("InputOperator",mOutResultSet.getString(54)));
					arrayList.add(DMUtil.get2String("InputDate","DATE'"+cDate+"'"));
					
					arrayList.add(DMUtil.get2String("InputTime",cTime));
					arrayList.add(DMUtil.get2String("FamilyType",mOutResultSet.getString(57)));
					arrayList.add(DMUtil.get2String("CardFlag",mOutResultSet.getString(58)));
					arrayList.add(DMUtil.get2String("ExecuteCom",sManageCom));
					arrayList.add(DMUtil.get2String("LOSTTIMES",mOutResultSet.getString(60)));
					
					arrayList.add(DMUtil.get2String("PEOPLES",mOutResultSet.getString(61)));
					arrayList.add(DMUtil.get2String("SumPrem",mOutResultSet.getString(62)));
					arrayList.add(DMUtil.get2String("DIF",mOutResultSet.getString(63)));
					arrayList.add(DMUtil.get2String("APPROVEFLAG",mOutResultSet.getString(64)));
					arrayList.add(DMUtil.get2String("ApproveCode",mOutResultSet.getString(65)));
					
					if(downDate != null && !"".equals(downDate)){
							arrayList.add(DMUtil.get2String("DOWNDATE","DATE'"+downDate.substring(0,10)+"'"));
							arrayList.add(DMUtil.get2String("DOWNTIME",downDate.substring(11,19)));
					}
					
					if(finDate != null && !"".equals(finDate)){
						arrayList.add(DMUtil.get2String("FINDOWNDATE","DATE'"+finDate.substring(0,10)+"'"));
						arrayList.add(DMUtil.get2String("FINDOWNTIME",finDate.substring(11,19)));
					}
					
					String sReceiveDate = mOutResultSet.getString(86);
					if(sReceiveDate != null && !"".equals(sReceiveDate)){
						arrayList.add(DMUtil.get2String("ReceiveDate","DATE'"+sReceiveDate.substring(0,10)+"'"));
						arrayList.add(DMUtil.get2String("ReceiveTime",sReceiveDate.substring(11,19)));
					}
					
					arrayList.add(DMUtil.get2String("OVERTIMEFLAG",mOutResultSet.getString(70)));
					
					arrayList.add(DMUtil.get2String("NorealFlag",mOutResultSet.getString(71)));
					arrayList.add(DMUtil.get2String("UWFlag",mOutResultSet.getString(72)));
					arrayList.add(DMUtil.get2String("UWOperator",mOutResultSet.getString(73)));
					if(sAXAUWDate != null &&!"".equals(sAXAUWDate)){
						arrayList.add(DMUtil.get2String("UWDate","DATE'"+sAXAUWDate+"'"));
					}
					arrayList.add(DMUtil.get2String("AXAUWFLAG",sAXAUWFlag));
					
					arrayList.add(DMUtil.get2String("UWTIME",mOutResultSet.getString(76)));
					arrayList.add(DMUtil.get2String("GetPolDate","DATE'"+cDate+"'"));
					arrayList.add(DMUtil.get2String("CUSTOMGETPOLDATE","DATE'"+cDate+"'"));
					arrayList.add(DMUtil.get2String("BALANCESTATE",mOutResultSet.getString(79)));
					arrayList.add(DMUtil.get2String("STATE",mOutResultSet.getString(80)));
					
					arrayList.add(DMUtil.get2String("FirstTrialdate","DATE'"+cDate+"'"));
					arrayList.add(DMUtil.get2String("FirstTrialTime",cTime));
					
					arrayList.add(DMUtil.get2String("OPERATOR","sys"));
					arrayList.add(DMUtil.get2String("MAKEDATE","DATE'"+cDate+"'"));
					arrayList.add(DMUtil.get2String("MAKETIME",cTime));
					arrayList.add(DMUtil.get2String("MODIFYDATE","DATE'"+cDate+"'"));
					arrayList.add(DMUtil.get2String("MODIFYTIME",cTime));

					ttSqlStr = DMUtil.getSBSql("LCCont", arrayList);

					cLogger.info(ttSqlStr);
					cLogger.info(tRecordNo);
					//ChangeCharset changeCharset = new ChangeCharset();
					//String ss = new ChangeCharset().toUTF_8(ttSqlStr+";"+"\n");
						buff=(ttSqlStr+";"+"\n").getBytes("utf-8");
					//buff=(ttSqlStr+";"+"\n").getBytes();
					    lccont.write(buff,0,buff.length);
					    if(tRecordNo%100 == 0){
							 //mLocalConn.commit();
							 buff=("commit"+";"+"\n").getBytes("utf-8");
							    lccont.write(buff,0,buff.length);
						 }
					    cLogger.info("1");
					   // ttLocalStmt = mLocalConn.createStatement();
					    cLogger.info("2");
//				 try{
//						 if (1 != ttLocalStmt.executeUpdate(ttSqlStr)) {
//							 throw new MidplatException("插入失败！");
//					 }
////					 
////					 
////					
//					 ttLocalStmt.close();
//					 }catch (Exception e) {
//						 cLogger.info(ttSqlStr);
//					System.out.println("有问题保单号:"+sContNo);
//						buff=(sContNo+e.getMessage()).getBytes();    
//					     out.write(buff,0,buff.length);
//						e.printStackTrace();
//					ttLocalStmt.close();
//					}
				}
				cLogger.info("本次同步记录数：" + tRecordNo);
				//mLocalConn.commit();
			} catch (Exception ex) {
				mLocalConn.rollback();
				throw ex;
			}

		} catch (Throwable ex) {
			ex.printStackTrace();
		} finally {
			if (null != mOutResultSet) {
				try {
					mOutResultSet.close();
				} catch (SQLException ex) {
					cLogger.error("关闭OutResultSet异常！", ex);
				}
			}
			if (null != mOutStmt) {
				try {
					mOutStmt.close();
				} catch (SQLException ex) {
					cLogger.error("关闭OutStatement异常！", ex);
				}
			}
			if (null != mOutConn) {
				try {
					mOutConn.close();
				} catch (SQLException ex) {
					cLogger.error("关闭OutConnection异常！", ex);
				}
			}
			if (null != mLocalConn) {
				try {
					mLocalConn.close();
				} catch (SQLException ex) {
					cLogger.error("关闭LocalConnection异常！", ex);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger
				.getLogger("com.sinosoft.midplat.bat.NodeUpdate.main");
		mLogger.info("程序开始...");

		DM_LCCont mBatch = new DM_LCCont();

		mBatch.run();

		mLogger.info("成功结束！");
	}
}
