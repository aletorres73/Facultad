package com.productosapp.fragments.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.productosapp.R
import com.productosapp.activities.LoginActivity

class UserFragment : Fragment() {

    lateinit var v : View

    lateinit var txtName        : TextView
    lateinit var txtLastName    : TextView
    lateinit var txtUserName    : TextView
    lateinit var txtEmail       : TextView
    lateinit var txtPasword     : TextView
    lateinit var btnEditUser    : Button
    lateinit var btnCloseSesion : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_user, container, false)

        txtName = v.findViewById(R.id.editName)
        txtLastName = v.findViewById(R.id.editLastName)
        txtUserName = v.findViewById(R.id.txtUserName)
        txtEmail = v.findViewById(R.id.txtEmail)
        txtPasword = v.findViewById(R.id.txtPasword)
        btnEditUser = v.findViewById(R.id.btnUpdatetUser)
        btnCloseSesion = v.findViewById(R.id.btnCloseSesion)

        return v
    }

    override fun onStart() {
        super.onStart()

        btnCloseSesion.setOnClickListener {
            //falta limpiar el usuario logeado

            val contextActivity = requireContext()
            val intent = Intent(contextActivity, LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        btnEditUser.setOnClickListener {
            val action = UserFragmentDirections.actionUserFragmentToEditUserFragment()
            findNavController().navigate(action)
        }
    }
}