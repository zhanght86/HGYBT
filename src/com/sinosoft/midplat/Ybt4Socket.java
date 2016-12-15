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
    //TranLogNo:6863
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
    //前端通讯模块：com.sinosoft.midplat.newccb.newNet.CcbNetImpl
    this.cLogger.info("前端通讯模块：" + mPreNetClass);
    Constructor tPreNetConstructor = Class.forName(
      mPreNetClass).getConstructor(new Class[] { Socket.class, Element.class });
    this.cPreNet = ((SocketNetImpl)tPreNetConstructor.newInstance(new Object[] { pSocket, this.cThisConfRoot }));
  }

  public final void run()
  {
	 //开始毫秒数:1481593022084[2016-12-13 09:37:02]
    long mStartMillis = System.currentTimeMillis();
    //Into Ybt4Socket.run()...
    this.cLogger.info("Into Ybt4Socket.run()...");
   //初始化输出非标准报文 
    Document mOutNoStd = null;
    try {
      //接收输入非标准报文
      Document tInNoStd = this.cPreNet.receive();
      Element tHeadEle = tInNoStd.getRootElement().getChild("Head");
      String tFormatClassName = "com.sinosoft.midplat.format.XmlSimpFormat";//com.sinosoft.midplat.format.XmlSimpFormat
      String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";//com.sinosoft.midplat.service.ServiceImpl
      
      //null
      String tFormatValue = this.cMidplatRoot.getChildText("format");
      //com.sinosoft.midplat.service.SimpService
      String tServiceValue = this.cMidplatRoot.getChildText("service");
      //false[null != null]
      if ((tFormatValue != null) && (!"".equals(tFormatValue))) {
        tFormatClassName = tFormatValue;
      }
      //true[com.sinosoft.midplat.service.SimpService != null]
      if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
    	//com.sinosoft.midplat.service.ServiceImpl=com.sinosoft.midplat.service.SimpService
        tServiceClassName = tServiceValue;
      }
      //null[<newccb/business/format>]
      tFormatValue = this.cThisConfRoot.getChildText("format");
      //null[<newccb/business/service>]
      tServiceValue = this.cThisConfRoot.getChildText("service");
      //null != null
      if ((tFormatValue != null) && (!"".equals(tFormatValue))) {
        tFormatClassName = tFormatValue;
      }
      //null != null
      if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
        tServiceClassName = tServiceValue;
      }
      
      //business[funcFlag='交易码']
      //child::business[(child::funcFlag = "1012")]
      XPath tXPath = XPath.newInstance("business[funcFlag='"+tHeadEle.getChildText("FuncFlag")+ "']");
      //[Element: <business/>]
      Element tBusinessEle = (Element)tXPath.selectSingleNode(this.cThisConfRoot);
      //<business/> != null
      if (tBusinessEle != null) {
    	 //com.sinosoft.midplat.newccb.format.NewCont
        String ttClass = tBusinessEle.getChildText("format");
        //com.sinosoft.midplat.newccb.format.NewCont != null
        if ((ttClass != null) && (!"".equals(ttClass))) {
        	//com.sinosoft.midplat.format.XmlSimpFormat=com.sinosoft.midplat.newccb.format.NewCont
          tFormatClassName = ttClass;
        }
        //[Element: <business/service>]
        //com.sinosoft.midplat.newccb.service.NewContInput
        ttClass = tBusinessEle.getChildText("service");
        //com.sinosoft.midplat.newccb.service.NewContInput != null
        if ((ttClass != null) && (!"".equals(ttClass))) {
          //com.sinosoft.midplat.service.SimpService=com.sinosoft.midplat.newccb.service.NewContInput
          tServiceClassName = ttClass;
        }
      }
      //报文转换模块：com.sinosoft.midplat.newccb.format.NewCont
      this.cLogger.info("报文转换模块：" + tFormatClassName);
      //public com.sinosoft.midplat.newccb.format.NewCont(org.jdom.Element)
      Constructor tFormatConstructor = Class.forName(
        tFormatClassName).getConstructor(new Class[] { Element.class });
      //格式化对象
      Format tFormat = (Format)tFormatConstructor.newInstance(new Object[] { tBusinessEle });
      //格式化非输入标准报文为输入标准报文
      Document InStd=tFormat.noStd2Std(tInNoStd);
      //业务处理模块：com.sinosoft.midplat.newccb.service.NewContInput
      this.cLogger.info("业务处理模块：" + tServiceClassName);
      //public com.sinosoft.midplat.newccb.service.NewContInput(org.jdom.Element)
      Constructor tServiceConstructor = Class.forName(
        tServiceClassName).getConstructor(new Class[] { Element.class });
      //com.sinosoft.midplat.newccb.service.NewContInput
      Service tService = (Service)tServiceConstructor.newInstance(new Object[] { tBusinessEle });
      //
      Document tOutStd = tService.service(InStd);
      //输出非标准报文
      mOutNoStd = tFormat.std2NoStd(tOutStd);
    } catch (NetException ex) {
      this.cLogger.error("通讯接口异常!", ex);
      mOutNoStd = MidplatUtil.getSimpOutXml(1, ex.getMessage());
    } catch (MidplatException ex) {
      this.cLogger.error("预期校验!", ex);
      mOutNoStd = MidplatUtil.getSimpOutXml(1, ex.getMessage());
    } catch (Throwable ex) {
      this.cLogger.error("系统出现非预期异常！", ex);
      mOutNoStd = MidplatUtil.getSimpOutXml(1, "系统异常，请联系保险公司！");
    } finally {
      if (mOutNoStd != null) {
        try {
          this.cPreNet.send(mOutNoStd);
        } catch (Exception ex) {
          //发送返回报文异常!
          this.cLogger.error("发送返回报文异常!", ex);
        }
      }
      //初始化数据库操作类实例
      TranLogDB tranLogDB = new TranLogDB();
      //设置日志号[5357]
	  tranLogDB.setLogNo(Thread.currentThread().getName());
	  //!false=true
	  if(!tranLogDB.getInfo()){
		  //查询Tranlog表异常!
		  cLogger.error("查询Tranlog表异常!");
	  } else {
		  tranLogDB.setBak3((System.currentTimeMillis()-mStartMillis)/1000.0+"");
		  tranLogDB.setOutNoDoc(cPreNet.cOutNoStdDoc);
		  if(!tranLogDB.update()){
			  cLogger.error("更新Tranlog表异常!");
		  }
	  }
	 
      this.cPreNet.close();
    }
    //处理总耗时：529.874s
    //处理总耗时：19629.266s
    this.cLogger.info("处理总耗时：" + (System.currentTimeMillis() - mStartMillis) / 1000.0D + "s");
    //Out Ybt4Socket.run()!
    this.cLogger.info("Out Ybt4Socket.run()!");
  }
}