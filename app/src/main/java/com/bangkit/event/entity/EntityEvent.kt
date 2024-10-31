package com.bangkit.event.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
class EntityEvent (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String = "",

    @ColumnInfo(name = "name")
    val name: String? = "",

    @ColumnInfo(name = "imageLogo")
    val imageLogo: String? = null,

    @ColumnInfo(name = "quota")
    val quota: Int,

    @ColumnInfo(name = "description")
    val description: String? = null,

    @ColumnInfo(name = "registrants")
    val registrants: Int



    ):Parcelable