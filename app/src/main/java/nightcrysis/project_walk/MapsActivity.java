package nightcrysis.project_walk;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
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
import nightcrysis.project_walk.Frontend.DateSeekBar;
import nightcrysis.project_walk.Frontend.UIUtilities;

/**
 * Created by NIghtCrysIs on 2015/12/03
 */
public class MapsActivity extends FragmentActivity {

    public static MapsActivity mapsActivity;

    private String[] dataArray;
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private TextView dateDisplay;
    private Spinner mapDataSpinner;
    private DateSeekBar dateSeekBar;
    private Button playButton;
    private Infographic infographicsPanel;
//    private ProgressBar loadingChart;
    private ProgressDialog progressDialog;

    private BitmapDrawable playImage, pauseImage;

    private Country world;

    private Data currentShownData = Data.LITERACY_RATE_FEMALE;
    private int currentYear = 2015;

    private volatile CountrySet countrySet;
    private volatile boolean playingSeekBar = false;

    private Object threadLocker = new Object();
    private SeekBarPlayer seekBarPlayer = new SeekBarPlayer();
    private Country currentCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mapsActivity = this;
        setUpMapIfNeeded();

        Display display = getWindowManager().getDefaultDisplay();
        int tempTextSize = (int) UIUtilities.findTextSizeByHeight("1", display.getHeight() / 96);
        TextView legendTextView = (TextView) findViewById(R.id.legend_textview0);
        legendTextView.setTextSize(tempTextSize);
        legendTextView = (TextView) findViewById(R.id.legend_textview1);
        legendTextView.setTextSize(tempTextSize);
        legendTextView = (TextView) findViewById(R.id.legend_textview2);
        legendTextView.setTextSize(tempTextSize);
        legendTextView = (TextView) findViewById(R.id.legend_textview3);
        legendTextView.setTextSize(tempTextSize);
        legendTextView = (TextView) findViewById(R.id.legend_textview4);
        legendTextView.setTextSize(tempTextSize);
        GridLayout legendContainer = (GridLayout) legendTextView.getParent();
        int padding = display.getHeight() / 256;
        legendContainer.setPadding(padding, padding, padding, padding);

        dateDisplay = (TextView) findViewById(R.id.text_view_date_display);
        dateDisplay.setTextColor(Color.argb(128, 0, 0, 0));
        dateDisplay.setTextSize((int) UIUtilities.findTextSizeByHeight("1", display.getHeight() / 24));
        dateDisplay.setTypeface(null, Typeface.BOLD);
        mapDataSpinner = (Spinner) findViewById(R.id.spinner_map_data);
        dateSeekBar = (DateSeekBar) findViewById(R.id.seek_bar_date);
        //dateSeekBar.getThumb().set
        playButton = (Button) findViewById(R.id.button_play_button);
        infographicsPanel = (Infographic) getFragmentManager().findFragmentById(R.id.fragment_right_panel);

//        loadingChart =  (ProgressBar) findViewById(R.id.loading_bar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Downloading data from World Bank");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if (!FileHandler.doesFileExist(FileHandler.getPathToCountrySet(this))) {

