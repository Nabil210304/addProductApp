package com.example.addproductapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class Helper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    static final String DATABASE_NAME = "tambahproduk";

    public Helper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_TABLE_PRODUCT = "CREATE TABLE produk (id_produk INTEGER PRIMARY KEY autoincrement, nama TEXT NOT NULL, jenis TEXT NOT NULL, harga REAL NOT NULL)";
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_PRODUCT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS produk");
        onCreate(sqLiteDatabase);
    }

    public ArrayList<HashMap<String, String>> getAll() {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        String QUERY = "SELECT * FROM produk";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(QUERY, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("id_produk", cursor.getString(0));
                map.put("nama", cursor.getString(1));
                map.put("jenis", cursor.getString(2));
                map.put("harga", cursor.getString(3));
                list.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<HashMap<String, String>> getByType(String jenis) {
        ArrayList<HashMap<String, String>> list = new ArrayList<>();
        String QUERY = "SELECT * FROM produk WHERE jenis = ?";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(QUERY, new String[]{jenis});
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<>();
                map.put("id_produk", cursor.getString(0));
                map.put("nama", cursor.getString(1));
                map.put("jenis", cursor.getString(2));
                map.put("harga", cursor.getString(3));
                list.add(map);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public void insert(String nama, String jenis, double harga) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama", nama);
        values.put("jenis", jenis);
        values.put("harga", harga);
        database.insert("produk", null, values);
    }

    public void update(int id_produk, String nama, String jenis, double harga) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nama", nama);
        values.put("jenis", jenis);
        values.put("harga", harga);
        database.update("produk", values, "id_produk = ?", new String[]{String.valueOf(id_produk)});
    }

    public void delete(int id_produk) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete("produk", "id_produk = ?", new String[]{String.valueOf(id_produk)});
    }
}
