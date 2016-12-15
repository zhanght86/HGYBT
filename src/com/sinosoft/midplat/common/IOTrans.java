package com.sinosoft.midplat.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;

/**
 * 输入输出转换
 * @author yuantongxin
 */
public class IOTrans {
	/**
	 * 流转换为字节数组，关闭流
	 */
	public static byte[] toBytes(InputStream pIns) {
		ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();
		
		try {
			byte[] tBytes = new byte[8*1024];
			for (int tReadSize; -1 != (tReadSize=pIns.read(tBytes)); ) {
				mByteArrayOutputStream.write(tBytes, 0, tReadSize);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			try {
				pIns.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return mByteArrayOutputStream.toByteArray();
	}
	
	/**
	 * 不关闭流
	 */
	public static byte[] toBytes5Close(InputStream pIns) {
		ByteArrayOutputStream mByteArrayOutputStream = new ByteArrayOutputStream();
		
		try {
			byte[] tBytes = new byte[8*1024];
			for (int tReadSize; -1 != (tReadSize=pIns.read(tBytes)); ) {
				mByteArrayOutputStream.write(tBytes, 0, tReadSize);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		
		return mByteArrayOutputStream.toByteArray();
	}
	
	/**
	 * 输出流转输入流，关闭输入流，会自动刷新输出流但并不关闭。
	 */
	public static void in2out(InputStream pIns, OutputStream pOuts) throws IOException {
		byte[] mBytes = new byte[8*1024];
		for (int tReadSize; -1 != (tReadSize=pIns.read(mBytes)); ) {
			pOuts.write(mBytes, 0, tReadSize);
		}
		pOuts.flush();
		pIns.close();
	}
	
	/**
	 * 从输入字节流中读取数据，填满传入的字节数组，不关闭流
	 */
	public static void readFull(byte[] pByte, InputStream pIs) throws IOException {
		for (int tReadSize = 0; tReadSize < pByte.length;) {
			//从输入流读取转换为字节数组数据达到字节数组长度个字节，返回读入缓冲区的总字节数
			int tRead = pIs.read(pByte, tReadSize, pByte.length-tReadSize);
			//如果没有更多的数据，因为数据流的末尾已到达返回-1
			if (-1 == tRead) {
				//抛出输入输出异常:读取数据出错！实际读入长度：读取大小
				throw new IOException("读取数据出错！实际读入长度：" + tReadSize);
			}
			//将读取大小与读入缓冲区的总字节数相加，并将结果赋给读取大小
			tReadSize += tRead;
		}
	}
	
	/**
	 * 将文件(pSrcPath)拷贝到指定目录(pDestDir)，文件名不变。
	 * 若目标输出目录不存在，会试图自动创建；若目标文件已存在，将抛IOException。
	 */
	public static void fileCopy(String pSrcPath, String pDestDir) throws IOException {
		fileCopy(new File(pSrcPath), new File(pDestDir));
	}

	/**
	 * 将文件(pSrcFile)拷贝到指定目录(pDestDir)，文件名不变。
	 * 若目标输出目录不存在，会试图自动创建；若目标文件已存在，将抛IOException。
	 */
	public static void fileCopy(File pSrcFile, File pDestDir) throws IOException {
		pDestDir.mkdirs();
		if (!pDestDir.exists()) {	//目标目录不存在
			throw new IOException("目标目录不存在，试图创建失败！" + pDestDir);
		}
		File mDestFile = new File(pDestDir, pSrcFile.getName());
		if (mDestFile.exists()) {	//目标文件已存在
			throw new IOException("目标文件已存在！" + mDestFile);
		}
		
		InputStream mSrcIs = new FileInputStream(pSrcFile);
		OutputStream mDestOs = new FileOutputStream(mDestFile);
		in2out(mSrcIs, mDestOs);
		mDestOs.close();
	}
	
	/**
	 * 将指定文件(pSrcPath)移到指定目录(pDestDir)，文件名不变，原文件删除。
	 * 若目标输出目录不存在，会试图自动创建；若目标文件已存在，将抛IOException。
	 */
	public static void fileMove(String pSrcPath, String pDestDir) throws IOException {
		fileMove(new File(pSrcPath), new File(pDestDir));
	}
	
	/**
	 * 将指定文件(pSrcFile)移到指定目录(pDestDir)，文件名不变，原文件删除。
	 * 若目标输出目录不存在，会试图自动创建；若目标文件已存在，将抛IOException。
	 */
	public static void fileMove(File pSrcFile, File pDestDir) throws IOException {
		pDestDir.mkdirs();
		if (!pDestDir.exists()) {	//目标目录不存在
			throw new IOException("目标目录不存在，试图创建失败！" + pDestDir);
		}
		File mDestFile = new File(pDestDir, pSrcFile.getName());
		if (mDestFile.exists()) {	//目标文件已存在
			throw new IOException("目标文件已存在！" + mDestFile);
		}
		
		if (!pSrcFile.renameTo(mDestFile)) {	//移动文件失败
			throw new IOException("移动文件失败！");
		}
	}
	
	public static String toString(Reader pReader) {
		StringWriter mStringWriter = new StringWriter();
		
		try {
			char[] tChars = new char[4*1024];
			for (int tReadSize; -1 != (tReadSize=pReader.read(tChars)); ) {
				mStringWriter.write(tChars, 0, tReadSize);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		} finally {
			try {
				pReader.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return mStringWriter.toString();
	}
}
