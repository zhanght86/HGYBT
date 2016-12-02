package com.sinosoft.midplat.newccb.security;

public class SecurityMessageHeader {
	private boolean isEnc;
	private boolean isContext;
	private boolean isMac;
	private String serviceId;
	private String serviceType;
	private String secNodeId;
	private String rmtSecNodeId;
	private String evtTraceId;
	private String secContext;
	private String respCode;
	private String secErrorCode;
	private String sign;

	public boolean isEnc() {
		return this.isEnc;
	}

	public void setEnc(boolean isEnc) {
		this.isEnc = isEnc;
	}

	public boolean isContext() {
		return this.isContext;
	}

	public void setContext(boolean isContext) {
		this.isContext = isContext;
	}

	public boolean isMac() {
		return this.isMac;
	}

	public void setMac(boolean isMac) {
		this.isMac = isMac;
	}

	public String getServiceId() {
		return this.serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getSecNodeId() {
		return this.secNodeId;
	}

	public void setSecNodeId(String secNodeId) {
		this.secNodeId = secNodeId;
	}

	public String getRmtSecNodeId() {
		return this.rmtSecNodeId;
	}

	public void setRmtSecNodeId(String rmtSecNodeId) {
		this.rmtSecNodeId = rmtSecNodeId;
	}

	public String getEvtTraceId() {
		return this.evtTraceId;
	}

	public void setEvtTraceId(String evtTraceId) {
		this.evtTraceId = evtTraceId;
	}

	public String getSecContext() {
		return this.secContext;
	}

	public void setSecContext(String secContext) {
		this.secContext = secContext;
	}

	public String getRespCode() {
		return this.respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getSecErrorCode() {
		return this.secErrorCode;
	}

	public void setSecErrorCode(String secErrorCode) {
		this.secErrorCode = secErrorCode;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String toString() {
		return "serviceId=" + this.serviceId + '\n' + "serviceType="
				+ this.serviceType + '\n' + "secNodeId=" + this.secNodeId
				+ '\n' + "rmtSecNodeId=" + this.rmtSecNodeId + '\n'
				+ "evtTraceId=" + this.evtTraceId + '\n' + "secContext="
				+ this.secContext + '\n' + "respCode=" + this.respCode + '\n'
				+ "secErrorCode=" + this.secErrorCode + '\n' + "sign="
				+ this.sign + '\n';
	}
}
