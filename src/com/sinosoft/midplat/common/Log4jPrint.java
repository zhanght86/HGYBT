package com.sinosoft.midplat.common;

import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.log4j.Logger;

public class Log4jPrint extends PrintStream {
	private static final Logger cLogger = Logger.getLogger(Log4jPrint.class);
	
	public Log4jPrint(OutputStream pOs) {
		super(pOs);
	}
	
	public OutputStream getSrcOs() {
		return out;
	}
	
	public void println(String pMessage) {
		cLogger.info(pMessage);
	}
	
//	public void println(Object pObject) {
//		if (pObject instanceof Throwable) {
//			cLogger.error(" ‰≥ˆ“Ï≥£∂—’ª£∫", (Throwable)pObject);
//		} else {
//			cLogger.info(pObject);
//		}
//	}
}
