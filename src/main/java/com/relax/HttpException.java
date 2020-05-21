package com.relax;


public class HttpException extends RuntimeException {

    private static final long serialVersionUID = -1251360056512844662L;

    /**
     * error code
     */
    private String errorCode;

    /**
     * error message
     */
    private String errorMessage;

    public HttpException(String errorCode, String errorMessage) {
        super(errorCode + errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public HttpException(String errorCode, String errorMessage, Exception e) {
        super(errorMessage, e);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
