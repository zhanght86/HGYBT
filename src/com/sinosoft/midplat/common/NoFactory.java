package com.sinosoft.midplat.common;

import org.apache.log4j.Logger;
import org.jdom.Element;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.utility.ExeSQL;

/**
 * 编号工厂类
 * @author yuantongxin
 */
public class NoFactory {
	private final static Logger cLogger = Logger.getLogger(NoFactory.class);
	
	//集群ID
	protected static final byte cClusterId;
	//集群数
	protected static final byte cClusterCount;
	/*静态代码块:初始化集群ID、集群数单一实例*/
	static {
		//cluster元素
		Element tClusterEle = MidplatConf.newInstance().getConf().getRootElement().getChild("cluster");
		//cluster元素存在，获取元素属性值
		if (null != tClusterEle) {
			//集群ID
			cClusterId = Byte.parseByte(tClusterEle.getAttributeValue("id"));
			//集群数
			cClusterCount = Byte.parseByte(tClusterEle.getAttributeValue("count"));
		//cluster元素不存在，赋默认值
		} else {
			cClusterId = 0;
			cClusterCount = 1;
		}
		//ClusterId=2; ClusterCount=3
		cLogger.debug("ClusterId=" + cClusterId + "; ClusterCount=" + cClusterCount);
	}
	
	//应用号锁
	private static final byte[] cAppNoLock = new byte[0];
	//应用号
	private static int cAppNo;//6
	
	/**
	 * 下个应用号
	 * @return cAppNo 应用号
	 */
	public final static int nextAppNo() {
		//同步应用号锁
		synchronized (cAppNoLock) {
			/*允许访问控制的代码*/
			//应用号=应用号+集群数[3]
			cAppNo += cClusterCount;//6+3=9
		}
		//返回应用号
		return cAppNo;//9
	}
	
	/**
	 * 设置应用号
	 * @param pMaxNo 最大编号
	 */
	public final static void setAppNo(int pMaxNo) {
		//同步应用号锁
		synchronized (cAppNoLock) {
			//应用号=最大编号-(最大编号余集群数[3])+集群ID
			cAppNo = pMaxNo - pMaxNo%cClusterCount + cClusterId;
		}
	}
	
	private static final byte[] cMillisNoLock = new byte[0];
	private static long cMillisNo;
	static {
		long tMax = System.currentTimeMillis() - 1280000000000L;
		cMillisNo = tMax - tMax%cClusterCount + cClusterId;
	}
	public final static long nextMillisNo() {
		synchronized (cMillisNoLock) {
			cMillisNo += cClusterCount;
		}
		return cMillisNo;
	}
	public final static void setMillisNo(long pMaxNo) {
		synchronized (cMillisNoLock) {
			cMillisNo = pMaxNo - pMaxNo%cClusterCount + cClusterId;
		}
	}
	
	private static final byte[] cSecondNoLock = new byte[0];
	private static int cSecondNo;
	static {
		int tMax = (int)(System.currentTimeMillis()/1000) - 1280000000;
		cSecondNo = tMax - tMax%cClusterCount + cClusterId;
	}
	public final static int nextSecondNo() {
		synchronized (cSecondNoLock) {
			cSecondNo += cClusterCount;
		}
		return cSecondNo;
	}
	public final static void setSecondNo(int pMaxNo) {
		synchronized (cSecondNoLock) {
			cSecondNo = pMaxNo - pMaxNo%cClusterCount + cClusterId;
		}
	}
	
	private static final byte[] cTranLogNoLock = new byte[0];
	private static int cTranLogNo;
	static {
		try {
//			int tMax = Integer.parseInt(
//					new ExeSQL().getOneValue("select max(LogNo) from TranLog"));
//			cTranLogNo = tMax - tMax%cClusterCount + cClusterId;
			cTranLogNo = Integer.parseInt(
					new ExeSQL().getOneValue("select max(LogNo) from TranLog where mod(LogNo,"+cClusterCount+")="+cClusterId));
		} catch (Throwable ex) {
			cLogger.error("初始化最大号异常！", ex);
		}
	}
	public final static int nextTranLogNo() {
		synchronized (cTranLogNoLock) {
			cTranLogNo += cClusterCount;
		}
		return cTranLogNo;
	}
	public final static void setTranLogNo(int pMaxNo) {
		synchronized (cTranLogNoLock) {
			cTranLogNo = pMaxNo - pMaxNo%cClusterCount + cClusterId;
		}
	}
	
