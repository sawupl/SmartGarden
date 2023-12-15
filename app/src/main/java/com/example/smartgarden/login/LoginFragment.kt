package com.example.smartgarden.login

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
import com.example.smartgarden.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: LoginViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory())[LoginViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding  = FragmentLoginBinding.inflate(inflater, container, false)

        viewModel.isLoginLiveData.observe(viewLifecycleOwner) {
            it?.let {  isLogin ->
                if (isLogin) {
                    findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                }
                else {
                    Toast.makeText(requireContext(), "Неправильная почта или пароль", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.login.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()){
                viewModel.signIn(
                    email = email,
                    password = password
                )
            }
            else{
                Toast.makeText(requireContext(), "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }
        binding.toReg.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }

        return binding.root
    }
}