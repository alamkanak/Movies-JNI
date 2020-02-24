package com.raquib.movies.model

/**
 * Wrapper class for data models allowing the ability to represent SUCCESS and LOADING states.
 */
class Resource<T> private constructor(private val status: Status, val data: T?) {

    fun getStatus(): Status {
        return status
    }

    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null)
        }
    }

    enum class Status {
        SUCCESS, LOADING
    }
}