package com.example.smartgarden

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.smartgarden.databinding.PlantItemBinding
import com.example.smartgarden.model.Plant
import com.squareup.picasso.Picasso

class PlantAdapter(private val plantsList: MutableList<Plant>, private val viewModel: ViewModel): RecyclerView.Adapter<PlantAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: PlantItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PlantItemBinding.inflate(LayoutInflater.from(parent.context), parent,  false)
        if (viewModel is PlantViewModel){
            binding.removePlant.visibility = View.INVISIBLE
        }

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
        holder.binding.removePlant.setOnClickListener {
            removeItem(position)
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

    private fun removeItem(position: Int) {
        plantsList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, plantsList.size)
    }
}