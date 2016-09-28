package toe.com.toedailyandroid.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.rey.material.widget.EditText;
import com.rey.material.widget.Spinner;

import toe.com.toedailyandroid.Adapter.NavTabPagerAdapter;
import toe.com.toedailyandroid.Entity.Mood;
import toe.com.toedailyandroid.R;
import toe.com.toedailyandroid.Service.MoodService;


public class NewMoodActivity extends AppCompatActivity {

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
                String moodType = mMoodTypeSpin.getSelectedItem().toString();
                String moodContent = mMoodContentET.getText().toString();
                Mood mood = new Mood();
                mood.setMoodType(moodType);
                mood.setMoodContent(moodContent);
                MoodService moodService = new MoodService(NewMoodActivity.this);
                moodService.newMood(mood);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
}
