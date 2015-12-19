/**
 * At class for representing the Data stored for he country, mainly used as a Data point in
 * CountrySet
 */
package nightcrysis.project_walk.Backend.data;

import android.graphics.Color;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.maps.android.PolyUtil;

import java.io.Serializable;
import java.util.ArrayList;

public class Country implements Serializable{

    private static final long serialVersionUID = 7526987412547776147L;

    private String name;

    private double currentValue = -1;
    private int currentYear = 2015;
    private Data currentData = Data.LITERACY_RATE_FEMALE;

    private ArrayList<Polygon> polygons;

    private ArrayList<DataPoint> literacyRateMale = new ArrayList<>();
    private ArrayList<DataPoint> literacyRateFemale = new ArrayList<>();
    private ArrayList<DataPoint> persistanceToGradeFiveMale = new ArrayList<>();
    private ArrayList<DataPoint> persistanceToGradeFiveFemale = new ArrayList<>();
    private ArrayList<DataPoint> enrolmentPrimary = new ArrayList<>();
    private ArrayList<DataPoint> enrolmentSecondary = new ArrayList<>();
    private ArrayList<DataPoint> enrolmentTertiary = new ArrayList<>();
    private ArrayList<DataPoint> femaleEmploymentAgriculture = new ArrayList<>();
    private ArrayList<DataPoint> nationalParliamentSeatsFemale = new ArrayList<>();
    private ArrayList<DataPoint> totalPopulation = new ArrayList<>();

    /**
     *
     * @param name The name of the country
     */
    public Country(String name){
        this.name = name;
    }

    /**
     * A method for inserting DataPoint objects into their respective fields of the country object,
     * uses insertion sort by date
     *
     * @param data The topic of data you are storing
     * @param dataPoint The datapoint object, containing the data itself and the year of the data,
     *                  that you want to store in the country
     */
    public void addDataPoint(Data data, DataPoint dataPoint){
        //Insertion sort by date
        int insertingYear = dataPoint.getYear();
        switch (data) {
            case LITERACY_RATE_MALE:
                for (int x = 0; x < literacyRateMale.size(); x++)
                    if (literacyRateMale.get(x).getYear() > insertingYear)
                    {
                        literacyRateMale.add(x, dataPoint);
                        return;
                    }
                literacyRateMale.add(dataPoint);
                return;
            case LITERACY_RATE_FEMALE:
                for (int x = 0; x < literacyRateFemale.size(); x++)
                    if (literacyRateFemale.get(x).getYear() > insertingYear)
                    {
                        literacyRateFemale.add(x, dataPoint);
                        return;
                    }
                literacyRateFemale.add(dataPoint);
                return;
            case PERSISTENCE_TO_GRADE_5_MALE:
                for (int x = 0; x < persistanceToGradeFiveMale.size(); x++)
                    if (persistanceToGradeFiveMale.get(x).getYear() > insertingYear)
                    {
                        persistanceToGradeFiveMale.add(x, dataPoint);
                        return;
                    }
                persistanceToGradeFiveMale.add(dataPoint);
                return;
            case PERSISTENCE_TO_GRADE_5_FEMALE:
                for (int x = 0; x < persistanceToGradeFiveFemale.size(); x++)
                    if (persistanceToGradeFiveFemale.get(x).getYear() > insertingYear)
                    {
                        persistanceToGradeFiveFemale.add(x, dataPoint);
                        return;
                    }
                persistanceToGradeFiveFemale.add(dataPoint);
                return;
            case SCHOOL_ENROLMENT_PRIMARY:
                for (int x = 0; x < enrolmentPrimary.size(); x++)
                    if (enrolmentPrimary.get(x).getYear() > insertingYear)
                    {
                        enrolmentPrimary.add(x, dataPoint);
                        return;
                    }
                enrolmentPrimary.add(dataPoint);
                return;
            case SCHOOL_ENROLMENT_SECONDARY:
                for (int x = 0; x < enrolmentSecondary.size(); x++)
                    if (enrolmentSecondary.get(x).getYear() > insertingYear)
                    {
                        enrolmentSecondary.add(x, dataPoint);
                        return;
                    }
                enrolmentSecondary.add(dataPoint);
                return;
            case SCHOOL_ENROLMENT_TERTIARY:
                for (int x = 0; x < enrolmentTertiary.size(); x++)
                    if (enrolmentTertiary.get(x).getYear() > insertingYear)
                    {
                        enrolmentTertiary.add(x, dataPoint);
                        return;
                    }
                enrolmentTertiary.add(dataPoint);
                return;
            case SHARE_OF_WOMEN_IN_WAGE_EMPLOYMENT_IN_NONAGRICULTURAL_SECTOR:
                for (int x = 0; x < femaleEmploymentAgriculture.size(); x++)
                    if (femaleEmploymentAgriculture.get(x).getYear() > insertingYear)
                    {
                        femaleEmploymentAgriculture.add(x, dataPoint);
                        return;
                    }
                femaleEmploymentAgriculture.add(dataPoint);
                return;
            case SEATS_HELD_BY_WOMEN_IN_NATIONAL_PARLIAMENT:
                for (int x = 0; x < nationalParliamentSeatsFemale.size(); x++)
                    if (nationalParliamentSeatsFemale.get(x).getYear() > insertingYear)
                    {
                        nationalParliamentSeatsFemale.add(x, dataPoint);
                        return;
                    }
                nationalParliamentSeatsFemale.add(dataPoint);
                return;
            case TOTAL_POPULATION:
                for (int x = 0; x < totalPopulation.size(); x++)
                    if (totalPopulation.get(x).getYear() > insertingYear)
                    {
                        totalPopulation.add(x, dataPoint);
                        return;
                    }
                totalPopulation.add(dataPoint);
        }
    }

