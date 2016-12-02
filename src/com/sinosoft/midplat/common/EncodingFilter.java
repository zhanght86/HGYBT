package com.sinosoft.midplat.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter {
	private String cEncoding = "GBK";
	
	public void destroy() {}
	
	public void doFilter(ServletRequest cRequest, ServletResponse cResponse,
			FilterChain pChain) throws IOException, ServletException {
		cRequest.setCharacterEncoding(cEncoding);
		pChain.doFilter(cRequest, cResponse);
	}
	
	public void init(FilterConfig pConfig) throws ServletException {
		String mEncoding = pConfig.getInitParameter("encoding");
		if (null!=mEncoding && !"".equals(mEncoding)) {
			cEncoding = mEncoding;
		}
	}
}
