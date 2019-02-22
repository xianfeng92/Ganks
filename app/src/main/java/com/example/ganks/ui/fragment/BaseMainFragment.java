package com.example.ganks.ui.fragment;

import android.content.Context;


import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created By zhongxianfeng on 19-2-22
 * github: https://github.com/xianfeng92
 * 懒加载
 */
public abstract class BaseMainFragment extends SupportFragment {

    private OnBackToFirstListener _mBackToFirstListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnBackToFirstListener){
            _mBackToFirstListener = (OnBackToFirstListener)context;
        }else {
            throw new RuntimeException(context.toString()
                    + " must implement OnBackToFirstListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        _mBackToFirstListener = null;
    }

    /**
     * 处理回退事件
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        if (getChildFragmentManager().getBackStackEntryCount() > 1){
            popChild();
        }else {
            if (this instanceof HomeFragment){
                // 如果是 第一个Fragment 则退出app
                _mActivity.finish();
            }else {
                // 如果不是,则回到第一个Fragment
                _mBackToFirstListener.onBackToFirstFragment();
            }
        }
        return true;
    }

    public interface OnBackToFirstListener {
        void onBackToFirstFragment();
    }
}
