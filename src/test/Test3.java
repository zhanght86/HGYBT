package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

import com.sinosoft.lis.db.ContDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.vschema.ContSet;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.YBTDataVerification;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newabc.format.NonRealTimeContOutXsl;
import com.sinosoft.utility.ElementLis;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class Test3 {

	@SuppressWarnings("null")
	public static void main(String[] args) throws Exception {
		
//		String mInFilePath = "G:/3115938_16_1_outSvc.xml";
//				
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs);
//		mIs.close();	
//	    Element tBody = mInXmlDoc.getRootElement().getChild("Body");
//	    List<Element> list  = tBody.getChildren("Risk");
//	    for (int i=0; i<list.size() ; i++) {
//			System.out.println("险种名称："+list.get(i).getChildText("RiskName"));
//		}
//	    Element mInRiskEle = tBody.getChild("Risk");
//	    System.out.println("险种名称:"+mInRiskEle.getChildText("RiskName"));
		
		//20141203
//		String s="aaa";
//		for (int i=0 ;i<5 ; i++) {
//			s="b"+s;
//			System.out.println("s:"+s);
//		}
//		
		String s="P53816107_20150119_010072_00001";
		String t="";
		
		System.out.println("t："+s.substring(10, 18));
		
//		String mSql="select * from contblcdtl where trancom='05' and trandate='20150130' and type='0' and proposalprtno is not null";
//		SSRS ssrs=new ExeSQL().execSQL(mSql);
//		System.out.println(ssrs.getMaxRow());
//		for (int i=0; i<ssrs.getMaxRow(); i++) {
//			System.out.println(ssrs.GetText(i+1, 3));
//		}
//    	  String mTranDate = String.valueOf(DateUtil.get8Date(new Date().getTime() - 86400000L));
//    	  System.out.println(mTranDate);
//		//得到下一天方法，各个月最后一天不一样
//		String curDate="";//当前日期
//		String sDate="";//生成文件日期
//		//20141217
//		Calendar c = Calendar.getInstance();
//	    curDate=String.valueOf(DateUtil.get8Date(c.getTime()));
//	    System.out.println("今天是："+curDate);
//	    
//	    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
//
//		Calendar   cDay1   =   Calendar.getInstance();   
//        cDay1.setTime(new Date());   
//        final   int   lastDay   =   cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);   
//        Date   lastDate   =   cDay1.getTime();   
//        lastDate.setDate(lastDay);  
//		System.out.println("最后一天："+DateUtil.get8Date(lastDate));
//		
//		if(String.valueOf(DateUtil.get8Date(lastDate))==curDate||String.valueOf(DateUtil.get8Date(lastDate)).equals(curDate)){
//			System.out.println("今天是本月最后一天");
//		    c.set(Calendar.MONTH, c.get(Calendar.MONTH)+1);
//		    c.set(Calendar.DAY_OF_MONTH, 1);
//		    sDate=String.valueOf(DateUtil.get8Date(c.getTime()));
//		    System.out.println("下个月的第一天: " + sDate);
//			System.out.println("今天的文件日期："+sDate);
//		}else{
//			System.out.println("今天不是本月最后一天");
//			
//			Calendar calendar = Calendar.getInstance();
//			calendar.add(Calendar.DAY_OF_YEAR, 1);
//			Date date = calendar.getTime();
//			sDate=String.valueOf(DateUtil.date10to8((sdf.format(date))));
//			System.out.println("今天生成的文件日期："+sDate);
//		}
		
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
