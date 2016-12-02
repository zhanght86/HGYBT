/*
 * <p>ClassName: AxaPolicyTransactionSet </p>
 * <p>Description: AxaPolicyTransactionSchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: ��ʢ����
 * @CreateDate��2012-03-31
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.AxaPolicyTransactionSchema;
import com.sinosoft.utility.*;

public class AxaPolicyTransactionSet extends SchemaSet
{
	// @Method
	public boolean add(AxaPolicyTransactionSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(AxaPolicyTransactionSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(AxaPolicyTransactionSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public AxaPolicyTransactionSchema get(int index)
	{
		AxaPolicyTransactionSchema tSchema = (AxaPolicyTransactionSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, AxaPolicyTransactionSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(AxaPolicyTransactionSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpAxaPolicyTransaction����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			AxaPolicyTransactionSchema aSchema = (AxaPolicyTransactionSchema)this.get(i);
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
			AxaPolicyTransactionSchema aSchema = new AxaPolicyTransactionSchema();
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
		AxaPolicyTransactionSchema tSchema = new AxaPolicyTransactionSchema();
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
