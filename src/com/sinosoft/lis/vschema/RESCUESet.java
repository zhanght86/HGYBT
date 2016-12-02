/*
 * <p>ClassName: RESCUESet </p>
 * <p>Description: RESCUESchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: rescue
 * @CreateDate��2014-08-05
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.RESCUESchema;
import com.sinosoft.utility.*;

public class RESCUESet extends SchemaSet
{
	// @Method
	public boolean add(RESCUESchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(RESCUESet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(RESCUESchema aSchema)
	{
		return super.remove(aSchema);
	}

	public RESCUESchema get(int index)
	{
		RESCUESchema tSchema = (RESCUESchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, RESCUESchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(RESCUESet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpRESCUE����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			RESCUESchema aSchema = (RESCUESchema)this.get(i);
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
			RESCUESchema aSchema = new RESCUESchema();
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
		RESCUESchema tSchema = new RESCUESchema();
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
