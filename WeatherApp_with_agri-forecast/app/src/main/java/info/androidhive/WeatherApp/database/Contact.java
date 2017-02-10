package info.androidhive.WeatherApp.database;


public class Contact {

    //private variables
    int _id;
    int _keyid;
    String _desc;

    // Empty constructor
    public Contact(){

    }
    // constructor
    public Contact(int id, int kid, String _phone_number){
        this._id = id;
        this._keyid = kid;
        this._desc = _phone_number;
    }

    // constructor
    public Contact(int kid, String _phone_number){
        this._keyid = kid;
        this._desc = _phone_number;
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
    public int getKeyid(){
        return this._keyid;
    }

    // setting name
    public void setKeyid(int kid){
        this._keyid = kid;
    }

    // getting phone number
    public String getDescription(){
        return this._desc;
    }

    // setting phone number
    public void setDescription(String description){
        this._desc = description;
    }
}


