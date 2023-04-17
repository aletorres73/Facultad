package com.example.user_interface1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.user_interface1.R
import com.example.user_interface1.entities.User

class Scree1Fragment : Fragment() {

    lateinit var textLogin: TextView
    lateinit var inputUser: EditText
    lateinit var inputPass: EditText
    lateinit var btnContinue: Button

    lateinit var v: View

    var users : MutableList<User> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_scree1, container, false)

        textLogin = v.findViewById(R.id.textLogin)
        inputUser = v.findViewById(R.id.inputUser)
        inputPass = v.findViewById(R.id.inputPass)
        btnContinue = v.findViewById(R.id.btnContinue)

        users.add(User(name = "Lio", lastname = "Messi", email = "Lio@scaloneta.com", password = "Lio"))
        users.add(User(name = "Angel", lastname = "Di Maria", email = "Angel@scaloneta.com", password = "Angel"))
        users.add(User(name = "Leandro", lastname = "Paredes", email = "Leandro@scaloneta.com", password = "Leandro"))
        users.add(User(name = "Dibu", lastname = "Martinez", email = "Dibu@scaloneta.com", password = "Dibu"))
        users.add(User(name = "Papu", lastname = "Gomez", email = "Papu@scaloneta.com", password = "Papu"))
        users.add(User(name = "Rodrigo", lastname = "De Paul", email = "Rodrigo@scaloneta.com", password = "Rodrigo"))
        return v
    }

    override fun onStart() {
        super.onStart()

        btnContinue.setOnClickListener {
            val action = Scree1FragmentDirections.actionScree1FragmentToScreen2Fragment()
            findNavController().navigate(action)

        }
    }

}