package com.sinosoft.midplat.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.jdom.Document;

import com.sinosoft.utility.StrTool;

public class StringTool{
	
public static String toHTMLValueFormat(String strInValue) {
		
		StringBuffer strOutValue = new StringBuffer("");
		
		if (strInValue != null) {
			char c;
			for (int i = 0; i < strInValue.length(); i++) {
				
				c = strInValue.charAt(i);
				switch (c) {
					case '\'':
						strOutValue.append("\\¡¯");
						break;
					
					case '"':
						strOutValue.append("&quot;");
						break;
						
					case '\\':
						strOutValue.append("\\\\");
						break;
					
					case '\n':
						strOutValue.append("<br>");
						break;
					case '&':
						strOutValue.append("&amp;");
						break;
					case '\r':
						break;
					case ' ':
						strOutValue.append("&emsp;");
						break;
					case '<':
						strOutValue.append("&lt;");
						break;
					case '>':
						strOutValue.append("&gt;");
						break;
						
					default:
						strOutValue.append(c);
						break;
				}
			}
		}
		
		return strOutValue.toString();
	}
	
	
	  /**
     * ²âÊÔº¯Êý
     * @param args String[]
     */
//    public static void main(String[] args)
//    {
//    	String mInFilePathName = "C:\\Users\\asus\\Desktop\\1.xml";
//		
//		InputStream mIs;
//		try {
//			mIs = new FileInputStream(mInFilePathName);
//			Document document = JdomUtil.build(mIs);
//			String ss = JdomUtil.toStringFmt(document);
//			String s = StrTool.toHTMLValueFormat(ss);
//			System.out.println(s);
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
    }