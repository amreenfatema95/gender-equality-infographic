package nightcrysis.project_walk;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

import nightcrysis.project_walk.Backend.data.Country;
import nightcrysis.project_walk.Backend.data.Data;
import nightcrysis.project_walk.Backend.data.DataPoint;
import nightcrysis.project_walk.Frontend.BookView;
import nightcrysis.project_walk.Frontend.ParliamentView;
import nightcrysis.project_walk.Frontend.PersonView;
import nightcrysis.project_walk.Frontend.UIUtilities;

/**
 * Created by NIghtCrysIs on 09/12/2015.
 * Main class for the infographics display. A fragment that contains all the views to be displayed
 * on the right side of the screen. Many text sizes, colours and image sizes are handled programmatically
 * within this class
 */
public class Infographic extends Fragment
{
    //private static final int maleColour = Color.argb(255, 76, 186, 255), femaleColour = Color.argb(255, 236, 243, 30);
    private static final int maleColour = Color.argb(255, 54, 246, 222), femaleColour = Color.argb(255, 255, 66, 76);

            /** Declaring the parent view group */
    private LinearLayout parent;

    //Country name and population
    private TextView countryName;
    private TextView countryPopulation;

    //Employment
    private PieChart employmentPieChart;
    private TextView employmentTextView;

    //Literacy rate
    private BookView maleLiteracyRate, femaleLiteracyRate;
    private TextView maleLiteracyLabel, femaleLiteracyLabel, literacyRateTitle;

    //GPI
    private PersonView primaryGPI, secondaryGPI, tertiaryGPI;
    private TextView primaryGPILabel, secondaryGPILabel, tertiaryGPILabel,
            primaryGPIDesc, secondaryGPIDesc, tertiaryGPIDesc, gipTitle;


    //Persistence from Grade 1 to Grade 5
    private TextView persistenceLabel;

    //Percentage of seats held by women in parliament
    private ParliamentView parliamentView;
    private TextView parliamentLabel;

