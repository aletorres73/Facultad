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
    lateinit var user : User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        viewModel.getUserLogged()
        viewModel.userFb.observe(viewLifecycleOwner){ it->loadUser(it)}
    }
    override fun onStart() {
        super.onStart()

        btnUpdatetUser.setOnClickListener {
            getInput()
            viewModel.updateAndSetUser()
            findNavController().popBackStack()
        }
    }
    private fun loadUser(userLogged: User){
        user = userLogged

        editName.text     = user.name
        editLastName.text = user.lastname
        editUserName.text = user.username
        editEmail.text    = user.email
    }
    private fun getInput(){
        user.name       = editName.text.toString()
        user.lastname   = editLastName.text.toString()
        user.username   = editUserName.text.toString()
        user.email      = editEmail.text.toString()

        viewModel.userEdit.value = user
    }
}