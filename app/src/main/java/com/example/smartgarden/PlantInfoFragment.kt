package com.example.smartgarden

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.smartgarden.databinding.FragmentMainBinding
import com.example.smartgarden.databinding.FragmentPlantInfoBinding
import com.example.smartgarden.model.PlantInfo
import com.squareup.picasso.Picasso


class PlantInfoFragment : Fragment() {
    private lateinit var binding: FragmentPlantInfoBinding
    private lateinit var viewModel: PlantInfoViewModel
    private var plantId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlantInfoBinding.inflate(layoutInflater, container, false)
        plantId = arguments?.getString("plantId")

        viewModel = ViewModelProvider(this, ViewModelFactory())[PlantInfoViewModel::class.java]
        println(plantId)
        viewModel.getPlantInfo(plantId.toString())

        viewModel.plantInfoLiveData.observe(viewLifecycleOwner){
            binding.plantInfoInfo.text = it.info
            binding.plantBloom.text = it.bloomTime
            binding.plantInfoHumidity.text = it.tempAndHumidity
            Picasso.get().load(it.picture).into(binding.plantInfoImage)
            binding.plantInfoName.text=it.name
            binding.plantInfoSoilType.text = it.soilType
            binding.plantInfoWatering.text = it.watering.toString()
            binding.plantInfoSunExposure.text = it.sunExposure
            binding.plantInfoSize.text = it.size
            binding.plantInfoSoilPh.text = it.soilPh
            binding.plantInfoToxicity.text = it.toxicity
            binding.plantInfoFertilizer.text = it.fertilizer
            binding.plantInfoSunExposureInfo.text = it.sunExposureInfo
            binding.plantInfoWaterInfo.text = it.waterInfo
            binding.plantInfoSoilInfo.text = it.soilInfo
        }

        binding.backToPlants.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }

}