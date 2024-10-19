package com.project.obj;

public class ApiResponse {
    private String status;
    private String failureCode;
    private String error;
    private Object data;
    public ApiResponse() {
    }
    public ApiResponse(Object data) {
        if (data == null) {
            this.status = "FAILURE";
            return;
        }
        this.status = "SUCCESS";
        this.data = data;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFailureCode() {
        return failureCode;
    }

    public void setFailureCode(String failureCode) {
        this.failureCode = failureCode;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String toJsonString() {
        return "{" +
                "\"status\":\"" + status + "\"" +
                ", \"failureCode\":\"" + failureCode + "\"" +
                ", \"error\":\"" + error + "\"" +
                ", \"data\":" + data +
                "}";
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "status='" + status + '\'' +
                ", failureCode='" + failureCode + '\'' +
                ", error='" + error + '\'' +
                ", data=" + data +
                '}';
    }
}
