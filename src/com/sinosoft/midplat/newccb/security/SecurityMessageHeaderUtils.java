package com.sinosoft.midplat.newccb.security;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cn.ccb.secapi.SecAPI;
import cn.ccb.secapi.SecException;

public class SecurityMessageHeaderUtils
{
	// private static final Log log =
	// LogFactory.getLog(SecurityMessageHeaderUtils.class);
	public static final String log001 = "接受并处理后的安全报文头信息=\n{0}";
	public static final String log002 = "服务{0}远程安全节点{1}调用安全出错,错误码为{2}";
	public static final String log003 = "服务{0}远程安全节点{1}发ꀨϷ求时调用安全出锿错误码为{2}";
	public static final String log004 = "服务{0}远程安全节点{1}响应时调用安全出锿错误码为{2}";
	public static final String log005 = "发ꀥΉ全报文头信恿\n{0}";
	protected static final String SEP = ":";
	protected static final byte[] SEP_BYTES = ":".getBytes(SecurityManagerUtils.CHARSET);
	protected static final byte SEP_BEGIN = SEP_BYTES[0];
	protected static final String SEP2 = "\r\n";
	protected static final byte[] SEP2_BYTES = "\r\n".getBytes(SecurityManagerUtils.CHARSET);
	protected static final byte SEP2_BEGIN = SEP2_BYTES[0];
	protected static final String SEC_ERROR_CODE = "SEC_ERROR_CODE";
	protected static final byte[] SEC_ERROR_CODE_BYTES = "SEC_ERROR_CODE".getBytes(SecurityManagerUtils.CHARSET);
	protected static final String SEC_RESP_CODE = "SEC_RESP_CODE";
	protected static final byte[] SEC_RESP_CODE_BYTES = "SEC_RESP_CODE".getBytes(SecurityManagerUtils.CHARSET);
	protected static final String SEC_IS_MAC = "SEC_IS_MAC";
	protected static final byte[] SEC_IS_MAC_BYTES = "SEC_IS_MAC".getBytes(SecurityManagerUtils.CHARSET);
	protected static final String SEC_IS_CONTEXT = "SEC_IS_CONTEXT";
	protected static final byte[] SEC_IS_CONTEXT_BYTES = "SEC_IS_CONTEXT".getBytes(SecurityManagerUtils.CHARSET);
	protected static final String SEC_IS_ENC = "SEC_IS_ENC";
	protected static final byte[] SEC_IS_ENC_BYTES = "SEC_IS_ENC".getBytes(SecurityManagerUtils.CHARSET);
	protected static final String SEC_MAC = "SEC_MAC";
	protected static final byte[] SEC_MAC_BYTES = "SEC_MAC".getBytes(SecurityManagerUtils.CHARSET);
	protected static final String SEC_CONTEXT = "SEC_CONTEXT";
	protected static final byte[] SEC_CONTEXT_BYTES = "SEC_CONTEXT".getBytes(SecurityManagerUtils.CHARSET);
	protected static final String SEC_ID1 = "SEC_ID1";
	protected static final byte[] SEC_ID1_BYTES = "SEC_ID1".getBytes(SecurityManagerUtils.CHARSET);
	protected static final String SEC_ID2 = "SEC_ID2";
	protected static final byte[] SEC_ID2_BYTES = "SEC_ID2".getBytes(SecurityManagerUtils.CHARSET);
	protected static final String SEC_TRACE_ID = "SEC_TRACE_ID";
	protected static final byte[] SEC_TRACE_ID_BYTES = "SEC_TRACE_ID".getBytes(SecurityManagerUtils.CHARSET);
	protected static final String SEC_TX_CODE = "SEC_TX_CODE";
	protected static final byte[] SEC_TX_CODE_BYTES = "SEC_TX_CODE".getBytes(SecurityManagerUtils.CHARSET);
	protected static final String SEC_TX_TYPE = "SEC_TX_TYPE";
	protected static final byte[] SEC_TX_TYPE_BYTES = "SEC_TX_TYPE".getBytes(SecurityManagerUtils.CHARSET);
	protected static final String SEC_LEN = "SEC_LEN";
	protected static final byte[] SEC_LEN_BYTES = "SEC_LEN".getBytes(SecurityManagerUtils.CHARSET);
	protected static final String FALSE_FLAG = "0";
	protected static final byte[] FALSE_FLAG_BYTES = "0".getBytes(SecurityManagerUtils.CHARSET);
	protected static final String TRUE_FLAG = "1";
	protected static final byte[] TRUE_FLAG_BYTES = "1".getBytes(SecurityManagerUtils.CHARSET);
	protected static final String SEC_ERROR_CODE_SUCCESS = "000000000000";

