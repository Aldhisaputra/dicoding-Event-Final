package com.bangkit.event.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bangkit.event.entity.EntityEvent

@Database(entities = [EntityEvent::class], version = 1)
abstract class DatabaseEvent : RoomDatabase() {

    abstract fun daoEvent(): DaoEvent

    companion object {
        @Volatile
        private var instance: DatabaseEvent? = null

        fun getDatabase(context: Context): DatabaseEvent =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseEvent::class.java,
                    "database_favorite"
                ).build().also { instance = it }
            }
    }
}
