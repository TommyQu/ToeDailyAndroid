package toe.com.toedailyandroid.Utils;

import android.graphics.drawable.Drawable;

import toe.com.toedailyandroid.R;

/**
 * Created by HQu on 9/30/2016.
 */

public class MoodTextImgConverter {

    public int convertToImg(String moodType) {
        switch (moodType) {
            case "Happy":
                return R.drawable.happy;
            case "Wink":
                return R.drawable.wink;
            case "Love":
                return R.drawable.love;
            case "Joy":
                return R.drawable.joy;
            case "Shy":
                return R.drawable.shy;
            case "Sad":
                return R.drawable.sad;
            case "Angry":
                return R.drawable.angry;
            case "Dead":
                return R.drawable.dead;
            case "Embarrass":
                return R.drawable.embarrass;
            case "Cry":
                return R.drawable.cry;
            case "Sleepy":
                return R.drawable.sleepy;
            case "Surprise":
                return R.drawable.surprise;
            default:
                return -1;
        }
    }
}
