package info.ivicel.blog.core.exception;

public class RestResultNotExistsRexception extends RuntimeException {
    private static final long serialVersionUID = 716898060800734335L;

    public RestResultNotExistsRexception() {
    }

    public RestResultNotExistsRexception(String message) {
        super(message);
    }

    public RestResultNotExistsRexception(String message, Throwable cause) {
        super(message, cause);
    }

    public RestResultNotExistsRexception(Throwable cause) {
        super(cause);
    }

}
