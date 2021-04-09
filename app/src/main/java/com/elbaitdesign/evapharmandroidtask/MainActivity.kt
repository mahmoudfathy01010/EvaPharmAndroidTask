package com.elbaitdesign.evapharmandroidtask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.elbaitdesign.evapharmandroidtask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        val navController = Navigation.findNavController(this@MainActivity, R.id.navHostFragment)
        binding.bottomNavigation.setupWithNavController(navController)
    }
}