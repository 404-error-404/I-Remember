//Author:刘行
package javaweb.remember.vo;


import lombok.Data;

import javax.validation.constraints.*;

@Data
public class SignUpVo {

    @NotNull(message = "邮箱不能为空")
    @NotBlank(message = "邮箱不能为空")
    @Email(message = "邮箱格式错误")
    private String email;

    @NotNull(message = "用户名不能为空")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotNull(message = "密码不能为空")
    @NotBlank(message = "密码不能为空")
    @Max(value = 20,message = "密码太长啦")
    @Min(value = 6,message = "密码太短啦")
    private String password;

    @NotNull(message = "验证码不能为空")
    @NotBlank(message = "验证码不能为空")
    private String code;

}
