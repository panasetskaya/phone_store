package com.panasetskaia.core.data.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.panasetskaia.core.data.models.PhoneDbModel

@Database(entities = [PhoneDbModel::class], version = 1, exportSchema = false)
@TypeConverters(CartConverters::class)
abstract class CartDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao

    companion object {
        private var INSTANCE: CartDatabase? = null
        private val LOCK = Any()
        private const val DB_NAME = "cart.db"

        fun getInstance(application: Application): CartDatabase {
            synchronized(LOCK) {
                INSTANCE?.let {
                    return it
                }
                val db = Room.databaseBuilder(application, CartDatabase::class.java, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = db
                return db
            }
        }
    }
}