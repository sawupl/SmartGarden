package com.example.smartgarden

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgarden.databinding.PlantItemBinding
import com.example.smartgarden.model.Plant
import com.squareup.picasso.Picasso

class PlantAdapter(private val plantsList: MutableList<Plant>): RecyclerView.Adapter<PlantAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: PlantItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PlantItemBinding.inflate(LayoutInflater.from(parent.context), parent,  false)
        return ViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = plantsList[position].name
        val soilType = plantsList[position].soilType
        val watering = plantsList[position].watering
        val picture = plantsList[position].picture
        holder.binding.apply {
            plantName.text = name
            plantSoilType.text = soilType
            wateringNumber.text = watering.toString()
            Picasso.get().load(picture).into(plantImage)
        }
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("plantId", plantsList[position].id)
            findNavController(it).navigate(R.id.plantInfoFragment , bundle)
        }
    }

    override fun getItemCount(): Int {
        return plantsList.size
    }

    fun add(plant: Plant) {
        var isInList = false
        for (i in plantsList) {
            if (i.name == plant.name) {
                isInList = true
                break
            }
        }
        if (!isInList) {
            plantsList.add(plant)
            notifyItemInserted(plantsList.size)
        }
    }
}