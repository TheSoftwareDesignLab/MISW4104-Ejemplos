package com.example.jetpack_codelab.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpack_codelab.R
import androidx.databinding.DataBindingUtil
import com.example.jetpack_codelab.databinding.CommentItemBinding
import com.example.jetpack_codelab.models.Comment


class CommentsAdapter : RecyclerView.Adapter<CommentsAdapter.CommentViewHolder>(){

    var comments :List<Comment> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val withDataBinding: CommentItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                CommentViewHolder.LAYOUT,
                parent,
                false)
        return CommentViewHolder(withDataBinding)
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.comment = comments[position]
        }
    }

    class CommentViewHolder(val viewDataBinding: CommentItemBinding) :
            RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.comment_item
        }
    }
}