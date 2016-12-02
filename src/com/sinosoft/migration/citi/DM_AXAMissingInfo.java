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

public class DM_AXAMissingInfo extends TimerTask implements XmlTag {
	private static final Logger cLogger = Logger.getLogger(DM_AXAMissingInfo.class);

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
			
			String sql = "SELECT * FROM AXAMissingInfo ";
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
				ttSqlStr = "delete from AXAMissingInfo";
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
					
					arrayList.add(DMUtil.get2String("EXTRACTEDDATE",DateUtil.date10to8(mOutResultSet.getString(2).substring(0,10))));
					arrayList.add(DMUtil.get2String("POLICYNO",mOutResultSet.getString(3).trim()));
					arrayList.add(DMUtil.get2String("APPLICATIONNO",mOutResultSet.getString(15).trim()));
					
					String insuredIDtype=mOutResultSet.getString(4).trim();
					String applicantIdtype=mOutResultSet.getString(5).trim();
					ExeSQL exeSQL = new ExeSQL();
					String insuredIDtype_citi = exeSQL.getOneValue("select codename from ldcode where codetype='citi_idtype' and code='"+insuredIDtype+"'");
					String applicantIdtype_citi=exeSQL.getOneValue("select codename from ldcode where codetype='citi_idtype' and code='"+applicantIdtype+"'");
					
					arrayList.add(DMUtil.get2String("INSUREDIDTYPE",insuredIDtype_citi));
					arrayList.add(DMUtil.get2String("APPLICANTIDTYPE",applicantIdtype_citi));
					
					arrayList.add(DMUtil.get2String("APPLICANTIDNO",mOutResultSet.getString(6).trim()));
					
					String s7 = mOutResultSet.getString(7);
					if(s7 == null || "".equals(s7)){
						s7 = " ";
					}
					arrayList.add(DMUtil.get2String("APPLICANTACCTNO",s7.trim()));
					
					arrayList.add(DMUtil.get2String("CUSTOMERNO",""));
					arrayList.add(DMUtil.get2String("POLICYCANCELCODE",""));
					arrayList.add(DMUtil.get2String("CANCELREASON",mOutResultSet.getString(10).trim()));
					
					arrayList.add(DMUtil.get2String("HADPROCESSED",mOutResultSet.getString(11).trim()));
					
					String s12 = mOutResultSet.getString(12);
					if(s12 == null || "".equals(s12)){
						s12 = " ";
					}
					arrayList.add(DMUtil.get2String("BECOVERED",s12.trim()));
					
					arrayList.add(DMUtil.get2String("MAKEDATE",DateUtil.date10to8(mOutResultSet.getString(2).substring(0,10))));
					arrayList.add(DMUtil.get2String("MAKETIME",sCurTime));
					arrayList.add(DMUtil.get2String("MODIFYDATE",DateUtil.date10to8(mOutResultSet.getString(14).substring(0,10))));
					arrayList.add(DMUtil.get2String("ModifyTime",sCurTime));
					//arrayList.add(DMUtil.get2String("MAKETIME",sCurTime));
					
					ttSqlStr = DMUtil.getSBSql("AXAMissingInfo", arrayList);
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

		DM_AXAMissingInfo mBatch = new DM_AXAMissingInfo();

		mBatch.run();

		mLogger.info("成功结束！");
	}
}
