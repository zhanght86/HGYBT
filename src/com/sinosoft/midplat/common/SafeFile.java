package com.sinosoft.midplat.common;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;

public class SafeFile {
	private static HashMap<String,SafeFile> cSafeFileMap = new HashMap<String,SafeFile>();
	
	private final File cSrcFile;
	private final File cBakFile;
	
	private boolean cIsClosed = true;
	
	private SafeFile(File pFile) {
		cSrcFile = pFile;
		cBakFile = new File(cSrcFile.getParentFile(), cSrcFile.getName()+".sysbak");
	}
	
	public static SafeFile newInstance(String pPath) {
		return newInstance(new File(pPath));
	}
	
	public synchronized static SafeFile newInstance(File pFile) {
		if (!pFile.exists()) {
			return null;
		}
		String mAbsolutePath = pFile.getAbsolutePath();
		SafeFile mSafeFile = cSafeFileMap.get(mAbsolutePath);
		if (null == mSafeFile) {
			mSafeFile = new SafeFile(pFile);
			cSafeFileMap.put(mAbsolutePath, mSafeFile);
		}
		return mSafeFile;
	}
	
	public InputStream getInputStream() throws IOException {
		if (!cIsClosed) {
			throw new IOException("文件挂起，可能别处正在操作，请稍后！" + cSrcFile);
		}
		
		/**
		 *  系统异常停止时，还原文件到之前版本
		 */
		if (cBakFile.exists()) {
			cSrcFile.delete();
			if (!cBakFile.renameTo(cSrcFile)) {
				throw new IOException("恢复文件失败！" + cBakFile);
			}
		}
		
		return new SafeFis(cSrcFile);
	}
	
	public OutputStream getOutputStream() throws IOException {
		if (!cIsClosed) {
			throw new IOException("文件挂起，可能别处正在操作，请稍后！" + cSrcFile);
		}
		if (!cSrcFile.renameTo(cBakFile)) {
			throw new IOException("备份文件失败！" + cSrcFile);
		}
		cIsClosed = false;
		return new SafeFos(cSrcFile);
	}
	
	private class SafeFis extends FileInputStream {
		public SafeFis(File pFile) throws IOException {
			super(pFile);
		}
		
		public void close() throws IOException {
			super.close();
			cIsClosed = true;
		}
	}
	
	private class SafeFos extends FileOutputStream {
		public SafeFos(File pFile) throws IOException {
			super(pFile);
		}
		
		public void close() throws IOException {
			super.close();
			cBakFile.delete();
			if (cBakFile.exists()) {
				throw new IOException("删除备份文件失败！" + cBakFile);
			}
			cIsClosed = true;
		}
	}
}
