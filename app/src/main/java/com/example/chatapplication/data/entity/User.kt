package com.example.chatapplication.data.entity

class User : java.io.Serializable {
    var name : String? = null
    var email : String? = null
    var uid : String? = null
    var profilePhoto: String? = null

    constructor(){}
    constructor(username: String?, email : String?, uid: String?, profilePhoto : String?){
        this.name = username
        this.email = email
        this.uid = uid
        this.profilePhoto = profilePhoto


    }
}

