package com.plcoding.daggerhiltcourse.data.repository

import android.app.Application
import com.plcoding.daggerhiltcourse.R
import com.plcoding.daggerhiltcourse.data.remote.MyApi
import com.plcoding.daggerhiltcourse.domain.repository.MyRepository
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.random.Random

class MyRepositoryImpl @Inject constructor(
    private val api: MyApi,
    private val appContext: Application
) : MyRepository {

    init {
        val appName = appContext.getString(R.string.app_name)
        println("Hello from the repository. The app name is $appName")
    }

    override suspend fun doNetworkCall(
        result: (result: String) -> Unit,
        error: (code: Int) -> Unit, progress: (progress: String) -> Unit
    ) {
        progress("0%")
        delay(300)
        progress("20%")
        delay(300)
        progress("40%")
        delay(300)
        progress("60%")
        delay(300)
        progress("80%")
        delay(300)
        var random = Random.nextInt()
        if (random % 2 == 0) result("$random") else error(404)

    }
}