package ru.mirea.medlib.view

import android.os.Bundle
import android.util.Log
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
import ru.mirea.medlib.databinding.AddMediaFragmentBinding
import ru.mirea.medlib.network.ResultWrapper
import ru.mirea.medlib.utility.MedLibConstants
import ru.mirea.medlib.viewmodel.AddMediaViewModel

@AndroidEntryPoint
class AddMediaFragment : Fragment() {

    private var _binding: AddMediaFragmentBinding? = null
    val binding get() = _binding!!

    private val viewModel: AddMediaViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.add_media_fragment, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.saveBtn.visibility = View.GONE

        // initialize
        val receivedId: Long = requireArguments().getLong(MedLibConstants.MEDIA_TO_ADD_ID_TAG)
        Log.i("AddMediaFragment", "Received ID: $receivedId")
        viewModel.getMediaDetails(receivedId)

        // set buttons and stuff
        binding.saveBtn.setOnClickListener {
            viewModel.saveMedia()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.detailsDtoResult.observe(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                }
                is ResultWrapper.Error -> {
                    // TODO: navigate back ?
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                is ResultWrapper.Success -> {
                    binding.data = it.data
                    binding.saveBtn.visibility = View.VISIBLE
                }
            }
        }

        viewModel.addMediaResult.observe(viewLifecycleOwner) {
            when (it) {
                is ResultWrapper.Loading -> {
                }
                is ResultWrapper.Error -> {
                    // TODO: navigate back ?
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                is ResultWrapper.Success -> {
                    // TODO: navigate back ?
                    Toast.makeText(context, R.string.save_success, Toast.LENGTH_SHORT).show()
                    binding.saveBtn.visibility = View.VISIBLE
                    Navigation.findNavController(view).popBackStack()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}