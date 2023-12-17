package com.example.smartgarden

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartgarden.databinding.FragmentPlantBinding

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
            val adapter = PlantAdapter(it,viewModel)
            binding.plantsRecyclerView.adapter = adapter
            binding.plantsRecyclerView.layoutManager = LinearLayoutManager(context)
        }

        return binding.root
    }
}