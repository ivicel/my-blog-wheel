package info.ivicel.blog.core.exception;

import org.springframework.security.core.AuthenticationException;

import info.ivicel.blog.utils.MessageUtil;

public class AccountOrPasswordNotFoundException extends AuthenticationException {

    private static final long serialVersionUID = -9085777797684721863L;

    public AccountOrPasswordNotFoundException() {
        this(MessageUtil.getMessage("account-password-error"));
    }

    public AccountOrPasswordNotFoundException(String message) {
        super(message);
    }

    public AccountOrPasswordNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
