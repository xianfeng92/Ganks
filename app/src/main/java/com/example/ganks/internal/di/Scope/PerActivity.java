package com.example.ganks.internal.di.Scope;

import java.lang.annotation.Retention;
import javax.inject.Scope;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created By zhongxianfeng on 19-4-4
 * github: https://github.com/xianfeng92
 */
@Scope
@Retention(RUNTIME)
public @interface PerActivity {}
