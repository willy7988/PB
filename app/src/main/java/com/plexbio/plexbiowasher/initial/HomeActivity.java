package com.plexbio.plexbiowasher.initial;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


import com.plexbio.plexbiowasher.R;
import com.plexbio.plexbiowasher.agent.Agent;
import com.plexbio.plexbiowasher.assay.AssayFragment;
import com.plexbio.plexbiowasher.logstatus.LogDisplayFragment;
import com.plexbio.plexbiowasher.menu.MenuFragment;
import com.plexbio.plexbiowasher.servicemode.ServiceModeFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private ActionBar mActionBar;
    private Menu mNavigationMenu;

    private AssayFragment mAssayFragment;
    private MenuFragment mMenuFragment;
    private LogDisplayFragment mLogDisplayFragment;
    private ServiceModeFragment mServiceModeFragment;


    private final List<MenuItem> menuItems = new ArrayList<>();
    private BroadcastReceiver mLangaugeChangedReceiver;
    private static boolean onLanguageChange=false;

    private Handler mHandler = new Handler();
    public Button backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.e("TAG", "On Create");
        super.onCreate(savedInstanceState);
        //get the current language from sharedpreference
        new Agent().changeLanguage(this, new Agent().getCurrentLanguage(this));
        setContentView(R.layout.activity_home);


        init();



        if(onLanguageChange){
            showDetails(menuItems.get(1));
            Log.e("TAG","on boolean change"+onLanguageChange);
        }
        mLangaugeChangedReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                finish();
                startActivity(getIntent());
                onLanguageChange=true;

            }
        };
        registerReceiver(mLangaugeChangedReceiver, new IntentFilter("Language.changed"));

    }

    public void init(){

        backButton= (Button)findViewById(R.id.back_btn);
        backButton.setVisibility(View.INVISIBLE);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nv_menu);

        mAssayFragment = new AssayFragment();
        mMenuFragment = new MenuFragment();
        mLogDisplayFragment = new LogDisplayFragment();
        mServiceModeFragment = new ServiceModeFragment();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_header);
        setSupportActionBar(toolbar);




        //actionbar用自定義的layout
        mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowCustomEnabled(true);
        //隱藏原本置左的title
        mActionBar.setDisplayShowTitleEnabled(false);
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setHomeAsUpIndicator(R.mipmap.menu_icon);
        mActionBar.setHomeButtonEnabled(true);

        //設定側攔，設定初始時default選第一項
        setupDrawerContent(mNavigationView);
        mNavigationMenu = mNavigationView.getMenu();
        mNavigationMenu.getItem(0).setChecked(true);

        //為了要得到menu內所有項目的position.
        for (int i = 0; i < mNavigationMenu.size(); i++) {
            menuItems.add(mNavigationMenu.getItem(i));
        }

        showDetails(menuItems.get(0));

    }

    //為了讓actionbar的Header置中...
    public void setupHeader(String title) {
        new Agent().setupHeader(this,title);
    }

    //設定側邊選單
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(

                new NavigationView.OnNavigationItemSelectedListener() {
                    private MenuItem mPreMenuItem;

                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        if (mPreMenuItem != null) mPreMenuItem.setChecked(false);
                        menuItem.setChecked(true);

                        //點選側蘭項目後關閉drawer
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDrawerLayout.closeDrawers();
                            }
                        },150);

                        showDetails(menuItem);
                        mPreMenuItem = menuItem;
                        return true;
                    }
                });
    }

    //切換fragment，設定header
    void showDetails(MenuItem menuItem) {
        int index = menuItems.indexOf(menuItem);

        setupHeader(menuItem.getTitle().toString());

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction().addToBackStack(null);
        switch (index) {
            case 0:
                ft.replace(R.id.fragment_container,mAssayFragment);
                new Agent().addDBLogs(this,"Select Assay","OK");
                break;
            case 1:
                ft.replace(R.id.fragment_container,mMenuFragment);
              //  new Agent().addLogs(this,"Select Menu","Good");
                new Agent().addDBLogs(this,"Select Menu","OK");
                break;
            case 2:
                ft.replace(R.id.fragment_container,mServiceModeFragment);
                new Agent().addDBLogs(this,"Service Mode","OK");
                break;
            case 3:
                ft.replace(R.id.fragment_container,mLogDisplayFragment);
                new Agent().addDBLogs(this,"Select Logs","OK");
                break;
            case 4:
                finish();
                System.exit(0);
                break;
            default:
                break;
        }
        ft.commit();
    }

    //設定header右邊的兩個按鈕 time & Info
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    //設定選擇drawer打開選單
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                Log.e("TAG", "secelct home");
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.action_bar_clock:
                new Agent().createClockDialog(this);
                break;
            case R.id.action_bar_info:
                new Agent().createInfoDialog(this);
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("TAG","OnStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TAG","OnResume");
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TAG","OnPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("TAG","OnStop");
    }

    @Override
    protected void onDestroy() {
        Log.e("TAG","OnDestroy");
        super.onDestroy();

        if (mLangaugeChangedReceiver != null){
            try {
                unregisterReceiver(mLangaugeChangedReceiver);
                mLangaugeChangedReceiver=null;
            }catch (final Exception e){}
        }

    }





}