    /**
     * Creates the parent view group layout for everything to be instantiated in
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        parent = (LinearLayout) inflater.inflate(R.layout.infographic_layout, container, false);

        ViewTreeObserver observer = parent.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            boolean ranOnce = false;

            @Override
            public void onGlobalLayout() {
                if (!ranOnce) {
                    initializeSize();
                    ranOnce = true;
                }
            }
        });

        //Employment settings
        employmentPieChart = (PieChart) parent.findViewById(R.id.employment_piechart);
        employmentPieChart.setHoleRadius(80);
        employmentPieChart.setDrawHoleEnabled(true);
        employmentPieChart.setHoleColorTransparent(true);
        employmentPieChart.setDrawCenterText(true);
        employmentPieChart.setCenterTextColor(femaleColour);
        employmentPieChart.setDrawSliceText(false);
        employmentPieChart.setDescription("");
        employmentPieChart.setCenterTextSizePixels(UIUtilities.findTextSizeByWidth(" 100% ", parent.getWidth() / 8) * 0.75f);

        employmentTextView = (TextView) parent.findViewById(R.id.employment_label);

        countryName = (TextView)parent.findViewById(R.id.country_name);
        countryName.setTextColor(Color.rgb(248, 78, 78));

        countryPopulation = (TextView)parent.findViewById(R.id.country_population);
        countryPopulation.setTextSize(countryName.getTextSize() / 2f);
        countryPopulation.setTextColor(Color.rgb(248, 78, 78));

        maleLiteracyRate = (BookView) parent.findViewById(R.id.maleLiteracyRate);
        femaleLiteracyRate = (BookView) parent.findViewById(R.id.femaleLiteracyRate);
        maleLiteracyLabel = (TextView) parent.findViewById(R.id.maleLiteracyLabel);
        femaleLiteracyLabel = (TextView) parent.findViewById(R.id.femaleLiteracyLabel);
        literacyRateTitle = (TextView) parent.findViewById(R.id.literacyRate_title);

        gipTitle = (TextView) parent.findViewById(R.id.gip_title);

        primaryGPI = (PersonView) parent.findViewById(R.id.gip_primary_display);
        secondaryGPI = (PersonView) parent.findViewById(R.id.gip_secondary_display);
        tertiaryGPI = (PersonView) parent.findViewById(R.id.gip_tertiary_display);

        primaryGPILabel = (TextView) parent.findViewById(R.id.gip_primary_label);
        secondaryGPILabel = (TextView) parent.findViewById(R.id.gip_secondary_label);
        tertiaryGPILabel = (TextView) parent.findViewById(R.id.gip_tertiary_label);

        primaryGPIDesc  = (TextView) parent.findViewById(R.id.gip_primary_education);
        secondaryGPIDesc = (TextView) parent.findViewById(R.id.gip_secondary_education);
        tertiaryGPIDesc =  (TextView) parent.findViewById(R.id.gip_tertiary_education);

        persistenceLabel = (TextView) parent.findViewById(R.id.persistence_text_view);

        parliamentView = (ParliamentView) parent.findViewById(R.id.parliament_display);
        parliamentLabel = (TextView) parent.findViewById(R.id.parliament_label);

        return parent;
    }

    /**
     * Method for initializing the text sizes, view sizes and colour for all components
     * that are displayed in the infographic.
     */
    private void initializeSize()
    {
        float countryNameTextSize = UIUtilities.findTextSizeByWidth("Extremely-long", parent.getWidth() / 5);
        countryName.setTextSize(countryNameTextSize);
        countryName.setTextColor(femaleColour);

        float populationTextSize = countryNameTextSize * 0.65f;

        Log.e("ScreenSize", countryName.getTextSize() + " " + populationTextSize);

        countryPopulation.setTextSize(populationTextSize);
        countryPopulation.setTextColor(femaleColour);

        employmentTextView.setTextSize(UIUtilities.findTextSizeByWidth(" non-agricultural ", parent.getWidth() / 8.4f));
        employmentTextView.setTextColor(Color.WHITE);
        Log.e("ASD2", employmentTextView.getTextSize() + "");
        employmentTextView.setText(Html.fromHtml("of <font color=" + getHexColourString(femaleColour) + ">females</font>\nworks in the\nnon-agricultural\nsector"));

        literacyRateTitle.setTextSize(populationTextSize);

        gipTitle.setTextSize(populationTextSize);
        gipTitle.setText(Html.fromHtml("Per <font color=" + getHexColourString(femaleColour) + ">female</font> student there are:"));

        int bookSize = parent.getWidth() / 3 / 10 * 2;
        maleLiteracyRate.setSize(bookSize, bookSize);
        maleLiteracyRate.setColor(maleColour);
        femaleLiteracyRate.setSize(bookSize, bookSize);
        femaleLiteracyRate.setColor(femaleColour);
        float literacyTextSize = UIUtilities.findTextSizeByWidth(" Female: 100000% ", parent.getWidth() / 6);
        maleLiteracyLabel.setTextSize(literacyTextSize);
        maleLiteracyLabel.setTextColor(maleColour);
        femaleLiteracyLabel.setTextSize(literacyTextSize);
        femaleLiteracyLabel.setTextColor(femaleColour);

        float gipTextSize = literacyTextSize * 0.75f;
        primaryGPILabel.setTextSize(gipTextSize);
        secondaryGPILabel.setTextSize(gipTextSize);
        tertiaryGPILabel.setTextSize(gipTextSize);

        TextView educationTextView = (TextView) parent.findViewById(R.id.gip_primary_education);
        educationTextView.setTextSize(gipTextSize);
        educationTextView = (TextView) parent.findViewById(R.id.gip_secondary_education);
        educationTextView.setTextSize(gipTextSize);
        educationTextView = (TextView) parent.findViewById(R.id.gip_tertiary_education);
        educationTextView.setTextSize(gipTextSize);

        primaryGPI.setSizeByHeight(bookSize * 2f);
        primaryGPI.setMaleColour(maleColour);
        primaryGPI.setFemaleColour(femaleColour);
        secondaryGPI.setSizeByHeight(bookSize * 2f);
        secondaryGPI.setMaleColour(maleColour);
        secondaryGPI.setFemaleColour(femaleColour);
        tertiaryGPI.setSizeByHeight(bookSize * 2f);
        tertiaryGPI.setMaleColour(maleColour);
        tertiaryGPI.setFemaleColour(femaleColour);

        persistenceLabel.setTextSize(UIUtilities.findTextSizeByWidth(" 1000% of male children reached grade 5 from grade 1, ", parent.getWidth() / 2));

        parliamentLabel.setTextSize(UIUtilities.findTextSizeByWidth(" held by women in the ", parent.getWidth() / 6));
        parliamentView.setColor(femaleColour);
        parliamentView.setSizeByWidth(parent.getWidth() / 4);
        parliamentView.invalidate();

        int sidePaddings = parent.getWidth() / 108;
        parent.setPadding(sidePaddings, sidePaddings, sidePaddings, sidePaddings);
    }

