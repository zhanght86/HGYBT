/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.utility;

import java.io.CharArrayWriter;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Savepoint;
import java.sql.Statement;
import java.sql.Struct;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.sinosoft.lis.pubfun.PubFun;

/*
 * <p>ClassName: DBConn </p> <p>Description: 连接数据库类文件 </p> <p>Copyright:
 * Copyright (c) 2002</p> <p>Company: sinosoft </p> @Database: LIS
 * @CreateDate：2002-08-09
 */
/**
 * 数据库连接
 * 
 * @author yuantongxin
 */
public class DBConn implements java.sql.Connection {
	// Java数据库连接统一资源定位符
	private JdbcUrl JUrl;

	// 连接
	private Connection con = null;

	private boolean bNotInUse;

	// 是池
	private boolean bIsPool = false;

	// 最后测试访问
	private java.util.Date m_lastestAccess = null;

	// 字符数组输出流(将数据写入到“字符数组”中去)
	private CharArrayWriter m_buf = new CharArrayWriter();

	// 向文本输出流打印对象的格式化表示形式
	private PrintWriter m_pw = new PrintWriter(m_buf, true);

	/**
	 * 创建连接
	 * 
	 * @return boolean
	 */
	public boolean createConnection() {
		// 数据库类型
		int dbType = 0;
		/**
		 * WebLogic连接池配置调用这里的方法，当从备份连接中读取得时候，没有把bIsPool重置为false
		 * 这样每次执行的就是con.close()方法，没有起到池的作用。 而且如果是连接池已满而且没有空闲的连接，从备份中去连接就违背了规则。
		 * dbtype=10没有意义
		 */
		// WebLogic池
		// Java数据库连接统一资源定位符得到数据库类型不考虑大小写值为WEBLOGICPOOL
		if (JUrl.getDBType().equalsIgnoreCase("WEBLOGICPOOL")) {
			// 数据库类型
			dbType = 10;
			// 是池
			bIsPool = true;
			//
			if (getWeblogicPoolConnection()) {
				return true;
			} else {
				// 当读取连接池失败时，从备份连接读取连接
				JdbcUrlBackUp tJdbcUrlBackUp = new JdbcUrlBackUp();
				JUrl.setDBName(tJdbcUrlBackUp.getDBName());
				JUrl.setDBType(tJdbcUrlBackUp.getDBType());
				JUrl.setIP(tJdbcUrlBackUp.getIP());
				JUrl.setPassWord(tJdbcUrlBackUp.getPassWord());
				JUrl.setPort(tJdbcUrlBackUp.getPort());
				JUrl.setServerName(tJdbcUrlBackUp.getServerName());
				JUrl.setUser(tJdbcUrlBackUp.getUserName());
			}
		}
		/**
		 * apache连接池配置调用
		 */
		else if (JUrl.getDBType().equalsIgnoreCase("COMMONSDBCP")) {
			bIsPool = true;
			if (getApachecommonDBCP()) {
				return true;
			} else {
				// 当读取连接池失败时，从备份连接读取连接
				JdbcUrlBackUp tJdbcUrlBackUp = new JdbcUrlBackUp();
				JUrl.setDBName(tJdbcUrlBackUp.getDBName());
				JUrl.setDBType(tJdbcUrlBackUp.getDBType());
				JUrl.setIP(tJdbcUrlBackUp.getIP());
				JUrl.setPassWord(tJdbcUrlBackUp.getPassWord());
				JUrl.setPort(tJdbcUrlBackUp.getPort());
				JUrl.setServerName(tJdbcUrlBackUp.getServerName());
				JUrl.setUser(tJdbcUrlBackUp.getUserName());
			}
		}
		/**
		 * WebSphere连接池配置调用
		 */
		else if (JUrl.getDBType().equalsIgnoreCase("WEBSPHERE")) {
			bIsPool = true;
			if (getWebSpherePoolConnection()) {
				return true;
			} else {
				// 当读取连接池失败时，从备份连接读取连接
				JdbcUrlBackUp tJdbcUrlBackUp = new JdbcUrlBackUp();
				JUrl.setDBName(tJdbcUrlBackUp.getDBName());
				JUrl.setDBType(tJdbcUrlBackUp.getDBType());
				JUrl.setIP(tJdbcUrlBackUp.getIP());
				JUrl.setPassWord(tJdbcUrlBackUp.getPassWord());
				JUrl.setPort(tJdbcUrlBackUp.getPort());
				JUrl.setServerName(tJdbcUrlBackUp.getServerName());
				JUrl.setUser(tJdbcUrlBackUp.getUserName());
			}
		}
		/**
		 * 如果上面都没有执行成功，则调用自己编写的jdbc连接
		 */
		try {
			if (con != null) {
				if (!con.isClosed()) {
					try {
						// 为了解决“超时”的问题，在返回之前，先试用一下con
						Statement stmt = con.createStatement();
						stmt.execute("SELECT * FROM DUAL");
						stmt.close();
						return true;
					} catch (SQLException e) {
						System.out.println("DBConn Exception1: "
								+ e.getMessage());
						e.printStackTrace();
						System.out.println("DBConn : recreate DBConn");
						// 由于执行的sql编写规范不到位，导致很多非法sql描述。
						// 因此需要在这个地方捕获一下异常处理。
						try {
							con.close();
						} catch (Exception ex) {
							System.out.println("DBConn Exception2: "
									+ ex.getMessage());
							e.printStackTrace();
						} finally {
							con.close();
						}
						con = null;
					}
				}
				con = null;
			}

			// 判定数据库类型
			if (JUrl.getDBType().equalsIgnoreCase("ORACLE")) {
				dbType = 1;
			} else if (JUrl.getDBType().equalsIgnoreCase("INFORMIX")) {
				dbType = 2;
			} else if (JUrl.getDBType().equalsIgnoreCase("SQLSERVER")) {
				dbType = 3;
			} else if (JUrl.getDBType().equalsIgnoreCase("DB2")) {
				dbType = 4;
			} else if (JUrl.getDBType().equalsIgnoreCase("SYBASE")) {
				dbType = 5;
			} else if (JUrl.getDBType().equalsIgnoreCase("ORACLE_P6SPY")) {
				dbType = 6;
			}
			// 根据数据库类型动态加载驱动
			switch (dbType) {
			case 1:

				// ORACLE
				Class.forName("oracle.jdbc.driver.OracleDriver");
				break;
			case 2:

				// INFORMIX
				Class.forName("com.informix.jdbc.IfxDriver");
				break;
			case 3:

				// SQLSERVER
				// Class.forName("com.inet.tds.TdsDriver");
				Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver");
				break;
			case 4:

				// DB2
				Class.forName("com.ibm.db2.jcc.DB2Driver");
				break;
			case 5:

				// SYBASE
				Class.forName("com.sybase.jdbc2.jdbc.SybDriver");
				break;
			case 6:

				// P6SPY ORACLE
				Class.forName("com.p6spy.engine.spy.P6SpyDriver");
				break;
			default:
				System.out.println("目前暂不支持此种类型的数据库!");
				return false;
			}
		} catch (Exception e) {
			System.out.println("DBConn Exception3: " + e.getMessage());
			return false;
		}
		// 尝试连接数据库
		try {
			switch (dbType) {

			case 1:

				// ORACLE
				// 不是很清楚下面的设置有什么含义的说
				// 好像一个是缓存取到的记录数，一个是设置默认的批量提交数
				Properties props = new Properties();
				props.setProperty("user", JUrl.getUserName());
				props.setProperty("password", JUrl.getPassWord());

				// 50的数量级好像已经很好的说，诡异
				props.setProperty("defaultRowPrefetch", "50");
				props.setProperty("defaultExecuteBatch", "50");
				con = DriverManager.getConnection(JUrl.getJdbcUrl(), props);

				// con = DriverManager.getConnection(JUrl.getJdbcUrl(),
				// JUrl.getUserName()
				// , JUrl.getPassWord());
				Statement stmt = con.createStatement(
						ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				stmt.execute("alter session set nls_date_format = 'YYYY-MM-DD HH24:MI:SS'");
				stmt.close();
				break;

			case 2:

				// INFORMIX
				con = DriverManager.getConnection(JUrl.getJdbcUrl());
				break;

			case 3:

				// SQLSERVER
				con = DriverManager.getConnection(JUrl.getJdbcUrl(),
						JUrl.getUserName(), JUrl.getPassWord());
				break;
			case 4:

				// DB2
				con = DriverManager.getConnection(JUrl.getJdbcUrl(),
						JUrl.getUserName(), JUrl.getPassWord());
				break;
			case 5:

				// SYBASE
				con = DriverManager.getConnection(JUrl.getJdbcUrl(),
						JUrl.getUserName(), JUrl.getPassWord());
				break;
			case 6:
				// ORACLE
				// 不是很清楚下面的设置有什么含义的说
				// 好像一个是缓存取到的记录数，一个是设置默认的批量提交数
				props = new Properties();
				props.setProperty("user", JUrl.getUserName());
				props.setProperty("password", JUrl.getPassWord());

				// 50的数量级好像已经很好的说，诡异
				props.setProperty("defaultRowPrefetch", "50");
				props.setProperty("defaultExecuteBatch", "50");
				con = DriverManager.getConnection(JUrl.getJdbcUrl(), props);

				// con = DriverManager.getConnection(JUrl.getJdbcUrl(),
				// JUrl.getUserName()
				// , JUrl.getPassWord());
				stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				stmt.execute("alter session set nls_date_format = 'YYYY-MM-DD HH24:MI:SS'");
				stmt.close();
				break;

			}
		} catch (SQLException e) {
			System.out.println("创建连接失败..." + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * 对于Weblogic连接池 Edited by wellhi 2007-06-15 得到weblogic连接池
	 * 得到WebLogic连接池
	 * @return boolean
	 */
	private boolean getWeblogicPoolConnection() {
		try {
			/* weblogic的连接池重写了close()方法 */
			// 初始上下文
			Context tContext = new InitialContext();
			// 返回绑定到数据库名称的数据源
			DataSource tDataSource = (DataSource) tContext.lookup(JUrl
					.getDBName());
			// 数据源获得连接
			con = tDataSource.getConnection();
			// 连接创建语句
			Statement stmt = con.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,// 返回可滚动的结果集，当数据库变化时，当前结果集同步改变。
					ResultSet.CONCUR_UPDATABLE);// 能用结果集更新数据库中的表
			//运行可返回多个结果的给定的 SQL 语句修改默认的日期格式
			stmt.execute("alter session set nls_date_format = 'YYYY-MM-DD HH24:MI:SS'");
			//关闭语句
			stmt.close();
		} catch (Exception ex) {
			System.out.println("$$$$$$$$WebLogicPool Connect Failed$$$$$\n"
					+ ex.toString());//$$$$$$$$WebLogicPool Connect Failed$$$$$\n这个抛出的字符串表示形式
			return false;//失败返回假
		}
		//成功返回真
		return true;
	}

	/**
	 * 从apache提供的连接池中取连接，失败返回false
	 * 得到Apache通用数据库连接池
	 * @return boolean
	 * @date:
	 */
	private boolean getApachecommonDBCP() {
		try {
			//初始化上下文实例
			Context tContext = new InitialContext();
			//访问JNDI资源
			tContext = (Context) tContext.lookup("java:comp/env");
			//检索Java数据库连接统一资源定位符得到数据库名称对象
			Object obj = tContext.lookup(JUrl.getDBName());
			//强转为数据源
			DataSource tDataSource = (DataSource) obj;
			//数据源非空
			if (tDataSource != null) {
				//数据源获取连接
				con = tDataSource.getConnection();
				// 如果连接的是Oracle数据库，需要稍微处理一下日期的格式，最好是在服务器哪里设置一下，而不调用下面的程序
				// 可以添加一个字段类型，来控制是否使用下面的语句
				//连接非空
				if (con != null) {
					// Statement stmt = con.createStatement(ResultSet.
					// TYPE_SCROLL_SENSITIVE,
					// ResultSet.CONCUR_UPDATABLE);
					// stmt.execute(
					// "alter session set nls_date_format = 'YYYY-MM-DD
					// HH24:MI:SS'");
					// stmt.close();
					return true;//返回真(连接非空)
				}
				return false;//返回假(连接为空)
				//数据源为空
			} else {
				//在获取数据源时一个错误发生
				System.out.println("a error occured when geting datasource");
				return false;//返回假(数据源为空)
			}
			//获取上下文数据库连接异常
		} catch (Throwable e) {
			//在连接apache通用数据库连接池时失败
			System.out.println("failure when connect apache commons dbcp ");
			//打印堆栈跟踪
			e.printStackTrace();
			//失败返回假
			return false;
		}
	}

	//单一数据源
	static DataSource singleDataSource;

	/**
	 * 从WebSphere提供的连接池中取连接，失败返回false
	 * 得到WebSphere连接池
	 * @return boolean
	 */
	private boolean getWebSpherePoolConnection() {
		try {
			//数据源
			DataSource tDataSource;
			//单一数据源非空
			if (singleDataSource != null) {
				//单一数据源赋值给数据源
				tDataSource = singleDataSource;
				//单一数据源为空
			} else {
				//哈希表
				Hashtable env = new Hashtable();
				//放置（对象，对象）
				env.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY,//保存用来指定要使用的初始上下文工厂的环境属性名称
						"com.ibm.websphere.naming.WsnInitialContextFactory");//将创建初始上下文的工厂类的完全限定类名称
				env.put(javax.naming.Context.PROVIDER_URL,//指定提供命名服务的 WebSphere 的 URL
						"iiop://localhost:2809");//端口指的是BOOTSTAR_ADDRESS 端口 在websphere控制台能查到
				//使用所提供的环境构造一个初始上下文
				Context tContext = new InitialContext(env);
				//对象
				Object obj;
				
				// if(JUrl.getDBName().startsWith("jdbc/"))
				// {
				// obj = tContext.lookup(JUrl.getDBName());
				// }
				// else
				// {
				// obj = tContext.lookup("jdbc/"+JUrl.getDBName());
				// }
				//检索java:comp/env/Java数据库连接统一资源定位符得到数据库名称
				obj = tContext.lookup("java:comp/env/" + JUrl.getDBName());
				//强转为数据源
				tDataSource = (DataSource) obj;
				//数据源赋值给单一数据源
				singleDataSource = tDataSource;
			}
			
			// Context tContext = new InitialContext();
			// 如果在web.xml中声明了引用对象，则采用下面的方法
			// DataSource tDataSource = (DataSource)
			// tContext.lookup("java:comp/env/" +
			// JUrl.getDBName());
			// 下面的方法也可以发现到jndi数据
			// DataSource tDataSource = (DataSource)
			// tContext.lookup("jdbc/MET");
			// 不过会在日志中输出如下错误信息，websphere不建议采用
			// [03-9-2 17:19:11:916 CST] 6b0e97e8 ConnectionFac I J2CA0122I:
			// 无法定位资源引用
			// jdbc/db2ds，因此使用下列缺省值：[Resource-ref settings]
			// res-auth: 1 (APPLICATION)
			// res-isolation-level: 0 (TRANSACTION_NONE)
			// res-sharing-scope: true (SHAREABLE)
			// res-resolution-control: 999 (undefined)
			//数据源非空
			if (tDataSource != null) {
				//数据源获得连接
				con = tDataSource.getConnection();
				//连接非空
				if (con != null) {
					//从WebSphere连接成功！
					System.out.println("Connect succeed from websphere!");
					//返回成功
					return true;
				//连接为空
				} else {
					//新连接错误…
					System.out.println("new Connection error ...");
					//返回失败
					return false;
				}
			//数据源为空
			} else {
				//新数据源错误...
				System.out.println("new DataSource error ...");
				//返回失败
				return false;
			}
		//数据源获取连接异常
		} catch (Throwable e) {
			//look for jndi name error ...Java数据库连接统一资源定位符得到数据库名称
			System.out.println("look for jndi name error ..."
					+ JUrl.getDBName());
			//打印堆栈跟踪
			e.printStackTrace();
			//返回失败
			return false;
		}
	}

	/*
	 * kevin 2002-10-04 friend function used by DBConnPool
	 * 数据库连接
	 */
	protected DBConn() {
		//Java数据库连接统一资源定位符
		JUrl = new JdbcUrl();
		//不在使用
		bNotInUse = true;
	}

	/**
	 * 是内部关闭
	 * @return 
	 */
	protected boolean isInnerClose() {
		try {
			//连接为空
			if (con == null) {
				return true;//返回是内部关闭
			}
			//连接非空，返回此 Connection 对象是否已经被关闭
			return con.isClosed();
			//异常
		} catch (SQLException ex) {
			return true;//返回真
		}
	}

	/**
	 * 打印出当前的堆栈信息,方便调试分析错误,尤其适用于公共类
	 * 
	 * @author wellhi 2007-10-22
	 */
	// private void PrintStackInfo(String operation) {
	// StringBuffer buf = new StringBuffer();
	// Connection aConnection = MakeConn.getConnection();
	// ExeSQL nExeSQL = new ExeSQL(aConnection);
	// String sql = "";
	// String mstack = "";
	//
	// // buf
	// // .append("\r\n################# Start of 堆栈信息
	// // ####################\r\n");
	// Throwable nThrowable = new Throwable();
	// StackTraceElement[] nStackTraceElement = nThrowable.getStackTrace();
	//
	// for (int j = nStackTraceElement.length - 1; j > 0; j--) {
	// if (nStackTraceElement[j].getClassName().toUpperCase().indexOf(
	// "COM.SINOSOFT.") != -1
	// || nStackTraceElement[j].getClassName().toUpperCase()
	// .indexOf("_jsp".toUpperCase()) != -1) {
	// // buf.append(" ");
	// // buf.append(nStackTraceElement[j].getLineNumber());
	// // buf.append("->");
	// buf.append(nStackTraceElement[j].getClassName());
	// buf.append(":");
	// buf.append(nStackTraceElement[j].getMethodName());
	// // System.out.println(nStackTraceElement[j].getClassName());
	// // buf.append("\r\n");
	// break;
	// }
	// }
	// sql =
	// "insert into dbconcheck (submittime,operation,stack,stackdetail) values ('"
	// + (PubFun.getCurrentDate() + " " + PubFun.getCurrentTime())
	// + "','"
	// + operation
	// + "','"
	// + buf
	// + "','"
	// + PubFun.PrintStackInfo() + "')";
	// try {
	// if (!nExeSQL.execUpdateSQL(sql)) {
	// System.out.println("插入数据不成功");
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	//
	// }
	// // buf.append("################# End of 堆栈信息 ####################");
	// }

	/**
	 * 设置使用
	 */
	protected void setInUse() {
		/**
		 * Record stack information when each connection is get We reassian
		 * System.err, so Thread.currentThread().dumpStack() can dump stack info
		 * into our class FilterPrintStream.
		 */
		// 错误异常的超类打印堆栈跟踪向文本输出流打印对象的格式化表示形式
		new Throwable().printStackTrace(m_pw);
		// SysLog sysLog = new SysLog();
		// 产品定义信息输出
		// PrintStackInfo("open");
		bNotInUse = false;// 在使用(不在使用置为假)

		/**
		 * record lastest access time
		 */
		setLastestAccess();// 设置最后一个测试访问
	}

	/**
	 * is this dbconn been used by someone 在使用
	 * 
	 * @return boolean
	 */
	protected boolean isInUse() {
		return !bNotInUse;// 在使用(不在使用置为假)
	}

	/*
	 * kevin 2002-10-04 Note: JDK 1.3 implements of java.sql.Connection
	 * 清除数据库连接警告
	 */
	public void clearWarnings() throws SQLException {
		//清除为此 Connection 对象报告的所有警告。调用此方法后，在为此 Connection 对象报告新的警告前，getWarnings 方法将返回 null。
		con.clearWarnings();
	}

	/**
	 * 实现了Connection接口的close()方法，没有真正的断开连接， 而是将连接放回连接池，将bNotInUse设置成未使用。
	 * 关闭
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		// SysLog sysLog = new SysLog();
		// 产品定义信息输出
		// Logger log = SysLog.getLogger("DBconnLog");
		// log.info("close-" + PrintStackInfo());
		// PrintStackInfo("close");
		//是池
		if (bIsPool) {
			// 如果是通过weblogic连接池得到连接
			// 此处的close()方法是将连接放回weblogic连接池。
			// 不晓得websphere和tomcat是否也使用此方法
			//立刻释放连接对象占用的数据库联接资源
			con.close();
			//不在使用
			bNotInUse = true;
			//非池
		} else {
			// clear stack info of connection
			//将此流开始位置重置到最后一次调用mark是流的读取位置
			m_buf.reset();
			//不在使用
			bNotInUse = true;
		}
	}

	/**
	 * 提交
	 */
	public void commit() throws SQLException {
		//手动提交
		con.commit();
	}

	/**
	 * 创建语句
	 */
	public Statement createStatement() throws SQLException {
		return con.createStatement();//返回通过连接实例化的语句对象
	}

	/**
	 * 创建语句
	 * @param resultSetType 结果集类型
	 * @param resultSetConcurrency 结果集并发
	 */
	public Statement createStatement(int resultSetType, int resultSetConcurrency)
			throws SQLException {
		//返回通过连接创建语句
		return con.createStatement(resultSetType, resultSetConcurrency);
	}
	
	public Statement createStatement(int resultSetType,
			int resultSetConcurrency, int resultSetHoldability) {
		try {
			return con.createStatement(resultSetType, resultSetConcurrency,
					resultSetHoldability);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public boolean getAutoCommit() throws SQLException {
		return con.getAutoCommit();
	}

	public String getCatalog() throws SQLException {
		return con.getCatalog();
	}

	public int getHoldability() {
		try {
			return con.getHoldability();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	public DatabaseMetaData getMetaData() throws SQLException {
		return con.getMetaData();
	}

	public int getTransactionIsolation() throws SQLException {
		return con.getTransactionIsolation();
	}

	public Map getTypeMap() throws SQLException {
		return con.getTypeMap();
	}

	public SQLWarning getWarnings() throws SQLException {
		return con.getWarnings();
	}

	public boolean isClosed() throws SQLException {
		if (bNotInUse) {
			return true;
		} else {
			return con.isClosed();
		}
	}

	public boolean isReadOnly() throws SQLException {
		return con.isReadOnly();
	}

	public String nativeSQL(String sql) throws SQLException {
		return con.nativeSQL(sql);
	}

	public CallableStatement prepareCall(String sql) throws SQLException {
		return con.prepareCall(sql);
	}

	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		return con.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	public CallableStatement prepareCall(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability) {
		try {
			return con.prepareCall(sql, resultSetType, resultSetConcurrency,
					resultSetHoldability);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException {
		return con.prepareStatement(sql);
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys) {
		try {
			return con.prepareStatement(sql, autoGeneratedKeys);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) {
		try {
			return con.prepareStatement(sql, columnIndexes);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency) throws SQLException {
		return con.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType,
			int resultSetConcurrency, int resultSetHoldability) {
		try {
			return con.prepareStatement(sql, resultSetType,
					resultSetConcurrency, resultSetHoldability);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames) {
		try {
			return con.prepareStatement(sql, columnNames);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public void releaseSavepoint(Savepoint savepoint) {
		try {
			con.releaseSavepoint(savepoint);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void rollback() throws SQLException {
		con.rollback();
	}

	public void rollback(Savepoint savepoint) {
		try {
			con.rollback(savepoint);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void setAutoCommit(boolean autoCommit) throws SQLException {
		// if (con.getAutoCommit() != autoCommit)
		// {
		con.setAutoCommit(autoCommit);
		// }
	}

	public void setCatalog(String catalog) throws SQLException {
		con.setCatalog(catalog);
	}

	public void setHoldability(int holdability) {
		try {
			con.setHoldability(holdability);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void setReadOnly(boolean readOnly) throws SQLException {
		con.setReadOnly(readOnly);
	}

	public Savepoint setSavepoint() {
		try {
			return con.setSavepoint();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public Savepoint setSavepoint(String name) {
		try {
			return con.setSavepoint(name);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public void setTransactionIsolation(int level) throws SQLException {
		con.setTransactionIsolation(level);
	}

	public void setTypeMap(Map map) throws SQLException {
		con.setTypeMap(map);
	}

	protected void dumpConnInfo(OutputStream os) throws Exception {
		// If this connection hasn't been closed, dump its' stack info
		if (!this.isClosed()) {
			os.write(m_buf.toString().getBytes());
		}
	}

	protected void setLastestAccess() {
		m_lastestAccess = new java.util.Date();
	}

	protected java.util.Date getLastestAccess() {
		return m_lastestAccess;
	}

	protected void innerClose() {
		System.out.println("DBConn.innerClose");
		if (isInUse()) {
			return;
		}

		m_lastestAccess = null;

		try {
			con.rollback();
			con.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			con = null;
		}
	}

	public static void main(String[] args) {
		DBConn conn = new DBConn();
		conn.createConnection();
	}

	public Array createArrayOf(String arg0, Object[] arg1) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Blob createBlob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public NClob createNClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public SQLXML createSQLXML() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Struct createStruct(String typeName, Object[] attributes)
			throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getClientInfo(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isValid(int timeout) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public void setClientInfo(Properties properties)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub

	}

	public void setClientInfo(String name, String value)
			throws SQLClientInfoException {
		// TODO Auto-generated method stub

	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
