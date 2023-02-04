package com.example.topscorer.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

@SuppressLint("SimpleDateFormat")
private val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

fun Long.toSimpleDate() : String {
    return simpleDateFormat.format(Date(this))
}