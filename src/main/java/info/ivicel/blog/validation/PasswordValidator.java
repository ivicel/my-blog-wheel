package info.ivicel.blog.validation;

import info.ivicel.blog.core.model.dto.PasswordDTO;
import java.util.regex.Pattern;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * 修改密码验证
 */
public class PasswordValidator implements Validator {
    private static final String pattern = "^[-_~!@#$%^&*0-9a-zA-Z]{6,16}$";

    @Override
    public boolean supports(Class<?> clazz) {
        return PasswordDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oldPassword", "field.required", "密码不能为空");

        PasswordDTO passwordDTO = (PasswordDTO) target;
        String newPassword = passwordDTO.getNewPassword().trim();
        String confirmPassword = passwordDTO.getConfirmPassword().trim();
        if (!Pattern.matches(pattern, newPassword)) {
            errors.rejectValue("newPassword", "newPassword",
                    "密码只能是长度为 6-16 包含数字, 大小写字母, 以及以下特殊字符[-, _, ~, !, @, #, $, %, ^, &, *]");
        }

        if (!newPassword.equals(confirmPassword)) {
            errors.rejectValue("confirmPassword", "confirmPassword", "两次输入的密码不相同");
        }
    }
}
