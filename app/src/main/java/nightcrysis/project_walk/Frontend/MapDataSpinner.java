package nightcrysis.project_walk.Frontend;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import nightcrysis.project_walk.Backend.data.Data;
import nightcrysis.project_walk.MapsActivity;

/**
 * Created by NIghtCrysIs on 2015/12/05.
 */
public class MapDataSpinner extends Spinner{

    private String[] dataArray;

    public MapDataSpinner(Context context) {
        super(context);
        initialize();
    }

    public MapDataSpinner(Context context, int mode) {
        super(context, mode);
        initialize();
    }

    public MapDataSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public MapDataSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize()
    {
        //TODO: If population is added, this needs to be changed
        dataArray = new String[8];
        dataArray[0] = Data.LITERACY_RATE_MALE.toString();
        dataArray[1] = Data.LITERACY_RATE_FEMALE.toString();
        dataArray[2] = Data.PERSISTENCE_TO_GRADE_5_MALE.toString();
        dataArray[3] = Data.PERSISTENCE_TO_GRADE_5_FEMALE.toString();
        //dataArray[4] = Data.PROGRESSION_TO_PRIMARY_SCHOOL_MALE.toString();
        //dataArray[5] = Data.PROGRESSION_TO_PRIMARY_SCHOOL_FEMALE.toString();
        dataArray[6] = Data.SHARE_OF_WOMEN_IN_WAGE_EMPLOYMENT_IN_NONAGRICULTURAL_SECTOR.toString();
        dataArray[7] = Data.SEATS_HELD_BY_WOMEN_IN_NATIONAL_PARLIAMENT.toString();

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, dataArray);
        setAdapter(arrayAdapter);
        setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                MapsActivity.mapsActivity.changeMapCountryData(Data.getEnumFromString(dataArray[position]));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do nothing
            }
        });
    }

    //TODO: Add percentage data values to this Spinner's selection
    //TODO: Implement listener when user selects a data set, then call public void changeMapCountryData(Data _data) in MapsActivity
}
