package com.example.chatapplication.repository

import androidx.lifecycle.MutableLiveData
import com.example.chatapplication.data.datasource.MessageDatasource
import com.example.chatapplication.data.datasource.UserDatasource
import com.example.chatapplication.data.entity.Message
import com.example.chatapplication.data.entity.User

class UserRepository (var userDatasource: UserDatasource, var messageDatasource: MessageDatasource) {
    fun loadNewChats () : MutableLiveData<List<User>> = userDatasource.loadNewChats()
    fun sent(fromID: String,
             text: String,
             timeStamp: Long,
             toId: String) = messageDatasource.sent(fromID, text, timeStamp, toId)
    fun loadChat(fromID: String, toId: String) : MutableLiveData<List<Message>> = messageDatasource.loadChat(fromID, toId)
    fun deleteMessage(uid : String) = messageDatasource.deleteMessage(uid)
    fun deleteChat(fromID: String, toId: String) = messageDatasource.deleteChat(fromID, toId)
  //  fun loadChatFriends() = messageDatasource.loadChatFriends()
    fun loadLatestMessages() : List<Message> = messageDatasource.loadLatestMessage()
}