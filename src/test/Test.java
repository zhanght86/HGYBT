package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

import com.sinosoft.lis.db.ContDB;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.vschema.ContSet;
import com.sinosoft.midplat.bat.BatConf;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.IOTrans;
import com.sinosoft.midplat.common.JdomUtil;
import com.sinosoft.midplat.common.NumberUtil;
import com.sinosoft.midplat.common.YBTDataVerification;
import com.sinosoft.midplat.net.CallWebsvcAtomSvc;
import com.sinosoft.midplat.newabc.format.NonRealTimeContOutXsl;
import com.sinosoft.utility.ElementLis;
import com.sinosoft.utility.ExeSQL;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class Test {

	@SuppressWarnings("null")
	public static void main(String[] args) throws Exception {
		
		String s2="20131313123456";
		System.out.println(s2.substring(0,8));
		
		
		DecimalFormat   df   =   new   DecimalFormat( "#######0.00 ");
        String   dff   =    df.format(0.0);
        System.out.println(dff);
		
//		String desc="保费计算失败(如果没有具体错误原因，请依次检查：1 交费方式和交费期间（或保险期间）是否匹配 2 保费，保额是否输入 3 相关要素（譬如年龄、被保人工资等）是否超过费率计算范围 4 职业代码是否和计算相关)";
//		System.out.println(""+desc.length());
//		
//		if(desc.length()>90){
//			desc=desc.substring(0,60)+"......)";
//			System.out.println(desc);
//		}
		
//  	  String[] mTranCom = new String[]{"01","01","05","06","07"};
//	  String[] mBank=new String[]{"工商银行","工商银行","农业银行","宁波银行","金华银行"};
//	  String[] mFuncFlag = new String[]{"1005","1104","2001","4005","5005"};
//  	  String mTranDate = String.valueOf(DateUtil.get8Date(new Date().getTime() - 86400000L));
//	  
//	  for (int i=0;i<mTranCom.length;i++) {
//		System.out.println(mTranCom[i]+mBank[i]+mFuncFlag[i]);
//	}
		  
//	  System.out.println("交易日期:"+mTranDate+" "+mFuncFlag.length+" "+mBank.length+" "+mTranCom.length);
		
//  	  String mTranDate = String.valueOf(DateUtil.get8Date(new Date().getTime() - 86400000L));
//  	  System.out.println(mTranDate);
		
//		  Element cThisConfRoot = null;
//		  Element cThisBusiConf = null;
//	      cThisConfRoot = BatConf.newInstance().getConf().getRootElement();
//	      cThisBusiConf = ((Element)XPath.selectSingleNode(
//	      BatConf.newInstance().getConf().getRootElement(), "batch[funcFlag='" + "1005" + "']"));
//
////	      System.out.println(cThisConfRoot);
////	      JdomUtil.print(BatConf.newInstance().getConf().getRootElement());
//	      System.out.println(cThisBusiConf.getChildText("name"));
//		String mInputFilePath = "H:/李路/杭州中韩人寿/工行接口/UAT业务测试报文/悦无忧承保2.xml";
//		String mOutFilePath = "H:/李路/杭州中韩人寿/工行接口/UAT业务测试报文/悦无忧承保2_转换后.xml";
//		InputStream mIs=new FileInputStream(mInputFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs);
//		mIs.close();
//		
//		String sOutNoStdXml=toStringFmtNull(mInXmlDoc, "GBK");
//		System.out.println(sOutNoStdXml);
//		
//		OutputStream mOs = new FileOutputStream(mOutFilePath);
////		Document mOutXmlDoc=JdomUtil.build(sOutNoStdXml);
////		JdomUtil.output(mOutXmlDoc, mOs);
//		
//		mOs.flush();
//		mOs.close();
//		
		
//		String f=new String("10000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
//		StringBuffer s=new StringBuffer();;
//		byte[] file_byte=f.getBytes();
//		int k=String.valueOf(file_byte.length).length();
//		for (int i = 0; i <12-k; i++) {
//			System.out.println(k+" "+file_byte.length);
//			s.append("0");
//		}
//		s.append(file_byte.length);
//		System.out.println(s);
//		byte[] b=String.valueOf(s).getBytes();
//		System.out.println(Arrays.toString(b));
		
//		String mInFilePath = "G:/998301_47_1_outSvc.xml";
//				
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs);
//		mIs.close();	
//	    Element tBody = mInXmlDoc.getRootElement().getChild("Body");
//	    List<Element> list  = tBody.getChildren("Detail");
//	    System.out.println(list.size());
//		
		
//		String tRcodeSql2 = new StringBuilder("select count(1) from TranLog where funcflag='").append("1012'")
//		.append(" and ProposalPrtNo='").append("210414111100001").append('\'')
//		.append(" and rcode ='").append("0'")
//		.toString();
//		System.out.println(tRcodeSql2);
//		//测试while
//		int i=1;
//		while(i<5){
//			if(i>0){
//				System.out.println(i);
//				i++;
//				continue;
//			}
//			break;
//		}
		
		
		//测试删除数据
//		Calendar tCurCalendar = Calendar.getInstance();
//		String tTranlogSql= new StringBuilder("select * from cont where proposalprtno='")
//		.append("210414100010023").append('\'')
//		.append(" and makedate='").append("20140812' ")
//		.toString();
//		System.out.println(tTranlogSql);
//		ContSet mContSet = new ContDB().executeQuery(tTranlogSql);
//		System.out.println(mContSet.size());
//		if(mContSet.size()==1){
//			String sql= new StringBuilder("delete  from tranlog where proposalprtno='")
//			.append("210414100010023").append('\'')
//			.append(" and makedate='").append("20140812' ")
//			.toString();
//			String sql2= new StringBuilder("delete  from cont where proposalprtno='")
//			.append("210414100010023").append('\'')
//			.append(" and makedate='").append("20140812' ")
//			.toString();
//			new ExeSQL().execSQL(sql);
//			new ExeSQL().execSQL(sql2);
//		}
//		Date date=new Date();
//		System.out.println(String.valueOf(DateUtil.get8Date(date)));
//		System.out.println(String.valueOf(DateUtil.date8to10(String.valueOf(DateUtil.get8Date(date)))));
//		System.out.println(String.valueOf(DateUtil.get6Time(date)));
		
//		System.out.println(System.currentTimeMillis());
//		System.out.println(DateUtil.get8Date(System.currentTimeMillis()));
//		System.out.println(DateUtil.get8Date(System.currentTimeMillis())+1);
		//测试文件
//		File f=new File("H:/aa/1.txt");
//		if(f.exists()){
//			f.delete();
//		}else{
//			f.createNewFile();
//		}
		
//		String s="123.0";
//		double d=0;
//		
//		d=Double.parseDouble(s);
//		System.out.println("d=:"+d);
//		
//		String s=null;
//		s=PubFun1.CreateMaxNo("Tranno", "20");
//		System.out.println("-----"+s);
//		
//		String s="  888   8888   999  ";
//		System.out.println(s+"aaa");
//		System.out.println(s.trim()+"aaa");
//		int a=0;
//		do {
//			a++;
//			System.out.println("======"+a);
//		} while (a==2);
		
//		String s="200123";
//		long t=com.sinosoft.midplat.common.NumberUtil.yuanToFen(s);
//		System.out.println(t);
		
//		String s="2013-08-08";
//		s=DateUtil.date10to8(s);
//		System.out.println(s);
		
//		String getProposalPrtNoSQL ="select ProposalPrtNo from tranlog where contno = '"+"2015081002000006"+"' and"+" funcflag='"+"1014'";
//		String mProposalPrtNo = new ExeSQL().getOneValue(getProposalPrtNoSQL);
//		System.out.println(mProposalPrtNo);
//		ElementLis TranData = new ElementLis("TranData");
//		ElementLis Head = new ElementLis("Head",TranData);
//		int date = DateUtil.get8Date(System.currentTimeMillis());
//		int time = DateUtil.get6Time(System.currentTimeMillis());
//		String trantime= new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
//		ElementLis TranDate = new ElementLis("TranDate",trantime.substring(0,8),Head);
//		ElementLis TranTime = new ElementLis("TranTime",trantime.substring(8,14),Head);
//		ElementLis TranCom = new ElementLis("TranCom","06",Head);
//		ElementLis ZoneNo = new ElementLis("ZoneNo","1101",Head);
//		ElementLis NodeNo = new ElementLis("NodeNo","11010102",Head);
//		ElementLis BankCode = new ElementLis("BankCode","0102",Head);
//		ElementLis TellerNo = new ElementLis("TellerNo","11010102110", Head);
//		PubFun1 p = new PubFun1();
//		String stranNo = p.CreateMaxNo("TransNo",16);
//		ElementLis TranNo = new ElementLis("TranNo",stranNo,Head);
//		ElementLis FuncFlag = new ElementLis("FuncFlag","2005",Head);//交易代码
//		ElementLis Body = new ElementLis("Body",TranData);
//		ElementLis Detail = new ElementLis("Detail",Body);
//		ElementLis RiskCode = new ElementLis("RiskCode","231202",Detail);
//		ElementLis Prem = new ElementLis("Prem","11111",Detail);
//		ElementLis Amnt = new ElementLis("Amnt","22222",Detail);
//		ElementLis Insured = new ElementLis("Insured",Detail);
//		ElementLis PayIntv = new ElementLis("PayIntv","12",Insured);
//		ElementLis Name = new ElementLis("Name","33333",Insured);
//		ElementLis Detail2 = new ElementLis("Detail",Body);
//		ElementLis RiskCode2 = new ElementLis("RiskCode","231201",Detail2);
//		ElementLis Prem2 = new ElementLis("Prem","44444",Detail2);
//		ElementLis Amnt2 = new ElementLis("Amnt","55555",Detail2);
//		ElementLis Insured2 = new ElementLis("Insured",Detail2);
//		ElementLis PayIntv2 = new ElementLis("PayIntv","12",Insured2);
//		ElementLis IDType2 = new ElementLis("IDType","0",Insured2);
//		ElementLis Name2 = new ElementLis("Name","66666",Insured2);
//		
//		Document pXmlDoc = new Document(TranData);	
//		JdomUtil.print(pXmlDoc);
//		pXmlDoc=NonRealTimeContOutXsl.newInstance().getCache().transform(pXmlDoc);
//		JdomUtil.print(pXmlDoc);
		
		
//		long mUsedContConfirm =0;
//		long mStartContConfirm = System.currentTimeMillis();
//		System.out.println(mStartContConfirm);
//		mUsedContConfirm = (System.currentTimeMillis() - mStartContConfirm+1);
//		System.out.println(mUsedContConfirm);
		
		
/*		int date = DateUtil.get8Date(System.currentTimeMillis());
		System.out.println(date+"   ");
		int appdate=20150303;
		int s=date-appdate;
		if(date-appdate<=15){
			System.out.println("犹豫期。。。。。。"+s);
		}
		
		
		System.out.println("。。。。。"+PubFun.calInterval("2015-03-03", DateUtil.getCur10Date(),"D"));*/
//		String trantime= new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
////		System.out.println("交易时间1："+trantime);
//		trantime=trantime.substring(0,8);
//		System.out.println("交易时间2："+trantime);
		
		
//		 String yearSalary=null;
//		 double yearSalaryD=0;
//		   if(yearSalary!=null&&!"".equals(yearSalary)){
//			   yearSalaryD=Double.valueOf(yearSalary)/10000;
//		   }
//		   System.out.println(String.valueOf(yearSalaryD));	   
//
//		System.out.println("程序开始…");
//		//发给核心的标准报文
//		String mInFilePath = "H:/李路/任务/P53819113InSvcStd.xml";
//		//核心出来的标准报文
//		String mOutFilePath = "H:/李路/P53819113OutSvc_1.xml";
//				
//		InputStream mIs = new FileInputStream(mInFilePath);
//		Document mInXmlDoc = JdomUtil.build(mIs);
//		mIs.close();	
//		
//		Document cOutXmlDoc = new CallWebsvcAtomSvc("0").call(mInXmlDoc);
//
//		JdomUtil.print(cOutXmlDoc);		
//
//		OutputStream mOs = new FileOutputStream(mOutFilePath);
//		JdomUtil.output(cOutXmlDoc, mOs);
//		mOs.flush();
//		mOs.close();
//
//		String s=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
//		SimpleDateFormat sft=new SimpleDateFormat("yyyyMMddHHmmssSSS");
//		String date=sft.format(new Date());
//		System.out.println(new Date());
//		System.out.println(date);
//		System.out.println("成功结束！"+s.substring(8,14));		
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
		
		public static String toStringFmtNull(Document pXmlDoc, String pEncodingDecl) {
			Format mFormat = Format.getRawFormat().setIndent("");
			mFormat.setLineSeparator("");
			if (null == pEncodingDecl) {   
				mFormat.setOmitDeclaration(true);
			} else if ("".equals(pEncodingDecl)) {
				mFormat.setOmitEncoding(true);
			} else {
				mFormat.setEncoding(pEncodingDecl);
			}
			return new XMLOutputter(mFormat).outputString(pXmlDoc);
		}
}
