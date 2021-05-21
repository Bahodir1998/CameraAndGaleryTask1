package com.example.cameraandgallerytask1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.cameraandgallerytask1.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentThirdBinding.inflate(inflater)

        binding.bnView.setOnNavigationItemSelectedListener {
            val itemId = it.itemId
            when(itemId){
                R.id.home -> {
                    val controller = Navigation.findNavController(binding.root)
                    controller.popBackStack(R.id.thirdFragment, true)
                    controller.navigate(R.id.homeFragment)
                }
                R.id.heartt -> {
                    val controller = Navigation.findNavController(binding.root)
                    controller.popBackStack(R.id.thirdFragment, true)
                    controller.navigate(R.id.secondFragment)
                }
                R.id.info -> {

                }
            }
            true
        }
        return binding.root
    }

}