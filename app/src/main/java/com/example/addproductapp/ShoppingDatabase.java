package com.example.addproductapp;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.addproductapp.model.Users;

public class ShoppingDatabase extends SQLiteOpenHelper {

    public static final String DB_NAME = "shopping_db";
    public static final int DB_VERSION = 1;

    public static final String TB_USERS = "users";

    public static final String TB_CLM_USER_ID = "user_id";
    public static final String TB_CLM_USER_NAME = "user_name";
    public static final String TB_CLM_USER_FULL_NAME = "full_name";
    public static final String TB_CLM_USER_PASSWORD = "user_password";
    public static final String TB_CLM_USER_EMAIL = "user_email";
    public static final String TB_CLM_USER_PHONE = "user_phone";
    public static final String TB_CLM_USER_IMAGE = "user_image";

    public ShoppingDatabase(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+TB_USERS+" ("+
                TB_CLM_USER_ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+
                TB_CLM_USER_NAME+" TEXT UNIQUE , "+
                TB_CLM_USER_FULL_NAME+" TEXT , "+
                TB_CLM_USER_PASSWORD+" TEXT , "+
                TB_CLM_USER_EMAIL+" TEXT UNIQUE , "+
                TB_CLM_USER_PHONE+" TEXT , "+
                TB_CLM_USER_IMAGE+" TEXT );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Handle database upgrade as needed
    }

    // Insert a new user into the database
    public boolean insertUser(Users user){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TB_CLM_USER_NAME,user.getUserName());
        values.put(TB_CLM_USER_FULL_NAME,user.getFullName());
        values.put(TB_CLM_USER_PASSWORD,user.getUserPassword());
        values.put(TB_CLM_USER_EMAIL,user.getEmail());
        values.put(TB_CLM_USER_PHONE,user.getPhone());
        values.put(TB_CLM_USER_IMAGE,user.getUserImage());

        long res = db.insert(TB_USERS,null,values);
        db.close();
        return res != -1;
    }

    // Check user credentials and return user ID if valid
    @SuppressLint("Range")
    public int checkUser(String userName, String password){
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {TB_CLM_USER_ID};
        String selection = TB_CLM_USER_NAME + " =? AND " + TB_CLM_USER_PASSWORD + " =?";
        String[] selectionArgs = {userName, password};

        Cursor cursor = db.query(TB_USERS, columns, selection, selectionArgs, null, null, null);
        int id = -1; // Default to -1 to indicate user not found

        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndex(TB_CLM_USER_ID));
        }
        cursor.close();
        db.close();
        return id;
    }

    // Retrieve user information by ID
    @SuppressLint("Range")
    public Users getUser(int userId){
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {
                TB_CLM_USER_ID, TB_CLM_USER_NAME, TB_CLM_USER_FULL_NAME,
                TB_CLM_USER_PASSWORD, TB_CLM_USER_EMAIL, TB_CLM_USER_PHONE, TB_CLM_USER_IMAGE
        };
        String selection = TB_CLM_USER_ID + " =?";
        String[] selectionArgs = {String.valueOf(userId)};

        Cursor cursor = db.query(TB_USERS, columns, selection, selectionArgs, null, null, null);

        Users user = null;
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(TB_CLM_USER_ID));
            String userName = cursor.getString(cursor.getColumnIndex(TB_CLM_USER_NAME));
            String fullName = cursor.getString(cursor.getColumnIndex(TB_CLM_USER_FULL_NAME));
            String password = cursor.getString(cursor.getColumnIndex(TB_CLM_USER_PASSWORD));
            String email = cursor.getString(cursor.getColumnIndex(TB_CLM_USER_EMAIL));
            String phone = cursor.getString(cursor.getColumnIndex(TB_CLM_USER_PHONE));
            String image = cursor.getString(cursor.getColumnIndex(TB_CLM_USER_IMAGE));

            user = new Users(id, userName, fullName, image, password, email, phone);
        }
        cursor.close();
        db.close();
        return user;
    }
}
