package com.sinosoft.midplat.common;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

public class RollFileReader {
	private final File cFile;
	private final String cCharset;
	private final int cCache;
	
	private final BufferedReader cBufReader;
	
	public RollFileReader(String pPath) throws IOException {
		this(pPath, 512000, System.getProperty("file.encoding", "GBK"));	//500K
	}
	
	public RollFileReader(String pPath, int pCache) throws IOException {
		this(pPath, pCache, System.getProperty("file.encoding", "GBK"));
	}
	
	public RollFileReader(String pPath, String pCharset) throws IOException {
		this(pPath, 512000, pCharset);	//500K
	}
	
	public RollFileReader(String pPath, int pCache, String pCharset) throws IOException {
		this(new File(pPath), pCache, pCharset);
	}
	
	public RollFileReader(File pFile) throws IOException {
		this(pFile, 512000, System.getProperty("file.encoding", "GBK"));
	}
	
	public RollFileReader(File pFile, int pCache) throws IOException {
		this(pFile, pCache, System.getProperty("file.encoding", "GBK"));
	}
	
	public RollFileReader(File pFile, String pCharset) throws IOException {
		this(pFile, 512000, pCharset);	//500K
	}
	
	public RollFileReader(File pFile, int pCache, String pCharset) throws IOException {
		cFile = pFile;
		cCharset = pCharset;
		cCache = pCache;
		
		long mFileLen = cFile.length();
		int mReadSize = (int) (mFileLen>pCache ? pCache:mFileLen);
		RandomAccessFile mRandomAccessFile = new RandomAccessFile(cFile, "r");
		mRandomAccessFile.seek(mFileLen-mReadSize);
		byte[] mBytes = new byte[mReadSize];
		for (int tReadSize = 0; tReadSize < mBytes.length;) {
			int tRead = mRandomAccessFile.read(mBytes, tReadSize, mBytes.length-tReadSize);
			if (-1 == tRead) {
				break;
			}
			tReadSize += tRead;
		}
		mRandomAccessFile.close();
		cBufReader = new BufferedReader(
				new InputStreamReader(
						new ByteArrayInputStream(mBytes), pCharset));
		cBufReader.readLine();	//第一行可能不完整，跳过
	}
	
	public String nextLine() {
		try {
			return cBufReader.readLine();
		} catch (IOException ex) {
			return null;
		}
	}
	
	public File getFile() {
		return cFile;
	}
	
	public String getCharset() {
		return cCharset;
	}
	
	public int getCache() {
		return cCache;
	}
}
