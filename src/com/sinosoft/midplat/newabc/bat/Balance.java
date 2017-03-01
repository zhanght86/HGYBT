package com.sinosoft.midplat.newabc.bat;
 
 import com.sinosoft.midplat.MidplatConf;
 import com.sinosoft.midplat.common.DateUtil;
 import com.sinosoft.midplat.common.IOTrans;
 import com.sinosoft.midplat.common.NoFactory;
 import com.sinosoft.midplat.common.XmlConf;
 import com.sinosoft.midplat.common.XmlTag;
 import com.sinosoft.midplat.exception.MidplatException;
 import com.sinosoft.midplat.service.Service;
 import java.io.BufferedReader;
 import java.io.ByteArrayInputStream;
 import java.io.ByteArrayOutputStream;
 import java.io.File;
 import java.io.FileFilter;
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.io.InputStream;
 import java.io.InputStreamReader;
 import java.lang.reflect.Constructor;
 import java.net.SocketTimeoutException;
 import java.util.Calendar;
 import java.util.Date;
 import java.util.TimerTask;
 import org.apache.commons.net.ftp.FTPClient;
 import org.apache.commons.net.ftp.FTPReply;
 import org.apache.log4j.Logger;
 import org.jdom.Document;
 import org.jdom.Element;
import org.jdom.xpath.XPath;
 
