package com.example.smartgarden

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.smartgarden.databinding.FragmentEditGardenBinding

class EditGardenFragment : Fragment() {
    private lateinit var binding: FragmentEditGardenBinding
    private lateinit var viewModel: EditGardenViewModel
    private var plants = mutableListOf<String>()

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

        viewModel.plantLiveData.observe(viewLifecycleOwner){
            val adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line, it
            )
            plants = it.toMutableList()
            binding.plant.setAdapter(adapter)
        }


        return binding.root
    }
}