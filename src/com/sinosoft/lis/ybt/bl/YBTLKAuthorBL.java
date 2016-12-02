/*
 * <p>ClassName: LKCodeBL </p>
 * <p>Description: LKCodeBL���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: �����������
 * @CreateDate��2005-08-30
 */
package com.sinosoft.lis.ybt.bl;


import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubFun;
import com.sinosoft.lis.pubfun.PubFun1;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.*;
import com.sinosoft.lis.vschema.*;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.Reflections;
import com.sinosoft.utility.VData;
import com.sinosoft.utility.ExeSQL;
import java.math.BigInteger;
import com.sinosoft.utility.TransferData;

public class YBTLKAuthorBL
{
    /** �������࣬ÿ����Ҫ����������ж����ø��� */
    public CErrors mErrors = new CErrors();
    private VData mResult = new VData();
    /** �����洫�����ݵ����� */
    private VData mInputData;
    /** ȫ������ */
    private GlobalInput mGlobalInput = new GlobalInput();
    /** ���ݲ����ַ��� */
    private String mOperate;
    /** ҵ������ر��� */
    private LKTransAuthorizationSchema mLKTransAuthorizationSchema = new LKTransAuthorizationSchema();
    private LKTransAuthorizationSet mLKTransAuthorizationSet = new LKTransAuthorizationSet();
    private TransferData mTransferData = null;
    private MMap map = new MMap();

    public YBTLKAuthorBL()
    {}

    public static void main(String[] args)
    {

    }

    /**
     * �������ݵĹ�������
     * @param: cInputData ���������
     *         cOperate ���ݲ���
     * @return:
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //���������ݿ�����������
        this.mOperate = cOperate;
        System.out.println(mOperate + "_ " + cOperate);
        //�õ��ⲿ���������,�����ݱ��ݵ�������
        if (!getInputData(cInputData))
        {
            return false;
        }


        if (!dealData())
        {
            // @@������
            CError tError = new CError();
            tError.moduleName = "YBTLKCodeBL";
            tError.functionName = "submitData";
            tError.errorMessage = "���ݴ���ʧ��YBTLKCodeBL-->dealData!";
            this.mErrors.addOneError(tError);
            return false;
        }

        //׼������̨������
        if (!prepareOutputData())
        {
            return false;
        }
        mInputData = null;
        return true;
    }




    /**
     * ����ǰ����������ݣ�����BL�߼�����
     * ����ڴ�������г����򷵻�false,���򷵻�true
     */
    private boolean dealData()
    {
        String currentTime = PubFun.getCurrentTime();
        
        if (this.mOperate.equals("INSERT||MAIN"))
        { 
           /* mLKTransAuthorizationSchema.setBankCode((String)mTransferData.getValueByName("BankCode"));
            mLKTransAuthorizationSchema.setBankBranch((String)mTransferData.getValueByName("BankBranch"));
            mLKTransAuthorizationSchema.setBankNode((String)mTransferData.getValueByName("BankNode"));
            mLKTransAuthorizationSchema.setManageCom((String)mTransferData.getValueByName("ManageCom"));
            mLKTransAuthorizationSchema.setFuncFlag((String)mTransferData.getValueByName("FuncFlag"));
			mLKTransAuthorizationSchema.setOperator(mGlobalInput.Operator);
             if (mLKTransAuthorizationSchema.getBankNode()==null || mLKTransAuthorizationSchema.getBankNode().trim().equals(""))
			 {
				 mLKTransAuthorizationSchema.setBankNode("***");
			 }
			 if(mLKTransAuthorizationSchema.getBankBranch()==null || mLKTransAuthorizationSchema.getBankBranch().trim().equals(""))
			 {
				 mLKTransAuthorizationSchema.setBankBranch("***");
			 }

            this.mLKTransAuthorizationSchema.setMakeDate(DateUtil.getCur8Date());
            this.mLKTransAuthorizationSchema.setMakeTime(currentTime);
            this.mLKTransAuthorizationSchema.setModifyDate(DateUtil.getCur8Date()); 
            this.mLKTransAuthorizationSchema.setModifyTime(currentTime);
            map.put(mLKTransAuthorizationSchema,"INSERT");*/
        }

        else if (this.mOperate.equals("DEL||MAIN"))
        {
            String BankCode=(String)mTransferData.getValueByName("BankCode");
           String BankBranch=(String)mTransferData.getValueByName("BankBranch");
           String BankNode=(String)mTransferData.getValueByName("BankNode");
		   String ManageCom = (String)mTransferData.getValueByName("ManageCom");
		   String FuncFlag = (String)mTransferData.getValueByName("FuncFlag");
        if(BankNode == null || BankNode.trim().equals("") )
		{
			BankNode = "***";
		}
		if(BankBranch == null || BankBranch.trim().equals(""))
		{
			BankBranch = "***";
		}

            String sql= "DELETE from LKTransAuthorization  where BankCode='"+BankCode.trim()+"' and  bankbranch='"+BankBranch.trim()+"' and BankNode='"+BankNode.trim()+"' and managecom ='"+ManageCom.trim()+"' and funcflag = '" + FuncFlag + "'" ;

            map.put(sql,"DELETE");
        }


        PubSubmit tSubmit = new PubSubmit();
        VData tInputData = new VData();
        tInputData.add(map);
        if (!tSubmit.submitData(tInputData, ""))
        {
            this.mErrors.copyAllErrors(tSubmit.mErrors);
            CError tError = new CError();
            tError.moduleName = "YBTLKCodeBL";
            tError.functionName = "dealData";
            tError.errorMessage = "�����ύʧ�ܡ�";
            this.mErrors.addOneError(tError);
            return false;
        }


        return true;
    }

    /**
     * �����������еõ����ж���
     *��������û�еõ��㹻��ҵ�����ݶ����򷵻�false,���򷵻�true
     */
    private boolean getInputData(VData cInputData)
    {
        //ȫ�ֱ���
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName("GlobalInput", 0));
        this.mTransferData=(TransferData) cInputData.getObjectByObjectName("TransferData", 0);

        if (mGlobalInput == null||mTransferData==null)
        {
            // @@������
            CError tError = new CError();
            tError.moduleName = "YBTLKCodeBL";
            tError.functionName = "getInputData";
            tError.errorMessage = "û�õ��㹻����Ϣ��";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;

    }

    /**
     * ׼��������������Ҫ������
     * ��������׼������ʱ���������򷵻�false,���򷵻�true
     */

    private boolean prepareOutputData()
    {
        try
        {
            this.mInputData = new VData();
            this.mInputData.add(mLKTransAuthorizationSchema);
        }
        catch (Exception ex)
        {
            // @@������
            CError tError = new CError();
            tError.moduleName = "YBTLKCodeBL";
            tError.functionName = "prrea";
            tError.errorMessage = "��׼����㴦������Ҫ������ʱ����";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public VData getResult()
    {
        mResult = new VData();
        mResult.add(mLKTransAuthorizationSchema);
        return this.mResult;
    }

}
