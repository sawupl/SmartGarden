package com.example.smartgarden

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartgarden.databinding.FragmentGardenBinding

class GardenFragment : Fragment() {
    private lateinit var binding: FragmentGardenBinding
    private lateinit var viewModel: GardenViewModel
    private var gardenId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory())[GardenViewModel::class.java]
        gardenId = arguments?.getString("gardenId")
        viewModel.getGarden(gardenId.toString())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGardenBinding.inflate(layoutInflater, container, false)

        viewModel.plantListLivaData.observe(viewLifecycleOwner) {
                val adapter = PlantAdapter(it)
                binding.plantsRecyclerView.adapter = adapter
                binding.plantsRecyclerView.layoutManager = LinearLayoutManager(context)
        }

        return binding.root
    }
}