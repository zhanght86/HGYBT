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
 * �������ת��
 * @author yuantongxin
 */
public class IOTrans {
	/**
	 * ��ת��Ϊ�ֽ����飬�ر���
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
	 * ���ر���
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
	 * �����ת���������ر������������Զ�ˢ��������������رա�
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
	 * ��ָ�����ж�ȡ���ݣ�����������ֽ����飬���ر���
	 */
	public static void readFull(byte[] pByte, InputStream pIs) throws IOException {
		for (int tReadSize = 0; tReadSize < pByte.length;) {
			int tRead = pIs.read(pByte, tReadSize, pByte.length-tReadSize);
			if (-1 == tRead) {
				throw new IOException("��ȡ���ݳ���ʵ�ʶ��볤�ȣ�" + tReadSize);
			}
			tReadSize += tRead;
		}
	}
	
	/**
	 * ���ļ�(pSrcPath)������ָ��Ŀ¼(pDestDir)���ļ������䡣
	 * ��Ŀ�����Ŀ¼�����ڣ�����ͼ�Զ���������Ŀ���ļ��Ѵ��ڣ�����IOException��
	 */
	public static void fileCopy(String pSrcPath, String pDestDir) throws IOException {
		fileCopy(new File(pSrcPath), new File(pDestDir));
	}

	/**
	 * ���ļ�(pSrcFile)������ָ��Ŀ¼(pDestDir)���ļ������䡣
	 * ��Ŀ�����Ŀ¼�����ڣ�����ͼ�Զ���������Ŀ���ļ��Ѵ��ڣ�����IOException��
	 */
	public static void fileCopy(File pSrcFile, File pDestDir) throws IOException {
		pDestDir.mkdirs();
		if (!pDestDir.exists()) {	//Ŀ��Ŀ¼������
			throw new IOException("Ŀ��Ŀ¼�����ڣ���ͼ����ʧ�ܣ�" + pDestDir);
		}
		File mDestFile = new File(pDestDir, pSrcFile.getName());
		if (mDestFile.exists()) {	//Ŀ���ļ��Ѵ���
			throw new IOException("Ŀ���ļ��Ѵ��ڣ�" + mDestFile);
		}
		
		InputStream mSrcIs = new FileInputStream(pSrcFile);
		OutputStream mDestOs = new FileOutputStream(mDestFile);
		in2out(mSrcIs, mDestOs);
		mDestOs.close();
	}
	
	/**
	 * ��ָ���ļ�(pSrcPath)�Ƶ�ָ��Ŀ¼(pDestDir)���ļ������䣬ԭ�ļ�ɾ����
	 * ��Ŀ�����Ŀ¼�����ڣ�����ͼ�Զ���������Ŀ���ļ��Ѵ��ڣ�����IOException��
	 */
	public static void fileMove(String pSrcPath, String pDestDir) throws IOException {
		fileMove(new File(pSrcPath), new File(pDestDir));
	}
	
	/**
	 * ��ָ���ļ�(pSrcFile)�Ƶ�ָ��Ŀ¼(pDestDir)���ļ������䣬ԭ�ļ�ɾ����
	 * ��Ŀ�����Ŀ¼�����ڣ�����ͼ�Զ���������Ŀ���ļ��Ѵ��ڣ�����IOException��
	 */
	public static void fileMove(File pSrcFile, File pDestDir) throws IOException {
		pDestDir.mkdirs();
		if (!pDestDir.exists()) {	//Ŀ��Ŀ¼������
			throw new IOException("Ŀ��Ŀ¼�����ڣ���ͼ����ʧ�ܣ�" + pDestDir);
		}
		File mDestFile = new File(pDestDir, pSrcFile.getName());
		if (mDestFile.exists()) {	//Ŀ���ļ��Ѵ���
			throw new IOException("Ŀ���ļ��Ѵ��ڣ�" + mDestFile);
		}
		
		if (!pSrcFile.renameTo(mDestFile)) {	//�ƶ��ļ�ʧ��
			throw new IOException("�ƶ��ļ�ʧ�ܣ�");
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
