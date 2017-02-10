package info.androidhive.WeatherApp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import info.androidhive.WeatherApp.adapter.ExpandableListAdapter;

public class Forecast extends AppCompatActivity implements DownloadForecast.AsyncResponses{

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    String city;
    double lat;
    double lng;
    static TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forecast);
        Intent ii = getIntent();
        Bundle ib = ii.getExtras();
        city = (String)ib.get("city");
        lat = (double)ib.get("lat");
        lng = (double)ib.get("lng");
        //txt = (TextView)findViewById(R.id.textCudna);
        //txt.setText(city);
        //city="Khulna";
        DownloadForecast downloadForecast = new DownloadForecast();

        //String stringUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q="+city+"&mode=json&units=metric&cnt=4&appid=6b0d85b3fd266e315d8a4273a495f544";
      //  api.openweathermap.org/data/2.5/forecast/daily?lat=35&lon=139&cnt=4
        String stringUrl="http://api.openweathermap.org/data/2.5/forecast/daily?lat="+lat+"&lon="+lng+"&mode=json&units=metric&cnt=4&appid=44db6a862fba0b067b1930da0d769e98";

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
        {
            downloadForecast.delegating=this;
           // txt.setText(city);
            downloadForecast.execute(stringUrl);

        }
        else
        {
            txt.setText("No network connection available.");
        }
       // process();
    }

    @Override
    public void processFinishing(String[] s,String[] s1,String[] s2,String[] s3,String[] s4,String[] s5)
    {
       // txt.setText(s[0]+" "+s[1]+" "+s[2]+" "+s1[0]+" "+s1[1]+" "+s1[2]+" "+s2[0]+" "+s2[1]+" "+s2[2]+" "+s3[0]+" "+s3[1]+" "+s3[2]);

        Bundle bundel = new Bundle();
        bundel.putStringArray("firstdate", s);

        Bundle bunde2 = new Bundle();
        bunde2.putStringArray("seconddate",s1);

        Bundle bunde3 = new Bundle();
        bunde3.putStringArray("thirddate",s2);

        Bundle bunde4 = new Bundle();
        bunde4.putStringArray("fourthdate",s3);

        Bundle bunde5 = new Bundle();
        bunde5.putStringArray("fifthdate",s4);

        Bundle bunde6 = new Bundle();
        bunde6.putStringArray("sixthdate",s5);

        Intent intent = new Intent(this,ForecastShow.class);
        intent.putExtras(bundel);
        intent.putExtras(bunde2);
        intent.putExtras(bunde3);
        intent.putExtras(bunde4);
        intent.putExtras(bunde5);
        intent.putExtras(bunde6);
        startActivity(intent);

    }
@Override
protected void onPause()
{
    super.onPause();
    finish();
  }
}
