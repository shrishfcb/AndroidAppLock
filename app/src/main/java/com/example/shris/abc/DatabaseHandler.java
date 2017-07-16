package com.example.shris.abc;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "D2";
    private static final String TABLE_APPS = "Apps";
    private static final String KEY_ID = "Id";
    private static final String KEY_NAME = "AppName";
    private static final String TABLE_ANSWERS = "Keys";
    private static final String KEY_ID1 = "Id";
    private static final String KEY_LOCKNAME = "Lname";
    private static final String KEY_ANS = "Value";
    private static final String KEY_APP = "App";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_APPS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_NAME + " TEXT" + ");";
        String query1 = "CREATE TABLE " + TABLE_ANSWERS +
                "(" + KEY_ID1 + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KEY_LOCKNAME + " TEXT," +
                KEY_ANS + " TEXT," +
                KEY_APP + " TEXT" + ");";
        db.execSQL(query1);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_APPS;
        String query1 = "DROP TABLE IF EXISTS " + TABLE_ANSWERS;
        db.execSQL(query1);
        db.execSQL(query);
        onCreate(db);
    }

    public void addApp(String appn){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(KEY_NAME, appn);
        db.insert(TABLE_APPS, null, val);
        db.close();
    }

    public void addNewVal(String lname, String ans, String ap){
        SQLiteDatabase db = this.getWritableDatabase();
        boolean isd=false;
        String query;
        //isd = IsDataInTable(db, ap);
        Log.d("Data in table: ", String.valueOf(isd));
        if (isd==true){
            ContentValues val = new ContentValues();
            val.put(KEY_ANS, ans);
            val.put(KEY_APP, ap);
            db.update(TABLE_ANSWERS, val, KEY_APP + "=" + "\'" + ap + "\'", null);
            Log.d("Inserted", String.valueOf(isd));
        }
        else {
            ContentValues values = new ContentValues();
            values.put(KEY_LOCKNAME, lname);
            values.put(KEY_ANS, ans);
            values.put(KEY_APP, ap);
            db.insert(TABLE_ANSWERS, null, values);
            Log.d("Inserted", String.valueOf(isd));
        }
    }

    public static boolean IsDataInTable(SQLiteDatabase db, String ap){
        String query = "SELECT * FROM " + TABLE_ANSWERS + " where " + KEY_APP + "=" + "\'" + ap + "\'";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }


    public boolean CheckRecord(String fld) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_APPS + " where " + KEY_NAME + " = " + "\'" + fld + "\'";
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public String getPattern(String ab){
        String ans="";
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_ANSWERS + " where " + KEY_APP + "=" + "\'" + ab + "\'";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor!=null)
            cursor.moveToFirst();
        ans = cursor.getString(2);
        return ans;
    }

    public void delApp(String appn){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_APPS, KEY_NAME + "=" + "\'" + appn + "\'", null);
        db.close();
    }
}
