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
 * ͨ��SFTPȡ�ļ� 
 */
public abstract class GetSFTPConnection extends TimerTask implements XmlTag{
protected final Logger cLogger = Logger.getLogger(getClass());
	
	//��ϵͳ�����ļ����������:icbc.xml
	private final XmlConf cThisConf;
	private final int cFuncFlag;	//���״���
	
	/**
	 * �ṩһ��ȫ�ַ��ʵ㣬ֻ��ÿ�ζ��˿�ʼʱ��ʼ����
	 * ȷ���ڸôζ��˴������������������һ���ԣ�
	 * ���ܿ���(ǰ��Ĵ�����0��ǰ���������0���)��Ӱ�졣
	 */
	protected Date cTranDate;
	
	protected String cResultMsg;
	
	/**
	 * �ṩһ��ȫ�ַ��ʵ㣬ֻ��ÿ�ζ��˿�ʼʱ���³�ʼ����
	 * ȷ���ڸôζ��˴������������������һ���ԣ�
	 * ���������ļ��Զ����ص�Ӱ�졣Ҳ����˵�����ζ�ʱ����һ��������
	 * ��������ļ����޸Ľ�������һ������ʱ��Ч����Ӱ�챾�Ρ�
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
	 * Ϊ��֤����Timer������Ϊĳ���һ���쳣����ֹ��������벶��run()�е������쳣��
	 * a)	��ȡ�������ڡ������ָ������Ϊ�����ˣ�����ȥ����ϵͳ���ڣ��������ˡ�
		b)	��ȡ�����ļ������ļ��������ඨ�塣
		c)	��֯��׼������ͷ����BaseInfo��
		d)	��ȡ�����ļ���ͨ�������ļ�������sftp���Ǳ��ء�
		e)	���������ļ������ɱ�׼������ϸ��
		f)	����ͷ����ϸ���ɱ�׼�����ģ���������instd��
		g)	���øý������õķ�������ж��˴���
		h)	������˴�������outstd��
		i)	����protected void ending(Document pOutStdXml) throws Exception����һЩ��Ҫ�ĺ�������һ��÷���ʲôҲ������
		j)	����ǵ���1�գ�����֮ǰ�Ķ����ļ���$ localDir/yyyy/yyyyMM�¡�
һ����˵��������������д�÷�����
	 */

	public void run() {
		Thread.currentThread().setName(
				String.valueOf(NoFactory.nextTranLogNo()));
		cLogger.info("Into GetSFTPConnection.run()...");
		
		//�����һ�ν����Ϣ
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
			System.out.println("====�˴���ӡ���������ĵı���====");
			JdomUtil.print(tInStdXml);
			Element tHeadEle = getHead();
			tTranData.addContent(tHeadEle);
			
			try {
				//�����ļ���
				String ttFileName = getFileName();
				cLogger.info("FileName = "+ttFileName);
				String ttLocalDir = cThisBusiConf.getChildTextTrim(localDir);
				Element ttFtpEle = cThisBusiConf.getChild(sftp);
				InputStream ttBatIns = null;
				if (null != ttFtpEle) {	//δ����localDir�����sftp�����ã�ͨ��sftpȡ�ļ�
					if(ttFtpEle.getAttributeValue("action").equals("down")){
						ttBatIns = getSFtpFile(ttFtpEle, ttFileName, ttLocalDir);
					}else if(ttFtpEle.getAttributeValue("action").equals("up")){
						deal(ttLocalDir);
						//����ļ���
						List<String> ttListFileName = getListLocalFileName(ttLocalDir);
						for (int i = 0; i < ttListFileName.size(); i++) {
							String fileName = ttListFileName.get(i);
							if(fileName.substring(5, 9).equals(getCoreNum()) && fileName.substring(10,18).equals(Integer.toString(DateUtil.get8Date(cTranDate)))){
								boolean msg = setSFtpFile(ttFtpEle, fileName, ttLocalDir);
							}
						}
					}
				} else if (null!=ttLocalDir && !ttLocalDir.equals("")) {	//���localDir�����ã�����ȡ�����ļ�
					ttBatIns = getLocalFile(ttLocalDir, ttFileName);
				} else {	//localDir��sftp��δ���ã�����
					throw new MidplatException("������������");
				}
				
				if(ttFtpEle.getAttributeValue("action").equals("down")){
					//��ȡ��׼���˱���
					Element ttBodyEle = parse(ttBatIns);
					JdomUtil.print(ttBodyEle);
					tTranData.addContent(ttBodyEle);
					System.out.println("zaidaiyin;");
					JdomUtil.print(tInStdXml);
				}
			} catch (Exception ex) {
				cLogger.error("���ɱ�׼���˱��ĳ���", ex);

				//��ȡ��׼���˱���
				Element ttError = new Element(Error);
				String ttErrorStr = ex.getMessage();
				if ("".equals(ttErrorStr)) {
					ttErrorStr = ex.toString();
				}
				ttError.setText(ttErrorStr);
				tTranData.addContent(ttError);
			}
			
			//����ҵ������ȡ��׼���ر���
			String tServiceClassName = "com.sinosoft.midplat.service.ServiceImpl";
			//��midplat.xml���зǿ�Ĭ�����ã����ø�����
			String tServiceValue = cMidplatRoot.getChildText(service);
			if (null!=tServiceValue && !"".equals(tServiceValue)) {
				tServiceClassName = tServiceValue;
			}
			//����ϵͳ�ĸ��Ի������ļ����зǿ�Ĭ�����ã����ø�����
			tServiceValue = cThisConfRoot.getChildText(service);
			if (null!=tServiceValue && !"".equals(tServiceValue)) {
				tServiceClassName = tServiceValue;
			}
			tServiceValue = cThisBusiConf.getChildText(service);
			if (null!=tServiceValue && !"".equals(tServiceValue)) {
				tServiceClassName = tServiceValue;
			}
			cLogger.info("ҵ����ģ�飺" + tServiceClassName);
			Constructor tServiceConstructor = (Constructor<Service>) Class.forName(
					tServiceClassName).getConstructor(new Class[]{Element.class});
			Service tService = (Service) tServiceConstructor.newInstance(new Object[]{cThisBusiConf});
			System.out.println("������ɶ��");
			JdomUtil.print(tInStdXml);
			Document tOutStdXml = tService.service(tInStdXml);
			
			cResultMsg = tOutStdXml.getRootElement().getChild( 
					Head).getChildText(Desc);
			
			//����һЩ��������(һ���ǿ�ʵ�֣���Щ���׿�����Ҫ�ڴ˽���һЩ�ļ��ϴ�����)
			ending(tOutStdXml);
			
			//ÿ��1�ձ������µĶ����ļ�
			if ("01".equals(DateUtil.getDateStr(cTranDate, "dd"))) {
				bakFiles(cThisBusiConf.getChildTextTrim(localDir));
			}
		} catch (Throwable ex) {
			cLogger.error("���׳���", ex);
			cResultMsg = ex.toString();
		}
		
