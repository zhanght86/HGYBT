//参照华夏银行的  犹豫期内退保对账 来修改
//xiaowq 20120827
package com.sinosoft.midplat.newabc.bat;

import org.apache.log4j.Logger;

import com.sinosoft.midplat.newabc.NewAbcConf;


/**
 * 密钥重置交易
 * @author chenjin
 *
 */
public class MYZH_Timing extends MYZH_TimingImpl{
	private static Logger cLogger = Logger.getLogger(MYZH_Timing.class);
	
	public MYZH_Timing() {
		super(NewAbcConf.newInstance(), "1001");	//pgwt_blnc 对应bc.xml中配置犹豫期内退保对账的funcflag
	}//SpdbConf.newInstance() 对应bc.xml
	public static void main(String[] args){
		
		cLogger.info("开始密钥重置数据传递 ...");	//没起作用，未打印
		
		MYZH_Timing pt = new MYZH_Timing();	//没起作用
		pt.run();	//没起作用
//			pt.makeLocalBalanceFile("D:\\file\\", "abc.txt", "1234567890");
		
		/*
		//不起作用
		String fileName = pt.getFileName();
		String localPath = PgwtTiming.class.getResource("/").getFile();
		File file=new File(localPath+fileName);
		mLogger.info("文件名："+file.getName());
		mLogger.info("文件路径："+file.getAbsolutePath());
		if(file.exists())			
		{	
			mLogger.info("file.exists()=="+file.exists());
			boolean delFlag = file.delete();
			mLogger.info("删除本地备份的犹撤对账文件成功了吗？"+delFlag);
		}*/
		
		cLogger.info("结束密钥重置 ...");	//没起作用，未打印
	}
	
}
