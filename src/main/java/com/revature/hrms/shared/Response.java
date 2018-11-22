package com.revature.hrms.shared;

import lombok.Data;

@Data
public class Response {
    private String code;
    private String msg;
    private String errorType;
    private String detailMsg;
    private Object data;

    // ---------------------- initializer
    public static Response init() {
        return new Response();
    }

    // --------------------------- Setter
    public Response setCode(CustomCode code) {
        this.code = code.getCode();
        return this;
    }

    public Response setErrorType(ErrorType errorType) {
        this.errorType = errorType == null ? null : errorType.toString();
        return this;
    }

    public Response success() {
        return setCode(CustomCode.SUCCESS);
    }
}
