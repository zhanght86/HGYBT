package com.sinosoft.midplat.gzbank.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Testabc {
	
	public static void main(String[] args) {
	    try {
			FileReader reader=new FileReader("c://users/anico/desktop/middlexml.txt");
			FileWriter writer=new FileWriter("c://users/anico/desktop/AAAAA.txt",false);
			BufferedReader bufferedReader=new BufferedReader(reader);
			BufferedWriter bufferedWriter=new BufferedWriter(writer);
			String data=null;
			while((data=bufferedReader.readLine())!=null){
				bufferedWriter.write("<"+data+"></"+data+">\r\n");
			}
			bufferedReader.close();
			reader.close();
			bufferedWriter.close();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
