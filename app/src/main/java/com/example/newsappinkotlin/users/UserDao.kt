package com.example.newsappinkotlin.users

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {

    @Insert
    fun insertUser(userTable: UserTable)

    @Query("SELECT EXISTS (SELECT * FROM UserTable WHERE email=:userEmail)")
    fun isTaken(userEmail: String): Boolean

    @Query("SELECT EXISTS (SELECT * FROM UserTable WHERE email=:userEmail AND password=:password)")
    fun isLogin(userEmail: String, password: String): Boolean
}