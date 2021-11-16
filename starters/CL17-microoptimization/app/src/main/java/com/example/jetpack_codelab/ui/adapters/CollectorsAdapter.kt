package com.example.jetpack_codelab.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpack_codelab.R
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.jetpack_codelab.databinding.CollectorItemBinding
import com.example.jetpack_codelab.models.Collector
import com.example.jetpack_codelab.ui.CollectorFragmentDirections


class CollectorsAdapter : RecyclerView.Adapter<CollectorsAdapter.CollectorViewHolder>(){

    var collectors :List<Collector> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectorViewHolder {
        val withDataBinding: CollectorItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                CollectorViewHolder.LAYOUT,
                parent,
                false)
        return CollectorViewHolder(withDataBinding)
    }

    override fun getItemCount(): Int {
        return collectors.size
    }

    override fun onBindViewHolder(holder: CollectorViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.collector = collectors[position]
        }
        holder.viewDataBinding.root.setOnClickListener {
            val action = CollectorFragmentDirections.actionCollectorFragment2ToAlbumFragment2(collectors[position].collectorId)
            // Navigate using that action
            holder.viewDataBinding.root.findNavController().navigate(action)
        }
    }

    class CollectorViewHolder(val viewDataBinding: CollectorItemBinding) :
            RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.collector_item
        }
    }
}