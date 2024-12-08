package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_rates")
data class CurrenciesEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val base: String,
    val currencyCode: String,
    val rate: Double
)