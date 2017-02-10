package info.androidhive.WeatherApp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import info.androidhive.WeatherApp.adapter.ExpandableListAdapter;

public class ForecastShow extends AppCompatActivity
{
    String[] date = new String[4];
    String[] pressure = new String[4];
    String[] humidity = new String[4];
    String[] description = new String[4];
    String[] getDay = new String[4];
    String[] getNight = new String[4];

    TextView textView;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast_show);

        date = getIntent().getStringArrayExtra("firstdate");
        pressure = getIntent().getStringArrayExtra("seconddate");
        humidity = getIntent().getStringArrayExtra("thirddate");
        description = getIntent().getStringArrayExtra("fourthdate");
        getDay = getIntent().getStringArrayExtra("fifthdate");
        getNight = getIntent().getStringArrayExtra("sixthdate");


        textView = (TextView)findViewById(R.id.txtview);

        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

    }

    private void prepareListData()
    {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add(date[0]);
        listDataHeader.add(date[1]);
        listDataHeader.add(date[2]);
        listDataHeader.add(date[3]);

        // Adding child data
        List<String> firstdate = new ArrayList<String>();
        firstdate.add("Probable temperature of day is " + getDay[0] + " celcius and night is " + getNight[0]+" celcius");
        firstdate.add("Pressure may be "+pressure[0]+"hPa");
        firstdate.add("Humidity would be probably "+humidity[0]+"%");
        firstdate.add("Sky condition will be like-- "+description[0]);


        List<String> seconddate = new ArrayList<String>();
        seconddate.add("Probable temperature of day is " + getDay[1] + " celcius and night is " + getNight[1]+" celcius");
        seconddate.add("Pressure may be " + pressure[1]+"hPa");
        seconddate.add("Humidity would be probably " + humidity[1]+"%");
        seconddate.add("Sky condition will be like-- " + description[1]);


        List<String> thirddate = new ArrayList<String>();
        thirddate.add("Probable temperature of day is " + getDay[2] + " celcius and night is " + getNight[2]+" celcius");
        thirddate.add("Pressure may be " + pressure[2]+"hPa");
        thirddate.add("Humidity would be probably " + humidity[2]+"%");
        thirddate.add("Sky condition will be like-- " + description[2]);

        List<String> fourthdate = new ArrayList<String>();
        fourthdate.add("Probable temperature of day is " + getDay[3] + " celcius and night is " + getNight[3]+" celcius");
        fourthdate.add("Pressure may be " + pressure[3]+"hPa");
        fourthdate.add("Humidity would be probably " + humidity[3]+"%");
        fourthdate.add("Sky condition will be like-- " + description[3]);



        listDataChild.put(listDataHeader.get(0), firstdate); // Header, Child data
        listDataChild.put(listDataHeader.get(1), seconddate);
        listDataChild.put(listDataHeader.get(2), thirddate);
        listDataChild.put(listDataHeader.get(3), fourthdate);

    }



}