/**
 * @ClassName: Balance
 * @Description: 
 * @author sinosoft
 * @date 2017-2-27 上午10:49:19
 */
 public abstract class Balance extends TimerTask
   implements XmlTag
 {
	 //生成一个本类的日志对象
     protected final Logger cLogger = Logger.getLogger(getClass());
   //当前银行交易配置文件
   protected final XmlConf cThisConf;
   //交易码
   protected final int cFuncFlag;
   //交易日期
   protected Date cTranDate;
   //返回信息
   protected String cResultMsg;
   	  //中间平台配置文件根节点
      protected Element cMidplatRoot = null;
      //当前银行交易配置文件根节点
      protected Element cThisConfRoot = null;
      //当前交易配置节点
      protected Element cThisBusiConf = null;
 
    /**
     * <p>Title: </p>
     * <p>Description: </p>
     * @param pThisConf 当前银行交易配置文件
     * @param pFuncFlag 交易码(字符串)
     */
   public Balance(XmlConf pThisConf, String pFuncFlag) {
        this(pThisConf, Integer.parseInt(pFuncFlag));
   }
 
   /**
    * <p>Title: </p>
    * <p>Description: </p>
    * @param pThisConf 当前银行交易配置文件
    * @param pFuncFlag 交易码(数值)
    */
   public Balance(XmlConf pThisConf, int pFuncFlag) {
        this.cThisConf = pThisConf;
        this.cFuncFlag = pFuncFlag;
   }
 
   /**
    * 运行
    */
   public void run()
   {
	   //设置当前线程名[下一个交易日志号]
        Thread.currentThread().setName(
          String.valueOf(NoFactory.nextTranLogNo()));
        //开始Balance执行...
        this.cLogger.info("Into Balance.run()...");
        //返回信息置为空
        this.cResultMsg = null;
     try
     {
    	 //获取中间平台配置文件根节点
         this.cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
         //获取当前银行交易配置文件根节点
         this.cThisConfRoot = this.cThisConf.getConf().getRootElement();
         //获取当前交易配置节点[在当前银行交易配置文件根节点中,根据交易码为当前交易码选择单个节点]
         this.cThisBusiConf = ((Element)XPath.selectSingleNode(
           this.cThisConfRoot, "business[funcFlag='" + this.cFuncFlag + "']"));
         //获取下一个日期[当前交易配置节点获取nextDate子节点文本]
         String nextDate = this.cThisBusiConf.getChildText("nextDate");
         //交易日期为空
         if (this.cTranDate == null)
           //下一个日期非空且为Y
           if ((nextDate != null) && ("Y".equals(nextDate))) {
        	 //交易日期为当前日期对象
             this.cTranDate = new Date();
             //交易日期为昨天
             this.cTranDate = new Date(this.cTranDate.getTime() - 86400000L);
      } else {
    	  	 //交易日期为当前日期对象
             this.cTranDate = new Date();
      }
         //新建根节点
         Element tTranData = new Element("TranData");
         //新建标准输入报文
         Document tInStdXml = new Document(tTranData);
         //获取报文头节点
         Element tHeadEle = getHead();
         //根节点加入报文头节点
         tTranData.addContent(tHeadEle);
         //服务类名称
         String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";
         //服务类值[获取中间平台配置文件根节点下service子节点文本:com.sinosoft.midplat.service.SimpService]
         String tServiceValue = this.cMidplatRoot.getChildText("service");
         //服务类值非空、空字符串
         if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
        	 //服务类值赋值给服务类名称
           tServiceClassName = tServiceValue;
         }
         //服务类值[获取当前银行交易配置文件根节点下service子节点文本:空]
         tServiceValue = this.cThisConfRoot.getChildText("service");
         //服务类值非空、空字符串
         if ((tServiceValue != null) && (!"".equals(tServiceValue))) {//为空，不执行此代码块
           //服务类值赋值给服务类名称
           tServiceClassName = tServiceValue;
         }
         //服务类值[获取当前交易配置节点下service子节点文本:有返回该值,无返回空]
         tServiceValue = this.cThisBusiConf.getChildText("service");
         //服务类值非空、空字符串
         if ((tServiceValue != null) && (!"".equals(tServiceValue))) {
        	//服务类值赋值给服务类名称
           tServiceClassName = tServiceValue;
         }
         //业务处理模块+服务类名称
         this.cLogger.info("业务处理模块" + tServiceClassName);
         //返回一个构造器对象，反映服务类名称类对象所表示的类的指定公共构造方法
         Constructor tServiceConstructor = Class.forName(
           tServiceClassName).getConstructor(new Class[] { Element.class });
         //使用此构造器对象表示的构造方法来创建该构造方法的声明类的新实例，并用当前交易配置节点初始化该实例
         Service tService = (Service)tServiceConstructor.newInstance(new Object[] { this.cThisBusiConf });
         //业务处理类处理标准输入报文返回标准输出报文
         Document tOutStdXml = tService.service(tInStdXml);
         //获取返回信息[标准输出报文根结点下报文头节点下交易结果描述子节点文本]
         this.cResultMsg = tOutStdXml.getRootElement().getChild(
           "Head").getChildText("Desc");
         //结束[标准输出报文]
         ending(tOutStdXml);
         //交易日期为每月的第一天
         if ("01".equals(DateUtil.getDateStr(this.cTranDate, "dd")))
           //备份文件到[当前交易配置节点下localDir子节点文本]目录
           bakFiles(this.cThisBusiConf.getChildTextTrim("localDir"));
  }
  catch (Throwable ex) {
	  	 //错误:交易出错
         this.cLogger.error("交易出错", ex);
         //返回信息赋值为异常简短描述
         this.cResultMsg = ex.toString();
  }
       //交易日期置为空
       this.cTranDate = null;
       //从Balance执行方法出来!
       this.cLogger.info("Out Balance.run()!");
   }
 
   /**
    * @Title: getHead
    * @Description: 获取报文头
    * @return 报文头节点
    * @throws MidplatException
    * @return Element
    * @throws
    */
   protected Element getHead()throws MidplatException {
	   //进入Balance获取报文头方法...
       this.cLogger.info("Into Balance.getHead()...");
 
       Element mTranDate = new Element("TranDate");
       mTranDate.setText(DateUtil.getDateStr(this.cTranDate, "yyyyMMdd"));
 
       Element mTranTime = new Element("TranTime");
       mTranTime.setText(DateUtil.getDateStr(this.cTranDate, "HHmmss"));
 
       Element mTranCom = (Element)this.cThisConfRoot.getChild("TranCom").clone();
 
       Element mNodeNo = (Element)this.cThisBusiConf.getChild("NodeNo").clone();
 
       Element mTellerNo = new Element("TellerNo");
       mTellerNo.setText("sys");
 
       Element mTranNo = new Element("TranNo");
       try {
		  mTranNo.setText(getFileName());
	   } catch (Exception e) {
		throw new  MidplatException(e.getMessage()); 
	  }
 
       Element mFuncFlag = new Element("FuncFlag");
       mFuncFlag.setText(this.cThisBusiConf.getChildText("funcFlag"));
 
       Element mHead = new Element("Head");
       mHead.addContent(mTranDate);
       mHead.addContent(mTranTime);
       mHead.addContent(mTranCom);
       mHead.addContent(mNodeNo);
       mHead.addContent(mTellerNo);
       mHead.addContent(mTranNo);
       mHead.addContent(mFuncFlag);
 
       this.cLogger.info("Out Balance.getHead()!");
       return mHead;
   }
 
   	/**
   	 * @Title: parse
   	 * @Description: 解析
   	 * @param pBatIs
   	 * @return
   	 * @throws Exception
   	 * @return Element
   	 * @throws
   	 */
     protected Element parse(InputStream pBatIs) throws Exception {
       this.cLogger.info("Into Balance.parse()...");

       String mCharset = this.cThisBusiConf.getChildText("charset");
       if ((mCharset == null) || ("".equals(mCharset))) {
         mCharset = "GBK";
       }

       BufferedReader mBufReader = new BufferedReader(
         new InputStreamReader(pBatIs, mCharset));

       Element mBodyEle = new Element("Body");
       String tLineMsg;
       while ((tLineMsg = mBufReader.readLine()) != null) {
         this.cLogger.info(tLineMsg);

         tLineMsg = tLineMsg.trim();
         if ("".equals(tLineMsg)) {
           this.cLogger.warn("空行，直接跳过，继续下一条！");
         }
         else
         {
           String[] tSubMsgs = tLineMsg.split("\\|", -1);

           Element tTranDate = new Element("TranDate");
           tTranDate.setText(tSubMsgs[1]);

           Element tNodeNo = new Element("NodeNo");
           tNodeNo.setText(tSubMsgs[3]);

           Element tTranNo = new Element("TranNo");
           tTranNo.setText(tSubMsgs[5]);

           Element tContNo = new Element("ContNo");
           tContNo.setText(tSubMsgs[6]);

           Element tPrem = new Element("Prem");
           tPrem.setText(tSubMsgs[7]);

           Element tDetail = new Element("Detail");
           tDetail.addContent(tTranDate);
           tDetail.addContent(tNodeNo);
           tDetail.addContent(tTranNo);
           tDetail.addContent(tContNo);
           tDetail.addContent(tPrem);

           mBodyEle.addContent(tDetail);
         }
       }
       mBufReader.close();

       this.cLogger.info("Out Balance.parse()!");
       return mBodyEle;
     }

     /**
      * @Title: bakFiles
      * @Description:  备份文件
      * @param pFileDir 文件目录
      * @return void
      * @throws
      */
     private void bakFiles(String pFileDir) {
    	//进入Balance备份文件方法...
       this.cLogger.info("Into Balance.bakFiles()...");
       //文件目录为空、空字符
       if ((pFileDir == null) || ("".equals(pFileDir))) {
    	 //警告[本地文件目录为空，不进行备份操作！]
         this.cLogger.warn("本地文件目录为空，不进行备份操作！");
         //退出该程序的运行
         return;
       }
       //新建目录文件对象
       File mDirFile = new File(pFileDir);
       //目录文件对象不存在、不是目录
       if ((!mDirFile.exists()) || (!mDirFile.isDirectory())) {
    	 //本地文件目录不存在，不进行备份操作！+目录文件对象
         this.cLogger.warn("本地文件目录不存在，不进行备份操作！" + mDirFile);
         //退出该程序的运行
         return;
       }
       //旧文件[返回抽象路径名数组，这些路径名表示此抽象路径名表示的目录中满足指定过滤器的文件和目录]
       File[] mOldFiles = mDirFile.listFiles(
         new FileFilter() {
         public boolean accept(File pFile) {
           if (!pFile.isFile()) {
             return false;
           }

           Calendar tCurCalendar = Calendar.getInstance();
           tCurCalendar.setTime(Balance.this.cTranDate);
           tCurCalendar.set(11, 8);

           Calendar tFileCalendar = Calendar.getInstance();
           tFileCalendar.setTimeInMillis(pFile.lastModified());

           return tFileCalendar.before(tCurCalendar);
         }
       });
       Calendar mCalendar = Calendar.getInstance();
       mCalendar.add(2, -1);
       File mNewDir = 
         new File(mDirFile, DateUtil.getDateStr(mCalendar, "yyyy/yyyyMM"));
       for (File tFile : mOldFiles) {
         try {
				String fileName_date=tFile.getName().substring(tFile.getName().length()-8);
				Date date=new Date();
				if(!fileName_date.equals(String.valueOf(DateUtil.get8Date(date)))){
					cLogger.info(tFile.getAbsoluteFile() + " start move...");
					IOTrans.fileMove(tFile, mNewDir);
					cLogger.info(tFile.getAbsoluteFile() + " end move!");
				}
         } catch (IOException ex) {
           this.cLogger.error(tFile.getAbsoluteFile() + "备份失败！", ex);
         }
       }

       this.cLogger.info("Out Balance.bakFiles()!");
     }

     public final void setDate(Date pDate) {
       this.cTranDate = pDate;
     }

     public final void setDate(String p8DateStr) {
       this.cTranDate = DateUtil.parseDate(p8DateStr, "yyyyMMdd");
     }

     protected abstract String getFileName()throws Exception;
     
     /**
      * @Title: ending 结束
      * @Description: 
      * @param pOutStdXml 标准输出报文
      * @throws Exception 异常
      * @return void
      * @throws
      */
     protected void ending(Document pOutStdXml)
       throws Exception
     {
    	//进入Balance结束方法...
       this.cLogger.info("Into Balance.ending()...");
       //什么都不做，只是出来 
       this.cLogger.info("do nothing, just out!");
       //从Balance结束方法出来!
       this.cLogger.info("Out Balance.ending()!");
     }

     public String getResultMsg() {
       return this.cResultMsg;
     }
   }
