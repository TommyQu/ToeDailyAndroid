package toe.com.toedailyandroid.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import toe.com.toedailyandroid.Entity.MoodsSpinnerItem;
import toe.com.toedailyandroid.Entity.MoodsSpinnerViewHolder;
import toe.com.toedailyandroid.Entity.MoodsViewHolder;
import toe.com.toedailyandroid.R;

/**
 * Created by HQu on 9/29/2016.
 */

public class MoodsSpinnerAdapter extends BaseAdapter implements SpinnerAdapter{

    private static final String TAG = "ToeMoodsSpinnerAdapter:";
    private Context mContext;
    private List<MoodsSpinnerItem> mMoodSpinnerItems;

    public MoodsSpinnerAdapter(Context context, List<MoodsSpinnerItem> moodsSpinnerItems) {
        mContext = context;
        mMoodSpinnerItems = moodsSpinnerItems;
    }


    @Override
    public int getCount() {
        return mMoodSpinnerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mMoodSpinnerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return moodsSpinnerView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return moodsSpinnerView(position, convertView, parent);
    }

    public View moodsSpinnerView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE );
        View view=inflater.inflate(R.layout.mood_spinner_item, parent, false);
        ImageView imageView = (ImageView)view.findViewById(R.id.mood_type_img);
        imageView.setImageResource(mMoodSpinnerItems.get(position).mImg);
        TextView textView = (TextView)view.findViewById(R.id.mood_type_text);
        textView.setText(mMoodSpinnerItems.get(position).mContent);
        return view;
    }



}