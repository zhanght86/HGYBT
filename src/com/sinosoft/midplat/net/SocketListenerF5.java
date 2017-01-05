package com.sinosoft.midplat.net;

import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.Log4jPrint;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.common.cache.FileCacheManage;

/**
 * 套接字监听器
 * @author yuantongxin
 * @version 1.0
 */
public class SocketListenerF5 implements ServletContextListener, XmlTag
{
	private final static Logger cLogger = Logger.getLogger(SocketListenerF5.class);
	//定义服务器端数组
	private Server[] cServers = null;
	
	/**
	 * 套接字监听构造器
	 */
	public SocketListenerF5()
	{
		//System.out = org.apache.tomcat.util.log.SystemLogHandler@13899213
		cLogger.debug("System.out = " + System.out);//“标准”输出流
		//中间平台配置新实例重置日志
		if (MidplatConf.newInstance().resetLog())
		{
			//Start reset System.out...[开始重置系统输出…]
			System.out.println("Start reset System.out...");
			//重新分配“标准”输出流。
			System.setOut(new Log4jPrint(System.out));
			//重新分配“标准”错误输出流。
			System.setErr(new Log4jPrint(System.err));
			//End reset System.out![结束重置系统输出!]
			System.out.println("End reset System.out!");
		}
		//套接字配置新实例设置监听当前实例
		SocketConf.newInstance().setListener(this); // 注册到Socket配置缓存中，调整配置后自动调用相关方法重启端口监听
		
		// 启动配置文件缓存管理系统使该线程开始执行
		FileCacheManage.newInstance().start();
	}

	public static void main(String[] args) throws Exception
	{
		new SocketListenerF5().contextInitialized(null);
	}

