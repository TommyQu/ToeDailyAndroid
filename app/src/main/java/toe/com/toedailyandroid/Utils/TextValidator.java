package toe.com.toedailyandroid.Utils;

import android.text.TextWatcher;
import android.widget.TextView;

/**
 * Created by HQu on 9/26/2016.
 */

public abstract class TextValidator implements TextWatcher {

    private final TextView textView;

    public TextValidator(TextView textView) {
        this.textView = textView;
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Don't care */ }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) { /* Don't care */ }
}
