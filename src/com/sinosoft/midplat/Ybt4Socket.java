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
  private final SocketNetImpl cPreNet;
  protected final Element cMidplatRoot;
  protected final Element cThisConfRoot;

  public Ybt4Socket(Socket pSocket, XmlConf pThisConf)
    throws Exception
  {
    setName(String.valueOf(NoFactory.nextTranLogNo()));

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

  /**
   * 运行
   */
  public final void run()
  {
	//产生一个当前的毫秒
    long mStartMillis = System.currentTimeMillis();
    this.cLogger.info("Into Ybt4Socket.run()...");
    //输出非标准报文
    Document mOutNoStd = null;
    try {
      //输入非标准报文
      Document tInNoStd = this.cPreNet.receive();
      //输入非标准报报文头
      Element tHeadEle = tInNoStd.getRootElement().getChild("Head");
      JdomUtil.print(tHeadEle);//将Element打印到控制台， GBK编码，缩进3空格。
      //格式化类名称
      String tFormatClassName = "com.sinosoft.midplat.format.XmlSimpFormat";
      //服务类名称
      String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";
      //取中间平台根节点子元素格式化的内容 
      String tFormatValue = this.cMidplatRoot.getChildText("format");
      //取中间平台根节点子元素服务的内容
      String tServiceValue = this.cMidplatRoot.getChildText("service");
      //格式化的内容非空且非空字符
      if ((tFormatValue != null) && (!"".equals(tFormatValue))) {
        tFormatClassName = tFormatValue;//显式格式化的内容的值赋给隐式格式化类名称
      }
      //服务的内容非空且非空字符
      if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
        tServiceClassName = tServiceValue;//显式服务的内容的值赋给隐式服务类名称
      }
      //取当前配置文件根节点子元素格式化的内容
      tFormatValue = this.cThisConfRoot.getChildText("format");
      //取当前配置文件根节点子元素服务的内容
      tServiceValue = this.cThisConfRoot.getChildText("service");
      //格式化的内容非空且非空字符
      if ((tFormatValue != null) && (!"".equals(tFormatValue))) {
        tFormatClassName = tFormatValue;//显式格式化的内容的值赋给隐式格式化类名称
      }
      //服务的内容非空非空字符
      if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
        tServiceClassName = tServiceValue;//显式服务的内容的值赋给隐式服务类名称
      }
      //找到交易类型对应的业务
      XPath tXPath = XPath.newInstance(
        "business[funcFlag='" + tHeadEle.getChildText("FuncFlag") + "']");
      //根据一个xpath语句返回符合条件的第一个业务元素
      Element tBusinessEle = (Element)tXPath.selectSingleNode(this.cThisConfRoot);
      //业务元素非空
      if (tBusinessEle != null) {
    	 //取业务元素子元素格式化的内容 
        String ttClass = tBusinessEle.getChildText("format");
        //格式化的内容非空且非空字符
        if ((ttClass != null) && (!"".equals(ttClass))) {
          tFormatClassName = ttClass;//显式格式化的内容的值赋给隐式格式化类名称
        }
        //取业务元素子元素服务的内容 
        ttClass = tBusinessEle.getChildText("service");
        //服务的内容非空且非空字符
        if ((ttClass != null) && (!"".equals(ttClass))) {
          tServiceClassName = ttClass;//显式服务的内容的值赋给隐式服务类名称
        }
      }
      //报文转换模块：com.sinosoft.midplat.newccb.format.CardControl
      this.cLogger.info("报文转换模块：" + tFormatClassName);
      //格式化构造器
      Constructor tFormatConstructor = Class.forName(
        tFormatClassName).getConstructor(new Class[] { Element.class });
      //通过格式化构造器创建实例并初始化数组
      Format tFormat = (Format)tFormatConstructor.newInstance(new Object[] { tBusinessEle });
      Document tInStd = tFormat.noStd2Std(tInNoStd);//业务元素格式化对象非标准报文转标准报文
      //业务处理模块：com.sinosoft.midplat.newccb.service.ContCancelCardControlCcb
      this.cLogger.info("业务处理模块：" + tServiceClassName);
      //服务构造器
      Constructor tServiceConstructor = Class.forName(
        tServiceClassName).getConstructor(new Class[] { Element.class });
     //通过服务构造器创建实例并初始化数组
      Service tService = (Service)tServiceConstructor.newInstance(new Object[] { tBusinessEle });
      Document tOutStd = tService.service(tInStd);//业务元素服务对象服务取输出标准报文

      mOutNoStd = tFormat.std2NoStd(tOutStd);//业务元素格式化对象标准报文转非标准报文
    } catch (NetException ex) {
      this.cLogger.error("通讯接口异常!", ex);
    } catch (MidplatException ex) {
      this.cLogger.error("预期校验!", ex);
      mOutNoStd = MidplatUtil.getSimpOutXml(1, ex.getMessage());
    } catch (Throwable ex) {
      this.cLogger.error("系统出现非预期异常！", ex);
      mOutNoStd = MidplatUtil.getSimpOutXml(1, "系统异常，请联系保险公司！");
    } finally {
      //输出非标准报文非空
      if (mOutNoStd != null) {
	        try {
	          this.cPreNet.send(mOutNoStd);//发送[Element: <TX/>]
	        } catch (Exception ex) {
	          this.cLogger.error("发送返回报文异常!", ex);
	        }
      }
      		//交易日志数据库对象
      		TranLogDB tranLogDB = new TranLogDB();//初始化字段
      		//设置日志号
			tranLogDB.setLogNo(Thread.currentThread().getName());//1406
			//获取信息失败
			if(!tranLogDB.getInfo()){
				cLogger.error("查询Tranlog表异常!");
			}else{
				//设置备用3[0.078]
				tranLogDB.setBak3((System.currentTimeMillis()-mStartMillis)/1000.0+"");
				tranLogDB.setOutNoDoc(cPreNet.cOutNoStdDoc);//设置返回报文 
				//更新失败
				if(!tranLogDB.update()){
					cLogger.error("更新Tranlog表异常!");
				}
			}
      this.cPreNet.close();//关闭
    }
    //处理总耗时：3850.487s
    this.cLogger.info("处理总耗时：" + (System.currentTimeMillis() - mStartMillis) / 1000.0D + "s");
    //Out Ybt4Socket.run()!
    this.cLogger.info("Out Ybt4Socket.run()!");
  }
}