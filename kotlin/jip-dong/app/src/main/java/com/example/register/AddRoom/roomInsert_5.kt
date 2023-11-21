package com.example.register.AddRoom


import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.register.R
import com.example.register.home
import okhttp3.*
import java.io.*

import java.net.HttpURLConnection
import java.net.URL

private val CAMERA_PERMISSION_REQUEST_CODE = 101

class roomInsert_5 : AppCompatActivity() {
    private val UPLOAD_URL = "http://capstone123.dothome.co.kr/upload123.php" // 업로드할 서버의 URL로 변경해야 합니다.

    private val MAX_IMAGE_COUNT = 6 // 최대 이미지 개수
    private val imageViews = mutableListOf<ImageView>() // 이미지를 보여줄 ImageView들의 리스트
    private val selectedImages = mutableListOf<Uri>() // 선택된 이미지들의 Uri 리스트

    private lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.maemul_5)


        val address = intent.getStringExtra("address")
        val detailAddress = intent.getStringExtra("detailAddress")
        val housesort = intent.getStringExtra("housesort")
        val sort_of_floor = intent.getStringExtra("sort_of_floor")
        val num_of_floor = intent.getStringExtra("num_of_floor")
        val area = intent.getStringExtra("area")
        val roomcount = intent.getStringExtra("roomcount")
        val toiletcount = intent.getStringExtra("toiletcount")
        val options = intent.getStringExtra("options")
        val sex = intent.getStringExtra("sex")
        val age = intent.getStringExtra("age")
        val smoke = intent.getStringExtra("smoke")
        val animal = intent.getStringExtra("animal")
        val enterdate = intent.getStringExtra("enterdate")
        val leastdate = intent.getStringExtra("leastdate")
        val gurantee = intent.getStringExtra("gurantee")
        val monthfee = intent.getStringExtra("monthfee")
        val manage = intent.getStringExtra("manage")
        val roomtitle = intent.getStringExtra("roomtitle")
        val content = intent.getStringExtra("content")

        val backbutton = findViewById<ImageView>(R.id.imageView60)

        backbutton.setOnClickListener {
            super.onBackPressed()
        }

        imageViews.add(findViewById(R.id.imageView1))
        imageViews.add(findViewById(R.id.imageView2))
        imageViews.add(findViewById(R.id.imageView3))
        imageViews.add(findViewById(R.id.imageView4))
        imageViews.add(findViewById(R.id.imageView5))
        imageViews.add(findViewById(R.id.imageView6))

        nextButton = findViewById(R.id.nextButton)

        imageViews.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                showImageSourceDialog(index)
            }
        }

        nextButton.setOnClickListener {
            if (selectedImages.isNotEmpty()) {
                uploadImages()

            }

            val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
            val uid = sharedPreferences.getString("uid", "")

            val task = InsertData()
            task.execute(
                "http://capstone123.dothome.co.kr/roomInsert_100.php",
                uid,
                address,
                detailAddress,
                housesort,
                sort_of_floor,
                num_of_floor,
                area,
                roomcount,
                toiletcount,
                options,
                sex,
                age,
                smoke,
                animal,
                enterdate,
                leastdate,
                gurantee,
                monthfee,
                manage,
                roomtitle,
                content

            )
            val intent = Intent(this, home::class.java)
            startActivity(intent)

        }
    }

    private fun selectImageUri(imageViewIndex: Int) {
        val selectPictureIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        if (selectPictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(selectPictureIntent, imageViewIndex)
        }
    }

    private fun showImageSourceDialog(imageViewIndex: Int) {
        val items = arrayOf("Take Photo", "Choose from Gallery")
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("Select Image Source")
        dialog.setItems(items) { _, which ->
            when (which) {
                0 -> {
                    if (checkCameraPermission()) {
                        dispatchTakePictureIntent(imageViewIndex)
                    } else {
                        requestCameraPermission()
                    }
                }
                1 -> {
                    val selectedCount = selectedImages.size
                    if (selectedCount < MAX_IMAGE_COUNT) {
                        selectImageUri(imageViewIndex)
                    } else {
                        Toast.makeText(applicationContext, "You can select up to $MAX_IMAGE_COUNT images", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        dialog.show()
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val imageViewIndex = requestCode // Use requestCode as imageViewIndex
                dispatchTakePictureIntent(imageViewIndex)
            } else {
                // Camera permission denied
            }
        }
    }

    private fun dispatchTakePictureIntent(imageViewIndex: Int) {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, imageViewIndex)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            val imageView = getImageViewById(requestCode)
            imageView?.let { image ->
                when (requestCode) {
                    in 0 until MAX_IMAGE_COUNT -> {
                        val imageUri = data?.data
                        image.setImageURI(imageUri)
                        image.scaleType = ImageView.ScaleType.CENTER_CROP
                        image.tag = imageUri // ImageView의 tag에 imageUri를 저장하여 나중에 참조할 수 있도록 합니다.
                        if (imageUri != null) {
                            selectedImages.add(imageUri)
                        }
                    }
                }
            }
        }
    }

    private fun getImageViewById(imageViewIndex: Int): ImageView? {
        return imageViews.getOrNull(imageViewIndex)
    }

    private fun checkCameraPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun uploadImages() {
        val client = OkHttpClient()

        val requestBodyBuilder = MultipartBody.Builder().setType(MultipartBody.FORM)

        selectedImages.forEachIndexed { index, imageUri ->
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            val file = createImageFile(bitmap, index)
            val imageRequestBody = RequestBody.create(MediaType.parse("image/jpeg"), file)
            requestBodyBuilder.addFormDataPart("file[]", file.name, imageRequestBody)
        }

        val requestBody = requestBodyBuilder.build()

        val request = Request.Builder()
            .url(UPLOAD_URL)
            .post(requestBody)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(applicationContext, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                response.use { response ->
                    if (response.isSuccessful) {
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Upload successful", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Upload failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        })
    }

    private fun createImageFile(bitmap: Bitmap, index: Int): File {
        val cacheDir = cacheDir
        val fileName = "image_$index.jpg"
        val file = File(cacheDir, fileName)

        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        return file
    }

    private class InsertData : AsyncTask<String, Void, String>() {


        override fun doInBackground(vararg params: String?): String {

            val serverURL: String? = params[0]
            val uid: String? = params[1]
            val address: String? = params[2]
            val detailAddress: String? = params[3]
            val housesort: String? = params[4]
            val sort_of_floor: String? = params[5]
            val num_of_floor: String? = params[6]
            val area: String? = params[7]
            val roomcount: String? = params[8]
            val toiletcount: String? = params[9]
            val options: String? = params[10]
            val sex: String? = params[11]
            val age: String? = params[12]
            val smoke: String? = params[13]
            val animal: String? = params[14]
            val enterdate: String? = params[15]
            val leastdate: String? = params[16]
            val gurantee: String? = params[17]
            val monthfee: String? = params[18]
            val manage: String? = params[19]
            val roomtitle: String? = params[20]
            val content: String? = params[21]


            val postParameters: String = "adress=$address&detailAdress=$detailAddress&housesort=$housesort&sort_of_floor=$sort_of_floor&num_of_floor=$num_of_floor&area=$area" +
                    "&roomcount=$roomcount&sort_of_floor=$sort_of_floor&toiletcount=$toiletcount&options=$options&sex=$sex&age=$age&smoke=$smoke" +
                    "&animal=$animal&enterdate=$enterdate&leastdate=$leastdate&gurantee=$gurantee&monthfee=$monthfee&manage=$manage" +
                    "&roomtitle=$roomtitle&content=$content&host=$uid"

            try {
                val url = URL(serverURL)
                val httpURLConnection: HttpURLConnection = url.openConnection() as HttpURLConnection


                httpURLConnection.readTimeout = 5000
                httpURLConnection.connectTimeout = 5000
                httpURLConnection.requestMethod = "POST"
                httpURLConnection.connect()


                val outputStream: OutputStream = httpURLConnection.outputStream
                outputStream.write(postParameters.toByteArray(charset("UTF-8")))
                outputStream.flush()
                outputStream.close()

                val responseStatusCode: Int = httpURLConnection.responseCode


                val inputStream: InputStream
                inputStream = if (responseStatusCode == HttpURLConnection.HTTP_OK) {
                    httpURLConnection.inputStream
                } else {
                    httpURLConnection.errorStream

                }


                val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
                val bufferedReader = BufferedReader(inputStreamReader)

                val sb = StringBuilder()
                var line: String? = null

                while (bufferedReader.readLine().also({ line = it }) != null) {
                    sb.append(line)
                }

                bufferedReader.close();

                return sb.toString();

            } catch (e: Exception) {
                return "Error" + e.message
            }

        }

    }


}
