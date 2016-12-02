/*
 * <p>ClassName: EndorseDetailSet </p>
 * <p>Description: EndorseDetailSchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: LKMatureBalance
 * @CreateDate��2015-01-06
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.EndorseDetailSchema;
import com.sinosoft.utility.*;

public class EndorseDetailSet extends SchemaSet
{
	// @Method
	public boolean add(EndorseDetailSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(EndorseDetailSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(EndorseDetailSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public EndorseDetailSchema get(int index)
	{
		EndorseDetailSchema tSchema = (EndorseDetailSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, EndorseDetailSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(EndorseDetailSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpEndorseDetail����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			EndorseDetailSchema aSchema = (EndorseDetailSchema)this.get(i);
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
			EndorseDetailSchema aSchema = new EndorseDetailSchema();
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
		EndorseDetailSchema tSchema = new EndorseDetailSchema();
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
