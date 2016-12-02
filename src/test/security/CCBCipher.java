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
		cLogger.debug("��Կ�ļ�·����" + mKeyPath);
		cNobis = Nobis.nobisFactory();
		cNobis.bisReadKey(mKeyPath);
	}
	
	/**
	 * ����
	 */
	public byte[] encode(String pClearStr) throws BaseException {
		return cNobis.bisPkgCompressEnc(pClearStr);
	}
	
	/**
	 * ����
	 */
	public byte[] decode(byte[] pCipherBytes) throws BaseException {
		return cNobis.bisPkgDecompressDec(pCipherBytes);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println("����ʼ...");
		
		String mTestClearStr = "djiejdijeijd�¹�ijadijaidjidei";
		System.out.println("\nԭʼ��\n" + mTestClearStr);
		
		CCBCipher mCCBCipher = new CCBCipher(14999);
		byte[] mCipherBytes = mCCBCipher.encode(mTestClearStr);
		System.out.println("\n���ܣ�\n" + new String(mCipherBytes));
		
		byte[] mClearBytes = mCCBCipher.decode(mCipherBytes);
		System.out.println("\n���ܣ�\n" + new String(mClearBytes, "GBK"));
		
		System.out.println("\n�ɹ�������");
	}
}
