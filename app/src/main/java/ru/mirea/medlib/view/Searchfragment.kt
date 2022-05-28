package ru.mirea.medlib.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.mirea.medlib.R
import ru.mirea.medlib.databinding.SearchMediaFragmentBinding
import ru.mirea.medlib.network.ResultWrapper
import ru.mirea.medlib.view.adapter.SearchAdapter
import ru.mirea.medlib.viewmodel.SearchViewModel
import javax.inject.Inject

@AndroidEntryPoint
class Searchfragment : Fragment() {

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
        binding.progressBar.visibility = View.GONE

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

        viewModel.searchResult.observe(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Success -> {
                    adapter.submitList(it.data!!.films)
                    binding.progressBar.visibility = View.GONE
                }
                is ResultWrapper.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                    binding.progressBar.visibility = View.GONE
                }
                is ResultWrapper.Loading -> {
                    // show progress
                    binding.progressBar.visibility = View.VISIBLE
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}