    /**
     * Method for changing the infographic display and statistics programmtically, by passing
     * a reference for the country displayed on screen.
     * @param country    The Country object that contains all the relevant statistics to be displayed
     * @param year       The specified year of which to show the statistic by. If statistics are not
     *                   available for that year, it will search for statistics before the specified date
     *                   and display it. If none found, an empty message will be shown.
     */
    public void changeCountryInfographic(Country country, int year)
    {
        //Setting country name and population
        String[] countryNameWords = country.getName().split(" ");
        StringBuilder name = new StringBuilder();
        int characterCounter = 0;
        for (String x : countryNameWords)
        {
            if (x.length() > 12)
            {
                if (characterCounter == 0)
                {
                    name.append(x + "\n");
                    characterCounter = 0;
                }
                else
                {
                    name.append("\n" + x);
                    characterCounter += name.length();
                }
            }
            else if (characterCounter + x.length() > 12)
            {
                name.append("\n" + x);
                characterCounter = 0;
            }
            else
            {
                name.append(" " + x);
                characterCounter += name.length();
            }
        }
        countryName.setText(name.toString());
        DataPoint population = country.getLatestValueByYear(year, Data.TOTAL_POPULATION);

        int linesLeft = 3 - countryNameWords.length;
        String linesToAdd = "";
        while (linesLeft > 0)
        {
            linesToAdd += "<br>";
            linesLeft--;
        }

        String text = "<font color=#ffffff>Population: </font>" + (population == null ? "No data found" : String.valueOf((long)population.getData()).replaceAll("(\\d)(?=(\\d{3})+$)", "$1,"));
        countryPopulation.setText(Html.fromHtml(text + "<font size=" + countryName.getTextSize() + ">" + linesToAdd + "</font>"));

        //Setting literacy values
        changeLiteracyRate(maleLiteracyRate, maleLiteracyLabel, country, year, Data.LITERACY_RATE_MALE);
        changeLiteracyRate(femaleLiteracyRate, femaleLiteracyLabel, country, year, Data.LITERACY_RATE_FEMALE);

        // Setting employment values
        changeEmploymentRate(country, year);

        //Setting GPI
        changeGPI(country, year);

        //Setting persistence from grade 1 to grade 5
        changeEducationPersistence(country, year);

        //Setting parliament participation rate
        changeParliamentParticipationRate(country, year);
    }

    private String getHexColourString(int color)
    {
        return String.format("#%06X", (0xFFFFFF & color));
    }

