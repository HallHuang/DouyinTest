package com.hfad.douyintest.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.hfad.douyintest.databinding.FragmentHomeBinding
import com.hfad.douyintest.home.HomeViewModel
import com.hfad.douyintest.home.adapter.HomeFragmentAdapter
import com.hfad.douyintest.home.fragments.HomeMainFragment
import com.hfad.douyintest.home.fragments.UserCenterFragment

class HomeFragment : Fragment() {

    private lateinit var fragmentHomeBinding : FragmentHomeBinding
    private lateinit var vpHome : ViewPager

    private val fragments : List<Fragment> by lazy {
        ArrayList<Fragment>().apply {
            this.add(HomeMainFragment())
            this.add(UserCenterFragment())
        }
    }

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        vpHome = fragmentHomeBinding.vpHomeFragment
        return fragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel = ViewModelProvider(activity!!).get(HomeViewModel::class.java)

        vpHome.adapter = HomeFragmentAdapter(childFragmentManager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, fragments)

        //用户界面/视频主界面 切换
        homeViewModel.isBackToHome.observe(viewLifecycleOwner, {
            if (it) vpHome.currentItem = 0 else vpHome.currentItem = 1
        })

    }


}