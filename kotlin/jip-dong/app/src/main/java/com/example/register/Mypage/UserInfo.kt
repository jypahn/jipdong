package com.example.register.Mypage

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.example.register.Profile
import com.example.register.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class UserInfo : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        val backbutton = findViewById<ImageButton>(R.id.backbutton)

        backbutton.setOnClickListener {
            val intent = Intent(this, Profile::class.java)
            startActivity(intent)
        }



        // SharedPreferences에서 로그인 정보 가져오기
        val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", "")

        val username = findViewById<TextView>(R.id.username)
        val useremail = findViewById<TextView>(R.id.useremail)
        val userphone = findViewById<TextView>(R.id.userphone)

        // PHP 서버 주소
        val phpUrl = "http://capstone123.dothome.co.kr/UserInfo.php"

        // 네트워크 요청 및 응답 처리
        GlobalScope.launch(Dispatchers.IO) {
            try {
                // URL 생성
                val url = URL("$phpUrl?uid=$uid")

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

                    // UI 업데이트
                    withContext(Dispatchers.Main) {
                        val json = JSONObject(response.toString())
                        val uName = json.getString("u_name")
                        val uEmail = json.getString("email")
                        val uPhone = json.getString("phone")

                        username.text = uName
                        useremail.text = uEmail
                        userphone.text = uPhone
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        val mofifybutton = findViewById<Button>(R.id.modifybutton)

        fun moveToCheckPWPage(){
            val intent = Intent(this, CheckPassword::class.java)
            startActivity(intent)

        }

        mofifybutton.setOnClickListener {
            moveToCheckPWPage()
        }

    }
}