    private void changeEmploymentRate(Country country, int year)
    {
        DataPoint dataPoint = country.getLatestValueByYear(year, Data.SHARE_OF_WOMEN_IN_WAGE_EMPLOYMENT_IN_NONAGRICULTURAL_SECTOR);
        if (dataPoint != null)
        {
            float dataValue = (float)dataPoint.getData() / 100f;
            ArrayList<Entry> entries = new ArrayList<>();
            entries.add(new Entry(dataValue, 0));
            entries.add(new Entry(1 - dataValue, 1));
            PieDataSet dataSet = new PieDataSet(entries, "");
            ArrayList<Integer> colors = new ArrayList<>();
            colors.add(femaleColour);
            colors.add(maleColour);
            dataSet.setColors(colors);
            String[] something = {"", ""};
            PieData data = new PieData(something, dataSet);
            employmentPieChart.setData(data);
            employmentPieChart.setCenterText(String.format("%.1f", dataValue * 100) + "%");
            employmentPieChart.setCenterTextSize(24); //TEMPORARY
            employmentPieChart.invalidate();

            employmentTextView.setText(Html.fromHtml("of <font color=\" + getHexColourString(femaleColour) + \">females</font>\nworks in the\nnon-agricultural\nsector (" + dataPoint.getYear() + " est.)"));
        }
        else
        {
            employmentPieChart.clear();
            employmentTextView.setText(Html.fromHtml("of <font color=\" + getHexColourString(femaleColour) + \">females</font>\nworks in the\nnon-agricultural\nsector (" + year + ")"));
        }
    }

    private void changeGPI(Country country, int year)
    {
        DataPoint tempDataPoint = country.getLatestValueByYear(year, Data.SCHOOL_ENROLMENT_PRIMARY);
        if (tempDataPoint != null)
        {
            primaryGPI.setNumberOfMale((float) tempDataPoint.getData());
            primaryGPIDesc.setText("Primary\neducation\n(" + tempDataPoint.getYear() + " est.)");
            primaryGPILabel.setText(Html.fromHtml(String.format("%.2f", (float) tempDataPoint.getData()) + " <font color=" + getHexColourString(maleColour) + ">male</font>\nstudent(s)"));
        }
        else
        {
            primaryGPI.setNumberOfMale(0);
            primaryGPIDesc.setText("Primary\neducation\n(" + year + ")");
            primaryGPILabel.setText(Html.fromHtml("N/A <font color=" + getHexColourString(maleColour) + ">male</font>\nstudent(s)"));
        }
        tempDataPoint = country.getLatestValueByYear(year, Data.SCHOOL_ENROLMENT_SECONDARY);
        if (tempDataPoint != null)
        {
            secondaryGPI.setNumberOfMale((float) tempDataPoint.getData());
            secondaryGPIDesc.setText("Secondary\neducation\n(" + tempDataPoint.getYear() + " est.)");
            secondaryGPILabel.setText(Html.fromHtml(String.format("%.2f", (float)tempDataPoint.getData()) + " <font color=" + getHexColourString(maleColour) + ">male</font>\nstudent(s)"));
        }
        else
        {
            secondaryGPI.setNumberOfMale(0);
            secondaryGPIDesc.setText("Secondary\neducation\n(" + year + ")");
            secondaryGPILabel.setText(Html.fromHtml("N/A <font color=" + getHexColourString(maleColour) + ">male</font>\nstudent(s)"));
        }
        tempDataPoint = country.getLatestValueByYear(year, Data.SCHOOL_ENROLMENT_TERTIARY);
        if (tempDataPoint != null)
        {
            tertiaryGPI.setNumberOfMale((float) tempDataPoint.getData());
            tertiaryGPIDesc.setText("Tertiary\neducation\n(" + tempDataPoint.getYear() + " est.)");
            tertiaryGPILabel.setText(Html.fromHtml(String.format("%.2f", (float)tempDataPoint.getData()) + " <font color=" + getHexColourString(maleColour) + ">male</font>\nstudent(s)"));
        }
        else
        {
            tertiaryGPI.setNumberOfMale(0);
            tertiaryGPIDesc.setText("Tertiary\neducation\n(" + year + ")");
            tertiaryGPILabel.setText(Html.fromHtml("N/A <font color=" + getHexColourString(maleColour) + ">male</font>\nstudent(s)"));
        }

        primaryGPI.invalidate();
        secondaryGPI.invalidate();
        tertiaryGPI.invalidate();
    }

