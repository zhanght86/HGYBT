/*
 * <p>ClassName: YBTLinePoliciesSet </p>
 * <p>Description: YBTLinePoliciesSchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: ��ʢ
 * @CreateDate��2012-03-12
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.YBTLinePoliciesSchema;
import com.sinosoft.utility.*;

public class YBTLinePoliciesSet extends SchemaSet
{
	// @Method
	public boolean add(YBTLinePoliciesSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(YBTLinePoliciesSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(YBTLinePoliciesSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public YBTLinePoliciesSchema get(int index)
	{
		YBTLinePoliciesSchema tSchema = (YBTLinePoliciesSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, YBTLinePoliciesSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(YBTLinePoliciesSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpYBTLinePolicies����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			YBTLinePoliciesSchema aSchema = (YBTLinePoliciesSchema)this.get(i);
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
			YBTLinePoliciesSchema aSchema = new YBTLinePoliciesSchema();
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
		YBTLinePoliciesSchema tSchema = new YBTLinePoliciesSchema();
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
