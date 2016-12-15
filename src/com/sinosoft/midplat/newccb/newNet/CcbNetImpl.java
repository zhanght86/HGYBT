package com.sinosoft.midplat.newccb.newNet;

import com.sinosoft.lis.db.NodeMapDB;
import com.sinosoft.lis.schema.NodeMapSchema;
import com.sinosoft.lis.vschema.NodeMapSet;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.SaveMessage;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.net.SocketNetImpl;
import com.sinosoft.midplat.newccb.format.ErrorOutXsl;
import com.sinosoft.midplat.newccb.util.NewCcbFormatUtil;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.transform.XSLTransformer;
import org.jdom.xpath.XPath;

public class CcbNetImpl extends SocketNetImpl
{
  private String cFuncFlag = null;
  private Document mInNoStd = null;

  private String startTime = null;

  public CcbNetImpl(Socket pSocket, Element pThisConfRoot) throws MidplatException {
    super(pSocket, pThisConfRoot);
  }
  
  /**
   * 接收
   */
  public Document receive() throws Exception {
	  //Into CcbNetImpl.receive()...
    this.cLogger.info("Into CcbNetImpl.receive()...");
    //20161213101951123
    this.startTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    //初始化报文头字节数组
    byte[] mHeadBytes = new byte[6];
    //输入输出转换从套接字输入字节流中读取数据，填满传入的报文头字节数组，不关闭流
    IOTrans.readFull(mHeadBytes, this.cSocket.getInputStream());
    //5877
    int mBodyLength = Integer.parseInt(new String(mHeadBytes, 0, 6).trim());
    //请求报文长度：5877
    this.cLogger.debug("请求报文长度：" + mBodyLength);
    //初始化报文长度个元素的报文体字节数组
    byte[] mBodyBytes = new byte[mBodyLength];
    //输入输出转换从输入字节流中读取数据，填满传入的字节数组，不关闭流
    IOTrans.readFull(mBodyBytes, this.cSocket.getInputStream());
    //此套接字的输入流置于“流的末尾”
    this.cSocket.shutdownInput();
    //构造一个新的字符串(使用UTF-8字符集解码报文头字节数组)
    String docIn=new String(mBodyBytes,"UTF-8");
    //-------------输入非标准报文字符串
    cLogger.info("-------------"+docIn);
    //Java文档对象模型工具采用GBK编码构建一个文档对象，忽略标签之间的空字符(空格、换行、制表符等)。
    this.mInNoStd = JdomUtil.build(docIn.getBytes());
    //输入非标准报文根元素
    Element mRootEle = this.mInNoStd.getRootElement();
    //报文头元素
    Element cHeader = mRootEle.getChild("TX_HEADER");
    //服务名[P53819113]
    String mSYS_TX_CODE = cHeader.getChildText("SYS_TX_CODE");
    //新建行交易码为==P53819113
    this.cLogger.info("新建行交易码为==" + mSYS_TX_CODE);
    //交易机构代码子节点内容[13]
    String mTranCom = this.cThisConfRoot.getChildText("TranCom");
    //mTranCom==============13
    this.cLogger.info("mTranCom==============" + mTranCom);
    //路径表达式:business/funcFlag[@outcode='P53819113']
    //child::business/child::funcFlag[(attribute::outcode = "P53819113")]
    XPath mXPath2 = XPath.newInstance(
      "business/funcFlag[@outcode='" + mSYS_TX_CODE + "']");
    //1012
    this.cFuncFlag = mXPath2.valueOf(this.cThisConfRoot);
    //7946_3_1012_in.xml
    StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())
      .append('_').append(NoFactory.nextAppNo()).append('_').append(this.cFuncFlag).append("_in.xml");
    SaveMessage.save(this.mInNoStd, this.cTranComEle.getText(), mSaveName.toString());
    //保存报文完毕！7946_3_1012_in.xml
    this.cLogger.info("保存报文完毕！" + mSaveName);
    //Java文档对象模型工具将输入非标准报文打印到控制台(GBK编码，缩进3空格)
    JdomUtil.print(this.mInNoStd);
    	
