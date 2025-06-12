package com.example.quotesapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quotesapp.data.Quotes
import com.example.quotesapp.repository.QuotesRepository
import com.example.quotesapp.repository.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel(private val quoteRepository: QuotesRepository): ViewModel() {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            quoteRepository.getQuotesData("Nation", "Population", "2013")
        }
    }

    val quoteList: LiveData<Response<Quotes>>
        get() = quoteRepository.quoteList
}