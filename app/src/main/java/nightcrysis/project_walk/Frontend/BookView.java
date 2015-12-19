package nightcrysis.project_walk.Frontend;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import nightcrysis.project_walk.R;

public class BookView extends View {

    private Bitmap book;
    private Bitmap whiteBook;
    private int percentage = 0;

    private static Paint paint;
    private Rect rect;

    private float width = 50, height = 50;

    private int color;

    public BookView(Context context) {
        super(context);
        init();
    }

    public BookView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    public void setSize(float _width, float _height)
    {
        width = _width;
        height = _height;
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

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Draws grey row
        paint.setColorFilter(null);
        for (int i = 0; i < 10; ++i) {
            canvas.drawBitmap(book, rect, new RectF(width * (i - 1), 0, width * i, height), paint);
        }

        //Draws percentage row
        int wholeBooks = (percentage / 10);
        double fragmentBook = percentage % 10;

        paint.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));

        for (int i = 0; i < wholeBooks; ++i) {
            canvas.drawBitmap(whiteBook, rect, new RectF(width * (i - 1), 0, width * i, height), paint);
        }

        if (fragmentBook != 0) {
            wholeBooks -= 1;
            fragmentBook = fragmentBook / 10;
            double leftAdjustment = width * fragmentBook;
            canvas.drawBitmap(whiteBook, new Rect(0, 0, (int) (whiteBook.getWidth() * fragmentBook), whiteBook.getHeight()),
                    new RectF(wholeBooks * width, 0, (float) ((wholeBooks * width) + leftAdjustment), height), paint);
        }

    }

    private void init() {
        if (paint == null)
            paint = new Paint();
        whiteBook = BitmapFactory.decodeResource(getResources(), R.drawable.whitebook);
        book = BitmapFactory.decodeResource(getResources(), R.drawable.book);

        rect = new Rect(0, 0, whiteBook.getWidth(), whiteBook.getHeight());
    }

    private int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}

