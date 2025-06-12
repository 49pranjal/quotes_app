package com.example.quotesapp.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.quotesapp.data.Data

@Database(entities = [Data::class], version = 1)
abstract class QuotesDb : RoomDatabase() {

    abstract fun quotesDao(): QuotesDao


    companion object {
        @Volatile
        private var INSTANCE: QuotesDb? = null


        fun getInstance(context: Context): QuotesDb {

            if (INSTANCE == null) {
                synchronized(this) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            QuotesDb::class.java,
                            "quotes_database"
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

}