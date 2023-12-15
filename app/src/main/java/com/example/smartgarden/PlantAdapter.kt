package com.example.smartgarden

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgarden.databinding.PlantItemBinding
import com.example.smartgarden.model.Plant

class PlantAdapter(private val plantsList: List<Plant>): RecyclerView.Adapter<PlantAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: PlantItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PlantItemBinding.inflate(LayoutInflater.from(parent.context), parent,  false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = plantsList[position].name
        holder.binding.apply {
//            plantName.text = name
        }

    }

    override fun getItemCount(): Int {
        return plantsList.size
    }
}