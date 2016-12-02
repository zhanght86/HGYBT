/*
 * <p>ClassName: NoRealBlcDtlDBSet </p>
 * <p>Description: DB����¼���ݿ�������ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: �к�
 * @CreateDate��2013-04-03
 */
package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.NoRealBlcDtlSchema;
import com.sinosoft.lis.vschema.NoRealBlcDtlSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

public class NoRealBlcDtlDBSet extends NoRealBlcDtlSet
{
	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: ����Connection
	* flag = false: ������Connection
	**/
	private boolean mflag = false;

	public CErrors mErrors = new CErrors();			// ������Ϣ

	// @Constructor
	public NoRealBlcDtlDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"NoRealBlcDtl");
		mflag = true;
	}

	public NoRealBlcDtlDBSet()
	{
		db = new DBOper( "NoRealBlcDtl" );
	}
	// @Method
	public boolean insert()
	{
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			NoRealBlcDtlSchema aSchema = new NoRealBlcDtlSchema();
			aSchema.setSchema( (NoRealBlcDtlSchema)this.get(i) );
			if (db.insert(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "NoRealBlcDtlDBSet";
				tError.functionName = "insert";
				tError.errorMessage = "����ʧ��!";
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
			NoRealBlcDtlSchema aSchema = new NoRealBlcDtlSchema();
			aSchema.setSchema( (NoRealBlcDtlSchema)this.get(i) );
			if (db.update(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "NoRealBlcDtlDBSet";
				tError.functionName = "update";
				tError.errorMessage = "����ʧ��!";
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
			NoRealBlcDtlSchema aSchema = new NoRealBlcDtlSchema();
			aSchema.setSchema( (NoRealBlcDtlSchema)this.get(i) );
			if (db.deleteSQL(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "NoRealBlcDtlDBSet";
				tError.functionName = "deleteSQL";
				tError.errorMessage = "����ʧ��!";
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
			NoRealBlcDtlSchema aSchema = new NoRealBlcDtlSchema();
			aSchema.setSchema( (NoRealBlcDtlSchema)this.get(i) );
			if (db.delete(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "NoRealBlcDtlDBSet";
				tError.functionName = "delete";
				tError.errorMessage = "����ʧ��!";
				this.mErrors .addOneError(tError);

				return false;
			}
		}
		return true;
	}

}
