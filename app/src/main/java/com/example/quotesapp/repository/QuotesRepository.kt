package com.example.quotesapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quotesapp.apiservice.QuoteService
import com.example.quotesapp.data.Quotes
import com.example.quotesapp.data.Source
import com.example.quotesapp.roomdb.QuotesDao
import com.example.quotesapp.utils.NetworkClass

class QuotesRepository(
    private val quoteService: QuoteService,
    private val quotesDao: QuotesDao,
    private val applicationContext: Context
) {
    private val mutableQuoteList = MutableLiveData<Response<Quotes>>()

    val quoteList: LiveData<Response<Quotes>>
        get() = mutableQuoteList

    suspend fun getQuotesData(state: String, measures: String, year: String) {
        if (NetworkClass.isInternetAvailable(applicationContext)) {
            try {
                mutableQuoteList.postValue(Response.Loading(true))
                val result = quoteService.getQuotesData(state, measures, year)
                if (result?.body() != null) {
                    quotesDao.insertQuotesData(result.body()!!.data)
                    mutableQuoteList.postValue(Response.Success(result.body()!!))
                } else {
                    mutableQuoteList.postValue(Response.Failure("Api not hit properly"))
                }
            } catch (e:Exception) {
                mutableQuoteList.postValue(Response.Failure(e.message.toString()))
            }

        } else {
            val quotesData = quotesDao.getQuotesData()
            val populationYearWise = Quotes(quotesData, ArrayList<Source>())
            mutableQuoteList.postValue(Response.Success(populationYearWise))
        }
    }

    suspend fun getQuotesFromBackground(state: String, measures: String) {
        val year = (Math.random()*10).toInt()+2013
        val result = quoteService.getQuotesData(state, measures, year.toString())

        if (result?.body() != null) {
            quotesDao.insertQuotesData(result?.body()!!.data)
        }
    }
}