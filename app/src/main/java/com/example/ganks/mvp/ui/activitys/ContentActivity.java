package com.example.ganks.mvp.ui.activitys;

import android.os.Bundle;
import com.example.ganks.R;
import com.example.ganks.delegates.BaseDelegate;
import com.example.ganks.mvp.ui.TabSelectedEvent;
import com.example.ganks.mvp.ui.fragment.HomeFragment;
import com.example.ganks.mvp.ui.fragment.LoveMeiziFragment;
import com.example.ganks.mvp.ui.fragment.MeiziFragment;
import com.example.ganks.mvp.ui.fragment.TanTanFragment;
import com.xforg.gank_core.StatusBar.StatusBarUtil;
import com.xforg.gank_core.activitys.ProxyActivity;
import com.example.ganks.delegates.GankDelegate;
import com.xforg.gank_core.utils.ToastUtils;
import com.xforg.gank_core.widge.BottomBar;
import com.xforg.gank_core.widge.BottomBarTab;
import com.xforg.gank_core.app.EventBusActivityScope;


public class ContentActivity extends ProxyActivity implements GankDelegate.OnBackToFirstListener{

    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;

    private BaseDelegate[] mFragments = new BaseDelegate[4];
    private BottomBar mBottomBar;

    private int flag = 0;
    // 再点一次退出程序时间设置
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        BaseDelegate firstFragment = findFragment(HomeFragment.class);
        if (firstFragment == null){
            mFragments[0] = HomeFragment.newInstance();
            mFragments[1] = TanTanFragment.newInstance();
            mFragments[2] = LoveMeiziFragment.newInstance();
            mFragments[3] = MeiziFragment.newInstance();
            //加载多个同级根Fragment
            loadMultipleRootFragment(R.id.fl_container, FIRST,
                    mFragments[FIRST],
                    mFragments[SECOND],
                    mFragments[THIRD],
                    mFragments[FOURTH]
            );
        }else {
            // 这里需要拿到mFragments的引用
            mFragments[FIRST] = firstFragment;
            mFragments[SECOND] = findFragment(TanTanFragment.class);
            mFragments[THIRD] = findFragment(LoveMeiziFragment.class);
            mFragments[FOURTH] = findFragment(MeiziFragment.class);
        }
        initView();
    }

    private void initView(){
        mBottomBar = findViewById(R.id.bottomBar);
        mBottomBar.addItem(new BottomBarTab(this,R.drawable.ic_home_white_24dp))
                .addItem(new BottomBarTab(this,R.drawable.ic_discover_white_24dp))
                .addItem(new BottomBarTab(this,R.drawable.icon_apple))
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
                final BaseDelegate currentFragment = mFragments[position];
                int count = currentFragment.getChildFragmentManager().getBackStackEntryCount();
                if (count > 1){
                    if (currentFragment instanceof HomeFragment) {
                    } else if (currentFragment instanceof TanTanFragment) {
                        currentFragment.popToChild(TanTanFragment.class, false);
                    } else if (currentFragment instanceof LoveMeiziFragment) {
                        StatusBarUtil.setStatusBarAndNavigationBarTranslucent(ContentActivity.this);
                    } else if(currentFragment instanceof MeiziFragment){
                        currentFragment.popToChild(MeiziFragment.class,false);
                    }
                    return;
                }
                // 这里推荐使用EventBus来实现 -> 解耦
                if (count == 1) {
                    // 在FirstPagerFragment中接收, 因为是嵌套的孙子Fragment 所以用EventBus比较方便
                    // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                    EventBusActivityScope.getDefault(ContentActivity.this).post(new TabSelectedEvent(position));
                }
            }
        });
    }

    @Override
    public void onBackPressedSupport() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            pop();
        } else {
                flag = flag + 1;
                if (flag == 1){
                    TOUCH_TIME = System.currentTimeMillis();
                    ToastUtils.showShortToast("再按一次退出");
                }else if (flag == 2){
                    if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME){
                        // 不再Finish到此Activity，仅仅是把它放到后台隐藏
                        // 类似于用户主动触发系统Home键的效果
                        moveTaskToBack(true);
                    }
                    flag = 0;
                }
            }
    }

    @Override
    public void onBackToFirstFragment() {
        mBottomBar.setCurrentItem(0);
    }
}
