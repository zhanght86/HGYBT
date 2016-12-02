package com.sinosoft.migration.ybt;

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

public class DM_YBT_DMS_PolicyTemp extends TimerTask implements XmlTag {
	private static final Logger cLogger = Logger.getLogger(DM_YBT_DMS_PolicyTemp.class);

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

			String sql = "SELECT * FROM YBT_DMS_PolicyTemp ";
		//DMUtil.getSql4LocalFile("C:\\Users\\asus\\Documents\\SQL Server Management Studio\\Projects\\LDPerson.sql");
			cLogger.info(sql);
			mOutStmt = mOutConn.createStatement();
			mOutResultSet = mOutStmt.executeQuery(sql);
		
			mLocalConn = new DBConnFactory(tMidplatRoot.getChild(database))
					.getConn();
			mLocalConn.setAutoCommit(false);
			String cDate = DateUtil.getCur10Date();
			String cTime = DateUtil.getCur8Time();
			try {
				String ttSqlStr = null;
				Statement ttLocalStmt = null;

				ttSqlStr = "delete YBT_DMS_PolicyTemp ";
				//	 +" d where d.makedate!=date'2012-03-31'";
				cLogger.info(ttSqlStr);
				ttLocalStmt = mLocalConn.createStatement();
				ttLocalStmt.executeUpdate(ttSqlStr);
				ttLocalStmt.close();

				int tRecordNo = 0;
				while (mOutResultSet.next()) {
					int i = mOutResultSet.getRow();
					tRecordNo++;
					
					ArrayList<String []> arrayList = new ArrayList<String[]>();
					
					arrayList.add(DMUtil.get2String("oid",PubFun1.CreateMaxNo("PrtNoInterface", 20)));
					arrayList.add(DMUtil.get2String("policySerialNumber",mOutResultSet.getString(1)));
					arrayList.add(DMUtil.get2String("applicationNumber",mOutResultSet.getString(2)));
					arrayList.add(DMUtil.get2String("policyNumber",mOutResultSet.getString(3)));
					arrayList.add(DMUtil.get2String("channelOrganizationNumber",mOutResultSet.getString(4)));
					arrayList.add(DMUtil.get2String("carrierOfficer",mOutResultSet.getString(5)));
					arrayList.add(DMUtil.get2String("signedDate","DATE'"+mOutResultSet.getString(6).substring(0,10)+"'"));
					
					ttSqlStr = DMUtil.getSBSql("YBT_DMS_PolicyTemp", arrayList);
					cLogger.info(ttSqlStr);
					cLogger.info(tRecordNo);
					
					 ttLocalStmt = mLocalConn.createStatement();
					 try{
						 if (1 != ttLocalStmt.executeUpdate(ttSqlStr)) {
							 throw new MidplatException("插入失败！");
					 }
					 
					 ttLocalStmt.close();
					 }catch (Exception e) {
						 cLogger.info(ttSqlStr);
						System.out.println("有问题保单号:"+mOutResultSet.getString(1));
						e.printStackTrace();
					}
				}
				cLogger.info("本次同步记录数：" + tRecordNo);
				mLocalConn.commit();
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

		DM_YBT_DMS_PolicyTemp mBatch = new DM_YBT_DMS_PolicyTemp();

		mBatch.run();

		mLogger.info("成功结束！");
	}
}
