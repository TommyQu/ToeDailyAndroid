package toe.com.toedailyandroid.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firebase.client.Firebase;

import toe.com.toedailyandroid.R;
import toe.com.toedailyandroid.Service.UserService;

/**
 * Created by HQu on 9/27/2016.
 */

public class ProfileFragment extends Fragment {

    private Button mSignOutBtn;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile_fragment,container,false);
        mSignOutBtn = (Button)view.findViewById(R.id.sign_out_btn);

        mSignOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserService userService = new UserService(getActivity());
                userService.signOut();
            }
        });
        return view;
    }

}
