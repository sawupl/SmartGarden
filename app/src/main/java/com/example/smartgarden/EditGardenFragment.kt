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
    private lateinit var adapter: PlantAdapter
    private var plantsList = mutableListOf<Plant>()
    private var gardenId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory())[EditGardenViewModel::class.java]
        adapter = PlantAdapter(mutableListOf(),viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditGardenBinding.inflate(inflater, container, false)
        gardenId = arguments?.getString("gardenId")

        viewModel.getGarden(gardenId.toString())


        viewModel.plantsStringLiveData.observe(viewLifecycleOwner){
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line, it
            )
            plants = it.toMutableList()
            binding.plant.setAdapter(adapter)
        }

        viewModel.gardenLivaData.observe(viewLifecycleOwner){
            binding.city.setText(it.city)
            binding.gardenName.setText(it.name)
        }


        viewModel.plantListLivaData.observe(viewLifecycleOwner){
            val adapter = PlantAdapter(it,viewModel)
            plantsList = it
            binding.plantsList.adapter = adapter
            binding.plantsList.layoutManager = LinearLayoutManager(context)
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
                viewModel.saveGarden(city, name, plantsList,gardenId)
                findNavController().popBackStack()
            }
            else {
                println("emptuyyyy")
            }
        }


        return binding.root
    }
}