package info.androidhive.WeatherApp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import info.androidhive.WeatherApp.adapter.ExpandableListAdapter;
import info.androidhive.WeatherApp.database.DatabaseWork;

public class DataShow extends AppCompatActivity {

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    Button noti;
    Button fori;


    int id;
    String desc = "";
    float tem;
    String pressure = "";
    String humidity = "";
    String windspeed = "";
    String city = "";
    String sunrise = "";
    String sunset = "";
    float foo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_show);

        Intent ii = getIntent();
        Bundle ib = ii.getExtras();
        id = (Integer) ib.get("id");
        desc = (String) ib.get("des");
        tem = (float) ib.get("temp");
        pressure = (String) ib.get("press");
        humidity = (String) ib.get("hum");
        windspeed = (String) ib.get("wind");
        city = (String) ib.get("city");
        sunrise = (String) ib.get("sunrise");
        sunset = (String) ib.get("sunset");
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        noti = (Button) findViewById(R.id.notify);
        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DatabaseWork.class);
                intent.putExtra("id", id);
                startActivity(intent);

            }
        });
        fori = (Button) findViewById(R.id.forecast);
        fori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), info.androidhive.WeatherApp.Forecast.class);
                intent.putExtra("city", city);
                startActivity(intent);
            }


        });
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Sunrise & Sunset");
        listDataHeader.add("Temperature");
        listDataHeader.add("Pressure");
        listDataHeader.add("Humidity");

        listDataHeader.add("Wind Speed");
        listDataHeader.add("Description of the sky in short");


        // Adding child data


        List<String> top = new ArrayList<String>();
        top.add("Sunrise today : " + sunrise + " am");
        top.add("Sunset today : " + sunset + " pm");

        List<String> top250 = new ArrayList<String>();
        top250.add("Temperature now :" + tem + " Celcius");


        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("Pressure now in atmosphere :" + pressure + "KPa");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("Humidity is " + humidity + "%");

        List<String> spd = new ArrayList<>();
        spd.add("wind speed " + windspeed + "km/h");

        List<String> descrip = new ArrayList<>();
        descrip.add(desc);

        listDataChild.put(listDataHeader.get(0), top);
        listDataChild.put(listDataHeader.get(1), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(2), nowShowing);
        listDataChild.put(listDataHeader.get(3), comingSoon);
        listDataChild.put(listDataHeader.get(4), spd);
        listDataChild.put(listDataHeader.get(5), descrip);

    }


}