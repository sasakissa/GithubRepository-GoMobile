package com.sasakissa.githubsample.search

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sasakissa.githubsample.R
import com.sasakissa.githubsample.databinding.ListItemBinding
import com.sasakissa.githubsample.entity.Repo

class SearchRepoAdapter : ListAdapter<Repo, SearchRepoAdapter.ViewHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo: Repo = getItem(position)
        holder.binding.repo = repo
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ListItemBinding.bind(itemView)
    }

    class DiffCallback : DiffUtil.ItemCallback<Repo>() {
        override fun areItemsTheSame(oldItem: Repo, newItem: Repo) = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Repo, newItem: Repo) = oldItem == newItem
    }
}