package com.example.retrohttp.function;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import io.reactivex.functions.Function;

/**
 * Created By zhongxianfeng on 19-3-26
 * github: https://github.com/xianfeng92
 */
public class ServerResultFunction  implements Function<JsonElement,Object> {
    @Override
    public Object apply(JsonElement jsonElement) throws Exception {
        /*此处不再处理业务相关逻辑交由开发者重写httpCallback*/
        return new Gson().toJson(jsonElement);
    }
}
