package ru.mirea.medlib.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import ru.mirea.medlib.R
import ru.mirea.medlib.databinding.SearchMediaFragmentBinding
import ru.mirea.medlib.network.ResultWrapper
import ru.mirea.medlib.network.dto.asDomainModel
import ru.mirea.medlib.utility.MedLibConstants
import ru.mirea.medlib.view.adapter.SearchAdapter
import ru.mirea.medlib.viewmodel.SearchViewModel
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: SearchMediaFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var adapter: SearchAdapter

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.search_media_fragment, container, false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.searchList.adapter = adapter

        binding.mediaSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.getSearchResults(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.clickListener.onItemClick = {
            val bundle = bundleOf(MedLibConstants.MEDIA_TO_ADD_ID_TAG to it.filmId)
            Navigation.findNavController(view)
                .navigate(R.id.action_searchfragment_to_addMediaFragment, bundle)
        }

        viewModel.searchResult.observe(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Success -> {
                    adapter.submitList(it.data?.films?.asDomainModel())
                }
                is ResultWrapper.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is ResultWrapper.Loading -> {
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}