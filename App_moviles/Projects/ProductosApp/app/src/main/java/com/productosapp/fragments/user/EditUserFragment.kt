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

class EditUserFragment : Fragment() {

    lateinit var v: View

    lateinit var editName        : TextView
    lateinit var editLastName    : TextView
    lateinit var editUserName    : TextView
    lateinit var editEmail       : TextView
    lateinit var btnUpdatetUser : Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_edit_user, container, false)

        editName        = v.findViewById(R.id.editName)
        editLastName    = v.findViewById(R.id.editLastName)
        editUserName    = v.findViewById(R.id.editUserName)
        editEmail       = v.findViewById(R.id.editEmail)
        btnUpdatetUser  = v.findViewById(R.id.btnUpdatetUser)

        return v
    }

    override fun onStart() {
        super.onStart()

        btnUpdatetUser.setOnClickListener {
            //código que actualiza el usuario

            findNavController().popBackStack()
        }
    }
}