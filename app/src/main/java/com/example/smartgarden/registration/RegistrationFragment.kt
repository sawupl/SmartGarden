package com.example.smartgarden.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.smartgarden.R
import com.example.smartgarden.ViewModelFactory
import com.example.smartgarden.databinding.FragmentRegistrationBinding

class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var viewModel: RegistrationViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory())[RegistrationViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding  = FragmentRegistrationBinding.inflate(inflater, container, false)

        viewModel.isLoginLiveData.observe(viewLifecycleOwner) {
            it?.let {  isLogin ->
                if (isLogin) {
                    findNavController().navigate(R.id.action_registrationFragment_to_mainFragment)
                }
                else {
                    Toast.makeText(requireContext(), "Неправильный ввод данных", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.register.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val login = binding.login.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty() && login.isNotEmpty()){
                viewModel.registration(
                    email = email,
                    password = password,
                    login = login
                )
            }
            else{
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
        binding.toLogin.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }
}