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

public class DM_LKNoRealBlcDtl extends TimerTask implements XmlTag {
	private static final Logger cLogger = Logger.getLogger(DM_LKNoRealBlcDtl.class);

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
					.getSql4LocalFile("C:\\Users\\asus\\Documents\\SQL Server Management Studio\\Projects\\LKNoRealBlcDtl.sql");
			cLogger.info(sql);
			mOutStmt = mOutConn.createStatement();
			mOutResultSet = mOutStmt.executeQuery(sql);
		
			mLocalConn = new DBConnFactory(tMidplatRoot.getChild(database))
					.getConn();
			mLocalConn.setAutoCommit(false);
			String cDate = DateUtil.getCur10Date();
			String cLastDate = "2010-04-09";
			String cTime = DateUtil.getCur8Time();
			try {
				String ttSqlStr = null;
				Statement ttLocalStmt = null;

				ttSqlStr = "delete from LKNoRealBlcDtl ";
				//	 +" d where d.makedate!=date'2012-03-31'";
				cLogger.info(ttSqlStr);
				ttLocalStmt = mLocalConn.createStatement();
				ttLocalStmt.executeUpdate(ttSqlStr);
				ttLocalStmt.close();
				int batno = NoFactory.nextBatTranLogNo();
				
				int tRecordNo = 0;
				while (mOutResultSet.next()) {
					int i = mOutResultSet.getRow();
					tRecordNo++;
					
					cDate = (String) mOutResultSet.getString(3).subSequence(0, 10);
					if(!cDate.equals(cLastDate)){
						cLastDate = cDate;
						batno =NoFactory.nextBatTranLogNo();
					}
					
					ArrayList<String []> arrayList = new ArrayList<String[]>();
					arrayList.add(DMUtil.get2String("BlcTranNo",String.valueOf(batno)));
					arrayList.add(DMUtil.get2String("TYPE","2"));
					
					arrayList.add(DMUtil.get2String("ProposalPrtNo",mOutResultSet.getString(1).trim()));
					arrayList.add(DMUtil.get2String("ContNo",mOutResultSet.getString(2).trim()));
					arrayList.add(DMUtil.get2String("TranDATE","DATE'"+cDate+"'"));
					arrayList.add(DMUtil.get2String("Prem",mOutResultSet.getString(4).trim()));
					arrayList.add(DMUtil.get2String("TranCom",mOutResultSet.getString(5).trim()));
					
					arrayList.add(DMUtil.get2String("TranNo",mOutResultSet.getString(6).trim()));
					arrayList.add(DMUtil.get2String("ZoneNo",mOutResultSet.getString(7).trim()));
					arrayList.add(DMUtil.get2String("NodeNo",mOutResultSet.getString(8).trim()));
					arrayList.add(DMUtil.get2String("Rcode",mOutResultSet.getString(9).trim()));

	
					arrayList.add(DMUtil.get2String("MAKEDATE","DATE'"+cDate+"'"));
					arrayList.add(DMUtil.get2String("MAKETIME",cTime));
					arrayList.add(DMUtil.get2String("ModifyDate","DATE'"+DateUtil.getCur10Date()+"'"));
					arrayList.add(DMUtil.get2String("ModifyTime",cTime));

					ttSqlStr = DMUtil.getSBSql("LKNoRealBlcDtl", arrayList);
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

		DM_LKNoRealBlcDtl mBatch = new DM_LKNoRealBlcDtl();

		mBatch.run();

		mLogger.info("成功结束！");
	}
}
