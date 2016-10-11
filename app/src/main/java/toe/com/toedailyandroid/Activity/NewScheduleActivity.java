package toe.com.toedailyandroid.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import toe.com.toedailyandroid.Entity.Schedule;
import toe.com.toedailyandroid.R;
import toe.com.toedailyandroid.Service.MoodService;
import toe.com.toedailyandroid.Service.ScheduleService;
import toe.com.toedailyandroid.Utils.UserAuthDataManager;


public class NewScheduleActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, ScheduleService.NewScheduleListener {

    private static final String TAG = "ToeNewScheduleActivity:";
    private TextView mStartTimeText;
    private Button mSelectStartTimeBtn;
    private TextView mEndTimeText;
    private Button mSelectEndTimeBtn;
    private EditText mTitleET;
    private EditText mContentET;
    private EditText mLocationET;
    private Button mSubmitBtn;

    private DatePickerDialog mDpd;
    private TimePickerDialog mTpd;
    private String mStartTime = "";
    private String mEndTime = "";
    private String mTimeTypeStr = "";

    private MaterialDialog mDialog;
    private MaterialDialog.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_schedule);
        getSupportActionBar().setTitle("New Schedule");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Firebase.setAndroidContext(this);

        mStartTimeText = (TextView)findViewById(R.id.start_time_text);
        mSelectStartTimeBtn = (Button)findViewById(R.id.select_start_time_btn);
        mEndTimeText = (TextView)findViewById(R.id.end_time_text);
        mSelectEndTimeBtn = (Button)findViewById(R.id.select_end_time_btn);
        mTitleET = (EditText)findViewById(R.id.schedule_title);
        mContentET = (EditText)findViewById(R.id.schedule_content);
        mLocationET = (EditText)findViewById(R.id.schedule_location);
        mSubmitBtn = (Button)findViewById(R.id.submit_btn);

        Calendar now = Calendar.getInstance();
        mDpd = DatePickerDialog.newInstance(NewScheduleActivity.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        mTpd = TimePickerDialog.newInstance(NewScheduleActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                true);

        mSelectStartTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimeTypeStr = "start";
                mDpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        mSelectEndTimeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimeTypeStr = "end";
                mDpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                String startTime = mStartTimeText.getText().toString();
                String endTime = mEndTimeText.getText().toString();
                try {
                    if(startTime.equalsIgnoreCase("Start time") || startTime == null
                            || endTime.equalsIgnoreCase("End time") || endTime == null
                            || mTitleET.getText().toString().equalsIgnoreCase("") || mTitleET == null
                            || mContentET.getText().toString().equalsIgnoreCase("") || mContentET == null) {
                        Toast.makeText(NewScheduleActivity.this, "Please fill required fields!", Toast.LENGTH_SHORT).show();
                    }
                    else if(sdf.parse(startTime).after(sdf.parse(endTime))) {
                        Toast.makeText(NewScheduleActivity.this, "Start time should not after end time!", Toast.LENGTH_SHORT).show();
                    } else {
                        UserAuthDataManager authDataManager = new UserAuthDataManager(NewScheduleActivity.this);
                        AuthData authData = authDataManager.getUserAuthData();
                        Date date = new Date();
                        Schedule schedule = new Schedule();
                        schedule.setStartTime(startTime);
                        schedule.setEndTime(endTime);
                        schedule.setTitle(mTitleET.getText().toString());
                        schedule.setContent(mContentET.getText().toString());
                        schedule.setLocation(mLocationET.getText().toString());
                        schedule.setCreatedAt(sdf.format(date));
                        schedule.setCreatedBy(authData.getUid());
                        mBuilder = new MaterialDialog.Builder(NewScheduleActivity.this)
                                .title("Submitting schedule")
                                .content("Please wait")
                                .positiveText("Cancel");
                        mDialog = mBuilder.build();
                        mDialog.show();
                        ScheduleService scheduleService = new ScheduleService(NewScheduleActivity.this, NewScheduleActivity.this, "newSchedule");
                        scheduleService.newSchedule(schedule);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        if(mTimeTypeStr == "start")
            mStartTime += String.format("%02d", monthOfYear+1)+"/"+String.format("%02d", dayOfMonth)+"/"+year;
        else
            mEndTime += String.format("%02d", monthOfYear+1)+"/"+String.format("%02d", dayOfMonth)+"/"+year;
        mTpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        if(mTimeTypeStr == "start") {
            mStartTime += " "+String.format("%02d", hourOfDay)+":"+String.format("%02d", minute)+":"+String.format("%02d", second);
            mStartTimeText.setText(mStartTime);
            mStartTime = "";
        }
        else {
            mEndTime += " "+String.format("%02d", hourOfDay)+":"+String.format("%02d", minute)+":"+String.format("%02d", second);
            mEndTimeText.setText(mEndTime);
            mEndTime = "";
        }

    }

    @Override
    public void newScheduleSucceed() {
        Toast.makeText(NewScheduleActivity.this, "Add schedule successfully!", Toast.LENGTH_SHORT).show();
        finish();
        mDialog.dismiss();
    }

    @Override
    public void newScheduleFail(String errorMsg) {
        Toast.makeText(NewScheduleActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        mDialog.dismiss();
    }
}