	private static String mSecNodeId = null;
	private static String mRmtSecNodeId = null;

	protected static final String SEC_SIGN = "SEC_SIGN";
	protected static final byte[] SEC_SIGN_BYTES = "SEC_SIGN".getBytes(SecurityManagerUtils.CHARSET);;

	public static byte[] marshal(SecurityMessageHeader securityMessageHeader, byte[] messageData, boolean isClient)
	{
		// log.debug("log001", new Object[] { securityMessageHeader });

		String serviceId = securityMessageHeader.getServiceId();
		String serviceType = securityMessageHeader.getServiceType();
		String secNodeId = securityMessageHeader.getSecNodeId();
		String rmtSecNodeId = securityMessageHeader.getRmtSecNodeId();
		String evtTraceId = securityMessageHeader.getEvtTraceId();
		String respCode = securityMessageHeader.getRespCode();
		String secErrorCode = securityMessageHeader.getSecErrorCode();

		String sign = securityMessageHeader.getSign();
		if (StringUtils.isEmpty(sign))
		{
			sign = null;
		}

		String secLen = null;
		byte[] isContextFlag = FALSE_FLAG_BYTES;
		byte[] secContext = new byte[0];

		byte[] isMacFlag = FALSE_FLAG_BYTES;
		byte[] mac = new byte[0];

		byte[] isEncFlag = FALSE_FLAG_BYTES;

		if (messageData == null)
		{
			messageData = new byte[0];
		}

		if ((StringUtils.isEmpty(secErrorCode)) || (StringUtils.equalsIgnoreCase("000000000000", secErrorCode)))
		{
			try
			{

				// 获取安全策略
				SecurityPolicy securityPolicy = SecurityManagerUtils.getSecPolicy(secNodeId, rmtSecNodeId);
				boolean isMac = securityPolicy.isMac();
				boolean isContext = securityPolicy.isContext();
				boolean isEnc = securityPolicy.isEnc();
				securityMessageHeader.setMac(isMac);
				securityMessageHeader.setContext(isContext);
				securityMessageHeader.setEnc(isEnc);

				// �Ƿ��ð�ȫ�����ģ��Լ���ɰ�ȫ�����￿
				if (isContext)
				{
					isContextFlag = TRUE_FLAG_BYTES;
					if (isClient)
					{
						secContext = SecurityManagerUtils.genSecContext(secNodeId, rmtSecNodeId, evtTraceId, securityMessageHeader.getSecContext()).getBytes(SecurityManagerUtils.CHARSET);
					}

				}

				// 是否报文增加数字验签
				if (!"".equals(sign) && sign != null)
				{
					sign = new String(SecurityManagerUtils.genSign(secNodeId, messageData));
				}

				// 是否加密，以及报文加密ꀨ޿
				if (isEnc)
				{
					isEncFlag = TRUE_FLAG_BYTES;
					messageData = SecurityManagerUtils.pkgEncrypt(secNodeId, rmtSecNodeId, messageData);
				}

				secLen = StringUtils.leftPad(messageData.length + "", 6, '0');

				// �Ƿ�MacУ�飬�����MacУ����
				if (isMac)
				{
					isMacFlag = TRUE_FLAG_BYTES;
					mac = SecurityManagerUtils.mac(secNodeId, rmtSecNodeId, messageData);
				}
			}
			catch (Throwable t)
			{
				if (isClient)
				{
					// log.error("log003", t, new Object[] { serviceId,
					// rmtSecNodeId, t.getMessage() });
					if ((t instanceof CommonRuntimeException))
					{
						throw ((CommonRuntimeException) t);
					}
					throw new SecurityException("XSSF301500AC", t, new Object[] { t.getLocalizedMessage() });
				}

				// secErrorCode = SwapAreaUtils.getSecErrorCode();
				if (StringUtils.isEmpty(secErrorCode))
					secErrorCode = "XSSF301500AC";
				// log.error("log004", t, new Object[] { serviceId,
				// rmtSecNodeId, secErrorCode });
			}
			finally
			{
				// SwapAreaUtils.setSecErrorCode(null);
			}
		}
		else if (!isClient)
		{
			if (securityMessageHeader.isContext())
				isContextFlag = TRUE_FLAG_BYTES;
			else
			{
				isContextFlag = FALSE_FLAG_BYTES;
			}

			if (securityMessageHeader.isMac())
				isMacFlag = TRUE_FLAG_BYTES;
			else
			{
				isMacFlag = FALSE_FLAG_BYTES;
			}

			if (securityMessageHeader.isEnc())
				isEncFlag = TRUE_FLAG_BYTES;
			else
			{
				isEncFlag = FALSE_FLAG_BYTES;
			}

		}

		if (StringUtils.isEmpty(secErrorCode))
		{
			secErrorCode = "000000000000";
		}

		if (secLen == null)
		{
			secLen = StringUtils.leftPad(messageData.length + "", 6, '0');
		}

		ByteArrayOutputStream os = null;
		try
		{
			// ����ͷ�ֽ���
			os = new ByteArrayOutputStream();

			outputHeader(SEC_ERROR_CODE_BYTES, secErrorCode, os);
			outputHeader(SEC_IS_MAC_BYTES, isMacFlag, os);
			outputHeader(SEC_IS_CONTEXT_BYTES, isContextFlag, os);
			outputHeader(SEC_IS_ENC_BYTES, isEncFlag, os);
			outputHeader(SEC_MAC_BYTES, mac, os);
			outputHeader(SEC_CONTEXT_BYTES, secContext, os);
			outputHeader(SEC_ID1_BYTES, secNodeId, os);
			outputHeader(SEC_ID2_BYTES, rmtSecNodeId, os);
			outputHeader(SEC_TRACE_ID_BYTES, evtTraceId, os);
			outputHeader(SEC_TX_CODE_BYTES, serviceId, os);
			outputHeader(SEC_TX_TYPE_BYTES, serviceType, os);
			outputHeader(SEC_RESP_CODE_BYTES, respCode, os);
			outputHeader(SEC_LEN_BYTES, secLen, os);

			if (null != sign)
			{
				outputHeader(SEC_SIGN_BYTES, sign, os);
			}

			os.write(SEP2_BYTES);

			if (messageData != null)
				os.write(messageData);
			// log.debug("log005", new Object[] { new String(os.toByteArray())
			// });

			// 安全组件逿Ǻ
			// System.out.println("逿Ǻ安全节点号为＿+secNodeId);
			// SecAPI.nodeFinal(secNodeId);
			// System.out.println("逿Ǻ安全节点完成");
		}
		catch (Throwable t)
		{
			throw new SecurityException("XSSF301500AD", t, new Object[0]);
		}

		return os.toByteArray();
	}

