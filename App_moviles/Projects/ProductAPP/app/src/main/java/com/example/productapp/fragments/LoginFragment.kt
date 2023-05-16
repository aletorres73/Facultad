package com.example.productapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.productapp.R
import com.example.productapp.activities.MainActivity
import com.example.productapp.entities.UserManager

class LoginFragment : Fragment() {

    private lateinit var inputUserName: EditText
    private lateinit var inputPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button

    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_login, container, false)

        inputUserName = v.findViewById(R.id.inputUserName)
        inputPass = v.findViewById(R.id.inputPass)
        btnLogin = v.findViewById(R.id.btnLogin)
        btnRegister = v.findViewById(R.id.btnRegister)

        return v
    }

    override fun onStart() {
        super.onStart()

        val username = inputUserName.text.toString()
        val pass     = inputPass.text.toString()
        val user =
            UserManager.getUsers().find { it.username == username && it.password == pass }

        btnLogin.setOnClickListener {
            if (username.isEmpty() || pass.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Ingrese un usuario y password",
                    Toast.LENGTH_SHORT
                ).show()

            } else {
                if (user != null) {
                    val contextActivity = requireContext()
                    val intent = Intent(contextActivity, MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()

                } else {
                    Toast.makeText(
                        requireContext(),
                        "Usuario o password incorrecto",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }
        }

        btnRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
    }
}