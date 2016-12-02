/*
 * <p>ClassName: IFLogSet </p>
 * <p>Description: IFLogSchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: ��ʢ
 * @CreateDate��2012-03-01
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.IFLogSchema;
import com.sinosoft.utility.*;

public class IFLogSet extends SchemaSet
{
	// @Method
	public boolean add(IFLogSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(IFLogSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(IFLogSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public IFLogSchema get(int index)
	{
		IFLogSchema tSchema = (IFLogSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, IFLogSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(IFLogSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpIFLog����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			IFLogSchema aSchema = (IFLogSchema)this.get(i);
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
			IFLogSchema aSchema = new IFLogSchema();
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
		IFLogSchema tSchema = new IFLogSchema();
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
