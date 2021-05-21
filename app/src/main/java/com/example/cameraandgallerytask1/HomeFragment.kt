package com.example.cameraandgallerytask1

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.cameraandgallerytask1.adapters.PagerAdapter
import com.example.cameraandgallerytask1.databinding.FragmentHomeBinding
import com.example.cameraandgallerytask1.databinding.TabItemBinding
import com.example.cameraandgallerytask1.db.MyDbHelper
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    lateinit var binding: FragmentHomeBinding
    lateinit var pagerAdapter: PagerAdapter
    lateinit var myDbHelper: MyDbHelper
    lateinit var tabTitlList: List<String>

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)

        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        (requireActivity() as AppCompatActivity).supportActionBar?.elevation = 0.0F
        (requireActivity() as AppCompatActivity).supportActionBar?.setTitle("Yo'l belgilari")
        setHasOptionsMenu(true)
        tabTitlList = listOf("Ogohlantiruvchi", "Imtiyozli", "Taqiqlovchi", "Buyuruvchi")

        binding.bnView.setOnNavigationItemSelectedListener {
            val itemId = it.itemId
            when(itemId){
                R.id.home -> {

                }
                R.id.heartt -> {
                    val controller = Navigation.findNavController(binding.root)
                    controller.popBackStack(R.id.homeFragment, true)
                    controller.navigate(R.id.secondFragment)
                }
                R.id.info -> {
                    val controller = Navigation.findNavController(binding.root)
                    controller.popBackStack(R.id.homeFragment, true)
                    controller.navigate(R.id.thirdFragment)
                }
            }
            true
        }

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
    }

    @SuppressLint("ResourceAsColor")
    override fun onResume() {
        super.onResume()
        pagerAdapter = PagerAdapter(requireActivity())
        binding.viewPager.adapter = pagerAdapter
        TabLayoutMediator(binding.tabL, binding.viewPager) { tab, position ->
            val tabSelectItemBinding =
                TabItemBinding.inflate(LayoutInflater.from(binding.root.context), null, false)
            tabSelectItemBinding.tvUnselected.text = tabTitlList[position]
            tab.customView = tabSelectItemBinding.root
            if (position == 0) {
                val customView = tab?.customView
                val bind1 = TabItemBinding.bind(customView!!)
                bind1.tvUnselected.setTextColor(Color.parseColor("#005CA1"))
            }
            binding.viewPager.setCurrentItem(tab.position, true)
        }.attach()
        binding.tabL.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val customView = tab?.customView
                val bind1 = TabItemBinding.bind(customView!!)
                bind1.tvUnselected.setTextColor(Color.parseColor("#005CA1"))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val customView = tab?.customView
                val bind1 = TabItemBinding.bind(customView!!)
                bind1.tvUnselected.setTextColor(Color.parseColor("#FFFFFF"))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })


    }

    override fun onPause() {
        super.onPause()
        pagerAdapter = PagerAdapter(requireActivity())
        binding.viewPager.adapter = pagerAdapter
    }

}