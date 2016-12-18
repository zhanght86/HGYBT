/*
 * <p>ClassName: TranLogSet </p>
 * <p>Description: TranLogSchemaSet类文件 </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate：2011-11-10
 */
package com.sinosoft.lis.vschema;

import com.sinosoft.lis.schema.TranLogSchema;
import com.sinosoft.utility.*;
/**
 * 交易日志集合类
 * @author yuantongxin
 */
public class TranLogSet extends SchemaSet
{
	/**
	 * 添加
	 * @param aSchema 交易日志数据库对象的集合类对象
	 * @return aSchema是否非空(true:非空，false:为空)
	 */
	// @Method
	public boolean add(TranLogSchema aSchema)
	{
		return super.add(aSchema);//是否非空
	}
	
	/**
	 * 添加
	 * @param aSet  交易日志集合类对象
	 * @return aSet是否非空(true:非空，false:为空)
	 */
	public boolean add(TranLogSet aSet)
	{
		return super.add(aSet);//是否非空
	}
	
	/**
	 * 去除
	 * @param aSchema 交易日志数据库对象的集合类对象
	 * @return aSchema是否为空(true:在对象数组中;false:为空，不在对象数组中)
	 */
	public boolean remove(TranLogSchema aSchema)
	{
		return super.remove(aSchema);//是否在对象数组中
	}
	
	/**
	 * 得到DB层 交易日志数据库对象的集合(用户) 类实例
	 * @param index 索引
	 * @return 交易日志数据库对象的集合类对象
	 */
	public TranLogSchema get(int index)
	{
		TranLogSchema tSchema = (TranLogSchema)super.getObj(index);//根据索引获取对象
		return tSchema;//返回交易日志数据库对象的集合类对象
	}

	/**
	 * 设置
	 * @param index 索引
	 * @param aSchema 交易日志数据库对象的集合类对象
	 * @return index是否存在(true:存在，false不存在)
	 */
	public boolean set(int index, TranLogSchema aSchema)
	{
		return super.set(index,aSchema);
	}
 
	/**
	 * 设置
	 * @param aSet 交易日志集合类对象
	 * @return aSet是否非空(true:非空，false:为空)
	 */
	public boolean set(TranLogSet aSet)
	{
		return super.set(aSet);//是否非空
	}

	/**
	* 数据打包，按 XML 格式打包，顺序参见<A href ={@docRoot}/dataStructure/tb.html#PrpTranLog描述/A>表字段
	* @param: 无
	* @return: 返回打包后字符串
	**/
	public String encode()
	{
		String strReturn = "";
		int n = this.size();
		for (int i = 1; i <= n; i++)
		{
			TranLogSchema aSchema = (TranLogSchema)this.get(i);
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
			TranLogSchema aSchema = new TranLogSchema();
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
		TranLogSchema tSchema = new TranLogSchema();
		if( tSchema.decode(str.substring(nBeginPos)) == false )
		{
			// @@错误处理
			this.mErrors.copyAllErrors( tSchema.mErrors );
			return false;
		}
		this.add(tSchema);

		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(new TranLogSet().encode());
	}

}
