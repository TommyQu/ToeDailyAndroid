package toe.com.toedailyandroid.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.roger.catloadinglibrary.CatLoadingView;

import toe.com.toedailyandroid.R;
import toe.com.toedailyandroid.Service.UserService;
import toe.com.toedailyandroid.Utils.TextValidator;


/**
 * Created by HQu on 9/26/2016.
 */

public class SignUpActivity extends AppCompatActivity {

    private Button mSubmitBtn;
    private Button mCancelBtn;
    private EditText mEmailEditText;
    private EditText mPwdEditText;
    private EditText mRePwdEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Firebase.setAndroidContext(this);

        mSubmitBtn = (Button)findViewById(R.id.submit_btn);
        mCancelBtn = (Button)findViewById(R.id.cancel_btn);
        mEmailEditText = (EditText)findViewById(R.id.email);
        mPwdEditText = (EditText)findViewById(R.id.pwd);
        mRePwdEditText = (EditText)findViewById(R.id.re_pwd);

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
                else if(s.toString().equals(mRePwdEditText.getText().toString()) == false)
                    mRePwdEditText.setError("Password doesn't match!");
                else
                    mRePwdEditText.setError(null);
            }
        });

        mRePwdEditText.addTextChangedListener(new TextValidator(mRePwdEditText) {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().equals(mPwdEditText.getText().toString()) == false)
                    mRePwdEditText.setError("Password doesn't match!");
            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEditText.getText().toString();
                String pwd = mPwdEditText.getText().toString();
                String rePwd = mRePwdEditText.getText().toString();
                if(TextUtils.isEmpty(email))
                    mEmailEditText.setError("This field is required!");
                else if(TextUtils.isEmpty(pwd))
                    mPwdEditText.setError("This field is required!");
                else if(TextUtils.isEmpty(rePwd))
                    mRePwdEditText.setError("This field is required!");
                else if(TextUtils.equals(pwd, rePwd) == false)
                    mRePwdEditText.setError("Password doesn't match!");
                else {
                    UserService userService = new UserService(SignUpActivity.this);
                    userService.signUp(email, pwd);
                }

            }
        });
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
