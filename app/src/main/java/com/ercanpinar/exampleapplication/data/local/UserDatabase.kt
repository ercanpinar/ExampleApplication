package com.ercanpinar.exampleapplication.data.local.user

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun userDAO(): UserDao

    companion object {
        const val DATABASE_NAME: String = "user_db"
    }
}