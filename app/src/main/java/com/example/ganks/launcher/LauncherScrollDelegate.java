package com.example.ganks.launcher;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.ganks.R;
import com.xforg.gank_core.delegates.BaseDelegate;
import com.xforg.gank_core.utils.storage.GankPreference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created By zhongxianfeng on 19-2-22
 * github: https://github.com/xianfeng92
 */
public class LauncherScrollDelegate extends BaseDelegate implements OnItemClickListener {

    private ConvenientBanner<Integer> mConvinientBanner = null;
    private final List<Integer> INTEGERS = new ArrayList<>();
    private ILauncherListener mIlaunchListener = null;

    private void initBanner(){
        INTEGERS.add(R.mipmap.banner_1);
        INTEGERS.add(R.mipmap.banner_2);
        INTEGERS.add(R.mipmap.banner_3);
        INTEGERS.add(R.mipmap.banner_4);
        mConvinientBanner
                .setPages(new LauncherHolderCreator(),INTEGERS)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus})
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ILauncherListener){
            mIlaunchListener = (ILauncherListener)context;
        }
    }

    @Override
    public Object setLayout() {
        mConvinientBanner = new ConvenientBanner<>(getContext());
        return mConvinientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        initBanner();
    }


    @Override
    public void onItemClick(int position) {
        if (position == INTEGERS.size()-1){
            GankPreference.setAppFlag(ScrollLauncherTag.HAS_FIRST_LAUNCHER_APP.name(),true);
            if (mIlaunchListener != null){
                mIlaunchListener.onLauncherFinish(OnLauncherFinishTag.SIGNED);
            }
        }
    }
}
