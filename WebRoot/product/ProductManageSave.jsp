
<jsp:directive.page import="java.util.Date"/>
<%@page import="java.sql.Connection"%>
<%@include file="../common/jsp/UsrCheck.jsp"%>
<%@page contentType="text/html;charset=GBK"%>

<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.sinosoft.midplat.common.*"%>
<%@page import="com.sinosoft.midplat.exception.*"%>
<%@page import="com.sinosoft.lis.pubfun.GlobalInput"%>
<%@page import="com.sinosoft.utility.*"%>
<%@page import="com.sinosoft.midplat.manage.ProductManageUI"%>

<%

	Logger cLogger = Logger.getLogger(getClass());
	cLogger.info("into NodeManageSave.jsp...");

	GlobalInput cGlobalInput = (GlobalInput) session.getValue("GI");
	String mFlag = "";
	String mMessage = "";
	//操作类型(INSERT/UPDATE/DELETE)
	String mOperType = request.getParameter("OperType");	
	String miBankCode = request.getParameter("iBankCode");	
	String miBankName = request.getParameter("iBankName");
	String miRiskCode = request.getParameter("iRiskCode");
	String miRiskName = request.getParameter("iRiskName");
	//产品更新时的产品代码
	String mProCode = request.getParameter("ProCode");
	String miStartDate = "2000-01-01";
	String miEndDate = "2000-01-01";
	String miComCode = request.getParameter("iComCode");
	String miComCodeName = request.getParameter("iComCodeName");
	String mStaDate = request.getParameter("StaDate");
	String mEndSDate = request.getParameter("EndSDate");
	String mASCode =request.getParameter("ASCode");
	String mUpBank = request.getParameter("UpBank");
	// 产品代码————————替换LMRiskMap中的Insure_code
	String miProductCode = request.getParameter("iProductCode");
	String mProStateCode = request.getParameter("ProStateCode");
	String date=DateUtil.getCur10Date();
	String miActivityCode = "";
	miActivityCode="2";
	if(mProStateCode.equals("1")){
		mOperType="DELETE";
	}

	System.out.println("miBankCode" + miBankCode);
	System.out.println("miBankName" + miBankName);
	System.out.println("miRiskCode" +miRiskCode);
	System.out.println("miRiskName" + miRiskName);
	System.out.println("miActivityCode" + miActivityCode);
	System.out.println("miComCode" + miComCode);
	System.out.println("miComCodeName" +miComCodeName );
	System.out.println("miStartDate" + DateUtil.date10to8(miStartDate));
	System.out.println("miEndDate" + DateUtil.date10to8(miEndDate));
	System.out.println("miProductCode" + miProductCode);
	VData vdate = new VData();
	TransferData tTransferData = new TransferData();
	tTransferData.setNameAndValue("Bankcode", miBankCode);
	tTransferData.setNameAndValue("InsuCode", miProductCode);
	tTransferData.setNameAndValue("ActivityCode", miActivityCode);
	tTransferData.setNameAndValue("StartDate", DateUtil.date10to8(miStartDate));
	tTransferData.setNameAndValue("EndDate", DateUtil.date10to8(miEndDate));
	tTransferData.setNameAndValue("iComCode", miComCode);
	//tTransferData.setNameAndValue("iProductCode", miProductCode);
