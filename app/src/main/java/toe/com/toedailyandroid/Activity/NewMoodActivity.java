package toe.com.toedailyandroid.Activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.rey.material.widget.EditText;
import com.rey.material.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Date;

import toe.com.toedailyandroid.Adapter.NavTabPagerAdapter;
import toe.com.toedailyandroid.Entity.Mood;
import toe.com.toedailyandroid.R;
import toe.com.toedailyandroid.Service.MoodService;
import toe.com.toedailyandroid.Utils.UserAuthDataManager;


public class NewMoodActivity extends AppCompatActivity implements MoodService.NewMoodListener{

    private Spinner mMoodTypeSpin;
    private EditText mMoodContentET;
    private Button mSubmitBtn;

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

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.mood_type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMoodTypeSpin.setAdapter(adapter);

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserAuthDataManager authDataManager = new UserAuthDataManager(NewMoodActivity.this);
                AuthData authData = authDataManager.getUserAuthData();
                if(authData != null) {
                    String moodType = mMoodTypeSpin.getSelectedItem().toString();
                    String moodContent = mMoodContentET.getText().toString();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date();
                    Mood mood = new Mood();
                    mood.setMoodType(moodType);
                    mood.setMoodContent(moodContent);
                    mood.setCreatedBy(authData.getUid());
                    mood.setCreatedAt(sdf.format(date));
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
    }

    @Override
    public void newMoodFail(String errorMsg) {
        Toast.makeText(NewMoodActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
    }
}
