package com.productosapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.productosapp.R
import com.productosapp.entities.AppModule
import com.productosapp.fragments.login_register.LoginFragment.Companion.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        startKoin {
            androidContext(this@LoginActivity)
            modules(AppModule)
        }
    }
}
