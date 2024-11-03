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

    @ColumnInfo(name = "ownerName")
    val ownerName: String? = "",

    @ColumnInfo(name = "beginTime")
    val beginTime: String? = "",


    @ColumnInfo(name = "description")
    val description: String ="",

    @ColumnInfo(name = "registrants")
    val registrants: Int,





    ):Parcelable