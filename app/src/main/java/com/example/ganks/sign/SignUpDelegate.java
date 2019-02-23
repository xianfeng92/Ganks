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
public class SignUpDelegate extends GankDelegate {
    private TextInputEditText mName = null;
    private TextInputEditText mEmail = null;
    private TextInputEditText mPhone = null;
    private TextInputEditText mPassword = null;
    private TextInputEditText mRePassword = null;
    private AppCompatButton mButton = null;
    private AppCompatTextView mSignInLink = null;
    private ISignListener mISignListener = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
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
        mName = rootView.findViewById(R.id.edit_sign_up_name);
        mEmail = rootView.findViewById(R.id.edit_sign_up_email);
        mPhone = rootView.findViewById(R.id.edit_sign_up_phone);
        mPassword = rootView.findViewById(R.id.edit_sign_up_password);
        mRePassword = rootView.findViewById(R.id.edit_sign_up_re_password);
        mButton = rootView.findViewById(R.id.btn_sign_up);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickSignUp();
            }
        });
        mSignInLink = rootView.findViewById(R.id.tv_link_sign_in);
        mSignInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLink();
            }
        });
    }

    private boolean checkForm() {
        final String name = mName.getText().toString();
        final String email = mEmail.getText().toString();
        final String phone = mPhone.getText().toString();
        final String password = mPassword.getText().toString();
        final String rePassword = mRePassword.getText().toString();

        boolean isPass = true;

        if (name.isEmpty()) {
            mName.setError("请输入姓名");
            isPass = false;
        } else {
            mName.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        } else {
            mEmail.setError(null);
        }

        if (phone.isEmpty() || phone.length() != 11) {
            mPhone.setError("手机号码错误");
            isPass = false;
        } else {
            mPhone.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("请填写至少6位数密码");
            isPass = false;
        } else {
            mPassword.setError(null);
        }

        if (rePassword.isEmpty() || rePassword.length() < 6 || !(rePassword.equals(password))) {
            mRePassword.setError("密码验证错误");
            isPass = false;
        } else {
            mRePassword.setError(null);
        }

        return isPass;
    }


    private void onClickSignUp() {
        if (checkForm()) {
            if (mISignListener != null){
                mISignListener.onSignInSuccess();
                getSupportDelegate().start(new SignInDelegate());
            }
        }
    }

    private void onClickLink() {
        getSupportDelegate().start(new SignInDelegate());
    }
}
