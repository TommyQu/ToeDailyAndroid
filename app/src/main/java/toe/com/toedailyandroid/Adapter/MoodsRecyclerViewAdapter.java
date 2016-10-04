package toe.com.toedailyandroid.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import toe.com.toedailyandroid.Entity.Mood;
import toe.com.toedailyandroid.Entity.MoodsViewHolder;
import toe.com.toedailyandroid.R;
import toe.com.toedailyandroid.Utils.MoodTextImgConverter;

/**
 * Created by HQu on 9/29/2016.
 */

public class MoodsRecyclerViewAdapter extends RecyclerView.Adapter<MoodsViewHolder> {

    private Context mContext;
    private List<Mood> mMoods;


    public MoodsRecyclerViewAdapter(Context context, List<Mood> moods) {
        mContext = context;
        mMoods = moods;
    }

    @Override
    public MoodsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mood_item, parent, false);
        MoodsViewHolder moodsViewHolder = new MoodsViewHolder(view);
        return moodsViewHolder;
    }

    @Override
    public void onBindViewHolder(MoodsViewHolder holder, int position) {
        MoodTextImgConverter converter = new MoodTextImgConverter();
        holder.mMoodTypeTV.setText(mMoods.get(position).getMoodType());
        holder.mMoodContentTV.setText(mMoods.get(position).getMoodContent());
        holder.mCreatedAtTV.setText(mMoods.get(position).getCreatedAt());
        int drawableImg = converter.convertToImg(mMoods.get(position).getMoodType());
        holder.mMoodImgTV.setImageDrawable(mContext.getResources().getDrawable(drawableImg));
    }


    @Override
    public int getItemCount() {
        return mMoods.size();
    }
}
