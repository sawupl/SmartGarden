package com.example.smartgarden

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartgarden.databinding.FragmentMainBinding
import com.example.smartgarden.model.Garden

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)

        val gardens = listOf<Garden>(
            Garden(id = "garden1", name = "Самара"),
            Garden(id = "garden2", name = "а"),
            Garden(id = "garden3", name = "п"),
            Garden(id = "garden4", name = "р"),
            Garden(id = "garden5", name = "с"),
            Garden(id = "garden6", name = "т"),
            Garden(id = "garden7", name = "ь"),
            Garden(id = "garden8", name = "б")
        )

        val adapter = GardenAdapter(gardens)
        binding.gardensRecycler.adapter = adapter
        binding.gardensRecycler.layoutManager = LinearLayoutManager(context)




        return binding.root
    }
}