package com.example.ganks.mvp.ui.activitys;

import android.os.Bundle;
import android.util.Log;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.domain.MeiziList;
import com.example.domain.repository.RepositoryManager;
import com.example.ganks.ConfigManage;
import com.example.ganks.R;
import com.example.ganks.base.GanksApplication;
import com.example.ganks.delegates.BaseDelegate;
import com.example.ganks.mvp.ui.TabSelectedEvent;
import com.example.ganks.mvp.ui.fragment.HomeFragment;
import com.example.ganks.mvp.ui.fragment.LoveMeiziFragment;
import com.example.ganks.mvp.ui.fragment.MeiziFragment;
import com.example.ganks.mvp.ui.fragment.TanTanFragment;
import com.orhanobut.logger.Logger;
import com.xforg.easyimage.ImageLoader;
import com.xforg.easyimage.config.ConfigBuilder;
import com.xforg.easyimage.config.ImageConfig;
import com.xforg.gank_core.StatusBar.StatusBarUtil;
import com.xforg.gank_core.Proxy.ProxyActivity;
import com.example.ganks.delegates.GankDelegate;
import com.xforg.gank_core.utils.RxUtils;
import com.xforg.gank_core.utils.ToastUtils;
import com.xforg.gank_core.widge.BottomBar;
import com.xforg.gank_core.widge.BottomBarTab;
import com.example.ganks.common.EventBusActivityScope;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ContentActivity extends ProxyActivity implements GankDelegate.OnBackToFirstListener{
    private static final String TAG = "ContentActivity";

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

    @Inject
    RepositoryManager repositoryManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Logger.d("onCreate");
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
        initializeInjector();
        cacheRandomImg();
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

    public void saveCacheImgUrl(String url){
        Log.d(TAG, "saveCacheImgUrl: "+url);
        ImageConfig config = new ConfigBuilder(this)
                .url(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .build();
        ImageLoader.getActualLoader().apply(config).preload();
        ConfigManage.INSTANCE.setBannerURL(url);
    }

    private void cacheRandomImg() {
        if (!ConfigManage.INSTANCE.isShowLauncherImg()) { // 不显示欢迎妹子，也就不需要预加载了
            return;
        }
        if (ConfigManage.INSTANCE.isProbabilityShowLauncherImg()) { // 概率出现欢迎妹子
            if (Math.random() < 0.75) {
                ConfigManage.INSTANCE.setBannerURL("");
                return;
            }
        }
        Observable<MeiziList> observable = repositoryManager.getRandomMeizi(1);
        observable.compose(RxUtils.rxSchedulerHelper())
                .subscribe(new Observer<MeiziList>() {
                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MeiziList meiziResult) {
                        if (meiziResult != null && meiziResult.results != null && meiziResult.results.size() > 0 && meiziResult.results.get(0).url != null) {
                            Log.d(TAG, "onNext: ");
                            saveCacheImgUrl(meiziResult.results.get(0).url);
                        }
                    }
                });
    }

    private void initializeInjector() {
        GanksApplication.getApplicationComponent().contentActivityComponent().build().inject(this);
    }
}
