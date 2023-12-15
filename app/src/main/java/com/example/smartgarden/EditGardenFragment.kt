package com.example.smartgarden

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.smartgarden.databinding.FragmentEditGardenBinding

class EditGardenFragment : Fragment() {
    private lateinit var binding: FragmentEditGardenBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentEditGardenBinding.inflate(inflater, container, false)
        return binding.root
    }
}