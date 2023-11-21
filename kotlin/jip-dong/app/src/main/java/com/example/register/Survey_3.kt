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

class Survey_3 : AppCompatActivity() {
    private var question_id: String? = null
    private var answer: String? = null

    private lateinit var button30: Button
    private lateinit var button31: Button
    private lateinit var button32: Button
    private lateinit var button33: Button
    private lateinit var button34: Button
    private lateinit var button35: Button
    private lateinit var button36: Button
    private lateinit var button37: Button
    private lateinit var button38: Button
    private lateinit var button39: Button
    private lateinit var button333: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.survey_3)
        NetworkUtil.setNetworkPolicy()

        val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", "")

        val radius = R.drawable.radius
        val okradius = R.drawable.okradius

        button30 = findViewById<View>(R.id.button30) as Button
        button30.setOnClickListener {
            button30.setBackgroundResource(okradius)
            button31.setBackgroundResource(radius)
            question_id = "9"
            answer = "D"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button31 = findViewById<View>(R.id.button31) as Button
        button31.setOnClickListener {
            button31.setBackgroundResource(okradius)
            button30.setBackgroundResource(radius)
            question_id = "9"
            answer = "S"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button32 = findViewById<View>(R.id.button32) as Button
        button32.setOnClickListener {
            button32.setBackgroundResource(okradius)
            button33.setBackgroundResource(radius)
            question_id = "10"
            answer = "S"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button33 = findViewById<View>(R.id.button33) as Button
        button33.setOnClickListener {
            button33.setBackgroundResource(okradius)
            button32.setBackgroundResource(radius)
            question_id = "10"
            answer = "T"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button34 = findViewById<View>(R.id.button34) as Button
        button34.setOnClickListener {
            button34.setBackgroundResource(okradius)
            button35.setBackgroundResource(radius)
            button36.setBackgroundResource(radius)
            question_id = "11"
            answer = "L"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button35 = findViewById<View>(R.id.button35) as Button
        button35.setOnClickListener {
            button35.setBackgroundResource(okradius)
            button34.setBackgroundResource(radius)
            button36.setBackgroundResource(radius)
            question_id = "11"
            answer = "M"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button36 = findViewById<View>(R.id.button36) as Button
        button36.setOnClickListener {
            button36.setBackgroundResource(okradius)
            button35.setBackgroundResource(radius)
            button34.setBackgroundResource(radius)
            question_id = "11"
            answer = "H"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button37 = findViewById<View>(R.id.button37) as Button
        button37.setOnClickListener {
            button37.setBackgroundResource(okradius)
            button38.setBackgroundResource(radius)
            button39.setBackgroundResource(radius)
            question_id = "12"
            answer = "L"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button38 = findViewById<View>(R.id.button38) as Button
        button38.setOnClickListener {
            button38.setBackgroundResource(okradius)
            button37.setBackgroundResource(radius)
            button39.setBackgroundResource(radius)
            question_id = "12"
            answer = "M"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button39 = findViewById<View>(R.id.button37) as Button
        button39.setOnClickListener {
            button39.setBackgroundResource(okradius)
            button38.setBackgroundResource(radius)
            button37.setBackgroundResource(radius)
            question_id = "12"
            answer = "H"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button333= findViewById(R.id.button333)
        button333.setOnClickListener {
            // 다른 액티비티 실행
            val intent = Intent(this,Survey_4::class.java)
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