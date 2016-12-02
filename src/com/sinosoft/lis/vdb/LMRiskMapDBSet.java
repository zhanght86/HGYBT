/*
 * <p>ClassName: LMRiskMapDBSet </p>
 * <p>Description: DB层多记录数据库操作类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2012-02-13
 */
package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.LMRiskMapSchema;
import com.sinosoft.lis.vschema.LMRiskMapSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

public class LMRiskMapDBSet extends LMRiskMapSet
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
	public LMRiskMapDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"LMRiskMap");
		mflag = true;
	}

	public LMRiskMapDBSet()
	{
		db = new DBOper( "LMRiskMap" );
	}
	// @Method
	public boolean insert()
	{
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LMRiskMapSchema aSchema = new LMRiskMapSchema();
			aSchema.setSchema( (LMRiskMapSchema)this.get(i) );
			if (db.insert(aSchema) == false)
			{
				// @@错误处理
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "LMRiskMapDBSet";
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
			LMRiskMapSchema aSchema = new LMRiskMapSchema();
			aSchema.setSchema( (LMRiskMapSchema)this.get(i) );
			if (db.update(aSchema) == false)
			{
				// @@错误处理
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "LMRiskMapDBSet";
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
			LMRiskMapSchema aSchema = new LMRiskMapSchema();
			aSchema.setSchema( (LMRiskMapSchema)this.get(i) );
			if (db.deleteSQL(aSchema) == false)
			{
				// @@错误处理
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "LMRiskMapDBSet";
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
			LMRiskMapSchema aSchema = new LMRiskMapSchema();
			aSchema.setSchema( (LMRiskMapSchema)this.get(i) );
			if (db.delete(aSchema) == false)
			{
				// @@错误处理
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "LMRiskMapDBSet";
				tError.functionName = "delete";
				tError.errorMessage = "操作失败!";
				this.mErrors .addOneError(tError);

				return false;
			}
		}
		return true;
	}

}
