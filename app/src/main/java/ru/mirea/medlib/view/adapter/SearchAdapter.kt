package ru.mirea.medlib.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.scopes.FragmentScoped
import ru.mirea.medlib.databinding.SearchItemBinding
import ru.mirea.medlib.domain.FilmSearchResult
import javax.inject.Inject

@FragmentScoped
class SearchAdapter @Inject constructor(val clickListener: ClickListener) :
    ListAdapter<FilmSearchResult, SearchAdapter.ViewHolder>(RecipeListDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = SearchItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    inner class ViewHolder(private val binding: SearchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FilmSearchResult, clickListener: ClickListener) {
            binding.data = data
            binding.executePendingBindings()
            binding.clickListener = clickListener
        }
    }

    class ClickListener @Inject constructor() {

        var onItemClick: ((FilmSearchResult) -> Unit)? = null

        fun onClick(data: FilmSearchResult) {
            onItemClick?.invoke(data)
        }
    }

    class RecipeListDiffCallback : DiffUtil.ItemCallback<FilmSearchResult>() {

        override fun areItemsTheSame(
            oldItem: FilmSearchResult,
            newItem: FilmSearchResult
        ): Boolean {
            return oldItem.filmId == newItem.filmId
        }

        override fun areContentsTheSame(
            oldItem: FilmSearchResult,
            newItem: FilmSearchResult
        ): Boolean {
            return oldItem == newItem
        }

    }
}