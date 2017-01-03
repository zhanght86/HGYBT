/*
 * <p>ClassName: TranLogDB </p>
 * <p>Description: DB层数据库操作类文象</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: 金盛
 * @CreateDate象011-11-10
 */
package com.sinosoft.lis.db;

import java.sql.*;
import com.sinosoft.lis.schema.TranLogSchema;
import com.sinosoft.lis.vschema.TranLogSet;
import com.sinosoft.utility.*;
/**
 * 交易日志数据库操作类
 * @author yuantongxin
 */
public class TranLogDB extends TranLogSchema
{
	/**
	 * 字段
	 */
	// @Field
	private Connection con;//连接对象
	private DBOper db;//数据库操作对象
	/**
	* flag = true: 传入Connection
	* flag = false: 不传入Connection
	**/
	private boolean mflag = false;

	public CErrors mErrors = new CErrors();		// 错误信息

	/**
	 * 有参构造函数
	 * @param tConnection 连接对象
	 */
	// @Constructor
	public TranLogDB( Connection tConnection )
	{
		con = tConnection;//传入连接对象
		db = new DBOper( con, "TranLog" );//通过连接和表名生成数据库操作对象
		mflag = true;//标记为已传入连接对象
	}

	public TranLogDB()
	{
		con = null;//不传入连接对象
		db = new DBOper( "TranLog" );//通过表名生成数据库操作对象
		mflag = false;//标记为未传入连接对象
	}

	/**
	 * 插入交易日志
	 * @return 操作结果(true成功，false失败)
	 */
	// @Method
	public boolean insert()
	{
		//[AppntIDNo:"371502198310206057",AppntName:"华贵",Bak1:"127.0.0.1",FuncFlag:1012,InNoDoc:"2246_3_1012_in.xml",InsuredIDNo:"371502198310206057",InsuredName:"华贵",LogNo:2246,MakeDate:20161208,MakeTime:142926,NodeNo:"060150001222",Operator:"5201300002",OtherNo:"",ProductId:"011A0100",ProposalPrtNo:"210414132201550",RCode:-1,RText:null,TranCom:9,TranDate:20161108,TranNo:"2016120800010",TranTime	:130101,UnitCode:null,UsedTime:-1,ZoneNo:"01",ModifyDate:20161208,ModifyTime:142926]
		//得到交易日志记录集
		TranLogSchema tSchema = this.getSchema();
		//数据库操作对象插入集合(用户)类对象失败
		//插入交易日志记录集失败[!false=true]
		if (!db.insert(tSchema))
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "TranLogDB";
			tError.functionName = "insert";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);

