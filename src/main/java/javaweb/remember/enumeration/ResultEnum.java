package javaweb.remember.enumeration;

public enum ResultEnum {
    /**
     * 出现格式错误时，状态码统一为-100
     */

    SEND_VERIFICATION_CODE_SUCCESS(1,"验证码发送成功，5分钟内有效"),
    REGISTER_SUCCESS(2,"注册成功"),
    LOGIN_SUCCESS(3,"登录成功"),

    SEND_VERIFICATION_CODE_FAIL(-1,"验证码发送失败"),
    NO_VERIFICATION_CODE(-2,"请先获取验证码"),
    VERIFICATION_CODE_FAILURE(-3,"验证码已失效，请重新获取"),
    VERIFICATION_CODE_INCORRECT(-4,"验证码错误，请核对后重新输入"),
    HAVE_REGISTERED(-5,"您已注册过I-Remember，请直接登录"),
    HAVE_NOT_REGISTERED(-6,"您还没有注册，请先注册"),
    PASSWORD_INCORRECT(-7,"密码错误"),

    ;
    private int code;
    private String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