		cTranDate = null;	//ÿ�����꣬�������
		
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
	//ȡSftp�ļ�
	private InputStream getSFtpFile(Element pSFtpEle, String pFileName, String pLocalDir) throws Exception {
		cLogger.info("Into GetSFTPConnection.getFtpFile()...");
		
		String mSFtpIp = pSFtpEle.getAttributeValue(ip);
		cLogger.debug("sftp.ip = " + mSFtpIp);
		if (null==mSFtpIp || mSFtpIp.equals("")) {
			throw new MidplatException("δ�����ϴ�sftp��ip��");
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
		
		//�ظ����Ӵ���
		int mReConn = 5;
		String mCon = pSFtpEle.getAttributeValue(reconn);
		if (null!=mCon && !"".equals(mCon)) {
			try {
				mReConn = Integer.parseInt(mCon);
			} catch (Exception ex) {
				cLogger.warn("δ��ȷ����sftp����ظ����Ӵ�����������ϵͳĬ��ֵ��");
			}
		}
		cLogger.debug("sftp.reconn = " + mReConn);
		
		//���ӳ�ʱ��Ĭ��Ϊ5����
		int mTimeout = 5 * 60;
		String mTime = pSFtpEle.getAttributeValue(timeout);
		if (null!=mTime && !"".equals(mTime)) {
			try {
				mTimeout = Integer.parseInt(mTime);
			} catch (Exception ex) {
				cLogger.warn("δ��ȷ����sftp��ʱ������ϵͳĬ��ֵ��");
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
		
		//sftp������
		System.out.println("˽Կ·����"+mSFtpPrivateKey);
		ChannelSftp sftp = connectSFTP(mSFtpIp, mSFtpUser, mSFtpPassword, mSFtpPrivateKey, mSFtpPassphrase, Integer.parseInt(mSFtpPort));
		//�����ļ�
		FileOutputStream tLocalFos = null;
		//try {
		if(mLocalPath!=null){
			tLocalFos = new FileOutputStream(mLocalPath);
		}
		else{
			tLocalFos = null;
		}
		//} catch (Exception ex) {
			//cLogger.warn("δ��ȷ����sftp�ļ����ر���Ŀ¼����ֹͣ���ݲ�����ֱ�ӽ��ж��� ��", ex);
		//}
		InputStream mBatIs = download(tLocalFos,mSFtpPath,pFileName,mLocalPath,sftp);
		if(mBatIs!=null){
			cLogger.info("sftp�������ݳɹ���" + mLocalPath);
		}
		//�Ͽ�����
		disconnected(sftp);
		if (null == mBatIs) {
			throw new MidplatException("δ�ҵ������ļ���" + pFileName);
		}
		cLogger.info("Out GetSFTPConnection.getFtpFile()!");
		return mBatIs;
	}
	//ȡ���� 
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
			//δ�ҵ������ļ�
			throw new MidplatException("δ�ҵ��ļ���" + mPathName);
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
			//���У�ֱ������
			tLineMsg = tLineMsg.trim();
			if ("".equals(tLineMsg)) {
				cLogger.warn("���У�ֱ��������������һ����");
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
	//�������ļ�
	private void bakFiles(String pFileDir) {
		cLogger.info("Into Balance.bakFiles()...");
		
		if (null==pFileDir || "".equals(pFileDir)) {
			cLogger.warn("�����ļ�Ŀ¼Ϊ�գ������б��ݲ�����");
			return;
		}
		File mDirFile = new File(pFileDir);
		if (!mDirFile.exists() || !mDirFile.isDirectory()) {
			cLogger.warn("�����ļ�Ŀ¼�����ڣ������б��ݲ�����" + mDirFile);
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
				cLogger.error(tFile.getAbsoluteFile()+"����ʧ�ܣ�", ex);
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
	 * ���ݶ����ļ������������ɵ�������ļ���
	 */
	protected abstract String getFileName();
	
	
	/**
	 * �õ����չ�˾���
	 */
	protected abstract String getCoreNum();
	/**
	 * ���˱�ҵ����
	 */
	protected abstract void deal(String ttLocalDir);
	/**
	 * �÷������ڵ������̨����֮�󱻵��á�
	 * Ĭ���ǿ�ʵ�֣���һЩ���⽻���п���������һЩ���Ի��Ķ����������
	 * ��������������з�ʵʱ�˱����ˣ��ڵ������̨�󣬽����ļ��ϴ������������ڴ˷�����ʵ�֡�
	 * @param Document pOutStdXml: ��̨���صı�׼���ġ�
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
     * ��ȡ����  20140711 dongxue
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
            	
//            	System.out.println("˽Կ��"+privateKey);
//            	 // ��1����ʹ��File���ҵ�һ���ļ�  
//                File f= new File(privateKey) ;  // ����File����  
//                System.out.println("��ǰ��׼·����"+f.getCanonicalPath());
//                // ��2����ͨ������ʵ�����������  
//                InputStream input = null ;  // ׼����һ������Ķ���  
//                input = new FileInputStream(f)  ;   // ͨ�������̬�ԣ�����ʵ����  
//                // ��3�������ж�����  
//                // byte b[] = new byte[input..available()] ;  ��ʹ������Ĵ�����һ����  
//                byte b[] = new byte[(int)f.length()] ;      // �����С���ļ�����  
//                int len = input.read(b) ;       // ��ȡ����  
//                // ��4�����ر������  
//                input.close() ;                     // �ر������\  
//                cLogger.info("�������ݵĳ��ȣ�" + len) ;  
//                cLogger.info("����Ϊ��" + new String(b)) ;    // ��byte�����Ϊ�ַ������ 
                //ʹ����Կ��֤��ʽ����Կ����ʹ�п������Կ��Ҳ������û�п������Կ
            	
                if (passphrase != null &&!"".equals(passphrase)) {
                    jsch.addIdentity(privateKey.toString(), passphrase);
                    cLogger.info("��������֤");
                } else {
                    jsch.addIdentity(privateKey.toString());
                    cLogger.info("��������֤");
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
            cLogger.info("��ʼ����......");
            session.connect();
            cLogger.info("��ͨ��......");
            //����sftpָ��Ҫ�򿪵�������sftp����
            channel = session.openChannel("sftp");
            channel.connect();
            cLogger.info("sftp���ӳɹ���" + host);
        } catch (JSchException e) {
            e.printStackTrace();
        }
        return (ChannelSftp) channel;
    }
	 /**
	     * �ϴ��ļ� 20140711 dongxue
	     * 
	     * @param directory
	     *            �ϴ���Ŀ¼
	     * @param uploadFile
	     *            Ҫ�ϴ����ļ�
	     * @param fileName           
	     *            �ϴ��ļ���
	     * @param sftp
	     */
    public boolean upload(String directory,InputStream uploadFile,String fileName,ChannelSftp sftp) {
        boolean returnMsg = false;
    	try {
            sftp.cd(directory);
            sftp.put(uploadFile,fileName);
    		if(sftp.get(fileName)!=null){
    			cLogger.info("sftp�ϴ��ļ��ɹ���");
    			returnMsg = true;
    		}else{
    			cLogger.warn("sftp�ϴ��ļ�ʧ�ܣ�");
    			returnMsg = false;
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnMsg;
    }
    /**
     * �����ļ�
     * 
     * @param directory
     *            ����Ŀ¼
     * @param downloadFile
     *            ���ص��ļ�
     * @param saveFile
     *            ���ڱ��ص�·��
     * @param sftp
     */
    public InputStream download(FileOutputStream tLocalFos, String directory, String downloadFile,
            String saveFile, ChannelSftp sftp) {
    	InputStream mBatIs = null;
    	try {
    		cLogger.info("��֪�����Ѿ���ʼ���ض����ļ��ˣ�");
    		sftp.cd(directory);
    		if (null == tLocalFos) {	//δ��ȷ���ñ���Ŀ¼��ֱ��ʹ��sftp�����ж���
				if (sftp.get(downloadFile)!= null) {
					cLogger.info("δ����sftp�������ݳɹ���");
					try{
						ByteArrayOutputStream ttBaos = new ByteArrayOutputStream();
						sftp.get(downloadFile,ttBaos);
						mBatIs = new ByteArrayInputStream(ttBaos.toByteArray());
					} catch (Exception ex) {
					cLogger.warn("δ��ȷ����sftp�ļ����ر���Ŀ¼����ֹͣ���ݲ�����ֱ�ӽ��ж��� ��", ex);
				    }
				} else {
					cLogger.warn("δ����sftp��������ʧ�ܣ�");
				}
			} else {
				if (sftp.get(downloadFile)!= null) {
					cLogger.info("sftp�������ݳɹ���");
					sftp.get(downloadFile,saveFile);
					mBatIs = new FileInputStream(saveFile);
					tLocalFos.close();
				}else{
					cLogger.warn("sftp��������ʧ�ܣ�");
				}
			}
        } catch (Exception e) {
            e.printStackTrace();
        }
        cLogger.info("�������ؽ�����");
        return mBatIs;
    }
    //�Ͽ�����
    public void disconnected(ChannelSftp sftp){
    	if (sftp != null && sftp.isConnected()) {
            try {
                sftp.getSession().disconnect();
                cLogger.info("sftp���ӶϿ���");
            } catch (JSchException e) {
                e.printStackTrace();
                cLogger.warn("����������ѶϿ���", e);
            }
            sftp.disconnect();
        }
    }
    //Sftp�����ļ�
	private boolean setSFtpFile(Element pSFtpEle, String pFileName, String pLocalDir) throws Exception {
		cLogger.info("Into GetSFTPConnection.setSFtpFile()...");
		
		String mSFtpIp = pSFtpEle.getAttributeValue(ip);
		cLogger.debug("sftp.ip = " + mSFtpIp);
		if (null==mSFtpIp || mSFtpIp.equals("")) {
			throw new MidplatException("δ�����ϴ�sftp��ip��");
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
		//�����ļ�·��
		String mSFtpPath = pSFtpEle.getAttributeValue("path");
		cLogger.debug("sftp.path = " + mSFtpPath);
		
		//�ظ����Ӵ���
		int mReConn = 5;
		String mAttrValue = pSFtpEle.getAttributeValue(reconn);
		if (null!=mAttrValue && !"".equals(mAttrValue)) {
			try {
				mReConn = Integer.parseInt(mAttrValue);
			} catch (Exception ex) {
				cLogger.warn("δ��ȷ����sftp����ظ����Ӵ�����������ϵͳĬ��ֵ��");
			}
		}
		cLogger.debug("sftp.reconn = " + mReConn);
		
		//���ӳ�ʱ��Ĭ��Ϊ5����
		int mTimeout = 5 * 60;
		mAttrValue = pSFtpEle.getAttributeValue(timeout);
		if (null!=mAttrValue && !"".equals(mAttrValue)) {
			try {
				mTimeout = Integer.parseInt(mAttrValue);
			} catch (Exception ex) {
				cLogger.warn("δ��ȷ����sftp��ʱ������ϵͳĬ��ֵ��");
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
		
		//sftp������
		ChannelSftp sftp = connectSFTP(mSFtpIp, mSFtpUser, mSFtpPassword, mSFtpPrivateKey, mSFtpPassphrase, Integer.parseInt(mSFtpPort));
		//ȡ�����ļ�
		InputStream mBatIs = getLocalFile(pLocalDir,pFileName);
		//�ϴ��ļ�
		boolean returnMsg = upload(mSFtpPath,mBatIs,pFileName,sftp);
		//�Ͽ�����
		disconnected(sftp);
		
		cLogger.info("Out GetSFTPConnection.setSFtpFile()!");
		return returnMsg;
	}
	//�˱�  ȡ���������ļ� 
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
			    cLogger.info("δ�ҵ������ļ���");
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
		        	cLogger.info(fs.getName()+"Ŀ¼<br />");
		        }
		}
		cLogger.info("Out GetSFTPConnection.getListLocalFile()!");
		return fileNames;
	}
	
}

