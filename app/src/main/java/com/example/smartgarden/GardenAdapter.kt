package com.example.smartgarden

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgarden.databinding.GardenItemBinding
import com.example.smartgarden.model.Garden


class GardenAdapter(private val gardenList: List<Garden>): RecyclerView.Adapter<GardenAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: GardenItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GardenItemBinding.inflate(LayoutInflater.from(parent.context), parent,  false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = gardenList[position].name
        holder.binding.apply {
            gardenName.text = name
        }

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("gardenId", gardenList[position].id)
            findNavController(it).navigate(R.id.action_mainFragment_to_gardenFragment, bundle)
        }

    }

    override fun getItemCount(): Int {
        return gardenList.size
    }
}