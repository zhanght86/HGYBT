package com.sinosoft.midplat.newccb.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import cn.ccb.secapi.SecAPI;

import com.f1j.data.source.InputStream;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.MidplatUtil;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.newccb.NewCcbConf;
import com.sinosoft.midplat.service.ServiceImpl;

/**
 * @ClassName: ContBatResponse 
 * @Description: 批量代收代付送盘业务处理类
 * @author yuantongxin
 * @date 2017-1-17 下午7:19:17
 */
public class ContBatResponse extends ServiceImpl {

	/**
	 * <p>Title: ContBatResponse</p>
	 * <p>Description: 批量代收代付送盘业务处理类构造函数</p>
	 * @param pThisBusiConf 当前交易配置文件
	 */
	public ContBatResponse(Element pThisBusiConf) {
		super(pThisBusiConf);
	}

	/**
	 * 标准输入报文处理为标准输出报文
	 * @param pInXmlDoc 标准输入报文
	 * @return 标准输出报文
	 */
	public Document service(Document pInXmlDoc) throws Exception {
		//Into ContBatRequResult()...
		cLogger.info("Into ContBatRequResult()...");
		long mStartMillis = System.currentTimeMillis();
		// 标准输入报文缩进3空格，忽略声明中的编码
		cLogger.info(JdomUtil.toStringFmt(pInXmlDoc));
		//银行回盘文件解密文件路径
		String localDir=this.cThisBusiConf.getChildText("LocalDir");
		//银行端安全节点号
		String localID=pInXmlDoc.getRootElement().getChild("Head").getChildText("LocalID");
		//保险公司端安全节点号
		String remoteID=pInXmlDoc.getRootElement().getChild("Head").getChildText("RemoteID");
		//银行端安全节点号:null
		cLogger.info("银行端安全节点号:"+remoteID);
		//保险公司端安全节点号:null
		cLogger.info("保险公司端安全节点号:"+localID);
		//送盘文件路径
		String filePath="/home/ap/fserver2/rcv/";//建行送盘文件路径
		//标准输入报文体
		Element tBodyEle = pInXmlDoc.getRootElement().getChild(Body);
		//代理保险批量包名称
		String tFileName = tBodyEle.getChildText("FileName");
		//银行回盘文件路径为:送盘文件路径+代理保险批量包名称
	    cLogger.info("银行回盘文件路径为:"+filePath+tFileName);
	    //代理保险批量包处理状态代码
		int tBatFlag = Integer.parseInt(tBodyEle.getChildText("BatFlag"));
		//当前批明细总笔数
		String tNum = tBodyEle.getChildText("Num");
		//当前批明细总金额
		String tSumAmt = tBodyEle.getChildText("SumAmt");
		try { 
			// 1:记录日志
			/**插入交易日志,设置[备用1:代理保险批量包名称,备用2:代理保险批量包处理状态代码,备用3:当前批明细总笔数,备用4:当前批明细总金额]*/
			cTranLogDB = insertTranLog(pInXmlDoc);
			cTranLogDB.setBak1(tFileName);
			cTranLogDB.setBak2(tBodyEle.getChildText("BatFlag"));
			cTranLogDB.setBak3(tNum);
			cTranLogDB.setBak4(tSumAmt);
			//描述字符串
			String descStr=null;
			//代理保险批量包处理状态代码非整批成功、整批失败
			if(!"00".equals(tBatFlag)||!"16".equals(tBatFlag)){
				//00:整批处理成功，16：整批处理失败
				/**匹配代理保险批量包处理状态代码对应状态*/
				switch (tBatFlag) {
				  case 1:descStr="未收到此包";break;
				  case 2:descStr="包的明细总笔数与实际明细笔数汇总不符";break;
				  case 3:descStr="包的明细总金额与实际明细金额汇总不符";break;
				  case 4:descStr="超出单包笔数限制";break;
				  case 5:descStr="包明细存在负金额";break;
				  case 6:descStr="保险公司返回批量包名称不符，或返回码有误";break;
				  case 7:descStr="包明细数为0";break;
				  case 8:descStr="批量包校验正确";break;
				  case 9:descStr="批量包未生成";break;
				  case 10:descStr="批量包已生成";break;
				  case 11:descStr="批量包可能重复提交,重复包可能为x";break;
				  case 12:descStr="下载批量包响应文件失败";break;
				  case 13:descStr="保险公司账户余额不足，整批失败";break;
				  case 14:descStr="批量明细序号重复";break;
				  case 15:descStr="批量包文件格式错误";break;
				  case 99:descStr="建行内部处理错误，需要人工核实情况";break;
			    }
				//抛出异常和描述字符串
				throw new MidplatException(descStr);
			}
			//解密文件入参localID:银行端安全节点号,remoteID:保险公司端安全节点号,解密前文件:送盘文件路径+代理保险批量包名称,解密后文件:银行回盘文件解密文件路径+代理保险批量包名称
			cLogger.info("解密文件入参localID:"+localID+",remoteID:"+remoteID +",解密前文件:"+filePath+tFileName
				         +",解密后文件:"+localDir+tFileName);
		    //解密银行送盘交易文件
			//解密送盘文件路径代理保险批量包到银行回盘文件解密文件路径
			SecAPI.fileUnEnvelop(localID, remoteID,filePath+tFileName,localDir+tFileName);
			//银行回盘文件解密文件路径+代理保险批量包生成核心压缩文件
			String fileName=generateCoreZipFile(localDir+tFileName);
			//根据交易结果和交易结果描述，生成简单标准输出报文
			cOutXmlDoc = MidplatUtil.getSimpOutXml(0, "交易成功");
			//交易日志设置交易结果
			cTranLogDB.setRCode(0);
			//交易日志设置结果描述[生成核心回盘文件成功:代理保险批量包压缩文件名]
			cTranLogDB.setRText("生成核心回盘文件成功:"+fileName);
		} catch (Exception ex) {
			//交易名称交易失败！
			cLogger.error(cThisBusiConf.getChildText(name) + "交易失败！", ex);
			//交易日志非空
			if (null != cTranLogDB) { 
				//设置交易结果
				cTranLogDB.setRCode(1); 
				//设置结果描述
				cTranLogDB.setRText(ex.getMessage());
			}
			//根据交易结果和结果描述，生成简单标准输出报文。
			cOutXmlDoc = MidplatUtil.getSimpOutXml(1,ex.getMessage());
		}
		//交易日志非空
		if (null != cTranLogDB) { // 插入日志失败时cTranLogDB=null
			//当前时间毫秒数
			long tCurMillis = System.currentTimeMillis();
			//交易日志服务耗时[当前时间毫秒数-开始时间毫秒数]
			cTranLogDB.setUsedTime((int) (tCurMillis - mStartMillis) / 1000);
			//交易日志最后修改日期[当前时间毫秒数前8位]
			cTranLogDB.setModifyDate(DateUtil.get8Date(tCurMillis));
			//交易日志最后修改时间[当前时间毫秒数后6位]
			cTranLogDB.setModifyTime(DateUtil.get6Time(tCurMillis));
			//更新交易日志
			if (!cTranLogDB.update()) {
				//更新日志信息失败！错误信息
				cLogger.error("更新日志信息失败！"+ cTranLogDB.mErrors.getFirstError());
			}
		}
		//标准输出报文[缩进3空格，忽略声明中的编码]
		cLogger.info(JdomUtil.toStringFmt(cOutXmlDoc));
		//返回标准输出报文
		return cOutXmlDoc;
	}
	
