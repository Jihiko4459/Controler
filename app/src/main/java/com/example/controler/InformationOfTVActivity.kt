package com.example.controler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class InformationOfTVActivity : AppCompatActivity() {
    private lateinit var route:EditText
    private lateinit var timeStart:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_information_of_tvactivity)
        route=findViewById(R.id.editTextNumber)
        timeStart=findViewById(R.id.editTextTime)
    }

    fun save(view: View) {
        if(route.text.isNotEmpty()&&timeStart.text.isNotEmpty()){
            val intent= Intent(this@InformationOfTVActivity, MainActivity2::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(this, "Введите маршрут и время открытия смены", Toast.LENGTH_LONG).show()
        }
    }
}