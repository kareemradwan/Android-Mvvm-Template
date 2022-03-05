package com.kradwan.codegeneartormvvmsample.domain.util


enum class ResponseStatus {
    SUCCESS,
    ERROR,
    EMPTY
}

public const val NO_INTERNET = 0
public const val OK_200 = 200
public const val CREATED = 201
public const val ACCEPTED = 202
public const val NO_CONTENT = 204
public const val OK_LAST_299 = 299
public const val NOT_MODIFIED = 304
public const val BAD_REQUEST = 400
public const val BAD_REQUEST_LAST = 499
public const val UNAUTHORIZED = 401
public const val FORBIDDEN = 403
public const val NOT_FOUND = 404
public const val UNSUPPORTED_ACTION = 405
public const val CONFLICT = 409
public const val VALIDATION_ERROR_DATA = 417
public const val VALIDATION_FAILED = 422
public const val SERVER_ERROR = 500

