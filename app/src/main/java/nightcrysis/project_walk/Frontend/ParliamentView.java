package nightcrysis.project_walk.Frontend;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import nightcrysis.project_walk.R;

/**
 * Created by NIghtCrysIs on 2015/12/09.
 */
public class ParliamentView extends View{
    private float width = 200, height = 132, widthToHeightRatio = 200f / 132f;
    private static Bitmap parliament;
    private static Paint paint;
    private int femaleColour = Color.WHITE;
    private float percentage = 0;

    public ParliamentView(Context context) {
        super(context);
        initialize();
    }

    public ParliamentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public ParliamentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize()
    {
        if (parliament == null)
            parliament = BitmapFactory.decodeResource(getResources(), R.drawable.parliament_white);
        if (paint == null)
            paint = new Paint();
    }


    public void setSizeByWidth(float _width)
    {
        width = _width;
        height = width / widthToHeightRatio;
    }

    public void setSizeByHeight(float _height)
    {
        height = _height;
        width = height * widthToHeightRatio;
    }

    public void setColor(int _femaleColor) {
        femaleColour = _femaleColor;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = (int) width * 10;
        int desiredHeight = (int) height;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Width calculation
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(desiredWidth, widthSize);
        } else {
            width = desiredWidth;
        }

        //Height calculation
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(desiredHeight, heightSize);
        } else {
            height = desiredHeight;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setColorFilter(new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY));
        canvas.drawBitmap(parliament, new Rect(0, 0, parliament.getWidth(), parliament.getHeight()),
                new RectF(0, 0, width, height), paint);
        paint.setColorFilter(new PorterDuffColorFilter(femaleColour, PorterDuff.Mode.MULTIPLY));
        canvas.drawBitmap(parliament, new Rect(0, (int)(height * (1 - percentage)), parliament.getWidth(), parliament.getHeight()),
                new RectF(0, (int)(height * (1 - percentage)), width, height), paint);
    }
}
