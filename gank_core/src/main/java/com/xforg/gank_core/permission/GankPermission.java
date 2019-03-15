package com.xforg.gank_core.permission;

import android.Manifest;
import android.support.annotation.NonNull;

import com.xforg.gank_core.delegates.BaseDelegate;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * Created By apple on 2019/2/20
 * github: https://github.com/xianfeng92
 */
@RuntimePermissions
public abstract class GankPermission extends BaseDelegate {

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void requestFilePermission() {
    }

    public void requestFilePermissionWithCheck() {
        GankPermissionPermissionsDispatcher.requestFilePermissionWithPermissionCheck(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        GankPermissionPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }
}