//	tTransferData.setNameAndValue("OutComCode", miRiskName);
//	tTransferData.setNameAndValue("ShortName", miComCodeName);
//	tTransferData.setNameAndValue("AreaID", miBankName);
	try {
		if(!mFlag.equals("Fail")){
		
		if ("INSERT".equals(mOperType)) {
			System.out.println("INSERT--新增停售操作");
			String sql="";
			SSRS ssrs =new SSRS();
			//如果是区域，省和市级别则给予处理，否则不予处理。
			if(miComCode.length()!=0){
				if(miComCode.length()==9){
//					//如果comcode为是9位，直接判断其时间是否在本产品的时间范围内。
				vdate.add(tTransferData);
				vdate.add(cGlobalInput);
				ProductManageUI aProductManageUI = new ProductManageUI(vdate);
						aProductManageUI.insert();
				}else {
					//如果comcode为不是9位
					//先查询ldcom中等级为04而且comcode前几位与页面传来的值相同，并且不在lmriskmap表中，再进行逐一添加
					sql="select comcode from ldcom where comgrade='04' and comcode like '"+miComCode+"%'" ;
					sql+=" and comcode not in (select comcode from lmriskmap where bankcode='"+miBankCode;
					sql+="' and insucode='"+miProductCode+"')";
					ssrs= new ExeSQL().execSQL(sql);
					System.out.println("最大行数为："+ssrs.getMaxRow());
					if(ssrs.getMaxRow()>0){
						//循环添加数据
					for(int i=1;i<=ssrs.getMaxRow();i++){
						System.out.println(ssrs.GetText(i, 1));
						VData vdateZP = new VData();
					    TransferData tTransferDataZP = new TransferData();
					    tTransferDataZP.setNameAndValue("Bankcode", miBankCode);
					    tTransferDataZP.setNameAndValue("InsuCode", miProductCode);
					    tTransferDataZP.setNameAndValue("ActivityCode", miActivityCode);
					    tTransferDataZP.setNameAndValue("StartDate", DateUtil.date10to8(miStartDate));
					    tTransferDataZP.setNameAndValue("EndDate", DateUtil.date10to8(miEndDate));
					    tTransferDataZP.setNameAndValue("iComCode", ssrs.GetText(i, 1));
						vdateZP.add(tTransferDataZP);
						vdateZP.add(cGlobalInput);
						ProductManageUI aProductManageUI=new ProductManageUI(vdateZP);
						aProductManageUI.insert();
					}
					}else {
						throw new MidplatException("该区或省内所有的市都该产品均已停售！");
					}
				}
				}else {
					throw new MidplatException("产品机构不能为空！");
				}
		} else if ("UPDATE".equals(mOperType)) {
			System.out.println("UPDATE操作");
			vdate.add(tTransferData);
			vdate.add(cGlobalInput);
			ProductManageUI aProductManageUI = new ProductManageUI(vdate);
			if(miComCode.length()!=2){
				String sql="select bak1,bak2,bak3 from bankandinsumap where trancom='"+miBankCode+
				"' and insu_code='"+miRiskCode+"'";
				SSRS ssrs = new ExeSQL().execSQL(sql);
				String bak1=DateUtil.date8to10(ssrs.GetText(1,1));
				String bak2=DateUtil.date8to10(ssrs.GetText(1,2));
				String bak3=ssrs.GetText(1,3);
				if(bak3.equals("0")){
					if((miStartDate.compareTo(bak1)>=0)&&(miEndDate.compareTo(bak2)<=0)){
						aProductManageUI.update();
					}else {
						throw new MidplatException("产品日期有误！提示日期必须在"+bak1+"和"+bak2+"之间");
					}
				}else {
					throw new MidplatException("总部此产品不是'在售'状态，不能设置权限！");
				}
				}else {
					throw new MidplatException("总部此产品不能在此操作！");
				}
		} else if ("DELETE".equals(mOperType)) {
			System.out.println("DELETE--取消停售操作");
           String sql="";
           SSRS ssrs =new SSRS();
			//如果comcode为不是9位
			//先查询ldcom中等级为04而且comcode前几位与页面传来的值相同，并且不在lmriskmap表中，再进行逐一添加
			sql="select comcode from LMRISKMAP where codetype='risk_com' and comcode like '"+miComCode+"%'" ;
			sql+=" and bankcode='"+miBankCode;
			sql+="' and insucode='"+miProductCode+"'";
			ssrs= new ExeSQL().execSQL(sql);
			System.out.println("最大行数为："+ssrs.getMaxRow());
			if(ssrs.getMaxRow()>0){
				//循环添加数据
			for(int i=1;i<=ssrs.getMaxRow();i++){
				System.out.println(ssrs.GetText(i, 1));
				VData vdateZP = new VData();
			    TransferData tTransferDataZP = new TransferData();
			    tTransferDataZP.setNameAndValue("Bankcode", miBankCode);
			    tTransferDataZP.setNameAndValue("InsuCode", miProductCode);
			    tTransferDataZP.setNameAndValue("ActivityCode", miActivityCode);
			    tTransferDataZP.setNameAndValue("StartDate", DateUtil.date10to8(miStartDate));
			    tTransferDataZP.setNameAndValue("EndDate", DateUtil.date10to8(miEndDate));
			    tTransferDataZP.setNameAndValue("iComCode", ssrs.GetText(i, 1));
				vdateZP.add(tTransferDataZP);
				vdateZP.add(cGlobalInput);
				ProductManageUI aProductManageUI=new ProductManageUI(vdateZP);
				aProductManageUI.delete();
			}
			}else {
				throw new MidplatException("该区或省内所有的市都该产品均不在停售状态！");
			}
		} else if("RISKUPDATE".equals(mOperType)){
			System.out.println("RISKUPDATE————总公司产品更新操作");
			String currentTime=DateUtil.getCur10Date();
			String bak1,bak2,bak3="";
			bak1=mStaDate;
			bak2=mEndSDate;
			if (mStaDate.compareTo(mEndSDate)>0){
				throw new MidplatException("起始日期不能晚于终止日期!");
			}else if(mEndSDate.compareTo(currentTime)<0){
				bak3="2";
			}else if(mStaDate.compareTo(currentTime)>0){
				bak3="1";
			}else if((mStaDate.compareTo(currentTime)<=0)&&(mEndSDate.compareTo(currentTime)>=0)){
				bak3="0";
			}
			//采用事务提交方面，先将自动提交置为false,如果执行失败，就回滚，执行成功，则提交，关闭连接。
			Connection mLocalConn = DBConnPool.getConnection();
			mLocalConn.setAutoCommit(false);
			//以下是本人自己写的更新语句，本应在JAVA类中处理，但由于本人对公司的还不能熟练运用
			//如果哪位高人可以帮忙代劳，不胜感激。         zfs
			String upSql="update bankandinsumap  B set B.bak1=date10to8('"+bak1+"'),B.bak2=date10to8('"+bak2+"'), B.bak3='"; 
			upSql+=bak3+ "' WHERE 1 = 1 AND B.CODETYPE='RiskCode' AND B.INSU_CODE='"+mASCode+"' and B.TRANCOM in ";
			upSql+="(select code from ldcode where codetype='bat_com' and codename='"+mUpBank+"') and EXISTS "; 
			upSql+="(SELECT 'X' FROM LMRIsKAPP M WHERE M.RISKCODE= B.INSU_CODE AND M.SUBRISKFLAG !='S') ";
			upSql+=" and b.tran_code='"+mProCode+"'";
			boolean bl = new ExeSQL(mLocalConn).execUpdateSQL(upSql);
			if(bl==false){
				mLocalConn.rollback();
				mLocalConn.close();
				throw new MidplatException("更新有误！" );
			}
			if (null != mLocalConn) {
				try {
					System.out.println("准备提交");
					mLocalConn.commit();
					System.out.println("准备完毕");
					mLocalConn.close();
				} catch (Exception ex) {
					cLogger.error("关闭LocalConnection异常！", ex);
				}
			}
		}
		else{
			throw new MidplatException("操作码有误！" + mOperType);
		}
	

		mFlag = "Succ";
		mMessage = "操作成功！";
		}
	} catch (Exception ex) {
		cLogger.error("操作失败！", ex);

		mFlag = "Fail";
		mMessage = "操作失败：" + ex.getMessage();
	}

	cLogger.info("out NodeManageSave.jsp!");
%>
<html>
	<script language="javascript">
parent.fraInterface.afterSubmit("<%=mFlag%>", "<%=mMessage%>");
</script>
</html>