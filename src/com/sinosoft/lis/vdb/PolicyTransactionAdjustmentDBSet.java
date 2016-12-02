/*
 * <p>ClassName: PolicyTransactionAdjustmentDBSet </p>
 * <p>Description: DB����¼���ݿ�������ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: ��ʢ����
 * @CreateDate��2012-03-24
 */
package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.PolicyTransactionAdjustmentSchema;
import com.sinosoft.lis.vschema.PolicyTransactionAdjustmentSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

public class PolicyTransactionAdjustmentDBSet extends PolicyTransactionAdjustmentSet
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
	public PolicyTransactionAdjustmentDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"PolicyTransactionAdjustment");
		mflag = true;
	}

	public PolicyTransactionAdjustmentDBSet()
	{
		db = new DBOper( "PolicyTransactionAdjustment" );
	}
	// @Method
	public boolean insert()
	{
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			PolicyTransactionAdjustmentSchema aSchema = new PolicyTransactionAdjustmentSchema();
			aSchema.setSchema( (PolicyTransactionAdjustmentSchema)this.get(i) );
			if (db.insert(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "PolicyTransactionAdjustmentDBSet";
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
			PolicyTransactionAdjustmentSchema aSchema = new PolicyTransactionAdjustmentSchema();
			aSchema.setSchema( (PolicyTransactionAdjustmentSchema)this.get(i) );
			if (db.update(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "PolicyTransactionAdjustmentDBSet";
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
			PolicyTransactionAdjustmentSchema aSchema = new PolicyTransactionAdjustmentSchema();
			aSchema.setSchema( (PolicyTransactionAdjustmentSchema)this.get(i) );
			if (db.deleteSQL(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "PolicyTransactionAdjustmentDBSet";
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
			PolicyTransactionAdjustmentSchema aSchema = new PolicyTransactionAdjustmentSchema();
			aSchema.setSchema( (PolicyTransactionAdjustmentSchema)this.get(i) );
			if (db.delete(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "PolicyTransactionAdjustmentDBSet";
				tError.functionName = "delete";
				tError.errorMessage = "����ʧ��!";
				this.mErrors .addOneError(tError);

				return false;
			}
		}
		return true;
	}

}
