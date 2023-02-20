package com.example.chatapplication.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapplication.data.entity.Message
import com.example.chatapplication.data.entity.User
import com.example.chatapplication.repository.UserRepository
import com.example.chatapplication.ui.fragments.ChatFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ChatViewModel @Inject constructor( var userRepository: UserRepository) : ViewModel(){

    var chat = MutableLiveData<List<Message>>()
    var user = MutableLiveData<User>()
    var toId = MutableLiveData<User>()

    init {
        val fromID = Firebase.auth.currentUser?.uid.toString()

        Log.e("Test chat5" , user.value.toString())
        loadChat(fromID, user.value?.uid.toString())
        Log.e("Test chat", chat.value.toString())
    }


    fun sent(fromID: String, text: String, timeStamp: Long, toId: String){
       userRepository.sent(fromID, text, timeStamp, toId)
    }

    fun loadChat(fromID: String, toId: String) {
        chat = userRepository.loadChat(fromID, toId)
        Log.e("Test chat2", chat.value.toString())
    }
    fun deleteMessage(uid : String){
        userRepository.deleteMessage(uid)
    }

    fun deleteChat(fromID: String, toId: String){
        userRepository.deleteChat(fromID, toId)
    }
}