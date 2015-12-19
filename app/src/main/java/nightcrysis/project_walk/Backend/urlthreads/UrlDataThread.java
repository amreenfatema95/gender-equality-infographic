/**
 * A class that retrieves data for a given topic for all countries, and saves the data to the
 * country once retrieved
 */
package nightcrysis.project_walk.Backend.urlthreads;

import android.app.ProgressDialog;

import nightcrysis.project_walk.Backend.data.Country;
import nightcrysis.project_walk.Backend.data.CountrySet;
import nightcrysis.project_walk.Backend.data.Data;
import nightcrysis.project_walk.Backend.data.DataPoint;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class UrlDataThread extends UrlThread{

    private CountrySet set;
    final private Object lock;
    final private Data data;
    final private ProgressDialog progressDialog;

    /**
     *
     * @param data The topic of data you want the thread to obtain
     * @param set The countrySet object you wish to write the data too
     * @param lock The lock to prevent multiple threads accessing one object at the same time
     * @param progressDialog The progress dialog that the user sees when data is being retrieved
     */
    public UrlDataThread(Data data, CountrySet set, Object lock, ProgressDialog progressDialog) {
        this.lock = lock;
        this.set = set;
        this.data = data;
        this.progressDialog = progressDialog;

        switch (data){
            case LITERACY_RATE_MALE:
                url = "http://api.worldbank.org/countries/indicators/SE.ADT.1524.LT.MA.ZS?per_page=10000&date=1981:2015&format=json";
                break;
            case LITERACY_RATE_FEMALE:
                url = "http://api.worldbank.org/countries/indicators/SE.ADT.1524.LT.FE.ZS?per_page=10000&date=1981:2015&format=json";
                break;
            case PERSISTENCE_TO_GRADE_5_MALE:
                url = "http://api.worldbank.org/countries/indicators/SE.PRM.PRSL.MA.ZS?per_page=10000&date=1981:2015&format=json";
                break;
            case PERSISTENCE_TO_GRADE_5_FEMALE:
                url = "http://api.worldbank.org/countries/indicators/SE.PRM.PRSL.FE.ZS?per_page=10000&date=1981:2015&format=json";
                break;
            case SCHOOL_ENROLMENT_PRIMARY:
                url = "http://api.worldbank.org/countries/indicators/SE.ENR.PRIM.FM.ZS?per_page=10000&date=1981:2015&format=json";
                break;
            case SCHOOL_ENROLMENT_SECONDARY:
                url = "http://api.worldbank.org/countries/indicators/SE.ENR.SECO.FM.ZS?per_page=10000&date=1981:2015&format=json";
                break;
            case SCHOOL_ENROLMENT_TERTIARY:
                url = "http://api.worldbank.org/countries/indicators/SE.ENR.TERT.FM.ZS?per_page=10000&date=1981:2015&format=json";
                break;
            case SHARE_OF_WOMEN_IN_WAGE_EMPLOYMENT_IN_NONAGRICULTURAL_SECTOR:
                url = "http://api.worldbank.org/countries/indicators/SL.EMP.INSV.FE.ZS?per_page=10000&date=1981:2015&format=json";
                break;
            case SEATS_HELD_BY_WOMEN_IN_NATIONAL_PARLIAMENT:
                url = "http://api.worldbank.org/countries/indicators/SG.GEN.PARL.ZS?per_page=10000&date=1981:2015&format=json";
                break;
            case TOTAL_POPULATION:
                url = "http://api.worldbank.org/countries/indicators/SP.POP.TOTL?per_page=10000&date=1985:2015&format=json";
                break;
        }

    }

    @Override
    /**
     * This method iterates through the json array obtained by the object and stores the data in the
     *  relevant country
     */
    protected void processJson() {

        //loops through json array, if the country found in the json array exists in the CountrySet,
        //It will update its data
        for(JsonElement e: jsonArray){
            JsonObject jO = e.getAsJsonObject();

            JsonObject nestedCountryObject = jO.get("country").getAsJsonObject();
            String name = nestedCountryObject.get("value").getAsString();
            int year = jO.get("date").getAsString().equals("Most Recent Value") ?
                    2015 : jO.get("date").getAsInt();

            Country country = set.getCountryByName(name);
            if(country != null){
                //synchronized to prevent objects being accessed by two threads at the same time
                synchronized (lock) {
                    JsonElement jE = jO.get("value");
                    if(jE != null && !jE.isJsonNull() ){

                        //add data to correct field
                        DataPoint dataPoint = new DataPoint(year, jO.get("value").getAsDouble());
                        country.addDataPoint(data, dataPoint);
                    }
                }
            }
        }

        progressDialog.incrementProgressBy(100 / Data.values().length);
        if(progressDialog.getProgress() == 100){
            progressDialog.setTitle("Loading data...");
        }

    }
}
