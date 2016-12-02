/*
 * <p>ClassName: TranLogDB </p>
 * <p>Description: DB灞傛暟鎹簱鎿嶄綔绫绘枃浠�</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 閲戠洓
 * @CreateDate锛�011-11-10
 */
package com.sinosoft.lis.db;

import java.sql.*;
import com.sinosoft.lis.schema.TranLogSchema;
import com.sinosoft.lis.vschema.TranLogSet;
import com.sinosoft.utility.*;
/**
 * DB灞傛暟鎹簱鎿嶄綔绫�
 * @author yuantongxin
 */
public class TranLogDB extends TranLogSchema
{
	/**
	 * 瀛楁
	 */
	// @Field
	private Connection con;//杩炴帴瀵硅薄
	private DBOper db;//鏁版嵁搴撴搷浣滃璞�
	/**
	* flag = true: 浼犲叆Connection
	* flag = false: 涓嶄紶鍏onnection
	**/
	private boolean mflag = false;

	public CErrors mErrors = new CErrors();		// 閿欒淇℃伅

	/**
	 * 鏈夊弬鏋勯�鍑芥暟
	 * @param tConnection 杩炴帴瀵硅薄
	 */
	// @Constructor
	public TranLogDB( Connection tConnection )
	{
		con = tConnection;//浼犲叆杩炴帴瀵硅薄
		db = new DBOper( con, "TranLog" );//閫氳繃杩炴帴鍜岃〃鍚嶇敓鎴愭暟鎹簱鎿嶄綔瀵硅薄
		mflag = true;//鏍囪涓哄凡浼犲叆杩炴帴瀵硅薄
	}

	public TranLogDB()
	{
		con = null;//涓嶄紶鍏ヨ繛鎺ュ璞�
		db = new DBOper( "TranLog" );//閫氳繃琛ㄥ悕鐢熸垚鏁版嵁搴撴搷浣滃璞�
		mflag = false;//鏍囪涓烘湭浼犲叆杩炴帴瀵硅薄
	}

