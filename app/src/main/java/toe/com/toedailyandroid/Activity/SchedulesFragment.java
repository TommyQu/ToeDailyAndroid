package toe.com.toedailyandroid.Activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.util.CircularArray;
import android.text.style.BackgroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import toe.com.toedailyandroid.Entity.Schedule;
import toe.com.toedailyandroid.Entity.TDADotSpan;
import toe.com.toedailyandroid.R;
import toe.com.toedailyandroid.Service.ScheduleService;

/**
 * Created by HQu on 9/27/2016.
 */

public class SchedulesFragment extends Fragment implements ScheduleService.GetAllSchedulesListener{

    private static final String TAG = "ToeSchedulesFragment:";
    private MaterialCalendarView mCalendarView;
    private CalendarDay mSelectedDay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        ScheduleService scheduleService = new ScheduleService(getActivity(), this, "getAllSchedules");
        scheduleService.getAllSchedules();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_schedules, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Intent intent = new Intent(getContext(), NewScheduleActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_schedules_fragment,container,false);
        mCalendarView = (MaterialCalendarView) view.findViewById(R.id.calendar);
        return view;
    }

    @Override
    public void getAllSchedulesSucceed(final List<Schedule> schedules) {
        mCalendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay calendarDay) {
                try {
                    for(int i = 0; i < schedules.size(); i++) {
                        String startTime = schedules.get(i).getStartTime();
                        int month = Integer.parseInt(startTime.substring(0, 2));
                        int day = Integer.parseInt(startTime.substring(3,5));
                        int year = Integer.parseInt(startTime.substring(6, 10));
                        if(year == calendarDay.getYear() && month == calendarDay.getMonth() && day == calendarDay.getDay())
                            return true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public void decorate(DayViewFacade view) {
                DotSpan dotSpan = new DotSpan(7, R.color.colorDarkGrey);
                view.addSpan(dotSpan);
            }
        });
    }

    @Override
    public void getAllSchedulesFail(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}
