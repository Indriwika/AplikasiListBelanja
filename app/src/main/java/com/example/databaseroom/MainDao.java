package com.example.databaseroom;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface MainDao {
    //insert query
    @Insert(onConflict = REPLACE)
    void insert (MainData mainData);

    @Delete
    void delete (MainData mainData);

    @Delete
    void reset (List<MainData> mainData);

    //update
    @Query("UPDATE table_name SET text = :sText WHERE ID = :sID")
    void update(int sID, String sText);

    //get all data
    @Query("SELECT * FROM table_name")
    List<MainData> getAll();
}
