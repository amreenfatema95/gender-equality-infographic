package nightcrysis.project_walk.Backend;

import android.graphics.Color;
import android.view.View;

import nightcrysis.project_walk.Backend.data.Country;
import nightcrysis.project_walk.Frontend.ScatterPlot;
import nightcrysis.project_walk.MapsActivity;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.Random;

/** TEMPORARY CLASS
 * Created by NIghtCrysIs on 2015/11/24.
 * Temporary class with static methods to simulate button press, or for the use of creating
 * and adding GUI components.
 * TEMPORARY CLASS
 */
public class TestController {




    //Adding a degree of randomness for completeness
    public static Random random = new Random();

    /**
     * This method is used to initializing things on screen. This is called by MainActivity
     * for testing purposes (in the actual application, this will be replaced by methods triggered
     * by buttons in the Backend.
     *
     * Note that the changing of the main panel and the side panel is accessed via the singleton
     * static variable in the class MainActivity, with methods called changeMainPanel and
     * changeSidePanel respectively
     */
    public static void initialize()
    {
        //Changes the main panel to the graph created
//        MainActivity.mapsActivity.addToMainPanel(createSampleScatterChart());
//        MainActivity.mapsActivity.addToMainPanel(createSampleBarChart());
    }

    /** The following methods are for the purpose of both quick
     * testing and as reference for frontend development in the future */


    //BarGraph Dummy Data
    public static View createSampleBarChart()
    {
        //Creating a sample graph

        ArrayList<BarEntry> engEntries = new ArrayList<>();
        engEntries.add(new BarEntry(8f, 0));
        engEntries.add(new BarEntry(10f, 1));
        engEntries.add(new BarEntry(12f, 2));
        engEntries.add(new BarEntry(25f, 3));
        engEntries.add(new BarEntry(17f, 4));

        ArrayList<BarEntry> frnEntries = new ArrayList<>();
        frnEntries.add(new BarEntry(15f, 0));
        frnEntries.add(new BarEntry(17f, 1));
        frnEntries.add(new BarEntry(20f, 2));
        frnEntries.add(new BarEntry(22f, 3));
        frnEntries.add(new BarEntry(8f, 4));

        ArrayList<BarEntry> germEntries = new ArrayList<>();
        germEntries.add(new BarEntry(20f, 0));
        germEntries.add(new BarEntry(27f, 1));
        germEntries.add(new BarEntry(31f, 2));
        germEntries.add(new BarEntry(42f, 3));
        germEntries.add(new BarEntry(45f, 4));



        BarDataSet engDataSet = new BarDataSet(engEntries, "United Kingdom");
        engDataSet.setColor(Color.BLUE);

        BarDataSet frnDataSet = new BarDataSet(frnEntries, "France");
        frnDataSet.setColor(Color.YELLOW);

        BarDataSet germDataSet = new BarDataSet(germEntries, "Germany");
        germDataSet.setColor(Color.DKGRAY);

        //3 Creating an ArrayList of strings to represent the labels on the x axis
        ArrayList<String> labelsYR = new ArrayList<>();
        labelsYR.add("1960-1970");
        labelsYR.add("1970-1980");
        labelsYR.add("1980-1990");
        labelsYR.add("1990-2000");
        labelsYR.add("2000-2010");

        //Create BarData to describe each bar on graph using the String ArrayList created, along
        //with the BarDataSet created
        BarData barData = new BarData(labelsYR, engDataSet);
        barData.addDataSet(frnDataSet);
        barData.addDataSet(germDataSet);

        //Create ScatterChart object with a reference to the current activity
        BarChart barChart = new BarChart(MapsActivity.mapsActivity);
        barChart.setData(barData);

        //Setting the description of the chart
        barChart.setDescription("Forest area (% of land area) ");

        //Adding animation to the Y axis
        barChart.animateY(5000, Easing.EasingOption.EaseInOutExpo);

        //Disabling double tap zoom as currently you cant zoom out
        barChart.setDoubleTapToZoomEnabled(false);

        //Setting the grid background colour
        barChart.setGridBackgroundColor(Color.LTGRAY);

        //Allowing user's touch to be enabled
        barChart.setTouchEnabled(true);
        //Allowing the chart to be dragged
        barChart.setDragEnabled(true);

        Legend bLegend = barChart.getLegend();
        bLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);

        //Allowing users to pinch zoom
        barChart.setPinchZoom(true);



        return barChart;
    }

    private static View createSampleLineChart()
    {
        return new View(MapsActivity.mapsActivity);
    }


    public static View createSampleScatterChart()
    {
        /*DUMMY DATA FOR SCATTER CHART*/

        //list of values on the yaxis stored as string.
        ArrayList<Float> yDummy = new ArrayList<>();
        yDummy.add(215f);
        yDummy.add(325f);
        yDummy.add(185f);
        yDummy.add(332f);
        yDummy.add(406f);
        yDummy.add(522f);
        yDummy.add(412f);
        yDummy.add(614f);
        yDummy.add(544f);
        yDummy.add(412f);
        yDummy.add(614f);
        yDummy.add(544f);
        yDummy.add(421f);
        yDummy.add(445f);
        yDummy.add(408f);

        //list of values on the x-axis
        ArrayList<String> xDummy = new ArrayList<>();
        xDummy.add("14.2");
        xDummy.add("16.4");
        xDummy.add("11.9");
        xDummy.add("15.2");
        xDummy.add("18.5");
        xDummy.add("22.1");
        xDummy.add("19.4");
        xDummy.add("25.1");
        xDummy.add("23.4");
        xDummy.add("18.1");
        xDummy.add("22.6");
        xDummy.add("17.2");


        //calling the scatterPlot class.
        ScatterPlot scatterPlot = new ScatterPlot(yDummy,xDummy);

        return scatterPlot.scatterChart();
    }


    private static View createSampleCandleChart()
    {
        return new View(MapsActivity.mapsActivity);
    }


    private static View createSampleBubbleChart()
    {
        return new View(MapsActivity.mapsActivity);
    }


    private static View createSamplePieChart()
    {
        return new View(MapsActivity.mapsActivity);
    }


    private static View createSampleRadarChart()
    {
        return new View(MapsActivity.mapsActivity);
    }
}
