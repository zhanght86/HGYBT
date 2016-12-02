package com.sinosoft.midplat.newccb.security;

public class SecurityPolicy {
	private boolean isMac;
	private boolean isContext;
	private boolean isEnc;

	public boolean isMac() {
		return this.isMac;
	}

	public void setMac(boolean isMac) {
		this.isMac = isMac;
	}

	public boolean isContext() {
		return this.isContext;
	}

	public void setContext(boolean isContext) {
		this.isContext = isContext;
	}

	public boolean isEnc() {
		return this.isEnc;
	}

	public void setEnc(boolean isEnc) {
		this.isEnc = isEnc;
	}
}