            Log.d("Init", "File not detected, initialising file");
            if(isNetworkAvailable()) {
                initializeCountryData();
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("No Internet Access")
                        .setMessage("This app requires internet connection to run for the first time!\nPlease enable your internet connection and restart the app.")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        System.exit(0);
                    }
                });
                return;
            }
        } else {

            Log.d("Init", "File detected! Attempting to load...");

            countrySet = FileHandler.loadObjectFromFile(FileHandler.getPathToCountrySet(this));
            if(countrySet == null){
                Log.d("DEBUG", "FAILED LOADING");
                //TODO throw error? notify user? try again?
            } else {
                for(Country c: countrySet.getList()){
                    Log.d("Init", c.toString() + " loaded!");
                    for(DataPoint d: c.getDataPointList(Data.LITERACY_RATE_MALE)){
                        Log.d("DEBUG", d.getData() + " " + d.getYear());
                    }
                }
                runOnUiThread(new LoadCountryPolygonThread(mMap, MapsActivity.this));
                initializeMapComponents();
            }

        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                ArrayList<Country> countries = countrySet.getList();
                for (Country x : countries)
                {
                    if (x.isPolygonTouched(latLng))
                    {
                        infographicsPanel.changeCountryInfographic(x, Integer.parseInt(dateDisplay.getText().toString()));
                        currentCountry = x;
                        return;
                    }
                }

                infographicsPanel.changeCountryInfographic(world, Integer.parseInt(dateDisplay.getText().toString()));
            }
        });

        //Start the thread if it is not alive.
        if (!seekBarPlayer.isAlive())
            seekBarPlayer.start();

        //Initializes a new thread for initializing the polygons on google map
        //new LoadCountryPolygonThread(mMap, this).run();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        // If the reference is not null
        if (playButton != null)
        {
            //Creating play button images.
            playButton.setHeight(playButton.getWidth());
            if (playImage == null)
                playImage = new BitmapDrawable(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.button_play), playButton.getWidth(), playButton.getWidth(), false));
            if (pauseImage == null)
                pauseImage = new BitmapDrawable(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.button_pause), playButton.getWidth(), playButton.getWidth(), false));
            playButton.setBackground(playImage);
        }
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        //mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    private void initializeCountryData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    InitializeCountriesThread icThread =
                            new InitializeCountriesThread();
                    icThread.start();
                    icThread.join();
                    MapsActivity.this.countrySet = icThread.getSet();

                    Object lock = new Object();

                    ExecutorService executor = Executors.newFixedThreadPool(Data.values().length);

                    for(Data d: Data.values()){
                        executor.submit(new UrlDataThread(d, MapsActivity.this.countrySet, lock, progressDialog));
                    }

                    executor.shutdown();

                    executor.awaitTermination(50, TimeUnit.SECONDS);

                    //rename countries
                    renameCountriesInCountrySet();

                    for(Country c: MapsActivity.this.countrySet.getList()){
                        Log.d("Init", c.toString());
                        for(DataPoint d: c.getDataPointList(Data.LITERACY_RATE_MALE)){
                            Log.d("DEBUG", d.getData() + " " + d.getYear());
                        }
                    }

                    FileHandler.saveObjectToFile(FileHandler.getPathToCountrySet(MapsActivity.this), countrySet);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new LoadCountryPolygonThread(mMap, MapsActivity.this));
                runOnUiThread(new Thread()
                {
                    @Override
                    public void run() {
                        initializeMapComponents();
                    }
                });
            }
        }).start();
    }

    public void playSeekBar(View view)
    {
        if (playingSeekBar)
        {
            //TODO: Change the playingSeekBar value and change button image accordingly.
            playButton.setBackground(playImage);
            playingSeekBar = false;
        }
        else
        {
            //TODO: Change the playingSeekBar value and change button image accordingly.
            playButton.setBackground(pauseImage);
            playingSeekBar = true;
            seekBarPlayer.resumeThread();
        }
    }

    //TODO: Call this method whenever user selects different type of data from Spinner object
    public void changeMapCountryData(Data _data)
    {
        currentShownData = _data;
        updateMapCountryData();
    }

    //TODO: Might need to do this on a separate thread in ui to prevent things from freezing up
    public void changeMapCountryDataYear(int year)
    {
        currentYear = year;
        changeDateText(year);
        updateMapCountryData();
    }

    private void updateMapCountryData()
    {
        ArrayList<Country> countries = countrySet.getList();
        for (Country x : countries)
            x.setPolygonColour(currentShownData, currentYear);
    }

    private void changeDateText(final int year)
    {
        runOnUiThread(new Thread()
        {
            @Override
            public void run()
            {
                dateDisplay.setText(String.valueOf(year));
            }
        });
    }

    /**
     * Can only be called after countrySet is initialized
     */
    private void initializeMapComponents()
    {
        RelativeLayout frameLayoutToRemove = (RelativeLayout) findViewById(R.id.loading_bar_container);
        frameLayoutToRemove.setClickable(false);
//        frameLayoutToRemove.removeView(loadingChart);
        progressDialog.dismiss();

        //TODO: Set the max value to current year?
        dateSeekBar.setValueRange(1981, currentYear);
        dateSeekBar.setValue(currentYear);
        changeDateText(currentYear);

        dateSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MapsActivity.mapsActivity.changeMapCountryDataYear(progress + dateSeekBar.offset);
                infographicsPanel.changeCountryInfographic(currentCountry, Integer.parseInt(dateDisplay.getText().toString()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Stop the thread if running
                playingSeekBar = false;
                playButton.setBackground(playImage);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Do nothing
            }
        });

        //TODO: Set the play button image
        //playButton.setBackground();

        playingSeekBar = false;

        //TODO: If population is added, this needs to be changed
        dataArray = new String[6];
        dataArray[0] = Data.LITERACY_RATE_MALE.toString();
        dataArray[1] = Data.LITERACY_RATE_FEMALE.toString();
        dataArray[2] = Data.PERSISTENCE_TO_GRADE_5_MALE.toString();
        dataArray[3] = Data.PERSISTENCE_TO_GRADE_5_FEMALE.toString();
        dataArray[4] = Data.SHARE_OF_WOMEN_IN_WAGE_EMPLOYMENT_IN_NONAGRICULTURAL_SECTOR.toString();
        dataArray[5] = Data.SEATS_HELD_BY_WOMEN_IN_NATIONAL_PARLIAMENT.toString();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_textview, dataArray);
        mapDataSpinner.setAdapter(arrayAdapter);
        mapDataSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MapsActivity.mapsActivity.changeMapCountryData(Data.getEnumFromString(dataArray[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do nothing
            }
        });


        ArrayList<Country> countries = countrySet.getList();
        for (Country x : countries)
            if (x.getName().equals("World"))
            {
                world = x;
                currentCountry = world;
                break;
            }
        infographicsPanel.changeCountryInfographic(world, Integer.parseInt(dateDisplay.getText().toString()));
    }

    private void renameCountriesInCountrySet() {
        for (Country c : MapsActivity.this.countrySet.getList()) {
            if (c.getName().equals("Bosnia and Herzegovina")) {
                c.setName("Bosnia-Herzegovina");
            } else if (c.getName().equals("Brunei Darussalam")) {
                c.setName("Brunei");
            } else if (c.getName().equals("Bahamas, The")) {
                c.setName("Bahamas");
            } else if (c.getName().equals("Congo, Rep.")) {
                c.setName("Republic of the Congo");
            } else if (c.getName().equals("Congo, Dem. Rep.")) {
                c.setName("Democratic Republic of Congo");
            } else if (c.getName().equals("Cabo Verde")) {
                c.setName("Cape Verde");
            } else if (c.getName().equals("Egypt, Arab Rep.")) {
                c.setName("Egypt");
            } else if (c.getName().equals("Micronesia, Fed. Sts.")) {
                c.setName("Micronesia");
            } else if (c.getName().equals("Gambia, The")) {
                c.setName("Gambia");
            } else if (c.getName().equals("Hong Kong SAR, China")) {
                c.setName("Hong Kong");
            } else if (c.getName().equals("Iran, Islamic Rep.")) {
                c.setName("Iran");
            } else if (c.getName().equals("Kyrgyz Republic")) {
                c.setName("Kyrgyzstan");
            } else if (c.getName().equals("St. Kitts and Nevis")) {
                c.setName("Saint Kitts and Nevis");
            } else if (c.getName().equals("Korea, Dem. Rep.")) {
                c.setName("North Korea");
            } else if (c.getName().equals("Korea, Rep.")) {
                c.setName("South Korea");
            } else if (c.getName().equals("Lao PDR")) {
                c.setName("Lao People's Democratic Republic");
            } else if (c.getName().equals("St. Lucia")) {
                c.setName("Saint Lucia");
            } else if (c.getName().equals("St. Martin (French part)")) {
                c.setName("Saint Martin");
            } else if (c.getName().equals("Macedonia, FYR")) {
                c.setName("Macedonia");
            } else if (c.getName().equals("Russian Federation")) {
                c.setName("Russia");
            } else if (c.getName().equals("Slovak Republic")) {
                c.setName("Slovakia");
            } else if (c.getName().equals("Syrian Arab Republic")) {
                c.setName("Syria");
            } else if (c.getName().equals("Timor-Leste")) {
                c.setName("East Timor");
            } else if (c.getName().equals("Taiwan, China")) {
                c.setName("Taiwan");
            } else if (c.getName().equals("United States")) {
                c.setName("United States of America");
            } else if (c.getName().equals("St. Vincent and the Grenadines")) {
                c.setName("Saint Vincent and the Grenadines");
            } else if (c.getName().equals("Venezuela, RB")) {
                c.setName("Venezuela");
            } else if (c.getName().equals("Virgin Islands (U.S.)")) {
                c.setName("United States Virgin Islands");
            } else if (c.getName().equals("Yemen, Rep.")) {
                c.setName("Yemen");
            }
        }
    }

    public CountrySet getCountrySet() {
        return countrySet;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class SeekBarPlayer extends Thread
    {
        public SeekBarPlayer()
        {

        }

        @Override
        public void run() {
            synchronized (this)
            {
                try
                {
                    while (true)
                    {
                        if (playingSeekBar)
                        {
                            //If the dateSeekBar reached its maximum value, exit the loop.
                            if (dateSeekBar.incrementSeekBar())
                            {
                                MapsActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        playButton.setBackground(playImage);
                                    }
                                });
                                playingSeekBar = false;
                            }

                            //Waits for 750 milliseconds, 0.75 second
                            if (playingSeekBar)
                                wait(750);
                        }
                        else wait();
                    }
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }

        public void resumeThread()
        {
            synchronized (this)
            {
                notify();
            }
        }
    }
}
