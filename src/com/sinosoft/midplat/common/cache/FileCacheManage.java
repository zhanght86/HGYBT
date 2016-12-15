package com.sinosoft.midplat.common.cache;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * �����ļ��������ϵͳ
 * @author yuantongxin
 */
public class FileCacheManage extends Thread {
	private static final Logger cLogger = Logger.getLogger(FileCacheManage.class);
	
	//�����ļ��������ϵͳʵ��
	private static FileCacheManage cFileCacheManageIns = new FileCacheManage();
	
	//�����ļ�ӳ���ϵ��
	private final HashMap<String, Load> cConfigMap = new HashMap<String, Load>();
	
	private int cSleepSecond = 5*60;	//Ĭ�������ɨ��һ��
	
	private boolean cIsOpen = true;
	
	private FileCacheManage() {
		setName("FileCache");
		setDaemon(true);
	}
	
	/**
	 * �����ļ��������ϵͳʵ��[����]
	 * @return ��ʵ��(Thread[FileCache,5,main])
	 */
	public static FileCacheManage newInstance() {
		return cFileCacheManageIns;
	}
	
	public void run() {
		cLogger.info("�������ϵͳ����...");
		
		while (cIsOpen) {
			cLogger.info("Start scan conf files...");
			//{conf/socket.xml=com.sinosoft.midplat.net.SocketConf@50a69b6b, conf/midplat.xml=com.sinosoft.midplat.MidplatConf@6a25b72a}
			//����������
			Iterator<String> mKeyIterator = cConfigMap.keySet().iterator();
			//��������������[����Ԫ�ؿ��Ե������򷵻� true]
			while (mKeyIterator.hasNext()) {
				//���ص�������һ��Ԫ��
				String tFilePath = mKeyIterator.next();
				//����ָ�����ڴ˱�ʶ��ϣӳ������ӳ���ֵ
				Load tLoad = cConfigMap.get(tFilePath);
				//ӳ���ֵ�ı�
				if (tLoad.isChanged()) {
					//ӳ���ֵ���¼���
					tLoad.load();
				}
			}
			//End scan conf files![�����ļ�ɨ�������]
			cLogger.info("End scan conf files!");
			//Thread sleeps 60s!
			cLogger.debug("Thread sleeps " + cSleepSecond + "s!");
			try {
				//��60000���������õ�ǰ����ִ�е��߳����ߣ���ִͣ�У�
				Thread.sleep(cSleepSecond*1000);//[1minute]
			} catch (InterruptedException ex) {
				cLogger.warn("Thread.sleep �쳣�жϣ�", ex);
			}
		}
	}
	
	public void shutdown() {
		cIsOpen = false;
	}
	
	public List<String> load(String pPartName) {
		if ("*".equals(pPartName) || "?".equals(pPartName)) {
			return getFiles();
		}
		
		List<String> mList = new ArrayList<String>();
		Iterator<String> mKeyIterator = cConfigMap.keySet().iterator();
		while (mKeyIterator.hasNext()) {
			String tFilePath = mKeyIterator.next();
			if (tFilePath.contains(pPartName)) {
				Load ttLoad = cConfigMap.get(tFilePath);
				ttLoad.load();
				mList.add(tFilePath);
			}
		}
		
		return mList;
	}
	
	public List<String> loadAll() {
		List<String> mList = new ArrayList<String>();
		
		Iterator<String> mKeyIterator = cConfigMap.keySet().iterator();
		while (mKeyIterator.hasNext()) {
			String tFilePath = mKeyIterator.next();
			Load tLoad = cConfigMap.get(tFilePath);
			tLoad.load();
			mList.add(tFilePath);
		}
		return mList;
	}
	
	/**
	 * ע��
	 * @param pPath ·��
	 * @param LoadImp  ����ʵ�� 
	 */
	public void register(String pPath, Load LoadImp) {
		//����·��������ʵ�� 
		cConfigMap.put(pPath, LoadImp);
		//register succesed! ·��
		cLogger.info("register succesed! " + pPath);
	}
	
	public void remove(String pPath) {
		cConfigMap.remove(pPath);
		cLogger.info("remove: " + pPath);
	}
	
	public List<String> getFiles() {
		List<String> mList = new ArrayList<String>();
		
		Iterator<String> mKeyIterator = cConfigMap.keySet().iterator();
		while (mKeyIterator.hasNext()) {
			mList.add(mKeyIterator.next());
		}
		
		return mList;
	}
	
	public List<String> getFiles(String pPartName) {
		if ("*".equals(pPartName) || "?".equals(pPartName)) {
			return getFiles();
		}
		
		List<String> mList = new ArrayList<String>();
		Iterator<String> mKeyIterator = cConfigMap.keySet().iterator();
		while (mKeyIterator.hasNext()) {
			String tFilePath = mKeyIterator.next();
			if (tFilePath.contains(pPartName)) {
				mList.add(tFilePath);
			}
		}
		
		return mList;
	}
	
	public int getSleepSecond() {
		return cSleepSecond;
	}
	
	public void setSleepSecond(int pSleepSecond) {
		cSleepSecond = pSleepSecond;
	}
}
