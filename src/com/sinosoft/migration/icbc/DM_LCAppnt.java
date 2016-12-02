package com.sinosoft.migration.icbc;

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

public class DM_LCAppnt extends TimerTask implements XmlTag {
	private static final Logger cLogger = Logger.getLogger(DM_LCAppnt.class);

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
					.getSql4LocalFile("C:\\Users\\asus\\Documents\\SQL Server Management Studio\\Projects\\LCAppnt.sql");
			cLogger.info(sql);
			mOutStmt = mOutConn.createStatement();
			mOutResultSet = mOutStmt.executeQuery(sql);
		
			mLocalConn = new DBConnFactory(tMidplatRoot.getChild(database))
					.getConn();
			mLocalConn.setAutoCommit(false);
			String cDate = DateUtil.getCur10Date();
			String cTime = DateUtil.getCur8Time();
			
			
			FileOutputStream out = null;
			FileOutputStream lccont = null;
			try {
				out = new FileOutputStream("C:\\Users\\asus\\Desktop\\LCAPPNT_error"+".txt",false);
				lccont = new FileOutputStream("C:\\Users\\asus\\Desktop\\LCAPPNT_utf8"+".sql",false);
			
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} 
			byte[] buff=new byte[]{};
			try {
				String ttSqlStr = null;
				Statement ttLocalStmt = null;

				ttSqlStr = "delete from LCAppnt ";
				//	 +" d where d.makedate!=date'2012-03-31'";
				cLogger.info(ttSqlStr);
				ttLocalStmt = mLocalConn.createStatement();
				ttLocalStmt.executeUpdate(ttSqlStr);
				ttLocalStmt.close();

				int tRecordNo = 0;
				while (mOutResultSet.next()) {
					int i = mOutResultSet.getRow();
					tRecordNo++;

					String sName = mOutResultSet.getString(6);
					String sGender = mOutResultSet.getString(7);
					String sBirthDate = mOutResultSet.getString(8);
					String sIDType = mOutResultSet.getString(9);
					String sIDNumber = mOutResultSet.getString(10);
					String sBirthDay = sBirthDate.substring(0, 10);
					 
					String sCustomerNo = DMUtil.getPersonNo(sName,sGender,sBirthDay,sIDType,sIDNumber);
					 
					cDate = (String) mOutResultSet.getString(19).subSequence(0, 10);
					String sLCAppntOid = (String) mOutResultSet.getString(20);
					String sAddressNo = DMUtil.getAddressNo(sCustomerNo,"Applicant",mOutResultSet.getString(1));
					
					ArrayList<String []> arrayList = new ArrayList<String[]>();
					arrayList.add(DMUtil.get2String("CONTNO",mOutResultSet.getString(1)));
					arrayList.add(DMUtil.get2String("GRPCONTNO",mOutResultSet.getString(2)));
					//arrayList.add(DMUtil.get2String("ProposalContNo",mOutResultSet.getString(3)));
					arrayList.add(DMUtil.get2String("PrtNo",mOutResultSet.getString(4)));
					arrayList.add(DMUtil.get2String("AppntNo",sCustomerNo));
					
					arrayList.add(DMUtil.get2String("AppntName",mOutResultSet.getString(6)));
					arrayList.add(DMUtil.get2String("AppntSex",mOutResultSet.getString(7)));
					arrayList.add(DMUtil.get2String("AppntBirthDay","DATE'"+sBirthDay+"'"));
					arrayList.add(DMUtil.get2String("IDType",mOutResultSet.getString(9)));
					arrayList.add(DMUtil.get2String("IDNo",mOutResultSet.getString(10)));
								
					arrayList.add(DMUtil.get2String("NATIVEPLACE",mOutResultSet.getString(11)));
					arrayList.add(DMUtil.get2String("BANKCODE",mOutResultSet.getString(12)));
					arrayList.add(DMUtil.get2String("BANKACCNO",mOutResultSet.getString(13)));
					arrayList.add(DMUtil.get2String("ACCNAME",mOutResultSet.getString(14)));
					arrayList.add(DMUtil.get2String("OCCUPATIONTYPE",mOutResultSet.getString(15)));					

					arrayList.add(DMUtil.get2String("OCCUPATIONCODE",mOutResultSet.getString(16)));
					arrayList.add(DMUtil.get2String("PLURALITYTYPE",mOutResultSet.getString(17)));
					arrayList.add(DMUtil.get2String("MANAGECOM",DMUtil.getComCode(mOutResultSet.getString(18).trim())));

					arrayList.add(DMUtil.get2String("OPERATOR","sys"));
					arrayList.add(DMUtil.get2String("MAKEDATE","DATE'"+cDate+"'"));
					arrayList.add(DMUtil.get2String("MAKETIME",cTime));
					arrayList.add(DMUtil.get2String("MODIFYDATE","DATE'"+cDate+"'"));
					arrayList.add(DMUtil.get2String("MODIFYTIME",cTime));
					
					arrayList.add(DMUtil.get2String("AddressNo",sAddressNo));
					ttSqlStr = DMUtil.getSBSql("LCAppnt", arrayList);
				//	cLogger.info(ttSqlStr);
					//cLogger.info(tRecordNo);
					
					 ttLocalStmt = mLocalConn.createStatement();
					 try{
					 if (1 != ttLocalStmt.executeUpdate(ttSqlStr)) {
					 throw new MidplatException("插入失败！");
					 }
					 
					 ttLocalStmt.close();
					 }catch (Exception e) {
						 buff=(mOutResultSet.getString(1)+e.getMessage()).getBytes();    
					     out.write(buff,0,buff.length);
						System.out.println("有问题保单号:"+mOutResultSet.getString(1));
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

		DM_LCAppnt mBatch = new DM_LCAppnt();

		mBatch.run();

		mLogger.info("成功结束！");
	}
}
