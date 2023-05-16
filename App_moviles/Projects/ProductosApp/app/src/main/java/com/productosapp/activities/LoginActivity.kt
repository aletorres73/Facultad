package com.productosapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.productosapp.R

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        }
    }