    /**
     * A method for retrieving all data of a topic from a country
     *
     * @param data The topic of data you wish to retrieve all the data of the coutnry for
     * @return Returns the ArrayList of DataPoint objects the country contains for that topic of
     * data, otherwise null
     */
    public ArrayList<DataPoint> getDataPointList(Data data){
        switch (data){
            case LITERACY_RATE_MALE:
                return literacyRateMale;

            case LITERACY_RATE_FEMALE:
                return literacyRateFemale;

            case PERSISTENCE_TO_GRADE_5_MALE:
                return persistanceToGradeFiveMale;

            case PERSISTENCE_TO_GRADE_5_FEMALE:
                return persistanceToGradeFiveFemale;

            case SCHOOL_ENROLMENT_PRIMARY:
                return enrolmentPrimary;

            case SCHOOL_ENROLMENT_SECONDARY:
                return enrolmentSecondary;

            case SCHOOL_ENROLMENT_TERTIARY:
                return enrolmentTertiary;

            case SHARE_OF_WOMEN_IN_WAGE_EMPLOYMENT_IN_NONAGRICULTURAL_SECTOR:
                return femaleEmploymentAgriculture;

            case SEATS_HELD_BY_WOMEN_IN_NATIONAL_PARLIAMENT:
                return nationalParliamentSeatsFemale;

            case TOTAL_POPULATION:
                return totalPopulation;

            default:
                return null;
        }
    }

    /**
     * A method for getting the name of the country the country object represents
     *
     * @return The name of the country
     */
    public String getName(){
        return this.name;
    }

    /**
     * A method mainly used for renaming countries such that they are the exact same name as the
     * countries in the google maps api
     *
     * @param name The name you wish to rename the country too
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     *
     * @return Returns the name of the country only
     */
    public String toString(){
        return name;
    }

