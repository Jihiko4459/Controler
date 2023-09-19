package com.example.controler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.controler.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var bind: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityResultBinding.inflate(layoutInflater)
        setContentView(bind.root)
        var data=intent.getStringExtra("Data")
        var dataArray= data.toString().split("/")
        bind.name.text= dataArray[0]
        bind.patronymic.text=dataArray[1]
        bind.birthday.text=dataArray[2]
        bind.numberofcard.text=dataArray[3]
        bind.typeofbenefit.text=dataArray[4]
    }

    fun back(view: View) {
        val intent= Intent(this@ResultActivity, MainActivity2::class.java)
        startActivity(intent)
    }

    fun complaint(view: View) {
        Toast.makeText(this@ResultActivity, "Сообщение о жалобе отправлено в поддержку", Toast.LENGTH_SHORT)
    }
}