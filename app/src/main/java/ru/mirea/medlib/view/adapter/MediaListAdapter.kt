package ru.mirea.medlib.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.scopes.FragmentScoped
import ru.mirea.medlib.databinding.MediaItemBinding
import ru.mirea.medlib.domain.MediaDetails
import javax.inject.Inject


@FragmentScoped
class MediaListAdapter @Inject constructor(val clickListener: ClickListener) :
    ListAdapter<MediaDetails, MediaListAdapter.ViewHolder>(RecipeListDiffCallback()), Filterable {

    private var localList = mutableListOf<MediaDetails>()

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
        fun bind(data: MediaDetails, clickListener: ClickListener) {
            binding.data = data
            binding.executePendingBindings()
            binding.clickListener = clickListener
        }
    }

    fun setData(list: MutableList<MediaDetails>) {
        this.localList = list
        submitList(list)
    }

    class ClickListener @Inject constructor() {

        var onItemClick: ((MediaDetails) -> Unit)? = null

        fun onClick(data: MediaDetails) {
            onItemClick?.invoke(data)
        }
    }

    class RecipeListDiffCallback : DiffUtil.ItemCallback<MediaDetails>() {

        override fun areItemsTheSame(oldItem: MediaDetails, newItem: MediaDetails): Boolean {
            return oldItem.kinopoiskId == newItem.kinopoiskId
        }

        override fun areContentsTheSame(oldItem: MediaDetails, newItem: MediaDetails): Boolean {
            return oldItem == newItem
        }

    }

    override fun getFilter(): Filter {
        return customFilter
    }

    private val customFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<MediaDetails>()
            if (constraint.isNullOrEmpty()) {
                filteredList.addAll(localList)
            } else {
                for (item in localList) {
                    if (item.getFilterString().contains(constraint.toString().lowercase())) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
            submitList(filterResults?.values as MutableList<MediaDetails>?)
        }

    }
}