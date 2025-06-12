package com.example.quotesapp.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.quotesapp.MainApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkerClass(private val context: Context, workerParameters: WorkerParameters ): Worker(context, workerParameters) {
    override fun doWork(): Result {
        Log.d("Worker Class","Entered after 2 min")
        val repository = (context as MainApplication).quotesRepository
        CoroutineScope(Dispatchers.IO).launch {
            repository.getQuotesFromBackground("Nation","Population")
        }

        return Result.success()
    }
}