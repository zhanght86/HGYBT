package com.sinosoft.midplat.spdb;

import java.net.Socket;

import com.sinosoft.midplat.Ybt4Socket;

public class SpdbYbt extends Ybt4Socket {

	public SpdbYbt(Socket pSocket) throws Exception {
		super(pSocket, SpdbConf.newInstance());
	}

}
