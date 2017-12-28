package sdk.dive.tv.ui.views;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;

public class TriangleCanvas extends ColorDrawable {

    private Path mPath = new Path();
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public TriangleCanvas(int color) {
        mPaint.setColor(color);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        mPath.reset();
        mPath.moveTo(canvas.getWidth(), canvas.getHeight());
        mPath.lineTo(canvas.getWidth(), canvas.getHeight());
        mPath.lineTo(0, canvas.getHeight());
        mPath.lineTo(canvas.getWidth(), 0);
        canvas.drawPath(mPath, mPaint);
    }

}
