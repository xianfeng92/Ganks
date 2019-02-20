package com.example.ganks;

import android.Manifest;
import android.support.annotation.NonNull;

import com.example.ganks.delegate.BaseDelegate;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created By apple on 2019/2/20
 * github: https://github.com/xianfeng92
 */
@RuntimePermissions
public abstract class GankFragment extends BaseDelegate {
    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void requestFilePermission() {
    }

    public void requestFilePermissionWithCheck() {
        GankFragmentPermissionsDispatcher.requestFilePermissionWithPermissionCheck(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        GankFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
