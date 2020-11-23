package com.example.databaseroom;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

//add database entities
@Database(entities = {MainData.class},version = 1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    //create database instance
    private static RoomDB database;

    //define database name
    private static String DATABASE_NAME = "database";


    public synchronized static RoomDB getInstance(Context context){
        //check condition

        if (database == null){
            //when database is nuul instialize database
            database = Room.databaseBuilder(context.getApplicationContext(),RoomDB.class,DATABASE_NAME).allowMainThreadQueries().fallbackToDestructiveMigration().build();

        }
        //return database
        return database;
    }
    //create dao
    public abstract MainDao mainDao();

}