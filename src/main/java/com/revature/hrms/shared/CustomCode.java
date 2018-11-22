package com.revature.hrms.shared;

import org.springframework.http.HttpStatus;

public enum CustomCode {
    SUCCESS("SEC000", HttpStatus.OK), ERROR("SEC520",
            HttpStatus.INTERNAL_SERVER_ERROR), INTERNAL_SERVER_ERROR("SEC500",
            HttpStatus.INTERNAL_SERVER_ERROR), BAD_REQUEST("SEC400",
            HttpStatus.BAD_REQUEST), UNAUTHORIZED("SEC401",
            HttpStatus.UNAUTHORIZED), INACTIVE_USER("SEC481",
            HttpStatus.UNAUTHORIZED), UNAUTHORIZED_USER("SEC480",
            HttpStatus.UNAUTHORIZED);

    private String code;
    private HttpStatus statusCode;

    // ----------------------------- Constructor
    CustomCode(String code, HttpStatus statusCode) {
        this.code = code;
        this.statusCode = statusCode;
    }

    public String getCode() {
        return this.code;
    }

    public HttpStatus getStatusCode() {
        return this.statusCode;
    }

    @Override
    public String toString() {
        return new StringBuilder(" { ").append(" code : ").append(this.code).append(", statusCode : ")
                .append(this.statusCode.value()).append(", statusMsg : ")
                .append(this.statusCode.getReasonPhrase()).append("} ").toString();
    }
}
