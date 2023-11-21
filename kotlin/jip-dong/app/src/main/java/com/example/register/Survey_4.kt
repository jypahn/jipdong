package com.example.register

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.register.AddRoom.NetworkUtil
import okhttp3.FormBody
import okhttp3.Callback
import okhttp3.OkHttpClient
import java.io.IOException
import okhttp3.Request

class Survey_4 : AppCompatActivity() {
    private var question_id: String? = null
    private var answer: String? = null

    private lateinit var button40: Button
    private lateinit var button41: Button
    private lateinit var button42: Button
    private lateinit var button43: Button
    private lateinit var button444: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.survey_4)
        NetworkUtil.setNetworkPolicy()

        val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", "")

        val radius = R.drawable.radius
        val okradius = R.drawable.okradius

        button40 = findViewById<View>(R.id.button40) as Button
        button40.setOnClickListener {
            button40.setBackgroundResource(okradius)
            button41.setBackgroundResource(radius)
            question_id = "13"
            answer = "F"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button41 = findViewById<View>(R.id.button41) as Button
        button41.setOnClickListener {
            button41.setBackgroundResource(okradius)
            button40.setBackgroundResource(radius)
            question_id = "13"
            answer = "M"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button42 = findViewById<View>(R.id.button42) as Button
        button42.setOnClickListener {
            button42.setBackgroundResource(okradius)
            button43.setBackgroundResource(radius)
            question_id = "14"
            answer = "R"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button43 = findViewById<View>(R.id.button43) as Button
        button43.setOnClickListener {
            button43.setBackgroundResource(okradius)
            button42.setBackgroundResource(radius)
            question_id = "14"
            answer = "N"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button444= findViewById(R.id.button444)
        button444.setOnClickListener {
            // 다른 액티비티 실행
            val intent = Intent(this,home::class.java)
            startActivity(intent)
        }
    }

    private fun sendDataToServer(uid: String, questionId: String, answer: String) {
        val url = "http://capstone123.dothome.co.kr/survey_insert.php" // PHP 파일의 URL
        val request = Request.Builder()
            .url(url)
            .post(
                FormBody.Builder()
                    .add("uid", uid)
                    .add("question_id", questionId)
                    .add("answer", answer)
                    .build()
            )
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                // 전송 실패 처리
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                // 전송 성공 처리
            }
        })
    }
}