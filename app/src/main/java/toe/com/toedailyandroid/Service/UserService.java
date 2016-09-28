package toe.com.toedailyandroid.Service;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import toe.com.toedailyandroid.Activity.HomeActivity;
import toe.com.toedailyandroid.Activity.LoginActivity;
import toe.com.toedailyandroid.Activity.SignUpActivity;

/**
 * Created by HQu on 9/28/2016.
 */

public class UserService {
    private static final String TAG = "ToeUserService:";
    private Context mContext;

    public UserService(Context context) {
        mContext = context;
    }

    public void login(String email, String pwd) {
        Firebase ref = new Firebase("https://toedailyandroid.firebaseio.com");
        ref.authWithPassword(email, pwd, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("token", authData.getToken());
                editor.putString("uid", authData.getUid());
                editor.commit();
                Intent intent = new Intent(mContext, HomeActivity.class);
                ((Activity) mContext).finish();
                mContext.startActivity(intent);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                Toast.makeText(mContext, firebaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void tokenAuth() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        String token = prefs.getString("token", null);
        if(token != null) {
            Firebase ref = new Firebase("https://toedailyandroid.firebaseio.com");
            ref.authWithCustomToken(token, new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    ((Activity) mContext).finish();
                    mContext.startActivity(intent);
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    Toast.makeText(mContext, firebaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void signUp(String email, String pwd) {
        Firebase ref = new Firebase("https://toedailyandroid.firebaseio.com");
        ref.createUser(email, pwd, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                Toast.makeText(mContext, "Sign up successfully!", Toast.LENGTH_SHORT).show();
                ((Activity) mContext).finish();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                Toast.makeText(mContext, firebaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void signOut() {
        Firebase ref = new Firebase("https://toedailyandroid.firebaseio.com");
        ref.unauth();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();
        Intent intent = new Intent(mContext, LoginActivity.class);
        ((Activity) mContext).finish();
        mContext.startActivity(intent);
        Toast.makeText(mContext, "Sign out successfully", Toast.LENGTH_SHORT).show();
    }
}
