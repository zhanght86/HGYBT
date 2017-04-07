package com.sinosoft.midplat.boc.bat;

/**
 * ������ͨ����
 * @author PengYF
 */
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import org.apache.log4j.Logger;
import org.jdom.Element;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.FTPDealBL;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.midplat.exception.MidplatException;

public class NoYbtContBlc extends TimerTask implements XmlTag 
{
	
	protected final Logger cLogger = Logger.getLogger(getClass());

	protected static Element cConfigEle;
	private static String cCurDate = "";
	@Override
	public void run() 
	{
		cLogger.info("Into SurrenderDataSyc.run()...");
		cLogger.info("�й����з�����ͨ����Ԥ�ǼǴ���ʼ...");
		try 
		{
			//1������ǰ�û�FTP�ļ�������
			cConfigEle = BatUtils.getConfigEle("1108");
			if ("".equals(cCurDate)) {
				cCurDate = new SimpleDateFormat("yyyyMMdd").format(new Date().getTime());
			}
			//���չ�˾����
			String mCorNo = cConfigEle.getChildTextTrim("ComCode");
			//�ļ���
			String mFIleName = "ALLYC"+mCorNo+"BOC"+cCurDate+".txt";
			cLogger.info("�����ļ���Ϊ��"+mFIleName);
			//==========================���ض����ļ���ʼ===========================
			Element ttFtpEle = cConfigEle.getChild("ftp");
			new BocFileDownload().getFtpFile(ttFtpEle,mFIleName,cConfigEle.getChildTextTrim("FilePath"));
			//==========================���ض����ļ�����===========================
			String mFilePath = cConfigEle.getChildTextTrim("FilePath")+ mFIleName;
			cLogger.info("�����ļ�·����"+mFilePath);
			File file =new File(mFilePath);
			if(!file.exists()){
				throw new MidplatException("�������з�����ͨ����Ԥ�ǼǴ����ļ�������,��ȷ�ϣ�");
			}
			//2���ϴ��ļ�������ǰ�û�
			String sFtpIP = cConfigEle.getChildText("FtpIP");
			String sFtpPort = cConfigEle.getChildText("FtpPort");
			String sFtpUser = cConfigEle.getChildText("FtpUser");
			String sFtpPass = cConfigEle.getChildText("FtpPass");
			String sFtpFilePath = cConfigEle.getChildText("FtpFilePath");//�ϴ�·��
			cLogger.info("��ȡ�����ļ�ftp�������...");
			FTPDealBL tFTPDealBL = new FTPDealBL(sFtpIP, sFtpUser,
					sFtpPass, Integer.valueOf(sFtpPort));
			File mfile = new File(mFilePath);
			cLogger.info("��ʼ�ϴ�...");
			if (!tFTPDealBL.ApacheFTPUploadFile(mfile, sFtpFilePath)) {
				cLogger.info("�ϴ�����...");
				throw new MidplatException("FTP�ϴ����� FileName = " + mFIleName);
			} 
			cLogger.info("�ϴ�����...");
			//3�����ݱ��ض����ļ�
			Date date = new Date();  
			if ("01".equals(DateUtil.getDateStr(date, "dd"))){
				bakFiles(cConfigEle.getChildTextTrim("FilePath"),date);
			}
		}catch (Exception e) {
			cLogger.error(cConfigEle.getChildText(name) + "����ʧ�ܣ�", e);
			e.printStackTrace();
		}finally {
			cCurDate = "";
		}
		cLogger.info("�й����з�����ͨ����Ԥ�ǼǴ������...");
		cLogger.info("Out SurrenderDataSyc.run()...");
	}
	
	
	/*****************�����ļ�********************/
	public void bakFiles(String pFileDir, final Date date) {
	    System.out.println("Into Balance.bakFiles()...");
	    
	    if ((pFileDir == null) || ("".equals(pFileDir))) {
	      System.out.println("�����ļ�Ŀ¼Ϊ�գ������б��ݲ�����");
	      return;
	    }
	    File mDirFile = new File(pFileDir);
	    if ((!mDirFile.exists()) || (!mDirFile.isDirectory())) {
	      System.out.println("�����ļ�Ŀ¼�����ڣ������б��ݲ�����" + mDirFile);
	      return;
	    }

	    File[] mOldFiles = mDirFile.listFiles(
	      new FileFilter()
	    {
	      public boolean accept(File pFile)
	      {
	        if (!pFile.isFile()) {
	          return false;
	        }

	        Calendar tCurCalendar = Calendar.getInstance();
	        tCurCalendar.setTime(date);
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
	      System.out.println(tFile.getAbsoluteFile() + " start move...");
	      try {
	        IOTrans.fileMove(tFile, mNewDir);
	        System.out.println(tFile.getAbsoluteFile() + " end move!");
	      } catch (IOException ex) {
	        System.out.printf(tFile.getAbsoluteFile() + "����ʧ�ܣ�", ex);
	      }
	    }

	    System.out.println("Out Balance.bakFiles()!");
	  }
	public static void main(String[] args) throws Exception {
			Logger mLogger = Logger
					.getLogger("com.sinosoft.midplat.boc.bat.SurrenderDataSyc.main");
			mLogger.info("�й����з�����ͨ����Ԥ�ǼǴ����������������");

			NoYbtContBlc mBatch = new NoYbtContBlc();

			// ���ڲ����ˣ����ò���������
			if (0 != args.length) {
				mLogger.info("args[0] = " + args[0]);

				/**
				 * �ϸ�����У���������ʽ��\\d{4}-((0\\d)|(1[012]))-(([012]\\d)|(3[01]))��
				 * 4λ��-2λ��-2λ�ա� 4λ�꣺4λ[0-9]�����֡�
				 * 1��2λ�£�������Ϊ0��[0-9]�����֣�˫���±�����1��ͷ��β��Ϊ0��1��2������֮һ��
				 * 1��2λ�գ���0��1��2��ͷ��[0-9]�����֣�������3��ͷ��0��1��
				 * 
				 * ������У���������ʽ��\\d{4}-\\d{2}-\\d{2}��
				 */
				if (args[0].matches("\\d{4}((0\\d)|(1[012]))(([012]\\d)|(3[01]))")) {
					cCurDate = args[0];
				} else {
					throw new MidplatException("���ڸ�ʽ����ӦΪyyyyMMdd��" + args[0]);
				}
			}
			mBatch.run();
			System.out.println("�й����з�����ͨ����Ԥ�ǼǴ��������ɡ�����");
		}
}
