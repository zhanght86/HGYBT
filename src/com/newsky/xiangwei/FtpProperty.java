package com.newsky.xiangwei;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import com.sinosoft.midplat.common.SysInfo;
/**
 * �ļ�����Э��������
 * @author yuantongxin
 */
public class FtpProperty {
	String propertyFileName = null;//�����ļ���
	String fileName = "ftpProperty.xml";//�ļ���
	private String filePath = SysInfo.cHome;//�ļ�·��

	Properties pps;

	public FtpProperty() {
//		filePath = System.getenv("NCPAI_FTP_XML_PATH");
//		if (filePath == null) {
//			File fi = new File("");
//			filePath = fi.getAbsolutePath();
//		}
		StringBuffer sb = new StringBuffer();
		sb.append(filePath);
		sb.append("/conf/");
		sb.append(fileName);
		propertyFileName = sb.toString();
		pps = new Properties();
		loadProperty();
	}

	public void loadProperty() {
		try {
			pps.loadFromXML(new FileInputStream(propertyFileName));
		} catch (InvalidPropertiesFormatException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void displayProperty() {
		pps.list(System.out);
	}

	/**
	 * @param args
	 * @param
	 * @return
	 */
	public static void main(String[] args) {
		FtpProperty fp = new FtpProperty();
		fp.displayProperty();
		System.out.println(fp.pps.getProperty("ncpai-ip"));
		File fi = new File("");
		try {
			System.out.println(fi.getCanonicalPath());
			System.out.println(fi.getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
