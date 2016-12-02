/*
 * <p>ClassName: AxaMissingInfoForPolicyTempDBSet </p>
 * <p>Description: DB����¼���ݿ�������ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: ��ʢ����
 * @CreateDate��2012-03-07
 */
package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.AxaMissingInfoForPolicyTempSchema;
import com.sinosoft.lis.vschema.AxaMissingInfoForPolicyTempSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

public class AxaMissingInfoForPolicyTempDBSet extends AxaMissingInfoForPolicyTempSet
{
	// @Field
	private Connection con;
	private DBOper db;
	/**
	* flag = true: ����Connection
	* flag = false: ������Connection
	**/
	private boolean mflag = false;
	public int iError = 0;
	public CErrors mErrors = new CErrors();			// ������Ϣ

	// @Constructor
	public AxaMissingInfoForPolicyTempDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"AxaMissingInfoForPolicyTemp");
		mflag = true;
	}

	public AxaMissingInfoForPolicyTempDBSet()
	{
		db = new DBOper( "AxaMissingInfoForPolicyTemp" );
	}
	// @Method
	public boolean insert()
	{
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			AxaMissingInfoForPolicyTempSchema aSchema = new AxaMissingInfoForPolicyTempSchema();
			aSchema.setSchema( (AxaMissingInfoForPolicyTempSchema)this.get(i) );
			if (db.insert(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				this.iError = i;
				tError.moduleName = "AxaMissingInfoForPolicyTempDBSet";
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
			AxaMissingInfoForPolicyTempSchema aSchema = new AxaMissingInfoForPolicyTempSchema();
			aSchema.setSchema( (AxaMissingInfoForPolicyTempSchema)this.get(i) );
			if (db.update(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "AxaMissingInfoForPolicyTempDBSet";
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
			AxaMissingInfoForPolicyTempSchema aSchema = new AxaMissingInfoForPolicyTempSchema();
			aSchema.setSchema( (AxaMissingInfoForPolicyTempSchema)this.get(i) );
			if (db.deleteSQL(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "AxaMissingInfoForPolicyTempDBSet";
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
			AxaMissingInfoForPolicyTempSchema aSchema = new AxaMissingInfoForPolicyTempSchema();
			aSchema.setSchema( (AxaMissingInfoForPolicyTempSchema)this.get(i) );
			if (db.delete(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "AxaMissingInfoForPolicyTempDBSet";
				tError.functionName = "delete";
				tError.errorMessage = "����ʧ��!";
				this.mErrors .addOneError(tError);

				return false;
			}
		}
		return true;
	}

}
