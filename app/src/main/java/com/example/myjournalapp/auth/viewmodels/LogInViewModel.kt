package com.example.myjournalapp.auth.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myjournalapp.auth.Event.AuthEvent
import com.example.myjournalapp.data.model.LogInRequest
import com.example.myjournalapp.data.model.LogInResponse
import com.example.myjournalapp.data.model.RegistrationResponse
import com.example.myjournalapp.data.model.Resource
import com.example.myjournalapp.data.remote.RetrofitBuilder
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import kotlin.concurrent.thread

class LogInViewModel: ViewModel() {
    val isFormValid: MutableLiveData<Boolean> = MutableLiveData(
        true
    )

    var status : MutableLiveData<Resource<*>> = MutableLiveData()
    private val api =RetrofitBuilder.gossipCentralAPI

    fun onEvent(event:AuthEvent){
        when(event){
            is AuthEvent.LogInEvent->{
                val request = event.request
                if(request.email.isEmpty() ||
                    request.password.isEmpty()
                ){
                    isFormValid.value = false
                    return
                }
                logIn(request)
            }
        }
    }
    private fun logIn(request: LogInRequest) {
        status.value = Resource.loading(data=null);
        viewModelScope.launch(Dispatchers.IO){
            val threadInfo = Thread.currentThread().name
            Log.i("login","login running on thread $threadInfo")
            val result: LogInResponse
            try {
                result = api.logIn(request)
                if(result.successful){
                   Log.i("login-success",result.toString())
                   val resource =Resource.success(data=result.data)
                   status.postValue(resource)
                }else{
                    Log.i("login-fail",result.data.toString())
                    val resource = Resource.error(data=null, message = result.data.first)
                    status.postValue(resource)
                }
            }catch (e:HttpException){
                val resource: Resource<*>
                if(e.code()==400){
                    val gson = Gson()
                    val errorResponse = gson.fromJson(
                        e.response()?.errorBody()!!.charStream(),
                        LogInResponse::class.java
                    )
                    Log.i("login-error", errorResponse.toString())
                    resource = Resource.error(data=null, message = errorResponse.data.first)
                    status.postValue(resource)
                }else{
                        Log.i("login-error", e.toString())
                        Log.i("login-error", e.message())
                        Log.i("login-error", e.response().toString())
                        resource = Resource.error(
                            data = null,
                            message = e . localizedMessage ?:
                            "look like something went wrong")

                    }
            }catch(e: Exception){
                Log.i("login",e.toString())
                val resource = Resource.error(data = null,
                    message = e.localizedMessage ?: "look like something went wrong"
                )
                status.postValue(resource)
            }

        }
    }
}