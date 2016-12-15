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

public class SocketListenerF5 implements ServletContextListener, XmlTag
{
	private final static Logger cLogger = Logger.getLogger(SocketListenerF5.class);

	private Server[] cServers = null;

	public SocketListenerF5()
	{
		//System.out = org.apache.tomcat.util.log.SystemLogHandler@13899213
		cLogger.debug("System.out = " + System.out);
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

	@SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent pEvent)
	{
		//Into SocketListenerF5.contextInitialized()...[进入套接字侦听器上下文初始化]
		cLogger.info("Into SocketListenerF5.contextInitialized()...");
		//[Element: <sockets/>]
		Document mSocketConfDoc = SocketConf.newInstance().getConf();
		//[Element: <sockets/>]获取根元素socket子元素列表
		List<Element> mSocketList = mSocketConfDoc.getRootElement().getChildren(socket);
		//[[Element: <socket/>], [Element: <socket/>], [Element: <socket/>], [Element: <socket/>]]
		int mSize = mSocketList.size();//size:4
		cServers = new Server[mSize];
		for (int i = 0; i < mSize; i++)
		{
			try
			{
				Element ttSocket = mSocketList.get(i);
				cLogger.info(i + "_Socket(" + ttSocket.getChildText(name) + "): port=" + ttSocket.getChildText(port) + "; class="
						+ ttSocket.getChildText("class"));
				int ttPort = Integer.parseInt(ttSocket.getChildText(port));
				String ttClassName = ttSocket.getChildText("class");
				Server ttServer = new Server(ttPort, ttClassName);
				cServers[i] = ttServer;
				ttServer.start();
				cLogger.info(i + "_Socket(" + ttSocket.getChildText(name) + ")加载成功!");
			}
			catch (Throwable ex)
			{
				cLogger.error(i + "_Socket加载失败!", ex);
			}
		}

		cLogger.info("Out SocketListenerF5.contextInitialized()!");
	}

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
