package com.example.smartgarden

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartgarden.databinding.FragmentGardenBinding

class GardenFragment : Fragment(),SensorEventListener{
    private lateinit var binding: FragmentGardenBinding
    private lateinit var viewModel: GardenViewModel
    private var gardenId: String? = null

    private lateinit var sensorManager: SensorManager
    private var sensor:Sensor?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory())[GardenViewModel::class.java]
        gardenId = arguments?.getString("gardenId")
        viewModel.getGarden(gardenId.toString())


        sensorManager= (requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
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

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
    override fun onSensorChanged(event: SensorEvent?) {
        binding.currentLight.text = event!!.values[0].toString()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}