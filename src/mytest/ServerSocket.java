package mytest;

import java.io.IOException;

import com.sinosoft.midplat.common.IOTrans;

public class ServerSocket {
		public static void main(String[] args) {
			  try {
				java.net.ServerSocket serverSocket=new java.net.ServerSocket(8888);
			    java.net.Socket socket=serverSocket.accept();
			    System.out.println(new String(IOTrans.toBytes(socket.getInputStream())));
			    socket.getOutputStream().write("·µ»ØÊý¾Ý".getBytes());
			    socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
}
