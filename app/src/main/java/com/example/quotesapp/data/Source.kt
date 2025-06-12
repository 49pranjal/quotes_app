package com.example.quotesapp.data

data class Source(
    val annotations: Annotations,
    val measures: List<String>,
    val name: String,
    val substitutions: List<Any>
)