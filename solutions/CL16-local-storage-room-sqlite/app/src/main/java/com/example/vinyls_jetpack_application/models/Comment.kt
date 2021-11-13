package com.example.vinyls_jetpack_application.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "comments_table")
data class Comment  (
    val description:String,
    val rating:String,
    val albumId:Int,
    @PrimaryKey(autoGenerate = true)
    val commentId:Int = 0
)