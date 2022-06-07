package ru.mirea.medlib.view.pagerTabs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.mirea.medlib.R
import ru.mirea.medlib.databinding.PagerEpisodesFragmentBinding
import ru.mirea.medlib.view.adapter.SectionedAdapter
import ru.mirea.medlib.viewmodel.PagerViewModel
import javax.inject.Inject

@AndroidEntryPoint
class PagerEpisodesFragment : Fragment() {

    private val sharedViewModel by viewModels<PagerViewModel>({ requireParentFragment() })

    private var _binding: PagerEpisodesFragmentBinding? = null
    val binding get() = _binding!!

    @Inject
    lateinit var adapter: SectionedAdapter

    companion object {
        fun create(): PagerEpisodesFragment {
            return PagerEpisodesFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.pager_episodes_fragment, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = sharedViewModel
        binding.episodeList.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        sharedViewModel.mediaItem.value?.let {
//            Log.i("PagerEpisodeAdapter", "Refresh adapter with ${it.episodes.size}")
//            adapter.addHeaderAndSubmitList(it.episodes)
//        }

        sharedViewModel.mediaItem.observe(viewLifecycleOwner) {
            Log.i("PagerEpisodeAdapter", "Observer media with ${it.episodes.size} episodes.")
            adapter.addHeaderAndSubmitList(it.episodes)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}