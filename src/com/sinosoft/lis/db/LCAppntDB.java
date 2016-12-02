/*
 * <p>ClassName: LCAppntDB </p>
 * <p>Description: DB灞傛暟鎹簱鎿嶄綔绫绘枃浠�</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 閲戠洓
 * @CreateDate锛�011-11-10
 */
package com.sinosoft.lis.db;

import java.sql.*;
import com.sinosoft.lis.schema.LCAppntSchema;
import com.sinosoft.lis.vschema.LCAppntSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

public class LCAppntDB extends LCAppntSchema
{
	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 浼犲叆Connection
	* flag = false: 涓嶄紶鍏onnection
	**/
	private boolean mflag = false;

	public CErrors mErrors = new CErrors();		// 閿欒淇℃伅

	// @Constructor
	public LCAppntDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LCAppnt" );
		mflag = true;
	}

	public LCAppntDB()
	{
		con = null;
		db = new DBOper( "LCAppnt" );
		mflag = false;
	}

	// @Method
	public boolean insert()
	{
		LCAppntSchema tSchema = this.getSchema();
		if (!db.insert(tSchema))
		{
			// @@閿欒澶勭悊
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCAppntDB";
			tError.functionName = "insert";
			tError.errorMessage = "鎿嶄綔澶辫触!";
			this.mErrors .addOneError(tError);

			return false;
		}

		return true;
	}

	public boolean update()
	{
		LCAppntSchema tSchema = this.getSchema();
		if (!db.update(tSchema))
		{
			// @@閿欒澶勭悊
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCAppntDB";
			tError.functionName = "update";
			tError.errorMessage = "鎿嶄綔澶辫触!";
			this.mErrors .addOneError(tError);

			return false;
		}

		return true;
	}

	public boolean deleteSQL()
	{
		LCAppntSchema tSchema = this.getSchema();
		if (!db.deleteSQL(tSchema))
		{
			// @@閿欒澶勭悊
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCAppntDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "鎿嶄綔澶辫触!";
			this.mErrors .addOneError(tError);

			return false;
		}

		return true;
	}

	public boolean delete()
	{
		LCAppntSchema tSchema = this.getSchema();
		if (!db.delete(tSchema))
		{
			// @@閿欒澶勭悊
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCAppntDB";
			tError.functionName = "delete";
			tError.errorMessage = "鎿嶄綔澶辫触!";
			this.mErrors .addOneError(tError);

			return false;
		}

		return true;
	}

	public int getCount()
	{
		LCAppntSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@閿欒澶勭悊
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LCAppntDB";
			tError.functionName = "getCount";
			tError.errorMessage = "鎿嶄綔澶辫触!";
			this.mErrors .addOneError(tError);

			return -1;
		}

		return tCount;
	}

	public boolean getInfo()
	{
		Statement stmt = null;
		ResultSet rs = null;

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("LCAppnt");
			LCAppntSchema aSchema = this.getSchema();
			sqlObj.setSQL(6,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;
				if (this.setSchema(rs,i) == false)
				{
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "LCAppntDB";
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

			if( i == 0 )
			{
				// @@閿欒澶勭悊
				CError tError = new CError();
				tError.moduleName = "LCAppntDB";
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
			tError.moduleName = "LCAppntDB";
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

	public LCAppntSet query()
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCAppntSet aLCAppntSet = new LCAppntSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("LCAppnt");
			LCAppntSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;
				LCAppntSchema s1 = new LCAppntSchema();
				s1.setSchema(rs,i);
				aLCAppntSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LCAppntDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

			if (mflag == false)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (mflag == false)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLCAppntSet;
	}

	public LCAppntSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCAppntSet aLCAppntSet = new LCAppntSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));
			int i = 0;
			while (rs.next())
			{
				i++;
				LCAppntSchema s1 = new LCAppntSchema();
				if (s1.setSchema(rs,i) == false)
				{
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "LCAppntDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql璇彞鏈夎锛岃鏌ョ湅琛ㄥ悕鍙婂瓧娈靛悕淇℃伅!";
					this.mErrors .addOneError(tError);
				}
				aLCAppntSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LCAppntDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

			if (mflag == false)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (mflag == false)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLCAppntSet;
	}

	public LCAppntSet query(int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCAppntSet aLCAppntSet = new LCAppntSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("LCAppnt");
			LCAppntSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break;
				}

				LCAppntSchema s1 = new LCAppntSchema();
				s1.setSchema(rs,i);
				aLCAppntSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LCAppntDB";
			tError.functionName = "query";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

			if (mflag == false)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (mflag == false)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLCAppntSet;
	}

	public LCAppntSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LCAppntSet aLCAppntSet = new LCAppntSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);

			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));
			int i = 0;
			while (rs.next())
			{
				i++;

				if( i < nStart ) {
					continue;
				}

				if( i >= nStart + nCount ) {
					break;
				}

				LCAppntSchema s1 = new LCAppntSchema();
				if (s1.setSchema(rs,i) == false)
				{
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "LCAppntDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql璇彞鏈夎锛岃鏌ョ湅琛ㄥ悕鍙婂瓧娈靛悕淇℃伅!";
					this.mErrors .addOneError(tError);
				}
				aLCAppntSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LCAppntDB";
			tError.functionName = "executeQuery";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}

			if (mflag == false)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
	    }

		if (mflag == false)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return aLCAppntSet;
	}

	public boolean update(String strWherePart)
	{
		Statement stmt = null;

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("LCAppnt");
			LCAppntSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LCAppnt " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@閿欒澶勭悊
				CError tError = new CError();
				tError.moduleName = "LCAppntDB";
				tError.functionName = "update";
				tError.errorMessage = "鏇存柊鏁版嵁澶辫触!";
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
			tError.moduleName = "LCAppntDB";
			tError.functionName = "update";
			tError.errorMessage = e.toString();
			this.mErrors .addOneError(tError);

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

}
