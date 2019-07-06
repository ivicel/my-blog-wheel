package info.ivicel.blog.core.exception;


import info.ivicel.blog.utils.MessageUtil;

public class PageNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 2686766022631924665L;

    public PageNotFoundException() {
        this(MessageUtil.getMessage("page-not-found"));
    }

    public PageNotFoundException(String message) {
        super(message);
    }

    public PageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
