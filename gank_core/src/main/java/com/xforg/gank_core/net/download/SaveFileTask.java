package com.xforg.gank_core.net.download;

import android.os.AsyncTask;
import com.xforg.gank_core.net.callbacks.IRequest;
import com.xforg.gank_core.net.callbacks.ISuccess;
import com.xforg.gank_core.utils.File.FileUtil;
import java.io.File;
import java.io.InputStream;
import okhttp3.ResponseBody;

/**
 * Created By zhongxianfeng on 19-2-26
 * github: https://github.com/xianfeng92
 */
public class SaveFileTask extends AsyncTask<Object,Void, File> {

    private final IRequest REQUEST;
    private final ISuccess SUCCESS;

    public SaveFileTask(IRequest request, ISuccess success) {
        REQUEST = request;
        SUCCESS = success;
    }

    //接收输入参数、执行任务中的耗时操作、返回 线程任务执行的结果
    @Override
    protected File doInBackground(Object... objects) {
        String downloadDir = (String) objects[0];
        String extension = (String) objects[1];
        final ResponseBody body = (ResponseBody) objects[2];
        final String name = (String)objects[3];
        final InputStream is = body.byteStream();
        if (downloadDir == null || downloadDir.equals(" ")){
            downloadDir = "downloads";
        }
        if (extension == null || extension.equals(" ") ){
            extension = " ";
        }
        if (name == null){
            File file = FileUtil.writeToDisk(is,downloadDir,extension.toUpperCase(),extension);
            return file;
        }else {
            return FileUtil.writeToDisk(is,downloadDir,name);

        }
    }


    @Override
    protected void onPostExecute(File file) {
        super.onPostExecute(file);
        if (SUCCESS != null){
            SUCCESS.onSuccess(file.getPath());
        }
        if(REQUEST != null){
            REQUEST.onRequestEnd();
        }
    }


}
