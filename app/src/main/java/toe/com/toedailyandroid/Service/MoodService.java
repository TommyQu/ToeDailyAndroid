package toe.com.toedailyandroid.Service;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import toe.com.toedailyandroid.Activity.NewMoodActivity;
import toe.com.toedailyandroid.Entity.Mood;

/**
 * Created by HQu on 9/28/2016.
 */

public class MoodService {
    private static final String TAG = "ToeMoodService:";
    private Context mContext;

    public MoodService(Context context) {
        mContext = context;
    }

    public void newMood(Mood mood) {
        Firebase ref = new Firebase("https://toedailyandroid.firebaseio.com");
        Firebase moodRef = ref.child("mood");

        moodRef.push().setValue(mood, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if(firebaseError == null) {
                    Toast.makeText(mContext, "Add mood successfully!", Toast.LENGTH_SHORT).show();
                    ((Activity) mContext).finish();
                } else {
                    Toast.makeText(mContext, firebaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
