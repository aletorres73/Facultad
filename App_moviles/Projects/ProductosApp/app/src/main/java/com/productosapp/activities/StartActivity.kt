package com.productosapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.productosapp.entities.productModule
import com.productosapp.entities.userModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class StartActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth

        GlobalContext.startKoin {
            androidContext(this@StartActivity)
            modules(userModule, productModule)
        }
        supportActionBar?.hide()
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            goToSplash()
        }
        else{
            goToLogin()
        }
    }
    private fun goToLogin(){
        val contextActivity = this
        val intent = Intent(contextActivity, LoginActivity::class.java)
        startActivity(intent)
    }
    private fun goToSplash() {
        val contextActivity = this
        val intent = Intent(contextActivity, SplashActivity::class.java)
        startActivity(intent)
    }
}