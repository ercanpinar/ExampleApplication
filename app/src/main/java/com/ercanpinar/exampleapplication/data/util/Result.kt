package com.ercanpinar.exampleapplication.data.util

sealed class Result<out R> {

    /**
     * Default Initial State
     */
    object Loading : Result<Nothing>()

    /**
     * Success response
     */
    data class Success<out T>(val data: T) : Result<T>()

    /**
     * Success Cached response
     */
    data class SuccessCached<out T>(val cacheData: T) : Result<T>()

    /**
     * General Error
     */
    data class Error(val message: String) : Result<Nothing>()
}