    private void changeEducationPersistence(Country country, int year)
    {
        //Retrieving male statistics
        DataPoint tempDataPoint = country.getLatestValueByYear(year, Data.PERSISTENCE_TO_GRADE_5_MALE);
        String malePersistence = "", femalePersistence = "", persistenceYear = "";
        if (tempDataPoint != null)
        {
            malePersistence = String.format("%.1f", tempDataPoint.getData());
            persistenceYear = String.valueOf(tempDataPoint.getYear());
        }
        else
        {
            malePersistence = "N/A";
            persistenceYear = String.valueOf(year);
        }

        //Retrieving female statistics
        tempDataPoint = country.getLatestValueByYear(year, Data.PERSISTENCE_TO_GRADE_5_FEMALE);
        if (tempDataPoint != null)
            femalePersistence = String.format("%.1f", tempDataPoint.getData());
        else femalePersistence = "N/A";

        //Setting label string
        persistenceLabel.setText(Html.fromHtml("<font color=" + getHexColourString(maleColour) + ">" + malePersistence + "%</font> of <font color="
                + getHexColourString(maleColour) + ">male children</font> reached grade 5 from grade 1,\n" +
                "opposed to <font color=" + getHexColourString(femaleColour) + ">" + femalePersistence + "%</font> of <font color="
                + getHexColourString(femaleColour) + ">female children. </font>(" + persistenceYear + " est.)"));
    }

    private void changeLiteracyRate(BookView literacyView, TextView literacyLabel, Country country, int year, Data literacyGender)
    {
        DataPoint tempDataPoint = country.getLatestValueByYear(year, literacyGender);

        switch (literacyGender)
        {
            case LITERACY_RATE_MALE:
                if (tempDataPoint != null) {
                    literacyView.setPercentage((int) (tempDataPoint.getData()));
                    literacyLabel.setText("Male: " + (String.format("%.1f", tempDataPoint.getData())) + "%");
                    literacyRateTitle.setText("Literacy rate (" + tempDataPoint.getYear() + " est.)");
                }
                else
                {
                    literacyView.setPercentage(0);
                    literacyLabel.setText("Male: " + "N/A");
                    literacyRateTitle.setText("Literacy rate (" + year + ")");
                }
                break;
            case LITERACY_RATE_FEMALE:
                if (tempDataPoint != null) {
                    literacyView.setPercentage((int)(tempDataPoint.getData()));
                    literacyLabel.setText("Female: " + (String.format("%.1f", tempDataPoint.getData())) + "%");
                }
                else
                {
                    literacyView.setPercentage(0);
                    literacyLabel.setText("Female: " + "N/A");
                }
        }

        literacyView.postInvalidate();
    }

    private void changeParliamentParticipationRate(Country country, int year)
    {
        DataPoint tempDataPoint = country.getLatestValueByYear(year, Data.SEATS_HELD_BY_WOMEN_IN_NATIONAL_PARLIAMENT);
        if (tempDataPoint != null)
        {
            parliamentView.setPercentage((float) tempDataPoint.getData() / 100f);
            parliamentLabel.setText(Html.fromHtml("<font color=" + getHexColourString(femaleColour) + ">" + String.format("%.1f", tempDataPoint.getData())
                    + "%</font> of seats are\nheld by <font color=" + getHexColourString(femaleColour) + ">women</font> in the\nnational parliament\n("
                    + tempDataPoint.getYear() + " est.)"));
        }
        else
        {
            parliamentView.setPercentage(0);
            parliamentLabel.setText(Html.fromHtml("<font color=" + getHexColourString(femaleColour) + ">N/A"
                    + "</font> of seats are\nheld by <font color=" + getHexColourString(femaleColour) + ">women</font> in the\nnational parliament\n("
                    + year + ")"));
        }
        parliamentView.invalidate();
    }
}
