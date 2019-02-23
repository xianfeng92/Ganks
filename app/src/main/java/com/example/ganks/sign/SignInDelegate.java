package com.example.ganks.sign;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Patterns;
import android.view.View;

import com.example.ganks.R;
import com.xforg.gank_core.delegates.GankDelegate;

/**
 * Created By apple on 2019/2/23
 * github: https://github.com/xianfeng92
 */
public class SignInDelegate extends GankDelegate {

    private TextInputEditText mEmail = null;
    private TextInputEditText mPassword = null;
    private ISignListener mISignListener = null;
    private AppCompatButton mButton = null;
    private AppCompatTextView mLinkSignUp = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_in;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ISignListener){
            mISignListener = (ISignListener)context;
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        mEmail = rootView.findViewById(R.id.edit_sign_in_email);
        mPassword = rootView.findViewById(R.id.edit_sign_in_password);
        mButton = rootView.findViewById(R.id.btn_sign_in);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignIn();
            }
        });
        mLinkSignUp = rootView.findViewById(R.id.tv_link_sign_up);
        mLinkSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLink();
            }
        });

    }

    private boolean checkForm() {
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        boolean isPass = true;

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        return isPass;
    }

    private void onClickLink() {
        getSupportDelegate().start(new SignUpDelegate());
    }

    private void onClickSignIn() {
        if (checkForm()) {
            // 这里先只做简单的格式验证
            if (mISignListener != null){
                mISignListener.onSignInSuccess();
                AccountManager.setSignState(true);
            }
        }
    }

}
