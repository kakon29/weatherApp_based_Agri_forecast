package info.androidhive.WeatherApp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class DownloadWebpageTask extends AsyncTask<String, Void, String>
 {


    private static final String DEBUG_TAG = "HttpExample";
     int id;
     String description = "";
     String temperature = "";
     String windSpeed = "";
     String humidity = "";
     String pressure = "";
     float temp;
     String sunset="";
     String sunrise="";
     long sunsr;
     long sunst;





     public interface AsyncResponse{
        void processFinish(int id,String description,float temp,String press,String hum,String wind,String sunrise,String sunset);
    }

     public AsyncResponse delegate = null;

    @Override
    protected String doInBackground(String... urls)
    {

        // params comes from the execute() call: params[0] is the url.
        try
        {
            return downloadUrl(urls[0]);
        }
        catch (IOException e)
        {
            return "Unable to retrieve web page. URL may be invalid.";
        }
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result)
    {

        // textView.setText(result);


        try
        {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArrayWeather = jsonObject.optJSONArray("weather");
            for(int i=0;i<jsonArrayWeather.length();i++)
            {
                JSONObject js = jsonArrayWeather.getJSONObject(i);
                 id = Integer.parseInt(js.optString("id").toString());
                String main = js.optString("main").toString();
                String des = js.optString("description").toString();

                description += des ;

            }
                  JSONObject main  = jsonObject.getJSONObject("main");
                  temp =Float.parseFloat(main.getString("temp"));
                  temp = temp-273;


                pressure +=main.getString("pressure");
                humidity +=main.getString("humidity");

                JSONObject wind  = jsonObject.getJSONObject("wind");
                windSpeed += wind.getString("speed");

            JSONObject sys  = jsonObject.getJSONObject("sys");
            sunsr = Integer.parseInt(sys.getString("sunrise"));

            Date date = new Date(sunsr*1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT+6"));
            String sunrise = sdf.format(date);

            sunst = Integer.parseInt(sys.getString("sunset"));
            sunst-=43200;
            Date date1 = new Date(sunst*1000L);
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
            sdf1.setTimeZone(TimeZone.getTimeZone("GMT+6"));
            String sunset = sdf1.format(date1);


            delegate.processFinish(id,description,temp,pressure,humidity,windSpeed,sunrise,sunset);


        }


        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }


    private String downloadUrl(String myurl) throws IOException
    {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 1000;

        try
        {
            URL url = new URL(myurl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();

            Log.d(DEBUG_TAG, "The response is: " + response);
            is = conn.getInputStream();

            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);

            //setBitmap(is);

            return contentAsString;

            // Makes sure that the InputStream is closed after the app is
            // finished using it.
        }
        finally
        {
            if (is != null)
            {
                is.close();
            }
        }
    }

    public String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException
    {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }
}


