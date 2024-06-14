package com.plcoding.daggerhiltcourse.domain.repository

interface MyRepository {
    suspend fun doNetworkCall(
        result: (result: String) -> Unit,
        error: (code: Int) -> Unit,
        progress: (progress: String) -> Unit
    )
}