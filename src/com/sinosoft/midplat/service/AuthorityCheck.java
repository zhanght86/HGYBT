package com.sinosoft.midplat.service;

/**
 * <p>Title:        ������ϢУ��</p>
 * <p>Description:  ����������״̬�Ƿ���Ч </p>
 * <p>Copyright:    Copyright (c) 2006.08.26</p>
 * <p>Company:      ��˾����picc</p>
 * @author
 * @version         1.0
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;


import com.sinosoft.lis.db.LKTransAuthorizationDB;
import com.sinosoft.lis.db.NodeMapDB;
import com.sinosoft.lis.schema.LKTransAuthorizationSchema;
import com.sinosoft.lis.schema.NodeMapSchema;
import com.sinosoft.lis.vschema.LKTransAuthorizationSet;
import com.sinosoft.midplat.common.XmlTag;
import com.sinosoft.utility.CError;
import com.sinosoft.utility.CErrors;

/**
 * @author Administrator
 */
public class AuthorityCheck implements XmlTag
{

	public AuthorityCheck() {
		

	}

	private Connection g_Connection = null;

	PreparedStatement pstmt = null;

	ResultSet rs = null;

	/** �������࣬ÿ����Ҫ����������ж����ø��� */
	public CErrors mErrors = new CErrors();

	/** �������ݵ��� */
	Element mHeadEle = null;

