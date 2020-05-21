package com.relax;

/**
 * RequestInfo
 * @author haiming.dhm
 * @version $Id: RequestInfo.java, v 0.1 2016年8月15日 下午3:47:14 haiming.dhm Exp $
 */
public abstract class RequestInfo implements Cloneable {
    private String url         = "";
    private String response    = null;
    private String method      = null;
    private String request     = null;
    private long   start       = 0;
    private long   end         = 0;
    private boolean connected  = false;
    private boolean succeed    = false;
    private Exception exception   = null;
    /**
     * Getter method for property <tt>url</tt>.
     * 
     * @return property value of url
     */
    public String getUrl() {
        return url;
    }
    /**
     * Setter method for property <tt>url</tt>.
     * 
     * @param url value to be assigned to property url
     */
    public void setUrl(String url) {
        this.url = url;
    }
    /**
     * Getter method for property <tt>response</tt>.
     * 
     * @return property value of response
     */
    public String getResponse() {
        return response;
    }
    /**
     * Setter method for property <tt>response</tt>.
     * 
     * @param response value to be assigned to property response
     */
    public void setResponse(String response) {
        this.response = response;
    }
    /**
     * Getter method for property <tt>method</tt>.
     * 
     * @return property value of method
     */
    public String getMethod() {
        return method;
    }
    /**
     * Setter method for property <tt>method</tt>.
     * 
     * @param method value to be assigned to property method
     */
    public void setMethod(String method) {
        this.method = method;
    }
    /**
     * Getter method for property <tt>request</tt>.
     * 
     * @return property value of request
     */
    public String getRequest() {
        return request;
    }
    /**
     * Setter method for property <tt>request</tt>.
     * 
     * @param request value to be assigned to property request
     */
    public void setRequest(String request) {
        this.request = request;
    }
    /**
     * Getter method for property <tt>connected</tt>.
     * 
     * @return property value of connected
     */
    public boolean isConnected() {
        return connected;
    }
    /**
     * Setter method for property <tt>connected</tt>.
     * 
     * @param connected value to be assigned to property connected
     */
    public void setConnected(boolean connected) {
        this.connected = connected;
    }
    /**
     * Getter method for property <tt>succeed</tt>.
     * 
     * @return property value of succeed
     */
    public boolean isSucceed() {
        return succeed;
    }
    /**
     * Setter method for property <tt>succeed</tt>.
     * 
     * @param succeed value to be assigned to property succeed
     */
    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    /**
     * Getter method for property <tt>exception</tt>.
     * 
     * @return property value of exception
     */
    public Exception getException() {
        return exception;
    }
    /**
     * Setter method for property <tt>exception</tt>.
     * 
     * @param exception value to be assigned to property exception
     */
    public void setException(Exception exception) {
        this.exception = exception;
    }
    
    public void start() {
        if (start == 0) {
            this.start = System.currentTimeMillis();
        }
    }
    public void end() {
        if (end == 0) {
            this.end = System.currentTimeMillis();
        }
    }
    public long time() {
        if (start == 0 || end == 0) {
            throw new IllegalStateException("No pair of start and end");
        }
        return end - start;
    }
    
    /** 
     * @see Object#clone()
     */
    @Override
    public RequestInfo clone() {
        try {
            return (RequestInfo)super.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
        }
    }

}
