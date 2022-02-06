package com.example.githubapimvvm.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapimvvm.databinding.RecyclerviewCellBinding
import com.example.githubapimvvm.domain.model.GitHubModel

class RecyclerViewAdapter (
    var list: List<GitHubModel>,
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(binding: RecyclerviewCellBinding): RecyclerView.ViewHolder(binding.root) {
        val binding = binding
        fun setup(item: GitHubModel) {
            binding.data = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RecyclerviewCellBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.setup(list[position])
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun reload(list: List<GitHubModel>) {
        this.list = list
        notifyDataSetChanged()
    }

}