package com.sinosoft.midplat.jhyh;

import java.net.Socket;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.midplat.Ybt4Socket;
import com.sinosoft.midplat.common.DateUtil;

public class JhyhYbt extends Ybt4Socket {
	public JhyhYbt(Socket pSocket) throws Exception {
		super(pSocket, JhyhConf.newInstance());
	}
}
