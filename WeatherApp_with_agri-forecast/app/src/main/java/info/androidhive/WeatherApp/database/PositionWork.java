package info.androidhive.WeatherApp.database;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

import info.androidhive.WeatherApp.R;

public class PositionWork extends AppCompatActivity {

    TextView textView;
    int counter=0;

    double currentLatitude;
    double currentLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_position_work);
        textView=(TextView)findViewById(R.id.position);
        Intent ii = getIntent();
        Bundle ib = ii.getExtras();
        currentLatitude=(double)ib.get("lat");
        currentLongitude=(double)ib.get("lng");
        PositionHandler ps = new PositionHandler(this);



        List<Position> pos = ps.getAllPosition();

        for (Position cn : pos)
        {

            counter++;

        }
           if(counter<8)
           {

               ps.addPosition(new Position(22.8446972,89.525603,"আপনার বর্তমান অবস্থান থেকে নিকটবর্তী \"সার\" এর দোকান এর অবস্থান : \n" +
                       "\n" +
                       "১২৩ দাসপাড়া, খুলনা  "));
               ps.addPosition(new Position(22.8434019,89.5145845,"আপনার বর্তমান অবস্থান থেকে নিকটবর্তী \"সার\" এর দোকান এর অবস্থান : \n" +
                       "\n" +
                       "১/২, রায়েরমহল , খুলনা  "));
               ps.addPosition(new Position(22.850837,89.5356988,"আপনার বর্তমান অবস্থান থেকে নিকটবর্তী \"সার\" এর দোকান এর অবস্থান : \n" +
                       "\n" +
                       "২৩, বি এল কলেজ এর পেছনে , খালিশপুর  , খুলনা  "));
               ps.addPosition(new Position(22.8906553,89.5066022,"আপনার বর্তমান অবস্থান থেকে নিকটবর্তী \"সার\" এর দোকান এর অবস্থান : \n" +
                       "\n" +
                       "টং এর মোড় , রেলিগেট ফেরি টার্মিনাল নিকটস্থ  , খুলনা  "));
               ps.addPosition(new Position(22.8667021, 89.5007656,"আপনার বর্তমান অবস্থান থেকে নিকটবর্তী \"সার\" এর দোকান এর অবস্থান : \n" +
                       "\n" +
                       "মুহসিন মোড় , দোউলতপুর  , খুলনা  "));
               ps.addPosition(new Position(22.8464972,89.5348566,"আপনার বর্তমান অবস্থান থেকে নিকটবর্তী \"সার\" এর দোকান এর অবস্থান : \n" +
                       "\n" +
                       "মুজগুন্নি বাস স্ট্যান্ড  , খুলনা  "));
               ps.addPosition(new Position(22.835789,89.5316809,"আপনার বর্তমান অবস্থান থেকে নিকটবর্তী \"সার\" এর দোকান এর অবস্থান : \n" +
                       "\n" +
                       "বয়রা বাজার , হানিফ কাউন্টারের পাশে   , খুলনা  "));

               ps.addPosition(new Position(22.7993274,89.5399206,"আপনার বর্তমান অবস্থান থেকে নিকটবর্তী \"সার\" এর দোকান এর অবস্থান : \n" +
                       "\n" +
                       "২৩/২/ গল্লামারি বাজার   , খুলনা  "));
           }



         double rad=0.0 ,val=0.0;
        double curlongitude,culatitude,nculongitude,nculatitude;

        List<Position> position = ps.getAllPosition();

        String log="";
        int c = 0;
        for (Position cn : position)
        {
            c++;
            if(c==1)
            {
                culatitude=(3.1416*currentLatitude)/180;
                nculatitude=(3.1416*cn.getLatitude())/180;
                curlongitude=(3.1416*currentLongitude)/180;
                nculongitude=(3.1416*cn.getLongitude())/180;


                rad=  6371 * Math.acos(
                        Math.sin(culatitude) * Math.sin(nculatitude)
                                + Math.cos(culatitude) * Math.cos(nculatitude) * Math.cos(curlongitude - nculongitude));

                val = rad;
            }
           else
             {
                 culatitude=(3.1416*currentLatitude)/180;
                 nculatitude=(3.1416*cn.getLatitude())/180;
                 curlongitude=(3.1416*currentLongitude)/180;
                 nculongitude=(3.1416*cn.getLongitude())/180;
                  val=  6371 * Math.acos(
                          Math.sin(culatitude) * Math.sin(nculatitude)
                                  + Math.cos(culatitude) * Math.cos(nculatitude) * Math.cos(curlongitude - nculongitude));   }


             Log.d("Name: ", log);
            if(val<=rad)
            {
                rad = val;
                log = cn.getLocation();
            }


        }

        textView.setText(log);
    }

}
