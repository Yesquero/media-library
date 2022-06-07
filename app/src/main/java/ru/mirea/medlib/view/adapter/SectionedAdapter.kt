package ru.mirea.medlib.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.mirea.medlib.databinding.EpisodeCardBinding
import ru.mirea.medlib.databinding.EpisodeListHeaderBinding
import ru.mirea.medlib.domain.EpisodeDetails
import javax.inject.Inject

private const val ITEM_VIEW_TYPE_HEADER = -1
private const val ITEM_VIEW_TYPE_ITEM = -2

@FragmentScoped
class SectionedAdapter @Inject constructor() :
    ListAdapter<DataItem, RecyclerView.ViewHolder>(EpisodeListDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    class HeaderViewHolder(private val binding: EpisodeListHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(episodeNum: Long) {
            binding.seasonNumber = episodeNum
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): HeaderViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EpisodeListHeaderBinding.inflate(
                    layoutInflater, parent, false
                )
                return HeaderViewHolder(binding)
            }
        }
    }

    class EpisodeViewHolder(private val binding: EpisodeCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: EpisodeDetails) {
            binding.data = data
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): EpisodeViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = EpisodeCardBinding.inflate(
                    layoutInflater, parent, false
                )
                return EpisodeViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.EpisodeItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> EpisodeViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is EpisodeViewHolder -> {
                val episodeItem = getItem(position) as DataItem.EpisodeItem
                holder.bind(episodeItem.episodeDetails)
            }
            is HeaderViewHolder -> {
                val headerItem = getItem(position) as DataItem.Header
                holder.bind(headerItem.seasonNum)
            }
        }
    }

    fun addHeaderAndSubmitList(list: List<EpisodeDetails>) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header(0))
                else -> {
                    val mappedList = list.groupBy { it.seasonNumber }
                    val groupedList = ArrayList<DataItem>()

                    for (key in mappedList.keys) {
                        groupedList.add(DataItem.Header(key))
                        for (value in mappedList.getValue(key)) {
                            groupedList.add(DataItem.EpisodeItem(value))
                        }
                    }

                    Log.i(
                        "SectionedAdapter",
                        "Keys: ${mappedList.keys.size}; values: ${mappedList.values.size}"
                    )

                    groupedList
                }
            }
            withContext(Dispatchers.Main) {
                Log.i("SectionedAdapter", "items size ${items.size}")
                Log.i("SectionedAdapter", items.toString())
                submitList(items)
            }
        }
    }
}

sealed class DataItem {
    data class EpisodeItem(val episodeDetails: EpisodeDetails) : DataItem() {
        override val id = episodeDetails.episodeId
    }

    data class Header(val seasonNum: Long) : DataItem() {
        override val id = Long.MIN_VALUE + seasonNum
    }

    abstract val id: Long
}

class EpisodeListDiffCallback : DiffUtil.ItemCallback<DataItem>() {

    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }

}

