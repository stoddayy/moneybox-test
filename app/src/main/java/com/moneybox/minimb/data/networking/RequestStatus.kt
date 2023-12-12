package com.moneybox.minimb.data.networking

enum class RequestStatus {
    DEFAULT,
    REQUESTING,
    SUCCESS,
    FAILURE
}

fun RequestStatus.isRequesting() = this == RequestStatus.REQUESTING

fun RequestStatus.isFailure() = this == RequestStatus.FAILURE

fun RequestStatus.isSuccess() = this == RequestStatus.SUCCESS