package info.ivicel.blog.common;

public class RestResponse<T> {
    private T msg;

    private Integer status;

    private RestResponse(Integer status, T msg) {
        this.msg = msg;
        this.status = status;
    }

    public T getMsg() {
        return msg;
    }

    public Integer getStatus() {
        return status;
    }

    public static <T> RestResponse create(ResponseStatus status, T data) {
        return new RestResponse<>(status.getStatus(), data);
    }

    public static <T> RestResponse success(T data) {
        return new RestResponse<>(ResponseStatus.SUCCESS.getStatus(), data);
    }

    public static <T> RestResponse fail(T data) {
        return new RestResponse<>(ResponseStatus.FAIL.getStatus(), data);
    }
}
