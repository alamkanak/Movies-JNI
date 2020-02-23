package com.raquib.movies.model

class Resource<T> private constructor(status: Status, data: T?, message: String?) {
    private val status: Status = status
    val data: T? = data

    fun getStatus(): Status {
        return status
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
    }

    enum class Status {
        SUCCESS, LOADING
    }
}