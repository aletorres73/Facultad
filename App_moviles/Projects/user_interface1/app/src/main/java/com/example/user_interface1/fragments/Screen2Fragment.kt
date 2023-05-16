package com.example.user_interface1.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.user_interface1.R

class Screen2Fragment : Fragment() {

    lateinit var textWelcome: TextView
    lateinit var v : View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v=inflater.inflate(R.layout.fragment_screen2, container, false)
        textWelcome = v.findViewById(R.id.textView)
        return v
    }
}