package com.example.roomplayground.database

import android.content.Context
import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.example.roomplayground.database.dao.UserInfoDao
import com.example.roomplayground.model.UserInfo
import com.example.roomplayground.utils.Converters

@Database(
    entities = [
        UserInfo::class
    ],
    version = 3,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = AuthDatabase.MigratePhoneNOToPhone::class), AutoMigration(from = 2, to = 3)
    ],
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AuthDatabase : RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao

    companion object {
        @Volatile
        private var instance: AuthDatabase? = null

        fun getDatabase(context: Context): AuthDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AuthDatabase::class.java, "auth")
                .fallbackToDestructiveMigration()
                .build()
    }

    @RenameColumn(tableName = "user_info", fromColumnName = "phoneNo", toColumnName = "phone")
    class MigratePhoneNOToPhone : AutoMigrationSpec
}
