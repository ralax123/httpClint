/**
 * dianying.taobao.com
 * Copyright (c) 2004-2016 All Rights Reserved.
 */
package com.relax;

/**
 * 
 * @author haiming.dhm
 * @version $Id: HttpRequestInfo.java, v 0.1 2016年8月5日 上午11:08:59 haiming.dhm Exp $
 */
public class HttpRequestInfo extends RequestInfo {

    private String header        = "";
    private int status           = 0;

    /**
     * Getter method for property <tt>header</tt>.
     * 
     * @return property value of header
     */
    public String getHeader() {
        return header;
    }

    /**
     * Setter method for property <tt>header</tt>.
     * 
     * @param header value to be assigned to property header
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Getter method for property <tt>status</tt>.
     * 
     * @return property value of status
     */
    public int getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     * 
     * @param status value to be assigned to property status
     */
    public void setStatus(int status) {
        this.status = status;
    }

}
