package com.example.chatapplication.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapplication.data.entity.Message
import com.example.chatapplication.data.entity.User
import com.example.chatapplication.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(val userRepository: UserRepository) : ViewModel(){
    var friendsChatList = MutableLiveData<List<Message>>()
      init {
       loadLatestMessages()
       Log.e("testInit",friendsChatList.value.toString() )
      }
    fun loadLatestMessages (){
      friendsChatList = userRepository.loadLatestMessages()
    }
}