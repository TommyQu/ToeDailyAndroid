package toe.com.toedailyandroid.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import java.util.List;

import toe.com.toedailyandroid.Activity.LoginActivity;
import toe.com.toedailyandroid.Entity.Mood;
import toe.com.toedailyandroid.Entity.MoodsViewHolder;
import toe.com.toedailyandroid.R;
import toe.com.toedailyandroid.Service.MoodService;
import toe.com.toedailyandroid.Utils.MoodTextImgConverter;

/**
 * Created by HQu on 9/29/2016.
 */

public class MoodsRecyclerViewAdapter extends RecyclerView.Adapter<MoodsViewHolder> {

    private static final String TAG = "ToeMoodsViewAdapter:";
    private Context mContext;
    private List<Mood> mMoods;
    private MaterialDialog mDialog;
    private MaterialDialog.Builder mBuilder;
    private MoodsViewDeleteMoodListener mMoodsViewDeleteMoodListener;

    public interface MoodsViewDeleteMoodListener {
        public void onConfirmDeleteMood(String id);
    }

    public MoodsRecyclerViewAdapter(Context context, Object listener, List<Mood> moods) {
        mContext = context;
        mMoods = moods;
        mMoodsViewDeleteMoodListener = (MoodsViewDeleteMoodListener)listener;
        mBuilder = new MaterialDialog.Builder(mContext)
                .title("Confirm Dialog")
                .content("Are you sure to delete this mood?")
                .positiveText("Confirm")
                .negativeText("Cancel");
    }

    @Override
    public MoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mood_item, parent, false);
        MoodsViewHolder moodsViewHolder = new MoodsViewHolder(view);
        return moodsViewHolder;
    }

    @Override
    public void onBindViewHolder(MoodsViewHolder holder, final int position) {
        MoodTextImgConverter converter = new MoodTextImgConverter();
        holder.mMoodTypeTV.setText(mMoods.get(position).getMoodType());
        holder.mMoodContentTV.setText(mMoods.get(position).getMoodContent());
        holder.mCreatedAtTV.setText(mMoods.get(position).getCreatedAt());
        int drawableImg = converter.convertToImg(mMoods.get(position).getMoodType());
        holder.mMoodImgTV.setImageDrawable(mContext.getResources().getDrawable(drawableImg));
        holder.mDeleteIM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBuilder.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mMoodsViewDeleteMoodListener.onConfirmDeleteMood(mMoods.get(position).getId());
                    }
                });
                mDialog = mBuilder.build();
                mDialog.show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mMoods.size();
    }


}
