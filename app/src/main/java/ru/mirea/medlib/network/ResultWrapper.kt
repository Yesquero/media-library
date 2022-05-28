package ru.mirea.medlib.network

sealed class ResultWrapper<out T>(
    val data: T? = null,
    val message: String? = null,
    val exception: Exception? = null,
) {
    class Loading<T> : ResultWrapper<T>()

    class Error<T>(message: String, data: T? = null) : ResultWrapper<T>(data, message)

    class Success<T>(data: T) : ResultWrapper<T>(data)

    fun isSuccessful(): Boolean {
        return data != null && message == null && exception == null
    }
}