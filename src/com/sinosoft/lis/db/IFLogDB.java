/*
 * <p>ClassName: IFLogDB </p>
 * <p>Description: DB层数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2012-03-01
 */
package com.sinosoft.lis.db;

import java.sql.*;
import com.sinosoft.lis.schema.IFLogSchema;
import com.sinosoft.lis.vschema.IFLogSet;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.utility.*;

public class IFLogDB extends IFLogSchema
{
	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;

	public CErrors mErrors = new CErrors();		// 错误信息

	// @Constructor
	public IFLogDB( Connection tConnection )
	{
		con = tConnection;
		db = new DBOper( con, "IFLog" );
		mflag = true;
	}

	public IFLogDB()
	{
		con = null;
		db = new DBOper( "IFLog" );
		mflag = false;
	}

	// @Method
	public boolean insert()
	{
		IFLogSchema tSchema = this.getSchema();
		if (!db.insert(tSchema))
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "IFLogDB";
			tError.functionName = "insert";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);

			return false;
		}

		return true;
	}

	public boolean update()
	{
		IFLogSchema tSchema = this.getSchema();
		if (!db.update(tSchema))
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "IFLogDB";
			tError.functionName = "update";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);

			return false;
		}

		return true;
	}

	public boolean deleteSQL()
	{
		IFLogSchema tSchema = this.getSchema();
		if (!db.deleteSQL(tSchema))
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "IFLogDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);

			return false;
		}

		return true;
	}

	public boolean delete()
	{
		IFLogSchema tSchema = this.getSchema();
		if (!db.delete(tSchema))
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "IFLogDB";
			tError.functionName = "delete";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);

			return false;
		}

		return true;
	}

	public int getCount()
	{
		IFLogSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "IFLogDB";
			tError.functionName = "getCount";
			tError.errorMessage = "操作失败!";
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
			SQLString sqlObj = new SQLString("IFLog");
			IFLogSchema aSchema = this.getSchema();
			sqlObj.setSQL(6,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;
				if (this.setSchema(rs,i) == false)
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "IFLogDB";
					tError.functionName = "getInfo";
					tError.errorMessage = "取数失败!";
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
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "IFLogDB";
				tError.functionName = "getInfo";
				tError.errorMessage = "未找到相关数据!";
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
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IFLogDB";
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
	    // 断开数据库连接
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

	public IFLogSet query()
	{
		Statement stmt = null;
		ResultSet rs = null;
		IFLogSet aIFLogSet = new IFLogSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("IFLog");
			IFLogSchema aSchema = this.getSchema();
			sqlObj.setSQL(5,aSchema);
			String sql = sqlObj.getSQL();

			rs = stmt.executeQuery(sql);
			int i = 0;
			while (rs.next())
			{
				i++;
				IFLogSchema s1 = new IFLogSchema();
				s1.setSchema(rs,i);
				aIFLogSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IFLogDB";
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

		return aIFLogSet;
	}

	public IFLogSet executeQuery(String sql)
	{
		Statement stmt = null;
		ResultSet rs = null;
		IFLogSet aIFLogSet = new IFLogSet();

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
				IFLogSchema s1 = new IFLogSchema();
				if (s1.setSchema(rs,i) == false)
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "IFLogDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aIFLogSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IFLogDB";
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

		return aIFLogSet;
	}

	public IFLogSet query(int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		IFLogSet aIFLogSet = new IFLogSet();

	  if( !mflag ) {
		  con = DBConnPool.getConnection();
		}

		try
		{
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
			SQLString sqlObj = new SQLString("IFLog");
			IFLogSchema aSchema = this.getSchema();
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

				IFLogSchema s1 = new IFLogSchema();
				s1.setSchema(rs,i);
				aIFLogSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IFLogDB";
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

		return aIFLogSet;
	}

	public IFLogSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;
		ResultSet rs = null;
		IFLogSet aIFLogSet = new IFLogSet();

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

				IFLogSchema s1 = new IFLogSchema();
				if (s1.setSchema(rs,i) == false)
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "IFLogDB";
					tError.functionName = "executeQuery";
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";
					this.mErrors .addOneError(tError);
				}
				aIFLogSet.add(s1);
			}
			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IFLogDB";
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

		return aIFLogSet;
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
			SQLString sqlObj = new SQLString("IFLog");
			IFLogSchema aSchema = this.getSchema();
			sqlObj.setSQL(2,aSchema);
			String sql = "update IFLog " + sqlObj.getUpdPart() + " where " + strWherePart;

			int operCount = stmt.executeUpdate(sql);
			if( operCount == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "IFLogDB";
				tError.functionName = "update";
				tError.errorMessage = "更新数据失败!";
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
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "IFLogDB";
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
	    // 断开数据库连接
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
