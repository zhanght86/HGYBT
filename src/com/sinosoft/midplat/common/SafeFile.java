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
			throw new IOException("�ļ����𣬿��ܱ����ڲ��������Ժ�" + cSrcFile);
		}
		
		/**
		 *  ϵͳ�쳣ֹͣʱ����ԭ�ļ���֮ǰ�汾
		 */
		if (cBakFile.exists()) {
			cSrcFile.delete();
			if (!cBakFile.renameTo(cSrcFile)) {
				throw new IOException("�ָ��ļ�ʧ�ܣ�" + cBakFile);
			}
		}
		
		return new SafeFis(cSrcFile);
	}
	
	public OutputStream getOutputStream() throws IOException {
		if (!cIsClosed) {
			throw new IOException("�ļ����𣬿��ܱ����ڲ��������Ժ�" + cSrcFile);
		}
		if (!cSrcFile.renameTo(cBakFile)) {
			throw new IOException("�����ļ�ʧ�ܣ�" + cSrcFile);
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
				throw new IOException("ɾ�������ļ�ʧ�ܣ�" + cBakFile);
			}
			cIsClosed = true;
		}
	}
}
