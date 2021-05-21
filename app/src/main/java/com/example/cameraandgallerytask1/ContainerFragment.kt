package com.example.cameraandgallerytask1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.cameraandgallerytask1.databinding.FragmentContainerBinding


class ContainerFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentContainerBinding.inflate(inflater)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        (requireActivity() as AppCompatActivity).supportActionBar?.elevation = 0.0F
        (requireActivity() as AppCompatActivity).supportActionBar?.setTitle("Yo'l belgilari")
        binding.bnView.setOnNavigationItemSelectedListener {
            val itemId = it.itemId
            when(itemId){
                R.id.home -> {
                    childFragmentManager.beginTransaction().replace(R.id.container,HomeFragment()).commit()
                }
                R.id.heartt -> {
                    childFragmentManager.beginTransaction().replace(R.id.container,SecondFragment()).commit()
                }
                R.id.info -> {
                    childFragmentManager.beginTransaction().replace(R.id.container,ThirdFragment()).commit()
                }
            }
            true
        }
        return binding.root
    }

}