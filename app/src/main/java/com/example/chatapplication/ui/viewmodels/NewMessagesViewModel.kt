package com.example.chatapplication.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.chatapplication.data.entity.User
import com.example.chatapplication.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewMessagesViewModel @Inject constructor(var repository: UserRepository): ViewModel() {
    var newChatsList = MutableLiveData<List<User>>()

    init {
        loadNewChats()
    }


    fun loadNewChats(){
        newChatsList = repository.loadNewChats()
    }
}