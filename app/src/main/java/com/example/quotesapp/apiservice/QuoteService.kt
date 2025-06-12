package com.example.quotesapp.apiservice

import com.example.quotesapp.data.Quotes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteService {

    @GET("/api/data")
    suspend fun getQuotesData(@Query("drilldowns") state: String, @Query("measures") measures: String, @Query("Year")year: String): Response<Quotes>
}