package com.pesan.instagram.model

import com.google.firebase.events.Publisher

class Post {
    private var postid  : String = ""
    private var postimage : String = ""
    private var publisher : String = ""
    private var description : String = ""

    constructor()
    constructor(postid: String, postImage: String, Publisher: String, descripcion: String){
        this.postid = postid
        this.postimage = postImage
        this.publisher = descripcion
    }
    fun getPostid(): String{
        return postid
    }
    fun getPostImage(): String{
        return postimage
    }
    fun getPublisher(): String{
        return publisher
    }
    fun getDescription(): String{
        return description
    }
    fun setPostid(postid: String){
        this.postid = postid
    }
    fun setImage(postImage: String){
        this.postimage = postImage
    }
    fun setPublisher(publisher: String){
        this.publisher = publisher
    }
    fun setDescription(descripcion: String){
        this.description = descripcion
    }
}