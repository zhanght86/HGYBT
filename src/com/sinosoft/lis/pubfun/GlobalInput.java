/**
 * Copyright (c) 2002 sinosoft  Co. Ltd.
 * All right reserved.
 */
package com.sinosoft.lis.pubfun;

/**
 * <p>Title: Web业务系统</p>
 * <p>Description: 全局变量区</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Sinosoft</p>
 * @author YT
 * @version 1.0
 * <p>增加兼业代理机构为满足兼业平台 2006-10-25 周磊</p>
 */

public class GlobalInput
{
    /** 当前操作员 */
    public String Operator;
    /** 当前管理机构 */
    public String ManageCom;
    /** 当前管理机构 */
    public String ManageComName;
    /** 当前登陆机构 */
    public String ComCode;
    /** 兼业代理机构 */
    public String AgentCom;
    /** 非实时核保标记 */
    public String UWFlag;
    /** 交易机构 */
    public String BankCode;

    public GlobalInput()
    {
    }

    /**
     * 两个GlobalInput对象之间的直接复制
     * @param cGlobalInput 包含有具体值的GlobalInput对象
     */
    public void setSchema(GlobalInput cGlobalInput)
    {
        //获取登陆用户基础信息：用户编码、管理机构等
        this.Operator = cGlobalInput.Operator;
        this.ComCode = cGlobalInput.ComCode;
        this.ManageCom = cGlobalInput.ManageCom;
        this.AgentCom = cGlobalInput.AgentCom;
        this.UWFlag = cGlobalInput.UWFlag;
        this.BankCode = cGlobalInput.BankCode;
    }
}
