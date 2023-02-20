package com.example.chatapplication.data.entity

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Message (var id: String? = "",
                    var fromId : String? = "",
                    var text: String? = "",
                    var timeStamp : Long? = 0,
                    var toId : String? = "") : java.io.Serializable{
}