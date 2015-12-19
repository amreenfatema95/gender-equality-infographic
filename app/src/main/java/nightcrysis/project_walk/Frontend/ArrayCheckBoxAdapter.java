package nightcrysis.project_walk.Frontend;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import nightcrysis.project_walk.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NIghtCrysIs on 2015/11/29.
 */
public class ArrayCheckBoxAdapter extends ArrayAdapter<String>{
    ArrayList<String> entries;

    public ArrayCheckBoxAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        entries = new ArrayList<>(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.list_item_checkbox, null);
        ((TextView)layout.findViewById(R.id.itemNameString)).setText(entries.get(position));
        ((CheckBox)layout.findViewById(R.id.checkbox)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v ;
                Toast.makeText(getContext(), "Clicked on Checkbox: " + cb.getText() + " is " + cb.isChecked(), Toast.LENGTH_LONG).show();
            }
        });
        return layout;
    }
}
