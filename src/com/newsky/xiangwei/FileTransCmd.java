package com.newsky.xiangwei;

public class FileTransCmd
{
	public static char  CMD_SEND_FILE    =	'0' ;
	public static char  CMD_RECV_FILE    =    '1' ;
	public static int CMDSTREAMLEN = 291;         /*命令行长度*/
	
	public static int FILEBUFSIZE  =  4*1024 ;     /* 文件分片传输大小 */
	public static String TRANSFER_OK	   =  "00" ;  /*文件传输完毕*/                      
	public static String ERR_FILESTAT     = "01"  ;  /* 远程文件状态错*/   
	public static String ERR_FILEOPEN     = "02"  ;  /* 文件打开失败  */   
	public static String ERR_FILESEEK     = "03"  ;  /* 文件定位失败  */   
	public static String ERR_SENDFLAG     = "04"  ;  /* 传送文件标志错*/   
	public static String ERR_ERRORMSG     = "05"  ;  /* 错误的消息    */   
	public static String ERR_WRITEFILE    = "06"  ;  /* 写文件错      */   
	public static String ERR_SENDACK      = "07"  ;  /* 写响应信息错  */   
	public static String ERR_FILECLOSE    = "08"  ;  /* 文件关闭错	*/   
	public static String ERR_RENAMEFILE   = "09"  ;  /* 文件重命名错	*/   
	public static String ERR_FILETRANS	   =  "10" ;  /* 文件网络传送错*/   
	
    private  String 	vs_outsys_code;            /* 外系统代码  */
	private  String   	vs_req_flag;		        /* 请求/应答标志*/
	private  String   	vs_send_recv_flag;         /* 收发标志 0:外系统向保险系统发送文件 1:外系统从保险系统接收文件 */
	private  String   	vs_local_file_dir;        /* 保险系统文件存放相对路径*/
	private  String   	vs_local_file_name;       /* 保险系统存放的文件名*/
	private  String   	vs_remote_file_dir;       /* 外系统文件存放相对路径,不包含$HOME */
	private  String   	vs_remote_file_name;      /* 外系统存放的文件名*/
	   
		 							             /* 外系统调用API时不用管理如下的变量*/
	private int	vl_file_size;			        /* 文件总大小 */
	private int	vl_file_start_positon;	        /* 本次传输文件起始位置 */
	private int	vl_trans_size ;			        /* 本次传输大小	*/
	private String	vs_retcode;		        /* 传输结果 00: ok 其他:失败*/
	
	
	public FileTransCmd()
	{
		vs_outsys_code = null;
		vs_req_flag = null;
		vs_send_recv_flag = null;
		vs_local_file_dir = null;
		vs_local_file_name = null;
		vs_remote_file_dir = null;
		vs_remote_file_name = null;
		vl_file_size = 0;
		vl_file_start_positon = 0;
		vl_trans_size = 0;
		vs_retcode = null;
		
	}


	/**
     * @return the vs_outsys_code
     */
    public String getVs_outsys_code()
    {
    	return vs_outsys_code;
    }


	/**
     * @param vs_outsys_code the vs_outsys_code to set
     */
    public void setVs_outsys_code(String vs_outsys_code)
    {
    	this.vs_outsys_code = vs_outsys_code;
    }


	/**
     * @return the vs_req_flag
     */
    public String getVs_req_flag()
    {
    	return vs_req_flag;
    }


	/**
     * @param vs_req_flag the vs_req_flag to set
     */
    public void setVs_req_flag(String vs_req_flag)
    {
    	this.vs_req_flag = vs_req_flag;
    }


	/**
     * @return the vs_send_recv_flag
     */
    public String getVs_send_recv_flag()
    {
    	return vs_send_recv_flag;
    }


	/**
     * @param vs_send_recv_flag the vs_send_recv_flag to set
     */
    public void setVs_send_recv_flag(String vs_send_recv_flag)
    {
    	this.vs_send_recv_flag = vs_send_recv_flag;
    }


	/**
     * @return the vs_local_file_dir
     */
    public String getVs_local_file_dir()
    {
    	return vs_local_file_dir;
    }


	/**
     * @param vs_local_file_dir the vs_local_file_dir to set
     */
    public void setVs_local_file_dir(String vs_local_file_dir)
    {
    	this.vs_local_file_dir = vs_local_file_dir;
    }


	/**
     * @return the vs_local_file_name
     */
    public String getVs_local_file_name()
    {
    	return vs_local_file_name;
    }


	/**
     * @param vs_local_file_name the vs_local_file_name to set
     */
    public void setVs_local_file_name(String vs_local_file_name)
    {
    	this.vs_local_file_name = vs_local_file_name;
    }


	


	/**
     * @return the vs_remote_file_dir
     */
    public String getVs_remote_file_dir()
    {
    	return vs_remote_file_dir;
    }


	/**
     * @param vs_remote_file_dir the vs_remote_file_dir to set
     */
    public void setVs_remote_file_dir(String vs_remote_file_dir)
    {
    	this.vs_remote_file_dir = vs_remote_file_dir;
    }


	/**
     * @return the vs_remote_file_name
     */
    public String getVs_remote_file_name()
    {
    	return vs_remote_file_name;
    }


	/**
     * @param vs_remote_file_name the vs_remote_file_name to set
     */
    public void setVs_remote_file_name(String vs_remote_file_name)
    {
    	this.vs_remote_file_name = vs_remote_file_name;
    }


	/**
     * @return the vl_file_size
     */
    public int getVl_file_size()
    {
    	return vl_file_size;
    }


	/**
     * @param vl_file_size the vl_file_size to set
     */
    public void setVl_file_size(int vl_file_size)
    {
    	this.vl_file_size = vl_file_size;
    }


	/**
     * @return the vl_file_start_positon
     */
    public int getVl_file_start_positon()
    {
    	return vl_file_start_positon;
    }


	/**
     * @param vl_file_start_positon the vl_file_start_positon to set
     */
    public void setVl_file_start_positon(int vl_file_start_positon)
    {
    	this.vl_file_start_positon = vl_file_start_positon;
    }


	/**
     * @return the vl_trans_size
     */
    public int getVl_trans_size()
    {
    	return vl_trans_size;
    }


	/**
     * @param vl_trans_size the vl_trans_size to set
     */
    public void setVl_trans_size(int vl_trans_size)
    {
    	this.vl_trans_size = vl_trans_size;
    }


	/**
     * @return the vs_retcode
     */
    public String getVs_retcode()
    {
    	return vs_retcode;
    }


	/**
     * @param vs_retcode the vs_retcode to set
     */
    public void setVs_retcode(String vs_retcode)
    {
    	this.vs_retcode = vs_retcode;
    }


    public void displayFileCmd()
    {
    	PrintLog.printLog("FileCmd:" + vs_outsys_code );
    	PrintLog.printLog("FileCmd:" + vs_req_flag);
    	PrintLog.printLog("FileCmd:" + vs_send_recv_flag);
    	PrintLog.printLog("FileCmd:" + vs_local_file_dir);
    	PrintLog.printLog("FileCmd:" + vs_local_file_name);
    	PrintLog.printLog("FileCmd:" + vs_remote_file_dir);
    	PrintLog.printLog("FileCmd:" + vs_remote_file_name);
    	PrintLog.printLog("FileCmd:" + vl_file_size);
    	PrintLog.printLog("FileCmd:" + vl_file_start_positon);
    	PrintLog.printLog("FileCmd:" + vl_trans_size);
    	PrintLog.printLog("FileCmd:" + vs_retcode);
    }
	
	

}
