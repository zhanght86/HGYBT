package com.sinosoft.midplat.service;

/**
 * <p>Title:        网点信息校验</p>
 * <p>Description:  检验网点是状态是否有效 </p>
 * <p>Copyright:    Copyright (c) 2006.08.26</p>
 * <p>Company:      公司名称picc</p>
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

	/** 错误处理类，每个需要错误处理的类中都放置该类 */
	public CErrors mErrors = new CErrors();

	/** 请求数据的类 */
	Element mHeadEle = null;

	/** 日志信息 */
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
		// 得到外部传入的数据,将数据备份到本类中
		if (!getInputData(mHeadEle))
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AuthorityCheck";
			tError.functionName = "getInputData";
			tError.errorMessage = "输入数据丢失AuthorityCheck-->getInputData！";
			this.mErrors.addOneError(tError);
			System.out.println("false");
			return false;
		}
		// 进行逻辑处理
		if (!dealData())
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AuthorityCheck";
			tError.functionName = "dealData";
			tError.errorMessage = "数据丢失AuthorityCheck-->dealData！";
			this.mErrors.addOneError(tError);
			return false;
		}
		// 准备往后台的数据
		if (!prepareOutputData())
		{
			// @@错误处理
			CError tError = new CError();
			tError.moduleName = "AuthorityCheck";
			tError.functionName = "prepareOutputData";
			tError.errorMessage = "传往后台的数据丢失AuthorityCheck-->prepareOutputData！";
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
			/* 机构网点映射处理 */
			mLogger.info("------Start AuthorityCheck dealData机构网点映射处理...");
						   
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
			/* 交易授权处理 */ 
			LKTransAuthorizationSchema mLKTransAuthorizationSchema = new LKTransAuthorizationSchema();
			LKTransAuthorizationSet mLKTransAuthorizationSet = new LKTransAuthorizationSet();
			LKTransAuthorizationDB mLKTransAuthorizationDB = new LKTransAuthorizationDB();
			mLogger.info("------Start AuthorityCheck dealData交易授权处理...");
			mLKTransAuthorizationSchema.setBankCode(mTranCom);
			
			//如果是工行
			if(mTranCom.equals("1")||mNodeNo.length()== 10){
			mLogger.info("------Start AuthorityCheck dealData 工行网点...");
			mLKTransAuthorizationSchema.setBankBranch(mNodeNo.substring(0, 5));
			mLKTransAuthorizationSchema.setBankNode(mNodeNo.substring(5, 10));
			}else { 
//				mLogger.info("------Start AuthorityCheck dealData ..."+mTranCom+":"+mNodeNo);
//				return false; 
				mLKTransAuthorizationSchema.setBankBranch(mNodeNo);
				mLKTransAuthorizationSchema.setBankNode(mNodeNo);
				} 
			
			/* 通过机构网点映射的管理机构设置交易授权的管理机构 */
//			String tManageCom = mNodeMapSchema.getManageCom();
//			if (tManageCom == null || tManageCom.equals(""))
//			{
//				mLogger.info("NodeMapSchema.getManageCom is null!!");
//				return true;  
//			}
//			else
//			{
				mLogger.info("LKTransAuthorizationSchema 第0步");
				//mLKTransAuthorizationSchema.setManageCom(mNodeMapSchema.getManageCom());// .substring(0,4));
				mLKTransAuthorizationSchema.setManageCom("8621");// .substring(0,4));
				mLKTransAuthorizationSchema.setFuncFlag(mFuncFlag);
				mLKTransAuthorizationDB.setSchema(mLKTransAuthorizationSchema);
				mLKTransAuthorizationSet = mLKTransAuthorizationDB.query();
//			} 
 
			if (mLKTransAuthorizationSet != null && mLKTransAuthorizationSet.size() > 0)
			{ 
				mLogger.info("LKTransAuthorizationSchema 第0步");
				return false;
			}
			else
			{   mLogger.info("LKTransAuthorizationSchema 第一步");
				mLKTransAuthorizationSchema.setBankNode("***");
				mLKTransAuthorizationDB.setSchema(mLKTransAuthorizationSchema);
				mLKTransAuthorizationSet = mLKTransAuthorizationDB.query();
				if (mLKTransAuthorizationSet != null && mLKTransAuthorizationSet.size() > 0)
				{
					mLogger.info("LKTransAuthorizationSchema 第一步");
					return false;
				}
				else
				{   mLogger.info("LKTransAuthorizationSchema 第二步");
					mLKTransAuthorizationSchema.setBankBranch("***");
					mLKTransAuthorizationDB.setSchema(mLKTransAuthorizationSchema);
					mLKTransAuthorizationSet = mLKTransAuthorizationDB.query();
					if (mLKTransAuthorizationSet != null && mLKTransAuthorizationSet.size() > 0)
					{   mLogger.info("LKTransAuthorizationSchema 第二步");
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
