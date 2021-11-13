package com.example.vinyls_jetpack_application.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vinyls_jetpack_application.models.Comment

@Dao
interface CommentsDao {
    @Query("SELECT * FROM comments_table WHERE albumId = :albumId ORDER BY rating DESC")
    fun getComments(albumId:Int):List<Comment>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(comment: Comment)

    @Query("DELETE FROM comments_table")
    suspend fun clear():Void

    @Query("DELETE FROM comments_table WHERE albumId = :albumId")
    suspend fun deleteAll(albumId: Int): Int
}