package toe.com.toedailyandroid.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.firebase.client.AuthData;
import com.google.gson.Gson;

/**
 * Created by HQu on 9/28/2016.
 */

public class UserAuthDataManager {

    private Context mContext;

    public UserAuthDataManager(Context context) {
        this.mContext = context;
    }

    public AuthData getUserAuthData() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        String userCookie = prefs.getString("userCookie", null);
        if(userCookie != null) {
            Gson gson = new Gson();
            AuthData authData = gson.fromJson(userCookie, AuthData.class);
            return  authData;
        }
        return null;
    }
}