	/**
	 * 鎻掑叆
	 * @return 鎿嶄綔缁撴灉(true鎴愬姛锛宖alse澶辫触)
	 */
	// @Method
	public boolean insert()
	{
		TranLogSchema tSchema = this.getSchema();
		if (!db.insert(tSchema))
		{
			// @@閿欒澶勭悊
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "TranLogDB";
			tError.functionName = "insert";
			tError.errorMessage = "鎿嶄綔澶辫触!";
			this.mErrors .addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 鏇存柊
	 * @return 鎿嶄綔缁撴灉(true鎴愬姛锛宖alse澶辫触)
	 */
	public boolean update()
	{
		TranLogSchema tSchema = this.getSchema();
		if (!db.update(tSchema))
		{
			// @@閿欒澶勭悊
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "TranLogDB";
			tError.functionName = "update";
			tError.errorMessage = "鎿嶄綔澶辫触!";
			this.mErrors .addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 鍒犻櫎SQL
	 * @return 鎿嶄綔缁撴灉(true鎴愬姛锛宖alse澶辫触)
	 */
	public boolean deleteSQL()
	{
		TranLogSchema tSchema = this.getSchema();
		if (!db.deleteSQL(tSchema))
		{
			// @@閿欒澶勭悊
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "TranLogDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "鎿嶄綔澶辫触!";
			this.mErrors .addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 鍒犻櫎
	 * @return 鎿嶄綔缁撴灉(true鎴愬姛锛宖alse澶辫触)
	 */
	public boolean delete()
	{
		TranLogSchema tSchema = this.getSchema();
		if (!db.delete(tSchema))
		{
			// @@閿欒澶勭悊
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "TranLogDB";
			tError.functionName = "delete";
			tError.errorMessage = "鎿嶄綔澶辫触!";
			this.mErrors .addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 寰楀埌鎬绘暟
	 * @return 鎿嶄綔缁撴灉(tCount鎴愬姛锛�1澶辫触)
	 */
	public int getCount()
	{
		TranLogSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@閿欒澶勭悊
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "TranLogDB";
			tError.functionName = "getCount";
			tError.errorMessage = "鎿嶄綔澶辫触!";
			this.mErrors .addOneError(tError);

			return -1;
		}

		return tCount;
	}

	/**
	 * 鑾峰彇淇℃伅
	 * @return 鎿嶄綔缁撴灉(true鎴愬姛锛宖alse澶辫触)
	 */
	public boolean getInfo()
	{
		Statement stmt = null;//JDBC杩炴帴涓紶閫扴QL璇彞鐨勫璞�
		ResultSet rs = null;//鏌ヨ缁撴灉杩斿洖鐨勭粨鏋滈泦瀵硅薄
	  
	  if( !mflag ) {//娌℃湁浼犲叆杩炴帴
		  con = DBConnPool.getConnection();//鏁版嵁搴撹繛鎺ユ睜鑾峰緱杩炴帴
		}

		try
		{
			//RSType(缁撴灉绫诲瀷):缁撴灉闆嗙殑娓告爣鍙兘鍚戜笅婊氬姩(娓告爣绉诲姩鎺ュ彛, 鐢ㄦ潵鎿嶄綔绉诲姩娓告爣)锛孯SConcurrency(缁撴灉骞跺彂):涓嶈兘鐢ㄧ粨鏋滈泦鏇存柊鏁版嵁搴撲腑鐨勮〃(鏇存柊鏁版嵁鎺ュ彛, 鐢ㄦ潵鏇存柊褰撳墠娓告爣鎸囧悜浣嶇疆鐨勬暟鎹� 骞跺彲浠ユ洿鏀瑰搴旀暟鎹簱涓殑鏁版嵁)
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);//閫氳繃杩炴帴鍒涘缓璇彞瀵硅薄
			SQLString sqlObj = new SQLString("TranLog");//鏍规嵁琛ㄥ悕鍒涘缓SQL瀛楃涓插璞�
			TranLogSchema aSchema = this.getSchema();//鑾峰緱DB灞�浜ゆ槗鏃ュ織鏁版嵁搴撳璞＄殑闆嗗悎(鐢ㄦ埛) 绫诲璞�
			sqlObj.setSQL(6,aSchema);//璁剧疆SQL瀛楃涓插璞�
			String sql = sqlObj.getSQL();//寰楀埌SQL瀛楃涓�

			rs = stmt.executeQuery(sql);//璇彞瀵硅薄鎵ц鏌ヨ
			int i = 0;//璁℃暟鍣�
			
			while (rs.next())//灏嗗厜鏍囩Щ鍔ㄥ埌涓嬩竴琛岋紝杩唬缁撴灉闆�
			{
				i++;//绱姞璁板綍鏁�
				/*娴嬭瘯浠ｇ爜*/
				System.out.println("-------------------------");
				System.out.println("this.setSchema(rs,i)="+this.setSchema(rs,i)+",i="+i);
				System.out.println("-------------------------");
				if (this.setSchema(rs,i) == false)
				{
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "TranLogDB";
					tError.functionName = "getInfo";
					tError.errorMessage = "鍙栨暟澶辫触!";
					this.mErrors .addOneError(tError);

					try{ rs.close(); } catch( Exception ex ) {}
					try{ stmt.close(); } catch( Exception ex1 ) {}

					if (mflag == false)
					{
						try
						{
							con.close();
						}
						catch(Exception et){}
					}
					return false;
				}
				break;
			}
			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}
System.out.println("i="+i);
			if( i == 0 )
			{
				// @@閿欒澶勭悊
				CError tError = new CError();
				tError.moduleName = "TranLogDB";
				tError.functionName = "getInfo";
				tError.errorMessage = "鏈壘鍒扮浉鍏虫暟鎹�";
				this.mErrors .addOneError(tError);

				if (mflag == false)
				{
					try
					{
						con.close();
					}
					catch(Exception et){}
				}
				return false;
			}
		}
		catch(Exception e)
	    {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "TranLogDB";
			tError.functionName = "getInfo";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}

			if (mflag == false)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
			return false;
	    }
	    // 鏂紑鏁版嵁搴撹繛鎺�
		if (mflag == false)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return true;
	}

	/**
	 * 鏌ヨ
	 * @return 浜ゆ槗鏃ュ織闆�
	 */
	public TranLogSet query()
	{
		Statement stmt = null;//JDBC杩炴帴涓紶閫扴QL璇彞鐨勫璞�
		ResultSet rs = null;//鏌ヨ缁撴灉杩斿洖鐨勭粨鏋滈泦瀵硅薄
		TranLogSet aTranLogSet = new TranLogSet();//浜ゆ槗鏃ュ織闆嗗悎绫诲璞�
		
	   if( !mflag ) {//娌℃湁浼犲叆杩炴帴
		  con = DBConnPool.getConnection();//鏁版嵁搴撹繛鎺ユ睜鑾峰緱杩炴帴
		}

		try
		{
			//RSType(缁撴灉绫诲瀷):缁撴灉闆嗙殑娓告爣鍙兘鍚戜笅婊氬姩(娓告爣绉诲姩鎺ュ彛, 鐢ㄦ潵鎿嶄綔绉诲姩娓告爣)锛孯SConcurrency(缁撴灉骞跺彂):涓嶈兘鐢ㄧ粨鏋滈泦鏇存柊鏁版嵁搴撲腑鐨勮〃(鏇存柊鏁版嵁鎺ュ彛, 鐢ㄦ潵鏇存柊褰撳墠娓告爣鎸囧悜浣嶇疆鐨勬暟鎹� 骞跺彲浠ユ洿鏀瑰搴旀暟鎹簱涓殑鏁版嵁)
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);//閫氳繃杩炴帴鍒涘缓璇彞瀵硅薄
			SQLString sqlObj = new SQLString("TranLog");//鏍规嵁琛ㄥ悕鍒涘缓SQL瀛楃涓插璞�
			TranLogSchema aSchema = this.getSchema();//鑾峰緱DB灞�浜ゆ槗鏃ュ織鏁版嵁搴撳璞＄殑闆嗗悎(鐢ㄦ埛) 绫诲璞�
			sqlObj.setSQL(5,aSchema);//璁剧疆SQL瀛楃涓插璞�
			String sql = sqlObj.getSQL();//寰楀埌SQL瀛楃涓�

			rs = stmt.executeQuery(sql);//璇彞瀵硅薄鎵ц鏌ヨ
			int i = 0;//璁℃暟鍣�
			while (rs.next())//灏嗗厜鏍囩Щ鍔ㄥ埌涓嬩竴琛岋紝杩唬缁撴灉闆�
			{
				i++;//绱姞璁板綍鏁�
				TranLogSchema s1 = new TranLogSchema();//浜ゆ槗鏃ュ織鏁版嵁搴撳璞＄殑闆嗗悎(鐢ㄦ埛) 绫诲璞�
				s1.setSchema(rs,i);//浣跨敤 ResultSet 涓殑绗�i 琛岀粰 Schema 璧嬪�
				aTranLogSet.add(s1);//浜ゆ槗鏃ュ織闆嗗悎绫诲璞℃坊鍔犱氦鏄撴棩蹇楁暟鎹簱瀵硅薄鐨勯泦鍚�鐢ㄦ埛) 绫诲璞�
			}
			try{ rs.close(); } catch( Exception ex ) {}//鍏抽棴缁撴灉闆嗗璞�
			try{ stmt.close(); } catch( Exception ex1 ) {}//鍏抽棴璇彞瀵硅薄
		}
		catch(Exception e)
	    {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "TranLogDB";//妯″潡鍚�
			tError.functionName = "query";//鍑芥暟鍚�
			tError.errorMessage = e.toString();//閿欒淇℃伅
			this.mErrors .addOneError(tError);//娣诲姞涓�釜閿欒

			try{ rs.close(); } catch( Exception ex2 ) {}//鍏抽棴缁撴灉闆嗗璞�
			try{ stmt.close(); } catch( Exception ex3 ) {}//鍏抽棴璇彞瀵硅薄

			if (mflag == false)//娌℃湁浼犲叆杩炴帴
			{
				try
				{
					con.close();//杩炴帴鍏抽棴
				}
				catch(Exception et){}
			}
	    }

		if (mflag == false)//娌℃湁浼犲叆杩炴帴
		{
			try
			{
				con.close();//杩炴帴鍏抽棴
			}
			catch(Exception e){}
		}

		return aTranLogSet;//杩斿洖浜ゆ槗鏃ュ織闆嗗悎绫诲璞�
	}

	/**
	 * 鎵ц鏌ヨ
	 * @param sql 缁撴瀯鍖栨煡璇㈣瑷�
	 * @return 浜ゆ槗鏃ュ織闆嗗悎绫诲璞�
	 */
	public TranLogSet executeQuery(String sql)
	{
		Statement stmt = null;//JDBC杩炴帴涓紶閫扴QL璇彞鐨勫璞�
		ResultSet rs = null;//鏌ヨ缁撴灉杩斿洖鐨勭粨鏋滈泦瀵硅薄
		TranLogSet aTranLogSet = new TranLogSet();//浜ゆ槗鏃ュ織闆嗗悎绫诲璞�
		
	  if( !mflag ) {//娌℃湁浼犲叆杩炴帴
		  con = DBConnPool.getConnection();//鏁版嵁搴撹繛鎺ユ睜鑾峰緱杩炴帴
		}

		try
		{
			//RSType(缁撴灉绫诲瀷):缁撴灉闆嗙殑娓告爣鍙兘鍚戜笅婊氬姩(娓告爣绉诲姩鎺ュ彛, 鐢ㄦ潵鎿嶄綔绉诲姩娓告爣)锛孯SConcurrency(缁撴灉骞跺彂):涓嶈兘鐢ㄧ粨鏋滈泦鏇存柊鏁版嵁搴撲腑鐨勮〃(鏇存柊鏁版嵁鎺ュ彛, 鐢ㄦ潵鏇存柊褰撳墠娓告爣鎸囧悜浣嶇疆鐨勬暟鎹� 骞跺彲浠ユ洿鏀瑰搴旀暟鎹簱涓殑鏁版嵁)
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);//閫氳繃杩炴帴鍒涘缓璇彞瀵硅薄
			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));//璇彞瀵硅薄鎵ц瀛楃涓插鐞嗗伐鍏风被灏嗗瓧绗︿覆杞崲涓篣nicode瀛楃涓叉煡璇�
			
			int i = 0;//璁℃暟鍣�
			while (rs.next())//灏嗗厜鏍囩Щ鍔ㄥ埌涓嬩竴琛岋紝杩唬缁撴灉闆�
			{
				i++;//绱姞璁板綍鏁�
				TranLogSchema s1 = new TranLogSchema();//浜ゆ槗鏃ュ織鏁版嵁搴撳璞＄殑闆嗗悎(鐢ㄦ埛) 绫诲璞�
				if (s1.setSchema(rs,i) == false)//浣跨敤 ResultSet 涓殑绗�i 琛岀粰 Schema 璧嬪�鏄惁澶辫触
				{
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "TranLogDB";//妯″潡鍚�
					tError.functionName = "executeQuery";//鍑芥暟鍚�
					tError.errorMessage = "sql璇彞鏈夎锛岃鏌ョ湅琛ㄥ悕鍙婂瓧娈靛悕淇℃伅!";//閿欒淇℃伅
					this.mErrors .addOneError(tError);//娣诲姞涓�釜閿欒
				}
				aTranLogSet.add(s1);//浜ゆ槗鏃ュ織闆嗗悎绫诲璞℃坊鍔犱氦鏄撴棩蹇楁暟鎹簱瀵硅薄鐨勯泦鍚�鐢ㄦ埛) 绫诲璞�
			}
			try{ rs.close(); } catch( Exception ex ) {}//鍏抽棴缁撴灉闆嗗璞�
			try{ stmt.close(); } catch( Exception ex1 ) {}//鍏抽棴璇彞瀵硅薄
		}
		catch(Exception e)
	    {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "TranLogDB";//妯″潡鍚�
			tError.functionName = "executeQuery";//鍑芥暟鍚�
			tError.errorMessage = e.toString();//閿欒淇℃伅
			this.mErrors .addOneError(tError);//娣诲姞涓�釜閿欒

			try{ rs.close(); } catch( Exception ex2 ) {}//鍏抽棴缁撴灉闆嗗璞�
			try{ stmt.close(); } catch( Exception ex3 ) {}//鍏抽棴璇彞瀵硅薄

			if (mflag == false)//娌℃湁浼犲叆杩炴帴
			{
				try
				{
					con.close();//杩炴帴鍏抽棴
				}
				catch(Exception et){}
			}
	    }

		if (mflag == false)//娌℃湁浼犲叆杩炴帴
		{
			try
			{
				con.close();//杩炴帴鍏抽棴
			}
			catch(Exception e){}
		}

		return aTranLogSet;//杩斿洖浜ゆ槗鏃ュ織闆嗗悎绫诲璞�
	}

