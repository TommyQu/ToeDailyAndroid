package toe.com.toedailyandroid.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import toe.com.toedailyandroid.R;
import toe.com.toedailyandroid.Utils.TextValidator;


public class LoginActivity extends AppCompatActivity {

    private EditText mEmailEditText;
    private EditText mPwdEditText;
    private Button mLoginBtn;
    private Button mSignUpBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);

        mEmailEditText = (EditText)findViewById(R.id.email);
        mPwdEditText = (EditText)findViewById(R.id.pwd);
        mLoginBtn = (Button)findViewById(R.id.login_btn);
        mSignUpBtn = (Button)findViewById(R.id.sign_up_btn);

        mEmailEditText.addTextChangedListener(new TextValidator(mEmailEditText) {
            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s.toString()))
                    mEmailEditText.setError("Please input email!");
            }
        });

        mPwdEditText.addTextChangedListener(new TextValidator(mPwdEditText) {
            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(s.toString()))
                    mPwdEditText.setError("Please input password!");
            }
        });

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEditText.getText().toString();
                String pwd = mPwdEditText.getText().toString();
                Firebase ref = new Firebase("https://toedailyandroid.firebaseio.com");
                ref.authWithPassword(email, pwd, new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("uid", authData.getUid());
                        editor.commit();
                        finish();
                        startActivity(intent);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        Toast.makeText(LoginActivity.this, firebaseError.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

}
