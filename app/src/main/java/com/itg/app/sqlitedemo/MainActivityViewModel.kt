package com.itg.app.sqlitedemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.itg.app.sqlitedemo.entities.SyncDataResponse
import com.itg.app.sqlitedemo.repository.MainRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Teerapong on 11/05/2020
 */
class MainActivityViewModel(private val repository: MainRepository) : ViewModel() {

    val syncDataResponse: MutableLiveData<MutableList<SyncDataResponse>> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()

    fun syncData(action: String) {

        CoroutineScope(Dispatchers.IO).launch {
            // get data from db
            val data = repository.getAll()
            // check data is not null
            if (!data.isNullOrEmpty()) {
                // show data to view
                withContext(Dispatchers.Main) {
                    syncDataResponse.value = data
                }
            }
        }

        getDataWithRemoteDataSource(action = action)
    }

    private fun getDataWithRemoteDataSource(action: String) {
        // get data from api with action
        repository.getRemoteDataSource(action, object : Callback<MutableList<SyncDataResponse>> {
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

                            // delete all data
                            repository.deleteAll()

                            val source = syncDataResponse.value
                            data.forEach { data ->
                                source?.filter {
                                    it.retailerId == data.retailerId
                                }?.map {
                                    it.title = data.title
                                    it.status = data.status
                                }
                            }

                            source?.let {
                                it.addAll(data)
                            }

                            if (source.isNullOrEmpty()) {
                                // insert data to d
                                repository.insert(data)
                            } else {
                                // compare data
                                val distinct =
                                    source.distinctBy { it.retailerId } as MutableList<SyncDataResponse>
                                repository.insert(distinct)
                            }

                            // get all data and show
                            val data = repository.getAll()
                            withContext(Dispatchers.Main) {
                                syncDataResponse.value = data
                            }
                        }
                    }
                } else {
                    error.value = response.errorBody()?.string()
                }
            }
        })
    }
}