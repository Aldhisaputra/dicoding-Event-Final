package com.bangkit.event.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bangkit.event.entity.EntityEvent

@Dao
interface DaoEvent {

    @Query("SELECT * FROM EntityEvent ORDER BY id ASC")
    fun getAllFavorite(): LiveData<List<EntityEvent>>

    @Query("SELECT * FROM EntityEvent WHERE id = :id")
    fun getFavoriteEventById(id: String): LiveData<EntityEvent>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertEvent(entityEvent: EntityEvent)

    @Delete
    fun delete(entityEvent: EntityEvent)

}