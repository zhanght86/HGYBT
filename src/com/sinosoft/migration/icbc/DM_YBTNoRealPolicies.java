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

public class DM_YBTNoRealPolicies extends TimerTask implements XmlTag {
	private static final Logger cLogger = Logger.getLogger(DM_YBTNoRealPolicies.class);

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
					.getSql4LocalFile("C:\\Users\\asus\\Documents\\SQL Server Management Studio\\Projects\\YBTNorealPolicies.sql");
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

				ttSqlStr = "delete from YBTNOREALPOLICIES ";
				//	 +" d where d.makedate!=date'2012-03-31'";
				cLogger.info(ttSqlStr);
				ttLocalStmt = mLocalConn.createStatement();
				ttLocalStmt.executeUpdate(ttSqlStr);
				ttLocalStmt.close();

				int tRecordNo = 0;
				while (mOutResultSet.next()) {
					int i = mOutResultSet.getRow();
					tRecordNo++;

					cDate = (String) mOutResultSet.getString(15).subSequence(0, 10);
					
					ArrayList<String []> arrayList = new ArrayList<String[]>();
					arrayList.add(DMUtil.get2String("SerialNo","9"+PubFun1.CreateMaxNo("YBTNOREALPOLICIES", 19)));
					arrayList.add(DMUtil.get2String("YNO",mOutResultSet.getString(1).trim()));
					arrayList.add(DMUtil.get2String("YSTUA",mOutResultSet.getString(2).trim()));
					arrayList.add(DMUtil.get2String("YMPREM",mOutResultSet.getString(3).trim()));
					arrayList.add(DMUtil.get2String("YMPREM1",mOutResultSet.getString(4).trim()));
					
					arrayList.add(DMUtil.get2String("YMPREM2",mOutResultSet.getString(5).trim()));
					arrayList.add(DMUtil.get2String("YMPREM3",mOutResultSet.getString(6).trim()));
					arrayList.add(DMUtil.get2String("YNAME",mOutResultSet.getString(7).trim()));
					arrayList.add(DMUtil.get2String("YID",mOutResultSet.getString(8).trim()));
					arrayList.add(DMUtil.get2String("YOWNER",mOutResultSet.getString(9).trim()));
								
					arrayList.add(DMUtil.get2String("YPLAN",mOutResultSet.getString(10).trim()));
					arrayList.add(DMUtil.get2String("YSA",mOutResultSet.getString(11).trim()));
					arrayList.add(DMUtil.get2String("YMODE",mOutResultSet.getString(12).trim()));
					arrayList.add(DMUtil.get2String("YLPRE",mOutResultSet.getString(13).trim()));
					arrayList.add(DMUtil.get2String("YLPREA",mOutResultSet.getString(14).trim()));					

					arrayList.add(DMUtil.get2String("YMDAT","DATE'"+cDate+"'"));
					arrayList.add(DMUtil.get2String("YFLAG",mOutResultSet.getString(17)));
					arrayList.add(DMUtil.get2String("MAKEDATE","DATE'"+cDate+"'"));
					arrayList.add(DMUtil.get2String("MAKETIME",cTime));
					arrayList.add(DMUtil.get2String("ModifyDate","DATE'"+DateUtil.getCur10Date()+"'"));
					arrayList.add(DMUtil.get2String("ModifyTime",cTime));
					arrayList.add(DMUtil.get2String("PROCESSFLAG","0"));

					ttSqlStr = DMUtil.getSBSql("YBTNOREALPOLICIES", arrayList);
					cLogger.info(ttSqlStr);
					cLogger.info(tRecordNo);
					
					 ttLocalStmt = mLocalConn.createStatement();
					 try{
					 if (1 != ttLocalStmt.executeUpdate(ttSqlStr)) {
					 throw new MidplatException("插入失败！");
					 }
					 
					 ttLocalStmt.close();
					 }catch (Exception e) {
						 e.printStackTrace();
						 throw new MidplatException("有问题保单号:"+mOutResultSet.getString(1));
						 
						//System.out.println("有问题保单号:"+mOutResultSet.getString(1));
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

		DM_YBTNoRealPolicies mBatch = new DM_YBTNoRealPolicies();

		mBatch.run();

		mLogger.info("成功结束！");
	}
}
