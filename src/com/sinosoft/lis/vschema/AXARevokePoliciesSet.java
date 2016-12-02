/*
 * <p>ClassName: AXARevokePoliciesSet </p>
 * <p>Description: AXARevokePoliciesSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2012-01-05
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.AXARevokePoliciesSchema;
import com.sinosoft.utility.*;

public class AXARevokePoliciesSet extends SchemaSet
{
	// @Method
	public boolean add(AXARevokePoliciesSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(AXARevokePoliciesSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(AXARevokePoliciesSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public AXARevokePoliciesSchema get(int index)
	{
		AXARevokePoliciesSchema tSchema = (AXARevokePoliciesSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, AXARevokePoliciesSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(AXARevokePoliciesSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpAXARevokePolicies描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			AXARevokePoliciesSchema aSchema = (AXARevokePoliciesSchema)this.get(i);
			strReturn += aSchema.encode();
			if( i != n ) strReturn += SysConst.RECORDSPLITER;
		}

		return strReturn;
	}

	/**
	* 数据解包
	* @param: 打包后字符串
	* @return: boolean
	**/
	public boolean decode( String str )
	{
		int nBeginPos = 0;
		int nEndPos = str.indexOf('^');
		this.clear();

		while( nEndPos != -1 )
		{
			AXARevokePoliciesSchema aSchema = new AXARevokePoliciesSchema();
			if( aSchema.decode(str.substring(nBeginPos, nEndPos)) == false )
			{
				// @@错误处理
				this.mErrors.copyAllErrors( aSchema.mErrors );
				return false;
			}
			this.add(aSchema);
			nBeginPos = nEndPos + 1;
			nEndPos = str.indexOf('^', nEndPos + 1);
		}
		AXARevokePoliciesSchema tSchema = new AXARevokePoliciesSchema();
		if( tSchema.decode(str.substring(nBeginPos)) == false )
		{
			// @@错误处理
			this.mErrors.copyAllErrors( tSchema.mErrors );
			return false;
		}
		this.add(tSchema);

		return true;
	}

}
