package com.productosapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.productosapp.R
import android.content.Intent
import android.os.Handler

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT:Long = 3000 // 3 segundos de duración de la pantalla de bienvenida

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            // Después de 3 segundos, se inicia la MainActivity
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))

            // Se cierra la actividad de la pantalla de bienvenida
            finish()
        }, SPLASH_TIME_OUT)
    }
}


