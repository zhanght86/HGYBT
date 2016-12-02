package com.sinosoft.migration.hnw;

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

public class DM_PolicyMaster extends TimerTask implements XmlTag {
	private static final Logger cLogger = Logger.getLogger(DM_PolicyMaster.class);

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
		
			String sql = "select extracted,policyNo, '' 保险公司代码,planNo 险种代码,case when PolicyStatus='Valid' then '1' else '0' end 保单状态,12 保险期间,sumInsured 累计保费," +
					" SubmitDate 投保日期,case when DeliveryDate is null then '' else DeliveryDate end 核保日期,PolicyIssuedDate 签单日,'' 保单到期日,'' 保费期间,'' 支付方式,'' MonthsCovered," +
					" case when PolicyCurrency='RMB' then 'CNY' else 'CNY' end 保单币种,AnnualPremium 期缴保费,'' 趸交追加,'' 首付总保费,'' 定期额外投资保费,"+
					" '' 缴费方式,'' 上期保费收到日,IdNo 被保人身份证件号码,Surname+FirstNames 被保人姓名,DateOfBirth 被保人生日," +
					" case when Gender='1' then 'F' else 'M' end 被保人性别," +
					" age 投保年龄,DateReceivedInMailBox 回执交回日期,'' 代理1,'' 代理2,'' UnitCode,'' 代理公司代码,'' 代理号码,'' 代理类型," +
					" '' 代理人姓名,'' 代理id,created 生成日期,'' 生成时间,Updated 更新日期,'' 更新时间,accumulatedPremiumAmount 累计总保费"+
					" from AxaHNWInfoFormal ";
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
				//把插入语句保存到文件里。。。
				FileOutputStream out = null;
				try {
					out = new FileOutputStream("C:\\AxaHNWInfoFormal_To_PolicyMaster"+".sql",false);
				
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} 
				byte[] buff=new byte[]{};
///////////////////////////////////////	
				ttSqlStr = "delete from PolicyMaster";
				cLogger.info(ttSqlStr);
				ttLocalStmt = mLocalConn.createStatement();
				ttLocalStmt.executeUpdate(ttSqlStr);
				ttLocalStmt.close();

