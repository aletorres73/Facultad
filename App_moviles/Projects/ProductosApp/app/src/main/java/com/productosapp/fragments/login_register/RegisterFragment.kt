package com.productosapp.fragments.login_register

import android.content.Intent
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
import com.productosapp.activities.SplashActivity
import com.productosapp.database.AppDataBase
import com.productosapp.database.UserDao
import com.productosapp.entities.User

class RegisterFragment : Fragment() {


    private lateinit var inputName: EditText
    private lateinit var inputLastName: EditText
    private lateinit var inputUser: EditText
    private lateinit var inputEmail: EditText
    private lateinit var inputPass: EditText
    private lateinit var btnMakeUser: Button

    private lateinit var v: View

    companion object{
        fun newInstance() = LoginFragment()
    }
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

        viewModel.instanceDataBase(requireContext())

    }

    override fun onStart() {
        super.onStart()

        btnMakeUser.setOnClickListener {
            viewModel.name.value     = inputName.text.toString()
            viewModel.lastname.value = inputLastName.text.toString()
            viewModel.email.value    = inputEmail.text.toString()
            viewModel.password.value = inputPass.text.toString()
            viewModel.username.value = inputUser.text.toString()

            viewModel.loadUser()

            if(viewModel.checkEmptyUser()){
                Toast.makeText(requireContext(),"Completa los campos vacíos", Toast.LENGTH_SHORT)
                    .show()
            }
            else{
                viewModel.createUser()
                Toast.makeText(requireContext(),"Usuario nuevo creado", Toast.LENGTH_SHORT)
                    .show()
                findNavController().popBackStack()
            }
        }
    }
}

