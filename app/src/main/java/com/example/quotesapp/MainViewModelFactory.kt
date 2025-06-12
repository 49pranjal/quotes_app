package com.example.quotesapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.quotesapp.repository.QuotesRepository

class MainViewModelFactory(private val mainRepository: QuotesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == MainViewModel::class.java)  {
            return MainViewModel(mainRepository) as T
        }
        return MainViewModel(mainRepository) as T
    }
}