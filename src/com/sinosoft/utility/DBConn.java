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
 * <p>ClassName: DBConn </p> <p>Description: �������ݿ����ļ� </p> <p>Copyright:
 * Copyright (c) 2002</p> <p>Company: sinosoft </p> @Database: LIS
 * @CreateDate��2002-08-09
 */
/**
 * ���ݿ�����
 * 
 * @author yuantongxin
 */
public class DBConn implements java.sql.Connection {
	// Java���ݿ�����ͳһ��Դ��λ��
	private JdbcUrl JUrl;

	// ����
	private Connection con = null;

	private boolean bNotInUse;

	// �ǳ�
	private boolean bIsPool = false;

	// �����Է���
	private java.util.Date m_lastestAccess = null;

	// �ַ����������(������д�뵽���ַ����顱��ȥ)
	private CharArrayWriter m_buf = new CharArrayWriter();

	// ���ı��������ӡ����ĸ�ʽ����ʾ��ʽ
	private PrintWriter m_pw = new PrintWriter(m_buf, true);

	/**
	 * ��������
	 * 
	 * @return boolean
	 */
	public boolean createConnection() {
		// ���ݿ�����
		int dbType = 0;
		/**
		 * WebLogic���ӳ����õ�������ķ��������ӱ��������ж�ȡ��ʱ��û�а�bIsPool����Ϊfalse
		 * ����ÿ��ִ�еľ���con.close()������û���𵽳ص����á� ������������ӳ���������û�п��е����ӣ��ӱ�����ȥ���Ӿ�Υ���˹���
		 * dbtype=10û������
		 */
		// WebLogic��
		// Java���ݿ�����ͳһ��Դ��λ���õ����ݿ����Ͳ����Ǵ�СдֵΪWEBLOGICPOOL
		if (JUrl.getDBType().equalsIgnoreCase("WEBLOGICPOOL")) {
			// ���ݿ�����
			dbType = 10;
			// �ǳ�
			bIsPool = true;
			//
			if (getWeblogicPoolConnection()) {
				return true;
			} else {
				// ����ȡ���ӳ�ʧ��ʱ���ӱ������Ӷ�ȡ����
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
		 * apache���ӳ����õ���
		 */
		else if (JUrl.getDBType().equalsIgnoreCase("COMMONSDBCP")) {
			bIsPool = true;
			if (getApachecommonDBCP()) {
				return true;
			} else {
				// ����ȡ���ӳ�ʧ��ʱ���ӱ������Ӷ�ȡ����
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
		 * WebSphere���ӳ����õ���
		 */
		else if (JUrl.getDBType().equalsIgnoreCase("WEBSPHERE")) {
			bIsPool = true;
			if (getWebSpherePoolConnection()) {
				return true;
			} else {
				// ����ȡ���ӳ�ʧ��ʱ���ӱ������Ӷ�ȡ����
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
		 * ������涼û��ִ�гɹ���������Լ���д��jdbc����
		 */
		try {
			if (con != null) {
				if (!con.isClosed()) {
					try {
						// Ϊ�˽������ʱ�������⣬�ڷ���֮ǰ��������һ��con
						Statement stmt = con.createStatement();
						stmt.execute("SELECT * FROM DUAL");
						stmt.close();
						return true;
					} catch (SQLException e) {
						System.out.println("DBConn Exception1: "
								+ e.getMessage());
						e.printStackTrace();
						System.out.println("DBConn : recreate DBConn");
						// ����ִ�е�sql��д�淶����λ�����ºܶ�Ƿ�sql������
						// �����Ҫ������ط�����һ���쳣����
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

			// �ж����ݿ�����
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
			// �������ݿ����Ͷ�̬��������
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
				System.out.println("Ŀǰ�ݲ�֧�ִ������͵����ݿ�!");
				return false;
			}
		} catch (Exception e) {
			System.out.println("DBConn Exception3: " + e.getMessage());
			return false;
		}
		// �����������ݿ�
		try {
			switch (dbType) {

			case 1:

				// ORACLE
				// ���Ǻ���������������ʲô�����˵
				// ����һ���ǻ���ȡ���ļ�¼����һ��������Ĭ�ϵ������ύ��
				Properties props = new Properties();
				props.setProperty("user", JUrl.getUserName());
				props.setProperty("password", JUrl.getPassWord());

				// 50�������������Ѿ��ܺõ�˵������
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
				// ���Ǻ���������������ʲô�����˵
				// ����һ���ǻ���ȡ���ļ�¼����һ��������Ĭ�ϵ������ύ��
				props = new Properties();
				props.setProperty("user", JUrl.getUserName());
				props.setProperty("password", JUrl.getPassWord());

				// 50�������������Ѿ��ܺõ�˵������
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
			System.out.println("��������ʧ��..." + e.getMessage());
			return false;
		}
		return true;
	}

	/**
	 * ����Weblogic���ӳ� Edited by wellhi 2007-06-15 �õ�weblogic���ӳ�
	 * �õ�WebLogic���ӳ�
	 * @return boolean
	 */
	private boolean getWeblogicPoolConnection() {
		try {
			/* weblogic�����ӳ���д��close()���� */
			// ��ʼ������
			Context tContext = new InitialContext();
			// ���ذ󶨵����ݿ����Ƶ�����Դ
			DataSource tDataSource = (DataSource) tContext.lookup(JUrl
					.getDBName());
			// ����Դ�������
			con = tDataSource.getConnection();
			// ���Ӵ������
			Statement stmt = con.createStatement(
					ResultSet.TYPE_SCROLL_SENSITIVE,// ���ؿɹ����Ľ�����������ݿ�仯ʱ����ǰ�����ͬ���ı䡣
					ResultSet.CONCUR_UPDATABLE);// ���ý�����������ݿ��еı�
			//���пɷ��ض������ĸ����� SQL ����޸�Ĭ�ϵ����ڸ�ʽ
			stmt.execute("alter session set nls_date_format = 'YYYY-MM-DD HH24:MI:SS'");
			//�ر����
			stmt.close();
		} catch (Exception ex) {
			System.out.println("$$$$$$$$WebLogicPool Connect Failed$$$$$\n"
					+ ex.toString());//$$$$$$$$WebLogicPool Connect Failed$$$$$\n����׳����ַ�����ʾ��ʽ
			return false;//ʧ�ܷ��ؼ�
		}
		//�ɹ�������
		return true;
	}

	/**
	 * ��apache�ṩ�����ӳ���ȡ���ӣ�ʧ�ܷ���false
	 * �õ�Apacheͨ�����ݿ����ӳ�
	 * @return boolean
	 * @date:
	 */
	private boolean getApachecommonDBCP() {
		try {
			//��ʼ��������ʵ��
			Context tContext = new InitialContext();
			//����JNDI��Դ
			tContext = (Context) tContext.lookup("java:comp/env");
			//����Java���ݿ�����ͳһ��Դ��λ���õ����ݿ����ƶ���
			Object obj = tContext.lookup(JUrl.getDBName());
			//ǿתΪ����Դ
			DataSource tDataSource = (DataSource) obj;
			//����Դ�ǿ�
			if (tDataSource != null) {
				//����Դ��ȡ����
				con = tDataSource.getConnection();
				// ������ӵ���Oracle���ݿ⣬��Ҫ��΢����һ�����ڵĸ�ʽ��������ڷ�������������һ�£�������������ĳ���
				// �������һ���ֶ����ͣ��������Ƿ�ʹ����������
				//���ӷǿ�
				if (con != null) {
					// Statement stmt = con.createStatement(ResultSet.
					// TYPE_SCROLL_SENSITIVE,
					// ResultSet.CONCUR_UPDATABLE);
					// stmt.execute(
					// "alter session set nls_date_format = 'YYYY-MM-DD
					// HH24:MI:SS'");
					// stmt.close();
					return true;//������(���ӷǿ�)
				}
				return false;//���ؼ�(����Ϊ��)
				//����ԴΪ��
			} else {
				//�ڻ�ȡ����Դʱһ��������
				System.out.println("a error occured when geting datasource");
				return false;//���ؼ�(����ԴΪ��)
			}
			//��ȡ���������ݿ������쳣
		} catch (Throwable e) {
			//������apacheͨ�����ݿ����ӳ�ʱʧ��
			System.out.println("failure when connect apache commons dbcp ");
			//��ӡ��ջ����
			e.printStackTrace();
			//ʧ�ܷ��ؼ�
			return false;
		}
	}

	//��һ����Դ
	static DataSource singleDataSource;

	/**
	 * ��WebSphere�ṩ�����ӳ���ȡ���ӣ�ʧ�ܷ���false
	 * �õ�WebSphere���ӳ�
	 * @return boolean
	 */
	private boolean getWebSpherePoolConnection() {
		try {
			//����Դ
			DataSource tDataSource;
			//��һ����Դ�ǿ�
			if (singleDataSource != null) {
				//��һ����Դ��ֵ������Դ
				tDataSource = singleDataSource;
				//��һ����ԴΪ��
			} else {
				//��ϣ��
				Hashtable env = new Hashtable();
				//���ã����󣬶���
				env.put(javax.naming.Context.INITIAL_CONTEXT_FACTORY,//��������ָ��Ҫʹ�õĳ�ʼ�����Ĺ����Ļ�����������
						"com.ibm.websphere.naming.WsnInitialContextFactory");//��������ʼ�����ĵĹ��������ȫ�޶�������
				env.put(javax.naming.Context.PROVIDER_URL,//ָ���ṩ��������� WebSphere �� URL
						"iiop://localhost:2809");//�˿�ָ����BOOTSTAR_ADDRESS �˿� ��websphere����̨�ܲ鵽
				//ʹ�����ṩ�Ļ�������һ����ʼ������
				Context tContext = new InitialContext(env);
				//����
				Object obj;
				
				// if(JUrl.getDBName().startsWith("jdbc/"))
				// {
				// obj = tContext.lookup(JUrl.getDBName());
				// }
				// else
				// {
				// obj = tContext.lookup("jdbc/"+JUrl.getDBName());
				// }
				//����java:comp/env/Java���ݿ�����ͳһ��Դ��λ���õ����ݿ�����
				obj = tContext.lookup("java:comp/env/" + JUrl.getDBName());
				//ǿתΪ����Դ
				tDataSource = (DataSource) obj;
				//����Դ��ֵ����һ����Դ
				singleDataSource = tDataSource;
			}
			
			// Context tContext = new InitialContext();
			// �����web.xml�����������ö������������ķ���
			// DataSource tDataSource = (DataSource)
			// tContext.lookup("java:comp/env/" +
			// JUrl.getDBName());
			// ����ķ���Ҳ���Է��ֵ�jndi����
			// DataSource tDataSource = (DataSource)
			// tContext.lookup("jdbc/MET");
			// ����������־��������´�����Ϣ��websphere���������
			// [03-9-2 17:19:11:916 CST] 6b0e97e8 ConnectionFac I J2CA0122I:
			// �޷���λ��Դ����
			// jdbc/db2ds�����ʹ������ȱʡֵ��[Resource-ref settings]
			// res-auth: 1 (APPLICATION)
			// res-isolation-level: 0 (TRANSACTION_NONE)
			// res-sharing-scope: true (SHAREABLE)
			// res-resolution-control: 999 (undefined)
			//����Դ�ǿ�
			if (tDataSource != null) {
				//����Դ�������
				con = tDataSource.getConnection();
				//���ӷǿ�
				if (con != null) {
					//��WebSphere���ӳɹ���
					System.out.println("Connect succeed from websphere!");
					//���سɹ�
					return true;
				//����Ϊ��
				} else {
					//�����Ӵ���
					System.out.println("new Connection error ...");
					//����ʧ��
					return false;
				}
			//����ԴΪ��
			} else {
				//������Դ����...
				System.out.println("new DataSource error ...");
				//����ʧ��
				return false;
			}
		//����Դ��ȡ�����쳣
		} catch (Throwable e) {
			//look for jndi name error ...Java���ݿ�����ͳһ��Դ��λ���õ����ݿ�����
			System.out.println("look for jndi name error ..."
					+ JUrl.getDBName());
			//��ӡ��ջ����
			e.printStackTrace();
			//����ʧ��
			return false;
		}
	}

	/*
	 * kevin 2002-10-04 friend function used by DBConnPool
	 * ���ݿ�����
	 */
	protected DBConn() {
		//Java���ݿ�����ͳһ��Դ��λ��
		JUrl = new JdbcUrl();
		//����ʹ��
		bNotInUse = true;
	}

	/**
	 * ���ڲ��ر�
	 * @return 
	 */
	protected boolean isInnerClose() {
		try {
			//����Ϊ��
			if (con == null) {
				return true;//�������ڲ��ر�
			}
			//���ӷǿգ����ش� Connection �����Ƿ��Ѿ����ر�
			return con.isClosed();
			//�쳣
		} catch (SQLException ex) {
			return true;//������
		}
	}

	/**
	 * ��ӡ����ǰ�Ķ�ջ��Ϣ,������Է�������,���������ڹ�����
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
	// // .append("\r\n################# Start of ��ջ��Ϣ
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
	// System.out.println("�������ݲ��ɹ�");
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	//
	// }
	// // buf.append("################# End of ��ջ��Ϣ ####################");
	// }

	/**
	 * ����ʹ��
	 */
	protected void setInUse() {
		/**
		 * Record stack information when each connection is get We reassian
		 * System.err, so Thread.currentThread().dumpStack() can dump stack info
		 * into our class FilterPrintStream.
		 */
		// �����쳣�ĳ����ӡ��ջ�������ı��������ӡ����ĸ�ʽ����ʾ��ʽ
		new Throwable().printStackTrace(m_pw);
		// SysLog sysLog = new SysLog();
		// ��Ʒ������Ϣ���
		// PrintStackInfo("open");
		bNotInUse = false;// ��ʹ��(����ʹ����Ϊ��)

		/**
		 * record lastest access time
		 */
		setLastestAccess();// �������һ�����Է���
	}

	/**
	 * is this dbconn been used by someone ��ʹ��
	 * 
	 * @return boolean
	 */
	protected boolean isInUse() {
		return !bNotInUse;// ��ʹ��(����ʹ����Ϊ��)
	}

	/*
	 * kevin 2002-10-04 Note: JDK 1.3 implements of java.sql.Connection
	 * ������ݿ����Ӿ���
	 */
	public void clearWarnings() throws SQLException {
		//���Ϊ�� Connection ���󱨸�����о��档���ô˷�������Ϊ�� Connection ���󱨸��µľ���ǰ��getWarnings ���������� null��
		con.clearWarnings();
	}

	/**
	 * ʵ����Connection�ӿڵ�close()������û�������ĶϿ����ӣ� ���ǽ����ӷŻ����ӳأ���bNotInUse���ó�δʹ�á�
	 * �ر�
	 * @throws SQLException
	 */
	public void close() throws SQLException {
		// SysLog sysLog = new SysLog();
		// ��Ʒ������Ϣ���
		// Logger log = SysLog.getLogger("DBconnLog");
		// log.info("close-" + PrintStackInfo());
		// PrintStackInfo("close");
		//�ǳ�
		if (bIsPool) {
			// �����ͨ��weblogic���ӳصõ�����
			// �˴���close()�����ǽ����ӷŻ�weblogic���ӳء�
			// ������websphere��tomcat�Ƿ�Ҳʹ�ô˷���
			//�����ͷ����Ӷ���ռ�õ����ݿ�������Դ
			con.close();
			//����ʹ��
			bNotInUse = true;
			//�ǳ�
		} else {
			// clear stack info of connection
			//��������ʼλ�����õ����һ�ε���mark�����Ķ�ȡλ��
			m_buf.reset();
			//����ʹ��
			bNotInUse = true;
		}
	}

	/**
	 * �ύ
	 */
	public void commit() throws SQLException {
		//�ֶ��ύ
		con.commit();
	}

	/**
	 * �������
	 */
	public Statement createStatement() throws SQLException {
		return con.createStatement();//����ͨ������ʵ������������
	}

	/**
	 * �������
	 * @param resultSetType ���������
	 * @param resultSetConcurrency ���������
	 */
	public Statement createStatement(int resultSetType, int resultSetConcurrency)
			throws SQLException {
		//����ͨ�����Ӵ������
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
