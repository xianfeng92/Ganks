package com.example.ganks;

import permissions.dispatcher.PermissionUtils;

/**
 * Created By apple on 2019/2/21
 * github: https://github.com/xianfeng92
 */
public class GankFragmentPermissionsDispatcher {

    private static final int REQUEST_REQUESTFILEPERMISSION = 0;

    private static final String[] PERMISSION_REQUESTFILEPERMISSION = new String[] {"android.permission.WRITE_EXTERNAL_STORAGE"};

    private GankFragmentPermissionsDispatcher() {
    }

    static void requestFilePermissionWithPermissionCheck(GankFragment target) {
        if (PermissionUtils.hasSelfPermissions(target.getActivity(), PERMISSION_REQUESTFILEPERMISSION)) {
            target.requestFilePermission();
        } else {
            target.requestPermissions(PERMISSION_REQUESTFILEPERMISSION, REQUEST_REQUESTFILEPERMISSION);
        }
    }

    static void onRequestPermissionsResult(GankFragment target, int requestCode,
                                           int[] grantResults) {
        switch (requestCode) {
            case REQUEST_REQUESTFILEPERMISSION:
                if (PermissionUtils.verifyPermissions(grantResults)) {
                    target.requestFilePermission();
                }
                break;
            default:
                break;
        }
    }
}
