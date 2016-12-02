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
	/**��־*/
	private static Logger mLogger = Logger.getLogger(LKPolicyXMLDao.class);
	
	
	
	/**
	 * 
	 * insert ͦ�뱣��������Ϣ
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
		//������ݿ�����
		Connection con = null;
		//����
		PreparedStatement preSt = null;    
		//�����
        ResultSet rs = null;   
        
        OutputStream os = null;
		try{
			con = DBConnPool.getConnection();
			
	        //���ò��Զ��ύ 
	        con.setAutoCommit(false);
	        
	        //����sql
	        String insertSql = "insert into LKPolicyXML(contNo,proposalPrtNo,tranCom,zoneNo,nodeNo,trandate,xmlContent,makeDate,makeTime,modifyDate,modifyTime) values('" + policyXML.getContNo()
	        		+ "','" + policyXML.getProposalPrtNo() + "','" + policyXML.getTranCom() + "','" + policyXML.getZoneNo() 
	        		+ "','" + policyXML.getNodeNo() + "','" + DateUtil.formatDate(policyXML.getTranDate(), "yyyy-MM-dd") + "',EMPTY_BLOB(),'" 
	        		+ DateUtil.formatDate(policyXML.getMakeDate(), "yyyy-MM-dd") + "','" + policyXML.getMakeTime() + "','" + DateUtil.formatDate(policyXML.getModifyDate(), "yyyy-MM-dd") + "','" + policyXML.getModifyTime() + "')";
	        
	        mLogger.info(insertSql);
	        
	        //��������
	        Statement st = con.createStatement();
	        
	        st.execute(insertSql);
	        st.close();
	        
	        //����sql
	        String updateSql = "select xmlContent from LKPolicyXML where contNo=? and tranCom=? for update";
	        
	        mLogger.info(updateSql);
	        
	        preSt = con.prepareStatement(updateSql);
	        preSt.setString(1, policyXML.getContNo());
	        preSt.setString(2, policyXML.getTranCom());
	        
	        //ִ�в�ѯ
	        rs = preSt.executeQuery();
	        
	        if(!rs.next())
	        {
	        	mLogger.error("XmlContent insert false");
	            //�ع�
	        	con.rollback();
	        	return false;
	        }
	        
	        //��������
            /**
             * ����weblogic���ӳص����ӻ�ȡ��BLob����ת����oracle.sql.BLOB����
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
            
			// �õ������
			os = xmlContent.getBinaryOutputStream();
			// д�뱨�ı�������
			os.write(policyXML.getXmlContent());

			// ˢ�������
			os.flush();
	     
	        //�ύ 
	        con.commit();
		}catch (SQLException e) {
			if(con != null){
				//�ع�
				con.rollback();
			}
        	throw e;
		}catch (IOException e){
			if(con != null){
				//�ع�
				con.rollback();
			}
			throw e;
		}finally{
			if(os != null){
				// �ر������
				os.close();
			}
			if(rs != null){
				//�رս����
		        rs.close();
			}
			if(preSt != null){
				//�ر�����
		        preSt.close();
			}
			if(con != null){
				//�ر����ݿ�����
		        con.close();
			}
		}
        
		return true;
	}
	
	/**
	 * 
	 * query ����������ѯ������Ϣ
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
		
		//������ݿ�����
		Connection con = DBConnPool.getConnection();

		// ����
		PreparedStatement preSt = null;
		
		// �����
		ResultSet rs = null;
		
		//��ѯsql
		String querySql = "select xmlContent from LKPolicyXML where contNo=? and tranCom=?";
		
		mLogger.info(querySql);
        
		//�������� 
        preSt = con.prepareStatement(querySql);
        preSt.setString(1, contNo);
        preSt.setString(2, tranCom);
        
        //��ѯ
        rs = preSt.executeQuery();
        while(rs.next())
        {
        	BLOB xmlContentBLOB = (BLOB)rs.getBlob(1);
        	xmlContent = xmlContentBLOB.getBytes(1, (int)xmlContentBLOB.length());
        }
        
        //�رս����
        rs.close();
        
        //�ر�����
        preSt.close();
        
        con.close();
        
		return xmlContent;
	}
	
	
	/**
	 * 
	 * setPreparedStatementValues ����PreparedStatement��ֵ
	 *
	 * @param st Ԥ����
	 * @param policyXML ��������
	 * @throws SQLException 
	 */
	private void setPreparedStatementValues(PreparedStatement st, LKPolicyXML policyXML) throws SQLException
	{

		// ������
		st.setString(1, policyXML.getContNo());

		// Ͷ������
		st.setString(2, policyXML.getProposalPrtNo());

		// ���л���
		st.setString(3, policyXML.getTranCom());

		// ������
		st.setString(4, policyXML.getZoneNo());

		// ������
		st.setString(5, policyXML.getNodeNo());

		// ��������
		st.setDate(6, new Date(policyXML.getTranDate().getTime()));

		
		//�������
		st.setDate(7, new Date(policyXML.getMakeDate().getTime()));
		
		//���ʱ��
		st.setString(8, policyXML.getMakeTime());
		
		//�޸�����
		st.setDate(9, new Date(policyXML.getModifyDate().getTime()));
		
		//�޸�ʱ��
		st.setString(10, policyXML.getModifyTime());
	}
}
