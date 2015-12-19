/**
 * A class to initialise the country objects if no country Data is found on the phone
 */
package nightcrysis.project_walk.Backend.urlthreads;

import nightcrysis.project_walk.Backend.data.Country;
import nightcrysis.project_walk.Backend.data.CountryDeserializer;
import nightcrysis.project_walk.Backend.data.CountrySet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.util.ArrayList;

public class InitializeCountriesThread extends UrlThread{

    private CountrySet set = new CountrySet(null);

    private final String ignore = "Africa\n" +
            "Andean Region\n" +
            "Arab World\n" +
            "Sub-Saharan Africa (IFC classification)\n" +
            "East Asia and the Pacific (IFC classification)\n" +
            "Central Europe and the Baltics\n" +
            "Europe and Central Asia (IFC classification)\n" +
            "Channel Islands\n" +
            "Latin America and the Caribbean (IFC classification)\n" +
            "Middle East and North Africa (IFC classification)\n" +
            "South Asia (IFC classification)\n" +
            "Caribbean small states\n" +
            "East Asia & Pacific (developing only)\n" +
            "East Asia & Pacific (all income levels)\n" +
            "Europe & Central Asia (developing only)\n" +
            "Europe & Central Asia (all income levels)\n" +
            "Euro area\n" +
            "European Union\n" +
            "Fragile and conflict affected situations\n" +
            "High income\n" +
            "Not classified\n" +
            "Latin America & Caribbean (developing only)\n" +
            "Latin America & Caribbean (all income levels)\n" +
            "Latin America and the Caribbean\n" +
            "Low income\n" +
            "Lower middle income\n" +
            "Low & middle income\n" +
            "Central America\n" +
            "Middle East (developing only)\n" +
            "Middle East & North Africa (developing only)\n" +
            "North America\n" +
            "North Africa\n" +
            "High income: nonOECD\n" +
            "High income: OECD\n" +
            "OECD members\n" +
            "Other small states\n" +
            "Middle East & North Africa (all income levels)\n" +
            "Middle income\n" +
            "Pacific island small states\n" +
            "South Asia\n" +
            "Southern Cone\n" +
            "Sub-Saharan Africa (developing only)\n" +
            "Sub-Saharan Africa (all income levels)\n" +
            "Small states\n" +
            "Sub-Saharan Africa excluding South Africa\n" +
            "Upper middle income\n" +
            "Sub-Saharan Africa excluding South Africa and Nigeria";

    private final String[] ignoreList = ignore.split("\\r?\\n");

    public InitializeCountriesThread() {
        super("http://api.worldbank.org/countries?per_page=500&format=json");
    }

    @Override
    /**
     * Creates country objects from the list of countries from the json string, then creates a
     * CountrySet object using the HashSet
     */
    protected void processJson() {

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Country.class, new CountryDeserializer());
        Gson gson = gsonBuilder.create();

        ArrayList<Country> jsonObjList = new ArrayList<>();

        for(JsonElement e: jsonArray){
            boolean skip = false;
            Country country = gson.fromJson(e, Country.class);

            for(String s: ignoreList){
                if(s.equals(country.getName())){
                    skip = true;
                    break;
                }
            }
            if(skip){
                continue;
            }
            jsonObjList.add(country);
        }

        set.setList(jsonObjList);

    }

    /**
     *
     * @return The set initialised by the thread
     */
    public CountrySet getSet(){
        return set;
    }
}
