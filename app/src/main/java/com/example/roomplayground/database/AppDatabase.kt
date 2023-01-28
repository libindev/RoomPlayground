package com.example.roomplayground.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.roomplayground.database.dao.UserInfoDao
import com.example.roomplayground.model.UserInfo
import com.example.roomplayground.utils.Converters

@Database(
    entities = [
        UserInfo::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "auth")
                .fallbackToDestructiveMigration()
                .build()
    }
}
