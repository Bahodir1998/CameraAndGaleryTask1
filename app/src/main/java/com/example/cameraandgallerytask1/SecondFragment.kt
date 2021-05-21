package com.example.cameraandgallerytask1

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.cameraandgallerytask1.adapters.PagerRvAdapter
import com.example.cameraandgallerytask1.databinding.FragmentSecondBinding
import com.example.cameraandgallerytask1.db.MyDbHelper
import com.example.cameraandgallerytask1.models.Sign

class SecondFragment : Fragment() {

    lateinit var myDbHelper: MyDbHelper
    lateinit var list: ArrayList<Sign>
    lateinit var pagerRvAdapter: PagerRvAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSecondBinding.inflate(inflater)

        myDbHelper = MyDbHelper(requireContext())
        list = myDbHelper.getAllSignByLike(1)
        pagerRvAdapter = PagerRvAdapter(list,object : PagerRvAdapter.OnMyItemClickListener{
            override fun onMyItemClick(id: Int) {
                val bundle = Bundle()
                bundle.putInt("id",id)
                findNavController().navigate(R.id.fourFragment,bundle)
            }

            override fun onMyEditClick(id: Int) {
                val bundle = Bundle()
                bundle.putInt("id",id)
                findNavController().navigate(R.id.firthFragment,bundle)
            }

            override fun onMyDeleteClick(sign: Sign, position: Int) {
                myDbHelper.deleteSign(sign)
                list.remove(sign)
                pagerRvAdapter.notifyItemRemoved(position)
            }

            override fun onMyHeartClick(sign: Sign) {
                myDbHelper.updateSign(sign)
                list.remove(sign)
                pagerRvAdapter.notifyDataSetChanged()
            }

        })
        binding.rv.adapter = pagerRvAdapter

        binding.bnView.setOnNavigationItemSelectedListener {
            val itemId = it.itemId
            when(itemId){
                R.id.home -> {
                    val controller = Navigation.findNavController(binding.root)
                    controller.popBackStack(R.id.secondFragment, true)
                    controller.navigate(R.id.homeFragment)
                }
                R.id.heartt -> {

                }
                R.id.info -> {
                    val controller = Navigation.findNavController(binding.root)
                    controller.popBackStack(R.id.secondFragment, true)
                    controller.navigate(R.id.thirdFragment)
                }
            }
            true
        }
        return binding.root
    }

}