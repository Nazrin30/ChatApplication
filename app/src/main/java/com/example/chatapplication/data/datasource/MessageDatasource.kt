package com.example.chatapplication.data.datasource

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.chatapplication.data.entity.Message
import com.example.chatapplication.data.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase

class MessageDatasource(var referenceUser: DatabaseReference) {
    private val chat = MutableLiveData<List<Message>>()
    private val chatFriends = MutableLiveData<List<User>>()
    private val referenceMessages = FirebaseDatabase.getInstance().getReference("/messages")
    private val currentUser = Firebase.auth.currentUser
    var latestMessages = mutableListOf<Message>()

    fun sent(
        fromID: String,
        text: String,
        timeStamp: Long,
        toId: String
    ) {
        val new_message = Message("", fromID, text, timeStamp, toId)
//        val referenceUserMessage = FirebaseDatabase.getInstance().getReference("/user-messages/$fromID/$toId")
//        val toReferenceUserMessage = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromID")
//        referenceUserMessage.push().setValue(new_message)
//        toReferenceUserMessage.push().setValue(new_message)
        referenceMessages.push().setValue(new_message)
        val latestMessageReference = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromID/$toId")
        latestMessageReference.setValue(new_message)
        val latestMessageToReference = FirebaseDatabase.getInstance().getReference("/latest-messages/$toId/$fromID")
        latestMessageToReference.setValue(new_message)
    }

    fun loadChat(fromID: String, toId: String): MutableLiveData<List<Message>> {
        referenceMessages.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val list = ArrayList<Message>()

                for (child in snapshot.children) {
                    val message = child.getValue(Message::class.java)
                    if (message != null) {
                        if ((message.fromId == fromID && message.toId == toId) || (message.fromId == toId && message.toId == fromID)) {
                            message.id = child!!.key!!
                            list.add(message)
                        }
                    }
                }
                chat.value = list

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        return chat


    }

    fun deleteMessage(uid: String) {
        referenceMessages.child(uid).removeValue()
    }

    fun deleteChat(fromID: String, toId: String) {
        referenceMessages.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    val message = child.getValue(Message::class.java)
                    if (message != null) {
                        if ((message.fromId == fromID && message.toId == toId) || (message.fromId == toId && message.toId == fromID)) {
                            referenceMessages.child(child.key.toString()).removeValue()
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    fun loadLatestMessage() : List<Message>{
        val hashMap = HashMap<String, Message>()
        var list = mutableListOf<Message>()
        val fromID = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromID")
        ref.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
              val chatMessage = snapshot.getValue(Message ::class.java) ?: return
              hashMap[snapshot.key.toString()] = chatMessage
                for ((key, value) in hashMap){
                    list.add(value)
                    Log.e("test", value.toString())

                }

            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

                val chatMessage = snapshot.getValue(Message ::class.java) ?: return
                hashMap[snapshot.key.toString()] = chatMessage
                for ((key, value) in hashMap){
                    Log.e("test ", "$key = $value")
                    list.add(value)
                    Log.e("test", value.toString())

                }

            }


            override fun onChildRemoved(snapshot: DataSnapshot) {}
            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
            override fun onCancelled(error: DatabaseError) {}

        })


       latestMessages = list
       Log.e("testLatestMessages", latestMessages.toString())
       return latestMessages
    }
}
