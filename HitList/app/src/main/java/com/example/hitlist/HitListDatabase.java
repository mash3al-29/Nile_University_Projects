
package com.example.hitlist;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Person.class}, version = 1)
public abstract class HitListDatabase extends RoomDatabase {
    public abstract PersonDao personDao();
}