			return false;
		}
		//插入成功
		return true;//返回真
	}

	/**
	 * 更新
	 * @return 操作结果(true成功，false失败)
	 */
	public boolean update()
	{
		TranLogSchema tSchema = this.getSchema();
		if (!db.update(tSchema))
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "TranLogDB";
			tError.functionName = "update";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 删除SQL
	 * @return 操作结果(true成功，false失败)
	 */
	public boolean deleteSQL()
	{
		TranLogSchema tSchema = this.getSchema();
		if (!db.deleteSQL(tSchema))
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "TranLogDB";
			tError.functionName = "deleteSQL";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 删除
	 * @return 操作结果(true成功，false失败)
	 */
	public boolean delete()
	{
		TranLogSchema tSchema = this.getSchema();
		if (!db.delete(tSchema))
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "TranLogDB";
			tError.functionName = "delete";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);

			return false;
		}

		return true;
	}

	/**
	 * 得到总数
	 * @return 操作结果(tCount成功，-1失败)
	 */
	public int getCount()
	{
		TranLogSchema tSchema = this.getSchema();

		int tCount = db.getCount(tSchema);
		if (tCount < 0)
		{
			// @@错误处理
			this.mErrors.copyAllErrors(db.mErrors);
			CError tError = new CError();
			tError.moduleName = "TranLogDB";
			tError.functionName = "getCount";
			tError.errorMessage = "操作失败!";
			this.mErrors .addOneError(tError);

			return -1;
		}

		return tCount;
	}

	/**
	 * 获取信息
	 * @return 操作结果(true成功，false失败)
	 */
	public boolean getInfo()
	{
		Statement stmt = null;//JDBC连接中传递SQL语句的对象
		ResultSet rs = null;//查询结果返回的结果集对象
	  //!false=true
	  if( !mflag ) {//没有传入连接
		  //得到连接
		  con = DBConnPool.getConnection();//数据库连接池获得连接
		}

		try
		{
			//RSType(结果类型):结果集的游标只能向下滚动(游标移动接口, 用来操作移动游标)，RSConcurrency(结果并发):不能用结果集更新数据库中的表(更新数据接口, 用来更新当前游标指向位置的数象 并可以更改对应数据库中的数据)
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);//通过连接创建语句对象
			SQLString sqlObj = new SQLString("TranLog");//根据表名创建SQL字符串对象
			TranLogSchema aSchema = this.getSchema();//获得DB象交易日志数据库对象的集合(用户) 类对象
			sqlObj.setSQL(6,aSchema);//设置SQL字符串对象
			String sql = sqlObj.getSQL();//得到SQL字符象

			rs = stmt.executeQuery(sql);//语句对象执行查询
			int i = 0;//计数象
			
			while (rs.next())//将光标移动到下一行，迭代结果象
			{
				i++;//累加记录象
				/*测试代码*/
				System.out.println("-------------------------");
				System.out.println("this.setSchema(rs,i)="+this.setSchema(rs,i)+",i="+i);
				System.out.println("-------------------------");
				if (this.setSchema(rs,i) == false)
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "TranLogDB";
					tError.functionName = "getInfo";
					tError.errorMessage = "取数失败!";
					this.mErrors .addOneError(tError);

					try{ rs.close(); } catch( Exception ex ) {}
					try{ stmt.close(); } catch( Exception ex1 ) {}

					if (mflag == false)
					{
						try
						{
							con.close();
						}
						catch(Exception et){}
					}
					return false;
				}
				break;
			}
			try{ rs.close(); } catch( Exception ex2 ) {}
			try{ stmt.close(); } catch( Exception ex3 ) {}
