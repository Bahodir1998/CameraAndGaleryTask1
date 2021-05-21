package com.example.cameraandgallerytask1

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cameraandgallerytask1.databinding.FragmentFourBinding
import com.example.cameraandgallerytask1.db.MyDbHelper


class FourFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentFourBinding.inflate(inflater)

        val id = arguments?.getInt("id")
        val myDbHelper = MyDbHelper(requireContext())
        val sign = myDbHelper.getSignById(id!!)

        binding.imageView.setImageURI(Uri.parse(sign.imagePath))
        binding.nameTv.text = sign.name
        binding.contentTv.text = sign.content
        return binding.root
    }

}