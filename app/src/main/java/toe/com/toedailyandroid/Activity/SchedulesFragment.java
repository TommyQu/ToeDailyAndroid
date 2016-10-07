package toe.com.toedailyandroid.Activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.util.Locale;
import java.util.TimeZone;

import toe.com.toedailyandroid.R;

/**
 * Created by HQu on 9/27/2016.
 */

public class SchedulesFragment extends Fragment {

    private static final String TAG = "ToeSchedulesFragment:";
    private MaterialCalendarView mCalendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_schedules_fragment,container,false);
        mCalendarView = (MaterialCalendarView) view.findViewById(R.id.calendar);
        mCalendarView.setContentDescription("hahaha");
        mCalendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                if (day.getDay() == 11) {
                    Log.i(TAG, String.valueOf(day.getDay()));
                    return true;
                }
                return false;
            }

            @Override
            public void decorate(DayViewFacade view) {
                Log.i(TAG, "add");
                view.addSpan(DotSpan.DEFAULT_RADIUS);
            }
        });
        mCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Log.i(TAG, String.valueOf(date.getYear()));
            }
        });

        return view;
    }

}
