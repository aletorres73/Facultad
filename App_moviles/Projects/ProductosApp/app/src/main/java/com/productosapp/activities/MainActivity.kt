package com.productosapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.productosapp.R
import com.productosapp.entities.productModule
import com.productosapp.entities.userModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext

class MainActivity : AppCompatActivity() {

    lateinit var bottomBar :      BottomNavigationView
    lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        GlobalContext.startKoin {
            androidContext(this@MainActivity)
            modules(userModule, productModule)
        }

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        bottomBar       = findViewById(R.id.bottom_bar)

        NavigationUI.setupWithNavController(bottomBar,navHostFragment.navController)
    }
    override fun onStart() {
        super.onStart()

        bottomBar.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    navHostFragment.navController.navigate(R.id.homeFragment)
                    true
                }
                R.id.userFragment -> {
                    navHostFragment.navController.navigate(R.id.userFragment)
                    true
                }
                R.id.createProductFragment2 -> {
                    navHostFragment.navController.navigate(R.id.createProductFragment2)
                    true
                }
                // Agrega más casos para los otros elementos de la barra
                else -> false
            }
        }

    }
}