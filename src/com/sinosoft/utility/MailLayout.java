package com.sinosoft.utility;

import org.apache.log4j.HTMLLayout;

/**
* 2010-12-01
* @author YH
* */
public class MailLayout extends HTMLLayout {


    public String getContentType() {
        return "text/html;charset=GBK";
    }
    
    public boolean ignoresThrowable() {
        return false;
    }

    public void activateOptions() {
    }
    
}
