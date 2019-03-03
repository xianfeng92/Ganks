//package com.example.ganks.ui.fragment;
//
//import android.content.Context;
//import com.example.ganks.ui.fragment.article.CategoryArticleFragment;
//import com.xforg.gank_core.utils.ToastUtils;
//import me.yokeyword.fragmentation.SupportFragment;
//
///**
// * Created By zhongxianfeng on 19-2-22
// * github: https://github.com/xianfeng92
// */
//public abstract class BaseMainFragment extends SupportFragment {
//
//    private static final String TAG = "BaseMainFragment";
//    //
//    private int flag = 0;
//    // 再点一次退出程序时间设置
//    private static final long WAIT_TIME = 2000L;
//    private long TOUCH_TIME = 0;
//    public OnBackToFirstListener _mBackToFirstListener;
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if(context instanceof OnBackToFirstListener){
//            _mBackToFirstListener = (OnBackToFirstListener)context;
//        }else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnBackToFirstListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        _mBackToFirstListener = null;
//    }
//
//    /**
//     * 处理回退事件
//     *
//     * @return
//     */
//    @Override
//    public boolean onBackPressedSupport() {
//        if (getChildFragmentManager().getBackStackEntryCount() > 1){
//            popChild();
//        }else {
//            if (this instanceof CategoryArticleFragment){
//                flag = flag + 1;
//                if (flag == 1){
//                    TOUCH_TIME = System.currentTimeMillis();
//                    ToastUtils.showShortToast("再按一次退出");
//                }else if (flag == 2){
//                    if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME){
//                        // 如果是 第一个Fragment 则退出app
//                        _mActivity.finish();
//                    }
//                    flag = 0;
//                }
//            }else {
//                // 如果不是,则回到第一个Fragment
//                _mBackToFirstListener.onBackToFirstFragment();
//            }
//        }
//        return true;
//    }
//
//    // 当前fragment不可见时，重置flag
//    @Override
//    public void onSupportInvisible() {
//        super.onSupportInvisible();
//        flag = 0;
//    }
//
//    public interface OnBackToFirstListener {
//        void onBackToFirstFragment();
//    }
//}
