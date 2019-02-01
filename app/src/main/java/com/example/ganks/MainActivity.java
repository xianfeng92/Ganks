package com.example.ganks;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.example.ganks.fragment.HomeFragment;
import com.example.ganks.fragment.MeiziFragment;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // UI Object
    // private TextView txt_topbar;
    private TextView txt_home;
    private TextView txt_meizi;
    private TextView txt_video;
    private TextView txt_setting;
    private FrameLayout ly_content;
    private List<Integer> mTitles;

    // Fragment Object
   // private MyFragment fg3,fg4;
    private HomeFragment homeFragment;
    private MeiziFragment meiziFragment;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        fManager = getSupportFragmentManager();
        bindViews();
        txt_home.performClick();//模拟一次点击，既进去后选择第一项
    }

    //UI组件初始化与事件绑定
    private void bindViews() {
//        txt_topbar = (TextView) findViewById(R.id.txt_topbar);
        txt_home = (TextView) findViewById(R.id.txt_home);
        txt_meizi = (TextView) findViewById(R.id.txt_meizi);
        txt_video = (TextView) findViewById(R.id.txt_video);
        txt_setting = (TextView) findViewById(R.id.txt_setting);
        ly_content = (FrameLayout) findViewById(R.id.ly_content);

        txt_home.setOnClickListener(this);
        txt_meizi.setOnClickListener(this);
        txt_video.setOnClickListener(this);
        txt_setting.setOnClickListener(this);
    }

    //重置所有文本的选中状态
    private void setSelected(){
        txt_home.setSelected(false);
        txt_meizi.setSelected(false);
        txt_video.setSelected(false);
        txt_setting.setSelected(false);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(homeFragment != null)fragmentTransaction.hide(homeFragment);
        if(meiziFragment != null)fragmentTransaction.hide(meiziFragment);
//        if(fg3 != null)fragmentTransaction.hide(fg3);
//        if(fg4 != null)fragmentTransaction.hide(fg4);
    }


    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (v.getId()){
            case R.id.txt_home:
                setSelected();
                txt_home.setSelected(true);
                if(homeFragment == null){
                    homeFragment = HomeFragment.newInstance();
                    fTransaction.add(R.id.ly_content,homeFragment);
                }else{
                    fTransaction.show(homeFragment);
                }
                break;
            case R.id.txt_meizi:
                setSelected();
                txt_meizi.setSelected(true);
                if(meiziFragment == null){
                    meiziFragment = MeiziFragment.newInstance();
                    fTransaction.add(R.id.ly_content,meiziFragment);
                }else{
                    fTransaction.show(meiziFragment);
                }
                break;
            case R.id.txt_video:
                setSelected();
                txt_video.setSelected(true);
//                if(fg3 == null){
//                    fg3 = MyFragment.newInstance("第三个Fragment");
//                    fTransaction.add(R.id.ly_content,fg3);
//                }else{
//                    fTransaction.show(fg3);
//                }
                break;
            case R.id.txt_setting:
                setSelected();
                txt_setting.setSelected(true);
//                if(fg4 == null){
//                    fg4 = MyFragment.newInstance("第四个Fragment");
//                    fTransaction.add(R.id.ly_content,fg4);
//                }else{
//                    fTransaction.show(fg4);
//                }
                break;
        }
        fTransaction.commit();
    }
}
