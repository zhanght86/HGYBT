package com.sinosoft.midplat.bat;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.xpath.XPath;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.common.CodeDef;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.common.XmlConf;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.midplat.service.Service;
/*
 * 2014-07-16 dongxue
 * 通过SFTP取文件 
 */
public abstract class GetSFTPConnection extends TimerTask implements XmlTag{
protected final Logger cLogger = Logger.getLogger(getClass());
	
	//子系统配置文件缓存代理。列:icbc.xml
	private final XmlConf cThisConf;
	private final int cFuncFlag;	//交易代码
	
	/**
	 * 提供一个全局访问点，只在每次对账开始时初始化，
	 * 确保在该次对账处理的整个过程中日期一致性，
	 * 不受跨天(前面的处理在0点前，后面的在0点后)的影响。
	 */
	protected Date cTranDate;
	
	protected String cResultMsg;
	
	/**
	 * 提供一个全局访问点，只在每次对账开始时重新初始化，
	 * 确保在该次对账处理的整个过程中配置一致性，
	 * 不受配置文件自动加载的影响。也就是说，本次定时任务一旦启动，
	 * 其后配置文件的修改将会在下一次批跑时生效，不影响本次。
	 */
	protected Element cMidplatRoot = null;
	protected Element cThisConfRoot = null; 
	protected Element cThisBusiConf = null;
	
	public GetSFTPConnection(XmlConf pThisConf, String pFuncFlag) {
		this(pThisConf, Integer.parseInt(pFuncFlag));
	}
	
	public GetSFTPConnection(XmlConf pThisConf, int pFuncFlag) {
		cThisConf = pThisConf;
		cFuncFlag = pFuncFlag;
	}
	
	/**
	 * 为保证对账Timer不会因为某天的一次异常而终止，这里必须捕获run()中的所有异常。
	 * a)	获取对账日期。如果有指定，则为补对账；否则去当天系统日期，正常对账。
		b)	获取对账文件名。文件名由子类定义。
		c)	组织标准请求报文头，即BaseInfo。
		d)	获取对账文件，通过配置文件区分是sftp还是本地。
		e)	解析对账文件，生成标准对账明细。
		f)	报文头和明细构成标准请求报文，并保存在instd。
		g)	调用该交易配置的服务类进行对账处理。
		h)	保存对账处理结果到outstd。
		i)	调用protected void ending(Document pOutStdXml) throws Exception进行一些必要的后续处理。一般该方法什么也不做。
		j)	如果是当月1日，备份之前的对账文件到$ localDir/yyyy/yyyyMM下。
一般来说，不建议子类重写该方法。
	 */

