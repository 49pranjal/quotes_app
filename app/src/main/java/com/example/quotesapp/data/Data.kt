package com.example.quotesapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "quotesTable")
data class Data(
   @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("ID Nation")
    val idNation: String,
    @SerializedName("ID Year")
    val idYear: Int,
    val Nation: String,
    val Population: Int,
    @SerializedName("Slug Nation")
    val slugNation: String,
    val Year: String
)