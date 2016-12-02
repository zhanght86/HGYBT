package test.security;

import org.apache.log4j.Logger;

import com.adtec.security.BaseException;
import com.adtec.security.Nobis;
import com.sinosoft.midplat.common.SysInfo;

public class CCBCipher {
	private Logger cLogger = Logger.getLogger(getClass());

	private Nobis cNobis = null;
	
	public CCBCipher(int pPort) throws BaseException {
//		String mKeyPath = getClass().getResource(pPort + ".dat").getPath();
		String mKeyPath = SysInfo.cHome + "key/ccbKey.dat";
		cLogger.debug("密钥文件路径：" + mKeyPath);
		cNobis = Nobis.nobisFactory();
		cNobis.bisReadKey(mKeyPath);
	}
	
	/**
	 * 加密
	 */
	public byte[] encode(String pClearStr) throws BaseException {
		return cNobis.bisPkgCompressEnc(pClearStr);
	}
	
	/**
	 * 解密
	 */
	public byte[] decode(byte[] pCipherBytes) throws BaseException {
		return cNobis.bisPkgDecompressDec(pCipherBytes);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("程序开始...");
		
		String mTestClearStr = "djiejdijeijd陈贵菠ijadijaidjidei";
		System.out.println("\n原始：\n" + mTestClearStr);
		
		CCBCipher mCCBCipher = new CCBCipher(14999);
		byte[] mCipherBytes = mCCBCipher.encode(mTestClearStr);
		System.out.println("\n加密：\n" + new String(mCipherBytes));
		
		byte[] mClearBytes = mCCBCipher.decode(mCipherBytes);
		System.out.println("\n解密：\n" + new String(mClearBytes, "GBK"));
		
		System.out.println("\n成功结束！");
	}
}
