package com.tomaschlapek.booklibrary.model


/**
 * Response holder provided to the UI
 */
sealed class Response constructor(data: String?, error: Throwable?)

class ResponseSuccess(val data: String) : Response(data, null)
class ResponseError(val error: Throwable) : Response(null, error)
class ResponseLoading() : Response(null, null)

//sealed class Status
//class StatusSuccess : Status()
//class StatusError : Status()
//class StatusLoading : Status()
