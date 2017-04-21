package com.sinosoft.midplat.net;

import java.io.IOException;

import java.net.Socket;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.exception.NetException;

/**
 * 套接字网络通讯实现类
 * @author yuantongxin
 */
public class SocketNetImpl implements XmlTag {
	//生成一个本类的日志记录器
	protected final Logger cLogger = Logger.getLogger(getClass());
	
	//套接字
	protected final Socket cSocket;
	//[客户端/银行端]IP
	protected final String cClientIp;
	
	//当前配置文件根节点
	protected final Element cThisConfRoot;
	
	//交易机构代码节点
	protected final Element cTranComEle;
	//交易类型
	protected String cFuncFlag;
	//非标准输出报文
	public  String cOutNoStdDoc;
	
	/**
	 * <p>Title: SocketNetImpl</p>
	 * <p>Description: 套接字网络通讯实现类构造器</p>
	 * @param pSocket 套接字(入参)
	 * @param pThisConfRoot 当前配置文件根节点(入参)
	 * @throws MidplatException
	 */
	public SocketNetImpl(Socket pSocket, Element pThisConfRoot) throws MidplatException {
		//为成员套接字赋值
		cSocket = pSocket;
		//获取套接字主机IP地址
		cClientIp = cSocket.getInetAddress().getHostAddress();
		//客户端IP：127.0.0.1；服务端口：39871
		cLogger.info("客户端IP：" + cClientIp + "；服务端口：" + cSocket.getLocalPort());
		//为成员当前配置文件根节点赋值
		cThisConfRoot = pThisConfRoot;
		//为成员交易机构代码节点赋值[克隆当前配置文件根节点下TranCom子节点]
		cTranComEle = (Element) cThisConfRoot.getChild(TranCom).clone();
		
		//获取当前配置文件根节点ip子节点文本内容
		String mOkIp = cThisConfRoot.getChildText(ip);
		//确定IP非空且不包含客户端IP
		if (null!=mOkIp && !mOkIp.contains(cClientIp)) {
			//抛出网络异常[非法ip：客户端IP]
			throw new NetException("非法ip："+cClientIp);
		}
	}
	
	/**
	 * 接收[银行非标准输入]报文，增加标准报文头
	 * @return
	 * @throws Exception
	 */
	public Document receive() throws Exception {
		//Into SocketNetImp.receive()...
		cLogger.info("Into SocketNetImp.receive()...");
		//输入输出转换接收到的数据为字节数组不关闭流
		byte[] mBodyBytes = 
			IOTrans.toBytes5Close(cSocket.getInputStream());
		//关闭输入流
		cSocket.shutdownInput();
		//采用GBK编码将接收数据字节数组构建成非标准输入报文对象，忽略标签之间的空字符(空格、换行、制表符等)
		Document mXmlDoc = JdomUtil.build(mBodyBytes);
		
		//测试
		JdomUtil.print(mXmlDoc);
		//获取[非标准输入报文]头节点
		Element mHeadEle = mXmlDoc.getRootElement().getChild(Head);
		//获取[非标准输入报文]交易类型节点
		Element mFuncFlagEle = mHeadEle.getChild(FuncFlag);
		//新建XML路径表达式(交易节点/交易类型子节点[外部代码为交易类型节点文本内容])
		XPath mXPath = XPath.newInstance(
				"business/funcFlag[@outcode='" + mFuncFlagEle.getText() + "']");
		//计算表达式的值产生一个交易类型
		cFuncFlag = mXPath.valueOf(cThisConfRoot);
		//交易类型为空
		if (null == cFuncFlag) {	//未定义内部交易码，直接取传过来的数据
			//交易类型赋值为[非标准输入报文]交易类型节点文本内容
			cFuncFlag = mFuncFlagEle.getText();
		}
		
		//保存文件名[线程名_下一个顺序号_交易码_in.xml]
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append('_').append(cFuncFlag)
			.append("_in.xml");
		//保存{[非标准]+[输入]}报文，到[交易机构代码]目录下，线程名_下一个顺序号_[交易码]_{[in]}.xml文件中
		SaveMessage.save(mXmlDoc, cTranComEle.getText(), mSaveName.toString());
		//保存报文完毕！保存文件名
		cLogger.info("保存报文完毕！"+mSaveName);
		
		//填充Head
		mFuncFlagEle.setText(cFuncFlag);
		//新建银行端IP节点
		Element mClientIpEle = new Element(ClientIp);
		//设置文本内容为银行端IP
		mClientIpEle.setText(cClientIp);
		//[非标准输入报文]头节点加入银行端IP节点
		mHeadEle.addContent(mClientIpEle);
		//[非标准输入报文]头节点加入交易机构代码节点
		mHeadEle.addContent(cTranComEle);
		
		cLogger.info("Out SocketNetImp.receive()!");
		//返回非标准输入报文
		return mXmlDoc;
	}
	
	/**
	 * 发送[银行非标准输出]报文到银行端
	 * @param pOutNoStd 输出非标准报文
	 * @throws Exception 
	 */
	public void send(Document pOutNoStd) throws Exception {
		//Into SocketNetImp.send()...
		cLogger.info("Into SocketNetImp.send()...");
		//保存[非标准输出报文]文件名[线程名_下一顺序号_交易码_out.xml]
		StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
			.append('_').append(NoFactory.nextAppNo())
			.append('_').append(cFuncFlag)
			.append("_out.xml");
		//保存{[非标准]+[输出]}报文，到[交易机构代码]目录下，线程名_下一个顺序号_[交易码]_{[out]}.xml文件中
		SaveMessage.save(pOutNoStd, cTranComEle.getText(), mSaveName.toString());
		//保存报文完毕！保存[非标准输出报文]文件名
		cLogger.info("保存报文完毕！"+mSaveName);
		//套接字输出流写入非标准输出报文GBK二进制序列
		cSocket.getOutputStream().write(JdomUtil.toBytes(pOutNoStd));
		//禁用套接字输出流
		cSocket.shutdownOutput();
		//Out SocketNetImp.send()![ 从SocketNetImp.send函数出来]
		cLogger.info("Out SocketNetImp.send()!");
	}
	
	/**
	 * 关闭套接字
	 */
	public final void close() {
		try {
			//关闭套接字
			cSocket.close();
		} catch (IOException ex) {
			//Socket可能已关闭！
			cLogger.debug("Socket可能已关闭！", ex);
		}
	}
}

