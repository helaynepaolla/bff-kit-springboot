package br.com.bradesco.kit.bff.exception;

public class InfrastructureException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private final String errorCode;
    private final String errorMsg;

    public InfrastructureException(Integer errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = Integer.toString(errorCode);
        this.errorMsg = errorMsg;
    }

    public InfrastructureException(String errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public InfrastructureException(Integer errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = Integer.toString(errorCode);
        this.errorMsg = errorMsg;
    }

    public InfrastructureException(String errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrCode() {
        return errorCode;
    }

    public String getErrMsg() {
        return errorMsg;
    }

}