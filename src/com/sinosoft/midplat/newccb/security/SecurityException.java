package com.sinosoft.midplat.newccb.security;

public class SecurityException extends CommonRuntimeException {
	private static final long serialVersionUID = 8856785523442969925L;

	public SecurityException(String code, Throwable e, Object[] params) {
		super(code, e, params);
	}

	public SecurityException(String code, Object[] params) {
		this(code, null, params);
	}
}