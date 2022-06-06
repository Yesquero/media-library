package ru.mirea.medlib.view.pagerTabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ru.mirea.medlib.R
import ru.mirea.medlib.databinding.PagerStaffFragmentBinding
import ru.mirea.medlib.viewmodel.PagerViewModel

@AndroidEntryPoint
class PagerStaffFragment : Fragment() {

    private val sharedViewModel by viewModels<PagerViewModel>({ requireParentFragment() })

    private var _binding: PagerStaffFragmentBinding? = null
    val binding get() = _binding!!

    companion object {
        fun create(): PagerStaffFragment {
            return PagerStaffFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.pager_staff_fragment, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = sharedViewModel

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}