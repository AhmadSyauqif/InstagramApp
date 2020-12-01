package com.pesan.instagram.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.pesan.instagram.*
import com.pesan.instagram.fragment.HomeFragment

class MainActivity : AppCompatActivity() {

    //untuk mengaktifkan bottomNavigation di Activity
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.nav_home ->{
                moveToFragment(HomeFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_search ->{
                moveToFragment(SearchFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_add_post ->{
                item.isChecked = false
                startActivity(Intent(this, Tambah_post::class.java))
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_notification->{
                moveToFragment(NotificationFragment())
                return@OnNavigationItemSelectedListener true
            }

            R.id.nav_profile -> {
                moveToFragment(ProfileFragment())
                return@OnNavigationItemSelectedListener true
            }
        }

        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //untuk membuild bottom navigationnnya
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        //supaya home menjadi default ketika aplikasi pertama kali di jalankan
        moveToFragment(HomeFragment())

    }

    // function untuk pindah antar fragment
    private fun moveToFragment(fragment: Fragment) {
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragmen_container, fragment)
        fragmentTrans.commit()
    }
}