System.out.println("i="+i);
			if( i == 0 )
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "TranLogDB";
				tError.functionName = "getInfo";
				tError.errorMessage = "未找到相关对象";
				this.mErrors .addOneError(tError);

				if (mflag == false)
				{
					try
					{
						con.close();
					}
					catch(Exception et){}
				}
				return false;
			}
		}
		catch(Exception e)
	    {
			// @@错误处理
			//初始化错误实例
			CError tError = new CError();
			tError.moduleName = "TranLogDB";//设置模块名[交易日志数据库操作类]
			tError.functionName = "getInfo";//设置函数名[获取信息]
			tError.errorMessage = e.toString();//设置错误信息[java.sql.SQLException: Io 异常: Read timed out]
			this.mErrors .addOneError(tError);

			try{ rs.close(); } catch( Exception ex ) {}
			try{ stmt.close(); } catch( Exception ex1 ) {}
			//false==false
			if (mflag == false)
			{
				try
				{
					con.close();
				}
				catch(Exception et){}
			}
			return false;
	    }
	    // 断开数据库连象
		if (mflag == false)
		{
			try
			{
				con.close();
			}
			catch(Exception e){}
		}

		return true;
	}

	/**
	 * 查询
	 * @return 交易日志象
	 */
	public TranLogSet query()
	{
		Statement stmt = null;//JDBC连接中传递SQL语句的对象
		ResultSet rs = null;//查询结果返回的结果集对象
		TranLogSet aTranLogSet = new TranLogSet();//交易日志集合类对象
		
	   if( !mflag ) {//没有传入连接
		  con = DBConnPool.getConnection();//数据库连接池获得连接
		}

		try
		{
			//RSType(结果类型):结果集的游标只能向下滚动(游标移动接口, 用来操作移动游标)，RSConcurrency(结果并发):不能用结果集更新数据库中的表(更新数据接口, 用来更新当前游标指向位置的数象 并可以更改对应数据库中的数据)
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);//通过连接创建语句对象
			SQLString sqlObj = new SQLString("TranLog");//根据表名创建SQL字符串对象
			TranLogSchema aSchema = this.getSchema();//获得DB象交易日志数据库对象的集合(用户) 类对象
			sqlObj.setSQL(5,aSchema);//设置SQL字符串对象
			String sql = sqlObj.getSQL();//得到SQL字符象

			rs = stmt.executeQuery(sql);//语句对象执行查询
			int i = 0;//计数象
			while (rs.next())//将光标移动到下一行，迭代结果象
			{
				i++;//累加记录象
				TranLogSchema s1 = new TranLogSchema();//交易日志数据库对象的集合(用户) 类对象
				s1.setSchema(rs,i);//使用 ResultSet 中的象i 行给 Schema 赋象
				aTranLogSet.add(s1);//交易日志集合类对象添加交易日志数据库对象的集象用户) 类对象
			}
			try{ rs.close(); } catch( Exception ex ) {}//关闭结果集对象
			try{ stmt.close(); } catch( Exception ex1 ) {}//关闭语句对象
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "TranLogDB";//模块象
			tError.functionName = "query";//函数象
			tError.errorMessage = e.toString();//错误信息
			this.mErrors .addOneError(tError);//添加象��错误

			try{ rs.close(); } catch( Exception ex2 ) {}//关闭结果集对象
			try{ stmt.close(); } catch( Exception ex3 ) {}//关闭语句对象

			if (mflag == false)//没有传入连接
			{
				try
				{
					con.close();//连接关闭
				}
				catch(Exception et){}
			}
	    }

		if (mflag == false)//没有传入连接
		{
			try
			{
				con.close();//连接关闭
			}
			catch(Exception e){}
		}

		return aTranLogSet;//返回交易日志集合类对象
	}

	/**
	 * 执行查询
	 * @param sql 结构化查询语象
	 * @return 交易日志集合类对象
	 */
	public TranLogSet executeQuery(String sql)
	{
		Statement stmt = null;//JDBC连接中传递SQL语句的对象
		ResultSet rs = null;//查询结果返回的结果集对象
		TranLogSet aTranLogSet = new TranLogSet();//交易日志集合类对象
		
	  if( !mflag ) {//没有传入连接
		  con = DBConnPool.getConnection();//数据库连接池获得连接
		}

		try
		{
			//RSType(结果类型):结果集的游标只能向下滚动(游标移动接口, 用来操作移动游标)，RSConcurrency(结果并发):不能用结果集更新数据库中的表(更新数据接口, 用来更新当前游标指向位置的数象 并可以更改对应数据库中的数据)
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);//通过连接创建语句对象
			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));//语句对象执行字符串处理工具类将字符串转换为Unicode字符串查象
			
			int i = 0;//计数象
			while (rs.next())//将光标移动到下一行，迭代结果象
			{
				i++;//累加记录象
				TranLogSchema s1 = new TranLogSchema();//交易日志数据库对象的集合(用户) 类对象
				if (s1.setSchema(rs,i) == false)//使用 ResultSet 中的象i 行给 Schema 赋象是否失败
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "TranLogDB";//模块象
					tError.functionName = "executeQuery";//函数象
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";//错误信息
					this.mErrors .addOneError(tError);//添加象��错误
				}
				aTranLogSet.add(s1);//交易日志集合类对象添加交易日志数据库对象的集象用户) 类对象
			}
			try{ rs.close(); } catch( Exception ex ) {}//关闭结果集对象
			try{ stmt.close(); } catch( Exception ex1 ) {}//关闭语句对象
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "TranLogDB";//模块象
			tError.functionName = "executeQuery";//函数象
			tError.errorMessage = e.toString();//错误信息
			this.mErrors .addOneError(tError);//添加象��错误

			try{ rs.close(); } catch( Exception ex2 ) {}//关闭结果集对象
			try{ stmt.close(); } catch( Exception ex3 ) {}//关闭语句对象

			if (mflag == false)//没有传入连接
			{
				try
				{
					con.close();//连接关闭
				}
				catch(Exception et){}
			}
	    }

		if (mflag == false)//没有传入连接
		{
			try
			{
				con.close();//连接关闭
			}
			catch(Exception e){}
		}

		return aTranLogSet;//返回交易日志集合类对象
	}

	/**
	 * 查询
	 * @param nStart 起点
	 * @param nCount 总数
	 * @return 交易日志集合象
	 */
	public TranLogSet query(int nStart, int nCount)
	{
		Statement stmt = null;//JDBC连接中传递SQL语句的对象
		ResultSet rs = null;//查询结果返回的结果集对象
		TranLogSet aTranLogSet = new TranLogSet();//交易日志集合类对象

	  if( !mflag ) {//没有传入连接
		  con = DBConnPool.getConnection();//数据库连接池获得连接
		}

		try
		{
			//RSType(结果类型):结果集的游标只能向下滚动(游标移动接口, 用来操作移动游标)，RSConcurrency(结果并发):不能用结果集更新数据库中的表(更新数据接口, 用来更新当前游标指向位置的数象 并可以更改对应数据库中的数据)
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);//通过连接创建语句对象
			SQLString sqlObj = new SQLString("TranLog");//根据表名创建SQL字符串对象
			TranLogSchema aSchema = this.getSchema();//获得DB象交易日志数据库对象的集合(用户) 类对象
			sqlObj.setSQL(5,aSchema);//设置SQL字符串对象
			String sql = sqlObj.getSQL();//得到SQL字符象

			rs = stmt.executeQuery(sql);//语句对象执行查询
			int i = 0;//计数器
			while (rs.next())//将光标移动到下一行，迭代结果集
			{
				i++;//累加记录数
				if( i < nStart ) {//累加记录数小于起始值
					continue;//结束单次循环(继续)
				}

				if( i >= nStart + nCount ) {//累加记录数大于等于起始值与总数的和
					break;//结束整个循环体
				}

				TranLogSchema s1 = new TranLogSchema();//获得DB象交易日志数据库对象的集合(用户) 类对象
				s1.setSchema(rs,i);//使用 ResultSet 中的象i 行给 Schema 赋值
				aTranLogSet.add(s1);//交易日志集合类对象添加交易日志数据库对象的集合(用户) 类对象
			}
			try{ rs.close(); } catch( Exception ex ) {}//关闭结果集对象
			try{ stmt.close(); } catch( Exception ex1 ) {}//关闭语句对象
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "TranLogDB";//模块名
			tError.functionName = "query";//函数名
			tError.errorMessage = e.toString();//错误信息
			this.mErrors .addOneError(tError);//添加一个错误

			try{ rs.close(); } catch( Exception ex2 ) {}//关闭结果集对象
			try{ stmt.close(); } catch( Exception ex3 ) {}//关闭语句对象

			if (mflag == false)//没有传入连接
			{
				try
				{
					con.close();//连接关闭
				}
				catch(Exception et){}
			}
	    }

		if (mflag == false)//没有传入连接
		{
			try
			{
				con.close();//连接关闭
			}
			catch(Exception e){}
		}

		return aTranLogSet;//返回交易日志集合类对象
	}

	/**
	 * 执行查询
	 * @param sql 结构化查询语象
	 * @param nStart 起点
	 * @param nCount 总数
	 * @return 交易日志集合象
	 */
	public TranLogSet executeQuery(String sql, int nStart, int nCount)
	{
		Statement stmt = null;//JDBC连接中传递SQL语句的对象
		ResultSet rs = null;//查询结果返回的结果集对象
		TranLogSet aTranLogSet = new TranLogSet();//交易日志集合类对象

	  if( !mflag ) {//没有传入连接
		  con = DBConnPool.getConnection();//数据库连接池获得连接
		}

		try
		{
			//RSType(结果类型):结果集的游标只能向下滚动(游标移动接口, 用来操作移动游标)，RSConcurrency(结果并发):不能用结果集更新数据库中的表(更新数据接口, 用来更新当前游标指向位置的数象 并可以更改对应数据库中的数据)
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);//通过连接创建语句对象
			rs = stmt.executeQuery(StrTool.GBKToUnicode(sql));//语句对象执行字符串处理工具类将字符串转换为Unicode字符串查询
			int i = 0;//计数器
			while (rs.next())//将光标移动到下一行，迭代结果集
			{
				i++;//累加记录数
				if( i < nStart ) {//累加记录数小于起始值
					continue;//结束单次循环(继续)
				}

				if( i >= nStart + nCount ) {//累加记录数大于等于起始值与总数的和
					break;//结束整个循环体
				}

				TranLogSchema s1 = new TranLogSchema();//获得DB象交易日志数据库对象的集合(用户) 类对象
				if (s1.setSchema(rs,i) == false)//使用 ResultSet 中的第i 行给 Schema 赋值是否失败
				{
					// @@错误处理
					CError tError = new CError();
					tError.moduleName = "TranLogDB";//模块名
					tError.functionName = "executeQuery";//函数名
					tError.errorMessage = "sql语句有误，请查看表名及字段名信息!";//错误信息
					this.mErrors .addOneError(tError);//添加一个错误
				}
				aTranLogSet.add(s1);//交易日志集合类对象添加交易日志数据库对象的集合(用户) 类对象
			}
			try{ rs.close(); } catch( Exception ex ) {}//关闭结果集对象
			try{ stmt.close(); } catch( Exception ex1 ) {}//关闭语句对象
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "TranLogDB";//模块名
			tError.functionName = "executeQuery";//函数名
			tError.errorMessage = e.toString();//错误信息
			this.mErrors .addOneError(tError);//添加一个错误

			try{ rs.close(); } catch( Exception ex2 ) {}//关闭结果集对象
			try{ stmt.close(); } catch( Exception ex3 ) {}//关闭语句对象

			if (mflag == false)//没有传入连接
			{
				try
				{
					con.close();//连接关闭
				}
				catch(Exception et){}
			}
	    }

		if (mflag == false)//没有传入连接
		{
			try
			{
				con.close();//连接关闭
			}
			catch(Exception e){}
		}

		return aTranLogSet;//返回交易日志集合类对象
	}

	/**
	 * 更新
	 * @param strWherePart 字符串其中一部分
	 * @return 
	 */
	public boolean update(String strWherePart)
	{
		Statement stmt = null;//JDBC连接中传递SQL语句的对象

	  if( !mflag ) {//没有传入连接
		  con = DBConnPool.getConnection();//数据库连接池获得连接
		}

		try
		{
			//RSType(结果类型):结果集的游标只能向下滚动(游标移动接口, 用来操作移动游标)，RSConcurrency(结果并发):不能用结果集更新数据库中的表(更新数据接口, 用来更新当前游标指向位置的数象 并可以更改对应数据库中的数据)
			stmt = con.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);//通过连接创建语句对象
			SQLString sqlObj = new SQLString("TranLog");//根据表名创建SQL字符串对象
			TranLogSchema aSchema = this.getSchema();//获得DB象交易日志数据库对象的集合(用户) 类对象
			sqlObj.setSQL(2,aSchema);//设置SQL字符串对象
			String sql = "update TranLog " + sqlObj.getUpdPart() + " where " + strWherePart;//得到拼接后SQL字符象

			int operCount = stmt.executeUpdate(sql);//语句对象执行更新结构化查询语句返回操作数
			if( operCount == 0 )//操作数为0
			{
				// @@错误处理
				CError tError = new CError();
				tError.moduleName = "TranLogDB";//模块名
				tError.functionName = "update";//函数名
				tError.errorMessage = "更新数据失败!";//错误信息
				this.mErrors .addOneError(tError);//添加一个错误

				if (mflag == false)//没有传入连接
				{
					try
					{
						con.close();//连接关闭
					}
					catch(Exception et){}
				}
				return false;//操作结果:失败
			}
		}
		catch(Exception e)
	    {
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "TranLogDB";//模块名
			tError.functionName = "update";//函数名
			tError.errorMessage = e.toString();//错误信息
			this.mErrors .addOneError(tError);//添加一个错误

			try{ stmt.close(); } catch( Exception ex1 ) {}//关闭语句对象

			if (mflag == false)//没有传入连接
			{
				try
				{
					con.close();//连接关闭
				}
				catch(Exception et){}
			}
			return false;//操作结果:失败
	    }
	    // 断开数据库连接
		if (mflag == false)//没有传入连接
		{
			try
			{
				con.close();//连接关闭
			}
			catch(Exception e){}
		}

		return true;//操作结果:成功
	}
	
	public static void main(String[] args) {
//		new TranLogDB().getInfo();
		System.out.println(new TranLogDB().insert());
	}

}
