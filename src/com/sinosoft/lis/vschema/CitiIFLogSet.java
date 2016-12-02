/*
 * <p>ClassName: CitiIFLogSet </p>
 * <p>Description: CitiIFLogSchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: ��ʢ����
 * @CreateDate��2012-03-24
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.CitiIFLogSchema;
import com.sinosoft.utility.*;

public class CitiIFLogSet extends SchemaSet
{
	// @Method
	public boolean add(CitiIFLogSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(CitiIFLogSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(CitiIFLogSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public CitiIFLogSchema get(int index)
	{
		CitiIFLogSchema tSchema = (CitiIFLogSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, CitiIFLogSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(CitiIFLogSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpCitiIFLog����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			CitiIFLogSchema aSchema = (CitiIFLogSchema)this.get(i);
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
			CitiIFLogSchema aSchema = new CitiIFLogSchema();
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
		CitiIFLogSchema tSchema = new CitiIFLogSchema();
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
