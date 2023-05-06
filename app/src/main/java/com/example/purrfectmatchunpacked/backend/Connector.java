package com.example.purrfectmatchunpacked.backend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;

import com.google.firebase.auth.FirebaseAuth;

public class Connector extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbPurrfect";
    private static int DATABASE_VERSION = 1;
    private static StringBuilder query;
    SQLiteDatabase db;

    public Connector (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        String SQL = "CREATE TABLE tblUser (\n" +
                "  email VARCHAR(50) PRIMARY KEY,\n" +
                "  fname VARCHAR(50) NOT NULL,\n" +
                "  lname VARCHAR(50) NOT NULL,\n" +
                "  address VARCHAR(100) NOT NULL,\n" +
                "  password VARCHAR(64) NOT NULL,\n" +
                "  contactNumber VARCHAR(12) NOT NULL\n" +
                ");";

        db.execSQL(SQL);

        SQL = "CREATE TABLE tblCat (\n" +
                "  email VARCHAR(50) NOT NULL,\n" +
                "  name VARCHAR(50) NOT NULL,\n" +
                "  organization VARCHAR(50) NOT NULL,\n" +
                "  description VARCHAR(200) NOT NULL,\n" +
                "  color VARCHAR(10) NOT NULL,\n" +
                "  age INTEGER,\n" +
                "  FOREIGN KEY (email) REFERENCES tblUser(email)\n" +
                ");";
        db.execSQL(SQL);

        SQL = "CREATE TABLE tblOrganization (\n" +
                "  email VARCHAR(50) PRIMARY KEY,\n" +
                "  contactNumber VARCHAR(12) NOT NULL,\n" +
                "  name VARCHAR(50) NOT NULL,\n" +
                "  address VARCHAR(100) NOT NULL\n" +
                ");";

        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tblUser");
        db.execSQL("DROP TABLE IF EXISTS tblOrganization");
        db.execSQL("DROP TABLE IF EXISTS tblCat");
        onCreate(db);
    }

    public Connector SELECT(String s){
        init();
        query.append("SELECT " + s + ' ');
        return this;
    }

    public Connector FROM (String s){
        init();
        query.append("FROM " + s + ' ');
        return this;
    }

    public void execute (){
        query.append(';');
        String sql = query.toString();
        query.delete(0, query.length());
        db.execSQL(sql);
    }

    public Cursor query(){
        query.append(';');
        String sql = query.toString();
        query.delete(0, query.length());
        return db.rawQuery(sql, null);
    }

    @SuppressLint("Range")
    public void printCursor(Cursor cursor){
        if (cursor.moveToFirst()) {
            do {
                System.out.println(cursor.getString(0) + " : " + cursor.getString(1));

            } while (cursor.moveToNext());
        }
    }




    void init(){
        if (query == null) query = new StringBuilder();
    }

    public Cursor rawQuery (String s, SQLiteDatabase db){
        return  db.rawQuery(s, null);
    }

    public void rawExec(String s, SQLiteDatabase db){
        try {
            db.execSQL(s);
        } catch (Exception e){
            System.out.println(e);
        }
    }

}
