/*
 * <p>ClassName: FundSummarySet </p>
 * <p>Description: FundSummarySchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: ��ʢ����
 * @CreateDate��2012-03-24
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.FundSummarySchema;
import com.sinosoft.utility.*;

public class FundSummarySet extends SchemaSet
{
	// @Method
	public boolean add(FundSummarySchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(FundSummarySet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(FundSummarySchema aSchema)
	{
		return super.remove(aSchema);
	}

	public FundSummarySchema get(int index)
	{
		FundSummarySchema tSchema = (FundSummarySchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, FundSummarySchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(FundSummarySet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpFundSummary����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			FundSummarySchema aSchema = (FundSummarySchema)this.get(i);
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
			FundSummarySchema aSchema = new FundSummarySchema();
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
		FundSummarySchema tSchema = new FundSummarySchema();
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