				int tRecordNo = 0;
				while (mOutResultSet.next()) {
					int i = mOutResultSet.getRow();
					tRecordNo++;
					
					
				
					ArrayList<String []> arrayList = new ArrayList<String[]>();
					arrayList.add(DMUtil.get2String("SerialNo","89"+NumberUtil.fillStrWith0(String.valueOf(i), 18)));
					
					arrayList.add(DMUtil.get2String("EXTRACTEDDATE",DateUtil.date10to8(mOutResultSet.getString(1).substring(0,10))));
					arrayList.add(DMUtil.get2String("POLICYNO",mOutResultSet.getString(2).trim()));
					arrayList.add(DMUtil.get2String("SOURCECODE",mOutResultSet.getString(3).trim()));
					arrayList.add(DMUtil.get2String("BASICPLANCODE",mOutResultSet.getString(4).trim()));
					arrayList.add(DMUtil.get2String("POLICYSTATUS",mOutResultSet.getString(5).trim()));
					
					arrayList.add(DMUtil.get2String("POLICYTERM",mOutResultSet.getString(6).trim()));
					arrayList.add(DMUtil.get2String("SUMINSURED",mOutResultSet.getString(7).trim()));
					arrayList.add(DMUtil.get2String("APPLICATIONDATE",DateUtil.date10to8(mOutResultSet.getString(8).substring(0,10))));
					arrayList.add(DMUtil.get2String("POLICYAPPROVEDDATE",DateUtil.date10to8(mOutResultSet.getString(9).substring(0,10))));
					arrayList.add(DMUtil.get2String("POLICYCONTRACTDATE",DateUtil.date10to8(mOutResultSet.getString(10).substring(0,10))));
					
					arrayList.add(DMUtil.get2String("MATURITYDATE",mOutResultSet.getString(11)));
					arrayList.add(DMUtil.get2String("PREMIUMTERM",mOutResultSet.getString(12).trim()));
					arrayList.add(DMUtil.get2String("PAYMENTMETHOD",mOutResultSet.getString(13).trim()));
					arrayList.add(DMUtil.get2String("MONTHSCOVERED",mOutResultSet.getString(14).trim()));
					arrayList.add(DMUtil.get2String("POLICYCURRENCY",mOutResultSet.getString(15).trim()));
					
					arrayList.add(DMUtil.get2String("MODALPREMIUM",mOutResultSet.getString(16).trim()));
					arrayList.add(DMUtil.get2String("LUMPSUMPREMIUM",mOutResultSet.getString(17).trim()));
					arrayList.add(DMUtil.get2String("INITLUMPSUMPREMIUM",mOutResultSet.getString(18).trim()));
					arrayList.add(DMUtil.get2String("REGULARTOPUPPREMIUM",mOutResultSet.getString(19).trim()));
					arrayList.add(DMUtil.get2String("PAYMODE",mOutResultSet.getString(20).trim()));
					
					arrayList.add(DMUtil.get2String("LASTPREMIUMDUEDATE",mOutResultSet.getString(21)));
					arrayList.add(DMUtil.get2String("INSUREDIDNO",mOutResultSet.getString(22).trim()));
					arrayList.add(DMUtil.get2String("INSUREDNAME",mOutResultSet.getString(23).trim()));
					arrayList.add(DMUtil.get2String("INSUREDBIRTHDAY",DateUtil.date10to8(mOutResultSet.getString(24).substring(0,10))));
					arrayList.add(DMUtil.get2String("INSUREDGENDER",mOutResultSet.getString(25).trim()));
					
					arrayList.add(DMUtil.get2String("AGEATISSUE",mOutResultSet.getString(26).trim()));
					arrayList.add(DMUtil.get2String("REPLYTARGETDATE",DateUtil.date10to8(mOutResultSet.getString(27).substring(0,10))));
					arrayList.add(DMUtil.get2String("PRODUCEAGENT1",mOutResultSet.getString(28).trim()));
					arrayList.add(DMUtil.get2String("PRODUCEAGENT2",mOutResultSet.getString(29).trim()));
					arrayList.add(DMUtil.get2String("UNITCODE",mOutResultSet.getString(30).trim()));
					
					arrayList.add(DMUtil.get2String("AGENTSOURCECODE",mOutResultSet.getString(31).trim()));
					arrayList.add(DMUtil.get2String("AGENTNO",mOutResultSet.getString(32).trim()));
					arrayList.add(DMUtil.get2String("AGENTTYPE",mOutResultSet.getString(33).trim()));
					arrayList.add(DMUtil.get2String("AGENTNAME",mOutResultSet.getString(34).trim()));
					arrayList.add(DMUtil.get2String("AGENTID",mOutResultSet.getString(35)));
						
					arrayList.add(DMUtil.get2String("MAKEDATE",DateUtil.date10to8(mOutResultSet.getString(1).substring(0,10))));
					arrayList.add(DMUtil.get2String("MAKETIME",sCurTime));
					arrayList.add(DMUtil.get2String("MODIFYDATE",DateUtil.date10to8(mOutResultSet.getString(1).substring(0,10))));
					arrayList.add(DMUtil.get2String("MODIFYTIME",sCurTime));
					arrayList.add(DMUtil.get2String("TOTALPREMIUM",mOutResultSet.getString(40)));
					
//					ExeSQL exeSQL = new ExeSQL();
//					String pTransaction = exeSQL.getOneValue("select TransactionDate from PolicyTransaction where policyno='"+mOutResultSet.getString(2).trim()+"' and ExtractedDate='"+mOutResultSet.getString(1)+"'");
//                    String fTransaction = exeSQL.getOneValue("select TransactionDate from FundTransaction where policyno='"+mOutResultSet.getString(2).trim()+"' and ExtractedDate='"+mOutResultSet.getString(1)+"'");
//					if(pTransaction!=null && !"".equals(pTransaction)){
//						arrayList.add(DMUtil.get2String("TransactionDate",pTransaction));
//					}else{
//						arrayList.add(DMUtil.get2String("TransactionDate",fTransaction));
//					}
                    
					arrayList.add(DMUtil.get2String("DEALDATE",DateUtil.date10to8(mOutResultSet.getString(1).substring(0,10))));
					arrayList.add(DMUtil.get2String("DEALTIME",sCurTime));
					
					ttSqlStr = DMUtil.getSBSql("PolicyMaster", arrayList);
					cLogger.info(ttSqlStr);
					cLogger.info(tRecordNo);

					//把插入语句写到文件中
					buff=(ttSqlStr+";"+"\n").getBytes();    
				     out.write(buff,0,buff.length);
					
				     
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

		DM_PolicyMaster mBatch = new DM_PolicyMaster();

		mBatch.run();

		mLogger.info("成功结束！");
	}
}
