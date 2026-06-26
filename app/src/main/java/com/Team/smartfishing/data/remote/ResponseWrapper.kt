package com.Team.smartfishing.data.remote

data class ResponseWrapper<T>(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: T? = null
)