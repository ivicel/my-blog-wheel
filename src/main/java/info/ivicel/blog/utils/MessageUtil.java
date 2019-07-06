package info.ivicel.blog.utils;

import java.util.Locale;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

public class MessageUtil {

    public static String getMessage(String code) {
        return getMessage(code, null, Locale.getDefault());
    }

    public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return InternalMessageUtil.messageSource.getMessage(code, args, defaultMessage, locale);
    }

    public static String getMessage(String code, Object[] args, Locale locale)
            throws NoSuchMessageException {
        return InternalMessageUtil.messageSource.getMessage(code, args, locale);
    }

    public static String getMessage(MessageSourceResolvable resolvable, Locale locale)
            throws NoSuchMessageException {
        return InternalMessageUtil.messageSource.getMessage(resolvable, locale);
    }

    @Component
    public static class InternalMessageUtil {
        private static MessageSource messageSource;

        private MessageSource ms;

        @Autowired
        public InternalMessageUtil(MessageSource ms) {
            this.ms = ms;
        }

        @PostConstruct
        public void setMessageSource() {
            messageSource = ms;
        }
    }
}