	// 交易机构代码元素
	Element mTranComEle = new Element(TranCom);
	//设置交易机构代码
	mTranComEle.setText(mTranCom);
	//进入报文元素
	Element mInNoDoc = new Element("InNoDoc");
	//设置进入报文
	mInNoDoc.setText(mSaveName.toString());
	//银行端IP元素
	Element mClientIpEle = new Element(ClientIp);
	//设置银行端IP
	mClientIpEle.setText(cClientIp);
	//交易码元素
	Element mFuncFlagEle = new Element(FuncFlag);
	//设置交易码
	mFuncFlagEle.setText(cFuncFlag);
	//代理机构编码元素
	Element mAgentCom = new Element(AgentCom);
	//代理人编码元素
	Element mAgentCode = new Element(AgentCode);
	//报文头元素
	Element mHeadEle = new Element(Head);
	//报文头元素加入银行端IP元素
	mHeadEle.addContent(mClientIpEle);
	//报文头元素加入交易机构代码元素
	mHeadEle.addContent(mTranComEle);
	//报文头元素加入交易码元素
	mHeadEle.addContent(mFuncFlagEle);
	//报文头元素加入代理机构编码元素
	mHeadEle.addContent(mAgentCom);
	//报文头元素加入代理人编码元素
	mHeadEle.addContent(mAgentCode);
	//报文头元素加入进入报文元素
	mHeadEle.addContent(mInNoDoc);
	
	//根元素加入报文头元素
	mRootEle.addContent(mHeadEle);
	//增加标准报文头节点后报文：
	cLogger.info("增加标准报文头节点后报文：");
	//Java文档对象模型工具将输入非标准报文打印到控制台(GBK编码，缩进3空格)
	JdomUtil.print(mInNoStd);