    /**
     * A method for getting a specific DataPoint object from a topic of DataPoints
     *
     * @param year The year of the data you want
     * @param data The topic of data you want
     * @return Returns the DataPoint object of that year from the topic of data chosen, if none
     * exists it will return null
     */
    public DataPoint getDataPointByYear(int year, Data data){
        switch (data){
            case LITERACY_RATE_MALE:
                for(DataPoint d: literacyRateMale){
                    if(d.getYear() == year){
                        return d;
                    }
                }
                break;
            case LITERACY_RATE_FEMALE:
                for(DataPoint d: literacyRateFemale){
                    if(d.getYear() == year){
                        return d;
                    }
                }
                break;
            case PERSISTENCE_TO_GRADE_5_MALE:
                for(DataPoint d: persistanceToGradeFiveMale){
                    if(d.getYear() == year){
                        return d;
                    }
                }
                break;
            case PERSISTENCE_TO_GRADE_5_FEMALE:
                for(DataPoint d: persistanceToGradeFiveFemale){
                    if(d.getYear() == year){
                        return d;
                    }
                }
                break;
            case SCHOOL_ENROLMENT_PRIMARY:
                for(DataPoint d: enrolmentPrimary){
                    if(d.getYear() == year){
                        return d;
                    }
                }
                break;
            case SCHOOL_ENROLMENT_SECONDARY:
                for(DataPoint d: enrolmentSecondary){
                    if(d.getYear() == year){
                        return d;
                    }
                }
                break;
            case SCHOOL_ENROLMENT_TERTIARY:
                for(DataPoint d: enrolmentTertiary){
                    if(d.getYear() == year){
                        return d;
                    }
                }
                break;
            case SHARE_OF_WOMEN_IN_WAGE_EMPLOYMENT_IN_NONAGRICULTURAL_SECTOR:
                for(DataPoint d: femaleEmploymentAgriculture){
                    if(d.getYear() == year){
                        return d;
                    }
                }
                break;
            case SEATS_HELD_BY_WOMEN_IN_NATIONAL_PARLIAMENT:
                for(DataPoint d: nationalParliamentSeatsFemale){
                    if(d.getYear() == year){
                        return d;
                    }
                }
                break;
            case TOTAL_POPULATION:
                for (DataPoint d : totalPopulation) {
                    if (d.getYear() == year) {
                        return d;
                    }
                }
                break;
        }

        return null;
    }

    /**
     * Method for finding the latest value provided by the World Bank given a specified year.
     * If there is a DataPoint object in the specified year for a certain data type, return that object.
     * Else if there is a DataPoint object  in any year before the specified year for a certain data type, return that object.
     * Else returns null.
     * @param year        The specified year
     * @param dataType    The specified data type
     * @return DataPoint object if one of such object exists in any of the year equal to or before the specified year.
     * @see Data
     */
    public DataPoint getLatestValueByYear(int year, Data dataType)
    {
        //Searches backwards from date specified
        switch (dataType){
            case LITERACY_RATE_MALE:
                for(int x = literacyRateMale.size() - 1; x >= 0; x--)
                    if(literacyRateMale.get(x) != null && literacyRateMale.get(x).getYear() <= year)
                        return literacyRateMale.get(x);
                return null;
            case LITERACY_RATE_FEMALE:
                for(int x = literacyRateFemale.size() - 1; x >= 0; x--)
                    if(literacyRateFemale.get(x) != null && literacyRateFemale.get(x).getYear() <= year)
                        return literacyRateFemale.get(x);
                return null;
            case PERSISTENCE_TO_GRADE_5_MALE:
                for(int x = persistanceToGradeFiveMale.size() - 1; x >= 0; x--)
                    if(persistanceToGradeFiveMale.get(x) != null && persistanceToGradeFiveMale.get(x).getYear() <= year)
                        return persistanceToGradeFiveMale.get(x);
                return null;
            case PERSISTENCE_TO_GRADE_5_FEMALE:
                for(int x = persistanceToGradeFiveFemale.size() - 1; x >= 0; x--)
                    if(persistanceToGradeFiveFemale.get(x) != null && persistanceToGradeFiveFemale.get(x).getYear() <= year)
                        return persistanceToGradeFiveFemale.get(x);
                return null;
            case SCHOOL_ENROLMENT_PRIMARY:
                for(int x = enrolmentPrimary.size() - 1; x >= 0; x--)
                    if(enrolmentPrimary.get(x) != null && enrolmentPrimary.get(x).getYear() <= year)
                        return enrolmentPrimary.get(x);
                return null;
            case SCHOOL_ENROLMENT_SECONDARY:
                for(int x = enrolmentSecondary.size() - 1; x >= 0; x--)
                    if(enrolmentSecondary.get(x) != null && enrolmentSecondary.get(x).getYear() <= year)
                        return enrolmentSecondary.get(x);
                return null;
            case SCHOOL_ENROLMENT_TERTIARY:
                for(int x = enrolmentTertiary.size() - 1; x >= 0; x--)
                    if(enrolmentTertiary.get(x) != null && enrolmentTertiary.get(x).getYear() <= year)
                        return enrolmentTertiary.get(x);
                return null;
            case SHARE_OF_WOMEN_IN_WAGE_EMPLOYMENT_IN_NONAGRICULTURAL_SECTOR:
                for(int x = femaleEmploymentAgriculture.size() - 1; x >= 0; x--)
                    if(femaleEmploymentAgriculture.get(x) != null && femaleEmploymentAgriculture.get(x).getYear() <= year)
                        return femaleEmploymentAgriculture.get(x);
                return null;
            case SEATS_HELD_BY_WOMEN_IN_NATIONAL_PARLIAMENT:
                for(int x = nationalParliamentSeatsFemale.size() - 1; x >= 0; x--)
                    if(nationalParliamentSeatsFemale.get(x) != null && nationalParliamentSeatsFemale.get(x).getYear() <= year)
                        return nationalParliamentSeatsFemale.get(x);
                return null;
            case TOTAL_POPULATION:
                for (int x = totalPopulation.size() - 1; x >= 0; x--)
                    if (totalPopulation.get(x) != null && totalPopulation.get(x).getYear() <= year)
                        return totalPopulation.get(x);
                return null;
        }
        return null;
    }

