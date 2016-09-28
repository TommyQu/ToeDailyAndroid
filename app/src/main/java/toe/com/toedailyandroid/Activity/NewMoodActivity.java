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

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(NewMoodActivity.this);
        String uid = prefs.getString("uid", null);
        Toast.makeText(NewMoodActivity.this, uid, Toast.LENGTH_SHORT).show();
        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String moodType = mMoodTypeSpin.getSelectedItem().toString();
                String moodContent = mMoodContentET.getText().toString();
                Firebase ref = new Firebase("https://toedailyandroid.firebaseio.com");
                Firebase moodRef = ref.child("mood");
                Mood mood = new Mood();
                mood.setMoodType(moodType);
                mood.setMoodContent(moodContent);
                moodRef.push().setValue(mood, new Firebase.CompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                        if(firebaseError == null) {
                            Toast.makeText(NewMoodActivity.this, "Add mood successfully!", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(NewMoodActivity.this, firebaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
