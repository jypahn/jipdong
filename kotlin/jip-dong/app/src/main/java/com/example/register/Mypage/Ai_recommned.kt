package com.example.register.Mypage

import com.bumptech.glide.Glide
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.register.AddRoom.NetworkUtil
import com.example.register.FindRoommate.Roommate
import com.example.register.R
import com.example.register.Survey
import com.example.register.home
import okhttp3.FormBody
import okhttp3.Request
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.Charset


class Ai_recommned: AppCompatActivity() {
    // 버튼 클릭 이벤트 처리 메소드
    private lateinit var button999: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        NetworkUtil.setNetworkPolicy()
        setContentView(R.layout.ai_recommend)

        val backbutton = findViewById<ImageButton>(R.id.backbutton)

        backbutton.setOnClickListener {
            val intent = Intent(this, home::class.java)
            startActivity(intent)
            finish()
        }

        button999 = findViewById<View>(R.id.button999) as Button

        button999.setOnClickListener {
            // PHP 파일 실행하여 데이터베이스 값 확인
            val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
            val uid = sharedPreferences.getString("uid", "")
            val hasValue = uid?.let { it1 -> checkValueInDatabase(it1) }

            // 조건에 따라 액티비티 시작
            if (hasValue == true) {
                // A 액티비티 시작
                val intent = Intent(this, Ai_result::class.java)
                startActivity(intent)
            } else {
                // B 액티비티 시작
                val intent = Intent(this, Survey::class.java)
                startActivity(intent)
            }
        }
    }

    // PHP 파일 실행하여 데이터베이스 값 확인하는 메소드
    private fun checkValueInDatabase(uid: String): Boolean {
        return try {
            val url = URL("http://capstone123.dothome.co.kr/survey_value_check.php")
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true

            val postData: String = URLEncoder.encode("uid", "UTF-8") + "=" + URLEncoder.encode(uid, "UTF-8")

            val outputStream: OutputStream = connection.outputStream
            outputStream.write(postData.toByteArray(Charset.forName("UTF-8")))
            outputStream.flush()
            outputStream.close()

            val responseCode: Int = connection.responseCode
            if (responseCode == HttpURLConnection.HTTP_OK) {
                val inputStream: InputStream = connection.inputStream
                val reader = BufferedReader(InputStreamReader(inputStream))
                val response: String = reader.readLine()

                // 값을 확인하여 결과 반환
                "true" == response
            } else {
                // 연결 실패 또는 응답 오류
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}