	/** ��־��Ϣ */
	private Logger mLogger = Logger.getLogger(AuthorityCheck.class);



	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
	//	AuthorityCheck tAuthor = new AuthorityCheck();
	//	TXLifeRequest cTXLifeRequest = new TXLifeRequest();
	//	System.out.println(tAuthor.submitData(cTXLifeRequest));

	}

	public boolean submitData(Element mHeadEle)
	{
		mLogger.info("--Start AuthorityCheck submitData...");
		// mTXLifeRequest = cTXLifeRequest;
		// �õ��ⲿ���������,�����ݱ��ݵ�������
		if (!getInputData(mHeadEle))
		{
			// @@������
			CError tError = new CError();
			tError.moduleName = "AuthorityCheck";
			tError.functionName = "getInputData";
			tError.errorMessage = "�������ݶ�ʧAuthorityCheck-->getInputData��";
			this.mErrors.addOneError(tError);
			System.out.println("false");
			return false;
		}
		// �����߼�����
		if (!dealData())
		{
			// @@������
			CError tError = new CError();
			tError.moduleName = "AuthorityCheck";
			tError.functionName = "dealData";
			tError.errorMessage = "���ݶ�ʧAuthorityCheck-->dealData��";
			this.mErrors.addOneError(tError);
			return false;
		}
		// ׼������̨������
		if (!prepareOutputData())
		{
			// @@������
			CError tError = new CError();
			tError.moduleName = "AuthorityCheck";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "������̨�����ݶ�ʧAuthorityCheck-->prepareOutputData��";
			this.mErrors.addOneError(tError);
			return false;
		}
		mLogger.info("--End AuthorityCheck submitData...");
		return true;
	}

	public boolean dealData()
	{ 
		try
		{
			mLogger.info("----Start AuthorityCheck dealData...");
			/* ��������ӳ�䴦�� */
			mLogger.info("------Start AuthorityCheck dealData��������ӳ�䴦��...");
						   
			String mTranCom = mHeadEle.getChildText(TranCom); 
			String mNodeNo = mHeadEle.getChildText(NodeNo); 
			String mFuncFlag = mHeadEle.getChildText(FuncFlag);
			
			NodeMapSchema mNodeMapSchema = new NodeMapSchema();
//			NodeMapDB mNodeMapDB = new NodeMapDB();
			mNodeMapSchema.setTranCom(mTranCom);
			mNodeMapSchema.setNodeNo(mNodeNo);
//			mNodeMapDB.setSchema(mNodeMapSchema);
//			mNodeMapDB.getInfo();
//			mNodeMapSchema.setSchema(mNodeMapDB);
			/* ������Ȩ���� */ 
			LKTransAuthorizationSchema mLKTransAuthorizationSchema = new LKTransAuthorizationSchema();
			LKTransAuthorizationSet mLKTransAuthorizationSet = new LKTransAuthorizationSet();
			LKTransAuthorizationDB mLKTransAuthorizationDB = new LKTransAuthorizationDB();
			mLogger.info("------Start AuthorityCheck dealData������Ȩ����...");
			mLKTransAuthorizationSchema.setBankCode(mTranCom);
			
			//����ǹ���
			if(mTranCom.equals("1")||mNodeNo.length()== 10){
			mLogger.info("------Start AuthorityCheck dealData ��������...");
			mLKTransAuthorizationSchema.setBankBranch(mNodeNo.substring(0, 5));
			mLKTransAuthorizationSchema.setBankNode(mNodeNo.substring(5, 10));
			}else { 
//				mLogger.info("------Start AuthorityCheck dealData ..."+mTranCom+":"+mNodeNo);
//				return false; 
				mLKTransAuthorizationSchema.setBankBranch(mNodeNo);
				mLKTransAuthorizationSchema.setBankNode(mNodeNo);
				} 
			
			/* ͨ����������ӳ��Ĺ���������ý�����Ȩ�Ĺ������ */
//			String tManageCom = mNodeMapSchema.getManageCom();
//			if (tManageCom == null || tManageCom.equals(""))
//			{
//				mLogger.info("NodeMapSchema.getManageCom is null!!");
//				return true;  
//			}
//			else
//			{
				mLogger.info("LKTransAuthorizationSchema ��0��");
				//mLKTransAuthorizationSchema.setManageCom(mNodeMapSchema.getManageCom());// .substring(0,4));
				mLKTransAuthorizationSchema.setManageCom("8621");// .substring(0,4));
				mLKTransAuthorizationSchema.setFuncFlag(mFuncFlag);
				mLKTransAuthorizationDB.setSchema(mLKTransAuthorizationSchema);
				mLKTransAuthorizationSet = mLKTransAuthorizationDB.query();
//			} 
 
			if (mLKTransAuthorizationSet != null && mLKTransAuthorizationSet.size() > 0)
			{ 
				mLogger.info("LKTransAuthorizationSchema ��0��");
				return false;
			}
			else
			{   mLogger.info("LKTransAuthorizationSchema ��һ��");
				mLKTransAuthorizationSchema.setBankNode("***");
				mLKTransAuthorizationDB.setSchema(mLKTransAuthorizationSchema);
				mLKTransAuthorizationSet = mLKTransAuthorizationDB.query();
				if (mLKTransAuthorizationSet != null && mLKTransAuthorizationSet.size() > 0)
				{
					mLogger.info("LKTransAuthorizationSchema ��һ��");
					return false;
				}
				else
				{   mLogger.info("LKTransAuthorizationSchema �ڶ���");
					mLKTransAuthorizationSchema.setBankBranch("***");
					mLKTransAuthorizationDB.setSchema(mLKTransAuthorizationSchema);
					mLKTransAuthorizationSet = mLKTransAuthorizationDB.query();
					if (mLKTransAuthorizationSet != null && mLKTransAuthorizationSet.size() > 0)
					{   mLogger.info("LKTransAuthorizationSchema �ڶ���");
						return false;
					}
					else
					{
						mLogger.info("----End AuthorityCheck dealData...");
						return true;

					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	public boolean getInputData(Element mHeadEle)
	{
		mLogger.info("----Start AuthorityCheck getInputData...");
		this.mHeadEle = mHeadEle;
		mLogger.info("----End AuthorityCheck getInputData...");
		return true;
	}

	public boolean prepareOutputData()
	{
		return true;
	}
}
