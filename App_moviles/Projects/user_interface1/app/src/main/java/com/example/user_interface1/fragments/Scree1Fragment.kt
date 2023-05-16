package com.example.user_interface1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.user_interface1.R
import com.example.user_interface1.entities.UserManager

class Scree1Fragment : Fragment(R.layout.fragment_scree1) {

    private lateinit var inputUser: EditText
    private lateinit var inputPass: EditText
    private lateinit var btnLogin: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputUser = view.findViewById(R.id.inputUser)
        inputPass = view.findViewById(R.id.inputPass)
        btnLogin = view.findViewById(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val email = inputUser.text.toString()
            val pass = inputPass.text.toString()

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(requireContext(), "Ingrese un email y password", Toast.LENGTH_SHORT).show()
            } else {
                //val user = users.find { it.email == email && it.password == pass }
                val user = UserManager.getUsers().find { it.email == email && it.password == pass }
                if (user != null) {
                    val action = Scree1FragmentDirections.actionScree1FragmentToScreen2Fragment()
                    findNavController().navigate(action)
                } else {
                    Toast.makeText(requireContext(), "Usuario o password incorrecto", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}


