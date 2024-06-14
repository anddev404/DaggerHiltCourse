package com.plcoding.daggerhiltcourse

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.daggerhiltcourse.domain.repository.MyRepository
import dagger.Lazy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MyViewModel @Inject constructor(
    private val repository: Lazy<MyRepository>
) : ViewModel() {

    private val _data = MutableStateFlow<String?>(null)
    val data = _data.asStateFlow()

    private val _error = MutableStateFlow<Int?>(null)
    val error = _error.asStateFlow()

    private val _progress = MutableStateFlow<String?>(null)
    val progress = _progress.asStateFlow()

    val _flow: Flow<Int> = flow {
        emit(11) // Emituje wartość z getInt1()
        emit(22) // Emituje wartość z getInt2()
        emit(33) // Emituje wartość statyczną
    }.flowOn(Dispatchers.IO)


    fun fetchData() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("MARCIN_W", "downloading");
            repository.get().doNetworkCall({
                _data.value = "It is data ${Random.nextInt()}"
                _error.value = null
                _progress.value = null

            }, {
                _data.value = null
                _error.value = it
                _progress.value = null

            }, {
                _progress.value = it
                _error.value = null
            })
        }

    }
}