/*
 * <p>ClassName: LMSpecContentSet </p>
 * <p>Description: LMSpecContentSchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: �˱����
 * @CreateDate��2012-06-06
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.LMSpecContentSchema;
import com.sinosoft.utility.*;

public class LMSpecContentSet extends SchemaSet
{
	// @Method
	public boolean add(LMSpecContentSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LMSpecContentSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LMSpecContentSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LMSpecContentSchema get(int index)
	{
		LMSpecContentSchema tSchema = (LMSpecContentSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LMSpecContentSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LMSpecContentSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpLMSpecContent����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LMSpecContentSchema aSchema = (LMSpecContentSchema)this.get(i);
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
			LMSpecContentSchema aSchema = new LMSpecContentSchema();
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
		LMSpecContentSchema tSchema = new LMSpecContentSchema();
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
