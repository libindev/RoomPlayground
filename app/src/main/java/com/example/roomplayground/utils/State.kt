package com.example.roomplayground.utils

sealed class State<T> {
    data class Success<T>(val data: T) : State<T>()
    data class Failed<T>(val exception: Exception) : State<T>()
}