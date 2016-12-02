/*
 * <p>ClassName: PolicyMasterSet </p>
 * <p>Description: PolicyMasterSchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: ��ʢ����
 * @CreateDate��2012-04-01
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.PolicyMasterSchema;
import com.sinosoft.utility.*;

public class PolicyMasterSet extends SchemaSet
{
	// @Method
	public boolean add(PolicyMasterSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(PolicyMasterSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(PolicyMasterSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public PolicyMasterSchema get(int index)
	{
		PolicyMasterSchema tSchema = (PolicyMasterSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, PolicyMasterSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(PolicyMasterSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpPolicyMaster����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			PolicyMasterSchema aSchema = (PolicyMasterSchema)this.get(i);
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
			PolicyMasterSchema aSchema = new PolicyMasterSchema();
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
		PolicyMasterSchema tSchema = new PolicyMasterSchema();
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
