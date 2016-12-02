package com.sinosoft.migration.icbc;

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
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DBConnFactory;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.migration.DMUtil;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;

public class DM_LDCom extends TimerTask implements XmlTag {
	private static final Logger cLogger = Logger.getLogger(DM_LDCom.class);

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
					.getSql4LocalFile("C:\\Users\\asus\\Documents\\SQL Server Management Studio\\Projects\\LCCom.sql");
			cLogger.info(sql);
			mOutStmt = mOutConn.createStatement();
			mOutResultSet = mOutStmt.executeQuery(sql);
		
			mLocalConn = new DBConnFactory(tMidplatRoot.getChild(database))
					.getConn();
			mLocalConn.setAutoCommit(false);
			int tCurDate = DateUtil.getCur8Date();
			int tCurTime = DateUtil.getCur6Time();
			String cDate = DateUtil.getCur10Date();
			String cTime = DateUtil.getCur8Time();
			try {
				String ttSqlStr = null;
				Statement ttLocalStmt = null;

				ttSqlStr = "delete from LCAddress d where d.makedate!=date'2012-03-31'";
				cLogger.info(ttSqlStr);
				ttLocalStmt = mLocalConn.createStatement();
				ttLocalStmt.executeUpdate(ttSqlStr);
				ttLocalStmt.close();

				int tRecordNo = 0;
				while (mOutResultSet.next()) {
					int i = mOutResultSet.getRow();
					tRecordNo++;
					
					String sContNo = mOutResultSet.getString(1);
					String sPersonOid = mOutResultSet.getString(2);
					String sName = mOutResultSet.getString(3);
					String sGender = mOutResultSet.getString(4);
					String sBirthDate = mOutResultSet.getString(5);
					String sIDType = mOutResultSet.getString(6);
					String sIDNumber = mOutResultSet.getString(7);
					
					String sBirthDay = sBirthDate.substring(0, 10);
					String sDATE = (String) mOutResultSet.getString(15).subSequence(0, 10);
					String sKind = mOutResultSet.getString(16);
					String sCustomerNo = DMUtil.getPersonNo(sName,sGender,sBirthDay,sIDType,sIDNumber);
					
					ArrayList<String []> arrayList = new ArrayList<String[]>();
					arrayList.add(DMUtil.get2String("CustomerNo",sCustomerNo));
					arrayList.add(DMUtil.get2String("AddressNo",String.valueOf(i)));
					arrayList.add(DMUtil.get2String("PostalAddress",mOutResultSet.getString(8)));
					arrayList.add(DMUtil.get2String("ZIPCODE",mOutResultSet.getString(9)));
					arrayList.add(DMUtil.get2String("PHONE",mOutResultSet.getString(10)));
					arrayList.add(DMUtil.get2String("HOMEADDRESS",mOutResultSet.getString(11)));
					arrayList.add(DMUtil.get2String("HomePhone",mOutResultSet.getString(12)));
					arrayList.add(DMUtil.get2String("COMPANYPHONE",mOutResultSet.getString(13)));
					arrayList.add(DMUtil.get2String("Mobile",mOutResultSet.getString(14)));
					arrayList.add(DMUtil.get2String("OPERATOR","sys"));
					arrayList.add(DMUtil.get2String("MAKEDATE","DATE'"+sDATE+"'"));
					arrayList.add(DMUtil.get2String("MAKETIME",cTime));
					arrayList.add(DMUtil.get2String("MODIFYDATE","DATE'"+sDATE+"'"));
					arrayList.add(DMUtil.get2String("MODIFYTIME",cTime));
					arrayList.add(DMUtil.get2String("PROVINCE",sKind));
					arrayList.add(DMUtil.get2String("CITY",sContNo));
					arrayList.add(DMUtil.get2String("COUNTY",sPersonOid));

					
					ttSqlStr = DMUtil.getSBSql("LCAddress", arrayList);
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

		DM_LDCom mBatch = new DM_LDCom();

		mBatch.run();

		mLogger.info("成功结束！");
	}
}
