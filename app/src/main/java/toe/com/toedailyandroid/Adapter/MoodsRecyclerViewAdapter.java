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
        holder.mMoodTypeTV.setText(mMoods.get(position).getMoodType());
        holder.mMoodContentTV.setText(mMoods.get(position).getMoodContent());
        holder.mCreatedAtTV.setText(mMoods.get(position).getCreatedAt());
        switch (mMoods.get(position).getMoodType()) {
            case "Happy":
                holder.mMoodImgTV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.happy));
                break;
            case "Wink":
                holder.mMoodImgTV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.wink));
                break;
            case "Love":
                holder.mMoodImgTV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.love));
                break;
            case "Joy":
                holder.mMoodImgTV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.joy));
                break;
            case "Shy":
                holder.mMoodImgTV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shy));
                break;
            case "Sad":
                holder.mMoodImgTV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.sad));
                break;
            case "Angry":
                holder.mMoodImgTV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.angry));
                break;
            case "Dead":
                holder.mMoodImgTV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dead));
                break;
            case "Embarrass":
                holder.mMoodImgTV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.embarrass));
                break;
            case "Cry":
                holder.mMoodImgTV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cry));
                break;
            case "Sleepy":
                holder.mMoodImgTV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.sleepy));
                break;
            case "Surprise":
                holder.mMoodImgTV.setImageDrawable(mContext.getResources().getDrawable(R.drawable.surprise));
                break;
            default:
                break;
        }

    }


    @Override
    public int getItemCount() {
        return mMoods.size();
    }
}
