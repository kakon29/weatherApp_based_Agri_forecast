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


public class DownloadForecast extends AsyncTask<String, Void, String>
{
    String getPressure[]=new String[4];
    String getTemperature[]=new String[4];
    String getDate[] = new String[4];
    String getHumidity[] = new String[4];
    String getDay[] = new String[4];
    String getNight[] = new String[4];
    String getDes[] = new String[4];

    private static final String DEBUG_TAG = "HttpExample";
    int id;
    String description = "";
    String temperature = "";
    String windSpeed = "";
    String humidity = "";
    String pressure = "";
    float temp;

    public interface AsyncResponses{
        void processFinishing(String[] getDate,String[] getPres,String[] hum,String[] des,String[] getDay,String[] getNight);
    }

   public AsyncResponses delegating = null;

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
        String data="";
       // Forecast.process();
        try {
            JSONObject  jsonRootObject = new JSONObject(result);

            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonArray = jsonRootObject.optJSONArray("list");

            //Iterate the jsonArray and print the info of JSONObjects
            for(int i=0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                // Getting date
                long id = Integer.parseInt(jsonObject.optString("dt"));
                long unixSeconds = id;
                Date date = new Date(unixSeconds*1000L);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setTimeZone(TimeZone.getTimeZone("GMT+6"));
                String formattedDate = sdf.format(date);
                getDate[i] = formattedDate;


                 // Getting temperature value
                 JSONObject tem = jsonObject.getJSONObject("temp");
                 getDay[i] = tem.getString("day");
                 getNight[i]=tem.getString("night");



                // Getting other value
                getPressure[i] = jsonObject.optString("pressure");
                getHumidity[i] = jsonObject.optString("humidity");

                // Getting weather description
                JSONArray jsonArray1 = jsonObject.optJSONArray("weather");
                for(int j=0;j<jsonArray1.length();j++)
                {
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(j);
                    getDes[i]=jsonObject1.optString("description");
                }


            }

            delegating.processFinishing(getDate,getPressure,getHumidity,getDes,getDay,getNight);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

//        // textView.setText(result);
//
//
      //  delegating.processFinishing(data);

    }


    private String downloadUrl(String myurl) throws IOException
    {
        InputStream is = null;
        // Only display the first 500 characters of the retrieved
        // web page content.
        int len = 100000;

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


