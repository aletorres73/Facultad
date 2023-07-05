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

class LoginFragment : Fragment() {

    private lateinit var inputUserName: EditText
    private lateinit var inputPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button

    private lateinit var v: View

    private lateinit var viewModel: LoginViewModel


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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        viewModel.checkLoggedCondition()
        viewModel.checkLogged.observe(viewLifecycleOwner) { logged ->
            if (logged) {
                goToSplash()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        btnLogin.setOnClickListener {
            loadInput()
            viewModel.isLoginEmpty()
            viewModel.checkEmpty.observe(viewLifecycleOwner) { empty ->
                if (!empty) {
                    viewModel.isLoginOk()
                    viewModel.checkLogin.observe(viewLifecycleOwner) { login ->
                        if (login) {
                            viewModel.setLogged()
                            goToSplash()
                        } else {
                            Toast.makeText(
                                requireContext(), "Usuario o password incorrecto",
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(
                        requireContext(), "Ingrese un usuario y password",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }
        btnRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            findNavController().navigate(action)
        }
    }

    private fun goToSplash() {
        val intent = Intent(requireContext(), SplashActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
    private fun loadInput(){
        viewModel.username.value = inputUserName.text.toString()
        viewModel.password.value = inputPass.text.toString()
    }
}
