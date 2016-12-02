/*
 * <p>ClassName: AxaMissingInfoDBSet </p>
 * <p>Description: DB����¼���ݿ�������ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: ��ʢ����
 * @CreateDate��2012-03-16
 */
package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.AxaMissingInfoSchema;
import com.sinosoft.lis.vschema.AxaMissingInfoSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

public class AxaMissingInfoDBSet extends AxaMissingInfoSet
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
	public AxaMissingInfoDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"AxaMissingInfo");
		mflag = true;
	}

	public AxaMissingInfoDBSet()
	{
		db = new DBOper( "AxaMissingInfo" );
	}
	// @Method
	public boolean insert()
	{
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			AxaMissingInfoSchema aSchema = new AxaMissingInfoSchema();
			aSchema.setSchema( (AxaMissingInfoSchema)this.get(i) );
			if (db.insert(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "AxaMissingInfoDBSet";
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
			AxaMissingInfoSchema aSchema = new AxaMissingInfoSchema();
			aSchema.setSchema( (AxaMissingInfoSchema)this.get(i) );
			if (db.update(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "AxaMissingInfoDBSet";
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
			AxaMissingInfoSchema aSchema = new AxaMissingInfoSchema();
			aSchema.setSchema( (AxaMissingInfoSchema)this.get(i) );
			if (db.deleteSQL(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "AxaMissingInfoDBSet";
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
			AxaMissingInfoSchema aSchema = new AxaMissingInfoSchema();
			aSchema.setSchema( (AxaMissingInfoSchema)this.get(i) );
			if (db.delete(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "AxaMissingInfoDBSet";
				tError.functionName = "delete";
				tError.errorMessage = "����ʧ��!";
				this.mErrors .addOneError(tError);

				return false;
			}
		}
		return true;
	}

}
