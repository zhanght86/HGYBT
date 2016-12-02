/*
 * <p>ClassName: IPCOM_CITISet </p>
 * <p>Description: IPCOM_CITISchemaSet���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: ��ʢ����
 * @CreateDate��2012-03-24
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.IPCOM_CITISchema;
import com.sinosoft.utility.*;

public class IPCOM_CITISet extends SchemaSet
{
	// @Method
	public boolean add(IPCOM_CITISchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(IPCOM_CITISet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(IPCOM_CITISchema aSchema)
	{
		return super.remove(aSchema);
	}

	public IPCOM_CITISchema get(int index)
	{
		IPCOM_CITISchema tSchema = (IPCOM_CITISchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, IPCOM_CITISchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(IPCOM_CITISet aSet)
	{
		return super.set(aSet);
	}

	/**
	* ���ݴ������ XML ��ʽ�����˳��μ�<A href ={@docRoot}/dataStructure/tb.html#PrpIPCOM_CITI����/A>���ֶ�
	* @param: ��
	* @return: ���ش�����ַ���
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			IPCOM_CITISchema aSchema = (IPCOM_CITISchema)this.get(i);
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
			IPCOM_CITISchema aSchema = new IPCOM_CITISchema();
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
		IPCOM_CITISchema tSchema = new IPCOM_CITISchema();
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
