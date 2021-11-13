package com.example.vinyls_jetpack_application.models

import kotlinx.serialization.Serializable

@Serializable
data class Comment  (
    val description:String,
    val rating:String,
    val albumId:Int
)