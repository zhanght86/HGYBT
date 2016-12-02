package mytest;

import java.io.IOException;
import java.net.UnknownHostException;

import com.sinosoft.midplat.common.IOTrans;

public class Socket {
    public static void main(String[] args) {
    	try {
			java.net.Socket socket=new java.net.Socket("127.0.0.1",8888);
			socket.getOutputStream().write("ÇëÇóÊý¾Ý".getBytes());
			socket.getOutputStream().flush();
			System.out.println(new String(IOTrans.toBytes(socket.getInputStream())));
			socket.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}