	public void run() {
		Thread.currentThread().setName(
				String.valueOf(NoFactory.nextTranLogNo()));
		cLogger.info("Into GetSFTPConnection.run()...");
		
		//清空上一次结果信息
		cResultMsg = null;
		
		try { 
			  
			cMidplatRoot = MidplatConf.newInstance().getConf().getRootElement();
			cThisConfRoot = cThisConf.getConf().getRootElement();
			cThisBusiConf = (Element) XPath.selectSingleNode(
					cThisConfRoot, "business[funcFlag='"+cFuncFlag+"']");
			
			String nextDate = cThisBusiConf.getChildText("nextDate");
				if (null == cTranDate) {
					if(null != nextDate && "Y".equals(nextDate)){
						cTranDate = new Date();
						cTranDate = new Date(cTranDate.getTime()-1000*3600*24);
					}else
						cTranDate = new Date();
				}
			
			Element tTranData = new Element(TranData);
			Document tInStdXml = new Document(tTranData);
			System.out.println("====此处打印：发给核心的报文====");
			JdomUtil.print(tInStdXml);
			Element tHeadEle = getHead();
			tTranData.addContent(tHeadEle);
			
			try {
				//单个文件名
				String ttFileName = getFileName();
				cLogger.info("FileName = "+ttFileName);
				String ttLocalDir = cThisBusiConf.getChildTextTrim(localDir);
				Element ttFtpEle = cThisBusiConf.getChild(sftp);
				InputStream ttBatIns = null;
				if (null != ttFtpEle) {	//未配置localDir，如果sftp有配置，通过sftp取文件
					if(ttFtpEle.getAttributeValue("action").equals("down")){
						ttBatIns = getSFtpFile(ttFtpEle, ttFileName, ttLocalDir);
					}else if(ttFtpEle.getAttributeValue("action").equals("up")){
						deal(ttLocalDir);
						//多个文件名
						List<String> ttListFileName = getListLocalFileName(ttLocalDir);
						for (int i = 0; i < ttListFileName.size(); i++) {
							String fileName = ttListFileName.get(i);
							if(fileName.substring(5, 9).equals(getCoreNum()) && fileName.substring(10,18).equals(Integer.toString(DateUtil.get8Date(cTranDate)))){
								boolean msg = setSFtpFile(ttFtpEle, fileName, ttLocalDir);
							}
						}
					}
				} else if (null!=ttLocalDir && !ttLocalDir.equals("")) {	//如果localDir有配置，优先取本地文件
					ttBatIns = getLocalFile(ttLocalDir, ttFileName);
				} else {	//localDir和sftp都未配置，报错
					throw new MidplatException("对账配置有误！");
				}
				
				if(ttFtpEle.getAttributeValue("action").equals("down")){
					//获取标准对账报文
					Element ttBodyEle = parse(ttBatIns);
					JdomUtil.print(ttBodyEle);
					tTranData.addContent(ttBodyEle);
					System.out.println("zaidaiyin;");
					JdomUtil.print(tInStdXml);
				}
			} catch (Exception ex) {
				cLogger.error("生成标准对账报文出错！", ex);

				//获取标准对账报文
				Element ttError = new Element(Error);
				String ttErrorStr = ex.getMessage();
				if ("".equals(ttErrorStr)) {
					ttErrorStr = ex.toString();
				}
				ttError.setText(ttErrorStr);
				tTranData.addContent(ttError);
			}
			
			//调用业务处理，获取标准返回报文
			String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";
			//若midplat.xml中有非空默认配置，采用该配置
			String tServiceValue = cMidplatRoot.getChildText(service);
			if (null!=tServiceValue && !"".equals(tServiceValue)) {
				tServiceClassName = tServiceValue;
			}
			//若子系统的个性化配置文件中有非空默认配置，采用该配置
			tServiceValue = cThisConfRoot.getChildText(service);
			if (null!=tServiceValue && !"".equals(tServiceValue)) {
				tServiceClassName = tServiceValue;
			}
			tServiceValue = cThisBusiConf.getChildText(service);
			if (null!=tServiceValue && !"".equals(tServiceValue)) {
				tServiceClassName = tServiceValue;
			}
			cLogger.info("业务处理模块：" + tServiceClassName);
			Constructor tServiceConstructor = (Constructor<Service>) Class.forName(
					tServiceClassName).getConstructor(new Class[]{Element.class});
			Service tService = (Service) tServiceConstructor.newInstance(new Object[]{cThisBusiConf});
			System.out.println("看看是啥：");
			JdomUtil.print(tInStdXml);
			Document tOutStdXml = tService.service(tInStdXml);
			
			cResultMsg = tOutStdXml.getRootElement().getChild( 
					Head).getChildText(Desc);
			
			//进行一些后续处理。(一般是空实现，有些交易可能需要在此进行一些文件上传动作)
			ending(tOutStdXml);
			
			//每月1日备份上月的对账文件
			if ("01".equals(DateUtil.getDateStr(cTranDate, "dd"))) {
				bakFiles(cThisBusiConf.getChildTextTrim(localDir));
			}
		} catch (Throwable ex) {
			cLogger.error("交易出错！", ex);
			cResultMsg = ex.toString();
		}
		
		cTranDate = null;	//每次跑完，清空日期
		
		cLogger.info("Out GetSFTPConnection.run()!");
	}
	
