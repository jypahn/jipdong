package com.example.register.Mypage

import android.content.Context
import android.content.Intent
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
import com.example.register.home

class CheckPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_password)
        val backbutton = findViewById<ImageButton>(R.id.backbutton)

        backbutton.setOnClickListener {
            super.onBackPressed()

        }

        val checkpw = findViewById<EditText>(R.id.editPassword)

        val sharedPreferences: SharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE)
        val uid = sharedPreferences.getString("uid", "")

        val checkPasswordButton = findViewById<Button>(R.id.checkPasswordButton)
        checkPasswordButton.setOnClickListener {
            val pw = checkpw.text.toString()
            val url = "http://capstone123.dothome.co.kr/logintest.php" // PHP 파일의 URL을 입력하세요.
            if (pw.isEmpty()) {
                Toast.makeText(this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                checkpw(uid, pw)
            }

        }
    }

    private fun checkpw(uid: String?, pw: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "http://capstone123.dothome.co.kr/logintest.php"

        val request = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                if (response != null) {
                    if (response == "success") {
                        val intent = Intent(this, ChangeUserInfo::class.java)
                        startActivity(intent)
                        finish()
                    } else if (response == "fail") {
                        // 비밀번호 실패
                        Toast.makeText(this, "비밀번호가 불일치.", Toast.LENGTH_SHORT).show()
                    } else { // 아이디 실패
                        Toast.makeText(this, "아이디 없음.", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            Response.ErrorListener { error ->
                // 네트워크 오류 처리
                Toast.makeText(this, "네트워크 오류: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params = HashMap<String, String>()
                params["uid"] = uid ?: "" // Use the passed uid parameter or an empty string if it's null
                params["pw"] = pw
                return params
            }
        }
        queue.add(request)
    }
}

