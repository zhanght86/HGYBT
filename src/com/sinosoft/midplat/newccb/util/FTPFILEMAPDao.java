package com.sinosoft.midplat.newccb.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

import com.sinosoft.midplat.exception.MidplatException;
import com.sinosoft.utility.DBConnPool;
import com.sinosoft.utility.ExeSQL;

public class FTPFILEMAPDao {
	protected final static Logger cLogger =Logger.getLogger(FTPFILEMAPDao.class);
	private static PreparedStatement ps=null;
	private static Connection conn=null;
	private static ResultSet rs=null;
    public static String query(String localFileName) throws Exception{
    	String querySql="select disposeflag from FTPFILEMAP where localname=?";
    	cLogger.info("select disposeflag from FTPFILEMAP where localname='"+localFileName+"'");
    	try {
		    conn=DBConnPool.getConnection();
			ps=conn.prepareStatement(querySql);
			ps.setString(1,localFileName);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				return rs.getString(1);
			}else{
				return "-1";
			}
		} catch (Exception e) {
			throw new MidplatException("��ѯ����ʧ��!"+e.getMessage());
		}finally{
			closeConn();
		}
    }
    public static void insert(String sql) throws Exception{
    	cLogger.info(sql);
    	try {
		    conn=DBConnPool.getConnection();
			ps=conn.prepareStatement(sql);
			ps.execute();
			cLogger.info("ִ�в������ɹ�!");
		} catch (Exception e) {
			throw new MidplatException("��������ʧ��!"+e.getMessage());
		}finally{
			closeConn();
		}
    }
    public static int update(String bankFileName) throws Exception{
    	String updateSql="update FTPFILEMAP set disposeflag='0' where bankname=?";
    	cLogger.info("update FTPFILEMAP set disposeflag='0' where bankname='"+bankFileName+"')");
    	try {
		    conn=DBConnPool.getConnection();
			ps=conn.prepareStatement(updateSql);
			ps.setString(1,bankFileName);
		    return ps.executeUpdate();
		} catch (Exception e) {
			throw new MidplatException("��������ʧ��!"+e.getMessage());
		}finally{
			closeConn();
		}
    }
    public static String selectLocalFileName(String bankFileName) throws Exception{
    	String querySql="select localname from FTPFILEMAP where bankname=? and disposeflag='1'";
    	cLogger.info("select localname from FTPFILEMAP where bankname='"+bankFileName+"' and disposeflag='1'");
    	try {
		    conn=DBConnPool.getConnection();
			ps=conn.prepareStatement(querySql);
			ps.setString(1,bankFileName);
		    rs= ps.executeQuery();
		    if(rs.next()){
		    	return rs.getString(1);//�鵽����δ�����ӳ���ļ�
		    }else{
		    	return "empty";//δ�鵽����δ�����ӳ���ļ�
		    }
		} catch (Exception e) {
			throw new MidplatException("��ѯlocalname����ʧ��!"+e.getMessage());
		}finally{
			closeConn();
		}
    }
    public static void closeConn(){
    	try {
    		if(rs!=null){
    			rs.close();
    			rs=null;
    		}
    		if(conn!=null){
    		   conn.close();
    		   rs=null;
    		}
    		if(ps!=null){
    		   ps.close();
    		   rs=null;
    		}
		} catch (Exception e) {
			e.printStackTrace();
			cLogger.info("�ر������쳣!"+e.getMessage());
		}
    	
    }
}
