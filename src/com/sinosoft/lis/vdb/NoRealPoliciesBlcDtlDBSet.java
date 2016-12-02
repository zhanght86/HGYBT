/*
 * <p>ClassName: NoRealPoliciesBlcDtlDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2012-03-03
 */
package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.NoRealPoliciesBlcDtlSchema;
import com.sinosoft.lis.vschema.NoRealPoliciesBlcDtlSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

public class NoRealPoliciesBlcDtlDBSet extends NoRealPoliciesBlcDtlSet
{
	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;

	public CErrors mErrors = new CErrors();			// 错误信息

	// @Constructor
	public NoRealPoliciesBlcDtlDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"NoRealPoliciesBlcDtl");
		mflag = true;
	}

	public NoRealPoliciesBlcDtlDBSet()
	{
		db = new DBOper( "NoRealPoliciesBlcDtl" );
	}
	// @Method
	public boolean insert()
	{
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			NoRealPoliciesBlcDtlSchema aSchema = new NoRealPoliciesBlcDtlSchema();
			aSchema.setSchema( (NoRealPoliciesBlcDtlSchema)this.get(i) );
			if (db.insert(aSchema) == false)
			{
				// @@错误处理
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "NoRealPoliciesBlcDtlDBSet";
				tError.functionName = "insert";
				tError.errorMessage = "操作失败!";
				this.mErrors .addOneError(tError);

				return false;
			}
		}
		return true;
	}

	public boolean update()
	{
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			NoRealPoliciesBlcDtlSchema aSchema = new NoRealPoliciesBlcDtlSchema();
			aSchema.setSchema( (NoRealPoliciesBlcDtlSchema)this.get(i) );
			if (db.update(aSchema) == false)
			{
				// @@错误处理
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "NoRealPoliciesBlcDtlDBSet";
				tError.functionName = "update";
				tError.errorMessage = "操作失败!";
				this.mErrors .addOneError(tError);

				return false;
			}
		}
		return true;
	}

	public boolean deleteSQL()
	{
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			NoRealPoliciesBlcDtlSchema aSchema = new NoRealPoliciesBlcDtlSchema();
			aSchema.setSchema( (NoRealPoliciesBlcDtlSchema)this.get(i) );
			if (db.deleteSQL(aSchema) == false)
			{
				// @@错误处理
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "NoRealPoliciesBlcDtlDBSet";
				tError.functionName = "deleteSQL";
				tError.errorMessage = "操作失败!";
				this.mErrors .addOneError(tError);

				return false;
			}
		}
		return true;
	}

	public boolean delete()
	{
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			NoRealPoliciesBlcDtlSchema aSchema = new NoRealPoliciesBlcDtlSchema();
			aSchema.setSchema( (NoRealPoliciesBlcDtlSchema)this.get(i) );
			if (db.delete(aSchema) == false)
			{
				// @@错误处理
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "NoRealPoliciesBlcDtlDBSet";
				tError.functionName = "delete";
				tError.errorMessage = "操作失败!";
				this.mErrors .addOneError(tError);

				return false;
			}
		}
		return true;
	}

}
