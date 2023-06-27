package com.productosapp.fragments.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.productosapp.R
import com.productosapp.activities.LoginActivity
import com.productosapp.fragments.login_register.LoginFragment

class UserFragment : Fragment() {

    lateinit var v : View

    lateinit var txtName        : TextView
    lateinit var txtLastName    : TextView
    lateinit var txtUserName    : TextView
    lateinit var txtEmail       : TextView
    lateinit var txtPasword     : TextView
    lateinit var btnEditUser    : Button
    lateinit var btnCloseSesion : Button

    companion object{
        fun newInstance() = LoginFragment()
    }
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_user, container, false)

        txtName         = v.findViewById(R.id.editName)
        txtLastName     = v.findViewById(R.id.editLastName)
        txtUserName     = v.findViewById(R.id.txtUserName)
        txtEmail        = v.findViewById(R.id.txtEmail)
        txtPasword      = v.findViewById(R.id.txtPasword)
        btnEditUser     = v.findViewById(R.id.btnUpdatetUser)
        btnCloseSesion  = v.findViewById(R.id.btnCloseSesion)

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(UserViewModel::class.java)

        viewModel.instanceDataBase(requireContext())

    }

    override fun onStart() {
        super.onStart()

        val userLogged = viewModel.getUserLogged()

        if(userLogged !=null){
            txtName.text        = userLogged.name
            txtLastName.text    = userLogged.lastname
            txtUserName.text    = userLogged.username
            txtEmail.text       = userLogged.email

        }

        btnCloseSesion.setOnClickListener {

            viewModel.clearUserLogged(userLogged!!.id)

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