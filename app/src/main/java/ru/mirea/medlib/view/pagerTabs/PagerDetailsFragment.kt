package ru.mirea.medlib.view.pagerTabs

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import ru.mirea.medlib.R
import ru.mirea.medlib.databinding.PagerDetailsFragmentBinding
import ru.mirea.medlib.viewmodel.PagerViewModel

@AndroidEntryPoint
class PagerDetailsFragment : Fragment() {

    private val sharedViewModel by viewModels<PagerViewModel>({ requireParentFragment() })

    private var _binding: PagerDetailsFragmentBinding? = null
    val binding get() = _binding!!

    companion object {
        fun create(): PagerDetailsFragment {
            return PagerDetailsFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.pager_details_fragment, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = sharedViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}