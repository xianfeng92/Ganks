package com.example.retrohttp.exception;

/**
 * Created By zhongxianfeng on 19-3-26
 * github: https://github.com/xianfeng92
 */
public class ServerException extends RuntimeException{
    private int code;
    private String msg;

    public ServerException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
