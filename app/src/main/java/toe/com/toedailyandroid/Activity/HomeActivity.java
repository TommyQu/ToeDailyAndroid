package toe.com.toedailyandroid.Activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import toe.com.toedailyandroid.Adapter.NavTabPagerAdapter;
import toe.com.toedailyandroid.R;
import toe.com.toedailyandroid.Service.UserService;


public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "ToeHomeActivity:";
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);

        mViewPager.setAdapter(new NavTabPagerAdapter(getSupportFragmentManager(), getApplicationContext()));
        mTabLayout.setupWithViewPager(mViewPager);

//        Set tab icon
        mTabLayout.getTabAt(0).setIcon(R.drawable.home_tab);
        mTabLayout.getTabAt(1).setIcon(R.drawable.mood_tab);
        mTabLayout.getTabAt(2).setIcon(R.drawable.schedules_tab);
        mTabLayout.getTabAt(3).setIcon(R.drawable.profile_tab);

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            if (i == 0)
                mTabLayout.getTabAt(i).getIcon().setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
            else
                mTabLayout.getTabAt(i).getIcon().setColorFilter(ContextCompat.getColor(HomeActivity.this, R.color.colorDarkGrey), PorterDuff.Mode.SRC_IN);
        }

//        Change tab color when selected or not
        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager) {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                int tabIconColor = ContextCompat.getColor(HomeActivity.this, R.color.colorPrimary);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
                int tabIconColor = ContextCompat.getColor(HomeActivity.this, R.color.colorDarkGrey);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
        });
    }




}
