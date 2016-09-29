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
import com.google.gson.Gson;

import toe.com.toedailyandroid.Activity.HomeActivity;
import toe.com.toedailyandroid.Activity.LoginActivity;
import toe.com.toedailyandroid.Activity.SignUpActivity;
import toe.com.toedailyandroid.Utils.UserAuthDataManager;

/**
 * Created by HQu on 9/28/2016.
 */

public class UserService {
    private static final String TAG = "ToeUserService:";
    private Context mContext;
    private LoginListener mLoginListener;
    private TokenAuthListener mTokenAuthListener;
    private SignUpListener mSignUpListener;
    private SignOutListener mSignOutListener;

    public interface LoginListener {
        public void loginSucceed(AuthData authData);
        public void loginFail(String errorMsg);
    }

    public interface TokenAuthListener {
        public void tokenAuthSucceed();
        public void tokenAuthFail(String errorMsg);
    }

    public interface SignUpListener {
        public void signUpSucceed();
        public void signUpFail(String errorMsg);
    }

    public interface SignOutListener {
        public void signOutSucceed();
        public void signOutFail(String errorMsg);
    }

    public UserService(Context context, Object userListener, String action) {
        mContext = context;
        if(action.equalsIgnoreCase("login"))
            mLoginListener = (LoginListener)userListener;
        else if(action.equalsIgnoreCase("signUp"))
            mSignUpListener = (SignUpListener)userListener;
        else if(action.equalsIgnoreCase("signOut"))
            mSignOutListener = (SignOutListener)userListener;
        else if(action.equalsIgnoreCase("tokenAuth"))
            mTokenAuthListener = (TokenAuthListener)userListener;
    }

    public void login(String email, String pwd) {
        Firebase ref = new Firebase("https://toedailyandroid.firebaseio.com");
        ref.authWithPassword(email, pwd, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                mLoginListener.loginSucceed(authData);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                mLoginListener.loginFail(firebaseError.getMessage().toString());
            }
        });
    }

    public void tokenAuth() {
        UserAuthDataManager authDataManager = new UserAuthDataManager(mContext);
        AuthData authData = authDataManager.getUserAuthData();
        if(authData != null) {
            Firebase ref = new Firebase("https://toedailyandroid.firebaseio.com");
            ref.authWithCustomToken(authData.getToken(), new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {
                    mTokenAuthListener.tokenAuthSucceed();
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    mTokenAuthListener.tokenAuthFail(firebaseError.getMessage().toString());
                }
            });
        }
    }

    public void signUp(String email, String pwd) {
        Firebase ref = new Firebase("https://toedailyandroid.firebaseio.com");
        ref.createUser(email, pwd, new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                mSignUpListener.signUpSucceed();
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                mSignUpListener.signUpFail(firebaseError.getMessage().toString());
            }
        });
    }

    public void signOut() {
        Firebase ref = new Firebase("https://toedailyandroid.firebaseio.com");
        ref.unauth();
        mSignOutListener.signOutSucceed();
    }
}
