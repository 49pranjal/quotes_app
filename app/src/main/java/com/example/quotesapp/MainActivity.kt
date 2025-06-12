package com.example.quotesapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.quotesapp.databinding.ActivityMainBinding
import com.example.quotesapp.repository.Response

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewmodel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val quotesRepository = (application as MainApplication).quotesRepository
        viewmodel = ViewModelProvider(this, MainViewModelFactory(quotesRepository)).get(MainViewModel::class.java)

        viewmodel.quoteList.observe(this, Observer { response ->
            when(response) {
                is Response.Failure -> {
                    Toast.makeText(this, "${response.errorMessage}", Toast.LENGTH_SHORT).show()
                }
                is Response.Loading -> {
                    Toast.makeText(this, "Loader is Loading: ${response.loadingState}", Toast.LENGTH_SHORT).show()
                }
                is Response.Success -> {
                    Log.d("Testing", response.data.toString())
                    response.data?.data?.forEach {
                        Toast.makeText(this, "${it.Year} - ${it.Population}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        })

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}