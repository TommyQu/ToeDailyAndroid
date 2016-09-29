package toe.com.toedailyandroid.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.util.List;

import toe.com.toedailyandroid.Entity.Mood;
import toe.com.toedailyandroid.R;
import toe.com.toedailyandroid.Service.MoodService;

/**
 * Created by HQu on 9/27/2016.
 */

public class MoodsFragment extends Fragment implements MoodService.GetAllMoodsListener{

    private static final String TAG = "ToeMoodsFragment:";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_moods, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Intent intent = new Intent(getContext(), NewMoodActivity.class);
                startActivity(intent);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_moods_fragment,container,false);
        MoodService moodService = new MoodService(getActivity(), this, "getAllMoods");
        moodService.getAllMoods();
        return view;
    }

    @Override
    public void getAllMoodsSucceed(List<Mood> moods) {
        Log.i(TAG, "aaaaaa");
    }

    @Override
    public void getAllMoodsFail(String errorMsg) {
        Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
    }
}
