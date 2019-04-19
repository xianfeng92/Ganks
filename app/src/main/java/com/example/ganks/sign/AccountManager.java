package com.example.ganks.sign;


/**
 * Created By apple on 2019/2/23
 * github: https://github.com/xianfeng92
 */
public class AccountManager {

    private enum SignTag {
        SIGN_TAG
    }

    //保存用户登录状态，登录后调用
    public static void setSignState(boolean state) {

    }

    public static boolean isSignIn() {

        return true;
    }


    public static void checkAccount(IUserChecker checker) {
        if (isSignIn()) {
            checker.onSignIn();
        } else {
            checker.onNotSignIn();
        }
    }
}
