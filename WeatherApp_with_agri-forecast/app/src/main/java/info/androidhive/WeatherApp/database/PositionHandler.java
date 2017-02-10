package info.androidhive.WeatherApp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class PositionHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "positionManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "position";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String Lat_ID = "lat_id";
    private static final String Long_ID = "long_id";
    private static final String Location = "location";
   // private static final String DESCRIPTION = "phone_number";

    public PositionHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // onUpgrade(db,1,1);

        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + Lat_ID + " REAL,"
                + Long_ID + " REAL,"+Location+" TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    void addPosition(Position position) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Lat_ID, position.getLatitude()); // Contact Name
        values.put(Long_ID, position.getLongitude());// Contact Phone
        values.put(Location,position.getLocation());
        // Inserting Row
        db.insert(TABLE_CONTACTS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    Position getPosition(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID,
                        Lat_ID, Long_ID}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Position position = new Position(Integer.parseInt(cursor.getString(0)),
                Double.parseDouble(cursor.getString(1)), Double.parseDouble(cursor.getString(2)),cursor.getString(3));
        // return contact
        return position;
    }

    // Getting All Contacts
    public List<Position> getAllPosition()
    {
        List<Position> positionList = new ArrayList<Position>();
        // Select All Query
        // String mew="id = 803";
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst())
        {
            do
            {
                Position position = new Position();

                    position.setID(Integer.parseInt(cursor.getString(0)));
                    position.setLatitude(Double.parseDouble(cursor.getString(1)));
                    position.setLongitude(Double.parseDouble(cursor.getString(2)));
                    position.setLocation(cursor.getString(3));
                    // Adding contact to list
                    positionList.add(position);


            } while (cursor.moveToNext());
        }

        // return contact list
        return positionList;
    }

//    // Updating single contact
//    public int updateContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        ContentValues values = new ContentValues();
//        values.put(WR_ID, contact.getKeyid());
//        values.put(DESCRIPTION, contact.getDescription());
//
//        // updating row
//        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//    }

    // Deleting single contact
//    public void deleteContact(Contact contact) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
//                new String[] { String.valueOf(contact.getID()) });
//        db.close();
//    }


    // Getting contacts Count
    public int getPositionCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}


