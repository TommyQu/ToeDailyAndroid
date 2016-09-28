package toe.com.toedailyandroid.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import toe.com.toedailyandroid.R;

/**
 * Created by HQu on 9/27/2016.
 */

public class SchedulesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_schedules_fragment,container,false);
    }

}
