package ru.mirea.medlib.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.scopes.FragmentScoped
import ru.mirea.medlib.databinding.MediaItemBinding
import ru.mirea.medlib.network.dto.FilmSearchDto
import javax.inject.Inject


@FragmentScoped
class MediaListAdapter @Inject constructor(val clickListener: ClickListener) :
    ListAdapter<FilmSearchDto, MediaListAdapter.ViewHolder>(RecipeListDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MediaItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    inner class ViewHolder(private val binding: MediaItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FilmSearchDto, clickListener: ClickListener) {
            binding.data = data
            binding.executePendingBindings()
            binding.clickListener = clickListener
        }
    }

    class ClickListener @Inject constructor() {

        var onItemClick: ((FilmSearchDto) -> Unit)? = null

        fun onClick(data: FilmSearchDto) {
            onItemClick?.invoke(data)
        }
    }

    class RecipeListDiffCallback : DiffUtil.ItemCallback<FilmSearchDto>() {

        override fun areItemsTheSame(oldItem: FilmSearchDto, newItem: FilmSearchDto): Boolean {
            return oldItem.filmId == newItem.filmId
        }

        override fun areContentsTheSame(oldItem: FilmSearchDto, newItem: FilmSearchDto): Boolean {
            return oldItem == newItem
        }

    }
}