	/**
	 * 上下文初始化(Servlet容器启动的时候，将会执行此方法)
	 * @param pEvent servlet上下文事件
	 */
	@SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent pEvent)
	{
		//Into SocketListenerF5.contextInitialized()...[进入套接字侦听器上下文初始化]
		cLogger.info("Into SocketListenerF5.contextInitialized()...");
		//[Element: <sockets/>]获取xml配置文件的缓存对象
		Document mSocketConfDoc = SocketConf.newInstance().getConf();
		//[Element: <sockets/>]获取根元素socket子元素列表
		List<Element> mSocketList = mSocketConfDoc.getRootElement().getChildren(socket);
		//[[Element: <socket/>], [Element: <socket/>], [Element: <socket/>], [Element: <socket/>]]
		int mSize = mSocketList.size();//size:4返回列表中的元素数
		//初始化服务器端数组[套接字长度个元素]
		cServers = new Server[mSize];
		//遍历套接字列表
		for (int i = 0; i < mSize; i++)
		{
			try
			{
				//返回套接字列表中指定位置的元素
				Element ttSocket = mSocketList.get(i);
				//0_Socket(农业银行): port=35006; class=com.sinosoft.midplat.abc.AbcYbt
				//1_Socket(贵州银行): port=35017; class=com.sinosoft.midplat.gzbank.GZBankYbt
				cLogger.info(i + "_Socket(" + ttSocket.getChildText(name) + "): port=" + ttSocket.getChildText(port) + "; class="
						+ ttSocket.getChildText("class"));
				//套接字得到套接字端口号子元素文本[35006,35017,]
				int ttPort = Integer.parseInt(ttSocket.getChildText(port));
				//套接字得到套接字类子元素文本[com.sinosoft.midplat.abc.AbcYbt]
				String ttClassName = ttSocket.getChildText("class");
				//Thread[Thread-2,5,main]构造服务器端实例[端口号:35006，类名:com.sinosoft.midplat.abc.AbcYbt;端口号:35017，类名:com.sinosoft.midplat.gzbank.GZBankYbt]
				Server ttServer = new Server(ttPort, ttClassName);
				//服务器端数组保存当前服务器端实例[0,1,2,3]
				cServers[i] = ttServer;		
				//服务器端实例使该线程开始执行[Java 虚拟机调用该线程的 run 方法]
				ttServer.start();
				//0_Socket(农业银行)加载成功!
				cLogger.info(i + "_Socket(" + ttSocket.getChildText(name) + ")加载成功!");
			}
			catch (Throwable ex)
			{
				cLogger.error(i + "_Socket加载失败!", ex);
			}
		}

		cLogger.info("Out SocketListenerF5.contextInitialized()!");
	}

	/**
	 * 上下文销毁(Servlet容器关闭时，则会执行此方法)
	 * @param pEvent servlet上下文事件
	 */
	public void contextDestroyed(ServletContextEvent pEvent)
	{
		cLogger.info("Into SocketListenerF5.contextDestroyed()...");

		cLogger.info("关闭ServerSocket监听...");
		/**
		 * 一定要关闭ServerSocket，否则更新class时自动重启服务，SocketListenerF5重新加载，
		 * 导致重复监听同一个端口，抛出端口已占用异常(java.net.BindException)。
		 */
		for (int i = 0; i < cServers.length; i++)
		{
			/**
			 * 在加载socketConfig.xml中某项失败时，cServers中对应的值会为null，在此需过滤掉。
			 */
			if (null != cServers[i])
			{
				cServers[i].close();
			}
		}

		// 关闭缓存管理系统
		FileCacheManage.newInstance().shutdown();

		// 恢复System.out
		cLogger.debug("System.out = " + System.out);
		if (MidplatConf.newInstance().resetLog())
		{
			System.out.println("Start revert System.out...");
			System.setOut((PrintStream) ((Log4jPrint) System.out).getSrcOs());
			System.setErr((PrintStream) ((Log4jPrint) System.err).getSrcOs());
			System.out.println("End revert System.out!");
		}

		cLogger.info("Out SocketListenerF5.contextDestroyed()!");
	}
	
	/**
	 * 服务器端
	 * @author yuantongxin
	 */
	private class Server extends Thread
	{
		private int cPort = -1;//端口号
		private Constructor<Thread> cConstructor = null;//构造器
		private ServerSocket cServerSocket = null;//服务器端套接字
		
		private Logger cLogger = Logger.getLogger(getClass());
		
		/**
		 * 构造服务器端实例
		 * @param pPort 端口号 
		 * @param pClassName 类名
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public Server(int pPort, String pClassName) throws Exception
		{
			//实参初始化端口号
			cPort = pPort;
			//实参初始化构造器
			cConstructor = (Constructor<Thread>) Class.forName(pClassName).getConstructor(new Class[]
			{ Socket.class });
		}
		
		/**
		 * 运行
		 */
		public void run()
		{
			try
			{
				//初始化服务器端套接字
				cServerSocket = new ServerSocket(cPort);
				//监听端口 35006[农业银行]
				//监听端口 35006[农业银行]
				cLogger.info("监听端口 " + cServerSocket.getLocalPort());
			}
			catch (IOException ex)
			{
				cLogger.error("创建服务器套接字失败！" + cPort, ex);
				return;
			}

			for (; true;)
			{
				Socket tSocket = null;
				try
				{
					tSocket = cServerSocket.accept();
					String tSkip = MidplatConf.newInstance().getConf().getRootElement().getChildText("skip");
					String tF5Ip = tSocket.getInetAddress().getHostAddress();
					if (null != tSkip && tSkip.contains(tF5Ip))
					{
						cLogger.debug("F5探测：" + tF5Ip + ":" + tSocket.getLocalPort());
						try
						{
							tSocket.close();
						}
						catch (IOException e)
						{
						}
						continue;
					}
					Thread ttPreControl = cConstructor.newInstance(new Object[]
					{ tSocket });
					ttPreControl.setName(String.valueOf(NoFactory.nextTranLogNo()));
					ttPreControl.start();
				}
				catch (IOException ex)
				{
					cLogger.error("等待客户端连接时发生I/O错误！" + cServerSocket.getLocalPort(), ex);
					if (null != tSocket)
						try
						{
							tSocket.close();
						}
						catch (IOException e)
						{
						}
					return;
				}
				catch (Throwable ex)
				{
					cLogger.error("加载本次请求的处理线程失败!", ex);
					if (null != tSocket)
						try
						{
							tSocket.close();
						}
						catch (IOException e)
						{
						}
				}
			}
		}

		public void close()
		{
			try
			{
				cServerSocket.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
