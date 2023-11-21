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

class Survey_2 : AppCompatActivity() {
    private var question_id: String? = null
    private var answer: String? = null

    private lateinit var button20: Button
    private lateinit var button21: Button
    private lateinit var button22: Button
    private lateinit var button23: Button
    private lateinit var button24: Button
    private lateinit var button25: Button
    private lateinit var button26: Button
    private lateinit var button27: Button
    private lateinit var button222: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.survey_2)
        NetworkUtil.setNetworkPolicy()

        val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", "")

        val radius = R.drawable.radius
        val okradius = R.drawable.okradius

        button20 = findViewById<View>(R.id.button20) as Button
        button20.setOnClickListener {
            button20.setBackgroundResource(okradius)
            button21.setBackgroundResource(radius)
            question_id = "5"
            answer = "R"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button21 = findViewById<View>(R.id.button21) as Button
        button21.setOnClickListener {
            button21.setBackgroundResource(okradius)
            button20.setBackgroundResource(radius)
            question_id = "5"
            answer = "P"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button22 = findViewById<View>(R.id.button22) as Button
        button22.setOnClickListener {
            button22.setBackgroundResource(okradius)
            button23.setBackgroundResource(radius)
            question_id = "6"
            answer = "Y"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button23 = findViewById<View>(R.id.button23) as Button
        button23.setOnClickListener {
            button23.setBackgroundResource(okradius)
            button22.setBackgroundResource(radius)
            question_id = "6"
            answer = "N"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button24 = findViewById<View>(R.id.button24) as Button
        button24.setOnClickListener {
            button24.setBackgroundResource(okradius)
            button25.setBackgroundResource(radius)
            question_id = "7"
            answer = "Y"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button25 = findViewById<View>(R.id.button25) as Button
        button25.setOnClickListener {
            button25.setBackgroundResource(okradius)
            button24.setBackgroundResource(radius)
            question_id = "7"
            answer = "N"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button26 = findViewById<View>(R.id.button26) as Button
        button26.setOnClickListener {
            button26.setBackgroundResource(okradius)
            button27.setBackgroundResource(radius)
            question_id = "8"
            answer = "Y"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button27 = findViewById<View>(R.id.button27) as Button
        button27.setOnClickListener {
            button27.setBackgroundResource(okradius)
            button26.setBackgroundResource(radius)
            question_id = "8"
            answer = "N"
            sendDataToServer(uid!!, question_id!!, answer!!)
        }

        button222= findViewById(R.id.button222)
        button222.setOnClickListener {
            // 다른 액티비티 실행
            val intent = Intent(this,Survey_3::class.java)
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