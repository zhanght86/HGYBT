/*
 * <p>ClassName: FundPriceUpTempDBSet </p>
 * <p>Description: DB����¼���ݿ�������ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: ��ʢ����
 * @CreateDate��2012-03-24
 */
package com.sinosoft.lis.vdb;

import java.sql.*;
import com.sinosoft.lis.schema.FundPriceUpTempSchema;
import com.sinosoft.lis.vschema.FundPriceUpTempSet;
import com.sinosoft.lis.pubfun.*;
import com.sinosoft.utility.*;

public class FundPriceUpTempDBSet extends FundPriceUpTempSet
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
	public FundPriceUpTempDBSet(Connection tConnection)
	{
		con = tConnection;
		db = new DBOper(con,"FundPriceUpTemp");
		mflag = true;
	}

	public FundPriceUpTempDBSet()
	{
		db = new DBOper( "FundPriceUpTemp" );
	}
	// @Method
	public boolean insert()
	{
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			FundPriceUpTempSchema aSchema = new FundPriceUpTempSchema();
			aSchema.setSchema( (FundPriceUpTempSchema)this.get(i) );
			if (db.insert(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "FundPriceUpTempDBSet";
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
			FundPriceUpTempSchema aSchema = new FundPriceUpTempSchema();
			aSchema.setSchema( (FundPriceUpTempSchema)this.get(i) );
			if (db.update(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "FundPriceUpTempDBSet";
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
			FundPriceUpTempSchema aSchema = new FundPriceUpTempSchema();
			aSchema.setSchema( (FundPriceUpTempSchema)this.get(i) );
			if (db.deleteSQL(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "FundPriceUpTempDBSet";
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
			FundPriceUpTempSchema aSchema = new FundPriceUpTempSchema();
			aSchema.setSchema( (FundPriceUpTempSchema)this.get(i) );
			if (db.delete(aSchema) == false)
			{
				// @@������
				this.mErrors.copyAllErrors(db.mErrors);
				CError tError = new CError();
				tError.moduleName = "FundPriceUpTempDBSet";
				tError.functionName = "delete";
				tError.errorMessage = "����ʧ��!";
				this.mErrors .addOneError(tError);

				return false;
			}
		}
		return true;
	}

}
