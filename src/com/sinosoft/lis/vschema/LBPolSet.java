/*
 * <p>ClassName: LBPolSet </p>
 * <p>Description: LBPolSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2011-11-10
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.LBPolSchema;
import com.sinosoft.utility.*;

public class LBPolSet extends SchemaSet
{
	// @Method
	public boolean add(LBPolSchema aSchema)
	{
		return super.add(aSchema);
	}

	public boolean add(LBPolSet aSet)
	{
		return super.add(aSet);
	}

	public boolean remove(LBPolSchema aSchema)
	{
		return super.remove(aSchema);
	}

	public LBPolSchema get(int index)
	{
		LBPolSchema tSchema = (LBPolSchema)super.getObj(index);
		return tSchema;
	}

	public boolean set(int index, LBPolSchema aSchema)
	{
		return super.set(index,aSchema);
	}

	public boolean set(LBPolSet aSet)
	{
		return super.set(aSet);
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpLBPol描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			LBPolSchema aSchema = (LBPolSchema)this.get(i);
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
			LBPolSchema aSchema = new LBPolSchema();
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
		LBPolSchema tSchema = new LBPolSchema();
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
