package com.practice.bank.common.exception

interface CodeInterface{
    val code: Int
    var message: String
}

enum class ErrorCode(
    override val code: Int,
    override var message: String
): CodeInterface{
    AUTH_CONFIG_NOT_FOUND(-100,"auth config not found"),
    FAILED_TO_CALL_CLIENT(-101,"failed to call client"),
    CALL_RESULT_BODY_NILL(-102, "body is nill"),
    PROVIDER_NOT_FOUND(-103,"provider not found"),
    TOKEN_INVALID(-104,"token is invalid"),
    TOKEN_EXPIRED(-105,"token expired"),
}