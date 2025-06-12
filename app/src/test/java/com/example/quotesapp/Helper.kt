package com.example.quotesapp

import java.io.InputStreamReader

object Helper {

    fun getDataFromFile(fileName: String): String {
        /*val stream = Helper::class.java.getResourceAsStream(fileName)
        val size = stream.available()
        val buffer = ByteArray(size)
        stream.read(buffer)
        return String(buffer, Charsets.UTF_8)*/
        val stream = Helper::class.java.getResourceAsStream(fileName)
        var builder = StringBuilder()
        val reader = InputStreamReader(stream, "UTF-8")
        reader.readLines().forEach {
            builder.append(it)
        }
        return builder.toString()
    }
}