	private static final byte[] cNodeMapNoLock = new byte[0];
	private static int cNodeMapNo;
	static {
		try {
//			int tMax = Integer.parseInt(
//					new ExeSQL().getOneValue("select max(MapNo) from NodeMap"));
//			cNodeMapNo = tMax - tMax%cClusterCount + cClusterId;
			//select max(MapNo) from NodeMap where mod(MapNo,3)=0
			cNodeMapNo = Integer.parseInt(
					new ExeSQL().getOneValue("select max(MapNo) from NodeMap where mod(MapNo,"+cClusterCount+")="+cClusterId));
		} catch (Throwable ex) {
			cLogger.error("初始化最大号异常！", ex);
		}
	}
	public final static int nextNodeMapNo() {
		synchronized (cNodeMapNoLock) {
			cNodeMapNo += cClusterCount;
		}
		return cNodeMapNo;
	}
	public final static void setNodeMapNo(int pMaxNo) {
		synchronized (cNodeMapNoLock) {
			cNodeMapNo = pMaxNo - pMaxNo%cClusterCount + cClusterId;
		}
	}
	
//	private static final byte[] cCardTraceNoLock = new byte[0];
//	private static int cCardTraceNo;
//	static {
//		try {
////			int tMax = Integer.parseInt(
////					new ExeSQL().getOneValue("select max(TraceNo) from CardTrace"));
////			cCardTraceNo = tMax - tMax%cClusterCount + cClusterId;
//			cCardTraceNo = Integer.parseInt(
//					new ExeSQL().getOneValue("select max(TraceNo) from CardTrace where mod(TraceNo,"+cClusterCount+")="+cClusterId));
//		} catch (Throwable ex) {
//			cLogger.error("初始化最大号异常！", ex);
//		}
//	}
//	public static int nextCardTraceNo() {
//		synchronized (cCardTraceNoLock) {
//			cCardTraceNo += cClusterCount;
//		}
//		return cCardTraceNo;
//	}
//	public final static void setCardTraceNo(int pMaxNo) {
//		synchronized (cCardTraceNoLock) {
//			cCardTraceNo = pMaxNo - pMaxNo%cClusterCount + cClusterId;
//		}
//	}
//	
	private static final byte[] cContRecordNoLock = new byte[0];
	private static int cContRecordNo;
	static {
		try {
//			int tMax = Integer.parseInt(
//					new ExeSQL().getOneValue("select max(RecordNo) from Cont"));
//			cContRecordNo = tMax - tMax%cClusterCount + cClusterId;
			cContRecordNo = Integer.parseInt(
					new ExeSQL().getOneValue("select max(RecordNo) from Cont where mod(RecordNo,"+cClusterCount+")="+cClusterId));
		} catch (Throwable ex) {
			cLogger.error("初始化最大号异常！", ex);
		}
	}
	public final static int nextContRecordNo() {
		synchronized (cContRecordNoLock) {
			cContRecordNo += cClusterCount;
		}
		return cContRecordNo;
	}
	public final static void setContRecordNo(int pMaxNo) {
		synchronized (cContRecordNoLock) {
			cContRecordNo = pMaxNo - pMaxNo%cClusterCount + cClusterId;
		}
	}
	
//	private static final byte[] cAgentRecordNoLock = new byte[0];
//	private static int cAgentRecordNo;
//	static {
//		try {
////			int tMax = Integer.parseInt(
////					new ExeSQL().getOneValue("select max(RecordNo) from Agent"));
////			cAgentRecordNo = tMax - tMax%cClusterCount + cClusterId;
//			cAgentRecordNo = Integer.parseInt(
//					new ExeSQL().getOneValue("select max(RecordNo) from Agent where mod(RecordNo,"+cClusterCount+")="+cClusterId));
//		} catch (Throwable ex) {
//			cLogger.error("初始化最大号异常！", ex);
//		}
//	}
//	public final static int nextAgentRecordNo() {
//		synchronized (cAgentRecordNoLock) {
//			cAgentRecordNo += cClusterCount;
//		}
//		return cAgentRecordNo;
//	}
//	public final static void setAgentRecordNo(int pMaxNo) {
//		synchronized (cAgentRecordNoLock) {
//			cAgentRecordNo = pMaxNo - pMaxNo%cClusterCount + cClusterId;
//		}
//	}
	
