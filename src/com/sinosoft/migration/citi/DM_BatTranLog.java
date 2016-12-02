package com.sinosoft.migration.citi;



import com.sinosoft.lis.db.BatTranLogDB;
import com.sinosoft.lis.pubfun.MMap;
import com.sinosoft.lis.pubfun.PubSubmit;
import com.sinosoft.lis.schema.BatTranLogSchema;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.utility.ExeSQL;
import com.sinosoft.utility.SSRS;
import com.sinosoft.utility.VData;


public class DM_BatTranLog {
	
	
	
	public static void main(String[] args) throws Exception {
	  
	  MMap cSubmitMMap = new MMap();
	  String sql="select distinct(extracteddate) from policymaster";
	  SSRS tSSRS = new SSRS();
	  ExeSQL texeSql = new ExeSQL();
	  tSSRS = texeSql.execSQL(sql);
	   
	  for (int i=1;i<=tSSRS.MaxRow;i++){
		    BatTranLogSchema mBatTranLogDB = new BatTranLogSchema();
			mBatTranLogDB.setLogNo(NoFactory.nextBatTranLogNo());
			mBatTranLogDB.setTranCom("021");
			mBatTranLogDB.setNodeNo("-");
			mBatTranLogDB.setZoneNo("-");
			mBatTranLogDB.setBatNo(String.valueOf(mBatTranLogDB.getLogNo()));
			mBatTranLogDB.setType(1);
			mBatTranLogDB.setFuncFlag("401");
			mBatTranLogDB.setTranDate(DateUtil.date8to10(tSSRS.GetText(i, 1)));
			mBatTranLogDB.setTranTime(DateUtil.getCur8Time());
			mBatTranLogDB.setRCode(0);
			mBatTranLogDB.setRText("数据处理成功！");
			mBatTranLogDB.setUsedTime(1);
			mBatTranLogDB.setOperator("sys");
			mBatTranLogDB.setMakeDate(DateUtil.getCur10Date());
			mBatTranLogDB.setMakeTime(DateUtil.getCur8Time());
			mBatTranLogDB.setModifyDate(mBatTranLogDB.getMakeDate());
			mBatTranLogDB.setModifyTime(mBatTranLogDB.getMakeTime());
			cSubmitMMap.put(mBatTranLogDB, "INSERT");		  
	  }
	 
	  VData mSubmitVData = new VData();
		mSubmitVData.add(cSubmitMMap);
		PubSubmit mPubSubmit = new PubSubmit();
		if (!mPubSubmit.submitData(mSubmitVData, ""))
		{ 
			throw new Exception(mPubSubmit.mErrors.getFirstError());

		}
		

  }
}

