package com.example.controler

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.example.controler.databinding.ActivityQrBinding

class QrActivity : AppCompatActivity() {
    private lateinit var bind: ActivityQrBinding
    private lateinit var codeScaner: CodeScanner
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind= ActivityQrBinding.inflate(layoutInflater)
        setContentView(bind.root)
        codeScaner= CodeScanner(this, bind.scannerId)
        activityResultLauncher.launch(arrayOf(android.Manifest.permission.CAMERA))
    }

    override fun onResume() {
        super.onResume()
        codeScaner.startPreview()
    }

    override fun onPause() {
        super.onPause()
        codeScaner.releaseResources()
    }

    private fun setupQRScaner() {
        bind.scannerId.apply {

            codeScaner.camera=CodeScanner.CAMERA_BACK
            codeScaner.formats=CodeScanner.ALL_FORMATS

            codeScaner.autoFocusMode= AutoFocusMode.SAFE
            codeScaner.scanMode= ScanMode.SINGLE
            codeScaner.isAutoFocusEnabled=true
            codeScaner.isFlashEnabled=false

            codeScaner.decodeCallback= DecodeCallback {
                runOnUiThread{
                    var data= it.text.toString()
                    val intent= Intent(this@QrActivity, ResultActivity::class.java)
                    intent.putExtra("Data", data)
                    startActivity(intent)
                }
            }
            codeScaner.errorCallback= ErrorCallback  {
                runOnUiThread{ Toast.makeText(this@QrActivity, "Ошибка сканирования: ${it.message}", Toast.LENGTH_LONG).show() }
            }
            codeScaner.startPreview()
        }
    }
    private val activityResultLauncher=registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){ permission->
        permission.entries.forEach{
            val permissionKey=it.key
            val isGranted=it.value
            if(isGranted){
                setupQRScaner()
            }else{
                Toast.makeText(this,"Пожалуйста, включите разрешение камеры, чтобы использовать эту функцию", Toast.LENGTH_LONG).show()
            }
        }
    }


}