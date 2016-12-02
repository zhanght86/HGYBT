/*
 * <p>ClassName: FundPriceUpSet </p>
 * <p>Description: FundPriceUpSchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: ��ʢ����
 * @CreateDate��2012-03-24
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.FundPriceUpSchema;
import com.sinosoft.utility.*;

public class FundPriceUpSet extends SchemaSet
{
	// @Method
	public boolean add(FundPriceUpSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(FundPriceUpSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(FundPriceUpSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public FundPriceUpSchema get(int index)
	{
		FundPriceUpSchema tSchema = (FundPriceUpSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, FundPriceUpSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(FundPriceUpSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpFundPriceUp����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			FundPriceUpSchema aSchema = (FundPriceUpSchema)this.get(i);
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
			FundPriceUpSchema aSchema = new FundPriceUpSchema();
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
		FundPriceUpSchema tSchema = new FundPriceUpSchema();
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
