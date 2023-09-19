# Controler
1.подключаем библиотеку code_scanner
build.grandle.kts(:app)
        dependencies {
        implementation("com.github.yuriy-budiyev:code-scanner:2.3.2")
        ...}
2. В activity_qr.xml пишем

        <com.budiyev.android.codescanner.CodeScannerView
                android:id="@+id/scanner_id"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                app:layout_constraintBottom_toBottomOf="parent"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toTopOf="parent" />
3. AndroidManifest.xml пишем, чтобы запросить у системы разрешение на использование камеры устройства

 <uses-permission android:name="android.permission.CAMERA" />
    <uses-feature
        android:name="android.hardware.camera"
        android:required="false" />
4. В QrActivity записываем код для чтения QR-кода
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
                            intent.putExtra("Data", data) //переносим прочитанные даннные с QR в ResultActivity
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
5. В ResultActivity в onCreate
        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                bind= ActivityResultBinding.inflate(layoutInflater)
                setContentView(bind.root)
                var data=intent.getStringExtra("Data")//получаем данные
                var dataArray= data.toString().split("/")//разделяем их по разделителю
                bind.name.text= dataArray[0]//и каждый из них распределяем в определенный TextView
                bind.patronymic.text=dataArray[1]
                bind.birthday.text=dataArray[2]
                bind.numberofcard.text=dataArray[3]
                bind.typeofbenefit.text=dataArray[4]
            }

