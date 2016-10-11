package toe.com.toedailyandroid.Entity;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.prolificinteractive.materialcalendarview.spans.DotSpan;

/**
 * Created by HQu on 10/11/2016.
 */

public class TDADotSpan extends DotSpan {

    private float mRadius;
    private int mColor;

    public TDADotSpan(float radius, int color) {
        super(radius, color);
        mRadius = radius;
        mColor = color;
    }

    @Override
    public void drawBackground(Canvas canvas, Paint paint, int left, int right, int top, int baseline, int bottom, CharSequence charSequence, int start, int end, int lineNum) {
        super.drawBackground(canvas, paint, left, right, top, baseline, bottom, charSequence, start, end, lineNum);
        canvas.drawCircle((left + right) / 2, 200 + mRadius, mRadius, paint);
    }
}
