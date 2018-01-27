package com.exercises.javid.educationapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
//import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Javid on 1/26/2018.
 */

public class DatabaseHandler extends SQLiteAssetHelper {

    static final String DB_NAME = "education.db";
    static final String DB_PATH = "/data/data/com.exercises.javid.educationapplication/databases/";
    static final String TABLE_NAME = "detail";
    static final String T_ID = "_id";
    static final String T_NAME = "name";
    static final String T_DESC = "description";
    static final String T_IMG = "photo";
    static final String T_VO = "voice";
    private final Context context;
    private SQLiteDatabase myDataBase;

    DatabaseHandler(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

//    @Override
//    public void onCreate(SQLiteDatabase db) {
//
//    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null) {
            myDataBase.close();
        }
        super.close();
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;

        try {
            String dbPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);

        } catch (SQLiteException e) {
            //database does't exist yet.
            Log.e("My Cod: ", "DB is not there " + e.toString());
        }
        if (checkDB != null) {
            checkDB.close();
        }

        return checkDB != null;
    }

    public void createDatabase() throws IOException {

    }

//    public void openDatabase() throws SQLiteException {
//        String dbPath = DB_PATH + DB_NAME;
//        myDataBase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);
//
//    }

    ArrayList<HashMap<String, String>> getDataTable() {

        ArrayList<HashMap<String, String>> dataList = new ArrayList<>();

        try {
            String dbPath = DB_PATH + DB_NAME;
//            SQLiteDatabase db = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_NAME, new String[]{T_ID, T_NAME, T_DESC, T_IMG, T_VO},
                    null, null, null, null, null);

            if (cursor.moveToFirst()) {
                for (int counter = 0; counter < cursor.getCount(); counter++) {
                    HashMap<String, String> record = new HashMap<>();
                    record.put(T_ID, cursor.getInt(cursor.getColumnIndex(T_ID)) + "");
                    record.put(T_NAME, cursor.getString(cursor.getColumnIndex(T_NAME)));
                    record.put(T_DESC, cursor.getString(cursor.getColumnIndex(T_DESC)));
                    record.put(T_IMG, cursor.getString(cursor.getColumnIndex(T_IMG)));
                    record.put(T_VO, cursor.getString(cursor.getColumnIndex(T_VO)));
                    dataList.add(record);
                    cursor.moveToNext();
                }
            }
            cursor.close();
            db.close();
        } catch (Exception e) {
            //database does't exist yet.
            Log.e("My Cod: ", "DB is not there " + e.toString());
        }
        return dataList;
    }

    HashMap<String, String> getDataRecord(int position) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[]{T_ID, T_NAME, T_DESC, T_IMG, T_VO},
                null, null, null, null, null);

        cursor.moveToPosition(position);

        HashMap<String, String> record = new HashMap<>();
        record.put("id", cursor.getString(cursor.getColumnIndex(T_ID)));
        record.put("name", cursor.getString(cursor.getColumnIndex(T_NAME)));
        record.put("desc", cursor.getString(cursor.getColumnIndex(T_DESC)));
        record.put("img", cursor.getString(cursor.getColumnIndex(T_IMG)));
        record.put("voice", cursor.getString(cursor.getColumnIndex(T_VO)));

        cursor.close();
        return record;
    }
}
