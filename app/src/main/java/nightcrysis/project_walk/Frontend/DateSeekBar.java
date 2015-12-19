package nightcrysis.project_walk.Frontend;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

import nightcrysis.project_walk.MapsActivity;

/**
 * Created by NIghtCrysIs on 2015/12/05.
 */
public class DateSeekBar extends SeekBar{
    public int offset;

    public DateSeekBar(Context context) {
        super(context);
    }

    public DateSeekBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DateSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setValue(int value)
    {
        setProgress(0);
        setProgress(value - offset);
    }

    public void setValueRange(int min, int max)
    {
        offset = min;
        setMax(max - offset);
    }


    /**
     *
     * @return
     */
    public boolean incrementSeekBar() {
        final int progress = getProgress();
        Log.d("Seekbar", "Increment called. Proress " + (progress + offset));

        if (progress == getMax())
            return true;

        setProgress(progress + 1);

        MapsActivity.mapsActivity.runOnUiThread(new Thread()
        {
            @Override
            public void run() {
                MapsActivity.mapsActivity.changeMapCountryDataYear(progress + 1 + offset);
            }
        });

        return false;
    }
}
