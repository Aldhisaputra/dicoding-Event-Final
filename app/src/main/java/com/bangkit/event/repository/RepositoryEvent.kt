package com.bangkit.event.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.bangkit.event.entity.EntityEvent
import com.bangkit.event.room.DaoEvent
import com.bangkit.event.room.DatabaseEvent
import java.util.concurrent.Executors

class RepositoryEvent private constructor(context: Context) {
    private val daoEvent: DaoEvent by lazy { DatabaseEvent.getDatabase(context).daoEvent() }
    private val executor = Executors.newSingleThreadExecutor()

    fun getAllFavoriteEvent(): LiveData<List<EntityEvent>> = daoEvent.getAllFavorite()

    fun insertEvent(event: EntityEvent) = executor.execute { daoEvent.insertEvent(event) }

    fun delete(event: EntityEvent) = executor.execute { daoEvent.delete(event) }

    fun getFavoriteEventById(id: String): LiveData<EntityEvent> = daoEvent.getFavoriteEventById(id)

    companion object {
        @Volatile
        private var instance: RepositoryEvent? = null

        fun getInstance(context: Context): RepositoryEvent =
            instance ?: synchronized(this) {
                instance ?: RepositoryEvent(context).also { instance = it }
            }
    }
}