	/**
	 * @Title: generateCoreZipFile
	 * @Description: 生成核心压缩文件
	 * @param localFile 银行回盘文件解密文件路径+代理保险批量包
	 * @return 压缩文件名
	 * @throws Exception 异常
	 * @return String 压缩文件名
	 * @throws 压缩文件名
	 */
	public String generateCoreZipFile(String localFile) throws Exception{
		//文件输出流
		FileOutputStream fileOutputStream=null;
		//压缩输出流
		ZipOutputStream zipOutputStream=null;
		//文件输入流
		FileInputStream fileInputStream=null;
		//输入流读取
		InputStreamReader inputStreamReader=null;
		//缓冲区读取
		BufferedReader BufferedReader=null;
		//文件输入流1
		FileInputStream fileInputStream1=null;
		//核心取回盘文件路径
		//核心下载批量包路径
		String coreDownloadFilePath = NewCcbConf.newInstance().getConf().getRootElement().getChildText("coreDownloadFilePath");
		try {
			//核心上传批量包路径
			String fileUploadStr=NewCcbConf.newInstance().getConf().getRootElement().getChildText("coreUploadFilePath");
			//核心上传批量包路径+后缀/
			if(!fileUploadStr.endsWith("/"))fileUploadStr+="/";
			//核心上传批量包路径文件对象
			File fileUpload=new File(fileUploadStr);
			//银行回盘文件解密文件路径+代理保险批量包文件对象
			File file=new File(localFile);
			//代理保险批量包名称
			String fileName=file.getName();
			//代收代付标志[AL?3100192017011101_RESULT.xml]
			final String SFflag=fileName.substring(2,3).equals("0")?"S":"F";
			//提交日期[AL0310019????????01_RESULT.xml]
			final String dateStr=fileName.substring(9,17);
			//当日批次号[AL031001920170111??_RESULT.xml]
			String orderNo=fileName.substring(17,19);
			//送盘文件包的序号位数不足5位前面补0
			//当日批次号不足5位
			if(orderNo.length()<5){
				//补0个数
				int num=5-orderNo.length();
				//当日批次号前补足0
				for (int i = 0; i <num; i++) {
					//补0
					orderNo="0"+orderNo;
				}
			}
			//当日批次号常量
			final String tOrderNo=orderNo;
			//生成包的标志为:代收代付标志
			cLogger.info("生成包的标志为:"+SFflag);
			//代收付交易文件匹配正则表达式:^\\w{10,15}_代收代付标志02提交日期_当日批次号.txt$
			cLogger.info("代收付交易文件匹配正则表达式:"+"^\\w{10,15}_"+SFflag+"02"+dateStr+"_"+tOrderNo+".txt$");
	        //核心上传批量包路径文件对象返回此抽象路径名所表示目录中的文件和目录的抽象路径名数组，这些路径名满足特定过滤器
			File[] fileList=fileUpload.listFiles(new FileFilter() {
				//编译正则表达式
			   Pattern pattern=Pattern.compile("^\\w{10,15}_"+SFflag+"02"+dateStr+"_"+tOrderNo+".txt$");
			   /**
			    * @Title: accept
			    * @Description: 接受
			    * @param filePath 银行回盘文件解密文件路径+代理保险批量包文件对象
			    * @return 匹配结果
			    * @return boolean true:成功，false失败
			    * @throws
			    */
			   @Override
			   public boolean accept(File filePath) {
				   //代理保险批量包名称
			      String fileName=filePath.getName();
			      //模式匹配代理保险批量包名称
				  Matcher matcher=pattern.matcher(fileName);
				  //编译正则表达式并尝试匹配输入
				  return matcher.matches();
			   }
			});
			//核心上传批量包路径文件数组没有文件
	        if(fileList.length==0){
	        	throw new MidplatException("匹配取盘txt文件失败，请核对！");
	        }
	        //核心上传批量包路径文件数组文件多于1个文件
	        if(fileList.length>1){
	        	throw new MidplatException("匹配取盘txt文件个数大于1，请核对！");
	        }
	        //匹配取盘的文件名为:文件路径名
	        cLogger.info("匹配取盘的文件名为:"+fileList[0].getPath());
	        //文件路径名
	        String fileNamePath=fileList[0].getPath();
	        //下载文件名[核心下载批量包路径+文件路径名前缀.zip]
	        String downloadFileName=coreDownloadFilePath+fileNamePath.substring(0,fileNamePath.lastIndexOf("."))+".zip";
	        //下载文件输出流
	        fileOutputStream=new FileOutputStream(downloadFileName);
	        //文件路径名创建ZIP 条目
	        ZipEntry zipEntry=new ZipEntry(fileNamePath);
	        //压缩输出流
	        zipOutputStream=new ZipOutputStream(fileOutputStream);
	        //写入ZIP文件条目并将流定位到条目数据开始处
	        zipOutputStream.putNextEntry(zipEntry);
	        //文件路径名文件输入流
	        fileInputStream=new FileInputStream(fileNamePath);
	        //输入流读取文件路径名文件输入流
	        inputStreamReader=new InputStreamReader(fileInputStream,"GBK");
	        //文件路径名输入流读取加入缓冲器
	        BufferedReader=new BufferedReader(inputStreamReader);
	        //银行回盘文件解密文件路径+代理保险批量包文件对象文件输入流
	        fileInputStream1=new FileInputStream(file);
	        //回盘代理保险批量包文件构建一个文档对象获取根节点
	        Element docRoot=JdomUtil.build(fileInputStream1,"UTF-8").getRootElement();//将银行送盘文件解析成document并获取根节点
	        //文本行
	        String dataStr=null;
	        //读取一个文本行非空
	        while((dataStr=BufferedReader.readLine())!=null||!"".equals(dataStr)){
	        	//根据,来拆分文本行
	        	String[] strs=dataStr.split(",");
	        	//文本行数组长度为6
	        	if(strs.length==6){
	        		//当前批明细总笔数非核心上传批量包路径文件明细总笔数
	        		if(!docRoot.getChildText("Cur_Btch_Dtl_TDnum").equals(strs[3])){
	        			//送盘文件和取盘文件总数据不一致！
	        		   throw new MidplatException("送盘文件和取盘文件总数据不一致！");
	        		}
	        		//文本行字节数组写入压缩输出流
	        		zipOutputStream.write(dataStr.getBytes("GBK"));
	        		//跳过
	        		continue;
	        	}
	        	//获取银行账号根据银行账号获取银行处理报文相对应的节点
	        	//客户账号
	        	String account=strs[4];
	        	//创建一个新的XPath对象，编译指定的XPath表达式
	        	XPath tXPath = XPath.newInstance("Detail_List/Detail[Cst_AccNo='"+account+"']");
	        	//根据包装的XPath表达式返回选中节点列表的第一项
	        	Element Detail=(Element) tXPath.selectSingleNode(docRoot);
	        	//明细为空
	        	if(Detail==null){
	        		//送盘数据与取盘数据不一致,请核对！
	        		throw new MidplatException("送盘数据与取盘数据不一致,请核对！");
	        	}
	        	//截取文本行字符串
	        	dataStr=dataStr.substring(0,dataStr.lastIndexOf(","));
	        	//主机响应代码
	        	String responseCode=Detail.getChildText("Hst_Rsp_Cd");//主机响应码
	        	//主机响应信息
	        	String responseStr=Detail.getChildText("Hst_Rsp_Inf");//主机响应描述
	        	//银行账号为:客户账号交易数据的响应码为:主机响应代码响应描述为:主机响应信息
	        	cLogger.info("银行账号为:"+account+"交易数据的响应码为:"+responseCode+"响应描述为:"+responseStr);
	        	//将字节数组写入压缩输出流
	        	zipOutputStream.write((dataStr+responseCodeTransfer(responseCode)+","+responseStr).getBytes("GBK"));
	        }
	        //生成核心回盘文件路径为：下载文件名
	        cLogger.info("生成核心回盘文件路径为："+downloadFileName);
	        //下载文件名
	        return downloadFileName;//返回生成文件路径
		} catch (Exception e) {
			//抛出异常,显示信息
			throw new MidplatException(e.getMessage());
		}finally{
			//压缩输出流非空
			if(zipOutputStream!=null){
				//关闭压缩输出流
				zipOutputStream.close();
			}
			//文件输出流非空
			if(fileOutputStream!=null){
				//关闭文件输出流
				fileOutputStream.close();
			}
			//缓冲区读取非空
			if(BufferedReader!=null){
				//关闭缓冲区读取
				BufferedReader.close();
			}
			//输入流读取非空
			if(inputStreamReader!=null){
				//关闭输入流读取
				inputStreamReader.close();
			}
			//文件输入流非空
			if(fileInputStream!=null){
				//关闭文件输入流
				fileInputStream.close();
			}
			//文件输入流1非空
			if(fileInputStream1!=null){
				//关闭文件输入流1
				fileInputStream1.close();
			}
		}
		
	}
	//银行响应码转换成核心响应码
	/**
	 * @Title: responseCodeTransfer
	 * @Description: 响应代码转换
	 * @param code 主机响应代码
	 * @return 反馈码
	 * @return String 反馈码
	 * @throws 异常
	 */
	public String responseCodeTransfer(String code){
		if("E8201".equals(code)){return "3039";}//帐户已销户
		else if("E3150".equals(code)){return "3009";}//帐户不存在
		else if("E5502".equals(code)){return "3059";}//密码已挂失
		else if("E4501".equals(code)){return "2005";}//帐户控制状态不允许存入
		else if("E3001".equals(code)){return "3032";}//币别或钞汇鉴别不存在
		else if("E4500".equals(code)){return "2006";}//帐户控制状态不允许支取
		else if("E5000".equals(code)){return "3016";}//存折已挂失
		else if("E3551".equals(code)){return "3017";}//帐户已冻结
		else if("E4500".equals(code)){return "3017";}//单向冻结状态不允许支取
		else if("E5002".equals(code)){return "3006";}//卡已挂失
		else if("E7102".equals(code)){return "3008";}//余额不足
		else if("E3266".equals(code)){return "3065";}//客户名称检查失败
		else if("E1408".equals(code)){return "3057";}//帐号类型不符
		else if("E8301".equals(code)){return "3999";}//专项账户存入摘要代码错误
		else if("E8301".equals(code)){return "3039";}//该卡已销户
		else if("E1085".equals(code)){return "3999";}//挂失标志不正确
		else if("E3540".equals(code)){return "3999";}//注销状态不正确
		else if("E3556".equals(code)){return "3999";}//卡状态不正常
		else if("SHK02".equals(code)){return "3999";}//使用非法账号导致
		else if("SDB01".equals(code)){return "3999";}//使用非法账号导致
		else if("E9999".equals(code)){return "3999";}//建行内部处理错误
		else{return "";}
	}
	public static void main(String[] args) throws Exception {

//     FileInputStream in=new FileInputStream(new File("C:\\Users\\anico\\Desktop\\建行测试报文\\CCB代收付文件20170111\\CCB代收付文件20170111\\AL03100192017011101_RESULT.XML"));
//     Element doc=JdomUtil.build(in,"UTF-8").getRootElement();
//     //System.out.println(JdomUtil.toStringFmt(doc));
//     XPath tXPath = XPath.newInstance("Detail_List/Detail[Cst_AccNo='1216129980110291838']");
//     Element e=(Element) tXPath.selectSingleNode(doc);
//	 System.out.println(JdomUtil.toStringFmt(e));
		String str="232493u,934924,24023,234,,";
		str=str.substring(0,str.lastIndexOf(","))+"2222"+","+"8888";
		System.out.println(str);
	}
}
