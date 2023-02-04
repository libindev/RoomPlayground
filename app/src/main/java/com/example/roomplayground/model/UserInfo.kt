package com.example.roomplayground.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "user_info")
data class UserInfo(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val name: String,
    val email: String,
    val phone: String,
    val createdDate: Date,
    @ColumnInfo(name = "address", defaultValue = "")
    val address: String = ""
)
