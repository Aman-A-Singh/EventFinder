package com.ticket.master.eventfinder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ticket.master.eventfinder.database.EventEntityDatabase
import com.ticket.master.eventfinder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        supportActionBar?.hide()
    }

    //    override fun onBackPressed() {
//        // Check the current fragment and handle the back button press if needed
////        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)?.childFragmentManager?.findFragmentById(R.id.fragmentContainerView2)
////        if (currentFragment is SearchFragment) {
////            finish()
////        }else{
//        findNavController(R.id.fragmentContainerView).popBackStack()
////        }
//    }
    override fun onDestroy() {
        super.onDestroy()
        EventEntityDatabase.getInstance(this).close()
    }
}