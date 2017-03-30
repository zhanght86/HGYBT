/**
 * $RCSfile: LKPolicyXML.java
 * $Revision: 1.0
 * $Date: 2015-4-12
 *
 * Copyright (C) 2010 SinoSoft, Inc. All rights reserved.
 *
 * This software is the proprietary information of SinoSoft, Inc.
 * Use is subject to license terms.
 */
package com.sinosoft.midplat.newccb.bean;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * <p>Title: zhybt</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2015</p>
 * <p>Company: SinoSoft</p>
 *
 * @author apple
 * @version 1.0
 */
public class LKPolicyXML
{
	/** 保单号 */
	private String contNo;
	/** 投保单号 */
	private String proposalPrtNo;
	/** 银行机构编码 */
	private String tranCom;
	/** 地区码 */
	private String zoneNo;
	/** 网点编码 */
	private String nodeNo;
	/** 交易日期 */
	private Date tranDate;
	/** 报文内容 */
	private byte[] xmlContent;
	/** 入机日期 */
	private Date makeDate;
	/** 入机时间 */
	private String makeTime;
	/** 修改日期 */
	private Date modifyDate;
	/** 修改时间 */
	private String modifyTime;
	
	
	/**
	 * 取得contNo
	 * 
	 * @return String contNo 
	 */
	public String getContNo()
	{
		if(StringUtils.isEmpty(contNo))
		{
			contNo = "";
		}
		return contNo;
	}
	/**
	 * 设定contNo 
	 * 
	 * @param contNo 
	 */
	public void setContNo(String contNo)
	{
		this.contNo = contNo;
	}
	/**
	 * 取得proposalPrtNo
	 * 
	 * @return String proposalPrtNo 
	 */
	public String getProposalPrtNo()
	{
		if(StringUtils.isEmpty(proposalPrtNo))
		{
			proposalPrtNo = "";
		}
		return proposalPrtNo;
	}
	/**
	 * 设定proposalPrtNo 
	 * 
	 * @param proposalPrtNo 
	 */
	public void setProposalPrtNo(String proposalPrtNo)
	{
		this.proposalPrtNo = proposalPrtNo;
	}
	/**
	 * 取得tranCom
	 * 
	 * @return String tranCom 
	 */
	public String getTranCom()
	{
		if(StringUtils.isEmpty(tranCom))
		{
			tranCom = "";
		}
		return tranCom;
	}
	/**
	 * 设定tranCom 
	 * 
	 * @param tranCom 
	 */
	public void setTranCom(String tranCom)
	{
		this.tranCom = tranCom;
	}
	/**
	 * 取得zoneNo
	 * 
	 * @return String zoneNo 
	 */
	public String getZoneNo()
	{
		if(StringUtils.isEmpty(zoneNo))
		{
			zoneNo = "";
		}
		return zoneNo;
	}
	/**
	 * 设定zoneNo 
	 * 
	 * @param zoneNo 
	 */
	public void setZoneNo(String zoneNo)
	{
		this.zoneNo = zoneNo;
	}
	/**
	 * 取得nodeNo
	 * 
	 * @return String nodeNo 
	 */
	public String getNodeNo()
	{
		if(StringUtils.isEmpty(nodeNo))
		{
			nodeNo = "";
		}
		return nodeNo;
	}
	/**
	 * 设定nodeNo 
	 * 
	 * @param nodeNo 
	 */
	public void setNodeNo(String nodeNo)
	{
		this.nodeNo = nodeNo;
	}
	/**
	 * 取得tranDate
	 * 
	 * @return Date tranDate 
	 */
	public Date getTranDate()
	{
		return tranDate;
	}
	/**
	 * 设定tranDate 
	 * 
	 * @param tranDate 
	 */
	public void setTranDate(Date tranDate)
	{
		this.tranDate = tranDate;
	}
	/**
	 * 取得xmlContent
	 * 
	 * @return byte xmlContent 
	 */
	public byte[] getXmlContent()
	{
		return xmlContent;
	}
	/**
	 * 设定xmlContent 
	 * 
	 * @param xmlContent 
	 */
	public void setXmlContent(byte xmlContent[])
	{
		this.xmlContent = xmlContent;
	}
	/**
	 * 取得makeDate
	 * 
	 * @return Date makeDate 
	 */
	public Date getMakeDate()
	{
		return makeDate;
	}
	/**
	 * 设定makeDate 
	 * 
	 * @param makeDate 
	 */
	public void setMakeDate(Date makeDate)
	{
		this.makeDate = makeDate;
	}
	/**
	 * 取得makeTime
	 * 
	 * @return String makeTime 
	 */
	public String getMakeTime()
	{
		if(StringUtils.isEmpty(makeTime))
		{
			makeTime = "";
		}
		return makeTime;
	}
	/**
	 * 设定makeTime 
	 * 
	 * @param makeTime 
	 */
	public void setMakeTime(String makeTime)
	{
		this.makeTime = makeTime;
	}
	/**
	 * 取得modifyDate
	 * 
	 * @return Date modifyDate 
	 */
	public Date getModifyDate()
	{
		return modifyDate;
	}
	/**
	 * 设定modifyDate 
	 * 
	 * @param modifyDate 
	 */
	public void setModifyDate(Date modifyDate)
	{
		this.modifyDate = modifyDate;
	}
	/**
	 * 取得modifyTime
	 * 
	 * @return String modifyTime 
	 */
	public String getModifyTime()
	{
		if(StringUtils.isEmpty(modifyTime))
		{
			modifyTime = "";
		}
		return modifyTime;
	}
	/**
	 * 设定modifyTime 
	 * 
	 * @param modifyTime 
	 */
	public void setModifyTime(String modifyTime)
	{
		this.modifyTime = modifyTime;
	}
}
