package ru.mirea.medlib.network

sealed class ResultWrapper<out T>(
    val data: T? = null,
    val message: String? = null,
    val exception: Exception? = null,
    val isLoading: Boolean = false
) {
    class Loading<T>(isLoading: Boolean = true) : ResultWrapper<T>(isLoading = isLoading)

    class Error<T>(message: String, data: T? = null, isLoading: Boolean = false) :
        ResultWrapper<T>(data, message, isLoading = isLoading)

    class Success<T>(data: T, isLoading: Boolean = false) :
        ResultWrapper<T>(data, isLoading = isLoading)

    fun isSuccessful(): Boolean {
        return data != null && message == null && exception == null
    }
}