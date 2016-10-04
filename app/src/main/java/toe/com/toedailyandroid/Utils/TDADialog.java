package toe.com.toedailyandroid.Utils;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

/**
 * Created by HQu on 9/30/2016.
 */

public class TDADialog {

    private MaterialDialog mDialog;

    public void showProgessDialog(Context context, String title) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(context)
                .title(title)
                .content("Please wait")
                .positiveText("Cancel");
        mDialog = builder.build();
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }
}
