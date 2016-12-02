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

public class AM_PolicyTransaction extends TimerTask implements XmlTag {
	private static final Logger cLogger = Logger.getLogger(AM_PolicyTransaction.class);

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

			String sql = "SELECT * FROM PolicyTransactionInfoAxa where extracted >=20120418 and extracted<20120426";
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
//					out = new FileOutputStream("C:\\Users\\asus\\Desktop\\CitiDM\\PolicyTransaction"+".sql",false);
//				
//				} catch (FileNotFoundException e1) {
//					e1.printStackTrace();
//				} 
//				byte[] buff=new byte[]{};
///////////////////////////////////////	
//				ttSqlStr = "delete from PolicyTransactionInfo";
//				cLogger.info(ttSqlStr);
//				ttLocalStmt = mLocalConn.createStatement();
//				ttLocalStmt.executeUpdate(ttSqlStr);
//				ttLocalStmt.close();

				
				///////////////////////////////////////////////////////////////////////////////
				FileOutputStream out = null;
				FileOutputStream lccont = null;
				try {
					out = new FileOutputStream("D:\\新建文件夹\\新建文件夹\\金盛\\文档\\花旗\\花旗提交\\PolicyTransactionInfo_error"+".txt",false);
					lccont = new FileOutputStream("D:\\新建文件夹\\新建文件夹\\金盛\\文档\\花旗\\花旗提交\\PolicyTransactionInfo_utf8"+".sql",false);
				
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} 
				byte[] buff=new byte[]{};
				////////////////////////////////////////////////////////////////////////////////

				
				
				int tRecordNo = 0;
				while (mOutResultSet.next()) {
					int i = mOutResultSet.getRow();
					tRecordNo++;

					ArrayList<String []> arrayList = new ArrayList<String[]>();
					arrayList.add(DMUtil.get2String("SERIALNO","90"+PubFun1.CreateMaxNo("PolicyTransactionIn", 18)));
					arrayList.add(DMUtil.get2String("EXTRACTEDDATE",mOutResultSet.getString(1).trim()));
					arrayList.add(DMUtil.get2String("TRANSACTIONSEQNO",mOutResultSet.getString(2).trim()));
					arrayList.add(DMUtil.get2String("TRANSACTIONSUBSEQNO",mOutResultSet.getString(3).trim()));
					arrayList.add(DMUtil.get2String("POLICYNO",mOutResultSet.getString(4).trim()));
					
					arrayList.add(DMUtil.get2String("PLANCODE",mOutResultSet.getString(5).trim()));
					arrayList.add(DMUtil.get2String("PLANTYPE",mOutResultSet.getString(6).trim()));
					arrayList.add(DMUtil.get2String("TRANSACTIONDATE",mOutResultSet.getString(7).trim()));
					arrayList.add(DMUtil.get2String("TRANSACTIONTYPE",mOutResultSet.getString(8).trim()));
					arrayList.add(DMUtil.get2String("SUBTRANSACTIONTYPE",mOutResultSet.getString(9).trim()));
					
					arrayList.add(DMUtil.get2String("TRANSACTIONAMOUNT",mOutResultSet.getString(10).trim()));
					arrayList.add(DMUtil.get2String("MODALREGULARPREMIUM",mOutResultSet.getString(11).trim()));
					arrayList.add(DMUtil.get2String("REGULARTOPUPPREMIUM",mOutResultSet.getString(12).trim()));
					arrayList.add(DMUtil.get2String("LUMPSUMPREMIUM",mOutResultSet.getString(13).trim()));
					arrayList.add(DMUtil.get2String("INITLUMPSUMPREMIUM",mOutResultSet.getString(14).trim()));

					arrayList.add(DMUtil.get2String("MONTHSCOVERED",mOutResultSet.getString(15).trim()));
					arrayList.add(DMUtil.get2String("PROCESSINGMODE",mOutResultSet.getString(16).trim()));
					arrayList.add(DMUtil.get2String("POLICYYEAR",mOutResultSet.getString(17).trim()));
					
					
					arrayList.add(DMUtil.get2String("MAKEDATE",mOutResultSet.getString(1).trim()));
					arrayList.add(DMUtil.get2String("MAKETIME",sCurTime));
					arrayList.add(DMUtil.get2String("MODIFYDATE",mOutResultSet.getString(1).trim()));
					arrayList.add(DMUtil.get2String("MODIFYTIME",sCurTime));
					arrayList.add(DMUtil.get2String("DEALDATE",mOutResultSet.getString(1).trim()));
					arrayList.add(DMUtil.get2String("DEALTIME",sCurTime));
					
					ttSqlStr = DMUtil.getSBSql("PolicyTransactionInfo", arrayList);
					cLogger.info(ttSqlStr);
					cLogger.info(tRecordNo);
					
					
					/////////////////////////////////////////////////////////////////
					buff=(ttSqlStr+";"+"\n").getBytes("utf-8");
					    lccont.write(buff,0,buff.length);
					    if(tRecordNo%100 == 0){
							 buff=("commit"+";"+"\n").getBytes("utf-8");
							    lccont.write(buff,0,buff.length);
						 }
					    cLogger.info("1");
					    cLogger.info("2");
					/////////////////////////////////////////////////////////////////

					
					
					
//					buff=(ttSqlStr+";"+"\n").getBytes();    
//				     out.write(buff,0,buff.length);
//					
					  ttLocalStmt = mLocalConn.createStatement();
						 try{
							 if (1 != ttLocalStmt.executeUpdate(ttSqlStr)) {
							 throw new MidplatException("插入失败！");
							 }
							 ttLocalStmt.close();
							 }catch (Exception e) {
								 cLogger.info(ttSqlStr);
							     System.out.println("有问题数据:"+mOutResultSet.getString(1)+"--"+mOutResultSet.getString(3));
								 buff=(mOutResultSet.getString(1)+"--"+mOutResultSet.getString(3)+e.getMessage()).getBytes();    
							     out.write(buff,0,buff.length);
								e.printStackTrace();
							ttLocalStmt.close();
							}
				}
				cLogger.info("本次同步记录数：" + tRecordNo);
//				mLocalConn.commit();
			} catch (Exception ex) {
			    ex.printStackTrace();
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

		AM_PolicyTransaction mBatch = new AM_PolicyTransaction();

		mBatch.run();

		mLogger.info("成功结束！");
	}
}
