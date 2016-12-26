package com.sinosoft.midplat;

import com.sinosoft.lis.db.TranLogDB;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.exception.NetException;
import com.sinosoft.midplat.format.Format;
import com.sinosoft.midplat.net.SocketNetImpl;
import com.sinosoft.midplat.service.Service;
import java.lang.reflect.Constructor;
import java.net.Socket;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

public abstract class Ybt4Socket extends Thread
  implements XmlTag
{
  protected final Logger cLogger = Logger.getLogger(getClass());
  //套接字网络实现类实例
  private final SocketNetImpl cPreNet;
  protected final Element cMidplatRoot;
  protected final Element cThisConfRoot;

  public Ybt4Socket(Socket pSocket, XmlConf pThisConf)
    throws Exception
  {
    setName(String.valueOf(NoFactory.nextTranLogNo()));
    cLogger.info("TranLogNo:"+getName());
    this.cThisConfRoot = pThisConf.getConf().getRootElement();
    this.cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();

    String mPreNetClass = "com.sinosoft.midplat.net.SocketNetImpl";
    String mClassName = this.cMidplatRoot.getChildText("preNet");
    if ((mClassName != null) && (!"".equals(mClassName))) {
      mPreNetClass = mClassName;
    }
    mClassName = this.cThisConfRoot.getChildText("preNet");
    if ((mClassName != null) && (!"".equals(mClassName))) {
      mPreNetClass = mClassName;
    }
    this.cLogger.info("前端通讯模块：" + mPreNetClass);
    Constructor tPreNetConstructor = Class.forName(
      mPreNetClass).getConstructor(new Class[] { Socket.class, Element.class });
    this.cPreNet = ((SocketNetImpl)tPreNetConstructor.newInstance(new Object[] { pSocket, this.cThisConfRoot }));
  }

  public final void run()
  {
	//[1482742347013:2016-12-26 04:52:27,1482752389487:2016-12-26 07:39:49]
    long mStartMillis = System.currentTimeMillis();
    //Into Ybt4Socket.run()...[进入Ybt4Socket.run]
    this.cLogger.info("Into Ybt4Socket.run()...");
    //非标准输出报文[out_Std.xml]
    Document mOutNoStd = null;
    //交易流水号
    String tranNo=null;
    try {
      //套接字网络实现类实例接收[银行非标准]报文，增加标准报文头
      Document tInNoStd = this.cPreNet.receive();
      /*获取非标准输入报文节点:Head、TranNo*/
      Element tHeadEle = tInNoStd.getRootElement().getChild("Head");//[TX/Head]
      tranNo=tInNoStd.getRootElement().getChild("Head").getChildText("TranNo");//[TX/Head/TranNo]
      //非标准输入报文移除TranNo子节点
      tInNoStd.getRootElement().getChild("Head").removeChild("TranNo");
      //报文转换类名
      String tFormatClassName = "com.sinosoft.midplat.format.XmlSimpFormat";//初始值
      //报文处理类名
      String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";//初始值
      
      //midplat.xml的报文转换类
      String tFormatValue = this.cMidplatRoot.getChildText("format");//[midplat/format]
      //midplat.xml的报文处理类
      String tServiceValue = this.cMidplatRoot.getChildText("service");//[midplat/service]
      //报文转换类非空、空字符
      if ((tFormatValue != null) && (!"".equals(tFormatValue))) {
    	 //为成员报文转换类名赋值
        tFormatClassName = tFormatValue;//默认:空
      }
      //报文处理类非空、空字符
      if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
    	  //为成员报文处理类名赋值
        tServiceClassName = tServiceValue;//默认:空
      }
      //newccb.xml的报文转换类
      tFormatValue = this.cThisConfRoot.getChildText("format");//[newccb/format]
    //newccb.xml的报文处理类
      tServiceValue = this.cThisConfRoot.getChildText("service");//[newccb/service]
      //报文转换类非空、空字符
      if ((tFormatValue != null) && (!"".equals(tFormatValue))) {
    	  //为成员报文转换类名赋值
        tFormatClassName = tFormatValue;//默认:空
      }
      //报文处理类非空、空字符
      if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
    	//为成员报文处理类名赋值
        tServiceClassName = tServiceValue;//默认:空
      }
      
      //business[funcFlag='1080']
      //取funcFlag节点值为1080的business节点路径
      XPath tXPath = XPath.newInstance("business[funcFlag='"+tHeadEle.getChildText("FuncFlag")+ "']");
      //在newccb.xml中使用路径表达式选择funcFlag为1080的单一business节点
      //路径表达式选择单一节点
      Element tBusinessEle = (Element)tXPath.selectSingleNode(this.cThisConfRoot);
      //business节点非空
      if (tBusinessEle != null) {
    	 //获取报文转换类
        String ttClass = tBusinessEle.getChildText("format");//取值
        //报文转换类非空、空字符
        if ((ttClass != null) && (!"".equals(ttClass))) {
          //为成员报文转换类名赋值
          tFormatClassName = ttClass;//保存
        }
        //获取报文处理类
        ttClass = tBusinessEle.getChildText("service");//取值
        //报文处理类非空、空字符
        if ((ttClass != null) && (!"".equals(ttClass))) {
          //为成员报文处理类名赋值
          tServiceClassName = ttClass;//保存
        }
      }
      //报文转换模块：
      this.cLogger.info("报文转换模块：" + tFormatClassName);//转换类
      //报文转换类构造器
      Constructor tFormatConstructor = Class.forName(
        tFormatClassName).getConstructor(new Class[] { Element.class });//构造器
      //报文转换类实例
      Format tFormat = (Format)tFormatConstructor.newInstance(new Object[] { tBusinessEle });//实例
      //非标准输入报文转换为标准输入报文
      Document InStd=tFormat.noStd2Std(tInNoStd);//转换
      //业务处理模块：
      this.cLogger.info("业务处理模块：" + tServiceClassName);//处理类
      //报文处理类构造器
      Constructor tServiceConstructor = Class.forName(
        tServiceClassName).getConstructor(new Class[] { Element.class });//构造器
      //报文处理类实例
      Service tService = (Service)tServiceConstructor.newInstance(new Object[] { tBusinessEle });//实例
      //业务处理类将标准输入报文处理成标准输出报文
      Document tOutStd = tService.service(InStd);//处理
      //标准输出报文转换为非标准输出报文
      mOutNoStd = tFormat.std2NoStd(tOutStd);//转换
    } catch (NetException ex) {
     /*网络[通讯]异常*/
      this.cLogger.error("通讯接口异常!", ex);
      mOutNoStd = MidplatUtil.getSimpOutXml(1, ex.getMessage());
    } catch (MidplatException ex) {
      /*midplat.xml[配置]异常*/
      this.cLogger.error("预期校验!", ex);
      mOutNoStd = MidplatUtil.getSimpOutXml(1, ex.getMessage());
    } catch (Throwable ex) {
      /*异常处理基类[系统]异常*/
      this.cLogger.error("系统出现非预期异常！", ex);
      mOutNoStd = MidplatUtil.getSimpOutXml(1, "系统异常，请联系保险公司！");
    } finally {
      /*最后:发送[非标准输出报文]、查询TranLog记录、关闭SocketNetImpl */
      //非标准输出报文非空
      if (mOutNoStd != null) {
        try {
          //套接字网络实现类实例:发送[非标准输出报文]
          this.cPreNet.send(mOutNoStd);
        } catch (Exception ex) {
          this.cLogger.error("发送返回报文异常!", ex);
        }
      }
      //交易日志记录
      TranLogDB tranLogDB = new TranLogDB();
      //设置交易流水号
	  tranLogDB.setTranNo(tranNo);
	  //交易流水号获取信息失败[!false=true]
	  if(!tranLogDB.getInfo()){
		  cLogger.error("查询Tranlog表异常!");
	  } else {
		  /*设置TranLog表2个字段:Bak3、OutNoDoc*/
		  tranLogDB.setBak3((System.currentTimeMillis()-mStartMillis)/1000.0+"");
		  tranLogDB.setOutNoDoc(cPreNet.cOutNoStdDoc);
		  //更新TranLog表记录失败[!false=true]
		  if(!tranLogDB.update()){
			  cLogger.error("更新Tranlog表异常!");
		  }
	  }
	 //关闭套接字网络实现类实例
      this.cPreNet.close();
    }
    //处理总耗时：[当前时间-开始时间]
    this.cLogger.info("处理总耗时：" + (System.currentTimeMillis() - mStartMillis) / 1000.0D + "s");
    //Out Ybt4Socket.run()![从Ybt4Socket.run出来]
    this.cLogger.info("Out Ybt4Socket.run()!");
  }
}