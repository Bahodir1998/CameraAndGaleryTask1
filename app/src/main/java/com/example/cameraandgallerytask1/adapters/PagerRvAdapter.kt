package com.example.cameraandgallerytask1.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cameraandgallerytask1.R
import com.example.cameraandgallerytask1.databinding.SignItemBinding
import com.example.cameraandgallerytask1.db.MyDbHelper
import com.example.cameraandgallerytask1.models.Sign

class PagerRvAdapter(var list: List<Sign>,var onMyItemClickListener: OnMyItemClickListener) : RecyclerView.Adapter<PagerRvAdapter.Vh>() {

    inner class Vh(var signItemBinding: SignItemBinding) :
        RecyclerView.ViewHolder(signItemBinding.root) {

        fun onBind(sign: Sign) {
            signItemBinding.tv.text = sign.name
            signItemBinding.imageV.setImageURI(Uri.parse(sign.imagePath))
            if (sign.like==0){
                signItemBinding.heart.setImageResource(R.drawable.heart_white)
            }else{
                signItemBinding.heart.setImageResource(R.drawable.heart_red)
            }
            signItemBinding.root.setOnClickListener {
                onMyItemClickListener.onMyItemClick(sign.id!!)
            }
            signItemBinding.edit.setOnClickListener {
                onMyItemClickListener.onMyEditClick(sign.id!!)
            }
            signItemBinding.delete.setOnClickListener {
                onMyItemClickListener.onMyDeleteClick(sign,adapterPosition)
            }
            signItemBinding.heart.setOnClickListener {
                if (sign.like==0){
                    signItemBinding.heart.setImageResource(R.drawable.heart_red)
                    sign.like = 1
                }else{
                    signItemBinding.heart.setImageResource(R.drawable.heart_white)
                    sign.like = 0
                }
                onMyItemClickListener.onMyHeartClick(sign)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(SignItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnMyItemClickListener {
        fun onMyItemClick(id: Int)
        fun onMyEditClick(id: Int)
        fun onMyDeleteClick(sign: Sign,position: Int)
        fun onMyHeartClick(sign: Sign)
    }
}