package com.example.retrohttp;


import com.example.retrohttp.retrofit.Method;

import java.io.File;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created By zhongxianfeng on 19-3-26
 * github: https://github.com/xianfeng92
 */
public class retroHttpClientBuilder {

    /*请求方式*/
    Method method;
    /*请求参数*/
    Map<String, Object> parameter;
    /*header*/
    Map<String, Object> header;
    /*标识请求的TAG*/
    String tag;
    /*文件map*/
    Map<String, File> fileMap;
    /*基础URL*/
    String baseUrl;
    /*apiUrl*/
    String apiUrl;
    /*String参数*/
    String bodyString;
    /*是否强制JSON格式*/
    boolean isJson;

    public retroHttpClientBuilder() {
    }

    /*GET*/
    public retroHttpClientBuilder get() {
        this.method = Method.GET;
        return this;
    }

    /*POST*/
    public retroHttpClientBuilder post() {
        this.method = Method.POST;
        return this;
    }

    /*DELETE*/
    public retroHttpClientBuilder delete() {
        this.method = Method.DELETE;
        return this;
    }

    /*PUT*/
    public retroHttpClientBuilder put() {
        this.method = Method.PUT;
        return this;
    }

    /*基础URL*/
    public retroHttpClientBuilder baseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
        return this;
    }

    /*API URL*/
    public retroHttpClientBuilder apiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
        return this;
    }

    /* 增加 Parameter 不断叠加参数 包括基础参数 */
    public retroHttpClientBuilder addParameter(Map<String, Object> parameter) {
        if (this.parameter == null) {
            this.parameter = new TreeMap<>();
        }
        this.parameter.putAll(parameter);
        return this;
    }

    /*设置 Parameter 会覆盖 Parameter 包括基础参数*/
    public retroHttpClientBuilder setParameter(Map<String, Object> parameter) {
        this.parameter = parameter;
        return this;
    }

    /* 设置String 类型参数  覆盖之前设置  isJson:是否强制JSON格式    bodyString设置后Parameter则无效 */
    public retroHttpClientBuilder setBodyString(String bodyString, boolean isJson) {
        this.isJson = isJson;
        this.bodyString = bodyString;
        return this;
    }

    /* 增加 Header 不断叠加 Header 包括基础 Header */
    public retroHttpClientBuilder addHeader(Map<String, Object> header) {
        this.header = header;
        return this;
    }

    /*设置 Header 会覆盖 Header 包括基础参数*/
    public retroHttpClientBuilder setHeader(Map<String, Object> header) {
        this.header = header;
        return this;
    }

    /*tag*/
    public retroHttpClientBuilder tag(String tag) {
        this.tag = tag;
        return this;
    }

    /*文件集合*/
    public retroHttpClientBuilder file(Map<String, File> file) {
        this.fileMap = file;
        return this;
    }

    /*一个Key对应多个文件*/
    public retroHttpClientBuilder file(String key, List<File> fileList) {
        if (fileMap == null) {
            fileMap = new IdentityHashMap();
        }
        if (fileList != null && fileList.size() > 0) {
            for (File file : fileList) {
                fileMap.put(new String(key), file);
            }
        }
        return this;
    }

    public retroHttpClient build() {
        return new retroHttpClient(this);
    }
}