	protected Element getHead() {
		cLogger.info("Into GetSFTPConnection.getHead()...");
		
		Element mTranDate = new Element(TranDate);
		mTranDate.setText(DateUtil.getDateStr(cTranDate, "yyyyMMdd"));
		
		Element mTranTime = new Element(TranTime);
		mTranTime.setText(DateUtil.getDateStr(cTranDate, "HHmmss"));
		
		Element mTranCom = (Element) cThisConfRoot.getChild(TranCom).clone();
		
		Element mNodeNo = (Element) cThisBusiConf.getChild(NodeNo).clone();
		
		Element mTellerNo = new Element(TellerNo);
		mTellerNo.setText(CodeDef.SYS);
		
		Element mTranNo = new Element(TranNo);
		mTranNo.setText(getFileName());
		
		Element mFuncFlag = new Element(FuncFlag);
		mFuncFlag.setText(cThisBusiConf.getChildText(funcFlag));
		
		Element mHead = new Element(Head);
		mHead.addContent(mTranDate);
		mHead.addContent(mTranTime);
		mHead.addContent(mTranCom);
		mHead.addContent(mNodeNo);
		mHead.addContent(mTellerNo);
		mHead.addContent(mTranNo);
		mHead.addContent(mFuncFlag);

		cLogger.info("Out GetSFTPConnection.getHead()!");
		return mHead;
	}
	//取Sftp文件
	private InputStream getSFtpFile(Element pSFtpEle, String pFileName, String pLocalDir) throws Exception {
		cLogger.info("Into GetSFTPConnection.getFtpFile()...");
		
		String mSFtpIp = pSFtpEle.getAttributeValue(ip);
		cLogger.debug("sftp.ip = " + mSFtpIp);
		if (null==mSFtpIp || mSFtpIp.equals("")) {
			throw new MidplatException("未配置上传sftp的ip！");
		}
		
		String mSFtpPort = pSFtpEle.getAttributeValue(port);
		if (null==mSFtpPort || mSFtpPort.equals("")) {
			mSFtpPort = "22";
		}
		cLogger.debug("sftp.port = " + mSFtpPort);
		
		String mSFtpUser = pSFtpEle.getAttributeValue(user);
		cLogger.debug("sftp.user = " + mSFtpUser);
		
		String mSFtpPassword = pSFtpEle.getAttributeValue(password);
		cLogger.debug("sftp.password = " + mSFtpPassword);
		
		String mSFtpPrivateKey = pSFtpEle.getAttributeValue("privateKey");
		cLogger.debug("sftp.privateKey = " + mSFtpPrivateKey);
		
		String mSFtpPassphrase = pSFtpEle.getAttributeValue("passphrase");
		cLogger.debug("sftp.passphrase = " + mSFtpPassphrase);
		
		String mSFtpPath = pSFtpEle.getAttributeValue("path");
		cLogger.debug("sftp.path = " + mSFtpPath);
		
		//重复连接次数
		int mReConn = 5;
		String mCon = pSFtpEle.getAttributeValue(reconn);
		if (null!=mCon && !"".equals(mCon)) {
			try {
				mReConn = Integer.parseInt(mCon);
			} catch (Exception ex) {
				cLogger.warn("未正确配置sftp最大重复连接次数，将采用系统默认值！");
			}
		}
		cLogger.debug("sftp.reconn = " + mReConn);
		
		//连接超时，默认为5分钟
		int mTimeout = 5 * 60;
		String mTime = pSFtpEle.getAttributeValue(timeout);
		if (null!=mTime && !"".equals(mTime)) {
			try {
				mTimeout = Integer.parseInt(mTime);
			} catch (Exception ex) {
				cLogger.warn("未正确配置sftp超时，采用系统默认值！");
			}
		}
		cLogger.debug("sftp.timeout = " + mTimeout + "s");
		
		String mLocalPath = null;
		if (null!=pLocalDir && !"".equals(pLocalDir)) {
			pLocalDir = pLocalDir.replace('\\', '/');
			if (!pLocalDir.endsWith("/")) {
				pLocalDir += '/';
			}
			mLocalPath = pLocalDir + pFileName;
		}
		cLogger.info("LocalPath = " + mLocalPath);
		
		//sftp到银行
		System.out.println("私钥路径："+mSFtpPrivateKey);
		ChannelSftp sftp = connectSFTP(mSFtpIp, mSFtpUser, mSFtpPassword, mSFtpPrivateKey, mSFtpPassphrase, Integer.parseInt(mSFtpPort));
		//下载文件
		FileOutputStream tLocalFos = null;
		//try {
		if(mLocalPath!=null){
			tLocalFos = new FileOutputStream(mLocalPath);
		}
		else{
			tLocalFos = null;
		}
		//} catch (Exception ex) {
			//cLogger.warn("未正确配置sftp文件本地备份目录，将停止备份操作，直接进行对账 ！", ex);
		//}
		InputStream mBatIs = download(tLocalFos,mSFtpPath,pFileName,mLocalPath,sftp);
		if(mBatIs!=null){
			cLogger.info("sftp下载数据成功！" + mLocalPath);
		}
		//断开连接
		disconnected(sftp);
		if (null == mBatIs) {
			throw new MidplatException("未找到对账文件！" + pFileName);
		}
		cLogger.info("Out GetSFTPConnection.getFtpFile()!");
		return mBatIs;
	}
	//取本地 
	private InputStream getLocalFile(String pDir, String pName) throws MidplatException {
		cLogger.info("Into GetSFTPConnection.getLocalFile()...");
		
		pDir = pDir.replace('\\', '/');
		if (!pDir.endsWith("/")) {
			pDir += '/';
		}
		String mPathName = pDir + pName;
		cLogger.info("LocalPath = " + mPathName);
		
		InputStream mIns = null;
		try {
			mIns = new FileInputStream(mPathName);
		} catch (IOException ex) {
			//未找到对账文件
			throw new MidplatException("未找到文件！" + mPathName);
		}
		
		cLogger.info("Out GetSFTPConnection.getLocalFile()!");
		return mIns;
	}
	
