package com.arnomutai.mynet.models

class user {
    private var fullname:String = ""
    private var userName:String = ""
    private var image:String = ""
    private var bio:String = ""
    private var uid:String = ""

    constructor()

    constructor(userName:String,bio:String,fullname:String,image:String,uid:String, ){
        this.userName = userName
        this.bio = bio
        this.fullname = fullname
        this.image = image
        this.bio = bio
    }

    fun getUserName():String{
        return userName
    }
    fun setUserName(userName: String){
        this.userName = userName
    }
    fun getFullName():String{
        return fullname
    }
    fun setFullName(fullname: String){
        this.fullname= fullname
    }
    fun getBio():String{
        return bio
    }
    fun setBio(bio: String){
        this.bio = bio
    }
    fun getImage():String{
        return image
    }
    fun setImage(image: String){
        this.image= image
    }
    fun getUid():String{
        return uid
    }
    fun setUid(uid: String){
        this.uid = uid
    }
}