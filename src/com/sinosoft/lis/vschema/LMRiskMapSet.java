/*
 * <p>ClassName: LMRiskMapSet </p>
 * <p>Description: LMRiskMapSchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: ��ʢ
 * @CreateDate��2012-02-13
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.LMRiskMapSchema;
import com.sinosoft.utility.*;

public class LMRiskMapSet extends SchemaSet
{
	// @Method
	public boolean add(LMRiskMapSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LMRiskMapSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LMRiskMapSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LMRiskMapSchema get(int index)
	{
		LMRiskMapSchema tSchema = (LMRiskMapSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LMRiskMapSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LMRiskMapSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpLMRiskMap����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LMRiskMapSchema aSchema = (LMRiskMapSchema)this.get(i);
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
			LMRiskMapSchema aSchema = new LMRiskMapSchema();
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
		LMRiskMapSchema tSchema = new LMRiskMapSchema();
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
