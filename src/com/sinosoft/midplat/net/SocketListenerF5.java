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
 * �׽��ּ�����
 * @author yuantongxin
 * @version 1.0
 */
public class SocketListenerF5 implements ServletContextListener, XmlTag
{
	private final static Logger cLogger = Logger.getLogger(SocketListenerF5.class);
	//���������������
	private Server[] cServers = null;
	
	/**
	 * �׽��ּ���������
	 */
	public SocketListenerF5()
	{
		//System.out = org.apache.tomcat.util.log.SystemLogHandler@13899213
		cLogger.debug("System.out = " + System.out);//����׼�������
		//�м�ƽ̨������ʵ��������־
		if (MidplatConf.newInstance().resetLog())
		{
			//Start reset System.out...[��ʼ����ϵͳ�����]
			System.out.println("Start reset System.out...");
			//���·��䡰��׼���������
			System.setOut(new Log4jPrint(System.out));
			//���·��䡰��׼�������������
			System.setErr(new Log4jPrint(System.err));
			//End reset System.out![��������ϵͳ���!]
			System.out.println("End reset System.out!");
		}
		//�׽���������ʵ�����ü�����ǰʵ��
		SocketConf.newInstance().setListener(this); // ע�ᵽSocket���û����У��������ú��Զ�������ط��������˿ڼ���
		
		// ���������ļ��������ϵͳʹ���߳̿�ʼִ��
		FileCacheManage.newInstance().start();
	}

	public static void main(String[] args) throws Exception
	{
		new SocketListenerF5().contextInitialized(null);
	}

	/**
	 * �����ĳ�ʼ��(Servlet����������ʱ�򣬽���ִ�д˷���)
	 * @param pEvent servlet�������¼�
	 */
	@SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent pEvent)
	{
		//Into SocketListenerF5.contextInitialized()...[�����׽��������������ĳ�ʼ��]
		cLogger.info("Into SocketListenerF5.contextInitialized()...");
		//[Element: <sockets/>]��ȡxml�����ļ��Ļ������
		Document mSocketConfDoc = SocketConf.newInstance().getConf();
		//[Element: <sockets/>]��ȡ��Ԫ��socket��Ԫ���б�
		List<Element> mSocketList = mSocketConfDoc.getRootElement().getChildren(socket);
		//[[Element: <socket/>], [Element: <socket/>], [Element: <socket/>], [Element: <socket/>]]
		int mSize = mSocketList.size();//size:4�����б��е�Ԫ����
		//��ʼ��������������[�׽��ֳ��ȸ�Ԫ��]
		cServers = new Server[mSize];
		//�����׽����б�
		for (int i = 0; i < mSize; i++)
		{
			try
			{
				//�����׽����б���ָ��λ�õ�Ԫ��
				Element ttSocket = mSocketList.get(i);
				//0_Socket(ũҵ����): port=35006; class=com.sinosoft.midplat.abc.AbcYbt
				//1_Socket(��������): port=35017; class=com.sinosoft.midplat.gzbank.GZBankYbt
				cLogger.info(i + "_Socket(" + ttSocket.getChildText(name) + "): port=" + ttSocket.getChildText(port) + "; class="
						+ ttSocket.getChildText("class"));
				//�׽��ֵõ��׽��ֶ˿ں���Ԫ���ı�[35006,35017,]
				int ttPort = Integer.parseInt(ttSocket.getChildText(port));
				//�׽��ֵõ��׽�������Ԫ���ı�[com.sinosoft.midplat.abc.AbcYbt]
				String ttClassName = ttSocket.getChildText("class");
				//Thread[Thread-2,5,main]�����������ʵ��[�˿ں�:35006������:com.sinosoft.midplat.abc.AbcYbt;�˿ں�:35017������:com.sinosoft.midplat.gzbank.GZBankYbt]
				Server ttServer = new Server(ttPort, ttClassName);
				//�����������鱣�浱ǰ��������ʵ��[0,1,2,3]
				cServers[i] = ttServer;		
				//��������ʵ��ʹ���߳̿�ʼִ��[Java ��������ø��̵߳� run ����]
				ttServer.start();
				//0_Socket(ũҵ����)���سɹ�!
				cLogger.info(i + "_Socket(" + ttSocket.getChildText(name) + ")���سɹ�!");
			}
			catch (Throwable ex)
			{
				cLogger.error(i + "_Socket����ʧ��!", ex);
			}
		}

		cLogger.info("Out SocketListenerF5.contextInitialized()!");
	}

	/**
	 * ����������(Servlet�����ر�ʱ�����ִ�д˷���)
	 * @param pEvent servlet�������¼�
	 */
	public void contextDestroyed(ServletContextEvent pEvent)
	{
		cLogger.info("Into SocketListenerF5.contextDestroyed()...");

		cLogger.info("�ر�ServerSocket����...");
		/**
		 * һ��Ҫ�ر�ServerSocket���������classʱ�Զ���������SocketListenerF5���¼��أ�
		 * �����ظ�����ͬһ���˿ڣ��׳��˿���ռ���쳣(java.net.BindException)��
		 */
		for (int i = 0; i < cServers.length; i++)
		{
			/**
			 * �ڼ���socketConfig.xml��ĳ��ʧ��ʱ��cServers�ж�Ӧ��ֵ��Ϊnull���ڴ�����˵���
			 */
			if (null != cServers[i])
			{
				cServers[i].close();
			}
		}

		// �رջ������ϵͳ
		FileCacheManage.newInstance().shutdown();

		// �ָ�System.out
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
	 * ��������
	 * @author yuantongxin
	 */
	private class Server extends Thread
	{
		private int cPort = -1;//�˿ں�
		private Constructor<Thread> cConstructor = null;//������
		private ServerSocket cServerSocket = null;//���������׽���
		
		private Logger cLogger = Logger.getLogger(getClass());
		
		/**
		 * �����������ʵ��
		 * @param pPort �˿ں� 
		 * @param pClassName ����
		 * @throws Exception
		 */
		@SuppressWarnings("unchecked")
		public Server(int pPort, String pClassName) throws Exception
		{
			//ʵ�γ�ʼ���˿ں�
			cPort = pPort;
			//ʵ�γ�ʼ��������
			cConstructor = (Constructor<Thread>) Class.forName(pClassName).getConstructor(new Class[]
			{ Socket.class });
		}
		
		/**
		 * ����
		 */
		public void run()
		{
			try
			{
				//��ʼ�����������׽���
				cServerSocket = new ServerSocket(cPort);
				//�����˿� 35006[ũҵ����]
				//�����˿� 35006[ũҵ����]
				cLogger.info("�����˿� " + cServerSocket.getLocalPort());
			}
			catch (IOException ex)
			{
				cLogger.error("�����������׽���ʧ�ܣ�" + cPort, ex);
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
						cLogger.debug("F5̽�⣺" + tF5Ip + ":" + tSocket.getLocalPort());
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
					cLogger.error("�ȴ��ͻ�������ʱ����I/O����" + cServerSocket.getLocalPort(), ex);
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
					cLogger.error("���ر�������Ĵ����߳�ʧ��!", ex);
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