	public static Object[] ummarshal(byte[] message, boolean isServer, SecurityMessageHeader securityMessageHeader)
	{
		// 返回结果数组对象＿为安全头＿为报文体
		Object[] retObjs = new Object[2];
		retObjs[0] = securityMessageHeader;
		retObjs[1] = new byte[0];

		if ((message == null) || (message.length == 0))
		{
			return retObjs;
		}

		byte[] messageBody = new byte[0];
		String secNodeId = null;
		String rmtSecNodeId = null;
		String secErrorCode = null;
		boolean isMac = false;
		boolean isContext = false;
		boolean isEnc = false;
		String secContext = null;
		String evtTraceId = null;
		String sign = null;

		int messageLength = message.length;
		Map<String, byte[]> container = new HashMap<String, byte[]>();
		List<Byte> content = new ArrayList();
		int contentLength = 0;
		byte[] key = null;
		byte[] value = null;
		int i = 0;
		try
		{
			for (; i < messageLength; i++)
			{
				if (message[i] == SEP_BEGIN)
				{
					contentLength = content.size();
					key = new byte[contentLength];
					for (int j = 0; j < contentLength; j++)
					{
						key[j] = ((Byte) content.get(j)).byteValue();
					}
					content = new ArrayList();
				}
				else if (message[i] == SEP2_BEGIN)
				{
					if (i + 2 <= messageLength)
					{
						contentLength = content.size();
						value = new byte[contentLength];
						for (int k = 0; k < contentLength; k++)
						{
							value[k] = ((Byte) content.get(k)).byteValue();
						}
						container.put(new String(key, SecurityManagerUtils.CHARSET), value);
						if ((i + 2 < messageLength) && (message[(i + 2)] == SEP2_BEGIN))
						{
							i += 4;
							break;
						}
						content = new ArrayList();

						i++;
					}
					else
					{
						throw new SecurityException("XSSF301500AE", new Object[0]);
					}
				}
				else
				{
					content.add(Byte.valueOf(message[i]));
				}
			}

			if (i < messageLength)
			{
				messageBody = new byte[messageLength - i];
				for (int l = 0; i < messageLength; l++)
				{
					messageBody[l] = message[i];

					i++;
				}

			}

			// SecAPI.nodeInit(rmtSecNodeId);
			secNodeId = getStringHeader("SEC_ID2", container);
			rmtSecNodeId = getStringHeader("SEC_ID1", container);
			secContext = getStringHeader("SEC_CONTEXT", container);
			evtTraceId = getStringHeader("SEC_TRACE_ID", container);
			sign = getStringHeader("SEC_SIGN", container);
			System.out.println("secNodeId=============" + secNodeId);
			System.out.println("rmtSecNodeId=============" + rmtSecNodeId);
			System.out.println("Library 路径" + System.getProperty("java.library.path"));
			
			try
			{
				SecAPI.nodeInit(secNodeId);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

		

			// 获取安全策略
			System.out.println("secNodeId:" + secNodeId + "   rmtSecNodeId" + rmtSecNodeId);
			SecurityPolicy securityPolicy = SecurityManagerUtils.getSecPolicy(secNodeId, rmtSecNodeId);
			isMac = securityPolicy.isMac();
			isContext = securityPolicy.isContext();
			isEnc = securityPolicy.isEnc();
			// System.out.println("isMac = "+isMac
			// +"  SEC_IS_MAC = "+getBooleanHeader("SEC_IS_MAC", container));
			if (isMac != getBooleanHeader("SEC_IS_MAC", container))
			{
				// System.out.println("校验MAC。䀣䀣䀣䀣䀣㿢);
				throw new SecurityException("XSSF301500AH", new Object[] { Boolean.valueOf(getBooleanHeader("SEC_IS_MAC", container)) });
			}
			// 进行安全上下文䀍AC、报文解寿
			if (isMac)
			{
				// System.out.println("MAC 值："+new String((byte[])
				// container.get("SEC_MAC")));
				// System.out.println("报文体："+new String(messageBody , "UTF-8"));
				SecurityManagerUtils.macVerify(secNodeId, rmtSecNodeId, messageBody, (byte[]) container.get("SEC_MAC"));
			}
			// 进行安全上下文校骿
			if (isContext != getBooleanHeader("SEC_IS_CONTEXT", container))
			{
				throw new SecurityException("XSSF301500AI", new Object[] { Boolean.valueOf(getBooleanHeader("SEC_IS_CONTEXT", container)) });
			}

			if ((isServer) && (isContext))
			{
				SecurityManagerUtils.checkSecContext(secNodeId, rmtSecNodeId, evtTraceId, secContext);
			}

			if (isEnc != getBooleanHeader("SEC_IS_ENC", container))
			{
				throw new SecurityException("XSSF301500AJ", new Object[] { Boolean.valueOf(getBooleanHeader("SEC_IS_ENC", container)) });
			}

			if (isEnc)
			{
				messageBody = SecurityManagerUtils.pkgDecrypt(secNodeId, rmtSecNodeId, messageBody);
			}
			// 验证签名
			// System.out.println("报文头中签名值："+sign);
			if (sign != null && !"".equals(sign))
			{
				SecurityManagerUtils.signVerify(rmtSecNodeId, messageBody, sign.getBytes());
			}
		}
		catch (Throwable t)
		{
			if (isServer)
			{
				// secErrorCode = SwapAreaUtils.getSecErrorCode();
				if (StringUtils.isEmpty(secErrorCode))
				{
					if ((t instanceof CommonRuntimeException))
						secErrorCode = ((CommonRuntimeException) t).getCode();
					else
					{
						secErrorCode = "XSSF301500AC";
					}
				}
			}
			if ((t instanceof CommonRuntimeException))
			{
				throw ((CommonRuntimeException) t);
			}
			throw new SecurityException("XSSF301500AC", t, new Object[] { t.getLocalizedMessage() });
		}
		finally
		{
			// SwapAreaUtils.setSecErrorCode(null);
			securityMessageHeader.setMac(getBooleanHeader("SEC_IS_MAC", container));
			securityMessageHeader.setContext(getBooleanHeader("SEC_IS_CONTEXT", container));
			securityMessageHeader.setEnc(getBooleanHeader("SEC_IS_ENC", container));
			securityMessageHeader.setServiceId(getStringHeader("SEC_TX_CODE", container));

			securityMessageHeader.setServiceType(getStringHeader("SEC_TX_TYPE", container));

			securityMessageHeader.setRespCode(getStringHeader("SEC_RESP_CODE", container));

			securityMessageHeader.setSecErrorCode(secErrorCode);
			securityMessageHeader.setSecNodeId(secNodeId);
			securityMessageHeader.setRmtSecNodeId(rmtSecNodeId);
			securityMessageHeader.setEvtTraceId(evtTraceId);
			securityMessageHeader.setSecContext(secContext);
			securityMessageHeader.setSign(sign);
			// log.debug("log001", new Object[] {
			// securityMessageHeader.toString() });
		}

		retObjs[1] = messageBody;
		// System.out.println("*********************** ummarshal 3 **************************");
		return retObjs;
	}

	public static byte[] pinDecrypt(byte[] cipherData)
	{
		// System.out.println("进入敏感字段解密。䀣䀣㿢);
		// 安全组件初始匿
		// try {
		// SecAPI.nodeInit(mRmtSecNodeId);
		// } catch (SecException e) {
		// e.printStackTrace();
		// }
		// System.out.println("本地安全节点号："+mRmtSecNodeId
		// +"      对端安全节点号："+mSecNodeId +"      " +
		// "解密内容＿+new String(cipherData));
		byte[] secDecryptBytes = SecurityManagerUtils.pinDecrypt(mRmtSecNodeId, mSecNodeId, cipherData);

		// System.out.println("解密后怤غ："+new String(secDecryptBytes));

		return secDecryptBytes;
	}

	private static void outputHeader(byte[] headerName, byte[] headerValue, OutputStream outputStream) throws IOException
	{
		outputStream.write(headerName);
		outputStream.write(SEP_BYTES);
		outputStream.write(headerValue);
		outputStream.write(SEP2_BYTES);
	}

	private static void outputHeader(byte[] headerName, String headerValue, OutputStream outputStream) throws IOException
	{
		byte[] bytesValue = new byte[0];
		if (headerValue != null)
		{
			bytesValue = headerValue.getBytes(SecurityManagerUtils.CHARSET);
		}
		outputHeader(headerName, bytesValue, outputStream);
	}

	private static boolean getBooleanHeader(String headerName, Map<String, byte[]> container)
	{
		return "1".equals(getStringHeader(headerName, container));
	}

	private static String getStringHeader(String headerName, Map<String, byte[]> container)
	{
		byte[] byteHeader = (byte[]) container.get(headerName);
		if ((byteHeader == null) || (byteHeader.length == 0))
		{
			return null;
		}

		return new String(byteHeader, SecurityManagerUtils.CHARSET);
	}
}