package com.example.flousquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.flousquiz.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private View decorView;
    //Initialize variable
    MeowBottomNavigation bottomNavigation;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        decorView = getWindow().getDecorView();
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            if(visibility == 0){
                decorView.setSystemUiVisibility(hideSystemBars());
            }
        });



        //Assign variable
        bottomNavigation = findViewById(R.id.bottom_navigation);

        //Add menu item
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_rank));
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_wallet));
        bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_user));

        bottomNavigation.setOnShowListener(item -> {

            //Initialize fragment
            Fragment fragment = null;
            //Check condition
            switch (item.getId()){
                case 1:
                    //when id is 1
                    //Initialize home fragment
                    fragment = new HomeFragment();
                    break;
                case 2:
                    //When id id 2
                    //Initialize Rank fragment
                    fragment = new RankFragment();
                    break;
                case 3:
                    //When id is 3
                    //Initialize Wallet fragment
                    fragment = new WalletFragment();
                    break;
                case 4:
                    //When id is 4
                    //Initialize User fragment
                    fragment = new UserFragment();
                    break;
            }
            //Load fragment
            loadFragment(fragment);

        });

        //Set notification count
        bottomNavigation.setCount(1,"10");

        //Set HomeFragment initially selected
        bottomNavigation.show(1,true);

        bottomNavigation.setOnClickMenuListener(item -> {

        });

        bottomNavigation.setOnReselectListener(item -> {

        });

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus){
            decorView.setSystemUiVisibility(hideSystemBars());
        }
    }

    private int hideSystemBars(){
        return View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                |View.SYSTEM_UI_FLAG_FULLSCREEN
                |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
    }

    private void loadFragment(Fragment fragment) {

        //Replace fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finishAffinity();
    }
}