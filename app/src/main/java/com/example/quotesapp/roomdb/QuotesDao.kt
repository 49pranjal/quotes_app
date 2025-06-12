package com.example.quotesapp.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.quotesapp.data.Data

@Dao
interface QuotesDao {

    @Insert
    suspend fun insertQuotesData(populationData: List<Data>)

    @Query("SELECT * FROM quotesTable")
    suspend fun getQuotesData(): List<Data>
}