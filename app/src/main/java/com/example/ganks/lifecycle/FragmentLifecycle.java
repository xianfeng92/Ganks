package com.example.ganks.lifecycle;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;

import com.example.ganks.fragment.FragmentDelegate;
import com.example.ganks.fragment.FragmentDelegateImpl;
import com.example.ganks.fragment.IFragment;

/**
 * Created By zhongxianfeng on 19-2-2
 * github: https://github.com/xianfeng92
 */
public class FragmentLifecycle extends FragmentManager.FragmentLifecycleCallbacks {

    private static final String TAG = "FragmentLifecycle";

    @Override
    public void onFragmentAttached(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull Context context) {
        Log.d(TAG, "onFragmentAttached: ");
        super.onFragmentAttached(fm, f, context);
        if (f instanceof IFragment && f.getArguments() != null) {
            FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
            if (fragmentDelegate == null || !fragmentDelegate.isAdded()) {
                fragmentDelegate = new FragmentDelegateImpl(fm, f);
                f.getArguments().putParcelable(FragmentDelegate.FRAGMENT_DELEGATE, fragmentDelegate);
            }
            fragmentDelegate.onAttach(context);
        }
    }

    @Override
    public void onFragmentCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onFragmentCreated: ");
        super.onFragmentCreated(fm, f, savedInstanceState);
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate == null){
            Log.d(TAG, "fragmentDelegate is null ");
        }
        if (fragmentDelegate != null) {
            Log.d(TAG, "onFragmentCreated: "+"for test");
            fragmentDelegate.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onFragmentViewCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onFragmentViewCreated(fm, f, v, savedInstanceState);
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onCreateView(v, savedInstanceState);
        }
    }

    @Override
    public void onFragmentActivityCreated(@NonNull FragmentManager fm, @NonNull Fragment f, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onFragmentActivityCreated: ");
        super.onFragmentActivityCreated(fm, f, savedInstanceState);
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null){
            fragmentDelegate.onActivityCreate(savedInstanceState);
        }
    }

    @Override
    public void onFragmentStarted(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentStarted(fm, f);
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null){
            fragmentDelegate.onStart();
        }
    }

    @Override
    public void onFragmentResumed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentResumed(fm, f);
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null){
            fragmentDelegate.onResume();
        }
    }

    @Override
    public void onFragmentPaused(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentPaused(fm, f);
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null){
            fragmentDelegate.onPause();
        }
    }

    @Override
    public void onFragmentStopped(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentStopped(fm, f);
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null){
            fragmentDelegate.onStop();
        }
    }

    @Override
    public void onFragmentViewDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentViewDestroyed(fm, f);
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onDestroyView();
        }
    }


    @Override
    public void onFragmentDestroyed(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentDestroyed(fm, f);
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onDestroy();
        }
    }

    @Override
    public void onFragmentDetached(@NonNull FragmentManager fm, @NonNull Fragment f) {
        super.onFragmentDetached(fm, f);
        FragmentDelegate fragmentDelegate = fetchFragmentDelegate(f);
        if (fragmentDelegate != null) {
            fragmentDelegate.onDetach();
            f.getArguments().clear();
        }
    }

    private FragmentDelegate fetchFragmentDelegate(Fragment fragment) {
        if (fragment instanceof IFragment) {
            Log.d(TAG, "fetchFragmentDelegate: ");
            return fragment.getArguments() == null ? null :(FragmentDelegate) fragment.getArguments().getParcelable("fragment_delegate");
        }
        return null;
    }
}
