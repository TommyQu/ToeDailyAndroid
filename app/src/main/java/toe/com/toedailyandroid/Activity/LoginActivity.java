package toe.com.toedailyandroid.Activity;

import android.app.Activity;
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

import com.afollestad.materialdialogs.MaterialDialog;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.gson.Gson;

import toe.com.toedailyandroid.R;
import toe.com.toedailyandroid.Service.UserService;
import toe.com.toedailyandroid.Utils.TDADialog;
import toe.com.toedailyandroid.Utils.TextValidator;


public class LoginActivity extends AppCompatActivity implements UserService.LoginListener, UserService.TokenAuthListener{

    private EditText mEmailEditText;
    private EditText mPwdEditText;
    private Button mLoginBtn;
    private Button mSignUpBtn;
    private TDADialog mTDADialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);

        mEmailEditText = (EditText)findViewById(R.id.email);
        mPwdEditText = (EditText)findViewById(R.id.pwd);
        mLoginBtn = (Button)findViewById(R.id.login_btn);
        mSignUpBtn = (Button)findViewById(R.id.sign_up_btn);
        mTDADialog = new TDADialog();

        mTDADialog.showProgessDialog(LoginActivity.this, "Logging in");
        UserService userService = new UserService(LoginActivity.this, LoginActivity.this, "tokenAuth");
        userService.tokenAuth();

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
                mTDADialog.showProgessDialog(LoginActivity.this, "Logging in");
                UserService userService = new UserService(LoginActivity.this, LoginActivity.this, "login");
                userService.login(email, pwd);
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

    @Override
    public void loginSucceed(AuthData authData) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String userCookie = gson.toJson(authData);
        editor.putString("userCookie", userCookie);
        editor.commit();
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        finish();
        startActivity(intent);
        mTDADialog.dismiss();
    }

    @Override
    public void loginFail(String errorMsg) {
        Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        mTDADialog.dismiss();
    }

    @Override
    public void tokenAuthSucceed() {
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        finish();
        startActivity(intent);
        mTDADialog.dismiss();
    }

    @Override
    public void tokenAuthFail(String errorMsg) {
        Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
        mTDADialog.dismiss();
    }

    @Override
    public void noToken(String msg) {
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
        mTDADialog.dismiss();
    }
}
