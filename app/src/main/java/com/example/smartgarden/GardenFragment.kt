package com.example.smartgarden

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.smartgarden.databinding.FragmentGardenBinding
import com.example.smartgarden.databinding.FragmentMainBinding

class GardenFragment : Fragment() {
    private lateinit var binding: FragmentGardenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGardenBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
}