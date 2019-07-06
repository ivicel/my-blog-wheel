package info.ivicel.blog.config;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import info.ivicel.blog.core.exception.PageNotFoundException;

@ControllerAdvice
public class PageErrorHandler {
    @ExceptionHandler(PageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String pageNotFound(PageNotFoundException e, Model model) {
        model.addAttribute("message", e.getMessage());
        return "404";
    }
}
