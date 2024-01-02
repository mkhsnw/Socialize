package com.example.socialize

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.ByteArrayInputStream

class Dashboard : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)

        bottomNavigation = findViewById(R.id.navigation)

        bottomNavigation.setOnItemSelectedListener {menuItem ->
            when(menuItem.itemId){
                R.id.home -> {
                    replaceFragment(Home())
                    true
                }
                R.id.profile -> {
                    replaceFragment(Profile())
                    true
                }
                R.id.tambah -> {
                    replaceFragment(Tambah())
                    true
                }
                else -> false
            }
        }



    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame,fragment).commit()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.button, menu)
        return super.onCreateOptionsMenu(menu)
    }

}