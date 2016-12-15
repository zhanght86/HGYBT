package com.sinosoft.midplat.common.cache;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * 配置文件缓存管理系统
 * @author yuantongxin
 */
public class FileCacheManage extends Thread {
	private static final Logger cLogger = Logger.getLogger(FileCacheManage.class);
	
	//配置文件缓存管理系统实例
	private static FileCacheManage cFileCacheManageIns = new FileCacheManage();
	
	//配置文件映射关系集
	private final HashMap<String, Load> cConfigMap = new HashMap<String, Load>();
	
	private int cSleepSecond = 5*60;	//默认五分钟扫描一次
	
	private boolean cIsOpen = true;
	
	private FileCacheManage() {
		setName("FileCache");
		setDaemon(true);
	}
	
	/**
	 * 配置文件缓存管理系统实例[单例]
	 * @return 新实例(Thread[FileCache,5,main])
	 */
	public static FileCacheManage newInstance() {
		return cFileCacheManageIns;
	}
	
	public void run() {
		cLogger.info("缓存管理系统启动...");
		
		while (cIsOpen) {
			cLogger.info("Start scan conf files...");
			//{conf/socket.xml=com.sinosoft.midplat.net.SocketConf@50a69b6b, conf/midplat.xml=com.sinosoft.midplat.MidplatConf@6a25b72a}
			//键集迭代器
			Iterator<String> mKeyIterator = cConfigMap.keySet().iterator();
			//遍历键集迭代器[仍有元素可以迭代，则返回 true]
			while (mKeyIterator.hasNext()) {
				//返回迭代的下一个元素
				String tFilePath = mKeyIterator.next();
				//返回指定键在此标识哈希映射中所映射的值
				Load tLoad = cConfigMap.get(tFilePath);
				//映射的值改变
				if (tLoad.isChanged()) {
					//映射的值重新加载
					tLoad.load();
				}
			}
			//End scan conf files![配置文件扫描结束！]
			cLogger.info("End scan conf files!");
			//Thread sleeps 60s!
			cLogger.debug("Thread sleeps " + cSleepSecond + "s!");
			try {
				//在60000毫秒数内让当前正在执行的线程休眠（暂停执行）
				Thread.sleep(cSleepSecond*1000);//[1minute]
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
	
	/**
	 * 注册
	 * @param pPath 路径
	 * @param LoadImp  加载实现 
	 */
	public void register(String pPath, Load LoadImp) {
		//加入路径，加载实现 
		cConfigMap.put(pPath, LoadImp);
		//register succesed! 路径
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
