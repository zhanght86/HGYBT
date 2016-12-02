package com.sinosoft.midplat.newabc;

import java.net.Socket;

import com.sinosoft.midplat.Ybt4Socket;

public class NewAbcYbt extends Ybt4Socket {
	public NewAbcYbt(Socket pSocket) throws Exception {
		super(pSocket, NewAbcConf.newInstance());
	}
}
