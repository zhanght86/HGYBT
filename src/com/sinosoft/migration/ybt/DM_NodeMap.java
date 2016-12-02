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

public class DM_NodeMap extends TimerTask implements XmlTag {
	private static final Logger cLogger = Logger.getLogger(DM_NodeMap.class);

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

				ttSqlStr = "delete nodemap D WHERE D.TRANCOM !=12";
				//	 +" d where d.makedate!=date'2012-03-31'";
				cLogger.info(ttSqlStr);
				ttLocalStmt = mLocalConn.createStatement();
				ttLocalStmt.executeUpdate(ttSqlStr);
				ttLocalStmt.close();

				int tRecordNo = 0;
				while (mOutResultSet.next()) {
					int i = mOutResultSet.getRow();
					tRecordNo++;
					
					String MapNo = "9"+NumberUtil.fillStrWith0(String.valueOf(i), 6);
					String TranCom = "0"+mOutResultSet.getString(2);
					String ZoneNo = mOutResultSet.getString(3);
					String NodeNo = mOutResultSet.getString(4);
					String Type = "0";
					//new ExeSQL().execUpdateSQL("delete nodemap a where a.trancom="+mOutResultSet.getString(2)+" and a.zoneno='"+ZoneNo+"'"+" and a.nodeno='"+NodeNo+"'");
					String AgentCom = mOutResultSet.getString(6);
					String AgentComName = mOutResultSet.getString(7);
					
					String sAgentComName = "";
					for(int j =0 ;j<AgentComName.length();j++){
						String s = AgentComName.substring(j,j+1);
					//	System.out.println(s);
						if("－".equals(s)){
							s="-";
						}
						sAgentComName = sAgentComName+s;
					}
					
					String AgentCode = mOutResultSet.getString(8);
					String AgentName = mOutResultSet.getString(9);
					String ManageCom = DMUtil.getComCode(mOutResultSet.getString(10));
					
					String UnitCode = mOutResultSet.getString(11);
					String AgentGrade = "04";
					String AgentCodeGrade = "03";
					String ConAgentNo = mOutResultSet.getString(14);
					String ConStartDate = mOutResultSet.getString(15);
					
					String ConEndDate = mOutResultSet.getString(16);		
					String SaleFlag = mOutResultSet.getString(18);
					String State = mOutResultSet.getString(17);
					String MakeDate = mOutResultSet.getString(19).substring(0,10);
					
					ArrayList<String []> arrayList = new ArrayList<String[]>();
					arrayList.add(DMUtil.get2String("MapNo",MapNo));
					arrayList.add(DMUtil.get2String("TranCom",TranCom.trim()));
					arrayList.add(DMUtil.get2String("ZoneNo",ZoneNo.trim()));
					arrayList.add(DMUtil.get2String("NodeNo",NodeNo.trim()));
					arrayList.add(DMUtil.get2String("Type",Type));
					
					arrayList.add(DMUtil.get2String("AgentCom",AgentCom.trim()));
					arrayList.add(DMUtil.get2String("AgentComName",sAgentComName.trim()));
					arrayList.add(DMUtil.get2String("AgentCode",AgentCode.trim()));
					arrayList.add(DMUtil.get2String("AgentName",AgentName.trim()));
					arrayList.add(DMUtil.get2String("ManageCom",ManageCom.trim()));
					
					arrayList.add(DMUtil.get2String("UnitCode",UnitCode.trim()));
					arrayList.add(DMUtil.get2String("AgentGrade",AgentGrade.trim()));
					arrayList.add(DMUtil.get2String("AgentCodeGrade",AgentCodeGrade.trim()));
					
					if(ConAgentNo != null && !"".equals(ConAgentNo)){
						arrayList.add(DMUtil.get2String("ConAgentNo",ConAgentNo.trim()));
					}
					
					if(ConStartDate != null && !"".equals(ConStartDate)){
						arrayList.add(DMUtil.get2String("ConStartDate","DATE'"+ConStartDate.substring(0,10)+"'"));
					}
					if(ConEndDate != null && !"".equals(ConEndDate)){
						arrayList.add(DMUtil.get2String("ConEndDate","DATE'"+ConEndDate.substring(0,10)+"'"));
					}
					if(!"NULL".equals(SaleFlag)){
						arrayList.add(DMUtil.get2String("SaleFlag",SaleFlag.trim()));
					}else{
						arrayList.add(DMUtil.get2String("SaleFlag",""));
					}
					if(!"NULL".equals(State)){
						arrayList.add(DMUtil.get2String("State",State.trim()));
					}else{
						arrayList.add(DMUtil.get2String("State",""));
					}
					

					arrayList.add(DMUtil.get2String("OPERATOR","sys"));
					arrayList.add(DMUtil.get2String("MAKEDATE",DateUtil.date10to8(MakeDate)));
					arrayList.add(DMUtil.get2String("MAKETIME",String.valueOf(DateUtil.getCur6Time())));
					arrayList.add(DMUtil.get2String("MODIFYDATE",DateUtil.date10to8(cDate)));
					arrayList.add(DMUtil.get2String("MODIFYTIME",String.valueOf(DateUtil.getCur6Time())));

					ttSqlStr = DMUtil.getSBSql("NodeMap", arrayList);
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

		DM_NodeMap mBatch = new DM_NodeMap();

		mBatch.run();

		mLogger.info("成功结束！");
	}
}
