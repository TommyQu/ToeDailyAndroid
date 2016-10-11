package toe.com.toedailyandroid.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import toe.com.toedailyandroid.Adapter.MoodsSpinnerAdapter;
import toe.com.toedailyandroid.Entity.Mood;
import toe.com.toedailyandroid.Entity.MoodsSpinnerItem;
import toe.com.toedailyandroid.R;
import toe.com.toedailyandroid.Service.MoodService;
import toe.com.toedailyandroid.Utils.MoodTextImgConverter;
import toe.com.toedailyandroid.Utils.UserAuthDataManager;


public class NewMoodActivity extends AppCompatActivity implements MoodService.NewMoodListener {

    private static final String TAG = "ToeNewMoodActivity:";
    private Spinner mMoodTypeSpin;
    private EditText mMoodContentET;
    private Button mSubmitBtn;
    private MoodsSpinnerAdapter mMoodsSpinnerAdapter;
    private MaterialDialog mDialog;
    private MaterialDialog.Builder mBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mood);
        getSupportActionBar().setTitle("New Mood");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Firebase.setAndroidContext(this);

        mMoodTypeSpin = (Spinner)findViewById(R.id.mood_type_spin);
        mMoodContentET = (EditText)findViewById(R.id.mood_content);
        mSubmitBtn = (Button)findViewById(R.id.submit_btn);


        List<MoodsSpinnerItem> moodSpinnerItems = new ArrayList<MoodsSpinnerItem>();
        List<String> moodTypeArray = Arrays.asList(getResources().getStringArray(R.array.mood_type_array));
        MoodTextImgConverter converter = new MoodTextImgConverter();
        for(int i = 0; i < moodTypeArray.size(); i++) {
            moodSpinnerItems.add(new MoodsSpinnerItem(moodTypeArray.get(i), converter.convertToImg(moodTypeArray.get(i))));
        }

        mMoodsSpinnerAdapter = new MoodsSpinnerAdapter(NewMoodActivity.this, moodSpinnerItems);

        mMoodTypeSpin.setAdapter(mMoodsSpinnerAdapter);

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAuthDataManager authDataManager = new UserAuthDataManager(NewMoodActivity.this);
                AuthData authData = authDataManager.getUserAuthData();
                if(authData != null) {
                    TextView textView = (TextView) mMoodTypeSpin.getSelectedView().findViewById(R.id.mood_type_text);
                    String moodType = textView.getText().toString();
                    String moodContent = mMoodContentET.getText().toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                    Date date = new Date();
                    Mood mood = new Mood();
                    mood.setMoodType(moodType);
                    mood.setMoodContent(moodContent);
                    mood.setCreatedBy(authData.getUid());
                    mood.setCreatedAt(sdf.format(date));
                    mBuilder = new MaterialDialog.Builder(NewMoodActivity.this)
                            .title("Submitting mood")
                            .content("Please wait")
                            .positiveText("Cancel");
                    mDialog = mBuilder.build();
                    mDialog.show();
                    MoodService moodService = new MoodService(NewMoodActivity.this, NewMoodActivity.this, "newMood");
                    moodService.newMood(mood);
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
    public void newMoodSucceed() {
        Toast.makeText(NewMoodActivity.this, "Add mood successfully!", Toast.LENGTH_SHORT).show();
        finish();
        mDialog.dismiss();
    }

    @Override
    public void newMoodFail(String errorMsg) {
        Toast.makeText(NewMoodActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        mDialog.dismiss();
    }

}
