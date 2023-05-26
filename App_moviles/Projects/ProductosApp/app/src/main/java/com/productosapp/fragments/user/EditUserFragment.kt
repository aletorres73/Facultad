package com.productosapp.fragments.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.productosapp.R
import com.productosapp.database.AppDataBase
import com.productosapp.database.UserDao

class EditUserFragment : Fragment() {

    private var db: AppDataBase? = null
    private var userDao: UserDao? = null

    lateinit var v: View

    lateinit var editName        : TextView
    lateinit var editLastName    : TextView
    lateinit var editUserName    : TextView
    lateinit var editEmail       : TextView
    lateinit var btnUpdatetUser  : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_edit_user, container, false)

        editName        = v.findViewById(R.id.inputNameEdit)
        editLastName    = v.findViewById(R.id.inputLastNameEdit)
        editUserName    = v.findViewById(R.id.inputUserNameEdit)
        editEmail       = v.findViewById(R.id.inputEmailEdit)
        btnUpdatetUser  = v.findViewById(R.id.btnUpdatetUser)

        return v
    }

    override fun onStart() {
        super.onStart()

        db = AppDataBase.getInstance(requireContext())
        userDao = db?.UserDao()
        userDao?.loadAllUsers()

        val userLogged = userDao?.findUserLogged()

        if(userLogged !=null){
            editName.text        = userLogged.name
            editLastName.text    = userLogged.lastname
            editUserName.text    = userLogged.username
            editEmail.text       = userLogged.email

        }

        btnUpdatetUser.setOnClickListener {
            userLogged?.name     = editName.text.toString()
            userLogged?.lastname = editLastName.text.toString()
            userLogged?.username = editUserName.text.toString()
            userLogged?.email    = editEmail.text.toString()

            userDao?.update(userLogged!!)
            userDao?.setLogged(userLogged!!.id)

            findNavController().popBackStack()
        }
    }
}