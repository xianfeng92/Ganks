package com.example.ganks.ui.activitys;

import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Window;

import com.example.ganks.R;
import com.example.ganks.ui.TabSelectedEvent;
import com.example.ganks.ui.fragment.BaseMainFragment;
import com.example.ganks.ui.widge.BottomBar;
import com.example.ganks.ui.widge.BottomBarTab;
import com.example.ganks.ui.fragment.HomeFragment;
import com.example.ganks.ui.fragment.MeiziFragment;
import com.example.ganks.ui.fragment.TanTanFragment;
import com.xforg.gank_core.app.EventBusActivityScope;

import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.SupportFragment;

public class MainActivity extends SupportActivity implements BaseMainFragment.OnBackToFirstListener{

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;

    private SupportFragment[] mFragments = new SupportFragment[3];
    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        SupportFragment firstFragment = findFragment(HomeFragment.class);
        if (firstFragment == null){
            mFragments[0] = HomeFragment.newInstance();
            mFragments[1] = MeiziFragment.newInstance();
            mFragments[2] = TanTanFragment.newInstance();

            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD]);
        }else {
            // 这里我们需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findFragment(HomeFragment.class);
            mFragments[THIRD] = findFragment(MeiziFragment.class);
        }
        initView();
    }

    private void initView(){
        mBottomBar = findViewById(R.id.bottomBar);
        mBottomBar.addItem(new BottomBarTab(this,R.drawable.ic_home_white_24dp))
                .addItem(new BottomBarTab(this,R.drawable.ic_discover_white_24dp))
                .addItem(new BottomBarTab(this,R.drawable.ic_message_white_24dp));
        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                final SupportFragment currentFragment = mFragments[position];
                int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();
                if (count > 1){
                    if (currentFragment instanceof HomeFragment) {
                        currentFragment.popToChild(HomeFragment.class, false);
                    } else if (currentFragment instanceof MeiziFragment) {
                        currentFragment.popToChild(MeiziFragment.class, false);
                    } else if (currentFragment instanceof TanTanFragment) {
                        currentFragment.popToChild(TanTanFragment.class, false);
                    }
                    return;
                }
                // 这里推荐使用EventBus来实现 -> 解耦
                if (count == 1) {
                    // 在FirstPagerFragment中接收, 因为是嵌套的孙子Fragment 所以用EventBus比较方便
                    // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                    EventBusActivityScope.getDefault(MainActivity.this).post(new TabSelectedEvent(position));
                }
            }
        });
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
            ActivityCompat.finishAfterTransition(this);
        }
    }

    @Override
    public void onBackToFirstFragment() {
        mBottomBar.setCurrentItem(0);
    }

}
