package nightcrysis.project_walk.Frontend;

import android.graphics.Color;
import android.graphics.Point;
import android.view.Display;
import android.view.View;

import nightcrysis.project_walk.MapsActivity;
import com.github.mikephil.charting.charts.ScatterChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.ScatterData;
import com.github.mikephil.charting.data.ScatterDataSet;

import java.util.ArrayList;

/**
 * Created by afssurani on 01/12/2015.
 */
public class ScatterPlot
{
    ArrayList<Entry> yEntries = new ArrayList<>();
    ArrayList<String> xEntries = new ArrayList<>();
    ScatterDataSet dataSet;
    ScatterChart chart;
    ScatterData data;

    public ScatterPlot(ArrayList<Float> yAxis,ArrayList<String> xAxis)
    {
        //yaxis values converted to type Entry.
        for (int i = 0;i<yAxis.size();i++)
        {
            yEntries.add(new Entry(yAxis.get(i),i));
        }
        //values for xAxis.
        xEntries = xAxis;

        //scatterChart();


    }

    public View scatterChart()
    {
        // Initializing the dataSet of type ScatterDataSet & adding features
        dataSet = new ScatterDataSet(yEntries,"Ice Cream Sales ($)");

        // Using the Scatter chart class to create a blank chart
        chart = new ScatterChart(MapsActivity.mapsActivity);

        //Adding contents to the chart.
        data = new ScatterData(xEntries,dataSet);
        chart.setData(data);

        //Title for the scatter plot
        chart.setDescription("IceCream scales V/S Temperature");

        datasetFeatures();
        titlePosition();
        chartColour();

        return chart;
    }

    public void datasetFeatures()
    {
        dataSet.setColor(Color.parseColor("#8A0829"));//colour of the dataset
        dataSet.setScatterShape(ScatterChart.ScatterShape.CIRCLE);//shape of the dataset
        dataSet.setScatterShapeSize(6);//shape size
        dataSet.setHighLightColor(Color.parseColor("#00FF00"));
        dataSet.setHighlightLineWidth(2);
    }

    public void titlePosition()
    {
        //Positioning the title
        Display display = MapsActivity.mapsActivity.getWindowManager().getDefaultDisplay();
        Point displaySize = new Point();
        display.getSize(displaySize);
        int maxX = displaySize.x;
        int maxY = displaySize.y;
        chart.setDescriptionPosition(maxX / 2, 87 * maxY / 100);
    }

    public void chartColour()
    {
        //background colour of the chart
        chart.setBackgroundColor(Color.parseColor("#E6E6E6"));
        chart.setGridBackgroundColor(Color.parseColor("#D8D8D8"));
        //adding basic animation
        chart.animateXY(5000, 5000);

        //highlighting enabled
        data.setHighlightEnabled(true);

        chart.setPinchZoom(true);
    }

}
