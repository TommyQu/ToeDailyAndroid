package toe.com.toedailyandroid.Entity;

import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import toe.com.toedailyandroid.R;

/**
 * Created by HQu on 9/29/2016.
 */

public class MoodsViewHolder extends RecyclerView.ViewHolder {

    public ImageView mMoodImgTV;
    public TextView mMoodTypeTV;
    public TextView mMoodContentTV;
    public TextView mCreatedAtTV;
    public ImageView mDeleteIM;

    public MoodsViewHolder(View itemView) {
        super(itemView);
        mMoodImgTV = (ImageView)itemView.findViewById(R.id.mood_img);
        mMoodTypeTV = (TextView)itemView.findViewById(R.id.mood_type);
        mMoodContentTV = (TextView)itemView.findViewById(R.id.mood_content);
        mCreatedAtTV = (TextView)itemView.findViewById(R.id.created_at);
        mDeleteIM = (ImageView)itemView.findViewById(R.id.delete_im);
    }
}