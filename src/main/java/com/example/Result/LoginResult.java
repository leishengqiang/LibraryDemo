package com.example.Result;

import lombok.Data;

@Data
public class LoginResult {
    private Object object;
    private String msg;
    private Integer code;//0通过 -1用户名错误 -2密码错误 -3未选择身份
}
