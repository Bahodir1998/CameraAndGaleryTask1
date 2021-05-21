package com.example.cameraandgallerytask1

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.example.cameraandgallerytask1.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    lateinit var binding: FragmentStartBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(inflater)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        val handler = Handler()

        handler.postDelayed(runnable, 2000)
        return binding.root
    }

    var runnable = object : Runnable {
        override fun run() {
            val controller = Navigation.findNavController(binding.root)
            controller.popBackStack(R.id.startFragment, true)
            controller.navigate(R.id.homeFragment)
//            findNavController().navigate(R.id.firthFragment)
        }

    }
}