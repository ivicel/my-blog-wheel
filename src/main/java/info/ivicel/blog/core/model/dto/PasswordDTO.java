package info.ivicel.blog.core.model.dto;

import lombok.Data;

@Data
public class PasswordDTO {
    // @NotEmpty
    // @Pattern(regexp = "[-_~!@#$%^&*0-9a-zA-Z]{6,16}",
    //         message = "密码只能是长度为 6-16 包含数字, 大小写字母, 以及以下特殊字符[-, _, ~, !, @, #, $, %, ^, &, *]")
    private String oldPassword;

    // @NotEmpty
    // @Pattern(regexp = "[-_~!@#$%^&*0-9a-zA-Z]{6,16}",
    //         message = "密码只能是长度为 6-16 包含数字, 大小写字母, 以及以下特殊字符[-, _, ~, !, @, #, $, %, ^, &, *]")
    private String newPassword;

    private String confirmPassword;
}
