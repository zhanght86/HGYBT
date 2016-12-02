/*
 * <p>ClassName: YBT_DMS_PolicyTempDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2012-03-01
 */
package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.YBT_DMS_PolicyTempSchema;
import com.sinosoft.lis.vschema.YBT_DMS_PolicyTempSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

public class YBT_DMS_PolicyTempDBSet extends YBT_DMS_PolicyTempSet
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
	public YBT_DMS_PolicyTempDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"YBT_DMS_PolicyTemp");
		mflag = true;
	}

	public YBT_DMS_PolicyTempDBSet()
	{
		db = new DBOper( "YBT_DMS_PolicyTemp" );
	}
	// @Method
	public boolean insert()
	{
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			YBT_DMS_PolicyTempSchema aSchema = new YBT_DMS_PolicyTempSchema();
			aSchema.setSchema( (YBT_DMS_PolicyTempSchema)this.get(i) );
			if (db.insert(aSchema) == false)
			{
				// @@错误处理
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "YBT_DMS_PolicyTempDBSet";
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
			YBT_DMS_PolicyTempSchema aSchema = new YBT_DMS_PolicyTempSchema();
			aSchema.setSchema( (YBT_DMS_PolicyTempSchema)this.get(i) );
			if (db.update(aSchema) == false)
			{
				// @@错误处理
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "YBT_DMS_PolicyTempDBSet";
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
			YBT_DMS_PolicyTempSchema aSchema = new YBT_DMS_PolicyTempSchema();
			aSchema.setSchema( (YBT_DMS_PolicyTempSchema)this.get(i) );
			if (db.deleteSQL(aSchema) == false)
			{
				// @@错误处理
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "YBT_DMS_PolicyTempDBSet";
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
			YBT_DMS_PolicyTempSchema aSchema = new YBT_DMS_PolicyTempSchema();
			aSchema.setSchema( (YBT_DMS_PolicyTempSchema)this.get(i) );
			if (db.delete(aSchema) == false)
			{
				// @@错误处理
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "YBT_DMS_PolicyTempDBSet";
				tError.functionName = "delete";
				tError.errorMessage = "操作失败!";
				this.mErrors .addOneError(tError);

				return false;
			}
		}
		return true;
	}

}
