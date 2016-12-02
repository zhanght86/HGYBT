/*
 * <p>ClassName: PolicyTransactionAdjustmentSet </p>
 * <p>Description: PolicyTransactionAdjustmentSchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: ��ʢ����
 * @CreateDate��2012-03-24
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.PolicyTransactionAdjustmentSchema;
import com.sinosoft.utility.*;

public class PolicyTransactionAdjustmentSet extends SchemaSet
{
	// @Method
	public boolean add(PolicyTransactionAdjustmentSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(PolicyTransactionAdjustmentSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(PolicyTransactionAdjustmentSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public PolicyTransactionAdjustmentSchema get(int index)
	{
		PolicyTransactionAdjustmentSchema tSchema = (PolicyTransactionAdjustmentSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, PolicyTransactionAdjustmentSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(PolicyTransactionAdjustmentSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpPolicyTransactionAdjustment����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			PolicyTransactionAdjustmentSchema aSchema = (PolicyTransactionAdjustmentSchema)this.get(i);
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
			PolicyTransactionAdjustmentSchema aSchema = new PolicyTransactionAdjustmentSchema();
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
		PolicyTransactionAdjustmentSchema tSchema = new PolicyTransactionAdjustmentSchema();
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
