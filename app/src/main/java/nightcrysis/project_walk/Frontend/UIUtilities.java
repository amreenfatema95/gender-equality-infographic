package nightcrysis.project_walk.Frontend;

import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by NIghtCrysIs on 2015/10/27.
 * Utilities class that contains some helpful methods that helps you with writing UI codes
 */
public class UIUtilities {

    /**
     * Passing a certain width value, this method returns the size of text of which it is the size of the width specified
     * @param text     Text to calculate text size by
     * @param width    The given width to find the text size
     * @return text size value in accordance to width
     */
    public static float findTextSizeByWidth(String text, float width)
    {
        Paint paint = new Paint();
        Rect bounds = new Rect();
        float toReturn = 0.5f;

        paint.setTextSize(toReturn);
        paint.getTextBounds(text, 0, text.length(), bounds);

        while (bounds.width() < width)
        {
            toReturn += 0.5f;
            paint.setTextSize(toReturn);
            paint.getTextBounds(text, 0, text.length(), bounds);
        }
        return toReturn;
    }

    /**
     * Passing a certain height value, this method returns the size of text of which it is the size of the width specified
     * @param text     Text to calculate text size by
     * @param height    The given width to find the text size
     * @return text size value in accordance to height
     */
    public static float findTextSizeByHeight(String text, float height)
    {
        Paint paint = new Paint();
        Rect bounds = new Rect();
        float toReturn = 0.5f;

        paint.setTextSize(toReturn);
        paint.getTextBounds(text, 0, text.length(), bounds);

        while (bounds.height() < height)
        {
            toReturn += 0.5f;
            paint.setTextSize(toReturn);
            paint.getTextBounds(text, 0, text.length(), bounds);
        }
        return toReturn;
    }
}
