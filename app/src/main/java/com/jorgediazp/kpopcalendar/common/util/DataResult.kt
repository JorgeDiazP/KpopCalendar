package com.jorgediazp.kpopcalendar.common.util

sealed class DataResult<out T>(
    val data: T? = null,
    val code: Int? = null,
    val message: String? = null,
    val exception: Exception? = null
) {
    class Success<out T>(data: T? = null, code: Int? = null) :
        DataResult<T>(data = data, code = code)

    class Error<out T>(
        message: String? = null,
        exception: Exception? = null,
        code: Int? = null
    ) : DataResult<T>(message = message, exception = exception, code = code)
}