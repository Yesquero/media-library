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
        binding.loadDetailsBar.visibility = View.GONE
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
                    binding.loadDetailsBar.visibility = View.VISIBLE
                }
                is ResultWrapper.Error -> {
                    // TODO: navigate back ?
                    binding.loadDetailsBar.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
                is ResultWrapper.Success -> {
                    binding.data = it.data
                    binding.loadDetailsBar.visibility = View.GONE
                    binding.saveBtn.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}