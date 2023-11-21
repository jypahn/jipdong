package com.example.register.chatting

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.register.R

class makechat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_makechat)

        val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", "")

        val backbutton = findViewById<ImageButton>(R.id.backbutton)

        backbutton.setOnClickListener {
            super.onBackPressed()
        }

        val createbutton = findViewById<Button>(R.id.createoombutton)

        val chatid = findViewById<EditText>(R.id.chatId)

        createbutton.setOnClickListener {
            val user2_id = chatid.text.toString()

            // 서버 URL 및 PHP 파일 경로 설정
            val url = "http://capstone123.dothome.co.kr/createchatroom.php"

            // Volley 라이브러리를 사용하여 서버에 요청 보내기
            val stringRequest = object : StringRequest(
                Request.Method.POST, url,
                Response.Listener<String> { response ->
                    if (response == "이미 생성된 채팅방") {
                        Toast.makeText(this, "존재하는 채팅방", Toast.LENGTH_SHORT).show()
                    } else if (response == "채팅방 생성") {
                        Toast.makeText(this, "채팅방 생성 완료", Toast.LENGTH_SHORT).show()
                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
                }) {
                override fun getParams(): Map<String, String> {
                    val params = HashMap<String, String>()
                    params["user1_id"] = uid!!
                    params["user2_id"] = user2_id
                    return params
                }
            }

            Volley.newRequestQueue(this).add(stringRequest)
        }
    }
}