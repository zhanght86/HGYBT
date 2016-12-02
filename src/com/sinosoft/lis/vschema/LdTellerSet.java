/*
 * <p>ClassName: LdTellerSet </p>
 * <p>Description: LdTellerSchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: ��ʢ
 * @CreateDate��2012-05-25
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.LdTellerSchema;
import com.sinosoft.utility.*;

public class LdTellerSet extends SchemaSet
{
	// @Method
	public boolean add(LdTellerSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LdTellerSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LdTellerSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LdTellerSchema get(int index)
	{
		LdTellerSchema tSchema = (LdTellerSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LdTellerSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LdTellerSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpLdTeller����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LdTellerSchema aSchema = (LdTellerSchema)this.get(i);
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
			LdTellerSchema aSchema = new LdTellerSchema();
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
		LdTellerSchema tSchema = new LdTellerSchema();
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
