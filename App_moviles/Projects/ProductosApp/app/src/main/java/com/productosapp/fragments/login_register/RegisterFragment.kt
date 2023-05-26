package com.productosapp.fragments.login_register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.productosapp.R
import com.productosapp.database.AppDataBase
import com.productosapp.database.UserDao
import com.productosapp.entities.User

//import com.productosapp.entities.User
//import com.productosapp.entities.UserManager

class RegisterFragment : Fragment() {

    private var db: AppDataBase? = null
    private var userDao: UserDao? = null

    private lateinit var inputName: EditText
    private lateinit var inputLastName: EditText
    private lateinit var inputUser: EditText
    private lateinit var inputEmail: EditText
    private lateinit var inputPass: EditText
    private lateinit var btnMakeUser: Button

    private lateinit var v: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_register, container, false)

        inputName = v.findViewById(R.id.inputName)
        inputLastName = v.findViewById(R.id.inputLastName)
        inputUser = v.findViewById(R.id.InputUser)
        inputEmail = v.findViewById(R.id.inputEmail)
        inputPass = v.findViewById(R.id.inputPass)
        btnMakeUser = v.findViewById(R.id.btnMakeUser)

        return v
    }


    override fun onStart() {
        super.onStart()

        db = AppDataBase.getInstance(requireContext())
        userDao = db?.UserDao()
        userDao?.loadAllUsers()

        btnMakeUser.setOnClickListener {
            val name     = inputName.text.toString()
            val lastname = inputLastName.text.toString()
            val email    = inputEmail.text.toString()
            val password = inputPass.text.toString()
            val username = inputUser.text.toString()

            if (name.isEmpty()) {
                Toast.makeText(requireContext(),"Ingrese un nombre", Toast.LENGTH_SHORT).show()
            } else if (lastname.isEmpty()) {
                Toast.makeText(requireContext(),"Ingrese un apellido", Toast.LENGTH_SHORT).show()
            } else if (email.isEmpty()) {
                Toast.makeText(requireContext(),"Ingrese un correo", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty()) {
                Toast.makeText(requireContext(),"Ingrese una contraseña", Toast.LENGTH_SHORT)
                    .show()
            } else if (username.isEmpty()) {
                Toast.makeText(requireContext(),"Ingrese un nombre de usuario", Toast.LENGTH_SHORT)
                    .show()
            }
            else{
                val user = User(
                    id       = 0,
                    logged   = 0,
                    name     = name,
                    lastname = lastname,
                    username = username,
                    email    = email,
                    password = password
                )
                userDao?.insertUser(user)
                Toast.makeText(requireContext(),"Usuario nuevo creado", Toast.LENGTH_SHORT)
                    .show()
                findNavController().popBackStack()
            }
        }
    }
}

            //else {
//                val user =
//                    UserManager.getUsers().find { it.email == email || it.username == username }
//                if (user != null) {
//                    Toast.makeText(
//                        requireContext(),
//                        "Ya existe un usuario con el correo o el nombre de usuario ingresados",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                } else {
//                    UserManager.addUser(
//                        User(
//                            name = name,
//                            lastname = lastname,
//                            username = username,
//                            email = email,
//                            password = password
//                        )
//                    )
//                    Toast.makeText(
//                        requireContext(),
//                        "Usuario creado correctamente",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//        }
//    }
//}
