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

public class DM_AaxAgent extends TimerTask implements XmlTag {
	private static final Logger cLogger = Logger.getLogger(DM_AaxAgent.class);

	public void run() {
		long mStartMillis = System.currentTimeMillis();

		Connection mOutConn = null;
		Statement mOutStmt = null;
		ResultSet mOutResultSet = null;
		Connection mLocalConn = null;
		FileOutputStream out = null;
		FileOutputStream lccont = null;
		try {
			out = new FileOutputStream("C:\\Users\\asus\\Desktop\\axaagent_error"+".txt",false);
			lccont = new FileOutputStream("C:\\Users\\asus\\Desktop\\axaagent_error"+".sql",false);
		
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

			//有问题的代理人054363 053513 053136
			
			String sql = DMUtil
					.getSql4LocalFile("C:\\Users\\asus\\Documents\\SQL Server Management Studio\\Projects\\AxaAgent.sql");
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

				ttSqlStr = "delete axaagent ";
				cLogger.info(ttSqlStr);
				ttLocalStmt = mLocalConn.createStatement();
				ttLocalStmt.executeUpdate(ttSqlStr);
				ttLocalStmt.close();

				int tRecordNo = 0;
				while (mOutResultSet.next()) {
					int i = mOutResultSet.getRow();
					tRecordNo++;
					
					String AgentCode = mOutResultSet.getString(1);
					//new ExeSQL().execUpdateSQL("delete axaagent ");
					String AgentName = mOutResultSet.getString(2);
					if("028".equals(mOutResultSet.getString(3))){
						continue;
					}
					
					String ManageCom = DMUtil.getComCode(mOutResultSet.getString(3));
					
					String UnitCode = mOutResultSet.getString(4);
					
					ArrayList<String []> arrayList = new ArrayList<String[]>();
					arrayList.add(DMUtil.get2String("AgentCode",AgentCode));
					arrayList.add(DMUtil.get2String("AgentName",AgentName));
					arrayList.add(DMUtil.get2String("ManageCom",ManageCom));
					arrayList.add(DMUtil.get2String("UnitCode",UnitCode));
					arrayList.add(DMUtil.get2String("Type","0"));
					
					
					

					arrayList.add(DMUtil.get2String("OPERATOR","sys"));
					arrayList.add(DMUtil.get2String("MAKEDATE",String.valueOf(DateUtil.getCur8Date())));
					arrayList.add(DMUtil.get2String("MAKETIME",String.valueOf(DateUtil.getCur6Time())));
					arrayList.add(DMUtil.get2String("MODIFYDATE",String.valueOf(DateUtil.getCur8Date())));
					arrayList.add(DMUtil.get2String("MODIFYTIME",String.valueOf(DateUtil.getCur6Time())));

					ttSqlStr = DMUtil.getSBSql("AXAAgent", arrayList);
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
						 buff=(AgentCode+"  "+e.getMessage()).getBytes();    
				     out.write(buff,0,buff.length);
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

		DM_AaxAgent mBatch = new DM_AaxAgent();

		mBatch.run();

		mLogger.info("成功结束！");
	}
}