    public void setPolygonArray(ArrayList<Polygon> _polygons)
    {
        polygons = _polygons;
    }

    /**
     * Sets the polygon colour for a country by passing the data type and the year as a parameter
     * to find the DataPoint object (if existent). This is only applicable to data with value
     * between 0% and 100%. 100% values will change the polygon colour to green, 50% to yellow
     * 0% to red, and if there is no DataPoint in existent in the year equal or less than the specified year
     * then the polygons become grey.
     * @param dataType The topic of data
     * @param year The year of the data
     */
    public void setPolygonColour(Data dataType, int year)
    {
        if (polygons == null) return;

//        if (name.equals("Russia"))
//        {
//            Log.d("Russia dataset test", "Listing all data for Russia: " + dataType.toString());
//            ArrayList<DataPoint> set = getDataPointList(dataType);
//            for (DataPoint x : set)
//                Log.d("Russia dataset test", x.getYear() + "");
//            Log.d("Russia dataset test", "End");
//        }

        DataPoint currentDataPoint;
        if (currentData == dataType && currentYear < year && currentValue >= 0)
        {
            currentDataPoint = getDataPointByYear(year, dataType);
            if (currentDataPoint == null)
            {
                currentDataPoint = new DataPoint(currentYear, currentValue);
            }
            else
            {
                currentValue = currentDataPoint.getData();
                currentYear = currentDataPoint.getYear();
            }
        }
        else
        {
            currentData = dataType;
            currentValue = -1;
            currentYear = year;
            currentDataPoint = getLatestValueByYear(year, currentData);
            if (currentDataPoint == null)
            {
                for (Polygon x : polygons)
                    x.setFillColor(Color.GRAY);

//                if (name.equals("Russia"))
//                    Log.d("Russia dataset test", "No data found for Russia, " + dataType.toString());

                return;
            }
            else
            {
                currentValue = currentDataPoint.getData();
                currentYear = currentDataPoint.getYear();
            }
        }

        float ratio = (float) currentDataPoint.getData() / 100f;
        int polygonColour = getColor(ratio);

        for (Polygon x : polygons)
            x.setFillColor(polygonColour);

//        if (name.equals("Russia"))
//            Log.d("Russia dataset test", dataType.toString() + ", year " + year + " " + ratio + " current year " + currentYear);
    }

    private static int getColor(float ratio)
    {
        float[] temp = {(ratio * 120f), 1f, 1f};
        return Color.HSVToColor(temp);
        //polygonColour = Color.argb(255, (int)(255 - (255 * ratio * 2)), (int)(255 * ratio * 2), 0);

        /*if (ratio <= 0.5)
            polygonColour = Color.argb(255, 255, (int)(255 * ratio * 2), 0);
        else
            polygonColour = Color.argb(255, (int)(255 - (255 * ratio * 2)), 255, 0);*/
    }

    /**
     * Tests if the user's input coordinates is within the country's polygons. The collision detection
     * uses PolyUtil from the google api. Returns true if user input coordinates is within the polygon,
     * false otherwise.
     * @param userPointCoordinates    The user input coordinates in LatLng on the google map
     * @return true if point is within the country's polygon, false otherwise.
     */
    public boolean isPolygonTouched(LatLng userPointCoordinates)
    {
        if (polygons != null)
            for (Polygon x : polygons)
                if (PolyUtil.containsLocation(userPointCoordinates, x.getPoints(), false))
                    return true;
        return false;
    }
}
