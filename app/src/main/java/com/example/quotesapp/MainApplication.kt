package com.example.quotesapp

import android.annotation.SuppressLint
import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.quotesapp.apiservice.QuoteService
import com.example.quotesapp.apiservice.RetrofitHelper
import com.example.quotesapp.repository.QuotesRepository
import com.example.quotesapp.roomdb.QuotesDb
import com.example.quotesapp.worker.WorkerClass
import java.util.concurrent.TimeUnit

class MainApplication: Application() {

    lateinit var quotesRepository: QuotesRepository
    override fun onCreate() {
        super.onCreate()
        initializeRepository()
        //setupWorker()

    }

    @SuppressLint("InvalidPeriodicWorkRequestInterval")
    private fun setupWorker() {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workerRequest = PeriodicWorkRequest.Builder(WorkerClass::class.java, 100, TimeUnit.SECONDS)
            .setConstraints(constraints)
            .build()
        WorkManager.getInstance(this).enqueue(workerRequest)
    }

    private fun initializeRepository() {
        val quoteService = RetrofitHelper.getInstance().create(QuoteService::class.java)
        val quotesDao = QuotesDb.getInstance(applicationContext).quotesDao()
        quotesRepository = QuotesRepository(quoteService, quotesDao, applicationContext)
    }
}