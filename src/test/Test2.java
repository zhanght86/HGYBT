//用于解决乱码的问题，参考而已

package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import test.security.CCBCipher;

import com.adtec.security.Nobis;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.SysInfo;
import com.sinosoft.midplat.common.YBTDataVerification;
import com.adtec.security.Nobis;
public class Test2 {
	private static Nobis cNobis;
	
	public static void main(String[] args) throws Exception {
		String mInFilePath = "D:/xml/psbc/1001.xml";
		InputStream mIs = new FileInputStream(mInFilePath);
		
		byte[] mOutBytes = IOTrans.toBytes(mIs);
		
		cNobis = Nobis.nobisFactory();
		cNobis.bisReadKey(SysInfo.cHome + "key/ccbKey.dat");
		
		CCBCipher mCCBCipher = new CCBCipher(1);
		byte[] mBodyBytes = mCCBCipher.encode(new String(
				mOutBytes, "GBK"));
		
		mBodyBytes = cNobis.bisPkgDecompressDec(mBodyBytes);
		
		Document doc = JdomUtil.build(mBodyBytes);
		
		JdomUtil.print(doc);
	}
	
	
	public static boolean testSameGradeTypeFenE(String filename){
		InputStream mIs = null;  
		try { 
			mIs = new FileInputStream(filename); 
		} catch (FileNotFoundException e) {
			e.printStackTrace(); 
		}
		byte[] mInClearBodyBytes = IOTrans.toBytes(mIs);
		Document cInXmlDoc = JdomUtil.build(mInClearBodyBytes, "GBK");
		
		YBTDataVerification verification = new YBTDataVerification();
		boolean GradeFlag = verification.SameGradeTypeBnfVerification(cInXmlDoc);
		
		return GradeFlag;
	}
	
	 public static int compareDay(int pFir8Date, int pSec8Date)
	  {
	    if (pFir8Date < pSec8Date) {
	      int tTemp = pFir8Date;
	      pFir8Date = pSec8Date;
	      pSec8Date = tTemp;
	    }
	    Calendar mSmlCalendar = Calendar.getInstance();
	    mSmlCalendar.set(pSec8Date / 10000, pSec8Date % 10000 / 100 - 1, pSec8Date % 100);

	    Calendar mBigCalendar = (Calendar)mSmlCalendar.clone();
	    mBigCalendar.set(pFir8Date / 10000, pFir8Date % 10000 / 100 - 1, pFir8Date % 100);

	    return (int)((mBigCalendar.getTimeInMillis() - mSmlCalendar.getTimeInMillis() + 3600000L) / 86400000L);
	  }

}
