package info.ivicel.blog.handler;

import info.ivicel.blog.common.RestResponse;
import info.ivicel.blog.core.exception.RestResultNotExistsRexception;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class AdminApiErrorHandler {
    private HttpServletRequest request;
    private HttpServletResponse response;

    @Autowired
    public AdminApiErrorHandler(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity badRequest(Exception e) {
        e.printStackTrace();
        return ResponseEntity.badRequest().body(RestResponse.fail("请求错误"));
    }

    @ExceptionHandler(RestResultNotExistsRexception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity notFound(RestResultNotExistsRexception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(RestResponse.fail(e.getMessage()));
    }
}
