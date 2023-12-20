package com.example.tubes_aksyal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database_logreg.db";
    private static final int DATABASE_VERSION = 1;

    public DataHelper (Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table logreg(username text primary key, password text null);  ";
        Log.d("data", "onCreate" + sql);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists logreg ");
    }

    // masukin data baru
    public Boolean insertData(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = db.insert("logreg", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    // login session cek user
    public Boolean cekusername(String username){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from logreg where username =?", new String[] {username});
        if(cursor.getCount()> 0)
            return true;
        else
            return false;
    }

    // login session cek password
    public Boolean cekuserdanpass(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from logreg where username =? and password =?", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    // update password session
    public Boolean updatepass(String pwdlama, String pwdbaru){
        SQLiteDatabase db = this.getWritableDatabase();
        if (cekpass(db, pwdlama)) {
            ContentValues values = new ContentValues();
            values.put("password", pwdbaru);

            int rowsAffected = db.update("logreg", values,  "password =?", new String[]{pwdlama});

            // Check if the update was successful
            return rowsAffected > 0;
        } else {
            // Password lama tidak valid
            return false;
        }
    }

    // cek update password
    public boolean cekpass(SQLiteDatabase db, String pwdlama){
        Cursor cursor = db.rawQuery("SELECT * FROM logreg WHERE password =?", new String[]{pwdlama});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }

    // update username session
    public Boolean updateuname(String unmlama, String unmbaru){
        SQLiteDatabase db = this.getWritableDatabase();
        if (cekuname(db, unmlama)) {
            ContentValues values = new ContentValues();
            values.put("username", unmbaru);

            int rowsAffected = db.update("logreg", values,  "username =?", new String[]{unmlama});

            // Check if the update was successful
            return rowsAffected > 0;
        } else {
            // Password lama tidak valid
            return false;
        }
    }

    // cek update username
    public boolean cekuname(SQLiteDatabase db, String unmlama){
        Cursor cursor = db.rawQuery("SELECT * FROM logreg WHERE username =?", new String[]{unmlama});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }

}
