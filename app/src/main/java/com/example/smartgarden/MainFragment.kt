package com.example.smartgarden

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartgarden.databinding.FragmentMainBinding
import com.example.smartgarden.model.Garden

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var viewModel: MainViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this, ViewModelFactory())[MainViewModel::class.java]

        viewModel.gardenLiveData.observe(viewLifecycleOwner){
            val adapter = GardenAdapter(it, viewModel)
            binding.gardensRecycler.adapter = adapter
            binding.gardensRecycler.layoutManager = LinearLayoutManager(context)
        }


        binding.createGarden.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_editGardenFragment)
        }

        binding.toPlantList.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_plantFragment)
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.getGarden()
    }
}