/**
 * $RCSfile: LKPolicyXMLDao.java
 * $Revision: 1.0
 * $Date: 2015-4-12
 *
 * Copyright (C) 2010 SinoSoft, Inc. All rights reserved.
 *
 * This software is the proprietary information of SinoSoft, Inc.
 * Use is subject to license terms.
 */
package com.sinosoft.midplat.newccb.dao;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import oracle.sql.BLOB;

import org.apache.commons.httpclient.util.DateUtil;
import org.apache.log4j.Logger;

import com.sinosoft.midplat.MidplatConf;
import com.sinosoft.midplat.newccb.bean.LKPolicyXML;
import com.sinosoft.utility.DBConnPool;


/**
 * <p>Title: zhybt</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: SinoSoft</p>
 *
 * @author apple
 * @version 1.0
 */
public class LKPolicyXMLDao
{
	/**日志*/
	private static Logger mLogger = Logger.getLogger(LKPolicyXMLDao.class);
	
	
	
	/**
	 * 
	 * insert 挺入保单报文信息
	 *
	 * @param policyXML
	 * @return
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public boolean insert(LKPolicyXML policyXML) throws SQLException, IOException, SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		//获得数据库链接
		Connection con = null;
		//声明
		PreparedStatement preSt = null;    
		//结果集
        ResultSet rs = null;   
        
        OutputStream os = null;
		try{
			con = DBConnPool.getConnection();
			
	        //设置不自动提交 
	        con.setAutoCommit(false);
	        
	        //插入sql
	        String insertSql = "insert into LKPolicyXML(contNo,proposalPrtNo,tranCom,zoneNo,nodeNo,trandate,xmlContent,makeDate,makeTime,modifyDate,modifyTime) values('" + policyXML.getContNo()
	        		+ "','" + policyXML.getProposalPrtNo() + "','" + policyXML.getTranCom() + "','" + policyXML.getZoneNo() 
	        		+ "','" + policyXML.getNodeNo() + "','" + DateUtil.formatDate(policyXML.getTranDate(), "yyyy-MM-dd") + "',EMPTY_BLOB(),'" 
	        		+ DateUtil.formatDate(policyXML.getMakeDate(), "yyyy-MM-dd") + "','" + policyXML.getMakeTime() + "','" + DateUtil.formatDate(policyXML.getModifyDate(), "yyyy-MM-dd") + "','" + policyXML.getModifyTime() + "')";
	        
	        mLogger.info(insertSql);
	        
	        //创建声明
	        Statement st = con.createStatement();
	        
	        st.execute(insertSql);
	        st.close();
	        
	        //更新sql
	        String updateSql = "select xmlContent from LKPolicyXML where contNo=? and tranCom=? for update";
	        
	        mLogger.info(updateSql);
	        
	        preSt = con.prepareStatement(updateSql);
	        preSt.setString(1, policyXML.getContNo());
	        preSt.setString(2, policyXML.getTranCom());
	        
	        //执行查询
	        rs = preSt.executeQuery();
	        
	        if(!rs.next())
	        {
	        	mLogger.error("XmlContent insert false");
	            //回滚
	        	con.rollback();
	        	return false;
	        }
	        
	        //处理结果集
            /**
             * 对于weblogic连接池的连接获取的BLob对象转换成oracle.sql.BLOB处理
             */
	        Object objBlob=rs.getBlob(1);
	        BLOB xmlContent=null;
	        if("weblogic.jdbc.wrapper.Blob_oracle_sql_BLOB".equals(objBlob.getClass().getName())){
	        	 Method  method = objBlob.getClass().getMethod("getVendorObj",new Class[]{});
	        	 xmlContent=(BLOB)method.invoke(objBlob);
	        }else{
	        	 xmlContent = (BLOB) rs.getBlob(1);
	        }
			//BLOB xmlContent = (BLOB) rs.getBlob(1);
            
			// 得到输出流
			os = xmlContent.getBinaryOutputStream();
			// 写入报文报单内容
			os.write(policyXML.getXmlContent());

			// 刷新输出流
			os.flush();
	     
	        //提交 
	        con.commit();
		}catch (SQLException e) {
			if(con != null){
				//回滚
				con.rollback();
			}
        	throw e;
		}catch (IOException e){
			if(con != null){
				//回滚
				con.rollback();
			}
			throw e;
		}finally{
			if(os != null){
				// 关闭输出流
				os.close();
			}
			if(rs != null){
				//关闭结果集
		        rs.close();
			}
			if(preSt != null){
				//关闭声明
		        preSt.close();
			}
			if(con != null){
				//关闭数据库连接
		        con.close();
			}
		}
        
		return true;
	}
	
	/**
	 * 
	 * query 根据条件查询报文信息
	 *
	 * @param contNo
	 * @param tranCom
	 * @return
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws NamingException 
	 */
	public byte[] queryXmlContent(String contNo, String tranCom) throws SQLException
	{
		byte[] xmlContent = null;
		
		//获得数据库链接
		Connection con = DBConnPool.getConnection();

		// 声明
		PreparedStatement preSt = null;
		
		// 结果集
		ResultSet rs = null;
		
		//查询sql
		String querySql = "select xmlContent from LKPolicyXML where contNo=? and tranCom=?";
		
		mLogger.info(querySql);
        
		//设置条件 
        preSt = con.prepareStatement(querySql);
        preSt.setString(1, contNo);
        preSt.setString(2, tranCom);
        
        //查询
        rs = preSt.executeQuery();
        while(rs.next())
        {
        	BLOB xmlContentBLOB = (BLOB)rs.getBlob(1);
        	xmlContent = xmlContentBLOB.getBytes(1, (int)xmlContentBLOB.length());
        }
        
        //关闭结果集
        rs.close();
        
        //关闭声音
        preSt.close();
        
        con.close();
        
		return xmlContent;
	}
	
	
	/**
	 * 
	 * setPreparedStatementValues 设置PreparedStatement中值
	 *
	 * @param st 预声明
	 * @param policyXML 保单报文
	 * @throws SQLException 
	 */
	private void setPreparedStatementValues(PreparedStatement st, LKPolicyXML policyXML) throws SQLException
	{

		// 保单号
		st.setString(1, policyXML.getContNo());

		// 投保单号
		st.setString(2, policyXML.getProposalPrtNo());

		// 银行机构
		st.setString(3, policyXML.getTranCom());

		// 地区码
		st.setString(4, policyXML.getZoneNo());

		// 网点码
		st.setString(5, policyXML.getNodeNo());

		// 交易日期
		st.setDate(6, new Date(policyXML.getTranDate().getTime()));

		
		//入机日期
		st.setDate(7, new Date(policyXML.getMakeDate().getTime()));
		
		//入机时间
		st.setString(8, policyXML.getMakeTime());
		
		//修改日期
		st.setDate(9, new Date(policyXML.getModifyDate().getTime()));
		
		//修改时间
		st.setString(10, policyXML.getModifyTime());
	}
}
