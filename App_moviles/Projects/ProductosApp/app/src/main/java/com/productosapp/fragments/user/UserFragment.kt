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
import com.productosapp.database.AppDataBase
import com.productosapp.database.UserDao

class UserFragment : Fragment() {

    private var db: AppDataBase? = null
    private var userDao: UserDao? = null

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

        txtName         = v.findViewById(R.id.editName)
        txtLastName     = v.findViewById(R.id.editLastName)
        txtUserName     = v.findViewById(R.id.txtUserName)
        txtEmail        = v.findViewById(R.id.txtEmail)
        txtPasword      = v.findViewById(R.id.txtPasword)
        btnEditUser     = v.findViewById(R.id.btnUpdatetUser)
        btnCloseSesion  = v.findViewById(R.id.btnCloseSesion)

        return v
    }

    override fun onStart() {
        super.onStart()

        db = AppDataBase.getInstance(requireContext())
        userDao = db?.UserDao()
        userDao?.loadAllUsers()

        val userLogged = userDao?.findUserLogged()

        if(userLogged !=null){
            txtName.text        = userLogged.name
            txtLastName.text    = userLogged.lastname
            txtUserName.text    = userLogged.username
            txtEmail.text       = userLogged.email

        }

        btnCloseSesion.setOnClickListener {

            userDao?.clearLogged(userLogged?.id)

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