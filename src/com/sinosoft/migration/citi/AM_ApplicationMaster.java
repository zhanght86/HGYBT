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

public class AM_ApplicationMaster extends TimerTask implements XmlTag {
	private static final Logger cLogger = Logger.getLogger(AM_ApplicationMaster.class);

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
			
			String sql = "SELECT * FROM MasterAppInfoAXA where extracted >=20120418 and extracted<20120426";
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
//					out = new FileOutputStream("C:\\Users\\asus\\Desktop\\CitiDM\\ApplicationMasterInfo"+".sql",false);
//				
//				} catch (FileNotFoundException e1) {
//					e1.printStackTrace();
//				} 
//				byte[] buff=new byte[]{};
///////////////////////////////////////		
				
//				ttSqlStr = "delete from ApplicationMasterInfo";
//				cLogger.info(ttSqlStr);
//				ttLocalStmt = mLocalConn.createStatement();
//				ttLocalStmt.executeUpdate(ttSqlStr);
//				ttLocalStmt.close();

				int tRecordNo = 0;
				while (mOutResultSet.next()) {
					int i = mOutResultSet.getRow();
					tRecordNo++;

					ArrayList<String []> arrayList = new ArrayList<String[]>();
					arrayList.add(DMUtil.get2String("SerialNo","91"+NumberUtil.fillStrWith0(String.valueOf(i), 18)));
					
					arrayList.add(DMUtil.get2String("EXTRACTEDDATE",mOutResultSet.getString(1).trim()));
					arrayList.add(DMUtil.get2String("POLICYNO",mOutResultSet.getString(2).trim()));
					arrayList.add(DMUtil.get2String("SOURCECODE",mOutResultSet.getString(3).trim()));
					arrayList.add(DMUtil.get2String("BASICPLANCODE",mOutResultSet.getString(4).trim()));
					arrayList.add(DMUtil.get2String("POLICYSTATUS",mOutResultSet.getString(5).trim()));
					
					
					arrayList.add(DMUtil.get2String("SUMINSURED",mOutResultSet.getString(6).trim()));
					arrayList.add(DMUtil.get2String("APPLICATIONDATE",mOutResultSet.getString(7).trim()));
					arrayList.add(DMUtil.get2String("POLICYCONTRACTDATE",mOutResultSet.getString(8).trim()));
					arrayList.add(DMUtil.get2String("PAYMENTMETHOD",mOutResultSet.getString(9).trim()));
					arrayList.add(DMUtil.get2String("MONTHSDEBITED",mOutResultSet.getString(10).trim()));
					
					arrayList.add(DMUtil.get2String("POLICYCURRENCY",mOutResultSet.getString(11).trim()));
					arrayList.add(DMUtil.get2String("MODALPREMIUM",mOutResultSet.getString(12).trim()));
					arrayList.add(DMUtil.get2String("LUMPSUMPREMIUM",mOutResultSet.getString(13).trim()));
					arrayList.add(DMUtil.get2String("INITLUMPSUMPREMIUM",mOutResultSet.getString(14).trim()));
					arrayList.add(DMUtil.get2String("REGULARTOPUPPREMIUM",mOutResultSet.getString(15).trim()));
					
					arrayList.add(DMUtil.get2String("PAYMODE",mOutResultSet.getString(16).trim()));
					arrayList.add(DMUtil.get2String("STATUSCHANGEDATE",mOutResultSet.getString(17).trim()));
					arrayList.add(DMUtil.get2String("INSUREDIDNO",mOutResultSet.getString(18).trim()));
					arrayList.add(DMUtil.get2String("INSUREDNAME",mOutResultSet.getString(19).trim()));
					arrayList.add(DMUtil.get2String("INSUREDBIRTHDAY",mOutResultSet.getString(20).trim()));
					
					arrayList.add(DMUtil.get2String("INSUREDGENDER",mOutResultSet.getString(21).trim()));
					arrayList.add(DMUtil.get2String("AGEATISSUE",mOutResultSet.getString(22).trim()));
					arrayList.add(DMUtil.get2String("PRODUCEAGENT1",mOutResultSet.getString(23).trim()));
					arrayList.add(DMUtil.get2String("PRODUCEAGENT2",mOutResultSet.getString(24).trim()));
					arrayList.add(DMUtil.get2String("UNITCODE",mOutResultSet.getString(25).trim()));
					
					arrayList.add(DMUtil.get2String("AGENTSOURCECODE",mOutResultSet.getString(26).trim()));
					arrayList.add(DMUtil.get2String("AGENTNO",mOutResultSet.getString(27).trim()));
					arrayList.add(DMUtil.get2String("AGENTTYPE",mOutResultSet.getString(28).trim()));
					arrayList.add(DMUtil.get2String("AGENTNAME",mOutResultSet.getString(29).trim()));
					arrayList.add(DMUtil.get2String("AGENTID",mOutResultSet.getString(30).trim()));
						
					arrayList.add(DMUtil.get2String("MAKEDATE",mOutResultSet.getString(1)));
					arrayList.add(DMUtil.get2String("MAKETIME",sCurTime));
					arrayList.add(DMUtil.get2String("MODIFYDATE",mOutResultSet.getString(1)));
					arrayList.add(DMUtil.get2String("MODIFYTIME",sCurTime));				
					arrayList.add(DMUtil.get2String("DEALDATE",mOutResultSet.getString(1)));
					arrayList.add(DMUtil.get2String("DEALTIME",sCurTime));
					
					ttSqlStr = DMUtil.getSBSql("ApplicationMasterInfo", arrayList);
					cLogger.info(ttSqlStr);
					cLogger.info(tRecordNo);
					
				//	 buff=(ttSqlStr+";"+"\n").getBytes();    
				 //    out.write(buff,0,buff.length);
					 
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

		AM_ApplicationMaster mBatch = new AM_ApplicationMaster();

		mBatch.run();

		mLogger.info("成功结束！");
	}
}
