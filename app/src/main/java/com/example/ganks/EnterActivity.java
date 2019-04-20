package com.example.ganks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ganks.mvp.ui.activitys.ContentActivity;
import com.example.ganks.mvp.view.EnterView;
import com.xforg.easyimage.ImageLoader;
import com.xforg.easyimage.config.ConfigBuilder;
import com.xforg.easyimage.config.ImageConfig;
import com.xforg.gank_core.StatusBar.StatusBarUtil;
import com.example.ganks.delegates.GankDelegate;

public class EnterActivity extends AppCompatActivity implements EnterView, GankDelegate.OnBackToFirstListener, View.OnClickListener {

    private static final String TAG = "EnterActivity";

    AppCompatImageView mImageView;

    private String mUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        mImageView = findViewById(R.id.img_launcher_welcome);
        mImageView.setOnClickListener(this);
        StatusBarUtil.setStatusBarAndNavigationBarTranslucent(this);
        loadImg();
    }

    @Override
    public void onBackToFirstFragment() {
        finish();
    }

    @Override
    public void goContentActivity() {
        startActivity(new Intent(EnterActivity.this, ContentActivity.class));
        finish();
    }

    private void loadImg() {
        mUrl = ConfigManage.INSTANCE.getBannerURL();
        Log.d(TAG, "loadImg: "+mUrl);
        ImageConfig config = new ConfigBuilder(this)
                .url(mUrl)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .error(R.mipmap.banner)
                .build();
        ImageLoader.getActualLoader().apply(config).into(mImageView);
    }

    @Override
    public void onClick(View v) {
        goContentActivity();
    }
}
