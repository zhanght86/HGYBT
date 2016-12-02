/*
 * <p>ClassName: ContSet </p>
 * <p>Description: ContSchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: midplat
 * @CreateDate��2010-11-25
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.ContSchema;
import com.sinosoft.utility.*;

public class ContSet extends SchemaSet
{
	// @Method
	public boolean add(ContSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(ContSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(ContSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public ContSchema get(int index)
	{
		ContSchema tSchema = (ContSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, ContSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(ContSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpCont����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			ContSchema aSchema = (ContSchema)this.get(i);
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
			ContSchema aSchema = new ContSchema();
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
		ContSchema tSchema = new ContSchema();
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
