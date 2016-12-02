package com.sinosoft.migration.citi;

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
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.midplat.MidplatConf;
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

public class DM_AxaPolicyTransaction extends TimerTask implements XmlTag {
	private static final Logger cLogger = Logger.getLogger(DM_AxaPolicyTransaction.class);

	public void run() {

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
			
			String sql = "select extracted,transactionSeqNo,transactionSubSeqNo,policyNo,planCode,linetype,lastPremiumDueDate,accountNo,transactionAmount,transactionDate,CommissionLevel,Commission,transactionCode,modalRegularPremium,regularTopUpPremium,lumpSumPremium,initLumpSumPremium,monthsCovered,policyYear," +
					"transactionTYPE,subTransactionTYPE from AxaPolicyTransaction WHERE DT='CL'";
				//DMUtil.getSql4LocalFile("C:\\Users\\asus\\Documents\\SQL Server Management Studio\\Projects\\LDPerson.sql");
			cLogger.info(sql);
			mOutStmt = mOutConn.createStatement();
			mOutResultSet = mOutStmt.executeQuery(sql);
			
			mLocalConn = new DBConnFactory(tMidplatRoot.getChild(database))
					.getConn();
			mLocalConn.setAutoCommit(false);
			int tCurDate = DateUtil.getCur8Date();
			int tCurTime = DateUtil.getCur6Time();
			String sCurDate = String.valueOf(tCurDate);
			String sCurTime = String.valueOf(tCurTime);
			String cDate = DateUtil.getCur10Date();
			String cTime = DateUtil.getCur8Time();
			try {
				String ttSqlStr = null;
				Statement ttLocalStmt = null;

///////////////////////////////////////
//				FileOutputStream out = null;
//				try {
//					out = new FileOutputStream("C:\\Users\\asus\\Desktop\\CitiDM\\AxaPolicyTransaction"+".sql",false);
//				
//				} catch (FileNotFoundException e1) {
//					e1.printStackTrace();
//				} 
//				byte[] buff=new byte[]{};
///////////////////////////////////////		
				
				ttSqlStr = "delete from AxaPolicyTransaction";
				cLogger.info(ttSqlStr);
				ttLocalStmt = mLocalConn.createStatement();
				ttLocalStmt.executeUpdate(ttSqlStr);
				ttLocalStmt.close();

				int tRecordNo = 0;
				while (mOutResultSet.next()) {
					int i = mOutResultSet.getRow();
					tRecordNo++;

					ArrayList<String []> arrayList = new ArrayList<String[]>();
					arrayList.add(DMUtil.get2String("Oid",String.valueOf(i)));
					
					arrayList.add(DMUtil.get2String("extracted",DateUtil.date10to8(mOutResultSet.getString(1).substring(0,10))));
					arrayList.add(DMUtil.get2String("TransactionSeqNo",mOutResultSet.getString(2)));
					arrayList.add(DMUtil.get2String("TransactionSubSeqNo",mOutResultSet.getString(3)));
					arrayList.add(DMUtil.get2String("policyNo",mOutResultSet.getString(4).trim()));
					arrayList.add(DMUtil.get2String("planCode",mOutResultSet.getString(5).trim()));
					
					String s6 = mOutResultSet.getString(6);
					if(s6 == null || "".equals(s6)){
						s6 = "";
					}
					arrayList.add(DMUtil.get2String("premiumType",s6));
					arrayList.add(DMUtil.get2String("lastPremiumDueDate",DateUtil.date10to8(mOutResultSet.getString(7).substring(0,10))));
					arrayList.add(DMUtil.get2String("accountNo",mOutResultSet.getString(8).trim()));
					arrayList.add(DMUtil.get2String("transactionAmount",mOutResultSet.getString(9).trim()));
					arrayList.add(DMUtil.get2String("transactionDate",DateUtil.date10to8(mOutResultSet.getString(10).substring(0,10))));
					
					String s11 = mOutResultSet.getString(11);
					if(s11 == null || "".equals(s11)){
						s11 = "0.00";
					}
					arrayList.add(DMUtil.get2String("CommissionLevel",s11));
					
					String s12 = mOutResultSet.getString(12);
					if(s12 == null || "".equals(s12)){
						s12 = "0.00";
					}
					arrayList.add(DMUtil.get2String("Commission",s12));
					arrayList.add(DMUtil.get2String("transactionCode",mOutResultSet.getString(13)));
					
					String s = "select CODENAME from ldcode where codetype='"+mOutResultSet.getString(20)
						+"' and code='"+mOutResultSet.getString(21)+"'";
					String ss = new ExeSQL().getOneValue(s);
					arrayList.add(DMUtil.get2String("TransactionDetial",ss));
					
					String s14 = mOutResultSet.getString(14);
					if(s14 == null || "".equals(s14)){
						s14 = "0.00";
					}
					arrayList.add(DMUtil.get2String("modalRegularPremium",s14));
					
					String s15 = mOutResultSet.getString(15);
					if(s15 == null || "".equals(s15)){
						s15 = "0.00";
					}
					arrayList.add(DMUtil.get2String("regularTopUpPremium",s15));
					
					String s16 = mOutResultSet.getString(16);
					if(s16 == null || "".equals(s16)){
						s16 = "0.00";
					}
					arrayList.add(DMUtil.get2String("lumpSumPremium",s16));
					
					String s17 = mOutResultSet.getString(17);
					if(s17 == null || "".equals(s17)){
						s17 = "0.00";
					}
					arrayList.add(DMUtil.get2String("initLumpSumPremium",s17));
					arrayList.add(DMUtil.get2String("monthsCovered",mOutResultSet.getString(18).trim()));
					arrayList.add(DMUtil.get2String("policyYear",mOutResultSet.getString(19).trim()));
			
					arrayList.add(DMUtil.get2String("MAKEDATE",DateUtil.date10to8(mOutResultSet.getString(1).substring(0,10))));
					arrayList.add(DMUtil.get2String("MAKETIME",sCurTime));
					arrayList.add(DMUtil.get2String("DealDate",DateUtil.date10to8(mOutResultSet.getString(1).substring(0,10))));
					arrayList.add(DMUtil.get2String("DealTime",sCurTime));
					
					
					
					ttSqlStr = DMUtil.getSBSql("AxaPolicyTransaction", arrayList);
					cLogger.info(ttSqlStr);
					cLogger.info(tRecordNo);
					
					// buff=(ttSqlStr+";"+"\n").getBytes();    
				     //out.write(buff,0,buff.length);
					 
					 ttLocalStmt = mLocalConn.createStatement();
				 if (1 != ttLocalStmt.executeUpdate(ttSqlStr)) {
					 throw new MidplatException("插入失败！");
					 }
					 ttLocalStmt.close();
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

		DM_AxaPolicyTransaction mBatch = new DM_AxaPolicyTransaction();

		mBatch.run();

		mLogger.info("成功结束！");
	}
}