	/**
	 * 鏌ヨ
	 * @param nStart 璧风偣
	 * @param nCount 鎬绘暟
	 * @return 浜ゆ槗鏃ュ織闆嗗悎绫�
	 */
	public TranLogSet query(int nStart, int nCount)
	{
		Statement stmt = null;//JDBC杩炴帴涓紶閫扴QL璇彞鐨勫璞�
		ResultSet rs = null;//鏌ヨ缁撴灉杩斿洖鐨勭粨鏋滈泦瀵硅薄
		TranLogSet aTranLogSet = new TranLogSet();//浜ゆ槗鏃ュ織闆嗗悎绫诲璞�

	  if( !mflag ) {//娌℃湁浼犲叆杩炴帴
		  con = DBConnPool.getConnection();//鏁版嵁搴撹繛鎺ユ睜鑾峰緱杩炴帴
		}

		try
		{
			//RSType(缁撴灉绫诲瀷):缁撴灉闆嗙殑娓告爣鍙兘鍚戜笅婊氬姩(娓告爣绉诲姩鎺ュ彛, 鐢ㄦ潵鎿嶄綔绉诲姩娓告爣)锛孯SConcurrency(缁撴灉骞跺彂):涓嶈兘鐢ㄧ粨鏋滈泦鏇存柊鏁版嵁搴撲腑鐨勮〃(鏇存柊鏁版嵁鎺ュ彛, 鐢ㄦ潵鏇存柊褰撳墠娓告爣鎸囧悜浣嶇疆鐨勬暟鎹� 骞跺彲浠ユ洿鏀瑰搴旀暟鎹簱涓殑鏁版嵁)
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);//閫氳繃杩炴帴鍒涘缓璇彞瀵硅薄
			SQLString sqlObj = new SQLString("TranLog");//鏍规嵁琛ㄥ悕鍒涘缓SQL瀛楃涓插璞�
			TranLogSchema aSchema = this.getSchema();//鑾峰緱DB灞�浜ゆ槗鏃ュ織鏁版嵁搴撳璞＄殑闆嗗悎(鐢ㄦ埛) 绫诲璞�
			sqlObj.setSQL(5,aSchema);//璁剧疆SQL瀛楃涓插璞�
			String sql = sqlObj.getSQL();//寰楀埌SQL瀛楃涓�

			rs = stmt.executeQuery(sql);//璇彞瀵硅薄鎵ц鏌ヨ
			int i = 0;//璁℃暟鍣�
			while (rs.next())//灏嗗厜鏍囩Щ鍔ㄥ埌涓嬩竴琛岋紝杩唬缁撴灉闆�
			{
				i++;//绱姞璁板綍鏁�
				if( i < nStart ) {//绱姞璁板綍鏁板皬浜庤捣濮嬪�
					continue;//缁撴潫鍗曟寰幆(缁х画)
				}

				if( i >= nStart + nCount ) {//绱姞璁板綍鏁板ぇ浜庣瓑浜庤捣濮嬪�涓庢�鏁扮殑鍜�
					break;//缁撴潫鏁翠釜寰幆浣�
				}

				TranLogSchema s1 = new TranLogSchema();//鑾峰緱DB灞�浜ゆ槗鏃ュ織鏁版嵁搴撳璞＄殑闆嗗悎(鐢ㄦ埛) 绫诲璞�
				s1.setSchema(rs,i);//浣跨敤 ResultSet 涓殑绗�i 琛岀粰 Schema 璧嬪�
				aTranLogSet.add(s1);//浜ゆ槗鏃ュ織闆嗗悎绫诲璞℃坊鍔犱氦鏄撴棩蹇楁暟鎹簱瀵硅薄鐨勯泦鍚�鐢ㄦ埛) 绫诲璞�
			}
			try{ rs.close(); } catch( Exception ex ) {}//鍏抽棴缁撴灉闆嗗璞�
			try{ stmt.close(); } catch( Exception ex1 ) {}//鍏抽棴璇彞瀵硅薄
		}
		catch(Exception e)
	    {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "TranLogDB";//妯″潡鍚�
			tError.functionName = "query";//鍑芥暟鍚�
			tError.errorMessage = e.toString();//閿欒淇℃伅
			this.mErrors .addOneError(tError);//娣诲姞涓�釜閿欒

			try{ rs.close(); } catch( Exception ex2 ) {}//鍏抽棴缁撴灉闆嗗璞�
			try{ stmt.close(); } catch( Exception ex3 ) {}//鍏抽棴璇彞瀵硅薄

			if (mflag == false)//娌℃湁浼犲叆杩炴帴
			{
				try
				{
					con.close();//杩炴帴鍏抽棴
				}
				catch(Exception et){}
			}
	    }

