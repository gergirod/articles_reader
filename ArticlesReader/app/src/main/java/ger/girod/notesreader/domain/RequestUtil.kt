package ger.girod.notesreader.domain

import ger.girod.notesreader.data.model.ResultWrapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

suspend fun <T> exequteRequest(
    dispatcher: CoroutineDispatcher,
    request: suspend () -> T
): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(request.invoke())
        } catch (e: Exception) {
            ResultWrapper.Error(e)
        }
    }
}