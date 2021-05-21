package com.example.cameraandgallerytask1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cameraandgallerytask1.adapters.PagerRvAdapter
import com.example.cameraandgallerytask1.databinding.FragmentHomeBinding
import com.example.cameraandgallerytask1.databinding.FragmentPagerBinding
import com.example.cameraandgallerytask1.databinding.TabItemBinding
import com.example.cameraandgallerytask1.db.MyDbHelper
import com.example.cameraandgallerytask1.models.Sign
import com.google.android.material.tabs.TabLayoutMediator

private const val ARG_PARAM1 = "param1"


class PagerFragment : Fragment() {

    private var param1: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
        }
    }

    lateinit var myDbHelper: MyDbHelper
    lateinit var binding: FragmentPagerBinding
    lateinit var pagerRvAdapter: PagerRvAdapter
    lateinit var listByType: ArrayList<Sign>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPagerBinding.inflate(inflater)

        setHasOptionsMenu(true)
        myDbHelper = MyDbHelper(binding.root.context)
        listByType = myDbHelper.getAllSignByType(param1!!)
        pagerRvAdapter = PagerRvAdapter(listByType,object : PagerRvAdapter.OnMyItemClickListener{
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
                listByType.remove(sign)
                pagerRvAdapter.notifyItemRemoved(position)
            }

            override fun onMyHeartClick(sign: Sign) {
                myDbHelper.updateSign(sign)
            }

        })
        binding.rv.adapter = pagerRvAdapter

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findNavController().navigate(R.id.firthFragment)
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()

    }
    companion object {

        @JvmStatic
        fun newInstance(param1: Int) =
            PagerFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                }
            }
    }
}