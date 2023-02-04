package com.example.topscorer.util

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    class Error<out T>(val data: Throwable? = null) : Resource<T>()
    class Loading<out T> : Resource<T>()
}