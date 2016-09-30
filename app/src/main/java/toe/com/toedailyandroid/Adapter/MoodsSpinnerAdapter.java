package toe.com.toedailyandroid.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import toe.com.toedailyandroid.R;

/**
 * Created by HQu on 9/29/2016.
 */

public class MoodsSpinnerAdapter extends BaseAdapter {

    private Context mContext;

    public MoodsSpinnerAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return 12;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.mood_spinner_item, null);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.mood_type_img);
            TextView textView = (TextView) convertView.findViewById(R.id.mood_type_text);
            switch (position) {
                case 0:
                    imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.happy));
                    textView.setText("Happy");
                    break;
                case 1:
                    imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.wink));
                    textView.setText("Wink");
                    break;
                case 2:
                    imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.love));
                    textView.setText("Love");
                    break;
                case 3:
                    imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.joy));
                    textView.setText("Joy");
                    break;
                case 4:
                    imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.shy));
                    textView.setText("Shy");
                    break;
                case 5:
                    imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.sad));
                    textView.setText("Sad");
                    break;
                case 6:
                    imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.angry));
                    textView.setText("Angry");
                    break;
                case 7:
                    imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.dead));
                    textView.setText("Dead");
                    break;
                case 8:
                    imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.embarrass));
                    textView.setText("Embarrass");
                    break;
                case 9:
                    imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.cry));
                    textView.setText("Cry");
                    break;
                case 10:
                    imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.sleepy));
                    textView.setText("Sleepy");
                    break;
                case 11:
                    imageView.setImageDrawable(mContext.getResources().getDrawable(R.drawable.surprise));
                    textView.setText("Surprise");
                    break;
                default:
                    break;

            }

        }
        return convertView;
    }
}
