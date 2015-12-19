package nightcrysis.project_walk;

import android.app.Instrumentation;
import android.graphics.Point;
import android.os.SystemClock;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import nightcrysis.project_walk.Backend.data.Country;
import nightcrysis.project_walk.Backend.data.CountrySet;
import nightcrysis.project_walk.Frontend.DateSeekBar;

public class TestMapsActivity extends ActivityInstrumentationTestCase2<MapsActivity> {

    private MapsActivity mActivity;
    private Instrumentation mInstrumentation;
    private String selectedCountryName = "";
    private boolean listenerCalled = false;

    public TestMapsActivity() {
        super(MapsActivity.class);

    }

    /**
     * Testing if the mActivty is being created!!
     */
    public void testActivityExists() {
        mActivity = getActivity();
        assertNotNull(getActivity());
        assertNotNull(mActivity.getSupportFragmentManager());
        mInstrumentation = getInstrumentation();
    }

    public void testClickOnCountry() {
        mActivity = getActivity();

        //get the support map fragment
        final SupportMapFragment map = (SupportMapFragment) mActivity.getSupportFragmentManager().findFragmentById(R.id.map);
        //Get the google map
        final GoogleMap googleMap = map.getMap();
        //Get the infographics panel
        final Infographic infographicsPanel = (Infographic) mActivity.getFragmentManager().findFragmentById(R.id.fragment_right_panel);
        //Get the country set
        final CountrySet countrySet = mActivity.getCountrySet();
        //Create a boolean object that checks if the listener has been called
        listenerCalled = false;
        //Set the country name string to be empty
        selectedCountryName = "";
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                //Check if google map is null
                assertNotNull(googleMap);
                //Check if the country set is empty
                assertFalse(countrySet.getList().size() == 0);
                //Then set a new customized onClick listener for the purposes of testing.
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        listenerCalled = true;
                        ArrayList<Country> countries = countrySet.getList();
                        for (Country x : countries) {
                            if (x.isPolygonTouched(latLng)) {
                                selectedCountryName = x.getName();
                                return;
                            }
                        }
                    }
                });
                //For clicking a map
                Projection projection = googleMap.getProjection();
                LatLng clickedLocation = new LatLng(3.8667, 11.5167); // this points to the Cameroon
                Point screenPosition = projection.toScreenLocation(clickedLocation);
                Log.e("Coordinates", screenPosition.toString());
                long downTime = SystemClock.uptimeMillis();
                long eventTime = SystemClock.uptimeMillis();
                MotionEvent motionEvent = MotionEvent.obtain(
                        downTime,
                        eventTime,
                        MotionEvent.ACTION_DOWN,
                        screenPosition.x,
                        screenPosition.y,
                        0
                );
                //Dispatch the location that has been pressed to the activity, in which in turns calls the GoogleMap listener (hopefully)
                mActivity.dispatchTouchEvent(motionEvent);
                downTime = SystemClock.uptimeMillis();
                eventTime = SystemClock.uptimeMillis();
                motionEvent = MotionEvent.obtain(
                        downTime,
                        eventTime,
                        MotionEvent.ACTION_UP,
                        screenPosition.x,
                        screenPosition.y,
                        0
                );
                mActivity.dispatchTouchEvent(motionEvent);
                //Wait for a while until the detection thread has ended and has called the listener,
                //then also having it to find a country
                try {
                    wait(800);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Check if the listener has indeed been called:
                assertTrue(listenerCalled);
                //Check if United Kingdom has been pressed and correctly identified
                assertEquals("Cameroon", selectedCountryName);
                //Set the country name string to be empty
                selectedCountryName = "";
                //End of test
            }
        });
    }

    public void testSelectDataFromSpinner() {
        mActivity = getActivity();
        final Spinner mapDataSpinner = (Spinner) mActivity.findViewById(R.id.spinner_map_data);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                //Clicks the spinner
                mapDataSpinner.performItemClick(mapDataSpinner, 0, mapDataSpinner.getItemIdAtPosition(0));

                //Get the current selected item
                assertEquals("Literacy rate, males (15-24)", mapDataSpinner.getSelectedItem().toString());
            }
        });
    }

    public void testSeekBar() {
        mActivity = getActivity();
        final DateSeekBar dateSeekBar = (DateSeekBar) mActivity.findViewById(R.id.seek_bar_date);
        final Infographic info = (Infographic) mActivity.getFragmentManager().findFragmentById(R.id.fragment_right_panel);
        final TextView parliament = (TextView) info.getView().findViewById(R.id.parliament_label);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                dateSeekBar.setValue(1981);
                assertTrue(parliament.getText().toString().contains("1981"));
            }
        });
    }
}