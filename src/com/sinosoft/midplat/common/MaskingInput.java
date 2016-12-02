package com.sinosoft.midplat.common;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MaskingInput {
	public static String getPassword(String pPromptMsg) throws IOException {
		FlushThread mFlushThread = new FlushThread(pPromptMsg);
		mFlushThread.start();
		
		BufferedReader mBufReader = 
			new BufferedReader(
				new InputStreamReader(System.in));
		String mPassword = mBufReader.readLine();
		
		mFlushThread.stopFlush();
		
		return mPassword;
	}
	
	private static class FlushThread extends Thread {
		private boolean cStop = false;
		
		private final String cPromptMsg;
		
		public FlushThread(String pPromptMsg) {
			cPromptMsg = pPromptMsg;
		}
		
		public void run() {
			while (!cStop) {
				try {
					sleep(1);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
				if (!cStop) {
					System.out.print("\r" + cPromptMsg + " \r" + cPromptMsg);
				}
				System.out.flush();
			}
		}
		
		public void stopFlush() {
			cStop = true;
		}
	}
}
