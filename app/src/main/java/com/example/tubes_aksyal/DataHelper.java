package com.example.tubes_aksyal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class DataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "database_logreg_new.db";
    private static final int DATABASE_VERSION = 1;
    SQLiteDatabase db = this.getWritableDatabase();

    public DataHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table logreg(username text primary key, password text null, img text null);  ";
        Log.d("NOTE", "onCreate" + sql);
        db.execSQL(sql);

        String tambahdata = "create table addata(id_ integer primary key AUTOINCREMENT, username text NOT NULL, mood text null, school text null, work text null, note text null);  ";
        Log.d("NOTE", "onCreate" + tambahdata);
        db.execSQL(tambahdata);

    }

    public void saveData(String username, String hwyd, String school, String work, String note) {
        Log.e("MY", "HALLOOOO");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("mood", hwyd);
        values.put("school", school);
        values.put("work", work);
        values.put("note", note);

        // Simpan data ke tabel sesuai dengan struktur tabel Anda
        db.insert("addata", null, values);
    }

    public void deletedata(Context context, String id){
        SQLiteDatabase db = this.getWritableDatabase();
        long delete = db.delete("addata", "id_=?", new String[]{id});
        if (delete == -1) {
            Toast.makeText(context, "gagal hapus data", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(context, "berhasil hapus data", Toast.LENGTH_SHORT).show();
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null){
            cursor = db.rawQuery("select * from addata", null);
        }
        return cursor;
    }

    public boolean updatedata(String username, String hwyd, String school, String work, String note) {
        Log.e("updatedata", "update");

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("mood", hwyd);
        values.put("school", school);
        values.put("work", work);
        values.put("note", note);

        Cursor cursor = db.rawQuery("select * from addata where username =?", new String[]{username});
        if (cursor.getCount() > 0) {
            long result = db.update("addata", values, "username=?", new String[]{username});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop Table if exists logreg ");
        db.execSQL("drop Table if exists adddata ");
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

    // login cek user
    public Boolean cekusername(String username){
//        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from logreg where username =?", new String[] {username});
        if(cursor.getCount()> 0)
            return true;
        else
            return false;
    }

    // login cek password
    public Boolean cekuserdanpass(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from logreg where username =? and password =?", new String[]{username, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    // update password
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

    // update username
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

    public boolean updateImage(String username, String imageBaru) {
        SQLiteDatabase db = this.getWritableDatabase();

        try {
            FileInputStream fi = new FileInputStream(imageBaru);
            byte[] imgByte = new byte[fi.available()];
            fi.read(imgByte);
            fi.close();

            ContentValues contentValues = new ContentValues();
            contentValues.put("img", imgByte);

            // Menggunakan metode update untuk memperbarui gambar berdasarkan username
            int result = db.update("logreg", contentValues, "username=?", new String[]{imageBaru});

            // Periksa apakah ada baris yang terpengaruh oleh perintah UPDATE
            return result > 0;

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            db.close();
        }
    }
}
