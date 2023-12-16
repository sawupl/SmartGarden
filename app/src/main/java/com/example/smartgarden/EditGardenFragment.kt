package com.example.smartgarden

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartgarden.databinding.FragmentEditGardenBinding
import com.example.smartgarden.model.Plant

class EditGardenFragment : Fragment() {
    private lateinit var binding: FragmentEditGardenBinding
    private lateinit var viewModel: EditGardenViewModel
    private var plants = mutableListOf<String>()
    private val adapter = PlantAdapter(mutableListOf())
    private val plantsList = mutableListOf<Plant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory())[EditGardenViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditGardenBinding.inflate(inflater, container, false)

        binding.plantsList.adapter = adapter
        binding.plantsList.layoutManager = LinearLayoutManager(context)

        viewModel.plantsStringLiveData.observe(viewLifecycleOwner){
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line, it
            )
            plants = it.toMutableList()
            binding.plant.setAdapter(adapter)
        }

        binding.addPlant.setOnClickListener {
            val name = binding.plant.text.toString()
            if (name.isNotEmpty()) {
                val plant = viewModel.addPlant(name)
                if (plant != null) {
                    adapter.add(plant)
                    plantsList.add(plant)
                }
                binding.plant.setText("")
            }
        }

        binding.backToMenu.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.addGarden.setOnClickListener {
            val city = binding.city.text.toString()
            val name = binding.gardenName.text.toString()
            if (city.isNotEmpty() && name.isNotEmpty() && plantsList.isNotEmpty()) {
                viewModel.saveGarden(city, name, plantsList)
                findNavController().popBackStack()
            }
            else {
                println("emptuyyyy")
            }
        }


        return binding.root
    }
}