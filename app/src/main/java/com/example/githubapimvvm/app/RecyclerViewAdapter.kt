package com.example.githubapimvvm.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapimvvm.databinding.RecyclerviewCellBinding
import com.example.githubapimvvm.domain.model.GitHubModel

class RecyclerViewAdapter (
    val list: List<GitHubModel>,
    ): RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(binding: RecyclerviewCellBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerviewCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.data = list[position]
    }

    override fun getItemCount(): Int {
        return list.size
    }

}