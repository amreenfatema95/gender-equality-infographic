package nightcrysis.project_walk;

import android.app.Activity;
import android.graphics.Color;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import nightcrysis.project_walk.Backend.data.Country;

/**
 * Created by NIghtCrysIs on 2015/12/05.
 */
public class LoadCountryPolygonThread extends Thread{
    private GoogleMap mMap;
    private Activity parentActivity;

    public LoadCountryPolygonThread(GoogleMap _map, Activity _parentActivity)
    {
        mMap = _map;
        parentActivity = _parentActivity;
    }

    @Override
    public void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(parentActivity.getResources().openRawResource(R.raw.country_coords)));
        ArrayList<Country> countries = MapsActivity.mapsActivity.getCountrySet().getList();
        try
        {
            String line, countryName;
            while ((line = reader.readLine()) != null)
            {
                if (line.length() == 0) break;

                countryName = line;
                ArrayList<Polygon> polygons = new ArrayList<>();
                while (true)
                {
                    line = reader.readLine();

                    if (line.equals("End")) break;

                    String[] coordinates = line.split(" ");
                    ArrayList<LatLng> polygonCoords = new ArrayList<>();
                    for (String x : coordinates)
                    {
                        String[] pair = x.split(",");
                        polygonCoords.add(new LatLng(Double.parseDouble(pair[0]), Double.parseDouble(pair[1])));
                    }

                    Polygon tempPolygon = mMap.addPolygon(new PolygonOptions()
                            .addAll(polygonCoords)
                            .strokeColor(Color.argb(96, 0, 0, 0))
                            .fillColor(Color.GREEN));
                    tempPolygon.setStrokeWidth(3);
                    polygons.add(tempPolygon);

                }

                boolean countryFound = false;

                for (Country x : countries)
                    if (x.getName().equals(countryName))
                    {
                        x.setPolygonArray(polygons);
                        countryFound = true;
                        break;
                    }

                if (!countryFound)
                    Log.d("Polygon to Country", "Country " + countryName + " not found. Polygon not linked.");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
