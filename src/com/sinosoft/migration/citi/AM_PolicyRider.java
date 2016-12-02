package com.sinosoft.migration.citi;

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

public class AM_PolicyRider extends TimerTask implements XmlTag {
	private static final Logger cLogger = Logger.getLogger(AM_PolicyRider.class);

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

			String sql = "SELECT * FROM RiderInfoAXA";
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

				ttSqlStr = "delete from PolicyRiderInfo";
				cLogger.info(ttSqlStr);
				ttLocalStmt = mLocalConn.createStatement();
				ttLocalStmt.executeUpdate(ttSqlStr);
				ttLocalStmt.close();

				int tRecordNo = 0;
				while (mOutResultSet.next()) {
					int i = mOutResultSet.getRow();
					tRecordNo++;

					ArrayList<String []> arrayList = new ArrayList<String[]>();
					arrayList.add(DMUtil.get2String("SerialNo","90"+NumberUtil.fillStrWith0(String.valueOf(i), 18)));
					arrayList.add(DMUtil.get2String("ExtractedDate",mOutResultSet.getString(1)));
					arrayList.add(DMUtil.get2String("PolicyNo",mOutResultSet.getString(2)));
					arrayList.add(DMUtil.get2String("RiderPlan",mOutResultSet.getString(3)));
					arrayList.add(DMUtil.get2String("AttachTo",mOutResultSet.getString(4)));
					
					arrayList.add(DMUtil.get2String("PackagedTo",mOutResultSet.getString(5)));
					arrayList.add(DMUtil.get2String("RiderStatus",mOutResultSet.getString(6)));
					arrayList.add(DMUtil.get2String("SumInsured",mOutResultSet.getString(7)));
					arrayList.add(DMUtil.get2String("ModalPremium",mOutResultSet.getString(8)));
					arrayList.add(DMUtil.get2String("EffectiveDate",mOutResultSet.getString(9)));
					
					arrayList.add(DMUtil.get2String("ExpireDate",mOutResultSet.getString(10)));
					arrayList.add(DMUtil.get2String("PolicyTerm",mOutResultSet.getString(11)));
					
					arrayList.add(DMUtil.get2String("MAKEDATE",mOutResultSet.getString(1)));
					arrayList.add(DMUtil.get2String("MAKETIME",sCurTime));
					arrayList.add(DMUtil.get2String("MODIFYDATE",mOutResultSet.getString(1)));
					arrayList.add(DMUtil.get2String("MODIFYTIME",sCurTime));
					
					arrayList.add(DMUtil.get2String("TOTALPREMIUM","0.00"));
					ttSqlStr = DMUtil.getSBSql("PolicyRiderInfo", arrayList);
					cLogger.info(ttSqlStr);
					cLogger.info(tRecordNo);
					
					 ttLocalStmt = mLocalConn.createStatement();
					 if (1 != ttLocalStmt.executeUpdate(ttSqlStr)) {
					 throw new MidplatException("插入失败！");
					 }
					 ttLocalStmt.close();
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

		AM_PolicyRider mBatch = new AM_PolicyRider();

		mBatch.run();

		mLogger.info("成功结束！");
	}
}
