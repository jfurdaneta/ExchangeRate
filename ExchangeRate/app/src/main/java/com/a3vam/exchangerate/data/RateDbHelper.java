package com.a3vam.exchangerate.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * @author Jose Urdaneta
 * @version 1.0
 * @date today
 */

public class RateDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Rates.db";

    public RateDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + RatesContract.RatesEntry.TABLE_NAME + " ("
                + RatesContract.RatesEntry._ID + " integer PRIMARY KEY AUTOINCREMENT,"
                + RatesContract.RatesEntry.date + " TEXT NOT NULL,"
                + RatesContract.RatesEntry.value + " DOUBLE NOT NULL,"
                + RatesContract.RatesEntry.currency + " TEXT NOT NULL)");

        Log.v(getClass().getName(),"onCreate - " + RatesContract.RatesEntry.TABLE_NAME + "  Creada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long RateInsert(ContentValues values) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        if (!RateExists(values.get("date").toString(), values.get("currency").toString())){
            return sqLiteDatabase.insert(
                    RatesContract.RatesEntry.TABLE_NAME,
                    null,
                    values);
        }else{
            return 1000;
        }


    }
    private Boolean RateExists(String date, String curr){
        String query = "select * from " + RatesContract.RatesEntry.TABLE_NAME + " WHERE date='" +date + "' and currency='" + curr +"'";
        SQLiteDatabase db = getReadableDatabase();

        Cursor c = db.rawQuery(query,null);

        if (c.getCount() > 0){
            c.close();
            return true;
        }else{
            c.close();
            return false;
        }

    }

    public Cursor RateSelectCursorTop(String curr){
        String query = "select top 1 * from " + RatesContract.RatesEntry.TABLE_NAME + " WHERE currency='" + curr + "' order desc";
        //String query = "select * from " + RatesContract.RatesEntry.TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c;
        c = db.rawQuery(query, null);
        return c;
    }
    public Cursor RateSelectCursor(String curr){
        String query = "select * from " + RatesContract.RatesEntry.TABLE_NAME + " WHERE currency='" + curr + "'";
        SQLiteDatabase db = getReadableDatabase();
        Cursor c;
        c = db.rawQuery(query, null);
        return c;
    }

}
