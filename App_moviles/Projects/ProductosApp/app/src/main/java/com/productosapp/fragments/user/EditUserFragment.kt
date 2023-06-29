package com.productosapp.fragments.user

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
import com.productosapp.entities.User
import com.productosapp.fragments.login_register.LoginFragment

class EditUserFragment : Fragment() {

    lateinit var v: View

    lateinit var editName        : TextView
    lateinit var editLastName    : TextView
    lateinit var editUserName    : TextView
    lateinit var editEmail       : TextView
    lateinit var btnUpdatetUser  : Button

    private lateinit var viewModel: EditUserViewModel

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
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(EditUserViewModel::class.java)
    }
    override fun onStart() {
        super.onStart()
        viewModel.getUserLogged()

        btnUpdatetUser.setOnClickListener {
            viewModel.user.value?.let { it->loadUser(it)}
            viewModel.updateAndSetUser()
            findNavController().popBackStack()
        }
    }
    private fun loadUser(userLogged: User){
        editName.text     = userLogged.name
        editLastName.text = userLogged.lastname
        editUserName.text = userLogged.username
        editEmail.text    = userLogged.email
    }
}