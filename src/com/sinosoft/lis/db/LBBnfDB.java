/*
 * <p>ClassName: LBBnfDB </p>
 * <p>Description: DB灞傛暟鎹簱鎿嶄綔绫绘枃浠�</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 閲戠洓
 * @CreateDate锛�011-11-10
 */
package com.sinosoft.lis.db;

import java.sql.*;
import com.sinosoft.lis.schema.LBBnfSchema;
import com.sinosoft.lis.vschema.LBBnfSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

public class LBBnfDB extends LBBnfSchema
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
	public LBBnfDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "LBBnf" );
		mflag = true;
	}

	public LBBnfDB()
	{
		con = null;
		db = new DBOper( "LBBnf" );
		mflag = false;
	}

	// @Method
	public boolean insert()
	{
		LBBnfSchema tSchema = this.getSchema();
		if (!db.insert(tSchema))
		{
			// @@閿欒澶勭悊
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBBnfDB";
			tError.functionName = "insert";
			tError.errorMessage = "鎿嶄綔澶辫触!";
			this.mErrors .addOneError(tError);

			return false;
		}

		return true;
	}

	public boolean update()
	{
		LBBnfSchema tSchema = this.getSchema();
		if (!db.update(tSchema))
		{
			// @@閿欒澶勭悊
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBBnfDB";
			tError.functionName = "update";
			tError.errorMessage = "鎿嶄綔澶辫触!";
			this.mErrors .addOneError(tError);

			return false;
		}

		return true;
	}

	public boolean deleteSQL()
	{
		LBBnfSchema tSchema = this.getSchema();
		if (!db.deleteSQL(tSchema))
		{
			// @@閿欒澶勭悊
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBBnfDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "鎿嶄綔澶辫触!";
			this.mErrors .addOneError(tError);

			return false;
		}

		return true;
	}

	public boolean delete()
	{
		LBBnfSchema tSchema = this.getSchema();
		if (!db.delete(tSchema))
		{
			// @@閿欒澶勭悊
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBBnfDB";
			tError.functionName = "delete";
			tError.errorMessage = "鎿嶄綔澶辫触!";
			this.mErrors .addOneError(tError);

			return false;
		}

		return true;
	}

	public int getCount()
	{
		LBBnfSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@閿欒澶勭悊
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "LBBnfDB";
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
			SQLString sqlObj = new SQLString("LBBnf");
			LBBnfSchema aSchema = this.getSchema();
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
					tError.moduleName = "LBBnfDB";
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
				tError.moduleName = "LBBnfDB";
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
			tError.moduleName = "LBBnfDB";
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

	public LBBnfSet query()
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBBnfSet aLBBnfSet = new LBBnfSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("LBBnf");
			LBBnfSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;
				LBBnfSchema s1 = new LBBnfSchema();
				s1.setSchema(rs,i);
				aLBBnfSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LBBnfDB";
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

		return aLBBnfSet;
	}

	public LBBnfSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBBnfSet aLBBnfSet = new LBBnfSet();

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
				LBBnfSchema s1 = new LBBnfSchema();
				if (s1.setSchema(rs,i) == false)
				{
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "LBBnfDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql璇彞鏈夎锛岃鏌ョ湅琛ㄥ悕鍙婂瓧娈靛悕淇℃伅!";
					this.mErrors .addOneError(tError);
				}
				aLBBnfSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LBBnfDB";
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

		return aLBBnfSet;
	}

	public LBBnfSet query(int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBBnfSet aLBBnfSet = new LBBnfSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("LBBnf");
			LBBnfSchema aSchema = this.getSchema();
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

				LBBnfSchema s1 = new LBBnfSchema();
				s1.setSchema(rs,i);
				aLBBnfSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LBBnfDB";
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

		return aLBBnfSet;
	}

	public LBBnfSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		LBBnfSet aLBBnfSet = new LBBnfSet();

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

				LBBnfSchema s1 = new LBBnfSchema();
				if (s1.setSchema(rs,i) == false)
				{
					// @@閿欒澶勭悊
					CError tError = new CError();
					tError.moduleName = "LBBnfDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql璇彞鏈夎锛岃鏌ョ湅琛ㄥ悕鍙婂瓧娈靛悕淇℃伅!";
					this.mErrors .addOneError(tError);
				}
				aLBBnfSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@閿欒澶勭悊
			CError tError = new CError();
			tError.moduleName = "LBBnfDB";
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

		return aLBBnfSet;
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
			SQLString sqlObj = new SQLString("LBBnf");
			LBBnfSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update LBBnf " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@閿欒澶勭悊
				CError tError = new CError();
				tError.moduleName = "LBBnfDB";
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
			tError.moduleName = "LBBnfDB";
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
