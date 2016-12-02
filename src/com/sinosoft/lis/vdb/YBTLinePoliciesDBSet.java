/*
 * <p>ClassName: YBTLinePoliciesDBSet </p>
 * <p>Description: DB����¼���ݿ�������ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: ��ʢ
 * @CreateDate��2012-03-12
 */
package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.YBTLinePoliciesSchema;
import com.sinosoft.lis.vschema.YBTLinePoliciesSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

public class YBTLinePoliciesDBSet extends YBTLinePoliciesSet
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
	public YBTLinePoliciesDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"YBTLinePolicies");
		mflag = true;
	}

	public YBTLinePoliciesDBSet()
	{
		db = new DBOper( "YBTLinePolicies" );
	}
	// @Method
	public boolean insert()
	{
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			YBTLinePoliciesSchema aSchema = new YBTLinePoliciesSchema();
			aSchema.setSchema( (YBTLinePoliciesSchema)this.get(i) );
			if (db.insert(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "YBTLinePoliciesDBSet";
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
			YBTLinePoliciesSchema aSchema = new YBTLinePoliciesSchema();
			aSchema.setSchema( (YBTLinePoliciesSchema)this.get(i) );
			if (db.update(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "YBTLinePoliciesDBSet";
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
			YBTLinePoliciesSchema aSchema = new YBTLinePoliciesSchema();
			aSchema.setSchema( (YBTLinePoliciesSchema)this.get(i) );
			if (db.deleteSQL(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "YBTLinePoliciesDBSet";
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
			YBTLinePoliciesSchema aSchema = new YBTLinePoliciesSchema();
			aSchema.setSchema( (YBTLinePoliciesSchema)this.get(i) );
			if (db.delete(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "YBTLinePoliciesDBSet";
				tError.functionName = "delete";
				tError.errorMessage = "����ʧ��!";
				this.mErrors .addOneError(tError);

				return false;
			}
		}
		return true;
	}

}
