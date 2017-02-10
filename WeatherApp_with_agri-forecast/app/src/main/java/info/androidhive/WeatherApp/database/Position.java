package info.androidhive.WeatherApp.database;


public class Position {

    //private variables
    int _id;
    double lat_id;
    double long_id;
    String location;

    // Empty constructor
    public Position(){

    }
    // constructor
    public Position(int id, double lat, double lng,String location){
        this._id = id;
        this.lat_id = lat;
        this.long_id = lng;
        this.location=location;
    }

    // constructor
    public Position(double lat, double lng,String location){
        this.lat_id = lat;
        this.long_id = lng;
        this.location = location;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public double getLatitude(){
        return this.lat_id;
    }

    // setting name
    public void setLatitude(double lat){
        this.lat_id = lat;
    }

    // getting phone number
    public double getLongitude(){
        return this.long_id;
    }

    // setting phone number
    public void setLongitude(double lng){
        this.long_id = lng;
    }

    public String getLocation(){
        return  this.location;
    }
    public void setLocation(String location)
    {
        this.location=location;
    }
}
