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

public class DM_AXAFundTransaction extends TimerTask implements XmlTag {
	private static final Logger cLogger = Logger.getLogger(DM_AXAFundTransaction.class);

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
		
			String sql = "SELECT extracted,policyNo,fundCode,fundNameEn,fundName,transactionDate,transactionType,transactionCode," +
					
					" units,investmentAmount,unitPrice,offerPrice,valuationDate,transactionCharge,transactionAmount " +
					" FROM AXAFundTransaction A";
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
//					out = new FileOutputStream("C:\\Users\\asus\\Desktop\\CitiDM\\ApplicationMaster"+".sql",true);
//				
//				} catch (FileNotFoundException e1) {
//					e1.printStackTrace();
//				} 
//				byte[] buff=new byte[]{};
///////////////////////////////////////		
				
				ttSqlStr = "delete from AXAFundTransaction";
				cLogger.info(ttSqlStr);
				ttLocalStmt = mLocalConn.createStatement();
				ttLocalStmt.executeUpdate(ttSqlStr);
				ttLocalStmt.close();

				int tRecordNo = 0;
				while (mOutResultSet.next()) {
					int i = mOutResultSet.getRow();
					tRecordNo++;
					ExeSQL exeSQL = new ExeSQL();
					String sTransactionCode = mOutResultSet.getString(8).trim();
					
					String TransactionType_oth = exeSQL.getOneValue("select othersign from ldcode where codetype='fund_trx' and code='"+sTransactionCode+"'");
					String TransactionCode_oth = exeSQL.getOneValue("select comcode from ldcode where codetype='fund_trx' and code='"+sTransactionCode+"'");
					String TransactionDesc = exeSQL.getOneValue("select codealias from ldcode where codetype='fund_trx' and code='"+sTransactionCode+"'");
					String TransactionDesc_oth = exeSQL.getOneValue("select codename from ldcode where codetype='fund_trx' and code='"+sTransactionCode+"'");
					
					ArrayList<String []> arrayList = new ArrayList<String[]>();
					arrayList.add(DMUtil.get2String("SerialNo",String.valueOf(i)));
					
					arrayList.add(DMUtil.get2String("ExtractedDate",DateUtil.date10to8(mOutResultSet.getString(1).substring(0,10))));
					arrayList.add(DMUtil.get2String("POLICYNO",mOutResultSet.getString(2).trim()));
					arrayList.add(DMUtil.get2String("FundCode",mOutResultSet.getString(3).trim()));
					arrayList.add(DMUtil.get2String("FundEnglishName",mOutResultSet.getString(4).trim()));
					arrayList.add(DMUtil.get2String("FundChineseName",mOutResultSet.getString(5).trim()));
					
					
					arrayList.add(DMUtil.get2String("TransactionDate",DateUtil.date10to8(mOutResultSet.getString(6).substring(0,10))));
					arrayList.add(DMUtil.get2String("TransactionType",mOutResultSet.getString(7).trim()));
					arrayList.add(DMUtil.get2String("TransactionCode",mOutResultSet.getString(8).trim()));
					arrayList.add(DMUtil.get2String("TransactionType_oth",TransactionType_oth));
					arrayList.add(DMUtil.get2String("TransactionCode_oth",TransactionCode_oth));
					
					arrayList.add(DMUtil.get2String("TransactionDesc",TransactionDesc));
					arrayList.add(DMUtil.get2String("TransactionDesc_oth",TransactionDesc_oth));
					arrayList.add(DMUtil.get2String("TransactionNo",mOutResultSet.getString(9).trim()));
					arrayList.add(DMUtil.get2String("InvestmentAmount",mOutResultSet.getString(10).trim()));
					arrayList.add(DMUtil.get2String("CurrentUnitPrice",mOutResultSet.getString(11).trim()));

					
					arrayList.add(DMUtil.get2String("OfferPrice",mOutResultSet.getString(12).trim()));
					arrayList.add(DMUtil.get2String("ValuationDate",DateUtil.date10to8(mOutResultSet.getString(13).substring(0,10))));
				
					String sc = "";
					if(mOutResultSet.getString(14) != null &&!"".equals(mOutResultSet.getString(14))){
						sc = mOutResultSet.getString(14);
					}
					
					arrayList.add(DMUtil.get2String("TransactionCharge",sc));
					arrayList.add(DMUtil.get2String("TransactionAmount",mOutResultSet.getString(15)));
					
					arrayList.add(DMUtil.get2String("MAKEDATE",DateUtil.date10to8(mOutResultSet.getString(1).substring(0,10))));
					arrayList.add(DMUtil.get2String("MAKETIME",sCurTime));
					arrayList.add(DMUtil.get2String("DealDate",DateUtil.date10to8(mOutResultSet.getString(1).substring(0,10))));
					arrayList.add(DMUtil.get2String("DealTime",sCurTime));
					ttSqlStr = DMUtil.getSBSql("AXAFundTransaction", arrayList);
					cLogger.info(ttSqlStr);
					cLogger.info(tRecordNo);
					
//					 buff=(ttSqlStr+"\n").getBytes();    
//				     out.write(buff,0,buff.length);
					
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

		DM_AXAFundTransaction mBatch = new DM_AXAFundTransaction();

		mBatch.run();

		mLogger.info("成功结束！");
	}
}
