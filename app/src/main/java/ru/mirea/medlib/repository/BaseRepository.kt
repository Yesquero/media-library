package ru.mirea.medlib.repository

import android.util.Log
import retrofit2.Response
import ru.mirea.medlib.network.ResultWrapper

open class BaseRepository(protected val TAG: String) {

    protected suspend fun <T> safeApiCall(call: suspend () -> Response<T>): ResultWrapper<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return ResultWrapper.Success(body)
                }
            }
            Log.d(TAG, "Call failed: $response")

            return formatError("${response.code()}; ${response.message()}")
        } catch (ex: Exception) {
            ex.message?.let {
                Log.d(TAG, "Call failed with exception: ${ex.message}")
                return formatError(ex.message!!)
            }
            Log.d(TAG, "Call failed with exception, NO message")
            return formatError("Exception, no message.")
        }
    }

    private fun <T> formatError(errorMessage: String): ResultWrapper<T> {
        return ResultWrapper.Error("Call failed: $errorMessage")
    }
}