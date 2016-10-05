package toe.com.toedailyandroid.Utils;

import toe.com.toedailyandroid.R;

/**
 * Created by HQu on 10/5/2016.
 */

public class WeahterTextImgConverter {

    public static int convertToImg(String conditions) {
        switch (conditions) {
            case "partlycloudy":
                return R.drawable.partly_cloudy;
            case "clear":
                return R.drawable.sunny_gif;
            default:
                return R.drawable.sunny;
        }
    }
}
