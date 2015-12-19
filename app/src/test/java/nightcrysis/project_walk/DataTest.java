package nightcrysis.project_walk;

import org.junit.Test;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import nightcrysis.project_walk.Backend.FileHandler;
import nightcrysis.project_walk.Backend.data.Country;
import nightcrysis.project_walk.Backend.data.CountrySet;
import nightcrysis.project_walk.Backend.data.Data;
import nightcrysis.project_walk.Backend.data.DataPoint;
import nightcrysis.project_walk.Backend.urlthreads.InitializeCountriesThread;
import nightcrysis.project_walk.Backend.urlthreads.UrlDataThread;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class DataTest {

    @Test
    public void addingPopulationDataToCountry(){
        Country country = new Country("UK");
        DataPoint data = new DataPoint(1984, 420.1337);

        country.addDataPoint(Data.TOTAL_POPULATION, data);
        DataPoint output = country.getDataPointByYear(1984, Data.TOTAL_POPULATION);

        assertTrue("Data should equal 420.1337", output.getData() == 420.1337);
        assertTrue("Year should be 1984", output.getYear() == 1984);
    }

    @Test
    public void gettingLatestDataFromCountry(){
        Country country = new Country("UK");
        DataPoint data = new DataPoint(1984, 420.1337);
        DataPoint data2 = new DataPoint(1994, 1337.619);

        country.addDataPoint(Data.TOTAL_POPULATION, data);
        country.addDataPoint(Data.TOTAL_POPULATION, data2);

        DataPoint output = country.getLatestValueByYear(2015, Data.TOTAL_POPULATION);

        assertTrue("Should get 1994 data", output.getYear() == 1994);
    }

    @Test
    public void doesInitializeCountriesWork(){
        InitializeCountriesThread icThread = new InitializeCountriesThread();
        icThread.start();
        try {
            icThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        CountrySet set = icThread.getSet();

        assertTrue("Country set should not be of size 0", set.getList().size() > 0);
    }

    @Test
    public void doesSavingAndLoadingDataWork(){

        FileHandler.saveObjectToFile("/test.string", "TEST");

        String test = FileHandler.loadObjectFromFile(
                "/test.string");

        assertNotNull(test);

        assertTrue("String is \"TEST\"", test.equals("TEST"));
    }

}
