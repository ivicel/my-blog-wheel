package info.ivicel.blog.api.backend;

import static info.ivicel.blog.common.RestResponse.fail;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

import info.ivicel.blog.common.RestResponse;
import info.ivicel.blog.core.exception.RestResultNotExistsRexception;
import info.ivicel.blog.core.model.domain.User;
import info.ivicel.blog.core.model.dto.PasswordDTO;
import info.ivicel.blog.core.service.UserService;
import info.ivicel.blog.validation.PasswordValidator;
import java.util.Optional;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Setter(onMethod = @__(@Autowired))
@RestController("backendUser")
@RequestMapping("/api/admin/user")
public class UserEndpoint {
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    /**
     * 查询当前登录用户
     * @param auth
     * @return
     */
    @GetMapping("/profiles")
    public ResponseEntity<User> getUserProfiles(Authentication auth) {
        Optional<User> optional = userService.findByUsername(auth.getName());
        return optional.map(ResponseEntity::ok).orElseThrow(() -> new RestResultNotExistsRexception("用户不存在"));
    }

    @PutMapping("/profiles")
    public ResponseEntity updateUserProfiles(Authentication auth, @RequestBody User user) {
        if (!auth.getName().equals(user.getUsername())) {
            return badRequest().body(fail("请求错误"));
        }

        user = userService.updatePofiles(user);

        return ok(user);
    }

    @PutMapping("/profiles/password")
    public ResponseEntity updateUserPassword(Authentication auth,
            @Validated @RequestBody PasswordDTO passwordDTO, BindingResult result) {
        if (result.hasErrors()) {
            return badRequest().body(result.getFieldErrors().stream()
                    .collect(groupingBy(FieldError::getField, mapping(FieldError::getDefaultMessage, toList()))));
        }

        if (!passwordEncoder.matches(passwordDTO.getOldPassword(), (String) auth.getCredentials())) {
            return status(HttpStatus.FORBIDDEN).body(fail("旧密码输入错误"));
        }

        userService.updatePasswordByUsername(auth.getName(), passwordDTO.getNewPassword());

        return ok(RestResponse.success("修改密码成功"));
    }

    @InitBinder("passwordDTO")
    protected void passwordBinder(WebDataBinder binder) {
        binder.addValidators(new PasswordValidator());
    }
}
