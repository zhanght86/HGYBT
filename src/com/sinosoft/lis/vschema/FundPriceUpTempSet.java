/*
 * <p>ClassName: FundPriceUpTempSet </p>
 * <p>Description: FundPriceUpTempSchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: ��ʢ����
 * @CreateDate��2012-03-24
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.FundPriceUpTempSchema;
import com.sinosoft.utility.*;

public class FundPriceUpTempSet extends SchemaSet
{
	// @Method
	public boolean add(FundPriceUpTempSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(FundPriceUpTempSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(FundPriceUpTempSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public FundPriceUpTempSchema get(int index)
	{
		FundPriceUpTempSchema tSchema = (FundPriceUpTempSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, FundPriceUpTempSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(FundPriceUpTempSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpFundPriceUpTemp����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			FundPriceUpTempSchema aSchema = (FundPriceUpTempSchema)this.get(i);
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
			FundPriceUpTempSchema aSchema = new FundPriceUpTempSchema();
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
		FundPriceUpTempSchema tSchema = new FundPriceUpTempSchema();
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
