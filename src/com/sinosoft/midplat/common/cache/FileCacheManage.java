package com.sinosoft.midplat.common.cache;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

public class FileCacheManage extends Thread {
	private static final Logger cLogger = Logger.getLogger(FileCacheManage.class);
	
	private static FileCacheManage cFileCacheManageIns = new FileCacheManage();
	
	private final HashMap<String, Load> cConfigMap = new HashMap<String, Load>();
	
	private int cSleepSecond = 5*60;	//默认五分钟扫描一次
	
	private boolean cIsOpen = true;
	
	private FileCacheManage() {
		setName("FileCache");
		setDaemon(true);
	}
	
	public static FileCacheManage newInstance() {
		return cFileCacheManageIns;
	}
	
	public void run() {
		cLogger.info("缓存管理系统启动...");
		
		while (cIsOpen) {
			cLogger.info("Start scan conf files...");
			
			Iterator<String> mKeyIterator = cConfigMap.keySet().iterator();
			while (mKeyIterator.hasNext()) {
				String tFilePath = mKeyIterator.next();
				Load tLoad = cConfigMap.get(tFilePath);
				if (tLoad.isChanged()) {
					tLoad.load();
				}
			}
			
			cLogger.info("End scan conf files!");
			
			cLogger.debug("Thread sleeps " + cSleepSecond + "s!");
			try {
				Thread.sleep(cSleepSecond*1000);
			} catch (InterruptedException ex) {
				cLogger.warn("Thread.sleep 异常中断！", ex);
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
	
	public void register(String pPath, Load LoadImp) {
		cConfigMap.put(pPath, LoadImp);
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