		if (mflag == false)//娌℃湁浼犲叆杩炴帴
		{
			try
			{
				con.close();//杩炴帴鍏抽棴
			}
			catch(Exception e){}
		}

		return aTranLogSet;//杩斿洖浜ゆ槗鏃ュ織闆嗗悎绫诲璞�
	}

	/**
	 * 鎵ц鏌ヨ
	 * @param sql 缁撴瀯鍖栨煡璇㈣瑷�
	 * @param nStart 璧风偣
	 * @param nCount 鎬绘暟
	 * @return 浜ゆ槗鏃ュ織闆嗗悎绫�
	 */
	public TranLogSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;//JDBC杩炴帴涓紶閫扴QL璇彞鐨勫璞�
		ResultSet rs = null;//鏌ヨ缁撴灉杩斿洖鐨勭粨鏋滈泦瀵硅薄
		TranLogSet aTranLogSet = new TranLogSet();//浜ゆ槗鏃ュ織闆嗗悎绫诲璞�

	  if( !mflag ) {//娌℃湁浼犲叆杩炴帴
		  con = DBConnPool.getConnection();//鏁版嵁搴撹繛鎺ユ睜鑾峰緱杩炴帴
		}

		try
		{
			//RSType(缁撴灉绫诲瀷):缁撴灉闆嗙殑娓告爣鍙兘鍚戜笅婊氬姩(娓告爣绉诲姩鎺ュ彛, 鐢ㄦ潵鎿嶄綔绉诲姩娓告爣)锛孯SConcurrency(缁撴灉骞跺彂):涓嶈兘鐢ㄧ粨鏋滈泦鏇存柊鏁版嵁搴撲腑鐨勮〃(鏇存柊鏁版嵁鎺ュ彛, 鐢ㄦ潵鏇存柊褰撳墠娓告爣鎸囧悜浣嶇疆鐨勬暟鎹� 骞跺彲浠ユ洿鏀瑰搴旀暟鎹簱涓殑鏁版嵁)
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);//閫氳繃杩炴帴鍒涘缓璇彞瀵硅薄
			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));//璇彞瀵硅薄鎵ц瀛楃涓插鐞嗗伐鍏风被灏嗗瓧绗︿覆杞崲涓篣nicode瀛楃涓叉煡璇�
			int i = 0;//璁℃暟鍣�
			while (rs.next())//灏嗗厜鏍囩Щ鍔ㄥ埌涓嬩竴琛岋紝杩唬缁撴灉闆�
			{
				i++;//绱姞璁板綍鏁�
				if( i < nStart ) {//绱姞璁板綍鏁板皬浜庤捣濮嬪�
					continue;//缁撴潫鍗曟寰幆(缁х画)
				}

				if( i >= nStart + nCount ) {//绱姞璁板綍鏁板ぇ浜庣瓑浜庤捣濮嬪�涓庢�鏁扮殑鍜�
					break;//缁撴潫鏁翠釜寰幆浣�
				}

				TranLogSchema s1 = new TranLogSchema();//鑾峰緱DB灞�浜ゆ槗鏃ュ織鏁版嵁搴撳璞＄殑闆嗗悎(鐢ㄦ埛) 绫诲璞�
				if (s1.setSchema(rs,i) == false)//浣跨敤 ResultSet 涓殑绗�i 琛岀粰 Schema 璧嬪�鏄惁澶辫触
				{
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "TranLogDB";//妯″潡鍚�
					tError.functionName = "executeQuery";//鍑芥暟鍚�
					tError.errorMessage = "sql璇彞鏈夎锛岃鏌ョ湅琛ㄥ悕鍙婂瓧娈靛悕淇℃伅!";//閿欒淇℃伅
					this.mErrors .addOneError(tError);//娣诲姞涓�釜閿欒
				}
				aTranLogSet.add(s1);//浜ゆ槗鏃ュ織闆嗗悎绫诲璞℃坊鍔犱氦鏄撴棩蹇楁暟鎹簱瀵硅薄鐨勯泦鍚�鐢ㄦ埛) 绫诲璞�
			}
			try{ rs.close(); } catch( Exception ex ) {}//鍏抽棴缁撴灉闆嗗璞�
			try{ stmt.close(); } catch( Exception ex1 ) {}//鍏抽棴璇彞瀵硅薄
		}
		catch(Exception e)
	    {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "TranLogDB";//妯″潡鍚�
			tError.functionName = "executeQuery";//鍑芥暟鍚�
			tError.errorMessage = e.toString();//閿欒淇℃伅
			this.mErrors .addOneError(tError);//娣诲姞涓�釜閿欒

			try{ rs.close(); } catch( Exception ex2 ) {}//鍏抽棴缁撴灉闆嗗璞�
			try{ stmt.close(); } catch( Exception ex3 ) {}//鍏抽棴璇彞瀵硅薄

			if (mflag == false)//娌℃湁浼犲叆杩炴帴
			{
				try
				{
					con.close();//杩炴帴鍏抽棴
				}
				catch(Exception et){}
			}
	    }

		if (mflag == false)//娌℃湁浼犲叆杩炴帴
		{
			try
			{
				con.close();//杩炴帴鍏抽棴
			}
			catch(Exception e){}
		}

		return aTranLogSet;//杩斿洖浜ゆ槗鏃ュ織闆嗗悎绫诲璞�
	}

	/**
	 * 鏇存柊
	 * @param strWherePart 瀛楃涓茬殑涓�儴鍒�
	 * @return 
	 */
	public boolean update(String strWherePart)
	{
		Statement stmt = null;//JDBC杩炴帴涓紶閫扴QL璇彞鐨勫璞�

	  if( !mflag ) {//娌℃湁浼犲叆杩炴帴
		  con = DBConnPool.getConnection();//鏁版嵁搴撹繛鎺ユ睜鑾峰緱杩炴帴
		}

		try
		{
			//RSType(缁撴灉绫诲瀷):缁撴灉闆嗙殑娓告爣鍙兘鍚戜笅婊氬姩(娓告爣绉诲姩鎺ュ彛, 鐢ㄦ潵鎿嶄綔绉诲姩娓告爣)锛孯SConcurrency(缁撴灉骞跺彂):涓嶈兘鐢ㄧ粨鏋滈泦鏇存柊鏁版嵁搴撲腑鐨勮〃(鏇存柊鏁版嵁鎺ュ彛, 鐢ㄦ潵鏇存柊褰撳墠娓告爣鎸囧悜浣嶇疆鐨勬暟鎹� 骞跺彲浠ユ洿鏀瑰搴旀暟鎹簱涓殑鏁版嵁)
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);//閫氳繃杩炴帴鍒涘缓璇彞瀵硅薄
			SQLString sqlObj = new SQLString("TranLog");//鏍规嵁琛ㄥ悕鍒涘缓SQL瀛楃涓插璞�
			TranLogSchema aSchema = this.getSchema();//鑾峰緱DB灞�浜ゆ槗鏃ュ織鏁版嵁搴撳璞＄殑闆嗗悎(鐢ㄦ埛) 绫诲璞�
			sqlObj.setSQL(2,aSchema);//璁剧疆SQL瀛楃涓插璞�
			String sql = "update TranLog " + sqlObj.getUpdPart() + " where " + strWherePart;//寰楀埌鎷兼帴鍚嶴QL瀛楃涓�

			int operCount = stmt.executeUpdate(sql);//璇彞瀵硅薄鎵ц鏇存柊缁撴瀯鍖栨煡璇㈣鍙ヨ繑鍥炴搷浣滄暟
			if( operCount == 0 )//鎿嶄綔鏁颁负0
			{
				// @@閿欒澶勭悊
				CError tError = new CError();
				tError.moduleName = "TranLogDB";//妯″潡鍚�
				tError.functionName = "update";//鍑芥暟鍚�
				tError.errorMessage = "鏇存柊鏁版嵁澶辫触!";//閿欒淇℃伅
				this.mErrors .addOneError(tError);//娣诲姞涓�釜閿欒

				if (mflag == false)//娌℃湁浼犲叆杩炴帴
				{
					try
					{
						con.close();//杩炴帴鍏抽棴
					}
					catch(Exception et){}
				}
				return false;//鎿嶄綔缁撴灉:澶辫触
			}
		}
		catch(Exception e)
	    {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "TranLogDB";//妯″潡鍚�
			tError.functionName = "update";//鍑芥暟鍚�
			tError.errorMessage = e.toString();//閿欒淇℃伅
			this.mErrors .addOneError(tError);//娣诲姞涓�釜閿欒

			try{ stmt.close(); } catch( Exception ex1 ) {}//鍏抽棴璇彞瀵硅薄

			if (mflag == false)//娌℃湁浼犲叆杩炴帴
			{
				try
				{
					con.close();//杩炴帴鍏抽棴
				}
				catch(Exception et){}
			}
			return false;//鎿嶄綔缁撴灉:澶辫触
	    }
	    // 鏂紑鏁版嵁搴撹繛鎺�
		if (mflag == false)//娌℃湁浼犲叆杩炴帴
		{
			try
			{
				con.close();//杩炴帴鍏抽棴
			}
			catch(Exception e){}
		}

		return true;//鎿嶄綔缁撴灉:鎴愬姛
	}
	
	public static void main(String[] args) {
//		new TranLogDB().getInfo();
		System.out.println(new TranLogDB().insert());
	}

}
