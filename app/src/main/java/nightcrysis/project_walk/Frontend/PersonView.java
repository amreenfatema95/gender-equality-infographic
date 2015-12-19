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

public class PersonView extends View{

    private static Paint paint = new Paint();
    private static Bitmap maleImage, femaleImage;

    private Rect maleRect, femaleRect;
    private RectF rectf;

    private Gender gender;
    private int maleColour = Color.GREEN, femaleColour = Color.RED;
    private float numberOfMale = 1;

    private float width = 50, height = 116, widthToHeightRatio = 2.32f;

    public PersonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setSizeByWidth(float _width)
    {
        width = _width;
        height = width * widthToHeightRatio;
    }

    public void setSizeByHeight(float _height)
    {
        height = _height;
        width = height / widthToHeightRatio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desiredWidth = (int) (width * (1 + numberOfMale));
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

    public void setNumberOfMale(float genderParityValue)
    {
        numberOfMale = genderParityValue;
    }

    public void setMaleColour(int maleColour) {
        this.maleColour = maleColour;
    }

    public void setFemaleColour(int femaleColour) {
        this.femaleColour = femaleColour;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int num = (int)Math.floor(numberOfMale);
//        paint.setStyle(Paint.Style.FILL);
//        paint.setColor(Color.BLACK);
//        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);

        paint.setColorFilter(new PorterDuffColorFilter(femaleColour, PorterDuff.Mode.MULTIPLY));
        canvas.drawBitmap(femaleImage, femaleRect, new RectF(0, 0, width, height), paint);
        paint.setColorFilter(new PorterDuffColorFilter(maleColour, PorterDuff.Mode.MULTIPLY));
        for(int i = 1; i < num + 1; ++i) {
            canvas.drawBitmap(maleImage, maleRect, new RectF(i * width, 0, (i + 1) * width, height), paint);
        }
        //Drawing the remaining partial image
        canvas.drawBitmap(maleImage, new Rect(0, 0, (int)(maleImage.getWidth() * (numberOfMale - num)), maleImage.getHeight()),
                new RectF((num + 1) * width, 0, (num + 1) * width + (width * (numberOfMale - num)), height), paint);
    }

    private void init() {
        if (paint == null)
        {
            paint = new Paint();
        }
        if (maleImage == null)
        {
            maleImage = BitmapFactory.decodeResource(getResources(), R.drawable.male);
            maleRect = new Rect(0, 0, maleImage.getWidth(), maleImage.getHeight());
        }
        if (femaleImage == null)
        {
            femaleImage = BitmapFactory.decodeResource(getResources(), R.drawable.female);
            femaleRect = new Rect(0, 0, femaleImage.getWidth(), femaleImage.getHeight());
        }
    }

    public enum Gender{
        MALE, FEMALE;
    }
}

