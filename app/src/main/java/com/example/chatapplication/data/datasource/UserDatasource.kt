package com.example.chatapplication.data.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chatapplication.data.entity.Message
import com.example.chatapplication.data.entity.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class UserDatasource (var reference: DatabaseReference) {

    var newChatsList =MutableLiveData<List<User>>()


    fun loadNewChats () : MutableLiveData<List<User>>{
        reference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<User>()

                for ( d in snapshot.children){
                    val user = d.getValue(User :: class.java)
                    list.add(user!!)
                }
                newChatsList.value = list
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        return newChatsList
    }





    }






