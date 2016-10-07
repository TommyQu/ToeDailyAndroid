package toe.com.toedailyandroid.Service;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    private DeleteMoodListener mDeleteMoodListener;
    private Context mContext;

    public MoodService(Context context, Object moodListener, String action) {
        mContext = context;
        if(action.equalsIgnoreCase("getAllMoods"))
            mGetAllMoodsListener = (GetAllMoodsListener)moodListener;
        else if(action.equalsIgnoreCase("newMood"))
            mNewMoodListener = (NewMoodListener)moodListener;
        else if(action.equalsIgnoreCase("deleteMood"))
            mDeleteMoodListener = (DeleteMoodListener) moodListener;
    }

    public interface GetAllMoodsListener {
        public void getAllMoodsSucceed(List<Mood> moods);
        public void getAllMoodsFail(String errorMsg);
    }

    public interface NewMoodListener {
        public void newMoodSucceed();
        public void newMoodFail(String errorMsg);
    }

    public interface DeleteMoodListener {
        public void deleteMoodSucceed();
        public void deleteMoodFail(String errorMsg);
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
                moods.clear();
                for (DataSnapshot moodSnapshot: dataSnapshot.getChildren()) {
                    Mood mood = moodSnapshot.getValue(Mood.class);
                    mood.setId(moodSnapshot.getKey());
                    moods.add(mood);
                }
//                Sort moods list in descend order by created at
                Collections.sort(moods, new Comparator<Mood>(){
                    public int compare(Mood m1, Mood m2) {
                        return m2.getCreatedAt().compareToIgnoreCase(m1.getCreatedAt());
                    }
                });
                mGetAllMoodsListener.getAllMoodsSucceed(moods);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                mGetAllMoodsListener.getAllMoodsFail(firebaseError.getMessage().toString());
            }
        });
    }

    public void deleteMood(String id) {
        String url ="https://toedailyandroid.firebaseio.com/mood/"+id;
        Firebase ref = new Firebase(url);
        ref.removeValue(new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if(firebaseError == null)
                    mDeleteMoodListener.deleteMoodSucceed();
                else
                    mDeleteMoodListener.deleteMoodFail(firebaseError.getMessage().toString());
            }
        });
    }
}