	protected Element parse(InputStream pBatIs) throws Exception {
		cLogger.info("Into GetSFTPConnection.parse()...");
		
		String mCharset = cThisBusiConf.getChildText(charset);
		if (null==mCharset || "".equals(mCharset)) {
			mCharset = "GBK";
		}
		BufferedReader mBufReader = new BufferedReader(
				new InputStreamReader(pBatIs, mCharset));
		
		Element mBodyEle = new Element(Body);
		for (String tLineMsg; null != (tLineMsg=mBufReader.readLine());) {
			cLogger.info(tLineMsg);
			//空行，直接跳过
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("空行，直接跳过，继续下一条！");
				continue;
			}
			
			String[] tSubMsgs = tLineMsg.split("\\|", -1);
			
			Element tTranDate = new Element(TranDate);
			tTranDate.setText(tSubMsgs[1]);
			
			Element tNodeNo = new Element(NodeNo);
			tNodeNo.setText(tSubMsgs[3]);
			
			Element tTranNo = new Element(TranNo);
			tTranNo.setText(tSubMsgs[5]);

			Element tContNo = new Element(ContNo);
			tContNo.setText(tSubMsgs[6]);
			
			Element tPrem = new Element(Prem);
			tPrem.setText(tSubMsgs[7]);
			
			Element tDetail = new Element(Detail);
			tDetail.addContent(tTranDate);
			tDetail.addContent(tNodeNo);
			tDetail.addContent(tTranNo);
			tDetail.addContent(tContNo);
			tDetail.addContent(tPrem);
			
			mBodyEle.addContent(tDetail);
		}
		System.out.println("666666666666");
		mBufReader.close();
		JdomUtil.print(mBodyEle);
		cLogger.info("Out GetSFTPConnection.parse()!");
		return mBodyEle;
	}
	//备份月文件
	private void bakFiles(String pFileDir) {
		cLogger.info("Into Balance.bakFiles()...");
		
		if (null==pFileDir || "".equals(pFileDir)) {
			cLogger.warn("本地文件目录为空，不进行备份操作！");
			return;
		}
		File mDirFile = new File(pFileDir);
		if (!mDirFile.exists() || !mDirFile.isDirectory()) {
			cLogger.warn("本地文件目录不存在，不进行备份操作！" + mDirFile);
			return;
		}
		
		File[] mOldFiles = mDirFile.listFiles(
				new FileFilter(){
					public boolean accept(File pFile) {
						if (!pFile.isFile()) {
							return false;
						}
						
						Calendar tCurCalendar = Calendar.getInstance();
						tCurCalendar.setTime(cTranDate);
						
						Calendar tFileCalendar = Calendar.getInstance();
						tFileCalendar.setTime(new Date(pFile.lastModified()));
						
						return tFileCalendar.before(tCurCalendar);
					}
				});
		
		Calendar mCalendar = Calendar.getInstance();
		mCalendar.add(Calendar.MONTH, -1);
		File mNewDir = 
			new File(mDirFile, DateUtil.getDateStr(mCalendar, "yyyy/yyyyMM"));
		for (File tFile : mOldFiles) {
			cLogger.info(tFile.getAbsoluteFile() + " start move...");
			try {
				IOTrans.fileMove(tFile, mNewDir);
				cLogger.info(tFile.getAbsoluteFile() + " end move!");
			} catch (IOException ex) {
				cLogger.error(tFile.getAbsoluteFile()+"备份失败！", ex);
			}
		}
		
		cLogger.info("Out Balance.bakFiles()!");
	}
	
	public final void setDate(Date pDate) {
		cTranDate = pDate;
	}
	
	public final void setDate(String p8DateStr) {
		cTranDate = DateUtil.parseDate(p8DateStr, "yyyyMMdd");
	}
	
	/**
	 * 根据对账文件命名规则，生成当天对账文件名
	 */
	protected abstract String getFileName();
	
	
	/**
	 * 得到保险公司编号
	 */
	protected abstract String getCoreNum();
	/**
	 * 核退保业务处理
	 */
	protected abstract void deal(String ttLocalDir);
	/**
	 * 该方法会在调用完后台服务之后被调用。
	 * 默认是空实现，在一些特殊交易中可以用来做一些个性化的额外后续处理，
	 * 常见的情况：工行非实时核保对账，在调用完后台后，进行文件上传动作，即可在此方法中实现。
	 * @param Document pOutStdXml: 后台返回的标准报文。
	 */
	protected void ending(Document pOutStdXml) throws Exception {
		cLogger.info("Into GetSFTPConnection.ending()...");
		
		cLogger.info("do nothing, just out!");
		
		cLogger.info("Out GetSFTPConnection.ending()!");
	}
	
	public String getResultMsg() {
		return cResultMsg;
	}
    
	/**
     * 获取连接  20140711 dongxue
     * @return channel
	 * @throws IOException 
     */
	 public ChannelSftp connectSFTP(String host,String username,String password,String privateKey,String passphrase,int port) throws IOException {
        JSch jsch = new JSch();
        passphrase = password;
        System.out.println("host:"+host+"   username:"+username+"    password:"+password+"   privateKey:"+privateKey+"    passphrase:"+passphrase+"  port:"+port);
        Channel channel = null;
        try {
            if (privateKey != null && !"".equals(privateKey)) {
            	
//            	System.out.println("私钥："+privateKey);
//            	 // 第1步、使用File类找到一个文件  
//                File f= new File(privateKey) ;  // 声明File对象  
//                System.out.println("当前标准路径："+f.getCanonicalPath());
//                // 第2步、通过子类实例化父类对象  
//                InputStream input = null ;  // 准备好一个输入的对象  
//                input = new FileInputStream(f)  ;   // 通过对象多态性，进行实例化  
//                // 第3步、进行读操作  
//                // byte b[] = new byte[input..available()] ;  跟使用下面的代码是一样的  
//                byte b[] = new byte[(int)f.length()] ;      // 数组大小由文件决定  
//                int len = input.read(b) ;       // 读取内容  
//                // 第4步、关闭输出流  
//                input.close() ;                     // 关闭输出流\  
//                cLogger.info("读入数据的长度：" + len) ;  
//                cLogger.info("内容为：" + new String(b)) ;    // 把byte数组变为字符串输出 
                //使用密钥验证方式，密钥可以使有口令的密钥，也可以是没有口令的密钥
            	
                if (passphrase != null &&!"".equals(passphrase)) {
                    jsch.addIdentity(privateKey.toString(), passphrase);
                    cLogger.info("有密码验证");
                } else {
                    jsch.addIdentity(privateKey.toString());
                    cLogger.info("无密码验证");
                }
            }
            Session session = jsch.getSession(username, host, port);
            if (password != null && !"".equals(password)) {
                session.setPassword(password);
            }
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");// do not verify host key
            session.setConfig(sshConfig);
            // session.setTimeout(timeout);
            //session.setServerAliveInterval(92000);
            cLogger.info("开始连接......");
            session.connect();
            cLogger.info("打开通道......");
            //参数sftp指明要打开的连接是sftp连接
            channel = session.openChannel("sftp");
            channel.connect();
            cLogger.info("sftp连接成功！" + host);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return (ChannelSftp) channel;
    }
	 /**
	     * 上传文件 20140711 dongxue
	     * 
	     * @param directory
	     *            上传的目录
	     * @param uploadFile
	     *            要上传的文件
	     * @param fileName           
	     *            上传文件名
	     * @param sftp
	     */
    public boolean upload(String directory,InputStream uploadFile,String fileName,ChannelSftp sftp) {
        boolean returnMsg = false;
    	try {
            sftp.cd(directory);
            sftp.put(uploadFile,fileName);
    		if(sftp.get(fileName)!=null){
    			cLogger.info("sftp上传文件成功！");
    			returnMsg = true;
    		}else{
    			cLogger.warn("sftp上传文件失败！");
    			returnMsg = false;
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMsg;
    }
    /**
     * 下载文件
     * 
     * @param directory
     *            下载目录
     * @param downloadFile
     *            下载的文件
     * @param saveFile
     *            存在本地的路径
     * @param sftp
     */
    public InputStream download(FileOutputStream tLocalFos, String directory, String downloadFile,
            String saveFile, ChannelSftp sftp) {
    	InputStream mBatIs = null;
    	try {
    		cLogger.info("你知道吗？已经开始下载对账文件了！");
    		sftp.cd(directory);
    		if (null == tLocalFos) {	//未正确设置备份目录，直接使用sftp流进行对账
				if (sftp.get(downloadFile)!= null) {
					cLogger.info("未备份sftp下载数据成功！");
					try{
						ByteArrayOutputStream ttBaos = new ByteArrayOutputStream();
						sftp.get(downloadFile,ttBaos);
						mBatIs = new ByteArrayInputStream(ttBaos.toByteArray());
					} catch (Exception ex) {
					cLogger.warn("未正确配置sftp文件本地备份目录，将停止备份操作，直接进行对账 ！", ex);
				    }
				} else {
					cLogger.warn("未备份sftp下载数据失败！");
				}
			} else {
				if (sftp.get(downloadFile)!= null) {
					cLogger.info("sftp下载数据成功！");
					sftp.get(downloadFile,saveFile);
					mBatIs = new FileInputStream(saveFile);
					tLocalFos.close();
				}else{
					cLogger.warn("sftp下载数据失败！");
				}
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
        cLogger.info("对账下载结束！");
        return mBatIs;
    }
    //断开连接
    public void disconnected(ChannelSftp sftp){
    	if (sftp != null && sftp.isConnected()) {
            try {
                sftp.getSession().disconnect();
                cLogger.info("sftp连接断开！");
            } catch (JSchException e) {
                e.printStackTrace();
                cLogger.warn("服务端连接已断开！", e);
            }
            sftp.disconnect();
        }
    }
    //Sftp发送文件
	private boolean setSFtpFile(Element pSFtpEle, String pFileName, String pLocalDir) throws Exception {
		cLogger.info("Into GetSFTPConnection.setSFtpFile()...");
		
		String mSFtpIp = pSFtpEle.getAttributeValue(ip);
		cLogger.debug("sftp.ip = " + mSFtpIp);
		if (null==mSFtpIp || mSFtpIp.equals("")) {
			throw new MidplatException("未配置上传sftp的ip！");
		}
		
		String mSFtpPort = pSFtpEle.getAttributeValue(port);
		if (null==mSFtpPort || mSFtpPort.equals("")) {
			mSFtpPort = "22";
		}
		cLogger.debug("sftp.port = " + mSFtpPort);
		
		String mSFtpUser = pSFtpEle.getAttributeValue(user);
		cLogger.debug("sftp.user = " + mSFtpUser);
		
		String mSFtpPassword = pSFtpEle.getAttributeValue(password);
		cLogger.debug("sftp.password = " + mSFtpPassword);
		
		String mSFtpPrivateKey = pSFtpEle.getAttributeValue("privateKey");
		cLogger.debug("sftp.privateKey = " + mSFtpPrivateKey);
		
		String mSFtpPassphrase = pSFtpEle.getAttributeValue("passphrase");
		cLogger.debug("sftp.passphrase = " + mSFtpPassphrase);
		//银行文件路径
		String mSFtpPath = pSFtpEle.getAttributeValue("path");
		cLogger.debug("sftp.path = " + mSFtpPath);
		
		//重复连接次数
		int mReConn = 5;
		String mAttrValue = pSFtpEle.getAttributeValue(reconn);
		if (null!=mAttrValue && !"".equals(mAttrValue)) {
			try {
				mReConn = Integer.parseInt(mAttrValue);
			} catch (Exception ex) {
				cLogger.warn("未正确配置sftp最大重复连接次数，将采用系统默认值！");
			}
		}
		cLogger.debug("sftp.reconn = " + mReConn);
		
		//连接超时，默认为5分钟
		int mTimeout = 5 * 60;
		mAttrValue = pSFtpEle.getAttributeValue(timeout);
		if (null!=mAttrValue && !"".equals(mAttrValue)) {
			try {
				mTimeout = Integer.parseInt(mAttrValue);
			} catch (Exception ex) {
				cLogger.warn("未正确配置sftp超时，采用系统默认值！");
			}
		}
		cLogger.debug("sftp.timeout = " + mTimeout + "s");
		
		String mLocalPath = null;
		if (null!=pLocalDir && !"".equals(pLocalDir)) {
			pLocalDir = pLocalDir.replace('\\', '/');
			if (!pLocalDir.endsWith("/")) {
				pLocalDir += '/';
			}
			mLocalPath = pLocalDir + pFileName;
		}
		cLogger.info("LocalPath = " + mLocalPath);
		
		//sftp到银行
		ChannelSftp sftp = connectSFTP(mSFtpIp, mSFtpUser, mSFtpPassword, mSFtpPrivateKey, mSFtpPassphrase, Integer.parseInt(mSFtpPort));
		//取本地文件
		InputStream mBatIs = getLocalFile(pLocalDir,pFileName);
		//上传文件
		boolean returnMsg = upload(mSFtpPath,mBatIs,pFileName,sftp);
		//断开连接
		disconnected(sftp);
		
		cLogger.info("Out GetSFTPConnection.setSFtpFile()!");
		return returnMsg;
	}
	//核保  取本地所有文件 
	private List<String> getListLocalFileName(String pDir){
		cLogger.info("Into GetSFTPConnection.getListLocalFileName()...");
		List<String> fileNames = new ArrayList<String>();
//		pDir = pDir.replace('\\', '/');
//		if (!pDir.endsWith("/")) {
//			pDir += '/';
//		}
		String mPathName = pDir+"/";
		cLogger.info("LocalPath = " + mPathName);
		File f = new File(mPathName);
		if (!f.exists())
		{
			    cLogger.info("未找到本地文件！");
		        return null;
		}
		File file[] = f.listFiles();
		for(int i=0;i<file.length;i++)
		{
		        File fs = file[i];
		        if (!fs.isDirectory())
		        {
		        	String fileName = fs.getName();
		        	fileNames.add(fileName);
		        	cLogger.info(fs.getName()+"<br />");
		        }else{
		        	cLogger.info(fs.getName()+"目录<br />");
		        }
		}
		cLogger.info("Out GetSFTPConnection.getListLocalFile()!");
		return fileNames;
	}
	
}

