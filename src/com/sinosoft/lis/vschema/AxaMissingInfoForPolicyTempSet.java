/*
 * <p>ClassName: AxaMissingInfoForPolicyTempSet </p>
 * <p>Description: AxaMissingInfoForPolicyTempSchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: ��ʢ����
 * @CreateDate��2012-03-24
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.AxaMissingInfoForPolicyTempSchema;
import com.sinosoft.utility.*;

public class AxaMissingInfoForPolicyTempSet extends SchemaSet
{
	// @Method
	public boolean add(AxaMissingInfoForPolicyTempSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(AxaMissingInfoForPolicyTempSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(AxaMissingInfoForPolicyTempSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public AxaMissingInfoForPolicyTempSchema get(int index)
	{
		AxaMissingInfoForPolicyTempSchema tSchema = (AxaMissingInfoForPolicyTempSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, AxaMissingInfoForPolicyTempSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(AxaMissingInfoForPolicyTempSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpAxaMissingInfoForPolicyTemp����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			AxaMissingInfoForPolicyTempSchema aSchema = (AxaMissingInfoForPolicyTempSchema)this.get(i);
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
			AxaMissingInfoForPolicyTempSchema aSchema = new AxaMissingInfoForPolicyTempSchema();
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
		AxaMissingInfoForPolicyTempSchema tSchema = new AxaMissingInfoForPolicyTempSchema();
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
