package ru.mirea.medlib.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ru.mirea.medlib.R
import ru.mirea.medlib.databinding.MediaPagerFragmentBinding
import ru.mirea.medlib.utility.MedLibConstants
import ru.mirea.medlib.viewmodel.PagerViewModel

@AndroidEntryPoint
class PagerParentFragment : Fragment() {

    private val viewModel: PagerViewModel by viewModels()

    private var _binding: MediaPagerFragmentBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.media_pager_fragment, container, false
        )

        viewModel.setMediaEntity(requireArguments().getParcelable(MedLibConstants.MEDIA_DETAILS_PARCEL_TAG)!!)
        setupPageView()

        return binding.root
    }

    private fun setupPageView() {
        val fragmentList = mutableListOf<Fragment>(
            PagerDetailsFragment.create(),
            PagerStaffFragment.create()
        )

        if (viewModel.mediaItem.value!!.serial) {
            fragmentList.add(PagerEpisodesFragment.create())
        }

        val adapter = MediaPagerAdapter(this, fragmentList)
        binding.pager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.pager) { tab, position ->
            when (position) {
                0 -> tab.text = "Описание"
                1 -> tab.text = "Состав"
                2 -> tab.text = "Эпизоды"
            }
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

class MediaPagerAdapter(
    parentFragment: Fragment,
    private val fragmentList: List<Fragment>
) : FragmentStateAdapter(parentFragment) {

    override fun getItemCount(): Int {
        return fragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position]
    }

}