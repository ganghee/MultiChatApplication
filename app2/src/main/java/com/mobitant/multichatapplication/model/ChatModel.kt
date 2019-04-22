package com.mobitant.multichatapplication.model

class ChatModel{

    var users : Map<String,Boolean>  = HashMap<String,Boolean>()// 채팅방의 유저들
    var comments : Map<String, Comment>  = HashMap<String,Comment>()// 채팅방의 대화내용

    class Comment {
        var uid: String? = null
        var message: String? = null
    }
}