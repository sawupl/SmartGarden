package com.example.smartgarden.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartgarden.plant.PlantAdapter
import com.example.smartgarden.ViewModelFactory
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

        binding.plantsList.adapter = adapter
        binding.plantsList.layoutManager = LinearLayoutManager(context)

        gardenId = arguments?.getString("gardenId")
        if (gardenId != null) {
            viewModel.getGarden(gardenId.toString())
        }
        else {
            binding.title.text = "Создание грядки"
        }

        viewModel.plantsStringLiveData.observe(viewLifecycleOwner){
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line, it
            )
            plants = it.toMutableList()
            binding.plant.setAdapter(adapter)
        }

        viewModel.gardenLivaData.observe(viewLifecycleOwner){
            it?.let {
                binding.city.setText(it.city)
                binding.gardenName.setText(it.name)
            }
        }


        viewModel.plantListLivaData.observe(viewLifecycleOwner){
            it?.let {
                plantsList = it
                adapter.setList(plantsList)
            }
        }

        binding.addPlant.setOnClickListener {
            val name = binding.plant.text.toString()
            if (name.isNotEmpty()) {
                viewModel.addPlant(name)
                binding.plant.setText("")
            }
        }

        binding.back.setOnClickListener {
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
                Toast.makeText(requireContext(), "Заполните все поля!", Toast.LENGTH_SHORT).show()
            }
        }


        return binding.root
    }
}