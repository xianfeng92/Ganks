package com.example.domain.exception;

/**
 * Created By zhongxianfeng on 19-4-3
 * github: https://github.com/xianfeng92
 */
public interface ErrorBundle {
    Exception getException();

    String getErrorMessage();
}
