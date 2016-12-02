
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
	//��������(INSERT/UPDATE/DELETE)
	String mOperType = request.getParameter("OperType");	
	String miBankCode = request.getParameter("iBankCode");	
	String miBankName = request.getParameter("iBankName");
	String miRiskCode = request.getParameter("iRiskCode");
	String miRiskName = request.getParameter("iRiskName");
	//��Ʒ����ʱ�Ĳ�Ʒ����
	String mProCode = request.getParameter("ProCode");
	String miStartDate = "2000-01-01";
	String miEndDate = "2000-01-01";
	String miComCode = request.getParameter("iComCode");
	String miComCodeName = request.getParameter("iComCodeName");
	String mStaDate = request.getParameter("StaDate");
	String mEndSDate = request.getParameter("EndSDate");
	String mASCode =request.getParameter("ASCode");
	String mUpBank = request.getParameter("UpBank");
	// ��Ʒ���롪���������������滻LMRiskMap�е�Insure_code
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
			System.out.println("INSERT--����ͣ�۲���");
			String sql="";
			SSRS ssrs =new SSRS();
			//���������ʡ���м�������账�������账��
			if(miComCode.length()!=0){
				if(miComCode.length()==9){
//					//���comcodeΪ��9λ��ֱ���ж���ʱ���Ƿ��ڱ���Ʒ��ʱ�䷶Χ�ڡ�
				vdate.add(tTransferData);
				vdate.add(cGlobalInput);
				ProductManageUI aProductManageUI = new ProductManageUI(vdate);
						aProductManageUI.insert();
				}else {
					//���comcodeΪ����9λ
					//�Ȳ�ѯldcom�еȼ�Ϊ04����comcodeǰ��λ��ҳ�洫����ֵ��ͬ�����Ҳ���lmriskmap���У��ٽ�����һ���
					sql="select comcode from ldcom where comgrade='04' and comcode like '"+miComCode+"%'" ;
					sql+=" and comcode not in (select comcode from lmriskmap where bankcode='"+miBankCode;
					sql+="' and insucode='"+miProductCode+"')";
					ssrs= new ExeSQL().execSQL(sql);
					System.out.println("�������Ϊ��"+ssrs.getMaxRow());
					if(ssrs.getMaxRow()>0){
						//ѭ���������
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
						throw new MidplatException("������ʡ�����е��ж��ò�Ʒ����ͣ�ۣ�");
					}
				}
				}else {
					throw new MidplatException("��Ʒ��������Ϊ�գ�");
				}
		} else if ("UPDATE".equals(mOperType)) {
			System.out.println("UPDATE����");
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
						throw new MidplatException("��Ʒ����������ʾ���ڱ�����"+bak1+"��"+bak2+"֮��");
					}
				}else {
					throw new MidplatException("�ܲ��˲�Ʒ����'����'״̬����������Ȩ�ޣ�");
				}
				}else {
					throw new MidplatException("�ܲ��˲�Ʒ�����ڴ˲�����");
				}
		} else if ("DELETE".equals(mOperType)) {
			System.out.println("DELETE--ȡ��ͣ�۲���");
           String sql="";
           SSRS ssrs =new SSRS();
			//���comcodeΪ����9λ
			//�Ȳ�ѯldcom�еȼ�Ϊ04����comcodeǰ��λ��ҳ�洫����ֵ��ͬ�����Ҳ���lmriskmap���У��ٽ�����һ���
			sql="select comcode from LMRISKMAP where codetype='risk_com' and comcode like '"+miComCode+"%'" ;
			sql+=" and bankcode='"+miBankCode;
			sql+="' and insucode='"+miProductCode+"'";
			ssrs= new ExeSQL().execSQL(sql);
			System.out.println("�������Ϊ��"+ssrs.getMaxRow());
			if(ssrs.getMaxRow()>0){
				//ѭ���������
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
				throw new MidplatException("������ʡ�����е��ж��ò�Ʒ������ͣ��״̬��");
			}
		} else if("RISKUPDATE".equals(mOperType)){
			System.out.println("RISKUPDATE���������ܹ�˾��Ʒ���²���");
			String currentTime=DateUtil.getCur10Date();
			String bak1,bak2,bak3="";
			bak1=mStaDate;
			bak2=mEndSDate;
			if (mStaDate.compareTo(mEndSDate)>0){
				throw new MidplatException("��ʼ���ڲ���������ֹ����!");
			}else if(mEndSDate.compareTo(currentTime)<0){
				bak3="2";
			}else if(mStaDate.compareTo(currentTime)>0){
				bak3="1";
			}else if((mStaDate.compareTo(currentTime)<=0)&&(mEndSDate.compareTo(currentTime)>=0)){
				bak3="0";
			}
			//���������ύ���棬�Ƚ��Զ��ύ��Ϊfalse,���ִ��ʧ�ܣ��ͻع���ִ�гɹ������ύ���ر����ӡ�
			Connection mLocalConn = DBConnPool.getConnection();
			mLocalConn.setAutoCommit(false);
			//�����Ǳ����Լ�д�ĸ�����䣬��Ӧ��JAVA���д��������ڱ��˶Թ�˾�Ļ�������������
			//�����λ���˿��԰�æ���ͣ���ʤ�м���         zfs
			String upSql="update bankandinsumap  B set B.bak1=date10to8('"+bak1+"'),B.bak2=date10to8('"+bak2+"'), B.bak3='"; 
			upSql+=bak3+ "' WHERE 1 = 1 AND B.CODETYPE='RiskCode' AND B.INSU_CODE='"+mASCode+"' and B.TRANCOM in ";
			upSql+="(select code from ldcode where codetype='bat_com' and codename='"+mUpBank+"') and EXISTS "; 
			upSql+="(SELECT 'X' FROM LMRIsKAPP M WHERE M.RISKCODE= B.INSU_CODE AND M.SUBRISKFLAG !='S') ";
			upSql+=" and b.tran_code='"+mProCode+"'";
			boolean bl = new ExeSQL(mLocalConn).execUpdateSQL(upSql);
			if(bl==false){
				mLocalConn.rollback();
				mLocalConn.close();
				throw new MidplatException("��������" );
			}
			if (null != mLocalConn) {
				try {
					System.out.println("׼���ύ");
					mLocalConn.commit();
					System.out.println("׼�����");
					mLocalConn.close();
				} catch (Exception ex) {
					cLogger.error("�ر�LocalConnection�쳣��", ex);
				}
			}
		}
		else{
			throw new MidplatException("����������" + mOperType);
		}
	

		mFlag = "Succ";
		mMessage = "�����ɹ���";
		}
	} catch (Exception ex) {
		cLogger.error("����ʧ�ܣ�", ex);

		mFlag = "Fail";
		mMessage = "����ʧ�ܣ�" + ex.getMessage();
	}

	cLogger.info("out NodeManageSave.jsp!");
%>
<html>
	<script language="javascript">
parent.fraInterface.afterSubmit("<%=mFlag%>", "<%=mMessage%>");
</script>
</html>