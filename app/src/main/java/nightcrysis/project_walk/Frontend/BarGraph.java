package nightcrysis.project_walk.Frontend;

import android.view.View;

import nightcrysis.project_walk.MapsActivity;
import nightcrysis.project_walk.R;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;

import java.util.ArrayList;

/**
 * Created by Senna on 30/11/2015.
 */
public class BarGraph {

    //Creating an ArrayList of strings for the x axis data- Timeline
    ArrayList<String> xAxisData;
    //Creating an ArrayList of BarDataSet for the y axis data- Actual Data
    ArrayList<BarDataSet> yAxisData;
    //Creating a Barchart object
    BarChart barChart;

    View barChartView;

    //Creating a constructor that takes in our string array  and bardataset array
    public BarGraph(ArrayList<String> xAxisData, ArrayList<BarDataSet> yAxisData){
        this.xAxisData = xAxisData;
        this.yAxisData = yAxisData;
        barChart = new BarChart(MapsActivity.mapsActivity);
        //Create BarChart object with a refernce to the current activity
        BarData barData = new BarData(xAxisData, yAxisData);
        //Setting our Barchart object data to the data object we created above
        barChart.setData(barData);
    }

    //Method to Display barchart and edit how it looks
    public View displaybarChart() {

        //Creating the BarChart object
        barChart = new BarChart(MapsActivity.mapsActivity);

        //Referring to the view id in xml so that we can insert our barchart there
//        barChartView = (View) MapsActivity.mapsActivity.findViewById(R.id.barGraph);

        //Initialising our BarChart variable by referencing it to our xml id
//        barChart = (BarChart) barChartView.findViewById(R.id.barGraph);

        //Setting the description of the chart
        barChart.setDescription("% of Forest Land availible in the past years...");
        //Setting animation on the charts x and y axis
        barChart.animateXY(5000, 7000);
        //Returning the chart
        return barChartView;

    }


}
