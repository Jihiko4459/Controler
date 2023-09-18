package com.example.controler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.controler.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    lateinit var bind:ActivityMain2Binding
    private val falist= listOf(HomeFragment(), SupportFragment(), ProfileFragment())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(bind.root)
        supportFragmentManager.beginTransaction().replace(R.id.framefr, falist[0]).commit()
        bind.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home->{
                    supportFragmentManager.beginTransaction().replace(R.id.framefr, falist[0]).commit()
                }
                R.id.support->{
                    supportFragmentManager.beginTransaction().replace(R.id.framefr, falist[1]).commit()
                }
                R.id.profile->{
                supportFragmentManager.beginTransaction().replace(R.id.framefr, falist[2]).commit()
            }
            }
            true
        }

    }
    fun scan_qr(view: View) {
        val intent= Intent(this@MainActivity2, QrActivity::class.java)
        startActivity(intent)
    }
}