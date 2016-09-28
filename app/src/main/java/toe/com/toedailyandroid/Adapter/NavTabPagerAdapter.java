package toe.com.toedailyandroid.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import toe.com.toedailyandroid.Activity.HomeFragment;
import toe.com.toedailyandroid.Activity.MoodsFragment;
import toe.com.toedailyandroid.Activity.ProfileFragment;
import toe.com.toedailyandroid.Activity.SchedulesFragment;

/**
 * Created by HQu on 9/27/2016.
 */

public class NavTabPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[] = new String[]{"", "", "", ""};
    private Context mContext;

    public NavTabPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new MoodsFragment();
            case 2:
                return new SchedulesFragment();
            case 3:
                return new ProfileFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
