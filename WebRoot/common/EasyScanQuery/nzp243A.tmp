<%
//程序名称：EasyScanQueryKernel.jsp
//程序功能：EasyScanQuery查询功能的核心函数
//创建日期：2002-11-07
//创建人  ：胡博
//更新：刘强 2005-03-27 修改查询接口
//更新记录：  更新人    更新日期     更新原因/内容       
%>

<%@page import="com.sinosoft.lis.schema.*"%>
<%@page import="com.sinosoft.lis.pubfun.*"%>
<%@page import="com.sinosoft.utility.*"%>

<%!

//************************************************
//根据DocID直接查找关联的影像文件
//调用EasyScanQueryUI进行提交和数据库查找，返回结果字符串strResult
//************************************************
public String[] easyScanQueryKernel0(String DocID,
				     String clientUrl) { 
  String[] arrResult;
  
  if (DocID == null || DocID.equals("") || DocID.equals("undefined")) {
    System.out.println("EasyScanQuery don't accept DocID!");
    return null;
  }

  EasyScanQueryUI tEasyScanQueryUI = new EasyScanQueryUI();
  VData tVData = new VData();
  VData tVData1 = new VData();
  tVData.add(DocID);
  tVData.add(clientUrl);
  tEasyScanQueryUI.submitData(tVData, "QUERY||0");

  if(tEasyScanQueryUI.mErrors.needDealError()) {
	  System.out.println("EasyScanQueryUI throw errors:" + tEasyScanQueryUI.mErrors.getFirstError());
	  return null;
  } else {
	  tVData.clear() ;
	  tVData = tEasyScanQueryUI.getResult() ;
	  tVData1 = (VData)tVData.get(0);	  
    arrResult = new String[tVData1.size()];
    for (int i=0; i<tVData1.size(); i++) {
      arrResult[i] = (String)tVData1.get(i);
      //System.out.println(arrResult[i]);
    }  
  }
  return arrResult;
}

//************************************************
//根据业务号码进行所关联的影像文件的查找
// 调用EasyScanQueryUI进行提交和数据库查找，返回结果字符串strResult
//************************************************
public String[] easyScanQueryKernel1(String BussNo,
				    String BussNoType,
				    String BussType,
				    String SubType,
				    String clientUrl) { 
  String[] arrResult;
  
  System.out.println(BussNo);
  System.out.println(BussNoType);
  System.out.println(BussType);
  System.out.println(SubType);
  if (BussNo == null || BussNo.equals("") || BussNo.equals("undefined")) {
    System.out.println("EasyScanQuery don't accept BussNo!");
    return null;
  }

  EasyScanQueryUI tEasyScanQueryUI = new EasyScanQueryUI();
  VData tVData = new VData();
  VData tVData1 = new VData();
  tVData.add(BussNo);
  tVData.add(BussNoType);
  tVData.add(BussType);
  tVData.add(SubType);
  tVData.add(clientUrl);
  
  tEasyScanQueryUI.submitData(tVData, "QUERY||1");

  if(tEasyScanQueryUI.mErrors.needDealError()) {
	  System.out.println("EasyScanQueryUI throw errors:" + tEasyScanQueryUI.mErrors.getFirstError());
	  return null;
  } else {
	  tVData.clear() ;
	  tVData = tEasyScanQueryUI.getResult() ;
	  tVData1 = (VData)tVData.get(0);	  
    arrResult = new String[tVData1.size()];
    for (int i=0; i<tVData1.size(); i++) {
      arrResult[i] = (String)tVData1.get(i);
      //System.out.println(arrResult[i]);
    }
	  
  }
  
  return arrResult;
}

//************************************************
//另外一种查寻方法:两种业务号码关联时的查询  
//调用EasyScanQueryUI进行提交和数据库查找，返回结果字符串strResult
//************************************************
public String[] easyScanQueryKernel2(String BussNo,
				     String BussNoType,
				     String BussNo2,
				     String BussNoType2,
				     String BussType,
				     String SubType,
				     String clientUrl) { 
  String[] arrResult;
  
  if (BussNo == null || BussNo.equals("") || BussNo.equals("undefined")) {
    System.out.println("EasyScanQuery don't accept BussNo!");
    return null;
  }

  EasyScanQueryUI tEasyScanQueryUI = new EasyScanQueryUI();
  VData tVData = new VData();
  tVData.add(BussNo);
  tVData.add(BussNoType);
  tVData.add(BussNo2);
  tVData.add(BussNoType2);
  tVData.add(BussType);
  tVData.add(SubType);
  tVData.add(clientUrl);
  tEasyScanQueryUI.submitData(tVData, "QUERY||2");

  if(tEasyScanQueryUI.mErrors.needDealError()) {
	  System.out.println("EasyScanQueryUI throw errors:" + tEasyScanQueryUI.mErrors.getFirstError());
	  return null;
  } else {
	  tVData.clear() ;
	  tVData = tEasyScanQueryUI.getResult();
	  VData mData = new VData();
	  mData = (VData)tVData.get(0);
    arrResult = new String[mData.size()];
    for (int i=0; i<mData.size(); i++) {
      arrResult[i] = (String)mData.get(i);
      //System.out.println(arrResult[i]);
    }
	  
  }
  
  return arrResult;
}

//************************************************
//Added by niuzj 20060926,新华历史单证扫描影像查询
//根据DocID直接查找关联的影像文件
//调用EasyScanQueryOldDataUI进行提交和数据库查找，返回结果字符串strResult
//************************************************
public String[] easyScanQueryKernel9999(String DocID,
				     String clientUrl) { 
  String[] arrResult;
  
  if (DocID == null || DocID.equals("") || DocID.equals("undefined")) {
    System.out.println("EasyScanQueryOldData don't accept DocID!");
    return null;
  }

  EasyScanQueryOldDataUI tEasyScanQueryOldDataUI = new EasyScanQueryOldDataUI();
  VData tVData = new VData();
  VData tVData1 = new VData();
  tVData.add(DocID);
  tVData.add(clientUrl);
  tEasyScanQueryOldDataUI.submitData(tVData, "QUERY||9999");

  if(tEasyScanQueryOldDataUI.mErrors.needDealError()) {
	  System.out.println("tEasyScanQueryOldDataUI throw errors:" + tEasyScanQueryOldDataUI.mErrors.getFirstError());
	  return null;
  } else {
	  tVData.clear() ;
	  tVData = tEasyScanQueryOldDataUI.getResult() ;
	  tVData1 = (VData)tVData.get(0);	  
    arrResult = new String[tVData1.size()];
    for (int i=0; i<tVData1.size(); i++) {
      arrResult[i] = (String)tVData1.get(i);
      //System.out.println(arrResult[i]);
    }  
  }
  return arrResult;
}
%>


