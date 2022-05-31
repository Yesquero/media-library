package ru.mirea.medlib.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import ru.mirea.medlib.R
import ru.mirea.medlib.databinding.MediaLibraryFragmentBinding
import ru.mirea.medlib.repository.MediaLibraryRepository
import ru.mirea.medlib.utility.MedLibConstants
import ru.mirea.medlib.view.adapter.MediaListAdapter
import ru.mirea.medlib.viewmodel.MediaListViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MediaListFragment : Fragment() {

    private var _binding: MediaLibraryFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var repository: MediaLibraryRepository

    @Inject
    lateinit var adapter: MediaListAdapter

    private val viewModel: MediaListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.media_library_fragment, container, false
        )
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mediaList.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.clickListener.onItemClick = {
            val bundle = Bundle()
            bundle.putParcelable(MedLibConstants.MEDIA_DETAILS_PARCEL_TAG, it)
            Navigation.findNavController(view)
                .navigate(R.id.action_mediaListFragment_to_pagerParentFragment, bundle)
        }

        binding.fabAddItem.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_mediaListFragment_to_searchfragment)
        }

        binding.testBtn.setOnClickListener {
            Toast.makeText(context, "Testing!", Toast.LENGTH_SHORT).show()
        }

        viewModel.mediaList.observe(viewLifecycleOwner) { it ->
            adapter.submitList(it)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}