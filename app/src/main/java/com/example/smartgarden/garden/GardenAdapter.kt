package com.example.smartgarden.garden

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgarden.main.MainViewModel
import com.example.smartgarden.R
import com.example.smartgarden.databinding.GardenItemBinding
import com.example.smartgarden.model.Garden


class GardenAdapter(private val gardenList: MutableList<Garden>, private val viewModel: MainViewModel): RecyclerView.Adapter<GardenAdapter.ViewHolder>() {
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

        holder.binding.changeGarden.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("gardenId", gardenList[position].id)
            findNavController(it).navigate(R.id.action_mainFragment_to_editGardenFragment, bundle)
        }


        holder.binding.deleteGarden.setOnClickListener {
            val gardenId = gardenList[position].id
            removeItem(position)
            viewModel.deleteGarden(gardenId)
        }
    }

    override fun getItemCount(): Int {
        return gardenList.size
    }

    private fun removeItem(position: Int) {
        gardenList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, gardenList.size)
    }
}