package com.example.register.Mypage

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.net.Uri
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.register.R
import com.example.register.home
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class ChangeRoomInsert_4 : AppCompatActivity() {
    private lateinit var roomtitle: EditText
    private lateinit var content: EditText
    private lateinit var button22: Button




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val configuration: Configuration = resources.configuration
        onConfigurationChanged(configuration)
        setContentView(R.layout.activity_change_room_insert4)

        val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val host = sharedPreferences.getString("uid", "")

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

        val roomid = intent.getStringExtra("roomid")


        roomtitle = findViewById<View>(R.id.editText12) as EditText
        content = findViewById<View>(R.id.text23) as EditText
        button22 = findViewById<View>(R.id.button22) as Button


        val backbutton = findViewById<ImageButton>(R.id.imageButton60)

        backbutton.setOnClickListener {
            super.onBackPressed()
        }

// PHP 서버 주소
        val phpUrl = "http://capstone123.dothome.co.kr/myroom1.php"

        // 네트워크 요청 및 응답 처리
        GlobalScope.launch(Dispatchers.IO) {
            try {
                // URL 생성
                val url = URL("$phpUrl?roomid=$roomid")

                // HTTPURLConnection 생성 및 설정
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 5000

                // 응답 코드 확인
                if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                    // 응답 데이터 읽기
                    val inputStream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val response = StringBuilder()
                    var line: String?
                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }

                    withContext(Dispatchers.Main) {
                        val json = JSONObject(response.toString())

                        val roomtitle1 = json.getString("roomtitle")
                        val content1 = json.getString("content")


                        roomtitle.setText(roomtitle1)
                        content.setText(content1)


                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }





        button22.setOnClickListener {

            val roomtitle =  roomtitle!!.text.toString()
            val content = content!!.text.toString()

            val intent = Intent(this, ChangeRoomInsert_5::class.java)
            intent.putExtra("roomid", roomid)

            intent.putExtra("address", address)
            intent.putExtra("detailAddress", detailAddress)
            intent.putExtra("housesort", housesort)
            intent.putExtra("sort_of_floor", sort_of_floor)
            intent.putExtra("num_of_floor", num_of_floor)
            intent.putExtra("area", area)
            intent.putExtra("roomcount", roomcount)
            intent.putExtra("toiletcount", toiletcount)
            intent.putExtra("options", options)
            intent.putExtra("sex", sex)
            intent.putExtra("age", age)
            intent.putExtra("smoke", smoke)
            intent.putExtra("animal", animal)
            intent.putExtra("enterdate", enterdate)
            intent.putExtra("leastdate", leastdate)
            intent.putExtra("gurantee", gurantee)
            intent.putExtra("monthfee", monthfee)
            intent.putExtra("manage", manage)
            intent.putExtra("roomtitle", roomtitle)
            intent.putExtra("content", content)
            startActivity(intent)
        }



    }


}