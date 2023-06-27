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
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.productosapp.R
import com.productosapp.activities.SplashActivity
import com.productosapp.database.FirebaseDataUserSource
import com.productosapp.database.UserSource
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class LoginFragment : Fragment() {

    private lateinit var inputUserName: EditText
    private lateinit var inputPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button

    private lateinit var v: View

    private val viewModel: LoginViewModel by viewModel()

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
        loadKoinModules(userSourceModule)

        viewModel.checkLoggedCondition()
        viewModel.checkView.observe(viewLifecycleOwner, Observer { result ->
            if (result) {
                goToSplash()
            }
        })
    }
    override fun onStart() {
        super.onStart()

        btnLogin.setOnClickListener {
            viewModel.username.value = inputUserName.text.toString()
            viewModel.password.value = inputPass.text.toString()

            viewModel.isLoginEmpty()
            viewModel.checkView.observe(viewLifecycleOwner, Observer { result ->
                if (result) {
                    Toast.makeText(
                        requireContext(), "Ingrese un usuario y password",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {

                }
            })
            viewModel.isLoginOk()
            viewModel.checkView.observe(viewLifecycleOwner, Observer { result ->
                if (result) {
                    goToSplash()
                } else {
                    Toast.makeText(
                        requireContext(), "Usuario o password incorrecto",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
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

    companion object {
        val userSourceModule = module {
            single<UserSource> { FirebaseDataUserSource() }
        }
    }
}
