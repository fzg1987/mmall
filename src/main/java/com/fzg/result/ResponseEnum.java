package com.fzg.result;

public enum ResponseEnum {

    USER_INFO_NULL(300,"用户信息不能为空"),
    EMAIL_ERROR(301,"邮箱格式错误"),
    MOBILE_ERROR(302,"手机格式错误"),
    USERNAME_EXISTS(303,"用户名已存在"),
    USER_REGISTER_ERROR(304,"用户注册失败");

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
    private Integer code;
    private String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