	/* 2015年2月28日 17:19:24
	 * lilu 注释调，免得启动时报错
	 */
	
//	
//	private static final byte[] cBankComNoLock = new byte[0];
//	private static int cBankComNo;
//	static {
//		try {
////			int tMax = Integer.parseInt(
////					new ExeSQL().getOneValue("select max(RecordNo) from Agent"));
////			cBankComNo = tMax - tMax%cClusterCount + cClusterId;
//			cBankComNo = Integer.parseInt(
//					new ExeSQL().getOneValue("select max(RecordNo) from LKBankCom where mod(RecordNo,"+cClusterCount+")="+cClusterId));
//		} catch (Throwable ex) {
//			cLogger.error("初始化最大号异常！", ex);
//		}
//	}
//	public final static int nextBankComNo() {
//		synchronized (cBankComNoLock) {
//			cBankComNo += cClusterCount;
//		}
//		return cBankComNo;
//	}
//	public final static void setBankComNo(int pMaxNo) {
//		synchronized (cBankComNoLock) {
//			cBankComNo = pMaxNo - pMaxNo%cClusterCount + cClusterId;
//		}
//	}
//	
	private static final byte[] cBatTranLogNoLock = new byte[0];
	private static int cBatTranLogNo;
//	static {
//		try {
//			cBatTranLogNo = Integer.parseInt(
//					new ExeSQL().getOneValue("select max(LogNo) from BatTranLog where mod(LogNo,"+cClusterCount+")="+cClusterId));
//		} catch (Throwable ex) {
//			cLogger.error("初始化最大号异常！", ex);
//		}
//	}
	public final static int nextBatTranLogNo() {
		synchronized (cBatTranLogNoLock) {
			cBatTranLogNo += cClusterCount;
		}
		return cBatTranLogNo;
	}
//	public final static void setBatTranLogNo(int pMaxNo) {
//		synchronized (cBatTranLogNoLock) {
//			cBatTranLogNo = pMaxNo - pMaxNo%cClusterCount + cClusterId;
//		}
//	}
//	
	private static final byte[] cIFdetailNoLock = new byte[0];
	private static int cIFdetailNo;
//	static {
//		try {
//			cIFdetailNo = Integer.parseInt(
//					new ExeSQL().getOneValue("select max(LogNo) from IFdetail where mod(LogNo,"+cClusterCount+")="+cClusterId));
//		} catch (Throwable ex) {
//			cLogger.error("初始化最大号异常！", ex);
//		}
//	}
	public final static int nextIFdetailNo() {
		synchronized (cIFdetailNoLock) {
			cIFdetailNo += cClusterCount;
		}
		return cIFdetailNo;
	}
//	public final static void setIFdetailNo(int pMaxNo) {
//		synchronized (cIFdetailNoLock) {
//			cIFLogNo = pMaxNo - pMaxNo%cClusterCount + cClusterId;
//		}
//	}
//	
	private static final byte[] cIFLogNoLock = new byte[0];
	private static int cIFLogNo;
//	static {
//		try {
//			cIFLogNo = Integer.parseInt(
//					new ExeSQL().getOneValue("select max(LogNo) from IFLog where mod(LogNo,"+cClusterCount+")="+cClusterId));
//		} catch (Throwable ex) {
//			cLogger.error("初始化最大号异常！", ex);
//		}
//	}
	public final static int nextIFLogNo() {
		synchronized (cIFLogNoLock) {
			cIFLogNo += cClusterCount;
		}
		return cIFLogNo;
	}
//	public final static void setIFLogNo(int pMaxNo) {
//		synchronized (cIFLogNoLock) {
//			cIFLogNo = pMaxNo - pMaxNo%cClusterCount + cClusterId;
//		}
//	}
//	
//	
//	private static final byte[] cCitiIFLogNoLock = new byte[0];
//	private static int cCitiIFLogNo;
//	static {
//		try {
//			cCitiIFLogNo = Integer.parseInt(
//					new ExeSQL().getOneValue("select max(LogNo) from CitiIFLog where mod(LogNo,"+cClusterCount+")="+cClusterId));
//		} catch (Throwable ex) {
//			cLogger.error("初始化最大号异常！", ex);
//		}
//	}
//	public final static int nextCitiIFLogNo() {
//		synchronized (cCitiIFLogNoLock) {
//			cCitiIFLogNo += cClusterCount;
//		}
//		return cCitiIFLogNo;
//	}
//	public final static void setCitiIFLogNo(int pMaxNo) {
//		synchronized (cCitiIFLogNoLock) {
//			cCitiIFLogNo = pMaxNo - pMaxNo%cClusterCount + cClusterId;
//		}
//	}
//	
//	
//	/*
//	private static final byte[] cAxaPolicyNoLock = new byte[0];
//	private static int cAxaPolicyNo;
//	static {
//		try {
//			cAxaPolicyNo = Integer.parseInt(
//					new ExeSQL().getOneValue("select max(oid) from axapolicytransaction where mod(oid,"+cClusterCount+")="+cClusterId));
//		} catch (Throwable ex) {
//			cLogger.error("初始化最大号异常！", ex);
//		}
//	}
//	public final static int nextAxaPolicyNo() {
//		synchronized (cAxaPolicyNoLock) {
//			cAxaPolicyNo += cClusterCount;
//		}
//		return cAxaPolicyNo;
//	}
//	public final static void setAxaPolicyNo(int pMaxNo) {
//		synchronized (cAxaPolicyNoLock) {
//			cAxaPolicyNo = pMaxNo - pMaxNo%cClusterCount + cClusterId;
//		}
//	}
//	*/
//	
//	
//	/*
//	private static final byte[] cAxaFundNoLock = new byte[0];
//	private static int cAxaFundNo;
//	static {
//		try {
//			cAxaFundNo = Integer.parseInt(
//					new ExeSQL().getOneValue("select max(serialno) from AXAFundTransaction where mod(serialno,"+cClusterCount+")="+cClusterId));
//		} catch (Throwable ex) {
//			cLogger.error("初始化最大号异常！", ex);
//		}
//	}
//	public final static int nextAxaFundNo() {
//		synchronized (cAxaFundNoLock) {
//			cAxaFundNo += cClusterCount;
//		}
//		return cAxaFundNo;
//	}
//	public final static void setAxaFundNo(int pMaxNo) {
//		synchronized (cAxaFundNoLock) {
//			cAxaFundNo = pMaxNo - pMaxNo%cClusterCount + cClusterId;
//		}
//	}
//	*/
//	
//	
//	
////	/*
////	 * IC卡前置机日志表的主键生成策略
////	 */
////	
////	private static final byte[] cICTranLogNoLock = new byte[0];
////	private static int cICTranLogNo;
////	static {
////		try {
//////			int tMax = Integer.parseInt(
//////					new ExeSQL().getOneValue("select max(LogNo) from TranLog"));
//////			cTranLogNo = tMax - tMax%cClusterCount + cClusterId;
////			cICTranLogNo = Integer.parseInt(
////					new ExeSQL().getOneValue("select max(LogNo) from ICTranLog where mod(LogNo,"+cClusterCount+")="+cClusterId));
////		} catch (Throwable ex) {
////			cLogger.error("初始化最大号异常！", ex);
////		}
////	}
////	public final static int nextICTranLogNo() {
////		synchronized (cICTranLogNoLock) {
////			cICTranLogNo += cClusterCount;
////		}
////		return cICTranLogNo;
////	}
////	public final static void setICTranLogNo(int pMaxNo) {
////		synchronized (cICTranLogNoLock) {
////			cICTranLogNo = pMaxNo - pMaxNo%cClusterCount + cClusterId;
////		}
////	}
////	
////	
//	
//	public static void main(String [] args){
//		System.out.println(NoFactory.nextBankComNo());
//			}
}




