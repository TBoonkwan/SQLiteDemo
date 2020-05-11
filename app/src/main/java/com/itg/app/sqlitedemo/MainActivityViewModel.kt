package com.itg.app.sqlitedemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itg.app.sqlitedemo.entities.SyncDataResponse
import com.itg.app.sqlitedemo.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Teerapong on 11/05/2020
 */
class MainActivityViewModel(private val repository: MainRepository) : ViewModel() {

    val syncDataResponse: MutableLiveData<MutableList<SyncDataResponse>> = MutableLiveData()

    fun syncData() {

        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getAll()
            if (!data.isNullOrEmpty()) {
                syncDataResponse.postValue(data)
            }
        }

        fetchData()
    }

    private fun fetchData() {
        repository.getRemoteDataSource(object : Callback<MutableList<SyncDataResponse>> {
            override fun onFailure(call: Call<MutableList<SyncDataResponse>>, t: Throwable) {
            }

            override fun onResponse(
                call: Call<MutableList<SyncDataResponse>>,
                response: Response<MutableList<SyncDataResponse>>
            ) {
                if (response.isSuccessful) {
                    val data = response.body()
                    data?.let {
                        CoroutineScope(Dispatchers.IO).launch {

                            val source = syncDataResponse.value
                            source?.let {
                                it.addAll(data)
                            }

                            repository.deleteAll()

                            if (source.isNullOrEmpty()) {
                                repository.insert(data)
                            } else {
                                val distinct =
                                    source.distinctBy { it.title } as MutableList<SyncDataResponse>
                                repository.insert(distinct)
                            }

                            syncDataResponse.postValue(repository.getAll())
                        }
                    }
                }
            }
        })
    }
}