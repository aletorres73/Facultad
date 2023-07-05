package com.productosapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.productosapp.entities.productModule
import com.productosapp.entities.userModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GlobalContext.startKoin {
            androidContext(this@StartActivity)
            modules(userModule, productModule)
        }
        supportActionBar?.hide()
        goToLogin()
    }
    private fun goToLogin(){
        val contextActivity = this
        val intent = Intent(contextActivity, LoginActivity::class.java)
        startActivity(intent)
    }
}