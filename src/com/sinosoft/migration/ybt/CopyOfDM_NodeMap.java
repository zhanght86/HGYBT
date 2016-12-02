package com.sinosoft.migration.ybt;

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

public class CopyOfDM_NodeMap extends TimerTask implements XmlTag {
	private static final Logger cLogger = Logger.getLogger(CopyOfDM_NodeMap.class);

	public void run() {
		long mStartMillis = System.currentTimeMillis();

		Connection mOutConn = null;
		Statement mOutStmt = null;
		ResultSet mOutResultSet = null;
		Connection mLocalConn = null;
		FileOutputStream out = null;
		FileOutputStream lccont = null;
		try {
			out = new FileOutputStream("C:\\Users\\asus\\Desktop\\NODEMAP_error"+".txt",false);
			lccont = new FileOutputStream("C:\\Users\\asus\\Desktop\\NODEMAP"+".sql",false);
		
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} 
		byte[] buff=new byte[]{};
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
					.getSql4LocalFile("C:\\Users\\asus\\Documents\\SQL Server Management Studio\\Projects\\NodeMap.sql");
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

				ttSqlStr = "delete ldcode2";
				//	 +" d where d.makedate!=date'2012-03-31'";
				cLogger.info(ttSqlStr);
				ttLocalStmt = mLocalConn.createStatement();
				ttLocalStmt.executeUpdate(ttSqlStr);
				ttLocalStmt.close();

				int tRecordNo = 0;
				while (mOutResultSet.next()) {
					int i = mOutResultSet.getRow();
					tRecordNo++;
				
					String TranCom = "0"+mOutResultSet.getString(2);
					String ZoneNo = mOutResultSet.getString(3);
					String NodeNo = mOutResultSet.getString(4);
					
					
					ArrayList<String []> arrayList = new ArrayList<String[]>();
					arrayList.add(DMUtil.get2String("a",TranCom.trim()));
					arrayList.add(DMUtil.get2String("b",ZoneNo.trim()));
					arrayList.add(DMUtil.get2String("c",NodeNo.trim()));
					
					ttSqlStr = DMUtil.getSBSql("ldcode2", arrayList);
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
						 tRecordNo=tRecordNo-1;
						 
						 buff=(TranCom+"-"+ZoneNo+"-"+NodeNo +"  "+e.getMessage()).getBytes();  
						 out.write(buff,0,buff.length);
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

		CopyOfDM_NodeMap mBatch = new CopyOfDM_NodeMap();

		mBatch.run();

		mLogger.info("成功结束！");
	}
}
