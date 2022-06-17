package com.example.myjournalapp.auth.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.myjournalapp.auth.Event.AuthEvent
import com.example.myjournalapp.data.model.RegistrationRequest
import com.example.myjournalapp.data.model.Resource
import com.example.myjournalapp.data.remote.RetrofitBuilder
import kotlinx.coroutines.Dispatchers


class RegisterViewModel: ViewModel() {
//    val isLoading: MutableLiveData<Boolean> = MutableLiveData(
//        false
//    )
    val isFormValid: MutableLiveData<Boolean> = MutableLiveData(
        true
    )
//    val loginSuccess: MutableLiveData<Boolean> = MutableLiveData(
//        false
//    )
//    val errorMessage: MutableLiveData<String> = MutableLiveData(
//        ""
//    )


   private  val api = RetrofitBuilder.gossipCentralAPI

    fun onEvent(event: AuthEvent){
        when(event){
            is AuthEvent.RegistrationEvent->{
                val request = event.request
                if (
                    request.email.isEmpty() ||
                    request.firstName.isEmpty() ||
                    request.lastName.isEmpty() ||
                    request.password.isEmpty()


                ){
                    isFormValid.value= false
                    return
                }
                register(request)
            }
        }
    }

    fun register(request: RegistrationRequest) {
        liveData(Dispatchers.IO){
            emit(Resource.loading(data=null))
            val result = api.register(request)
           if(result.successful){
               emit(Resource.success(result.data))
           }else{
               emit(Resource.error(data=null, message = result.data.toString()))
           }

        }
    }

    }
