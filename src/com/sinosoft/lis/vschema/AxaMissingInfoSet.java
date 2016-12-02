/*
 * <p>ClassName: AxaMissingInfoSet </p>
 * <p>Description: AxaMissingInfoSchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: ��ʢ����
 * @CreateDate��2012-03-16
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.AxaMissingInfoSchema;
import com.sinosoft.utility.*;

public class AxaMissingInfoSet extends SchemaSet
{
	// @Method
	public boolean add(AxaMissingInfoSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(AxaMissingInfoSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(AxaMissingInfoSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public AxaMissingInfoSchema get(int index)
	{
		AxaMissingInfoSchema tSchema = (AxaMissingInfoSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, AxaMissingInfoSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(AxaMissingInfoSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpAxaMissingInfo����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			AxaMissingInfoSchema aSchema = (AxaMissingInfoSchema)this.get(i);
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
			AxaMissingInfoSchema aSchema = new AxaMissingInfoSchema();
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
		AxaMissingInfoSchema tSchema = new AxaMissingInfoSchema();
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