	//Out CcbNetImpl.receive()!
    this.cLogger.info("Out CcbNetImpl.receive()!");
    //返回输入非标准报文
    return this.mInNoStd;
  }

  public void send(Document pOutNoStd) throws Exception {
	//Into CcbNetImpl.send()...
    this.cLogger.info("Into CcbNetImpl.send()...");
    
    //Java文档对象模型工具将文档(请求报文协议报文头+报文体+通用域+实体域+公共域+应用域)打印到控制台， GBK编码，缩进3空格。
    //将输出非标准报文打印到控制台
    JdomUtil.print(pOutNoStd);
    if (pOutNoStd.getRootElement().getChild("TX_HEADER") == null) {
      System.out.println("==================send====");
      Element tRootEle = this.mInNoStd.getRootElement();

      Element mNodeNo = new Element("NodeNo");
      String tNodeNo = tRootEle.getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("CCBIns_ID");
      mNodeNo.setText(tNodeNo);

      Element mTranNo = new Element("TranNo");
      String tTranNo = tRootEle.getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("SvPt_Jrnl_No");
      mTranNo.setText(tTranNo);

      Element mTellerNo = new Element("TellerNo");
      String tTellerNo = tRootEle.getChild("TX_BODY").getChild("ENTITY").getChild("COM_ENTITY").getChildText("CCB_EmpID");
      mTellerNo.setText(tTellerNo);

      Element mTranDate = new Element("TranDate");
      String tTranDate = tRootEle.getChild("TX_HEADER").getChildText("SYS_REQ_TIME").substring(0, 8);
      mTranDate.setText(tTranDate);

      Element mHeadEle = pOutNoStd.getRootElement().getChild("Head");
      mHeadEle.addContent(mNodeNo);
      mHeadEle.addContent(mTranNo);
      mHeadEle.addContent(mTellerNo);
      mHeadEle.addContent(mTranDate);

      JdomUtil.print(pOutNoStd);
      String endTime = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
      Document mNoStdXml = 
        ErrorOutXsl.newInstance().getCache().transform(pOutNoStd);
      NewCcbFormatUtil.setDom(this.mInNoStd, mNoStdXml, this.startTime, endTime);
      JdomUtil.print(mNoStdXml);

      Element mSYS_TX_STATUS = mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_TX_STATUS");

      Element mSYS_RESP_CODE = mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_CODE");

      Element mSYS_RESP_DESC_LEN = mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC_LEN");

      Element mSYS_RESP_DESC = mNoStdXml.getRootElement().getChild("TX_HEADER").getChild("SYS_RESP_DESC");
      Element mRetData = pOutNoStd.getRootElement().getChild("Head");
      if (mRetData.getChildText("Flag").equals("0")) {
        mSYS_TX_STATUS.setText("00");
        mSYS_RESP_CODE.setText("000000000000");
        mSYS_RESP_DESC_LEN.setText(Integer.toString(mRetData.getChildText("Desc").length()));
        mSYS_RESP_DESC.setText(mRetData.getChildText("Desc"));
      } else if (mRetData.getChildText("Flag").equals("1")) {
        mSYS_TX_STATUS.setText("01");
        mSYS_RESP_CODE.setText("ZZZ073000000");
        mSYS_RESP_DESC_LEN.setText(Integer.toString(mRetData.getChildText("Desc").length()));
        mSYS_RESP_DESC.setText(mRetData.getChildText("Desc"));
      } else {
        mSYS_TX_STATUS.setText("02");
        mSYS_RESP_CODE.setText("ZZZ073000000");
        mSYS_RESP_DESC_LEN.setText(Integer.toString("不确定".length()));
        mSYS_RESP_DESC.setText("不确定");
      }
      pOutNoStd = mNoStdXml;
    }

    //[Element: <TX_HEADER/>]
    //输出非标准报文头元素
    Element mHeadEle = pOutNoStd.getRootElement().getChild("TX_HEADER");
    //获取Java文档对象模型工具将报文头元素转换为UTF-8编码的字节数组(保持原格式)的元素个数[800]
    int mHeadEleLength = JdomUtil.toBytes(mHeadEle, "UTF-8").length;//827
    //827+(3-1)=829
    //800+(3-1)=802
    mHeadEleLength += String.valueOf(mHeadEleLength).length() - 1;//829
    //<SYS_HDR_LEN>829</SYS_HDR_LEN>
    //设置输出非标准报文头长度[802]
    mHeadEle.getChild("SYS_HDR_LEN").setText(String.valueOf(mHeadEleLength));
    // Java文档对象模型工具将文档转换为UTF-8编码的字节数组(保持原格式)[Element: <TX/>]
    //输出非标准报文字节数组
    byte[] mXmlBytes = JdomUtil.toBytes(pOutNoStd, "UTF-8");
    //XML文档字节数组元素个数[1753]
    //输出非标准报文字节数组长度[1902]
    int mXmlDocLength = mXmlBytes.length;
    //1753+(4-1)=1756
    //1902+(4-1)=1905
    mXmlDocLength += String.valueOf(mXmlDocLength).length() - 1;
    //<SYS_TTL_LEN>1756</SYS_TTL_LEN>
    //设置输出非标准报文头报文总长度
    mHeadEle.getChild("SYS_TTL_LEN").setText(String.valueOf(mXmlDocLength));
    //
    //1406_6_111_out.xml
    //5357_9_1012_out.xml
    StringBuffer mSaveName = new StringBuffer(Thread.currentThread().getName())//1406[5357]
      .append('_').append(NoFactory.nextAppNo())//_6[_9]
      .append('_').append(this.cFuncFlag)//_111[_1012]
      .append("_out.xml");//_out.xml
    //[Element: <TX/>]，[Element: <TranCom/>]，1406_6_111_out.xml
    SaveMessage.save(pOutNoStd, this.cTranComEle.getText(), mSaveName.toString());
    //保存报文完毕！1406_6_111_out.xml
    this.cLogger.info("保存报文完毕！" + mSaveName);
    //1406_6_111_out.xml
    //5357_9_1012_out.xml
    this.cOutNoStdDoc = mSaveName.toString();
    //将文档转换为UTF-8编码的字节数组，保持原格式。
    //输出非标准报文字节数组
    byte[] mBodyBytes0 = JdomUtil.toBytes(pOutNoStd,"UTF-8");
    //返回给银行的报文：建行绿灯测试响应报文
    //将输出非标准报文字节数组字符串打印到控制台
    this.cLogger.info("返回给银行的报文：" + new String(mBodyBytes0));	
    //Socket[addr=/127.0.0.1,port=50412,localport=39871]得到输出流，从报文体字节数组写入报文体字节数组元素个字节到该文件输出流。
    //Socket[addr=/127.0.0.1,port=50200,localport=39871]写入输出非标准报文字节数组到输出流
    this.cSocket.getOutputStream().write(mBodyBytes0);
    //Socket[addr=/127.0.0.1,port=50412,localport=39871]关闭输出
    //Socket[addr=/127.0.0.1,port=50200,localport=39871]关闭输出流
    this.cSocket.shutdownOutput();
    //Out CcbNetImpl.send()!
    this.cLogger.info("Out CcbNetImpl.send()!");
  }
}