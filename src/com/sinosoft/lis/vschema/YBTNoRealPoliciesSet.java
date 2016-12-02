/*
 * <p>ClassName: YBTNoRealPoliciesSet </p>
 * <p>Description: YBTNoRealPoliciesSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2012-02-20
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.YBTNoRealPoliciesSchema;
import com.sinosoft.utility.*;

public class YBTNoRealPoliciesSet extends SchemaSet
{
	// @Method
	public boolean add(YBTNoRealPoliciesSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(YBTNoRealPoliciesSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(YBTNoRealPoliciesSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public YBTNoRealPoliciesSchema get(int index)
	{
		YBTNoRealPoliciesSchema tSchema = (YBTNoRealPoliciesSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, YBTNoRealPoliciesSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(YBTNoRealPoliciesSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpYBTNoRealPolicies描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			YBTNoRealPoliciesSchema aSchema = (YBTNoRealPoliciesSchema)this.get(i);
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
			YBTNoRealPoliciesSchema aSchema = new YBTNoRealPoliciesSchema();
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
		YBTNoRealPoliciesSchema tSchema = new YBTNoRealPoliciesSchema();
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
