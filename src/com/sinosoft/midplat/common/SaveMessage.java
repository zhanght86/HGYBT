package com.sinosoft.midplat.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.jdom.Document;

import com.sinosoft.midplat.MidplatConf;

/**
 * ���汨��
 * @author yuantongxin
 */
public class SaveMessage {
	private final static Logger cLogger = Logger.getLogger(SaveMessage.class);
	public final static String cSavePath;//����·��
	static {
		//��ȡ�м�ƽ̨�����ļ�����·��[�̷�]
		String tSavePath =  MidplatConf.newInstance().getConf().getRootElement().getChildText("SavePath");
		//SavePath_HOME = f:\
		cLogger.debug("SavePath_HOME = " + tSavePath);
		//����·��Ϊ��
		if (null==tSavePath || "".equals(tSavePath)) {
			//ʹ��
			tSavePath = SysInfo.getRealPath("..");
		}
		tSavePath = tSavePath.replace('\\', '/');
		if (!tSavePath.endsWith("/")) {
			tSavePath += '/';
		}
		File tFile = new File(tSavePath);
		if (tFile.exists() && tFile.isDirectory()) {
			cSavePath = tSavePath;
		} else {
			cLogger.error("SavePath_HOME��������" + tSavePath);
			cSavePath = null;
		}
		
		cLogger.debug("SavePath_HOME = " + cSavePath);
	}
	
	/**
	 * ����[�Ǳ�׼���]���ģ���03[���׻�������]Ŀ¼��
	 * @param pXmlDoc �Ǳ�׼�������
	 * @param pTranCom ���׻�������
	 * @param pName �����ļ���
	 */
	public static void save(Document pXmlDoc, String pTranCom, String pName) {
		//1481623141858[2016-12-13 05:59:01]
		long mStartMillis = System.currentTimeMillis();
		//f:/msg/13/2016/201612/20161213/
		//�����ļ�·��[����·��(�̷�)/msg/���׻�������/��ǰ����(��/����/������)]
		StringBuilder mFilePath = new StringBuilder(cSavePath)//f:/
					.append("msg/")//msg/
					.append(pTranCom).append('/')//13/
					.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"));//2016/201612/20161213/
		//f:/msg/13/2016/201612/20161213/
		//�����ļ�·���ļ���
		File mFileDir = new File(mFilePath.toString());
		//�˳���·������ʾ���ļ���Ŀ¼������
		if (!mFileDir.exists()) {
			//�����˳���·����ָ����Ŀ¼�������������赫�����ڵĸ�Ŀ¼
			mFileDir.mkdirs();
			//�˳���·������ʾ���ļ���Ŀ¼���ǲ�����[��ʾ������Ϣ]
			if (!mFileDir.exists()) {
				//Ŀ¼�����ڣ��ҳ��Դ���ʧ�ܣ��˳���·����ת��Ϊһ��·�����ַ���
				cLogger.error("Ŀ¼�����ڣ��ҳ��Դ���ʧ�ܣ�" + mFileDir.getPath());
				//ǿ���˳�����
				return;
			}
		}
		
		/**
		 * �����ļ����������XML�ĵ�������ļ���������ر������ 
		 */
		try {
			//����һ�������ָ�����Ƶ��ļ���д�����ݵ�����ļ���
			FileOutputStream tFos = new FileOutputStream(mFilePath.toString() + pName);
			//��XML�ĵ�������ļ������
			JdomUtil.output(pXmlDoc, tFos);
			//�ر������ 
			tFos.close();
		} catch (IOException ex) {
			//�����ļ�ʧ�ܣ�[�쳣����]
			cLogger.error("�����ļ�ʧ�ܣ�", ex);
		}
		//1388_6_111_out.xml IO�����ʱ:0.0s
		//1406_6_111_out.xml IO�����ʱ:0.015s
		//1550_6_0_inSvc.xml IO�����ʱ:0.021s
		//1561_11_108_in.xml IO�����ʱ:0.0020s
		//1561_12_30_inSvc.xml IO�����ʱ:0.0010s
		cLogger.info(pName+" IO�����ʱ:"+(System.currentTimeMillis()-mStartMillis)/1000.0 + "s");
	}
	
	public static void save(byte[] pBytes, String pTranCom, String pName) {
		StringBuilder mFilePath = new StringBuilder(cSavePath)
			.append("msg/")
			.append(pTranCom).append('/')
			.append(DateUtil.getCurDate("yyyy/yyyyMM/yyyyMMdd/"));
		File mFileDir = new File(mFilePath.toString());
		mFileDir.mkdirs();
		if (!mFileDir.exists()) {
			cLogger.error("Ŀ¼�����ڣ��ҳ��Դ���ʧ�ܣ�" + mFileDir.getPath());
			return;
		}
		
		try {
			FileOutputStream tFos = new FileOutputStream(mFilePath.toString() + pName);
			tFos.write(pBytes);
			tFos.close();
		} catch (IOException ex) {
			cLogger.error("�����ļ�ʧ�ܣ�", ex);
		}
	}
}
