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

public class DM_FundPriceUp extends TimerTask implements XmlTag {
	private static final Logger cLogger = Logger.getLogger(DM_FundPriceUp.class);

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
				System.out.println("���ŵ�");
			}
			mOutStmt = mOutConn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			mOutStmt.close();
			
			String sql = "SELECT * FROM FundPriceInfoAxa ";
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
//					out = new FileOutputStream("C:\\Users\\asus\\Desktop\\CitiDM\\FundPriceUp"+".sql",true);
//				
//				} catch (FileNotFoundException e1) {
//					e1.printStackTrace();
//				} 
//				byte[] buff=new byte[]{};
///////////////////////////////////////			
				ttSqlStr = "delete from FundPriceUp";
				cLogger.info(ttSqlStr);
				ttLocalStmt = mLocalConn.createStatement();
				ttLocalStmt.executeUpdate(ttSqlStr);
				ttLocalStmt.close();

				int tRecordNo = 0;
				while (mOutResultSet.next()) {
					int i = mOutResultSet.getRow();
					tRecordNo++;

					ArrayList<String []> arrayList = new ArrayList<String[]>();
					arrayList.add(DMUtil.get2String("oid","90"+NumberUtil.fillStrWith0(String.valueOf(i), 18)));
					
					arrayList.add(DMUtil.get2String("FUNDCODE",mOutResultSet.getString(2).trim()));
					arrayList.add(DMUtil.get2String("PRICEEFFECTIVEDATE",mOutResultSet.getString(3).trim()));
					arrayList.add(DMUtil.get2String("CURRENCY",mOutResultSet.getString(4).trim()));
					arrayList.add(DMUtil.get2String("UTILPRICE",mOutResultSet.getString(5).trim()));
					arrayList.add(DMUtil.get2String("OFFERPRICE",mOutResultSet.getString(6).trim()));
					
					
					arrayList.add(DMUtil.get2String("BIDPRICE",mOutResultSet.getString(7).trim()));
					arrayList.add(DMUtil.get2String("HADPROCESSED",mOutResultSet.getString(8).trim()));
					arrayList.add(DMUtil.get2String("MAKEDATE",mOutResultSet.getString(1)));
					arrayList.add(DMUtil.get2String("ModifyDate",mOutResultSet.getString(1)));
					arrayList.add(DMUtil.get2String("EXTRACTEDDATE",mOutResultSet.getString(1)));
			
					
					ttSqlStr = DMUtil.getSBSql("FundPriceUp", arrayList);
					cLogger.info(ttSqlStr);
					cLogger.info(tRecordNo);
					
					 ttLocalStmt = mLocalConn.createStatement();
					 if (1 != ttLocalStmt.executeUpdate(ttSqlStr)) {
					 throw new MidplatException("����ʧ�ܣ�");
					 }
					 ttLocalStmt.close();
				}
				cLogger.info("����ͬ����¼����" + tRecordNo);
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
					cLogger.error("�ر�OutResultSet�쳣��", ex);
				}
			}
			if (null != mOutStmt) {
				try {
					mOutStmt.close();
				} catch (SQLException ex) {
					cLogger.error("�ر�OutStatement�쳣��", ex);
				}
			}
			if (null != mOutConn) {
				try {
					mOutConn.close();
				} catch (SQLException ex) {
					cLogger.error("�ر�OutConnection�쳣��", ex);
				}
			}
			if (null != mLocalConn) {
				try {
					mLocalConn.close();
				} catch (SQLException ex) {
					cLogger.error("�ر�LocalConnection�쳣��", ex);
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Logger mLogger = Logger
				.getLogger("com.sinosoft.midplat.bat.NodeUpdate.main");
		mLogger.info("����ʼ...");

		DM_FundPriceUp mBatch = new DM_FundPriceUp();

		mBatch.run();

		mLogger.info("�ɹ�������");
	}
}
