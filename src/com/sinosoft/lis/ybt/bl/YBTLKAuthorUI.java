/*
 * <p>ClassName: LKCodeUI </p>
 * <p>Description: LKCodeUI���ļ� </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: sinosoft </p>
 * @Database: �����������
 * @CreateDate��2005-08-30
 */
package com.sinosoft.lis.ybt.bl;

import com.sinosoft.lis.pubfun.GlobalInput;

import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.VData;
import com.sinosoft.lis.schema.LKTransAuthorizationSchema;


public class YBTLKAuthorUI
{
    /** �������࣬ÿ����Ҫ����������ж����ø��� */
    public CErrors mErrors = new CErrors();
    /** ��Ų�ѯ��� */
    private VData mResult = new VData();
    /** �����洫�����ݵ����� */
    private VData mInputData = new VData();
    /** ���ݲ����ַ��� */
    private String mOperate;
    //ҵ������ر���
    /** ȫ������ */
    private GlobalInput mGlobalInput = new GlobalInput();
    private LKTransAuthorizationSchema mLKTransAuthorSchema = new LKTransAuthorizationSchema();

 
    public YBTLKAuthorUI()
    {
    }

    /**
       �������ݵĹ�������
     */
    public boolean submitData(VData cInputData, String cOperate)
    {
        //���������ݿ�����������
        this.mOperate = cOperate;
        //�õ��ⲿ���������,�����ݱ��ݵ�������
        if (!getInputData(cInputData))
        {
            return false;
        }
        System.out.println("over getInputData");
        //����ҵ����
        if (!dealData())
        {
            return false;
        }
        System.out.println("over dealData");
        //׼������̨������
        //if (!prepareOutputData())
        //{/
        //    return false;
        //}
        System.out.println("over prepareOutputData");
        YBTLKAuthorBL tYBTLKAuthorBL = new YBTLKAuthorBL();
        System.out.println("Start LKCode UI Submit...");
        tYBTLKAuthorBL.submitData(cInputData, mOperate);
        System.out.println("End LKCode UI Submit...");
        //�������Ҫ����Ĵ����򷵻�
        if (tYBTLKAuthorBL.mErrors.needDealError())
        {
            // @@������
            this.mErrors.copyAllErrors(tYBTLKAuthorBL.mErrors);
            CError tError = new CError();
            tError.moduleName = "YBTLKCodeUI";
            tError.functionName = "submitData";
            tError.errorMessage = "�����ύʧ��!";
            this.mErrors.addOneError(tError);
            return false;
        }
        this.mResult.clear();
        this.mResult = tYBTLKAuthorBL.getResult();
        mInputData = null;
        return true;
    }

    public static void main(String[] args)
    {

    }

    /**
     * ׼��������������Ҫ������
     * ��������׼������ʱ���������򷵻�false,���򷵻�true
     */
    private boolean prepareOutputData()
    {
        try
        {
            mInputData.clear();
            mInputData.add(this.mGlobalInput);
            mInputData.add(this.mLKTransAuthorSchema);

        }
        catch (Exception ex)
        {
            // @@������
            CError tError = new CError();
            tError.moduleName = "YBTLKCodeUI";
            tError.functionName = "prepareData";
            tError.errorMessage = "��׼������㴦������Ҫ������ʱ����";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    /**
     * ����ǰ����������ݣ�����UI�߼�����
     * ����ڴ�������г����򷵻�false,���򷵻�true
     */
    private boolean dealData()
    {
        boolean tReturn = false;
        String SQL = "";
        tReturn = true;
        return tReturn;
    }


    /**
     * �����������еõ����ж���
     *��������û�еõ��㹻��ҵ�����ݶ����򷵻�false,���򷵻�true
     */
    private boolean getInputData(VData cInputData)
    {
        //ȫ�ֱ���
        mGlobalInput.setSchema((GlobalInput) cInputData.getObjectByObjectName(
                "GlobalInput", 0));
       // this.mLKCodeMappingSchema.setSchema((LKCodeMappingSchema) cInputData.getObjectByObjectName("LKCodeMappingSchema", 0));

        if (mGlobalInput == null)
        {
            // @@������
            CError tError = new CError();
            tError.moduleName = "YBTLKCodeUI";
            tError.functionName = "getInputData";
            tError.errorMessage = "û�õ��㹻����Ϣ��";
            this.mErrors.addOneError(tError);
            return false;
        }
        return true;
    }

    public VData getResult()
    {
        return this.mResult;
    }

}
