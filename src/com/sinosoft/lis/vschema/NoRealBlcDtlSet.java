/*
 * <p>ClassName: NoRealBlcDtlSet </p>
 * <p>Description: NoRealBlcDtlSchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: �к�
 * @CreateDate��2013-04-03
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.NoRealBlcDtlSchema;
import com.sinosoft.utility.*;

public class NoRealBlcDtlSet extends SchemaSet
{
	// @Method
	public boolean add(NoRealBlcDtlSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(NoRealBlcDtlSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(NoRealBlcDtlSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public NoRealBlcDtlSchema get(int index)
	{
		NoRealBlcDtlSchema tSchema = (NoRealBlcDtlSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, NoRealBlcDtlSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(NoRealBlcDtlSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpNoRealBlcDtl����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			NoRealBlcDtlSchema aSchema = (NoRealBlcDtlSchema)this.get(i);
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
			NoRealBlcDtlSchema aSchema = new NoRealBlcDtlSchema();
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
		NoRealBlcDtlSchema tSchema = new NoRealBlcDtlSchema();
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
