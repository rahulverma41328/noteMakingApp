package com.example.notetaking;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {User.class},version = 2)
public abstract class AddDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
