/*
 * <p>ClassName: LKTransAuthorizationSet </p>
 * <p>Description: LKTransAuthorizationSchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: PhysicalDataModel_1
 * @CreateDate��2010-11-12
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.LKTransAuthorizationSchema;
import com.sinosoft.utility.*;

public class LKTransAuthorizationSet extends SchemaSet
{
	// @Method
	public boolean add(LKTransAuthorizationSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LKTransAuthorizationSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LKTransAuthorizationSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LKTransAuthorizationSchema get(int index)
	{
		LKTransAuthorizationSchema tSchema = (LKTransAuthorizationSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LKTransAuthorizationSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LKTransAuthorizationSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpLKTransAuthorization����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LKTransAuthorizationSchema aSchema = (LKTransAuthorizationSchema)this.get(i);
			strReturn += aSchema.encode();
			if( i != n ) strReturn += SysConst.RECORDSPLITER;
		}

		return strReturn;
	}

	/**
	* ���ݽ��
	* @param: ������ַ���
	* @return: boolean
	**/
	public boolean decode( String str )
	{
		int nBeginPos = 0;
		int nEndPos = str.indexOf('^');
		this.clear();

		while( nEndPos != -1 )
		{
			LKTransAuthorizationSchema aSchema = new LKTransAuthorizationSchema();
			if( aSchema.decode(str.substring(nBeginPos, nEndPos)) == false )
			{
				// @@������
				this.mErrors.copyAllErrors( aSchema.mErrors );
				return false;
			}
			this.add(aSchema);
			nBeginPos = nEndPos + 1;
			nEndPos = str.indexOf('^', nEndPos + 1);
		}
		LKTransAuthorizationSchema tSchema = new LKTransAuthorizationSchema();
		if( tSchema.decode(str.substring(nBeginPos)) == false )
		{
			// @@������
			this.mErrors.copyAllErrors( tSchema.mErrors );
			return false;
		}
		this.add(tSchema);

		return true;
	}

}
