package com.example.smartgarden

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartgarden.databinding.FragmentEditGardenBinding
import com.example.smartgarden.databinding.FragmentPlantBinding
import com.example.smartgarden.model.Plant

class PlantFragment : Fragment() {
    private lateinit var binding: FragmentPlantBinding
    private lateinit var viewModel: PlantViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory())[PlantViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlantBinding.inflate(inflater,container,false)

        viewModel.plantLiveData.observe(viewLifecycleOwner){
            val adapter = PlantAdapter(it)
            binding.plantsRecyclerView.adapter = adapter
            binding.plantsRecyclerView.layoutManager = LinearLayoutManager(context)
        }

        return binding.root
    }
}