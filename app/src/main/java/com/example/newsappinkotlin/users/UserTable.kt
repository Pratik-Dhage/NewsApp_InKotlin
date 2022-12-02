package com.example.newsappinkotlin.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userTable")
class UserTable (

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo
    val email: String? = null,

    @ColumnInfo
    val password: String? = null
)