/*
 * <p>ClassName: LNContStatusUpdateSet </p>
 * <p>Description: LNContStatusUpdateSchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LNContStatusUpdate
 * @CreateDate��2014-08-20
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.LNContStatusUpdateSchema;
import com.sinosoft.utility.*;

public class LNContStatusUpdateSet extends SchemaSet
{
	// @Method
	public boolean add(LNContStatusUpdateSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LNContStatusUpdateSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LNContStatusUpdateSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LNContStatusUpdateSchema get(int index)
	{
		LNContStatusUpdateSchema tSchema = (LNContStatusUpdateSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LNContStatusUpdateSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LNContStatusUpdateSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpLNContStatusUpdate����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LNContStatusUpdateSchema aSchema = (LNContStatusUpdateSchema)this.get(i);
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
			LNContStatusUpdateSchema aSchema = new LNContStatusUpdateSchema();
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
		LNContStatusUpdateSchema tSchema = new LNContStatusUpdateSchema();
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
