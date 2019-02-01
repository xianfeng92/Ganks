package com.example.ganks;

import android.net.Uri;

import com.squareup.picasso.Downloader;

import java.io.IOException;

/**
 * Created By zhongxianfeng on 19-2-1
 * github: https://github.com/xianfeng92
 */
public class CustomDownloader implements Downloader {

    @Override
    public Response load(Uri uri, int networkPolicy) throws IOException {
        return null;
    }

    @Override
    public void shutdown() {

    }

}
