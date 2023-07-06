package com.productosapp.fragments.login_register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.productosapp.R
import com.productosapp.entities.User

class RegisterFragment : Fragment() {


    private lateinit var inputName      : EditText
    private lateinit var inputLastName  : EditText
    private lateinit var inputUser      : EditText
    private lateinit var inputEmail     : EditText
    private lateinit var inputPass      : EditText
    private lateinit var btnMakeUser    : Button

    private lateinit var v              : View

    private lateinit var viewModel      : RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_register, container, false)

        inputName     = v.findViewById(R.id.inputName)
        inputLastName = v.findViewById(R.id.inputLastName)
        inputUser     = v.findViewById(R.id.InputUser)
        inputEmail    = v.findViewById(R.id.inputEmail)
        inputPass     = v.findViewById(R.id.inputPass)
        btnMakeUser   = v.findViewById(R.id.btnMakeUser)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(RegisterViewModel::class.java)
    }

    override fun onStart() {
        super.onStart()

        btnMakeUser.setOnClickListener {
            loadUserFromInput()
            viewModel.checkEmptyUser()
            viewModel.viewState.observe(viewLifecycleOwner){result ->
                when(result) {
                    RegisterViewModel.STATE_CREATING->{
                        viewModel.createUser(requireActivity())
                    }
                    RegisterViewModel.STATE_DONE->{
                        Toast.makeText(requireContext(), "Usuario nuevo creado", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().popBackStack()
                    }
                    RegisterViewModel.STATE_ERROR->{
                        Toast.makeText(requireContext(), "Completa los campos vacíos", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }
    private fun loadUserFromInput(){
        val user = User()
        user.name     = inputName.text.toString()
        user.lastname = inputLastName.text.toString()
        user.email    = inputEmail.text.toString()
        user.password = inputPass.text.toString()
        user.username = inputUser.text.toString()

        viewModel.userDb.value = user
    }
}

