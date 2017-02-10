package info.androidhive.WeatherApp;

import android.content.Context;
import android.content.Intent;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import info.androidhive.WeatherApp.activity.Aboutus;
import info.androidhive.WeatherApp.activity.FragmentDrawer;
import info.androidhive.WeatherApp.activity.HomeFragment;
import info.androidhive.WeatherApp.database.DatabaseWork;

import info.androidhive.WeatherApp.DownloadWebpageTask.AsyncResponse;
import info.androidhive.WeatherApp.database.PositionWork;

public class MainActivity extends AppCompatActivity implements AsyncResponse, FragmentDrawer.FragmentDrawerListener
{

    TextView text;
    private LocationManager locationManager;

    int iid;
    String desc;
    float temperature;
    String pressure;
    String humidity;
    String windspeed;
    String sunrs;
    String sunst;
    double lat;
    double lng;

    private static String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(false);

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch


    }

    @Override
    protected void onStart()
    {
        super.onStart();
        boolean gpsEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (!gpsEnabled) {
            Toast.makeText(getApplicationContext(),
                    "Please consider enabling GPS", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUp();
    }

    public void setUp() {
        Location gpsLocation = null;
        Location networkLocation = null;


        locationManager.removeUpdates(listener);
        gpsLocation = requestUpdateFromProvider(LocationManager.GPS_PROVIDER);
        networkLocation = requestUpdateFromProvider(LocationManager.NETWORK_PROVIDER);

        if (gpsLocation != null && networkLocation != null) {
            Location myLocation = getBetterLocation(gpsLocation,
                    networkLocation);
            setValuesInUI(myLocation);


        }
    }

    private void setValuesInUI(Location loc) {
        lat = loc.getLatitude();

        lng=loc.getLongitude();


                DownloadWebpageTask downloadWebpageTask = new DownloadWebpageTask();
               String stringUrl="http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lng+"&appid=44db6a862fba0b067b1930da0d769e98";
                ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected())
                {
                    downloadWebpageTask.delegate=this;
                    downloadWebpageTask.execute(stringUrl);

                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Please consider enabling network", Toast.LENGTH_LONG).show();

                }


    }

    private Location getBetterLocation(Location newLocation, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // A new location is always better than no location
            return newLocation;
        }

        // Check whether the new location fix is newer or older
        long timeDelta = newLocation.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > 60000;
        boolean isSignificantlyOlder = timeDelta < 60000;
        boolean isNewer = timeDelta > 0;

        // If it's been more than two minutes since the current location, use
        // the new location
        // because the user has likely moved.
        if (isSignificantlyNewer) {
            return newLocation;
            // If the new location is more than two minutes older, it must be
            // worse
        }
        else if (isSignificantlyOlder)
        {
            return currentBestLocation;
        }

        // Check whether the new location fix is more or less accurate
        int accuracyDelta = (int) (newLocation.getAccuracy() - currentBestLocation
                .getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;

        // Determine location quality using a combination of timeliness and
        // accuracy
        if (isMoreAccurate) {
            return newLocation;
        } else if (isNewer && !isLessAccurate) {
            return newLocation;
        }
        return currentBestLocation;
    }

    private Location requestUpdateFromProvider(String provider) {
        Location location = null;
        if (locationManager.isProviderEnabled(provider)) {
            locationManager.requestLocationUpdates(provider, 1000, 10, listener);
            location = locationManager.getLastKnownLocation(provider);

        } else {
            Toast.makeText(getApplicationContext(),
                    provider + " is not enabled.", Toast.LENGTH_LONG).show();
        }
        return location;
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    LocationListener listener = new LocationListener() {

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onLocationChanged(Location location) {
            setValuesInUI(location);

        }
    };



    @Override
    public void processFinish(int id,String description,float temp,String press,String hum,String wind,String sunrise,String sunset)
    {

        iid = id;
        desc=description;
        pressure=press;
        humidity=hum;
        windspeed=wind;
        sunrs=sunrise;
        sunst=sunset;
        temperature=temp;
        displayView(0);
    }



    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        Intent intent;
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);

                break;
            case 1:
                 intent = new Intent(getApplicationContext(),DataShow.class);
                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                 intent.putExtra("id",iid);
                 intent.putExtra("des",desc);
                 intent.putExtra("temp",temperature);
                 intent.putExtra("press",pressure);
                 intent.putExtra("hum",humidity);
                 intent.putExtra("wind", windspeed);
                 //intent.putExtra("city",city);
                 intent.putExtra("sunrise",sunrs);
                 intent.putExtra("sunset",sunst);
                 Log.i("abcv","message");
                 startActivity(intent);

                break;
            case 2:
                intent = new Intent(getApplicationContext(),Forecast.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("lat",lat);
                intent.putExtra("lng", lng);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(getApplicationContext(),DatabaseWork.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("id", iid);
                startActivity(intent);

                break;
            case 4:
                intent = new Intent(getApplicationContext(), PositionWork.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("lat", lat);
                intent.putExtra("lng",lng);
                startActivity(intent);
                break;

            case 5:
                intent = new Intent(getApplicationContext(), Aboutus.class);

                startActivity(intent);
                break;


            default:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title);
        }
    }




}
