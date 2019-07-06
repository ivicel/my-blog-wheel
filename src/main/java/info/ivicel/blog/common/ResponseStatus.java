package info.ivicel.blog.common;

public enum ResponseStatus {
    SUCCESS(0, "success"),
    FAIL(-1, "fail"),
    LOGIN_FAILED(403, "login failed"),
    LOGIN_SUCCESS(200, "login success");


    private Integer status;

    private String message;

    ResponseStatus(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
