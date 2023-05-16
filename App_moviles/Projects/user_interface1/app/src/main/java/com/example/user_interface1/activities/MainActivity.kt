package com.example.user_interface1.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.user_interface1.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
    }
}