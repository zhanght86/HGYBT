package com.sinosoft.midplat.newccb.security;

import java.util.List;
import java.util.Locale;
import org.springframework.util.StringUtils;

public class CommonRuntimeException extends RuntimeException
{
  private static final long serialVersionUID = 6326450420472654286L;
  //private static final Log log = LogFactory.getLog(CommonRuntimeException.class);
  public static final String log001 = "无法找到异常信息，code={0}，locale={1}";
  private String code;
  private Object[] params;

  public CommonRuntimeException(String code, Throwable e, List<Locale> locales, Object[] params)
  {
    super(e);
    this.code = code;
    this.params = params;
  }

  public CommonRuntimeException(String code, Throwable e, Object[] params) {
    this(code, e, null, params);
  }

  public CommonRuntimeException(String code, Object[] params) {
    this(code, null, params);
  }

  public CommonRuntimeException(Throwable e) {
    this(null, e, new Object[0]);
  }

  public CommonRuntimeException()
  {
  }

  public String getMessage() {
    return assembleMessage(code);
  }

  private String assembleMessage(String exceptionMessage) {
    return new StringBuilder("[ERRORCODE=").append(getCode()).append("] [").append(StringUtils.hasText(exceptionMessage) ? exceptionMessage : "").append(']').toString();
  }

  public String getCode()
  {
    return this.code;
  }

  public Object[] getParameters()
  {
    return this.params;
  }

}