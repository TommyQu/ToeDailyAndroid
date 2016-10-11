package toe.com.toedailyandroid.Service;

import android.content.Context;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import toe.com.toedailyandroid.Entity.Mood;
import toe.com.toedailyandroid.Entity.Schedule;
import toe.com.toedailyandroid.Utils.UserAuthDataManager;

/**
 * Created by HQu on 10/11/2016.
 */

public class ScheduleService {
    private static final String TAG = "ToeScheduleService:";
    private NewScheduleListener mNewScheduleListener;
    private GetAllSchedulesListener mGetAllSchedulesListener;
    private Context mContext;

    public interface NewScheduleListener {
        public void newScheduleSucceed();
        public void newScheduleFail(String errorMsg);
    }

    public interface GetAllSchedulesListener {
        public void getAllSchedulesSucceed(List<Schedule> schedules);
        public void getAllSchedulesFail(String errorMsg);
    }

    public ScheduleService(Context context, Object scheduleListener, String action) {
        mContext = context;
        if(action.equalsIgnoreCase("newSchedule"))
            mNewScheduleListener = (NewScheduleListener)scheduleListener;
        else if(action.equalsIgnoreCase("getAllSchedules"))
            mGetAllSchedulesListener = (GetAllSchedulesListener)scheduleListener;
    }

    public void newSchedule(Schedule schedule) {
        Firebase ref = new Firebase("https://toedailyandroid.firebaseio.com/schedule");
        ref.push().setValue(schedule, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if(firebaseError == null) {
                    mNewScheduleListener.newScheduleSucceed();
                } else {
                    mNewScheduleListener.newScheduleFail(firebaseError.getMessage().toString());
                }
            }
        });
    }

    public void getAllSchedules() {
        final List<Schedule> schedules = new ArrayList<Schedule>();
        UserAuthDataManager authDataManager = new UserAuthDataManager(mContext);
        AuthData authData = authDataManager.getUserAuthData();
        Firebase ref = new Firebase("https://toedailyandroid.firebaseio.com/schedule");
        Query queryRef = ref.orderByChild("createdBy").equalTo(authData.getUid());
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                schedules.clear();
                for (DataSnapshot scheduleSnapshot: dataSnapshot.getChildren()) {
                    Schedule schedule = scheduleSnapshot.getValue(Schedule.class);
                    schedule.setId(scheduleSnapshot.getKey());
                    schedules.add(schedule);
                }
                mGetAllSchedulesListener.getAllSchedulesSucceed(schedules);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                mGetAllSchedulesListener.getAllSchedulesFail(firebaseError.getMessage().toString());
            }
        });
    }
}
