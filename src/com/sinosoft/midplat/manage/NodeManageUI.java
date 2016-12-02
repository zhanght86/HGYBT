/**
 * 网点管理。
 */

package com.sinosoft.midplat.manage;

import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.lis.db.NodeMapDB;
import com.sinosoft.lis.pubfun.GlobalInput;
import com.sinosoft.midplat.common.DateUtil;
import com.sinosoft.midplat.common.NoFactory;
import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.ExeSQL;

public class NodeManageUI {
	private final static Logger cLogger = Logger.getLogger(NodeManageUI.class);
	
	private final GlobalInput cGlobalInput;
	
	public NodeManageUI(GlobalInput pGlobalInput) {
		cGlobalInput = pGlobalInput;
	}
	
	public void insertNode(
			String pNodeType
			, String pTranCom, String pNodeNo
			, String pAgentCom, String pAgentComName, String pAgentCode, String pAgentName, String pManageCom)
		throws MidplatException {
		cLogger.info("Into NodeManageUI.insertNode()...");
		
		if (!pManageCom.startsWith(cGlobalInput.ManageCom)) {
			throw new MidplatException("您的当前登录用户无权操作该网点！");
		}
		
		String mSqlStr = new StringBuilder("select count(1) from NodeMap where Type=").append(pNodeType)
			.append(" and TranCom='").append(pTranCom).append('\'')
			.append(" and NodeNo='").append(pNodeNo).append('\'')
			.toString();
		if (!"0".equals(new ExeSQL().getOneValue(mSqlStr))) {
			throw new MidplatException("该网点已存在！");
		}
		
		NodeMapDB mNodeMapDB = new NodeMapDB();
		mNodeMapDB.setMapNo(NoFactory.nextNodeMapNo());
		mNodeMapDB.setTranCom(Integer.parseInt(pTranCom));
		mNodeMapDB.setNodeNo(pNodeNo);
		mNodeMapDB.setType(Integer.parseInt(pNodeType));
		mNodeMapDB.setAgentCom(pAgentCom);
		mNodeMapDB.setAgentComName(pAgentComName);
		mNodeMapDB.setAgentCode(pAgentCode);
		mNodeMapDB.setAgentName(pAgentName); 
		mNodeMapDB.setManageCom(pManageCom);  
		Date mCurDate = new Date();
		mNodeMapDB.setMakeDate(DateUtil.get8Date(mCurDate));
		mNodeMapDB.setMakeTime(DateUtil.get6Time(mCurDate));
		mNodeMapDB.setModifyDate(mNodeMapDB.getMakeDate());
		mNodeMapDB.setModifyTime(mNodeMapDB.getMakeTime());
		mNodeMapDB.setOperator(cGlobalInput.Operator);
		if (!mNodeMapDB.insert()) {
			cLogger.error(mNodeMapDB.mErrors.getFirstError());
			throw new MidplatException("插入网点信息失败！");
		}
		
		cLogger.info("Out NodeManageUI.insertNode()!");
	}
	
	public void updateNode(
			String pMapNo
			, String pTranCom, String pNodeNo
			, String pAgentCom, String pAgentComName, String pAgentCode, String pAgentName, String pManageCom)
		throws MidplatException {
		cLogger.info("Into NodeManageUI.updateNode()...");
		
		if (!pManageCom.startsWith(cGlobalInput.ManageCom)) {
			throw new MidplatException("您的当前登录用户无权操作该网点！");
		} 
		
		Date mCurDate = new Date();
		String mSqlStr = new StringBuilder("update NodeMap set TranCom=").append(pTranCom)
			.append(", NodeNo='").append(pNodeNo).append('\'')
			.append(", AgentCom='").append(pAgentCom).append('\'')
			.append(", AgentComName='").append(pAgentComName).append('\'')
			.append(", AgentCode='").append(pAgentCode).append('\'')
			.append(", AgentName='").append(pAgentName).append('\'')
			.append(", ManageCom='").append(pManageCom).append('\'')
			.append(", ModifyDate=").append(DateUtil.get8Date(mCurDate))
			.append(", ModifyTime=").append(DateUtil.get6Time(mCurDate))
			.append(", Operator='").append(cGlobalInput.Operator).append('\'')
			.append(" where MapNo=").append(pMapNo)
			.append(" and ManageCom like '").append(cGlobalInput.ManageCom).append("%'")
			.toString();
		ExeSQL mExeSQL = new ExeSQL();
      if (!mExeSQL.execUpdateSQL(mSqlStr)) {
      	cLogger.error(mExeSQL.mErrors.getFirstError());
			throw new MidplatException("更新网点失败！");
      }
      
		cLogger.info("Out NodeManageUI.updateNode()!");
	}
	
	public void deleteNode(String pMapNo) throws MidplatException {
		cLogger.info("Into NodeManageUI.deleteNode()...");
		
      String mSqlStr = "delete from NodeMap where MapNo="+pMapNo+" and ManageCom like '"+cGlobalInput.ManageCom+"%'";
      ExeSQL mExeSQL = new ExeSQL();
      if (!mExeSQL.execUpdateSQL(mSqlStr)) {
      	cLogger.error(mExeSQL.mErrors.getFirstError());
			throw new MidplatException("删除网点失败！");
      }
      
		cLogger.info("Out NodeManageUI.deleteNode()!");
	}
}
