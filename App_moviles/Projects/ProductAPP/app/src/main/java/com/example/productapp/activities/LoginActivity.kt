package com.example.productapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.productapp.R


class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
    }
}