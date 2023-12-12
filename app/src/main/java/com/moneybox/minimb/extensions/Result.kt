package com.moneybox.minimb.extensions

suspend fun <T> Result<T>.doOnSuccess(action: suspend (T) -> Unit): Result<T> {
    if (this.isSuccess) action(getOrNull()!!)
    return this
}

suspend fun <T> Result<T>.doOnFailure(action: suspend (Throwable?) -> Unit): Result<T> {
    if (this.isFailure) action(this.exceptionOrNull())
    return this
}