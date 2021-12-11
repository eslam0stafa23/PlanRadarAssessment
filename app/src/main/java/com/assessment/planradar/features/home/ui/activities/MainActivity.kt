package com.assessment.planradar.features.home.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.assessment.planradar.R
import com.assessment.planradar.databinding.ActivityMainBinding
import com.assessment.planradar.utils.appComponent

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    appComponent.inject(this)
    setupNavigationComponent()
  }

  private fun setupNavigationComponent() {
    navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main)
  }

  override fun onSupportNavigateUp(): Boolean {
    return NavigationUI.navigateUp(navController, null)
  }
}