package toe.com.toedailyandroid.Service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import toe.com.toedailyandroid.Entity.Mood;
import toe.com.toedailyandroid.Utils.UserAuthDataManager;

/**
 * Created by HQu on 9/28/2016.
 */

public class MoodService {
    private static final String TAG = "ToeMoodService:";
    private NewMoodListener mNewMoodListener;
    private GetAllMoodsListener mGetAllMoodsListener;
    private Context mContext;

    public MoodService(Context context, Object moodListener, String action) {
        mContext = context;
        if(action.equalsIgnoreCase("getAllMoods"))
            mGetAllMoodsListener = (GetAllMoodsListener)moodListener;
        else if(action.equalsIgnoreCase("newMood"))
            mNewMoodListener = (NewMoodListener)moodListener;
    }

    public interface GetAllMoodsListener {
        public void getAllMoodsSucceed(List<Mood> moods);
        public void getAllMoodsFail(String errorMsg);
    }

    public interface NewMoodListener {
        public void newMoodSucceed();
        public void newMoodFail(String errorMsg);
    }

    public void newMood(Mood mood) {
        Firebase ref = new Firebase("https://toedailyandroid.firebaseio.com");
        Firebase moodRef = ref.child("mood");
        moodRef.push().setValue(mood, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if(firebaseError == null) {
                    mNewMoodListener.newMoodSucceed();
                } else {
                    mNewMoodListener.newMoodFail(firebaseError.getMessage().toString());
                }
            }
        });
    }

    public void getAllMoods() {
        final List<Mood> moods = new ArrayList<Mood>();
        UserAuthDataManager authDataManager = new UserAuthDataManager(mContext);
        AuthData authData = authDataManager.getUserAuthData();
        Firebase ref = new Firebase("https://toedailyandroid.firebaseio.com/mood");
        Query queryRef = ref.orderByChild("createdBy").equalTo(authData.getUid());
        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot moodSnapshot: dataSnapshot.getChildren()) {
                    Mood mood = moodSnapshot.getValue(Mood.class);
                    moods.add(mood);
                }
                mGetAllMoodsListener.getAllMoodsSucceed(moods);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                mGetAllMoodsListener.getAllMoodsFail(firebaseError.getMessage().toString());
            }
        });

    }
}
