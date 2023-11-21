package com.example.register

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.register.AddRoom.NetworkUtil
import okhttp3.*
import java.io.IOException


class Survey : AppCompatActivity() {
    private var question_id: String? = null
    private var answer: String? = null

    private lateinit var button56: Button
    private lateinit var button1: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button6: Button
    private lateinit var button7: Button
    private lateinit var button9: Button
    private lateinit var button10: Button
    private lateinit var button111: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.survey)

        val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", "")


        val radius = R.drawable.radius
        val okradius = R.drawable.okradius

        button56 = findViewById<View>(R.id.button56) as Button
        button56.setOnClickListener {
            question_id = "1"
            answer = "E"

            sendDataToServer(uid!!, question_id!!, answer!!)

            button56.setBackgroundResource(okradius)
            button1.setBackgroundResource(radius)
        }

        button1 = findViewById<View>(R.id.button1) as Button
        button1.setOnClickListener {
            button1.setBackgroundResource(okradius)
            button56.setBackgroundResource(radius)
            question_id = "1"
            answer = "L"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button3 = findViewById<View>(R.id.button3) as Button
        button3.setOnClickListener {
            button3.setBackgroundResource(okradius)
            button4.setBackgroundResource(radius)
            question_id = "2"
            answer = "Y"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button4 = findViewById<View>(R.id.button4) as Button
        button4.setOnClickListener {
            button4.setBackgroundResource(okradius)
            button3.setBackgroundResource(radius)
            question_id = "2"
            answer = "N"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button6 = findViewById<View>(R.id.button6) as Button
        button6.setOnClickListener {
            button6.setBackgroundResource(okradius)
            button7.setBackgroundResource(radius)
            question_id = "3"
            answer = "Y"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button7 = findViewById<View>(R.id.button7) as Button
        button7.setOnClickListener {
            button7.setBackgroundResource(okradius)
            button6.setBackgroundResource(radius)
            question_id = "3"
            answer = "N"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button9 = findViewById<View>(R.id.button9) as Button
        button9.setOnClickListener {
            button9.setBackgroundResource(okradius)
            button10.setBackgroundResource(radius)
            question_id = "4"
            answer = "N"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button10 = findViewById<View>(R.id.button10) as Button
        button10.setOnClickListener {
            button10.setBackgroundResource(okradius)
            button9.setBackgroundResource(radius)
            question_id = "4"
            answer = "I"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button111 = findViewById(R.id.button111)
        button111.setOnClickListener {
            // 다른 액티비티 실행
            val intent = Intent(this, Survey_2::class.java)
            startActivity(intent)
        }
    }


    private fun sendDataToServer(uid: String, questionId: String, answer: String) {
        val url = "http://capstone123.dothome.co.kr/survey_insert.php" // PHP 파일의 URL

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("uid", uid)
            .addFormDataPart("question_id", questionId)
            .addFormDataPart("answer", answer)
            .build()

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
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